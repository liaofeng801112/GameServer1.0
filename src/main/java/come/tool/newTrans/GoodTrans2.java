package come.tool.newTrans;

import java.math.BigDecimal;

import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.RoleSummoning;

/**交易数据变成*/
public class GoodTrans2 {
	//false取消这个物品   true是添加这个物品
	private boolean i;
	/**准备交易的物品*/
	private Goodstable goods;
	/**准备交易的召唤兽*/
	private RoleSummoning pet;
	/**准备交易的灵宝*/
	private Lingbao lingbao;
	/**准备交易的金钱*/
	private BigDecimal money;
	
	public boolean isI() {
		return i;
	}
	public void setI(boolean i) {
		this.i = i;
	}
	public Goodstable getGoods() {
		return goods;
	}
	public void setGoods(Goodstable goods) {
		this.goods = goods;
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
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
}
