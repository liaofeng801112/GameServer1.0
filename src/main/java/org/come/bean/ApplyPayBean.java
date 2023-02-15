package org.come.bean;

import java.math.BigDecimal;

public class ApplyPayBean {
	private BigDecimal addX;//添加的仙玉
	private BigDecimal addC;//添加的积分
	private BigDecimal addM;//添加的累计充值金额
	private BigDecimal LowOrHihtpack;//开通礼包的变化
	private BigDecimal Dayandpayorno;//连充数据的变化
	private UseCardBean useCardBean;//周月卡状态
	private UseCardBean VIPBean;//VIP状态刷新
	
	private String msg;//提示
	public BigDecimal getAddX() {
		return addX;
	}
	public void setAddX(BigDecimal addX) {
		this.addX = addX;
	}
	public BigDecimal getAddC() {
		return addC;
	}
	public void setAddC(BigDecimal addC) {
		this.addC = addC;
	}
	public BigDecimal getAddM() {
		return addM;
	}
	public void setAddM(BigDecimal addM) {
		this.addM = addM;
	}
	public BigDecimal getLowOrHihtpack() {
		return LowOrHihtpack;
	}
	public void setLowOrHihtpack(BigDecimal lowOrHihtpack) {
		LowOrHihtpack = lowOrHihtpack;
	}
	public BigDecimal getDayandpayorno() {
		return Dayandpayorno;
	}
	public void setDayandpayorno(BigDecimal dayandpayorno) {
		Dayandpayorno = dayandpayorno;
	}
	public UseCardBean getUseCardBean() {
		return useCardBean;
	}
	public void setUseCardBean(UseCardBean useCardBean) {
		this.useCardBean = useCardBean;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public UseCardBean getVIPBean() {
		return VIPBean;
	}
	public void setVIPBean(UseCardBean vIPBean) {
		VIPBean = vIPBean;
	}
	
}
