package come.tool.FightingData;

import java.util.ArrayList;
import java.util.List;

public class AI_MixDeal {
        /**封装的ai条件判断*/
	    public static boolean isAIFull(List<AICondition> list,Battlefield battlefield,ManData data){
	    	if (list==null) {
				return true;
			}
	    	for (int i = list.size()-1; i >=0; i--) {
	    		AICondition point=list.get(i);
	            if (point.getX()==AI.AI_BILITY) {
					if (point.getY()<Battlefield.random.nextInt(100)) {
					   return false;
					}
				}else if (point.getX()==AI.AI_ROUND) {
					if (battlefield.CurrentRound<point.getY()) {
						 return false;
					}
				}else if (point.getX()==AI.AI_DEATH) {
					ManData data2=battlefield.getMonster(point.getY());
					if (data2!=null&&data2.getStates()==0) {
						 return false;
					}
				}else if (point.getX()==AI.AI_SKILL) {
					if (battlefield.JLSkills.get(point.getY())==null) {
						 return false;
					}
				}else if (point.getX()==AI.AI_BEARSKILL) {
					ManData data2=data;
					if (point.getY()!=0) {
						data2=battlefield.getMonster(point.getY());
					}
					if (data2==null||!data2.isBear(point.getSy())) {
						return false;
					}
				}else if (point.getX()==AI.AI_CARRYSTATE) {
					ManData data2=data;
					if (point.getY()!=0) {
						data2=battlefield.getMonster(point.getY());
					}
					if (data2==null||data2.xzstate(point.getSy())==null) {
						return false;
					}
				}
			}
			return true;
	    }
	    //指令ai判断
		public static AI AI_ZL(Battlefield battlefield,ManData data){
			if (data.getAis()==null) {
				return null;
			}
			if (data.getStates()!=0) {
				return null;
			}
			for (int i = data.getAis().size()-1; i >=0; i--) {
				AI ai=data.getAis().get(i);
				if (ai.isAI(1)) {
					if (isAIFull(ai.getAiConditions(), battlefield, data)) {
						 return ai;	
					}
				}
			}
			return null;
		}
		//回合开始判断
		public static void AI_KS(Battlefield battlefield){
			for (int i = battlefield.fightingdata.size()-1; i >=0; i--) {
				ManData data=battlefield.fightingdata.get(i);
				if (data.getType()!=2||data.getAis()==null) {
					continue;
				}
				if (data.getStates()!=0) {
					continue;
				}
				for (int j = data.getAis().size()-1; j >=0; j--) {
					AI ai=data.getAis().get(j);
					if (ai.isAI(2)) {
						if (isAIFull(ai.getAiConditions(), battlefield, data)) {
							int type=2003;
							if (type==AI.AI_TYPE_VIOLENT_PHY) {//物理狂暴 3分2追
								if (data.xzstate("物理狂暴")==null) {
									data.setSp(data.getSp2()+600);
								    data.getZDSKILL(1831, 50000000);
								    data.getZDSKILL(1833, 50000000);	
								    data.addAddState("物理狂暴", 0, 0, ai.getMan());
								}
							}else if (type==AI.AI_TYPE_VIOLENT_SKILL) {//法术狂暴 
								if (data.xzstate("仙法狂暴")==null) {
									data.setSp(data.getSp2()+600);
									data.getQuality().kuangbao(1);
									data.addAddState("仙法狂暴", 0, 0, ai.getMan());
								}			
							}else if (type==AI.AI_TYPE_STATE_START) {//回合开始附加状态
								if (data.addAddState(ai.getState(),0,0,ai.getValue())) {
									FightingEvents fightingEvents=new FightingEvents();
						        	FightingState org=new FightingState();
						        	org.setCamp(data.getCamp());
						        	org.setMan(data.getMan());
						        	org.setStartState("代价");
						        	org.setEndState_1(ai.getState());
						        	List<FightingState> States=new ArrayList<>();
						        	States.add(org);
						        	fightingEvents.setAccepterlist(States);
						        	battlefield.NewEvents.add(fightingEvents);
								}
								ai.setMan(ai.getMan()-1);
								if (ai.getMan()<=0) {
									data.getAis().remove(j);
								}
							}
						}
					}
				}
			}
		}
		/**出手结束附加状态*/
		public static void AI_End(Battlefield battlefield,ManData data){
			if (data==null) {return ;}
			if (data.getAis()==null) {return ;}
			if (data.getStates()!=0) {return ;}
			for (int i = data.getAis().size()-1; i >=0; i--) {
				AI ai=data.getAis().get(i);
				if (ai.isAI(3)) {
					if (isAIFull(ai.getAiConditions(), battlefield, data)) {
						if (data.addAddState(ai.getState(),0,0,ai.getValue())) {
							FightingEvents fightingEvents=new FightingEvents();
				        	FightingState org=new FightingState();
				        	org.setCamp(data.getCamp());
				        	org.setMan(data.getMan());
				        	org.setStartState("代价");
				        	org.setEndState_1(ai.getState());
				        	List<FightingState> States=new ArrayList<>();
				        	States.add(org);
				        	fightingEvents.setAccepterlist(States);
				        	battlefield.NewEvents.add(fightingEvents);
						}
						ai.setMan(ai.getMan()-1);
						if (ai.getMan()<=0) {
							data.getAis().remove(i);
						}
						return;
					}
				}
			}
		}
		/**死亡处理*/
		public static void AI_Die(Battlefield battlefield,ManData data){
			if (data.getAis()==null) {
				return ;
			}
			if (data.getStates()!=0) {
				return ;
			}
			for (int i = data.getAis().size()-1; i >=0; i--) {
				AI ai=data.getAis().get(i);
				if (ai.isAI(4)) {
					if (isAIFull(ai.getAiConditions(), battlefield, data)) {	
						
						return;
					}
				}
			}
		}
}
