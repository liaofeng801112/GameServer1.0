package org.come.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.come.ApiValid;
import org.come.bean.managerTable;
import org.come.entity.Record;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import java.util.List;
public class SelectRecordServlet extends HttpServlet {



    public SelectRecordServlet() {super();}

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> returnData = new HashMap<>();

        // managerTable manege= (managerTable)request.getSession().getAttribute("xy2o");
        // String token = request.getHeader("manage_token");
        // String VALID_NAME = request.getParameter(ApiValid.VALID_NAME);
        //  if(null == VALID_NAME || !VALID_NAME.equals(ApiValid.VALID_VALUE) || manege ==null || !ApiValid.vaildToken(token,manege.getUsername()) ){
        //    System.out.println("【SelectRecordServlet】非法请求！！,已踢出");
        //     return ;
        // }
        // response.setHeader("manage_token",ApiValid.getToken(manege.getUsername()));

        int type = Integer.parseInt(request.getParameter("type"));
        int count = Integer.parseInt(request.getParameter("count"));
        List<Record> list = AllServiceUtil.getRecordService().selectRecordByType(type, count);
        returnData.put("list", list);
        PrintWriter pwPrintWriter = response.getWriter();
        pwPrintWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
        pwPrintWriter.flush();
        pwPrintWriter.close();
    }

    public void init() throws ServletException {
    }
}