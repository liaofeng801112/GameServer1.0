package org.come.bean;

import java.util.List;
import java.util.Map;

import org.come.model.Alchemy;

public class AlchemyBean {
	
	private Map<String, List<Alchemy>> allAlchemy;

	public Map<String, List<Alchemy>> getAllAlchemy() {
		return allAlchemy;
	}

	public void setAllAlchemy(Map<String, List<Alchemy>> allAlchemy) {
		this.allAlchemy = allAlchemy;
	}
	
	

}
