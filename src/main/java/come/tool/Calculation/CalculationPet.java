package come.tool.Calculation;

import java.math.BigDecimal;

import org.come.action.suit.SuitPetEquip;
import org.come.entity.Goodstable;
import org.come.entity.RoleSummoning;
import org.come.model.Skill;
import org.come.tool.CustomFunction;

import come.tool.Battle.BattleMixDeal;
import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.GetqualityUntil;
import come.tool.FightingData.Ql;

public class CalculationPet {
	/**根据技能id添加*/
	public static void addQlSkill(Ql ql, int id, long qm) {
		double value;
		switch (id) {
		case 1810:
			value = 5 + CustomFunction.XS(qm, 0.3);
			GetqualityUntil.AddR(ql, "抗风", -value);
			GetqualityUntil.AddR(ql, "抗火", -value);
			GetqualityUntil.AddR(ql, "抗水", -value);
			GetqualityUntil.AddR(ql, "抗雷", -value);
			GetqualityUntil.AddR(ql, "抗鬼火", -value);
			GetqualityUntil.AddR(ql, "反震率", value);
			GetqualityUntil.AddR(ql, "反震程度", value);
			break;
		case 1811:
			value = 10 + CustomFunction.XS(qm, 0.6);
			GetqualityUntil.AddR(ql, "抗风", -value);
			GetqualityUntil.AddR(ql, "抗火", -value);
			GetqualityUntil.AddR(ql, "抗水", -value);
			GetqualityUntil.AddR(ql, "抗雷", -value);
			GetqualityUntil.AddR(ql, "抗鬼火", -value);
			GetqualityUntil.AddR(ql, "反震率", value);
			GetqualityUntil.AddR(ql, "反震程度", value);
			break;
		case 1813:
		case 1820:
		case 1821:
		case 1822:
		case 1823:
		case 1824:
			value = 2 + CustomFunction.XS(qm, 0.2);
			GetqualityUntil.AddR(ql, "抗封印", value);
			GetqualityUntil.AddR(ql, "抗混乱", value);
			GetqualityUntil.AddR(ql, "抗遗忘", value);
			break;
		case 1815:
			value = 2 + CustomFunction.XS(qm, 0.2);
			GetqualityUntil.AddR(ql, "金", 50);
			GetqualityUntil.AddR(ql, "抗封印", value);
			GetqualityUntil.AddR(ql, "抗混乱", value);
			GetqualityUntil.AddR(ql, "抗遗忘", value);
			break;
		case 1816:
			value = 2 + CustomFunction.XS(qm, 0.2);
			GetqualityUntil.AddR(ql, "木", 50);
			GetqualityUntil.AddR(ql, "抗封印", value);
			GetqualityUntil.AddR(ql, "抗混乱", value);
			GetqualityUntil.AddR(ql, "抗遗忘", value);
			break;
		case 1817:
			value = 2 + CustomFunction.XS(qm, 0.2);
			GetqualityUntil.AddR(ql, "水", 50);
			GetqualityUntil.AddR(ql, "抗封印", value);
			GetqualityUntil.AddR(ql, "抗混乱", value);
			GetqualityUntil.AddR(ql, "抗遗忘", value);
			break;
		case 1818:
			value = 2 + CustomFunction.XS(qm, 0.2);
			GetqualityUntil.AddR(ql, "火", 50);
			GetqualityUntil.AddR(ql, "抗封印", value);
			GetqualityUntil.AddR(ql, "抗混乱", value);
			GetqualityUntil.AddR(ql, "抗遗忘", value);
			break;
		case 1819:
			value = 2 + CustomFunction.XS(qm, 0.2);
			GetqualityUntil.AddR(ql, "土", 50);
			GetqualityUntil.AddR(ql, "抗封印", value);
			GetqualityUntil.AddR(ql, "抗混乱", value);
			GetqualityUntil.AddR(ql, "抗遗忘", value);
			break;
		case 1207:
			value = 3 + CustomFunction.XS(qm, 0.2);
			GetqualityUntil.AddR(ql, "连击率", value);
			break;
		case 1208:
			value = 10 + CustomFunction.XS(qm, 0.6);
			GetqualityUntil.AddR(ql, "忽视防御几率", value);
			GetqualityUntil.AddR(ql, "忽视防御程度", value);
			break;
		case 1209:
			value = 5 + CustomFunction.XS(qm, 0.3);
			GetqualityUntil.AddR(ql, "狂暴率", value);
			GetqualityUntil.AddR(ql, "致命率", value);
			break;
		case 1213:
			value = 5 + CustomFunction.XS(qm, 0.3);
			GetqualityUntil.AddR(ql, "反震率", value);
			GetqualityUntil.AddR(ql, "反震程度", value);
			break;
		case 1214:
			value = 5 + CustomFunction.XS(qm, 0.4);
			GetqualityUntil.AddR(ql, "狂暴率", value);
			GetqualityUntil.AddR(ql, "致命率", value);
			GetqualityUntil.AddR(ql, "命中率", value);
			break;
		case 1222:
			value = 5 + CustomFunction.XS(qm, 0.4);
			GetqualityUntil.AddR(ql, "抗震慑", value);
			break;
		case 1226:
			value = 10 + CustomFunction.XS(qm, 0.6);
			GetqualityUntil.AddR(ql, "物理吸收", value);
			break;
		case 1845:
			value = 5 + CustomFunction.XS(qm, 0.5);
			GetqualityUntil.AddR(ql, "附加封印攻击", value);
			break;
		case 1846:
			value = 5 + CustomFunction.XS(qm, 0.5);
			GetqualityUntil.AddR(ql, "附加混乱攻击", value);
			break;
		case 1855:
			value = 10 + CustomFunction.XS(qm, 0.6);
			GetqualityUntil.AddR(ql, "抗致命率", value);
			break;
		case 1856:
			value = 10 + CustomFunction.XS(qm, 0.6);
			GetqualityUntil.AddR(ql, "仙法狂暴", value);
			break;
		case 1857:
			value = 10 + CustomFunction.XS(qm, 0.6);
			GetqualityUntil.AddR(ql, "忽视仙法", value);
			break;
		case 1859:
			value = 3 + CustomFunction.XS(qm, 0.3);
			GetqualityUntil.AddR(ql, "抗封印", value);
			GetqualityUntil.AddR(ql, "抗混乱", value);
			GetqualityUntil.AddR(ql, "抗遗忘", value);
			break;
		case 1860:
			value = 5 + CustomFunction.XS(qm, 0.5);
			GetqualityUntil.AddR(ql, "附加三尸攻击", value);
			break;
		case 1235:
			GetqualityUntil.AddR(ql, "伤害减免", 15);
			break;
		}
	}
	//抗性型内丹加抗性方法
	public static void addNedanMsg(RoleSummoning roleSummoning,Ql ql,int nddj,int ndzscs,String goodsname){
		int zhsdj = BattleMixDeal.petLvlint(roleSummoning.getGrade());//召唤兽等级
		int zhszscs = roleSummoning.getTurnRount();//召唤兽转生次数
		long zhsqm = roleSummoning.getFriendliness();//召唤兽亲密值
		if(goodsname.equals("暗度陈仓")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
			GetqualityUntil.AddR(ql,"忽视躲闪",Math.round(ndjl*10000)/100D);
			GetqualityUntil.AddR(ql,"忽视反击",Math.round(ndjl*10000)/100D);
		}else if(goodsname.equals("凌波微步")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.00001;
			GetqualityUntil.AddR(ql,"躲闪率",Math.round(ndjl*10000)/100D);
		}else if(goodsname.equals("借力打力")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.00001;
			int ndcd_jldl=nd_jldl_fjcs(ndjl);
			GetqualityUntil.AddR(ql,"反击率",Math.round(ndjl*10000)/100D);
			GetqualityUntil.AddR(ql,"反击次数",ndcd_jldl);
		}else if(goodsname.equals("梅花三弄")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
			int ndcd_jldl=nd_mhsn_ljcs(ndjl);
			GetqualityUntil.AddR(ql,"仙法连击率",Math.round(ndjl*10000)/100D);
			GetqualityUntil.AddR(ql,"仙法连击次数",ndcd_jldl);
		}else if(goodsname.equals("红颜白发")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
			GetqualityUntil.AddR(ql,"仙法狂暴",Math.round(ndjl*10000)/100D);
		}else if(goodsname.equals("开天辟地")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
			GetqualityUntil.AddR(ql,"忽视仙法抗性率",Math.round(ndjl*10000)/100D);
			GetqualityUntil.AddR(ql,"忽视仙法抗性程度",Math.round(ndjl*10000)/100D);
		}else if(goodsname.equals("万佛朝宗")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
			double ndcd=ndjl*2;
			if (roleSummoning.getPetSkills().contains("1247")) {
				GetqualityUntil.AddR(ql,"反震率",Math.round(ndjl*10100)/100D);
				GetqualityUntil.AddR(ql,"反震程度",Math.round(ndcd*10100)/100D);
			}else {
				GetqualityUntil.AddR(ql,"反震率",Math.round(ndjl*10000)/100D);
				GetqualityUntil.AddR(ql,"反震程度",Math.round(ndcd*10000)/100D);
			}
		}
	}
	//非抗性型内丹获取几率、伤害率的方法
	public static FightingSkill accessNedanMsg(Goodstable goodstable,int nddj,int ndzscs,int zhsdj,int zhszscs,long zhsqm,int zhsmpz){	
		zhsmpz+=100;
		if(goodstable.getGoodsname().equals("浩然正气")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
			double ndcd=Math.floor((zhsdj*zhsdj*0.2/(zhsmpz*1+1)+ndjl)*10000)/10000;
			return new FightingSkill(goodstable.getGoodsname(), Math.round(ndjl*10000)/100D, Math.round(ndcd*10000)/100D);
		}else if(goodstable.getGoodsname().equals("隔山打牛")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000005;
			double ndcd=Math.floor((zhsdj*zhsdj*0.2/(zhsmpz*1+1)+ndjl*3)*1000)/1000;	
			return new FightingSkill(goodstable.getGoodsname(), Math.round(ndjl*10000)/100D, Math.round(ndcd*10000)/100D);
		}else if(goodstable.getGoodsname().equals("天魔解体")){
			double ndjl = 0;
			if(zhszscs==0)ndjl=Math.floor((Math.pow(zhsdj*nddj/160000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/4000)*1000)/1000+0.01;
			else ndjl=Math.floor((Math.pow(zhsdj*nddj/160000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/3755)*1000)/1000+0.01;
			double ndhq=Math.floor(ndjl*(zhsdj*zhsdj*0.15/(zhsmpz*1+0.01)+0.2)*1000)/1000;
			return new FightingSkill(goodstable.getGoodsname(), Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
		}else if(goodstable.getGoodsname().equals("分光化影")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj/160000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/4000)*1000)/1000+0.01;
			double ndhq=Math.floor(ndjl*(zhsdj*zhsdj*0.15/(zhsmpz*1+0.01)+0.2)*1000)/1000;
			return new FightingSkill(goodstable.getGoodsname(), Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
		}else if(goodstable.getGoodsname().equals("小楼夜哭")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj/206600.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/4170)*1000)/1000+0.01;
			double ndhq=ndjl*0.3;
			return new FightingSkill(goodstable.getGoodsname(), Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
		}else if(goodstable.getGoodsname().equals("青面獠牙")){
			double ndjl=Math.floor((Math.pow(zhsdj*nddj/698000.0,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*nddj/7500)*1000)/1000+0.01;
			double ndhq=ndjl*0.7;
			return new FightingSkill(goodstable.getGoodsname(), Math.round(ndjl*10000)/100D, Math.round(ndhq*10000)/100D);
		}else {
			double ndjl=Math.floor((Math.pow(zhsdj*nddj*0.04,1.0/2.0)*(1+0.25*ndzscs)+Math.pow(zhsqm,1.0/6.0)*xstz(zhszscs,ndzscs)*nddj/50)*1000)*0.000004;
			double ndcd=Math.floor(296.1572+0.0002364957*Math.pow(zhsmpz,1.57));
			return new FightingSkill(goodstable.getGoodsname(), Math.round(ndjl*10000)/100D, ndcd);
		}
	}
	//获得转生系数的方法
	public static double xstz(int zhs_zscs,int nd_zscs){
		if(zhs_zscs*nd_zscs==1)return 1.04;
		else if(zhs_zscs*nd_zscs==4)return 1.071;
		else if(zhs_zscs*nd_zscs==6)return 1.073;
		else if(zhs_zscs*nd_zscs==9)return 1.09;
		else return 1;
	}
	//获得反击次数的方法
	public static int nd_jldl_fjcs(double jl){
		if(jl>0.56)return 10;
		else if(jl>0.51)return 8;
		else if(jl>0.45)return 7;
		else if(jl>0.39)return 6;
		else if(jl>0.32)return 5;
		else if(jl>0.25)return 4;
		else if(jl>0.17)return 3;
		else if(jl>0.09)return 2;
		else return 1;
	}
	//获得连击次数的方法
	public static int nd_mhsn_ljcs(double mhsn){
		if(mhsn>0.28)return 5;
		else if(mhsn>0.21)return 4;
		else if(mhsn>0.14)return 3;
		else if(mhsn>0.7)return 2;
		else return 1;
	}
	/**根据觉醒技返回技能*/
	public static FightingSkill JX(BaseSkill jx1,BaseSkill jx2,BaseSkill jx3){
	    if (jx1==null||jx2==null||jx3==null) {return null;}
	    if (jx1.getSkillId()!=jx2.getSkillId()||jx1.getSkillId()!=jx3.getSkillId()) {return null;}
	    Skill skill=jx1.getSkill();
	    if (skill==null) {return null;}
	    double sld=averageMath(jx1.getPz(),  jx2.getPz(),  jx3.getPz() ).doubleValue();
	    long   exp=averageMath(jx1.getLvl(), jx2.getLvl(), jx3.getLvl()).longValue();
	    int    lvl=SuitPetEquip.expChangeLevel(exp);
	    FightingSkill fightingSkill=new FightingSkill(skill,lvl, 0, sld, 0, 0);
		return fightingSkill;
	}
	/** 三个参数求平均 */
	public static BigDecimal averageMath(double one, double two, double three) {
		return (new BigDecimal(one).add(new BigDecimal(two)).add(new BigDecimal(three))).divide(new BigDecimal(3), 2, BigDecimal.ROUND_HALF_UP);
	}
}
