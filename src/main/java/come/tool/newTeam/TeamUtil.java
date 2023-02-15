package come.tool.newTeam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.monitor.TBean;
import org.come.protocol.Agreement;
import org.come.redis.RedisCacheUtil;
import org.come.until.GsonUtil;

public class TeamUtil {

	
	
	private static BigDecimal teamID;
	private static ConcurrentHashMap<BigDecimal,TeamBean> teamMap;
	private static ConcurrentHashMap<BigDecimal,BigDecimal> teamRoleMap;
	
	private static List<TeamBean> enlist;//招募队伍
	private static String enlistText;//招募信息JSON数据
	private static Object object;
	private static TBean<List<TeamBean>> t;
	static{
		teamID =new BigDecimal(0);
		teamMap=new ConcurrentHashMap<>();
		teamRoleMap=new ConcurrentHashMap<>();
		enlist =new ArrayList<>();
		object =new Object();
		enlistText=null;
		t=new TBean<List<TeamBean>>(enlist);
	}
	/**添加玩家id对应队伍*/
	public static void addTeamRole(BigDecimal roleId,BigDecimal teamId) {
		teamRoleMap.put(roleId, teamId);
	}
	/**删除玩家id对应队伍*/
    public static void removeTeamRole(BigDecimal roleId) {
    	teamRoleMap.remove(roleId);
	}
	/**根据玩家id获取队伍*/
	public static TeamBean getTeamRole(BigDecimal roleId) {
		BigDecimal teamID=teamRoleMap.get(roleId);
		if (teamID!=null) {
			return getTeam(teamID);
		}
		return null;
	}
	/**获取队伍信息*/
	public static TeamBean getTeam(BigDecimal teamID) {
		if (teamID==null) {
			return null;
		}
		return teamMap.get(teamID);
	}
	/**添加一个队伍*/
	public static TeamBean addTeam(TeamRole role){
		BigDecimal teamId=TeamUtil.getTeamID();
		TeamBean teamBean=new TeamBean(teamId, role);
		teamMap.put(teamBean.getTeamId(), teamBean);
		teamRoleMap.put(role.getRoleId(), teamId);
		return teamBean;
	} 
	/**删除一个队伍*/
    public static void removeTeam(TeamBean teamBean){
    	teamMap.remove(teamBean.getTeamId());
    	for (int i = 0,length = teamBean.getTeams().size(); i < length; i++) {
    		teamRoleMap.remove(teamBean.getTeams().get(i).getRoleId());
		}
    	removeEnlist(teamBean);
    } 
    /**招募队伍信息变更*/
    public static void upEnlist(TeamBean teamBean){
    	synchronized (object) {
    		if (teamBean.getTeamState()!=1) {
				return;
			}
    		if (teamBean.getTeams().size()>=5) {
    			removeEnlist(teamBean);
			}else {
				enlistText=null;
			}
    	}
    }
	/**添加招募队伍*/
	public static void addEnlist(TeamBean teamBean){
		synchronized (object) {
			if (!enlist.contains(teamBean)) {
				enlist.add(teamBean);
				teamBean.setTeamState(1);
			}
			enlistText=null;
		}
	}
	/**招募队伍移除*/
    public static void removeEnlist(TeamBean teamBean){
        synchronized (object) {
			if (enlist.remove(teamBean)) {
				teamBean.setTeamState(0);
				enlistText=null;
			}
		}
	}
    /**获取招募队伍信息*/
    public static String getEnlist(){
        synchronized (object) {
			if (enlistText!=null) {
				return enlistText;
			}
			enlistText=Agreement.getAgreement().enlistAgreement(GsonUtil.getGsonUtil().getgson().toJson(t));
			return enlistText;
        }
	}
    public static synchronized BigDecimal getTeamID() {
		teamID=teamID.add(RedisCacheUtil.ADD);
		return teamID;
	}
    /**获取对象信息*/
    public static String getTeamInfo(BigDecimal teamId,String OTeamInfo,String NTeamInfo){
    	StringBuffer buffer=new StringBuffer();
    	buffer.append(teamId);
    	buffer.append("#");
    	buffer.append((OTeamInfo!=null&&!OTeamInfo.equals(""))?OTeamInfo:"null");
    	buffer.append("#");
    	buffer.append((NTeamInfo!=null&&!NTeamInfo.equals(""))?NTeamInfo:"null");
    	return Agreement.getAgreement().team3Agreement(buffer.toString());
    }
}
