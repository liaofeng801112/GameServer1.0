package come.tool.teamArena;

import java.math.BigDecimal;

public class TeamArenaMatch {
	private int JF;//平均积分
	private long time;//开始确认的时间点 or 开始匹配的时间点 or  确认匹配的倒计时时间点
	private BigDecimal[] ids;//同意的id集合
	private int state;//所处状态  0 同意阶段   1 匹配阶段  2准备阶段  3战斗阶段  4结束
	public TeamArenaMatch(int size) {
		// TODO Auto-generated constructor stub
		ids=new BigDecimal[size];
		time=System.currentTimeMillis();
	}
	/**0标识添加失败(重复添加也是失败)  1标识添加成功 2标识添加成功且全部同意*/
	public int isIds(BigDecimal id){
		synchronized (this) {
			for (int i = 0; i < ids.length; i++) {
				if (ids[i]==null) {
					ids[i]=id;
					return (i+1==ids.length)?2:1;
				}else if (ids[i].compareTo(id)==0) {
					return 0;	
				}
			}
			return 0;	
		}
	}
	public int getJF() {
		return JF;
	}

	public void setJF(int jF) {
		JF = jF;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public BigDecimal[] getIds() {
		return ids;
	}

	public void setIds(BigDecimal[] ids) {
		this.ids = ids;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
