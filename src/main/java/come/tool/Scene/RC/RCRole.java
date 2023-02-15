package come.tool.Scene.RC;

import java.math.BigDecimal;

public class RCRole {

	private BigDecimal Id;
	private String role;
	private boolean isBB;//true表示挑战过了
	private int state;//状态   -1挑战失败  0正常  1挑战中
	private int advance;//进度
	public RCRole(BigDecimal id, String role) {
		super();
		this.Id = id;
		this.role = role;
		this.state=0;
		this.advance=0;
		this.isBB=false;
	}
	public BigDecimal getId() {
		return Id;
	}
	public void setId(BigDecimal id) {
		Id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getAdvance() {
		return advance;
	}
	public void setAdvance(int advance) {
		this.advance = advance;
	}
	public boolean isBB() {
		return isBB;
	}
	public void setBB(boolean isBB) {
		this.isBB = isBB;
	}
	
}
