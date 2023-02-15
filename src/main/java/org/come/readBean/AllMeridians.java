package org.come.readBean;

import cn.hutool.core.util.RandomUtil;
import come.tool.Calculation.BaseMeridians;
import org.come.bean.Meridians;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AllMeridians {

    private Map<Integer, List<Meridians>> meridiansMap = new ConcurrentHashMap<>();

    public Map<Integer, List<Meridians>> getMeridians() {
        return meridiansMap;
    }

    public void setMeridians(List<Meridians> meridiansList) {
        for (Meridians meridians : meridiansList) {
            if (!meridiansMap.containsKey(meridians.getId())) {
                meridiansMap.put(meridians.getId(), new ArrayList<>());
            }
            meridiansMap.get(meridians.getId()).add(meridians);
        }
    }

    public List<Meridians> getByType(int id) {
        return meridiansMap.get(id);
    }

    public Meridians getByQuality(int id, int quality) {
        for (Meridians m : getByType(id)) {
            if (m.getQuality() == quality) {
                return m;
            }
        }
        return null;
    }

    public Meridians getRandomByType(int id) {
        for (Meridians m : meridiansMap.get(id)) {
            int p = m.getProbability();
            int r = RandomUtil.randomInt(100) + 1;
            if (p >= r) {
                return m;
            }
        }
        return RandomUtil.randomEle(meridiansMap.get(id));
    }

    public Meridians getFirstByType(int id) {
        return meridiansMap.get(id).get(0);
    }

    public static BaseMeridians createRandomBaseMeridians(Meridians meridians) {
        // 经脉编号_修炼进度_品质_属性_值
        String[] ss = meridians.getAttrs().split("\\|");
        String attr = RandomUtil.randomEle(ss);
        double value = RandomUtil.randomDouble(meridians.getMin(), meridians.getMax());
        BaseMeridians m = new BaseMeridians(meridians.getId(), 0, meridians.getQuality(), attr, value, 0);
        return m;
    }
}
