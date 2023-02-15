package come.tool.Scene.ZZS;

public class ZZSAward {

	//类型 1-5参与奖    2-5胜奖  3-3连胜奖    4-(10强)奖励  5-冠军奖励
	private int type;
	//奖励
	private boolean isAward;
	
	public ZZSAward(int type) {
		super();
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isAward() {
		return isAward;
	}
	public void setAward(boolean isAward) {
		this.isAward = isAward;
	}
	
}
