package org.come.service;

import java.math.BigDecimal;

import org.come.entity.GangBattle;

public interface GangBattleService {
         //添加帮派战争记录
	int addGangBattle( GangBattle gangBattle );
	     //修改帮派战争记录
	void updataGangBattle( GangBattle gangBattle );
	//查询帮派战争记录
	GangBattle selectGangBattle(BigDecimal week);
}
