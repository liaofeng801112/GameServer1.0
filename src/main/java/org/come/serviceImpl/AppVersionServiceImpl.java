package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.entity.AppVersion;
import org.come.extInterBean.Goodsrecord2;
import org.come.extInterBean.ShopBuyRecordReqBean;
import org.come.extInterBean.ShopBuyRecordResultBean;
import org.come.extInterBean.ShopBuyTypeResult;
import org.come.handler.MainServerHandler;
import org.come.mapper.AppVersionMapper;
import org.come.service.AppVersionService;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

// 三端
public class AppVersionServiceImpl implements AppVersionService {

	private AppVersionMapper appVersionMapper;
	
	private static List<Goodsrecord2> list1=new ArrayList<>();
	private static List<Goodsrecord2> list2=new ArrayList<>();
	
	private static Object lock=new ArrayList<>();
	public static void add(Goodsrecord2 e){
		synchronized (lock) {
			list1.add(e);
		}
	}
	public synchronized static void up(){
		List<Goodsrecord2> list=null;
		synchronized (lock) {
			list=list1;
		    list1=new ArrayList<>(); 
		}
		System.out.println("本次同步物品记录数量:"+list.size());
		while (list.size()!=0) {
			try {
				list2.clear();
				for (int i = 0,length=list.size(); i<length&&i<100; i++) { list2.add(list.remove(length-i-1)); }
				if (list2.size()!=0) { AllServiceUtil.getAppVersionService().insertGoodsRecord(list2); }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				WriteOut.addtxt("同步物品记录报错:" + MainServerHandler.getErrorMessage(e)+":"+GsonUtil.getGsonUtil().getgson().toJson(list2),9999);
			}
		}
	}

	public AppVersionServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		appVersionMapper = (AppVersionMapper) ctx.getBean("appVersionMapper");
	}
	
	@Override
	public int insertGoodsRecord(List<Goodsrecord2> goodslist) {
		// TODO Auto-generated method stub
		return appVersionMapper.insertGoodsRecord(goodslist);
	}

	@Override
	public List<AppVersion> selectVersionUrl(String version, String sign) {
		// TODO Auto-generated method stub
		return appVersionMapper.selectVersionUrl(version, sign);
	}

	@Override
	public String selectPhoneVersion() {
		// TODO Auto-generated method stub
		return appVersionMapper.selectPhoneVersion();
	}

	@Override
	public int deleteUserByCondition() {
		// TODO Auto-generated method stub
		return appVersionMapper.deleteUserByCondition();
	}

	private final int limit = 10;

	@Override
	public List<Goodsrecord2> selectGoodsRecordByPage(String sql, Integer page) {
		// TODO Auto-generated method stub
		int start = (page - 1) * limit;
		int end = start + limit;
		List<Goodsrecord2> goodsRecord = appVersionMapper.selectGoodsRecordByPage(sql, start + 1, end + 1);
		return goodsRecord;
	}

	@Override
	public List<Goodsrecord2> trackGoods(int rgid, int quid, int page) {
		// TODO Auto-generated method stub
		int start = (page - 1) * limit;
		int end = start + limit;
		List<Goodsrecord2> trackGoods = appVersionMapper.trackGoods(rgid, quid, start + 1, end + 1);
		return trackGoods;
	}

	@Override
	public List<ShopBuyTypeResult> selectShopBuyType() {
		// TODO Auto-generated method stub
		return appVersionMapper.selectShopBuyType();
	}

	@Override
	public List<ShopBuyRecordResultBean> selectShopBuyRecord(ShopBuyRecordReqBean reqBean) {
		// TODO Auto-generated method stub
		int start = (Integer.valueOf(reqBean.getNowPage()) - 1) * limit;
		int end = start + limit;

		reqBean.setStart(start + 1);
		reqBean.setEnd(end + 1);
		List<ShopBuyRecordResultBean> selectShopBuyRecord = appVersionMapper.selectShopBuyRecord(reqBean);
		return selectShopBuyRecord;
	}

	@Override
	public int updatePhoneVersion(String version) {
		// TODO Auto-generated method stub
		return appVersionMapper.updatePhoneVersion(version);
	}
	@Override
	public BigDecimal selectSequence() {
		// TODO Auto-generated method stub
		return appVersionMapper.selectSequence();
	}
}
