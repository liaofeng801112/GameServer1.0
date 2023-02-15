package org.come;

import org.come.redis.RedisPoolUntil;
import redis.clients.jedis.Jedis;
import java.util.UUID;

/**
 * 增减简单接口校验配置
 */
public class ApiValid {

    public static final  String  VALID_NAME = "wdltxyss";
    public static final  String VALID_VALUE = "zzswxy2o!@#HH";
    private String token="";

    public static String getToken(String userName) {
        String uuid = UUID.randomUUID().toString();
        Jedis jedis = RedisPoolUntil.getJedis();
        jedis.set("dhToken:"+userName,uuid);
        //设置session的过期时间 时间单位是秒
        jedis.expire("dhToken:"+userName, 1800);
        return uuid;
    }

    public static boolean vaildToken(String token,String userName){
        Jedis jedis = RedisPoolUntil.getJedis();
        if(jedis.get("dhToken:"+userName).equals(token)) {
            jedis.expire("dhToken:"+userName, 1800);
            return true;
        }
        else
            return false;
    }
}
