package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import org.come.bean.ImportantgoodssumrecordBean;
import org.come.entity.BuytypeEntity;
import org.come.entity.ImportantgoodsluEntity;
import org.come.entity.ImportantgoodssumrecordEntity;
import org.come.mapper.ImportantgoodtrcordMapper;
import org.come.service.IImportantgoodtrcordService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

import com.github.pagehelper.PageHelper;

public class ImportantgoodtrcordImpl implements IImportantgoodtrcordService {

	private ImportantgoodtrcordMapper importantgoodtrcordMapper;

	public ImportantgoodtrcordImpl() {

		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		importantgoodtrcordMapper = (ImportantgoodtrcordMapper) ctx.getBean("importantgoodtrcordMapper");

	}

	/**
	 * 重要物资汇总条件搜索
	 * 
	 * @param time
	 * @param weekendsum
	 * @param page
	 * @return
	 */
	@Override
	public List<ImportantgoodssumrecordBean> selectImportantgoodsrecordList(String time, String weekendsum, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 10);
		return importantgoodtrcordMapper.selectImportantgoodsrecordList(time, weekendsum);
	}

	/**
	 * 重要物资汇总条件搜索对应物品
	 * 
	 * @param time
	 * @param weekendsum
	 * @return
	 */
	@Override
	public List<ImportantgoodssumrecordBean> selectImportantgoodsrecordGoods(BigDecimal goodsid) {

		return importantgoodtrcordMapper.selectImportantgoodsrecordGoods(goodsid);
	}

	/**
	 * 监控物资设置条件搜索
	 * 
	 * @param goodsid
	 * @param goodsname
	 * @param page
	 * @return
	 */
	@Override
	public List<ImportantgoodsluEntity> selectImportantGoodsLuList(String goodsid, String goodsname, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 10);
		return importantgoodtrcordMapper.selectImportantGoodsLuList(goodsid, goodsname);
	}

	@Override
	public int updateImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity) {
		// TODO Auto-generated method stub
		return importantgoodtrcordMapper.updateImportantGoodsLu(importantGoodsLuEntity);
	}

	@Override
	public int deleteImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity) {
		// TODO Auto-generated method stub
		return importantgoodtrcordMapper.deleteImportantGoodsLu(importantGoodsLuEntity);
	}

	@Override
	public List<BuytypeEntity> selectBuyTypeList(String typename, String type, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 10);
		return importantgoodtrcordMapper.selectBuyTypeList(typename, type);
	}

	@Override
	public int updateBuyType(BuytypeEntity buytypeEntity) {
		// TODO Auto-generated method stub
		return importantgoodtrcordMapper.updateBuyType(buytypeEntity);
	}

	@Override
	public int deleteBuyType(BuytypeEntity buytypeEntity) {
		// TODO Auto-generated method stub
		return importantgoodtrcordMapper.deleteBuyType(buytypeEntity);
	}

	@Override
	public int addImportantGoodsLu(ImportantgoodsluEntity importantGoodsLuEntity) {
		// TODO Auto-generated method stub
		return importantgoodtrcordMapper.addImportantGoodsLu(importantGoodsLuEntity);
	}

	@Override
	public int addBuyType(BuytypeEntity buytypeEntity) {
		// TODO Auto-generated method stub
		return importantgoodtrcordMapper.addBuyType(buytypeEntity);
	}

	@Override
	public List<ImportantgoodssumrecordEntity> selectImportantGoods() {
		return importantgoodtrcordMapper.selectImportantGoods();
	}

	@Override
	public int addImporatantGoodsSum(ImportantgoodssumrecordEntity importantgoodssumrecordEntity) {
		return importantgoodtrcordMapper.addImporatantGoodsSum(importantgoodssumrecordEntity);
	}
	
	/** HGC-2019-12-06 */
	@Override
	public void addImporatantGoodsLuTableSpace(String time, String tableSpaceName, String path) {
		// TODO Auto-generated method stub
		String sql = "create tablespace " + tableSpaceName + " datafile '" + path + tableSpaceName + ".dbf' size 10M autoextend on next 10M maxsize unlimited ";
		importantgoodtrcordMapper.addImporatantGoodsLuTableSpace(sql);
	}

	/** HGC-2019-12-19 */
	@Override
	public void addTableImporatantGoodsLuTableSpace(String time, String partitionName,String tableName) {
		// TODO Auto-generated method stub

		StringBuffer buffer = new StringBuffer(time);
		buffer.insert(6, "-");
		buffer.insert(4, "-");
		String sql = "ALTER TABLE " + tableName + " ADD PARTITION " + partitionName + " VALUES ('" + buffer + "') TABLESPACE " + partitionName + "";
		importantgoodtrcordMapper.addTableImporatantGoodsLuTableSpace(sql);
	}
	
	@Override
	public int selectTableSapce(String tableSpaceName) {
		// SELECT COUNT(*)
		// FROM USER_TAB_PARTITIONS where TABLE_NAME = 'IMPORTANTGOODSLU' and
		// PARTITION_NAME='HGCTEST20200102' ;
		return importantgoodtrcordMapper.selectTableSapce(tableSpaceName);
	}

	@Override
	public int selectTablePartition(String partitionName, String tableName) {
		// TODO Auto-generated method stub
		return importantgoodtrcordMapper.selectTablePartition(partitionName, tableName);
	}

}
