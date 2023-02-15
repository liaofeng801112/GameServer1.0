package come.tool.Scene.RC;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.LoginResult;
import org.come.model.Robots;
import org.come.model.TaskData;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;
import org.come.tool.ReadExelTool;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;
import come.tool.Battle.BattleThreadPool;
import come.tool.Battle.RewardLimit;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Scene.Scene;

public class RCScene implements Scene{

	private ConcurrentHashMap<String,RCRole> rcMap;
	
	private TaskData task;//前置任务
	private BBRecord bbRecord;
	public RCScene() {
		// TODO Auto-generated constructor stub
		rcMap=new ConcurrentHashMap<>();
		task=GameServer.getTaskName("混沌幻境");
		bbRecord=null;
		String text = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "bbRecord.txt");
		if (text==null||text.equals("")) {bbRecord=new BBRecord();}
		else {bbRecord=GsonUtil.getGsonUtil().getgson().fromJson(text,BBRecord.class);}
		bbRecord.setBBrobots(GameServer.getAllRobot().get("905"));
		bbRecord.setBBboos(GameServer.boosesMap.get("290"));
	}
	/**获取玩家*/
	public RCRole getRole(LoginResult login){
		RCRole rcRole=rcMap.get(login.getRolename());
		if (rcRole==null) {
			rcRole=new RCRole(login.getRole_id(),login.getRolename());
			rcMap.put(rcRole.getRole(),rcRole);	
		}
		return rcRole;
	}
	/**获取玩家*/
	public RCRole getRole(String role){
		return rcMap.get(role);
	}
	/**添加九生九死挑战队伍*/
	public String addNo1(LoginResult loginResult){
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return "你不是队长";}
		RCRole[] rcRoles=new RCRole[teams.length];
		for (int i = 0; i < teams.length; i++) {
			LoginResult login=null;
			if (i==0) {
				login=loginResult;
			}else {
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
				if (ctx!=null) {login=GameServer.getAllLoginRole().get(ctx);}
			}
			if (login==null) {return teams[i]+"处于异常状态";}
			RoleData roleData=RolePool.getRoleData(loginResult.getRole_id());
			if (roleData==null) {return teams[i]+"处于异常状态";}
			if (roleData.getTaskWC(task.getTaskSetID())<=0) {return teams[i]+"还未完成前置任务:混沌幻境";}
			RCRole role=getRole(login);
			if (role.getState()== 1) {return teams[i]+"处于挑战状态中";}
			if (role.getState()==-1||role.getAdvance()>0) {return teams[i]+"已经挑战过了";}	
			rcRoles[i]=role;
		}
		if (BattleThreadPool.RCFB(teams,getRobots(1),1)) {
			for (int i = 0; i < rcRoles.length; i++) {rcRoles[i].setState(1);}
		}		
		return null;		
	}
	/**添加宝宝副本挑战队伍*/
	public String addNo2(LoginResult loginResult){
		String[] teams=loginResult.getTeam().split("\\|");
		if (!teams[0].equals(loginResult.getRolename())) {return "你不是队长";}
		RCRole[] rcRoles=new RCRole[teams.length];
		for (int i = 0; i < teams.length; i++) {
			LoginResult login=null;
			if (i==0) {
				login=loginResult;
			}else {
				ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
				if (ctx!=null) {login=GameServer.getAllLoginRole().get(ctx);}
			}
			if (login==null) {return teams[i]+"处于异常状态";}
			RCRole role=getRole(login);
			if (role.isBB()) {return teams[i]+"已经挑战过了";}
			rcRoles[i]=role;
		}
		if (BattleThreadPool.BBFB(teams,bbRecord.getBBrobots(),0)) {
			for (int i = 0; i < rcRoles.length; i++) {rcRoles[i].setBB(true);}
		}		
		return null;		
	}
	/**宝宝副本npc描述*/
	public String getBBMsg(){
		return bbRecord.getMsg();
	}
	/**获取robot*/
	public Robots getRobots(int advance){
		if (advance<=3) {return GameServer.getAllRobot().get((894+advance)+"");}
		return GameServer.getAllRobot().get("301");	
	}
	@Override
	public String UPMonster(BattleData battleData, String[] teams, int type,StringBuffer buffer) {
		// TODO Auto-generated method stub
		if (battleData.getMonsterBean().getType() == 0) {
			JSJS(teams, type);
		} else if (battleData.getMonsterBean().getType() == 1) {
			BBFB(teams, type, battleData.getMonsterBean().getI());
		}
		return null;
	}
	/**BB副本*/
	public void BBFB(String[] names,int type,int i){
		if (type==2) {
			i+=1;
			bbRecord.UPRecord(i, names);
			BattleThreadPool.BBFB(names,bbRecord.getBBrobots(),i);
		}else if (i>0) {//发放奖励
			String JF=bbRecord.getReward(i);
			RewardLimit.rewardS(names,JF);	
		}
	}
	/**九生九死*/
	public void JSJS(String[] names,int type){
		boolean is=type==2;
		int advance=0;
		for (int i=0;i<names.length;i++) {
			RCRole rcRole=getRole(names[i]);
			if (rcRole==null) {continue;}
			if (type==2) {
				rcRole.setAdvance(rcRole.getAdvance()+1);
				if (rcRole.getAdvance()==9) {rcRole.setState(0);}//九生九死结束
				if (is) {
					if (i==0) {advance=rcRole.getAdvance();}
					else if (is&&advance!=rcRole.getAdvance()){is=false;}
				}
			}else {
				rcRole.setState(-1);
			}
		}
		if (is) {
			if (advance<=8) {//继续九生九死
				BattleThreadPool.RCFB(names,getRobots(advance+1),advance+1);
			}		
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
		return false;
	}

	@Override
	public boolean isTime(long time) {
		// TODO Auto-generated method stub
		return false;
	}
    /**清理*/
	public void clean(){rcMap.clear();}
	/**宝宝副本撒天女散花次数*/
	public BBBrush BBOpen(){
		BBBrush bbBrush=new BBBrush();
		int size=bbRecord.getBBNum()/1000;
		size++;
		bbBrush.setZ(size);
		bbBrush.setBoos(bbRecord.getBBboos());
		bbRecord.setBBNum(0);
		bbRecord.setSize(0);
		bbRecord.setTeams(null);
		return bbBrush;
	}
	public BBRecord getBbRecord() {
		return bbRecord;
	}
	public void setBbRecord(BBRecord bbRecord) {
		this.bbRecord = bbRecord;
	}
	@Override
	public String getSceneMsg(LoginResult loginResult, long oldMapId, long mapId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void getAward(ChannelHandlerContext ctx, LoginResult loginResult) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int battleEnd(BattleEnd battleEnd,LoginResult loginResult,MapMonsterBean bean,int v) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
