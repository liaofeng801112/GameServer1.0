package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.entity.Titletable;
/**
 * 角色称谓操作
 * @author 叶豪芳
 * @date 2017年12月25日 下午6:06:01
 * 
 */ 
public interface ITitletableService {	
	// 查询角色所有称谓
	List<Titletable> selectRoleAllTitle( BigDecimal roleid );
	// 创建称谓
	void createRoleTitle(Titletable titletable);
	
	Titletable selectRoleTitle(@Param("roleid")BigDecimal roleid,@Param("titlename")String titlename);
}
