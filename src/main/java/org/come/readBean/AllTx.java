package org.come.readBean;

import java.util.Map;

import org.come.bean.RoleTxBean;

public class AllTx {

    // 所有新手引导信息
    private Map<Integer, RoleTxBean> txmap;

    public Map<Integer, RoleTxBean> getTxmap() {
        return txmap;
    }

    public void setTxmap(Map<Integer, RoleTxBean> txmap) {
        this.txmap = txmap;
    }

}
