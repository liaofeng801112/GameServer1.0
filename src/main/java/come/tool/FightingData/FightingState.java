package come.tool.FightingData;

import java.math.BigDecimal;


/**
 * 战斗状态
 * @author Administrator
 *
 */
public class FightingState {
//  额外显示类型接受事件额外标注   伤害类型 如狂暴 躲闪 致命   
	private String proces;	
//  移动 表示 阵营|位置|方向  
	private String endState;
//  技能动画
	private String skillskin;
//  技能声音	
	private String skillsy;
//	阵营                       0  1
	private int Camp;
//	位置
	private int man;
//  发起事件的类型或者接受的事件类型     等于0代表属于接受事件   (普通攻击还是法术) 
	private String StartState;
//  召唤bb的数据
	private FightingManData data;
//  接受事件后新增的附加状态 
	private String state_1;
//  接受事件后取消的附加状态
	private String state_2;
//  接受事件后hp改变量  
	private BigDecimal hp_c;
//  接受事件后mp改变量
	private BigDecimal mp_c;
//  接受事件后怨气改变量	
	private BigDecimal yq_c;
//  接受事件后怒气改变量	
	private BigDecimal nq_c;
//  喊话
	private String text;
	private String msg;//系统提示
//  buff刷新
	private String buff;
	//血量蓝量 上限调整
	private String up; 
	//造型变换
	private String skin; 
	
	public FightingState() {
		// TODO Auto-generated constructor stub
	}
	public FightingState(int camp, int man, String startState) {
		super();
		this.Camp = camp;
		this.man = man;
		this.StartState = startState;
	}
    public int getCamp() {
		return Camp;
	}
	public void setCamp(int camp) {
		Camp = camp;
	}
	public int getMan() {
		return man;
	}
	public void setMan(int man) {
		this.man = man;
	}
	public String getStartState() {
		if (StartState==null) {
			StartState="";
		}
		return StartState;
	}
	public void setStartState(String startState) {
		StartState = startState;
	}
	public String getProcessState() {
		return proces;
	}
	public void setProcessState(String ProcessState) {
		this.proces = ProcessState;
	}
	public String getEndState_1() {
		return state_1;
	}

	public void setEndState_1(String endState_1) {
		if (this.state_1 == null || this.state_1.equals("")) {
			this.state_1 = endState_1;
		} else {
			this.state_1 = this.state_1 + "|" + endState_1;
		}
	}

	public BigDecimal getHp_Change() {
		return hp_c;
	}

	public void setHp_Change(int hp_Change) {
		if (this.hp_c == null) {
			this.hp_c = new BigDecimal(hp_Change);
		} else {
			this.hp_c = new BigDecimal(this.hp_c.intValue() + hp_Change);
		}
	}

	public BigDecimal getMp_Change() {
		return mp_c;
	}

	public void setMp_Change(int mp_Change) {
		if (this.mp_c == null) {
			this.mp_c = new BigDecimal(mp_Change);
		} else {
			this.mp_c = new BigDecimal(this.mp_c.intValue() + mp_Change);
		}
	}

	public BigDecimal getYq_c() {
		return yq_c;
	}

	public void setYq_c(int yq_c) {
		if (this.yq_c == null) {
			this.yq_c = new BigDecimal(yq_c);
		} else {
			this.yq_c = new BigDecimal(this.yq_c.intValue() + yq_c);
		}
	}

	public BigDecimal getNq_c() {
		return nq_c;
	}

	public void setNq_c(int nq_c) {
		if (this.nq_c == null) {
			this.nq_c = new BigDecimal(nq_c);
		} else {
			this.nq_c = new BigDecimal(this.nq_c.intValue() + nq_c);
		}
	}

	public FightingManData getFightingManData() {
		return data;
	}

	public void setFightingManData(FightingManData fightingManData) {
		this.data = fightingManData;
	}

	public String getEndState_2() {
		return state_2;
	}

	public void setEndState_2(String endState_2) {
		if (this.state_2 == null || this.state_2.equals("")) {
			this.state_2 = endState_2;
		} else {
			this.state_2 = this.state_2 + "|" + endState_2;
		}
	}

	public String getEndState() {
		return endState;
	}

	public void setEndState(String endState) {
		this.endState = endState;
	}

	public String getSkillskin() {
		return skillskin;
	}

	public void setSkillskin(String skillskin) {
		this.skillskin = skillskin;
	}

	public String getSkillsy() {
		return skillsy;
	}

	public void setSkillsy(String skillsy) {
		this.skillsy = skillsy;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBuff() {
		return buff;
	}

	public void setBuff(String buff) {
		if (this.buff == null || this.buff.equals("")) {
			this.buff = buff;
		} else {
			this.buff = this.buff + "|" + buff;
		}
	}

	public String getUp() {
		return up;
	}

	public void setUp(String type,int value) {
		if (this.up==null||this.up.equals("")) {
			this.up=type+"="+value;
			return;
		}
		StringBuffer buffer=new StringBuffer();
		String[] vs=this.up.split("\\|");
		for (int i = 0; i < vs.length; i++) {
			if (!vs[i].startsWith(type)) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append(type);
				buffer.append("=");
				buffer.append(value);
				
			}
		}
		if (buffer.length()!=0) {buffer.append("|");}
		buffer.append(type);
		buffer.append("=");
		buffer.append(value);
		this.up=buffer.toString();
	}

	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
