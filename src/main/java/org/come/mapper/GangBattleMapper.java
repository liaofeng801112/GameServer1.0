package org.come.mapper;


import java.math.BigDecimal;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.GangBattle;
/**
 * 帮派申请表
 * @author 叶豪芳
 * @date 2017年12月21日 下午9:15:58
 * 
 */ 
@MyBatisAnnotation
public interface GangBattleMapper {
	   //添加帮派战争记录
		int addGangBattle( GangBattle gangBattle );
		     //修改帮派战争记录
		void updataGangBattle( GangBattle gangBattle );
		//查询帮派战争记录
		GangBattle selectGangBattle(BigDecimal week);
	
}
