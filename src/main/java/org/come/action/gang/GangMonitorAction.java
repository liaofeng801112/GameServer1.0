package org.come.action.gang;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.NPCDialog;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterUtil;
import org.come.until.GsonUtil;

import come.tool.BangBattle.BangBattlePool;
import come.tool.BangBattle.BangFight;
import come.tool.BangBattle.Build;
import come.tool.PK.PKStake;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.BTY.BTYRole;
import come.tool.Scene.BTY.BTYScene;
import come.tool.Scene.DNTG.DNTGScene;
import come.tool.Scene.LTS.LTSArena;
import come.tool.Scene.LTS.LTSScene;
import come.tool.Scene.RC.RCScene;
import come.tool.Scene.SLDH.SLDHScene;
import come.tool.Scene.TGDB.TGDBScene;
import come.tool.Scene.ZZS.ZZSRole;
import come.tool.Scene.ZZS.ZZSScene;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;

public class GangMonitorAction implements IAction{
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		if (message.startsWith("M")) {		
			int zhi=Integer.parseInt(message.substring(1));
			MapMonsterBean bean=MonsterUtil.getMonster(zhi);
			if (bean==null) {
				return;
			}
			if (bean.getRobotType()>=100&&bean.getRobotType()<=199) {
				DNTG(bean, ctx);
			}
			return;
		}

		int zhi=Integer.parseInt(message);
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		//控制对话
		if (zhi==0) {
			BangBattlePool pool=BangBattlePool.getBangBattlePool();
	     	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
			//我要给塔充能or取消操作
			if (bangFight==null)return;
			NPCDialog npcDialog=new NPCDialog();
			List<String> functions=new ArrayList<>();
			Build build=bangFight.getBuild(zhi);
			if (build.getRoleName().equals("")) {
				npcDialog.setMsg("现在没有人在操控龙神大炮");
				functions.add("我要给塔充能");
			}else if (build.getRoleName().equals(roleInfo.getRolename())) {
				npcDialog.setMsg("你正在操控龙神大炮");
				functions.add("我要取消操作");
			}else {
				npcDialog.setMsg("龙神大炮正在被"+build.getRoleName()+"操控");
				functions.add("我要掐断炮火");
			}
			npcDialog.setFunctions(functions);
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
		}else if (zhi==1||zhi==11) {
			//我要攻击塔or取消操作
			BangBattlePool pool=BangBattlePool.getBangBattlePool();
	     	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
			if (bangFight==null)return;
			NPCDialog npcDialog=new NPCDialog();
			List<String> functions=new ArrayList<>();
			Build build=bangFight.getBuild(zhi);
			if (build.getRoleName().equals("")) {
				npcDialog.setMsg("当前城门没有人在攻击");
				if (bangFight.getBuildCamp(zhi).compareTo(roleInfo.getGang_id())!=0)
					functions.add("我要攻击塔");
			}else if (build.getRoleName().equals(roleInfo.getRolename())) {
				npcDialog.setMsg("你正在进攻城门");
				functions.add("我要取消操作");
			}else {
				npcDialog.setMsg("城门正在被"+build.getRoleName()+"攻击");
			}
			npcDialog.setFunctions(functions);
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
		}else if (zhi==886) {
			NPCDialog npcDialog=new NPCDialog();
			List<String> functions=new ArrayList<>();
			functions.add("我要前往武神山");
			functions.add("我什么都不做");
			npcDialog.setFunctions(functions);
			npcDialog.setType("N886");
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));
		}else if (zhi==1002) {
			//我要挑战or我要迎战or我要取消挑战 and 进入营地
			BangBattlePool pool=BangBattlePool.getBangBattlePool();
	     	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
			NPCDialog npcDialog=new NPCDialog();
			List<String> functions=new ArrayList<>();
			if (bangFight!=null) {
				BigDecimal camp=bangFight.getPK();
				if (camp==null) {
					npcDialog.setMsg("当前没有人在挑战");
					functions.add("我要挑战");				
				}else if (bangFight.Launch.getKey().equals(roleInfo.getRolename())) {
					functions.add("我要取消挑战");		
				}else if (camp.compareTo(roleInfo.getGang_id())!=0) {
					npcDialog.setMsg("当前正在的挑战的队伍是"+bangFight.Launch.getKey());
					functions.add("我要应战");			
				}else {
					npcDialog.setMsg("当前在挑战的人和你是同阵营");
				}
			}
			functions.add("回到营地");
			npcDialog.setFunctions(functions);
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
		}else if (zhi==1101) {
			NPCDialog npcDialog=new NPCDialog();
            List<String> functions=new ArrayList<>();
            functions.add("我要去守卫蟠桃园");
            functions.add("我要领取守卫蟠桃园奖励");
			npcDialog.setFunctions(functions);
			Scene scene=SceneUtil.getScene(SceneUtil.BTYID);
			if (scene==null) {
				npcDialog.setMsg("活动尚未开始");
			}else {
				BTYScene btyScene=(BTYScene) scene;
				StringBuffer buffer=new StringBuffer();
				if (btyScene.getI()==1) {
					buffer.append("活动正在进行,");
				}else if (btyScene.getI()==3) {
					buffer.append("本次活动胜利,");
				}else if (btyScene.getI()==4) {
					buffer.append("本次活动失败,");
				}
//				你本次活动积分
				BTYRole btyRole=btyScene.getRole(roleInfo.getRolename());
				if (btyRole==null) {
					buffer.append("你未参与活动");
				}else {
					buffer.append("你本次活动积分:");
					buffer.append(btyRole.getJf());
				}
				npcDialog.setMsg(buffer.toString());
			}
		    SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
		}else if (zhi>=1102&&zhi<=1105) {
			TGDB(zhi-1101,ctx);
		}else if (zhi==1107) {
			ZZS(ctx);
		}else if (zhi>=510&&zhi<=514) {
			LTS(zhi, ctx);
		}else if (zhi==521) {
			RCFB(zhi, ctx);
		}else if (zhi==2020) {
			SLDH(zhi, ctx);
		}else if (zhi==2022||zhi==2023) {
			GangNpc(zhi, ctx);
		}else {
			BangBattlePool pool=BangBattlePool.getBangBattlePool();
	     	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
			//我要给塔充能or我要攻击塔or取消操作
			if (bangFight==null)return;
			NPCDialog npcDialog=new NPCDialog();
			List<String> functions=new ArrayList<>();
			Build build=bangFight.getBuild(zhi);
			if (build.getRoleName().equals("")) {
				npcDialog.setMsg("当前"+build.getName()+"没有人在操作");
				if (bangFight.getBuildCamp(zhi).compareTo(roleInfo.getGang_id())!=0)
					 functions.add("我要攻击塔");
				else functions.add("我要给塔充能");
			}else if (build.getRoleName().equals(roleInfo.getRolename())) {
				npcDialog.setMsg("你正在操控"+build.getName());
				functions.add("我要取消操作");
			}else {
				npcDialog.setMsg(build.getName()+"正在被"+build.getRoleName()+"操控");
			}
			npcDialog.setFunctions(functions);
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
		}
	}
	static String MSG1;
	static String MSG2;
	static{
		NPCDialog npcDialog=new NPCDialog();
		npcDialog.setMsg("未到开放时间");
		MSG1=Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog));
		npcDialog.setMsg("前一层还未通过");
		MSG2=Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog));
	}
	/**宝库npc点击*/
	public void TGDB(int i,ChannelHandlerContext ctx){
		Scene scene=SceneUtil.getScene(SceneUtil.TGDBID);
		if (scene==null) {
			SendMessage.sendMessageToSlef(ctx,MSG1);	
			return;
		}
		TGDBScene tgdbScene=(TGDBScene)scene;
		int type=tgdbScene.isC(i);
		if (type==2) {
			SendMessage.sendMessageToSlef(ctx,MSG2);	
			return;
		}	
		NPCDialog npcDialog=new NPCDialog();
		npcDialog.setMsg("剩余生命值:"+tgdbScene.getTgdbMonsters()[i-1].getHp());
		List<String> functions=new ArrayList<>();
		if (type==0) {
			if (i==1) {
				functions.add("进入宝库二层");
			}else if (i==2) {
				functions.add("进入宝库三层");
			}else if (i==3) {
				functions.add("进入宝库四层");
			}
		}else if (type==1) {
			if (i==1) {
				functions.add("挑战一层守护者");
			}else if (i==2) {
				functions.add("挑战二层守护者");
			}else if (i==3) {
				functions.add("挑战三层守护者");
			}else if (i==4) {
				functions.add("挑战四层守护者");
			}
		}
		npcDialog.setFunctions(functions);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
	}
	public void ZZS(ChannelHandlerContext ctx){
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {
    		return;
    	}
    	ZZSScene zzsScene=SceneUtil.getZZS(roleInfo);
    	if (zzsScene==null) {
    		SendMessage.sendMessageToSlef(ctx,MSG1);	
    		return;
		}
    	ZZSRole role=zzsScene.getRole(roleInfo);
    	NPCDialog npcDialog=new NPCDialog();
    	if (role!=null) {	
    		npcDialog.setMsg(zzsScene.getRanking(role));
    		if (zzsScene.getI()==2) {
				List<String> functions=new ArrayList<>();
				if (role.getI()==1) {
					functions.add("取消种族赛匹配");
				}else if (role.getI()==0) {
					functions.add("种族赛匹配");
				}
				npcDialog.setFunctions(functions);
			}
		}else {
			npcDialog.setMsg("没有你的报名数据");
		}
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
	}
	/**擂台赛*/
	public void LTS(int i,ChannelHandlerContext ctx){
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
		Scene scene=SceneUtil.getScene(SceneUtil.LTSID);
		if (scene==null) {
			SendMessage.sendMessageToSlef(ctx,MSG1);	
			return;
		}
		LTSScene ltsScene=(LTSScene)scene;
		LTSArena arena=ltsScene.getArena(i);
		if (arena==null) {return;}
		NPCDialog npcDialog=new NPCDialog();
		if (arena.getBattleNumber()!=0) {
			StringBuffer buffer=new StringBuffer();
			buffer.append("当前:  ");
			buffer.append(arena.getPkMatch().getPkMan1().getRoleName());
			buffer.append("  正在与  ");
			buffer.append(arena.getPkMatch().getPkMan2().getRoleName());
			buffer.append("  交战");
			npcDialog.setMsg(buffer.toString());
			List<String> functions=new ArrayList<>();
			functions.add("我要观战");
			npcDialog.setFunctions(functions);
		}else if (arena.getPkMatch()==null) {
			npcDialog.setMsg("无人摆擂");
			List<String> functions=new ArrayList<>();
			functions.add("我要下战书");
			npcDialog.setFunctions(functions);
		}else{
			StringBuffer buffer=new StringBuffer();
			buffer.append("当前擂主:  ");
			buffer.append(arena.getPkMatch().getPkMan1().getRoleName());
			PKStake pkStake=arena.getPkMatch().getpKStake1();
			if (pkStake.getXianYu()!=0) {
				buffer.append(",下注仙玉:");	
				buffer.append(pkStake.getXianYu());	
			}
			if (pkStake.getMoney()!=0) {
				buffer.append(",下注大话币:");	
				buffer.append(pkStake.getMoney());	
			}
			npcDialog.setMsg(buffer.toString());
			List<String> functions=new ArrayList<>();
			if (arena.getPkMatch().getPkMan1().getRoleId().compareTo(roleInfo.getRole_id())==0) {
				functions.add("我要取消战书");
			}else {
				functions.add("我要接收战书");
			}
			npcDialog.setFunctions(functions);
		}
		npcDialog.setType("N"+i);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
	}
    /**日常副本*/
    public void RCFB(int value,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.RCID);
		if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		RCScene rcScene=(RCScene) scene;
		NPCDialog npcDialog=new NPCDialog();
		npcDialog.setMsg(rcScene.getBBMsg());
		List<String> functions=new ArrayList<>();
		functions.add("开启桃源仙境");
		npcDialog.setFunctions(functions);
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
    }
    /**大闹天宫*/
    public void DNTG(MapMonsterBean bean,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
		if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		DNTGScene dntgScene=(DNTGScene) scene;
		String msg=dntgScene.getDialog(bean,roleInfo);
		if (msg!=null) {
			SendMessage.sendMessageToSlef(ctx,msg);	
		}
    }
    /**水陆大会*/
    public void SLDH(int value,ChannelHandlerContext ctx){
    	Scene scene=SceneUtil.getScene(SceneUtil.SLDHID);
		if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		SLDHScene sldhScene=(SLDHScene) scene;
		NPCDialog npcDialog=new NPCDialog();
		npcDialog.setMsg(sldhScene.getMsg());
		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog)));	
    }
    /**帮派*/
    public void GangNpc(int value,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	GangDomain domain=GangUtil.getGangDomain(roleInfo.getGang_id());
    	if (domain==null) {
    		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你还没有帮派"));
    		return;
		}
    	String msg=domain.getMsg(value);
    	if (msg!=null) {
    		SendMessage.sendMessageToSlef(ctx,msg);	
        }
    	
    }
}
