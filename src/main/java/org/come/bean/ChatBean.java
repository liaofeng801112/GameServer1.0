package org.come.bean;

import java.math.BigDecimal;
/**
 * 聊天bean
 * @author 叶豪芳
 * @date : 2017年11月27日 上午10:06:10
 */
public class ChatBean {
    // 帮派ID
    private BigDecimal gangid;
    // 队伍ID
    private BigDecimal troop_id;
    // 所在地图
    private Long mapid;
	// 发送者Id
	private BigDecimal SendRoleId;
    // 好友名字
    private String friendName;
	// 好友角色名
	private String rolename;
	// 种族名称
	private String race_name;
    // 好友等级
	private Integer grade;
    // 聊天内容
    private String message;
    //发送时间
    private long time;
	public BigDecimal getGangid() {
		return gangid;
	}

	public void setGangid(BigDecimal gangid) {
		this.gangid = gangid;
	}

	public BigDecimal getTroop_id() {
		return troop_id;
	}

	public void setTroop_id(BigDecimal troop_id) {
		this.troop_id = troop_id;
	}

	public Long getMapid() {
		return mapid;
	}

	public void setMapid(Long mapid) {
		this.mapid = mapid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRace_name() {
		return race_name;
	}

	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public BigDecimal getSendRoleId() {
		return SendRoleId;
	}

	public void setSendRoleId(BigDecimal sendRoleId) {
		SendRoleId = sendRoleId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
    
}
