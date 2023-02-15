package come.tool.Calculation;

public class BaseSuit {

	private int suitId;
	
	private int lvl;

	private int sum;
	
	
	/**根据id获取对应的属性*/
    public static String getsuitSkill(int id){
    	if (id==6001) {return "加强封印";}
    	else if (id==6002) {return "加强昏睡";}
    	else if (id==6003) {return "加强遗忘";}
    	else if (id==6004) {return "加强混乱";}
    	else if (id==6005) {return "加强风法";}
    	else if (id==6006||id==6007) {return "加强法术";}
    	else if (id==6008) {return "加强火法";}
    	else if (id==6009) {return "加强鬼火";}
    	else if (id==6010) {return "忽视抗火";}
    	else if (id==6011) {return "忽视抗混";}
    	else if (id==6012) {return "忽视抗风";}
    	else if (id==6013) {return "忽视抗封";}
    	else if (id==6014) {return "忽视抗睡";}
    	else if (id==6015) {return "忽视遗忘";}
    	else if (id==6016) {return "加强震慑";}
    	else if (id==6017) {return "提抗上限";}
    	else if (id==6030) {return "加强速度法术效果";}
    	else if (id==6031) {return "水魔附身";}
    	else if (id==6032) {return "加强三尸虫";}
    	else if (id==6035) {return "加强防御法术效果";}
    	else if (id==6036) {return "加强防御法术效果";}
    	else if (id==6039) {return "加强攻击法术效果";}
		return null;	
    }
    /**根据id获取对应的属性值*/
    public static double getSuitValue(int id,int lvl){
    	if (id==6016) {
			return 1.5+lvl*0.5;
		}if (id==6017) {
			return 5+lvl;
		}else if ((id>=6001&&id<=6009)||id==6030||id==6035||id==6036||id==6039) {
			return 3+lvl;
		}else if (id==6031) {
			return lvl;
		}else if (id==6032) {
			return 100+lvl*100;
		}else {
			return 0.5+lvl*0.5;
		}
    }
	
	public BaseSuit(int suitId, int lvl, int sum) {
		super();
		this.suitId = suitId;
		this.lvl = lvl;
		this.sum = sum;
	}

	public BaseSuit(int suitId, int lvl) {
		super();
		this.suitId = suitId;
		this.lvl = lvl;
	}

	public int getSuitId() {
		return suitId;
	}

	public void setSuitId(int suitId) {
		this.suitId = suitId;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
	
}
