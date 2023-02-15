package org.come.readBean;

import java.util.concurrent.ConcurrentHashMap;

import org.come.model.TaskData;
import org.come.model.TaskSet;

public class AllTask {
	//任务
	private ConcurrentHashMap<Integer, TaskData> allTaskData;
	private ConcurrentHashMap<Integer, TaskSet>  allTaskSet;
	public ConcurrentHashMap<Integer, TaskData> getAllTaskData() {
		return allTaskData;
	}
	public void setAllTaskData(ConcurrentHashMap<Integer, TaskData> allTaskData) {
		this.allTaskData = allTaskData;
	}
	public ConcurrentHashMap<Integer, TaskSet> getAllTaskSet() {
		return allTaskSet;
	}
	public void setAllTaskSet(ConcurrentHashMap<Integer, TaskSet> allTaskSet) {
		this.allTaskSet = allTaskSet;
	}
	
	
}
