package org.come.readBean;

import java.util.Map;

import org.come.model.Door;

public class AllDoorBean {

	// 所有传送门的信息
		private Map<String,Door> alldoor;

		public Map<String, Door> getAlldoor() {
			return alldoor;
		}

		public void setAlldoor(Map<String, Door> alldoor) {
			this.alldoor = alldoor;
		}
		
}
