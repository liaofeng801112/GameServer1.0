package org.come.bean;

import java.util.List;

import org.come.entity.Gang;
import org.come.entity.Gangapplytable;

import come.tool.newGang.GangGroup;
/**
 * 返回帮派
 * @author 叶豪芳
 * @date : 2017年11月30日 下午2:36:42
 */

public class GangResultBean {
	// 帮派
	private Gang gang;
	// 帮派建设情况
	private GangGroup gangGroup;
	// 帮派成员
	private List<LoginResult> roleTables;
	// 帮派申请表
	private List<Gangapplytable> gangapplytables;
	public Gang getGang() {
		return gang;
	}
	public void setGang(Gang gang) {
		this.gang = gang;
	}
	public GangGroup getGangGroup() {
		return gangGroup;
	}
	public void setGangGroup(GangGroup gangGroup) {
		this.gangGroup = gangGroup;
	}
	public List<LoginResult> getRoleTables() {
		return roleTables;
	}
	public void setRoleTables(List<LoginResult> roleTables) {
		this.roleTables = roleTables;
	}
	public List<Gangapplytable> getGangapplytables() {
		return gangapplytables;
	}
	public void setGangapplytables(List<Gangapplytable> gangapplytables) {
		this.gangapplytables = gangapplytables;
	}
}
