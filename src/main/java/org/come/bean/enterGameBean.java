package org.come.bean;

import java.util.List;

import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.PackRecord;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;

import come.tool.Role.PrivateData;
import come.tool.Role.RoleShow;
import come.tool.Role.RoleSystem;
import come.tool.Stall.Stall;
import come.tool.Stall.StallBean;

public class enterGameBean {
	// 旧角色信息
	private LoginResult loginResult;
	//私密数据
	private PrivateData privateData;
	private List<RoleShow> roleShows;
	// 物品信息
	private List<Goodstable> list;
	// 召唤兽列表
	private List<RoleSummoning> roleSummonings;
	// 坐骑列表
	private List<Mount> mounts;
	//怪物集合
	private String monster;
	//灵宝集合
	private List<Lingbao> lingbaos;
	//宝宝集合
	private List<Baby> babys;
	//伙伴集合
	private List<Pal> pals;
	// 记录摆摊
	private List<StallBean> stallBeans;
	// 记录玩家的摆摊的数据
	private Stall stall;
	// 背包记忆
	private PackRecord packRecord;
	//玩家系统设置
	private RoleSystem roleSystem;
	//副本数据
	private String sceneMsg;
	public String getSceneMsg() {
		return sceneMsg;
	}
	public void setSceneMsg(String sceneMsg) {
		this.sceneMsg = sceneMsg;
	}
	public List<RoleShow> getRoleShows() {
		return roleShows;
	}
	public void setRoleShows(List<RoleShow> roleShows) {
		this.roleShows = roleShows;
	}
	public List<Goodstable> getList() {
		return list;
	}
	public void setList(List<Goodstable> list) {
		this.list = list;
	}
	public List<RoleSummoning> getRoleSummonings() {
		return roleSummonings;
	}
	public void setRoleSummonings(List<RoleSummoning> roleSummonings) {
		this.roleSummonings = roleSummonings;
	}
	public List<Mount> getMounts() {
		return mounts;
	}
	public void setMounts(List<Mount> mounts) {
		this.mounts = mounts;
	}
	
	public String getMonster() {
		return monster;
	}
	public void setMonster(String monster) {
		this.monster = monster;
	}
	public List<Lingbao> getLingbaos() {
		return lingbaos;
	}
	public void setLingbaos(List<Lingbao> lingbaos) {
		this.lingbaos = lingbaos;
	}
	public List<Baby> getBabys() {
		return babys;
	}
	public void setBabys(List<Baby> babys) {
		this.babys = babys;
	}
	public List<StallBean> getStallBeans() {
		return stallBeans;
	}
	public void setStallBeans(List<StallBean> stallBeans) {
		this.stallBeans = stallBeans;
	}
	public Stall getStall() {
		return stall;
	}
	public void setStall(Stall stall) {
		this.stall = stall;
	}
	public PackRecord getPackRecord() {
		return packRecord;
	}
	public void setPackRecord(PackRecord packRecord) {
		this.packRecord = packRecord;
	}
	public RoleSystem getRoleSystem() {
		return roleSystem;
	}
	public void setRoleSystem(RoleSystem roleSystem) {
		this.roleSystem = roleSystem;
	}
	public LoginResult getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(LoginResult loginResult) {
		this.loginResult = loginResult;
	}
	public PrivateData getPrivateData() {
		return privateData;
	}
	public void setPrivateData(PrivateData privateData) {
		this.privateData = privateData;
	}
	public List<Pal> getPals() {
		return pals;
	}
	public void setPals(List<Pal> pals) {
		this.pals = pals;
	}
}
