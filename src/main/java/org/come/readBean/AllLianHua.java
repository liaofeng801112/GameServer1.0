package org.come.readBean;

import org.come.action.monitor.MonitorUtil;
import org.come.model.LianHua;

import java.util.Map;

/**炼化*/
public class AllLianHua {

    private Map<Integer, LianHua> lianhuas;

    public AllLianHua(Map<Integer, LianHua> lianhuas) {
        super();
        this.lianhuas = lianhuas;
    }

    public Map<Integer, LianHua> getLianhuas() {
        return lianhuas;
    }


    public LianHua get1000(int num) {
        for (LianHua e : lianhuas.values()) {
            if (e.getType() == 1000) {
                if (e.getNum() == num) {
                    return e;
                }
            }
        }
        return null;
    }

    public LianHua get2000(int num) {
        for (LianHua e : lianhuas.values()) {
            if (e.getType() == 2000) {
                if (e.getNum() == num){
                    return e;
                }

            }
        }
        return null;
    }

}
