package come.tool.Calculation;

public class BaseLimit {

	private int lm;//力量要求
	private int lx;//灵性要求
	private int gg;//根骨要求
	private int mj;//敏捷要求
	private double xs;//属性需求   -999是无属性
	private int zs;//最小转生
	private int lvl;//最小等级
	
	private int zsMax;//最大转生
	private int lvlMax;//最大等级
	private boolean isL;//true表示无级别
	private int sex;//0女 1男 2无性别
	
	public BaseLimit() {
		// TODO Auto-generated constructor stub
		this.xs=100;
		this.zsMax=4;
		this.lvlMax=200;
		this.isL=false;
		this.sex=2;
	}
	
	public BaseLimit(int lm, int lx, int gg, int mj, double xs, int zs,int lvl, int zsMax, int lvlMax, boolean isL, int sex) {
		super();
		this.lm = lm;
		this.lx = lx;
		this.gg = gg;
		this.mj = mj;
		this.xs = xs;
		this.zs = zs;
		this.lvl = lvl;
		this.zsMax = zsMax;
		this.lvlMax = lvlMax;
		this.isL = isL;
		this.sex = sex;
	}
	public int getLm() {
		return lm;
	}
	public void setLm(int lm) {
		this.lm = lm;
	}
	public int getLx() {
		return lx;
	}
	public void setLx(int lx) {
		this.lx = lx;
	}
	public int getGg() {
		return gg;
	}
	public void setGg(int gg) {
		this.gg = gg;
	}
	public int getMj() {
		return mj;
	}
	public void setMj(int mj) {
		this.mj = mj;
	}
	public double getXs() {
		return xs;
	}
	public void setXs(double xs) {
		this.xs = xs;
	}
	public int getZs() {
		return zs;
	}
	public void setZs(int zs) {
		this.zs = zs;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getZsMax() {
		return zsMax;
	}
	public void setZsMax(int zsMax) {
		this.zsMax = zsMax;
	}
	public int getLvlMax() {
		return lvlMax;
	}
	public void setLvlMax(int lvlMax) {
		this.lvlMax = lvlMax;
	}
	public boolean isL() {
		return isL;
	}
	public void setL(boolean isL) {
		this.isL = isL;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
}
