package come.tool.Calculation;

public class BaseXingpans {
    //	星盘编号_数量_等级_属性1_值_属性2_值
    private int bh;//星盘编号
    private String exp;//数量
    private int xs;//等级
    private String key; //属性1
    private double value;//值
    private String key1;//属性2
    private double value1;//值

    public BaseXingpans(int bh, String exp, int xs, String key, double value, String key1, double value1) {
        super();
        this.bh = bh;
        this.exp= exp;
        init(xs, key, value, key1, value1);
    }
    public void init(int xs, String key, double value, String key1, double value1){
        this.xs = xs;
        this.key = key;
        this.value = value;
        this.key1 = key1;
        this.value1 = value1;
    }
    /**获取星盘编号*/
    public int getBh() { return bh; }
    /**等级*/
    public int getXs() {return xs;}
    public void setXs(int xs) {
        this.xs = xs;
    }
    /**获取key*/
    public String getKey() { return key; }
    public String getKey1() { return key1; }
    public void setKey(String key) {
        this.key = key;
    }
    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public void setValue1(double value1) {
        this.value1 = value1;
    }




    /**获取属性*/
    public double getKeyValue(){ return (value*0.4)+(value*xs*0.04); }
    /**获取属性1*/
    public double getKeyValue1(){ return (value1*0.4)+(value1*xs*0.04); }

    /**获取数量*/
    public String getExp() {return exp;}
    public void setExp(String exp) {
        this.exp =exp;
    }

    public String toString() {
        return bh + "_" + exp + "_" + xs + "_" + key + "_" + value+ "_" + key1 + "_" + value1;
    }
}
