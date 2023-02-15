package come.tool.newTask;

import java.util.List;

import org.come.entity.Goodstable;

public class TaskConsume {

	private long money;
	private List<Goodstable> goods;
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public List<Goodstable> getGoods() {
		return goods;
	}
	public void setGoods(List<Goodstable> goods) {
		this.goods = goods;
	}
}
