package org.come.action.give;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.action.monitor.MonitorUtil;
import org.come.action.monster.ClickMonsterAction;
import org.come.bean.Bbuy;
import org.come.bean.GiveGoodsBean;
import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Record;
import org.come.handler.SendMessage;
import org.come.model.Lshop;
import org.come.model.Robots;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MonsterUtil;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleMixDeal;
import come.tool.Mixdeal.AnalysisString;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;
/**
 * 给于,客户端发送GiveGoodsBean,返回GoodsListResultBean
 * @author 叶豪芳
 * @date 2017年12月20日 上午11:39:43
 * 
 */ 
public class GiveAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		LoginResult loginResult=GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
		GiveGoodsBean giveBean = GsonUtil.getGsonUtil().getgson().fromJson(message, GiveGoodsBean.class);
		if (giveBean.getType()==0) {
			ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(giveBean.getOtherName());
			LoginResult otherRole=ctx2!=null?GameServer.getAllLoginRole().get(ctx2):null;	
			if (otherRole==null||otherRole.getRole_id().compareTo(loginResult.getRole_id())==0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("对方不在线"));
				return;
			}	
			RoleData roleData=RolePool.getRoleData(otherRole.getRole_id());
			if (roleData.getRoleSystem().getIsGood()==0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("对方关闭物品接收功能"));
				return;
			}
			Goodstable goodstable=null;
			if (giveBean.getRgid()!=null) {
				goodstable=getGiveGood(giveBean.getRgid(), loginResult.getRole_id(), giveBean.getSum(),true);
				if (goodstable==null) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你给与的物品处于异常"));
					return;
				}
			}
			if (giveBean.getGold()!=null) {
				if (giveBean.getGold().longValue()==0) {
					giveBean.setGold(null);
				}else if (giveBean.getGold().longValue()<0) {
					AllServiceUtil.getRecordService().insert(new Record(5,"给与异常:角色id:"+loginResult.getRole_id()+"_"+message));
					return;
				}else if (loginResult.getGold().longValue()<giveBean.getGold().longValue()) {
					SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你给与的金钱不足"));
					return;
				}			
			}
			AssetUpdate giveData=new AssetUpdate(AssetUpdate.USEGOOD);//给与者 
			giveData.setMsg("给与物品成功");
			AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);//被接收者
			StringBuffer assetBuffer=new StringBuffer();
			assetBuffer.append("#G");
			assetBuffer.append(loginResult.getRolename());
			assetBuffer.append("#Y给你");
			if (giveBean.getGold()!=null) {
				assetBuffer.append(giveBean.getGold());
	    		assetBuffer.append("大话币,");
	    		
				loginResult.setGold(loginResult.getGold().subtract(giveBean.getGold()));			
	    		otherRole.setGold(otherRole.getGold().add(giveBean.getGold()));	
	    		assetUpdate.updata("D="+giveBean.getGold().longValue());
	    		giveData.updata("D=-"+giveBean.getGold().longValue());
	    		if (MonitorUtil.isUpMoney(2,giveBean.getGold().longValue())) {
					StringBuffer buffer=new StringBuffer();
					buffer.append("给与大宗金额流动:");
					buffer.append(loginResult.getRole_id());
					buffer.append("送给");
					buffer.append(otherRole.getRole_id());
					buffer.append("金额");
					buffer.append(giveBean.getGold().longValue());
					AllServiceUtil.getRecordService().insert(new Record(2,buffer.toString()));
	    		}
			}
			if (goodstable!=null) {
				assetBuffer.append(giveBean.getSum());
	    		assetBuffer.append("个");
	    		assetBuffer.append(goodstable.getGoodsname());
	    		
				if(EquipTool.canSuper(goodstable.getType())){//可重叠
					goodstable.goodxh(giveBean.getSum());
					AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
					giveData.updata("G"+goodstable.getRgid()+"="+goodstable.getUsetime());
					// 判断该角色是否拥有这件物品
					List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(otherRole.getRole_id(),goodstable.getGoodsid());
					if (sameGoodstable.size() != 0) {
						sameGoodstable.get(0).setUsetime(sameGoodstable.get(0).getUsetime()+ giveBean.getSum());
						AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
						assetUpdate.setGood(sameGoodstable.get(0));
					} else {
						Goodstable good = goodstable.clone();
						good.setRgid(null);
						good.setStatus(0);
						good.setRole_id(otherRole.getRole_id());
						good.setUsetime(giveBean.getSum());
						AllServiceUtil.getGoodsTableService().insertGoods(good);
						assetUpdate.setGood(good);
					}
				}else {
					AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, otherRole.getRole_id(), null, null);
					assetUpdate.setGood(goodstable);
					giveData.updata("G"+goodstable.getRgid()+"=0");
				}
				Goodstable JLGood=goodstable.clone();
				JLGood.setRole_id(loginResult.getRole_id());
				AllServiceUtil.getGoodsrecordService().insert(JLGood,otherRole.getRole_id(),giveBean.getSum(),2);//添加记录
			}
			assetUpdate.setMsg(assetBuffer.toString());
			SendMessage.sendMessageToSlef(ctx2,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate))); 
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(giveData))); 
			return;
		}
		if (giveBean.getRgid()==null) {return;}
		Goodstable goodstable=getGiveGood(giveBean.getRgid(), loginResult.getRole_id(), giveBean.getSum(),false);
		if (goodstable==null) {
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你给与的物品处于异常"));
			return;
		}
		if (giveBean.getType()==1) {//金钱回收
			Bbuy bbuy=GameServer.getBbuy(goodstable.getGoodsid());
			if (bbuy==null||bbuy.getPrice1()==0) {return;}
			int num=giveBean.getSum();
			if (giveBean.getSum()>25) {giveBean.setSum(25);}
			giveBean.setSum(bbuy.addNum(giveBean.getSum()));
			if (giveBean.getSum()<=0||goodstable.getUsetime()<giveBean.getSum()) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("今天回收的材料已经够用了,明天再来吧"));
				return;
			}
			goodstable.setUsetime(goodstable.getUsetime()-giveBean.getSum());
			AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USEGOOD);
			long money=(bbuy.getPrice1()*giveBean.getSum());
			assetUpdate.updata("D="+money);
    		assetUpdate.updata("G"+goodstable.getRgid()+"="+goodstable.getUsetime());
    		if (num>25) {assetUpdate.setMsg("收购获得"+money+"银两|单次收购最大数25个");}
      		else{assetUpdate.setMsg("收购获得"+money+"银两");}
    		loginResult.setGold(loginResult.getGold().add(new BigDecimal(money)));
			MonitorUtil.getMoney().addD(money, 3);
    		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
		}else if (giveBean.getType()==2) {//绑玉回收
			Bbuy bbuy=GameServer.getBbuy(goodstable.getGoodsid());
			if (bbuy==null||bbuy.getPrice2()==0) {return;}
			//int num=MonitorUtil.addBY(loginResult.getRole_id(),giveBean.getSum(),bbuy.getPrice2());
//			if (num<=0) {
//				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("超过单日绑玉最大获取量将收购数量修改为0个"));
//				return;
//			}
			goodstable.goodxh(1);
			AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USEGOOD);
			long money=(bbuy.getPrice2());
			assetUpdate.updata("S="+money);
    		assetUpdate.updata("G"+ goodstable.getRgid()+"="+goodstable.getUsetime());
//            if (num!=giveBean.getSum()) {
//            	assetUpdate.setMsg("超过单日绑玉最大获取量将收购数量修改为"+num+"个|收购获得"+money+"绑玉");
//			}

				assetUpdate.setMsg("收购获得"+money+"绑玉");

    		loginResult.setSavegold(loginResult.getSavegold().add(new BigDecimal(money)));
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  
		}else if (giveBean.getType()==3) {//限时回收
			MapMonsterBean bean=MonsterUtil.getMonster(giveBean.getOtherID().intValue());
			if (bean==null||bean.getRobotType()!=3) {SendMessage.sendMessageToSlef(ctx,ClickMonsterAction.CHECKTS2);return;}
			Lshop lshop=bean.getShops().get(goodstable.getGoodsid().toString());
			if (lshop==null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("不属于回收范围"));
				return;
			}
			Robots robots=GameServer.getAllRobot().get(bean.getRobotid()+"");	
			if (robots==null) {return;}
			if (robots.getLvls()!=null) {
				String value=BattleMixDeal.isLvl(loginResult.getGrade(), robots.getLvls());
        		if (value!=null) {SendMessage.sendMessageToSlef(ctx,value);return;}
			}
			String v=ClickMonsterAction.isTime20s(loginResult.getRole_id());
			if (v!=null) {SendMessage.sendMessageToSlef(ctx,v);return;}
			AssetUpdate assetUpdate=new AssetUpdate();
			assetUpdate.setType(AssetUpdate.USEGOOD);
			String msg=null;
			if (giveBean.getSum()>lshop.getlNum()) {
				msg="单次最大购买数量"+lshop.getlNum();
				giveBean.setSum(lshop.getlNum());
			}
			giveBean.setSum(lshop.addNum(giveBean.getSum()));
			if (giveBean.getSum()==0) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("回收的材料已经够用了"));
				return;
			}
			goodstable.setUsetime(goodstable.getUsetime()-giveBean.getSum());
			AllServiceUtil.getGoodsTableService().updateGoodRedis(goodstable);
			assetUpdate.updata("G"+goodstable.getRgid()+"="+goodstable.getUsetime());
			if (lshop.getType()==0) {
				long money=(lshop.getMoney().longValue()*giveBean.getSum());
				loginResult.setGold(loginResult.getGold().add(new BigDecimal(money)));
				assetUpdate.updata("D="+money);
				MonitorUtil.getMoney().addD(money, 3);
				if (msg!=null) {msg=msg+"|收购获得"+money+"银两";}
				else{msg="收购获得"+money+"银两";}
			}else if (lshop.getType()==1) {
				long money=(lshop.getMoney().longValue()*giveBean.getSum());
				loginResult.setCodecard(loginResult.getCodecard().add(new BigDecimal(money)));
				MonitorUtil.getMoney().addX(money, 2);
				assetUpdate.updata("X="+money);
			    if (msg!=null) {msg=msg+"|收购获得"+money+"仙玉";}
				else{msg="收购获得"+money+"仙玉";}
			}
			assetUpdate.setMsg(msg);
    		SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));  	
		}
		AllServiceUtil.getGoodsrecordService().insert(goodstable, null, giveBean.getSum(), 2);//添加记录
	}
	/**给与物品判断*/
	public Goodstable getGiveGood(BigDecimal rgid,BigDecimal roleid,int sum,boolean isJY){
		if (sum<0) {
			AllServiceUtil.getRecordService().insert(new Record(5,"给与异常:id:"+rgid+"_角色:"+roleid+"_数量:"+sum));
			return null;
		}
		Goodstable goodstable=AllServiceUtil.getGoodsTableService().getGoodsByRgID(rgid);
	    if (goodstable==null) {
	    	AllServiceUtil.getRecordService().insert(new Record(5,"给与异常:id:"+rgid+"_角色:"+roleid+"_数量:"+sum));
			return null;
		}
		if (goodstable.getRole_id().compareTo(roleid)!=0||goodstable.getUsetime()<sum) {
	    	StringBuffer buffer=new StringBuffer();
	    	buffer.append("给与异常:id:"+rgid+"_角色:"+roleid+"_数量:"+sum);
	    	buffer.append("_物品属性:");
	    	buffer.append(GsonUtil.getGsonUtil().getgson().toJson(goodstable));
	    	AllServiceUtil.getRecordService().insert(new Record(5,buffer.toString()));
			return null;
	    }
		if (isJY&&AnalysisString.jiaoyi(goodstable.getQuality())) {
			StringBuffer buffer=new StringBuffer();
	    	buffer.append("给与异常物品绑定:id:"+rgid+"_角色:"+roleid+"_数量:"+sum);
	    	buffer.append("_物品属性:");
	    	buffer.append(GsonUtil.getGsonUtil().getgson().toJson(goodstable));
	    	AllServiceUtil.getRecordService().insert(new Record(5,buffer.toString()));
			return null;
		}
	    return goodstable;
	}
}
