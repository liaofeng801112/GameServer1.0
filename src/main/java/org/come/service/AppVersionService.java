package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.AppVersion;
import org.come.extInterBean.Goodsrecord2;
import org.come.extInterBean.ShopBuyRecordReqBean;
import org.come.extInterBean.ShopBuyRecordResultBean;
import org.come.extInterBean.ShopBuyTypeResult;

// 三端
public interface AppVersionService {

	List<AppVersion> selectVersionUrl(String version, String sign);

	String selectPhoneVersion();

	// 一个月不上线且充值不到500的帐号 自动删除
	int deleteUserByCondition();

	// 查询物品记录
	List<Goodsrecord2> selectGoodsRecordByPage(String sql, Integer page);

	// 版本修改
	int updatePhoneVersion(String version);

	// 物品追踪
	List<Goodsrecord2> trackGoods(int rgid, int quid, int page);

	// 商城购买类型
	List<ShopBuyTypeResult> selectShopBuyType();

	// 商城购买记录查询
	List<ShopBuyRecordResultBean> selectShopBuyRecord(ShopBuyRecordReqBean reqBean);
	
	/** zrikka  插入物品记录 2020-0509 */
	int insertGoodsRecord(List<Goodsrecord2> list);
	/** zrikka 2020-0509 获取物品记录的序列 */
	BigDecimal selectSequence();
}
