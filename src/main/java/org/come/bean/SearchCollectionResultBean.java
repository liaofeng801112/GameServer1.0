package org.come.bean;

import java.util.List;

import org.come.entity.Collection;

/**
 * 查询收藏返回的bean
 * @author Administrator
 *
 */
public class SearchCollectionResultBean {

	/**
	 * 查询的收藏集合
	 */
	private List<Collection> collections;
	
	/**
	 * 总页数
	 */
	private Integer total;

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
