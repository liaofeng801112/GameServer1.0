package org.come.bean;
/**
 * 附加状态
 * @author Administrator
 *
 */
public class AddState {

	//附加状态名
	private String statename;
	//状态效果
	private double stateEffect;
	//持续到的回合
	private int Surplus;

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public int getSurplus() {
		return Surplus;
	}

	public void setSurplus(int surplus) {
		Surplus = surplus;
	}

	public double getStateEffect() {
		return stateEffect;
	}

	public void setStateEffect(double stateEffect) {
		this.stateEffect = stateEffect;
	}


	
	
}
