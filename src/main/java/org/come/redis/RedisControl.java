package org.come.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.come.bean.LoginResult;
import org.come.entity.PackRecord;
import org.come.until.GsonUtil;

import redis.clients.jedis.Jedis;

public class RedisControl {
	/**
	 * @param <T>
	 * @param key 表名(e.g RedisParameterUtil.MOUNT)
	 * @param filed 主键
	 * @param
	 */
	public static <T> void insertKeyT(String key, String filed,T t) {
		RedisCacheBean<T> cacheBean=(RedisCacheBean<T>) RedisCacheUtil.getRedisCacheBean(key);
		if (cacheBean!=null) {cacheBean.addCache(filed, t);}
		Jedis jedis = RedisPoolUntil.getJedis();
		jedis.hset(key, filed, GsonUtil.getGsonUtil().getgson().toJson(t));// 插入单张表数据
		RedisPoolUntil.returnResource(jedis);//返回使用的连接
	}
	/**
	 * @param key 表名
	 * @param filed 主键
	 * @param value json数据
	 */
	public static void insertKey(String key, String filed, String value) {
		Jedis jedis=RedisPoolUntil.getJedis();
		jedis.hset(key,filed,value);// 插入单张表数据
		RedisPoolUntil.returnResource(jedis);//返回使用的连接
	}
	/**删除key 对应的vale 
	 * @param <T>*/
	public static <T> Long delForKey(String key, String value) {
		RedisCacheBean<T> cacheBean=(RedisCacheBean<T>) RedisCacheUtil.getRedisCacheBean(key);
		if (cacheBean!=null) {cacheBean.delCache(value);}
		Jedis jedis = RedisPoolUntil.getJedis();// 获取连接资源
		Long length = jedis.hdel(key, value);
		RedisPoolUntil.returnResource(jedis);// 返回连接资源
		return length;
	}
	/**在list中插入数据*/
	public static Long insertListRedis(String key, String id, String value) {
		Jedis jedis = RedisPoolUntil.getJedis();
		// 插入操作
		Long okOrNo=null;
		if (id!=null) {
			okOrNo = jedis.sadd(key+"_"+id, value);			
		}else {
			okOrNo = jedis.sadd(key, value);
		}
		RedisPoolUntil.returnResource(jedis);//返回资源
		jedis = null;// 清空数据，进行回收垃圾
		return okOrNo;
	}
	/**物品记录@return*/
	public static Long insertGoodsRecordRedis(String key, String id, String value) {
		Jedis jedis = RedisGoodPoolUntil.getJedis();
		// 插入操作
		Long okOrNo=null;
		if (id!=null) {
			okOrNo = jedis.sadd(key+"_"+id, value);			
		}else {
			okOrNo = jedis.sadd(key, value);
		}
		RedisGoodPoolUntil.returnResource(jedis);//返回资源
		jedis = null;// 清空数据，进行回收垃圾
		return okOrNo;
	}
	/**依据值删除 list中的数据*/
	public static String deletrValue(String key, String roleID, String member) {
		Jedis jedis = RedisPoolUntil.getJedis();
		Long okOrNo = jedis.srem(key + "_" + roleID, member);
		// 返回资源
		RedisPoolUntil.returnResource(jedis);
		// 清空数据，进行回收垃圾
		jedis = null;
		return okOrNo + "";
	}
	/**获取单条表数据*/
	public static <T> T getV(String key, String field, Class<T> classOfT) {
		RedisCacheBean<T> cacheBean=(RedisCacheBean<T>) RedisCacheUtil.getRedisCacheBean(key);
		if (cacheBean!=null) {
			T t=cacheBean.getCache(field);
			if (t!=null) {return t;}
		}
		Jedis jedis = RedisPoolUntil.getJedis();// 获取连接资源
		String a = jedis.hget(key, field);// 获取返回的字符串
		RedisPoolUntil.returnResource(jedis);// 返回使用的连接
		if (a!=null) {
			T t=GsonUtil.getGsonUtil().getgson().fromJson(a,classOfT);
			if (cacheBean!=null) {cacheBean.addCache(field, t);}
			return t;
		}
		return null;
	}
	/**获取查询list*/
	public static <T> List<T> getS(String qz, String id, Class<T> classOfT) {
		List<T> Ts = new ArrayList<>();
		//0没归属 1归属物品 2归属 召唤兽 3归属 灵宝 4归属 宝宝 5归属坐骑
		Jedis jedis = RedisPoolUntil.getJedis();
		Set<String> list =jedis.smembers(qz + "_" + id);
		if (list==null) {
			RedisPoolUntil.returnResource(jedis);
			return Ts;
		}
		RedisCacheBean<T> cacheBean=(RedisCacheBean<T>) RedisCacheUtil.getRedisCacheBean(qz);
		if (cacheBean!=null) {cacheBean.getCaches(list, Ts);}
		if (list.size()!=0) {
			String[] b = new String[list.size()];
			list.toArray(b);
			List<String> list2 = jedis.hmget(qz, b);
			RedisPoolUntil.returnResource(jedis);
			if (list2 != null) {
				for (int i = 0; i < list2.size(); i++) {
					if (list2.get(i)!=null) {
						T t=GsonUtil.getGsonUtil().getgson().fromJson(list2.get(i), classOfT);
						Ts.add(t);
						if (cacheBean!=null) {cacheBean.addCache(b[i], t);}
					}
				}
			}
			return Ts;
		}
		RedisPoolUntil.returnResource(jedis);
		return Ts;
	}
	/**
	 * 获取查询list
	 * @param qz 人物表名(e.g RedisParameterUtil.ROLE_MOUNT)
	 * @param ids 查询的主键集合
	 * @param classOfT 要转化的对象类型
	 * @return
	 */
	public static <T> List<T> getS(String qz, Set<String> ids, Class<T> classOfT) {
		List<T> Ts = new ArrayList<>();
		if (ids==null) {
			return Ts;
		}
		RedisCacheBean<T> cacheBean=(RedisCacheBean<T>) RedisCacheUtil.getRedisCacheBean(qz);
		if (cacheBean!=null) {cacheBean.getCaches(ids, Ts);}
		if (ids.size()!=0) {
			String[] v=new String[ids.size()];
			ids.toArray(v);
			Jedis jedis = RedisPoolUntil.getJedis();
			List<String> list = jedis.hmget(qz,v);
			RedisPoolUntil.returnResource(jedis);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					T t=GsonUtil.getGsonUtil().getgson().fromJson(list.get(i), classOfT);
					Ts.add(t);
					if (cacheBean!=null) {cacheBean.addCache(v[i],t);}
				}
			}	
		}	
		return Ts;
	}	
	/**添加记录表*/
	public static final String CADD="1";//增加
	public static final String CALTER="2";//修改
	public static final String CDELETE="3";//删除
	public static void insertController(String key, String id, String control) {
		Jedis jedis = RedisPoolUntil.getJedis();
		// 插入单张表数if
		String v=key+":"+id;
		String controlIN=jedis.hget(RedisParameterUtil.ROLE_CONTROL, v);
		if (controlIN==null) {
			jedis.hset(RedisParameterUtil.ROLE_CONTROL,v, control);
		}else if (control.equals(CALTER)) {
			
		}else if (control.equals(CDELETE)) {
			if (controlIN.equals(CADD)) {
				jedis.hdel(RedisParameterUtil.ROLE_CONTROL,v);
			}else {
				jedis.hset(RedisParameterUtil.ROLE_CONTROL,v,control);
			}
		}else {
			jedis.hset(RedisParameterUtil.ROLE_CONTROL,v,control);
		}
		// 返回使用的连接
		RedisPoolUntil.returnResource(jedis);
	}
	/**人物数据备份*/
	public static void userController(String key,String id,String control,String json) {
		Jedis jedis = RedisGoodPoolUntil.getJedis();
		// 插入单张表数if
		String v=key+":"+id;
		String controlIN=jedis.hget(RedisParameterUtil.USER_REDIS, v);
		if (controlIN!=null&&!controlIN.equals("")) {controlIN=controlIN.substring(0,1);}
		if (controlIN==null) {
			jedis.hset(RedisParameterUtil.USER_REDIS,v, control+json);
		}else if (control.equals(CALTER)) {
			
		}else if (control.equals(CDELETE)) {
			if (controlIN.equals(CADD)) {
				jedis.hdel(RedisParameterUtil.USER_REDIS,v);
			}else {
				jedis.hset(RedisParameterUtil.USER_REDIS,v,control+json);
			}
		}else {
			jedis.hset(RedisParameterUtil.USER_REDIS,v,control+json);
		}
		// 返回使用的连接
		RedisGoodPoolUntil.returnResource(jedis);
	}
	/**依据两个list名称返回交集list*/
	public static Set<String> sinterJiao(String... mes) {
		Jedis jedis = RedisPoolUntil.getJedis();
		Set<String> list =jedis.sinter(mes);
		RedisPoolUntil.returnResource(jedis);
		return list;
	}
    /**添加人物数据备份*/
	public static void addUpDate(LoginResult loginResult,PackRecord packRecord){
		Jedis jedis = RedisPoolUntil.getJedis();
		String id=loginResult.getRole_id().toString();
		jedis.hset(RedisParameterUtil.COPY_LOGIN,id,GsonUtil.getGsonUtil().getgson().toJson(loginResult));
		jedis.hset(RedisParameterUtil.COPY_PACK,id,GsonUtil.getGsonUtil().getgson().toJson(packRecord));
		RedisPoolUntil.returnResource(jedis);
	}

}
