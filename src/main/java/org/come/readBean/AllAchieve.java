package org.come.readBean;

import java.util.List;

import org.come.model.Achieve;

/**所有达成目标*/
public class AllAchieve {
	
	private List<Achieve> achieves;

	public AllAchieve(List<Achieve> achieves) {
		super();
		this.achieves = achieves;
	}
	public List<Achieve> getAchieves() {
		return achieves;
	}
	public void setAchieves(List<Achieve> achieves) {
		this.achieves = achieves;
	}
	
}
