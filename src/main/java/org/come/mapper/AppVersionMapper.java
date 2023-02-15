package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.AppVersion;
import org.come.extInterBean.Goodsrecord2;
import org.come.extInterBean.ShopBuyRecordReqBean;
import org.come.extInterBean.ShopBuyRecordResultBean;
import org.come.extInterBean.ShopBuyTypeResult;

// 三端
@MyBatisAnnotation
public interface AppVersionMapper {

	List<AppVersion> selectVersionUrl(@Param("version") String version, @Param("sign") String sign);

	String selectPhoneVersion();

	// 一个月不上线且充值不到500的帐号 自动删除
	int deleteUserByCondition();

	// 查询物品记录
	List<Goodsrecord2> selectGoodsRecordByPage(@Param("sql") String sql, @Param("start") int start, @Param("end") int end);

	// 物品追踪
	List<Goodsrecord2> trackGoods(@Param("rgid") int rgid, @Param("quid") int quid, @Param("start") int start, @Param("end") int end);

	// 版本修改
	int updatePhoneVersion(String version);

	// 商城购买类型
	List<ShopBuyTypeResult> selectShopBuyType();

	// 商城购买记录查询
	List<ShopBuyRecordResultBean> selectShopBuyRecord(ShopBuyRecordReqBean reqBean);
	
	/** zrikka 2020-0509 插入物品记录  */
	int insertGoodsRecord(List<Goodsrecord2> goodslist);
	/** zrikka 2020-0509 获取物品记录的序列 */
	BigDecimal selectSequence();
}
