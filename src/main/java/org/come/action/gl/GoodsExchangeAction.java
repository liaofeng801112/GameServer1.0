package org.come.action.gl;

import come.tool.Stall.AssetUpdate;
import io.netty.channel.ChannelHandlerContext;
import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.GoodsExchange;
import org.come.protocol.Agreement;
import org.come.redis.RedisCacheUtil;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodsExchangeAction implements IAction {
    @Override
    public void action(ChannelHandlerContext ctx, String message) {
        GoodsExchange goodsExchange = GsonUtil.getGsonUtil().getgson().fromJson(message, GoodsExchange.class);
        goodschange(goodsExchange, ctx);
    }
    /**兑换物品*/
    public void goodschange(GoodsExchange goodsExchange, ChannelHandlerContext ctx){
        LoginResult loginResult= GameServer.getAllLoginRole().get(ctx);
        if (loginResult==null) {return;}
        GoodsExchange exchange=goodsExchange;
        Goodstable goodss= exchange!=null?GameServer.getAllGoodsMap().get(exchange.getGid()):null;
        if (goodss==null) {
            SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("错误兑换公式"));
            return;
        }
        goodss.setRole_id(loginResult.getRole_id());
        goodss.setRgid(RedisCacheUtil.getGoods_pk());
        goodss.setStatus(0);
        //验证消耗
        List<Goodstable> list=new ArrayList<>();
        long money=0;
        String goodName = null;
        String[] v=exchange.getConsume().split("\\|");

        for (int i = 0; i < v.length; i++) {
            if (v[i].startsWith("D")) {
                money=Long.parseLong(v[i].substring(2));
                if (loginResult.getGold().longValue()<money){
                    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("金钱不够"+money));
                    return;
                }
            }else if (v[i].startsWith("G")) {
                String[] vs=v[i].split("=");
                BigDecimal goodid=new BigDecimal(vs[1]);
                int sum=Integer.parseInt(vs[2]);
                Goodstable goodstable=GameServer.getAllGoodsMap().get(goodid);
                if (goodstable==null) {
                    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("错误兑换公式"));
                    return;
                }
                List<Goodstable> goods= AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), goodid);
                int SYsum=sum;
                for (int k = 0; k < goods.size(); k++) {
                    Goodstable good=goods.get(k);
                    if (goodName==null) {
                        goodName=good.getGoodsname();
                    }
                    if (good.getUsetime()>=SYsum) {
                        good.setUsetime(good.getUsetime()-SYsum);
                        SYsum=0;
                        list.add(good);
                        break;
                    }
                    SYsum-=good.getUsetime();
                    good.setUsetime(0);
                    list.add(good);
                }
                if (SYsum>0) {
                    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不够"+sum+"个"+goodstable.getGoodsname()));
                    return;
                }
            }
        }
        AssetUpdate assetUpdate=new AssetUpdate();
        assetUpdate.setType(AssetUpdate.USEGOOD);
        if (money!=0) {
            loginResult.setGold(loginResult.getGold().add(new BigDecimal(-money)));
            MonitorUtil.getMoney().useD(money);
        }
        assetUpdate.updata("D=-"+money);
        if (list.size()!=0) {
            for (int i = 0; i < list.size(); i++) {
                Goodstable good=list.get(i);
                AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
                assetUpdate.updata("G"+good.getRgid()+"="+good.getUsetime());
            }
        }
        // 判断该角色是否拥有这件物品
        List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), goodss.getGoodsid());
        if (sameGoodstable.size() != 0 && EquipTool.canSuper(goodss.getType())) {
            // 修改使用次数
            int uses = sameGoodstable.get(0).getUsetime() + 1;
            sameGoodstable.get(0).setUsetime(uses);
            // 修改数据库
            AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
            assetUpdate.setGood(sameGoodstable.get(0));
            AllServiceUtil.getGoodsrecordService().insert(goodss, null, uses, -3);
        } else {
            goodss.setGoodsid(loginResult.getRole_id());
            AllServiceUtil.getGoodsTableService().insertGoods(goodss);
            assetUpdate.setGood(goodss);
            AllServiceUtil.getGoodsrecordService().insert(goodss, null, 1, -3);
        }
        assetUpdate.setMsg("成功兑换#R"+goodss.getGoodsname());
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));


//        StringBuffer buffer=new StringBuffer();
//        buffer.append("#Y天空一声巨响,#G");
//        buffer.append(loginResult.getRolename());
//        buffer.append("#Y终于集齐#G");
//        buffer.append(goodName);
//        buffer.append("#Y,获得#G");
//        buffer.append(goodss.getGoodsname());
//        buffer.append("#Y！#89");
//        NChatBean bean = new NChatBean();
//        bean.setId(4);
//        bean.setMessage(buffer.toString());
//        String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
//        SendMessage.sendMessageToAllRoles(msg);
    }
}
