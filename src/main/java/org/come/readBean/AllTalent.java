package org.come.readBean;

import java.util.Map;

import org.come.model.Talent;

public class AllTalent {
    // 所有npc的信息
    private Map<Integer, Talent> allTalent;

    public Map<Integer, Talent> getAllTalent() {
        return allTalent;
    }

    public void setAllTalent(Map<Integer, Talent> allTalent) {
        this.allTalent = allTalent;
    }
}
