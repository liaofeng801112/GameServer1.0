package org.come.entity;

import java.math.BigDecimal;

// 宝宝属性
public class Baby {
    //表id
	private BigDecimal babyid;
	//角色id
	private BigDecimal roleid;
	//宝宝名
	private String babyname;
	//亲密
	private Integer qizhi;
	//内力
	private Integer neili;
	//智力
	private Integer zhili;
	//耐力
	private Integer naili;
	//名气
	private Integer mingqi;
	//道德
	private Integer daode;
	//叛逆
	private Integer panni;
	//玩性
	private Integer wanxing;
	//亲密
	private Integer qingmi;
	//孝心
	private Integer xiaoxin;
	//温饱
	private Integer wenbao;
	//疲劳
	private Integer pilao;
	//养育金
	private Integer yangyujin;
	//性别
	private Integer childSex;//0:男  1:女
	//宝宝年龄  单位日    培养一次增加10天 面板展示 年 月
	private Integer babyage;
	//结局
	private String outcome;
	//天资   id|id|id
	private String Talents;
	//装备的id(衣服|帽子|鞋子|武器)-1|-1|-1|-1
	private String parts;
	/**
	 * 获取装备id
	 * @return
	 */
	public BigDecimal getpart(int type){
		String[] v=getParts().split("\\|");
		if (type<v.length)
		return new BigDecimal(v[type]);
		return new BigDecimal(-1);
	}
	/**
	 * 获取所有装备id
	 * @return
	 */
	public BigDecimal[] getpartAll(){
		BigDecimal[] bigs=new BigDecimal[4];
		String[] v=getParts().split("\\|");
		for (int i = 0; i < 4; i++) {
			if (i<v.length)bigs[i]=new BigDecimal(v[i]);
			else bigs[i]=new BigDecimal(-1);	
		}
		return bigs;
	}
	/**
	 * 装备id替换
	 * 返回替换前的id
	 * @return
	 */
	public BigDecimal ChangePart(BigDecimal id,int type){
		BigDecimal[] bigs=getpartAll();
		BigDecimal yid=bigs[type];
		bigs[type]=id;
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < bigs.length; i++) {
			if (i!=0)buffer.append("|");
			buffer.append(bigs[i]);
		}
		this.parts=buffer.toString();
		return yid;
	}
	/**
	 * 替换制定位置的天资
	 * @return
	 */
	public boolean ChangeTalent(int type,String talent){
		String[] v=getTalents().split("\\|");
		for (int i = 0; i < v.length; i++) {
		     if (v[i].split("=")[0].equals(talent)) {
				return false;
			}	
		}
		v[type]=talent+"=1";
		StringBuffer buffer=new StringBuffer();
		buffer.append(v[0]);
		buffer.append("|");
		buffer.append(v[1]);
		buffer.append("|");
		buffer.append(v[2]);
		Talents=buffer.toString();
		return true;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public String getTalents() {
		return Talents;
	}
	public void setTalents(String talents) {
		Talents = talents;
	}
	public String getParts() {
		if (parts==null||parts.equals(""))parts="-1|-1|-1|-1";
		return parts;
	}
	public void setParts(String parts) {
		this.parts = parts;
	}
	public BigDecimal getBabyid() {
		return babyid;
	}
	public void setBabyid(BigDecimal babyid) {
		this.babyid = babyid;
	}
	public String getBabyname() {
		return babyname;
	}
	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}
	public Integer getQizhi() {
		return qizhi;
	}
	public void setQizhi(Integer qizhi) {
		this.qizhi = qizhi;
	}
	public Integer getNeili() {
		return neili;
	}
	public void setNeili(Integer neili) {
		this.neili = neili;
	}
	public Integer getZhili() {
		return zhili;
	}
	public void setZhili(Integer zhili) {
		this.zhili = zhili;
	}
	public Integer getNaili() {
		return naili;
	}
	public void setNaili(Integer naili) {
		this.naili = naili;
	}
	public Integer getMingqi() {
		return mingqi;
	}
	public void setMingqi(Integer mingqi) {
		this.mingqi = mingqi;
	}
	public Integer getDaode() {
		return daode;
	}
	public void setDaode(Integer daode) {
		this.daode = daode;
	}
	public Integer getPanni() {
		return panni;
	}
	public void setPanni(Integer panni) {
		this.panni = panni;
	}
	public Integer getWanxing() {
		return wanxing;
	}
	public void setWanxing(Integer wanxing) {
		this.wanxing = wanxing;
	}
	public Integer getQingmi() {
		return qingmi;
	}
	public void setQingmi(Integer qingmi) {
		this.qingmi = qingmi;
	}
	public Integer getXiaoxin() {
		return xiaoxin;
	}
	public void setXiaoxin(Integer xiaoxin) {
		this.xiaoxin = xiaoxin;
	}
	public Integer getWenbao() {
		return wenbao;
	}
	public void setWenbao(Integer wenbao) {
		this.wenbao = wenbao;
	}
	public Integer getPilao() {
		return pilao;
	}
	public void setPilao(Integer pilao) {
		this.pilao = pilao;
	}
	public Integer getYangyujin() {
		return yangyujin;
	}
	public void setYangyujin(Integer yangyujin) {
		this.yangyujin = yangyujin;
	}
	public BigDecimal getRoleid() {
		return roleid;
	}
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
	public Integer getChildSex() {
		return childSex;
	}
	public void setChildSex(Integer childSex) {
		this.childSex = childSex;
	}
	public Integer getBabyage() {
		return babyage;
	}
	public void setBabyage(Integer babyage) {
		this.babyage = babyage;
	}
	
}
