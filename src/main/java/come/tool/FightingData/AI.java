package come.tool.FightingData;

import java.util.ArrayList;
import java.util.List;

public class AI {
	public static int AI_BILITY=0;//概率
	public static int AI_DEATH=1;//死亡
	public static int AI_ROUND=2;//回合
	public static int AI_SKILL=3;//法术
	public static int AI_BEARSKILL=4;//承受的法术
	public static int AI_CARRYSTATE=5;//携带状态
	//出手指令判断
	public static int AI_TYPE_FY=1001;//防御
	public static int AI_TYPE_TP=1002;//逃跑
	public static int AI_TYPE_HELP=1003;//加血
	public static int AI_TYPE_SKILL=1004;//指定法术
	public static int AI_TYPE_AUTOMATE=1005;//重复指令顺序 自动化操作
	
	//回合开始判断
	public static int AI_TYPE_VIOLENT_PHY=2001;//物理狂暴 3分2追
	public static int AI_TYPE_VIOLENT_SKILL=2002;//法术狂暴 
	public static int AI_TYPE_STATE_START=2003;//回合开始附加状态
	//出手结束判断
	public static int AI_TYPE_STATE_END=3001;//出手结束附加状态
	//死亡判断
	public static int AI_TYPE_FLASH=4001;//闪现
	
    //条件=死亡-1000&死亡-1001=逃跑(救人-1001)(狂暴)
	//类型
	private int type;
	//目标id
	private int man;
	//数值
	private int value;
	//状态名
	private String state;
	//触发条件
	private List<AICondition> aiConditions;
	//自动化操作顺序集合
	private List<AIAutomate> aiAutomates;
	public AI(String type,String[] vs) {
		super();
		this.type =initType(type);
		aiAutomates=new ArrayList<>();
		for (int i = 1; i < vs.length; i++) {
			String[] v=vs[i].split("-");
			int size=Integer.parseInt(v[1]);
			value+=size;
			AIAutomate automate=new AIAutomate(v[0], v);
			for (int j = 0; j < size; j++) {
				aiAutomates.add(automate);
			}
		}
	}
	public AI(String type, int man, int value,List<AICondition> aiConditions) {
		super();
		this.type =initType(type);
		this.man = man;
		this.value = value;
		this.aiConditions = aiConditions;
	}
	public AI(String type,String state,int sum,List<AICondition> aiConditions,int value) {
		super();
		this.type =initType(type);
		this.state=state;
		this.man = sum;
		this.aiConditions = aiConditions;
		this.value=value;
	}
	//判断是否触发概率ai
	public boolean isai(int round){
		for (int i = aiConditions.size()-1; i >=0; i--) {
			AICondition point=aiConditions.get(i);
            if (point.getX()==AI_BILITY) {
				if (point.getY()<Battlefield.random.nextInt(100)) {
					return false;
				}
			}else if (point.getX()==AI_ROUND) {
				if (round!=point.getY()) {
					return false;
				}
			}else {
				return false;
			}
		}
		return true;
	}
	/**类型划分*/
	public int initType(String type){
		switch (type) {
		case "逃跑":return AI_TYPE_TP;
		case "救人":
		case "加血":return AI_TYPE_HELP;
		case "法术":return AI_TYPE_SKILL;
		case "物理狂暴":return AI_TYPE_VIOLENT_PHY;
		case "仙法狂暴":return AI_TYPE_VIOLENT_SKILL;
		case "回合状态":return AI_TYPE_STATE_START;
		case "出手状态":return AI_TYPE_STATE_END;
		case "闪现":return AI_TYPE_FLASH;		
		case "指令":return AI_TYPE_AUTOMATE;		
		
		}
		return AI_TYPE_FY;//未知类型防御	
	}
	/**判断指定是指定类型的ai  1指令ai 2回合开始  3出手结束 4死亡*/
	public boolean isAI(int type){
		if (this.type>1000&&this.type<2000) {
			return type==1;
		}else if (this.type>2000&&this.type<3000) {
			return type==2;
		}else if (this.type>3000&&this.type<4000) {
			return type==3;
		}else if (this.type>4000&&this.type<5000) {
			return type==4;
		}
		return false;		
	}
	public AIAutomate getAiAutomate(int BattleType){
		if (aiAutomates==null||aiAutomates.size()==0) {
			return null;
		}
		BattleType%=value;
		if (BattleType>=aiAutomates.size()) {
			BattleType=aiAutomates.size()-1;
		}
		return aiAutomates.get(BattleType);
	} 
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMan() {
		return man;
	}
	public void setMan(int man) {
		this.man = man;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public List<AICondition> getAiConditions() {
		return aiConditions;
	}
	public void setAiConditions(List<AICondition> aiConditions) {
		this.aiConditions = aiConditions;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<AIAutomate> getAiAutomates() {
		return aiAutomates;
	}
	public void setAiAutomates(List<AIAutomate> aiAutomates) {
		this.aiAutomates = aiAutomates;
	}
}
