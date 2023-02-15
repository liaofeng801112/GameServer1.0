package come.tool.Calculation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.come.entity.Goodstable;
import org.come.entity.Pal;
import org.come.model.PalData;
import org.come.model.Skill;
import org.come.server.GameServer;
import org.come.until.AllServiceUtil;

import come.tool.FightingData.FightingSkill;
import come.tool.FightingData.GetqualityUntil;
import come.tool.FightingData.ManData;
import come.tool.FightingData.Ql;

public class CalculationPal {

	public static void getPal(Pal pal,PalData palData,ManData data,int lvl,ManData master){
//		int lvl=pal.getLvl();
		int zs=master.getZs();
		Map<String,Double> map=new HashMap<>();
		int point=lvl+zs*40;//分配点数
		CalculationUtil.addValue(map,"根骨",lvl+point/palData.getSize()*palData.getJds()[0]);
		CalculationUtil.addValue(map,"灵性",lvl+point/palData.getSize()*palData.getJds()[1]);
		CalculationUtil.addValue(map,"力量",lvl+point/palData.getSize()*palData.getJds()[2]);
		CalculationUtil.addValue(map,"敏捷",lvl+point/palData.getSize()*palData.getJds()[3]);





		point=lvl%palData.getSize();
		for (int i = 0; i < palData.getJds().length; i++) {
			if (point<=0) {
				break;
			}else if (palData.getJds()[i]==0) {
				continue;
			}
			if (point>palData.getJds()[i]) {
				CalculationUtil.addValue(map,i==0?"根骨":i==1?"灵性":i==2?"力量":"敏捷", palData.getJds()[i]);
				point-=palData.getJds()[i];
			}else {
				CalculationUtil.addValue(map,i==0?"根骨":i==1?"灵性":i==2?"力量":"敏捷", point);
				point=0;
			}
		}
		if (palData.getQls()!=null) {
			for (int i = 0; i < palData.getQls().length; i++) {
				PalQl ql=palData.getQls()[i];
				double value=ql.getValue()+ql.getSv()*lvl;
				if (value!=0) {
					CalculationUtil.addValue(map,ql.getKey(),value);
				}
			}
		}
		if (palData.getSkills()!=null) {
			double sld=lvl*150;
			if (sld<10000) {sld=10000;}
			else if (sld>=25000) {sld=25000;}
			long qm=lvl*(zs+1)*10000;
			for (int i = 0; i < palData.getSkills().length; i++) {
				Skill skill=GameServer.getSkill(palData.getSkills()[i]);
				if (skill!=null) {
					if (skill.getSkillid()>=1001&&skill.getSkillid()<=1100) {
						FightingSkill fightingSkill=new FightingSkill(skill,lvl,zs,sld,0,0);
						data.addSkill(fightingSkill);
					}else if (skill.getSkillid()>=1500&&skill.getSkillid()<=2000) {
						FightingSkill fightingSkill=new FightingSkill(skill,lvl,zs,1,qm,0);
						data.addSkill(fightingSkill);
					}
				}
			}
		}
		if (pal.getParts()!=null&&!pal.getParts().equals("")) {
			String[] v=pal.getParts().split("\\|");
			for (int i = 0; i < v.length; i++) {
				String[] vs=v[i].split("=");
				Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[1]));
				if (good!=null) {
					BaseEquip equip=good.getEquip();
					if (equip!=null&&equip.getQls()!=null) {
						for (int j = equip.getQls().size()-1; j >=0; j--) {
							BaseQl baseQl=equip.getQls().get(j);
							CalculationUtil.addValue(map,baseQl.getKey(),baseQl.getValue());
						}
					}
				}
			}
		}

		data.setHuoyue(CalculationUtil.removeValue("根骨", map));
		data.setShanghai(CalculationUtil.removeValue("灵性", map));
		data.setKangluobao(CalculationUtil.removeValue("力量", map));
		data.setYuanzhu(CalculationUtil.removeValue("敏捷", map));
		data.setHp_z(getBase(pal.getLvl(), (int)data.getHuoyue()    , pal.getGrow(), palData.getHp(), 0, map));
		data.setMp_z(getBase(pal.getLvl(), (int)data.getShanghai()  , pal.getGrow(), palData.getMp(), 1, map));
		data.setAp(  getBase(pal.getLvl(), (int)data.getKangluobao(), pal.getGrow(), palData.getAp(), 2, map));
		data.setSp(  getBase(pal.getLvl(), (int)data.getYuanzhu()   , pal.getGrow(), palData.getSp(), 3, map));
		data.setHp(data.getHp_z());
		data.setMp(data.getMp_z());
		Ql ql=new Ql();
		double value=CalculationUtil.removeValue("四抗", map);
		if (value!=0) {
			CalculationUtil.addValue(map, "抗混乱",value);
			CalculationUtil.addValue(map, "抗封印",value);
			CalculationUtil.addValue(map, "抗昏睡",value);
			CalculationUtil.addValue(map, "抗遗忘",value);
		}
		value=CalculationUtil.removeValue("抗仙法", map);
		if (value!=0) {
			CalculationUtil.addValue(map, "抗风",value);
			CalculationUtil.addValue(map, "抗火",value);
			CalculationUtil.addValue(map, "抗水",value);
			CalculationUtil.addValue(map, "抗雷",value);
		}
		for (String key:map.keySet()) {
			GetqualityUntil.AddR(ql,key,map.get(key));
		}
		data.setQuality(ql);
	}
	public static int getBase(int lvl,int P,double G, int base, int type,Map<String,Double> map){
		int value=BaseValue.getPetValue(lvl,P,G,base,type);
		if (type==0) {
			value+=CalculationUtil.removeValue("hp",map);
			value+=CalculationUtil.removeValue("HP",map);
			value+=CalculationUtil.removeValue("加气血",map);
			value*=(CalculationUtil.removeValue("HP成长",map)+1);
			value*=(CalculationUtil.removeValue("加强气血",map)/100+1);
		}else if (type==1) {
			value+=CalculationUtil.removeValue("mp",map);
			value+=CalculationUtil.removeValue("MP",map);
			value+=CalculationUtil.removeValue("加魔法",map);
			value*=(CalculationUtil.removeValue("MP成长",map)+(CalculationUtil.removeValue("加强魔法",map)/100)+1);
		}else if (type==2) {
			value+=CalculationUtil.removeValue("ap",map);
			value+=CalculationUtil.removeValue("AP",map);
			value+=CalculationUtil.removeValue("攻击",map);
			value+=CalculationUtil.removeValue("加攻击",map);
			value*=(CalculationUtil.removeValue("AP成长",map)+(CalculationUtil.removeValue("加强攻击",map)/100)+1);
		}else if (type==3) {
			value+=CalculationUtil.removeValue("sp",map);
			value+=CalculationUtil.removeValue("SP",map);
			value+=CalculationUtil.removeValue("速度",map);
			value+=CalculationUtil.removeValue("加速度",map);
			value*=(CalculationUtil.removeValue("SP成长",map)+(CalculationUtil.removeValue("加强速度",map)/100)+1);
		}
		return value;
	}
}
