package org.come.pay;

import cn.hutool.core.util.StrUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.ApiValid;
import org.come.bean.managerTable;
import org.come.entity.Openareatable;
import org.come.entity.UserTable;
import org.come.service.OpenareatableService;
import org.come.serviceImpl.OpenareatableServiceImpl;
import org.come.until.AllServiceUtil;

public class ModifyUserPwdServlet extends HttpServlet {
    static OpenareatableService openareatableService;

    public ModifyUserPwdServlet() {
        openareatableService = new OpenareatableServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        managerTable manege= (managerTable)request.getSession().getAttribute("xy2o");
        String token = request.getHeader("manage_token");
        String VALID_NAME = request.getParameter(ApiValid.VALID_NAME);
        if(null == VALID_NAME || !VALID_NAME.equals(ApiValid.VALID_VALUE) || manege ==null || !ApiValid.vaildToken(token,manege.getUsername())){
            System.out.println("【PayvipBeanServlet】非法请求！！,已踢出");
            return ;
        }
        response.setHeader("manage_token",ApiValid.getToken(manege.getUsername()));
        String userid = request.getParameter("userid");
        String userpwd = request.getParameter("userpwd");
        if (!StrUtil.isBlankIfStr(userid) && !StrUtil.isBlankIfStr(userpwd)) {
            String ret = "修改成功";

            try {
                UserTable userTable = new UserTable();
                userTable.setUser_id(new BigDecimal(userid));
                userTable.setUserpwd(userpwd);
                int flag = AllServiceUtil.getUserTableService().updateUsterWithUidforuserpasswd(userTable);
                if (flag == 0) {
                    ret = "修改失败";
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
        List list = (new OpenareatableServiceImpl()).selectAllOpenareatable();
        Iterator var2 = list.iterator();
        if (var2.hasNext()) {
            Openareatable openareatable = (Openareatable)var2.next();
            return openareatable.getOt_atid();
        } else {
            return "";
        }
    }
}
