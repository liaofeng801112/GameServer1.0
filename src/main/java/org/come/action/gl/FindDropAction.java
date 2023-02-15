package org.come.action.gl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.IAction;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import com.github.pagehelper.util.StringUtil;

import io.netty.channel.ChannelHandlerContext;

//TODO 查询掉落信息
public class FindDropAction implements IAction {

    @Override
    public void action(ChannelHandlerContext ctx, String itemName) {
        if (GameServer.OPEN)
            return;
        ConcurrentHashMap<String, List<String>> map = GameServer.getGoodsByRobot();
        String mes = "";
        //校验物品名称是否存在掉落列表
        StringBuffer str = new StringBuffer();
        if (map.containsKey(itemName) && map.get(itemName).size() > 0) {
            List<String> list = map.get(itemName);
            for(String robot : list) {
                if (!StringUtil.isEmpty(str.toString())) {
                    str.append("、");
                }
                str.append(robot);
            }
            mes = Agreement.getAgreement().PromptAgreement("物品#G" + itemName + "#Y击杀以下怪物有几率掉落：" + str.toString());
        }else {
            str.append("请检查您输入的物品名称#G"+itemName+"#Y是否正确，或者此物品不存在掉落列表");
            mes = Agreement.getAgreement().PromptAgreement(str.toString());
        }
        SendMessage.sendMessageToSlef(ctx, mes);

    }

}
