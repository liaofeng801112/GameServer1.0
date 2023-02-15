package org.come.bean;

import java.math.BigDecimal;

/**
 * 中量级
 * @author Administrator
 * 后续大改做为中量级数据广播
 */
public class Middle {

	//标识 后续这改成id
	private String rolename;
   	//记录完成次数
 	private String taskComplete;
    //坐牢标志  PK点数=身份标志=做天牢次数=每周坐牢次数
   	private String taskDaily;
   	private BigDecimal Dayandpayorno;
   	private BigDecimal Daypaysum;
   	private BigDecimal Daygetorno;
   	private String Vipget;
   	private int Dayfirstinorno;
   	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getTaskComplete() {
		return taskComplete;
	}
	public void setTaskComplete(String taskComplete) {
		this.taskComplete = taskComplete;
	}
	public String getTaskDaily() {
		return taskDaily;
	}
	public void setTaskDaily(String taskDaily) {
		this.taskDaily = taskDaily;
	}
	public BigDecimal getDayandpayorno() {
		return Dayandpayorno;
	}
	public void setDayandpayorno(BigDecimal dayandpayorno) {
		Dayandpayorno = dayandpayorno;
	}
	public BigDecimal getDaypaysum() {
		return Daypaysum;
	}
	public void setDaypaysum(BigDecimal daypaysum) {
		Daypaysum = daypaysum;
	}
	public BigDecimal getDaygetorno() {
		return Daygetorno;
	}
	public void setDaygetorno(BigDecimal daygetorno) {
		Daygetorno = daygetorno;
	}
	public String getVipget() {
		return Vipget;
	}
	public void setVipget(String vipget) {
		Vipget = vipget;
	}
	public int getDayfirstinorno() {
		return Dayfirstinorno;
	}
	public void setDayfirstinorno(int dayfirstinorno) {
		Dayfirstinorno = dayfirstinorno;
	}
}
