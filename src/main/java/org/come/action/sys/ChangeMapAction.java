package org.come.action.sys;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.come.action.IAction;
import org.come.bean.ChangeMapBean;
import org.come.bean.GetClientUserMesageBean;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterUtil;
import org.come.until.GsonUtil;

import come.tool.BangBattle.BangBattlePool;
import come.tool.BangBattle.BangFight;
import come.tool.Role.RoleShow;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.LTS.LTSScene;
import come.tool.Stall.StallPool;
/**传送地图，客户端发来地图ID，返回npc集合和地图所有角色*/
public class ChangeMapAction implements IAction{
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		ChangeMapBean changeMapBean = GsonUtil.getGsonUtil().getgson().fromJson(message, ChangeMapBean.class);
		ChangeMap(changeMapBean, ctx);
	}	
	/***/
	public static void ChangeMap(ChangeMapBean changeMapBean,ChannelHandlerContext ctx){
		// 获得角色信息
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo==null) {return;}		
		long oldMapId=roleInfo.getMapid();
		// 地图ID转格式
		long mapid = Long.parseLong(changeMapBean.getMapid());
		//要传送的角色
		String[] roles = roleInfo.getTeam().split("\\|");
        if (changeMapBean.getType()==4) {//判断对外是否都是同一帮派
        	BigDecimal gangid=roleInfo.getGang_id();
        	if (gangid.intValue()==0) {
        		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你没有帮派"));
        		return;
			}
			for (int i = 1; i < roles.length; i++) {
				ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(roles[i]);
				LoginResult changRole = ctx2!=null?GameServer.getAllLoginRole().get(ctx2):null;
				if (changRole!=null&&gangid.compareTo(changRole.getGang_id()) != 0){
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不能携带非本帮派成员进入"));
	        		return;
				}
			}
		}
		MapMonsterBean monsterBean=MonsterUtil.getFollowMonster(roles);
		if (monsterBean!=null&&changeMapBean.getType()==1) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("飞行棋使用限制"));
			return;
		}
		Map<String, ChannelHandlerContext> mapRoleMap =GameServer.getMapRolesMap().get(roleInfo.getMapid());
		boolean ismap=(mapid!=oldMapId);
		//判断是否到了新地图
		if (ismap) {
			// 清除地图里集合
			for (int i = 0; i < roles.length; i++){
				mapRoleMap.remove(roles[i]);
			}
			//退出地图
			SendMessage.sendMessageToMapRoles(roleInfo.getMapid(),Agreement.getAgreement().UserRetreatAgreement(roleInfo.getTeam()));
			if (monsterBean!=null) {
				SendMessage.sendMessageToMapRoles(roleInfo.getMapid(),Agreement.getAgreement().battleStateAgreement("M"+monsterBean.getI()+"^2"));
			}
		}
		// 储存修改后的传送角色信息
		List<RoleShow> roleShows = new ArrayList<RoleShow>();
		for (int i = 0; i < roles.length; i++) {
//			// 获得需要传送的角色信息
			ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(roles[i]);
			if (ctx2==null) {continue;}
			LoginResult changRole = GameServer.getAllLoginRole().get(ctx2);
			if (changRole!=null) {
				// 修改自己的地图ID
				changRole.setMapid(mapid);
				// 修改坐标
				changRole.setX(new Long(changeMapBean.getMapx()));
				changRole.setY(new Long(changeMapBean.getMapy()));
				changRole.getRoleShow().getPlayer_Paths().clear();
				roleShows.add(changRole.getRoleShow());	
			}
		}
		String mes2=null;
		if (monsterBean!=null) {
			monsterBean.setX(changeMapBean.getMapx());
			monsterBean.setY(changeMapBean.getMapy());	
			if (ismap) {
				MonsterUtil.getList(monsterBean.getMap(),monsterBean.getRobotid()).remove(monsterBean);
				monsterBean.setMap(mapid);
				MonsterUtil.getList(monsterBean.getMap(),monsterBean.getRobotid()).add(monsterBean);
				StringBuffer buffer = new StringBuffer();
				buffer.append(monsterBean.getRobotid());
				buffer.append("#");
				buffer.append(monsterBean.getRobotname());
				buffer.append("#");
				buffer.append(monsterBean.getRobotskin());
				buffer.append("#");
				buffer.append(monsterBean.getRobotType()); 
				buffer.append("#");
				buffer.append(monsterBean.getI());
				buffer.append("^");
				buffer.append(monsterBean.getX());
				buffer.append("^");
                buffer.append(monsterBean.getY());
                if (monsterBean.getFollow()!=null&&monsterBean.getFollow().getFollowID()!=null) {
			        buffer.append("^G");
			        buffer.append(monsterBean.getFollow().getFollowID());
				}
                mes2=Agreement.getAgreement().MonsterRefreshAgreement(buffer.toString());
			}
		}
		mapRoleMap =GameServer.getMapRolesMap().get(mapid);
		GetClientUserMesageBean getClientUserMesageBean = new GetClientUserMesageBean();
		getClientUserMesageBean.setRoleShows(roleShows);
		String mes = Agreement.getAgreement().intogameAgreement(GsonUtil.getGsonUtil().getgson().toJson(getClientUserMesageBean));
		Iterator<Map.Entry<String, ChannelHandlerContext>> entries =mapRoleMap.entrySet().iterator(); 
		//遍历需要的条件
		BigDecimal gang_id = null;
		if (mapid == 3000)gang_id=roleInfo.getGang_id();
		BangFight bangFight = null;
		if (mapid == 3315)bangFight=BangBattlePool.getBangBattlePool().getBangFight(roleInfo.getGang_id());
		while (entries.hasNext()) {
			Entry<String, ChannelHandlerContext> entrys = entries.next();
			ChannelHandlerContext value=entrys.getValue();
			LoginResult loginResult=GameServer.getAllLoginRole().get(value);
			if (value==null||loginResult==null) {continue;}
			if (gang_id!=null&&gang_id.compareTo(loginResult.getGang_id()) != 0){continue;}
			if (bangFight!=null&&bangFight.getMap(loginResult.getGang_id())==null){continue;}
			SendMessage.sendMessageToSlef(value,mes);	
			//添加新地图角色
			if (ismap){
				roleShows.add(loginResult.getRoleShow());	
				if (mes2!=null) {SendMessage.sendMessageToSlef(value,mes2);}
			}
		}
		//添加传送的人进入新map
		if (ismap) {
			boolean isScene=SceneUtil.isSceneMsg(oldMapId, mapid);
			// 新地图所有怪物
			getClientUserMesageBean.setIsmap(1);
			getClientUserMesageBean.setMonster(MonsterUtil.getMapMonster(mapid,roleInfo.getGang_id()));		
			// 摆摊列表
			getClientUserMesageBean.setStallBeans(StallPool.getPool().getmap(roleInfo.getMapid().intValue()));
			if (!isScene) {
				mes = Agreement.getAgreement().intogameAgreement(GsonUtil.getGsonUtil().getgson().toJson(getClientUserMesageBean));
			}
			for (int i = 0; i < roles.length; i++){
				ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(roles[i]);
				if (ctx2==null) {continue;}
				mapRoleMap.put(roles[i], ctx2);
				if (isScene) {
					LoginResult login = GameServer.getAllLoginRole().get(ctx2);
					if (login!=null) {
						getClientUserMesageBean.setSceneMsg(SceneUtil.getSceneMsg(login,oldMapId,mapid));
					}else {
						getClientUserMesageBean.setSceneMsg(null);
					}
					mes = Agreement.getAgreement().intogameAgreement(GsonUtil.getGsonUtil().getgson().toJson(getClientUserMesageBean));
				}
				SendMessage.sendMessageToSlef(ctx2,mes);
			}	
			if (mapid==3333) {
				Scene scene=SceneUtil.getScene(SceneUtil.LTSID);
				if (scene!=null) {
                    LTSScene ltsScene=(LTSScene)scene;
                    mes=Agreement.getAgreement().duelBoradDataAgreement(ltsScene.getRanking());
                    for (int i = 0; i < roles.length; i++) {
                    	SendMessage.sendMessageByRoleName(roles[i],mes);
					}
				}
			}
		}
	}
}
