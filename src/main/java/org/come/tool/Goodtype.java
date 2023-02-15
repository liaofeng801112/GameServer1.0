package org.come.tool;

public class Goodtype {
	/**
	 * 不需要对功能进行描述的类型
	 */
	public static boolean CancelMsg(long type) {
		if (type == 100 || type == 8 || type == 49 || type == 88 || type == 99
				|| type == 111 || type == 113 || type == 212 || type == 213
				|| type == 501 || type == 502 || type == 503 || type == 504
				|| type == 716 || type == 717 || type == 718 || type == 719
				|| type == 720 || type == 721 || type == 7005 || type == 118
				|| type == 190|| type == 112) {
			return true;
		}
		return false;
	}
	/**判断是否是真护身符*/
	public static boolean Amulet2(long type) {
		if (type == 612) {return true;}
		return false;
	}
	/**判断是否是仙器礼盒*/
	public static boolean xianlihe(long type) {
		if (type == 7005) {
			return true;
		}
		return false;
	}
	/**判断是否是宝石*/
	public static boolean baoshi(long type) {
		if (type == 746||type == 749||type == 752||type == 755
			||type == 758||type == 761||type == 764||type == 767) {
			return true;
		}
		return false;
	}
	/**判断是否是强化宝石*/
	public static boolean QHbaoshi(long type) {
		if (type>=123&&type<=127) {
			return true;
		}
		return false;
	}
	/**判断该类型是否可以装备宝石*/
	public static boolean EquipGem(long type){
		return Weapons(type)||Helmet(type)||Necklace(type)||Clothes(type)||Shoes(type);
	}
	/**判断装备类型是否使用对应类型的强化石  type1 装备类型  type2 强化石类型*/
	public static boolean QHEquipGem(long type1,long type2){
		if (type2==123&&Weapons(type1)) {
			return true;
		}else if (type2==124&&Necklace(type1)) {
			return true;
		}else if (type2==125&&Shoes(type1)) {
			return true;
		}else if (type2==126&&Helmet(type1)) {
			return true;
		}else if (type2==127&&Clothes(type1)) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否是超级药丸
	 */
	public static boolean TimingGood(long type) {
		if (type == 493 || type == 492) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否是超级药丸月
	 */
   public  static boolean Medicine(long type){
		if (type == 921|| type == 922){
			return true;
		}
	   return false;
   }
	/**
	 * 判断是否是回蓝符
	 */
	public static boolean BlueBack(long type) {
		if (type == 494 || type == 495 || type == 496) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是飞行旗
	 */
	public static boolean Flightchess(long type) {
		if (type == 2010 || type == 2011 || type == 2012) {
			return true;
		}
		return false;
	}

	/**
	 * 物品vlaue解析
	 */
	public static String[] StringParsing(String vlaue) {
		return vlaue.split("\\|");
	}

	/**
	 * 判断是否是钱 点卡 经验丹 亲密丹 技能熟练度
	 */
	public static boolean Consumption(long type) {
		if (type == 888 || type == 100 || type == 715 || type == 2041
				|| type == 2040 || type == 2042||type==935) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是炼妖石
	 */
	public static boolean ExerciseMonsterOre(long type) {
		if (type >= 702 && type <= 711) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是战斗使用的药品
	 */
	public static boolean FightingMedicine(long type) {
		if (type == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是配饰
	 */
	public static boolean Accessories(long type) {
		if (Mask(type)) {
			return true;
		} else if (Belt(type)) {
			return true;
		} else if (Cloak(type)) {
			return true;
		} else if (Pendant(type)) {
			return true;
		} else if (Ring(type)) {
			return true;
		} else if (Amulet(type)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是神兵
	 */
	public static boolean GodEquipment_God(long type) {
		if (type == 6500 || type == 6900 || type == 6601 || type == 6600
				|| type == 6701 || type == 6700 || type == 6900 || type == 6800) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是仙器
	 */
	public static boolean GodEquipment_xian(long type) {
		if (type >= 7000 && type <= 7004) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是定制装备
	 */
	public static boolean GodEquipment_Ding(long type) {
		if (type >=  8868&&type <= 8872) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否是矿石
	 */
	public static boolean Ore(long type) {
		if (type == 500) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是普通装备
	 */
	public static boolean OrdinaryEquipment(long type) {
		if ((type == 800) || (type >= 600 && type <= 605)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断装备的类型 -1 不是装备 0武器 1头盔 2项链 3衣服 4护身符 5鞋子 6面具 7腰带 8披风 9挂件 10 11戒指  12翅膀 13星卡
	 */ 
	public static int EquipmentType(long type) {

		if (Weapons(type)) {
			return 0;
		} else if (Helmet(type)) {
			return 1;
		} else if (Necklace(type)) {
			return 2;
		} else if (Clothes(type)) {
			return 3;
		} else if (Amulet(type)) {
			return 4;
		} else if (Shoes(type)) {
			return 5;
		} else if (Mask(type)) {
			return 6;
		} else if (Belt(type)) {
			return 7;
		} else if (Cloak(type)) {
			return 8;
		} else if (Pendant(type)) {
			return 9;
		} else if (Ring(type)) {
			return 10;
		}else if (type==8888) {
			return 12;
		}else if (type==520) {
			return 13;
		}

		return -1;
	}

	/**
	 * 判断是否是武器
	 */
	public static boolean Weapons(long type) {
		if ((type == 800) || (type == 6500) || (type == 7004)) {
			return true;
		}
		return false;

	}

	/**
	 * 判断是否是头盔
	 */
	public static boolean Helmet(long type) {
		if ((type == 601) || (type == 600) || (type == 6600) || (type == 6601)
				|| (type == 600) || (type == 7001)) {
			return true;
		}
		return false;
	}
	/**=================================================*/
	public static boolean Xqitouk(long type) {
		if ((type == 7001)) {
			return true;
		}
		return false;
	}
	public static boolean xqiwuqi(long type) {
		if ((type == 7004)) {
			return true;
		}
		return false;
	}

	public static boolean xqixianglian(long type) {
		if ((type == 7002)) {
			return true;
		}
		return false;
	}
	public static boolean xqiwyf(long type) {
		if ((type == 7000)) {
			return true;
		}
		return false;
	}
	public static boolean xqixiezi(long type) {
		if ((type == 7003)) {
			return true;
		}
		return false;
	}
	/**=================================================*/
	/**
	 * 判断是否是项链
	 */
	public static boolean Necklace(long type) {
		if ((type == 603) || (type == 7002) || type == 6800) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是衣服
	 */
	public static boolean Clothes(long type) {
		if ((type == 605) || (type == 604) || (type == 6700) || (type == 6701)
				|| (type == 7000)) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否是护身符
	 */
	public static boolean Amulet(long type) {
		if (type == 612||type == 611) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否是鞋子
	 */
	public static boolean Shoes(long type) {
		if ((type == 602) || (type == 6900) || (type == 7003)) {
			return true;
		}
		return false;
	}

	/**判断是否是面具*/
	public static boolean Mask(long type) {
		if ((type == 609)||(type==927)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是戒指
	 */
	public static boolean Ring(long type) {
		if ((type == 606)||(type==928)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是腰带
	 */
	public static boolean Belt(long type) {
		if ((type == 608)||(type==929)){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是披风
	 */
	public static boolean Cloak(long type) {
		if ((type == 610)||(type==930)){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是挂件
	 */
	public static boolean Pendant(long type) {
		if ((type == 607)||(type==931)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是召唤兽装备所需物品<br>
	 * 498 九彩<br>
	 * 497 内丹<br>
	 * 513玄铁晶石 <br>
	 * 514千年魂石<br>
	 * 515隐月神石<br>
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isSummonGoods(long type) {
		if (type == 498 || type == 497 || type == 513 || type == 514 || type == 515) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是召唤兽装备<br>
	 * 510兽环<br>
	 * 511兽铃<br>
	 * 512兽甲<br>
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isSummonEquip(long type) {
		if (type == 510 || type == 511 || type == 512) {
			return true;
		}
		return false;
	}
	/**伙伴装备*/
	public static boolean isPalEquip(long type) {
		if (type>=7503&&type<=7509) {
			return true;
		}
		return false;
	}
}
