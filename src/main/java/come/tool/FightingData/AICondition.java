package come.tool.FightingData;

public class AICondition {
	private int x;
	private int y;
	private String sy;
	public AICondition() {
		// TODO Auto-generated constructor stub
	}
	public AICondition(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public AICondition(int x, String sy) {
		super();
		this.x = x;
		this.sy = sy;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getSy() {
		return sy;
	}
	public void setSy(String sy) {
		this.sy = sy;
	}
}
