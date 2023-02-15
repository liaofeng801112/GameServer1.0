package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.entity.Gangapply;
import org.come.entity.Gangapplytable;

/**
 * 帮派申请表
 * @author 叶豪芳
 * @date 2017年12月21日 下午9:16:33
 * 
 */ 
public interface IGangapplyService {

	/**添加帮派申请人*/
	void insertIntGangapple( Gangapply gangapply );
	/**查找帮派申请表中是否有此人*/ 
	Gangapply selectGangApply(@Param("roleid")BigDecimal roleid,@Param("gangid")BigDecimal gangid);
	/**删除该申请人 全部*/
	void deleteGangappleAll(BigDecimal roleid);
	/**删除该申请人 局部*/
	void deleteGangapple(@Param("roleid")BigDecimal roleid,@Param("gangid")BigDecimal gangid);
	/**删除该申请人 清空帮派申请*/
	void deleteGangappleGang(BigDecimal gangid);
	
	/**帮派申请表信息*/
	List<Gangapplytable> getGangapplytables( BigDecimal gangid );
	
}
