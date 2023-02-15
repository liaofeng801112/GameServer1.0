package come.tool.Scene.LTS;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.model.Dorp;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;
import come.tool.Good.DropUtil;
import come.tool.PK.PKPool;
import come.tool.PK.PkMatch;
import come.tool.Role.RoleCard;
import come.tool.Scene.Scene;

public class LTSScene implements Scene{

	private int I;//状态 0关闭  1开启 2关闭
	private ConcurrentHashMap<String,LTSRole> ltsMap;
	private List<LTSRole> top;//排名前5
	private long mapID;
	private LTSThread ltsThread;
	private LTSArena[] arenas;
	public LTSScene() {
		// TODO Auto-generated constructor stub
		mapID=3333;
		arenas=new LTSArena[5];
		for (int i = 0; i < arenas.length; i++) {
			arenas[i]=new LTSArena(510+i);
		}
    	I=1;
		top=new ArrayList<>();
		ltsMap=new ConcurrentHashMap<>();
		ltsThread = new LTSThread(this);
		Thread T1 = new Thread(ltsThread);
		T1.start();	
		// 发起世界喊话
		NChatBean bean=new NChatBean();
		bean.setId(4);
		bean.setMessage("#R擂台争霸赛已经开启");
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**报名进场的玩家*/
	public LTSRole getRole(String role){
		LTSRole ltsRole=ltsMap.get(role);
		if (ltsRole==null) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role);
			if (ctx!=null) {
				LoginResult login=GameServer.getAllLoginRole().get(ctx);
				ltsRole=new LTSRole(login.getRole_id(),login.getRolename());
				ltsMap.put(ltsRole.getRole(),ltsRole);	
			}
		}
		return ltsRole;
	}
	/**取消*/
	public void Cancel(PkMatch pkMatch){
		PKPool.getPkPool().returnStake(pkMatch.getPkMan1(),pkMatch.getpKStake1(),"退回下注");
		LTSArena arena=getArena(pkMatch);
		if (arena==null) {return;}
		arena.setBattleNumber(0);
		arena.setPkMatch(null);
		PKPool.getPkPool().deletePkMatch(pkMatch.getPkMan1().getRoleId(), pkMatch.getPkMan2()!=null?pkMatch.getPkMan2().getRoleId():null);
	}
	/**超时判断*/
	public void OVERTIME(long time){
		for (int i = 0; i < arenas.length; i++) {
			PkMatch pkMatch=arenas[i].getPkMatch();
			if (pkMatch==null) {continue;}
			if (pkMatch.isOverTime2(time)) {
				PKPool.getPkPool().returnStake(pkMatch.getPkMan1(),pkMatch.getpKStake1(),"退回下注");
				addJF(pkMatch,0,pkMatch.getPkMan1().getRoleId());
				PKPool.getPkPool().deletePkMatch(pkMatch.getPkMan1().getRoleId(), pkMatch.getPkMan2()!=null?pkMatch.getPkMan2().getRoleId():null);
			}
		}
	}
	/**添加积分 -1取消  0不战而胜  1战斗结果 camp 0擂主 1打擂  nlvl 擂台等级*/
	public void addJF(PkMatch pkMatch,int type,BigDecimal id){
		LTSArena arena=getArena(pkMatch);
		if (arena==null) {return;}
		arena.setBattleNumber(0);
		arena.setPkMatch(null);
		if (type==-1) {return;}
		boolean is=false;
		if (type==0) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(pkMatch.getPkMan1().getRoleName());
			LoginResult loginResult=null;
			if (ctx!=null) {loginResult=GameServer.getAllLoginRole().get(ctx);}
			if (loginResult==null) {return;}
			String[] teams=loginResult.getTeam().split("\\|");
			for (int j = 0; j < teams.length; j++) {
				LTSRole ltsRole=getRole(teams[j]);
				if (ltsRole==null) {continue;}
				if (ltsRole.getBZnum()>=5) {continue;}
				ltsRole.setBZnum(ltsRole.getBZnum()+1);
				ltsRole.battle(true,true,arena.getlLvl(),0);
				if (PointRanking(ltsRole)) {is=true;}
			}
			return;
		}else {
			boolean ZL=false;
			RoleCard roleSB=null;
			int lsnum=0;//被终极的连胜数
			String zj=null;//姓名
			int lsnum2=0;//队长的连胜数
			String zj2=null;//姓名
			if (pkMatch.getPkMan1().getRoleId().compareTo(id)==0) {ZL=false;roleSB=pkMatch.getPkMan2();}
			else {ZL=true;roleSB=pkMatch.getPkMan1();}
			if (roleSB==null) {return;}
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(roleSB.getRoleName());
			LoginResult loginResult=null;
			if (ctx!=null) {loginResult=GameServer.getAllLoginRole().get(ctx);}
			String[] teams=null;
			if (loginResult==null) {teams=new String[]{roleSB.getRoleName()};}
			else{teams=loginResult.getTeam().split("\\|");}
			for (int i = 0; i < teams.length; i++) {
				LTSRole ltsRole=getRole(teams[i]);
				if (ltsRole==null) {continue;}
				if (lsnum<ltsRole.getLSnum()) {
					lsnum=ltsRole.getLSnum();
					   zj=ltsRole.getRole();
				}
				ltsRole.battle(false,ZL,arena.getlLvl(),0);
				if (PointRanking(ltsRole)) {is=true;}
			}
			if (pkMatch.getPkMan1().getRoleId().compareTo(id)!=0) {ZL=false;roleSB=pkMatch.getPkMan2();}
			else {ZL=true;roleSB=pkMatch.getPkMan1();}
			if (roleSB==null) {return;}
			ctx=GameServer.getRoleNameMap().get(roleSB.getRoleName());
			loginResult=null;
			if (ctx!=null) {loginResult=GameServer.getAllLoginRole().get(ctx);}
			teams=null;
			if (loginResult==null) {teams=new String[]{roleSB.getRoleName()};}
			else{teams=loginResult.getTeam().split("\\|");}
			for (int i = 0; i < teams.length; i++) {
				LTSRole ltsRole=getRole(teams[i]);
				if (ltsRole==null) {continue;}
				ltsRole.battle(true,ZL,arena.getlLvl(),0);
				if (PointRanking(ltsRole)) {is=true;}
				if (i==0) {
					lsnum2=ltsRole.getLSnum();
					zj2=ltsRole.getRole();
				}
			}
			if (lsnum>=3) {
				StringBuffer buffer=new StringBuffer();
				buffer.append("#G");
				buffer.append(zj2);
				buffer.append("#24威武霸气，前来应战，#Y最终大战三百回合#24，一锤定音，成功KO了#F");
				buffer.append(zj);
				buffer.append(",终结了他的");
				buffer.append(getWZ(lsnum));
				buffer.append(",此时大吼一声#24 还有谁#24");
				NChatBean bean=new NChatBean();
				bean.setId(lsnum>=8?4:5);
				bean.setMessage(buffer.toString());
				String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
				SendMessage.sendMessageToAllRoles(msg);	
			}else if (lsnum2>=3) {
				StringBuffer buffer=new StringBuffer();
				buffer.append("#R");
				buffer.append(getWZ(lsnum2));
				if (lsnum2<=4) {
					buffer.append("#Y横冲直装，犹如一批黑马,#G");
					buffer.append(zj2);
					buffer.append("#Y横空出世，犹如战神附体，#Y何人赶来应战，跪求一败！#132");
				}else if (lsnum2<=8) {
					buffer.append("#Y观音姐姐，唐僧哥哥，开发组在这一刻灵魂附体#G");
					buffer.append(zj2);
					buffer.append("#Y身上！！！神挡杀神佛挡杀佛，#24在此高吼一声，何人敢战！#132");
				}else {
					buffer.append("#Y王者霸气，已经无法遮挡#24#G");
					buffer.append(zj2);
					buffer.append("#Y给了全场在做得各位一个眼神#132似再吧再表示#G在座的所有人都是渣渣。#132");
				}
				NChatBean bean=new NChatBean();
				bean.setId(lsnum2>=8?4:5);
				bean.setMessage(buffer.toString());
				String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
				SendMessage.sendMessageToAllRoles(msg);	
			}
		}
		if (is) {//刷新排行榜
			SendMessage.sendMessageToMapRoles(mapID,Agreement.getAgreement().duelBoradDataAgreement(getRanking()));
		}
		if (pkMatch.getPkMan1().getRoleId().compareTo(id)==0) {
			pkMatch=PKPool.getPkPool().addPkMatch(pkMatch.getPkMan1(),null, pkMatch.getpKStake1(), 11);
			arena.setPkMatch(pkMatch);
		}
	}
	/***/
	public String getWZ(int ci){
		return ci+"连胜";
	}
	/**获取积分榜数据*/
	public String getRanking(){
		StringBuffer buffer=new StringBuffer();
		for (int i =0; i<top.size() ; i++) {
			if (buffer.length()!=0) {buffer.append("|");}
			LTSRole role=top.get(i);
			buffer.append(role.getRole());
			buffer.append("&");
			buffer.append(role.getHSnum());
			buffer.append("&");
			buffer.append(role.getJf());
		}
		return buffer.toString();
	}
	/**积分排行刷新*/
	public synchronized boolean PointRanking(LTSRole role){
		top.remove(role);
		boolean is=true;
		boolean is2=false;
		for (int i =0; i<top.size() ; i++) {
			LTSRole zzsRole=top.get(i);
			if (zzsRole.getJf()<role.getJf()) {
				is=false;
				top.add(i,role);
				is2=true;
				break;
			}
		}
		if (is) {
			if (top.size()<5) {
				top.add(role);	
				is2=true;
			}
		}else if (top.size()>5) {
			for (int i = top.size()-1;i>=5;i--) {
				top.remove(i);
				is2=true;
			}
		}
		return is2;//true刷新排行榜
	}
	/**获取擂台*/
	public LTSArena getArena(int id){
		for (int i = 0; i < arenas.length; i++) {
			if (arenas[i].getlId()==id) {
				return arenas[i];
			}
		}
		return null;
	}
	/**获取擂台*/
	public LTSArena getArena(PkMatch pkMatch){
		if (pkMatch==null) {return null;}
		for (int i = 0; i < arenas.length; i++) {
			if (arenas[i].getPkMatch()!=null) {
				if (arenas[i].getPkMatch()==pkMatch) {
					return  arenas[i];
				}
			}
		}
		return null;
	}
	/**判断是否是擂主*/
	public boolean isLZ(BigDecimal roleId){
		for (int i = 0; i < arenas.length; i++) {
			if (arenas[i].getPkMatch()!=null) {
				if (arenas[i].getPkMatch().getPkMan1().getRoleId().compareTo(roleId)==0) {
					return  true;
				}
			}
		}
		return false;
	}
	/**判断是否是擂主*/
	public LTSArena getLZ(BigDecimal roleId){
		for (int i = 0; i < arenas.length; i++) {
			if (arenas[i].getPkMatch()!=null) {
				if (arenas[i].getPkMatch().getPkMan1().getRoleId().compareTo(roleId)==0) {
					return  arenas[i];
				}
			}
		}
		return null;
	}
	@Override
	public String UPMonster(BattleData battleData, String[] teams, int type,StringBuffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		if (!isEnd()) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("活动还未结束无法领奖"));
			return;
		}
		LTSRole ltsRole=ltsMap.get(loginResult.getRolename());
		if (ltsRole==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你未参与本次获得"));
			return;
		}
		if (ltsRole.isAward()) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你已经领取过奖励了"));
			return;
		}
		ltsRole.setAward(true);
		boolean is=true;
		if (ltsRole.getZBnum()+ltsRole.getHSnum()>=3) {//获取参与奖
			Dorp dorp=GameServer.getDorp("2101");
			if (dorp!=null) {
				DropUtil.getDrop(loginResult,dorp.getDorpValue(),"擂台赛参与礼包", 22,1D,null);	
			}
			is=false;
		}
        if (ltsRole.getHSnum()>=5) {//获取5胜奖
        	Dorp dorp=GameServer.getDorp("2102");
            if (dorp!=null) {
            	DropUtil.getDrop(loginResult,dorp.getDorpValue(),"擂台赛五胜礼包", 22,1D,null);	
			}
            is=false;
		}
        if (top.contains(ltsRole)) {//获取擂台霸主奖
        	Dorp dorp=GameServer.getDorp("2103");
            if (dorp!=null) {
            	DropUtil.getDrop(loginResult,dorp.getDorpValue(),"擂台霸主礼包", 22,1D,null);	
			}
            is=false;
		}
        if (is) {
        	SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你未达到领取标准"));
			return;
		}
	}
	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		// TODO Auto-generated method stub
		return moveMap;
	}
	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return I!=1;
	}
	public void setI(int i) {
		I = i;
		// 发起世界喊话
		NChatBean bean=new NChatBean();
		bean.setId(4);
		bean.setMessage("#R本次擂台争霸赛已经结束");
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	@Override
	public boolean isTime(long time) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getSceneMsg(LoginResult loginResult, long oldMapId, long mapId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int battleEnd(BattleEnd battleEnd,LoginResult loginResult,MapMonsterBean bean,int v) {
		// TODO Auto-generated method stub
		return 0;
	}
}
