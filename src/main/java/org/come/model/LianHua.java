package org.come.model;


//炼化
public class LianHua {
//	id	类型	需要仙玉	说明	数量
//	1	1000	100	消耗{0}仙玉锁定一条炼化属性	1
//	2	1000	500	消耗{0}仙玉锁定二条炼化属性	2
//	3	1000	2500	消耗{0}仙玉锁定三条炼化属性	3
//	4	1000	5000	消耗{0}仙玉锁定四条炼化属性	4
//	5	2000	10000	消耗{0}仙玉追加一条特技	1

    private int id;
    private int type;
    private int money;
    private String des;
    private int num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
