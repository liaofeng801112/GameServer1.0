package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.mapper.OneArenaNotesMapper;
import org.come.redis.RedisCacheUtil;
import org.come.service.OneArenaNotesService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

import come.tool.oneArena.OneArenaNotes;

public class OneArenaNotesServiceImpl implements OneArenaNotesService {

	private OneArenaNotesMapper oneArenaNotesMapper;
	public OneArenaNotesServiceImpl() {
		// TODO Auto-generated constructor stub
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		oneArenaNotesMapper = (OneArenaNotesMapper) ctx.getBean("oneArenaNotesMapper");
	}
	@Override
	public int insertOneArenaNotes(OneArenaNotes notes) {
		// TODO Auto-generated method stub
		notes.setId(RedisCacheUtil.getOneAreanNotes_pk().longValue());
		return oneArenaNotesMapper.insertOneArenaNotes(notes);
	}
	@Override
	public BigDecimal selectMaxID(String time) {
		// TODO Auto-generated method stub
		return oneArenaNotesMapper.selectMaxID(time);
	}
	@Override
	public List<OneArenaNotes> selectRole(BigDecimal roleId, BigDecimal min) {
		// TODO Auto-generated method stub
		return oneArenaNotesMapper.selectRole(roleId, min);
	}
}
