package org.come.readUtil;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Goodstable;
import org.come.handler.MainServerHandler;
import org.come.model.TaskData;
import org.come.model.TaskSet;
import org.come.model.TaskTerm;
import org.come.model.TaskTime;
import org.come.server.GameServer;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.tool.SplitStringTool;

import come.tool.Good.DropModel;

public class ReadTaskSetUtil {
    public static ConcurrentHashMap<Integer, TaskSet> selectTaskSet(String path, StringBuffer buffer){
    	// 读取刷新文件获取所有刷新信息
    	ConcurrentHashMap<Integer,TaskSet> map=new ConcurrentHashMap<>();
    	String[][] result = ReadExelTool.getResult("config/"+path+".xls");
    	for (int i = 1; i < result.length; i++) {
    		if (result[i][0].equals("")) {continue;}
    		TaskSet taskSet=new TaskSet();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(taskSet,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (taskSet.getTaskSetID()==0) {
				continue;
			}
			if (taskSet.getTaskMsg()!=null&&taskSet.getTaskMsg().equals("")) {
				taskSet.setTaskMsg(null);
			}
			map.put(taskSet.getTaskSetID(), taskSet);
			try {
				if (taskSet.getRobots()!=null&&!taskSet.getRobots().equals("")) {
					List<String> list=SplitStringTool.splitString(taskSet.getRobots());
					for (int j = 0,length=list.size(); j < length; j++) {
						int robotID=-Integer.parseInt(list.get(j));
						if (robotID>=0) {UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, "","robot出现负数ID");return null;}
						TaskSet set=map.get(robotID);
						if (set!=null) {UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, "","robot出现重复");return null;}
						map.put(robotID,taskSet);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0, taskSet.getRobots(),"taskset的robot数据解析错误");
                return null;
			}
		}
    	//添加活跃
    	TaskSet taskSet2=new TaskSet();
    	taskSet2.setTaskSetID(2);
    	taskSet2.setTaskType("日常活跃");
    	taskSet2.setResetcycle(1);
    	map.put(taskSet2.getTaskSetID(), taskSet2);
    	
    	TaskSet taskSet3=new TaskSet();
    	taskSet3.setTaskSetID(3);
    	taskSet3.setTaskType("单人竞技场");
    	taskSet3.setResetcycle(1);
    	map.put(taskSet3.getTaskSetID(), taskSet3);
    	
    	TaskSet taskSet4=new TaskSet();
    	taskSet4.setTaskSetID(4);
    	taskSet4.setTaskType("单人竞技场");
    	map.put(taskSet4.getTaskSetID(), taskSet4);
    	return map;
	}
    public static ConcurrentHashMap<Integer, TaskData> selectTaskData(String path, StringBuffer buffer){
		// 读取刷新文件获取所有刷新信息
		ConcurrentHashMap<Integer,TaskData> map=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
    	for (int i = 1; i < result.length; i++) {
    		if (result[i][0].equals("")) {continue;}
    		TaskData taskData=new TaskData();
			 for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(taskData, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			 }
			 try {
				if (taskData.getTaskID() == 0) {
					continue;
				}
				if (taskData.getLvl() != null && !taskData.getLvl().equals("")) {
					int[] lvls = new int[] { 0, 0, 4, 200, 200 };
					String[] lvls1 = taskData.getLvl().split("\\|");
					String[] vs = lvls1[0].split("\\-");
					lvls[1] = Integer.parseInt(vs[0]);
					lvls[3] = Integer.parseInt(vs[1]);
					if (lvls1.length >= 2) {
						lvls[0] = Integer.parseInt(lvls1[1]);
					}
					if (lvls1.length == 3) {
						vs = lvls1[2].split("\\-");
						lvls[2] = Integer.parseInt(vs[0]);
						lvls[4] = Integer.parseInt(vs[1]);
					}
					taskData.setLvls(lvls);
				}
				if (taskData.getConsume() != null && !taskData.getConsume().equals("")) {
					taskData.setConsumes(taskData.getConsume().split("\\|"));
				}
				if (taskData.getPostTaskId() != null && !taskData.getPostTaskId().equals("") && !taskData.getPostTaskId().equals("0")) {
					List<String> list = SplitStringTool.splitString(taskData.getPostTaskId());
					if (list.size() != 0) {
						List<Integer> integers = new ArrayList<>();
						for (int j = 0; j < list.size(); j++) {
							integers.add(Integer.parseInt(list.get(j)));
						}
						taskData.setPostTaskIds(integers);
					}
				}
				if (taskData.getFinishTerm() != null && !taskData.getFinishTerm().equals("")) {
					String[] vs = taskData.getFinishTerm().split(" ");
					TaskTerm[] taskTerms = new TaskTerm[vs.length];
					for (int j = 0; j < taskTerms.length; j++) {
						taskTerms[j] = new TaskTerm(vs[j]);
					}
					taskData.setTaskTerms(taskTerms);
				}
				if (taskData.getTaskAward() != null && !taskData.getTaskAward().equals("")) {
					taskData.setDropModel(new DropModel(taskData.getTaskAward().split("\\|")));
				}
				if (taskData.getOpenTime() != null && !taskData.getOpenTime().equals("")) {
					String[] vs = taskData.getOpenTime().split("\\|");
					TaskTime[] taskTimes = new TaskTime[vs.length];
					for (int j = 0; j < taskTimes.length; j++) {
						taskTimes[j] = new TaskTime();
						String[] times = vs[j].split("-");
						int week = Integer.parseInt(times[0]);
						if (week == 7) {
							week = 0;
						} else {
							week -= 1;
						}
						taskTimes[j].setWeek(week);
						taskTimes[j].setStartTime(Integer.parseInt(times[1]));
						taskTimes[j].setEndTime(Integer.parseInt(times[2]));
					}
					taskData.setTaskTimes(taskTimes);
				}
				if (taskData.getTaskText() != null && taskData.getTaskText().equals("")) {
					taskData.setTaskText(null);
				}
				if (taskData.getTaskSet()==null) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误","找不到对应的系列id任务id为:"+taskData.getTaskID());    
//					return null;
				}
				map.put(taskData.getTaskID(), taskData);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return map;
	}
	// TODO:物品掉落查询
	public static ConcurrentHashMap<String, List<String>> getTaskDrop(ConcurrentHashMap<String, List<String>> goodsByRobot) {
		ConcurrentHashMap<BigDecimal, Goodstable> map2 = GameServer.getAllGoodsMap();

		// 遍历robots，形成  <商品ID，[robotName1,robotName2...]> 模型数据
		for (TaskData data : GameServer.getAllTaskData().values()) {
			String itemStr = data.getTaskAward();
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
									if (!robotName.contains(data.getTaskName())) {
										robotName.add(data.getTaskName());
									} else {
										continue;
									}
								} else {
									robotName = new ArrayList<String>();
									robotName.add(data.getTaskName());
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
}
