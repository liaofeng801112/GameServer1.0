package come.tool.FightingData;
/**
 * 战斗灵宝
 * @author Administrator
 *
 */
public class FightingLingbao {
	    //灵宝数据
		private ManData lingbaonData;
		//灵宝是否出场 0未上场 1正在上场 2已经下场  
		private int Play;
		
		public FightingLingbao() {
			// TODO Auto-generated constructor stub
		}
		public FightingLingbao(ManData lingbaonData, int play) {
			super();
			this.lingbaonData = lingbaonData;
			Play = play;
		}
		public ManData getLingbaonData() {
			return lingbaonData;
		}
		public void setLingbaonData(ManData lingbaonData) {
			this.lingbaonData = lingbaonData;
		}
		public int getPlay() {
			return Play;
		}
		public void setPlay(int play) {
			Play = play;
		}
		
		
}
