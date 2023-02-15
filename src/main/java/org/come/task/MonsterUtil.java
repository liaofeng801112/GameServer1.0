package org.come.task;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.bean.PathPoint;
import org.come.bean.ShopGoodsBean;
import org.come.entity.Gang;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Boos;
import org.come.model.Lshop;
import org.come.model.Robots;
import org.come.model.Sghostpoint;
import org.come.model.Shop;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.SplitStringTool;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.RewardLimit;
import come.tool.Good.FYModel;
import come.tool.Good.TSModel;
import come.tool.Role.RolePool;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.RC.BBBrush;
import come.tool.Scene.RC.RCScene;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;

public class MonsterUtil {
	//M路径数据|{野怪数据}|{野怪数据}
	//M路径数据     路径编号^节点1x^节点1y^节点2x^节点2y#路径编号^节点1x^节点1y^节点2x^节点2y
	//野怪数据   robotid#野怪名称&称谓#皮肤#野怪类型#{野怪集合}#{野怪集合}#{野怪集合}#{野怪集合#}
	//野怪名称$称谓              野怪不一定有称谓
	//野怪集合   野怪唯一标识^X坐标^Y坐标^S野怪的状态^L野怪的移动参数^H野怪血量^G野怪跟随的目标
	//S野怪的状态 例S0  0空闲状态 没有标注野怪状态默认0  1战斗中  2野怪消失
	//L路径编号,已移动时间   例L1,100
	//H当前血量,最大血量       例H1,100
	//G野怪跟随的玩家名称      例G玩家名

	//战斗状态    M野怪状态|R人物状态
	//R人物状态   R战斗编号#{人物名称}#{人物名称}
	//M野怪状态   M{野怪数据}#{野怪数据}
	//野怪数据       野怪编号^状态^G跟随^H血量^Z坐标变换
	// 存放定时刷新地图怪物的集合信息   地图id
	public static ConcurrentHashMap<Integer,MapMonsterBean> allMonster;
	//地图野怪
	private static ConcurrentHashMap<Long, ConcurrentHashMap<Integer, List<MapMonsterBean>>> allMapMonsterMap;

	/**击杀跟随野怪*/
	private static List<MapMonsterBean> followList;
	/**自增长编号*/
	public static int increasesum;
	public static Random random =new Random();
    private static final int JG=5;
    public static List<Boos> booses;
    private static BBBrush bbBrush;
    private static MonsterMatchThread matchThread;
    public static List<MapMonsterBean> matchList;
	public static void init(){
		allMonster      =new ConcurrentHashMap<>();
		allMapMonsterMap=new ConcurrentHashMap<>();
		followList      =new ArrayList<>();
		matchList       =new ArrayList<>();
		matchThread     =new MonsterMatchThread();
		matchThread.start();
	}
	/**获取地图robot数据*/
	public static List<MapMonsterBean> getList(long mapId,int robotId){
		ConcurrentHashMap<Integer,List<MapMonsterBean>> map=allMapMonsterMap.get(mapId);
	    if (map==null) {
	    	map=new ConcurrentHashMap<>();
	    	allMapMonsterMap.put(mapId, map);
		}
	    List<MapMonsterBean> lists=map.get(robotId);
	    if (lists==null) {
	    	lists=new ArrayList<>();
	    	map.put(robotId,lists);
		}
		return lists;
	}
	/**获取野怪地图*/
	public static String getMapMonster(long mapId,BigDecimal gangID){
		StringBuffer buffer=new StringBuffer();
		Map<Integer, MonsterMoveBase> moveMap=null;
		ConcurrentHashMap<Integer, List<MapMonsterBean>> value=allMapMonsterMap.get(mapId);
		if (value!=null) {
			Iterator<Entry<Integer, List<MapMonsterBean>>> iterator = value.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer, List<MapMonsterBean>> entrys = iterator.next();
			    List<MapMonsterBean> list=entrys.getValue();
			    if (list.size()!=0) {
			    	if (buffer.length()!=0) {buffer.append("|");} 
			    	MapMonsterBean bean=list.get(0);
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
			    	for (int i = list.size()-1; i >=0; i--) {
			    		bean=list.get(i);
			    		if (bean.getMove()!=null) {
							if (moveMap==null) {moveMap=new HashMap<>();}
							moveMap.put(bean.getMove().getBh(), bean.getMove().getMoveBase());
						}
			    		monsterBuffer1(bean, buffer);
				    }
				}   
			}		
		}
		if (mapId==3000&&gangID!=null&&gangID.intValue()!=0) {
			GangDomain gangDomain=GangUtil.getGangDomain(gangID);
			if (gangDomain!=null) {
				moveMap=gangDomain.getBandits(buffer, moveMap);
			}
		}
		moveMap=SceneUtil.getMapMonster(buffer,moveMap,mapId);
		if (buffer.length()==0) {
			return null;
		}
		if (moveMap!=null&&moveMap.size()!=0) {
			StringBuffer moveBuffer=new StringBuffer();
			moveBuffer.append("M");
			for (MonsterMoveBase move:moveMap.values()) {
				if (moveBuffer.length()>1) {moveBuffer.append("#");}
				moveBuffer.append(move.getMoveMsg());
			}
			moveBuffer.append("|");
			buffer=moveBuffer.append(buffer);
		}
		return buffer.toString();
	}
	/**刷新*/
	public static void refurbishMonster(String week,int day,int hour,int minute,int second){
		clearMonsters();
		for (int i = 0; i < booses.size(); i++) {
			Boos boos=booses.get(i);
			if (boos.getBoosid().equals("")) {continue;}
			if (!week(boos.getBoosweekday(), week)) {continue;}// 判断星期
			// 判断开放时间
			if (time(boos.getBoosstime(),boos.getBoosetime(),hour)) {// 判断是否到了刷新时间
				if (Refresh(boos.getBoosstime(), boos.getBoosrtime(), hour, minute)) {// 刷新怪物
					refreshMonsters(boos,null,null,null);
				}
			}
		}
		if (minute==0&&hour==22&&day==6) {
			Scene scene=SceneUtil.getScene(SceneUtil.RCID);
			if (scene!=null) {
				RCScene rcScene=(RCScene) scene;
				bbBrush=rcScene.BBOpen();
			}
		}
		if (bbBrush!=null) {
			bbBrush.setD(bbBrush.getD()+1);
			Boos boos=bbBrush.getBoos();
			if (boos!=null) {
				refreshMonsters(boos,null,"当前第"+bbBrush.getD()+"波,总计"+bbBrush.getZ()+"波",null);
			}
			if (bbBrush.getD()>=bbBrush.getZ()) {bbBrush=null;}
		}
	}
	/**清除野怪*/
	public static void clearMonsters() {
		Iterator<Entry<Long, ConcurrentHashMap<Integer, List<MapMonsterBean>>>> iterator = allMapMonsterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, ConcurrentHashMap<Integer, List<MapMonsterBean>>> entrys = iterator.next();
			clearMonsters(entrys.getKey(),entrys.getValue());
		}
		timeGang();
	}
	public static void clearMonsters(long mapid,ConcurrentHashMap<Integer, List<MapMonsterBean>> value) {
		Iterator<Entry<Integer, List<MapMonsterBean>>> iterator = value.entrySet().iterator();
		StringBuffer buffer=new StringBuffer();
		buffer.append("M");
		while (iterator.hasNext()) {
			Entry<Integer, List<MapMonsterBean>> entrys = iterator.next();
		    List<MapMonsterBean> list=entrys.getValue();
		    if (list.size()==0) {
		    	iterator.remove();
			}else {
				 for (int i = list.size()-1; i >=0; i--) {
				     MapMonsterBean bean=list.get(i);
				     if (bean.isMaxtime(JG)) {
				    	if (buffer.length()>1) {buffer.append("#");}
				    	list.remove(i);
				    	allMonster.remove(bean.getI());
				    	if (bean.getFollow()!=null) {
							followList.remove(bean);
						}
				    	buffer.append(bean.getI());
				    	buffer.append("^2");
					}
				}
			}
		}
		if (buffer.length()>1) {
			SendMessage.sendMessageToMapRoles(mapid,Agreement.getAgreement().battleStateAgreement(buffer.toString()));		
		}
	}
	/**判断强盗任务超时*/
	public static void timeGang(){
		List<Gang> gangs=GangUtil.getGangs();
		for (int i = gangs.size()-1; i >=0 ; i--) {
			Gang gang=gangs.get(i);
			GangDomain gangDomain=GangUtil.getGangDomain(gang.getGangid());
			if (gangDomain!=null) {gangDomain.banditsEnd(null);}
		}
	}
	/**刷新帮派内的野怪*/
	public static List<BigDecimal> refreshMonstersGang(Boos boos,Robots robot,int size,Sghostpoint sghostpoint){
		List<BigDecimal> ids=new ArrayList<>();
		List<Gang> gangs=GangUtil.getGangs();
		for (int i = gangs.size()-1; i >=0 ; i--) {
			Gang gang=gangs.get(i);
			GangDomain gangDomain=GangUtil.getGangDomain(gang.getGangid());
			if (gangDomain!=null&&gangDomain.banditsOpen(boos, robot, size, sghostpoint)) {
				ids.add(gang.getGangid());
			}
		}
		return ids;
	}
	/**刷新怪物*/
	public static void refreshMonsters(Boos boos,BigDecimal roleID,String name,int robotType,int size,int SX,MapZB mapZB) {
		Sghostpoint sghostpoint = null;
		if (mapZB == null) {
			sghostpoint = GameServer.getSghostpoint(boos.getBoosmapname());//获取Boss地图名字
			if (sghostpoint == null || sghostpoint.getPoints().length == 0) {
				System.out.println(boos.getBoosmapname() + "_没有对应刷怪点");
				return;
			}
		}
		Robots robot = GameServer.getAllRobot().get(boos.getBoosrobot());
		if (robot == null) {
			System.out.println(boos.getBoosrobot() + "_没有对应robot");
			return;
		}
		FYModel fyModel = (roleID != null && boos.getFyDrop() != null && !boos.getFyDrop().equals("")) ? new FYModel(roleID, boos.getFyDrop()) : null;
		String zb = null;
		long mapId = GameServer.getMapIds(mapZB != null ? mapZB.getMap() : boos.getBoosmapname());
		ConcurrentHashMap<Integer, List<MapMonsterBean>> map = allMapMonsterMap.get(mapId);
		if (map == null) {
			map = new ConcurrentHashMap<>();
			allMapMonsterMap.put(mapId, map);
		}
		int robotId = Integer.parseInt(robot.getRobotid());
		List<MapMonsterBean> lists = map.get(robotId);
		if (lists == null) {
			lists = new ArrayList<>();
			map.put(robotId, lists);
		}
		//刷新的数量
		if (size == 0) {
			size = boos.getBoosnum();
		}
		//刷怪类型

		if (robotType == 0) {
			robotType = robot.getRobotType();
		}
		int max = sghostpoint != null ? sghostpoint.getPoints().length : 1;
		List<BigDecimal> gangIDs = null;
		if (boos.getBoosgpk() != 0) {
			gangIDs = refreshMonstersGang(boos, robot, size, sghostpoint);
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(robot.getRobotid());
			buffer.append("#");
			buffer.append(robot.getRobotname());
			buffer.append("#");
			buffer.append(robot.getRobotskin());
			buffer.append("#");
			buffer.append(robotType);
			int maxtime = boos.getBoosetime();
				//id#名称#皮肤#唯一标识-x-y-状态可有可无
				for (int i = 0; i < size; i++) {
					// 每个坐标对应的怪物的bean
					MapMonsterBean mapMonsterBean = new MapMonsterBean();
					//切割坐标获得X,Y//坐标
					if (mapZB != null) {
						mapMonsterBean.setX(mapZB.getX() + getPYTwo());
						mapMonsterBean.setY(mapZB.getY() + getPYTwo());
					} else {
						PathPoint point = sghostpoint.getPoints()[random.nextInt(max)];
						mapMonsterBean.setX(point.getX() + getPY());
						mapMonsterBean.setY(point.getY() + getPY());
					}
					mapMonsterBean.setRobotid(robotId);
					mapMonsterBean.setRobotname(robot.getRobotname());
					mapMonsterBean.setRobotskin(robot.getRobotskin());
					mapMonsterBean.setRobotType(robotType);
					mapMonsterBean.setI(getIncreasesum());
					mapMonsterBean.setMap(mapId);
					mapMonsterBean.setMaxtime(maxtime);
					mapMonsterBean.setSX(SX);
//					MonsterMoveBase moveBase = new MonsterMoveBase(mapMonsterBean.getX(), mapMonsterBean.getY());//x6
//						mapMonsterBean.setMove(new MonsterMove(moveBase, moveBase.getEndTime(), mapMonsterBean.getX(), mapMonsterBean.getY()));//y
					mapMonsterBean.setTsModel(robot.getTsModel());
					mapMonsterBean.setFyModel(fyModel);
					if (robotType == 2) {//随机商品
						ShopGoodsBean shopGoodsBean = new ShopGoodsBean();
						shopGoodsBean.setnId(mapMonsterBean.getI());
						shopGoodsBean.setType(0);
						ConcurrentHashMap<String, Lshop> shops = new ConcurrentHashMap<>();
						List<Shop> shopList = new ArrayList<>();
						List<String> ids = SplitStringTool.Randoms(robot.getRobotreward());
						for (int j = 0; j < ids.size(); j++) {
							Lshop lshop = GameServer.getLshop(ids.get(j));
							if (lshop == null) {
								continue;
							}
							Goodstable good = GameServer.getAllGoodsMap().get(lshop.getGid());
							if (good == null) {
								continue;
							}
							shops.put(lshop.getId() + "", lshop);
							Shop shop = new Shop();
							shop.setShopid(lshop.getId() + "");
							shop.setShopiid(lshop.getGid());
							shop.setShopname(good.getGoodsname());
							shop.setShoptype(lshop.getType());
							shop.setShopprice(lshop.getMoney().longValue());
							shop.setShopskin(good.getSkin());
							shop.setShoptext(good.getInstruction());
							shopList.add(shop);
						}
						shopGoodsBean.setShopList(shopList);
						mapMonsterBean.setShops(shops);
						mapMonsterBean.setShopMsg(Agreement.getAgreement().BuyShopGoodsAgreement(GsonUtil.getGsonUtil().getgson().toJson(shopGoodsBean)));
						zb = "(" + (mapMonsterBean.getX()) / 20 + "," + (mapMonsterBean.getY()) / 20 + ")";
					} else if (robotType == 3) {
						ConcurrentHashMap<String, Lshop> shops = new ConcurrentHashMap<>();
						List<String> ids = SplitStringTool.Randoms(robot.getRobotreward());
						for (int j = 0; j < ids.size(); j++) {
							Lshop lshop = GameServer.getLshop(ids.get(j));
							if (lshop == null) {
								continue;
							}
							Goodstable good = GameServer.getAllGoodsMap().get(lshop.getGid());
							if (good == null) {
								continue;
							}
							shops.put(lshop.getGid().toString(), lshop);
						}
						mapMonsterBean.setShops(shops);
						zb = "(" + (mapMonsterBean.getX()) / 20 + "," + (mapMonsterBean.getY()) / 20 + ")";
					} else if (robotType == 4 || robotType == 132) {
						if (robotType == 132) {
							mapMonsterBean.setCreateTime(0);
						} else {
							mapMonsterBean.setCreateTime(System.currentTimeMillis());
						}
						MonsterMatch match = new MonsterMatch();
						mapMonsterBean.setMatch(match);
						MonsterFollow follow = new MonsterFollow(sghostpoint.getPoints());
						mapMonsterBean.setFollow(follow);
						followList.add(mapMonsterBean);
						zb = "(" + (mapMonsterBean.getX()) / 20 + "," + (mapMonsterBean.getY()) / 20 + ")";
					}
					lists.add(mapMonsterBean);
					allMonster.put(mapMonsterBean.getI(), mapMonsterBean);
					monsterBuffer1(mapMonsterBean, buffer);
					if (i == 0) {
						mapMonsterBean.setBoosId(RewardLimit.isBoosDrop(boos));
					}
				}
				//根据地图发送信息
				SendMessage.sendMessageToMapRoles(mapId, Agreement.getAgreement().MonsterRefreshAgreement(buffer.toString()));
			}
			boosChat(boos, name, zb, gangIDs);
		}

	/**刷新怪物*/
	public static void refreshMonsters(Boos boos,BigDecimal roleID,String name,MapZB mapZB) {
		refreshMonsters(boos,roleID,name, 0, 0, 0,mapZB);
	}
	/**单个怪物刷新*/
	public static Map<Integer, MonsterMoveBase> monsterBuffer(MapMonsterBean bean,StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap){
		if (bean.getMove()!=null) {
			if (moveMap==null) {moveMap=new HashMap<>();}
			moveMap.put(bean.getMove().getBh(), bean.getMove().getMoveBase());
		}
		if (buffer.length() != 0) {buffer.append("|");}
		buffer.append(bean.getRobotid());
		buffer.append("#");
		buffer.append(bean.getRobotname());
		buffer.append("#");
		buffer.append(bean.getRobotskin());
		buffer.append("#");
		buffer.append(bean.getRobotType());
		MonsterUtil.monsterBuffer1(bean,buffer);
		return moveMap;
	}
	/**刷新野怪字符串拼接*/
	public static void monsterBuffer1(MapMonsterBean bean,StringBuffer buffer){
		 buffer.append("#");
		 buffer.append(bean.getI());
		 buffer.append("^");
		 buffer.append(bean.getX());
		 buffer.append("^");
		 buffer.append(bean.getY());
		 if (bean.getType()==1&&bean.getMove()!=null) {
			 buffer.append("^S1");
		 }
		 if (bean.getFollow()!=null&&bean.getFollow().getFollowID()!=null) {
		     buffer.append("^G");
		     buffer.append(bean.getFollow().getFollowID());
		 }
		 if (bean.getHp()!=null) {
		     buffer.append("^H");
		     buffer.append(bean.getHp().getHp());
		     buffer.append(",");
		     buffer.append(bean.getHp().getHpMax());
		 }
		 if (bean.getMove()!=null) {
		     buffer.append("^L");
		     buffer.append(bean.getMove().getBh());
		     buffer.append(",");
		     buffer.append(bean.getMove().getTime());
		}
	}
	public static void sendMsg(String msg,List<BigDecimal> gangIDs){
		if (gangIDs!=null) {
			for (int i = 0; i < gangIDs.size(); i++) {
				SendMessage.sendMessageToGangRoles(gangIDs.get(i), msg);
			}
			return;
		}
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**喊话*/
	public static void boosChat(Boos boos,String name,String zb,List<BigDecimal> gangIDs){
		String v1=boos.getBoostext();
		String v2=boos.getBoosGGtext();
		if (v1!=null&&v2!=null&&v1.equals(v2)) {
			if (!v1.equals("")) {
				NChatBean bean=new NChatBean();
				bean.setId(7);
				if (name!=null) {v1=v1.replace("{角色名}", name);}
				if (zb!=null) {v1=v1.replace("{坐标}", zb);}
				bean.setMessage(v1);
				String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
				sendMsg(msg, gangIDs);
			}
			return;
		}
		if (v1!=null&&!v1.equals("")) {
			// 发起世界喊话
			NChatBean bean=new NChatBean();
			bean.setId(5);
			if (name!=null) {v1=v1.replace("{角色名}", name);}
			if (zb!=null) {v1=v1.replace("{坐标}", zb);}
			bean.setMessage(v1);
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			sendMsg(msg, gangIDs);
		}	
		if (v2!=null&&!v2.equals("")) {
			// 发起世界喊话
			NChatBean bean=new NChatBean();
			bean.setId(9);
			if (name!=null) {v2=v2.replace("{角色名}", name);}
			if (zb!=null) {v2=v2.replace("{坐标}", zb);}
			bean.setMessage(v2);
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			sendMsg(msg, gangIDs);
		}
	}
	/**获取随机偏移量*/
	public static int getPY(){
		return (random.nextInt(15)-7)*7;
	}
	/**获取随机偏移量*/
	public static int getPYTwo(){
		return (random.nextInt(21)-10)*7;
	}
	/**判断是否在当前日期内*/
	public static boolean week(String weeks, String week) {
		String[] names = weeks.split("\\|");
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(week) || names[i].equals("")) {
				return true;
			}
		}
		return false;
	}
	/**判断是否在当前时间内*/
	public static boolean time(int start, int end, int current) {
		if (start == 0 && end == 24) {
			return true;
		} else if (current >= start && current < end) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 12; j++) {
				if (Refresh(0, 45, i, j*5)) {
					System.out.println(i+":"+j*5+":"+Refresh(0, 45, i, j*5));
				}
				
			}
		}
	}
	/**判断是否到了刷新时间点*/
	public static boolean Refresh(int start, int interval, int current_Hour, int current_minute) {
		current_Hour = current_Hour * 60 + current_minute;
		//当前时间 = 当前时间*60 + 当前分钟
		if ((current_Hour - (interval * 60)) % interval == 0) {
			//当前时间 - 时间间隔*60 % 时间间隔 == 0
			return true;
		}
		return false;
	}
	/**添加*/
	public static MapMonsterBean getMonster(int I){
		return allMonster.get(I);
	}
	/**添加*/
	public static void addMonster(MapMonsterBean bean){
		allMonster.put(bean.getI(), bean);	
		getList(bean.getMap(), bean.getRobotid()).add(bean);
	}
	/**额外添加*/
	public static void addEMonster(MapMonsterBean bean){
		allMonster.put(bean.getI(), bean);	
	}
	public static void removeMonster(MapMonsterBean bean,int type){
		allMonster.remove(bean.getI());	
		getList(bean.getMap(), bean.getRobotid()).remove(bean);	
		SendMessage.sendMessageToMapRoles(bean.getMap(),Agreement.getAgreement().battleStateAgreement("M"+bean.getI()+"^2"));
	}
	/**删除  0是失败  1是进入战斗 2是销毁*/
	public static String UPMonster(BattleData battleData,String[] teams,int type,StringBuffer buffer){
		MapMonsterBean bean=battleData.getMonsterBean();
		if (!(bean.getSX()==0||bean.getSX()==1)) {
			Scene scene=SceneUtil.getScene(bean.getSX());
			if (scene!=null) {return scene.UPMonster(battleData, teams, type, buffer);}
		}
		if (type==1) {//进入战斗
			if (!(bean.getHp()!=null&&bean.getHp().isMuch())) {
				bean.setType(1);
			}
			if (bean.getMove()!=null) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append("M");
				buffer.append(bean.getI());
				buffer.append("^");
				buffer.append(bean.getType());
			}
		}else if (type==2) {//结束战斗 玩家胜利
			if (bean.getFollow()!=null) {
				if (bean.getFollow().getTime() == 0) {bean.getFollow().setTime(1);} 
				else {bean.getFollow().setTime(System.currentTimeMillis());}
				bean.setType(0);
				ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(teams[0]);
				LoginResult log2=ctx2!=null?GameServer.getAllLoginRole().get(ctx2):null;
				if (log2!=null) {
					bean.getFollow().setFollowID(log2.getRole_id());
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append("M");
					buffer.append(bean.getI());
					buffer.append("^");
					buffer.append(bean.getType());
					buffer.append("^G");
					buffer.append(log2.getRole_id());
					StringBuffer bufferTwo = new StringBuffer();
					bufferTwo.append("#R");
					bufferTwo.append(log2.getRolename());
					bufferTwo.append("#Y在");
					bufferTwo.append(GameServer.getMapName(log2.getMapid().toString()));
					bufferTwo.append("(");
					bufferTwo.append(log2.getX() / 20);
					bufferTwo.append(",");
					bufferTwo.append(log2.getY() / 20);
					bufferTwo.append(")抢到#R");
					bufferTwo.append(bean.getRobotname());
					NChatBean chatBean = new NChatBean();
					chatBean.setId(9);
					chatBean.setMessage(bufferTwo.toString());
					SendMessage.sendMessageToMapRoles(bean.getMap(),Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
				}
			}else if (bean.getHp()!=null) {
				bean.getHp().addHp(-1);
				if (bean.getHp().getHp()<=0) {
					allMonster.remove(bean.getI());
					getList(bean.getMap(), bean.getRobotid()).remove(bean);	 
					StringBuffer bufferTwo=new StringBuffer();
					bufferTwo.append(bean.getRobotname());
					bufferTwo.append("已被#R");
					bufferTwo.append(teams[0]);
					bufferTwo.append("#Y所带领的队伍完成最后一击");
					NChatBean chatBean=new NChatBean();
					chatBean.setId(4);
					chatBean.setMessage(bufferTwo.toString());
					SendMessage.sendMessageToMapRoles(bean.getMap(),Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
				}else {
					bean.setType(0);	
				}
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append("M");
				buffer.append(bean.getI());
				buffer.append("^");
				buffer.append(bean.getType());
				buffer.append("^H");
				buffer.append(bean.getHp().getHp());
				buffer.append(",");
				buffer.append(bean.getHp().getHpMax());
			}else {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append("M");
				buffer.append(bean.getI());
				buffer.append("^2");
				allMonster.remove(bean.getI());	
				getList(bean.getMap(), bean.getRobotid()).remove(bean);	
				TSModel tsModel=bean.getDieTsModel();//特殊怪死亡处理
				if (tsModel!=null) {tsModel.die(bean,teams);}
				FYModel fyModel=bean.getDieFyModel();//放妖人 额外掉落
				if (fyModel!=null) {fyModel.die(bean,teams);}
			}
		}else if (type==0) {//结束战斗 玩家失败
			bean.setType(0);
			if (bean.getExp()!=null&&teams!=null&&teams.length>=1) {
				NChatBean chatBean = new NChatBean();
				chatBean.setId(4);
				chatBean.setMessage(bean.getExp().addEXP(bean,teams[0]));
				SendMessage.sendMessageToAllRoles(Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
			}
			
			if (bean.getFollow() != null && bean.getFollow().getFollowID() == null) {
				PathPoint point = bean.getFollow().getPoints()[random.nextInt(bean.getFollow().getPoints().length)];
				bean.setX(point.getX());
				bean.setY(point.getY());
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append("M");
				buffer.append(bean.getI());
				buffer.append("^0^Z");
				buffer.append(bean.getX());
				buffer.append(",");
				buffer.append(bean.getY());
				
				StringBuffer bufferTwo = new StringBuffer();
				bufferTwo.append(bean.getRobotname());
				bufferTwo.append("重新出现在#R(");
				bufferTwo.append(point.getX() / 20);
				bufferTwo.append(",");
				bufferTwo.append(point.getY() / 20);
				bufferTwo.append(")");
				NChatBean chatBean = new NChatBean();
				chatBean.setId(4);
				chatBean.setMessage(bufferTwo.toString());
				SendMessage.sendMessageToMapRoles(bean.getMap(),Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(chatBean)));
			}else if (bean.getMove()!=null) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append("M");
				buffer.append(bean.getI());
				buffer.append("^0");
			}
		}
		return null;
		
	}
	/**删除 就删除 allMonster*/
	public static void removeMonster2(MapMonsterBean bean){
		allMonster.remove(bean.getI());	
		if (bean.getFollow()!=null) {
			followList.remove(bean);
		}
	}
	public synchronized static int getIncreasesum() {
		increasesum++;
		if (increasesum>99999999)increasesum=1;
		return increasesum;
	}
	/**获取跟随野怪*/
	public static MapMonsterBean getFollowMonster(String... names){
		for (int i = 0; i < followList.size(); i++) {
			MapMonsterBean bean=followList.get(i);
			if (bean.getFollow()==null||bean.getFollow().getFollowID()==null) {continue;}
			LoginResult loginResult=RolePool.getLoginResult(bean.getFollow().getFollowID());
			if (loginResult==null) {bean.getFollow().setFollowID(null);continue;}
			String follow=loginResult.getRolename();
			for (int j=0;j<names.length;j++) {
				if (names[j].equals(follow)) {
					return bean;
				}
			}
		}
		return null;
	}	
	/** 添加预加载的匹配队伍*/
	public static void addMatch(LoginResult loginResult,MapMonsterBean monsterBean) {
		if (loginResult == null) {return;}
		if (monsterBean.getMatch().addMatch(loginResult.getRolename())) {
			String TS=monsterBean.getRobotType()==4?  matchThread.MSG:
			          monsterBean.getRobotType()==132?matchThread.MSGTwo:
				Agreement.getAgreement().PromptAgreement(monsterBean.getRobotname()+"正在挑选顺眼的玩家…… 请等待#24");
			monsterBean.getMatch().setTS(TS);
			SendMessage.sendMessageByRoleName(loginResult.getRolename(), TS);
			if (!matchList.contains(monsterBean)) {
				matchList.add(monsterBean);
				matchThread.addMatch();
			}
		}
	
	}

	/** 判断是否为空 */
	public static boolean isMatch() {
		return matchList.size() != 0;
	}
	public static List<Boos> getBooses() {
		return booses;
	}
	public static void setBooses(List<Boos> booses) {
		MonsterUtil.booses = booses;
	}
   
}
