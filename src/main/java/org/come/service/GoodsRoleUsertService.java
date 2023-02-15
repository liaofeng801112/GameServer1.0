package org.come.service;

import java.util.List;

import org.come.entity.GoodsRoleUser;

public interface GoodsRoleUsertService {

	// 人物物品查询
	List<GoodsRoleUser> selectGoodsByPage(GoodsRoleUser goodsRoleUser);

	// 查询数量
	Integer selectGoodsCount(GoodsRoleUser goodsRoleUser);

}
