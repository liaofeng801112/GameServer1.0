package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Gangapply;
import org.come.entity.Gangapplytable;
import org.come.mapper.GangapplyMapper;
import org.come.service.IGangapplyService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;
/**
 * 帮派申请表
 * @author 叶豪芳
 * @date 2017年12月21日 下午9:17:30
 * 
 */ 
public class GangapplyServiceImpl implements IGangapplyService{
	private GangapplyMapper gangapplyMapper;
	public GangapplyServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		gangapplyMapper = (GangapplyMapper) ctx.getBean("gangapplyMapper");
	}
	@Override
	public void insertIntGangapple(Gangapply gangapply) {
		// TODO Auto-generated method stub
		gangapplyMapper.insertIntGangapple(gangapply);
	}
	@Override
	public Gangapply selectGangApply(BigDecimal roleid, BigDecimal gangid) {
		// TODO Auto-generated method stub
		return gangapplyMapper.selectGangApply(roleid, gangid);
	}
	@Override
	public void deleteGangappleAll(BigDecimal roleid) {
		// TODO Auto-generated method stub
		gangapplyMapper.deleteGangappleAll(roleid);
	}
	@Override
	public void deleteGangapple(BigDecimal roleid, BigDecimal gangid) {
		// TODO Auto-generated method stub
		gangapplyMapper.deleteGangapple(roleid, gangid);
	}
	@Override
	public List<Gangapplytable> getGangapplytables(BigDecimal gangid) {
		// TODO Auto-generated method stub
		return gangapplyMapper.getGangapplytables(gangid);
	}
	@Override
	public void deleteGangappleGang(BigDecimal gangid) {
		// TODO Auto-generated method stub
		gangapplyMapper.deleteGangappleGang(gangid);
	}
	
}
