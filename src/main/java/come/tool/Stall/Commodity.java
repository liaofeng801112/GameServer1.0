package come.tool.Stall;

import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.RoleSummoning;
/**
 * 商品
 * @author Administrator
 */
public class Commodity {
	//物品
	private Goodstable good;
	//召唤兽
	private RoleSummoning pet;
	//灵宝
	private Lingbao lingbao;
	//货币
	private String currency;
	//货币值
	private long money;
	public Goodstable getGood() {
		return good;
	}
	public void setGood(Goodstable good) {
		this.good = good;
	}
	public RoleSummoning getPet() {
		return pet;
	}
	public void setPet(RoleSummoning pet) {
		this.pet = pet;
	}
	public Lingbao getLingbao() {
		return lingbao;
	}
	public void setLingbao(Lingbao lingbao) {
		this.lingbao = lingbao;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}	
}
