package org.come.servlet;

import io.netty.channel.ChannelHandlerContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

public class RedisBackUpServlet extends HttpServlet {
    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map returnData = new HashMap();
        String openAreaId = request.getParameter("");
        GameServer.OPEN = true;
        Iterator entries = GameServer.getRoleNameMap().entrySet().iterator();

        while(entries.hasNext()) {
            Entry entrys = (Entry)entries.next();
            SendMessage.sendMessageToSlef((ChannelHandlerContext)entrys.getValue(), Agreement.getAgreement().serverstopAgreement());
        }

        GameServer.gameServer.loadResource();
        returnData.put("status", 200);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
        printWriter.flush();
        printWriter.close();
    }
}
