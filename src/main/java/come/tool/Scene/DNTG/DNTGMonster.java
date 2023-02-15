package come.tool.Scene.DNTG;

import org.come.task.MapMonsterBean;

public class DNTGMonster {

	
	private MapMonsterBean bean;
	//阵营
	private int camp;
	//攻击的对象
	private int value;
	private int hurt;//伤害
	public DNTGMonster(MapMonsterBean bean,int camp,int value,int hurt) {
		super();
		this.bean = bean;
		this.camp=camp;
		this.value=value;
		this.hurt=hurt;
	}
	/**移动*/
	public boolean move(){
		if (bean.getType()!=0||bean.getMove()==null) {return false;}
		return bean.getMove().isMove(1000);
	}
	public MapMonsterBean getBean() {
		return bean;
	}
	public void setBean(MapMonsterBean bean) {
		this.bean = bean;
	}
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
		this.camp = camp;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getHurt() {
		return hurt;
	}
	public void setHurt(int hurt) {
		this.hurt = hurt;
	}
}
