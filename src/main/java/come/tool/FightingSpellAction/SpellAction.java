package come.tool.FightingSpellAction;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.ManData;

public interface SpellAction {
	/**法术处理**/
	void spellAction(ManData manData,FightingSkill skill,FightingEvents events,Battlefield battlefield);
}
