package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Lingbao;
import org.come.entity.LingbaoRoleUser;
import org.come.mapper.LingbaoMapper;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.ILingbaoService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class LingbaoServiceImpl implements ILingbaoService {
	private LingbaoMapper lingbaoMapper;
	
	public LingbaoServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		lingbaoMapper = (LingbaoMapper) ctx.getBean("lingbaoMapper");
	}
	/**
	 * 添加灵宝
	 */
	@Override
	public void insertLingbao(Lingbao lingbao) {
		lingbao.setBaoid(RedisCacheUtil.getLingbao_pk());//获取主键
		lingbao.setSkillsum(0);
		lingbao.setFusum(0);
		lingbao.setLingbaolvl(new BigDecimal(1));
		lingbao.setLingbaoexe(new BigDecimal(0));
		lingbao.setLingbaoqihe(0);
		RedisControl.insertKeyT(RedisParameterUtil.LINGBAO, lingbao.getBaoid().toString(),lingbao);		
		RedisControl.insertListRedis(RedisParameterUtil.LINGBAO, lingbao.getRoleid().toString(), lingbao.getBaoid().toString());
		RedisControl.insertController(RedisParameterUtil.LINGBAO,lingbao.getBaoid().toString(),RedisControl.CADD);	
	}

	/**
	 * 查找角色所有灵宝
	 */
	@Override
	public List<Lingbao> selectLingbaoByRoleID(BigDecimal roleid) {
		List<Lingbao> lingbaos = RedisControl.getS(RedisParameterUtil.LINGBAO, roleid.toString(), Lingbao.class);
		return lingbaos;
	}

	@Override
	public Lingbao selectLingbaoByID(BigDecimal baoid) {
		// TODO Auto-generated method stub
		Lingbao lingbao=RedisControl.getV(RedisParameterUtil.LINGBAO, baoid.toString(),Lingbao.class);
		return lingbao;
	}
	/**
	 * 修改灵宝属性
	 */
	@Override
	public void updateLingbaoIndex(Lingbao lingbao,BigDecimal role_id) {
		BigDecimal yrid=lingbao.getRoleid();//原来id;
    	BigDecimal nrid=role_id;//新id
    	lingbao.setRoleid(role_id);
    	if (yrid!=null&&nrid!=null&&yrid.compareTo(nrid)!=0) {
    		RedisControl.deletrValue(RedisParameterUtil.LINGBAO, yrid.toString(), lingbao.getBaoid().toString());
	    	RedisControl.insertListRedis(RedisParameterUtil.LINGBAO, nrid.toString(), lingbao.getBaoid().toString());	
		}
		RedisControl.insertKeyT(RedisParameterUtil.LINGBAO,lingbao.getBaoid().toString(),lingbao);
		RedisControl.insertController(RedisParameterUtil.LINGBAO,lingbao.getBaoid().toString(),RedisControl.CALTER);	
	}
	@Override
	public void updateLingbaoRedis(Lingbao lingbao) {
		// TODO Auto-generated method stub
		RedisControl.insertKeyT(RedisParameterUtil.LINGBAO,lingbao.getBaoid().toString(), lingbao);
		RedisControl.insertController(RedisParameterUtil.LINGBAO,lingbao.getBaoid().toString(),RedisControl.CALTER);
	}
	/**
	 * 删除灵宝
	 */
	@Override
	public void deleteLingbao(BigDecimal baoid) {
		Lingbao lingbao=RedisControl.getV(RedisParameterUtil.LINGBAO, baoid.toString(),Lingbao.class);
		if (lingbao!=null) {
			RedisControl.deletrValue(RedisParameterUtil.LINGBAO,lingbao.getRoleid().toString(),baoid.toString());				
		}
		RedisControl.delForKey(RedisParameterUtil.LINGBAO, baoid.toString());
		RedisControl.insertController(RedisParameterUtil.LINGBAO,baoid.toString(),RedisControl.CDELETE);
//		lingbaoMapper.deleteLingbao(baoid);	
	}
	// 查找所有灵宝
	@Override
	public List<Lingbao> selectAllLingbao() {
		return lingbaoMapper.selectAllLingbao();
	}
	@Override
	public BigDecimal selectMaxID() {
		return lingbaoMapper.selectMaxID();
	}
	@Override
	public void updateLingbaosql(Lingbao lingbao) {
		// TODO Auto-generated method stub
		lingbaoMapper.updateLingbao(lingbao);
	}
	@Override
	public void deleteLingbaosql(BigDecimal baoid) {
		// TODO Auto-generated method stub
		lingbaoMapper.deleteLingbao(baoid);
	}
	@Override
	public void insertLingbaosql(Lingbao lingbao) {
		// TODO Auto-generated method stub
		lingbaoMapper.insertLingbao(lingbao);
	}

	@Override
	public Lingbao selectByPrimaryKey(BigDecimal baoid) {
		Lingbao lingbao2=RedisControl.getV(RedisParameterUtil.LINGBAO, baoid.toString(), Lingbao.class);
		return lingbao2;
	}
	
	private final Integer limit = 10;

	@Override
	public List<LingbaoRoleUser> selectLingBaoRU(LingbaoRoleUser lru) {
		// TODO Auto-generated method stub
		Integer start = ((lru.getPageNow() - 1) * limit) + 1;
		Integer end = start + limit;
		lru.setStart(start);
		lru.setEnd(end);
		List<LingbaoRoleUser> LingBaoRUList = lingbaoMapper.selectLingBaoRU(lru);
		return LingBaoRUList;
	}

	@Override
	public Integer selectLingBaoRUCount(LingbaoRoleUser lru) {
		// TODO Auto-generated method stub
		Integer count = lingbaoMapper.selectLingBaoRUCount(lru);
		return count;
	}
	

    @Override
    public void deleteLingbaoList(List<BigDecimal> list) {
        // TODO Auto-generated method stub
        lingbaoMapper.deleteLingbaoList(list);

    }

    @Override
    public void insertLingbaoList(List<Lingbao> list) {
        // TODO Auto-generated method stub
        lingbaoMapper.insertLingbaoList(list);
    }

    @Override
    public void updateLingbaoList(List<Lingbao> list) {
        // TODO Auto-generated method stub
        lingbaoMapper.updateLingbaoList(list);
    }

}
