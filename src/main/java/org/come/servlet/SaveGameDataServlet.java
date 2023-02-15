package org.come.servlet;

import come.tool.BangBattle.BangBattlePool;
import come.tool.BangBattle.BangFileSystem;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.LTS.LTSUtil;
import come.tool.Scene.LaborDay.LaborScene;
import come.tool.Scene.PKLS.PKLSScene;
import come.tool.Scene.PKLS.lsteamBean;
import come.tool.Scene.RC.RCScene;
import come.tool.Stall.StallPool;
import come.tool.newGang.GangUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.come.action.festival.HatchvalueAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.redis.RedisControl;
import org.come.server.GameServer;
import org.come.task.RefreshMonsterTask;
import org.come.thread.RedisEqualWithSqlThread;
import org.come.tool.ReadExelTool;
import org.come.tool.WriteOut;
import org.come.until.CreateTextUtil;
import org.come.until.GsonUtil;

public class SaveGameDataServlet extends HttpServlet {
    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap returnData = new HashMap();

        try {
            System.err.println("开始处理摆摊物品");
            StallPool.getPool().guanbi();
            System.err.println("开始保存擂台赛积分数据");
            LTSUtil.getLtsUtil().BCLts();
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        try {
            Thread.sleep(2000L);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        try {
            BangFileSystem.getBangFileSystem().DataSaving(BangBattlePool.getBangBattlePool());
            GangUtil.upGangs(false);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        System.err.println("开始备份玩家数据");
        Iterator entries = GameServer.getAllLoginRole().entrySet().iterator();

        while(entries.hasNext()) {
            Entry entrys = (Entry)entries.next();
            LoginResult loginResult = (LoginResult)entrys.getValue();
            if (loginResult != null) {
                try {
                    loginResult.setUptime(String.valueOf(System.currentTimeMillis()));
                    RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
                    roleData.roleRecover(loginResult);
                    RedisControl.addUpDate(loginResult, roleData.getPackRecord());
                } catch (Exception var10) {
                    System.err.println("处理玩家备份失败" + loginResult.getRolename());
                    var10.printStackTrace();
                }
            }
        }

        RedisEqualWithSqlThread.AllToDataRole();

        try {
            Thread.sleep(10000L);
            RedisEqualWithSqlThread.AllToDatabase();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        if (WriteOut.buffer != null) {
            WriteOut.writeTxtFile(WriteOut.buffer.toString());
        }

        try {
            if (LaborScene.laborScene != null) {
                LaborScene.Save(true);
            }

            CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "hatch.txt", HatchvalueAction.hatch.toString().getBytes());
            Scene scene = SceneUtil.getScene(1009);
            if (scene != null) {
                RCScene rcScene = (RCScene)scene;
                CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "bbRecord.txt", GsonUtil.getGsonUtil().getgson().toJson(rcScene.getBbRecord()).getBytes());
            }

            scene = SceneUtil.getScene(1010);
            if (scene != null) {
                PKLSScene pklsScene = (PKLSScene)scene;
                lsteamBean lsteamBean = new lsteamBean();
                lsteamBean.setLSTeams(pklsScene.getLSTeams());
                CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "lsteam.txt", GsonUtil.getGsonUtil().getgson().toJson(lsteamBean).getBytes());
            }

            CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "money.txt", GsonUtil.getGsonUtil().getgson().toJson(MonitorUtil.getMoney()).getBytes());
            RefreshMonsterTask.upBuyCount(-1, false);
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        returnData.put("status", 200);
        returnData.put("mes", "系统将会在5S后完成数据保存");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(GsonUtil.getGsonUtil().getgson().toJson(returnData));
        printWriter.flush();
        printWriter.close();
    }
}
