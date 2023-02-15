package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Pal;

public interface PalService {
	
	/**reids查找角色所有伙伴*/
	List<Pal> selectPalByRoleID( BigDecimal roleid );	
	Pal selectPalByID( BigDecimal id );	
	/**reids删除伙伴*/
	void deletePal( BigDecimal id );
	/**reids数据库修改伙伴属性*/
	void updatePal( Pal pal );
	/**reids数据库创建伙伴*/
	void insertPal( Pal pal );
	
	
	/**数据库查找所有伙伴*/
	List<Pal> selectAllPalSql();
	/**数据库查找角色所有伙伴*/
	List<Pal> selectPalByRoleIDSql( BigDecimal roleid );	
	/**数据库删除伙伴*/
	void deletePalSql( BigDecimal id );
	/**数据库修改伙伴属性*/
	void updatePalSql( Pal pal );
	/**数据库创建伙伴*/
	void insertPalSql( Pal pal );
	

    /** HGC-2020-01-20 */
    /** 数据库删除伙伴 */
    void deletePalList(List<BigDecimal> list);

    /** 数据库修改伙伴属性 */
    void updatePalList(List<Pal> list);

    /** 数据库创建伙伴 */
    void insertPalList(List<Pal> list);
}
