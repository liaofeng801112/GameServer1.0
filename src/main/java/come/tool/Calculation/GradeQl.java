package come.tool.Calculation;

public class GradeQl {

	private String type;
	private int P;//多少等级换算一点D
	private double V;//基础点数数值
	
	public GradeQl(String type, int p, double v) {
		super();
		this.type = type;
		P = p;
		V = v;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getP() {
		return P;
	}
	public void setP(int p) {
		P = p;
	}
	public double getV() {
		return V;
	}
	public void setV(double v) {
		V = v;
	}
	
}
