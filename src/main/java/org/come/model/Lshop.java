package org.come.model;

import java.math.BigDecimal;

import org.come.entity.BuyCount;

public class Lshop implements Cloneable{

	private int id;//id
	private BigDecimal Gid;//物品id
	private int type;//货币类型(0:大话币,1:仙玉)
	private BigDecimal money;//价格
	private int lNum;//限购数量
	private int maxNum;//单次最大购买数
	private int num;//已购买数
	private transient BuyCount buyCount;
	public boolean addPrice(int num,long jg){
		buyCount.addCount(num, jg);
		return false;
	}
	public int addNum(int size){
    	if (maxNum==0) {return size;}
    	if (num>=maxNum) {return 0;}
    	num+=size;
    	if (num>maxNum) {
    		size=num-maxNum;
    		num=maxNum;
    		return size;
		}
		return size;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getGid() {
		return Gid;
	}
	public void setGid(BigDecimal gid) {
		Gid = gid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	
	public int getlNum() {
		return lNum;
	}
	public void setlNum(int lNum) {
		this.lNum = lNum;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public BuyCount getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(BuyCount buyCount) {
		this.buyCount = buyCount;
	}
	@Override
	public Lshop clone(){
		try {
			Lshop lshop=(Lshop) super.clone();		
			lshop.buyCount=this.buyCount;
			return lshop;		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
}
