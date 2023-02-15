package come.tool.FightingData;

import java.util.ArrayList;
import java.util.List;

public class ChangeFighting {
    //改变的血量
	private int Changehp;
    //改变的蓝量	
	private int Changemp;
    //改变后的状态
	private int Changestate;
    //持续回合的类型
    private String Changetype;
    //持续回合的数值
    private double Changevlaue;
    //持续回合的数值2
    private double Changevlaue2;
    //持续回合的回合数	
    private int Changesum;
    //装载的技能 技能取消后产生的反应
    private List<FightingSkill> skills;
    //额外处理类型
    private String Changetype2;
    public ChangeFighting() {
		// TODO Auto-generated constructor stub
    	Changehp=0;
    	Changemp=0;
    	Changestate=0;
    	Changevlaue=0;
    	Changevlaue2=0;
    	Changesum=0;
	}
	public int getChangehp() {
		return Changehp;
	}
	public void setChangehp(int changehp) {
		Changehp = changehp;
	}
	public int getChangemp() {
		return Changemp;
	}
	public void setChangemp(int changemp) {
		Changemp = changemp;
	}
	public int getChangestate() {
		return Changestate;
	}
	public void setChangestate(int changestate) {
		Changestate = changestate;
	}
	public String getChangetype() {
		if (Changetype==null) {Changetype="";}
		return Changetype;
	}
	public void setChangetype(String changetype) {
		Changetype = changetype;
	}
	public int getChangesum() {
		return Changesum;
	}
	public void setChangesum(int changesum) {
		Changesum = changesum;
	}
	public double getChangevlaue() {
		return Changevlaue;
	}
	public void setChangevlaue(double changevlaue) {
		Changevlaue = changevlaue;
	}
	public double getChangevlaue2() {
		return Changevlaue2;
	}
	public void setChangevlaue2(double changevlaue2) {
		Changevlaue2 = changevlaue2;
	}
	public List<FightingSkill> getSkills() {
		return skills;
	}
	public void setSkills(List<FightingSkill> skills) {
		this.skills = skills;
	}
	public FightingSkill getSkill(int id) {
		if (skills==null) {
			return null;
		}
		for (int i = skills.size()-1;i>=0; i--) {
			if (skills.get(i).getSkillid()==id) {
				return skills.get(i);
			}
		}
		return null;
	}
	public void setSkill(FightingSkill skill) {
		if (skills==null) {
			skills=new ArrayList<>();
		}
		skills.add(skill);
	}
	public String getChangetype2() {
		return Changetype2;
	}
	public void setChangetype2(String changetype2) {
		Changetype2 = changetype2;
	}
	
    
}
