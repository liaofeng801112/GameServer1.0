package org.come.entity;

import java.math.BigDecimal;
import java.util.List;
/**
 * 坐骑表
 * @author 叶豪芳
 * @date 2017年11月27日 下午7:28:40
 * 
 */ 
public class Mount implements Cloneable{
	// 表ID
	private BigDecimal mid;
	// 坐骑ID
	private Integer mountid;
	// 坐骑名称
	private String mountname;
	// 坐骑等级
	private Integer mountlvl;
	// 体力
	private Integer live;
	// 灵性
	private Integer spri;
	// 力量
	private Integer power;
	// 根骨
	private Integer bone;
	// 经验
	private Integer exp;
	// 角色ID
	private BigDecimal roleid;
	// 管制的召唤兽
	private BigDecimal sid;
	// 管制的召唤兽  
	private BigDecimal othrersid;
	// 升级所需经验
	private Integer gradeexp;
	// 坐骑技能
	List<MountSkill> mountskill;
	// 使用筋骨提气丹的次数
	private Integer useNumber;
	// 熟练度
	private Integer Proficiency;
	// 管制的召唤兽
	private BigDecimal sid3;
	public BigDecimal getMid() {
		return mid;
	}
	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}
	public Integer getMountid() {
		return mountid;
	}
	public void setMountid(Integer mountid) {
		this.mountid = mountid;
	}
	public String getMountname() {
		return mountname;
	}
	public void setMountname(String mountname) {
		this.mountname = mountname;
	}
	public Integer getMountlvl() {
		return mountlvl;
	}
	public void setMountlvl(Integer mountlvl) {
		this.mountlvl = mountlvl;
	}
	public Integer getLive() {
		return live;
	}
	public void setLive(Integer live) {
		this.live = live;
	}
	public Integer getSpri() {
		return spri;
	}
	public void setSpri(Integer spri) {
		this.spri = spri;
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public Integer getBone() {
		return bone;
	}
	public void setBone(Integer bone) {
		this.bone = bone;
	}
	public Integer getExp() {
		return exp;
	}
	public void setExp(Integer exp) {
		this.exp = exp;
	}
	public BigDecimal getRoleid() {
		return roleid;
	}
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
	public BigDecimal getSid() {
		return sid;
	}
	public void setSid(BigDecimal sid) {
		this.sid = sid;
	}
	public BigDecimal getOthrersid() {
		return othrersid;
	}
	public void setOthrersid(BigDecimal othrersid) {
		this.othrersid = othrersid;
	}
	public Integer getGradeexp() {
		return gradeexp;
	}
	public void setGradeexp(Integer gradeexp) {
		this.gradeexp = gradeexp;
	}
	public List<MountSkill> getMountskill() {
		return mountskill;
	}
	public void setMountskill(List<MountSkill> mountskill) {
		this.mountskill = mountskill;
	}
	public Integer getUseNumber() {
		return useNumber;
	}
	public void setUseNumber(Integer useNumber) {
		this.useNumber = useNumber;
	}
	public Integer getProficiency() {
		return Proficiency;
	}
	public void setProficiency(Integer proficiency) {
		Proficiency = proficiency;
	}
	public BigDecimal getSid3() {
		return sid3;
	}
	public void setSid3(BigDecimal sid3) {
		this.sid3 = sid3;
	}
	@Override
	public Mount clone(){
		try {
			return (Mount) super.clone();		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}