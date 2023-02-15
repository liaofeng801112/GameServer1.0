package come.tool.BangBattle;


public class BangThread extends Thread{
    private BangBattlePool pool; 
	public BangThread(BangBattlePool pool) {
		super();
		this.pool = pool;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			for (int i = pool.BangFights.size()-1; i >=0; i--) {
				BangFight fight=pool.BangFights.get(i);
				try {
					fight.process();
					if (fight.isEnd()) {
						pool.BangFights.remove(fight);
						pool.WinOrLose(fight);
					}		
			    } catch (Exception e) {
				    // TODO: handle exception
			    	fight.isVictory();
			    	pool.BangFights.remove(fight);
			    	pool.WinOrLose(fight);		
				    e.printStackTrace();
			    }	
			}
			try {
				Thread.sleep(1200);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
