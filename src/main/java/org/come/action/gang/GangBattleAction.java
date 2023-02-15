package org.come.action.gang;

import come.tool.Battle.BattleData;
import come.tool.activity.WSS.BattleInfo;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.action.sys.ChangeMapAction;
import org.come.bean.ChangeMapBean;
import org.come.bean.ConfirmBean;
import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.entity.Mount;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.Door;
import org.come.model.Dorp;
import org.come.protocol.Agreement;
import org.come.protocol.AgreementUtil;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterUtil;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.BangBattle.BangBattlePool;
import come.tool.BangBattle.BangFight;
import come.tool.BangBattle.BangFileSystem;
import come.tool.BangBattle.BangPoints;
import come.tool.BangBattle.Build;
import come.tool.BangBattle.Member;
import come.tool.Battle.BattleThreadPool;
import come.tool.Battle.FightingForesee;
import come.tool.Good.DropUtil;
import come.tool.Good.ExpUtil;
import come.tool.PK.PalacePkBean;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.DNTG.DNTGRole;
import come.tool.Scene.DNTG.DNTGScene;
import come.tool.Scene.LTS.LTSArena;
import come.tool.Scene.LTS.LTSScene;
import come.tool.Scene.PKLS.PKLSScene;
import come.tool.Scene.RC.RCScene;
import come.tool.Scene.SLDH.SLDHRole;
import come.tool.Scene.SLDH.SLDHScene;
import come.tool.Scene.TGDB.TGDBScene;
import come.tool.Scene.ZZS.ZZSRole;
import come.tool.Scene.ZZS.ZZSScene;
import come.tool.Stall.AssetUpdate;
import come.tool.newGang.GangDomain;
import come.tool.newGang.GangUtil;

public class GangBattleAction implements IAction{
	//最小人数
	public static int MINSUM=3;
    //营地左
	public static String HOME_Left="";
	//营地右
    public static String HOME_Right="";
	//离开帮战
    public static String OUTBANG="";
    //高手
    public static String GAOSHOU="";
    //进入战场
    public static String ZHANC_Left="";
    public static String ZHANC_Right="";   
    
    public static String TGDB_2="";   
    public static String TGDB_3="";   
    public static String TGDB_4="";  
    //回长安
    public static String OUTCA="";  
    public static String LTS="";
	//武神山
	public static String WSS="";
    static{
    	ChangeMapBean change=new ChangeMapBean();
    	change.setMapid("3315");
    	//营地左
    	change.setMapx(276);
    	change.setMapy(1703);
    	HOME_Left=GsonUtil.getGsonUtil().getgson().toJson(change);
    	//营地右
    	change.setMapx(2122);
    	change.setMapy(367);
    	HOME_Right=GsonUtil.getGsonUtil().getgson().toJson(change);
    	//高手
    	change.setMapx(2309);
    	change.setMapy(1687);
    	GAOSHOU=GsonUtil.getGsonUtil().getgson().toJson(change);
    	//左进入战场
    	change.setMapx(345);
    	change.setMapy(1422);
    	ZHANC_Left=GsonUtil.getGsonUtil().getgson().toJson(change);
    	//右进入战场
    	change.setMapx(2085);
    	change.setMapy(616);
    	ZHANC_Right=GsonUtil.getGsonUtil().getgson().toJson(change);
    	//离开帮战
    	change.setMapid("1207");
    	change.setMapx(7920);
    	change.setMapy(4340);
    	OUTBANG=GsonUtil.getGsonUtil().getgson().toJson(change);
    	
    	//二层
    	change.setMapid("3326");
    	change.setMapx(1200);
    	change.setMapy(600);
    	TGDB_2=GsonUtil.getGsonUtil().getgson().toJson(change);
    	
    	//三层
    	change.setMapid("3327");
    	change.setMapx(1400);
    	change.setMapy(1200);
    	TGDB_3=GsonUtil.getGsonUtil().getgson().toJson(change);
    	
    	//四层
    	change.setMapid("3328");
    	change.setMapx(1200);
    	change.setMapy(900);
    	TGDB_4=GsonUtil.getGsonUtil().getgson().toJson(change);
    	
    	//长安
    	change.setMapid("1207");
    	change.setMapx(4294);
    	change.setMapy(2887);
    	OUTCA=GsonUtil.getGsonUtil().getgson().toJson(change);
    	
    	//擂台赛
    	change.setMapid("3333");
    	change.setMapx(1040);
    	change.setMapy(840);
    	LTS=GsonUtil.getGsonUtil().getgson().toJson(change);
    }
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		try {
			if (message.startsWith("M")) {		
				String[] v=message.substring(1).split("\\|");
				int zhi=Integer.parseInt(v[0]);
				MapMonsterBean bean=MonsterUtil.getMonster(zhi);
				if (bean==null) {return;}
				if (bean.getRobotType()>=100&&bean.getRobotType()<=199) {
					DNTG(bean,v[1],ctx);
				}
				return;
			}
			String[] v=message.split("\\|");
			int zhi1=Integer.parseInt(v[0]);
			switch (zhi1) {
			case 0:
				enroll(ctx);
				break;
			case 1:
				intobang(ctx);
				break;
			case 2:
				outbang(ctx);
				break;
			case 3:
				intomaster(ctx);
				break;
			case 4:
				intoBattle(ctx);
				break;
			case 5:
				gohome(ctx);
				break;
			case 6:
				ytz(ctx);
				break;			
			case 7:
				qxtz(ctx);
				break;			
			case 8:
				jstz(ctx);
				break;			
			case 9:
				cn(ctx,Integer.parseInt(v[1]));
				break;			
			case 10:
				gjt(ctx,Integer.parseInt(v[1]));
				break;			
			case 11:
				qx(ctx,Integer.parseInt(v[1]));
				break;	
			case 12:
				qdph(ctx,Integer.parseInt(v[1]));
				break;	
			case 13://领取蟠桃园奖励
				bty(ctx);
				break;	
			case 14://进入宝库2层
			case 15://进入宝库3层
			case 16://进入宝库4层
			case 17://挑战宝库1层
			case 18://挑战宝库2层
			case 19://挑战宝库3层
			case 20://挑战宝库4层
				TGDB(zhi1, ctx);
				break;
			case 21://参加种族赛
			case 22://一键领取种族赛奖励
			case 23://种族赛匹配
			case 24://取消种族赛匹配					
				ZZS(zhi1,ctx);
				break;
//			case 25://我要下战书
			case 26://我要接收战书
			case 27://我要取消战书
			case 28://我要观战		
			case 29://参加擂台赛
			case 30://一键领取擂台赛奖励		
				LTS(zhi1,v.length<=1?0:Integer.parseInt(v[1]),ctx);	
				break;
			case 31://我要进行九生九死挑战		
			case 32:
				RCFB(zhi1,ctx);
				break;
			case 33://我要参加跨服联赛
				PKLS(zhi1,ctx);
				break;
			case 34://我要参加大闹天宫
			case 36://我要进入上古战场
			case 37://我要离开上古战场
			case 38://领取大闹天宫奖励
				DNTG(zhi1,ctx);
				break;
			case 35://我来上交跟随野怪
				FOLLOW(zhi1,Integer.parseInt(v[1]),ctx);
				break;
			case 39://我要参加水陆大会
			case 40://领取水陆大会奖励
				SLDH(zhi1, ctx);
				break;
			case 41://领取经验加成
			case 42://领取强法加成
			case 43://领取抗性加成
			case 44://驯养参战召唤兽亲密
			case 45://驯养坐骑经验
			case 46://驯养坐骑技能熟练度
			case 47://升级帮派等级
			case 48://升级科技等级
			case 49://升级驯养师等级
				GangNpc(zhi1,v.length>1?v[1]:null,ctx);
				break;
				case 50://武神山守护
					JRWSS(zhi1,ctx);
					break;
				case 51://武神山守护
					WSS(ctx,1);
					break;
				case 52://武神山守护
					WSS(ctx,2);
					break;
				case 53://武神山守护
					WSS(ctx,3);
					break;
				case 54://武神山守护
					WSS(ctx,4);
					break;
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	/**
	 * 武神山挑战
	 * @param i
	 * @param ctx
	 * @param type  1人  2地  3天  4天帝
	 */
	public void WSS(ChannelHandlerContext ctx, int type){
		// 是否在开始时间
		if(!BattleInfo.isOpen()) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("太阳还没落山，现在"+(type==4 ? "帝印" : "烛火")+"还不需要守护"));
			return;
		}

		// 有没有人在挑战
		BattleData battleData = BattleThreadPool.BattleDatas.get(type);
		if (battleData != null) {
			SendMessage.sendMessageToSlef(ctx, BattleInfo.CHECKTS2);
			return;
		}

		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		String msg = BattleInfo.battle(roleInfo,type);
		if (msg != null) {
			SendMessage.sendMessageToSlef(ctx, msg);
			return;
		}
	}
	private void JRWSS(int zhi1, ChannelHandlerContext ctx) {
		// 检查坐骑，不允许乘骑
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo.getTeamInfo() != null && !roleInfo.getTeamInfo().equals("")) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("武神山乃是上古禁地不可以组队进入"));
			return;
		}
		if(roleInfo.getRoleData().getMid() != null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("前往武神山不可以乘骑坐骑"));
			return;
		}


		// 传送
		IAction action=ParamTool.ACTION_MAP.get("changemap");
		action.action(ctx,WSS);
	}
	//类型0 我要报名帮战
    public void enroll(ChannelHandlerContext ctx){
    	// 获得人物信息
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	if (pool.isEnroll()) {
			if (roleInfo.getGang_id().intValue()==0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你没有帮派无法报名"));
				return;		
			}
			if (!(roleInfo.getGangpost().equals("帮主")||roleInfo.getGangpost().equals("护法"))) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你在帮中职务太低"));
				return;		
			}
			BangPoints bangPoints=pool.getBangPoints(roleInfo.getGang_id());
			if (bangPoints==null) {
				if (pool.group.getlist().size()<16) {
					pool.group.addlist(new BangPoints(roleInfo.getGang_id()));	
					BangFileSystem.getBangFileSystem().DataSaving(pool);
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("报名成功"));
				}else {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("名额已经满了"));
				}
			}else {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你帮派已经报过名了"));
			}		
		}else {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("不在报名时间段内"));
		}
    }
	//类型1 我要参加帮战 
    public void intobang(ChannelHandlerContext ctx){
    	// 获得人物信息
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo.getTeamInfo() != null && !roleInfo.getTeamInfo().equals("")) {
    		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("进去后在组队"));
			return;	
		}else if (roleInfo.getGrade()<31) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("怎么也得有30级吧"));
			return;	
		}
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
    	if (bangFight==null) {
			if (roleInfo.getGang_id().longValue()==0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你还没有加入帮战"));
				return;	
			}
			BangPoints bangPoints=pool.getBangPoints(roleInfo.getGang_id());
			if (bangPoints==null) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你帮派还没报名帮战"));
				return;	
			}
			if (bangPoints.getRank()!=-1) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你们帮派在本周取得第"+bangPoints.getRank()+"名次"));
				return;	
			}
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮战还未开启"));				
		}else {
			if (bangFight.BangState!=1) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("帮战还未开启"));
				return;	
			}
			if (bangFight.addMember(roleInfo.getRolename(), roleInfo.getGang_id())) {
				//送进帮战
				roleInfo.setTitle(roleInfo.getGangname()+"帮"+roleInfo.getGangpost());
				bangFight.getzk(roleInfo.getRolename(), true);
				IAction action=ParamTool.ACTION_MAP.get("changemap");
				action.action(ctx,bangFight.iscamp(roleInfo.getGang_id())?HOME_Left:HOME_Right);
			}
		}
    }
    //类型2 我要离开帮战
    public void outbang(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo.getTeamInfo() != null && !roleInfo.getTeamInfo().equals("")) {
    		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("离开后在组队"));
			return;	
		}
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
    	if (bangFight!=null)
    		bangFight.removeMember(roleInfo.getRolename(), roleInfo.getGang_id());
    	//离开帮战地图
    	IAction action=ParamTool.ACTION_MAP.get("changemap");
    	action.action(ctx, OUTBANG);
    }
    //类型3 我要进入高手挑战赛
    public void intomaster(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        if (member==null)return;
        if (member.getState()!=0) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
			return;	
		}
        IAction action=ParamTool.ACTION_MAP.get("changemap");
    	action.action(ctx,GAOSHOU);
    }
    //类型4 我要进入战场
    public void intoBattle(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        if (member==null)return;
        if (member.getState()!=0) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
			return;	
		}
        IAction action=ParamTool.ACTION_MAP.get("changemap");
    	action.action(ctx,bangFight.iscamp(roleInfo.getGang_id())?ZHANC_Left:ZHANC_Right);
    }
    //类型5 回到营地
    public void gohome(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1){
        	IAction action=ParamTool.ACTION_MAP.get("changemap");
        	action.action(ctx,HOME_Left);
        	return;
        }
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        if (member==null){
        	IAction action=ParamTool.ACTION_MAP.get("changemap");
        	action.action(ctx,bangFight.iscamp(roleInfo.getGang_id())?HOME_Left:HOME_Right);
        	return;
        }
        if (member.getState()!=0) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
			return;	
		}
        // 队伍中所有角色(队伍传送)
        String[] roles = roleInfo.getTeam().split("\\|");
		for (int i = 0; i < roles.length; i++) {
			 Member role=bangFight.getMember(roles[i], roleInfo.getGang_id());
			 if (role!=null){
				 role.setState(1);
				 role.setTime2(60);			
			 }
		}
        IAction action=ParamTool.ACTION_MAP.get("changemap");
    	action.action(ctx,bangFight.iscamp(roleInfo.getGang_id())?HOME_Left:HOME_Right);
    }
    //类型6 我要挑战
    public void ytz(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        if (member==null)return;
        if (member.getState()!=0) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
			return;	
		}
        if (roleInfo.getTeam().split("\\|").length<MINSUM) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("最少人数"+MINSUM));
			return;
		}
        if (bangFight.Launch!=null) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("已经有人在叫战了"));
			return;	
		}else if (bangFight.PKstate!=0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("本轮挑战赛已经开始了"));
			return;
		}else {
		    member.setState(3); 
			bangFight.getzk(roleInfo.getRolename(),false);
		    //补充
		    StringBuffer buffer=new StringBuffer();
			buffer.append("#G");
			buffer.append(roleInfo.getRolename());
			buffer.append("#Y说在座各位都是垃圾");
			bangFight.BattleNews(buffer.toString());	
			bangFight.Launch=member;
		}
    }
    //类型7 我要取消挑战
    public void qxtz(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        if (member==null)return;
        if (bangFight.Launch!=null) {
        	if (bangFight.Launch.getKey().equals(roleInfo.getRolename())) {
        		member.setState(0);
        		bangFight.getzk(roleInfo.getRolename(),false);
        		bangFight.Launch=null;
        		//补充
        		StringBuffer buffer=new StringBuffer();
     			buffer.append("#G ");
     			buffer.append(roleInfo.getRolename());
     			buffer.append(" #Y灰溜溜离开挑战台");
     			bangFight.BattleNews(buffer.toString());	
			}
		}
    }
    //类型8 我要应战
    public void jstz(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        if (member==null)return;
        if (member.getState()!=0) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
			return;	
		}
        if (roleInfo.getTeam().split("\\|").length<MINSUM) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("最少人数"+MINSUM));
			return;
		}
        if (bangFight.Launch==null) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("没有人在叫战"));
		}else {
			if (bangFight.Launch.getCamp().compareTo(roleInfo.getGang_id())==0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前在叫战的是自己人"));
			}else {
				String launame=bangFight.Launch.getKey();
//				StringBuffer buffer=new StringBuffer();
//				buffer.append("#G");
//				buffer.append(roleInfo.getRolename());
//				buffer.append("#Y受不了#G ");
//				buffer.append(launame);
//				buffer.append("#Y的挑衅打起来");				
//				bangFight.BattleNews(buffer.toString());
//				StringBuffer buffer=new StringBuffer();
//				buffer.append(BangFight.TIME_GAO);
//				buffer.append("|");
//				for (int i = 0; i < array.length; i++) {
//					ChannelHandlerContext ctxx=GameServer.getRoleNameMap().get(launame);
//					if (ctxx==null) {
//						continue;
//					}
//					LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
//				}
//				bangFight.Msg(buffer.toString());
				//补充
				bangFight.daduan2(bangFight.Launch.getKey(),1);
				FightingForesee foresee=new FightingForesee();
				foresee.setYidui(roleInfo.getTeam());
				LoginResult loginResult=GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(launame));
				foresee.setErdui(loginResult.getTeam());
				foresee.setType(12);			
				BattleThreadPool.addBattle(ctx, foresee);

			}
		} 
    }
    //类型9 我要给塔充能
    public void cn(ChannelHandlerContext ctx,int v){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        Build build=bangFight.getBuild(v);
        if (roleInfo.getTeam().split("\\|").length<MINSUM) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("最少人数"+MINSUM));
			return;
		}
        if (build.getState()!=Build.IDLE) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("塔处于繁忙状态"));
		}else if (member.getState()!=0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
		}else {
			if (build.getHp()<=0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("塔已经损坏了"));
				return;
			}
			if (build.getType()==Build.TOWER_LONG) {
				build.setState(Build.ATTACK);	
			}else {
				build.setState(Build.ENERGY);
			}
			build.setRoleName(roleInfo.getRolename());
			StringBuffer buffer=new StringBuffer();
			buffer.append("#G ");
			buffer.append(roleInfo.getRolename());
			buffer.append(" #Y正在给#G ");
			buffer.append(build.getName());
			buffer.append(" #Y充能");			
			bangFight.BattleNews(buffer.toString());	
			//补充
			member.setState(4);
			bangFight.getzk(roleInfo.getRolename(),false);
		}
    }
    //类型10 我要攻击塔
    public void gjt(ChannelHandlerContext ctx,int v){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        Build build=bangFight.getBuild(v);
        if (roleInfo.getTeam().split("\\|").length<MINSUM) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("最少人数"+MINSUM));
			return;
		}
        if (build.getState()!=Build.IDLE) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("塔处于繁忙状态"));
		}else if (member.getState()!=0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
		}else {
			if (build.getHp()<=0) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("塔已经损坏了"));
				return;
			}
			if (build.getType()==Build.TOWER_LONG) {
				build.setState(Build.ATTACK);	
			}else {
				build.setState(Build.BEATEN);
			}
			build.setRoleName(roleInfo.getRolename());
			StringBuffer buffer=new StringBuffer();
			buffer.append("#G ");
			buffer.append(roleInfo.getRolename());
			buffer.append(" #Y正在攻击#G ");
			buffer.append(build.getName());
			bangFight.BattleNews(buffer.toString());		
			//补充
			member.setState(4);
			bangFight.getzk(roleInfo.getRolename(),false);
		}
    }
    //类型11 我要取消操作
    public void qx(ChannelHandlerContext ctx,int v){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        Build build=bangFight.getBuild(v);
        if (build.getState()!=Build.IDLE) {
            if (build.getRoleName().equals(roleInfo.getRolename())) {
            	build.setState(Build.IDLE);
            	build.setRoleName(null);
            	//补充
            	member.setState(0);
    			bangFight.getzk(roleInfo.getRolename(),false);
			}
        }
    }
    //类型12 我要掐断炮火
    public void qdph(ChannelHandlerContext ctx,int v){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	BangBattlePool pool=BangBattlePool.getBangBattlePool();
    	BangFight bangFight=pool.getBangFight(roleInfo.getGang_id());
        if (bangFight==null||bangFight.BangState!=1)return;
        Member member=bangFight.getMember(roleInfo.getRolename(), roleInfo.getGang_id());
        if (member==null)return;
        if (member.getState()!=0) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态无法操作"));
			return;	
		}
        if (roleInfo.getTeam().split("\\|").length<MINSUM) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("最少人数"+MINSUM));
			return;
		}
        Build build=bangFight.getBuild(0);
        if (build.getState()!=Build.ATTACK) {
        	SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("没人在点火"));
			return;
		}
    	String launame=build.getRoleName();
    	LoginResult loginResult=GameServer.getAllLoginRole().get(GameServer.getRoleNameMap().get(launame));
		if (loginResult==null) {
			build.setState(Build.IDLE);
			build.setTime(0);
			build.setRoleName(null);
			return;
		}
		if (loginResult.getGang_id().compareTo(roleInfo.getGang_id())==0) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("自家兄弟？"));
			return;
		}
    	StringBuffer buffer=new StringBuffer();
		buffer.append("#G");
		buffer.append(roleInfo.getRolename());
		buffer.append("#Y掐断炮火和#G");
		buffer.append(launame);
		buffer.append("#Y打起来");				
		bangFight.BattleNews(buffer.toString());	
		//补充
		bangFight.daduan2(launame,3);
		FightingForesee foresee=new FightingForesee();
		foresee.setYidui(roleInfo.getTeam());
		foresee.setErdui(loginResult.getTeam());
		foresee.setType(11);			
		BattleThreadPool.addBattle(ctx, foresee);
    }
    //TODO 守护蟠桃园
    public void bty(ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.BTYID);
    	if (scene==null) {
    		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));
			return;
		}
    	scene.getAward(ctx,roleInfo);
    }
    public void TGDB(int i,ChannelHandlerContext ctx){
    	Scene scene=SceneUtil.getScene(SceneUtil.TGDBID);
		if (scene==null) {
			return;
		}
        //case 14://进入宝库2层case 15://进入宝库3层case 16://进入宝库4层
        //case 17://挑战宝库1层case 18://挑战宝库2层case 19://挑战宝库3层case 20://挑战宝库4层
		TGDBScene tgdbScene=(TGDBScene)scene;
		if (i>=14&&i<=16) {
			if (tgdbScene.isC(i-13)==0) {
				  IAction action=ParamTool.ACTION_MAP.get("changemap");
			      if (i==14) {action.action(ctx,TGDB_2);}
			      else if (i==15) {action.action(ctx,TGDB_3);}
			      else if (i==16) {action.action(ctx,TGDB_4);}	
			}     
		}else if (i>=17&&i<=20) {
			if (tgdbScene.isC(i-16)==1) {
				FightingForesee fightingForesee=new FightingForesee();
		    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
				fightingForesee.setYidui(roleInfo.getTeam());
				fightingForesee.setType(1);
				fightingForesee.setI(tgdbScene.getTgdbMonsters()[i-17].getBean().getI());
				BattleThreadPool.addBattle(ctx, fightingForesee);
			}
		}
    }
    public void ZZS(int i,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {
    		return;
    	}
    	ZZSScene zzsScene=SceneUtil.getZZS(roleInfo);
    	if (zzsScene==null) {
    		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));
    		return;
		}
    	if (i==21) {//参加种族赛
    		if (zzsScene.getI()!=1) {
        		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("已经关闭进场通道"));
        		return;
    		}	
            if (!roleInfo.getTeam().equals(roleInfo.getRolename())) {
            	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("只能单人参加"));
            	return;
			}
            if (roleInfo.getTurnAround()<3) {
            	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("最少要3转"));
            	return;
			}
            if (roleInfo.getGrade()<419) {
            	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("最少要3转140级"));
            	return;
			}
    		if ((zzsScene.getType()+10001)!=roleInfo.getRace_id().intValue()) {
    			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("才过了几分钟!种族怎么变了?"));
    			return;
			}
    		zzsScene.addRole(ctx, roleInfo);
		}else if (i==22) {//一键领取种族赛奖励
			zzsScene.getAward(ctx, roleInfo);
		}else if (i==23||i==24) {//种族赛匹配 //取消种族赛匹配	
	        if (zzsScene.getI()==2) {
				ZZSRole role=zzsScene.getRole(roleInfo);
				if (role!=null) {
					if (i==23) {
						if (role.getCYnum()>=15) {
							SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("最多匹配15场"));
							return;
						}
						long time=System.currentTimeMillis();
						int c=role.getCYnum()-role.getHSnum();
						if (c>10&&time-role.getTime()<120000) {
							SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你失败场数太多了匹配间隔2分钟"));
							return;
						}else if (c>5&&time-role.getTime()<60000) {
							SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你失败场数有点多匹配间隔1分钟"));
							return;
						}else if (time-role.getTime()<30000) {
							SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("匹配间隔30秒"));
							return;
						}
						role.setTime(time);
					}
					role.setI(i==23?1:0);
					if (i==23) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("正在匹配"));
					}else {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("取消匹配"));
					}
				}
			}
		}
    }
    public void LTS(int value,int type,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {
    		return;
    	}
    	Scene scene=SceneUtil.getScene(SceneUtil.LTSID);
		if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		LTSScene ltsScene=(LTSScene)scene;
//	    case 29://参加擂台赛
//	    case 30://一键领取擂台赛奖励	
		if (value==29) {
			String[] teams=roleInfo.getTeam().split("\\|");
			if (!teams[0].equals(roleInfo.getRolename())) {return;}
			for (int i = 0; i < teams.length; i++) {
				LoginResult login=null;
				if (i==0) {login=roleInfo;}
				else {
					ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(teams[i]);
					if (ctx2!=null) {login=GameServer.getAllLoginRole().get(ctx2);}
				}
				if (login==null) {return;}
				else if (login.getGrade()<399) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("最低3转120"));return;}
			}
			IAction action=ParamTool.ACTION_MAP.get("changemap");
			action.action(ctx,LTS);
			return;
		}else if (value==30) {
			ltsScene.getAward(ctx,roleInfo);
			return;
		}
		if (ltsScene.isEnd()) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("擂台赛已经结束"));
			return;
		}
		LTSArena arena=ltsScene.getArena(type);
		if (arena==null) {return;}
		if (arena.getPkMatch()==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("当前擂台无人摆擂"));return;}
	    //case 26://我要接收战书
	    //case 27://我要取消战书
	    //case 28://我要观战	
		if (value==26) {
			if (arena.getPkMatch().getPkMan1().getRoleId().compareTo(roleInfo.getRole_id())==0) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("自己还想打自己?"));return;}
			PalacePkBean palacePkBean=new PalacePkBean();
			palacePkBean.setPId(arena.getPkMatch().getPid());
			palacePkBean.setUsername(arena.getPkMatch().getPkMan1().getRoleName());
			palacePkBean.setType(1);
			palacePkBean.setNtype(arena.getlId());
			palacePkBean.setGold(new BigDecimal(arena.getPkMatch().getpKStake1().getMoney()));
			palacePkBean.setXianyu(new BigDecimal(arena.getPkMatch().getpKStake1().getXianYu()));
			palacePkBean.setExp(new BigDecimal(arena.getPkMatch().getpKStake1().getExp()));
			String message=Agreement.getAgreement().bookofchalgAgreement(GsonUtil.getGsonUtil().getgson().toJson(palacePkBean));
	    	SendMessage.sendMessageToSlef(ctx,message);	
		}else if (value==27) {
			if (arena.getPkMatch().getPkMan1().getRoleId().compareTo(roleInfo.getRole_id())!=0) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("又不是你在摆擂"));return;}
			if (arena.getBattleNumber()!=0) {return;}
			ltsScene.Cancel(arena.getPkMatch());
		}else if (value==28) {
			if (arena.getBattleNumber()==0) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("还没有人前来挑战擂主"));return;}
			if (roleInfo.getFighting()!=0) {return;}
			IAction action=ParamTool.ACTION_MAP.get(AgreementUtil.battleconnection);
			action.action(ctx,arena.getBattleNumber()+"");
		}
    }
    /**日常副本*/
    public void RCFB(int value,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.RCID);
		if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		RCScene rcScene=(RCScene) scene;
		String msg=null;
	    if (value==31) {msg=rcScene.addNo1(roleInfo);}
	    else if (value==32) {msg=rcScene.addNo2(roleInfo);}
		if (msg!=null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(msg));}
    }
    /**联赛副本*/
    public void PKLS(int value,ChannelHandlerContext ctx){
//    	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("报名已经截止"));
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.PKLSID);
		if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		PKLSScene pklsScene=(PKLSScene) scene;
		String msg=pklsScene.addEnroll(roleInfo);
	    if (msg!=null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(msg));}
    }
    /**我要参加大闹天宫*/
    public void DNTG(int value,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
    	if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
    	DNTGScene dntgScene=(DNTGScene)scene;
    	if (value==34) {
    		if (!dntgScene.isEnd()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经结束"));
				return;
			}
    		String msg=dntgScene.addEnroll(ctx, roleInfo);
        	if (msg!=null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(msg));}
        }else if(value==36||value==37){
        	DNTGRole dntgRole=dntgScene.getRole(roleInfo.getRole_id());
            if (dntgRole==null) {
				return;
			}
        	Door door=null;
        	if (value==37) {
        		MapMonsterBean bean=MonsterUtil.getFollowMonster(roleInfo.getTeam().split("\\|"));
    			if (bean!=null) {
    				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("携带宝箱无法离开"));
    				return;
    			}	
    			door=GameServer.getDoor(dntgRole.getCamp()==0?522:523);
			}else if (value==36) {
				if (dntgScene.getI()!=3) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("上古战场还未开放"));
    				return;
				}
				door=GameServer.getDoor(dntgRole.getCamp()==0?524:525);	
			}	
			if (door!=null) {
				ChangeMapBean changeMapBean=new ChangeMapBean();
				changeMapBean.setMapid(door.getDoormap());
				String[] vs=door.getDoorpoint().split("\\|");
				changeMapBean.setMapx(Integer.parseInt(vs[0]));
				changeMapBean.setMapy(Integer.parseInt(vs[1]));
				ChangeMapAction.ChangeMap(changeMapBean, ctx);
			}
		}else if (value==38) {
			if (dntgScene.isEnd()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动还未结束"));
				return;
			}
			DNTGRole dntgRole=dntgScene.getRole(roleInfo.getRole_id());
            if (dntgRole==null) {
            	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("本次活动你未参与"));
				return;
			}
            if (dntgRole.isA()) {
            	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已领取过奖励"));
				return;
			}
            dntgRole.setA(true);
            Dorp dorp=GameServer.getDorp(dntgRole.getDNJF()<=80000?"106":"107");
            if (dorp!=null) {
				double xs=1+dntgRole.getDNJF()/20000;
				if (xs>=50) {
					xs=50;
				}
            	DropUtil.getDrop(roleInfo,dorp.getDorpValue(),"大闹天宫礼包",22,xs,null);	
			}
		}
    }
    /**提交跟随野怪*/
    public void FOLLOW(int value,int type,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
		String[] teams=roleInfo.getTeam().split("\\|");
		if (!teams[0].equals(roleInfo.getRolename())) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你不是队长"));return;}
		if (roleInfo.getFighting()!=0) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你还在战斗中"));return;}
    	if (type==530) {//提交地煞星魂
			MapMonsterBean bean=MonsterUtil.getFollowMonster(teams);
			if (bean==null||bean.getRobotType()!=4) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你没有地煞星魂"));return;}
			MonsterUtil.removeMonster2(bean);
			SendMessage.sendMessageToMapRoles(roleInfo.getMapid(),Agreement.getAgreement().battleStateAgreement("M"+bean.getI()+"^2"));
			Dorp dorp=GameServer.getDorp("300");
			if (dorp==null) {return;}
			for (int i = 0; i < teams.length; i++) {
				LoginResult login=null;
				if (i==0) {login=roleInfo;}
				else {
					ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(teams[i]);
					if (ctx2!=null) {login=GameServer.getAllLoginRole().get(ctx2);}
				}
				if (login!=null) {
					DropUtil.getDrop(login,dorp.getDorpValue(),"地煞星魂礼包", 22,1D,null);		
				}
			}
    	}else if (type==42||type==43) {
			Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
			if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		    DNTGScene dntgScene=(DNTGScene) scene;
		    String msg=dntgScene.SGBX(roleInfo,teams,type);
		    if (msg!=null) {
		    	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(msg));
			}
		}
    }
    /**大闹天宫*/
    public void DNTG(MapMonsterBean bean,String msg,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
		if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
		DNTGScene dntgScene=(DNTGScene) scene;
		dntgScene.Function(bean, roleInfo, msg);
    }
    /**水陆大会*/
    public void SLDH(int value,ChannelHandlerContext ctx){
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	Scene scene=SceneUtil.getScene(SceneUtil.SLDHID);
    	if (scene==null) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动已经关闭了"));return;}
    	SLDHScene sldhScene=(SLDHScene) scene;
		if (value==39) {//参加水陆大会
    		String msg=sldhScene.addEnroll(ctx, roleInfo);
    		if (msg!=null) {
    			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(msg));
			}
    		
		}else if (value==40) {
			if (scene.isEnd()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("本次水陆大会还未结束"));
				return;
			}
			SLDHRole sldhRole=sldhScene.getRole(roleInfo.getRole_id());
			if (sldhRole==null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你未参加本次水陆大会"));
				return;
			}
			if (sldhRole.getI()!=0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已经领取过了"));
				return;
			}
			int dc=sldhRole.getSldhTeam().getValue();
			if (dc==0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你所在的队伍未参与任何一场战斗"));
				return;
			}
			//队伍成绩 0:未参与  1:参与   2:16强   3:季军   4:亚军   5:冠军
			Dorp dorp=GameServer.getDorp(dc==5?"2200":dc==4?"2201":dc==3?"2202":dc==2?"2203":"2204");
			if (dorp!=null) {
				sldhRole.setI(1);
				DropUtil.getDrop(roleInfo,dorp.getDorpValue(),dc==5?"水陆冠军礼包":dc==4?"水陆亚军礼包":dc==3?"水陆季军礼包":dc==2?"水陆8强礼包":"水陆参与礼包", 22,1D,null);		
			}
		}
    }
    public void GangNpc(int value,String ID,ChannelHandlerContext ctx){
    	//41://领取经验加成 42://领取强法加成 43://领取抗性加成
    	//44://驯养参战召唤兽亲密 45://驯养坐骑经验 46://驯养坐骑技能熟练度
    	//47://升级帮派等级 48://升级科技等级 49://升级驯养师等级
    	LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
    	if (roleInfo==null) {return;}
    	GangDomain domain=GangUtil.getGangDomain(roleInfo.getGang_id());
    	if (domain==null) {
    		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你帮派都没有"));
			return;
		}
    	if (value==41||value==42||value==43) {//41://领取经验加成 42://领取强法加成 43://领取抗性加成
            if (domain.getGangGroup().getKj()<=0) {
            	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("当前科技等级0级无法领取"));
            	return;
			}
    		if (roleInfo.getContribution().longValue()<100) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不足100帮贡"));
				return;
			}
    		int sum=MonitorUtil.getSum(roleInfo.getRole_id(),0);
    		if (sum>=2) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你今日已经领取2次,明日请早"));
				return;
			}
    		RoleData roleData=RolePool.getRoleData(roleInfo.getRole_id());
    		if (roleData!=null) {
    			MonitorUtil.addSum(roleInfo.getRole_id(),0);
    			String cardValue=null;
    			long time=1000*60*60*2;//2小时 			
    			if (value==41) {
    				cardValue="经验加成="+(domain.getGangGroup().getKj()+1)*5;
				}else if (value==42) {
					cardValue="加强全系法术="+(domain.getGangGroup().getKj()+1)*1.5;
				}else if (value==43) {
					double add=(domain.getGangGroup().getKj()+1)*2;
					cardValue="抗混乱="+add+"|抗封印="+add+"|抗昏睡="+add+"|抗鬼火="+add+"|抗雷="+add+"|抗风="+add+"|抗水="+add+"|抗火="+add;					
				}
    			UseCardBean limit=roleData.getLimit("帮派");
    			if (limit==null||!limit.getValue().equals(cardValue)) {
    	        	limit=new UseCardBean("帮派符文", "帮派", "222", time+System.currentTimeMillis(), cardValue);
    	        	roleData.addLimit(limit);
    			}else {
    				limit.setTime(limit.getTime()+time);
    			}
    			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
    			roleInfo.setContribution(new BigDecimal(roleInfo.getContribution().longValue()-100));  
	        	assetUpdate.updata("B=-100");
	        	assetUpdate.setUseCard(limit);
    			assetUpdate.setMsg("你领取了#G帮派符文");
    			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
			}
			
    	}else if (value==44||value==45||value==46) {
			//10000 500 200
			if (domain.getGangGroup().getXy()<=0) {
            	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("当前驯养师等级0级无法驯养"));
            	return;
			}
			if (roleInfo.getContribution().longValue()<150) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不足150帮贡"));
				return;
			}
			int sum=MonitorUtil.getSum(roleInfo.getRole_id(),1);
			if (sum>=10) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你今日已经驯养10次,明日请早"));
				return;
			}
			if (value==44) {
				RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(new BigDecimal(ID));
				if (pet==null||pet.getRoleid().compareTo(roleInfo.getRole_id())!=0) {return;}
				long qm=pet.getFriendliness();
				if (qm>=100000000) {SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的宝宝亲密已满"));return;}
				if (!domain.useXY()) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("驯养师太累了,让他休息一会儿!"));
					return;
				}
				MonitorUtil.addSum(roleInfo.getRole_id(),1);
				qm+=10000*(1+(domain.getGangGroup().getXy()-1)*0.2);
				if (qm>=100000000) {qm=100000000;}
				pet.setFriendliness(qm);
	        	AllServiceUtil.getRoleSummoningService().updatePetRedis(pet);
	        	AssetUpdate assetUpdate=new AssetUpdate();
	        	assetUpdate.setType(AssetUpdate.USEGOOD);
	        	roleInfo.setContribution(new BigDecimal(roleInfo.getContribution().longValue()-150));  
	        	assetUpdate.updata("B=-150");
	        	assetUpdate.setMsg("你的召唤兽#G"+pet.getSummoningname()+"#Y获得#G"+((int)(10000*(1+(domain.getGangGroup().getXy()-1)*0.2)))+"#Y亲密");
	    		assetUpdate.updata("P"+pet.getSid()+"="+pet.getGrade()+"="+pet.getExp()+"="+pet.getFriendliness()+"="+pet.getBasishp()+"="+pet.getBasismp());
	    		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
			}else {
				Mount mount=AllServiceUtil.getMountService().selectMountsByMID(new BigDecimal(ID));
				if (mount==null||mount.getRoleid().compareTo(roleInfo.getRole_id())!=0) {return;}
				if (value==45) {
					int lvl=mount.getMountlvl();
					if(lvl == 100||lvl>=200){//达到最高等级
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("坐骑 "+mount.getMountname()+" 已达最高等级100级！！"));
						return;
					}
					if (!domain.useXY()) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("驯养师太累了,让他休息一会儿!"));
						return;
					}
					MonitorUtil.addSum(roleInfo.getRole_id(),1);
					AssetUpdate assetUpdate=new AssetUpdate();
			    	assetUpdate.setType(AssetUpdate.USEGOOD);
			    	roleInfo.setContribution(new BigDecimal(roleInfo.getContribution().longValue()-150));  
		        	assetUpdate.updata("B=-150");
			    	int addexp = (int) (500*(1+(domain.getGangGroup().getXy()-1)*0.2));//经验值
					ExpUtil.MountExp(mount, addexp);//进行升级判断
					AllServiceUtil.getMountService().updateMountRedis(mount);
					assetUpdate.setMsg("你的坐骑#G"+mount.getMountname()+"#Y获得#G"+((int)(500*(1+(domain.getGangGroup().getXy()-1)*0.2)))+"#Y经验");
		    		assetUpdate.setMount(mount);
				    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
				}else {
					int up=100000;
					if (mount.getMountlvl()>100) {up=150000;}
					if(mount.getProficiency()>=up){//达到峰值
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("坐骑 "+mount.getMountname()+"的技能熟练度已达到峰值！！"));
						return;
					}
					if (!domain.useXY()) {
						SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("驯养师太累了,让他休息一会儿!"));
						return;
					}
					MonitorUtil.addSum(roleInfo.getRole_id(),1);
					AssetUpdate assetUpdate=new AssetUpdate();
			    	assetUpdate.setType(AssetUpdate.USEGOOD);
			    	roleInfo.setContribution(new BigDecimal(roleInfo.getContribution().longValue()-150));  
		        	assetUpdate.updata("B=-150");
			    	int addvalue = (int) (200*(1+(domain.getGangGroup().getXy()-1)*0.2));//物品的技能熟练度
					int proficiency = mount.getProficiency() + addvalue;
					if(proficiency > up){proficiency = up;}
					mount.setProficiency(proficiency);
					AllServiceUtil.getMountService().updateMountRedis(mount);
					assetUpdate.setMsg("你的坐骑#G"+mount.getMountname()+"#Y获得#G"+((int)(200*(1+(domain.getGangGroup().getXy()-1)*0.2)))+"#Y技能熟练度");
		    		assetUpdate.setMount(mount);
				    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
				}
			}
		}else if (value==47||value==48||value==49) {//47://升级帮派等级 48://升级科技等级 49://升级驯养师等级
			if (!roleInfo.getGangpost().equals("帮主")) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你不是帮主无法操作"));
				return;
			}
			int lvl=0;
			int xh=0;
			String name = null;
			if (value==47) {
				lvl=domain.getGang().getGanggrade().intValue();
				xh=(int) (Math.pow(2, lvl)*35000);
				name="帮派等级";
			}else if (value==48) {
				lvl=domain.getGangGroup().getKj();
				xh=(int) (Math.pow(2, lvl)*150000);
				name="科技等级";
			}else if (value==49) {
				lvl=domain.getGangGroup().getXy();
				xh=(int) (Math.pow(2, lvl)*150000);
				name="驯养师等级";
			}	
			if (lvl>=5) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("等级上限5级"));
				return;
			}else if (value!=47&&lvl>=domain.getGang().getGanggrade().intValue()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不能超过帮派等级"));
				return;
			}
			if (domain.getGang().getBuilder().longValue()<xh) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的帮派资金不足#G"+xh));
				return;
			}
			ConfirmBean confirmBean=new ConfirmBean();
			confirmBean.setMSG("你是否要消耗#G"+xh+"#W帮派资金升级"+name);
			confirmBean.setType(value);
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().confirmAgreement(GsonUtil.getGsonUtil().getgson().toJson(confirmBean)));
			return;
		}
    }
}
