package come.tool.FightingLingAction;

import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.ManData;

public interface LingAction {
	/**灵宝战斗行为处理**/
	void lingAction(ManData manData,List<ManData> help,FightingSkill skill,Battlefield battlefield);
}
