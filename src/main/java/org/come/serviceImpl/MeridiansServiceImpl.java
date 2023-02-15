package org.come.serviceImpl;

import org.come.redis.RedisPoolUntil;
import org.come.service.MeridiansService;
import redis.clients.jedis.Jedis;

public class MeridiansServiceImpl implements MeridiansService {

    static String meridians_key = "meridians";

    @Override
    public String selectMeridians(Long roleid) {
        Jedis jedis = RedisPoolUntil.getJedis();
        try {
            return jedis.hget(meridians_key, roleid + "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisPoolUntil.returnResource(jedis);
        }
        return null;
    }

    @Override
    public void saveMeridians(Long roleid, String meridians) {
        Jedis jedis = RedisPoolUntil.getJedis();
        try {
            jedis.hset(meridians_key, roleid + "", meridians);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisPoolUntil.returnResource(jedis);
        }
    }

}
