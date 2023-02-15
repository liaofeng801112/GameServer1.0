package org.come.bean;

/**
 * 
 * 用来保存战斗时候一些状态信息
 * @author 黄建彬
 *
 */
public class FightStateBean {
     
	private int gameStartRound;//起始回合
	
	private int gameoverRound;//战斗进行回合
	
	private String playerInearseState;//此时玩家的状态，增益
	
	private String playerstate;//封印 ，震慑,中毒，混乱，昏睡

	public String getPlayerInearseState() {
		return playerInearseState;
	}

	public void setPlayerInearseState(String playerInearseState) {
		this.playerInearseState = playerInearseState;
	}

	public String getPlayerstate() {
		return playerstate;
	}

	public void setPlayerstate(String playerstate) {
		this.playerstate = playerstate;
	}

	public int getGameStartRound() {
		return gameStartRound;
	}

	public void setGameStartRound(int gameStartRound) {
		this.gameStartRound = gameStartRound;
	}

	public int getGameoverRound() {
		return gameoverRound;
	}

	public void setGameoverRound(int gameoverRound) {
		this.gameoverRound = gameoverRound;
	}

	
	
}
