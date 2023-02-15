package org.come.bean;

import java.util.Map;

/**
 * 表分区Bean
 * @author HGC-2020-01-02
 *
 */
public class TableSpaceBean {
	private String path;
	
	private Map<String, String> tableName;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getTableName() {
		return tableName;
	}

	public void setTableName(Map<String, String> tableName) {
		this.tableName = tableName;
	}
	
	

}
