package come.tool.Scene.SLDH;


public class SLDHBattleThread implements Runnable{

	private SLDHScene sldhScene;
	public SLDHBattleThread(SLDHScene sldhScene) {
		// TODO Auto-generated constructor stub
		this.sldhScene=sldhScene;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(SLDHScene.JG/5*3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sldhScene.PKOpen();
	}

}
