package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Pal;
import org.come.mapper.PalMapper;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.PalService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class PalServiceImpl implements PalService {
	private PalMapper palMapper;
	
	public PalServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		palMapper = (PalMapper) ctx.getBean("palMapper");
	}

	@Override
	public List<Pal> selectPalByRoleID(BigDecimal roleid) {
		// TODO Auto-generated method stub
		List<Pal> pals = RedisControl.getS(RedisParameterUtil.PAL, roleid.toString(), Pal.class);
		return pals;
	}
	@Override
	public Pal selectPalByID(BigDecimal id) {
		// TODO Auto-generated method stub
		return RedisControl.getV(RedisParameterUtil.PAL, id.toString(),Pal.class);
	}
	@Override
	public void deletePal(BigDecimal id) {
		// TODO Auto-generated method stub
		Pal pal=RedisControl.getV(RedisParameterUtil.PAL, id.toString(),Pal.class);
		if (pal!=null) {
			RedisControl.deletrValue(RedisParameterUtil.PAL,pal.getRoleId().toString(),id.toString());				
		}
		RedisControl.delForKey(RedisParameterUtil.PAL, id.toString());
		RedisControl.insertController(RedisParameterUtil.PAL,id.toString(),RedisControl.CDELETE);
	}
	@Override
	public void updatePal(Pal pal) {
		// TODO Auto-generated method stub
		RedisControl.insertKeyT(RedisParameterUtil.PAL,pal.getId().toString(), pal);
		RedisControl.insertController(RedisParameterUtil.PAL,pal.getId().toString(),RedisControl.CALTER);
	}
	@Override
	public void insertPal(Pal pal) {
		// TODO Auto-generated method stub
		pal.setId(RedisCacheUtil.getPal_pk());
		pal.setLvl(70);
		pal.setExp(0);
		pal.setGrow(1);
		RedisControl.insertKeyT(RedisParameterUtil.PAL, pal.getId().toString(),pal);		
		RedisControl.insertListRedis(RedisParameterUtil.PAL, pal.getRoleId().toString(), pal.getId().toString());
		RedisControl.insertController(RedisParameterUtil.PAL, pal.getId().toString(),RedisControl.CADD);	
	}
	@Override
	public List<Pal> selectAllPalSql() {
		// TODO Auto-generated method stub
		return palMapper.selectAllPal();
	}
	@Override
	public List<Pal> selectPalByRoleIDSql(BigDecimal roleid) {
		// TODO Auto-generated method stub
		return palMapper.selectPalByRoleID(roleid);
	}
	@Override
	public void deletePalSql(BigDecimal id) {
		// TODO Auto-generated method stub
		palMapper.deletePal(id);
	}
	@Override
	public void updatePalSql(Pal pal) {
		// TODO Auto-generated method stub
		palMapper.updatePal(pal);
	}
	@Override
	public void insertPalSql(Pal pal) {
		// TODO Auto-generated method stub
		palMapper.insertPal(pal);
	}
	

    @Override
    public void deletePalList(List<BigDecimal> list) {
        // TODO Auto-generated method stub
        palMapper.deletePalList(list);
    }

    @Override
    public void updatePalList(List<Pal> list) {
        // TODO Auto-generated method stub
        palMapper.updatePalList(list);
    }

    @Override
    public void insertPalList(List<Pal> list) {
        // TODO Auto-generated method stub
        palMapper.insertPalList(list);
    }
}
