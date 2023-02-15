package come.tool.newTeam;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import come.tool.BangBattle.BangBattlePool;
import come.tool.BangBattle.BangFight;
import come.tool.BangBattle.Member;
import come.tool.PK.PKPool;
import come.tool.PK.PkMatch;
import come.tool.Role.RolePool;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.DNTG.DNTGRole;
import come.tool.Scene.DNTG.DNTGScene;
import come.tool.Scene.LTS.LTSArena;
import come.tool.Scene.LTS.LTSScene;
/**队伍操作*/
public class TeamOperateAction implements IAction{
//	A同意加队 R拒绝加队 E清空加队
//	K踢出队伍 S设为队长 D解散队伍 
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		TeamBean bean=TeamUtil.getTeam(loginResult.getTroop_id());
		if (bean==null) {
			return;
		}
		boolean is=bean.isCaptian(loginResult.getRole_id());
		if (is) {
			if (message.startsWith("A")) {//同意加队
				addTeam(bean, loginResult,new BigDecimal(message.substring(1)),ctx);
			}else if (message.startsWith("R")) {//拒绝加队
				bean.getApply(new BigDecimal(message.substring(1)));
			}else if (message.startsWith("E")) {//清空加队
				bean.removeApply();
			}else if (message.startsWith("K")) {//踢出队伍
				kickOutTeam(bean, new BigDecimal(message.substring(1)));
			}else if (message.startsWith("S")) {//设为队长
				setCaptainTeam(bean, loginResult,new BigDecimal(message.substring(1)),ctx);
			}else if (message.startsWith("D")) {//解散队伍
				bean.dismissTeam();
			}	
		}else {
			if (message.startsWith("D")) {//离开队伍
				bean.removeTeamRole(loginResult.getRole_id());
			}
		}
	}

	/**同意加队*/
	public void addTeam(TeamBean bean,LoginResult loginResult,BigDecimal applyId,ChannelHandlerContext ctx){
		if (bean.getTeamSize()>=5) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("队伍已满"));
			return;
		}
		TeamRole teamRole=bean.getApply(applyId);
		if (teamRole==null) {return;}
		LoginResult login=RolePool.getLoginResult(teamRole.getRoleId());
		if (login==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("玩家已经离线"));
			return;
		}
		TeamBean teamBean=TeamUtil.getTeam(login.getTroop_id());
		if (teamBean!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("玩家已经有队伍了"));
			return;
		}
		if (login.getMapid() == 3315) {
			BangFight bangFight = BangBattlePool.getBangBattlePool().getBangFight(login.getGang_id());
			if (bangFight != null) {
				Member member = bangFight.getrole(login.getRolename());
				if (member != null) {
					if (member.getState() != 0) {
						SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("申请人处于忙碌状态"));
						return;
					}
					Member member2 = bangFight.getrole(loginResult.getRolename());
					if (member2 != null) {
						if (member2.getCamp().compareTo(member.getCamp()) != 0) {
							SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("申请人和你不是同个阵营"));
							return;
						}
					}
				}
			}
		} else if (login.getMapid() == DNTGScene.mapIdF || login.getMapid() == DNTGScene.mapIdZ
				||loginResult.getMapid() == DNTGScene.mapIdF || loginResult.getMapid() == DNTGScene.mapIdZ) {
			Scene scene = SceneUtil.getScene(SceneUtil.DNTGID);
			if (scene != null) {
				DNTGScene dntgScene = (DNTGScene) scene;
				DNTGRole role1 = dntgScene.getRole(loginResult.getRole_id());
				DNTGRole role2 = dntgScene.getRole(login.getRole_id());
				if (role1 == null || role2 == null || role1.getCamp() != role2.getCamp()) {
					SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("申请人和你不是同个阵营"));
					return;
				}
			}
		} else if ((loginResult.getMapid() >= 3329 && loginResult.getMapid() <= 3332)||(login.getMapid() >= 3329 && login.getMapid() <= 3332)) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("禁止组队"));
			return;
		}
		PkMatch match = PKPool.getPkPool().seekPkMatch(login.getRole_id());
		if (match != null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("申请人是携带战书没法加入队伍"));
			return;
		}
		match = PKPool.getPkPool().seekPkMatch(loginResult.getRole_id());
		if (match != null && match.getType() == 11) {
			Scene scene = SceneUtil.getScene(SceneUtil.LTSID);
			if (scene != null) {
				LTSScene ltsScene = (LTSScene) scene;
				LTSArena arena = ltsScene.getLZ(loginResult.getRole_id());
				if (arena != null) {
					if (login.getGrade() > arena.getMaxLvl()) {
						SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("申请人超过当前擂台等级限制"));
						return;
					}
				}
			}
		}
		
		teamRole=login.getTeamRole();
		bean.addTeamRole(teamRole,loginResult.getMapid().longValue()==login.getMapid().longValue()?0:-1);
	} 
	/**踢出队伍*/
	public void kickOutTeam(TeamBean bean,BigDecimal applyId){
		bean.removeTeamRole(applyId);
	}
	/**设为队长*/
	public void setCaptainTeam(TeamBean bean,LoginResult loginResult,BigDecimal applyId,ChannelHandlerContext ctx){
		LoginResult login=RolePool.getLoginResult(applyId);
		if (login==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("玩家已经离线"));
			return;
		}
		if (loginResult.getMapid()==3315) {
			BangFight bangFight = BangBattlePool.getBangBattlePool().getBangFight(loginResult.getGang_id());
			if (bangFight!=null) {
				Member member=bangFight.getrole(loginResult.getRolename());
				if (member!=null) {
					if (member.getState()!=0) {
						SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("当前状态不能换队长"));	
						return;
					}
				}
			}	
		}
		PkMatch match=PKPool.getPkPool().seekPkMatch(loginResult.getRole_id());
		if (match!=null) {
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("你身上携带战书必须当带头的"));	
			return;
		}
		
		String msg=bean.setCaptain(login);
		if (msg!=null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement(msg));
		}
	} 
}
