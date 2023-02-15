package come.tool.Scene.BTY;

import org.come.task.MapMonsterBean;

public class BTYMonster{
	
	private int CI;
	private MapMonsterBean bean;
	public BTYMonster(MapMonsterBean bean,int CI) {
		super();
		this.bean = bean;
		this.CI=CI;
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
	public int getCI() {
		return CI;
	}
	public void setCI(int cI) {
		CI = cI;
	}
}
