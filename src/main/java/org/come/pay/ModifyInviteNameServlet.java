package org.come.pay;

import cn.hutool.core.util.StrUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.ApiValid;
import org.come.bean.managerTable;
import org.come.entity.Openareatable;
import org.come.service.OpenareatableService;
import org.come.serviceImpl.OpenareatableServiceImpl;

public class ModifyInviteNameServlet extends HttpServlet {
    static OpenareatableService openareatableService;

    public ModifyInviteNameServlet() {
        openareatableService = new OpenareatableServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String name = request.getParameter("name");
        if (!StrUtil.isBlankIfStr(name)) {
            String ret = "修改成功";

            try {
                List list = openareatableService.selectAllOpenareatable();
                Iterator var7 = list.iterator();

                while(var7.hasNext()) {
                    Openareatable openareatable = (Openareatable)var7.next();
                    openareatable.setOt_areaname(name);
                    openareatableService.updateOpenareatable(openareatable);
                }
            } catch (Exception var8) {
                ret = "保存失败，请发送错误信息给技术人员";
                var8.printStackTrace();
            }

            PrintWriter pwPrintWriter = response.getWriter();
            pwPrintWriter.write(ret);
            pwPrintWriter.flush();
            pwPrintWriter.close();
        }
    }

    public static String getOt_areaname() {
        List list = (new OpenareatableServiceImpl()).selectAllOpenareatable();
        Iterator var2 = list.iterator();
        if (var2.hasNext()) {
            Openareatable openareatable = (Openareatable)var2.next();
            return openareatable.getOt_areaname();
        } else {
            return "";
        }
    }
}
