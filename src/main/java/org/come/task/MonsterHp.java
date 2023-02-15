package org.come.task;

public class MonsterHp {

	private int hp;//当前生命值
	private int hpMax;//最大生命值
	private boolean isMuch;//true表示多人同时挑战   false 单人挑战
	public MonsterHp() {
		// TODO Auto-generated constructor stub
	}
	public MonsterHp(int hp,boolean isMuch) {
		super();
		this.hp = hp;
		this.hpMax = hp;
		this.isMuch = isMuch;
	}
	/***/
	public void addHp(int add){
		hp+=add;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getHpMax() {
		return hpMax;
	}
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}
	public boolean isMuch() {
		return isMuch;
	}
	public void setMuch(boolean isMuch) {
		this.isMuch = isMuch;
	}
	
	
	
}
