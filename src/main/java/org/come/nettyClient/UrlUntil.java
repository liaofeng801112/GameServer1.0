package org.come.nettyClient;

import org.come.bean.Account;
import org.come.bean.AccountActionBean;
import org.come.bean.IpActionBean;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

/**
 * 存储url ip
 * 
 * @author zeng
 * 
 */
public class UrlUntil {

	/** zrikka 2020 0419 账号服务器的连接信息  测试 */
//	public static String account_ip = "127.0.0.1";// 账号服务器ip
//	public static String tomcat_port = "8081";// tomcat端口
//	public static String poject = "TradingWeb";// 项目名
	/** zrikka 2020 0422 账号服务器的连接信息 正式 */
	public static String account_ip = "103.85.86.54";// 账号服务器 -->ip
	public static String tomcat_port = "8082";// 账号服务器 -->tomcat端口
	public static String poject = "loginServer";// 项目名
	public static int account_port = 7101;// 账号服务器 -->端口

	public static long time;

	public static void accountAction(String type, String account_flag, String param) {
		AccountActionBean accaction = new AccountActionBean();
		Account account = new Account();
		account.setAc_flag(account_flag);
		if ("updatePhone".equals(type)) {
			// 手机号 修改
			account.setAc_phone(param);
		} else if ("updatePasw".equals(type)) {
			// 密码 修改
			account.setAc_pasw(param);
		} else if ("updateSafely".equals(type)) {
			// 安全码 修改
			account.setAc_safely(param);
		}

		accaction.setAccount(account);
		accaction.setType(type);

		String Content = GsonUtil.getGsonUtil().getgson().toJson(accaction);
		String res = Agreement.getAgreement().Account_UpdateAgreement(Content);
		ClientSendMessage.toServer(account_ip + "|" + account_port, res);
	}

	public static void ipAction(String type, String ip) {
		IpActionBean ipaction = new IpActionBean();
		ipaction.setType(type);
		ipaction.setIpaddress(ip);

		String content = GsonUtil.getGsonUtil().getgson().toJson(ipaction);
		String res = Agreement.getAgreement().Ip_ActionAgreement(content);
		ClientSendMessage.toServer(account_ip + "|" + account_port, res);
	}

}
