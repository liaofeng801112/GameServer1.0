package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Openareatable;

public interface OpenareatableService {
	// 查
	List<Openareatable> selectAllOpenareatable();

	// 增
	Integer insertOpenareatable(Openareatable openareatable);

	// 改
	Integer updateOpenareatable(Openareatable openareatable);

	// 删
	Integer deleteOpenareatable(BigDecimal tb_id);

	List<BigDecimal> selectTuijiNum(String tuiji);

	List<Openareatable> selectAllArea(BigDecimal userid);

	String selectBelong(String qid);

	String selectAtid(String qid);
}
