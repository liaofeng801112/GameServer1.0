package come.tool.Scene;

import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

import org.come.bean.LoginResult;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterMoveBase;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleEnd;

public interface Scene {
	  
	  /**战斗结束*/
      String UPMonster(BattleData battleData,String[] teams,int type,StringBuffer buffer);
      /**单人结算奖励*/
      int battleEnd(BattleEnd battleEnd,LoginResult loginResult,MapMonsterBean bean,int v);
      /**领取奖励*/
      void getAward(ChannelHandlerContext ctx,LoginResult loginResult);
      /**获取野怪数据*/
      Map<Integer, MonsterMoveBase> getMapMonster(StringBuffer buffer,Map<Integer, MonsterMoveBase> moveMap, long mapId);
      /**判断是否结束*/
      public boolean isEnd();
      public boolean isTime(long time);
      /**获取副本数据*/
      String getSceneMsg(LoginResult loginResult,long oldMapId,long mapId);
}
