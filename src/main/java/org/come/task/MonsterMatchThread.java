package org.come.task;

import io.netty.channel.ChannelHandlerContext;

import org.come.bean.LoginResult;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

import come.tool.Battle.BattleThreadPool;

public class MonsterMatchThread extends Thread {
	public String MSG=Agreement.getAgreement().PromptAgreement("地煞星正在挑选顺眼的玩家…… 请等待#24");
	public String MSGTwo=Agreement.getAgreement().PromptAgreement("上古之魂,正在备战状态，请等待战斗回合#32");
	
	boolean is = true;
	public MonsterMatchThread() {
		// TODO Auto-generated constructor stub
		setDaemon(true);
	}
	public void addMatch() {
		synchronized (this) {
			if (is) {return;}
			is = true;
			this.notifyAll();
		}
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				if (MonsterUtil.isMatch()) {
					for (int i = MonsterUtil.matchList.size()-1; i>=0; i--) {
						MapMonsterBean bean=MonsterUtil.matchList.get(i);
						if (bean.getType()!=0||bean.getMatch()==null) {
							MonsterUtil.matchList.remove(i);
							continue;
						}
						MonsterMatch match=bean.getMatch();
						match.setCountDown(match.getCountDown()-1);
						if (match.getCountDown()<=0) {
							while (true) {
								String roleName=match.getMatch();
								if (roleName==null) {break;}
								ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(roleName);
								if (ctx==null) {continue;}
								LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
								if (loginResult==null) {continue;}
								if (bean.getMap().longValue()!=loginResult.getMapid()) {continue;}
								if (Math.abs(bean.getX()-loginResult.getX())>600||Math.abs(bean.getY()-loginResult.getY())>600) {continue;}
								String[] teams=loginResult.getTeam().split("\\|");
								if (!teams[0].equals(loginResult.getRolename())) {continue;}
								if (BattleThreadPool.addBattle(loginResult,teams,bean)) {//进入战斗成功
									bean.getMatch().clearMatch();
									break;
								}
							}
							if (bean.getMatch()!=null) {bean.getMatch().clearMatch();}
							MonsterUtil.matchList.remove(i);
						}else if (match.getCountDown()%3==0) {
							match.sendMatch();
						}
					}
				}else {
					synchronized (this) {
						is=false;	
						this.wait();
					}
				}	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
