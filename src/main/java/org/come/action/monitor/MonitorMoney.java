package org.come.action.monitor;

public class MonitorMoney {
    /**增量*/
	/**仙玉*/
	private long add_X_CZ;//充值增加
	private long add_X_VIP;//VIP系统增加
	private long add_X_Other;//其他增加
	
	/**大话币*/
	private long add_D_Robot;//robot产出
	private long add_D_Task;//任务产出
	private long add_D_GGL;//刮刮乐产出
	private long add_D_Other;//其他增加
	
	/**充值积分*/
	private long add_C_CZ;//充值增加
	
	/**消耗量*/
	private long use_X;//消耗的仙玉
	private long use_D;//消耗的大话币
	private long use_C;//消耗的充值积分
	
	public void toString(StringBuffer buffer){
		buffer.append("__今日充值总量:");
		buffer.append(add_C_CZ);
		buffer.append("__今日充值积分消耗:");
		buffer.append(use_C);
		buffer.append("__今日仙玉增量:");
		buffer.append((add_X_CZ+add_X_VIP+add_X_Other));
		buffer.append("__来源:充值=");
		buffer.append(add_X_CZ);
		buffer.append(",VIP系统=");
		buffer.append(add_X_VIP);
		buffer.append(",其他游戏行为=");
		buffer.append(add_X_Other);
		
		buffer.append("__今日大话币增量:");
		buffer.append((add_D_Robot+add_D_Task+add_D_GGL+add_D_Other)/10000L);
		buffer.append("W__来源:robot=");
		buffer.append(add_D_Robot/10000L);
		buffer.append("W,任务=");
		buffer.append(add_D_Task/10000L);
		buffer.append("W,刮刮乐=");
		buffer.append(add_D_GGL/10000L);
		buffer.append("W,其他游戏行为=");
		buffer.append(add_D_Other/10000L);
		
		buffer.append("W__今日仙玉消耗:");
		buffer.append(use_X);
		buffer.append("__今日大话币消耗:");
		buffer.append(use_D/10000L);
		buffer.append("W");
		
	}
	/**清空*/
	public void reset(){
		add_X_CZ=0;add_X_VIP=0;add_X_Other=0;
		add_D_Robot=0;add_D_Task=0;add_D_GGL=0;add_D_Other=0;
		add_C_CZ=0;
		use_X=0;
		use_D=0;
		use_C=0;
	}
	
	/**添加增加仙玉*/
	public synchronized void addX(long add,int type){
		if (type==0) {
			add_X_CZ+=add;
		}else if (type==1) {
			add_X_VIP+=add;
		}else if (type==3){
			add_C_CZ+=add;
		}else {
			add_X_Other+=add;
		}
	}
	/**添加增加大话币*/
	public synchronized void addD(long add,int type){
		if (type==0) {
			add_D_Robot+=add;
		}else if (type==1) {
			add_D_Task+=add;
		}else if (type==2) {
			add_D_GGL+=add;
		}else {
			add_D_Other+=add;
		}
	}
	/**添加增加充值积分*/
	public synchronized void addC(long add){
		add_C_CZ+=add;
	}
	/**消耗仙玉*/
	public synchronized void useX(long use){
		use_X+=use;
	}
	/**消耗大话币*/
	public synchronized void useD(long use){
		use_D+=use;
	}
	/**消耗充值积分*/
	public synchronized void useC(long use){
		use_C+=use;
	}
	public long getAdd_X_CZ() {
		return add_X_CZ;
	}
	public void setAdd_X_CZ(long add_X_CZ) {
		this.add_X_CZ = add_X_CZ;
	}
	public long getAdd_X_VIP() {
		return add_X_VIP;
	}
	public void setAdd_X_VIP(long add_X_VIP) {
		this.add_X_VIP = add_X_VIP;
	}
	public long getAdd_X_Other() {
		return add_X_Other;
	}
	public void setAdd_X_Other(long add_X_Other) {
		this.add_X_Other = add_X_Other;
	}
	public long getAdd_D_Robot() {
		return add_D_Robot;
	}
	public void setAdd_D_Robot(long add_D_Robot) {
		this.add_D_Robot = add_D_Robot;
	}
	public long getAdd_D_Task() {
		return add_D_Task;
	}
	public void setAdd_D_Task(long add_D_Task) {
		this.add_D_Task = add_D_Task;
	}
	public long getAdd_D_GGL() {
		return add_D_GGL;
	}
	public void setAdd_D_GGL(long add_D_GGL) {
		this.add_D_GGL = add_D_GGL;
	}
	public long getAdd_D_Other() {
		return add_D_Other;
	}
	public void setAdd_D_Other(long add_D_Other) {
		this.add_D_Other = add_D_Other;
	}
	public long getAdd_C_CZ() {
		return add_C_CZ;
	}
	public void setAdd_C_CZ(long add_C_CZ) {
		this.add_C_CZ = add_C_CZ;
	}
	public long getUse_X() {
		return use_X;
	}
	public void setUse_X(long use_X) {
		this.use_X = use_X;
	}
	public long getUse_D() {
		return use_D;
	}
	public void setUse_D(long use_D) {
		this.use_D = use_D;
	}
	public long getUse_C() {
		return use_C;
	}
	public void setUse_C(long use_C) {
		this.use_C = use_C;
	}	
}
