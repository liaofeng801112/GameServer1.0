package org.come.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.GoodsRoleUser;

@MyBatisAnnotation
public interface GoodsRoleUsertMapper {

	// 人物物品查询
	List<GoodsRoleUser> selectGoodsByPage(@Param("gRU") GoodsRoleUser goodsRoleUser);

	// 查询数量
	Integer selectGoodsCount(@Param("gRU") GoodsRoleUser goodsRoleUser);
}
