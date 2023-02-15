package org.come.action.monitor;

import java.math.BigDecimal;

import org.come.entity.Record;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;

/**行为限制处理 如封号  解封 禁言  解禁 */
public class MonitorLimit {

	/**封号  账号  理由*/
	public static void seal(String userName,String msg){
		if (userName==null) {return;}
		// 获取账号名
		UserTable table = new UserTable();
		table.setUsername(userName);
		table.setActivity((short) 1);
		AllServiceUtil.getUserTableService().updateUser(table);
		AllServiceUtil.getRecordService().insert(new Record(5, "封号:"+userName+"_理由:"+msg));//添加记录
		SendMessage.sendMessageByUserName(userName,Agreement.getAgreement().serverstopAgreement());		
	}
	/**解封  账号  理由*/
	public static boolean unSeal(String userName,String msg){
		if (userName==null) {return false;}
		if (AllServiceUtil.getUserTableService().updateUnSeal(userName)!=0) {
			AllServiceUtil.getRecordService().insert(new Record(5, "解封:" + userName + "_理由:" + msg));// 添加记录
			return true;
		}
		return false;
	}
	/**禁言*/
	public static void silence(BigDecimal roleId,String msg){
		
	}
	/**解除禁言*/
	public static boolean unSilence(BigDecimal roleId,String msg){
		return AllServiceUtil.getHatersService().deleteByPrimaryKey(roleId)!=0;
	}
	/**添加禁词*/
	/**移除禁词*/
}
