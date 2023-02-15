package come.tool.Scene.DNTG;
/**倒计时*/
public class DNTGCountDown {
     
	private int  type;//0是女武神倒计时  1是上古战场倒计时
	private long endTime;//倒计时结束时间
	public DNTGCountDown(int type,int time) {
		super();
		this.type = type;
		this.endTime=System.currentTimeMillis()+(time*1000*60);
	}
	public void toString(StringBuffer buffer){
    	buffer.append(type);
    	buffer.append("$");
    	buffer.append(endTime);
    }
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
}
