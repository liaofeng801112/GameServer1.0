package org.come.redis;

import org.come.server.GameServer;

public class RedisParameterUtil {
	/**宝宝*/
	public final static String BABY = GameServer.area+"BABY";
	/**好友*/
	public final static String FRIENDS = GameServer.area+"FRIENDS";
	/**帮派申请列表*/
	public final static String GANG_APPLY = GameServer.area+"GANG_APPLY";
	/**帮派*/
	public final static String GANG = GameServer.area+"GANG";
	/**帮派名称*/
	public final static String GANG_NAME = GameServer.area+"GANG_NAME";
	/**物品*/
	public final static String GOODS   = GameServer.area+"GOODS";
	public final static String GOODSID = GameServer.area+"GOODSID";//物品item表id
	public final static String GOODSST = GameServer.area+"GOODSST";//物品 背包 装备上 仓库
	/**坐骑*/
	public final static String MOUNT = GameServer.area+"MOUNT";
	/**召唤兽*/
	public final static String PET = GameServer.area+"PET";
	/**灵宝*/
	public final static String LINGBAO = GameServer.area+"LINGBAO";
	/**伙伴*/
	public final static String PAL = GameServer.area+"PAL";
	/**物品记录*/
	public final static String GOODS_RECORD = GameServer.area+"GOODS_RECORD";
	/**人物数据同步*/
	public final static String USER_REDIS = GameServer.area+"USER_REDIS";
	/**人物操作*/
	public final static String ROLE_CONTROL = GameServer.area+"control_redis_delete";
	/**人物数据备份对象 LoginResult*/
	public final static String COPY_LOGIN = GameServer.area+"COPY_LOGIN";
	/**人物数据备份对象 PackRecord*/
	public final static String COPY_PACK = GameServer.area+"COPY_PACK";
	/**salesgoods存储表名*/
	public final static String SALESGOODS_STATUES=GameServer.area+"SALESGOODS_STATUES";
}
