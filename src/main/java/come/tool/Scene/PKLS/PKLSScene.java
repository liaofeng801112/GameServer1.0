package come.tool.Scene.PKLS;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;
import org.come.tool.ReadExelTool;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;
import come.tool.Scene.Scene;

public class PKLSScene implements Scene{
	//玩家报名数据
	private List<LSTeam> LSTeams;
	public PKLSScene() {
		// TODO Auto-generated constructor stub
		LSTeams=null;
		String text = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "lsteam.txt");
		if (text==null||text.equals("")) {LSTeams=new ArrayList<>();}
		else {
			lsteamBean lsteamBean=GsonUtil.getGsonUtil().getgson().fromJson(text,lsteamBean.class);
			LSTeams=lsteamBean.getLSTeams();
			if (LSTeams==null) {LSTeams=new ArrayList<>();}
		}
	}
	/**报名*/
	public synchronized String addEnroll(LoginResult loginResult){
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return "你不是队长";}
		if (teams.length!=5) {return "必须队伍满5人才能报名";}
		BigDecimal[] roleids=new BigDecimal[teams.length];
		for (int i = 0; i < teams.length; i++) {
			LoginResult login=null;
			if (i==0) {
				login=loginResult;
			}else {
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
				if (ctx!=null) {login=GameServer.getAllLoginRole().get(ctx);}
			}
			if (login==null) {return teams[i]+"处于异常状态";}
			if (login.getGrade()<439) {return teams[i]+"未满3转160";}
		    if (contains(login.getRole_id())) {return teams[i]+"已经报名过了";}
		    roleids[i]=login.getRole_id();
		}
		LSTeams.add(new LSTeam(roleids));
		return "报名成功";		
	}
	
	/***/
	public boolean contains(BigDecimal id){
		for (int i = LSTeams.size()-1; i>=0;i--) {
			if (LSTeams.get(i).contains(id)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String UPMonster(BattleData battleData, String[] teams, int type,StringBuffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId) {
		// TODO Auto-generated method stub
		return moveMap;
	}

	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTime(long time) {
		// TODO Auto-generated method stub
		return false;
	}
	public List<LSTeam> getLSTeams() {
		return LSTeams;
	}
	public void setLSTeams(List<LSTeam> lSTeams) {
		LSTeams = lSTeams;
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
