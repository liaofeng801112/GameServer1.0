package come.tool.FightingDataAction;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.monitor.MonitorUtil;
import org.come.bean.LoginResult;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingEvents;
import come.tool.FightingData.FightingState;
import come.tool.FightingData.ManData;
import come.tool.Stall.AssetUpdate;

public class Stealing implements DataAction{

	@Override
	public void analysisAction(ManData manData, FightingEvents fightingEvents,
		String type, Battlefield battlefield) {
		// TODO Auto-generated method stub
		//获取玩家单位
		ManData roleData=null;
		for (int i = 0; i < battlefield.fightingdata.size(); i++) {
			ManData data=battlefield.fightingdata.get(i);
			if (data.getType()==0) {
				roleData=data;
				break;
			}
		}
        if (roleData==null) {
			return;
		}
        ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(roleData.getManname());
        LoginResult login=GameServer.getAllLoginRole().get(ctx);
        if (login==null) {return;}
        long money=login.getGold().longValue();
        long money2=getMoney(battlefield.CurrentRound);
        if (money2>money) {
        	money2=money;
		}
        money-=money2;
        login.setGold(new BigDecimal(money));
        MonitorUtil.getMoney().useD(money2);
        AssetUpdate assetUpdate=new AssetUpdate();
        assetUpdate.setType(AssetUpdate.STEALING);
        assetUpdate.updata("D="+(-money2));//钱数
        SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate))); 
        roleData.setMoney(money2);
        //处理回合流程
    	List<FightingState> Accepterlist=new ArrayList<>();
        FightingState f1=new FightingState();
        f1.setCamp(manData.getCamp());
        f1.setMan(manData.getMan());
		f1.setStartState("法术攻击");
		Accepterlist.add(f1);
		FightingState state=new FightingState();
		state.setCamp(roleData.getCamp());
		state.setMan(roleData.getMan());
		state.setStartState("代价");
		StringBuffer buffer=new StringBuffer();
		buffer.append("#Y被偷取#R");
		buffer.append(money2);
		buffer.append("#Y金钱");
		state.setText(buffer.toString());
		Accepterlist.add(state);	
		fightingEvents.setOriginator(null);
		fightingEvents.setAccepterlist(Accepterlist);
		battlefield.NewEvents.add(fightingEvents);	
	}
	
	/**偷钱档次         
	 10w-20w     79              
	 20w-100w    16.5 
	 100w-200w   3.6
	 200w-1000w  0.7
	 1000w-2000w 0.1475
	 2000w-1e    0.04
	 1e-2e       0.01
	 2e-10e      0.0025
             获取偷取的金额*/
	static final int MAXGL=1000000;
	static final int MAXGL2=975000;
	static final int MAXGL3=950000;
	static final int MAXGL4=925000;
	static final int GL_1=20;
	static final int GL_2=100;
	static final int GL_3=450;
	static final int GL_4=1800;
	static final int GL_5=8500;
	static final int GL_6=41500;
	static final int GL_7=200000;
	static final long JS=20000;//偷取金钱下限
	public long getMoney(int h){//h<=2只能在1 2档次抽取奖励
		long money=JS;
		if (h<=2) {
			if (h==1) {
				money=JS/2;	
			}		
			money+=Battlefield.random.nextInt((int)money)*1;
		}else{
			int gl=0;
			if (h>3) {
				gl=Battlefield.random.nextInt(MAXGL2);
			}else if (h>4) {
				gl=Battlefield.random.nextInt(MAXGL3);
			}else if (h>5) {
				gl=Battlefield.random.nextInt(MAXGL4);
			}else {
				gl=Battlefield.random.nextInt(MAXGL);
			}
			if (gl<GL_1) {
				money=JS*2000;
				money+=Battlefield.random.nextInt((int)money/2)*8;
			}else if (gl<GL_2) {
				money=JS*1000;
				money+=Battlefield.random.nextInt((int)money)*1;
			}else if (gl<GL_3) {
				money=JS*200;
				money+=Battlefield.random.nextInt((int)money/2)*8;
			}else if (gl<GL_4) {
				money=JS*100;
				money+=Battlefield.random.nextInt((int)money)*1;
			}else if (gl<GL_5) {
				money=JS*20;
				money+=Battlefield.random.nextInt((int)money/2)*8;
			}else if (gl<GL_6) {
				money=JS*10;
				money+=Battlefield.random.nextInt((int)money)*1;
			}else if (gl<GL_7) {
				money=JS*2;
				money+=Battlefield.random.nextInt((int)money/2)*8;
			}else{
				money=JS;
				money+=Battlefield.random.nextInt((int)money)*1;
			}	
		}
		return money;		
	}
	public static void main(String[] args) {
		Stealing stealing=new Stealing();
        int size=200000000;
        //连偷次数
        int lj=0;
        for (int k = 0; k < 10; k++) {
        	lj++;
            long z=0;
        	for (int i = 0; i < size; i++) {
    			for (int j =  0; j <lj; j++) {
    				long x=stealing.getMoney(j);
    				z-=x;
                    if (x>200000) {
                    	
                    	z+=2*x;
                    	break;
					}
    				if (j==lj-1) {
    					
    					z+=2*x;
    				}
    			    
    			}
    			
    		}
        	System.out.println("偷钱次数"+lj);
    		System.out.println("平均收益"+(z/size));
		}
	
		
	}
}
