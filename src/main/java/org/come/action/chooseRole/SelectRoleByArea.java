package org.come.action.chooseRole;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.IAction;
import org.come.entity.AreaQuery;
import org.come.entity.Openareatable;
import org.come.entity.RegionResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

/**
 * 
 * @author zz
 * @time 2019年7月17日14:06:23
 * 
 */
public class SelectRoleByArea implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		AreaQuery area = GsonUtil.getGsonUtil().getgson().fromJson(message, AreaQuery.class);

		String userId = area.getUserId();
		List<RegionResult> result = new ArrayList<>();
		if (userId == null) {
			// 返回区域信息
			String msg = Agreement.getAgreement().returnAgreement(GsonUtil.getGsonUtil().getgson().toJson(result));
			SendMessage.sendMessageToSlef(ctx, msg);
		}
		String[] userIdArr = userId.split("\\|");
		if (userIdArr.length > 1) {
			// 返回区域信息
			String msg = Agreement.getAgreement().returnAgreement(GsonUtil.getGsonUtil().getgson().toJson(result));
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
		List<Openareatable> allArea = AllServiceUtil.getOpenareatableService().selectAllArea(new BigDecimal(userId));
		if (allArea == null) {
			// 返回区域信息
			String msg = Agreement.getAgreement().returnAgreement(GsonUtil.getGsonUtil().getgson().toJson(result));
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}

		for (int i = 0; i < allArea.size(); i++) {
			RegionResult res = new RegionResult();
			// 大区名称
			res.setRE_NAME("天界");
			// 区id
			res.setRA_ID(allArea.get(i).getOt_areaid());
			// 区名称
			res.setRA_NAME(allArea.get(i).getOt_areaname());
			res.setOT_BELONG(allArea.get(i).getOt_belong() + "");
			result.add(res);
		}

		String json = GsonUtil.getGsonUtil().getgson().toJson(result);

		// 返回区域信息
		String msg = Agreement.getAgreement().returnAgreement(json);
		SendMessage.sendMessageToSlef(ctx, msg);
	}
}
