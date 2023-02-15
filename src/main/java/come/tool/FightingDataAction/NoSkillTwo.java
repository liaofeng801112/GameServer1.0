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
 * 化无技能 处理触发化无状态
 * @author Administrator
 *
 */
public class NoSkillTwo implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
			String type,Battlefield battlefield) {
		// TODO Auto-generated method stub
		AddState addState=manData.xzstate("化无");
		if (addState==null) return;
		FightingEvents hhe=new FightingEvents();
		List<FightingState> hhs=new ArrayList<>();
		ManData hhz=gethhz(addState.getStateEffect(),battlefield);
		if (hhz!=null) {
			FightingState hhze=new FightingState();
			hhze.setCamp(hhz.getCamp());
			hhze.setMan(hhz.getMan());
			hhze.setStartState(TypeUtil.JN);
			hhze.setSkillskin("1828");
			hhs.add(hhze);
		}
		FightingState fs=new FightingState();
		manData.RemoveAbnormal("化无".split("\\|"));
		fs.setCamp(manData.getCamp());
		fs.setMan(manData.getMan());
		fs.setEndState_2("化无");
		fs.setStartState(TypeUtil.JN);
		hhs.add(fs);
		hhe.setAccepterlist(hhs);
		battlefield.NewEvents.add(hhe);
	}

	
	/**
	 * 获取化无归属者
	 */
	public ManData gethhz(double ren,Battlefield battlefield){
		try {
			int camp=(int)ren;
			int man=(int)(ren*10%10);
			int path=battlefield.Datapathhuo(camp, man);
			if (path!=-1) {
				return battlefield.fightingdata.get(path);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
	}
}
