package org.come.bean;
/**
 * 订单查询bean
 * @author Administrator
 *
 */
public class SearchOrderBean {
    /**
     * 状态（1未付钱  2超时 3已付钱 4已取货）
     */
    private Integer status;
    
    /**
     * 查询时间（1一周内  2一个月内 3一个月前）
     */
    private Integer time;
    
    /**
     * 页数
     */
    private Integer pageNum;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
    
}
