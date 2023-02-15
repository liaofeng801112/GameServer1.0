package come.tool.Scene.DNTG;

public class DNTG_SG {
	
	private long endTime;//倒计时结束时间
	private int max;
	private int TT_GX;
	private int HGS_GX;
	
	public DNTG_SG() {
		super();
		this.endTime=System.currentTimeMillis()+30*60*1000;
		this.max=1000;
	}
	public boolean isEnd(){
		return TT_GX>=max||HGS_GX>=max;
	}
	public int getEnd(){
		if (TT_GX>HGS_GX) {
			return 0;
		}else if (HGS_GX>TT_GX) {
			return 1;
		}
		return -1;
	}
	public void toString(StringBuffer buffer){
		buffer.append("|S2");
		buffer.append(endTime);
		buffer.append("&");
		buffer.append(max);
		buffer.append("|S0");
		buffer.append(TT_GX);
		buffer.append("|S1");
		buffer.append(HGS_GX);
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getTT_GX() {
		return TT_GX;
	}
	public void setTT_GX(int tT_GX) {
		TT_GX = tT_GX;
	}
	public int getHGS_GX() {
		return HGS_GX;
	}
	public void setHGS_GX(int hGS_GX) {
		HGS_GX = hGS_GX;
	}
	
}
