package come.tool.newTrans;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Record;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Stall.AssetUpdate;

public class TransUtil {
	/**自增长战斗编号*/
	public static int increasesum;
	//交易的房间
	private static ConcurrentHashMap<Integer,TransRoom> transMap=new ConcurrentHashMap<>();
	//玩家指向的房间
	private static ConcurrentHashMap<String,Integer> transIntMap=new ConcurrentHashMap<>();
	public synchronized static int getIncreasesum() {
		increasesum++;
		if (increasesum>99999999) increasesum=1;
		return increasesum;
	}
    /**判断玩家是否在交易中*/
	public static boolean isTrans(String roleName){
		Integer transId=transIntMap.get(roleName);
		if (transId!=null) {
			TransRoom room=transMap.get(transId);
			return room!=null;
		}
		return false;
	}
	/**发起交易*/
	public static void launchTrans(LoginResult role1,LoginResult role2){
		Integer transId=getIncreasesum();
		TransRole transRole1=new TransRole(role1.getRole_id(), role1.getRolename());
		TransRole transRole2=new TransRole(role2.getRole_id(), role2.getRolename());
		TransRoom room=new TransRoom(transId, transRole1, transRole2);
		transMap.put(transId,room);
		transIntMap.put(role1.getRolename(), transId);
		transIntMap.put(role2.getRolename(), transId);
	}
	/**清除*/
	public static void removeTrans(Integer transId){
		TransRoom transRoom=transMap.remove(transId);
		if (transRoom!=null) {
			transIntMap.remove(transRoom.getRole1().getRolename());
			transIntMap.remove(transRoom.getRole2().getRolename());
		}
	}
	/**玩家下线处理*/
	public static void roleDown(String roleName){
		Integer transId=transIntMap.get(roleName);
		if (transId!=null) {
			TransRoom transRoom=transMap.remove(transId);
			if (transRoom!=null) {
				if (!transRoom.getRole1().getRolename().equals(roleName)) {
					SendMessage.sendMessageByRoleName(transRoom.getRole1().getRolename(),MSGREMOVE); 	
				}
				if (!transRoom.getRole2().getRolename().equals(roleName)) {
					SendMessage.sendMessageByRoleName(transRoom.getRole2().getRolename(),MSGREMOVE); 
				}
				transIntMap.remove(transRoom.getRole1().getRolename());
				transIntMap.remove(transRoom.getRole2().getRolename());
			}
		}
	}
	static String MSGREMOVE=Agreement.getAgreement().TransStateAgreement("2");
	/**取消交易处理*/
	public static void removeTrans(String roleName){
		Integer transId=transIntMap.get(roleName);
		if (transId!=null) {
			TransRoom transRoom=transMap.remove(transId);
			if (transRoom!=null) {
				SendMessage.sendMessageByRoleName(transRoom.getRole1().getRolename(),MSGREMOVE); 
				SendMessage.sendMessageByRoleName(transRoom.getRole2().getRolename(),MSGREMOVE); 
				transIntMap.remove(transRoom.getRole1().getRolename());
				transIntMap.remove(transRoom.getRole2().getRolename());
			}
		}
		SendMessage.sendMessageByRoleName(roleName,MSGREMOVE); 
	}
	/**锁定和取消锁定操作 ture 锁定 false取消锁定 确定*/
	public static void lockingTrans(String roleName,int type){
		Integer transId=transIntMap.get(roleName);
		if (transId!=null) {
			TransRoom room=transMap.get(transId);
			if (room==null) {return;}
			synchronized (room) {
				TransRole role1=null;TransRole role2=null;
				if (room.getRole1().getRolename().equals(roleName)) {
					role1=room.getRole1();
					role2=room.getRole2();
				}else if (room.getRole2().getRolename().equals(roleName)) {
					role1=room.getRole2();
					role2=room.getRole1();
				}else {
					return;
				}
//				请求交易0|角色名 同意交易1|角色名 取消交易2 锁定3 取消锁定4 确定5
				//玩家状态   0  1锁定  2确定
				if (type==3) {
					if (role1.getState()!=0) {return;}
					role1.setState(1);
					String sendMessage=Agreement.getAgreement().TransStateAgreement(3+"|"+role1.getRolename());
					SendMessage.sendMessageByRoleName(role1.getRolename(),sendMessage); 
					SendMessage.sendMessageByRoleName(role2.getRolename(),sendMessage); 
				}else if (type==4) {
					if (role1.getState()==0) {return;}
					role1.setState(0);
					if (role2.getState()==2) {role2.setState(1);}
					String sendMessage=Agreement.getAgreement().TransStateAgreement(4+"|"+role1.getRolename());
					SendMessage.sendMessageByRoleName(role1.getRolename(),sendMessage); 
					SendMessage.sendMessageByRoleName(role2.getRolename(),sendMessage); 
				}else {
					if (role1.getState()==0||role2.getState()==0) {return;}
					role1.setState(2);
					String sendMessage=Agreement.getAgreement().TransStateAgreement(5+"|"+role1.getRolename());
					SendMessage.sendMessageByRoleName(role1.getRolename(),sendMessage); 
					SendMessage.sendMessageByRoleName(role2.getRolename(),sendMessage); 
					if (role2.getState()==2) {//开始实施交易
						startTrans(room);
						removeTrans(transId);
					}
				}
			}
		}
	}
	/**交易实施*/
	public static void startTrans(TransRoom room){
		LoginResult login1=GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(room.getRole1().getRolename()));
		LoginResult login2=GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(room.getRole2().getRolename()));
		if (login1==null||login2==null) {return;}
		GoodTrans goodTrans1=room.getRole1().getGoodTrans();
		GoodTrans goodTrans2=room.getRole2().getGoodTrans();
		//2边交易金额差
		long money=0L;
		if (goodTrans1.getMoney()!=null) {money-=goodTrans1.getMoney().longValue();}
		if (goodTrans2.getMoney()!=null) {money+=goodTrans2.getMoney().longValue();}
		if (money!=0) {
			if (money<0) {if (login1.getGold().longValue()<Math.abs(money)) {return;}}
			else {if (login2.getGold().longValue()<Math.abs(money)) {return;}}
		}
	    Map<BigDecimal,Goodstable> map=new HashMap<>();
	    if (!goodTrans1.check(map, login1.getRole_id())) {
	    	return;
	    }
	    if (!goodTrans2.check(map, login2.getRole_id())) {
	    	return;
	    }
		for (Goodstable value : map.values()) {
			AllServiceUtil.getGoodsTableService().updateGoodRedis(value);
		}
		String sendMessage=Agreement.getAgreement().TransStateAgreement("5");
		SendMessage.sendMessageByRoleName(login1.getRolename(),sendMessage); 
		SendMessage.sendMessageByRoleName(login2.getRolename(),sendMessage);
		AssetUpdate asset1=null;AssetUpdate asset2=null;
		if (money!=0) {
			asset1=new AssetUpdate();asset1.updata("D="+money);
			login1.setGold(login1.getGold().add(new BigDecimal(money)));
			asset2=new AssetUpdate();asset2.updata("D="+(-money));
			login2.setGold(login2.getGold().subtract(new BigDecimal(money)));
			if (MonitorUtil.isUpMoney(1,money)) {
				StringBuffer buffer=new StringBuffer();
				buffer.append("交易大宗金额流动:");
				buffer.append(login1.getRole_id());
				buffer.append("交易");
				buffer.append(login2.getRole_id());
				buffer.append("金额");
				buffer.append(money);
				AllServiceUtil.getRecordService().insert(new Record(1,buffer.toString()));
			}
		}
		asset2=goodTrans1.goTrans(asset2, map, login2.getRole_id());
		asset1=goodTrans2.goTrans(asset1, map, login1.getRole_id());
		if (asset1!=null) {
			asset1.setType(AssetUpdate.DEAL);
			asset1.reset();
			String send1=Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(asset1));
			SendMessage.sendMessageByRoleName(login1.getRolename(),send1);
		}
		if (asset2!=null) {
			asset2.setType(AssetUpdate.DEAL);
			asset2.reset();
			String send2=Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(asset2));
			SendMessage.sendMessageByRoleName(login2.getRolename(),send2);
		}
	}
	/**交易的物品更新*/
	public static void TransGood(String roleName,GoodTrans2 goodTrans2){
		Integer transId=transIntMap.get(roleName);
		if (transId!=null) {
			TransRoom room=transMap.get(transId);
			if (room!=null) {
				TransRole role1=null;TransRole role2=null;
				if (room.getRole1().getRolename().equals(roleName)) {
					role1=room.getRole1();
					role2=room.getRole2();
				}else if (room.getRole2().getRolename().equals(roleName)) {
					role1=room.getRole2();
					role2=room.getRole1();
				}else {
					return;
				}
				if (role1.getState()!=0) {return;}
				role1.getGoodTrans().updateGood(goodTrans2);
				String send=Agreement.getAgreement().TransGoodAgreement(GsonUtil.getGsonUtil().getgson().toJson(goodTrans2));
				SendMessage.sendMessageByRoleName(role2.getRolename(),send);
			}
		}	
	}
}
