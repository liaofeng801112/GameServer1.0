package come.tool.newTeam;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.newTask.TaskRecord;
import come.tool.teamArena.TeamArenaMatch;
import come.tool.teamArena.TeamArenaUtil;

public class TeamBean {

	//队伍id
	private BigDecimal teamId;
	//队伍内的玩家
	private List<TeamRole> teams;//队伍列表
	private transient List<TeamRole> applyTeams;//申请列表
	private String eTeam;//招募队伍类型
	private String eTask;//招募活动类型	
	private String eMsg;//招募补充
	private transient int teamState;//0:默认  1:在招募列表内
	//已跟随的队伍
	private transient String teamInfo;
	private transient Object object;//锁
	private transient long[] mapIds;

	private transient TeamArenaMatch teamArenaMatch;
	
	public TeamBean(BigDecimal teamId,TeamRole role) {
		// TODO Auto-generated constructor stub
		this.teamId=teamId;
		role.setState(1);//设置状态
		this.teams=new ArrayList<>();
		this.applyTeams=new ArrayList<>();
		this.teams.add(role);
		this.object=new Object();
		this.mapIds=new long[5];
		this.teamInfo=role.getName();
	}
    /**加入申请列表*/
	public boolean applyTeam(TeamRole role){
		synchronized (object) {
			for (int i = 0,length = applyTeams.size(); i < length; i++) {
				TeamRole applyRole=applyTeams.get(i);
				if (role.getRoleId().compareTo(applyRole.getRoleId())==0) {
					applyTeams.set(i, role);
					return false;
				}
			}
			applyTeams.add(role);
			return true;
		}
	}
	/**删除式获取申请者信息*/
	public TeamRole getApply(BigDecimal roleID){
		synchronized (object) {
			for (int i = 0,length = applyTeams.size(); i < length; i++) {
				TeamRole applyRole=applyTeams.get(i);
				if (applyRole.getRoleId().compareTo(roleID)==0) {
					return applyTeams.remove(i);
				}
			}
			return null;
		}
	}
	/**清空申请列表*/
    public void removeApply(){
    	synchronized (object) {
    		applyTeams.clear();
    	}
	}
	/**更改招募信息*/
	public void upEnlist(TeamBean bean){
		eTeam=bean.eTeam;
		eTask=bean.eTask;
		eMsg =bean.eMsg;
	}
	/**获取玩家*/
	public TeamRole getTeamRole(BigDecimal roleID){
		synchronized (object) {
			for (int i = 0,length = teams.size(); i < length; i++) {
				TeamRole role=teams.get(i);
				if (role.getRoleId().compareTo(roleID)==0) {
					return role;
				}
			}
			return null;
		}
	}
	/**添加队友*/
	public void addTeamRole(TeamRole role,int type){
		synchronized (object) {
			TeamUtil.addTeamRole(role.getRoleId(), teamId);
			upteamInfo(role,type,1);
			TeamUtil.upEnlist(this);
			SendMessage.sendMessageByRoleName(role.getName(), Agreement.getAgreement().team1Agreement(GsonUtil.getGsonUtil().getgson().toJson(this)));
		}
	}
	/**移除队友*/
	public boolean removeTeamRole(BigDecimal roleID){
		synchronized (object) {
			TeamRole role=null;
			for (int i = 1,length = teams.size(); i < length; i++) {
				TeamRole teamRole=teams.get(i);
				if (teamRole.getRoleId().compareTo(roleID)==0) {
					role=teams.get(i);
					break;
				}
			}
			if (role!=null) {
				upteamInfo(role,-3,2);	
				TeamUtil.removeTeamRole(roleID);
				TeamUtil.upEnlist(this);
			}
			return role!=null;
		}
	}
	/**设立队长*/
	public String setCaptain(LoginResult loginResult){
		synchronized (object) {
			TeamRole role=null;
			TeamRole captain=teams.get(0);
			int index=-1;
			for (int i = 1,length = teams.size(); i < length; i++) {
				TeamRole teamRole=teams.get(i);
				if (teamRole.getRoleId().compareTo(loginResult.getRole_id())==0) {
					index=i;
					role=teams.get(i);
					break;
				}
			}
			if (role==null) {return "该队员状态异常";}
			if (role.getState()!=0) {return "该队员处于离队状态";}
			role.setState(1);
			captain.setState(0);
			teams.set(0,role);
			teams.set(index,captain);
			upteamInfo(null,0,0);
			upTeamRoleState(role);
			TeamUtil.upEnlist(this);
			return "队长设立成功";
		}
	}
	/**解散队伍*/
	public void dismissTeam(){
		synchronized (object) {
			upteamInfo(null,0,3);
			TeamUtil.removeTeam(this);
		}		
	}
    /**玩家离队  -1正常离队  -2离线离队*/
	public void stateLeave(TeamRole role,int type){
		synchronized (object) {
			upteamInfo(role,type,2);
			if (teamArenaMatch!=null&&teamArenaMatch.getState()<=2) {//退出匹配
				TeamArenaUtil.quitTeamArena(this,role);
			}
		}
	}
	/**玩家归队*/
	public void stateCome(TeamRole role){
		synchronized (object) {
			TeamRole captain=teams.get(0);
			upteamInfo(role,captain.getRoleId().compareTo(role.getRoleId())==0?1:0,0);
		}
	}
	/**更新队员的状态*/
	public void upTeamRoleState(TeamRole role){
		String msg=Agreement.getAgreement().team4Agreement(GsonUtil.getGsonUtil().getgson().toJson(role));
		for (int i = 0,length = teams.size(); i < length; i++) {
			SendMessage.sendMessageByRoleName(teams.get(i).getName(), msg);
		}
	} 
	/**type 0是正常修改  1是添加  2是离队 3是解散*/
	public void upteamInfo(TeamRole role,int state,int type){
		StringBuffer buffer=new StringBuffer();
		StringBuffer bufferState=null;
		int ostate=0;
		if (role!=null) {
			role.setState(state);
		}
		for (int i = 0,length = teams.size(); i < length; i++) {
			TeamRole teamRole=teams.get(i);
			if (type==3) {
				if (bufferState==null) {bufferState=new StringBuffer();}
				bufferState.append("#");
				bufferState.append(teamRole.getName());
				bufferState.append("&");
				bufferState.append(-3);
				teamRole.setState(-3);
			}else if (i==0||teamRole.getState()>=0) {
				if (buffer.length()!=0) {buffer.append("|");}	
				buffer.append(teamRole.getName());			
			}else {
				if (bufferState==null) {bufferState=new StringBuffer();}
				bufferState.append("#");
				bufferState.append(teamRole.getName());
				bufferState.append("&");
				bufferState.append(teamRole.getState());
			}
		}
		if (type==1) {
			if (role.getState()>=0) {
				if (buffer.length()!=0) {buffer.append("|");}	
				buffer.append(role.getName());		
			}else {
				if (bufferState==null) {bufferState=new StringBuffer();}
				bufferState.append("#");
				bufferState.append(role.getName());
				bufferState.append("&");
				bufferState.append(role.getState());
			}
		}
		teamInfo=buffer.length()!=0?buffer.toString():null;
		buffer.setLength(0);
		buffer.append(teamId);
		buffer.append("#");
		buffer.append(teamInfo);
		if (bufferState!=null) {
			buffer.append(bufferState.toString());			
		}
		String msg=Agreement.getAgreement().team3Agreement(buffer.toString());
		mapIds[0]=0;mapIds[1]=0;mapIds[2]=0;mapIds[3]=0;mapIds[4]=0;
		for (int i = 0,length = teams.size(); i < length; i++) {
			TeamRole teamRole=teams.get(i);
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teamRole.getName());
			LoginResult login=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (login!=null) {
				login.setTeamInfo(teamRole.getState()>=0?teamInfo:null);
				if (teamRole.getState()==-3) {login.setTroop_id(null);}	
				if ((type==0||type==1)&&teamRole.getState()<0) {
					continue;
				}else if (type==2) {
					if (state==-3) {
						if (role!=teamRole) {
							if (ostate<0) {
								continue;
							}else if (teamRole.getState()<0) {
								continue;
							}
						}
					}else if (ostate==1) {
						continue;
					}
				}
				for (int j = 0; j < mapIds.length; j++) {
					if (mapIds[j]==login.getMapid()) {break;}
					else if (mapIds[j]==0) {mapIds[j]=login.getMapid();break;}
				}
			}
		}
		if (type==1) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role.getName());
			LoginResult login=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (login!=null) {
				login.setTeamInfo(role.getState()>=0?teamInfo:null);
				login.setTroop_id(teamId);
				for (int j = 0; j < mapIds.length; j++) {
					if (mapIds[j]==login.getMapid()) {break;}
					else if (mapIds[j]==0) {mapIds[j]=login.getMapid();break;}
				}
			}
		}
		for (int i = 0; i < mapIds.length; i++) {
			if (mapIds[i]!=0) {
				SendMessage.sendMessageToMapRoles(mapIds[i],msg);
			}
		}
		if (role!=null) {
			upTeamRoleState(role);
		}
		if (type==2&&state==-3) {
			teams.remove(role);
		}else if (type==1) {
			teams.add(role);
		}
	}
	/**队伍内 群发数据*/
	public void sendTeam(String msg){
		for (int i = 0,length = teams.size(); i < length; i++) {
			SendMessage.sendMessageByRoleName(teams.get(i).getName(), msg);
		}
	}
	/**队伍内 群发数据*/
	public void addTaskAndSendTeam(int taskSetID,String msg){
		for (int i = 0,length = teams.size(); i < length; i++) {
			TeamRole teamRole=teams.get(i);
			RoleData roleData=RolePool.getRoleData(teamRole.getRoleId());
			if (roleData!=null) {
				TaskRecord taskRecord=roleData.getTaskRecord(taskSetID);
				if (taskRecord==null) {
					taskRecord=new TaskRecord(5);
					roleData.addTaskRecord(taskRecord);
				}
				taskRecord.addCSum(1);
			}
			SendMessage.sendMessageByRoleName(teamRole.getName(), msg);
		}		
	}
	public int getTeamState() {
		return teamState;
	}
	public void setTeamState(int teamState) {
		this.teamState = teamState;
	}
	public boolean isCaptian(BigDecimal roleID){
		synchronized (object) {
			return teams.get(0).getRoleId().compareTo(roleID)==0;
		}
	}
	public BigDecimal getTeamId() {
		return teamId;
	}
	public String getTeamInfo() {
		return teamInfo;
	}
	public void setTeamInfo(String teamInfo) {
		this.teamInfo = teamInfo;
	}
	public List<TeamRole> getTeams() {
		return teams;
	}
	public List<TeamRole> getApplyTeams() {
		return applyTeams;
	}
	/**队伍人数*/
	public int getTeamSize() {
		return teams.size();
	}
	/**获取队伍名称*/
	public String getTeamName() {
		return teams.get(0).getName();
	}
	public TeamArenaMatch getTeamArenaMatch() {
		return teamArenaMatch;
	}
	public void setTeamArenaMatch(TeamArenaMatch teamArenaMatch) {
		this.teamArenaMatch = teamArenaMatch;
	}
	
}
