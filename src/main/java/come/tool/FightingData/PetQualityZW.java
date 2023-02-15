package come.tool.FightingData;

public class PetQualityZW {
	/**
	 * 增加或者减小对应属性
	 */
	public static void insertValues(Ql quality,String key,double value){
		switch (key) {	
		    case "加强风":
		    quality.setRoleqff(quality.getRoleqff()+value);break;
		    case "加强雷":
		    quality.setRoleqlf(quality.getRoleqlf()+value);break;
		    case "加强火":
		    quality.setRoleqhf(quality.getRoleqhf()+value);break;
		    case "加强水":
		    quality.setRoleqsf(quality.getRoleqsf()+value);break;	       
	        case "加强昏睡":
			quality.setRoleqhs(quality.getRoleqhs()+value);break;	
	        case "加强中毒":
	        quality.setRoleqzd(quality.getRoleqzd()+value);break;
	        case "加强封印":
			quality.setRoleqfy(quality.getRoleqfy()+value);break;
			case "加强混乱":
		    quality.setRoleqhl(quality.getRoleqhl()+value);break;
			case "加强鬼火":
		    quality.setRolegstronghostfire(quality.getRolegstronghostfire()+value);break;
		    case "加强遗忘":
		    quality.setRolestrongforget(quality.getRolestrongforget()+value);break;
		    case "加强三尸虫":
		    quality.setRolestrongbodyblood(quality.getRolestrongbodyblood()+value);break;
		    case "加强三尸虫回血程度":
		    quality.setRolestrongbodyblooddeep(quality.getRolestrongbodyblooddeep()+value);break;

		    case "忽视鬼火":
		    quality.setRolehshs(quality.getRolehsgh()+value);break;
		    case "忽视遗忘":
		    quality.setRolehszd(quality.getRolehsyw()+value);break;		    
		    case "忽视抗睡":
	        quality.setRolehshs(quality.getRolehshs()+value);break;
	        case "忽视抗毒":
	        quality.setRolehszd(quality.getRolehszd()+value);break;
	        case "忽视抗封":
			quality.setRolehsfy(quality.getRolehsfy()+value);break;
	        case "忽视防御程度":
			quality.setRolehsfyv(quality.getRolehsfyv()+value);break;
		    case "忽视防御几率":
			quality.setRolehsfyl(quality.getRolehsfyl()+value);break;
			case "忽视混乱":
		    quality.setRolehshl(quality.getRolehshl()+value);break; 	
			case "忽视抗雷":
		    quality.setRolehslf(quality.getRolehslf()+value);break;
		    case "忽视抗水":
		    quality.setRolehssf(quality.getRolehssf()+value);break;
		    case "忽视抗火":
		    quality.setRolehshf(quality.getRolehshf()+value);break;
		    case "忽视抗风":
		    quality.setRolehsff(quality.getRolehsff()+value);break; 
		    case "忽视反击":
		    quality.setRolehsfj(quality.getRolehsfj()+value);break; 
		    case "忽视仙法抗性率":
		    quality.setRolehsxfkl(quality.getRolehsxfkl()+value);break; 
		    case "忽视仙法抗性程度":
		    quality.setRolehsxfcd(quality.getRolehsxfcd()+value);break; 	
		    case "仙法连击率":
			quality.setRolexfljl(quality.getRolexfljl()+value);break; 
			case "仙法连击次数":
			quality.setRolexfljs(quality.getRolexfljs()+value);break; 	
			    
		    case "雷法伤害":
		    quality.setRolelfsh(quality.getRolelfsh()+value);break;
		    case "水法伤害":
		    quality.setRolesfsh(quality.getRolesfsh()+value);break;
		    case "风法伤害":
		    quality.setRoleffsh(quality.getRoleffsh()+value);break;
		    case "火法伤害":
		    quality.setRolehfsh(quality.getRolehfsh()+value);break;
		    case "鬼火伤害":
		    quality.setRoleghsh(quality.getRoleghsh()+value);break;		
	        case "无属性伤害":
	        quality.setRolewsxsh(quality.getRolewsxsh()+value);break;	       	
	        case "抗水":
	    	quality.setRoleksf(quality.getRoleksf()+value);break;
	    	case "抗火":
	    	quality.setRolekhf(quality.getRolekhf()+value);break;
			case "抗雷":
	    	quality.setRoleklf(quality.getRoleklf()+value);break;
	    	case "抗风":
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
	    	quality.setRoleksc(quality.getRoleksc()+value);break;
	    	case "抗震慑":
	    	quality.setRolekzs(quality.getRolekzs()+value);break;
	    	
	    	case "雷法狂暴":
	    	quality.setRolelfkb(quality.getRolelfkb()+value);break;
	    	case "火法狂暴":
	    	quality.setRolehfkb(quality.getRolehfkb()+value);break;
	    	case "水法狂暴": 
	    	quality.setRolesfkb(quality.getRolesfkb()+value);break;
	    	case "风法狂暴":
	    	quality.setRoleffkb(quality.getRoleffkb()+value);break;
	    	case "鬼火狂暴":
	    	quality.setRoleffkb(quality.getRoleghkb()+value);break;
	    	case "三尸虫狂暴":
	    	quality.setRoleffkb(quality.getRolesskb()+value);break;
	    	
	        case "致命率":
	       	quality.setRolefmzl(quality.getRolefmzl()+value);break;
	    	case "狂暴":
	       	case "狂暴率":
	       	quality.setRolefkbl(quality.getRolefkbl()+value);break;
	       	case "连击率":
	       	quality.setRolefljl(quality.getRolefljl()+value);break;
	        case "反击率":
	       	quality.setRoleffjl(quality.getRoleffjl()+value);break;
	        case "物理吸收":
			quality.setRolekwl(quality.getRolekwl()+value);break;
	        case "躲闪率":
		    quality.setRolefdsl(quality.getRolefdsl()+value);break;
		    case "连击次数":
		    quality.setRolefljv(quality.getRolefljv()+value);break;
		    case "反击次数":
		    quality.setRoleffjv(quality.getRoleffjv()+value);break;
		    case "反震程度":
		    quality.setRoleffzcd(quality.getRoleffzcd()+value);break;
		    case "反震率":
		    quality.setRoleffzl(quality.getRoleffzl()+value);break;
		    case "命中率":
		    case "忽视躲闪":
			quality.setRolefmzl(quality.getRolefmzl()+value);break;	   
			
	    	case "金":
	        quality.setRolewxj( quality.getRolewxj()+value);break;
	        case "木":
	        quality.setRolewxm(quality.getRolewxm()+value);break;
	        case "水":
	        quality.setRolewxs(quality.getRolewxs()+value);break;
	        case "火":
	        quality.setRolewxh(quality.getRolewxh()+value);break;
	        case "土":
	        quality.setRolewxt(quality.getRolewxt()+value);break;
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
		    
		    
	        }
		
	}
}
