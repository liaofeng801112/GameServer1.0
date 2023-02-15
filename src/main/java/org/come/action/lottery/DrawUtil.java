package org.come.action.lottery;

import java.math.BigDecimal;

import org.come.action.monitor.MonitorUtil;
import org.come.action.reward.DrawnitemsAction;
import org.come.action.suit.SuitMixdeal;
import org.come.bean.LoginResult;
import org.come.bean.QuackGameBean;
import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.server.GameServer;

import come.tool.Good.AddGoodAction;
import come.tool.Role.RoleData;
import come.tool.Stall.AssetUpdate;

public class DrawUtil {

	/**抽奖*/
	public static QuackGameBean CJ(int type,Draw draw,AssetUpdate asset,LoginResult loginResult,RoleData data){
		StringBuffer buffer =new StringBuffer();
		QuackGameBean bean=new QuackGameBean();
		bean.setType(4);
		if (type==0) {
			MonitorUtil.addCJ(draw.getDid(), 1);
            int path=GameServer.random.nextInt(12);
			for (int i = 0; i < 12; i++) {
				DrawBase base=null;
				if (path==i) {
					base=draw.rDrawBase();
					if (buffer.length()!=0) {buffer.append("|");}
					int id=base.getId();
					buffer.append(id);
					buffer.append("_");
					buffer.append(base.getSum());
					buffer.append("_");
					buffer.append(1);	
					
					BigDecimal goodsid=new BigDecimal(id);
					Goodstable goodstable = GameServer.getGood(goodsid);
					//特效物品判断是拥有特效
					if (id<0&&data.getPackRecord().isTX(-id+"")) {continue;}
					if( goodstable == null ){continue;}
					XXGDBean xxgdBean=new XXGDBean();
					xxgdBean.setId(id+"");
					xxgdBean.setSum(base.getSum());
					AddGoodAction.addGood(asset,goodstable,loginResult,data,xxgdBean,15);
					SuitMixdeal.ZP(goodstable,draw.getName(),loginResult.getRolename());
				}else {
					base=draw.rDrawBase2();
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append(base.getId());
					buffer.append("_");
					buffer.append(base.getSum());
					buffer.append("_");
					buffer.append(0);
				}
			}
		}else if (type==1) {
			MonitorUtil.addCJ(draw.getDid(),10);
			for (int i = 0; i < 12; i++) {
				DrawBase base=draw.rDrawBase();
				if (buffer.length()!=0) {buffer.append("|");}
				int id=base.getId();
				buffer.append(id);
				buffer.append("_");
				buffer.append(base.getSum());
				buffer.append("_");
				buffer.append(1);	
				
				BigDecimal goodsid=new BigDecimal(id);
				Goodstable goodstable = GameServer.getGood(goodsid);
				//特效物品判断是拥有特效
				if (id<0&&data.getPackRecord().isTX(-id+"")) {continue;}
				if( goodstable == null ){continue;}
				XXGDBean xxgdBean=new XXGDBean();
				xxgdBean.setId(id+"");
				xxgdBean.setSum(base.getSum());
				AddGoodAction.addGood(asset,goodstable,loginResult,data,xxgdBean,15);
				SuitMixdeal.ZP(goodstable,draw.getName(),loginResult.getRolename());
			}
		}else {
			return bean;
		}
		String jf=draw.getName()+"积分="+(type==0?draw.getIntegral():draw.getIntegral()*10);
		asset.updata(jf);
		loginResult.setScore(DrawnitemsAction.Splice(loginResult.getScore(),jf,2));
		asset.setMsg("你获得"+draw.getName()+"积分"+(type==0?draw.getIntegral():draw.getIntegral()*10));
		bean.setPetmsg(buffer.toString());
		bean.setMoney(loginResult.getScoretype(draw.getName()+"积分"));
		return bean;		
	}
}
