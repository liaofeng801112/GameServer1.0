package org.come.redis;

import org.come.server.GameServer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 实例化redis 连接池
 * @author 黄建彬
 *
 */

public class RedisGoodPoolUntil {
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		private static int MAX_ACTIVE = -1;
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
		private static int MAX_IDLE = 256;
		// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
		private static int MAX_WAIT = 60000;
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		private static boolean TEST_ON_BORROW = true;
		private static JedisPool jedisPool = null;
		/**初始化Redis连接池*/
		public static void init() {
			try {
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(MAX_ACTIVE);
				config.setMaxIdle(MAX_IDLE);
				config.setMaxWaitMillis(MAX_WAIT);
				config.setTestOnBorrow(TEST_ON_BORROW);
				jedisPool = new JedisPool(config,GameServer.redisIp,6379);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 获取Jedis实例
		 * @return
		 */
		public synchronized static Jedis getJedis() {
			try {
				if (jedisPool != null) {
					Jedis resource = jedisPool.getResource();
					return resource;
				} else {
					//实例化连接池
					init();
					Jedis resource = jedisPool.getResource();
					return resource;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * 释放jedis资源
		 * @param jedis
		 */
		public static void returnResource(final Jedis jedis) {
			if (jedis != null) {
				jedisPool.returnResourceObject(jedis);
			}
		}
}
