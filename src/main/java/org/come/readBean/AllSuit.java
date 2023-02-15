package org.come.readBean;

import java.util.Map;

import org.come.entity.Suit;

public class AllSuit {

    // 所有套装信息
    private Map<Integer, Suit> rolesuit;

    public Map<Integer, Suit> getRolesuit() {
        return rolesuit;
    }

    public void setRolesuit(Map<Integer, Suit> rolesuit) {
        this.rolesuit = rolesuit;
    }
}
