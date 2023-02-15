package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Titletable;
import org.come.mapper.TitletableMapper;
import org.come.service.ITitletableService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;
/**
 * 角色称谓操作
 * @author 叶豪芳
 * @date 2017年12月25日 下午6:07:53
 * 
 */ 
public class TitleServiceImpl implements ITitletableService{
	
	private TitletableMapper titletableMapper;

	public TitleServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		titletableMapper = (TitletableMapper) ctx.getBean("titletableMapper");
		
	}

	// 获得角色所有称谓
	@Override
	public List<Titletable> selectRoleAllTitle( BigDecimal roleid ) {
		List<Titletable> titletables = titletableMapper.selectRoleAllTitle(roleid);
		return titletables;
	}

	// 添加称谓
	@Override
	public void createRoleTitle(Titletable titletable) {
		titletableMapper.createRoleTitle(titletable);
	}

	@Override
	public Titletable selectRoleTitle(BigDecimal roleid, String titlename) {
		// TODO Auto-generated method stub
		return titletableMapper.selectRoleTitle(roleid,titlename);
	}

}
