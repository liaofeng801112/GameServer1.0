package come.tool.FightingData;

public class FBUtil {
	/**获取系数 转生次数 人物等级  法宝总等级  法宝品质  法宝等级*/
	public static int getFBlvl(int t,int g,int zlvl,int qv,int lvl){
//		200  70基础 30是等级  100是总等级
		int pj=0;
		g+=t*25;
		pj+=g*3;
		pj+=zlvl;
		qv+=lvl;
		pj+=qv*7;
		return pj>>5;	
	}
	//获取持续回合数
	public static int getFBcx(int id,int lvl){
			if (id==5007||id==5010||id==5012||id==5015) {
				return 0;
			}else if (id==5006) {
				if (lvl>=158) {
					return 7;
				}else if (lvl>=112) {
					return 6;
				}else if (lvl>=65) {
					return 5;
				}else if (lvl>=20) {
					return 4;
				}
				return 3;
			}else if (id==5009) {
			    if (lvl>=182) {
			    	return 8;
				}else if (lvl>=138) {
					return 7;
				}else if (lvl>=94) {
					return 6;
				}else if (lvl>=51) {
					return 5;
				}else if (lvl>=7) {
					return 4;
				}
				return 3;
			}else {
				    if (lvl>=170) { return 7; }
				    else if (lvl>=140) { return 6; }
					else if (lvl>=110) { return 5; }
					else if (lvl>=80)  { return 4; }
					else if (lvl>=50)  { return 3; }
					return 2;
			}	
	 }
	//获取法宝作用目标数
	public static int getFBsum(int id,int lvl){
		if (id==5003||id==5006||id==5008||id==5011||id==5014||id==5015) {
			return 1;
		}
		if (lvl>=100) {
			return 2;
		}
		return 1;
		
	} 
}
