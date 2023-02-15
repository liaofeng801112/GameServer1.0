package come.tool.PK;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.Role.RoleCard;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.LTS.LTSArena;
import come.tool.Scene.LTS.LTSRole;
import come.tool.Scene.LTS.LTSScene;
import come.tool.Stall.AssetUpdate;
/**下战书*/
public class BookofchalgAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		//0皇宫   1同意 2拒绝  11是擂台赛我要下战书  12是我要应战   13我要观战
		LoginResult login1=GameServer.getAllLoginRole().get(ctx);
		if (login1==null) {return;}
		PalacePkBean palacePkBean = GsonUtil.getGsonUtil().getgson().fromJson(message,PalacePkBean.class);
		palacePkBean.setExp(null);
		
		if (palacePkBean.getType()==0||palacePkBean.getType()==11) {
			PKStake pKStake=YZ(palacePkBean, login1);
			if (pKStake==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不够资本"));return;}		
			LoginResult login2=null;
			if (palacePkBean.getType()==0) {
				ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(palacePkBean.getUsername());
				if (ctx2==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你输入的玩家还没上线"));return;}	
				login2=GameServer.getAllLoginRole().get(ctx2);
			}
			PkMatch pkMatch=PKPool.getPkPool().seekPkMatch(login1.getRole_id());
			if (pkMatch!=null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你身上有战书未解决没法重新申请战书"));return;}
            String[] teams=login1.getTeam().split("\\|");
            if (!teams[0].equals(login1.getRolename())) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你不是队长没法下战书"));return;}
            LTSArena arena=null;
            if (palacePkBean.getType()==11) {
    			Scene scene=SceneUtil.getScene(SceneUtil.LTSID);
    			if (scene==null||scene.isEnd()) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("擂台赛未开启"));return;}
    			LTSScene ltsScene=(LTSScene)scene;
    			arena=ltsScene.getArena(palacePkBean.getNtype());
    			if (arena==null) {return;}
    			if (arena.getPkMatch()!=null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("已经有人在摆擂了"));return;}
    			long time=System.currentTimeMillis();
    			for (int i = 0; i < teams.length; i++) {
					LoginResult login=null;
					if (i==0) {login=login1;}
					else {
						ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(teams[i]);
						if (ctx2!=null) {login=GameServer.getAllLoginRole().get(ctx2);}
					}
					if (login==null||login.getFighting()!=0) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("队伍中有人没在状态"));return;
					}else if (login.getGrade()>arena.getMaxLvl()) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你们等级太高了"));return;
					}else if (login.getGrade()<399) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你们等级太低了"));return;
					}
					LTSRole ltsRole=ltsScene.getRole(login.getRolename());
					if (ltsRole==null||ltsRole.getZBnum()>=3) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(login.getRolename()+"失败次数过多没有参赛资格了"));return;
					}
					if (time<=ltsRole.getTime()) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(login.getRolename()+"处于调整时间内,无法战斗"));return;
					}
				}
			}
			RoleCard roleCard1=new RoleCard(login1.getRole_id(),login1.getRolename(),login1.getUserName());
		    RoleCard roleCard2=null;
		    if (login2!=null) {
		    	roleCard2=new RoleCard(login2.getRole_id(),login2.getRolename(),login2.getUserName());
			}
			PkMatch match=PKPool.getPkPool().addPkMatch(roleCard1, roleCard2, pKStake,palacePkBean.getType());
			if (match==null) {return;}
			if (arena!=null) {arena.setPkMatch(match);}
			AssetUpdate assetUpdate=YZKC(pKStake,login1);//先扣除资本
			laba(palacePkBean,login1,assetUpdate);		
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate))); 
			
		    if (palacePkBean.getType()==0) {//发送挑战
		    	palacePkBean.setPId(match.getPid());
	    	    palacePkBean.setUsername(login1.getRolename());
	    	    message=Agreement.getAgreement().bookofchalgAgreement(GsonUtil.getGsonUtil().getgson().toJson(palacePkBean));
	    	    SendMessage.sendMessageByRoleName(login2.getRolename(),message);	
	    	}
		    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("战书申请成功,战书存在期间一些功能被限制"));
		}else if (palacePkBean.getType()==1) {//同意
			PkMatch pkMatch=PKPool.getPkPool().seekPkMatch(palacePkBean.getPId());
			if (pkMatch==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("该战书已经过期了"));return;}
			if (pkMatch.getState()!=0) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("战书已经被生效了"));return;}
			String[] teams1=login1.getTeam().split("\\|");
			if (!teams1[0].equals(login1.getRolename())) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你不是队长没法同意"));return;}
            LoginResult login2=null;
			ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(pkMatch.getPkMan1().getRoleName());
			if (ctx2!=null) {login2=GameServer.getAllLoginRole().get(ctx2);}
			//擂主不在
			if (login2==null) {
				PKPool.getPkPool().cancelPkMatch(pkMatch);
				if (pkMatch.getType()==11) {
                	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("有一方认怂了战书作废"));	
    			}
				return;
			}
			String[] teams2=login2.getTeam().split("\\|");
			if (!teams2[0].equals(login2.getRolename())) {
				PKPool.getPkPool().cancelPkMatch(pkMatch);
                if (pkMatch.getType()==11) {
                	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("有一方认怂了战书作废"));	
    			}
				return;
			}
			PKStake pKStake=pkMatch.getpKStake1();
			pKStake=YZ(pKStake,login1);
			if (pKStake==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不够资本"));return;}	
            LTSScene ltsScene=null;
            LTSArena arena=null;
            if (pkMatch.getType()==11) {
          	   Scene scene=SceneUtil.getScene(SceneUtil.LTSID);
  			   if (scene==null||scene.isEnd()) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("擂台赛未开启"));return;}
  			   ltsScene=(LTSScene)scene;
  			   if (ltsScene.isLZ(login1.getRole_id())) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已经是擂主了"));return;}
  			   arena=ltsScene.getArena(pkMatch);
  			   if (arena==null) {return;}
  			   if (teams2.length>teams1.length) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("必须大于等于擂主队伍人数:"+teams2.length));return;}
            }
			BattleData battleData=new BattleData();		
			battleData.setBattleType(33);
			battleData.setBattletime(System.currentTimeMillis());
			battleData.setTeam1(teams2);
			battleData.setTeam2(teams1);
			String value=BattleThreadPool.check2(teams2, battleData,0,arena,ltsScene);
			if (value!=null) {
				SendMessage.sendMessageByRoleName(teams1[0],value);
				SendMessage.sendMessageByRoleName(teams2[0],value);
				return;
			}
			value=BattleThreadPool.check2(teams1, battleData,1,arena,ltsScene);
			if (value!=null) {
				SendMessage.sendMessageByRoleName(teams1[0],value);
				SendMessage.sendMessageByRoleName(teams2[0],value);
				return;
			}
		    //先扣除资本
		    AssetUpdate assetUpdate=YZKC(pKStake,login1);//先扣除资本
		    laba(palacePkBean,login1,assetUpdate);
		    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate))); 
		    pkMatch.PKAgree(pKStake);
			if (arena!=null) {
				RoleCard roleCard=new RoleCard(login1.getRole_id(),login1.getRolename(),login1.getUserName());   
	  			PKPool.getPkPool().addLTSPkMatch(roleCard, pkMatch);
			}
			battleData.setBattleNumber(BattleThreadPool.getIncreasesum());
		    battleData.setPkMatch(pkMatch);
			pkMatch.setBattleNumber(battleData.getBattleNumber());
			battleData.setCalculator(battleData.getParticipantlist().size());
			PKPool.getPkPool().endPkMatch(battleData.getRoleId1(),battleData.getRoleId2());
			if (arena!=null) {arena.setBattleNumber(battleData.getBattleNumber());}
			BattleThreadPool.addPool(battleData);
		}else if (palacePkBean.getType()==2) {//拒绝
			PkMatch pkMatch=PKPool.getPkPool().seekPkMatch(login1.getRole_id());
			if (pkMatch!=null&&pkMatch.getType()==0) {
				if (pkMatch.getState()<=1) {
					PKPool.getPkPool().cancelPkMatch(pkMatch);
				}
			}
		}	
	}
	/**验资*/
	public PKStake YZ(PalacePkBean pkBean,LoginResult login){
		long money=0;
		long xianYu=0;
		long exp=0;
		long charge=2000000;
		if (pkBean.getGold()!=null) {money=pkBean.getGold().longValue();}
		if (pkBean.getXianyu()!=null) {xianYu=pkBean.getXianyu().longValue();}
		if (pkBean.getExp()!=null) {xianYu=pkBean.getExp().longValue();}
		if (money<0||xianYu<0||exp<0||charge<0) {return null;}
		//判断是否有足够的金额
		if (login.getGold().longValue()<(money+charge)||login.getCodecard().longValue()<xianYu||login.getExperience().longValue()<exp) {
			return null;
		}
		return new PKStake(charge,money,xianYu,exp);
	}
	/**验资*/
	public PKStake YZ(PKStake pkStake,LoginResult login){
		long money=pkStake.getMoney();
		long xianYu=pkStake.getXianYu();
		long exp=pkStake.getExp();
		long charge=0;
		if (money<0||xianYu<0||exp<0||charge<0) {return null;}
		//判断是否有足够的金额
		if (login.getGold().longValue()<(money+charge)||login.getCodecard().longValue()<xianYu||login.getExperience().longValue()<exp) {return null;}
		return new PKStake(charge,money,xianYu,exp);
	}
	/**KC扣除*/
	public AssetUpdate YZKC(PKStake pkStake,LoginResult login){
		//先扣除资本
		AssetUpdate assetUpdate=new AssetUpdate();
		assetUpdate.setType(AssetUpdate.STEALING);
		login.setGold(new BigDecimal(login.getGold().longValue()-(pkStake.getMoney()+pkStake.getCharge())));
		assetUpdate.updata("D="+(-(pkStake.getMoney()+pkStake.getCharge())));
		if (pkStake.getXianYu()!=0) {//扣仙玉
			login.setCodecard(new BigDecimal(login.getCodecard().longValue()-pkStake.getXianYu()));
			assetUpdate.updata("X="+(-pkStake.getXianYu()));
		}
		if (pkStake.getExp()!=0) {//扣经验
			login.setExperience(new BigDecimal(login.getExperience().longValue()-pkStake.getExp()));
			assetUpdate.updata("E="+(-pkStake.getExp()));
		}
		return assetUpdate;
	}
	/**喇叭消耗*/
	public void laba(PalacePkBean pkBean,LoginResult login,AssetUpdate asset){
		if (pkBean.getType()==11) {
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("#G"+login.getRolename()+"#c00FFFFP再#Y擂台挑战赛中#c00FFFFP成功抢夺擂台擂主，挑战天下豪杰，有那位英雄敢来应战#80.");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToMapRoles(3333L,msg);	
		}
		if (pkBean.getChoices()==0||pkBean.getSendStr()==null||pkBean.getSendStr().equals("")) {return;}
//		4传音 5系统
		int type=0;
		if ((pkBean.getChoices()&0x01)==1) {//公告
			if (login.getGold().longValue()>5000000) {
				login.setGold(new BigDecimal(login.getGold().longValue()-5000000));
				asset.updata("D=-5000000");
				type=5;
			}
		}
        if ((pkBean.getChoices()>>>1&0x01)==1) {//喇叭
        	if (login.getCodecard().longValue()>100) {
				login.setCodecard(new BigDecimal(login.getCodecard().longValue()-100));
				asset.updata("X=-100");
				if (type==5) {type=7;}
				else {type=4;}
			}
		}
        if (type!=0) {
        	NChatBean bean=new NChatBean();
			bean.setId(type);
			bean.setMessage(pkBean.getSendStr());
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToAllRoles(msg);
		}
	}
	static long CD=1000000;
	/**根据等级解出几级*/
	public int lvlint(int lvl){
		if (lvl<=102) {
			return lvl;
		}else if (lvl<=210) {
			return (lvl-102+14);
		}else if (lvl<=338) {
			return (lvl-210+14);
		}else if (lvl<=459){
			return (lvl-338+59);	
		}else {
			return (lvl-459+139);
		}
	} 	
}
