package come.tool.newTask;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.action.suit.SuitPalEquip;
import org.come.action.sys.ChangeMapAction;
import org.come.bean.ChangeMapBean;
import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Achieve;
import org.come.model.ActiveAward;
import org.come.model.Door;
import org.come.model.TaskData;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleMixDeal;
import come.tool.Good.DropUtil;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;

public class TaskAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		//L领取任务 T任务超时 E取消任务 G完成给予类型条件 W完成问候类型条件 R领取任务奖励
		// 获取角色信息
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
		if (roleData==null) {return;}
		if (message.startsWith("T")) {//人物超时
			String[] vs=message.split("\\|");		
			vs[0]=vs[0].substring(1);
			String value=roleData.removeTasks(9,vs);
			if (value!=null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().TaskNAgreement(value));
			}
			return;
		}else if (message.startsWith("R")) {//R领取任务奖励
			message=message.substring(1);
			receive(ctx,loginResult,roleData,message);
			return;
		}
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return;}
		String type=message.substring(0, 1);
		if (type.equals("L")||type.equals("N")) {
			int nsize = 0;
			int taskID=0;
			if (type.equals("L")) {
				taskID=Integer.parseInt(message.substring(1));
			}else {
				String[] vs=message.substring(1).split("\\|");
				nsize=Integer.parseInt(vs[0]);
				taskID=Integer.parseInt(vs[1]);
				if (nsize>=5) {
					WriteOut.addtxt("下一个任务次数过多:"+nsize+":"+loginResult.getRole_id()+":"+loginResult.getRolename(),9999);
					return;
				}
			}
			if (taskID<1000) {
				WriteOut.addtxt("非法领取任务"+taskID+":"+loginResult.getRole_id()+":"+loginResult.getRolename(),9999);
				return;
			}
			TaskData taskData=GameServer.getTaskData(taskID);
			if (taskData==null) {return;}
			int max=0;
			int length=taskData.getTeamSum()<=1?1:teams.length;
			int size=teams.length+roleData.PSize();
			if (size>5) {size=5;}
			LoginResult[] logs=new LoginResult[length];
			for (int i = 0; i < length; i++) {
				LoginResult login=null;
				if (i==0) {login=loginResult;}
				else {
					ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(teams[i]);
					if (ctx2!=null) {login=GameServer.getAllLoginRole().get(ctx2);}
				}
				if (login==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(teams[i]+"处于异常状态"));return;}
				logs[i]=login;
				int lvl=BattleMixDeal.lvlint(login.getGrade());
				if (lvl>max) {max=lvl;}
			}
			TaskConsume taskConsume=null;
			Object object=TaskUtil.isTaskReceive(nsize==0,taskData,max,size,logs);
			if (object!=null) {
				if (object instanceof String) {SendMessage.sendMessageToSlef(ctx,(String)object);return;}
				else if (object instanceof TaskConsume) {taskConsume=(TaskConsume)object;}
			}
		    TaskUtil.taskConsume(taskConsume, logs);
		    if (taskData.getDoorID()!=0) {//判断是否有传送操作	
			    Door door=GameServer.getDoor(taskData.getDoorID());  
			    if (door!=null) {
			    	ChangeMapBean changeMapBean=new ChangeMapBean();
			    	changeMapBean.setMapid(door.getDoormap());
			    	String[] vs=door.getDoorpoint().split("\\|");
			    	changeMapBean.setMapx(Integer.parseInt(vs[0]));
			    	changeMapBean.setMapy(Integer.parseInt(vs[1]));
			    	ChangeMapAction.ChangeMap(changeMapBean, ctx);
				}
			}
		    if (nsize==0) {TaskUtil.addSumReceive(taskData, logs);} 
		    Task task=TaskUtil.createTask(taskID,max);
			task.setTaskState(TaskState.doTasking);
			if (task.getProgress()==null) {//直接完成任务
				task.setTaskState(TaskState.finishTask);
				TaskUtil.addSumLimit(taskData, logs);
				if (taskData.getTaskSet().getSumreceive()!=0||taskData.getTaskSet().getSumlimit()!=0) {
					StringBuffer buffer=new StringBuffer();
					buffer.append(task.getTaskId());
					buffer.append("=");
					buffer.append(task.getTaskState());
					StringBuffer buffer2=null;
					if (nsize==0&&taskData.getTaskSet().getSumreceive()!=0) {
						if (buffer2==null) {
							buffer2=new StringBuffer("C");
							buffer2.append(taskData.getTaskSetID());
						}
						buffer2.append("=R");
					}
					buffer2=TaskUtil.addTaskL(buffer2, taskData.getTaskID(), taskData.getTaskSet());
					if (buffer2!=null) {
						buffer.append("|");
						buffer.append(buffer2);
					}
					String msg=Agreement.getAgreement().TaskNAgreement(buffer.toString());
					for (int i = 0; i < logs.length; i++) {
						SendMessage.sendMessageByRoleName(logs[i].getRolename(), msg);
					}
				}
				//判断是否有下一个任务
				int newTaskId=taskData.getNewTaskId();
				nsize+=1;
				if (newTaskId!=0) {action(ctx, "N"+nsize+"|"+newTaskId);}
			}else {
				StringBuffer buffer=new StringBuffer();
				buffer.append(task.getTaskId());
				buffer.append("=");
				buffer.append(task.getTaskState());
				if (task.getTime()!=0) {buffer.append("=T");buffer.append(task.getTime()/1000);}
				TaskUtil.Progress(task,buffer);
				
				if (nsize==0&&taskData.getTaskSet().getSumreceive()!=0) {
					buffer.append("|C");
					buffer.append(taskData.getTaskSetID());
					buffer.append("=R");
				}
//				if (nsize==0&&taskData.getTaskSet().getSumreceive()!=0) {
//					buffer.append("=R");
//					buffer.append(taskData.getTaskSetID());	
//				}
				String msg=Agreement.getAgreement().TaskNAgreement(buffer.toString());
				for (int i = 0; i < logs.length; i++) {
					RoleData data=RolePool.getRoleData(logs[i].getRole_id());
					data.addTask(task,i==0);
					SendMessage.sendMessageByRoleName(logs[i].getRolename(), msg);
				}
			}
		}else if (type.equals("E")) {		
			int taskID=Integer.parseInt(message.substring(1));
			TaskData taskData=GameServer.getTaskData(taskID);
			if (taskData==null) {return;}
			String msg=Agreement.getAgreement().TaskNAgreement(taskID+"=8");
		    for (int i = 0; i < teams.length; i++) {
		    	ChannelHandlerContext ctx2=null;
				if (i==0) {roleData.removeTask(taskID);ctx2=ctx;}
				else{
					ctx2=GameServer.getRoleNameMap().get(teams[i]);
					if (ctx2==null) {continue;}
					LoginResult log2=GameServer.getAllLoginRole().get(ctx2);
					if (log2==null) {continue;}
					RoleData data2=RolePool.getRoleData(log2.getRole_id());
					if (data2==null) {continue;}
					data2.removeTask(taskID);
				}
				SendMessage.sendMessageToSlef(ctx2,msg);
				if (i==0&&taskData.getTeamSum()<=1) {
					break;
				}
			}
		}else if (type.equals("G")) {
			String[] vs=message.split("\\|");		
			vs[0]=vs[0].substring(1);
			int taskID=Integer.parseInt(vs[0]);
			TaskData taskData=GameServer.getTaskData(taskID);
			if (taskData==null) {return;}
			BigDecimal rgid=new BigDecimal(vs[1]);
			int sum=Integer.parseInt(vs[2]);
			Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(rgid);
			if (good==null||good.getRole_id().compareTo(loginResult.getRole_id())!=0||sum>good.getUsetime()) {
				return;
			}
			good.goodxh(sum);
			AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
			assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
			String name=good.getGoodsname();
			MMM(taskData, teams, name, sum, "给予物品");
		}else if (type.equals("W")) {
			String[] vs=message.split("\\|");		
			vs[0]=vs[0].substring(1);
			int taskID=Integer.parseInt(vs[0]);
			TaskData taskData=GameServer.getTaskData(taskID);
			if (taskData==null) {return;}
			String name=vs[1];
			MMM(taskData, teams, name, 1, "问候");
		}
	}
	/***/
	public void MMM(TaskData taskData,String[] teams,String name,int sum,String type){
		int length=taskData.getTeamSum()<=1?1:teams.length;
		for (int i = length-1; i >=0; i--) {
			LoginResult login=null;
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
			if (ctx==null) {continue;}
			login=GameServer.getAllLoginRole().get(ctx);
			if (login==null) {continue;}
			RoleData data=RolePool.getRoleData(login.getRole_id());
			if (data==null) {continue;}
			Task task=data.getTask(taskData.getTaskID());
			if (task==null) {continue;}
			int part=task.PartFinish(name,sum,type);
			if (part!=0) {
				StringBuffer buffer=new StringBuffer();
				boolean is=task.isFinish();
				buffer.append(task.getTaskId());
				buffer.append("=");
				if (is) {//开启下一个任务
					buffer.append(TaskState.finishTask);
					data.removeTask(task.getTaskId());
					if (taskData.getTaskAward()!=null&&!taskData.getTaskAward().equals("")) {
						DropUtil.getDrop(login,taskData.getTaskAward(),taskData.getTalk(),22,1D,null);
					}
					if (taskData.getTaskSet().getSumlimit()!=0) {
						TaskUtil.addSumLimit(taskData,login);
						StringBuffer buffer2=TaskUtil.addTaskL(null, taskData.getTaskID(), taskData.getTaskSet());
						if (buffer2!=null) {
							buffer.append("|");
							buffer.append(buffer2);
						}
					}
					//判断是否有下一个任务
					int newTaskId=taskData.getNewTaskId();
					if (newTaskId!=0) {
						Task task2=TaskUtil.TaskReceive(newTaskId, teams.length, 0, login, data, buffer);
						 if (i==0&&task2!=null&&task2.getTaskData().getDoorID()!=0) {//判断是否有传送操作	
							Door door = GameServer.getDoor(task2.getTaskData().getDoorID());
							if (door != null) {
								ChangeMapBean changeMapBean = new ChangeMapBean();
								changeMapBean.setMapid(door.getDoormap());
								String[] vs = door.getDoorpoint().split("\\|");
								changeMapBean.setMapx(Integer.parseInt(vs[0]));
								changeMapBean.setMapy(Integer.parseInt(vs[1]));
								ChangeMapAction.ChangeMap(changeMapBean,ctx);
							}
						}
					}
				}else {
					buffer.append(task.getTaskState());
					TaskUtil.Progress(task,buffer);
				}
				String msg=Agreement.getAgreement().TaskNAgreement(buffer.toString());
				SendMessage.sendMessageByRoleName(login.getRolename(), msg);
			}
		}
	}
//	Tasksetid  3   单人竞技场挑战记录
//	今日购买次数 今日挑战次数
//	Tasksetid  4   单人竞技场次数记录
//	战斗场次 胜利场次 领取记录
	/**领取奖励*/
	public void receive(ChannelHandlerContext ctx,LoginResult loginResult,RoleData roleData,String v){
		int id=v.indexOf("=");
		int bs=0;
		if (id==-1) {
			id=Integer.parseInt(v);
		}else {
			bs=Integer.parseInt(v.substring(id+1));
			id=Integer.parseInt(v.substring(0,id));
		}
		if (id==2) {//领取活跃奖励
			ActiveAward award=GameServer.getActiveAward(bs);
			if (award==null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("没有该档次的活跃奖励"));
				return;
			}
			TaskRecord taskRecord=roleData.getTaskRecord(2);
			if (taskRecord!=null&&((taskRecord.getNewID()>>bs)&0x01)==1) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已领取过该奖励"));
				return;
			}
			int value=GameServer.getActiveValue(roleData);
			if (value<award.getActive()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你活跃度不够"));
				return;
			}
			if (taskRecord==null) {taskRecord=new TaskRecord(2);}
			taskRecord.setNewID(taskRecord.getNewID()|(1<<bs));
			roleData.addTaskRecord(taskRecord);
			//领取奖励
			AssetUpdate assetUpdate=DropUtil.getDrop(loginResult, award.getDropModel(), award.getActive()+"活跃礼包", null, 1, 0, 1, 22);
			if (assetUpdate==null) {assetUpdate=new AssetUpdate(AssetUpdate.USERGOOD);}
			assetUpdate.setTask("C2=N"+taskRecord.getNewID());
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
		}else if (id==4) {
			Achieve achieve=GameServer.getAchieve(bs);
			if (achieve==null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("没有该档次的奖励"));
				return;
			}
			TaskRecord taskRecord=roleData.getTaskRecord(4);
			if (taskRecord!=null&&((taskRecord.getNewID()>>bs)&0x01)==1) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已领取过该奖励"));
				return;
			}
			if (achieve.getNum()>(taskRecord!=null?taskRecord.getcSum():0)) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你还没达到条件"));
				return;
			}
			if (taskRecord==null) {taskRecord=new TaskRecord(4);}
			taskRecord.setNewID(taskRecord.getNewID()|(1<<bs));
			roleData.addTaskRecord(taskRecord);
			AssetUpdate assetUpdate=DropUtil.getDrop(loginResult, achieve.getDropModel(), achieve.getName(), null, 1, 0, 1, 22);
			if (assetUpdate==null) {assetUpdate=new AssetUpdate(AssetUpdate.USERGOOD);}
			assetUpdate.setTask("C4=N"+taskRecord.getNewID());
			//判断是否会增加称谓奖励
			if (bs%3==0) {
				int lvl=bs/3;
				String value=SuitPalEquip.getOneArena(null, lvl);
				UseCardBean cardBean=roleData.getLimit("单人竞技场");
				if (cardBean==null) {
					cardBean=new UseCardBean(achieve.getName(), "单人竞技场", "cwzs", 0, null);
					for (int i = 1; i < lvl; i++) {
						String value2=SuitPalEquip.getOneArena(null, i);
						cardBean.upValue(value2,i-1);
					}
					roleData.addLimit(cardBean);
				}
				cardBean.upValue(value, lvl-1);
				cardBean.setName(achieve.getName());
				assetUpdate.setUseCard(cardBean);
				assetUpdate.upmsg("你的"+cardBean.getName()+"BUFF刷新了");
				AllServiceUtil.getGoodsrecordService().insertGoodsrecord(loginResult.getRole_id(), null, 50201, loginResult.getRole_id(), "单人竞技场属性", cardBean.getValue(), 1);
			}
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
		}
	}
}
