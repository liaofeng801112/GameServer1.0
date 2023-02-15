package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.AddState;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.TypeUtil;
import come.tool.Mixdeal.AnalysisString;

public class Yao implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		int camp=-1;
		int man=-1;
		if (fightingEvents.getAccepterlist()!=null&&fightingEvents.getAccepterlist().size()!=0) {
			camp=fightingEvents.getAccepterlist().get(0).getCamp();
			man=fightingEvents.getAccepterlist().get(0).getMan();
		}
		type=fightingEvents.getOriginator().getEndState();
		//获取作用者
		int nocamp=battlefield.nomy(manData.getCamp());
		int path=battlefield.Datapath(camp,man);
		ManData mdata=null;
		if (path!=-1) {
			mdata =battlefield.fightingdata.get(path);
			nocamp=battlefield.nomy(mdata.getCamp());
		}
		int fengyin=0;
		int size=1;
		if (type.equals("清除异常状态")) {
			fengyin=1;
		}else if (type.equals("破隐")) {
			size=10;
		}else if (type.equals("惊天雷")) {
			size=5;
		}else if (type.equals("愿上钩")) {
			size=10;
		}else if (type.equals("谛听符")) {
			size=3;
		}
		List<ManData> datas=MixDeal.get(true, mdata,1,nocamp,1, 0, fengyin, 0, size, 0, battlefield,1);
		FightingEvents EVE=new FightingEvents();
		List<FightingState> Accepterlist=new ArrayList<>();	
		if (type.equals("隐身")) {
			for (int i = datas.size()-1; i >=0; i--) {
				ManData data=datas.get(i);
				data.addAddState(type, 0,0,3);
				FightingState yaostate=new FightingState();
				yaostate.setStartState(TypeUtil.JN);
				yaostate.setCamp(data.getCamp());
				yaostate.setMan(data.getMan());
				yaostate.setEndState_1(type);
				Accepterlist.add(yaostate);
			}
		}else if (type.equals("破隐")) {
            for (int i = datas.size()-1; i >=0; i--) {
            	ManData data=datas.get(i);
            	if (data.deleteState("隐身")) {
            		FightingState yaostate=new FightingState();
    				yaostate.setStartState(TypeUtil.JN);
    				yaostate.setCamp(data.getCamp());
    				yaostate.setMan(data.getMan());
    				yaostate.setEndState_2("隐身");
    				Accepterlist.add(yaostate);
				}
			}
		}else if (type.equals("清除异常状态")) {
            for (int i = datas.size()-1; i >=0; i--) {
            	ManData data=datas.get(i);
            	if (50>Battlefield.random.nextInt(100)) {
            		 if (data.deleteState(type)) {
                     	FightingState yaostate=new FightingState();
         				yaostate.setStartState(TypeUtil.JN);
         				yaostate.setCamp(data.getCamp());
         				yaostate.setMan(data.getMan());
         				yaostate.setEndState_2(type);
         				Accepterlist.add(yaostate);
     				}	
				}
			}
		}else if (type.equals("惊天雷")) {
			for (int i = datas.size()-1; i >=0; i--) {
            	ManData data=datas.get(i);
                if (data.getType()!=2) {continue;}
                if (data.getId()==5391||data.getId()==5392||data.getId()==5393) {
                	ChangeFighting changeFighting=new ChangeFighting(); 
                	changeFighting.setChangehp(-100000);
    				FightingState yaostate=new FightingState();
    				yaostate.setStartState(TypeUtil.JN);
    				data.ChangeData(changeFighting, yaostate);
    				yaostate.setSkillskin("1056");
    				Accepterlist.add(yaostate);
			    }
			}
		}else if (type.equals("愿上钩")||type.equals("弥天网")) {
			for (int i = datas.size()-1; i >=0; i--) {
            	ManData data=datas.get(i);
                if (data.getType()!=2) {continue;}
                if (data.getId()==5386||data.getId()==5387||data.getId()==5388
                	||data.getId()==5389||data.getId()==5390||data.getId()==5391
                	||data.getId()==5392||data.getId()==5393) {
                	ChangeFighting changeFighting=new ChangeFighting(); 
                	changeFighting.setChangehp(-50000);
    				FightingState yaostate=new FightingState();
    				yaostate.setStartState(TypeUtil.JN);
    				data.ChangeData(changeFighting, yaostate);
    				Accepterlist.add(yaostate);
    			}
			}
		}else if (type.equals("谛听符")) {
			for (int i = datas.size()-1; i >=0; i--) {
            	ManData data=datas.get(i);
                if (data.getType()!=2) {continue;}
                if (data.getId()==5420) {
                	ChangeFighting changeFighting=new ChangeFighting(); 
                	changeFighting.setChangehp(-50000);
                	changeFighting.setChangetype(TypeUtil.HL);
                	changeFighting.setChangevlaue(2);
    				FightingState yaostate=new FightingState();
    				yaostate.setStartState(TypeUtil.JN);
    				data.ChangeData(changeFighting, yaostate);
    				Accepterlist.add(yaostate);	
        		}
			}
		}else {
			int[] yao=AnalysisString.yao(fightingEvents.getOriginator().getEndState());
            if (manData.getSkillType(TypeUtil.TJ_SN)!=null) {
				if (yao[2]!=0) {yao[2]+=10;}
				if (yao[3]!=0) {yao[3]+=10;}
			}
            FightingSkill skill=manData.getSkillType(TypeUtil.TJ_FBYG);
			for (int i = datas.size()-1; i >=0; i--) {
            	ManData data=datas.get(i);
            	if (data.getStates()==1&&yao[0]==0&&yao[2]==0)continue;
            	ChangeFighting changeFighting=battlefield.Typeyao(data,yao); 
				FightingState yaostate=new FightingState();
				yaostate.setStartState("药");
				data.ChangeData(changeFighting, yaostate);
				Accepterlist.add(yaostate);
				AddState addState=data.xzstate(TypeUtil.TZ_CXYF);//判断是否有吹箫引凤
				if (addState!=null) {
					int[] yao2=yao.clone();
					yao2[1]=0;
					yao2[3]=0;
					yao2[0]=(int) (yao2[0]*addState.getStateEffect()/100);
					yao2[2]=(int) (yao2[2]*addState.getStateEffect()/100);
					if (yao2[0]==0&&yao2[2]==0) {continue;}
					List<ManData> datas2=MixDeal.get(false,mdata,1,nocamp,1,3, 1, 1, 5, 0, battlefield,1);
					for (int j = 0; j < datas2.size(); j++) {
						ManData data2=datas.get(j);
						changeFighting=battlefield.Typeyao(data2,yao); 
						FightingState yaostate2=new FightingState();
						yaostate2.setStartState("药");
						data2.ChangeData(changeFighting, yaostate2);
						Accepterlist.add(yaostate2);
					}
					yaostate.setEndState_2(addState.getStatename());
					data.getAddStates().remove(addState);
				}
				if (skill!=null) {
					changeFighting.setChangehp((int)(changeFighting.getChangehp()*0.2));
					changeFighting.setChangemp((int)(changeFighting.getChangemp()*0.2));	
					FightingState myao=new FightingState();
					myao.setStartState("药");
					manData.ChangeData(changeFighting, myao);
					Accepterlist.add(myao);
				}
			}
		}
		if (Accepterlist.size()!=0) {
			EVE.setAccepterlist(Accepterlist);
			battlefield.NewEvents.add(EVE);	
		}	
	}
}
