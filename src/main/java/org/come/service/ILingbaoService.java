package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Lingbao;
import org.come.entity.LingbaoRoleUser;

public interface ILingbaoService {
	
	// 根据主键查找灵宝
	Lingbao selectByPrimaryKey( BigDecimal baoid );
	
	// 查找所有灵宝
	List<Lingbao> selectAllLingbao();
	
	// 创建灵宝
	void insertLingbao( Lingbao lingbao );
	
	// 查找角色所有灵宝
	List<Lingbao> selectLingbaoByRoleID( BigDecimal roleid );

	// 查找角色所有灵宝
	Lingbao selectLingbaoByID( BigDecimal baoid );

	// 修改灵宝属性
	void updateLingbaoIndex( Lingbao lingbao ,BigDecimal role_id);
	// 修改灵宝属性刚从redis取出
	void updateLingbaoRedis( Lingbao lingbao );
		
	// 删除灵宝
	void deleteLingbao( BigDecimal baoid );

	BigDecimal selectMaxID();

	void updateLingbaosql(Lingbao lingbao);

	void deleteLingbaosql(BigDecimal baoid);

	void insertLingbaosql(Lingbao lingbao);
	
	
	List<LingbaoRoleUser> selectLingBaoRU(LingbaoRoleUser lru);

	Integer selectLingBaoRUCount(LingbaoRoleUser lru);
	
	   // HGC-2020-01-20
    /** 批量删除 */
    void deleteLingbaoList(List<BigDecimal> list);

    /** 批量添加 */
    void insertLingbaoList(List<Lingbao> list);

    /** 批量修改 */
    void updateLingbaoList(List<Lingbao> list);
}
