package come.tool.Scene.ZZS;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.IAction;
import org.come.action.gang.GangBattleAction;
import org.come.bean.ChangeMapBean;
import org.come.bean.LoginResult;
import org.come.bean.NChatBean;
import org.come.handler.SendMessage;
import org.come.model.Dorp;
import org.come.protocol.Agreement;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.Battle.FightingForesee;
import come.tool.Good.DropUtil;
import come.tool.Scene.Scene;

public class ZZSScene implements Scene{
    //   1进场时间   2匹配赛   3中场休息5分钟  4淘汰赛  5结束 
	private int I;
	// 0人 1魔 2仙 3鬼
	public int type;
	private ConcurrentHashMap<String,ZZSRole> zzsMap;
	private List<ZZSRole> top;//排名前10
	private long mapID;
	private String TP;
	private ZZSThread zzsThread;
	public ZZSScene(int type) {
		// TODO Auto-generated constructor stub
		this.type=type;
		mapID=3329+type;
		ChangeMapBean change=new ChangeMapBean();
    	change.setMapid(mapID+"");
    	change.setMapx(1200);
    	change.setMapy(900);
    	TP=GsonUtil.getGsonUtil().getgson().toJson(change);
    	
		init();
	}
	public void init(){
		I=1;
		top=new ArrayList<>();
		matchs=new ArrayList<>();
		zzsMap=new ConcurrentHashMap<>();
		zzsThread = new ZZSThread(this);
		Thread T1 = new Thread(zzsThread);
		T1.start();	
	}
	/**修改状态*/
	public void upI(int ST){
		if (I>=ST) {return;}
		I=ST;
		if (I==2) {
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("#R种族赛正式开始,准备好后点NPC进行匹配");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToMapRoles(mapID,msg);
		}else if (I==3) {
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("#R种族赛中场休息5分钟后进入淘汰赛,排名前10的玩家获得淘汰赛资格,其他玩家请离场");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToMapRoles(mapID,msg);
		}else if (I==4) {
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("#R种族赛进入淘汰赛,玩家自行PK,战败2场将被踢出地图,剩下最后一个就是获胜者");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToMapRoles(mapID,msg);
			clearRole();
		}else if (I==5) {
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage("#R种族赛结束 ");
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToMapRoles(mapID,msg);
		}
	}
	private List<ZZSRole> matchs;
	/**匹配*/
	public void match(){
		matchs.clear();
		boolean is=true;
		for (ZZSRole value : zzsMap.values()) { 
			if (isMatch(value)) {
				is=true;
				for (int i =0; i<matchs.size() ; i++) {
					ZZSRole role=matchs.get(i);
					if (role.getJf()>value.getJf()) {
						is=false;
						matchs.add(i,value);
						break;
					}
				}
				if (is) {
					matchs.add(value);	
				}
			}
		}
//		小到大排序
		int size=matchs.size();
		for (int i =0; i<size;) {
			ZZSRole role=matchs.get(i);
			i++;
			if (i>=size) {break;}
			ZZSRole role2=matchs.get(i);
			if (Math.abs(role2.getJf()-role.getJf())>60) {continue;}
			i++;
			role.setI(0);role2.setI(0);
			BattleInto(role,role2);
		}
	}
	/**检测玩家状态是否匹配*/
	public boolean isMatch(ZZSRole value){
		if (value.getI()!=1) {return false;}
		ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(value.getRole());
		if (ctx==null) {value.setI(0);return false;}
		LoginResult log=GameServer.getAllLoginRole().get(ctx);
		if (log==null) {value.setI(0);return false;}
		if (log.getMapid()!=mapID) {value.setI(0);return false;}
		if (log.getFighting()!=0) {value.setI(0);return false;}
		return true;	
	}
	/**检测玩家状态是否匹配*/
	public boolean isEnd(ZZSRole value){
		if (value.getI()!=3) {return false;}
		ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(value.getRole());
		if (ctx==null) {return false;}
		LoginResult log=GameServer.getAllLoginRole().get(ctx);
		if (log==null) {return false;}
		if (log.getMapid()!=mapID) {return false;}
		return true;	
	}
	/**2个玩家进入战斗*/
	public void BattleInto(ZZSRole m1,ZZSRole m2){
		FightingForesee foresee=new FightingForesee();
		foresee.setYidui(m1.getRole());
		foresee.setErdui(m2.getRole());
		foresee.setType(31);
		BattleThreadPool.addBattle(GameServer.getRoleNameMap().get(m1.getRole()), foresee);
	}
	/**战斗结束 前面是胜者 后面是败者*/
	public void BattleEnd(String m1,String m2,int type){
		if (m1!=null) {
			ZZSRole role1=zzsMap.get(m1);
			if (role1!=null) {
				if (role1.Battle(true,type)) {
					PointRanking(role1);
				}	
			}
		}
		if (m2!=null) {
			ZZSRole role2=zzsMap.get(m2);
			if (role2!=null) {
				if (role2.Battle(false,type)) {
					PointRanking(role2);
				}
				if (type==32&&role2.getZBnum()>=2) {//踢回长安
					IAction action=ParamTool.ACTION_MAP.get("changemap");
					ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role2.getRole());
					if (ctx!=null) {
					 	action.action(ctx,GangBattleAction.OUTCA);		
					}
				}
			}
		}
	}
	/**积分排行刷新*/
	public synchronized void PointRanking(ZZSRole role){
		top.remove(role);
		boolean is=true;
		for (int i =0; i<top.size() ; i++) {
			ZZSRole zzsRole=top.get(i);
			if (zzsRole.getJf()<role.getJf()) {
				is=false;
				top.add(i,role);
				break;
			}
		}
		if (is) {
			if (top.size()<10) {
				top.add(role);		
			}
		}else if (top.size()>10) {
			for (int i = top.size()-1; i >10; i--) {
				top.remove(i);
			}
		}
	}
	/**报名进场的玩家*/
	public void addRole(ChannelHandlerContext ctx,LoginResult loginResult){
		if (zzsMap.get(loginResult.getRolename())==null) {
			ZZSRole role=new ZZSRole(loginResult.getRole_id(),loginResult.getRolename());
			zzsMap.put(role.getRole(), role);	
			if (top.size()<10) {
				PointRanking(role);
			}
		}
		//进场传送
		IAction action=ParamTool.ACTION_MAP.get("changemap");
		action.action(ctx,TP);
	}
	/**获取玩家*/
	public ZZSRole getRole(LoginResult loginResult){
		return zzsMap.get(loginResult.getRolename());
	}
	@Override
	public String UPMonster(BattleData battleData, String[] teams, int type,StringBuffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		ZZSRole zzsRole=getRole(loginResult);
		if (zzsRole==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你没参加种族赛"));
		    return;
		}
		List<ZZSAward> awards=zzsRole.getAwards();
		if (awards==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("未达到奖励要求"));
		    return;
		}
		int size=0;
		for (int i = 0; i < awards.size(); i++) {
			ZZSAward award=awards.get(i);
			if (!award.isAward()) {
				size++;
				award.setAward(true);
				Dorp dorp=Award(award);
				if (dorp!=null) {
					if (award.getType()==1) {
						DropUtil.getDrop(loginResult,dorp.getDorpValue(),"种族赛参与礼包", 22,1D,null);						
					}else if (award.getType()==2) {
						DropUtil.getDrop(loginResult,dorp.getDorpValue(),"种族赛5场胜利礼包", 22,1D,null);	
					}else if (award.getType()==3) {
						DropUtil.getDrop(loginResult,dorp.getDorpValue(),"种族赛3连胜礼包", 22,1D,null);	
					}else if (award.getType()==4) {
						DropUtil.getDrop(loginResult,dorp.getDorpValue(),"种族赛10强礼包", 22,1D,null);	
					}else if (award.getType()==5) {
						DropUtil.getDrop(loginResult,dorp.getDorpValue(),"种族赛冠军礼包", 22,1D,null);	
					}
				}
			}
		}
		if (size==0) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你的奖励都领取完了"));
		}
	}
	/**根据档次获得物品奖励*/
	public Dorp Award(ZZSAward award){
//		400-404
		Dorp dorp=GameServer.getDorp((award.getType()+399)+"");
		if (dorp==null) {
			return null;
		}
		return dorp;
	}
	/**获取排名信息*/
	public String getRanking(ZZSRole value){
		StringBuffer buffer=new StringBuffer();
		if (I==2) {
			buffer.append("处于匹配赛阶段  ");
		}else {
			buffer.append("不处于匹配赛阶段  ");
		}
		buffer.append("当前积分排名 ");
		int v=-1;
		for (int i = 0; i < top.size(); i++) {
			ZZSRole role=top.get(i);
			if (role.getId().compareTo(value.getId())==0) {
				v=i;
			}
			if (i==0) {
				buffer.append("第一:");
				buffer.append(role.getRole());
				buffer.append(" 积分:");
				buffer.append(role.getJf());
			}else if (i==1) {
				buffer.append("   第二:");
				buffer.append(role.getRole());
				buffer.append(" 积分:");
				buffer.append(role.getJf());
			}else if (i==2) {
				buffer.append("   第三:");
				buffer.append(role.getRole());
				buffer.append(" 积分:");
				buffer.append(role.getJf());
			}
		}
		buffer.append("   你当前积分为:");
		buffer.append(value.getJf());
		buffer.append(" 目前排名:");
		if (v==-1) {
			buffer.append("未上榜");
		}else {
			buffer.append(v+1);
		}
		return buffer.toString();
		
	}
	/**淘汰赛清场*/
	public void clearRole(){
		IAction action=ParamTool.ACTION_MAP.get("changemap");
		for (ZZSRole value : zzsMap.values()) { 
			if (!top.contains(value)) {
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(value.getRole());
				if (ctx!=null) {
					LoginResult log=GameServer.getAllLoginRole().get(ctx);
					if (log!=null&&log.getMapid()==mapID) {
						action.action(ctx,GangBattleAction.OUTCA);			
					}	
				}
			}else {
				value.setI(3);
				value.addAward(4);
			}
		}
	}
	/**淘汰赛判断*/
	public void isTT(){//判断当前地图是否只剩一个人
		matchs.clear();
		for (ZZSRole value : zzsMap.values()) { 
			if (isEnd(value)) {
				matchs.add(value);
				if (matchs.size()>=2) {break;}
			}
		}
		if (matchs.size()<=1) {
			if (matchs.size()==1) {
				ZZSRole role=matchs.get(0);
				role.addAward(5);
				NChatBean bean=new NChatBean();
				bean.setId(5);
				StringBuffer buffer=new StringBuffer();
				buffer.append("#Y玩家#G");
				buffer.append(role.getRole());
				buffer.append("#R获得种族赛-");
				buffer.append(type==0?"人":type==1?"魔":type==2?"仙":"鬼");
				buffer.append("的冠军");
				bean.setMessage(buffer.toString());
				String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
				SendMessage.sendMessageToAllRoles(msg);
			}	
			upI(5);
		}
	}
	@Override
	public boolean isEnd(){
		if (I==5) {return false;}
		return true;
	}
	public int getI() {
		return I;
	}
	public int getType() {
		return type;
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
	public int battleEnd(come.tool.Battle.BattleEnd battleEnd, LoginResult loginResult, MapMonsterBean bean, int v) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		// TODO Auto-generated method stub
		return moveMap;
	}
}
