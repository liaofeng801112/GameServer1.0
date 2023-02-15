package org.come.thread;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;

public class DataBaseControl {

    public static DataBaseManage manageGoodstable = new GoodstableManage();
    public static DataBaseManage manageBaby = new BabyManage();
    public static DataBaseManage manageMount = new MountManage();
    public static DataBaseManage manageLingBao = new LingBaoManage();
    public static DataBaseManage managePal = new PalManage();
    public static DataBaseManage managePet = new PetManage();

    private static Map<String, DataBaseManage> map = new HashMap<String, DataBaseManage>();

    static {
        map.put(RedisParameterUtil.GOODS, manageGoodstable);
        map.put(RedisParameterUtil.PAL, managePal);
        map.put(RedisParameterUtil.PET, managePet);
        map.put(RedisParameterUtil.MOUNT, manageMount);
        map.put(RedisParameterUtil.BABY, manageBaby);
        map.put(RedisParameterUtil.LINGBAO, manageLingBao);
    }

    public void control(String value, String ID, String type) {
        if ("3".equals(value)) {
            map.get(type).del(new BigDecimal(ID));
        } else {
            Class<Object> list = getList(type);
            if (list == null)
                return;
            Object v = RedisControl.getV(type, ID, list);
            if (v == null) {
                System.out.println("同步数据库出错:" + type + ":" + ID + ":" + value);
                return;
            }
            if ("2".equals(value)) {
                map.get(type).upd(v);
            } else if ("1".equals(value)) {
                map.get(type).add(v);
            }
        }
    }

    /** HGC-2020-01-20 */
    /** 判断类型 */
    public static <T> Class<T> getList(String value) {
        if (RedisParameterUtil.BABY.equals(value)) {
            return (Class<T>) Baby.class;
        } else if (RedisParameterUtil.GOODS.equals(value)) {
            return (Class<T>) Goodstable.class;
        } else if (RedisParameterUtil.LINGBAO.equals(value)) {
            return (Class<T>) Lingbao.class;
        } else if (RedisParameterUtil.MOUNT.equals(value)) {
            return (Class<T>) Mount.class;
        } else if (RedisParameterUtil.PAL.equals(value)) {
            return (Class<T>) Pal.class;
        } else if (RedisParameterUtil.PET.equals(value)) {
            return (Class<T>) RoleSummoning.class;
        }
        return null;
    }
}
