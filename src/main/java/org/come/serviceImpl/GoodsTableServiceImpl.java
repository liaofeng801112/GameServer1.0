package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.Goodsbuyrecordsumbean;
import org.come.entity.GoodsbuyrecordEntity;
import org.come.entity.GoodssaledayrecordEntity;
import org.come.entity.Goodstable;
import org.come.entity.ShangchengshopEntity;
import org.come.mapper.GoodstableMapper;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.IGoodsTableService;
import org.come.tool.Goodtype;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

import com.github.pagehelper.PageHelper;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

/**
 * 角色物品操作类
 * 
 * @author 叶豪芳
 * @date : 2017年11月25日 下午7:35:50
 */
public class GoodsTableServiceImpl implements IGoodsTableService {

	private GoodstableMapper goodstableMapper;

	public GoodsTableServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		goodstableMapper = (GoodstableMapper) ctx.getBean("goodstableMapper");
	}

	/** 获取角色的全部物品(non-Javadoc) */
	@Override
	public List<Goodstable> getGoodsByRoleID(BigDecimal role_id) {
		List<Goodstable> list = RedisControl.getS(RedisParameterUtil.GOODS, role_id.toString(), Goodstable.class);
		return list;
	}

	/** 购买物品 */
	@Override
	public void insertGoods(Goodstable goodstable) {
		goodstable.setRgid(RedisCacheUtil.getGoods_pk());
		if (goodstable.getUsetime() == null) {
			goodstable.setUsetime(1);
		}
		if (goodstable.getStatus() == null) {
			goodstable.setStatus(0);
		}
		RedisControl.insertKeyT(RedisParameterUtil.GOODS, goodstable.getRgid().toString(), goodstable);
		RedisControl.insertController(RedisParameterUtil.GOODS, goodstable.getRgid().toString(), RedisControl.CADD);
		RedisControl.insertListRedis(RedisParameterUtil.GOODS, goodstable.getRole_id().toString(), goodstable.getRgid().toString());
		RedisControl.insertListRedis(RedisParameterUtil.GOODSID + "_" + goodstable.getRole_id().toString(), goodstable.getGoodsid().toString(), goodstable.getRgid().toString());
		RedisControl.insertListRedis(RedisParameterUtil.GOODSST + "_" + goodstable.getRole_id().toString(), goodstable.getStatus().toString(), goodstable.getRgid().toString());
		if (goodstable.getStatus() == 0 && goodstable.getType() != 8888) {
			RoleData data = RolePool.getRoleData(goodstable.getRole_id());
			if (data != null) {
				data.upGoodNum(1);
			}
		}
	}

	/** 更改索引 */
	@Override
	public void updateGoodsIndex(Goodstable goodstable, BigDecimal role_id, BigDecimal goodsid, Integer status) {
		// TODO Auto-generated method stub
		BigDecimal yrid = goodstable.getRole_id();// 原来的状态
		BigDecimal ygid = goodstable.getGoodsid();// 原来的物品id;
		Integer yss = goodstable.getStatus();// 原来的状态
		BigDecimal nrid = role_id != null ? role_id : yrid;
		BigDecimal ngid = goodsid != null ? goodsid : ygid;// 改变的物品id;
		Integer nss = status != null ? status : yss;// 改变的状态
		goodstable.setRole_id(nrid);
		goodstable.setGoodsid(ngid);
		goodstable.setStatus(nss);

		RedisControl.insertKeyT(RedisParameterUtil.GOODS, goodstable.getRgid().toString(), goodstable);
		RedisControl.insertController(RedisParameterUtil.GOODS, goodstable.getRgid().toString(), RedisControl.CALTER);
		if (nrid != null && nrid.compareTo(yrid) != 0) {// 用户id
			RedisControl.deletrValue(RedisParameterUtil.GOODS, yrid.toString(), goodstable.getRgid().toString());
			RedisControl.insertListRedis(RedisParameterUtil.GOODS, nrid.toString(), goodstable.getRgid().toString());
			RedisControl.deletrValue(RedisParameterUtil.GOODSID + "_" + yrid, ygid.toString(), goodstable.getRgid().toString());
			RedisControl.insertListRedis(RedisParameterUtil.GOODSID + "_" + nrid, ngid.toString(), goodstable.getRgid().toString());
			RedisControl.deletrValue(RedisParameterUtil.GOODSST + "_" + yrid, yss.toString(), goodstable.getRgid().toString());
			RedisControl.insertListRedis(RedisParameterUtil.GOODSST + "_" + nrid, nss.toString(), goodstable.getRgid().toString());
			if ((nss == 0 || yss == 0) && goodstable.getType() != 8888) {
				RoleData data = RolePool.getRoleData(yrid);
				if (data != null) {
					data.upGoodNum(-1);
				}
				data = RolePool.getRoleData(nrid);
				if (data != null) {
					data.upGoodNum(1);
				}
			}
		} else {
			if (ngid != null && ngid.compareTo(ygid) != 0) {// 物品id
				String key = RedisParameterUtil.GOODSID + "_" + goodstable.getRole_id().toString();
				RedisControl.deletrValue(key, ygid.toString(), goodstable.getRgid().toString());
				RedisControl.insertListRedis(key, ngid.toString(), goodstable.getRgid().toString());
			}
			if (nss != null && nss != yss) {// 物品状态
				String key = RedisParameterUtil.GOODSST + "_" + goodstable.getRole_id().toString();
				RedisControl.deletrValue(key, yss.toString(), goodstable.getRgid().toString());
				RedisControl.insertListRedis(key, nss.toString(), goodstable.getRgid().toString());
				if (nss == 1 || yss == 1) {
					int type = Goodtype.EquipmentType(goodstable.getType());
					if (type != -1) {
						RoleData data = RolePool.getRoleData(goodstable.getRole_id());
						if (data != null) {
							data.CEquip(goodstable.getRgid(), type, nss == 1);
						}
					}
				}
				if ((nss == 0 || yss == 0) && goodstable.getType() != 8888) {
					RoleData data = RolePool.getRoleData(goodstable.getRole_id());
					if (data != null) {
						data.upGoodNum(yss == 0 ? -1 : 1);
					}
				}
			}
		}
	}

	/***/
	@Override
	public String updateGoodsNum(Goodstable goodstable, int I) {
		Goodstable good = RedisControl.getV(RedisParameterUtil.GOODS, goodstable.getRgid().toString(), Goodstable.class);
		if (I == 2 && !(good.getType() == 80156 || good.getType() == 2012 || good.getType() == 729 || good.getType() == 2010 || good.getType() == 2011)) {
			I = 1;
		}
		String monitor = MonitorUtil.isGoodMonitor(good, goodstable, I);
		if (monitor != null) {
			return monitor;
		}
		Integer yss = good.getStatus();// 原来的状态
		Integer nss = goodstable.getStatus();// 原来的状态
		int nUse = goodstable.getUsetime();
		if (nUse <= 0) {
			RedisControl.deletrValue(RedisParameterUtil.GOODS, good.getRole_id().toString(), good.getRgid().toString());
			String key = RedisParameterUtil.GOODSID + "_" + good.getRole_id().toString();
			RedisControl.deletrValue(key, good.getGoodsid().toString(), good.getRgid().toString());
			key = RedisParameterUtil.GOODSST + "_" + good.getRole_id().toString();
			RedisControl.deletrValue(key, good.getStatus().toString(), good.getRgid().toString());
			RedisControl.delForKey(RedisParameterUtil.GOODS, good.getRgid().toString());
			RedisControl.insertController(RedisParameterUtil.GOODS, good.getRgid().toString(), RedisControl.CDELETE);
			if (yss == 0 && goodstable.getType() != 8888) {
				RoleData data = RolePool.getRoleData(good.getRole_id());
				if (data != null) {
					data.upGoodNum(-1);
				}
			}
			return null;
		}
		good.setUsetime(nUse);
		good.setStatus(nss);
		good.setGoodlock(goodstable.getGoodlock());
		if (I == 2) {
			good.setGoodsname(goodstable.getGoodsname());
			good.setValue(goodstable.getValue());
			good.setSkin(goodstable.getSkin());
		}
		RedisControl.insertKeyT(RedisParameterUtil.GOODS, good.getRgid().toString(), good);
		RedisControl.insertController(RedisParameterUtil.GOODS, good.getRgid().toString(), RedisControl.CALTER);
		if (yss != nss) {// 物品状态
			String key = RedisParameterUtil.GOODSST + "_" + good.getRole_id().toString();
			RedisControl.deletrValue(key, yss.toString(), good.getRgid().toString());
			RedisControl.insertListRedis(key, nss.toString(), good.getRgid().toString());
			if (nss == 1 || yss == 1) {
				int type = Goodtype.EquipmentType(good.getType());
				if (type != -1) {
					RoleData data = RolePool.getRoleData(good.getRole_id());
					if (data != null) {
						data.CEquip(good.getRgid(), type, nss == 1);
					}
				}
			}
			if ((nss == 0 || yss == 0) && goodstable.getType() != 8888) {
				RoleData data = RolePool.getRoleData(good.getRole_id());
				if (data != null) {
					data.upGoodNum(yss == 0 ? -1 : 1);
				}
			}
		}
		return null;
	}

	/** 删除物品 */
	@Override
	public void deleteGoodsByRgid(BigDecimal rgid) {
		Goodstable goodstable = RedisControl.getV(RedisParameterUtil.GOODS, rgid.toString(), Goodstable.class);
		if (goodstable != null) {
			RedisControl.deletrValue(RedisParameterUtil.GOODS, goodstable.getRole_id().toString(), rgid.toString());
			String key = RedisParameterUtil.GOODSID + "_" + goodstable.getRole_id().toString();
			RedisControl.deletrValue(key, goodstable.getGoodsid().toString(), goodstable.getRgid().toString());
			key = RedisParameterUtil.GOODSST + "_" + goodstable.getRole_id().toString();
			RedisControl.deletrValue(key, goodstable.getStatus().toString(), goodstable.getRgid().toString());
			if (goodstable.getStatus() == 0 && goodstable.getType() != 8888) {
				RoleData data = RolePool.getRoleData(goodstable.getRole_id());
				if (data != null) {
					data.upGoodNum(-1);
				}
			}
		}
		RedisControl.delForKey(RedisParameterUtil.GOODS, rgid.toString());
		RedisControl.insertController(RedisParameterUtil.GOODS, rgid.toString(), RedisControl.CDELETE);
	}

	/** 修改从redis里面拿出来的物品 */
	@Override
	public void updateGoodRedis(Goodstable goodstable) {
		if (goodstable.getUsetime() > 0) {
			RedisControl.insertKeyT(RedisParameterUtil.GOODS, goodstable.getRgid().toString(), goodstable);
			RedisControl.insertController(RedisParameterUtil.GOODS, goodstable.getRgid().toString(), RedisControl.CALTER);
		} else {
			BigDecimal rgid = goodstable.getRgid();
			RedisControl.deletrValue(RedisParameterUtil.GOODS, goodstable.getRole_id().toString(), rgid.toString());
			String key = RedisParameterUtil.GOODSID + "_" + goodstable.getRole_id().toString();
			RedisControl.deletrValue(key, goodstable.getGoodsid().toString(), goodstable.getRgid().toString());
			key = RedisParameterUtil.GOODSST + "_" + goodstable.getRole_id().toString();
			RedisControl.deletrValue(key, goodstable.getStatus().toString(), goodstable.getRgid().toString());
			RedisControl.delForKey(RedisParameterUtil.GOODS, rgid.toString());
			RedisControl.insertController(RedisParameterUtil.GOODS, rgid.toString(), RedisControl.CDELETE);
			if (goodstable.getStatus() == 0 && goodstable.getType() != 8888) {
				RoleData data = RolePool.getRoleData(goodstable.getRole_id());
				if (data != null) {
					data.upGoodNum(-1);
				}
			}
		}
	}

	/** 获取单个物品信息 */
	@Override
	public Goodstable getGoodsByRgID(BigDecimal rgid) {
		Goodstable goodstable = RedisControl.getV(RedisParameterUtil.GOODS, rgid.toString(), Goodstable.class);
		return goodstable;
	}

	/** 查询是否含有该件物品 */
	@Override
	public List<Goodstable> selectGoodsByRoleIDAndGoodsID(BigDecimal roleid, BigDecimal goodsid) {
		String key = RedisParameterUtil.GOODS + "_" + roleid.toString();
		String key1 = RedisParameterUtil.GOODSID + "_" + roleid.toString() + "_" + goodsid.toString();
		String key2 = RedisParameterUtil.GOODSST + "_" + roleid.toString() + "_0";
		List<Goodstable> goodstables = RedisControl.getS(RedisParameterUtil.GOODS, RedisControl.sinterJiao(key1, key2, key), Goodstable.class);
		return goodstables;
	}

	@Override
	public List<Goodstable> selectGoodsByRoleIDAndGoodsIDAndState(BigDecimal roleid, BigDecimal goodsid, int state) {
		// TODO Auto-generated method stub
		String key = RedisParameterUtil.GOODS + "_" + roleid.toString();
		String key1 = RedisParameterUtil.GOODSID + "_" + roleid.toString() + "_" + goodsid.toString();
		String key2 = RedisParameterUtil.GOODSST + "_" + roleid.toString() + "_" + state;
		List<Goodstable> goodstables = RedisControl.getS(RedisParameterUtil.GOODS, RedisControl.sinterJiao(key1, key2, key), Goodstable.class);
		return goodstables;
	}

	@Override
	public List<Goodstable> getAllGoods() {
		return goodstableMapper.getAllGoods();
	}

	@Override
	public BigDecimal selectMaxID() {
		return goodstableMapper.selectMaxID();
	}

	@Override
	public void insertGoodssql(Goodstable goodstable) {
		// TODO Auto-generated method stub
		goodstableMapper.insertGoods(goodstable);
	}

	@Override
	public void updateGoodssql(Goodstable goodstable) {
		// TODO Auto-generated method stub
		goodstableMapper.updateGoods(goodstable);
	}

	@Override
	public void deleteGoodsByRgidsql(BigDecimal rgid) {
		// TODO Auto-generated method stub
		goodstableMapper.deleteGoodsByRgid(rgid);
	}

	@Override
	public void insertGoodssqlS(List<Goodstable> goods) {
		// TODO Auto-generated method stub
		goodstableMapper.insertGoodssqlS(goods);
	}

	/** HGC-2019-11-18 */
	/** 查询仙玉商品销售 */
	@Override
	public List<Goodsbuyrecordsumbean> selectXianYuGoodsbuy(String time, String goodsname, int pageNum, String type) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, 10);
		return goodstableMapper.selectXianYuGoodsbuy(time, goodsname, type);
	}

	@Override
	public List<Goodsbuyrecordsumbean> selectXianYuGoodsbuyZhuZhuangTu(BigDecimal gid) {
		return goodstableMapper.selectXianYuGoodsbuyZhuZhuangTu(gid);
	}

	@Override
	public List<ShangchengshopEntity> selectShangChengShopList(String goodsid, String goodsname, int page) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 10);
		return goodstableMapper.selectShangChengShopList(goodsid, goodsname);
	}

	@Override
	public int updateShangChengShop(ShangchengshopEntity shangchengshopEntity) {
		// TODO Auto-generated method stub
		return goodstableMapper.updateShangChengShop(shangchengshopEntity);
	}

	@Override
	public int deleteShangChengShop(ShangchengshopEntity shangchengshopEntity) {
		// TODO Auto-generated method stub
		return goodstableMapper.deleteShangChengShop(shangchengshopEntity);
	}

	@Override
	public int addShangChengShop(ShangchengshopEntity shangchengshopEntity) {
		// TODO Auto-generated method stub
		return goodstableMapper.addShangChengShop(shangchengshopEntity);
	}

	@Override
	public List<GoodssaledayrecordEntity> selectGoodsBuyRecordSumList() {
		// TODO Auto-generated method stub
		return goodstableMapper.selectGoodsBuyRecordSumList();
	}

	@Override
	public int addGoodssaledayrecord(GoodssaledayrecordEntity goodssaledayrecordEntity) {
		// TODO Auto-generated method stub
		return goodstableMapper.addGoodssaledayrecord(goodssaledayrecordEntity);
	}

	@Override
	public int addGoodsBuyRecord(GoodsbuyrecordEntity goodsBuy) {
		// TODO Auto-generated method stub
		return goodstableMapper.addGoodsBuyRecord(goodsBuy);
	}
	//HGC-2020-01-20
	   @Override
	    public int updateGoodssqlS(List<Goodstable> list) {
	        return goodstableMapper.updateGoodsList(list);
	    }

	    @Override
	    public void deleteGoodsByRgidsqlS(List<BigDecimal> rgid) {
	        goodstableMapper.deleteGoodsByRgids(rgid);
	    }
}
