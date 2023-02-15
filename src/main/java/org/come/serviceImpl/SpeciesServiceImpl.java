package org.come.serviceImpl;

import java.util.List;

import org.come.entity.Species;
import org.come.mapper.SpeciesMapper;
import org.come.service.ISpeciesService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;
/**
 * 种类表操作类
 * @author 叶豪芳
 * @date : 2017年11月23日 下午5:00:59
 */
public class SpeciesServiceImpl implements ISpeciesService{
	
	private SpeciesMapper speciesMapper;
	
	public SpeciesServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		speciesMapper = (SpeciesMapper) ctx.getBean("speciesMapper");
		
	}
	
	@Override
	public List<Species> getAllSpecies() {
		
		List<Species> species = speciesMapper.getAllSpecies();
		return species;
	}

}
