package org.come.bean;


/**
 * 协议发送bean类
 * @author 黄建彬
 *
 */
public class SendProcessBean {

	//发送的协议
	private  byte[] cipherText;
	
	//发送的数据
	private String sendMes;

	public byte[] getCipherText() {
		return cipherText;
	}


	public void setCipherText(byte[] cipherText) {
		this.cipherText = cipherText;
	}


	public String getSendMes() {
		return sendMes;
	}


	public void setSendMes(String sendMes) {
		this.sendMes = sendMes;
	}
	
	
	
}
