package org.come.serviceImpl;

import java.math.BigDecimal;

import org.come.entity.GangBattle;
import org.come.mapper.GangBattleMapper;
import org.come.service.GangBattleService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class GangBattleServiceImpl implements GangBattleService{
	
	private GangBattleMapper gangBattleMapper;
	
	public GangBattleServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		gangBattleMapper = (GangBattleMapper) ctx.getBean("gangBattleMapper");
	}

	@Override
	public int addGangBattle(GangBattle gangBattle) {
		// TODO Auto-generated method stub
		return gangBattleMapper.addGangBattle(gangBattle);
	}

	@Override
	public void updataGangBattle(GangBattle gangBattle) {
		// TODO Auto-generated method stub
		gangBattleMapper.updataGangBattle(gangBattle);
	}

	@Override
	public GangBattle selectGangBattle(BigDecimal week) {
		// TODO Auto-generated method stub
		return gangBattleMapper.selectGangBattle(week);
	}

	


}
