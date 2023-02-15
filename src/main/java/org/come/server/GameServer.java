package org.come.server;

import come.tool.BangBattle.*;
import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.FightingData.Battlefield;
import come.tool.FightingDataAction.PhyAttack;
import come.tool.Role.RoleCard;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.DNTG.DNTGAward;
import come.tool.Scene.LTS.LTSUtil;
import come.tool.Scene.LaborDay.LaborScene;
import come.tool.Scene.PKLS.PKLSScene;
import come.tool.Scene.PKLS.lsteamBean;
import come.tool.Scene.RC.RCScene;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Stall.StallPool;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;
import come.tool.newTeam.TeamBean;
import come.tool.newTeam.TeamRole;
import come.tool.newTeam.TeamUtil;
import come.tool.newTrans.TransUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.come.action.exchange.ExchangeUtil;
import org.come.action.festival.HatchvalueAction;
import org.come.action.lottery.Draw;
import org.come.action.lottery.EventRanking;
import org.come.action.monitor.MonitorUtil;
import org.come.action.suit.*;
import org.come.bean.Bbuy;
import org.come.bean.LoginResult;
import org.come.bean.PathPoint;
import org.come.bean.RoleTxBean;
import org.come.entity.*;
import org.come.handler.MainServerHandler;
import org.come.handler.SendMessage;
import org.come.model.*;
import org.come.protocol.Agreement;
import org.come.protocol.ParamTool;
import org.come.readBean.AllActive;
import org.come.readBean.AllLianHua;
import org.come.readBean.AllMeridians;
import org.come.readUtil.ReadPoolUtil;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisGoodPoolUntil;
import org.come.redis.RedisPoolUntil;
import org.come.serverInitializer.MainServerInitializer;
import org.come.task.RefreshMonsterTask;
import org.come.thread.RedisEqualWithSqlThread;
import org.come.tool.Goodtype;
import org.come.tool.ReadExelTool;
import org.come.tool.SplitStringTool;
import org.come.tool.WriteOut;
import org.come.until.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameServer implements ServletContextListener {

	// 服务器校验码
	public static String signNum = "ae82a5a093ef80266dc4fe0f5c70e98a";
	/*** 区编号*/
	private static int qh;
	/*** 充值链接名称*/
	private static String gameServerPay;
	/*** 服务端监听的端口地址*/
	private static int portNumber;
	/*** 注册开关*/
	public static int registerOnOff;
	/**
	 * 是否重置redis 1重置
	 */
	public static int redisReset;
	/**
	 * 是否开启锁定炼化 0开启 1不开启
	 */
	public static int lianhua;
	/**
	 * redis端口
	 */
	public static String redisIp = "127.0.0.1";
	public static int redisPort = 16379;
	/**
	 * 合区时前缀
	 */
	public static String QZ;
	/**
	 * 登录手机验证开启
	 */
	public static boolean isCode = false;
	/**
	 * 表分区路径
	 */
	public static String tablePath;
	/**
	 * 表分区
	 */
	public static Map<String, String> tableZone;
	private static final long serialVersionUID = 1L;
	// 标记每个socket所对应的角色登入信息
	private static ConcurrentHashMap<ChannelHandlerContext, LoginResult> allLoginRole;

	// 储存地图上的人物通道集合
	private static ConcurrentHashMap<Long, ConcurrentHashMap<String, ChannelHandlerContext>> mapRolesMap;
	// 储存帮派里的人物通道集合
//	private static ConcurrentHashMap<BigDecimal,ConcurrentHashMap<String,ChannelHandlerContext>> gangRolesMap;
	// 储存名字通道集合
	private static ConcurrentHashMap<String, ChannelHandlerContext> roleNameMap;
	// 标记每个登陆游戏名字所对应的socket
	private static ConcurrentHashMap<String, ChannelHandlerContext> inlineUserNameMap;
	// 标记每个socket所对应的登陆游戏名字
	private static ConcurrentHashMap<ChannelHandlerContext, String> socketUserNameMap;


	// 存放地图ID与地图信息相对应的集合
	private static ConcurrentHashMap<String, Gamemap> gameMap;
	// 所有npc信息
	private static ConcurrentHashMap<String, Npctable> npcMap;
	// 所有传送门信息
	private static ConcurrentHashMap<Integer, Door> doorMap;
	// 所有怪物信息
	private static ConcurrentHashMap<String, Monster> monsterMap;
	// 储存所有物品与物品ID相对应的集合 version
	private static ConcurrentHashMap<BigDecimal, Goodstable> allGoodsMap;
	// 获得物品名字与物品相对应的集合
	private static ConcurrentHashMap<String, Goodstable> getGoods;
	// 获得物品ID与培养信息相对应的集合
	private static ConcurrentHashMap<String, List<Decorate>> allDecorate;
	// 所有的随机地址
	private static ConcurrentHashMap<String, List<Sghostpoint>> monsterpointMap;

	private static ConcurrentHashMap<String, Robots> allRobot;
	// 获得所有的升级物品
	private static ConcurrentHashMap<String, List<Newequip>> sameNewequipMap;
	// 获得所有洗练信息
	private static ConcurrentHashMap<String, List<Alchemy>> allAlchemy;
	// 获得所有仙器信息
	private static ConcurrentHashMap<String, List<Xianqi>> getAllXianqi;
	// 获得所有仙器类型信息
	private static ConcurrentHashMap<String, List<String>> XianqiTypeValue;
	// 所有符石
	private static ConcurrentHashMap<BigDecimal, Goodstable> allLingbaoFushi;
	// 技能列表
	private static ConcurrentHashMap<String, Skill> getSkill;
	//任务
	private static ConcurrentHashMap<Integer, TaskData> allTaskData;
	private static ConcurrentHashMap<Integer, TaskSet> allTaskSet;
	// 所有的神兵石属性
	private static ConcurrentHashMap<String, List<GodStone>> godMap;
	// 所有召唤兽
	private static ConcurrentHashMap<BigDecimal, RoleSummoning> allPet;
	// 所有的灵宝
	private static ConcurrentHashMap<String, Lingbao> allLingbao;
	// 掉落表
	private static ConcurrentHashMap<String, Dorp> allDorp;
	// 当前在线人数
	public static AtomicInteger inlineNum;
	//最高在线人数
	public static int inlineMax;
	// 路径前缀
	private static String TXTPATH;
	// boosID与boos对应的map
	public static ConcurrentHashMap<String, Boos> boosesMap;
	// 蒋功堂
	public static CopyOnWriteArrayList<RewardHall> rewardList;
	// 所有排行榜内容
	public static ConcurrentHashMap<Integer, List<LoginResult>> allBangList;
	// 商城表
	private static ConcurrentHashMap<String, Eshop> allEshopGoods;
	// 商店表
	private static ConcurrentHashMap<String, Shop> allShopGoods;
	// 回收表
	private static ConcurrentHashMap<Integer, Bbuy> allBbuys;
	// 套装表
	private static ConcurrentHashMap<Integer, Suit> allSuits;
	// 特效表
	private static ConcurrentHashMap<Integer, RoleTxBean> allTXs;
	// 注册赠送
	private static List<Present> presents;
	// 宝石
	private static ConcurrentHashMap<String, Gem> gems;
	// 经验表
	private static ConcurrentHashMap<Integer, Long> expMap;
	// 坐骑表
	private static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Mount>> allMount;
	// 颜色表
	private static ConcurrentHashMap<String, ColorScheme> allColor;
	// 天资表
	private static ConcurrentHashMap<Integer, Talent> alltalent;
	// 限购表
	private static ConcurrentHashMap<String, Lshop> allLShopGoods;
	// 奖池
	private static ConcurrentHashMap<Integer, Draw> allDraws;
	// 卡组
	private static ConcurrentHashMap<Integer, aCard> allACard;
	// 称谓
	private static ConcurrentHashMap<String, Title> alltitle;
	// 充值用的
	private static List<Title> moneyTitles;
	// 活动
	private static ConcurrentHashMap<Integer, EventModel> allevent;
	// 翅膀培养表
	private static ConcurrentHashMap<Long, WingTraining> allWingTraining;
	// 星位
	private static ConcurrentHashMap<String, StarPalace> allStarPalace;
	//礼包集合
	private static ConcurrentHashMap<Integer, List<ChongjipackBean>> packgradecontrol;
	//vip兑换
	private static List<PayvipBean> payvipList;
	//大闹天宫奖励表
	private static ConcurrentHashMap<Integer, DNTGAward> allDntg;
	/**
	 * 伙伴表
	 */
	private static ConcurrentHashMap<Integer, PalData> allPalData;
	/**
	 * 伙伴装备表
	 */
	private static ConcurrentHashMap<Long, PalEquip> allPalEquip;
	/**
	 * 召唤兽兑换表
	 */
	private static ConcurrentHashMap<Integer, PetExchange> allPetExchange;
	private static ConcurrentHashMap<Integer, GoodsExchange> allGoodsExchange;
	/**
	 * 活跃表
	 */
	private static AllActive allActive;
	/**
	 * 成就表
	 */
	private static ConcurrentHashMap<Integer, Achieve> allAchieve;
	/** 炼化表 */
	private static AllLianHua allLianHua;

	private static String[] allStarPalaceKey;

	private static AllMeridians allMeridians;
	// 获取物品名称与掉落的怪物名称
	private static ConcurrentHashMap<String, List<String>> goodsByRobot;

	public static ConcurrentHashMap<String, List<String>> getGoodsByRobot() {
		return goodsByRobot;
	}
	public static void setGoodsByRobot(ConcurrentHashMap<String, List<String>> goodsByRobot) {
		GameServer.goodsByRobot = goodsByRobot;
	}


	// 服务器区服
	public static String area;

	public static GameServer gameServer;

	public GameServer() {
		allLoginRole = new ConcurrentHashMap<ChannelHandlerContext, LoginResult>();
		roleNameMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
		inlineUserNameMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
		socketUserNameMap = new ConcurrentHashMap<ChannelHandlerContext, String>();
		inlineNum = new AtomicInteger();
	}

	public void start() throws InterruptedException {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.localAddress(new InetSocketAddress(portNumber));
			b.childHandler(new MainServerInitializer());
			b.childOption(ChannelOption.SO_KEEPALIVE, true);//保持连接生命，不因空闲而断开
			b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);//Netty实现了一个Java版的J
			// emalloc内存管理库 ByteBuf内存池 要
			// 搭配ReferenceCountUtil.release(msg)
			b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			b.option(ChannelOption.SO_BACKLOG, 1024);//指定此套接口排队的最大连接个数
			b.option(ChannelOption.TCP_NODELAY, false);// 如果false的话会缓冲数据达到一定量在flush,降低系统网络调用（具体场景）
			b.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);//容量动态调整的接收缓冲区分配器 以节约内存
			// 服务器绑定端口监听
			ChannelFuture f = b.bind(portNumber).sync();
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	// 启动主服务器
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		for(int i=3;i>=0;i--){
//			System.out.println("bolin275064733");
//			Scanner admin=new Scanner(System.in);
//			String admins=admin.nextLine();
//			System.out.println("admin275064733");
//			Scanner pw=new Scanner(System.in);
//			String pws=admin.nextLine();
//			if(!admins.equals("xy2o.com")||!pws.equals("www.xy2o.com")){
//				System.out.println("用户名或者密码错误不正确");
//				System.out.println("你还有"+(i-1)+"次机会");
//			} else {
//					break;}
//			}
		Properties properties = new Properties();
		try {
			// 使用ClassLoader加载properties配置文件生成对应的输入流
			InputStream in = GameServer.class.getClassLoader().getResourceAsStream("important.properties");
			properties.load(in);// 使用properties对象加载输入流
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 获取key对应的value值 version
		portNumber = Integer.parseInt(properties.getProperty("server.port"));
		registerOnOff = Integer.parseInt(properties.getProperty("server.register"));
		area = properties.getProperty("server.area");
		gameServerPay = properties.getProperty("server.pay");
		redisReset = Integer.parseInt(properties.getProperty("server.redis"));
		lianhua = Integer.parseInt(properties.getProperty("server.lianhua"));
		redisIp = properties.getProperty("server.redisip");
		redisPort = Integer.parseInt(properties.getProperty("server.redisport"));
		QZ = properties.getProperty("server.QZ");
		qh = Integer.parseInt(properties.getProperty("server.qh"));
		tablePath = properties.getProperty("server.tablePath");
		tableZone = GsonUtil.getGsonUtil().getgson().fromJson(properties.getProperty("server.tableZone"), Map.class);
		String version = properties.getProperty("server.version");
		if (lianhua == 1) {
			version = version + "L1";
		}
		MainServerHandler.VS = Agreement.getAgreement().VersionAgreement(version);
		TXTPATH = this.getClass().getClassLoader().getResource("/").getPath() + "GetTXT/";
		File file = new File(TXTPATH);
		file.mkdirs();
		System.err.println(portNumber + " 当前端口,正在启动主服务器...");
		// 主服务器
		gameServer = new GameServer();
		System.err.println(portNumber + " 当前端口,正在初始化数据...");
		try {
			gameServer.loadResource();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.err.println(portNumber + " 当前端口,初始化数据完毕");
		new Thread() {
			@Override
			public void run() {
				try {
					gameServer.start();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		System.err.println(portNumber + " 当前端口,主服务器启动完毕");
		BangBattlePool.getBangBattlePool();
//		RefreshMonsterTask.createTableSplace(0);
		// 怪物刷新线程
		RefreshMonsterTask activityRunnable = new RefreshMonsterTask();
		Thread t0 = new Thread(activityRunnable);
		t0.start();
		SceneUtil.init();

		/** zrikka 2020 0419 与账号服务器 进行连接 */
//		try {
//			new ClientMapAction();
//			ClientSendMessage.getClientUntil(UrlUntil.account_ip, UrlUntil.account_port);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/***/
	}

	// **false表示运行 true表示关闭
	public static boolean OPEN = false;

	// 关闭服务器时执行操作
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		OPEN = true;
		System.err.println("开始关闭服务器,准备保存数据，请不要直接关闭Tomcat");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 开始处理摆摊物品
		try {
			System.err.println("开始处理摆摊物品");
			StallPool.getPool().guanbi();
			System.err.println("开始保存擂台赛积分数据");
			LTSUtil.getLtsUtil().BCLts();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			BangFileSystem.getBangFileSystem().DataSaving(BangBattlePool.getBangBattlePool());
			GangUtil.upGangs(false);//保存帮派
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.err.println("开始备份玩家数据");
		Iterator<Map.Entry<ChannelHandlerContext, LoginResult>> entries = GameServer.getAllLoginRole().entrySet().iterator();
		while (entries.hasNext()) {
			Entry<ChannelHandlerContext, LoginResult> entrys = entries.next();
			// 保存用户信息
			LoginResult loginResult = entrys.getValue();
			if (loginResult == null) {
				continue;
			}
			try {
				// 保存角色信息
				loginResult.setUptime(System.currentTimeMillis() + "");
				RoleData roleData = RolePool.getRoleData(loginResult.getRole_id());
				roleData.roleRecover(loginResult);
				RedisControl.addUpDate(loginResult, roleData.getPackRecord());
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("处理玩家备份失败" + loginResult.getRolename());
				e.printStackTrace();
			}
		}
		/** 通知玩家重启了 */
		SendMessage.sendMessageToAllRoles(Agreement.getAgreement().serverstopAgreement());
		RedisEqualWithSqlThread.AllToDataRole();
		try {
			Thread.sleep(10000);
			RedisEqualWithSqlThread.AllToDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (WriteOut.buffer != null) {
			WriteOut.writeTxtFile(WriteOut.buffer.toString());
		}
		try {
			LaborScene.Save(true);//劳动节活动
			// 保存孵化值
			CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "hatch.txt", HatchvalueAction.hatch.toString().getBytes());
			// 保存首杀记录
			saveEventRoles();
			Scene scene = SceneUtil.getScene(SceneUtil.RCID);
			if (scene != null) {
				RCScene rcScene = (RCScene) scene;
				CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "bbRecord.txt", GsonUtil.getGsonUtil().getgson().toJson(rcScene.getBbRecord()).getBytes());
			}
			scene = SceneUtil.getScene(SceneUtil.PKLSID);
			if (scene != null) {
				PKLSScene pklsScene = (PKLSScene) scene;
				lsteamBean lsteamBean = new lsteamBean();
				lsteamBean.setLSTeams(pklsScene.getLSTeams());
				CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "lsteam.txt", GsonUtil.getGsonUtil().getgson().toJson(lsteamBean).getBytes());
			}
			//今日消耗数据
			CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "money.txt", GsonUtil.getGsonUtil().getgson().toJson(MonitorUtil.getMoney()).getBytes());
			//保存销售记录
			RefreshMonsterTask.upBuyCount(-1, false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 退出系统
		System.exit(0);
	}

	// 加载数据
	public void loadResource() {

		// 初始化所有操作类
		AllServiceUtil.initServices();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 50; i++) {
			if (!ReadPoolUtil.readTypeTwo(buffer, i)) {
				System.out.println(buffer.toString());
				try {
					Thread.sleep(999999999);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
		}
		GangUtil.init();
		List<ChongjipackBean> chongjipackBeans = AllServiceUtil.getChongjipackServeice().selectAllPack();
		packgradecontrol = new ConcurrentHashMap<Integer, List<ChongjipackBean>>();
		for (int i = 0; i < chongjipackBeans.size(); i++) {
			ChongjipackBean bean = chongjipackBeans.get(i);
			List<ChongjipackBean> packs = packgradecontrol.get(bean.getPacktype());
			if (packs == null) {
				packs = new ArrayList<ChongjipackBean>();
				packgradecontrol.put(bean.getPacktype(), packs);
			}
			packs.add(bean);
		}
		payvipList = AllServiceUtil.getPayvipBeanServer().selectAllVip();
		// 获得所有蒋功堂物品
		RewardHallExample example = new RewardHallExample();
		rewardList = AllServiceUtil.getRewardHallMallService().selectByExample(example);
		// 获取排行榜信息
		bangLists();
		LTSUtil.getLtsUtil();
		// 读取孵化值
		String hatch = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "hatch.txt");
		if (hatch != null) {
			HatchvalueAction.hatch.set(Integer.parseInt(hatch));
			;
		}
		// 调用业务逻辑
		ParamTool.handles();
		BangFileSystem.getBangFileSystem();
		PhyAttack.initSkill();
		ExchangeUtil.init();
		// 初始化redis
		RedisPoolUntil.init();
		RedisGoodPoolUntil.init();
		new RedisCacheUtil().databaseToCache();
	}

	// 获取排行榜
	public static void bangLists() {
		allBangList = new ConcurrentHashMap<Integer, List<LoginResult>>();
		allBangList.put(1, AllServiceUtil.getRoleTableService().selectOrderByType(1));
		allBangList.put(2, AllServiceUtil.getRoleTableService().selectOrderByType(2));
		allBangList.put(3, AllServiceUtil.getRoleTableService().selectOrderByType(3));
		allBangList.put(6, AllServiceUtil.getRoleTableService().selectOrderByType(6));
	}

	/**
	 * 将指定的客户端的输出流存入集合中
	 */
	public synchronized static void addOuts(ChannelHandlerContext ctx, LoginResult loginResult) {
		allLoginRole.put(ctx, loginResult);
		roleNameMap.put(loginResult.getRolename(), ctx);
		inlineUserNameMap.put(loginResult.getUserName(), ctx);
		socketUserNameMap.put(ctx, loginResult.getUserName());
		if (loginResult.getGang_id() != null) {
			GangDomain gangDomain = GangUtil.getGangDomain(loginResult.getGang_id());
			if (gangDomain != null) {
				gangDomain.upGangRole(loginResult.getRole_id(), ctx);
			}
		}
	}

	public static Object userLock = new Object();

	/**
	 * 用户下线处理
	 */
	public static void userDown(ChannelHandlerContext ctx) {
		synchronized (userLock) {
			userDownTwos(ctx);
		}
	}

	/**
	 * 用户下线处理
	 */
	public static void userDownTwos(ChannelHandlerContext ctx) {
		if (OPEN)
			return;
		// 退出的用户信息
		LoginResult exitUser = GameServer.getAllLoginRole().get(ctx);
		if (exitUser == null) {// 判断用户是否登入,用户没有登入时没有角色信息
			return;
		}
		try {
			// 从战斗人中去除
			BattleData battle = BattleThreadPool.BattleDatas.get(exitUser.getFighting());
			if (battle != null) {
				battle.getParticipantlist().remove(exitUser.getRolename());
			}
			StallPool.getPool().updateState(exitUser.getBooth_id(), StallPool.MANAGE, exitUser.getRole_id());
			TransUtil.roleDown(exitUser.getRolename());
			BangFight bangFight = BangBattlePool.getBangBattlePool().getBangFight(exitUser.getGang_id());
			if (bangFight != null) {
				Build door = bangFight.getBuild(exitUser.getRolename());
				if (door != null) {
					door.setRoleName(null);
					door.setState(Build.IDLE);
					door.setTime(0);
				}
				Member member = bangFight.getMember(exitUser.getRolename(), exitUser.getGang_id());
				if (member != null) {
					if (bangFight.Launch == member) {
						bangFight.Launch = null;
						StringBuffer buffer = new StringBuffer();
						buffer.append("#G ");
						buffer.append(exitUser.getRolename());
						buffer.append(" #Y灰溜溜离开挑战台");
						bangFight.BattleNews(buffer.toString());
					}
					member.setState(-1);
				}
			}
			// 当用户退出时，将该角色信息广播出去
			String message = Agreement.getAgreement().UserRetreatAgreement(exitUser.getRolename());
			SendMessage.sendMessageToMapRoles(exitUser.getMapid(), message);
			//队伍中角色退出
			TeamBean teamBean = TeamUtil.getTeam(exitUser.getTroop_id());
			TeamRole teamRole = teamBean != null ? teamBean.getTeamRole(exitUser.getRole_id()) : null;
			if (teamRole != null) {
				teamBean.stateLeave(teamRole, -2);
			}
			// 宝宝剩余时间
			if (exitUser.getHavebaby() != null && !exitUser.getHavebaby().equals("")) {
				exitUser.setHavebaby(exitUser.getHavebaby() - (int) (System.currentTimeMillis() - exitUser.getMakeloveTime()) / 1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		// 保存角色信息
		exitUser.setUptime(System.currentTimeMillis() + "");
		RoleData roleData = RolePool.deleteRoleData(exitUser.getRole_id());
		if (roleData == null) {
			roleData = exitUser.getRoleData();
			AllServiceUtil.getRecordService().insert(new Record(0, "找不到RoleData:" + exitUser.getRole_id() + (roleData == null ? ":依然找不到" : "")));
		}
		String TIME = TimeUntil.getPastDate();
		String IP = roleData != null ? roleData.getIP() : null;
		UserTable userTable = new UserTable();
		userTable.setUSERLASTLOGIN(TIME);
		userTable.setLoginip(IP);
		userTable.setCodecard(exitUser.getCodecard());
		userTable.setMoney(exitUser.getMoney());
		userTable.setUsername(exitUser.getUserName());
		try {
			AllServiceUtil.getUserTableService().updateUser(userTable);
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("人物数据保存报错UserTable:" + GsonUtil.getGsonUtil().getgson().toJson(userTable), 9999);
		}
		if (roleData != null) {
			try {
				AllServiceUtil.getPackRecordService().updateByPrimaryKeySelective(roleData.getPackRecord());
			} catch (Exception e) {
				// TODO: handle exception
				WriteOut.addtxt("人物数据保存报错PackRecord:" + GsonUtil.getGsonUtil().getgson().toJson(roleData.getPackRecord()), 9999);
			}
			try {
				roleData.roleRecover(exitUser);
			} catch (Exception e) {
				// TODO: handle exception
				WriteOut.addtxt("人物数据转换报错:" + MainServerHandler.getErrorMessage(e), 9999);
			}
		}
		try {
			AllServiceUtil.getRoleTableService().updateRoleWhenExit(exitUser);
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("人物数据保存报错:" + GsonUtil.getGsonUtil().getgson().toJson(exitUser), 9999);
			e.printStackTrace();
		}
		LogIn(IP, exitUser.getRolename(), false);//登录日志打印
		try {
			//清除集合
			GameServer.getAllLoginRole().remove(ctx);
			GameServer.getRoleNameMap().remove(exitUser.getRolename());
			GameServer.getInlineUserNameMap().remove(exitUser.getUserName());
			GameServer.getSocketUserNameMap().remove(ctx);
			GameServer.getMapRolesMap().get(exitUser.getMapid()).remove(exitUser.getRolename());
			// 帮派集合
			GangDomain gangDomain = GangUtil.getGangDomain(exitUser.getGang_id());
			if (gangDomain != null) {
				gangDomain.downGangRole(exitUser.getRole_id());
			}
		} catch (Exception e) {
			// TODO: handle exception
			// 防止退出操作报错导致对象在缓存中没有清除
			WriteOut.addtxt("人物退出报错，清除缓存对象" + e.toString(), 9999);
			e.printStackTrace();
		}
	}

	/**
	 * 登录日志 true 上线
	 */
	public static void LogIn(String IP, String roleName, boolean is) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("时间:");
		buffer.append(TimeUntil.getPastDate());
		buffer.append("__IP__");
		buffer.append(IP);
		buffer.append("__");
		buffer.append(roleName);
		if (is) {
			buffer.append(":玩家上线:");
			buffer.append(inlineNum.incrementAndGet());
		} else {
			buffer.append(":玩家下线:");
			buffer.append(inlineNum.decrementAndGet());
		}
		System.err.println(buffer.toString());
	}

	public static Random random = new Random();

	// 获取物品
	public static Goodstable getGood(BigDecimal id) {
		Goodstable goodstable = null;
		if (id.longValue() < 0) {
			RoleTxBean txBean = getTxBean(-id.intValue());
			if (txBean != null) {
				goodstable = new Goodstable();
				goodstable.setGoodsname(txBean.getRdName());
				goodstable.setGoodsid(id);
				goodstable.setType(-1);
			}
			return goodstable;
		} else if (id.longValue() <= 25) {
			goodstable = allLingbaoFushi.get(id);
		} else {
			goodstable = getAllGoodsMap().get(id);
		}
		if (goodstable == null) {
			return null;
		}
		long type = goodstable.getType();
		if (type == 1000) {
			id = SplitStringTool.GoodRandomId(goodstable.getValue());
			return getGood(id);
		}
		goodstable = goodstable.clone();
		if (type == 2051 || type == 2052 || type == 1007 || type == 2057) {
			Sghostpoint sghostpoint = getSghostpoint("藏宝图");
			PathPoint point = sghostpoint.getPoints()[Battlefield.random.nextInt(sghostpoint.getPoints().length)];
			StringBuffer buffer = new StringBuffer();
			buffer.append("宝图=");
			buffer.append(sghostpoint.getPointkey());
			buffer.append(",");
			buffer.append(sghostpoint.getPointmap());
			buffer.append(",");
			buffer.append(point.getX());
			buffer.append(",");
			buffer.append(point.getY());
			buffer.append(",");
			buffer.append(type);
			goodstable.setValue(buffer.toString());
		} else if (id.longValue() <= 25) {
			goodstable.setQuality(new Long(0));
			goodstable.setValue(SplitFushiValue.splitFushiValue(goodstable
					.getValue()));
		} else if (type == 80156) {// 挖宝类型
			StringBuffer buffer = new StringBuffer();
			buffer.append("耐久=20");
			buffer.append(",");
			buffer.append(type);
			goodstable.setValue(buffer.toString());
		} else if (type == 729) {
			if (goodstable.getValue() == null || goodstable.getValue().equals("")) {
				StringBuffer buffer = new StringBuffer();
				int v = random.nextInt(100);
				if (v < 3) {
					buffer.append("根骨=1|灵性=1|力量=1|敏捷=1");
				} else if (v < 10) {
					buffer.append("根骨=1|力量=1|敏捷=1");
				} else if (v < 20) {
					buffer.append("根骨=1|力量=1");
				} else {
					buffer.append("根骨=1");
				}
				buffer.append("|品质=1");
				goodstable.setValue(buffer.toString());
			}
		} else if (type == 825) {
			// 概率&品质&套装集合^概率&品质&套装集合
			// 80&1&1-102^16&2&1-102^4&3&1-102
			int s = random.nextInt(100), g = 0;
			String[] vs = goodstable.getValue().split("\\^");
			goodstable.setValue("");
			for (int i = 0; i < vs.length; i++) {
				String[] vs1 = vs[i].split("&");
				g += Integer.parseInt(vs1[0]);
				if (g > s) {// 抽中玉符
					int pz = Integer.parseInt(vs1[1]);
					int tzid = SplitStringTool.RandomId(vs1[2]).intValue();
					Suit suit = getSuit(tzid);
					if (suit != null) {
						int part = suit.getParts()[random.nextInt(suit
								.getParts().length)];
						StringBuffer buffer = new StringBuffer();
						buffer.append(tzid);
						buffer.append("|");
						buffer.append(part);
						buffer.append("|");
						buffer.append(pz);
						goodstable.setValue(buffer.toString());
						buffer.setLength(0);
						buffer.append(suit.getSname());
						buffer.append("-");
						buffer.append(getPartsName(part));
						buffer.append("-");
						buffer.append(getPZ(pz));
						goodstable.setGoodsname(buffer.toString());
					}
					break;
				}
			}
		} else if (type == 751) {
			int lvl = 1;
			String[] vs = goodstable.getValue().split("\\|");
			lvl = Integer.parseInt(vs[0].split("=")[1]);
			Gem gem = GameServer.getGem(null);
			GemBase base = gem.getGemBase(null);
			long quality = goodstable.getQuality();
			goodstable = GameServer.getGood(new BigDecimal(gem.getBid()));
			if (lvl >= 7) {
				goodstable.setSkin((Integer.parseInt(goodstable.getSkin()) + 2) + "");
			} else if (lvl >= 4) {
				goodstable.setSkin((Integer.parseInt(goodstable.getSkin()) + 1) + "");
			}
			goodstable.setValue(base.getGemValue(lvl, 95 + SuitComposeAction.random.nextInt(9)));
			goodstable.setQuality(quality);
		} else if (type == 8888) {
			// 品质=xxx|星级=xxx|经验=xxx|颜色=xxx|...........................
			StringBuffer buffer = new StringBuffer();
			buffer.append("品质=把玩|星级=0|经验=0|颜色=无");
			int[] arr = new int[4];
			int size = 0;
			int gl = random.nextInt(100);
			if (gl <= 15) {
				size += random.nextInt(9) + 2;
			} else if (gl <= 75) {
				size += random.nextInt(6) + 2;
			} else {
				size += random.nextInt(4) + 2;
			}
			WingCompose.addWingQuality(arr, size, random.nextInt(4));
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != 0) {
					if (buffer.length() != 0) {
						buffer.append("|");
					}
					if (i == 0) {
						buffer.append("根骨=");
					} else if (i == 1) {
						buffer.append("力量=");
					} else if (i == 2) {
						buffer.append("灵性=");
					} else if (i == 3) {
						buffer.append("敏捷=");
					}
					buffer.append(arr[i]);
				}
			}
			goodstable.setValue(buffer.toString());
		} else if (Goodtype.isSummonEquip(type)) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(goodstable.getValue());
			buffer.append("|装备部位=");
			buffer.append(type == 510 ? "兽环" : type == 511 ? "兽铃" : "兽甲");
			buffer.append("|");
			buffer.append("等级需求=0转100级|");
			int v = random.nextInt(4);
			buffer.append(v == 0 ? "根骨" : v == 1 ? "灵性" : v == 2 ? "力量" : "敏捷");
			buffer.append("=1|品质=");
			int pz = 80 + random.nextInt(21);
			buffer.append(80 + random.nextInt(21));
			buffer.append("|通灵=0|");
			buffer.append(SuitPetEquip.petAlchemy(type, pz, 1, 0));
			goodstable.setValue(buffer.toString());
		} else if (type == 520) {
			// 当前等级
			Integer lvlNow = Integer.parseInt(goodstable.getValue().split("=")[1]);
			StringBuffer buffer = new StringBuffer();
			buffer.append(goodstable.getValue());
			buffer.append("/");
			int uplvl = lvlNow + random.nextInt(4);
			if (uplvl > 12) {
				uplvl = 12;
			}
			if (lvlNow > uplvl) {
				uplvl = lvlNow;
			}
			buffer.append(uplvl);
			buffer.append("|神力=0|战力=100|");
			List<Alchemy> list = GameServer.getAllAlchemy().get("星卡");
			Alchemy alchemy1 = list.get(random.nextInt(list.size()));
			Alchemy alchemy2 = list.get(random.nextInt(list.size()));
			int aptitude = random.nextInt(31) + 70;
			buffer.append(StarCard.anewRefining(lvlNow, aptitude, alchemy1,
					alchemy2, 0));
			buffer.append("|");
			buffer.append(StarCard.anewFiveElements());
			goodstable.setValue(buffer.toString());
		} else if (Goodtype.isPalEquip(type)) {
			String[] vs = goodstable.getValue().split("\\|");
			if (vs.length == 1) {
				int lvl = Integer.parseInt(vs[0].split("=")[1]);
				SuitPalEquip.PalEquipValue(goodstable, type, lvl, null, 0);
			}
		}
		return goodstable;
	}

	/**
	 * 通过部件id获取部件名的方法
	 */
	public static String getPartsName(int id) {
		switch (id) {
			case 1:
				return "帽子";
			case 2:
				return "项链";
			case 3:
				return "衣服";
			case 6:
				return "面具";
			case 7:
				return "腰带";
			case 8:
				return "披风";
			case 9:
				return "挂件";
			case 10:
				return "左戒指";
			case 11:
				return "右戒指";
		}
		return null;
	}

	public List<LoginResult> getUserRole(BigDecimal userId) {
		List<LoginResult> loginResults = null;
		for(ChannelHandlerContext key: allLoginRole.keySet()){
			LoginResult loginResult = allLoginRole.get(key);
			if(loginResult.getUser_id().equals(userId)){
				if(loginResults == null){
					loginResults = new ArrayList<LoginResult>();
				}
				loginResults.add(loginResult);
			}
		}
		return loginResults;
	}

	/**
	 * 根据等级获取品质
	 */
	public static String getPZ(int pz) {
		switch (pz) {
			case 1:
				return "把玩";
			case 2:
				return "贴身";
			case 3:
				return "珍藏";
			case 4:
				return "无价";
			case 5:
				return "传世";
		}
		return null;
	}

	// 根据地图名获取
	public static Sghostpoint getSghostpoint(String type) {
		List<Sghostpoint> sghostpoints = monsterpointMap.get(type);
		if (sghostpoints != null && sghostpoints.size() != 0) {
			return sghostpoints.get(Battlefield.random.nextInt(sghostpoints.size()));
		}
		return null;
	}

	// 根据地图名放回地图idlong
	public static Long getMapIds(String mapname) {
		Gamemap gamemap = gameMap.get(mapname);
		if (gamemap != null) {
			return new Long(gamemap.getMapid());
		}
		return new Long(0);
	}

	public static String getMapName(String key) {
		Gamemap gamemap = gameMap.get(key);
		if (gamemap != null) {
			return gamemap.getMapname();
		}
		return "未知地图";
	}

	public static Gamemap getMap(String key) {
		return gameMap.get(key);
	}

	/**
	 * 获取npc所在地图
	 */
	public static int getMapNpc(String NPCId) {
		for (Gamemap gamemap : gameMap.values()) {
			if (gamemap.getNpcs() != null && gamemap.getNpcs().contains(NPCId)) {
				return Integer.parseInt(gamemap.getMapid());
			}
		}
		return 0;
	}

	/**
	 * 任务名获取
	 */
	public static TaskData getTaskName(String taskname) {
		Iterator<Integer> iter = allTaskData.keySet().iterator();
		while (iter.hasNext()) {
			Integer key = iter.next();
			if (allTaskData.get(key).getTaskName().equals(taskname))
				return allTaskData.get(key);
		}
		return null;
	}

	/**
	 * 任务id
	 */
	public static TaskData getTaskData(int id) {
		return allTaskData.get(id);
	}

	public static TaskSet getTaskSet(int id) {
		return allTaskSet.get(id);
	}

	/**
	 * 获取对应robot关联的任务系列
	 */
	public static TaskSet getRobotTaskSet(int robotID) {
		return allTaskSet.get(-robotID);
	}

	public static ConcurrentHashMap<Integer, TaskData> getAllTaskData() {
		return allTaskData;
	}

	public static void setAllTaskData(ConcurrentHashMap<Integer, TaskData> allTaskData) {
		GameServer.allTaskData = allTaskData;
	}

	public static ConcurrentHashMap<Integer, TaskSet> getAllTaskSet() {
		return allTaskSet;
	}

	public static void setAllTaskSet(ConcurrentHashMap<Integer, TaskSet> allTaskSet) {
		GameServer.allTaskSet = allTaskSet;
	}

	// 根据物品名和物品属性返回
	public static GodStone getGodStone(String name, String[] vs) {
		List<GodStone> godStones = godMap.get(name);
		if (godStones == null || godStones.size() == 0) {
			return null;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < vs.length; i++) {
			String[] vss = vs[i].split("=");
			map.put(vss[0], 1);
		}
		int sum = 0;
		for (int i = godStones.size() - 1; i >= 0; i--) {
			sum = 0;
			GodStone godStone = godStones.get(i);
			switch (godStone.getType()) {
				case "反震":
					if (map.get("反震率") != null) {
						return godStone;
					}
					break;
				case "速度":
					if (map.get(godStone.getType()) != null) {
						return godStone;
					}
					break;
				case "附毒":
					if (map.get("加强中毒") != null) {
						return godStone;
					}
					if (map.get("加强毒伤害") != null) {
						return godStone;
					}
					if (map.get("附加毒法攻击") != null) {
						return godStone;
					}
					break;
				case "破物理":
					if (map.get("忽视防御程度") != null) {
						return godStone;
					}
					break;
				case "双忽视仙法":
					if (map.get("忽视抗风") != null) {
						sum++;
					}
					if (map.get("忽视抗火") != null) {
						sum++;
					}
					if (map.get("忽视抗水") != null) {
						sum++;
					}
					if (map.get("忽视抗雷") != null) {
						sum++;
					}
					if (sum >= 2) {
						return godStone;
					}
					break;
				case "双抗水火":
					if (map.get("抗水") != null) {
						sum++;
					}
					if (map.get("抗火") != null) {
						sum++;
					}
					if (sum >= 2) {
						return godStone;
					}
					break;
				case "双抗风雷":
					if (map.get("抗风") != null) {
						sum++;
					}
					if (map.get("抗雷") != null) {
						sum++;
					}
					if (sum >= 2) {
						return godStone;
					}
					break;
				case "双强人法":
					if (map.get("加强混乱") != null) {
						sum++;
					}
					if (map.get("加强昏睡") != null) {
						sum++;
					}
					if (map.get("加强封印") != null) {
						sum++;
					}
					if (map.get("加强毒伤害") != null) {
						sum++;
					}
					if (sum >= 2) {
						return godStone;
					}
					break;
				case "抗人法":
					if (map.get("抗混乱") != null) {
						sum++;
					}
					if (map.get("抗昏睡") != null) {
						sum++;
					}
					if (map.get("抗封印") != null) {
						sum++;
					}
					if (sum >= 2) {
						return godStone;
					}
					break;
				case "附吸":
					if (map.get("加强震慑") != null) {
						return godStone;
					}
					if (map.get("附加震慑攻击") != null) {
						return godStone;
					}
					break;
				case "抗吸":
					if (map.get("抗震慑") != null) {
						return godStone;
					}
					break;
				case "强鬼法":
					if (map.get("加强鬼火") != null) {
						sum++;
					}
					if (map.get("忽视鬼火") != null) {
						sum++;
					}
					if (map.get("加强遗忘") != null) {
						sum++;
					}
					if (map.get("加强三尸虫") != null) {
						sum++;
					}
					if (map.get("加强三尸虫回血程度") != null) {
						sum++;
					}
					if (map.get("加强三尸虫回血程度") != null) {
						sum++;
					}
					if (sum >= 2) {
						return godStone;
					}
					break;
				case "抗鬼法":
					if (map.get("抗鬼火") != null) {
						sum++;
					}
					if (map.get("抗遗忘") != null) {
						sum++;
					}
					if (map.get("抗三尸虫") != null || map.get("抗三尸") != null) {
						sum++;
					}
					if (sum >= 2) {
						return godStone;
					}
					break;
				case "霹雳":
					if (map.get("加强雷霆效果") != null) {
						return godStone;
					}
					break;
				case "扶摇":
					if (map.get("加强甘霖回血程度") != null) {
						return godStone;
					}
					break;
				default:
					break;
			}
		}
		return godStones.get(0);
	}

	public static RoleSummoning getPet(BigDecimal id) {
		RoleSummoning pet = allPet.get(id);
		if (pet != null) {
			return pet.clone();
		}
		return pet;
	}

	public static Lingbao getLingbao(String baoname) {
		Lingbao lingbao = allLingbao.get(baoname);
		if (lingbao != null) {
			return lingbao.clone();
		}
		return lingbao;
	}

	public static String getTXTPATH() {
		return TXTPATH;
	}

	public static void setTXTPATH(String tXTPATH) {
		TXTPATH = tXTPATH;
	}

	public static int getPortNumber() {
		return portNumber;
	}

	public static void setPortNumber(int portNumber) {
		GameServer.portNumber = portNumber;
	}

	public static ConcurrentHashMap<ChannelHandlerContext, LoginResult> getAllLoginRole() {
		return allLoginRole;
	}

	public static void setAllLoginRole(
			ConcurrentHashMap<ChannelHandlerContext, LoginResult> allLoginRole) {
		GameServer.allLoginRole = allLoginRole;
	}

	public static ConcurrentHashMap<String, Gamemap> getGameMap() {
		return gameMap;
	}

	public static void setGameMap(ConcurrentHashMap<String, Gamemap> gameMap) {
		if (mapRolesMap == null) {
			mapRolesMap = new ConcurrentHashMap<Long, ConcurrentHashMap<String, ChannelHandlerContext>>();
		}
		for (Gamemap map : gameMap.values()) {
			ConcurrentHashMap<String, ChannelHandlerContext> hashMap = mapRolesMap.get(Long.parseLong(map.getMapid()));
			if (hashMap == null) {
				hashMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
				mapRolesMap.put(Long.parseLong(map.getMapid()), hashMap);
			}
		}
		GameServer.gameMap = gameMap;
	}

	public static ConcurrentHashMap<String, Monster> getMonsterMap() {
		return monsterMap;
	}

	public static void setMonsterMap(
			ConcurrentHashMap<String, Monster> monsterMap) {
		GameServer.monsterMap = monsterMap;
	}
	// TODO 获取所有商品（通过何处获取未知）
	public static ConcurrentHashMap<BigDecimal, Goodstable> getAllGoodsMap() {
		return allGoodsMap;
	}

	public static void setAllGoodsMap(
			ConcurrentHashMap<BigDecimal, Goodstable> allGoodsMap) {
		GameServer.allGoodsMap = allGoodsMap;
	}

	public static ConcurrentHashMap<String, Goodstable> getGetGoods() {
		return getGoods;
	}

	public static void setGetGoods(
			ConcurrentHashMap<String, Goodstable> getGoods) {
		GameServer.getGoods = getGoods;
	}

	public static ConcurrentHashMap<String, List<Decorate>> getAllDecorate() {
		return allDecorate;
	}

	public static void setAllDecorate(
			ConcurrentHashMap<String, List<Decorate>> allDecorate) {
		GameServer.allDecorate = allDecorate;
	}

	public static ConcurrentHashMap<String, List<Sghostpoint>> getMonsterpointMap() {
		return monsterpointMap;
	}

	public static void setMonsterpointMap(
			ConcurrentHashMap<String, List<Sghostpoint>> monsterpointMap) {
		GameServer.monsterpointMap = monsterpointMap;
	}

	public static ConcurrentHashMap<String, Robots> getAllRobot() {
		return allRobot;
	}

	public static void setAllRobot(ConcurrentHashMap<String, Robots> allRobot) {
		GameServer.allRobot = allRobot;
	}

	public static ConcurrentHashMap<String, List<Newequip>> getSameNewequipMap() {
		return sameNewequipMap;
	}

	public static void setSameNewequipMap(ConcurrentHashMap<String, List<Newequip>> sameNewequipMap) {
		GameServer.sameNewequipMap = sameNewequipMap;
	}

	public static ConcurrentHashMap<String, List<Alchemy>> getAllAlchemy() {
		return allAlchemy;
	}

	public static void setAllAlchemy(
			ConcurrentHashMap<String, List<Alchemy>> allAlchemy) {
		GameServer.allAlchemy = allAlchemy;
	}

	public static ConcurrentHashMap<String, List<Xianqi>> getGetAllXianqi() {
		return getAllXianqi;
	}

	public static void setGetAllXianqi(
			ConcurrentHashMap<String, List<Xianqi>> getAllXianqi) {
		GameServer.getAllXianqi = getAllXianqi;
	}

	public static ConcurrentHashMap<String, List<String>> getXianqiTypeValue() {
		return XianqiTypeValue;
	}

	public static void setXianqiTypeValue(
			ConcurrentHashMap<String, List<String>> xianqiTypeValue) {
		XianqiTypeValue = xianqiTypeValue;
	}

	public static ConcurrentHashMap<BigDecimal, Goodstable> getAllLingbaoFushi() {
		return allLingbaoFushi;
	}

	public static void setAllLingbaoFushi(
			ConcurrentHashMap<BigDecimal, Goodstable> allLingbaoFushi) {
		GameServer.allLingbaoFushi = allLingbaoFushi;
	}

	public static ConcurrentHashMap<Long, ConcurrentHashMap<String, ChannelHandlerContext>> getMapRolesMap() {
		return mapRolesMap;
	}

	public static void setMapRolesMap(
			ConcurrentHashMap<Long, ConcurrentHashMap<String, ChannelHandlerContext>> mapRolesMap) {
		GameServer.mapRolesMap = mapRolesMap;
	}

	public static ConcurrentHashMap<String, ChannelHandlerContext> getRoleNameMap() {
		return roleNameMap;
	}

	public static void setRoleNameMap(
			ConcurrentHashMap<String, ChannelHandlerContext> roleNameMap) {
		GameServer.roleNameMap = roleNameMap;
	}

	public static ConcurrentHashMap<String, ChannelHandlerContext> getInlineUserNameMap() {
		return inlineUserNameMap;
	}

	public static void setInlineUserNameMap(ConcurrentHashMap<String, ChannelHandlerContext> inlineUserNameMap) {
		GameServer.inlineUserNameMap = inlineUserNameMap;
	}

	public static ConcurrentHashMap<ChannelHandlerContext, String> getSocketUserNameMap() {
		return socketUserNameMap;
	}

	public static void setSocketUserNameMap(ConcurrentHashMap<ChannelHandlerContext, String> socketUserNameMap) {
		GameServer.socketUserNameMap = socketUserNameMap;
	}

	public static ConcurrentHashMap<String, Shop> getAllShopGoods() {
		return allShopGoods;
	}

	public static void setAllShopGoods(ConcurrentHashMap<String, Shop> allShopGoods) {
		GameServer.allShopGoods = allShopGoods;
	}

	/**
	 * 获取获取  双索引 根据存id字符串和技能名称
	 */
	public static Skill getSkill(String key) {
		return getSkill.get(key);
	}

	public static ConcurrentHashMap<String, Skill> getGetSkill() {
		return getSkill;
	}

	public static void setGetSkill(ConcurrentHashMap<String, Skill> getSkill) {
		GameServer.getSkill = getSkill;
	}

	public static ConcurrentHashMap<String, List<GodStone>> getGodMap() {
		return godMap;
	}

	public static void setGodMap(
			ConcurrentHashMap<String, List<GodStone>> godMap) {
		GameServer.godMap = godMap;
	}

	public static ConcurrentHashMap<BigDecimal, RoleSummoning> getAllPet() {
		return allPet;
	}

	public static void setAllPet(
			ConcurrentHashMap<BigDecimal, RoleSummoning> allPet) {
		GameServer.allPet = allPet;
	}

	public static ConcurrentHashMap<String, Lingbao> getAllLingbao() {
		return allLingbao;
	}

	public static void setAllLingbao(
			ConcurrentHashMap<String, Lingbao> allLingbao) {
		GameServer.allLingbao = allLingbao;
	}

	public static ConcurrentHashMap<String, Eshop> getAllEshopGoods() {
		return allEshopGoods;
	}

	public static void setAllEshopGoods(
			ConcurrentHashMap<String, Eshop> allEshopGoods) {
		GameServer.allEshopGoods = allEshopGoods;
	}

	public static boolean isOPEN() {
		return OPEN;
	}

	public static void setOPEN(boolean oPEN) {
		OPEN = oPEN;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 物品id获取
	 */
	public static Bbuy getBbuy(BigDecimal goodsid) {
		return allBbuys.get(goodsid.intValue());
	}

	/**
	 * 重置最大收购次数
	 */
	public static void resetBbuy() {
		Iterator<Entry<Integer, Bbuy>> entries = allBbuys.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<Integer, Bbuy> entrys = entries.next();
			entrys.getValue().setNum(0);
		}
	}

	public static void setAllBbuys(ConcurrentHashMap<Integer, Bbuy> allBbuys) {
		GameServer.allBbuys = allBbuys;
	}

	/**
	 * 根据特效id获取
	 */
	public static RoleTxBean getTxBean(int gid) {
		return allTXs.get(gid);
	}

	public static void setAllTXs(ConcurrentHashMap<Integer, RoleTxBean> allTXs) {
		GameServer.allTXs = allTXs;
	}

	public static Suit getSuit(int id) {
		return allSuits.get(id);
	}

	public static void setAllSuits(ConcurrentHashMap<Integer, Suit> allSuits) {
		GameServer.allSuits = allSuits;
	}

	public static String getGameServerPay() {
		return gameServerPay;
	}

	public static void setGameServerPay(String gameServerPay) {
		GameServer.gameServerPay = gameServerPay;
	}

	public static Dorp getDorp(String id) {
		return allDorp.get(id);
	}

	public static void setAllDorp(ConcurrentHashMap<String, Dorp> allDorp) {
		GameServer.allDorp = allDorp;
	}

	public static List<Present> getPresents() {
		return presents;
	}

	public static void setPresents(List<Present> presents) {
		GameServer.presents = presents;
	}

	static String[] gemns = new String[]{"赤焰石", "芙蓉石", "寒山石", "孔雀石", "琉璃石", "落星石", "沐阳石", "紫烟石"};

	/**
	 * 获取宝石
	 */
	public static Gem getGem(String name) {
		if (name == null) {
			return gems.get(gemns[random.nextInt(gemns.length)]);
		}
		return gems.get(name);
	}

	public static void setGems(ConcurrentHashMap<String, Gem> gems) {
		GameServer.gems = gems;
	}

	/**
	 *
	 * @param //等级上限设置
	 * @return
	 */
	public static long getExp(int lvl) {
		if (lvl > 199) {
			lvl = 199;
		}
		Long exp = expMap.get(lvl);
		return exp;

	}

	public static void setExpMap(ConcurrentHashMap<Integer, Long> expMap) {
		GameServer.expMap = expMap;
	}

	public static Mount getMount(int raceid, int lvl) {
		ConcurrentHashMap<Integer, Mount> map = allMount.get(raceid);
		if (map == null) {
			return null;
		}
		Mount mount = map.get(lvl);
		if (mount != null) {
			mount = mount.clone();
		}
		return mount;
	}

	public static void setAllMount(ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Mount>> allMount) {
		GameServer.allMount = allMount;
	}

	/**
	 * 随机变色方案
	 */
	public static ColorScheme getColors(int type) {
		List<Integer> a = new ArrayList<Integer>();
		for (ColorScheme value : allColor.values()) {
			if (value.getZid() == 0 || value.getZid() == type) {
				a.add(value.getId());
			}
		}
		if (a.size() == 0) {
			return null;
		}
		return allColor.get(a.get(random.nextInt(a.size())) + "");
	}

	/**
	 * 变色代码获取
	 */
	public static ColorScheme getColor(String color) {
		return allColor.get(color);
	}

	public static void setAllColor(ConcurrentHashMap<String, ColorScheme> allColor) {
		GameServer.allColor = allColor;
	}

	public static Talent getTalent(int talentid) {
		// TODO Auto-generated method stub
		return alltalent.get(talentid);
	}

	public static void setAlltalent(ConcurrentHashMap<Integer, Talent> alltalent) {
		GameServer.alltalent = alltalent;
	}

	public static Lshop getLshop(String id) {
		// TODO Auto-generated method stub
		Lshop lshop = allLShopGoods.get(id);
		if (lshop == null) {
			return null;
		}
		return lshop.clone();
	}

	public static ConcurrentHashMap<String, Lshop> getAllLShopGoods() {
		return allLShopGoods;
	}

	public static void setAllLShopGoods(ConcurrentHashMap<String, Lshop> allLShopGoods) {
		GameServer.allLShopGoods = allLShopGoods;
	}

	public static Draw getDraw(int id) {
		// TODO Auto-generated method stub
		return allDraws.get(id);
	}

	public static void setAllDraws(ConcurrentHashMap<Integer, Draw> allDraws) {
		GameServer.allDraws = allDraws;
	}

	public static aCard getCard(int id) {
		// TODO Auto-generated method stub
		return allACard.get(id);
	}

	public static void setAllACard(ConcurrentHashMap<Integer, aCard> allACard) {
		GameServer.allACard = allACard;
	}

	public static Title getTitle(String id) {
		// TODO Auto-generated method stub
		if (id == null || id.equals("")) {
			return null;
		}
		return alltitle.get(id);
	}

	public static void setAlltitle(ConcurrentHashMap<String, Title> alltitle) {
		GameServer.alltitle = alltitle;
	}

	public static List<Title> getMoneyTitles() {
		return moneyTitles;
	}

	public static void setMoneyTitles(List<Title> moneyTitles) {
		GameServer.moneyTitles = moneyTitles;
	}

	public static EventModel getEvent(int id) {
		return allevent.get(id);
	}

	public static void setAllevent(ConcurrentHashMap<Integer, EventModel> allevent) {
		GameServer.allevent = allevent;
	}

	public static WingTraining getWingTraining(long type) {
		return allWingTraining.get(type);
	}

	public static void setAllWingTraining(ConcurrentHashMap<Long, WingTraining> allWingTraining) {
		GameServer.allWingTraining = allWingTraining;
	}

	public static StarPalace getStarPalace(String type) {
		return allStarPalace.get(type);
	}

	public static String randomStarPalace() {
		return allStarPalaceKey[random.nextInt(allStarPalaceKey.length)];
	}

	public static void setAllStarPalace(ConcurrentHashMap<String, StarPalace> allStarPalace) {
		GameServer.allStarPalace = allStarPalace;
	}

	public static void setAllStarPalaceKey(String[] allStarPalaceKey) {
		GameServer.allStarPalaceKey = allStarPalaceKey;
	}

	public static Npctable getNpc(String npcId) {
		return npcMap.get(npcId);
	}

	public static ConcurrentHashMap<String, Npctable> getNpcMap() {
		return npcMap;
	}

	public static void setNpcMap(ConcurrentHashMap<String, Npctable> npcMap) {
		GameServer.npcMap = npcMap;
	}

	public static Door getDoor(int doorId) {
		return doorMap.get(doorId);
	}

	public static ConcurrentHashMap<Integer, Door> getDoorMap() {
		return doorMap;
	}

	public static void setDoorMap(ConcurrentHashMap<Integer, Door> doorMap) {
		GameServer.doorMap = doorMap;
	}

	/**
	 * 根据充值金额获取VIP阶段
	 */
	public static PayvipBean getVIP(long moeny) {
		for (int i = payvipList.size() - 1; i >= 0; i--) {
			PayvipBean vip = payvipList.get(i);
			if (vip.getPaynum() <= moeny) {
				return vip;
			}
		}
		return null;
	}

	/**
	 * 大闹天宫的奖励
	 */
	public static DNTGAward getAllDntg(int id) {
		return allDntg.get(id);
	}

	public static void setAllDntg(ConcurrentHashMap<Integer, DNTGAward> allDntg) {
		GameServer.allDntg = allDntg;
	}

	/**
	 * 获取伙伴数据
	 */
	public static PalData getPalData(int id) {
		return allPalData.get(id);
	}

	public static void setAllPalData(ConcurrentHashMap<Integer, PalData> allPalData) {
		GameServer.allPalData = allPalData;
	}

	/**
	 * 获取伙伴装备数据
	 */
	public static PalEquip getPalEquip(long type) {
		return allPalEquip.get(type);
	}

	public static void setAllPalEquip(ConcurrentHashMap<Long, PalEquip> allPalEquip) {
		GameServer.allPalEquip = allPalEquip;
	}

	public static PetExchange getPetExchange(int id) {
		return allPetExchange.get(id);
	}

	public static void setAllPetExchange(ConcurrentHashMap<Integer, PetExchange> allPetExchange) {
		GameServer.allPetExchange = allPetExchange;
	}
	public static GoodsExchange getGoodsExchange(int id) {
		return allGoodsExchange.get(id);
	}

	public static void setAllGoodsExchange(ConcurrentHashMap<Integer, GoodsExchange> allGoodsExchange) {
		GameServer.allGoodsExchange = allGoodsExchange;
	}
	/**
	 * 保存通关记录
	 */
	public static void saveEventRoles() {
		EventRanking eventRanking = new EventRanking();
		Map<Integer, RoleCard[]> map = new HashMap<Integer, RoleCard[]>();
		Iterator<Entry<Integer, EventModel>> entries = allevent.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<Integer, EventModel> entrys = entries.next();
			EventModel value = entrys.getValue();
			if (value.getRoles() != null) {
				map.put(value.getgId(), value.getRoles());
			}
		}
		eventRanking.setMap(map);
		try {
			CreateTextUtil.createFile(ReadExelTool.class.getResource("/")
					.getPath() + "event.txt", GsonUtil.getGsonUtil().getgson()
					.toJson(eventRanking).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ConcurrentHashMap<Integer, List<ChongjipackBean>> getPackgradecontrol() {
		return packgradecontrol;
	}

	public static List<PayvipBean> getPayvipList() {
		return payvipList;
	}

	public static void refreshBean() {
		List<ChongjipackBean> chongjipackBeans = AllServiceUtil.getChongjipackServeice().selectAllPack();
		packgradecontrol = new ConcurrentHashMap<Integer, List<ChongjipackBean>>();
		for (int i = 0; i < chongjipackBeans.size(); i++) {
			ChongjipackBean bean = chongjipackBeans.get(i);
			List<ChongjipackBean> packs = GameServer.getPackgradecontrol().get(bean.getPacktype());
			if (packs == null) {
				packs = new ArrayList<ChongjipackBean>();
				GameServer.getPackgradecontrol().put(bean.getPacktype(), packs);
			}
			packs.add(bean);
		}
		payvipList = AllServiceUtil.getPayvipBeanServer().selectAllVip();
	}

	public static int getQh() {
		return qh;
	}

	/**
	 * 获取已完成的活跃值
	 */
	public static int getActiveValue(RoleData roleData) {
		int value = 0;
		for (int i = 0; i < allActive.getBases().length; i++) {
			ActiveBase activeBase = allActive.getBases()[i];
			int num = roleData.getTaskWC(activeBase.getSid());
			if (num > activeBase.getNum()) {
				num = activeBase.getNum();
			}
			value += (num * activeBase.getValue());
		}
		return value;
	}

	/**
	 * 获取活跃奖励阶段
	 */
	public static ActiveAward getActiveAward(int i) {
		if (i < 0) {
			return null;
		}
		if (i < allActive.getAwards().length) {
			return allActive.getAwards()[i];
		}
		return null;
	}

	public static void setAllActive(AllActive allActive) {
		GameServer.allActive = allActive;
	}

	/**
	 * 获取成就目标
	 */
	public static Achieve getAchieve(int id) {
		return allAchieve.get(id);
	}

	public static void setAllAchieve(ConcurrentHashMap<Integer, Achieve> allAchieve) {
		GameServer.allAchieve = allAchieve;
	}

	//炼化
	public static AllLianHua getAllLianHua() {
		return allLianHua;
	}

	public static void setAllLianHua(AllLianHua all) {
		GameServer.allLianHua = all;
	}

	public static AllMeridians getAllMeridians() {
		return allMeridians;
	}

	public static void setAllMeridians(AllMeridians allMeridians) {
		GameServer.allMeridians = allMeridians;
	}


}