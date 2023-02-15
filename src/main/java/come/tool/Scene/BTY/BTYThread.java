package come.tool.Scene.BTY;

public class BTYThread implements Runnable{

	private BTYScene btyScene;
	
	public BTYThread(BTYScene btyScene) {
		super();
		this.btyScene = btyScene;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (btyScene.isEnd()) {
			if (btyScene.time%180==0) {//刷怪
				btyScene.open();
			}
			try {
				btyScene.move();//移动
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			btyScene.time++;
		}
	}

}
