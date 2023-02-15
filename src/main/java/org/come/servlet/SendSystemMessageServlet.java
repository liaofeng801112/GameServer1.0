package org.come.servlet;

import come.tool.Stall.AssetUpdate;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

public class SendSystemMessageServlet extends HttpServlet {
    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map returnData = new HashMap();
        String message = request.getParameter("message");
        String type = request.getParameter("type");
        String msgLiaoTian;
        if (type.equals("1") || type.equals("0")) {
            NChatBean bean = new NChatBean();
            bean.setId(9);
            bean.setMessage("#Y" + message);
            msgLiaoTian = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
            SendMessage.sendMessageToAllRoles(msgLiaoTian);
        }

        if (type.equals("2") || type.equals("0")) {
            AssetUpdate assetUpdate = new AssetUpdate();
            assetUpdate.setType(25);
            assetUpdate.setMsg("#Y" + message);
            msgLiaoTian = Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate));
            SendMessage.sendMessageToAllRoles(msgLiaoTian);
        }

        returnData.put("status", 200);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
        printWriter.flush();
        printWriter.close();
    }
}
