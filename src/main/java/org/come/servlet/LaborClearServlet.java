package org.come.servlet;

import come.tool.Scene.LaborDay.LaborScene;
import come.tool.Stall.AssetUpdate;
import org.come.ApiValid;
import org.come.bean.LoginResult;
import org.come.bean.managerTable;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class LaborClearServlet extends HttpServlet {

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> returnData = new HashMap<>();
        String roleId = request.getParameter("roleId");
        BigDecimal roleIdDec = null;
        try{
            roleIdDec = new BigDecimal(roleId);
        }catch (Exception e){
            returnData.put("status", 500);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
            printWriter.flush();
            printWriter.close();
            return ;
        }
        //删除该角色抽奖次数
        LaborScene.clearRoleMapByRoleId(roleIdDec);
        AssetUpdate assetUpdate=new AssetUpdate();
        assetUpdate.setType(25);
        assetUpdate.setMsg("你的抽奖次数被清理为0次");
        LoginResult loginResult= AllServiceUtil.getRoleTableService().selectRoleByRoleId(roleIdDec);
        SendMessage.sendMessageByRoleName(loginResult.getRolename(), Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
        returnData.put("status", 200);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
        printWriter.flush();
        printWriter.close();

    }


}
