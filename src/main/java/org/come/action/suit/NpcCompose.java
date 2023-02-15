package org.come.action.suit;

import java.math.BigDecimal;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

import org.come.bean.LoginResult;
import org.come.bean.SuitOperBean;
import org.come.entity.Goodstable;

public class NpcCompose {
	
	public static void typeNPC(LoginResult loginResult, ChannelHandlerContext ctx, SuitOperBean suitOperBean) {
		for (int i=suitOperBean.getGoods().size()-1;i>=0;i--) {
			BigDecimal rgid=suitOperBean.getGoods().get(i);
			for (int j=0;j<i;j++) {if (rgid.compareTo(suitOperBean.getGoods().get(j))==0) {suitOperBean.getGoods().remove(j);break;}}
		}
		if (suitOperBean.getGoods().size()!=2) {return;}//物品不对
		List<Goodstable> goods = SuitComposeAction.getGoods(suitOperBean.getGoods(), loginResult.getRole_id(), 0);
		if (goods==null||goods.size()!=2) {return;}//物品不对
		if (suitOperBean.getType()==71) {type71(loginResult, ctx, goods);}
//		我要合成仙器  71
//		我要升级仙器  72
//		我要打造普通装备/打造11-16级装备  73
//		我要合成炼妖石 74
//		我要培养饰品 75
//		我要重铸饰品 76
//		我要合成符石/我要洗练符石 77
//		我要上神兵石 78 
//		我要培养护身符 79
//		我要重铸护身符 80
//		培养彩晶石 81
	}
	/**我要合成仙器 71*/
	public static void type71(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要升级仙器 72*/
	public static void type72(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要打造普通装备/打造11-16级装备  73*/
	public static void type73(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要合成炼妖石 74*/
	public static void type74(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要培养饰品 75*/
	public static void type75(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要培养饰品 76*/
	public static void type76(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要合成符石/我要洗练符石 77*/
	public static void type77(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要上神兵石 78*/
	public static void type78(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要培养护身符 79*/
	public static void type79(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**我要重铸护身符 80*/
	public static void type80(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	/**培养彩晶石 81*/
	public static void type81(LoginResult loginResult, ChannelHandlerContext ctx,List<Goodstable> goods) {}
	
}
