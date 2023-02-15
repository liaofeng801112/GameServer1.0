package come.tool.Scene.BTY;

import java.math.BigDecimal;

public class BTYRole {

	private BigDecimal Id;
	private String role;
	private int jf;
	private boolean isAward;
	
	/***/
	public void addJF(int jf){
		this.jf+=jf;
	}
	public BTYRole(BigDecimal id, String role) {
		super();
		Id = id;
		this.role = role;
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
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}
	public boolean isAward() {
		return isAward;
	}
	public void setAward(boolean isAward) {
		this.isAward = isAward;
	}
	
}
