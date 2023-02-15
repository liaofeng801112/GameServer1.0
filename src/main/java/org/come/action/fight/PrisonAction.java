package org.come.action.fight;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;

import come.tool.FightingData.Battlefield;

public class PrisonAction implements IAction {
	static Map<Integer, List<String>> Prisonroom=new HashMap<>();
	static String GOHOME="{\"mapid\":\"1207\",\"mapx\":4297,\"mapy\":2887}";
	//出狱地址 目前送回长安
	static{
		//死
		List<String> room1=new ArrayList<>();
		room1.add("{\"mapid\":\"3312\",\"mapx\":2203,\"mapy\":1138}");
		//地
		List<String> room2=new ArrayList<>();
		room2.add("{\"mapid\":\"3313\",\"mapx\":338,\"mapy\":1214}");
		//天
		List<String> room3=new ArrayList<>();
		room3.add("{\"mapid\":\"3314\",\"mapx\":1410,\"mapy\":1015}");
		Prisonroom.put(1, room1);
		Prisonroom.put(2, room2);
		Prisonroom.put(3, room3);
	}
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult roleinfo=GameServer.getAllLoginRole().get(ctx);
		roleinfo.setTaskDaily(message);
		String team=roleinfo.getTeamInfo();
		if (team==null||team.equals("")) {
			
		}else if (team.split("\\|")[0].equals(roleinfo.getRolename())) {
			//判断是否为队长
			IAction action=ParamTool.ACTION_MAP.get("teambreak");
			action.action(ctx, null);
		}else {
			//是队员	
			IAction action=ParamTool.ACTION_MAP.get("teammove");
			action.action(ctx, null);
		}
        String[] v=message.split("\\|");
        int ll=Integer.parseInt(v[0]);
        if (ll==0) {
			//送回长安
        	IAction action=ParamTool.ACTION_MAP.get("changemap");
     		action.action(ctx,GOHOME);
     		return;
		}     
        if (ll>4)ll=2;
        else if (ll>=8)ll=3;
        else ll=1;
        IAction action=ParamTool.ACTION_MAP.get("changemap");
		action.action(ctx,Prisonroom.get(ll).get(Battlefield.random.nextInt(Prisonroom.get(ll).size())));
	}
   
    
}
