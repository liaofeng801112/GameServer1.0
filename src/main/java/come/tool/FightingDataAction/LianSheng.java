package come.tool.FightingDataAction;

import java.util.ArrayList;
import java.util.List;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.ChangeFighting;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingPackage;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.GroupBuff;
import come.tool.FightingData.ManData;
import come.tool.FightingData.MixDeal;
import come.tool.FightingData.PK_MixDeal;
import come.tool.FightingData.TypeUtil;

/**
 * 莲生被动技能天降流火
 *
 * @author sinmahod
 * @date 21-06-01
 */
public class LianSheng implements DataAction {

    @Override
    public void analysisAction(ManData manData, FightingEvents fightingEvents, String type, Battlefield battlefield) {
        boolean isHSSF = TypeUtil.BB_E_HSSF.equals(type);

        if (!type.equals("兵临城下")) {
            type = TypeUtil.PTGJ;
        }

        FightingSkill fightingSkill = manData.getSkillType(TypeUtil.BB_TJLH);

        // 自动攻击时取随机目标
        int nocamp = MixDeal.getcamp(type, manData.getCamp(), battlefield.nomy(manData.getCamp()));
        ManData noman = PhyAttack.getdaji(nocamp, fightingEvents, battlefield, manData, fightingSkill.getSkillhurt());
        if (noman == null)
            return;


        double ljl;
        int ljv;
        long ap;
        long zap = 0;
        long zap7 = 0;
        long zap8 = 0;
        List<FightingEvents> gjEventss = new ArrayList<>();
        FightingSkill skill_4 = null, skill_5 = null, skill_6 = null, skill_7 = null, skill_8 = null, skill_9 = null;// 箭无虚发,一帆风顺,一鼓作气,同仇敌忾,一击毙命,横扫四方,莲火流星
        for (int i = manData.getSkills().size() - 1; i >= 0; i--) {
            FightingSkill sl = manData.getSkills().get(i);
            if (sl.getSkillbeidong() != 1) {
                continue;
            }
            if (sl.getSkilltype().equals(TypeUtil.BB_E_TCDK)) {
                skill_4 = sl;
            } else if (sl.getSkilltype().equals(TypeUtil.BB_E_YJBM)) {
                if (!PK_MixDeal.isPK(battlefield.CurrentRound)) {
                    skill_5 = sl;
                }
            } else if (sl.getSkilltype().equals(TypeUtil.BB_E_HSSF)) {
                skill_6 = sl;
            } else if (sl.getSkilltype().equals(TypeUtil.BB_E_DSFS)) {
                skill_7 = sl;
                zap7 = (long) (manData.getKangluobao() * 175);
            } else if (sl.getSkilltype().equals(TypeUtil.BB_E_QKYZ)) {
                skill_8 = sl;
                zap8 = PhyAttack.getMoney(manData, battlefield);
                if (zap8 >= 500000) {
                    zap8 = 500000;
                }
                zap8 = (long) (zap8 / 2.8);
            } else if (sl.getSkilltype().equals(TypeUtil.BB_E_LHLX)) {
                skill_9 = sl;
            }
        }

        List<ManData> zzs = null;
        if (skill_4 != null && Battlefield.isV(skill_4.getSkillgain())) {
            zzs = new ArrayList<>();
            for (int i = battlefield.fightingdata.size() - 1; i >= 0; i--) {
                ManData data = battlefield.fightingdata.get(i);
                if (data.getType() != 1 || data.getCamp() != manData.getCamp() || data.getStates() != 0) {
                    continue;
                }
                if (data.getMan() == manData.getMan()) {
                    continue;
                }
                zap += data.getAp();
                zzs.add(data);
            }
            if (zap >= 150000) {
                zap = 150000;
            }
        }

        ljl = manData.getQuality().getRolefljl() - (noman.getSkillType(TypeUtil.TJ_YCDY) == null ? 0 : 15);
        ljv = (int) manData.getQuality().getRolefljv();
        FightingSkill skill5 = manData.getAppendSkill(9811);
        if (skill5 != null) {
            ljl -= skill5.getSkillhurt();
        }


        // 记录最后一次伤害的数值
        long lastAP = manData.getAp();
        // 是否追击
        boolean zhui = false;
        // 是否分裂
        boolean fen = false;
        //被分裂人
        ManData fenman = null;

        //攻击次数
        int maxg = 1;
        // 怒不可揭层数
        int nbkj = 0;
        // 已攻击次数
        int g = 0;

        // 触发战意分裂
        boolean zy = false;
        // 势不可遏附加伤害
        long sbke = 0;

        // 将功补过判定，等于true则回合末尾执行
        boolean jgbg = (maxg == 1);

        while (noman.getStates() == 0 && g < maxg) {

            // 判断是否连击
            if (maxg == 1) {
                maxg = PhyAttack.GMax(manData, noman, 0, battlefield);
                if (PK_MixDeal.isPK(battlefield.BattleType)) {
                    // 如果对方有牵制技能则连击次数，不超过3次
                    GroupBuff buff = battlefield.getBuff(noman.getCamp(), TypeUtil.BB_QZ);
                    if (buff != null) {
                        if (maxg > 3) {
                            maxg = 3;
                        }
                    }
                }
            }

            // 先射箭
            FightingEvents gjEvents = new FightingEvents();
            List<FightingState> list = new ArrayList<>();

            // 处理灵犀-火冒三丈、攻守兼备
            int hmgs = manData.executeHmsz(list);
            if (hmgs == 2) {
                // 势不可揭额外攻击
                sbke = manData.getAp();
            } else {
                sbke = 0;
            }

            // 本次攻击为连击，并且为连击的第一次攻击
            if (g == 0 && maxg > 1) {
                // 处理灵犀-惊涛拍岸
                maxg = manData.executeJtpa(list,maxg);
                jgbg = false;
            }

            // 莲生原地射箭
            /**
             FightingState gjz = new FightingState();
             gjz.setCamp(manData.getCamp());
             gjz.setSkillsy(fightingSkill.getSkillname());
             gjz.setMan(manData.getMan());
             gjz.setText(fightingSkill.getSkillname());
             gjz.setStartState("特效2"); // 特效2时间稍短 特效2<法术攻击<特效1
             fightingEvents.setAccepterlist(list);
             battlefield.NewEvents.add(fightingEvents);
             */
            //着一端射箭速度贼快
            FightingState gjz = new FightingState();
            gjz.setStartState("特技2");
            gjz.setSkillsy(fightingSkill.getSkillname());
            gjz.setCamp(manData.getCamp());
            gjz.setMan(manData.getMan());
            gjz.setText(fightingSkill.getSkillname());
            list.add(gjz);
            fightingEvents.setAccepterlist(list);
            battlefield.NewEvents.add(fightingEvents);

            if (zhui) {
                // 怒不可揭判定
                if (manData.executeNbkj(nbkj++)) {
                    FightingState gjz2 = new FightingState();
                    gjz2.setCamp(manData.getCamp());
                    gjz2.setMan(manData.getMan());;
                    gjz2.setText("看我的#G怒不可揭");
                    gjz2.setStartState("代价");
                    list.add(0,gjz2);
                }
                gjz.setSkillskin("1831");
            } else if (fen) {
                // 怒不可揭判定
                if (manData.executeNbkj(nbkj++)) {
                    FightingState gjz2 = new FightingState();
                    gjz2.setCamp(manData.getCamp());
                    gjz2.setMan(manData.getMan());;
                    gjz2.setText("看我的#G怒不可揭");
                    gjz2.setStartState("代价");
                    list.add(0,gjz2);
                }
                gjz.setSkillskin("1832");
            } else if (zy) {
                // 怒不可揭判定
                if (manData.executeNbkj(nbkj++)) {
                    FightingState gjz3 = new FightingState();
                    gjz3.setCamp(manData.getCamp());
                    gjz3.setMan(manData.getMan());;
                    gjz3.setText("看我的#G怒不可揭");
                    gjz3.setStartState("代价");
                    list.add(0,gjz3);
                }
                FightingState gjz2 = new FightingState();
                gjz2.setCamp(manData.getCamp());
                gjz2.setMan(manData.getMan());;
                gjz2.setText("看我的#G冲冠一怒");
                gjz2.setStartState("代价");
                list.add(0,gjz2);
                gjz.setSkillskin("cgyn");
            }

            list.add(gjz);
            gjEvents.setAccepterlist(list);
            gjEventss.add(gjEvents);



            FightingEvents gjEvents2 = new FightingEvents();
            List<FightingState> zls = new ArrayList<>();
            gjEvents2.setAccepterlist(zls);
            gjEventss.add(gjEvents2);

            ap = (int)Math.ceil(manData.getAp() * 1.2);
            ap += zap + sbke;


            zhui = false;
            g++;

            // 对目标释放天降流火
            FightingState ace = new FightingState();
            ace.setStartState("被攻击");
            ace.setSkillskin("1237");
            if (skill_7 != null && Battlefield.isV(skill_7.getSkillgain())) {
                ap += zap7;
                gjz.setText("大圣附身#2");
                //ace.setSkillskin(TypeUtil.BB_E_DSFS);
            } else if (skill_8 != null && Battlefield.isV(skill_8.getSkillgain())) {
                ap += zap8;
                gjz.setText("乾坤一掷#2");
                //ace.setSkillskin(TypeUtil.BB_E_QKYZ);
            }
            ap = PhyAttack.Hurt(ap, g, manData, noman, type, ace,zls, battlefield,10,10);
            lastAP = ap / 4;

            // 灵犀-化险为夷
            manData.addDun(ap, gjz);

            if (skill_9 != null) {
                // 携带莲火流星觉醒技溅射伤害增加25%，这里用乘法计算，大概为原始伤害的56%（此溅射伤害不计算物理抗性）这个不是死亡时的溅射，是兽装溅射
                if (skill_9.getSkillhurt() > 0) {
                    lastAP += lastAP * 100 / skill_9.getSkillhurt();
                }
                if (Battlefield.isV(skill_9.getSkillgain())) {
                    //触发莲火流星特效改变
                    gjz.setStartState("特效1");
                    ace.setSkillskin("800");
                    gjz.setText("莲火流星#2");
                    //触发莲火流星后溅射范围2的所有敌人
                    List<ManData> datas = battlefield.getZW2(noman);
                    // 要让怪物同时掉血必须把State装在同一个List里面
                    for (int i = datas.size() - 1; i >= 0; i--) {
                        FightingState ace1 = new FightingState();
                        ManData nomyData2 = datas.get(i);
                        if (nomyData2.getStates() != 0)
                            continue;
                        ChangeFighting acec1 = new ChangeFighting();
                        int currAp = PhyAttack.Hurt(lastAP, g, manData, nomyData2, type, ace1,zls, battlefield,10,10);
                        acec1.setChangehp(-currAp);
                        FightingPackage.ChangeProcess(acec1, null, nomyData2, ace1, type, zls, battlefield);

                    }
                }
            }




            ChangeFighting acec = new ChangeFighting();
            acec.setChangehp((int) -ap);

            if (skill_5 != null && Battlefield.isV(skill_5.getSkillgain())) {
                FightingPackage.ChangeProcess(acec, null, noman, ace, TypeUtil.ZSSH, zls, battlefield);
                ace.setText("不堪一击的选手#2");
            } else {
                FightingPackage.ChangeProcess(acec, null, noman, ace, TypeUtil.PTGJ, zls, battlefield);
            }
            PhyAttack.feedback(type, manData, ap, battlefield, zls);
            PhyAttack.neidan(type, manData, noman, ap, battlefield, zls, g, 0, 0);

            if (zap != 0 && zzs != null) {
                ace.setText("合力一击#2");
                for (int k = 0; k < zzs.size(); k++) {
                    ManData zz = zzs.get(k);
                    zls.add(MixDeal.skillmove(noman, zz, "9"));
                }
            }



            // 横扫四方
            if (skill_6 != null && (isHSSF || Battlefield.isV(skill_6.getSkillgain() / 20))) {
                List<ManData> datas = battlefield.getZW(noman);
                for (int j = datas.size() - 1; j >= 0; j--) {
                    FightingState ace1 = new FightingState();
                    ManData nomyData2 = datas.get(j);
                    if (nomyData2.getStates() != 0)
                        continue;
                    ChangeFighting acec1 = new ChangeFighting();
                    ap = PhyAttack.Hurt((long) (manData.getAp() * (skill_6.getSkillgain()) / 100D), g, manData,
                            nomyData2, type, ace1,zls, battlefield,10,10);
                    acec1.setChangehp((int) -ap);
                    FightingPackage.ChangeProcess(acec1, null, nomyData2, ace1, type, zls, battlefield);
                }
            }

            if (noman.getStates()!=0||noman.xzstate(TypeUtil.FY)!=null){//死亡中断连击
                g=maxg;

                if (noman.getStates()!=0) {
                    // 目标死亡判定一往无前
                    manData.executeYwwq(gjz);
                }
            }

            zy = false;
            // 灵犀-战意分裂（不加追击数）
            if (g == maxg && manData != null && manData.getStates() == 0) {
                if (manData.executeZhanyi()) {
                    List<ManData> zjdata=MixDeal.get(false, noman,0,manData.getCamp(), 0, 0, 0, 0, 1, -1, battlefield,1);
                    if (zjdata.size()!=0) {
                        noman = zjdata.get(0);// 获取分裂人
                        zy = true;
                        g = 0;
                        maxg = 1;
                        continue;
                    }
                }
            }

            fen = false;
            // 先判断是否有分裂攻击，再判定是否触发
            FightingSkill skill = manData.getSkillType("分裂");
            if (g == maxg && skill != null) {
                if (Battlefield.random.nextInt(100) < manData.getSkillType("分裂").getSkillhurt()) {
                    List<ManData> zjdata=MixDeal.get(false, noman,0,manData.getCamp(), 0, 0, 0, 0, 1, -1, battlefield,1);
                    if (zjdata.size()!=0) {
                        noman = zjdata.get(0);// 获取分裂人
                        fen = true;
                        g = 0;
                        maxg = 1;
                        continue;
                    }
                }
            }


            // 被打击人已经死亡
            if (noman.getStates() != 0) {  // 0
                // 被攻击致死触发溅射,周围人受到伤害
                // 被动技能不打自己人
                if ((nocamp == manData.getCamp() || nocamp == -1)) {
                    // 默认的被动技能只取一格范围
                    List<ManData> datas = battlefield.getZW(noman);
                    FightingEvents gjEventsZhui = new FightingEvents();
                    // 要让怪物同时掉血必须把State装在同一个List里面
                    List<FightingState> listZhui = new ArrayList<>();
                    for (int i = datas.size() - 1; i >= 0; i--) {
                        FightingState ace1 = new FightingState();
                        ManData nomyData2 = datas.get(i);
                        if (nomyData2.getStates() != 0)
                            continue;
                        ChangeFighting acec1 = new ChangeFighting();
                        // 伤害不再单独计算
                        // ap=PhyAttack.Hurt(Zap,g, manData, nomyData2, type, ace1, battlefield);
                        acec1.setChangehp((int)-lastAP);
                        FightingPackage.ChangeProcess(acec1, null, nomyData2, ace1, type, listZhui, battlefield);

                    }
                    gjEventsZhui.setAccepterlist(listZhui);
                    gjEventss.add(gjEventsZhui);
                }

                // 先判断是否有分花拂柳，再判定是否触发
                FightingSkill skillFen = manData.getSkillType("追击");
                if (skillFen != null) {
                    if (Battlefield.random.nextInt(100) < skillFen.getSkillhurt()) {
                        List<ManData> zjdata = MixDeal.get(false, noman, 0, manData.getCamp(), 0, 0, 0, 0, 1, -1,
                                battlefield, 1);
                        if (zjdata.size() != 0) {
                            noman = zjdata.get(0);// 获取追击人
                            zhui = true;
                            g = 0;
                            maxg = 1; // 追击时重新计算连击
                        }
                    }
                }
            }
        }


        for (int i = 0; i < gjEventss.size(); i++) {
            battlefield.NewEvents.add(gjEventss.get(i));
        }

        // 从头到尾没有触发连击，判定将功补过
        if (jgbg) {
            manData.executeJgbg(battlefield.NewEvents.get(battlefield.NewEvents.size()-1).getAccepterlist());
        }
    }
}
