package org.come.pay;

import cn.hutool.json.JSONUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.ApiValid;
import org.come.bean.LoginResult;
import org.come.bean.managerTable;
import org.come.pay.pojo.CatUserRole;
import org.come.until.AllServiceUtil;

public class CatAllUserRoleServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        managerTable manege= (managerTable)request.getSession().getAttribute("xy2o");
        String token = request.getHeader("manage_token");
        String VALID_NAME = request.getParameter(ApiValid.VALID_NAME);
        if(null == VALID_NAME || !VALID_NAME.equals(ApiValid.VALID_VALUE) || manege ==null || !ApiValid.vaildToken(token,manege.getUsername())){
            System.out.println("【PayvipBeanServlet】非法请求！！,已踢出");
            return ;
        }
        response.setHeader("manage_token",ApiValid.getToken(manege.getUsername()));
        ArrayList roles = new ArrayList();

        try {
            List list = AllServiceUtil.getUserTableService().findAllUserRoles();
            Iterator var6 = list.iterator();

            while(var6.hasNext()) {
                LoginResult ret = (LoginResult)var6.next();
                roles.add(new CatUserRole(ret));
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        PrintWriter pwPrintWriter = response.getWriter();
        pwPrintWriter.write(JSONUtil.toJsonStr(roles));
        pwPrintWriter.flush();
        pwPrintWriter.close();
    }
}
