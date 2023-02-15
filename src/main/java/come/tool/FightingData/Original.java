package come.tool.FightingData;

/**原始的战斗数据*/
public class Original {

	private int hp_z;
	private double hpxs;
	private int mp_z;
	private double mpxs;
	private String model;
	
	public Original(int hp_z, int mp_z, String model) {
		super();
		this.hp_z = hp_z;
		this.mp_z = mp_z;
		this.model = model;
		this.hpxs=1D;
		this.mpxs=1D;
	}
	public double upXS(int type,double xs){
		if (type==0) {return hpxs+=xs;}
		else if (type==1) {return mpxs+=xs;}
		return 1D;
	}
	public int getHp_z() {
		return hp_z;
	}
	public void setHp_z(int hp_z) {
		this.hp_z = hp_z;
	}
	public int getMp_z() {
		return mp_z;
	}
	public void setMp_z(int mp_z) {
		this.mp_z = mp_z;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
}
