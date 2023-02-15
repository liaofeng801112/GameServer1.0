package come.tool.Good;

import org.come.action.lottery.DrawBase;

public class DropGood {

	private double empty;//空的概率
	private DrawBase[] draws;//掉了
	public double getEmpty() {
		return empty;
	}
	public void setEmpty(double empty) {
		this.empty = empty;
	}
	public DrawBase[] getDraws() {
		return draws;
	}
	public void setDraws(DrawBase[] draws) {
		this.draws = draws;
	}
	
}
