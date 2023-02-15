package org.come.action.suit;

import java.math.BigDecimal;
import java.util.Random;

import com.gl.util.Config;
import org.come.bean.NChatBean;
import org.come.entity.Goodstable;
import org.come.handler.SendMessage;
import org.come.model.Gem;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.GsonUtil;

import come.tool.Good.DropV;
import come.tool.Good.UsePalAction;

public class SuitMixdeal {

	/**根据宝石类型获取*/
	public static Gem getGemType(long type){
		String goodname=null;
		if (type==746) {goodname="赤焰石";}
		else if (type==749) {goodname="芙蓉石";}
		else if (type==752) {goodname="寒山石";}
		else if (type==755) {goodname="孔雀石";}
		else if (type==758) {goodname="琉璃石";}
		else if (type==761) {goodname="落星石";}
		else if (type==764) {goodname="沐阳石";}
		else if (type==767) {goodname="紫烟石";}
		return GameServer.getGem(goodname);
	}
	/**根据物品类型获取套装的洗炼类型*/
	
	/**通过部件id获取部件名的方法*/
	public static String getPartsName(int id){
		switch (id) {
		case 1:return "帽子";
		case 2:return "项链";
		case 3:return "衣服";
		case 6:return "面具";
		case 7:return "腰带";
		case 8:return "披风";
		case 9:return "挂件";
		case 10:return "左戒指";
		case 11:return "右戒指";
		}
		return null;
	}
	/**根据品质名获取品质等级*/
	public static int getPZlvl(String pz){
		switch (pz) {
		case "把玩":return 1;
		case "贴身":return 2;
		case "珍藏":return 3;
		case "无价":return 4;
		case "传世":return 5;
		case "神迹":return 6;
		}
		return 1;
	}
	/**根据品质等级获取品质名*/
   public static String getPZName(int pzlvl){
		switch (pzlvl) {
		case 1:return "把玩";
		case 2:return "贴身";
		case 3:return "珍藏";
		case 4:return "无价";
		case 5:return "传世";
		case 6:return "神迹";
		}
		return null;		
	}
   /**获取玉符升级(所需的玉符数量)方法*/
	public static int returnJadeNum(int type){
		switch (type) {
		case 1:return 5;
		case 2:return 4;
		case 3:return 3;
		case 4:return 3;
		}
		return 99999999;
	}
	/**获取玉符升级(所需的金钱)方法*/
	public static BigDecimal returnJadeMoney(int type){
		switch (type) {
		case 1:return new BigDecimal(5000000);
		case 2:return new BigDecimal(5000000);
		case 3:return new BigDecimal(5000000);
		case 4:return new BigDecimal(5000000);	
		default:
			break;
		}
		return new BigDecimal(5000000);
	}
	/**获取玉符可以兑换的灵修值的方法  6是用九天玄玉换*/
	public static int returnExcNum(int type){
		switch (type) {
		case 1:return 1;
		case 2:return 1;
		case 3:return 2;
		case 4:return 2;
		case 5:return 3;
		case 6:return 3;
		}
		return 0;
	}
	/**
	 * 获取转移所需灵修值
	 */
	public static int getSxlxz(int pz){
		switch (pz) {
		case 1:return 10;
		case 2:return 20;
		case 3:return 40;
		case 4:return 80;
		case 5:return 160;
		}	
		return 9999999;
	}
	/**
	 * 获取洗练装备的类型
	 */
	public static String lianhua(long type){
		String leixing=null;
		int etype=Goodtype.EquipmentType(type);
		if (etype==-1) {
			etype=UsePalAction.palEquipPath(type);
			if (etype==0) {
				return "武器";
			}else if (etype==2) {
				return "衣服";
			}else if (etype==3) {
				return "帽子";
			}else if (etype==4) {
				return "项链";
			}else if (etype==5) {
				return "鞋子";
			}
		}
		switch (etype) {
		case 0:
			leixing="武器";
			break;
		case 1:
			leixing="帽子";
			break;
		case 2:
			leixing="项链";
			break;
		case 3:
			leixing="衣服";
			break;
		case 4:
			leixing="护身符";
			break;
		case 5:
			leixing="鞋子";
			break;
		case 12:
			leixing="翅膀";
			break;
		}
		if (leixing!=null) {
			if (Goodtype.GodEquipment_God(type)||Goodtype.GodEquipment_xian(type)) {
				leixing="仙器"+leixing;
			}	
		}	
		return leixing;
	}
	static Random random=new Random();
    /**根据装备类型获取炼化条数*/
    public static int getAlchemySum(long type){
    	int size=1;
    	if (type == 6500 || type == 6900 || type == 6601 || type == 6600|| 
    		type == 6701 || type == 6700 || type == 6900 || type == 6800) {
//    		return 1;/**判断是否是神兵*/
		}
    	if (random.nextInt(100)<39) {
			size++;
		}
        if (random.nextInt(100)<9) {
        	size++;
		}
		return size;
    }
    /**根据装备类型获取炼化条数*/
    public static int getAlchemySum(long type,int max){
    	int size=1;
    	if (random.nextInt(100)<80) {size++;}
        if (random.nextInt(100)<70) {size++;}
        if (max>3) {
        	if (random.nextInt(100)<60){
        		size++;
        	}
        	if (max>4) {
            	if (random.nextInt(100)<50){
            		size++;
            	}
            }
        }
		return max>size?size:max;
    }
    /**判断获得的特技数量*/
	public static int getTJSum(long type) {
		int size = 0;
		if (Goodtype.GodEquipment_xian(type)||Goodtype.GodEquipment_Ding(type)) {
			if (random.nextInt(Config.getInt("xq_teji1")) == 0) {
				++size;
				if (random.nextInt(Config.getInt("xq_teji2")) == 0) {
					++size;
				}
			}
		} else if (Goodtype.GodEquipment_God(type)) {
			if (random.nextInt(Config.getInt("sb_teji1")) == 0) {
				++size;
				if (random.nextInt(Config.getInt("sb_teji2")) == 0) {
					++size;
				}
			}
		} else if (random.nextInt(Config.getInt("pt_teji1")) == 0) {
			++size;
			if (random.nextInt(Config.getInt("pt_teji2")) == 0) {
				++size;
			}
		}

		return size;
	}
	static String[] NOS=new String[]{"炼化属性","炼器属性","神兵属性","套装属性","宝石属性","装备角色","性别要求"}; 
    /**将神兵属性转为指定等级的属性*/
    public static String GetGodEquipment(String[] vs,int lvl){
    	StringBuffer buffer=new StringBuffer();
    	int ylvl=Integer.parseInt(vs[0].split("=")[1]);
    	for (int i = 0; i < vs.length; i++) {
			if (buffer.length()!=0) {buffer.append("|");}
		    if (vs[i].length()>=4) {
			   	String vj=vs[i].substring(0, 4);
				if (vj.equals("装备角色")||vj.equals("性别要求")||vj.equals("炼化属性")||
				    vj.equals("炼器属性")||vj.equals("神兵属性")||vj.equals("套装属性")) {
					buffer.append(vs[i]);
					continue;
				}
			}
			String[] vss=vs[i].split("=");	
			buffer.append(vss[0]);
			buffer.append("=");
			buffer.append((int)(Double.parseDouble(vss[1])/ylvl*lvl));
		}
		return buffer.toString();	
    }
    static String MSG4="#G{角色名}#Y眼疾手快的夺回了何大雷刚想贪污的#R{物品名}#Y,定眼一看原来四级了,乐的屁颠屁颠的。";
    static String MSG5="#Y刹那间,天空忽然传来#G{角色名}#Y的一声长啸“有此五级#R{物品名}#Y,今世别无所求”";
    static String MSG6="#Y神器问世，谁与争锋！何大雷双手将六级#R{物品名}#Y奉上，叹道：“只有风流如#G{角色名}#Y，才配得上这绝世神兵”。"; 
    /**神兵升级喊话*/
	public static void sbsj(int lvl,String rolename,String goodname){
		String msg=null;
		if (lvl==4) {msg=MSG4;}
		else if (lvl==5) {msg=MSG5;}
		else if (lvl==6) {msg=MSG6;}
		if (msg==null) {return;}
		msg=msg.replace("{角色名}", rolename);
	    msg=msg.replace("{物品名}", goodname);
		// 发起世界喊话
	    NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(msg);
		msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**刮刮乐类型喊话*/
	public static void ggl(String goodname,String name,String good) throws Exception{
		StringBuffer buffer=new StringBuffer();	
		buffer.append("#Y玩家#G");	
		buffer.append(name);
		buffer.append("#Y使用#G");
		buffer.append(good);
		buffer.append("#Y意外获得");
		buffer.append("#c00ffff");
		buffer.append(goodname);
		buffer.append("#Y");
		buffer.append("物品，真是人品爆发！！！");
		// 发起世界喊话
	    NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**获得物品喊话*/
	public static void good(Goodstable good,String rolename,String msg,int type){
		if (msg==null||msg.equals("")) {return;}
		if (good.getQuality()==null||good.getQuality()<2) {return;}
		if (type==22) {
			StringBuffer buffer=new StringBuffer();
			buffer.append("#Y玩家#G");	
			buffer.append(rolename);
			buffer.append("#Y使用#G");
			buffer.append(msg);
			buffer.append("#Y意外获得");
			buffer.append("#c00ffff");
			buffer.append(good.getGoodsname());
			buffer.append("#Y");
			buffer.append("物品，真是人品爆发！！！");
			msg=buffer.toString();
		}else {
			msg=msg.replace("{角色名}", rolename);
		    msg=msg.replace("{物品名}", good.getGoodsname());	
		}
		// 发起世界喊话
	    NChatBean bean=new NChatBean();
	    if (good.getQuality()>3) {
	    	bean.setId(9);
		}else {
			bean.setId(5);
		}
		bean.setMessage(msg);
		msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	} 
	/**获得物品喊话*/
	public static void good(Goodstable good,String rolename,DropV dropV){
		if (good.getQuality()==null||good.getQuality()<2) {return;}
		String msg=dropV.getMsg();
		if (msg==null||msg.equals("")) {return;}
		if (dropV.getI()==1) {
			StringBuffer buffer=new StringBuffer();
			buffer.append("#Y玩家#G");	
			buffer.append(rolename);
			buffer.append("#Y使用#G");
			buffer.append(dropV.getV2());
			buffer.append("#Y意外获得");
			buffer.append("#c00ffff");
			buffer.append(good.getGoodsname());
			buffer.append("#Y");
			buffer.append("物品，真是人品爆发！！！");
			msg=buffer.toString();
		}else if (msg!=null) {
			msg=msg.replace("{角色名}", rolename);
		    msg=msg.replace("{物品名}", good.getGoodsname());
		}
		// 发起世界喊话
	    NChatBean bean=new NChatBean();
	    if (good.getQuality()>3) {
	    	bean.setId(9);
		}else {
			bean.setId(5);
		}
		bean.setMessage(msg);
		msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
//	static String MSG7="#Y神器问世，谁与争锋！何大雷双手将六级#R{物品名}#Y奉上，叹道：“只有风流如#G{角色名}#Y，才配得上这绝世神兵”。"; 
	/**宝石升级喊话*/
	public static void Gem(String rolename,String goodname,int lvl){
		// 发起世界喊话
//		#c00FFFF鸿运当头，[玩家名]成功合成7级宝石[宝石名]，天地为之一震！#89
		StringBuffer buffer=new StringBuffer();
		buffer.append("#c00FFFF鸿运当头,#R");
		buffer.append(rolename);
		buffer.append("#c00FFFF成功合成");
		buffer.append(lvl);
		buffer.append("级宝石#R");
		buffer.append(goodname);
		buffer.append("#c00FFFF,天地为之一震！#89");
	    NChatBean bean=new NChatBean();
	    if (lvl==10) {bean.setId(9);}
	    else {bean.setId(5);}
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**终极修炼丹技能喊话*/
	public static void PYJN(String rolename,String petname,String skillname){
		StringBuffer buffer=new StringBuffer();
		buffer.append("#R");
		buffer.append(rolename);
		buffer.append("#W的#G");
		buffer.append(petname);
		buffer.append("#W修炼终成正果,领悟了#Y");
		buffer.append(skillname);
		buffer.append("#W技能");
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**技能喊话*/
	public static void JN(String rolename,String petname,String skillname,String lvl){
		StringBuffer buffer=new StringBuffer();
		buffer.append("#G");
		buffer.append(rolename);
		buffer.append("#Y的召唤兽#G");
		buffer.append(petname);
		buffer.append("#Y灵光一闪，顿时领悟了一个");
		buffer.append(lvl);
		buffer.append("技能#G");	
		buffer.append(skillname);
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**技能喊话*/
	public static void JN2(String rolename,String petname,String skillname,String lvl){
		StringBuffer buffer=new StringBuffer();
		buffer.append("#R");
		buffer.append(rolename);
		buffer.append("#c00FFFF的#R");
		buffer.append(petname);
		buffer.append("#c00FFFF灵光一闪，顿时领悟了一个");
		buffer.append(lvl);
		buffer.append("技能#G[");	
		buffer.append(skillname);
		buffer.append("]#c58C7A4。#50");	
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**灵猴金钱喊话*/
	public static void Stealing(String rolename,long money){
//		（玩家）与灵猴斗智斗勇，成功骗取灵猴400000000银两
		StringBuffer buffer=new StringBuffer();
		buffer.append("#Y");		
		buffer.append("玩家");
		buffer.append("#G");	
		buffer.append(rolename);
		buffer.append("#Y与灵猴斗智斗勇，成功骗取灵猴#R");
		buffer.append(money);
		buffer.append("#Y金钱");
		NChatBean bean=new NChatBean();
		bean.setId(5);
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**聚魄丹类型喊话*/
	public static void jpd(String rolename,String petname){
			StringBuffer buffer=new StringBuffer();
			buffer.append("#Y");
			buffer.append("鸿运当头，");
			buffer.append("#G");
			buffer.append(rolename);
			buffer.append("#Y");
			buffer.append("使用聚魄丹时意外的让自己的召唤兽");
			buffer.append("#G");
			buffer.append(petname);
			buffer.append("#Y");
			buffer.append("获得一个新技能栏！");
			NChatBean bean=new NChatBean();
			bean.setId(5);
			bean.setMessage(buffer.toString());
			String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
			SendMessage.sendMessageToAllRoles(msg);
	}
	/**转盘抽奖喊话*/
	public static void ZP(Goodstable good,String type,String rolename){
		if (good.getQuality()==null||good.getQuality()<2) {return;}
		StringBuffer buffer=new StringBuffer();
		buffer.append("#R");
		buffer.append(rolename);
		buffer.append("#Y在#c00FFFF");
		buffer.append(type);
		buffer.append("#Y中被#G");
		buffer.append(good.getGoodsname());
		buffer.append("#Y砸中#24,紧忙收入囊中#132,迈出了六亲不认得步伐离开了#89#89");
		// 发起世界喊话
	    NChatBean bean=new NChatBean();
	    if (good.getQuality()>3) {
	    	bean.setId(9);
		}else {
			bean.setId(5);
		}
		bean.setMessage(buffer.toString());
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
	/**仙玉转盘喊话*/
	public static void XYZP(String rolename,int value){
		if (value<10000) { return; }
		NChatBean bean=new NChatBean();
		if (value>=100000) { bean.setId(9); }
		else { bean.setId(5); }	
		if (value==100000) {
			bean.setMessage("#Y天道之外,轮回之中,恭喜#R"+rolename+"#Y被几率大神附身,在仙玉转盘中获得#R10万仙玉#Y大奖#97转身挥一挥衣袖不带走一片云彩#50");
		}else if (value==50000) {
			bean.setMessage("#Y乌云遮日,电闪雷鸣,#R"+rolename+"#Y在仙玉转盘中竟然抽中了#R5万仙玉#Y,让诸位英雄豪杰羡煞不已#89");
		}else {
			bean.setMessage("祥瑞彩照,鸿运当头.#R"+rolename+"#Y在仙玉转盘中意外获得#R"+value/10000+"万仙玉#24,真乃好运!");
		}
		String msg = Agreement.getAgreement().chatAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean));
		SendMessage.sendMessageToAllRoles(msg);
	}
}
