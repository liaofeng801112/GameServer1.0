package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Lingbao;
import org.come.entity.LingbaoRoleUser;

@MyBatisAnnotation
public interface LingbaoMapper {
	
	// 查找所有灵宝
	List<Lingbao> selectAllLingbao();
	
	// 创建灵宝
	void insertLingbao( Lingbao lingbao );
	
	// 查找角色所有灵宝
	List<Lingbao> selectLingbaoByRoleID( BigDecimal roleid );

	// 修改灵宝属性
	void updateLingbao( Lingbao lingbao );
	
	// 删除灵宝
	void deleteLingbao( BigDecimal baoid );

	BigDecimal selectMaxID();
	

	List<LingbaoRoleUser> selectLingBaoRU(@Param("lru") LingbaoRoleUser lru);

	Integer selectLingBaoRUCount(@Param("lru") LingbaoRoleUser lru);
	
	
	   // HGC-2020-01-20
    /** 批量删除 */
    void deleteLingbaoList(List<BigDecimal> list);

    /** 批量添加 */
    void insertLingbaoList(List<Lingbao> list);

    /** 批量修改 */
    void updateLingbaoList(List<Lingbao> list);
}
