package come.tool.BangBattle;

import java.math.BigDecimal;

/**
 * 帮派积分
 * @author Administrator
 */
public class BangPoints {
	//帮派id
	private BigDecimal id;
	//战绩记录            00000000 
	private int record;	
	//排名
	private int rank=-1;
	//计算排名
	public void CalculateRank(int sum){
        int max=sum;
        int min=1;
        int i=0;
        while (min<max) {
        	if (i>999999) {break;}
            sum=max-min+1;
            if (record==(record|(1<<i))) {
               max-=sum/2;          	    
			}else {
			   min+=(sum+1)/2;
			}
            i++;
		}
        rank=min;
	}
	public BangPoints(BigDecimal id) {
		super();		
		this.id = id;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public int getRecord() {
		return record;
	}
	public void setRecord(int record) {
		this.record = record;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
