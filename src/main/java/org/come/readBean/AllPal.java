package org.come.readBean;

import java.util.concurrent.ConcurrentHashMap;

import org.come.model.PalData;

public class AllPal {
	private ConcurrentHashMap<Integer, PalData> allPalData;

	public ConcurrentHashMap<Integer, PalData> getAllPalData() {
		return allPalData;
	}
	public void setAllPalData(ConcurrentHashMap<Integer, PalData> allPalData) {
		this.allPalData = allPalData;
	}
}
