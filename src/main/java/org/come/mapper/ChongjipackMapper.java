package org.come.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.ChongjipackBean;

@MyBatisAnnotation
public interface ChongjipackMapper {
	/**
	 * 查询vip 分页查询
	 */
	List<ChongjipackBean> selectAllPack();
	List<ChongjipackBean> selectChongjipack(int type);
	int updateChongjipack(ChongjipackBean chongjipackBean);
	int deleteChongjipack(@Param("id") Integer id);
	int insertChongjipack(ChongjipackBean chongjipackBean);
	
}
