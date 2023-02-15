package org.come.service;

import java.util.List;

import org.come.entity.ChongjipackBean;

public interface ChongjipackServeice {
	/**
	 * 查询vip 分页查询
	 */
	List<ChongjipackBean> selectAllPack();
	List<ChongjipackBean> selectChongjipack(int type,int page);
	public int deleteChongjipack(Integer id);
	public int insertChongjipack(ChongjipackBean chongjipackBean);
	public int updateChongjipack(ChongjipackBean chongjipackBean);

}
