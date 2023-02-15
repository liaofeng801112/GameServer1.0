package come.tool.newGang;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.NChatBean;
import org.come.bean.PathPoint;
import org.come.entity.Gang;
import org.come.handler.SendMessage;
import org.come.model.Boos;
import org.come.model.Robots;
import org.come.model.Sghostpoint;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;
import org.come.task.MonsterUtil;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.RewardLimit;
import come.tool.FightingData.Battlefield;

public class GangDomain {
	
	private Object object;
	private Gang gang;
	private GangGroup gangGroup;
	private ConcurrentHashMap<BigDecimal,ChannelHandlerContext> roleMap;
	
	/**帮派强盗使用数据*/
	private int banditsSum;//今日胜利次数
	private int banditsValue;//失败免一轮刷新
	private int banditsTime;//剩余击杀时间
	private List<MapMonsterBean> banditsList;//帮派强盗
	
	private boolean isUp;
	public GangDomain(Gang gang) {
		// TODO Auto-generated constructor stub
		this.object=new Object();
		this.gang=gang;
		this.gangGroup=new GangGroup(gang);
		this.roleMap=new ConcurrentHashMap<>();
		this.isUp=false;
	}
	/**获取帮派强盗数据*/
	public Map<Integer, MonsterMoveBase> getBandits(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap){
		synchronized (this) {
			if (banditsList==null||banditsList.size()==0) {return moveMap;}
			for (int i = 0; i < banditsList.size(); i++) {
				MapMonsterBean bean=banditsList.get(0);
				if (i==0) {
					if (buffer.length()!=0) {buffer.append("|");} 
			    	buffer.append(bean.getRobotid());
			        buffer.append("#");
			        buffer.append(bean.getRobotname());
			        if (bean.getRobottitle()!=null&&!bean.getRobottitle().equals("")) {
		        	    buffer.append("$");
		         	    buffer.append(bean.getRobottitle());
		            }
			        buffer.append("#");
			        buffer.append(bean.getRobotskin());
			        buffer.append("#");
			        buffer.append(bean.getRobotType()); 
				}
	    		if (bean.getMove()!=null) {
					if (moveMap==null) {moveMap=new HashMap<>();}
					moveMap.put(bean.getMove().getBh(), bean.getMove().getMoveBase());
				}
	    		MonsterUtil.monsterBuffer1(bean, buffer);
			}
			return moveMap;
		}
	}
	/**开启帮派强盗 */
    public boolean banditsOpen(Boos boos,Robots robot,int size,Sghostpoint sghostpoint){
    	synchronized (this) {
    		if (banditsValue>0) {banditsValue--;return false;}
    		if (roleMap.size()<5) {return false;}
    		if ((banditsList==null||banditsList.size()==0)&&Battlefield.isV(boos.getBoosgpk()-banditsSum*2)) {//上一轮未刷新 或者胜利参与刷新
    			banditsTime=30;
    			if (banditsList==null) {banditsList=new ArrayList<>();}
        		int max=sghostpoint.getPoints().length;
        		int robotId=Integer.parseInt(robot.getRobotid());
        		long mapId=GameServer.getMapIds(boos.getBoosmapname());
        		StringBuffer buffer=new StringBuffer();
    	        buffer.append(robot.getRobotid());
    	        buffer.append("#");
    	        buffer.append(robot.getRobotname());
    	        buffer.append("#");
    	        buffer.append(robot.getRobotskin());
    	        buffer.append("#");
    	        buffer.append(0); 
    	        int maxtime=boos.getBoosetime();
    	        //id#名称#皮肤#唯一标识-x-y-状态可有可无
    		    for (int i = 0; i < size; i++) {
    		    	// 每个坐标对应的怪物的bean
    				MapMonsterBean mapMonsterBean = new MapMonsterBean();
    				mapMonsterBean.setI(MonsterUtil.getIncreasesum());
    				//切割坐标获得X,Y//坐标
    				PathPoint point = sghostpoint.getPoints()[MonsterUtil.random.nextInt(max)];
    				mapMonsterBean.setX(point.getX()+MonsterUtil.getPY());
    				mapMonsterBean.setY(point.getY()+MonsterUtil.getPY());	
    				mapMonsterBean.setRobotid(robotId);
    				mapMonsterBean.setRobotname(robot.getRobotname());
    				mapMonsterBean.setRobotskin(robot.getRobotskin());	
    				mapMonsterBean.setRobotType(0);
    				mapMonsterBean.setMap(mapId);
    				mapMonsterBean.setMaxtime(maxtime);
    				mapMonsterBean.setGangId(gang.getGangid());
    				mapMonsterBean.setTsModel(robot.getTsModel());
    				banditsList.add(mapMonsterBean);
    				MonsterUtil.allMonster.put(mapMonsterBean.getI(), mapMonsterBean);	
    				MonsterUtil.monsterBuffer1(mapMonsterBean, buffer);
    			    if (i==0) {mapMonsterBean.setBoosId(RewardLimit.isBoosDrop(boos));}
    			}
    			SendMessage.sendMessageToGangMap(gang.getGangid(),mapId, Agreement.getAgreement().MonsterRefreshAgreement(buffer.toString()));
    			return true;
    		}
        	return false;	
    	}
	}
	/**帮派强盗结束-1处理玩家击杀成功 0上次未刷新 1上次刷新完成成功 2上次完成失败*/
	public void banditsEnd(MapMonsterBean bean){
		synchronized (this) {
			if (banditsList==null) {return;}//如果闯入对象是空的表示判断超时
			if (bean!=null) {
				if (banditsList!=null) {
					synchronized (banditsList) {
						banditsList.remove(bean);
						if (banditsList.remove(bean)) {
							int size=banditsList.size();
							if (size==0) {
								banditsDraw(true);
							}else if (size<=5||size%8==7) {//小于5只或者剩余数量和8余7 提示剩余数量
								banditsSendMsg();
							}
						}
						
					}	
				}
				return;
			}
			if (banditsList.size()!=0) {
				banditsTime-=5;
				if (banditsTime>0) {//提示剩余数量
					banditsSendMsg();
				}else {//失败
					banditsDraw(false);	
				}
			}
		}
	}
	/**提示帮派强盗数量*/
	public void banditsSendMsg(){
		NChatBean chatBean=new NChatBean();
		chatBean.setId(2);
		chatBean.setMessage("剩余帮派强盗数量:#R"+banditsList.size());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean));
		SendMessage.sendMessageToGangRoles(gang.getGangid(), msg);
	}
	/**帮派强盗奖励 ture 胜利  false 失败*/
	public void banditsDraw(boolean isV){
		if (isV) {
			banditsSum++;
			long add=15000;
			addBG(add);//奖励二万帮贡
			NChatBean chatBean=new NChatBean();
			chatBean.setId(2);
			chatBean.setMessage("众人齐心协力共同抗敌,极力清除帮中强盗通过变卖强盗遗留物品为帮派资金增加#R "+add);
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean));
			SendMessage.sendMessageToGangRoles(gang.getGangid(), msg);
		}else {
			//一只扣一千
			banditsValue=3;
			long add=banditsList.size()*1000;
			if (add>gang.getBuilder().longValue()) {add=gang.getBuilder().longValue();}
			addBG(-add);
			NChatBean chatBean=new NChatBean();
			chatBean.setId(2);
			chatBean.setMessage("强盗在帮中横行霸道,导致帮派资金被抢走了#R "+add+" #W,希望下次大家齐心协力共同抗敌，与强盗势不两立#23");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean));
			SendMessage.sendMessageToGangRoles(gang.getGangid(), msg);
			//清空野怪
			long mapId=0;
			StringBuffer buffer=new StringBuffer("M");
			for (int i = 0; i < banditsList.size(); i++) {
				MapMonsterBean bean=banditsList.get(0);
				MonsterUtil.removeMonster2(bean);
				if (mapId==0) {mapId=bean.getMap();}
				if (buffer.length()>1) {buffer.append("#");}
				buffer.append(bean.getI());
				buffer.append("^2");
			}
			banditsList=null;
			if (mapId!=0) {
				SendMessage.sendMessageToGangMap(gang.getGangid(),mapId, Agreement.getAgreement().battleStateAgreement(buffer.toString()));
			}
		}
	}
	/**获取对话框*/
	public String getMsg(int type){
		if (type==2022) {//科技
			return gangGroup.getKJNpc();
		}else if (type==2023) {//驯养
			return gangGroup.getXYNpc();
		}
		return null;
	}
	/**添加帮贡*/
	public void addBG(long add){
		synchronized (object) {
			gang.setBuilder(new BigDecimal(gang.getBuilder().longValue()+add));
			isUp=true;
		}
	}
	/**消耗驯养次数*/
	public boolean useXY(){
		synchronized (object) {
			int num=gangGroup.getXyNum();
			if (num>0) {
				gangGroup.setXyNum(num-1);
				isUp=true;
				return true;
			}
			return false;
		}
	}
	/**恢复驯养次数*/
	public void upXY(){
		synchronized (object) {
			if (gangGroup.addXY(1)) {
				isUp=true;
			}
		}
	}
	/**提升等级*/
	public String upLvl(int type){
		synchronized (object) {
			//47://升级帮派等级 48://升级科技等级 49://升级驯养师等级
		    int lvl=0;
		    int xh=0;
		    String name = null;
		    if (type == 47) {
				lvl = gang.getGanggrade().intValue();
				xh = (int) (Math.pow(2, lvl) * 35000);
				name = "帮派等级";
			} else if (type == 48) {
				lvl = gangGroup.getKj();
				xh = (int) (Math.pow(2, lvl) * 150000);
				name = "科技等级";
			} else if (type == 49) {
				lvl = gangGroup.getXy();
				xh = (int) (Math.pow(2, lvl) * 150000);
				name = "驯养师等级";
			}	
			if (lvl >= 5) {
				return "等级上限5级";
			} else if (type != 47 && lvl >= gang.getGanggrade().intValue()) {
				return "不能超过帮派等级";
			}
			if (gang.getBuilder().longValue() < xh) {
				return "你的帮派资金不足#G" + xh;
			}
			gang.setBuilder(new BigDecimal(gang.getBuilder().longValue()-xh));
			if (type == 47) {
				gang.setGanggrade(gang.getGanggrade().add(new BigDecimal(1)));
			}else if (type == 48) {
				gangGroup.setKj(gangGroup.getKj()+1);
			}else if (type == 49) {
				gangGroup.setXy(gangGroup.getXy()+1);
			}
			isUp=true;
			return name+"升级成功";
		}
	}
	/**加入一个成员*/
	public void addGangRole(){
		synchronized (object) {
			gang.setGangnumber(gang.getGangnumber().add(new BigDecimal(1)));
			isUp=true;
		}
	}
	/**退出一个成员*/
    public void removeGangRole(){
        synchronized (object) {
        	gang.setGangnumber(gang.getGangnumber().subtract(new BigDecimal(1)));
        	isUp=true;
		}
	}
    /**更换帮主*/
    public void upGangMaster(String roleName){
    	synchronized (object) {
    		gang.setGangbelong(roleName);
    		isUp=true;
    	}
    }
    public void upGang(){
    	synchronized (object) {
    		if (isUp) {
    			gang.setGangTxt(gangGroup.getTxt());
    			AllServiceUtil.getGangService().updateGang(gang);
    			isUp=false;
			}
    	}
    }
	/**一个成员上线*/
    public void upGangRole(BigDecimal roleId,ChannelHandlerContext ctx){
    	roleMap.put(roleId,ctx);
    }
	/**一个成员下线*/
    public void downGangRole(BigDecimal roleId){
    	roleMap.remove(roleId);
    }
	public Gang getGang() {
		return gang;
	}
	public GangGroup getGangGroup() {
		return gangGroup;
	}
	public ConcurrentHashMap<BigDecimal, ChannelHandlerContext> getRoleMap() {
		return roleMap;
	}
}
