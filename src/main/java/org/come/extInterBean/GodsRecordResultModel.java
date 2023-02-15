package org.come.extInterBean;

import java.util.List;

public class GodsRecordResultModel {

	private List<Goodsrecord2> godsRec;
	private int Page;
	private int PageSum;

	public GodsRecordResultModel() {
		// TODO Auto-generated constructor stub
	}

	public GodsRecordResultModel(List<Goodsrecord2> godsRec, int page, int pageSum) {
		super();
		this.godsRec = godsRec;
		Page = page;
		PageSum = pageSum;
	}

	public List<Goodsrecord2> getGodsRec() {
		return godsRec;
	}

	public void setGodsRec(List<Goodsrecord2> godsRec) {
		this.godsRec = godsRec;
	}

	public int getPage() {
		return Page;
	}

	public void setPage(int page) {
		Page = page;
	}

	public int getPageSum() {
		return PageSum;
	}

	public void setPageSum(int pageSum) {
		PageSum = pageSum;
	}

}
