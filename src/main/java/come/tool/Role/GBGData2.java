package come.tool.Role;

import java.math.BigDecimal;

import come.tool.FightingData.ManData;
import come.tool.FightingData.Ql;

public class GBGData2 {
	
	private BigDecimal hp;//气血
	private BigDecimal mp;//魔法
	private BigDecimal ap;//攻击
	private BigDecimal sp;//速度
	private BigDecimal cd;//禅定
	
	private BigDecimal gg;//根骨
	private BigDecimal lx;//灵性
	private BigDecimal ll;//力量
	private BigDecimal mj;//敏捷
	private BigDecimal dl;//定力
	
	private Ql ql;//抗性字段

	public GBGData2(ManData manData) {
		// TODO Auto-generated constructor stub
		this.hp=new BigDecimal(manData.getHp_z());
		this.mp=new BigDecimal(manData.getMp_z());
		this.ap=new BigDecimal(manData.huoAp());
		this.sp=new BigDecimal(manData.getSp2());
		this.cd=new BigDecimal(manData.getQihe());
		
		this.gg=new BigDecimal(manData.getHuoyue());
		this.lx=new BigDecimal(manData.getShanghai());
		this.ll=new BigDecimal(manData.getKangluobao());
		this.mj=new BigDecimal(manData.getYuanzhu());
		this.dl=cd;
		this.ql=manData.getQuality();
	}
	public BigDecimal getHp() {
		return hp;
	}
	public void setHp(BigDecimal hp) {
		this.hp = hp;
	}
	public BigDecimal getMp() {
		return mp;
	}
	public void setMp(BigDecimal mp) {
		this.mp = mp;
	}
	public BigDecimal getAp() {
		return ap;
	}
	public void setAp(BigDecimal ap) {
		this.ap = ap;
	}
	public BigDecimal getSp() {
		return sp;
	}
	public void setSp(BigDecimal sp) {
		this.sp = sp;
	}
	public BigDecimal getCd() {
		return cd;
	}
	public void setCd(BigDecimal cd) {
		this.cd = cd;
	}
	public BigDecimal getGg() {
		return gg;
	}
	public void setGg(BigDecimal gg) {
		this.gg = gg;
	}
	public BigDecimal getLx() {
		return lx;
	}
	public void setLx(BigDecimal lx) {
		this.lx = lx;
	}
	public BigDecimal getLl() {
		return ll;
	}
	public void setLl(BigDecimal ll) {
		this.ll = ll;
	}
	public BigDecimal getMj() {
		return mj;
	}
	public void setMj(BigDecimal mj) {
		this.mj = mj;
	}
	public BigDecimal getDl() {
		return dl;
	}
	public void setDl(BigDecimal dl) {
		this.dl = dl;
	}
	public Ql getQl() {
		return ql;
	}
	public void setQl(Ql ql) {
		this.ql = ql;
	}
	
}
