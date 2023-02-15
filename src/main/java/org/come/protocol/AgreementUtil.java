package org.come.protocol;
/**
 * 协议对照表s
 * @author Administrator
 *
 */
public class AgreementUtil {
	
	public static final String changerolename="changerolename";//修改角色名字
	public static final String rolelevelup="rolelevelup";//人物升级
	public static final String login="login";//登陆
	public static final String register="register";//注册
	public static final String createrole="createrole";//创建角色
	public static final String intogame="intogame";//进入游戏
	
	public static final String chat="chat";//聊天频道
	
	public static final String deletefriend="deletefriend";//删除好友
	public static final String npc="npc";//npc报文
	
	public static final String packchange="packchange";//背包使用信息
	public static final String eshop="eshop";//商城
	public static final String pet="pet";//召唤兽  同226
	public static final String mount="mount";//坐骑
//	public static final String buy="buy";//购买物品 同228
	public static final String groupApply="groupApply";//申请加入队伍
	public static final String groupaccess="groupaccess";//加入队伍成功
	public static final String friend="friend";//好友列表
	public static final String addfriend="addfriend";//添加好友
	public static final String friendchat="friendchat";//好友聊天
	public static final String shop="shop";//npc商店
	public static final String shopGood="shopGood";//商店货物
	public static final String shopPrice="shopPrice";//商店货物价格
	
	public static final String changemap="changemap";//传送
	public static final String ganglist="ganglist";//我要加入帮派
	public static final String give="give";//给予协议
	public static final String petchange="petchange";//宠物信息修改
	public static final String packlock="packlock";//设置背包密码
	public static final String packgift="packgift";//新手礼包
	public static final String npcgift="npcgift";//新手使者
	public static final String skilllearn="skilllearn";//学习技能
	public static final String monsterrefresh="monsterrefresh";//刷新怪物
	public static final String creepsfight="creepsfight";//野怪集合
	public static final String Summonpet="Summonpet";//召唤神兽
	public static final String npccompose="npccompose";//npc物品合成
	public static final String rolechange="rolechange";//角色进行
	public static final String summonpet="summonpet";//使用物品卡产生召唤兽
	public static final String titlelist="titlelist";//显示角色称谓列表
	public static final String intogang="intogang";//进入帮派
	public static final String gangcreate="gangcreate";//创建帮派 同237
	public static final String gangapply="gangapply";//申请加入帮派同239
	public static final String gangretreat="gangretreat";//退出帮派 同240
	public static final String gangshot="gangshot";//踢出帮派 
	public static final String gangagree="gangagree";//帮派允许加入
	public static final String gangrefuse="gangrefuse";//帮派拒绝加入
	public static final String gangappoint="gangappoint";//帮派职务任命
	public static final String gangchange="gangchange";//帮派退位让贤
	public static final String npccure="npccure";//修复人物和召唤兽信息
	public static final String givemoney="givemoney";//捐款
	public static final String marry="marry";//结婚
	public static final String makelove="makelove";//洞房
	public static final String prompt="prompt";//前端提示
	public static final String titlechange="titlechange";//更改称谓
	public static final String mountcarry="mountcarry";//坐骑骑乘/休息
	public static final String petrelease="petrelease";//放生召唤兽
	public static final String buymingchao="buymingchao";//购买冥钞
	public static final String racialtransformation="racialtransformation";//种族转换
	public static final String getthetask="getthetask";//领取任务
	public static final String giveupthetask="giveupthetask";//放弃任务
	public static final String addmountskill="addmountskill";//坐骑学习技能
	public static final String missmountskill="missmountskill";//坐骑遗忘技能
	public static final String changemountvalue="changemountvalue";//修改坐骑的属性(根骨,灵性,力量)
	public static final String mountget="mountget";//获取坐骑(通过坐骑卡获取)
	public static final String baby="baby";//获取宝宝
	public static final String unmarry="unmarry";//离婚
	public static final String equipmentLing="equipmentLing";//更改装备灵宝法宝
	public static final String ling="ling";//获取灵宝法宝
	public static final String updateling="updateling";//获取灵宝法宝
	public static final String updatefa="updatefa";//更新法宝
	public static final String deductiontael="deductiontael";//扣除银两
	public static final String babyborn="babyborn";//宝宝产生
	public static final String applypay="applypay";//充值信息
	public static final String babycustoday="babycustoday";//宝宝抚养权 
	public static final String pawn="pawn";//典当协议
	public static final String serverstop="serverstop";//服务器停止
	public static final String battleconnection="battleconnection";//战斗重连
	public static final String npcdialog="npcdialog";//NPC弹窗
	public static final String middle="middle";//中量级数据广播
	public static final String quoteout="quoteout";//引用监狱广播
	public static final String addgood="addgood";//添加单个物品
	public static final String updababy="updababy";//更新宝宝数据
//	public static final String getreward="getreward";//奖励协议头
	public static final String gangmonitor="gangmonitor";//帮战npc点击 
	public static final String gangbattle="gangbattle";//帮战功能反应
	public static final String gangstate="gangstate";//帮战状态
	public static final String stall="stall";//发布摊位信息 同271
	public static final String stallget="stallget";//获取摊位信息
	public static final String stallbuy="stallbuy";//购买摊位 同 273
	public static final String asset="asset";//资源获取的统一处理
	public static final String stallstate="stallstate";//更新摊位
	public static final String mountrelease="mountrelease";//放生坐骑
	public static final String updatemonsters="updatemonsters";//添加BOOS(放妖)
	public static final String ClickMonsters="clickmonsters";//野怪点击(点击直接获得物品之类)
	public static final String throwinarticle="throwinarticle";//投放赏功堂物品
	public static final String obtainarticle="obtainarticle";//获取赏功堂物品
	public static final String drawnitems="drawnitems";//抽中物品
	public static final String drawnitemsfail="drawnitemsfail";//抽物品失败
	public static final String bindingmobile="bindingmobile";//绑定手机号 
	public static final String pankinglist="pankinglist";//排行榜信息
	public static final String nbuy="nbuy";//购买物品信息 
	public static final String user="user";//人物的物品使用	
	public static final String userpet="userpet";//召唤兽的物品使用	
	public static final String usermount="usermount";//坐骑的物品使用	
	public static final String userling="userling";//灵宝的物品使用	
	public static final String userbaby="userbaby";//宝宝的物品使用	
	public static final String usercard="usercard";//卡片类的物品使用	
	public static final String userpal="userpal";//伙伴类的物品使用	
	
	public static final String drop="drop";//掉落拆解	
	
	public static final String loginsuccess="loginsuccess";//登陆成功封装协议
	public static final String loginerror="loginerror";//登陆失败封装协议
	public static final String inlinelogin="inlinelogin";//登陆已在线封装协议
	public static final String rigistersuccess="rigistersuccess";//注册成功
	public static final String rigistererror="rigistererror";//注册失败封装协议
	public static final String createsuccess="createsuccess";//创建角色成功
	public static final String createerror="createerror";//创建角色失败封装协议
	
	public static final String teamexist="teamexist";//已存在申请		
	public static final String userretreat="userretreat";//用户退出
	public static final String transalllist="transalllist";//全部队伍列表
	public static final String petinfo="petinfo";//召唤兽信息
	public static final String npcmonster="npcmonster";//刷怪任务
	public static final String petalchemy="petalchemy";//炼妖
	public static final String stringTeamCaptain="stringTeamCaptain";//修改角色队伍成员	
	public static final String pong="pong";//回复包
	public static final String retrieve="retrieve";//取回典当
	public static final String version="version";//认证版本号
	public static final String zeropoint="zeropoint";//零点报时
	public static final String bangtz="bangtz";//挑战开始
	public static final String accountstop="accountstop";//封号
	public static final String getout="getout";//送监狱
	public static final String registration="registration";//报名参加水陆大会
	public static final String enterfiled="enterfiled";//进入水陆大会比武场
	public static final String tip="tip";//提示
	
	public static final String packRecord="1";//背包记忆
	public static final String enterGame="enterGame";//背包记忆
	public static final String roleSystem="roleSystem";//保存系统设置
	public static final String rolePrivate="rolePrivate";//保存玩家私密数据
	public static final String bookofchalg = "bookofchalg";//下挑战书
	public static final String refusechalg = "refusechalg";//拒绝别人的挑战
	
	public static final String richM = "richm";//展示点击请求
	
    public static final String fightround="fig1";//战斗回合角色出招 同275
    public static final String fightingforesee="fig2";//战斗预知
//    public static final String fightingresponse="fig3";//战斗响应
    public static final String fightingroundend="fig4";//战斗回合结束
    public static final String fightingrounddeal="fig5";//接收战斗回合处理结果
    public static final String fightingend="fig6";//战斗结束
    public static final String battlestate="fig7";//战斗状态
    public static final String fightQl="fig8";//获取战斗抗性
    
    public static final String move="move";//移动	
    public static final String getfivemsg="getfivemsg";//刮刮乐
    public static final String suitoperate = "suitoperate";//有关套装的操作
    public static final String exchangegoods = "exchangegoods";//兑换物品
    public static final String extrattroper = "extrattroper";//额外属性操作
    public static final String bindingMobile = "bindingMobile";//绑定安全码
    
    public static final String hatchvalue = "hatchvalue";//获取孵化进度
    public static final String hatchadd = "hatchadd";//加孵化进度
    public static final String givegoods = "givegoods";//进度满时发送物品
    
    public static final String searcahChatRoleId = "searcahChatRoleId";//根据角色ID查找好友
    public static final String searcahChatRoleName = "searcahChatRoleName";//根据角色名查找好友
    public static final String searchChatRecorde = "searchChatRecorde";//获取好友的聊天记录
    
    public static final String CBGShelf  ="CBGShelf";//藏宝阁商品上架
	public static final String CBGSearch1="CBGSearch1";//商品搜索
	public static final String CBGSearch2="CBGSearch2";//我的商品搜索
	public static final String CBGSearch3="CBGSearch3";//我的订单搜索
	public static final String CBGSearch4="CBGSearch4";//指定购买搜索
	public static final String CBGSearch5="CBGSearch5";//我的货物搜索
	public static final String CBGSearch6="CBGSearch6";//消息搜索
	public static final String CBGSearch7="CBGSearch7";//收藏查询搜索
	public static final String CBGGood   ="CBGGood";//藏宝阁商品下架 重新上架 取回
	public static final String CBGCollect="CBGCollect";//收藏-收藏取消
	public static final String CBGMes    ="CBGMes";//消息操作
	public static final String CBGBuy    ="CBGBuy";//购买
	public static final String CBGBack   ="CBGBack";//我的货物取回
	
    public static final String TransState="TransState";//交易状态
    public static final String TransGood="TransGood";//交易物品
    public static final String Monitor="Monitor";

    public static final String DUELBOARD="DUELBOARD";//实时决斗排行榜
    public static final String TASKN="taskN";//任务数据变更
    
    public static final String upRoleShow="upRoleShow";//更改人物显示
    
    
	/** 手机验证--HGC--2019-11-13--start */
	public static final String PhoneBang = "PhoneBang";// 手机账号绑定验证码
	public static final String UnPhoneBang = "UnPhoneBang";// 手机账号,解绑
	public static final String PhoneNumberIsNoGet = "PhoneNumberIsNoGet";// 获取手机绑定
	/** 手机验证--HGC--2019-11-13--end */
	public static final String Getvipgradepack = "Getvipgradepack"; // 获取vip兑换 
	public static final String Vipgradesure = "Vipgradesure"; // vip兑换
	public static final String Paydaygradepay = "Paydaygradepay"; // 选区协议头
	public static final String Paydaygradesure = "Paydaygradesure"; // 每日充值兑换
	public static final String Dayforweekgradeget = "Dayforweekgradeget"; // 连续充值实例化
	public static final String Dayforweekgradesure = "Dayforweekgradesure"; // 连续充值领取
	public static final String Dayforoneget = "Dayforoneget"; // 每日特惠
	public static final String Dayforonesure = "Dayforonesure"; // 每日特惠购买
	public static final String chongjipackget = "chongjipackget"; // 冲级礼包实例化
	public static final String Chongjipacksure  = "Chongjipacksure"; // 冲级礼包兑换
	public static final String Chongjipackopen = "Chongjipackopen"; // 开通冲级礼包开通

	public static final String Scene="scene";//副本数据
	
	//组队系统相关协议头
	public static final String enlist ="enlist";//招募信息
	public static final String team1="team1";//创建队伍
	public static final String team2="team2";//申请加队
	public static final String team3="team3";//队伍信息
	public static final String team4="team4";//队伍状态
	public static final String team5="team5";//队伍操作
	public static final String team6="team6";//申请列表
	public static final String findway="findway";//寻路
	
	public static final String confirm="confirm";//确认框
	
	public static final String oneArena = "oneArena";//单人竞技场
	public static final String teamArena="teamArena";//组队竞技场

	public static final String labor="labor";//51劳动节特别任务 排行数据
	public static final String vicon="vicon";//活动图标显示隐藏
	
	public static final String REAREA = "RETAREA";// 返回区域信息
	public static final String UidAndQidForRole = "UidAndQidForRole";// 区域和用户查询对应的角色
	public static final String PhoneNumberReturn = "PhoneNumberReturn"; // 手机验证码返回
	public static final String PhoneNumber = "PhoneNumber"; // 手机验证码协议头
	public static final String GETAREA = "GETAREA";// 请求区域信息
	public static final String goodsBuy_inter = "goodsBuy_inter";// 藏宝阁商品购买
	public static final String salesGoodsChangeOrder_inter = "salesGoodsChangeOrder_inter";// 进行物品状态的更改
	public static final String saleGoodsStatues_inter = "saleGoodsStatues_inter";// 进行salegoods物品进行
//    public static String PAYRETURN="PAYRETURN";	// 充值回调
    
    /** zrikka 2020 0415 */
	public static final String ACCOUNT_BINDING = "ACCOUNT_BINDING";// 账号绑定信息
	public static final String ACCOUNT_GOLOGIN = "ACCOUNT_GOLOGIN";// 账号登录
	public static final String ACCOUNT_UPDATE = "ACCOUNT_UPDATE";// 账号信息修改
	public static final String IP_ACTION = "IP_ACTION";// ip封禁操作
	public static final String LOGINVERSION = "LOGINVERSION";// 连接获取版本

	/** ww 2021   0917 */
	public static final String FINDDROP = "findDrop";//查询掉落

	public static final String GET_EGG = "getEgg";//查询掉落

	public static final String HJSL = "hjsl";	// 幻境试炼

	public static final String LINGXI = "lingxi";//灵犀

	public static final String skilllearn2="skilllearn2";//学习法门

	public static final String GOODSFORGOODS = "GOODSFORGOODS";
	public static final String rolechange1="rolechange1";
	
}
