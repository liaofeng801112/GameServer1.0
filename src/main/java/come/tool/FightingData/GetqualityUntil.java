package come.tool.FightingData;

import org.come.model.Monster;

/**
 * 放回对象返回rolequality信息
 * @author Administrator
 */
public class GetqualityUntil {
/**默认初始值*/
 public static double csz(String type){try {return Double.parseDouble(type);} catch (Exception e) {return 0;}} 
    /**获取野怪抗性*/
    public static Ql getMonsterQl(Monster monster){
    	Ql ql=new Ql();
    	for (int i = 0; i < 7; i++) {
			String[] vs=null;
			if (i==0) {vs=monster.getK().split("\\|");}
			else if (i==1) {vs=monster.getH().split("\\|");}
			else if (i==2) {vs=monster.getQ().split("\\|");}
			else if (i==3) {vs=monster.getWX().split("\\|");}
			else if (i==4) {vs=monster.getSS().split("\\|");}
			else if (i==5) {vs=monster.getKB().split("\\|");}
			else if (i==6) {vs=monster.getQT().split("\\|");}
			if (!vs[0].equals("")) {
				for (int j = 0; j < vs.length; j++) {
					String[] vss=vs[j].split("=");
					if (vss[0].startsWith("抗")) {
						vss[0]="抗"+vss[0].substring(1);
					}
					AddM(ql,vss[0],csz(vss[1]));
				}	
			}	
		}
		return ql;
    }
    /**增加或者减小对应属性野怪用的*/
	public static void AddM(Ql quality,String key,double value){
		switch (key) {
		case "抗物理":quality.setRolekwl(quality.getRolekwl()+value);break;
		case "抗致命率":quality.setKzml(quality.getKzml()+value);break;
		case "抗风":quality.setRolekff(quality.getRolekff()+value);break;
		case "抗雷":quality.setRoleklf(quality.getRoleklf()+value);break;
		case "抗水":quality.setRoleksf(quality.getRoleksf()+value);break;
		case "抗火":quality.setRolekhf(quality.getRolekhf()+value);break;
		case "抗仙法":
			quality.setRolekff(quality.getRolekff()+value);
			quality.setRoleklf(quality.getRoleklf()+value);
			quality.setRoleksf(quality.getRoleksf()+value);
			quality.setRolekhf(quality.getRolekhf()+value);
			break;
		case "抗混乱":quality.setRolekhl(quality.getRolekhl()+value);break;
		case "抗昏睡":quality.setRolekhs(quality.getRolekhs()+value);break;
		case "抗封印":quality.setRolekfy(quality.getRolekfy()+value);break;
		case "抗中毒":quality.setRolekzd(quality.getRolekzd()+value);break;
		case "抗人法":	
			quality.setRolekhl(quality.getRolekhl()+value);
			quality.setRolekhs(quality.getRolekhs()+value);
			quality.setRolekfy(quality.getRolekfy()+value);
			quality.setRolekzd(quality.getRolekzd()+value);
			break;
		case "抗中毒伤害":quality.setKzds(quality.getKzds()+value);break;
		case "抗遗忘":quality.setRolekyw(quality.getRolekyw()+value);break;
		case "抗鬼火":quality.setRolekgh(quality.getRolekgh()+value);break;
		case "抗三尸":quality.setRoleksc(quality.getRoleksc()+value);break;
		case "抗灵宝伤害":quality.setRoleklb(quality.getRoleklb()+value);break;
		case "抵御强克效果":quality.setK_qk(quality.getK_qk()+value);break;
		case "抗无属性伤害":quality.setK_wsxsh(quality.getK_wsxsh()+value);break;
		case "抗震慑":quality.setRolekzs(quality.getRolekzs()+value);break;
		case "抗震慑气血":quality.setK_zshp(quality.getK_zshp()+value);break;
		case "抗震慑魔法":quality.setK_zsmp(quality.getK_zsmp()+value);break;
		case "抗金箍":quality.setK_jge(quality.getK_jge()+value);break;
		case "抗情网":quality.setK_qw(quality.getK_qw()+value);break;
		case "抗浩然正气":quality.setK_ndhr(quality.getK_ndhr()+value);break;
		case "抗青面獠牙":quality.setK_ndqm(quality.getK_ndqm()+value);break;
		case "抗天魔解体":quality.setK_ndtm(quality.getK_ndtm()+value);break;
		case "抗小楼夜哭":quality.setK_ndxl(quality.getK_ndxl()+value);break;
		case "抗分光化影":quality.setK_ndfg(quality.getK_ndfg()+value);break;
		case "抗内丹":	
			quality.setK_ndhr(quality.getK_ndhr()+value);
			quality.setK_ndqm(quality.getK_ndqm()+value);
			quality.setK_ndtm(quality.getK_ndtm()+value);
			quality.setK_ndxl(quality.getK_ndxl()+value);
			quality.setK_ndfg(quality.getK_ndfg()+value);
			break;
		//忽视	
		case "忽视防御程度":quality.setRolehsfyv(quality.getRolehsfyv()+value);break;	
		case "忽视防御几率":quality.setRolehsfyl(quality.getRolehsfyl()+value);break;	
		case "忽视风法":quality.setRolehsff(quality.getRolehsff()+value);break;
		case "忽视雷法":quality.setRolehslf(quality.getRolehslf()+value);break;
		case "忽视水法":quality.setRolehssf(quality.getRolehssf()+value);break;
		case "忽视火法":quality.setRolehshf(quality.getRolehshf()+value);break;
		case "忽视仙法":	
			quality.setRolehsff(quality.getRolehsff()+value);
			quality.setRolehslf(quality.getRolehslf()+value);
			quality.setRolehssf(quality.getRolehssf()+value);
			quality.setRolehshf(quality.getRolehshf()+value);
			break;
		case "忽视混乱":quality.setRolehshl(quality.getRolehshl()+value);break;
		case "忽视昏睡":quality.setRolehshs(quality.getRolehshs()+value);break;
		case "忽视封印":quality.setRolehsfy(quality.getRolehsfy()+value);break;
		case "忽视中毒":quality.setRolehszd(quality.getRolehszd()+value);break;
		case "忽视人法":	
			quality.setRolehshl(quality.getRolehshl()+value);
			quality.setRolehshs(quality.getRolehshs()+value);
			quality.setRolehsfy(quality.getRolehsfy()+value);
			quality.setRolehszd(quality.getRolehszd()+value);
			break;
		case "忽视抗震慑":quality.setRolehszs(quality.getRolehszs()+value);break;
		case "忽视躲闪":quality.setRolehsds(quality.getRolehsds()+value);break;
		case "忽视反击":quality.setRolehsfj(quality.getRolehsfj()+value);break;
		case "忽视仙法抗性率":quality.setRolehsxfkl(quality.getRolehsxfkl()+value);break;
		case "忽视仙法抗性程度":	quality.setRolehsxfcd(quality.getRolehsxfcd()+value);break;
		case "忽视鬼火":quality.setRolehsgh(quality.getRolehsgh()+value);break;
		case "忽视遗忘":quality.setRolehsyw(quality.getRolehsyw()+value);break;
		//强	
		case "强风法":quality.setRoleqff(quality.getRoleqff()+value);break;
		case "强雷法":quality.setRoleqlf(quality.getRoleqlf()+value);break;
		case "强水法":quality.setRoleqsf(quality.getRoleqsf()+value);break;
		case "强火法":quality.setRoleqhf(quality.getRoleqhf()+value);break;
		case "强仙法":
			quality.setRoleqff(quality.getRoleqff()+value);
			quality.setRoleqlf(quality.getRoleqlf()+value);
			quality.setRoleqsf(quality.getRoleqsf()+value);
			quality.setRoleqhf(quality.getRoleqhf()+value);
			break;
		case "强混乱":quality.setRoleqhl(quality.getRoleqhl()+value);break;
		case "强昏睡":quality.setRoleqhs(quality.getRoleqhs()+value);break;
		case "强封印":quality.setRoleqfy(quality.getRoleqfy()+value);break;
		case "强中毒":quality.setRoleqzd(quality.getRoleqzd()+value);break;
		case "强人法":
			quality.setRoleqhl(quality.getRoleqhl()+value);
			quality.setRoleqhs(quality.getRoleqhs()+value);
			quality.setRoleqfy(quality.getRoleqfy()+value);
			quality.setRoleqzd(quality.getRoleqzd()+value);
			break;
			
		case "强中毒伤害":quality.setQzds(quality.getQzds()+value);break;	
		case "强鬼火":quality.setRolegstronghostfire(quality.getRolegstronghostfire()+value);break;
		case "强遗忘":quality.setRolestrongforget(quality.getRolestrongforget()+value);break;
		case "强三尸血":quality.setRolestrongforget(quality.getRolestrongbodyblood()+value);break;
		case "强三尸血回血程度":quality.setRolestrongbodyblooddeep(quality.getRolestrongbodyblooddeep()+value);break;
		case "增加强克效果":quality.setQ_qk(quality.getQ_qk()+value);break;
		case "对召唤兽伤害":quality.setQ_zhssh(quality.getQ_zhssh()+value);break;
		case "加强攻击法术效果":quality.setJqgjfs(quality.getJqgjfs()+value);break;
		case "加强防御法术效果":quality.setJqfyfs(quality.getJqfyfs()+value);break;
		case "加强速度法术效果":quality.setJqsdfs(quality.getJqsdfs()+value);break;
		case "加强霹雳效果":quality.setQlpl(quality.getQlpl()+value);break;
		case "加强扶摇效果":quality.setQlfy(quality.getQlfy()+value);break;
		case "加强沧波效果":quality.setQlcb(quality.getQlcb()+value);break;
		case "加强甘霖回血值":quality.setQlglv(quality.getQlglv()+value);break;
		case "加强甘霖回血程度":quality.setQlglc(quality.getQlglc()+value);break;
		//五行
		case "金":quality.setRolewxj(quality.getRolewxj()+value);break;
		case "木":quality.setRolewxm(quality.getRolewxm()+value);break;
		case "土":quality.setRolewxt(quality.getRolewxt()+value);break;
		case "水":quality.setRolewxs(quality.getRolewxs()+value);break;
		case "火":quality.setRolewxh(quality.getRolewxh()+value);break;
		case "强力克金":quality.setRolewxqkj(quality.getRolewxqkj()+value);break;
		case "强力克木":quality.setRolewxqkm(quality.getRolewxqkm()+value);break;
		case "强力克土":quality.setRolewxqkt(quality.getRolewxqkt()+value);break;
		case "强力克水":quality.setRolewxqks(quality.getRolewxqks()+value);break;
		case "强力克火":quality.setRolewxqkh(quality.getRolewxqkh()+value);break;
		//伤害
		case "无属性伤害":quality.setRolewsxsh(quality.getRolewsxsh()+value);break;
		case "风法伤害":quality.setRoleffsh(quality.getRoleffsh()+value);break;
		case "雷法伤害":quality.setRolelfsh(quality.getRolelfsh()+value);break;
		case "水法伤害":quality.setRolesfsh(quality.getRolesfsh()+value);break;
		case "火法伤害":quality.setRolehfsh(quality.getRolehfsh()+value);break;
		case "仙法伤害":
			quality.setRoleffsh(quality.getRoleffsh()+value);
			quality.setRolelfsh(quality.getRolelfsh()+value);
			quality.setRolesfsh(quality.getRolesfsh()+value);
			quality.setRolehfsh(quality.getRolehfsh()+value);
			break;
		case "毒伤害":quality.setRolezdsh(quality.getRolezdsh()+value);break;
		case "鬼火伤害":quality.setRoleghsh(quality.getRoleghsh()+value);break;
		case "三尸伤害":quality.setRolesssh(quality.getRolesssh()+value);break;
		//狂暴
		case "雷法狂暴":quality.setRolelfkb(quality.getRolelfkb()+value);break;
		case "风法狂暴":quality.setRoleffkb(quality.getRoleffkb()+value);break;
		case "水法狂暴":quality.setRolesfkb(quality.getRolesfkb()+value);break;
		case "火法狂暴":quality.setRolehfkb(quality.getRolehfkb()+value);break;
		case "仙法狂暴":
			quality.setRolelfkb(quality.getRolelfkb()+value);
			quality.setRoleffkb(quality.getRoleffkb()+value);
			quality.setRolesfkb(quality.getRolesfkb()+value);
			quality.setRolehfkb(quality.getRolehfkb()+value);
			break;
		case "鬼火狂暴":quality.setRoleghkb(quality.getRoleghkb()+value);break;
		case "三尸虫狂暴":quality.setRolesskb(quality.getRolesskb()+value);break;
		
		case "雷法狂暴程度":quality.setBlfcd(quality.getBlfcd()+value);break;
		case "风法狂暴程度":quality.setBffcd(quality.getBffcd()+value);break;
		case "水法狂暴程度":quality.setBsfcd(quality.getBsfcd()+value);break;
		case "火法狂暴程度":quality.setBhfcd(quality.getBhfcd()+value);break;
		case "仙法狂暴程度":
			quality.setBlfcd(quality.getBlfcd()+value);
			quality.setBffcd(quality.getBffcd()+value);
			quality.setBsfcd(quality.getBsfcd()+value);
			quality.setBhfcd(quality.getBhfcd()+value);
			break;
		case "鬼火狂暴程度":quality.setBghcd(quality.getBghcd()+value);break;
		case "三尸虫狂暴程度":quality.setBsccd(quality.getBsccd()+value);break;
		
		//其他
		case "躲闪率":quality.setRolefdsl(quality.getRolefdsl()+value);break;
		case "反击率":quality.setRoleffjl(quality.getRoleffjl()+value);break;
		case "反击次数":quality.setRoleffjv(quality.getRoleffjv()+value);break;
		case "连击率":quality.setRolefljl(quality.getRolefljl()+value);break;
		case "连击次数":quality.setRolefljv(quality.getRolefljv()+value);break;
		case "命中率":quality.setRolefmzl(quality.getRolefmzl()+value);break;
		case "致命率":quality.setRolefzml(quality.getRolefzml()+value);break;
		case "狂暴率":quality.setRolefkbl(quality.getRolefkbl()+value);break;
		case "反震率":quality.setRoleffzl(quality.getRoleffzl()+value);break;
		case "反震程度":quality.setRoleffzcd(quality.getRoleffzcd()+value);break;
		case "仙法连击率":quality.setRolexfljl(quality.getRolexfljl()+value);break;
		case "仙法连击次数":quality.setRolexfljs(quality.getRolexfljs()+value);break;
		case "附加封印攻击":quality.setF_f(quality.getF_f()+value);break;
		case "附加混乱攻击":quality.setF_h(quality.getF_h()+value);break;
		case "附加中毒攻击":quality.setF_d(quality.getF_d()+value);break;
		case "附加风法攻击":quality.setF_xf(quality.getF_xf()+value);break;
		case "附加火法攻击":quality.setF_xh(quality.getF_xh()+value);break;
		case "附加水法攻击":quality.setF_xs(quality.getF_xs()+value);break;
		case "附加雷法攻击":quality.setF_xl(quality.getF_xl()+value);break;
		case "附加震慑攻击":quality.setF_zs(quality.getF_zs()+value);break;
		case "附加三尸攻击":quality.setF_sc(quality.getF_sc()+value);break;
		case "伤害减免":quality.setEjs(quality.getEjs()+value);break;
		case "仙法伤害减免":quality.setExfjs(quality.getExfjs()+value);break;
		case "物理伤害减免":quality.setEwljs(quality.getEwljs()+value);break;
		case "抗震慑效果":quality.setEzsjs(quality.getEzsjs()+value);break;
		case "法术躲闪":quality.setEfsds(quality.getEfsds()+value);break;
		case "增伤":quality.setEzs(quality.getEzs()+value);break;
		case "物理增伤":quality.setEwlzs(quality.getEwlzs()+value);break;
		
		case "震慑躲闪":quality.setDzs(quality.getDzs()+value);break;
		case "火法躲闪":quality.setDhf(quality.getDhf()+value);break;
		case "雷法躲闪":quality.setDlf(quality.getDlf()+value);break;
		case "风法躲闪":quality.setDff(quality.getDff()+value);break;
		case "水法躲闪":quality.setDsf(quality.getDsf()+value);break;
		case "毒法躲闪":quality.setDdf(quality.getDdf()+value);break;
		case "封印躲闪":quality.setDfy(quality.getDfy()+value);break;
		case "混乱躲闪":quality.setDhl(quality.getDhl()+value);break;
		case "昏睡躲闪":quality.setDhs(quality.getDhs()+value);break;
		case "遗忘躲闪":quality.setDyw(quality.getDyw()+value);break;
		case "鬼火躲闪":quality.setDgh(quality.getDgh()+value);break;
		case "三尸虫躲闪":quality.setDsc(quality.getDsc()+value);break;
	    
		case "水法伤害减免":quality.setJsf(quality.getJsf()+value);break;
		case "风法伤害减免":quality.setJff(quality.getJff()+value);break;
		case "雷法伤害减免":quality.setJlf(quality.getJlf()+value);break;
		case "火法伤害减免":quality.setJhf(quality.getJhf()+value);break;
		case "鬼火伤害减免":quality.setJgh(quality.getJgh()+value);break;

//		default:
//			if (!ReadMonsterUtil.a.contains(key)) {
//				ReadMonsterUtil.a.add(key);
//			}
//			break;
		}
	}
	/**
	 * 增加或者减小对应属性
	 */
	public static void AddR(Ql quality,String key,double value){
		switch (key) {	
		    case "加强全系法术":quality.addQ(value);break;	
	        case "加强昏睡":
			quality.setRoleqhs(quality.getRoleqhs()+value);		break;	
			case "忽视抗睡":
			case "忽视抗昏睡":
	        quality.setRolehshs(quality.getRolehshs()+value);break;
	        case "加强中毒":
	        quality.setRoleqzd(quality.getRoleqzd()+value);break;
	        case "忽视抗毒":
	        case "忽视抗中毒":
	        quality.setRolehszd(quality.getRolehszd()+value);break;
	        case "无属性伤害":
	        case "加强无属性伤害":
	        quality.setRolewsxsh(quality.getRolewsxsh()+value);break;
	        case "加强封印":
			quality.setRoleqfy(quality.getRoleqfy()+value);break;
	        case "忽视抗封":
	        case "忽视抗封印":
			quality.setRolehsfy(quality.getRolehsfy()+value);break;
	        case "致命":
			case "致命率":
	       	quality.setRolefzml(quality.getRolefzml()+value);break;
			case "命中":
			case "命中率":
		    quality.setRolefmzl(quality.getRolefmzl()+value);break;
			case "狂暴":
	       	case "狂暴率":
	       	quality.setRolefkbl(quality.getRolefkbl()+value);break;
	       	case "连击":
	       	case "连击率":
	       	quality.setRolefljl(quality.getRolefljl()+value);break;
	        case "反击":
	        case "反击率":
	       	quality.setRoleffjl(quality.getRoleffjl()+value);break;
	        case "忽视防御程度":
			quality.setRolehsfyv(quality.getRolehsfyv()+value);break;
	        case "忽视防御几率":
			quality.setRolehsfyl(quality.getRolehsfyl()+value);break;
			case "加强混乱":
	      	quality.setRoleqhl(quality.getRoleqhl()+value);break;
	      	case "忽视抗混乱":
	      	case "忽视抗混":
		    quality.setRolehshl(quality.getRolehshl()+value);break;
	       	case "忽视抗震慑":
			quality.setRolehszs(quality.getRolehszs()+value);break;
	      	case "鬼火伤害":
	      	quality.setRoleghsh(quality.getRoleghsh()+value);break;
	        case "抗水":
	        case "抗水法":
	    	quality.setRoleksf(quality.getRoleksf()+value);break;
	    	case "抗火":
	    	case "抗火法":
	    	quality.setRolekhf(quality.getRolekhf()+value);break;
			case "抗雷":
			case "抗雷法":
	    	quality.setRoleklf(quality.getRoleklf()+value);break;
	    	case "抗风":
	    	case "抗风法":
	    	quality.setRolekff(quality.getRolekff()+value);break;
	    	case "抗昏睡":
	    	quality.setRolekhs(quality.getRolekhs()+value);break;
	    	case "抗混乱":
	    	quality.setRolekhl(quality.getRolekhl()+value);break;
	    	case "抗封印":
	    	quality.setRolekfy(quality.getRolekfy()+value);break;
	    	case "抗遗忘":
	    	quality.setRolekyw(quality.getRolekyw()+value);break;
	    	case "抗鬼火":
	    	quality.setRolekgh(quality.getRolekgh()+value);break;
	    	case "抗三尸":
	    	case "抗三尸虫":
	    	quality.setRoleksc(quality.getRoleksc()+value);break;
	    	case "抗中毒":
		    quality.setRolekzd(quality.getRolekzd()+value);break;
	    	case "抗震慑":
	    	quality.setRolekzs(quality.getRolekzs()+value);break;
	    	case "忽视抗雷":
	    	quality.setRolehslf(quality.getRolehslf()+value);break;
	    	case "忽视抗水":
	    	quality.setRolehssf(quality.getRolehssf()+value);break;
	    	case "忽视抗火":
	    	quality.setRolehshf(quality.getRolehshf()+value);break;
	    	case "忽视抗风":
	    	quality.setRolehsff(quality.getRolehsff()+value);break;
	    	case "雷法伤害":
	    	quality.setRolelfsh(quality.getRolelfsh()+value);break;
	    	case "水法伤害":
	    	quality.setRolesfsh(quality.getRolesfsh()+value);break;
	    	case "风法伤害":
	    	quality.setRoleffsh(quality.getRoleffsh()+value);break;
	    	case "火法伤害":
	    	quality.setRolehfsh(quality.getRolehfsh()+value);break;
	    	case "加强鬼火":
	    	quality.setRolegstronghostfire(quality.getRolegstronghostfire()+value);break;
	    	case "加强遗忘":
	    	quality.setRolestrongforget(quality.getRolestrongforget()+value);break;
	    	case "加三尸":
	    	case "加强三尸虫":
	        quality.setRolesssh(quality.getRolesssh()+value);break;
	    	case "加强三尸虫回血程度":
	    	quality.setRolestrongbodyblooddeep(quality.getRolestrongbodyblooddeep()+value);break;
	    	case "雷法狂暴":
	    	quality.setRolelfkb(quality.getRolelfkb()+value);break;
	    	case "火法狂暴":
	    	quality.setRolehfkb(quality.getRolehfkb()+value);break;
	    	case "水法狂暴": 
	    	quality.setRolesfkb(quality.getRolesfkb()+value);break;
	    	case "风法狂暴":
	    	quality.setRoleffkb(quality.getRoleffkb()+value);break;
	    	
			case "雷法狂暴程度":quality.setBlfcd(quality.getBlfcd()+value);break;
			case "风法狂暴程度":quality.setBffcd(quality.getBffcd()+value);break;
			case "水法狂暴程度":quality.setBsfcd(quality.getBsfcd()+value);break;
			case "火法狂暴程度":quality.setBhfcd(quality.getBhfcd()+value);break;
			case "仙法狂暴程度":
				quality.setBlfcd(quality.getBlfcd()+value);
				quality.setBffcd(quality.getBffcd()+value);
				quality.setBsfcd(quality.getBsfcd()+value);
				quality.setBhfcd(quality.getBhfcd()+value);
				break;
			case "鬼火狂暴程度":quality.setBghcd(quality.getBghcd()+value);break;
			case "三尸虫狂暴程度":quality.setBsccd(quality.getBsccd()+value);break;
	    	
	        case "物理吸收":
	        case "抗物理":
			quality.setRolekwl(quality.getRolekwl()+value);break;
	    	case "金":
	        quality.setRolewxj( quality.getRolewxj()+value);break;
	        case "木":
	        quality.setRolewxm(quality.getRolewxm()+value);break;
	        case "水":
	        quality.setRolewxs(quality.getRolewxs()+value);break;
	        case "火":
	        quality.setRolewxh( quality.getRolewxh()+value);break;
	        case "土":
	        quality.setRolewxt(quality.getRolewxt()+value);break;  
	        case "躲闪":
	        case "躲闪率":
	        case "物理躲闪":
	        quality.setRolefdsl(quality.getRolefdsl()+value);break;
	        case "连击次数":
	        quality.setRolefljv(quality.getRolefljv()+value);break;
	        case "反击次数":
	        quality.setRoleffjv(quality.getRoleffjv()+value);break;
	        case "加强风":
	        quality.setRoleqff(quality.getRoleqff()+value);break;
	        case "加强雷":
	        quality.setRoleqlf(quality.getRoleqlf()+value);break;
	        case "加强火":
	        quality.setRoleqhf(quality.getRoleqhf()+value);break;
	        case "加强水":
	        quality.setRoleqsf(quality.getRoleqsf()+value);break;
	    	case "强震慑":
	    	case "加强震慑":
		    quality.setRoleqzs(quality.getRoleqzs()+value);break;
	        case "强力克金":
	        quality.setRolewxqkj(quality.getRolewxqkj()+value);break;
	        case "强力克水":
	        quality.setRolewxqks(quality.getRolewxqks()+value);break;
	        case "强力克火":
	        quality.setRolewxqkh(quality.getRolewxqkh()+value);break;
	        case "强力克木":
	        quality.setRolewxqkm(quality.getRolewxqkm()+value);break;
	        case "强力克土":
	        quality.setRolewxqkt(quality.getRolewxqkt()+value);break;
	        case "反震程度":
	        quality.setRoleffzcd(quality.getRoleffzcd()+value);break;
	        case "反震率":
	        quality.setRoleffzl(quality.getRoleffzl()+value);break;
	        case "鬼火狂暴":
		    quality.setRoleghkb(quality.getRoleghkb()+value);break;
		    case "三尸虫狂暴":
		    quality.setRolesskb(quality.getRolesskb()+value);break;
		    case "忽视躲闪":
		    quality.setRolehsds(quality.getRolehsds()+value);break;
		    case "忽视反击":
		    quality.setRolehsfj(quality.getRolehsfj()+value);break;
		    case "仙法连击率":
		    quality.setRolexfljl(quality.getRolexfljl()+value);break;
		    case "仙法连击次数":
		    quality.setRolexfljs(quality.getRolexfljs()+value);break;
		    case "忽视仙法抗性率":
		    quality.setRolehsxfkl(quality.getRolehsxfkl()+value);break;
		    case "忽视仙法抗性程度":
		    quality.setRolehsxfcd(quality.getRolehsxfcd()+value);break;
		    case "忽视遗忘":
		    case "忽视抗遗忘":
		    quality.setRolehsyw(quality.getRolehsyw()+value);break;
		    case "忽视鬼火":
		    case "忽视抗鬼火":
		    quality.setRolehsgh(quality.getRolehsgh()+value);break;
		    case "加强攻击法术效果":
		    quality.setJqgjfs(quality.getJqgjfs()+value);break;
		    case "加强防御法术效果":
		    quality.setJqfyfs(quality.getJqfyfs()+value);break;
		    case "加强速度法术效果":
			quality.setJqsdfs(quality.getJqsdfs()+value);break;
		    case "增加强克效果":
		    quality.setQ_qk(quality.getQ_qk()+value);break;
		    case "抵御强克效果":
			quality.setK_qk(quality.getK_qk()+value);break;
		    case "抗无属性伤害":
			quality.setK_wsxsh(quality.getK_wsxsh()+value);break;
		    case "抗震慑气血":
			quality.setK_zshp(quality.getK_zshp()+value);break;
		    case "抗震慑魔法":
			quality.setK_zsmp(quality.getK_zsmp()+value);break;
		    case "对召唤兽伤害":
			quality.setQ_zhssh(quality.getQ_zhssh()+value);break;
		    case "抗金箍":
			quality.setK_jge(quality.getK_jge()+value);break;
		    case "抗情网":
			quality.setK_qw(quality.getK_qw()+value);break;
		    case "抗浩然正气":
			quality.setK_ndhr(quality.getK_ndhr()+value);break;
		    case "抗青面獠牙":
			quality.setK_ndqm(quality.getK_ndqm()+value);break;
		    case "抗天魔解体":
			quality.setK_ndtm(quality.getK_ndtm()+value);break;
		    case "抗小楼夜哭":
			quality.setK_ndxl(quality.getK_ndxl()+value);break;
		    case "抗分光化影":
			quality.setK_ndfg(quality.getK_ndfg()+value);break;
		    case "抗致命率":
		    quality.setKzml(quality.getKzml()+value);break;
		    case "附加水法攻击":
			quality.setF_xs(quality.getF_xs()+value);break;
		    case "附加雷法攻击":
			quality.setF_xl(quality.getF_xl()+value);break;
		    case "附加风法攻击":
			quality.setF_xf(quality.getF_xf()+value);break;
		    case "附加火法攻击":
			quality.setF_xh(quality.getF_xh()+value);break;
		    case "附加混乱攻击":
			quality.setF_h(quality.getF_h()+value);break;
		    case "附加封印攻击":
			quality.setF_f(quality.getF_f()+value);break;
		    case "附加毒法攻击":
			quality.setF_d(quality.getF_d()+value);break;
		    case "附加震慑攻击":
			quality.setF_zs(quality.getF_zs()+value);break;
		    case "附加三尸攻击":
			quality.setF_sc(quality.getF_sc()+value);break;
		    
		    case "仙法狂暴":
		    	quality.setRolelfkb(quality.getRolelfkb()+value);
		    	quality.setRolehfkb(quality.getRolehfkb()+value);
		    	quality.setRolesfkb(quality.getRolesfkb()+value);
		    	quality.setRoleffkb(quality.getRoleffkb()+value);break;
		    case "忽视仙法":
	    	    quality.setRolehslf(quality.getRolehslf()+value);
	    	    quality.setRolehssf(quality.getRolehssf()+value);
	    	    quality.setRolehshf(quality.getRolehshf()+value);
	    	    quality.setRolehsff(quality.getRolehsff()+value);break;
		    case "忽视人法":
	    	    quality.setRolehshs(quality.getRolehshs()+value);
	    	    quality.setRolehsfy(quality.getRolehsfy()+value);
	    	    quality.setRolehshl(quality.getRolehshl()+value);
	    	    quality.setRolehszd(quality.getRolehszd()+value);break;
		    case "强毒伤":
		    case "加强毒伤害":
				quality.setQzds(quality.getQzds()+value);break;	
		    case "抗毒伤":
				quality.setKzds(quality.getKzds()+value);break;	
		    case "抗风法狂暴":
				quality.setKbf(quality.getKbf()+value);break;	
		    case "抗火法狂暴":
					quality.setKbh(quality.getKbh()+value);break;	
		    case "抗水法狂暴":
					quality.setKbs(quality.getKbs()+value);break;	
		    case "抗雷法狂暴":
					quality.setKbl(quality.getKbl()+value);break;	
		    case "抗鬼火狂暴":
				quality.setKbg(quality.getKbg()+value);break;	
		    case "加强魅惑":
				quality.setQmh(quality.getQmh()+value);break;	
		    case "强金箍":
				quality.setQjg(quality.getQjg()+value);break;	
		    case "强情网":
				quality.setQqw(quality.getQqw()+value);break;	
		    case "封印狂暴":
				quality.setBfy(quality.getBfy()+value);break;	
		    case "混乱狂暴":
				quality.setBhl(quality.getBhl()+value);break;	
		    case "昏睡狂暴":
				quality.setBhs(quality.getBhs()+value);break;	
		    case "毒法狂暴":
				quality.setBzd(quality.getBzd()+value);break;	
		    case "加防狂暴":
				quality.setBjf(quality.getBjf()+value);break;	
		    case "加攻狂暴":
				quality.setBjg(quality.getBjg()+value);break;	
		    case "震慑狂暴":
				quality.setBzs(quality.getBzs()+value);break;	
		    case "遗忘狂暴":
				quality.setByw(quality.getByw()+value);break;	
		    case "魅惑狂暴":
				quality.setBmh(quality.getBmh()+value);break;	
				
		    case "法术躲闪":quality.setEfsds(quality.getEfsds()+value);break;	
		    case "伤害减免":quality.setEjs(quality.getEjs()+value);break;	
		    case "伤害加深":quality.setEzs(quality.getEzs()+value);break;	

			case "加强霹雳效果":quality.setQlpl(quality.getQlpl()+value);break;
			case "加强扶摇效果":quality.setQlfy(quality.getQlfy()+value);break;
			case "加强沧波效果":quality.setQlcb(quality.getQlcb()+value);break;
			case "加强甘霖回血值":quality.setQlglv(quality.getQlglv()+value);break;
			case "加强甘霖回血程度":quality.setQlglc(quality.getQlglc()+value);break;
		    
			case "震慑躲闪":quality.setDzs(quality.getDzs()+value);break;
			case "火法躲闪":quality.setDhf(quality.getDhf()+value);break;
			case "雷法躲闪":quality.setDlf(quality.getDlf()+value);break;
			case "风法躲闪":quality.setDff(quality.getDff()+value);break;
			case "水法躲闪":quality.setDsf(quality.getDsf()+value);break;
			case "毒法躲闪":quality.setDdf(quality.getDdf()+value);break;
			case "封印躲闪":quality.setDfy(quality.getDfy()+value);break;
			case "混乱躲闪":quality.setDhl(quality.getDhl()+value);break;
			case "昏睡躲闪":quality.setDhs(quality.getDhs()+value);break;
			case "遗忘躲闪":quality.setDyw(quality.getDyw()+value);break;
			case "鬼火躲闪":quality.setDgh(quality.getDgh()+value);break;
			case "三尸虫躲闪":quality.setDsc(quality.getDsc()+value);break;
		    
			case "水法伤害减免":quality.setJsf(quality.getJsf()+value);break;
			case "风法伤害减免":quality.setJff(quality.getJff()+value);break;
			case "雷法伤害减免":quality.setJlf(quality.getJlf()+value);break;
			case "火法伤害减免":quality.setJhf(quality.getJhf()+value);break;
			case "鬼火伤害减免":quality.setJgh(quality.getJgh()+value);break;

		}
	}
}
