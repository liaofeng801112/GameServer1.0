package come.tool.Calculation;

import come.tool.FightingData.FightingSkill;
import org.come.model.Skill;
import org.come.server.GameServer;

public class BaseSkill extends FightingSkill {

	private int skillId;
	private int lvl;
	private double pz;
	private Skill skill;
	private BaseQl ql;
	public BaseSkill() {
		// TODO Auto-generated constructor stub
	}
	
	/**判断是否对属性加成*/
	public boolean isAffect(){
		if ((skillId>=6001&&skillId<=6017)||skillId==6030||skillId==6035||
				skillId==6036||skillId==6039||skillId==6031||skillId==6032||
				(skillId>=8001&&skillId<=8023)||(skillId>=8030&&skillId<=8036)||
				(skillId>=8038&&skillId<=8039)) {
			return true;
		}
		return false;	
	}
	
	
	public BaseSkill(int skillId, int lvl) {
		super();
		this.skillId = skillId;
		this.lvl = lvl;
	}

	public BaseSkill(int skillId, int lvl, double pz, Skill skill) {
		super();
		this.skillId = skillId;
		this.lvl = lvl;
		this.pz = pz;
		this.skill = skill;
	}

	public BaseSkill(int skillId, int lvl, Skill skill,BaseQl ql) {
		super();
		this.skillId = skillId;
		this.lvl = lvl;
		this.skill = skill;
		this.ql = ql;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public double getPz() {
		return pz;
	}

	public void setPz(double pz) {
		this.pz = pz;
	}

	public Skill getSkill() {
		if (skill==null) {
			skill=GameServer.getSkill(skillId+"");
		}
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public BaseQl getQl() {
		return ql;
	}

	public void setQl(BaseQl ql) {
		this.ql = ql;
	}

	
	
}
