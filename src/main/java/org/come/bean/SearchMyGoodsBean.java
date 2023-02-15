package org.come.bean;
/**
 * 我的商品查询bean
 * @author Administrator
 *
 */
public class SearchMyGoodsBean {
    /**
     * 上下架标识
     */
    private Integer flag;
    
    /**
     * 页数
     */
    private Integer pageNum;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
    
    
}
