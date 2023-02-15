package org.come.serviceImpl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.come.entity.Goodsrecord;
import org.come.entity.GoodsrecordExample;
import org.come.entity.Goodstable;
import org.come.extInterBean.Goodsrecord2;
import org.come.mapper.GoodsrecordMapper;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.IGoodsrecordService;
import org.come.until.GsonUtil;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GoodsrecordServiceImpl implements IGoodsrecordService {
	
	private GoodsrecordMapper goodsrecordMapper;
	
	public GoodsrecordServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		goodsrecordMapper = ctx.getBean("goodsrecordMapper",GoodsrecordMapper.class);
		
	}

	@Override
	public int countByExample(GoodsrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(GoodsrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal grid) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 添加物品记录
	 */
	@Override
	public int insert(Goodstable goodstable, BigDecimal otherrole, Integer goodsnum, Integer recordtype) {
		try {
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String nowdayTime = dateFormat.format(new Date());
//			Date nowDate = dateFormat.parse(nowdayTime);
			// 创建记录对象
			Goodsrecord2 goodsrecord = new Goodsrecord2();
			goodsrecord.setGrid(RedisCacheUtil.getRecord_pk());
			goodsrecord.setRoleid(goodstable.getRole_id());
			goodsrecord.setOtherrole(otherrole);
			// goodsrecord.setGoods(goodstable.toString());
			// goodsrecord.setRecordtime(nowDate);
			goodsrecord.setRecordtype(recordtype);
			goodsrecord.setGoodsnum(goodsnum);
			/** 设置物品记录值 */
			setGoodsRecordValue(goodstable, goodsrecord);

			AppVersionServiceImpl.add(goodsrecord);
			// RedisControl.insertGoodsRecordRedis(RedisParameterUtil.GOODS_RECORD,null,
			// GsonUtil.getGsonUtil().getgson().toJson(goodsrecord),jedis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insertGoodsrecord(BigDecimal roleID, BigDecimal otherID, int type, BigDecimal id, String name, String value, int num) {
		try {
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String nowdayTime = dateFormat.format(new Date());
//			Date nowDate = dateFormat.parse(nowdayTime);
			Goodsrecord2 goodsrecord = new Goodsrecord2();
			goodsrecord.setGrid(RedisCacheUtil.getRecord_pk());
			//goodsrecord.setRecordtime(nowDate);
			goodsrecord.setRoleid(roleID);
			// goodsrecord.setGoods(Goodstable.toStringTwo(roleID, id, type,
			// name, value));
			goodsrecord.setOtherrole(otherID);
			goodsrecord.setRecordtype(type);
			goodsrecord.setGoodsnum(num);
			/** 设置物品记录值 */
			goodsrecord.setGoodsname(name);
			goodsrecord.setRgid(id);
			goodsrecord.setValue(value);

			AppVersionServiceImpl.add(goodsrecord);
			// RedisControl.insertGoodsRecordRedis(RedisParameterUtil.GOODS_RECORD,null,
			// GsonUtil.getGsonUtil().getgson().toJson(goodsrecord),jedis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 添加物品记录
	 */
//	@Override
//	public int insert(Goodstable goodstable, BigDecimal otherrole, Integer goodsnum, Integer recordtype) {
//		
//		try {
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String nowdayTime = dateFormat.format(new Date());
//			Date nowDate = dateFormat.parse(nowdayTime);
//			// 创建记录对象
//			Goodsrecord goodsrecord = new Goodsrecord();
//			goodsrecord.setRoleid(goodstable.getRole_id());
//			goodsrecord.setOtherrole(otherrole);
//			goodsrecord.setGoods(goodstable.toString());
//			goodsrecord.setRecordtime(nowDate);
//			goodsrecord.setRecordtype(recordtype);
//			goodsrecord.setGoodsnum(goodsnum);
//			RedisControl.insertGoodsRecordRedis(RedisParameterUtil.GOODS_RECORD,null, GsonUtil.getGsonUtil().getgson().toJson(goodsrecord));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	@Override
//	public int insertGoodsrecord(BigDecimal roleID, BigDecimal otherID,int type, BigDecimal id, String name,String value, int num) {
//		// TODO Auto-generated method stub
//		try {
//			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String nowdayTime = dateFormat.format(new Date());
//			Date nowDate = dateFormat.parse(nowdayTime);
//			Goodsrecord goodsrecord = new Goodsrecord();
//			goodsrecord.setRecordtime(nowDate);	
//			goodsrecord.setRoleid(roleID);
//			goodsrecord.setGoods(Goodstable.toStringTwo(roleID, id, type, name, value));
//			goodsrecord.setOtherrole(otherID);
//			goodsrecord.setRecordtype(type);
//			goodsrecord.setGoodsnum(num);
//			RedisControl.insertGoodsRecordRedis(RedisParameterUtil.GOODS_RECORD,null, GsonUtil.getGsonUtil().getgson().toJson(goodsrecord));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return 0;
//	}
	@Override
	public int insertSelective(Goodsrecord record) {
		goodsrecordMapper.insert(record);
		return 0;
	}

	@Override
	public List<Goodsrecord> selectByExample(GoodsrecordExample example) {
		// TODO Auto-generated method stub
		return goodsrecordMapper.selectByExample(example);
	}

	@Override
	public Goodsrecord selectByPrimaryKey(Integer grid) {
		// TODO Auto-generated method stub
		return goodsrecordMapper.selectByPrimaryKey(grid);
	}

	@Override
	public int updateByExampleSelective(Goodsrecord record,
			GoodsrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(Goodsrecord record, GoodsrecordExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(Goodsrecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Goodsrecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<Goodsrecord> selectGoodsRecord( Integer pageNum, String condition ) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Goodsrecord goodsrecord = gson.fromJson(condition, Goodsrecord.class);
		PageHelper.startPage(pageNum, 8);
		List<Goodsrecord> list = goodsrecordMapper.selectGoodsRecord(goodsrecord);
		PageInfo<Goodsrecord> pageinfo = new PageInfo<>(list);
		return pageinfo;
	}

	/** 设置物品记录的值 */
	public static void setGoodsRecordValue(Goodstable goodstable, Goodsrecord2 goodsrecord) {
		// 物品名称
		goodsrecord.setGoodsname(goodstable.getGoodsname());
		// 物品加成
		goodsrecord.setValue(goodstable.getValue());
		// 使用次数
		goodsrecord.setUsetime(goodstable.getUsetime() + "");
		// 物品标识
		goodsrecord.setGoodsid(goodstable.getGoodsid());
		// 皮肤
		goodsrecord.setSkin(goodstable.getSkin());
		// 物品类型
		goodsrecord.setType(Long.valueOf(goodstable.getType()));
		// 物品贵重
		if(goodstable.getQuality() == null){
			goodsrecord.setQuality(0L);
		}else {
			goodsrecord.setQuality(Long.valueOf(goodstable.getQuality()));
		}
		goodsrecord.setRgid(goodstable.getRgid());
		// 物品状态
		if(goodstable.getStatus() == null){
			goodsrecord.setStatus(0);
		}else {
			goodsrecord.setStatus(Integer.valueOf(goodstable.getStatus()));
		}
		// 自定义价格/仙玉/大话币/积分
		// 加锁
		goodsrecord.setGoodlock(0);
	}
}
