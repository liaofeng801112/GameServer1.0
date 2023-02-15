package org.come.readBean;

import java.util.List;

import org.come.model.ActiveAward;
import org.come.model.ActiveBase;

public class AllActive {

	private ActiveAward[] awards;//活跃奖励
	private ActiveBase[] bases;//获取活跃选项
	
	public AllActive(List<ActiveAward> awardList,List<ActiveBase> baseList) {
		super();
		awards=new ActiveAward[awardList.size()];
		for (int i = 0; i < awards.length; i++) {awards[i]=awardList.get(i);}
		
		bases=new ActiveBase[baseList.size()];
		for (int i = 0; i < bases.length; i++) {bases[i]=baseList.get(i);}
	}

	public ActiveAward[] getAwards() {
		return awards;
	}
	public void setAwards(ActiveAward[] awards) {
		this.awards = awards;
	}
	public ActiveBase[] getBases() {
		return bases;
	}
	public void setBases(ActiveBase[] bases) {
		this.bases = bases;
	}
	
}
