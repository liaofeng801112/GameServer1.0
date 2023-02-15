package org.come.bean;

import java.util.List;
import java.util.Map;

import org.come.model.Sghostpoint;
/**
 * 刷新信息
 * @author Administrator
 *
 */
public class SghostpointBean {
	
	// 根据类型匹配刷新信息
	private Map<String, List<Sghostpoint>> monsterTask;

	public Map<String, List<Sghostpoint>> getMonsterTask() {
		return monsterTask;
	}

	public void setMonsterTask(Map<String, List<Sghostpoint>> monsterTask) {
		this.monsterTask = monsterTask;
	}
	
	

}
