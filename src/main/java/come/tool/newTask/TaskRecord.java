package come.tool.newTask;

public class TaskRecord {
	private int taskId;//系列ID
	private int rSum;//任务领取次数
	private int cSum;//任务完成次数
	private int newID;//连续任务最新任务ID
	
	public TaskRecord(int taskId) {
		super();
		this.taskId = taskId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getrSum() {
		return rSum;
	}
	public void addRSum(int add) {
		this.rSum+=add;
	}
	public int getcSum() {
		return cSum;
	}
	public void addCSum(int add) {
		this.cSum+=add;
	}
	public int getNewID() {
		return newID;
	}
	public void setNewID(int newID) {
		this.newID = newID;
	}
}
