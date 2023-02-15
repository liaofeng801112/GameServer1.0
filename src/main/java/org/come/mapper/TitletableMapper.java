package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Titletable;
/**
 * 角色称谓
 * @author 叶豪芳
 * @date 2017年12月25日 下午5:29:29
 * 
 */ 
@MyBatisAnnotation
public interface TitletableMapper {
	
	// 该角色的所有称谓
	List<Titletable> selectRoleAllTitle( BigDecimal roleid );

	// 创建帮派
	void createRoleTitle(Titletable titletable);
	
	Titletable selectRoleTitle(@Param("roleid")BigDecimal roleid,@Param("titlename")String titlename);

}
