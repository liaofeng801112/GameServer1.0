package org.come.action.monitor;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import org.come.action.IAction;
import org.come.action.chat.ChatAction;
import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.task.RefreshMonsterTask;
import org.come.tool.ReadExelTool;
import org.come.tool.WriteOut;
import org.come.until.CreateTextUtil;
import org.come.until.GsonUtil;

import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.PKLS.PKLSScene;
import come.tool.Scene.PKLS.lsteamBean;
import come.tool.Scene.SLDH.SLDHScene;


public class MonitorAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		//监测 // 获得该角色ID
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		String cd=message.substring(0,1);
		String ab=message.substring(1);
		int type=Integer.parseInt(cd);
		String msg=null;
		if (type==0) {
			msg="M0:"+ab;
			System.out.println(msg);
			WriteOut.addtxt(msg,9999);
		}else if (type==1) {
			msg="M1:"+ab+(loginResult!=null?loginResult.getRole_id():null);
			System.out.println(msg);
			WriteOut.addtxt(msg,9999);
		}else if (type==2) {//获取联赛报名数据
			Scene scene=SceneUtil.getScene(SceneUtil.PKLSID);
			if (scene!=null) {
				PKLSScene pklsScene=(PKLSScene) scene;
				lsteamBean lsteamBean=new lsteamBean();
				lsteamBean.setLSTeams(pklsScene.getLSTeams());
				lsteamBean.initUserData();
				try {
					CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "lsteamDATA.txt", GsonUtil.getGsonUtil().getgson().toJson(lsteamBean).getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if (type==3) {//保存玩家数据
			RefreshMonsterTask.saveRoleInfo();
		}else if (type==4) {//手动重启

		//	GameServer.gameServer.contextDestroyed(null);
		}else if (type==5) {//禁词添加
			if (!ChatAction.ggs.contains(ab)) {
				ChatAction.ggs.add(ab);
			}
		}else if (type==6) {//禁词删除
			ChatAction.ggs.remove(ab);
		}else if (type==7) {//今日消耗数据
			try {
				CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "money.txt", GsonUtil.getGsonUtil().getgson().toJson(MonitorUtil.getMoney()).getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (type==8) {//开启或者关闭验证码
			GameServer.isCode=!GameServer.isCode;
			System.out.println("现在登录验证状态:"+(GameServer.isCode?"开启":"关闭"));
		}else if (type==9) {//水陆大会控制
			Scene scene=SceneUtil.getScene(SceneUtil.SLDHID);
			if (scene!=null) {
				SLDHScene sldhScene=(SLDHScene) scene;
				sldhScene.cx(Integer.parseInt(ab));
			}
		}
	}
}
