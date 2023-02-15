package org.come.mapper;

import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.ChongjipackBean;

@MyBatisAnnotation
public interface PackGradeMapper {
	
	List<ChongjipackBean> selectAllPack();
}
