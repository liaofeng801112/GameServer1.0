package come.tool.FightingData;


public class AIAutomate {

	private String type;
	private int bs;//0正常  1表示释放法术
	private int[] skills;
	
	public AIAutomate(String type,String[] vs) {
		super();
		this.type=type;
		if (type.equals(TypeUtil.JN)) {
			bs=1;
			vs=vs[2].split("_");
			skills=new int[vs.length];
			for (int i = 0; i < skills.length; i++) {
				skills[i]=Integer.parseInt(vs[i]);
			}
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getBs() {
		return bs;
	}
	public void setBs(int bs) {
		this.bs = bs;
	}
	public int[] getSkills() {
		return skills;
	}
	public void setSkills(int[] skills) {
		this.skills = skills;
	}
	
}
