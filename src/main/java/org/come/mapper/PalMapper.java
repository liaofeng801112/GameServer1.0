package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Pal;

@MyBatisAnnotation
public interface PalMapper {
	
	// 查找所有伙伴
	List<Pal> selectAllPal();
	// 查找角色所有伙伴
	List<Pal> selectPalByRoleID( BigDecimal roleid );	
	// 删除伙伴
	void deletePal( BigDecimal id );
	// 修改伙伴属性
	void updatePal( Pal pal );
	// 创建伙伴
	void insertPal( Pal pal );
	

    /** HGC-2020-01-20 */
    /** 数据库删除伙伴 */
    void deletePalList(List<BigDecimal> list);

    /** 数据库修改伙伴属性 */
    void updatePalList(List<Pal> list);

    /** 数据库创建伙伴 */
    void insertPalList(List<Pal> list);
}
