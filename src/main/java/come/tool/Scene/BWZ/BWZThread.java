package come.tool.Scene.BWZ;

public class BWZThread implements Runnable{

	private BWZScene btyScene;
	
	public BWZThread(BWZScene btyScene) {
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
