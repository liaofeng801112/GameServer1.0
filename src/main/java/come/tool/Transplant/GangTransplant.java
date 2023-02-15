package come.tool.Transplant;

import java.util.List;

import org.come.entity.Gang;

/**帮派转移*/
public class GangTransplant {

	private List<Gang> gangs;

	public GangTransplant(List<Gang> gangs) {
		super();
		this.gangs = gangs;
	}

	public List<Gang> getGangs() {
		return gangs;
	}

	public void setGangs(List<Gang> gangs) {
		this.gangs = gangs;
	}
	
}
