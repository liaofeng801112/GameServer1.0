package come.tool.FightingDataAction;

import come.tool.FightingData.*;
import come.tool.FightingSpellAction.ControlAction;
import come.tool.FightingSpellAction.HurtAction;

import java.util.ArrayList;
import java.util.List;

/**
 * 归去来兮
 * @author BigGreen
 *
 */
public class LingTing implements DataAction{
    @Override
    public void analysisAction(ManData manData, FightingEvents fightingEvents,String type,Battlefield battlefield) {
        if (manData.xzstate("封印") != null || manData.xzstate("混乱") != null ) {
            return;
        }

        FightingSkill gqlx = manData.getSkillType(TypeUtil.BB_GQLX);
        // 根据亲密计算是否触发
        if (Battlefield.random.nextInt(100)>gqlx.getSkillgain()){
            return;
        }

        // 获取所有被标记的敌人
        List<ManData> datas= new ArrayList<ManData>();

        for (int i = 0; i < battlefield.fightingdata.size(); i++) {
            //if (battlefield.fightingdata.get(i).zuoyong(death, nocamp, yin, ren, point, fengyin,yao,hs))
            if (manData.getCamp() != battlefield.fightingdata.get(i).getCamp()) {
                AddState addState=battlefield.fightingdata.get(i).xzstate("1238");
                if (addState!=null) {
                    datas.add(battlefield.fightingdata.get(i));
                }
            }
        }

        if (datas.size() == 0) {
            return;
        }

        // 对被标记的敌人释放4阶火法       原始技能伤害  + 绝对伤害(亲密  * 1.33% * lv / 100 + MP * 25% * lv / 100)
        FightingSkill skill=manData.getSkillName("烈火骄阳");
        if (skill == null) {
            return;
        }

        List<FightingState> Accepterlist=new ArrayList<>();

        // 先喊一句话，如果喊话放在技能中会导致持续时间太短看不清就消失了
        FightingState chat=new FightingState();
        chat.setCamp(manData.getCamp());
        chat.setMan(manData.getMan());
        chat.setText("悲莫悲兮生别离，乐莫乐兮归来去。");
        Accepterlist.add(chat);

        FightingState Originator=new FightingState();
        Originator.setEndState(skill.getSkillname());
        Originator.setCamp(manData.getCamp());
        Originator.setMan(manData.getMan());

        double jc=manData.getSpellJC();

        // 隐藏属性增加狂暴率
        double kbl=35;

        List<FightingSkill> skills=ControlAction.getSkills(manData, skill,battlefield.BattleType);

        for (int j = datas.size()-1; j>=0; j--) {
            ManData data=datas.get(j);
            if (data.getStates()!=0) {
                continue;
            }
            FightingState Accepter=MixDeal.DSMY(manData,data,skill.getSkillid(),battlefield);
            if (Accepter==null) {
                data.addBear(skill.getSkilltype());
                Accepter=new FightingState();

                // 被动触发时伤害 = 原始技能伤害 +  绝对伤害(亲密 *1.33% + MP*20%)
                double jdsh =  gqlx.getSkillhurt() * manData.getLvl() / 15000 + manData.getMp_z() * manData.getLvl() / 1000;

                HurtAction.hurt((int)skill.getSkillhurt(), jc, jdsh, kbl, skills, Accepterlist, Accepter, manData, data, skill, battlefield);
            }else {
                Accepterlist.add(Accepter);
            }
            Accepter.setSkillskin("801");
        }

        //Originator.setText("悲莫悲兮生别离，乐莫乐兮归来去");
        Originator.setStartState("法术攻击");
        Originator.setSkillsy(skill.getSkillname());
        Accepterlist.add(Originator);
        FightingEvents Events=new FightingEvents();
        Events.setAccepterlist(Accepterlist);
        battlefield.NewEvents.add(Events);
    }

}
