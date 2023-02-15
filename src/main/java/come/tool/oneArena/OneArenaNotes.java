package come.tool.oneArena;

import java.math.BigDecimal;

import org.come.until.TimeUntil;

/**战斗记录*/
public class OneArenaNotes {
	
	private long id;
	private BigDecimal role1;//玩家一   发起方
	private String name1;//名称等级图标
	private String skin1;
	private int lvl1;
	private BigDecimal role2;//玩家二   被动方
	private String name2;//名称等级图标
	private String skin2;
	private int lvl2;
	private int isV;//0玩家一胜利   1玩家二胜利
	private int place;//排名变化值  排名变化绝对值
	private String time;//发生时间
	
	public OneArenaNotes(OneArenaRole arenaRole1,OneArenaRole arenaRole2,int isV,int place) {
		// TODO Auto-generated constructor stub
		this.role1=arenaRole1.getRoleId();
		this.name1=arenaRole1.getName();
		this.skin1=arenaRole1.getSkin();
		this.lvl1 =arenaRole1.getLvl();
		
		this.role2=arenaRole2.getRoleId();
		this.name2=arenaRole2.getName();
		this.skin2=arenaRole2.getSkin();
		this.lvl2 =arenaRole2.getLvl();
		
		this.isV=isV;
		this.place=place;
		this.time=TimeUntil.getPastDate();
	}
	
	public OneArenaNotes() {
    }

    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BigDecimal getRole1() {
		return role1;
	}
	public void setRole1(BigDecimal role1) {
		this.role1 = role1;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getSkin1() {
		return skin1;
	}
	public void setSkin1(String skin1) {
		this.skin1 = skin1;
	}
	public int getLvl1() {
		return lvl1;
	}
	public void setLvl1(int lvl1) {
		this.lvl1 = lvl1;
	}
	public BigDecimal getRole2() {
		return role2;
	}
	public void setRole2(BigDecimal role2) {
		this.role2 = role2;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getSkin2() {
		return skin2;
	}
	public void setSkin2(String skin2) {
		this.skin2 = skin2;
	}
	public int getLvl2() {
		return lvl2;
	}
	public void setLvl2(int lvl2) {
		this.lvl2 = lvl2;
	}
	public int getIsV() {
		return isV;
	}
	public void setIsV(int isV) {
		this.isV = isV;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
