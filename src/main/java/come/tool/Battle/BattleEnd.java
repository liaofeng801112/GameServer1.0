package come.tool.Battle;

import come.tool.Stall.AssetUpdate;
import come.tool.oneArena.OneArenaBean;

/**
 * 战斗结束
 * @author Administrator
 */
public class BattleEnd {
	/**战斗编号*/
	private int FightingNumber;
	/**胜利阵营*/
	private int camp;
	private AssetUpdate assetUpdate;
	private OneArenaBean arenaBean;
	/**任务数据*/
	private String taskn;
	/**坐牢数据 PK点数=身份标志=做天牢次数=每周坐牢次数*/       
	private String taskDaily;
	private String msg;
	public BattleEnd(int fightingNumber, int camp) {
		super();
		FightingNumber = fightingNumber;
		this.camp = camp;
	}
	/**清理*/
	public void clean(){
		this.assetUpdate=null;
		this.taskDaily=null;
		this.taskn=null;
		this.msg=null;
		this.arenaBean=null;
	}
	public void upMsg(String msg) {
		if (msg==null||msg.equals("")) {return;}
		if (this.msg==null||this.msg.equals("")) {
			this.msg=msg;
		}else {
			this.msg=this.msg+"|"+msg;
		}
	}
	public void upAssetData(String data) {
		if (data==null||data.equals("")) {return;}
		if (assetUpdate==null) {
			assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		}
		assetUpdate.updata(data);
	}
	public void upAssetMsg(String msg) {
		if (msg==null||msg.equals("")) {return;}
		if (assetUpdate==null) {
			assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
		}
		assetUpdate.upmsg(msg);
	}
	public int getFightingNumber() {
		return FightingNumber;
	}
	public void setFightingNumber(int fightingNumber) {
		FightingNumber = fightingNumber;
	}
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
		this.camp = camp;
	}
	public AssetUpdate getAssetUpdate() {
		return assetUpdate;
	}
	public void setAssetUpdate(AssetUpdate assetUpdate) {
		this.assetUpdate = assetUpdate;
	}
	public String getTaskn() {
		return taskn;
	}
	public void upTaskn(String taskn) {
		if (taskn==null||taskn.equals("")) {return;}
		if (this.taskn==null||this.taskn.equals("")) {this.taskn=taskn;}
		else {this.taskn=this.taskn+"|"+msg;}
	}
	public void setTaskn(String taskn) {
		this.taskn = taskn;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTaskDaily() {
		return taskDaily;
	}
	public void setTaskDaily(String taskDaily) {
		this.taskDaily = taskDaily;
	}
	public OneArenaBean getArenaBean() {
		return arenaBean;
	}
	public void setArenaBean(OneArenaBean arenaBean) {
		this.arenaBean = arenaBean;
	}
}
