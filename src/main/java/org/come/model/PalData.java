package org.come.model;

import java.util.List;

import come.tool.Calculation.PalQl;
import come.tool.FightingData.AI;

/**伙伴*/
public class PalData {

	private int palId;//id
	private String name;//名称
	private String skin;//皮肤
	private String trait;//特征
	private transient int sex;//性别
	private int hp;//基础hp
	private int mp;//基础mp
	private int ap;//基础ap
	private int sp;//基础sp
	private String kx;//抗性                      抗性=初始抗性=每级添加
	private transient String jd;//加点方案               H=1|M=1|A=1|S=1
	private String skill;//法术                技能id|技能id
	private String xh;//激活消耗               D15000 大话币15000 X15000 仙玉15000
	private transient String ai;//智能
	
	private transient PalQl[] qls;
	private transient String[] skills;
	private int[] jds;
	private transient int size;
	private transient List<AI> ais;// AI;
	
	public int getPalId() {
		return palId;
	}
	public void setPalId(int palId) {
		this.palId = palId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMp() {
		return mp;
	}
	public void setMp(int mp) {
		this.mp = mp;
	}
	public int getAp() {
		return ap;
	}
	public void setAp(int ap) {
		this.ap = ap;
	}
	public int getSp() {
		return sp;
	}
	public void setSp(int sp) {
		this.sp = sp;
	}
	public String getKx() {
		return kx;
	}
	public void setKx(String kx) {
		this.kx = kx;
	}
	public String getJd() {
		return jd;
	}
	public void setJd(String jd) {
		this.jd = jd;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getAi() {
		return ai;
	}
	public void setAi(String ai) {
		this.ai = ai;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public PalQl[] getQls() {
		return qls;
	}
	public void setQls(PalQl[] qls) {
		this.qls = qls;
	}
	public int[] getJds() {
		return jds;
	}
	public void setJds(int[] jds) {
		this.jds = jds;
		if (jds!=null) {
			this.size=0;
			for (int i = 0; i < jds.length; i++) {
				this.size+=jds[i];
			}
		}
	}
	public int getSize() {
		return size;
	}
	public String[] getSkills() {
		return skills;
	}
	public void setSkills(String[] skills) {
		this.skills = skills;
	}
	public List<AI> getAis() {
		return ais;
	}
	public void setAis(List<AI> ais) {
		this.ais = ais;
	}
	public String getTrait() {
		return trait;
	}
	public void setTrait(String trait) {
		this.trait = trait;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
}
