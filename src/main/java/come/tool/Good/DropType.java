package come.tool.Good;

import org.come.action.lottery.DrawBase;

public class DropType {
	public static final int SCORE          = 1;//积分=xxx
	public static final int RECORD         = 2;//记录=xxx
	public static final int KILL           = 3;//击杀xx=xxx
	public static final int GOOD           = 4;//物品=x
	public static final int BOOS           = 5;//放妖=x
	public static final int TITLE          = 6;//称谓=x
	public static final int GANG           = 7;//帮贡=xxx
	
	public static final int DEATHBOOS      =11;//死亡放妖=xxx
	public static final int ADDEXP         =12;//经验累加=xxx
	
	private int dropType;
	private String key;
	private Integer value;
	private DropGood dropGood;
	public DropType(int dropType,String value) {
		this.dropType = dropType;
		String[] goods = value.split("&");
		dropGood=new DropGood();
		dropGood.setEmpty(Double.parseDouble(goods[0]));
		DrawBase[] drawBases=new DrawBase[goods.length-1];
		for (int i = 0; i < goods.length-1; i++) {
		    String[] canGetGoods = goods[i+1].split("\\$");
			drawBases[i]=new DrawBase(); 
			if (canGetGoods.length==3) {
				drawBases[i].setV(Double.parseDouble(canGetGoods[2]));
				drawBases[i].setSum(Integer.parseInt(canGetGoods[1]));
			}else {
				drawBases[i].setV(0);
				drawBases[i].setSum(1);
			}
			String[] getGoods = canGetGoods[0].split("-");
			if (this.dropType!=BOOS) {
				int[] ids=new int[getGoods.length];
				for (int k = 0; k < ids.length; k++) {
					ids[k]=Integer.parseInt(getGoods[k]);
				}
				drawBases[i].setIds(ids);	
			}else {
				drawBases[i].setIdBooss(getGoods);
			}
		}
		dropGood.setDraws(drawBases);
	}
	public DropType(int dropType, String key, Integer value) {
		this.dropType = dropType;
		this.key = key;
		this.value = value;
	}
	public DropType(int dropType, Integer value) {
		this.dropType = dropType;
		this.value = value;
	}
	public int getDropType() {
		return dropType;
	}
	public void setDropType(int dropType) {
		this.dropType = dropType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public DropGood getDropGood() {
		return dropGood;
	}
	public void setDropGood(DropGood dropGood) {
		this.dropGood = dropGood;
	}
	
}
