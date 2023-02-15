package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Baby;

public interface IBabyService {
	
	// 查找所有宝宝
	List<Baby> selectAllBaby();
	
	// 创建宝宝
	void createBaby( Baby baby );
	
	// 查找角色所有宝宝
	List<Baby> selectBabyByRolename( BigDecimal roleid );
	// 根据ID宝宝
	Baby selectBabyById( BigDecimal babyid );
	// 修改角色所有宝宝
	void updateBaby( Baby baby );
	// 修改角色所有宝宝刚从redis取出
	void updateBabyRedis( Baby baby );

	BigDecimal selectMaxID();

	void deleteBaby(Baby baby);

	void createBabysql(Baby baby);

	void updateBabysql(Baby baby);

	void deleteBabysql(Baby baby);
	

    // HGC-2020-01-20
    /** 批量删除 */
    void deleteBabyList(List<BigDecimal> list);

    /** 批量添加 */
    void createBabyList(List<Baby> list);

    /** 批量修改 */
    void updateBabyList(List<Baby> list);

    /** 单条插入 */
    void createBabySingle(Baby baby);

    /** 单条删除 */
    void deleteBabySingle(BigDecimal babyid);
}
