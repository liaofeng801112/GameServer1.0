package come.tool.Scene.SLDH;

import come.tool.Role.RoleShow;

public class SLDHBean {

	private int JS;//第N届第M轮水路大会
	private int CI;
	private RoleShow[] lastShows;//上届的水陆大会·战神
	public int getJS() {
		return JS;
	}
	public void setJS(int jS) {
		JS = jS;
	}
	public int getCI() {
		return CI;
	}
	public void setCI(int cI) {
		CI = cI;
	}
	public RoleShow[] getLastShows() {
		return lastShows;
	}
	public void setLastShows(RoleShow[] lastShows) {
		this.lastShows = lastShows;
	}
}
