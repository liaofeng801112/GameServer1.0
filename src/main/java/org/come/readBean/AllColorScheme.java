package org.come.readBean;

import java.util.Map;

import org.come.model.ColorScheme;

public class AllColorScheme {
    // 所有变色的信息
    private Map<Integer, ColorScheme> allMap;

    public Map<Integer, ColorScheme> getAllMap() {
        return allMap;
    }

    public void setAllMap(Map<Integer, ColorScheme> allMap) {
        this.allMap = allMap;
    }
}
