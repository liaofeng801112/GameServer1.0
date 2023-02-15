package come.tool.PK;
/**
 * PK投注
 * @author Administrator
 */
public class PKStake {
    private long charge;//手续费
 	private long money; //下注金钱
 	private long xianYu;//下注仙玉
 	private long exp;   //下注经验
	public PKStake(long charge, long money, long xianYu) {
		super();
		this.charge = charge;
		this.money = money;
		this.xianYu = xianYu;
	}
	public PKStake(long charge, long money, long xianYu, long exp) {
		super();
		this.charge = charge;
		this.money = money;
		this.xianYu = xianYu;
		this.exp = exp;
	}
    public void qk(){
    	charge=0;
    	money=0;
    	xianYu=0;
    	exp=0;
    }
	
	public long getCharge() {
		return charge;
	}
	public void setCharge(long charge) {
		this.charge = charge;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public long getXianYu() {
		return xianYu;
	}
	public void setXianYu(long xianYu) {
		this.xianYu = xianYu;
	}
	public long getExp() {
		return exp;
	}
	public void setExp(long exp) {
		this.exp = exp;
	}
}
