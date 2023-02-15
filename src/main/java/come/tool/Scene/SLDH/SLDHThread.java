package come.tool.Scene.SLDH;

import org.come.handler.SendMessage;
import org.come.protocol.Agreement;

public class SLDHThread implements Runnable{

	private SLDHScene sldhScene;
	public SLDHThread(SLDHScene sldhScene) {
		// TODO Auto-generated constructor stub
		this.sldhScene=sldhScene;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
		    StringBuffer buffer=new StringBuffer();
			buffer.append("第#R");
			buffer.append(sldhScene.getJS());
			buffer.append("#Y届第#R");
			buffer.append(sldhScene.getCI());
			buffer.append("#Y轮水路大会即将开始,想参与的队伍请在#R");
			buffer.append((25-(i*5)));
			buffer.append("#Y分钟内进场");
			SendMessage.sendMessageToAllRoles(Agreement.getAgreement().PromptAgreement(buffer.toString()));		
			try {
				Thread.sleep(SLDHScene.JG);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		sldhScene.setI(1);
		sldhScene.grouping();
	}

}
