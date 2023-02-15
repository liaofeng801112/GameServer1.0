package org.come.readBean;

import java.util.Map;

import org.come.model.EventModel;


/**
 * 所有的EventModelMap
 * 
 * @author Administrator
 * 
 */
public class AllEventModelBean {
	private Map<Integer, EventModel> allEventModelMap;
	public Map<Integer, EventModel> getAllEventModelMap() {
		return allEventModelMap;
	}
	public void setAllEventModelMap(Map<Integer, EventModel> allEventModelMap) {
		this.allEventModelMap = allEventModelMap;
	}

 }
