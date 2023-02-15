package come.tool.hjsl;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.bean.ConfirmBean;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.model.Robots;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleThreadPool;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;
import come.tool.newTask.TaskRecord;
import io.netty.channel.ChannelHandlerContext;

public class HjslAction implements IAction{

    @Override
    public void action(ChannelHandlerContext ctx, String message) {
        // TODO Auto-generated method stub
        LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
        if (roleInfo==null) {
            return;
        }
        if (message.startsWith("P")) {//挑战处理
            Integer index = Integer.parseInt(message.substring(1))+9000;
            Robots robots = getRobots(String.valueOf(index));
            hjtz(ctx, roleInfo,robots,Integer.parseInt(message.substring(1)));
        }else if (message.equals("R")) {//添加次数
            addNumHjsl(ctx,roleInfo,null);
        }
    }
    /**获取robot*/
    public Robots getRobots(String advance){
        return GameServer.getAllRobot().get(advance);
    }
    /**挑战处理*/
    public static void hjtz(ChannelHandlerContext ctx,LoginResult roleInfo,Robots robots,int max){
        RoleData roleData=RolePool.getRoleData(roleInfo.getRole_id());
        if (roleData==null) {return;}
        String[] teams=roleInfo.getTeam().split("\\|");
        int num=10;
        TaskRecord taskRecord=roleData.getTaskRecord(6);
        if (taskRecord!=null) {
            num+=taskRecord.getrSum();
            num-=taskRecord.getcSum();
        }
        if (num<=0) {
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("今日挑战次数已用完"));
            return;
        }
        boolean  value = BattleThreadPool.HJSL(roleInfo, teams,robots,1);
        if (value!=true) {//进入战斗失败
            SendMessage.sendMessageToSlef(ctx,"数据加载失败!");
            return;
        }
        if (taskRecord==null) {
            taskRecord=new TaskRecord(6);
            roleData.addTaskRecord(taskRecord);
        }
    }
    /**添加次数*/
    public static void addNumHjsl(ChannelHandlerContext ctx,LoginResult roleInfo,Object object){
//		Tasksetid  6   幻境试炼
//		今日购买次数 今日挑战次数
        RoleData roleData=RolePool.getRoleData(roleInfo.getRole_id());
        if (roleData==null) {return;}
        TaskRecord taskRecord=roleData.getTaskRecord(6);
        if (taskRecord!=null&&taskRecord.getrSum()>=10) {
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("达到每日购买上限"));
            return;
        }
        int moeny=((taskRecord!=null?taskRecord.getrSum():0)+1)*2000;
        if (roleInfo.getCodecard().longValue()<moeny) {
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的仙玉不足"+moeny));
            return;
        }
        if (object==null) {//发送2次确认
            ConfirmBean confirmBean=new ConfirmBean();
            confirmBean.setMSG("你是否要消耗#G"+moeny+"仙玉#W购买挑战次数");
            confirmBean.setType(103);
            SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().confirmAgreement(GsonUtil.getGsonUtil().getgson().toJson(confirmBean)));
            return;
        }
        //扣除仙玉
        AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
        roleInfo.setCodecard(new BigDecimal(roleInfo.getCodecard().longValue()-moeny));
        assetUpdate.updata("X=-"+moeny);
        MonitorUtil.getMoney().useX(moeny);
        if (taskRecord==null) {
            taskRecord=new TaskRecord(6);
            roleData.addTaskRecord(taskRecord);
        }
        taskRecord.addRSum(1);
        assetUpdate.setTask("C6=R");
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
    }


}
