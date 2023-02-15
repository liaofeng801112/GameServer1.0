package org.come.mapper;

import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Species;
/**
 * @author 叶豪芳
 * @date : 2017年11月23日 下午4:55:48
 */
@MyBatisAnnotation
public interface SpeciesMapper {
	
	// 获得所有初始角色信息
	List<Species> getAllSpecies();
	
}