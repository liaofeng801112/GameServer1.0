package org.come.action.gl;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.action.buy.AddGoodUtil;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;

import com.gl.util.EggUtil;

import come.tool.Role.RolePool;
/**
 * 获取元气蛋
 * @author gl
 *
 */
public class EggAction implements IAction {

    @Override
    public void action(ChannelHandlerContext ctx, String message) {



        // 获得角色id
        LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
        BigDecimal roleid = loginResult.getRole_id();

        // 查询玩家是否已经拥有元气蛋，如果有则提示
        List<Goodstable> goodsList = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(roleid,EggUtil.EGG_ID);
        if (goodsList.size() > 0) {
            SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你已经有一个了，带着它去战斗先把它孵化出来吧"));
            return;
        }

        if (RolePool.getRoleData(roleid).isGoodFull()){
            SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你的物品栏已经满了，请留出至少一个空位"));
            return;
        }

        // 获得元气蛋
        Goodstable goodstable = GameServer.getGood(EggUtil.EGG_ID);

        // 角色ID
        goodstable.setRole_id(roleid);
        // 插入数据库
        AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
        AddGoodUtil.addGood(ctx, goodstable);
        SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你获得了一个元气蛋，快带着它去参加战斗吸收天地精华吧"));

        // 添加记录
        AllServiceUtil.getGoodsrecordService().insert(goodstable,null,1, 0);
    }

}
