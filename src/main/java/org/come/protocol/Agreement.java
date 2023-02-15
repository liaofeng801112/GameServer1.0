package org.come.protocol;

import org.come.nettyClient.Clinet_NewAESUtil;
import org.come.tool.NewAESUtil;

public class Agreement {
	
	    private Agreement() {
			
		}
		private static Agreement agreement;

		public static Agreement getAgreement() {
			if (agreement == null) {
				agreement = new Agreement();
			}
			return agreement;
		}

		static final String FG="//";
		// 登陆成功封装协议  RETAREA
		public String successLoginAgreement(String Content) {
			
			return NewAESUtil.AESJDKEncode(AgreementUtil.loginsuccess+FG+Content);			
		}
		// 登陆失败封装协议
		public String erroLoginAgreement() {
			return NewAESUtil.AESJDKEncode(AgreementUtil.loginerror+FG);
		}
		// 登陆已在线封装协议
		public String inlineLoginAgreement() {
			return NewAESUtil.AESJDKEncode(AgreementUtil.inlinelogin+FG);
		}
		// 注册成功
		public String successRigisterAgreement() {
			return NewAESUtil.AESJDKEncode(AgreementUtil.rigistersuccess+FG);
		}
		// 注册失败封装协议
		public String erroRigisterAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.rigistererror+FG + Content);
		}
		// 创建角色成功
		public String successCreateAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.createsuccess+FG + Content);
		}
		// 创建角色失败封装协议
		public String errorCreateAgreement() {
			return NewAESUtil.AESJDKEncode(AgreementUtil.createerror+FG);
		}
		// 已存在申请
		public String TeamApplyExistAgreement() {
			return NewAESUtil.AESJDKEncode(AgreementUtil.teamexist+FG);
		}
		// 用户退出
		public String UserRetreatAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.userretreat+FG + Content);
		}
		// 全部队伍列表
		public String TransAllListAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.transalllist+FG + Content);
		}
		// 召唤兽信息
		public String PetInfoAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.petinfo+FG + Content);
		}
		// 刷怪任务
		public String NpcMonsterAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.npcmonster+FG + Content);
		}
		// 炼妖
		public String PetAlchemyAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.petalchemy+FG + Content);
		}
		// 修改角色队伍成员
		public String stringTeamCaptain(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.stringTeamCaptain+FG + Content);
		}
		// 回复包
		public String PongAgreement(){
			return NewAESUtil.AESJDKEncode(AgreementUtil.pong+FG);
		}
		// 取回典当
		public String RecivePawnAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.retrieve+FG + Content);
		}
		// 认证版本号
		public String VersionAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.version+FG + Content);
		}
		// 零点报时
		public String ZeropointAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.zeropoint+FG + Content);
		}

		// 挑战开始
		public String bangtzAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.bangtz+FG+Content);
		}
		
		// 修改角色名字
		public String ChangeRoleNameAgreement(String Content){
			  return NewAESUtil.AESJDKEncode(AgreementUtil.changerolename+FG + Content);
		}
		// 人物升级
		public String RoleLevelUpAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.rolelevelup+FG + Content);
		}
		// 登陆
		public String LoginAgreement(String Content) {
			return  NewAESUtil.AESJDKEncode(AgreementUtil.login+FG +Content);
		}
		// 注册
		public String registerAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.register+FG + Content);
		}
		// 创建角色
		public String createRoleAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.createrole+FG + Content);
		}
		// 进入游戏
		public String intogameAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.intogame+FG + Content);
		}
		
		// 聊天栏
		public String chatAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.chat+FG + Content);
		}
		
	    // npc报文
	    public String npcAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.npc+FG + Content);
		}
	    // 背包使用信息
	    public String packchangeAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.packchange+FG + Content);
		}
	    // 商城
	    public String EshopAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.eshop+FG + Content);
	    }
	    // 坐骑
	    public String MountAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.mount+FG+ Content);
	    }
	    // 坐骑
	    public String MountAgreement() {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.mount+FG);
	    }
	    // 申请加入队伍
	    public String groupApplyAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.groupApply+FG + Content);
	    }
	    // 加入队伍成功
	    public String groupaccessAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.groupaccess+FG + Content);
	    }
	    // 好友列表
	    public String friendAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.friend+FG + Content);
	    }
	    // 添加好友
	    public String addFrientAgreement(String Content) {
	        return NewAESUtil.AESJDKEncode(AgreementUtil.addfriend+FG + Content);
	    }
//	    // 购买物品
//	    public String buyAgreement(String Content) {
//	    	return NewAESUtil.AESJDKEncode(AgreementUtil.buy+FG + Content);
//	    }
	    // 好友聊天
	    public String friendchatAgreement(String Content) {
	        return NewAESUtil.AESJDKEncode(AgreementUtil.friendchat+FG + Content);
	    }
	    //npc商店
	    public String BuyNPCGoodsAgreement(String Content) {
	    		return NewAESUtil.AESJDKEncode(AgreementUtil.shop+FG + Content);
	    }
		// npc商店货物
		public String BuyShopGoodsAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.shopGood + FG + Content);
		}
		// 商店货物价格
		public String ShopPriceAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.shopPrice + FG + Content);
		}
	    // 传送
	    public String ChangemapAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.changemap+FG + Content);
	    }
	    //我要加入帮派
	    public String ganglistAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.ganglist+FG + Content);
	    }
	    //给予协议
	    public String giveAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.give+FG + Content);
	    }
	    //请求召唤兽
	    public String petAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.pet+FG + Content);
	    }
	    // 宠物信息修改
	    public String petchangeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.petchange+FG + Content);
	    }
	    // 设置背包密码
	    public String packlockAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.packlock+FG + Content);
	    }
	    //新手礼包
	    public String packgiftAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.packgift+FG + Content);
	    }
	    //新手使者
	    public String npcgiftAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.npcgift+FG + Content);
	    }
	    //学习技能 
	    public String skilllearnAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.skilllearn+FG + Content);
	    }
	    // 刷新怪物
	    public String MonsterRefreshAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.monsterrefresh+FG + Content);
	    }
	    // 野怪集合
	    public String CreepsFightAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.creepsfight+FG + Content);
	    }
	    // 召唤神兽
	    public String SummonpetAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.Summonpet+FG + Content);
	    }
	    //npc物品合成
	    public String npccomposeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.npccompose+FG + Content);
	    }
	    //角色进行
	    public String rolechangeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.rolechange+FG + Content);
	    }
	    //使用物品卡产生召唤兽
	    public String summonpetAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.summonpet+FG + Content);
	    }
	    // 显示角色称谓列表
	    public String TitleListAgreement() {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.titlelist+FG);
	    }
	    //显示角色称谓列表
	    public String TitleListAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.titlelist+FG+Content);
	    }
	    // 进入帮派
	    public String IntogangAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.intogang+FG + Content);
	    }
	    // 创建帮派
	    public String GangCreateAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangcreate+FG + Content);
	    }
	    // 申请加入帮派
	    public String GangApplyAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangapply+FG + Content);
	    }
	    // 退出帮派
	    public static String GangRetreatAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangretreat+FG + Content);
	    }
	    // 踢出帮派
	    public static String GangShotAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangshot+FG + Content);
	    }
	    // 帮派允许加入
	    public static String GangAgreeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangagree+FG + Content);
	    }
	    // 帮派拒绝加入
	    public static String GangRefuseAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangrefuse+FG + Content);
	    }
	    // 帮派职务任命
	    public static String GangAppointAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangappoint+FG + Content);
	    }
	    // 帮派退位让贤
	    public static String GangChangeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangchange+FG + Content);
	    }
	    //修复人物和召唤兽信息
	    public static String npccureAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.npccure+FG + Content);
	    }
	    //捐款
	    public static String givemoneyAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.givemoney+FG + Content);
	    }
	    //结婚
	    public static String marryAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.marry+FG + Content);
	    }
	    //洞房
	    public static String makeloveAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.makelove+FG + Content);
	    }
	    //前端提示
	    public String PromptAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.prompt+FG + Content);
	    }
	    // 更改称谓
	    public String TitleChangeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.titlechange+FG + Content);
	    }
	    // 坐骑骑乘/休息
	    public String MountCarryAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.mountcarry+FG + Content);
	    }
	    // 放生召唤兽
	    public String PetReleaseAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.petrelease+FG + Content);
	    }
	    // 购买冥钞
	    public String BuyMingChaoAgreement() {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.buymingchao+FG);
	    }
	    //种族转换
	    public String RacialTransformationAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.racialtransformation+FG + Content);
	    }
	    //领取任务
	    public String GetTheTaskAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.getthetask+FG + Content);
	    }
	    //放弃任务
	    public String GiveUpTheTaskAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.giveupthetask+FG + Content);
	    }
	    //删除好友  212
	    public String delectFriend(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.deletefriend+FG + Content);
	    }
	    //坐骑学习技能
	    public String addMountSkill(String Content) {
	        return NewAESUtil.AESJDKEncode(AgreementUtil.addmountskill+FG + Content);
	    }
	    //坐骑遗忘技能
	    public String missMountSkill(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.missmountskill+FG + Content);
	    }
	    //修改坐骑的属性(根骨,灵性,力量)
	    public String changeMountValue(String Content) {
	  		return NewAESUtil.AESJDKEncode(AgreementUtil.changemountvalue+FG + Content);
	    }
	    //获取坐骑(通过坐骑卡获取)
	    public String mountGet(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.mountget+FG + Content);
	    }
	    //获取宝宝
	    public String getBaby(String Content) {		  
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.baby+FG + Content);
	    }
	    //离婚
	    public String unMarry(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.unmarry+FG + Content);
	    }
	    //更改装备灵宝法宝   传LingBaoEquipment
	    public static String EquipmentLing(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.equipmentLing+FG + Content);
	    }
	    //获取灵宝法宝    接收LingFaBean 
	    public static String Ling(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.ling+FG + Content);
	    } 	
	    //更新灵宝   传 UpdateLingBean 根据操作字段修改数据库  不回传
	    public static String UpdateLing(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.updateling+FG + Content);
	    }
	    //更新法宝        法宝暂时不要
	    public static String UpdateFa(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.updatefa+FG + Content);
	    } 
	    //扣除银两 传string
	    public static String Deductiontael(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.deductiontael+FG + Content);
	    }
	    //宝宝产生
	    public String babyborn(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.babyborn+FG + Content);	
	    }
	    //充值信息
	    public String applyPay(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.applypay+FG + Content);
	    }
	    // 宝宝抚养权
	    public String BabyCustodayAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.babycustoday+FG + Content);
	    }
	    //典当协议
	    public String pawnAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.pawn+FG + Content);
	    }
	    //服务器停止 serverstop
	    public String serverstopAgreement() {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.serverstop+FG );
	    }
	    //战斗重连
	    public String battleConnectionAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.battleconnection+FG + Content);
	    }
	    //NPC弹窗
	    public String NPCDialogAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.npcdialog+FG + Content);
	    }
	    //中量级数据广播
	    public String MiddleAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.middle+FG + Content);
	    }
	    //引用监狱广播
	    public String QuoteOutAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.quoteout+FG + Content);
	    }
	    //添加单个物品
	    public String AddGood(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.addgood+FG + Content);
	    }
	    //更新宝宝数据
	    public String updababy(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.updababy+FG + Content);	
	    }
//	    //奖励协议头
//	    public String getreward(String Content) {
//	    	return NewAESUtil.AESJDKEncode(AgreementUtil.getreward+FG + Content);	
//	    }
	    //帮战npc点击
	    public String gangmonitor(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangmonitor+FG + Content);	
	    }
	    //帮战功能反应
	    public String gangbattle(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangbattle+FG + Content);	
	    }
	    //帮战状态
	    public String gangstate(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.gangstate+FG + Content);	
	    }
	    //发布摊位信息
	    public String stallAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.stall+FG + Content);	
	    }
	    //获取摊位信息
	    public String stallgetAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.stallget+FG + Content);
	    }
	    //购买摊位
	    public String stallbuyAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.stallbuy+FG + Content);	
	    }
	    //资源获取的统一处理
	    public String assetAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.asset+FG + Content);	
	    }
	    //更新摊位  StallBean
	    public String stallstateAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.stallstate+FG + Content);	
	    }
	    //放生坐骑
	    public String mountreleaseAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.mountrelease+FG + Content);
	    }
	    // 添加或清除明雷怪
	    public String updateMonstersAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.updatemonsters+FG + Content);	
	    }
	    //野怪点击(点击直接获得物品之类)
	    public String clickMonstersAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.ClickMonsters+FG + Content);	
	    }
	    //投放赏功堂物品
	    public String throwinarticleAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.throwinarticle+FG + Content);
	    }
	    //获取赏功堂物品
	    public String obtainarticleAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.obtainarticle+FG+Content);
	    }
	  //获取赏功堂物品
	    public String obtainarticleAgreement() {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.obtainarticle+FG);
	    }
	    //抽中物品
	    public String drawnitemsAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.drawnitems+FG+Content);
	    }
	    //抽中物品
	    public String drawnitemsAgreement() {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.drawnitems+FG);
	    }
	    //抽物品失败
	    public String drawnitemsfailAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.drawnitemsfail+FG+Content);
	    }
	    //绑定手机号
	    public String bindingmobileAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.bindingmobile+FG + Content);
	    }
	    //排行榜信息
	    public String pankinglistAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.pankinglist+FG + Content);
	    }
	    //购买物品信息
	    public String nbuyAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.nbuy+FG + Content);	    
	    }
    	//人物的物品使用
    	public String userAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.user+FG + Content);	    
    	}
    	//召唤兽的物品使用
    	public String userpetAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.userpet+FG + Content);	    
    	}
    	//坐骑的物品使用
    	public String usermountAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.usermount+FG + Content);	    
    	}
    	//灵宝的物品使用
    	public String userlingAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.userling+FG + Content);	    
    	}
    	//宝宝的物品使用
    	public String userbabyAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.userbaby+FG + Content);	    
    	}
    	//卡片类物品使用
    	public String usercardAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.usercard+FG + Content);	    
    	}
    	//伙伴类物品使用
    	public String userpalAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.userpal+FG + Content);	    
    	}
    	// 掉落拆解
    	public String dropAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.drop + FG + Content);
    	}
    	//背包记忆
    	public String packRecordAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.packRecord+FG + Content);	    
    	}
    	// 进入游戏
    	public String enterGameAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.enterGame+FG + Content);
    	}
    	// 保存系统设置
    	public String roleSystemAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.roleSystem+FG+Content);
    	}
    	//报名参加水陆大会
    	public String registrationAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.registration+FG + Content);	    
    	}
    	//进入水陆大会比武场
    	public String enterfiledAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.enterfiled+FG + Content);	    
    	}
    	//提示
    	public String tipAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.tip+FG + Content);	    
    	}
  
    	//保存私密数据
    	public String rolePrivateAgreement(String Content) {
    		return NewAESUtil.AESJDKEncode(AgreementUtil.rolePrivate+FG + Content);	    
    	}
    	//下挑战书
	    public String bookofchalgAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.bookofchalg+FG + Content);
		}
	    //拒绝别人的挑战
	    public String refusechalgAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.refusechalg+FG + Content);
		}
	    /**刮刮乐*/
	    public String getfivemsgAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.getfivemsg+FG + Content);
		}
	    /**战斗结束*/
	    public static String FightingendAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.fightingend+FG + Content);
	    }
	    //战斗预知
	    public static String FightingForeseeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.fightingforesee+FG + Content);
	    }
	    //接收战斗回合处理结果
	    public static String FightingRoundDealAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.fightingrounddeal+FG + Content);
	    }
	    /**额外属性替换*/
	    public static String ExtrattroperAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.extrattroper+FG + Content);
		}
	    /**获取孵化进度*/
	    public String HatchvalueAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.hatchvalue+FG + Content);
	    }
	    /**加孵化进度*/
	    public String HatchaddAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.hatchadd+FG + Content);
	    }
	    /**进度满时发送物品*/
	    public String GivegoodsAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.givegoods+FG + Content);
	    }
	    /**绑定安全码*/
	    public String bindingMobileAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.bindingMobile+FG + Content);
	    }
	    /**展示点击*/
	    public String richMAgreement(int type,String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.richM+FG +type+Content);
	    }
	    
	    /**根据角色ID查找好友*/
	    public String searcahChatRoleIdAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.searcahChatRoleId+FG + Content);
	    }
	    /**根据角色名查找好友*/
	    public String searcahChatRoleNameAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.searcahChatRoleName+FG + Content);
	    }
	    /**获取好友的聊天记录*/
	    public String searchChatRecordeAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.searchChatRecorde+FG + Content);
	    }

	    /**实时决斗排行榜*/
	    public String duelBoradDataAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.DUELBOARD + FG + Content);
	    }
	    
	    /**商品搜索*/
	    public String CBGSearch1Agreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGSearch1+FG + Content);
	    }
	    /**我的商品搜索*/
	    public String CBGSearch2Agreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGSearch2+FG + Content);
	    }
	    /**我的订单搜索*/
	    public String CBGSearch3Agreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGSearch3+FG + Content);
	    }
	    /**指定购买搜索*/
	    public String CBGSearch4Agreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGSearch4+FG + Content);
	    }
	    /**我的货物搜索*/
	    public String CBGSearch5Agreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGSearch5+FG + Content);
	    }
	    /**消息搜索*/
	    public String CBGSearch6Agreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGSearch6+FG + Content);
	    }
	    /**收藏查询搜索*/
	    public String CBGSearch7Agreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGSearch7+FG + Content);
	    }
	    /**商品购买*/
	    public String CBGBuyAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGBuy+FG + Content);
	    }
	    /**我的货物取回*/
	    public String CBGBackAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.CBGBack+FG + Content);
	    }
	    /**交易状态*/
	    public String TransStateAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.TransState+FG + Content);
	    }
	    /**交易物品刷新*/
	    public String TransGoodAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.TransGood+FG + Content);
	    }
	    /**任务协议头*/
	    public String TaskNAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.TASKN+FG + Content);
	    }
	    /**战斗抗性*/
	    public String fightQlAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.fightQl+FG + Content);
	    }
	    /**刷新人物显示*/
	    public String upRoleShowAgreement(String Content) {
	    	return NewAESUtil.AESJDKEncode(AgreementUtil.upRoleShow+FG + Content);
	    }
	public String goodforgoodstAgreement(String Content) {
		return NewAESUtil.AESJDKEncode(AgreementUtil.GOODSFORGOODS + FG + Content);
	}
    	static String FB="\n";
	    //战斗回合角色出招
	    public String battleroundAgreement(String Content) {
	    	return AgreementUtil.fightround+FG + Content+FB;
//	    	return NewAESUtil.AESJDKEncode(AgreementUtil.fightround+FG + Content);
	    }
	    //战斗回合结束
	    public static String FightingRoundEndAgreement(String Content) {
	    	return AgreementUtil.fightingroundend+FG + Content+FB;
//    		return NewAESUtil.AESJDKEncode(AgreementUtil.fightingroundend+FG + Content);
	    }	
    	//战斗状态
    	public String battleStateAgreement(String Content) {
    		return AgreementUtil.battlestate+FG+Content+FB;
//    		return NewAESUtil.AESJDKEncode(AgreementUtil.battlestate+FG + Content);
    	}
    	// 移动
	    public String moveAgreement(String Content) {
	    	return AgreementUtil.move+FG+Content+FB;
//			return NewAESUtil.AESJDKEncode(AgreementUtil.move+FG + Content);
		}
	    

		/** HGC--2019-11-13 */
		/** 手机账号绑定验证码 */
		public String PhoneBangAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.PhoneBang + FG + Content);
		}

		/** 手机验证码，解绑 */
		public String UnPhoneBangAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.UnPhoneBang + FG + Content);
		}

		/** 获取手机绑定协议头 */
		public String PhoneNumberIsNoGetAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.PhoneNumberIsNoGet + FG + Content);
		}
		/** HGC--2019-11-13--end */
		// 获取vip兑换 
		public String GetvipgradepackAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Getvipgradepack + FG + Content);
		}
		// vip兑换
		public String VipgradesureAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Vipgradesure + FG + Content);
		}
		 // 选区协议头
		public String PaydaygradepayAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Paydaygradepay + FG + Content);
		}
		// 每日充值兑换
		public String PaydaygradesureAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Paydaygradesure + FG + Content);
		}
		// 连续充值实例化
		public String DayforweekgradegetAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Dayforweekgradeget + FG + Content);
		}
		// 连续充值领取
		public String DayforweekgradesureAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Dayforweekgradesure + FG + Content);
		}
		// 每日特惠
		public String DayforonegetAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Dayforoneget + FG + Content);
		}
		// 每日特惠购买
		public String DayforonesureAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Dayforonesure + FG + Content);
		}
		// 冲级礼包实例化
		public String chongjipackgetAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.chongjipackget + FG + Content);
		}
		// 冲级礼包兑换
		public String ChongjipacksureAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Chongjipacksure + FG + Content);
		}
		// 冲级礼包兑换
		public String ChongjipackopenAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Chongjipackopen + FG + Content);
		}
		//副本数据
		public String sceneAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.Scene + FG + Content);
		}
		//招募
		public String enlistAgreement(String Content){
			return NewAESUtil.AESJDKEncode(AgreementUtil.enlist + FG + Content);
		}
		//创建队伍
	    public String team1Agreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.team1 + FG + Content);
	    }
	    //申请加队
	    public String team2Agreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.team2 + FG + Content);
	    }
	    //队伍信息
	    public String team3Agreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.team3 + FG + Content);
	    }
	    //队伍状态
	    public String team4Agreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.team4 + FG + Content);
	    }
	    //队伍操作
	    public String team5Agreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.team5 + FG + Content);
	    }
	    //申请列表
	    public String team6Agreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.team6 + FG + Content);
	    }
	    //寻路
	    public String findWayAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.findway + FG + Content);
	    }
	    //确认框
	    public String confirmAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.confirm + FG + Content);
	    }
	    //单人竞技场
	    public String oneArenaAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.oneArena + FG + Content);
	    }
	    //组队竞技场
	    public String teamArenaAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.teamArena + FG + Content);
	    }
	    //51劳动节特别任务 排行数据
	    public String laborAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.labor + FG + Content);
	    }
	    //活动图标显示隐藏
	    public String viconAgreement(String Content) {
		    return NewAESUtil.AESJDKEncode(AgreementUtil.vicon + FG + Content);
	    }
		// 返回区域信息
		public String returnAgreement(String Content) {
			
			return NewAESUtil.AESJDKEncode(AgreementUtil.REAREA + FG + Content);
		}
//		public static void main(String[] args) {
//			String bb="表达式撒旦节哀顺便的阿萨大家把数据打算把大苏打说不定就啊三个代表";
//			System.out.println("f返回区域信息加密:"+bb);
//			String aa=NewAESUtil.AESJDKEncode(bb);
//			System.out.println("f返回区域信息解密:"+NewAESUtil.AESJDKDncode(bb));
//		}
		// 区域和用户查询对应的角色
		public String uidAndQidForRoleAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.UidAndQidForRole + FG + Content);
		}
		
		// 返回验证码
		public String PhoneNumberReturnAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.PhoneNumberReturn + FG + Content);
		}
		
		// 藏宝阁商品购买(结果返回)
		public String goodsBuyAgreement(String content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.goodsBuy_inter + FG + content);
		}
		
		// 进行物品状态的更改(结果返回)
		public String salesGoodsChangeOrderAgreement(String content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.salesGoodsChangeOrder_inter + FG + content);
		}
		
		// 进行salegoods 物品进行(结果返回)
		public String saleGoodsStatuesAgreement(String content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.saleGoodsStatues_inter + FG + content);
		}
		
		/** zrikka 2020 0415 */
		// 绑定信息返回
		public String Account_BindingAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.ACCOUNT_BINDING + FG + Content);
		}

		/** zrikka 2020 0419 发送给账号服务器的消息 */
		// 修改用户相关信息
		public String Account_UpdateAgreement(String Content) {
			return Clinet_NewAESUtil.AESJDKEncode(AgreementUtil.ACCOUNT_UPDATE + FG + Content);
		}

		// ip封禁操作
		public String Ip_ActionAgreement(String Content) {
			return Clinet_NewAESUtil.AESJDKEncode(AgreementUtil.IP_ACTION + FG + Content);
		}
		// 灵犀操作
		public String LingXiAgreement(String Content) {
			return NewAESUtil.AESJDKEncode(AgreementUtil.LINGXI + FG + Content);
	}
	//学习法门
	public String skillfmlearnAgreement(String Content) {
		return NewAESUtil.AESJDKEncode(AgreementUtil.skilllearn2+FG + Content);
	}
	public String skillxplearnAgreement(String Content) {
		return NewAESUtil.AESJDKEncode(AgreementUtil.rolechange1+FG + Content);
	}
}