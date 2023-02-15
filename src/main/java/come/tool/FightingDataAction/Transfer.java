package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.TypeUtil;

/**
 * 转移状态 移花接木
 * @author Administrator
 *
 */
public class Transfer implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
	//选人判断
		ManData data=null;
	       if (fightingEvents.getAccepterlist()!=null&&fightingEvents.getAccepterlist().size()!=0) {
	    	   FightingState ac=fightingEvents.getAccepterlist().get(0);
	       if (ac.getCamp()!=manData.getCamp()||ac.getMan()!=manData.getMan()) {
			int path=battlefield.Datapathhuo(ac.getCamp(), ac.getMan());	
		   if (path!=-1) {
				data=battlefield.fightingdata.get(path);
		   }
		   }
		   }
		if (data==null) {
			List<Integer> lists=new ArrayList<>();
			for (int i = 0; i < battlefield.fightingdata.size(); i++) {
				ManData data2=battlefield.fightingdata.get(i);
				if (data2.getStates()==0&&data2.getType()!=3&&data2.getType()!=4) {
					if (data2.getCamp()==manData.getCamp()&&data2.getMan()==manData.getMan())continue;
					lists.add(i);
				}
			}
			if (lists.size()!=0)
			data=battlefield.fightingdata.get(Battlefield.random.nextInt(lists.size()));
		    
		}
		if (data==null) {
			FightingState Originator=fightingEvents.getOriginator();
			if (manData.daijia(Originator,battlefield)) {
				return;
			}
			Originator.setStartState("法术攻击");
			Originator.setSkillsy("移花接木");
			fightingEvents.setOriginator(null);
			List<FightingState> Accepterlist=new ArrayList<>();
			Accepterlist.add(Originator);
			fightingEvents.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(fightingEvents);
			return;
		}    
		List<FightingState> myfStates=new ArrayList<>();
		FightingState Originator=fightingEvents.getOriginator();
		if (manData.daijia(Originator,battlefield)) {
			return;
		}
		Originator.setStartState("法术攻击");
		Originator.setSkillsy("移花接木");
		myfStates.add(Originator);
		List<FightingState> nofStates=new ArrayList<>();
		FightingState acc=new FightingState();
		acc.setCamp(data.getCamp());
		acc.setMan(data.getMan());	
		acc.setStartState(TypeUtil.JN);
		acc.setSkillskin("1215");
		nofStates.add(acc);
		List<AddState> myStates=tiqu(manData, myfStates);
		List<AddState> noStates=tiqu(data, nofStates);
		add(manData, noStates, myfStates);
		add(data, myStates, nofStates);
		for (int i = 0; i < nofStates.size(); i++) {
			myfStates.add(nofStates.get(i));
		}
		FightingEvents events=new FightingEvents();
		events.setAccepterlist(myfStates);
		battlefield.NewEvents.add(events);
	}

	/**
	 * 提取出(盘，牛，速，冰，混，睡，忘，毒， 魅惑)
	 */
	public List<AddState> tiqu(ManData data,List<FightingState> fightingStates){
		int jl=0;
		boolean isyw=false;
		List<AddState> States=new ArrayList<>();
		for (int i = data.getAddStates().size()-1; i >=0 ; i--) {
			AddState addState=data.getAddStates().get(i);
			String type=addState.getStatename();
			if (type.equals("力量")||type.equals("加速")||type.equals("抗性")||type.equals("遗忘")||
				type.equals("混乱")||type.equals("封印")||type.equals("昏睡")||type.equals("中毒")) {
				if ((type.equals("遗忘")&&!isyw)||!type.equals("遗忘")) {
					FightingState fightingState=null;
					if (fightingStates.size()<=jl) {
						fightingState=new FightingState();
						fightingState.setStartState("代价");
						fightingState.setCamp(data.getCamp());
						fightingState.setMan(data.getMan());
						fightingStates.add(fightingState);
					}else {
						fightingState=fightingStates.get(jl);
					}
					fightingState.setEndState_2(type);
					jl++;
				}
				States.add(addState);
				data.getAddStates().remove(i);
				if (type.equals("遗忘"))isyw=true;
			}
		}
		return States;
	}
	/**
	 * 添加提取出(盘，牛，速，冰，混，睡，忘，毒， 魅惑)
	 */
	public void add(ManData data,List<AddState> addStates,List<FightingState> fightingStates){
		int jl=0;
		boolean isyw=false;
		for (int i = addStates.size()-1; i >=0 ; i--) {
			AddState addState=addStates.get(i);
			String type=addState.getStatename();
				if ((type.equals("遗忘")&&!isyw)||!type.equals("遗忘")) {
					FightingState fightingState=null;
					if (fightingStates.size()<=jl) {
						fightingState=new FightingState();
						fightingState.setStartState("代价");
						fightingState.setCamp(data.getCamp());
						fightingState.setMan(data.getMan());
						fightingStates.add(fightingState);
					}else {
						fightingState=fightingStates.get(jl);
					}
					fightingState.setEndState_1(type);
					jl++;
				}
				data.getAddStates().add(addState);
				if (type.equals("遗忘"))isyw=true;
		}
	}
}
