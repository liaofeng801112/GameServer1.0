package come.tool.Scene.TGDB;

import org.come.task.MapMonsterBean;

public class TGDBMonster{

	private MapMonsterBean bean;
	//记录怪物层次
	private int I;
	//记录剩余击杀次数
	private int hp;
	//判断当前正在击杀的队伍数量
	private int num;
	public MapMonsterBean getBean() {
		return bean;
	}
	public void setBean(MapMonsterBean bean) {
		this.bean = bean;
	}
	public int getI() {
		return I;
	}
	public void setI(int i) {
		I = i;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
