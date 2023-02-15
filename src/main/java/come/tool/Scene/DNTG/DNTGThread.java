package come.tool.Scene.DNTG;

import org.come.bean.NChatBean;
import org.come.handler.MainServerHandler;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.tool.WriteOut;
import org.come.until.GsonUtil;

public class DNTGThread implements Runnable{

	private DNTGScene dntgScene;
	
	public DNTGThread(DNTGScene dntgScene) {
		super();
		this.dntgScene = dntgScene;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 6; i++) {
			NChatBean bean=new NChatBean();
			bean.setId(9);
			bean.setMessage("#R大闹天宫即将开始了,想要参加的玩家到对应NPC进场,离正式开始还剩"+((6-i)*5)+"分钟");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToAllRoles(msg);		
			try {
				Thread.sleep(1000*60*5);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}	
		}
		dntgScene.upI(1);
//		20 : 00-21：30  正常
//		20 : 25-20: 30  展示女武神试炼倒计时
//		20 : 30-21：00  女武神试炼
//		21 : 00-21: 30  正常
//		21 : 25-21: 30  展示上古战场倒计时
//		21 : 30-22: 00  上古战场
//		22 : 00-23: 00  正常
		long time=0;
		while (dntgScene.isEnd()) {
			try {
				if (time==1500) {//刷新女武神试炼倒计时
					dntgScene.activity(0);
				}else if (time==1800) {//女武神试炼开始 同时取消 女武神试炼倒计时
					dntgScene.activity(1);	
				}else if (time==3600) {//关闭女武神试炼
					dntgScene.activity(2);
				}else if (time==5100) {//刷新上古战场倒计时
					dntgScene.activity(3);
				}else if (time==5400) {//上古战场开始     同时取消 上古战场倒计时
					dntgScene.activity(4);
				}else if (time==7200) {//关闭上古战场
					dntgScene.activity(5);
				}
				//移动
				dntgScene.move(time);
				Thread.sleep(1000);
				//刷怪
				if (time%90==0) {dntgScene.open((int)time/90);}
				time++;				
				dntgScene.isDNTG(time);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				WriteOut.addtxt("大闹天宫:"+MainServerHandler.getErrorMessage(e),9999);
				//报错 关闭活动
				dntgScene.isDNTG(10800);
			}
		}
	}

}
