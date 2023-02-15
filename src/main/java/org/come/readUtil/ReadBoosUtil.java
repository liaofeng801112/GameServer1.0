package org.come.readUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Goodstable;
import org.come.handler.MainServerHandler;
import org.come.model.Boos;
import org.come.model.Monster;
import org.come.model.Npctable;
import org.come.model.Robots;
import org.come.model.TaskData;
import org.come.model.TaskTerm;
import org.come.server.GameServer;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.tool.SplitStringTool;

import come.tool.FightingData.GetqualityUntil;
import come.tool.FightingData.Ql;
import come.tool.Good.DropModel;
import come.tool.Good.TSModel;
/**
 * 怪物刷新信息
 * @author 叶豪芳
 * @date 2017年12月27日 下午3:55:57
 * 
 */ 
public class ReadBoosUtil {

	/**获得全部Boos刷新信息*/
	public static List<Boos> selectBoos(String path, StringBuffer buffer){
		List<Boos> booses = new ArrayList<Boos>();
		// 读取刷新文件获取所有刷新信息
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Boos boos = new Boos();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(boos,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (boos.getBoosid().equals("")) {continue;}

			if (boos.getBoosstime()<0||boos.getBoosstime()>24) {UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0, result[i][0],"开始时间违规");return null;}
			if (boos.getBoosetime()<0||boos.getBoosetime()>24) {UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0, result[i][0],"结束时间违规");return null;}
			int value=boos.getBoosrtime()/5;
			if (value<=0) {value=24;};
			boos.setBoosrtime(value*5);
			if (boos.getBoosDrop()==null||boos.getBoosDrop().equals("")) {boos.setBoosDropMax(0);}
			booses.add(boos);
		}
		return booses;
	}
	/**根据怪兽ID查询怪兽信息,组成一个集合*/
	public static ConcurrentHashMap<String, Monster> getMonster(String path, StringBuffer buffer){
		ConcurrentHashMap<String, Monster> monsterMap = new ConcurrentHashMap<String, Monster>();  
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Monster monster = new Monster();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(monster,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (monster.getMonsterid()==0) {continue;}
			try {
				Ql ql=GetqualityUntil.getMonsterQl(monster);
    			ql.setRoleklb(ql.getRoleklb()-100);
    			ql.setKzds(ql.getKzds()-4000);
    			monster.setQl(ql);
    			monsterMap.put(monster.getMonsterid()+"",monster);								
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return monsterMap;
	}
	/**根据怪兽ID查询怪兽掉落信息*/
	public static ConcurrentHashMap<String, Robots> getRobot(String path, StringBuffer buffer){
		Map<Integer,List<Integer>> robotMap=getTaskIDs();
		ConcurrentHashMap<String, Robots> allRobotMap = new ConcurrentHashMap<String, Robots>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Robots robot = new Robots();
			for (int j = 0; j < result[i].length; j++) {
				try {
					if (j==13) {
						if (!result[i][j].equals("")) {robot.setTsModel(new TSModel(result[i][j]));}
					}else {
						SettModelMemberTool.setReflect(robot,result[i][j], j);	
					}
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			try {
				if (robot.getRobotid()==null||robot.getRobotid().equals("")) {continue;}
				robot.setRobotID(Integer.parseInt(robot.getRobotid()));
				if (robot.getLvllimit()!=null&&!robot.getLvllimit().equals("")) {
					int[] lvls=new int[]{0,0,4,200,200};
					String[] lvls1=robot.getLvllimit().split("\\|");
					String[] vs=lvls1[0].split("\\-");
					lvls[1]=Integer.parseInt(vs[0]);
					lvls[3]=Integer.parseInt(vs[1]);
					if (lvls1.length>=2) {
						lvls[0]=Integer.parseInt(lvls1[1]);
					}
					if (lvls1.length==3) {
						vs=lvls1[2].split("\\-");
						lvls[2]=Integer.parseInt(vs[0]);
						lvls[4]=Integer.parseInt(vs[1]);
					}
					robot.setLvls(lvls);
				}
				robot.setTaskIds(robotMap.get(Integer.parseInt(robot.getRobotid())));
				if (robot.getRobotmonster()!=null&&!robot.getRobotmonster().equals("")) {
					robot.setMonsterList(SplitStringTool.splitString(robot.getRobotmonster()));
				}
				if (robot.getRobotreward()!=null&&!robot.getRobotreward().equals("")) {
					robot.setDropModel(new DropModel(robot.getRobotreward().split("\\|")));
				}
				if (robot.getRobotname().indexOf("#")!=-1) {robot.setRobotname(robot.getRobotname().replace("#"," "));}
		        if (robot.getRobotname().indexOf("-")!=-1) {robot.setRobotname(robot.getRobotname().replace("-"," "));}
		        if (robot.getRobotname().indexOf("|")!=-1) {robot.setRobotname(robot.getRobotname().replace("|"," "));}
		        
		        allRobotMap.put(robot.getRobotid(), robot);
				//TODO 限制掉落的怪物绑定任务
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return allRobotMap;
	}
	/**获取任务包含的 robot范围*/
	public static Map<Integer,List<Integer>> getTaskIDs(){
		ConcurrentHashMap<String,Npctable> npcMap=GameServer.getNpcMap();
		ConcurrentHashMap<Integer,TaskData> taskMap=GameServer.getAllTaskData();
		Map<Integer,List<Integer>> robotMap=new HashMap<>();
		for (TaskData taskData : taskMap.values()) { 
			if (taskData.getTaskTerms()==null) {
				continue;
			}
			TaskTerm[] taskTerms=taskData.getTaskTerms();
			for (int i = 0; i < taskTerms.length; i++) {
				TaskTerm taskTerm=taskTerms[i];
				if (taskTerm.getType()==0||taskTerm.getType()==1) {
					for (int j = 0; j < taskTerm.getdList().size(); j++) {
						int robotId=Integer.parseInt(taskTerm.getdList().get(j));
						List<Integer> list=robotMap.get(robotId);
						if (list==null) {
							list=new ArrayList<>();
							robotMap.put(robotId, list);
						}
						if (!list.contains(taskData.getTaskID())) {
							list.add(taskData.getTaskID());
							robotMap.put(robotId, list);
						}
					}
				}else if (taskTerm.getType()==2) {
					for (int j = 0; j < taskTerm.getdList().size(); j++) {
						Npctable npctable=npcMap.get(taskTerm.getdList().get(j));
						if (npctable==null||npctable.getBinding()==null||npctable.getBinding().equals("")) {continue;}
						int robotId=Integer.parseInt(npctable.getBinding());
						List<Integer> list=robotMap.get(robotId);
						if (list==null) {
							list=new ArrayList<>();
							robotMap.put(robotId, list);
						}
						if (!list.contains(taskData.getTaskID())) {
							list.add(taskData.getTaskID());
						}
					}
				}
			}
		}
		return robotMap;
	}
	/**去重复*/
	public static List<String> removeDuplicate(List<String> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	} 
	// 创建ID与boos对应的map
	public static ConcurrentHashMap<String, Boos> boosesMap(List<Boos> booses){
		ConcurrentHashMap<String, Boos> boosMap = new ConcurrentHashMap<>();
		for (Boos boos : booses) {
			boosMap.put(boos.getBoosid(), boos);
		}
		return boosMap;
	}
    public static Random random =new Random();
	// 产生随机数
	public static List<Integer> randomArray(int max,int n){
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			int ran = random.nextInt(max);
			result.add(ran);
		}
		return result;
	}

	/**查询外务掉落*/
	public static ConcurrentHashMap<String, List<String>> getRobotByGoods(ConcurrentHashMap<String, Robots> map) {
		ConcurrentHashMap<String, List<String>> goodsByRobot = new ConcurrentHashMap<String, List<String>>();
		ConcurrentHashMap<BigDecimal, Goodstable> map2 = GameServer.getAllGoodsMap();

		// 遍历robots，形成  <商品ID，[robotName1,robotName2...]> 模型数据
		for (Robots robot : map.values()) {
			String itemStr = robot.getRobotreward(); //固定经验=2000000|金钱=20000|最大经验=3-50&2-100&1-200|物品=0&193-194-307-308-171-80125-309-310-311-728$1$30&729-80164-614-729-80031-80034-10079-80489-80049-80001-80002-80003-80028-7050-80164-727-772-212-80031-80034-80010-600-601-602-603-604-605-606-607-608-609-610-611-612-613-80113-80114-80115-80116-80117-80118-80119-80120-80121-80122-81014-81015-80010$1$20&6500-6505-6510-6515-6520-6525-6530-6535-6540-6545-6550-6555-6560-6565-6570-6575-6580-6585-6590-6595-6600-6605-6610-6615-6620-6625-6630-6635-6640-6645-6650-6655-6660-6665-6670-6675-6680-6685-6690-6695-6700-6705-6710-6715-6720-6725-6730-6735-6740-6745-6750-6755-6760-6765-6770-6775-6780-6785-6790-6795-6800-6805-6810-6815-6820-6825-6830-6835-6840-6845-6850-6855-6860-6865-6870-6875-6880-6885-6890-6895-6900-6905-6910-6915-6920-6925-6930-6935-6940-6945-80175-80180-80185-80190-80195-80200-80205-80210-80215-80220-80225-80230-80235-80240-80245-80250-80255-80260-80265-80270-80275-80280-80285-80290-80295-80300-80305-80310-80315-80320-80325-80330-80335-80340-80345-80350-80355-80360-80365-80370-80375-80380-80385-80390-80395-80400-80405-80410-80415-80420-80425-80430-80435-80440-80405-80410-80415-80420-80425-80430-80435-80440-80445-80450-80455-80450-80455-80460-80103-80104-80105-80106-80107-80108-80109-80110-80111-80122-81012-81013-730-81105-81095$1$5&80073-80074-80075-80076-80077-80078-80079-80080-80081-80082-81004-81005$1$0.02&731-80154-80059-80004-80005-80006-770-730-731-748-80154-80166-8070-80073-80074-80075-80076-80077-80078-80079-80080-80081-80082-81004-81005$1$1&502-503-504-505-506-507-508-509-510-511-512-513-514$1$0.6&515-516-522-542-543-544-545-546-556$1$0.3&80168-80167-81105-81095$1$2&80132-80133-80134-80135-80136-80137-80138-80140-80141-80142-80143-80144-80145-80146-80147-80148-80149-80150-80151$1$0.1&80125-167-185-80164-171-728-600-601-81020$1$80&204-205-206$3$100
			String[] items = itemStr.split("\\|");
			for (String item : items) {
				if (item.startsWith("物品")) {
					String[] n = item.split("&");
					for (String nn : n) {
						if (nn.startsWith("物品")) {
							continue;
						}
						String[] m = nn.split("\\$");
						String[] ids = m[0].split("-"); // 物品ID
						for (String id : ids) {
							// 得到物品ID查询物品名称
							Goodstable goods = map2.get(new BigDecimal(id));
							if (goods != null) {
								List<String> robotName;
								if (goodsByRobot.containsKey(goods.getGoodsname())) {
									robotName = goodsByRobot.get(goods.getGoodsname());
									if (!robotName.contains(robot.getRobotname())) {
										robotName.add(robot.getRobotname());
									} else {
										continue;
									}
								} else {
									robotName = new ArrayList<String>();
									robotName.add(robot.getRobotname());
								}
								goodsByRobot.put(goods.getGoodsname(), robotName);
							}
						}
					}
				}
			}
		}


		return goodsByRobot;
	}
	/**
	 * 插入单个掉落
	 * @param goodsByRobot
	 * @param itemStr
	 * @param dropName
	 * @return
	 */
	public static ConcurrentHashMap<String, List<String>> setDrop(ConcurrentHashMap<String, List<String>> goodsByRobot,String itemStr,String dropName) {
		ConcurrentHashMap<BigDecimal, Goodstable> map2 = GameServer.getAllGoodsMap();

		String[] items = itemStr.split("\\|");
		for (String item : items) {
			if (item.startsWith("物品")) {
				String[] n = item.split("&");
				for (String nn : n) {
					if (nn.startsWith("物品")) {
						continue;
					}
					String[] m = nn.split("\\$");
					String[] ids = m[0].split("-"); // 物品ID
					for (String id : ids) {
						// 得到物品ID查询物品名称
						Goodstable goods = map2.get(new BigDecimal(id));
						if (goods != null) {
							List<String> robotName;
							if (goodsByRobot.containsKey(goods.getGoodsname())) {
								robotName = goodsByRobot.get(goods.getGoodsname());
								if (!robotName.contains(dropName)) {
									robotName.add(dropName);
								} else {
									continue;
								}
							} else {
								robotName = new ArrayList<String>();
								robotName.add(dropName);
							}
							goodsByRobot.put(goods.getGoodsname(), robotName);
						}
					}
				}
			}
		}


		return goodsByRobot;
	}
}
