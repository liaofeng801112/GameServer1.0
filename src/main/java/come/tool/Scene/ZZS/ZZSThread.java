package come.tool.Scene.ZZS;

import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

public class ZZSThread implements Runnable{

	private ZZSScene zzsScene;

	public ZZSThread(ZZSScene zzsScene) {
		super();
		this.zzsScene = zzsScene;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		// 发起世界喊话
		for (int i = 6; i >0; i--) {
			if (zzsScene.type==0) {
				NChatBean bean=new NChatBean();
				bean.setId(5);
				bean.setMessage("#R种族赛即将开始了,想要参加的玩家到对应NPC进场,"+i*5+"分钟后将关闭进场通道");
				String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
				SendMessage.sendMessageToAllRoles(msg);			
			}		
			try {
				Thread.sleep(300000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		zzsScene.upI(2);
		//修改状态为 匹配赛阶段
//		55分钟的匹配赛  3300秒  3秒一次匹配计算  
		for (int i = 0; i < 1080; i++) {
			zzsScene.match();
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		zzsScene.upI(3);
		try {
			Thread.sleep(300000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		zzsScene.upI(4);
		while (zzsScene.isEnd()) {
			try {
				zzsScene.isTT();
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
