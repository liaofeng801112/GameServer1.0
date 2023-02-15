package org.come.action.gl;

import com.github.pagehelper.util.StringUtil;
import come.tool.FightingData.Battlefield;
import come.tool.Stall.AssetUpdate;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.Skill;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import java.math.BigDecimal;
import java.util.List;

//TODO 灵犀修炼费用
public class LxAction implements IAction {

    /*
        Lx      灵犀选择的的是 1物理  2法术  3辅助
        Lv      修炼等级
        Point   灵犀点数
        Open    已开启的技能格子以及所加点数（第一列免费赠送）
    */
    private final String LINGXI_INIT = "Lx=0&Lv=0&Point=0&Open=11001_0|11002_0|11003_0|11004_0|11005_0|11006_0|11007_0|11026_0|11045_0|11046_0";
    private long needMoneyNum = 5000000;//灵犀修炼金钱
    private long needExperienceNum = 2000000;//灵犀修炼经验
    private long needQinmiNum = 5000;

    private long xidian = 20000;//洗点扣除仙玉

    // message = "X|ID";
    @Override
    public void action(ChannelHandlerContext ctx, String message) {

        LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
        if (loginResult==null) {
            return;
        }
        message = message.replaceAll("\"", "");
        String[] vs=message.split("&");
        if (vs.length < 2) {
            return;
        }

        /*
         * X  修炼	扣除召唤兽经验、金钱、亲密度
         * Z  修炼2	扣除召唤兽经验、金钱、亲密度
         * S  保存
         * K  开格子
         * D  洗点
         */
        RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(new BigDecimal(vs[1]));
        if (pet==null) {
            return;
        }

        String lx = pet.getLingxi();
        if (StringUtil.isEmpty(lx)) {
            lx = LINGXI_INIT;
        }

        String[] svs=lx.split("&");

        if (svs.length != 4) {
            SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R召唤兽信息出错，请联系管理员，并提供加点方案及召唤兽信息"));
            return;
        }

        if (vs[0].equals("D")) { //洗点
            //D&SID
            // 扣除玩家货币
            if (loginResult.getCodecard().longValue() < xidian) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R你的仙玉不足以支付洗点费用#17"));
                return;
            }

            loginResult.setCodecard(new BigDecimal(loginResult.getCodecard().longValue() - xidian));
            MonitorUtil.getMoney().useX(xidian);

            String srcLx = svs[3].split("=")[1];
            String[] srcJN = srcLx.split("\\|");

            for (int i = 0 ; i < srcJN.length ; i++) {
                srcJN[i] = srcJN[i].split("_")[0] + "_0";
            }

            svs[0] = "Lx=0";
            svs[3] = "Open=" + StringUtils.join(srcJN, "|");
            // -----------保存并通知客户端刷新面板
            pet.setLingxi(StringUtils.join(svs, "&"));
            AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
            AssetUpdate assetUpdate=new AssetUpdate();
            assetUpdate.setType(AssetUpdate.USEGOOD);
            assetUpdate.updata("X=-" + xidian);;
            assetUpdate.setPet(pet);
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("#G洗点成功"));
            // 通知修炼面板更新点数及召唤兽信息，灵犀面板更新点数
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().LingXiAgreement(GsonUtil.getGsonUtil().getgson().toJson(pet)));
        }

        if (vs[0].equals("K")) { //开启灵犀格
            // K&SID&SKILLID
            if (lx.indexOf(vs[2]) == -1) {
                lx += "|" + vs[2] + "_0";
            }

            // 计算需要多少灵犀丹
            String srcLx = svs[3].split("=")[1];
            String[] srcJN = srcLx.split("\\|");

            int sysum = srcJN.length - 10 + 1;
            if (sysum < 1) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R召唤兽信息出错，请联系管理员，并提供加点方案及召唤兽信息"));
                return;
            }

            // 灵犀丹
            Goodstable good = null;

            List<Goodstable> goods=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), new BigDecimal(66669));
            for (int k = 0; k < goods.size(); k++) {
                good=goods.get(k);
            }

            if (good == null) {
                SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("#R你没有灵犀丹开什么技能格#24"));
                return;
            }

            if (sysum > good.getUsetime()) {
                SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("#R你的灵犀丹不够"+sysum+"个#99"));
                return;
            }
            good.setUsetime(good.getUsetime()-sysum);


            AssetUpdate assetUpdate=new AssetUpdate();
            String msg = "";
            // 开启成功率为33%
            if (Battlefield.random.nextInt(100) < 33) {
                pet.setLingxi(lx);
                AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
                msg = "#G灵犀技能格开启成功";
                assetUpdate.setPet(pet);
            } else {
                msg = "很遗憾，开启失败#75";
            }

            AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
            assetUpdate.setType(AssetUpdate.USEGOOD);
            assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(msg));
            // 通知修炼面板更新点数及召唤兽信息，灵犀面板更新点数
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().LingXiAgreement(GsonUtil.getGsonUtil().getgson().toJson(pet)));
        }

        if (vs[0].equals("S")) { //保存加点
            // "S&"+UserMessUntil.getChosePetMes().getSid()) + "&" + "Lx=1&11001_1|11002_2"
            if (vs.length != 4) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R召唤兽信息出错，请联系管理员，并提供加点方案及召唤兽信息"));
                return;
            }

            int mb = Integer.parseInt(vs[2].split("=")[1]);  // 所选面板
            if (mb == 0) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R该召唤兽还没有选择灵犀技能类型"));
                return;
            }

            int smb = Integer.parseInt(svs[0].split("=")[1]);
            // 灵犀点数
            int point = Integer.parseInt(svs[2].split("=")[1]);
            // 原灵犀技能
            String srcLx = svs[3].split("=")[1];
            // 新灵犀技能
            String newLx = vs[3];

            // -----------对比灵犀面板：前后要一致，如果之前为0则后面可以换其他类型
            if (smb == 0){
                svs[0] = "Lx=" + mb;
            } else {
                if (smb != mb) {
                    SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R该召唤兽已选择了其他灵犀类型，如要改变类型请先清空之前的技能点数"));
                    return;
                } else {
                    svs[0] = "Lx=" + mb;
                }
            }

            // -----------对比灵犀技能点数，只能比原来的多不能比原来的少，格子只能比原来的少不能比原来的多
            String[] srcJN = srcLx.split("\\|");
            String[] newJN = newLx.split("\\|");

            XSkillLx[] xskills = new XSkillLx[srcJN.length];
            for (int i = 0; i < srcJN.length ; i++) {
                String[] sjn = srcJN[i].split("_");
                Skill skill = GameServer.getSkill(sjn[0]);
                if (skill == null) {
                    SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R召唤兽技能信息出错，请联系管理员，并提供加点方案及召唤兽信息"));
                    return;
                }
                // 原技能点数
                int count = Integer.parseInt(sjn[1]);
                xskills[i] = getXSkillLx(skill.getSkillid(), mb);
                if (xskills[i] == null) {
                    SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R召唤兽技能信息出错，请联系管理员，并提供加点方案及召唤兽信息"));
                    return;
                }

                xskills[i].setNowPoint(count);
                xskills[i].setSKill(skill);

                if (xskills[i].type != mb) {
                    xskills[i].setNowPoint(0);
                    continue;
                }

                for (String jn2 : newJN) {
                    String[] njn = jn2.split("_");
                    // 新技能点数
                    int count2 = Integer.parseInt(njn[1]);
                    if (sjn[0].equals(njn[0]) && xskills[i].nowPoint < count2) {
                        // 点数更新了则重新计算
                        xskills[i].setNowPoint(count2);
                    }
                }
            }

            // -----------校验灵犀点，不能超出所拥有的灵犀点
            if (calPoint(xskills) > point) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R当前召唤兽的灵犀点数不足"));
                return;
            }

            // 校验
            for (XSkillLx xskill : xskills) {
                if (xskill.type != mb) {
                    continue;
                }
                //未加点的技能项跳过
                if (xskill.nowPoint == 0) {
                    continue;
                }

                if (xskill.skill.getDielectric() > 0) {
                    // 有前置要求则判断
                    if (downPoint(xskills,xskill.col) < xskill.skill.getDielectric()) {
                        SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R技能" + xskill.skill.getSkillname() + "不满足前置要求无法保存"));
                        return;
                    }
                }

                //校验等级
                int lv = pet.getGrade() - 544;
                if (xskill.skill.getSkilllevel() != 0 && lv < xskill.skill.getSkilllevel()) {
                    SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R召唤兽等级不满足条件，无法保存"));
                    return;
                }

                //判断技能互斥
                if (StringUtils.isNotEmpty(xskill.skill.getSkillralation()) && getCountTemp(xskills , Integer.parseInt(xskill.skill.getSkillralation())) > 0) {
                    SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R存在互斥关系，无法保存"));
                    return;
                }
                //判断6选2
                if (!selectMax(xskills, xskill.id, xskill.type)) {
                    SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R超出可同时修炼限制，此召唤兽不可修炼此技能"));
                    return;
                }
            }

            svs[3] = getLingXiStr(xskills);
            // -----------保存并通知客户端刷新面板
            pet.setLingxi(StringUtils.join(svs, "&"));
            AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
            AssetUpdate assetUpdate=new AssetUpdate();
            assetUpdate.setType(AssetUpdate.USEGOOD);
            assetUpdate.setPet(pet);
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("#G加成完成，保存成功"));
            // 通知修炼面板更新点数及召唤兽信息，灵犀面板更新点数
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().LingXiAgreement(GsonUtil.getGsonUtil().getgson().toJson(pet)));
            return;
        }

        if (vs[0].equals("X")) { //单次修炼
            lx = addXiulian(lx,1);
            if (lx == null) {
                return;
            }
            if (!lx.startsWith("Lx")) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement(lx));
                return;
            }
            // 扣除召唤兽经验
            long exp = pet.getExp().longValue();
            if (exp < needExperienceNum) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R当前召唤兽经验不足本次修炼所需#132"));
                return;
            }
            // 扣除召唤兽亲密
            long qm = pet.getFriendliness();
            if (qm < needQinmiNum) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R当前召唤兽亲密值不足本次修炼所需#132"));
                return;
            }
            // 扣除玩家货币
            if (loginResult.getGold().longValue() < needMoneyNum) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R你的金钱不足召唤兽本次修炼所需#132"));
                return;
            }


            loginResult.setGold(new BigDecimal(loginResult.getGold().longValue() - needMoneyNum));
            MonitorUtil.getMoney().useD(needMoneyNum);
            pet.setExp(new BigDecimal(exp - needExperienceNum));
            pet.setLingxi(lx);
            pet.setFriendliness(qm - needQinmiNum);
            AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);

            AssetUpdate assetUpdate=new AssetUpdate();
            assetUpdate.setType(AssetUpdate.USEGOOD);
            assetUpdate.updata("D=-" + needMoneyNum);
            assetUpdate.setPet(pet);
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("#G修炼完成，召唤兽的修炼进度提高了1点"));
            // 通知修炼面板更新点数及召唤兽信息，灵犀面板更新点数
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().LingXiAgreement(GsonUtil.getGsonUtil().getgson().toJson(pet)));
            return;
        }

        if (vs[0].equals("Z")) { //一键修炼
            // 扣除召唤兽经验
            long exp = pet.getExp().longValue();
            if (exp < needExperienceNum) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R当前召唤兽经验不足本次修炼所需#132"));
                return;
            }
            // 扣除召唤兽亲密
            long qm = pet.getFriendliness();
            if (qm < needQinmiNum) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R当前召唤兽亲密值不足本次修炼所需#132"));
                return;
            }
            // 扣除玩家货币
            long gold = loginResult.getGold().longValue();
            if (gold < needMoneyNum) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement("#R你的金钱不足召唤兽本次修炼所需#132"));
                return;
            }
            int count = calCount(lx,exp,qm,gold);
            lx = addXiulian(lx,count);
            if (lx == null) {
                return;
            }
            if (!lx.startsWith("Lx")) {
                SendMessage.sendMessageByRoleName(loginResult.getRolename(),Agreement.getAgreement().PromptAgreement(lx));
                return;
            }

            loginResult.setGold(new BigDecimal(loginResult.getGold().longValue() - needMoneyNum * count));
            MonitorUtil.getMoney().useD(needMoneyNum * count);
            pet.setExp(new BigDecimal(exp - needExperienceNum * count));
            pet.setLingxi(lx);
            pet.setFriendliness(qm - needQinmiNum * count);
            AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);

            AssetUpdate assetUpdate=new AssetUpdate();
            assetUpdate.setType(AssetUpdate.USEGOOD);
            assetUpdate.updata("D=-" + needMoneyNum * count);
            assetUpdate.setPet(pet);
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("#G修炼完成，召唤兽的修炼进度提高了"+count+"点"));
            // 通知修炼面板更新点数及召唤兽信息，灵犀面板更新点数
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().LingXiAgreement(GsonUtil.getGsonUtil().getgson().toJson(pet)));
            return;
        }

    }

    /**
     * 组合灵犀字符串
     * @param lxs
     * @return
     */
    public String getLingXiStr(XSkillLx[] lxs) {
        StringBuilder str = new StringBuilder();
        str.append("Open=");
        for (int i = 0 ; i < lxs.length ; i++) {
            if (i != 0) {
                str.append("|");
            }
            str.append(lxs[i].id);
            str.append("_");
            str.append(lxs[i].nowPoint);
        }
        return str.toString();
    }



    /**
     * 获取技能的最大点击点数
     * @return
     */
    public int getMaxPoint(String skillId) {
        Skill skill=GameServer.getSkill(skillId);
        return (int) skill.getValue();
    }


    /**
     * 计算所消耗的灵犀点
     * @param lxs
     * @return
     */
    public int calPoint(XSkillLx[] lxs) {
        int point = 0;
        for (XSkillLx lx :lxs) {
            point += lx.nowPoint;
        }
        return point;
    }

    /**
     * 计算某一级别以下的技能点数
     * @param lxs
     * @param col
     * @return
     */
    public int downPoint(XSkillLx[] lxs, int col) {
        int point = 0;
        for (XSkillLx lx :lxs) {
            if (lx.col < col) {
                point += lx.nowPoint;
            }
        }
        return point;
    }

    /**
     * 获取某个技能的已点点数
     * @param skillId
     * @return
     */
    public int getCountTemp(XSkillLx[] lxs, int skillId) {
        for (XSkillLx xSkilllx : lxs) {
            if (xSkilllx.id == skillId) {
                return xSkilllx.nowPoint;
            }
        }
        return 0;
    }

    private static final int[] W = {11020,11021,11022,11023,11024,11025};
    private static final int[] F = {11039,11040,11041,11042,11043,11044};
    private static final int[] Z = {11058,11059,11060,11061,11062,11063};

    /**
     * 判断6选2系列技能是否可选
     * @return
     */
    public boolean selectMax(XSkillLx[] lxs, int skillId, int type) {
        // 判断当前技能是否属于6选2系列
        int idx = -1;
        int upCount = 0;	//上半部分加点项目数量
        int downCount = 0;  //下半部分加点项目数量
        switch (type) {
            case 1:
                for (int i = 0 ; i < W.length ; i++) {
                    if (W[i] == skillId) {
                        idx = i;
                        break;
                    }
                }
                if (idx == -1) {
                    // 不属于6选2直接跳过
                    return true;
                }
                for (XSkillLx xSkilllx : lxs) {
                    if (xSkilllx.id == W[0] && idx != 0 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == W[1] && idx != 1 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == W[2] && idx != 2 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == W[3] && idx != 3 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    } else
                    if (xSkilllx.id == W[4] && idx != 4 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    } else
                    if (xSkilllx.id == W[5] && idx != 5 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    }
                }
                break;
            case 2:
                for (int i = 0 ; i < F.length ; i++) {
                    if (F[i] == skillId) {
                        idx = i;
                        break;
                    }
                }
                if (idx == -1) {
                    // 不属于6选2直接跳过
                    return true;
                }
                for (XSkillLx xSkilllx : lxs) {
                    if (xSkilllx.id == F[0] && idx != 0 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == F[1] && idx != 1 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == F[2] && idx != 2 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == F[3] && idx != 3 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    } else
                    if (xSkilllx.id == F[4] && idx != 4 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    } else
                    if (xSkilllx.id == F[5] && idx != 5 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    }
                }
                break;
            case 3:
                for (int i = 0 ; i < Z.length ; i++) {
                    if (Z[i] == skillId) {
                        idx = i;
                        break;
                    }
                }
                if (idx == -1) {
                    // 不属于6选2直接跳过
                    return true;
                }
                for (XSkillLx xSkilllx : lxs) {
                    if (xSkilllx.id == Z[0] && idx != 0 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == Z[1] && idx != 1 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == Z[2] && idx != 2 && xSkilllx.nowPoint > 0) {
                        upCount++;
                    } else
                    if (xSkilllx.id == Z[3] && idx != 3 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    } else
                    if (xSkilllx.id == Z[4] && idx != 4 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    } else
                    if (xSkilllx.id == Z[5] && idx != 5 && xSkilllx.nowPoint > 0) {
                        downCount++;
                    }
                }
                break;
            default:
                return false;
        }

        if (idx < 3) {
            // 上半部分
            return upCount <= 1 && downCount == 0;
        } else {
            // 下半部分
            return downCount <= 1 && upCount == 0;
        }
    }


    /**
     * 计算最大修炼次数
     * @param lingxi
     * @param exp
     * @param qm
     * @param gold
     * @return
     */
    public int calCount(String lingxi, long exp, long qm, long gold) {
        int count = 0;

        String param[] = lingxi.split("&");
        if (param.length != 4) {
            return 0;
        }

        // 修炼等级
        int xl = Integer.parseInt(param[1].split("=")[1]);
        // 灵犀点数
        int ds = Integer.parseInt(param[2].split("=")[1]);

        // 当前级别最大修炼次数
        count = ds - xl + 1;

        // 金钱可修炼次数
        if (gold < needMoneyNum * count) {
            count = (int)(gold / needMoneyNum);
            if (count == 0) {
                return 0;
            }
        }

        // 经验可修炼次数
        if (exp < needExperienceNum * count) {
            count = (int)(exp / needExperienceNum);
            if (count == 0) {
                return 0;
            }
        }

        // 亲密可修炼次数
        if (qm < needQinmiNum * count) {
            count = (int)(qm / needQinmiNum);
            if (count == 0) {
                return 0;
            }
        }

        return count;
    }


    /**
     * 添加修炼次数
     * @param lingxi
     * @param count
     * @return
     */
    public String addXiulian(String lingxi, int count) {
        String param[] = lingxi.split("&");
        if (param.length != 4) {
            return null;
        }
        // 修炼等级
        int xl = Integer.parseInt(param[1].split("=")[1]);
        // 灵犀点数
        int ds = Integer.parseInt(param[2].split("=")[1]);

        if (ds == 175) {
            return "#R当前召唤兽的修炼等级已达到最高";
        }

        if (xl + count > ds) {
            xl = 0;
            ds++;
        } else {
            xl += count;
        }

        param[1] = param[1].split("=")[0] + "=" + xl;
        param[2] = param[2].split("=")[0] + "=" + ds;

        return StringUtils.join(param, "&");
    }


    // 纯静态的灵犀变量，记录灵犀技能的基本信息
    private final static XSkillLx[] WULI = new XSkillLx[24];
    private final static XSkillLx[] FASHU = new XSkillLx[23];
    private final static XSkillLx[] FUZHU = new XSkillLx[23];

    static {
        WULI[0] = new XSkillLx(1,11003,5,1);
        WULI[1] = new XSkillLx(1,11001,5,1);
        WULI[2] = new XSkillLx(1,11004,5,1);
        WULI[3] = new XSkillLx(1,11005,5,1);
        WULI[4] = new XSkillLx(1,11006,5,1);
        WULI[5] = new XSkillLx(1,11007,5,1);

        // 222222222222222222
        WULI[6] = new XSkillLx(1,11008,3,2);
        WULI[7] = new XSkillLx(1,11009,3,2);
        WULI[8] = new XSkillLx(1,11010,3,2);
        WULI[9] = new XSkillLx(1,11011,3,2);

        // 33333333333
        WULI[10] = new XSkillLx(1,11012,3,3);
        WULI[11] = new XSkillLx(1,11013,3,3);

        // 4444444444
        WULI[12] = new XSkillLx(1,11014,3,4);
        WULI[13] = new XSkillLx(1,11015,3,4);

        // 555555555555
        WULI[14] = new XSkillLx(1,11016,3,5);
        WULI[15] = new XSkillLx(1,11017,3,5);

        // 666666666666666
        WULI[16] = new XSkillLx(1,11018,4,6);
        WULI[17] = new XSkillLx(1,11019,4,6);

        // 7777777777777777
        WULI[18] = new XSkillLx(1,11020,30,7);
        WULI[19] = new XSkillLx(1,11021,30,7);
        WULI[20] = new XSkillLx(1,11022,30,7);
        WULI[21] = new XSkillLx(1,11023,30,7);
        WULI[22] = new XSkillLx(1,11024,30,7);
        WULI[23] = new XSkillLx(1,11025,30,7);


        //-----------------------------------------------------------------------
        FASHU[0] = new XSkillLx(2,11001,5,1);
        FASHU[1] = new XSkillLx(2,11004,5,1);
        FASHU[2] = new XSkillLx(2,11002,5,1);
        FASHU[3] = new XSkillLx(2,11005,5,1);
        FASHU[4] = new XSkillLx(2,11007,5,1);
        FASHU[5] = new XSkillLx(2,11026,5,1);

        // 222222222222222222
        FASHU[6] = new XSkillLx(2,11027,5,2);
        FASHU[7] = new XSkillLx(2,11028,4,2);
        FASHU[8] = new XSkillLx(2,11029,4,2);

        // 33333333333
        FASHU[9] = new XSkillLx(2,11031,3,3);
        FASHU[10] = new XSkillLx(2,11032,3,3);

        // 4444444444
        FASHU[11] = new XSkillLx(2,11033,3,4);
        FASHU[12] = new XSkillLx(2,11034,3,4);

        // 555555555555
        FASHU[13] = new XSkillLx(2,11035,3,5);
        FASHU[14] = new XSkillLx(2,11030,3,5);

        // 666666666666666
        FASHU[15] = new XSkillLx(2,11036,4,6);
        FASHU[16] = new XSkillLx(2,11037,4,6);

        // 7777777777777777
        FASHU[17] = new XSkillLx(2,11039,30,7);
        FASHU[18] = new XSkillLx(2,11040,30,7);
        FASHU[19] = new XSkillLx(2,11041,30,7);
        FASHU[20] = new XSkillLx(2,11042,30,7);
        FASHU[21] = new XSkillLx(2,11043,30,7);
        FASHU[22] = new XSkillLx(2,11044,30,7);

        //----------------------------------------------------------------------------
        FUZHU[0] = new XSkillLx(3,11001,5,1);
        FUZHU[1] = new XSkillLx(3,11004,5,1);
        FUZHU[2] = new XSkillLx(3,11002,5,1);
        FUZHU[3] = new XSkillLx(3,11005,5,1);
        FUZHU[4] = new XSkillLx(3,11045,5,1);
        FUZHU[5] = new XSkillLx(3,11046,5,1);

        // 222222222222222222
        FUZHU[6] = new XSkillLx(3,11047,3,2);
        FUZHU[7] = new XSkillLx(3,11048,5,2);
        FUZHU[8] = new XSkillLx(3,11049,5,2);

        // 33333333333
        FUZHU[9] = new XSkillLx(3,11050,5,3);
        FUZHU[10] = new XSkillLx(3,11051,5,3);

        // 4444444444
        FUZHU[11] = new XSkillLx(3,11052,3,4);
        FUZHU[12] = new XSkillLx(3,11053,3,4);

        // 555555555555
        FUZHU[13] = new XSkillLx(3,11054,5,5);
        FUZHU[14] = new XSkillLx(3,11055,5,5);

        // 666666666666666
        FUZHU[15] = new XSkillLx(3,11056,4,6);
        FUZHU[16] = new XSkillLx(3,11057,4,6);

        // 7777777777777777
        FUZHU[17] = new XSkillLx(3,11058,30,7);
        FUZHU[18] = new XSkillLx(3,11059,30,7);
        FUZHU[19] = new XSkillLx(3,11060,30,7);
        FUZHU[20] = new XSkillLx(3,11061,30,7);
        FUZHU[21] = new XSkillLx(3,11062,30,7);
        FUZHU[22] = new XSkillLx(3,11063,30,7);
    }


    /**
     * 根据技能类型和ID获取XSkillLx
     * @return
     */
    public XSkillLx getXSkillLx(int skillid , int type) {

        switch (type) {
            case 1:
                for (XSkillLx lx : WULI) {
                    if (lx.id == skillid) {
                        return lx;
                    }
                }
                break;
            case 2:
                for (XSkillLx lx : FASHU) {
                    if (lx.id == skillid) {
                        return lx;
                    }
                }
                break;
            case 3:
                for (XSkillLx lx : FUZHU) {
                    if (lx.id == skillid) {
                        return lx;
                    }
                }
                break;

            default:
                break;
        }
        for (XSkillLx lx : WULI) {
            if (lx.id == skillid) {
                return lx;
            }
        }
        for (XSkillLx lx : FASHU) {
            if (lx.id == skillid) {
                return lx;
            }
        }
        for (XSkillLx lx : FUZHU) {
            if (lx.id == skillid) {
                return lx;
            }
        }
        return null;
    }


    static class XSkillLx{
        public int type;
        public int id;
        public int nowPoint;
        public int maxPoint;
        public int col;
        public Skill skill;

        /**
         *
         * @param type	类型
         * @param id	技能id
         * @param maxPoint	最大点数
         * @param col		级别
         */
        public XSkillLx(int type,int id, int maxPoint, int col) {
            this.type = type;
            this.id = id;
            this.maxPoint = maxPoint;
            this.col = col;
        }

        public void setNowPoint(int nowPoint) {
            if (maxPoint >= nowPoint) {
                this.nowPoint = nowPoint;
            } else {
                this.nowPoint = maxPoint;
            }
        }

        public void setSKill(Skill skill) {
            this.skill = skill;
        }


    }


}