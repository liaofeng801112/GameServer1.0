package org.come.readBean;

import java.util.concurrent.ConcurrentHashMap;

import org.come.model.PetExchange;

public class allPetExchange {
	private ConcurrentHashMap<Integer, PetExchange> allPetExchange;

	public ConcurrentHashMap<Integer, PetExchange> getAllPetExchange() {
		return allPetExchange;
	}

	public void setAllPetExchange(ConcurrentHashMap<Integer, PetExchange> allPetExchange) {
		this.allPetExchange = allPetExchange;
	}

	
}
