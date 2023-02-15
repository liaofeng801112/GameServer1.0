package org.come.bean;
/**
 * 同意交易时bean
 * @author 叶豪芳
 * @date 2017年12月23日 下午2:54:08
 * 
 */ 
public class TransAgreeBean {
	// 对方角色名
	private String rolename;
	
	// 背包所剩格数
	private Integer packNumber;
	
	// 所剩宠物列表个数
	private Integer petNumber;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Integer getPackNumber() {
		return packNumber;
	}

	public void setPackNumber(Integer packNumber) {
		this.packNumber = packNumber;
	}

	public Integer getPetNumber() {
		return petNumber;
	}

	public void setPetNumber(Integer petNumber) {
		this.petNumber = petNumber;
	}
	
}
