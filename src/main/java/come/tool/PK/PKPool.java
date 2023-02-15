package come.tool.PK;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.LoginResult;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.Role.RoleCard;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.LTS.LTSScene;
import come.tool.Stall.AssetUpdate;

public class PKPool {

	private static PKPool pkPool=new PKPool();
	public static PKPool getPkPool() {
		return pkPool;
	}
	public PKPool() {
		// TODO Auto-generated constructor stub
		PkMatchs =new ConcurrentHashMap<>();
		PkMatchId=new ConcurrentHashMap<>();
	}
	//记录皇宫PK的带队人
	private ConcurrentHashMap<String,PkMatch> PkMatchs;
	private ConcurrentHashMap<BigDecimal,PkMatch> PkMatchId;
	
	//根据一个id遍历查找
	public PkMatch seekPkMatch(BigDecimal pkMan1){
		return PkMatchId.get(pkMan1);
	}
	/**添加挑战者*/
	public void addLTSPkMatch(RoleCard roleCard,PkMatch pkMatch){
		if (pkMatch.getPkMan2()!=null) {return;}
		pkMatch.setPkMan2(roleCard);
		PkMatchId.put(roleCard.getRoleId(), pkMatch);
	}
	//根据一个id遍历查找
	public PkMatch seekPkMatch(int pid){
		for (PkMatch value : PkMatchs.values()) { 
			if (value!=null) {
				if (value.getPid()==pid) {
				   return value;
				}
			}
		}
		return null;
	}
	/**添加*/
	public PkMatch addPkMatch(RoleCard pkMan1,RoleCard pkMan2,PKStake pKStake,int Ptype){
		PkMatch match=new PkMatch(BattleThreadPool.getIncreasesum(),Ptype,pkMan1,pkMan2,pKStake);
		if (pkMan2!=null) {
			PkMatchs.put(pkMan1.getRoleId()+":"+pkMan2.getRoleId(), match);		
			PkMatchId.put(pkMan1.getRoleId(), match);
			PkMatchId.put(pkMan2.getRoleId(), match);		
		}else {
			PkMatchs.put(pkMan1.getRoleId().toString(), match);		
			PkMatchId.put(pkMan1.getRoleId(), match);
		}
		return match;
	}
	/**如果已经开始打架是胜利结束皇宫处理 未开始 就是状态调整*/
	public void endPkMatch(BigDecimal pkMan1,BigDecimal pkMan2){
		PkMatch pkMatch=getPkMatch(pkMan1,pkMan2);
		if (pkMatch!=null) {
			int type=pkMatch.isPKStart();
			if (type==0||type==1) {return;}
			deletePkMatch(pkMan1, pkMan2);
			if (pkMatch.getPkMan1().getRoleId().compareTo(pkMan1)==0) {
				PKStake pKStake=pkMatch.getpKStake1();
				pKStake.setCharge(0);
				if (pkMatch.getType()!=11) {
					pKStake.setMoney(pKStake.getMoney()+pkMatch.getpKStake2().getMoney());
					pKStake.setXianYu(pKStake.getXianYu()+pkMatch.getpKStake2().getXianYu());			
				}
				returnStake(pkMatch.getPkMan1(), pKStake,"你获得挑战的胜利");
				pkMatch.getpKStake2().qk();
			}else {
				PKStake pKStake=pkMatch.getpKStake2();
				if (pKStake!=null) {
					pKStake.setCharge(0);
					pKStake.setMoney(pKStake.getMoney()+pkMatch.getpKStake1().getMoney());
					pKStake.setXianYu(pKStake.getXianYu()+pkMatch.getpKStake1().getXianYu());
					returnStake(pkMatch.getPkMan2(), pKStake,"你获得挑战的胜利");
					pkMatch.getpKStake1().qk();
				}
			}
            if (pkMatch.getType()==11) {
            	Scene scene=SceneUtil.getScene(SceneUtil.LTSID);
    			if (scene!=null) {
    				LTSScene ltsScene=(LTSScene)scene;	
    				ltsScene.addJF(pkMatch, 1, pkMan1);
    				
    			}	
			}
		}
	}
	public PkMatch getPkMatch(BigDecimal pkMan1,BigDecimal pkMan2){
		PkMatch match=PkMatchs.get(pkMan1+":"+pkMan2);
		if (match==null) {match=PkMatchs.get(pkMan2+":"+pkMan1);}
		if (match==null) {match=PkMatchs.get(pkMan1.toString());}
		if (match==null) {match=PkMatchs.get(pkMan2.toString());}
		return match;
	}
	//删除
	public PkMatch deletePkMatch(BigDecimal pkMan1,BigDecimal pkMan2){
		PkMatch match=PkMatchs.remove(pkMan1+":"+pkMan2);
		if (match==null) {
			match=PkMatchs.remove(pkMan2+":"+pkMan1);
		}
		if (match==null&&pkMan1!=null) {
			match=PkMatchs.remove(pkMan1.toString());
		}
		if (match==null&&pkMan2!=null) {
			match=PkMatchs.remove(pkMan2.toString());
		}
		if (match!=null) {
			if (match.getPkMan1()!=null) {
				PkMatchId.remove(match.getPkMan1().getRoleId());		
			}
			if (match.getPkMan2()!=null) {
				PkMatchId.remove(match.getPkMan2().getRoleId());		
			}
		}
		return match;
	}
	/**取消PK*/
	public void cancelPkMatch(PkMatch pkMatch){
		pkMatch=deletePkMatch(pkMatch.getPkMan1().getRoleId(), pkMatch.getPkMan2()!=null?pkMatch.getPkMan2().getRoleId():null);
	    if (pkMatch!=null) {//退还赌注
	    	returnStake(pkMatch.getPkMan1(), pkMatch.getpKStake1(),"有一方认怂了或者超时了,战书作废");
	    	returnStake(pkMatch.getPkMan2(), pkMatch.getpKStake2(),"有一方认怂了或者超时了,战书作废");
	    	if (pkMatch.getType()==11) {//擂台赛擂主取消
	    		Scene scene=SceneUtil.getScene(SceneUtil.LTSID);
    			if (scene!=null) {
    				LTSScene ltsScene=(LTSScene)scene;	
    				ltsScene.addJF(pkMatch,-1,null);
    			}	
	  		}
		}
	}
	/**退还赌注*/
	public void returnStake(RoleCard pkMan,PKStake pKStake,String ts){
		if (pkMan==null) {return;}
		ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(pkMan.getRoleName());
		LoginResult loginResult=null;
		if (ctx!=null) {loginResult=GameServer.getAllLoginRole().get(ctx);}
		if (ctx!=null&&ts!=null) {
			 SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(ts));	
		}	
		if (pKStake==null) {return;}
		AssetUpdate assetUpdate=null;
		StringBuffer buffer=new StringBuffer();
		if (loginResult==null) {
			  BigDecimal gold=AllServiceUtil.getRoleTableService().selectMoneyRoleID(pkMan.getRoleId());
			  gold=gold.add(new BigDecimal(pKStake.getCharge()+pKStake.getMoney()));
			  if( gold.compareTo(new BigDecimal("999999999999")) > 0 ){
					gold=new BigDecimal("999999999999");
			  }
			  AllServiceUtil.getRoleTableService().updateMoneyRoleID(pkMan.getRoleId(),gold);
		}else {
			 if (assetUpdate==null) {assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);}
			 loginResult.setGold(loginResult.getGold().add(new BigDecimal(pKStake.getCharge()+pKStake.getMoney())));
			 assetUpdate.updata("D="+(pKStake.getCharge()+pKStake.getMoney()));	
			 buffer.append("你获得"+(pKStake.getCharge()+pKStake.getMoney())+"金钱");
		}
		if (pKStake.getXianYu()!=0) {
			ctx=GameServer.getInlineUserNameMap().get(pkMan.getUserName());
			loginResult=GameServer.getAllLoginRole().get(ctx);
			if (loginResult==null) {
				loginResult=AllServiceUtil.getRoleTableService().selectRoleID(pkMan.getRoleId());
				if (loginResult!=null) {
					UserTable userTable = new UserTable();
					userTable.setCodecard(loginResult.getCodecard().add(new BigDecimal(pKStake.getXianYu())));
					userTable.setMoney(loginResult.getMoney());
					userTable.setUsername(loginResult.getUserName());
					AllServiceUtil.getUserTableService().updateUser(userTable);					
				}
			}else {
				 if (assetUpdate==null) {assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);}
				 loginResult.setCodecard(loginResult.getCodecard().add(new BigDecimal(pKStake.getXianYu())));
				 assetUpdate.updata("X="+(pKStake.getXianYu()));	 		
				 if (buffer.length()!=0) {buffer.append("|");}
				 buffer.append("你获得"+(pKStake.getXianYu())+"仙玉");
			}
		}
		pKStake.qk();
		if (ctx!=null&&assetUpdate!=null) {
			assetUpdate.setMsg(buffer.toString());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
		}
	}
	/**超时判断*/
	public void OVERTIME() {
		long OverTime = System.currentTimeMillis();
		String key;
		Iterator<String> it = PkMatchs.keySet().iterator();
		while (it.hasNext()) {
			key = it.next();
			PkMatch value = PkMatchs.get(key);
			if (value != null) {
				if (value.getType() == 0 && value.isOverTime(OverTime)) {
					it.remove();
					returnStake(value.getPkMan1(), value.getpKStake1(),"皇宫PK超时");
					returnStake(value.getPkMan2(), value.getpKStake2(),"皇宫PK超时");
					PkMatchId.remove(value.getPkMan1().getRoleId());
					if (value.getPkMan2() != null) {
						PkMatchId.remove(value.getPkMan2().getRoleId());
					}
				}
				if (value.getState() == 2) {
					BattleData battleData = BattleThreadPool.BattleDatas.get(value.getBattleNumber());
					if (battleData == null) {
						it.remove();
						returnStake(value.getPkMan1(), value.getpKStake1(),"皇宫PK超时");
						returnStake(value.getPkMan2(), value.getpKStake2(),"皇宫PK超时");
						PkMatchId.remove(value.getPkMan1().getRoleId());
						if (value.getPkMan2() != null) {
							PkMatchId.remove(value.getPkMan2().getRoleId());
						}
					}
				}
			}
		}
	}
}

