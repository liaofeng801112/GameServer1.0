package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.come.entity.PayvipBean;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class VipPayOptionServlet extends HttpServlet {
    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map returnData = new HashMap();
        String payvip = request.getParameter("payvip");
        String type = request.getParameter("type");
        PayvipBean payvipBean = GsonUtil.getGsonUtil().getgson().fromJson(payvip, PayvipBean.class);
        if (type.equals("insert")) {
            AllServiceUtil.getPayvipBeanServer().insertPayvioBean(payvipBean);
        } else if (type.equals("del")) {
            String id = request.getParameter("id");
            AllServiceUtil.getPayvipBeanServer().deletePayvioBean(Integer.parseInt(id));
        } else if (type.equals("find")) {
            List list = AllServiceUtil.getPayvipBeanServer().selectAllVip();
            returnData.put("list", list);
        } else {
            AllServiceUtil.getPayvipBeanServer().updatePayvioBean(payvipBean);
        }

        GameServer.refreshBean();
        returnData.put("status", 200);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
        printWriter.flush();
        printWriter.close();
    }
}
