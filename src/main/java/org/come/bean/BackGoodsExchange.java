package org.come.bean;

import java.util.List;
import org.come.entity.Goodsexchange;

public class BackGoodsExchange {
    private List<Goodsexchange> list;
    private int pageNum;
    private int pages;
    private long total;

    public List<Goodsexchange> getList() {
        return this.list;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public int getPages() {
        return this.pages;
    }

    public long getTotal() {
        return this.total;
    }

    public void setList(List<Goodsexchange> list2) {
        this.list = list2;
    }

    public void setPageNum(int i) {
        this.pageNum = i;
    }

    public void setPages(int i) {
        this.pages = i;
    }

    public void setTotal(long j) {
        this.total = j;
    }
}
