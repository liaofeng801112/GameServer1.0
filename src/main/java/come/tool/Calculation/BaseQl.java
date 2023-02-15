package come.tool.Calculation;

public class BaseQl {
	private String key;
	private double value;
	
	public BaseQl(String key, double value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
