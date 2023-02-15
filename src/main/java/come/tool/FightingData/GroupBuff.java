package come.tool.FightingData;
//群体buff
public class GroupBuff {
	//buffid
	private int buffId;
	private String buffType;
	//buff所属者
	private ManData data;
	private int camp;
	//buff值
	private double value;
	//buff值2
	private double value2;
	public GroupBuff(int buffId,String buffType, ManData data, double value) {
		super();
		this.buffId = buffId;
		this.buffType = buffType;
		this.data=data;
		this.value = value;
		this.camp=data.getCamp();
	}
	
	public int getBuffId() {
		return buffId;
	}

	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}

	public String getBuffType() {
		return buffType;
	}
	public void setBuffType(String buffType) {
		this.buffType = buffType;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
		this.camp=camp;
	}
	public ManData getData() {
		return data;
	}
	public void setData(ManData data) {
		this.data = data;
	}
	public double getValue2() {
		return value2;
	}
	public void setValue2(double value2) {
		this.value2 = value2;
	}	
}
