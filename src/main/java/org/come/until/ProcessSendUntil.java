package org.come.until;

import org.come.bean.SendProcessBean;


/**
 * 信息传送工具类
 * 
 * 加入协议和发送的数据
 * @author Administrator
 *
 */
public class ProcessSendUntil {
	
	//传送的Bean类
	public static   SendProcessBean sendProcessBean=new SendProcessBean();
	public static final String MSM="&IqcL'DHrDgYPzE'dJ>~-KDNCAxvt7DJT=PYq34Vu(xf/fIV!QVt-2G<RG@qKEF&>|ObKak7|4n!xu`Uli9cO-D.WOxk*'ASJ;Rg!-'Fhqw(W;jUphQUH.1/n/`YuisD";
	
	/**
	 * 客户端产生加密数据
	 * @param proceString
	 * @param sendMes
	 * @return
	 * @throws Exception
	 */
	public static String returnClientSendMes(String proceString,String sendMes) throws Exception{
	    sendProcessBean.setCipherText(AESUtil.AESJDKEncode(sendMes, MSM));
		sendProcessBean.setSendMes(proceString);
		return GsonUtil.getGsonUtil().getgson().toJson(sendProcessBean);
	}
	/**
	 * 服务端产生加密数据发送
	 */
	public static String returnServerSendMes(String proceString,String sendMes) throws Exception{
		sendProcessBean.setCipherText(AESUtil.AESJDKEncode(sendMes, MSM));
		sendProcessBean.setSendMes(proceString);
		return GsonUtil.getGsonUtil().getgson().toJson(sendProcessBean);		
	}
}
