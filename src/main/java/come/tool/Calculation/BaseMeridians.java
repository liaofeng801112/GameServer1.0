package come.tool.Calculation;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.come.bean.Meridians;
import org.come.server.GameServer;

import java.util.ArrayList;
import java.util.List;

public class BaseMeridians {
    //	经脉编号_修炼进度_品质_属性_值
    private int id;// 经脉编号
    private int exp;// 进度
    private int quality;// 品质
    private String key;
    private double value;
    private int stage;// 阶段，境界

    public BaseMeridians() {
    }

    public BaseMeridians(int id, int exp, int quality, String key, double value, int stage) {
        this.id = id;
        this.exp = exp;
        this.quality = quality;
        this.key = key;
        this.value = value;
        this.stage = stage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String toString() {
        return id + "_" + exp + "_" + quality + "_" + key + "_" + value + "_" + stage;
    }

    public double getKeyValue() {
        String[] ss = GameServer.getAllMeridians().getByQuality(id, quality).getStages().split("_");
        String s = ss[stage];
        return value * (1 + Double.parseDouble(s));
    }

    static BaseMeridians createBaseMeridians(String meridians) {
        String[] strs = meridians.split("_");
        return new BaseMeridians(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2]),
                strs[3], Double.parseDouble(strs[4]), Integer.parseInt(strs[5]));
    }

    public static BaseMeridians getBaseMeridians(List<BaseMeridians> list, int id) {
        for (BaseMeridians baseMeridians : list) {
            if (baseMeridians.getId() == id) {
                return baseMeridians;
            }
        }
        return null;
    }

    public static boolean isContains(List<BaseMeridians> list, int id) {
        for (BaseMeridians baseMeridians : list) {
            if (baseMeridians.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static List<BaseMeridians> createBaseMeridiansList(String meridians) {
        List<BaseMeridians> list = new ArrayList<BaseMeridians>();
        if (StrUtil.isBlankIfStr(meridians)) {
            return list;
        }
        String[] strs = meridians.split("\\|");
        for (String string : strs) {
            list.add(createBaseMeridians(string));
        }
        return list;
    }

    public static String createBaseMeridiansString(List<BaseMeridians> list) {
        String str = "";
        for (BaseMeridians m : list) {
            str += m.toString() + "|";
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    public static BaseQl[] createBaseQl(List<BaseMeridians> list) {
        BaseQl[] qls = new BaseQl[list.size()];
        for (int i = 0; i < qls.length; i++) {
            BaseMeridians bm = list.get(i);
            qls[i] = new BaseQl(bm.getKey(), bm.getKeyValue());
        }
        return qls;
    }

    public void reset(int attr, int quality) {
        // 经脉编号_修炼进度_品质_属性_值
        String a = null;
        int q = 0;
        if (attr == 1) {
            a = getKey();
        } else {
            Meridians m = GameServer.getAllMeridians().getFirstByType(getId());
            String[] ss = m.getAttrs().split("\\|");
            a = RandomUtil.randomEle(ss);
        }
        if (quality == 1) {
            q = getQuality();
        } else {
            Meridians m = GameServer.getAllMeridians().getRandomByType(getId());
            q = m.getQuality();
//			System.out.println(q);
        }
        Meridians m = GameServer.getAllMeridians().getByQuality(getId(), q);
        double value = RandomUtil.randomDouble(m.getMin(), m.getMax());
        // return new BaseMeridians(meridians.getId(), meridians.getExp(), q, a, value,
        // meridians.getStage());
        this.quality = q;
        this.key = a;
        this.value = value;
    }

}
