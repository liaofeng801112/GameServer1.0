
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
import org.come.entity.Openareatable;
import org.come.service.OpenareatableService;
import org.come.serviceImpl.OpenareatableServiceImpl;

public class ModifyInviteCodeServlet extends HttpServlet {
    static OpenareatableService openareatableService;

    public ModifyInviteCodeServlet() {
        openareatableService = new OpenareatableServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String code = request.getParameter("code");
        if (!StrUtil.isBlankIfStr(code)) {
            String ret = "修改成功";

            try {
                List<Openareatable> list = openareatableService.selectAllOpenareatable();
                Iterator var7 = list.iterator();

                while(var7.hasNext()) {
                    Openareatable openareatable = (Openareatable)var7.next();
                    openareatable.setOt_atid(code);
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

    public static String getOt_atid() {
        List<Openareatable> list = (new OpenareatableServiceImpl()).selectAllOpenareatable();
        Iterator var2 = list.iterator();
        if (var2.hasNext()) {
            Openareatable openareatable = (Openareatable)var2.next();
            return openareatable.getOt_atid();
        } else {
            return "";
        }
    }
}
