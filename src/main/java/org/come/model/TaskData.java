package org.come.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.RefreshMonsterTask;
import org.come.until.AllServiceUtil;

import come.tool.Battle.BattleMixDeal;
import come.tool.Good.DropModel;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.newTask.TaskConsume;
import come.tool.newTask.TaskRecord;

public class TaskData {
//	-|
//	 空格
//	目的类型0系统野怪 1自定义野怪 2击杀NPC 3问候NPC 4给与物品 5护送任务
//	0=Did集合=N指定名称=M	指定位置=S指定数量没有默认1
//	1=Did集合=N指定名称=M	指定位置=S指定数量没有默认1
//	2=Did集合=N指定名称=M	指定位置=S指定数量没有默认1
//	3=Did集合=N指定名称=M	指定位置=S指定数量没有默认1
//	4=Did集合=S指定数量没有默认1=G接受者ID
//	5=Did集合=G接受者ID

    
//	主线只记录进度
//	存在连续任务的 记录进度 
//  其他正常记录 完成次数 领取次数  
//	记录任务完成条件 系列ID=R任务领取次数=L任务完成次数=D任务进度
	private int taskID;//任务ID
	private int taskSetID;//任务系列ID
	private String taskName;//任务名称
	private String taskOpen;//任务领取选项   
	private transient String consume;//任务消耗     
	private transient String finishTerm;//任务目的 
	private transient String taskAward;//任务奖励
	private String taskText;//任务说明
	private String taskEnd;//任务结束语 
	private transient int postTaskTerm;//下一任务条件
	private transient String postTaskId;//后置任务id
	private String lvl;//等级限制
	private int TeamSum;//组队人数 0只能自己完成  其他数字表示队伍最少人数	
	private transient int DoorID;//传送 0为不传送
	private int isTP;//任务进行时传送限制 1不可传送
	private int time;//时间 0为无限制 单位分钟
    private String openTime;//任务开放时间	
 	private transient String talk;//喊话
 	private int nd;//难度系数
 	
	private transient int[] lvls;
    private transient String[] consumes;
    private transient TaskTerm[] taskTerms;
    private transient TaskTime[] taskTimes;
    private transient List<Integer> postTaskIds;
    private transient DropModel dropModel;
    private transient TaskSet taskSet;//任务系列数据
	
 	public TaskData() {
		// TODO Auto-generated constructor stub
	}
	/**判断任务是否在开发时间内 */
	public boolean isTime(){
		if (taskTimes==null) {
			return false;
		}
		for (int i = 0; i < taskTimes.length; i++) {
			TaskTime taskTime=taskTimes[i];
			if (taskTime.getWeek()==RefreshMonsterTask.day&&RefreshMonsterTask.hour>=taskTime.getStartTime()&&RefreshMonsterTask.hour<taskTime.getEndTime()) {
				return false;
			}
		}
		return true;
	}
	/**人数和等级判断*/
	public String isLvl(LoginResult[] logs,int size){
		if (TeamSum==0&&size!=1) {
			return Agreement.getAgreement().PromptAgreement("该任务只能一个人完成");
		}else if (TeamSum!=1&&TeamSum>size) {
			return Agreement.getAgreement().PromptAgreement("队伍人数不够"+TeamSum+"个人,先凑齐人数在来吧");
		}
		if (lvls!=null) {
			for (int i = 0; i < logs.length; i++) {
				String value=BattleMixDeal.isLvl(logs[i].getGrade(), lvls);
				if (value!=null) {return value;}
			}
		}
		return null;
	}
	/**领取次数 完成次数*/
	public String isSum(boolean is,LoginResult[] logs){
		if (taskSet.getSumlimit()==0&&taskSet.getSumreceive()==0)return null;
		for (int i = 0; i < logs.length; i++) {
			RoleData data=RolePool.getRoleData(logs[i].getRole_id());
			TaskRecord record=data.getTaskRecord(taskSetID);
			if (record!=null) {
				if (taskSet.getSumlimit()!=0&&record.getcSum()>=taskSet.getSumlimit()) {
					return Agreement.getAgreement().PromptAgreement(logs[i].getRolename()+"达到最大任务完成次数");
				}else if (is&&taskSet.getSumreceive()!=0&&record.getrSum()>=taskSet.getSumreceive()) {
					return Agreement.getAgreement().PromptAgreement(logs[i].getRolename()+"达到最大领取任务次数");
				}
			}
		}
		return null;	
	}
	/**任务消耗*/
	public Object isConsume(LoginResult[] logs){
		if (consumes==null) {return null;}
		TaskConsume taskConsume=null;
		for (int i = 0; i < consumes.length; i++) {
			String[] value=consumes[i].split("=");
			if (value[0].equals("前置任务")) {
				int qzmax=Integer.parseInt(value[2]);
				TaskData taskData=GameServer.getTaskData(Integer.parseInt(value[1]));
				for (int j = 0; j < logs.length; j++) {
					RoleData data=RolePool.getRoleData(logs[j].getRole_id());
					TaskRecord record=data.getTaskRecord(taskData.getTaskSetID());
					int qz=0;
					if (record!=null) {
						if (record.getNewID()!=0) {
							qz=record.getNewID()>=taskData.getTaskID()?1:0;
						}else {
							qz=record.getcSum();
						}
					}
					if (qz<qzmax) {
						StringBuffer buffer=new StringBuffer();
						buffer.append(logs[j].getRolename());
						if (qzmax==1) {
							buffer.append("需要完成前置任务");
							buffer.append(taskData.getTaskName());
							buffer.append("才能领取该任务!");		
						}else {
							buffer.append("需要完成");
							buffer.append(qzmax);
							buffer.append("次");
							buffer.append(taskData.getTaskName());
							buffer.append("才能领取该任务!已完成");		
							buffer.append(qz);	
							buffer.append("次");
						}
						return Agreement.getAgreement().PromptAgreement(buffer.toString());
					}
				}
			}else if (value[0].equals("金钱")) {
				long money=Long.parseLong(value[1]);
				for (int j = 0; j < logs.length; j++) {
					if (logs[j].getGold().longValue()<money){
						return Agreement.getAgreement().PromptAgreement(logs[j].getRolename()+"的金钱不够"+value[1]);
					}
				}
				if (taskConsume==null) {taskConsume=new TaskConsume();}
				taskConsume.setMoney(money);
			}else if (value[0].equals("物品")) {
				String[] vs=value[1].split("\\*");
				BigDecimal goodid=new BigDecimal(vs[0]);
				int sum=Integer.parseInt(vs[1]);	
				Goodstable goodstable=GameServer.getAllGoodsMap().get(goodid);
				if (goodstable!=null) {
					List<Goodstable> list=new ArrayList<>();
					for (int j = 0; j < logs.length; j++) {
						List<Goodstable> goods=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(logs[j].getRole_id(), goodid);
						int SYsum=sum;
						for (int k = 0; k < goods.size(); k++) {
							Goodstable good=goods.get(k);
							if (good.getUsetime()>=SYsum) {
								good.setUsetime(good.getUsetime()-SYsum);
								SYsum=0;
								list.add(good);
								break;
							}
							SYsum-=good.getUsetime();
							good.setUsetime(0);
							list.add(good);
						}
						if (SYsum>0) {
							return Agreement.getAgreement().PromptAgreement(logs[j].getRolename()+"不够"+sum+"个"+goodstable.getGoodsname());
						}
					}
					if (taskConsume==null) {taskConsume=new TaskConsume();}
					taskConsume.setGoods(list);
				}	
			}
		}
		return taskConsume;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public int getTaskSetID() {
		return taskSetID;
	}
	public void setTaskSetID(int taskSetID) {
		this.taskSetID = taskSetID;
	}
	public String getTaskOpen() {
		return taskOpen;
	}
	public void setTaskOpen(String taskOpen) {
		this.taskOpen = taskOpen;
	}
	public String getConsume() {
		return consume;
	}
	public void setConsume(String consume) {
		this.consume = consume;
	}
	public String getFinishTerm() {
		return finishTerm;
	}
	public void setFinishTerm(String finishTerm) {
		this.finishTerm = finishTerm;
	}
	public String getTaskAward() {
		return taskAward;
	}
	public void setTaskAward(String taskAward) {
		this.taskAward = taskAward;
	}
	public String getTaskText() {
		return taskText;
	}
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	public String getTaskEnd() {
		return taskEnd;
	}
	public void setTaskEnd(String taskEnd) {
		this.taskEnd = taskEnd;
	}
	public int getPostTaskTerm() {
		return postTaskTerm;
	}
	public void setPostTaskTerm(int postTaskTerm) {
		this.postTaskTerm = postTaskTerm;
	}
	public String getPostTaskId() {
		return postTaskId;
	}
	public void setPostTaskId(String postTaskId) {
		this.postTaskId = postTaskId;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public int getTeamSum() {
		return TeamSum;
	}
	public void setTeamSum(int teamSum) {
		TeamSum = teamSum;
	}
	public int getDoorID() {
		return DoorID;
	}
	public void setDoorID(int doorID) {
		DoorID = doorID;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getTalk() {
		return talk;
	}
	public void setTalk(String talk) {
		this.talk = talk;
	}
	public int[] getLvls() {
		return lvls;
	}
	public void setLvls(int[] lvls) {
		this.lvls = lvls;
	}
	public String[] getConsumes() {
		return consumes;
	}
	public void setConsumes(String[] consumes) {
		this.consumes = consumes;
	}
	public TaskTerm[] getTaskTerms() {
		return taskTerms;
	}
	public void setTaskTerms(TaskTerm[] taskTerms) {
		this.taskTerms = taskTerms;
	}
	public List<Integer> getPostTaskIds() {
		return postTaskIds;
	}
	public void setPostTaskIds(List<Integer> postTaskIds) {
		this.postTaskIds = postTaskIds;
	}
	public DropModel getDropModel() {
		return dropModel;
	}
	public void setDropModel(DropModel dropModel) {
		this.dropModel = dropModel;
	}
	public int getIsTP() {
		return isTP;
	}
	public void setIsTP(int isTP) {
		this.isTP = isTP;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public TaskTime[] getTaskTimes() {
		return taskTimes;
	}
	public void setTaskTimes(TaskTime[] taskTimes) {
		this.taskTimes = taskTimes;
	}
	public TaskSet getTaskSet() {
		if (taskSet==null) {
			taskSet=GameServer.getTaskSet(taskSetID);
		}
		return taskSet;
	}
	public void setTaskSet(TaskSet taskSet) {
		this.taskSet = taskSet;
	}
	/** 获取下一个任务 */
	public int getNewTaskId() {
		if (postTaskIds==null||postTaskIds.size()==0) {return 0;}
		return postTaskIds.get(GameServer.random.nextInt(postTaskIds.size()));
	}
	public int getNd() {
		return nd;
	}
	public void setNd(int nd) {
		this.nd = nd;
	}
	
}
