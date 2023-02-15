package come.tool.Stall;

import java.math.BigDecimal;

public class StallBean {
	//记录摆摊id
	private int id;
	//摊位所在的地图id
	private int mapid;
	//记录摊位人
	private String role;
	private BigDecimal roleid;
	//记录摊位名
	private String stall;
	//记录摊位状态
	private int state;
	//记录位置
	private int x;
	private int y;	
	public StallBean(Stall stall) {
		// TODO Auto-generated constructor stub
		this.id=stall.getId();
		this.mapid=stall.getMapid();
		this.role=stall.getRole();
		this.roleid=stall.getRoleid();
		this.stall=stall.getStall();
		this.state=stall.getState();
		this.x=stall.getX();
		this.y=stall.getY();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMapid() {
		return mapid;
	}
	public void setMapid(int mapid) {
		this.mapid = mapid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public BigDecimal getRoleid() {
		return roleid;
	}
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
	public String getStall() {
		return stall;
	}
	public void setStall(String stall) {
		this.stall = stall;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
