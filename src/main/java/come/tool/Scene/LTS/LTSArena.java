package come.tool.Scene.LTS;

import come.tool.PK.PkMatch;

/**擂台*/
public class LTSArena {

	//擂台id
	private int lId;
	//擂台等级
	private int lLvl;
	//擂台战斗编号
	private int battleNumber;
	private PkMatch pkMatch;
	private int minLvl;
	private int maxLvl;
//  我要下战书
//  我要取消战书(不退押金)
//	我要应战
//  我要观战
//	500-505
//	120-159  160-180  飞升 
	public LTSArena(int lId) {
		super();
		this.lId = lId;
//		399-438  439-459 460-520
		if (this.lId==510||this.lId==511) {//低级擂台
			this.minLvl=399;
			this.maxLvl=438;
			this.lLvl=1;
		}else if (this.lId==512||this.lId==513) {//中级擂台
			this.minLvl=439;
			this.maxLvl=459;
			this.lLvl=2;
		}else {//高级擂台
			this.minLvl=460;
			this.maxLvl=520;
			this.lLvl=3;
		}
	}	
	/**判断等级*/
	public boolean isLvl(int lvl){
		if (lvl>=minLvl&&lvl<=maxLvl) {
			return true;
		}
		return false;
	}
	public int getlId() {
		return lId;
	}
	public void setlId(int lId) {
		this.lId = lId;
	}
	public int getBattleNumber() {
		return battleNumber;
	}
	public void setBattleNumber(int battleNumber) {
		this.battleNumber = battleNumber;
	}
	public PkMatch getPkMatch() {
		return pkMatch;
	}
	public void setPkMatch(PkMatch pkMatch) {
		this.pkMatch = pkMatch;
	}
	public int getMinLvl() {
		return minLvl;
	}
	public void setMinLvl(int minLvl) {
		this.minLvl = minLvl;
	}
	public int getMaxLvl() {
		return maxLvl;
	}
	public void setMaxLvl(int maxLvl) {
		this.maxLvl = maxLvl;
	}
	public int getlLvl() {
		return lLvl;
	}
	public void setlLvl(int lLvl) {
		this.lLvl = lLvl;
	}
}
