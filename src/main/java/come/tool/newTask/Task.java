package come.tool.newTask;

import java.util.ArrayList;
import java.util.List;

import org.come.model.Npctable;
import org.come.model.TaskData;
import org.come.model.TaskProgress;
import org.come.model.TaskSet;
import org.come.server.GameServer;

public class Task {
	/**任务id*/
	private int taskId;
	/**任务状态*/
	private int taskState;
	/**任务进度   */
	private List<TaskProgress> progress;
	/**任务超时*/
	private long time;
	/**任务配置表*/
	private transient TaskData taskData;
	public Task(int taskId) {
		super();
		this.taskId = taskId;
	}
	
	public Task(int taskId, int taskState,String[] text) {
		super();
		this.taskId = taskId;
		this.taskState = taskState;
		this.addProgress(text);
	}
	/**将任务进度拆分*/
	public void addProgress(String[] values){
		if (progress!=null) {progress.clear();}
		else {progress=new ArrayList<>();}
        if (values==null) {return;}
    	//任务id=任务状态=T过期时间=PTYPE_S1-1_MMAP-X-Y_DID-NAME_GID-NAME;
		for (int i = 2; i < values.length; i++) {
			if (values[i].startsWith("P")) {
				String[] vs=values[i].split("_");
				TaskProgress taskProgress=new TaskProgress();
				taskProgress.setType(Integer.parseInt(vs[0].substring(1)));
				taskProgress.setSum(0);
				taskProgress.setMax(1);
				for (int j = 1; j < vs.length; j++) {
					if (vs[j].startsWith("S")) {
						String[] ts=vs[j].split("-");
						taskProgress.setSum(Integer.parseInt(ts[0].substring(1)));
						taskProgress.setMax(Integer.parseInt(ts[1]));
					}else if (vs[j].startsWith("M")) {
						String[] ts=vs[j].split("-");
						taskProgress.setMap(Integer.parseInt(ts[0].substring(1)));
						taskProgress.setX(Integer.parseInt(ts[1]));
						taskProgress.setY(Integer.parseInt(ts[2]));
					}else if (vs[j].startsWith("D")) {
						String[] ts=vs[j].split("-");
						taskProgress.setDId(Integer.parseInt(ts[0].substring(1)));
						taskProgress.setDName(ts[1]);
					}else if (vs[j].startsWith("G")) {
						String[] ts=vs[j].split("-");
						taskProgress.setGId(Integer.parseInt(ts[0].substring(1)));
						taskProgress.setGName(ts[1]);
					}
				}
				progress.add(taskProgress);
			}else if (values[i].startsWith("T")) {
				this.time=Long.parseLong(values[i].substring(1))*1000;
			}	
		}
	}
	/**
	 * 完成部分
	 * false无关 true有关
	 * @return
	 */
	public int PartFinish(int id,int sum,String leixing){
		for (int i = 0; i < progress.size(); i++) {
			TaskProgress Progre=progress.get(i);
			if (Progre.getSum()>=Progre.getMax()) {
				continue;
			}
			if ((Progre.getType()==0||Progre.getType()==1||Progre.getType()==2)&&leixing.equals("击杀")) {
				if (Progre.getType()==0||Progre.getType()==1) {
					if (id==Progre.getDId()) {
						Progre.addSum(sum);
	    				return 3;
					}
				}else if (Progre.getType()==2) {
					Npctable npctable=GameServer.getNpc(Progre.getDId()+"");
					if (npctable!=null) {
						if (npctable.getRobotID()==id) {
							Progre.addSum(sum);
		    				return 3;
						}
					}
				}
			}else if ((Progre.getType()==3||Progre.getType()==5)&&leixing.equals("问候")) {
				if (id==(Progre.getType()==3?Progre.getDId():Progre.getGId())) {
				    Progre.addSum(sum);
				    return 1;
				}
			}else if (Progre.getType()==4&&leixing.equals("给予物品")) {
				if (id==Progre.getDId()) {
				    Progre.addSum(sum);
				    return 1;
				}
			}
		}		
		return 0;
	}
	/**
	 * 完成部分
	 * false无关 true有关
	 * @return
	 */
	public int PartFinish(String content,int sum,String leixing){
		for (int i = 0; i < progress.size(); i++) {
			TaskProgress Progre=progress.get(i);
			if (Progre.getSum()>=Progre.getMax()) {
				continue;
			}
			if (content.equals((Progre.getType()!=5?Progre.getDName():Progre.getGName()))) {
				if ((Progre.getType()==0||Progre.getType()==1||Progre.getType()==2)&&leixing.equals("击杀")) {
					Progre.addSum(sum);
    				return 3;	
				}else if ((Progre.getType()==3||Progre.getType()==5)&&leixing.equals("问候")) {
					Progre.addSum(sum);
    				return 1;
				}else if (Progre.getType()==4&&leixing.equals("给予物品")) {
					Progre.addSum(sum);
    				return 2;
				}
			}
		}		
		return 0;
	}
	/**任务是否完成*/
	public boolean isFinish(){
		if (taskState==TaskState.doTasking) {
			if (progress==null) {
				taskState=TaskState.completeTask;
				return true;
			}
			for (int i = 0; i < progress.size(); i++) {
				if (progress.get(i).getSum()<progress.get(i).getMax()) {
					return false;
				}
			}
			taskState=TaskState.completeTask;
			return true;	
		}
		return false;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTaskState() {
		return taskState;
	}
	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}
	public List<TaskProgress> getProgress() {
		return progress;
	}
	public void setProgress(List<TaskProgress> progress) {
		this.progress = progress;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public TaskData getTaskData() {
		if (taskData==null) {
			taskData=GameServer.getTaskData(taskId);
		}
		return taskData;
	}
	public TaskSet getTaskSet() {
		return getTaskData().getTaskSet();
	}
	/**复制*/
	public Task FZ(){
		Task task=new Task(taskId);
		task.taskState=taskState;
		task.time=time;
		if (progress!=null) {
			task.progress=new ArrayList<>();
			for (int i = 0; i < progress.size(); i++) {
				task.progress.add(new TaskProgress(progress.get(i)));
			}
		}
		return task;
	}
}
