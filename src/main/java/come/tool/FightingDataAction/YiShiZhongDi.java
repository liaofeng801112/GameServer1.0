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

public class YiShiZhongDi implements DataAction{

    @Override
    public void analysisAction(ManData manData, FightingEvents fightingEvents,String type,Battlefield battlefield) {
        // TODO Auto-generated method stub
        FightingSkill fightingSkill=manData.skillname(fightingEvents.getOriginator().getEndState());
        if (fightingSkill==null) return;
        String skilltype=fightingSkill.getSkilltype();
        if (!skilltype.equals("一矢中的"))return;


        // 判断是否有暗渡陈仓内丹
        FightingSkill ad = manData.skillname("暗渡陈仓");
        if (ad == null) {
            battlefield.systemMsg(manData,null,999,fightingSkill);
            return;
        }
        int camp=-1;
        int man=-1;
        if (fightingEvents.getAccepterlist()!=null&&fightingEvents.getAccepterlist().size()!=0) {
            camp=fightingEvents.getAccepterlist().get(0).getCamp();
            man=fightingEvents.getAccepterlist().get(0).getMan();
        }
        //获取作用者
        int path=battlefield.Datapath(camp,man);
        ManData data=null;
        if (path!=-1) {
            data =battlefield.fightingdata.get(path);
        }

        if (data == null || data.getStates() != 0) return;

        //一矢中的   指定向友方人物使用，使其获得自身暗渡陈仓技能的<18+16>%效果，但自己将暂时失去技能效果，效果持续#R2#G回合，冷却#R5#G回合。


        // 给自己添加debuff，暗渡陈仓效果暂时消失
        List<FightingState> list=new ArrayList<>();
        if (data.getStates() != 0) {
            return;
        }

        FightingState mys=fightingEvents.getOriginator();
        mys.setStartState("法术攻击");
        mys.setSkillsy("一矢中的");
        mys.setCamp(manData.getCamp());
        mys.setMan(manData.getMan());
        list.add(mys);
        fightingEvents.setOriginator(null);


        AddState addState = new AddState();
        addState.setStateEffect(ad.getSkillhurt());
        addState.setType(1);
        addState.setStatename("暗渡失效");
        addState.setSurplus(3);

        manData.addAddState("暗渡失效", ad.getSkillhurt(), 0, 3);			// 暗渡失效状态
        manData.addAddState("冷却", fightingSkill.getSkillid(), 0, 5);	// 一矢中的进入CD


        double xs = ad.getSkillhurt() * manData.executeYszd(1) / 100.0;
        // 队友增加暗渡状态
        data.addAddState("一矢中的", xs, 0, 2);

        // 给对象添加buff，暗渡陈仓效果提升

        FightingState firend = new FightingState();
        //firend.setStartState(TypeUtil.PTGJ);
        firend.setCamp(data.getCamp());
        firend.setMan(data.getMan());
        firend.setEndState_1("一矢中的");
        list.add(firend);

        fightingEvents.setOriginator(null);
        fightingEvents.setAccepterlist(list);
        battlefield.NewEvents.add(fightingEvents);
    }



    public ChangeFighting TypeGain(String type,double xs){
        ChangeFighting changeFighting=new ChangeFighting();
        changeFighting.setChangetype(type);
        changeFighting.setChangesum(2);
        changeFighting.setChangevlaue(xs);
        return changeFighting;
    }
}
