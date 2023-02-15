package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.bean.ImportantgoodssumrecordBean;
import org.come.entity.BuytypeEntity;
import org.come.entity.ImportantgoodsluEntity;
import org.come.entity.ImportantgoodssumrecordEntity;

@MyBatisAnnotation
public interface ImportantgoodtrcordMapper {

	/**
	 * 重要物资汇总条件搜索
	 * 
	 * @param time
	 * @param weekendsum
	 * @return
	 */
	List<ImportantgoodssumrecordBean> selectImportantgoodsrecordList(@Param("time") String time, @Param("weekendsum") String weekendsum);

	/**
	 * 重要物资汇总条件搜索
	 * 
	 * @param time
	 * @param weekendsum
	 * @return
	 */
	List<ImportantgoodssumrecordBean> selectImportantgoodsrecordGoods(BigDecimal goodsid);

	/**
	 * 监控物资设置条件搜索
	 * 
	 * @param goodsid
	 * @param goodsname
	 * @param page
	 * @return
	 */
	List<ImportantgoodsluEntity> selectImportantGoodsLuList(@Param("goodsid") String goodsid, @Param("goodsname") String goodsname);

	/**
	 * 监控物资设置修改
	 * 
	 * @param importantGoodsLuEntity
	 * @return
	 */
	int updateImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity);

	/**
	 * 监控物资设置新增
	 * 
	 * @param importantGoodsLuEntity
	 * @return
	 */
	int addImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity);

	/**
	 * 监控物资设置删除
	 * 
	 * @param importantGoodsLuEntity
	 * @return
	 */
	int deleteImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity);

	/**
	 * 类型设置条件搜索
	 * 
	 * @param typename
	 * @param type
	 * @return
	 */
	List<BuytypeEntity> selectBuyTypeList(@Param("typename") String typename, @Param("type") String type);

	/**
	 * 类型设置修改
	 * 
	 * @param buytypeEntity
	 * @return
	 */
	int updateBuyType(BuytypeEntity buytypeEntity);

	/**
	 * 类型设置删除
	 * 
	 * @param buytypeEntity
	 * @return
	 */
	int deleteBuyType(BuytypeEntity buytypeEntity);

	/**
	 * 类型设置新增
	 * 
	 * @param buytypeEntity
	 * @return
	 */
	int addBuyType(BuytypeEntity buytypeEntity);

	/**
	 * 查询重要物资记录统计数量
	 * 
	 * @return
	 */
	List<ImportantgoodssumrecordEntity> selectImportantGoods();

	/**
	 * 添加重要物资记录统计数量
	 * 
	 * @param importantgoodssumrecordBean
	 * @return
	 */
	int addImporatantGoodsSum(ImportantgoodssumrecordEntity importantgoodssumrecordEntity);
	
	/** HGC-2019-12-06 */
	/** 添加表空间 */
	void addImporatantGoodsLuTableSpace(@Param("time") String time);

	/** HGC-2019-12-19 */
	/** 表添加表分区 */
	void addTableImporatantGoodsLuTableSpace(@Param("time") String time);


	/** 查询表空间是否存在 */
	int selectTableSapce(@Param("tableSpaceName") String tableSpaceName);
	
	/** 查询表分区是否存在 */
	int selectTablePartition(@Param("partitionName")String partitionName,@Param("tableName")String tableName);
}
