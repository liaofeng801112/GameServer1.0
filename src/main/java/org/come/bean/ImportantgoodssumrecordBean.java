package org.come.bean;

/**
 * HGC--2019-11-18 重要物资汇总 bean
 * 
 * @author Administrator
 * 
 */
public class ImportantgoodssumrecordBean {
	private String gid;// 物品 id
	private String name;// 物品名称
	private String sum;// 记录数据 (按 照时间记录 1-7)
	private String time;// 记录数据 (按 照时间记录 1-7)
	private int page;// 查询页码

	public ImportantgoodssumrecordBean() {
		super();
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTime() {

		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
