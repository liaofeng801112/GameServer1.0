package come.tool.Battle;

import java.util.List;

import org.come.model.Robots;

public class BattleEndMonitor {

	//战斗id
	private int fightingId;
	//战斗robotID
	private int robotId;
	//该战斗可完成的任务集合
	private List<Integer> taskIds;
	
	public BattleEndMonitor(int fightingId,Robots robots) {
		super();
		this.fightingId = fightingId;
		if (robots!=null) {
			this.robotId=robots.getRobotID();
			this.taskIds=robots.getTaskIds();		
		}
	}
	public int getFightingId() {
		return fightingId;
	}
	public void setFightingId(int fightingId) {
		this.fightingId = fightingId;
	}
	public int getRobotId() {
		return robotId;
	}
	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}
	public List<Integer> getTaskIds() {
		return taskIds;
	}
	public void setTaskIds(List<Integer> taskIds) {
		this.taskIds = taskIds;
	}
}
