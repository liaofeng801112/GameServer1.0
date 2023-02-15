package come.tool.Calculation;

/**星阵属性*/
public class BaseStar {

	private String[] vs;//星阵属性 星阵属性=星阵名称=星阵的五行属性=宫位属性星卡 
    private double xs;//五行加成之力
    
    private int man;
    public BaseStar(String xz,String wx) {
		// TODO Auto-generated constructor stub
    	vs=xz.split("=");
    	String[] wxs=wx.split("&");
        xs=0;
    	for (int j = 1; j < wxs.length; j++) {
			String[] split = wxs[j].split("=");
			xs += fiveElementRestrainCreate(vs[2], split[0], split[1]);
		}
    	xs/=100D;
	} 
	/** 判断相克五行属性 */
	public static Integer fiveElementRestrainNum(String attr) {
		if (attr.equals("金")) {
			return 1;
		} else if (attr.equals("木")) {
			return 2;
		} else if (attr.equals("土")) {
			return 3;
		} else if (attr.equals("水")) {
			return 4;
		} else if (attr.equals("火")) {
			return 5;
		}
		return null;
	}
	/** 判断相生五行属性 */
	public static Integer fiveElementCreateNum(String attr) {
		if (attr.equals("金")) {
			return 1;
		} else if (attr.equals("水")) {
			return 2;
		} else if (attr.equals("木")) {
			return 3;
		} else if (attr.equals("火")) {
			return 4;
		} else if (attr.equals("土")) {
			return 5;
		}
		return null;
	}
	/** 判断相生相克 */
	public static double fiveElementRestrainCreate(String attr, String value, String num) {
		Integer num1 = fiveElementRestrainNum(attr);
		Integer num2 = fiveElementRestrainNum(value);
		int abs = Math.abs(num1 - num2);
		if (abs == 1 || abs == 4) {// 相克
			if ((num1 == 1 && num2 == 5) || (num1 > num2)) {
				return Integer.parseInt(num) * 0.1;
			}
		}
		num1 = fiveElementCreateNum(attr);
		num2 = fiveElementCreateNum(value);
		abs = Math.abs(num1 - num2);
		if (abs == 1 || abs == 4) {// 相生
			if ((num1 == 1 && num2 == 5) || (num1 > num2)) {
				return Integer.parseInt(num) * 0.3;
			}
		}
		return Integer.parseInt(num) * 0.2;
	}
    
	public String[] getVs() {
		return vs;
	}

	public void setVs(String[] vs) {
		this.vs = vs;
	}

	public double getXs() {
		return xs;
	}

	public void setXs(double xs) {
		this.xs = xs;
	}
	public int getMan() {
		return man;
	}
	public void setMan(int man) {
		this.man = man;
	}
    
}
