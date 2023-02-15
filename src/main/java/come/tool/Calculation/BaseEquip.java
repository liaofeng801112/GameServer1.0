package come.tool.Calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.action.suit.SuitComposeAction;
import org.come.entity.Goodstable;
import org.come.model.Skill;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;

public class BaseEquip {

	private static String[] BaseLimits=new String[]{"力量要求","灵性要求","根骨要求","敏捷要求","属性需求","装备角色","等级要求","最高携带等级","性别"};
	//限制条件
	private BaseLimit baseLimit;
	//基础属性
	private List<BaseQl> qls;
	//额外属性
	private List<BaseQl> qlews;
	//特技
	private List<BaseSkill> baseSkills;
	//套装id
	private BaseSuit baseSuit;
	//星阵
	private BaseStar baseStar;
	//强化等级
	private Integer qhv;
	//耐久度
	private Integer NJ;
	
	public BaseEquip(String value,long type) {
		// TODO Auto-generated constructor stub
		try {
			String[] vs=value.split("\\|");		
			if (type==8888) {//翅膀
				initWing(vs);
			}else if (type==520) {//星卡
				initStar(vs);
			}else if (type==510||type==511||type==512) {//召唤兽装备
				initPetEquip(vs);
			}else {//正常装备
				init(vs);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	public static double getQHGemXS(int lvl){
		double xs=0D;
		for (int i = 1; i <=lvl; i++) {
			xs+=((i-1)/3+1)*0.8;
		}
		return xs;
	}
	/**正常装备*/
	public void init(String[] vs){
		for (int i = 0; i < vs.length; i++) {
			if (vs[i].startsWith(SuitComposeAction.Extras[0])) {//炼化属性
				String[] vStrings = vs[i].split("&");
				for (int j = 1; j < vStrings.length; j++) {
					String[] mes = vStrings[j].split("=");
					if (mes[0].equals("特技")) {
						for (int l = 1; l < mes.length; l++) {
							Skill skill=GameServer.getSkill(mes[l]);
							if (skill!=null) {
								if (skill.getSkillid()==8016||skill.getSkillid()==8017) {//无属性//无级别
									addLimit(skill.getSkillid(), null);
								}else {
									BaseSkill baseSkill=new BaseSkill(skill.getSkillid(), 1, skill, null);
									addSkill(baseSkill);	
								}	
							}
						}
					}else if (mes.length==2) {
						try {
							addQlEW(mes[0], Double.parseDouble(mes[1]));
							//addQl(mes[0], Double.parseDouble(mes[1]));		
						} catch (Exception e) {
							// TODO: handle exception
							WriteOut.addtxt("转换报错:"+mes[0]+"="+mes[1],9999);
						}
					}		
				}
			}else if (vs[i].startsWith(SuitComposeAction.Extras[1])) {//炼器
				String[] vStrings = vs[i].split("&");
				for (int j = 2; j < vStrings.length; j++) {
					addKeyEW(vStrings[j]);	
				}
			}else if (vs[i].startsWith(SuitComposeAction.Extras[2])) {//神兵
				String[] vStrings = vs[i].split("&");
				for (int j = 1; j < vStrings.length; j++) {
					addKeyEW(vStrings[j]);	
				}
			}else if (vs[i].startsWith(SuitComposeAction.Extras[3])) {//套装
				String[] vStrings = vs[i].split("&");
				for (int j = 4; j < vStrings.length; j++) {
					addKeyEW(vStrings[j]);	
				}
				baseSuit=new BaseSuit(Integer.parseInt(vStrings[1]), BaseValue.getQv(vStrings[3])/10);
			}else if (vs[i].startsWith(SuitComposeAction.Extras[4])) {//宝石
				String[] vStrings = vs[i].split("&");
				for (int j = 1; j < vStrings.length; j++) {
				     Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vStrings[j]));
				     if (good!=null) {
				    	 String[] mes = good.getValue().split("\\|");
				    	 for (int k = 1; k < mes.length; k++) {
							String[] mess=mes[k].split("=");
							if (mess.length==2) {
								try {
									addQlEW(mess[0], Double.parseDouble(mess[1]));
									//addQl(mess[0], Double.parseDouble(mess[1]));		
								} catch (Exception e) {
									// TODO: handle exception
									WriteOut.addtxt("转换报错:"+mess[0]+"="+mess[1],9999);
								}
							}
						}
					 }
			    }
			}else {
				addKey(vs[i]);		
			}
		}
	}
	/**召唤兽装备*/
	public void initPetEquip(String[] vs){
//		等级=1|装备部位=兽环|等级需求=0转100级|敏捷=9|品质=90|通灵=230|炼化属性&分光化影等级=1&强力克金=0.7|觉醒技&1333&2.2&0
	    for (int i = 3; i < vs.length; i++) {
	    	if (vs[i].startsWith(SuitComposeAction.Extras[0])) {//炼化属性
				String[] vStrings = vs[i].split("&");
				for (int j = 1; j < vStrings.length; j++) {
					String[] mes = vStrings[j].split("=");
					addQl(mes[0], Double.parseDouble(mes[1]));
				}
			}else if (vs[i].startsWith(SuitComposeAction.Extras[5])) {//觉醒技
				String[] vStrings = vs[i].split("&");
				Skill skill=GameServer.getSkill(vStrings[1]);
				if (skill!=null) {
					int skillId=skill.getSkillid();
					double pz=Double.parseDouble(vStrings[2]);
					int exp=Integer.parseInt(vStrings[3]);
					BaseSkill baseSkill=new BaseSkill(skillId, exp, pz, skill);
					addSkill(baseSkill);
				}
				
			}else {
				String[] mes = vs[i].split("=");
				if (mes.length==2) {
					if (mes[0].equals("根骨")||mes[0].equals("灵性")||mes[0].equals("力量")||mes[0].equals("敏捷")||mes[0].equals("定力")) {
						try {
							addQl(mes[0], Double.parseDouble(mes[1]));		
						} catch (Exception e) {
							// TODO: handle exception
							WriteOut.addtxt("转换报错:"+mes[0]+"="+mes[1],9999);
						}
					}
				}
			}
		}
	}
	/**星卡*/
	public void initStar(String[] vs){
		String xz=null,wx=null;
		NJ=Integer.parseInt(vs[2].split("=")[1]);
		for (int i = 3; i < vs.length; i++) {
			if (vs[i].startsWith(SuitComposeAction.Extras[0])) {
				String[] vStrings = vs[i].split("&");
				for (int j = 2; j < vStrings.length; j++) {
					if (vStrings[j].startsWith(SuitComposeAction.Extras[7])) {
						xz=vStrings[j];
					}else {
						String[] mes = vStrings[j].split("=");	
						if (mes.length==2) {
							try {
								addQl(mes[0], Double.parseDouble(mes[1]));		
							} catch (Exception e) {
								// TODO: handle exception
								WriteOut.addtxt("转换报错:"+mes[0]+"="+mes[1],9999);
							}
						}
					}
				}
			}else if (vs[i].startsWith(SuitComposeAction.Extras[6])) {
				wx=vs[i];
			}
		}
		if (xz!=null&&wx!=null) {
			baseStar=new BaseStar(xz, wx);
		}
	}
	/**翅膀*/
	public void initWing(String[] vs){
		int pz = BaseValue.getQv(vs[0].split("=")[1])/10;
		int lvl = Integer.parseInt(vs[1].split("=")[1]);
		for (int j = 4; j < vs.length; j++) {
			if (vs[j].startsWith(SuitComposeAction.Extras[0])) {//炼化
				String[] vStrings = vs[j].split("&");
				for (int k = 1; k < vStrings.length; k++) {
					String[] mes = vStrings[k].split("=");
					try {
						addQl(mes[0], Double.parseDouble(mes[1]));		
					} catch (Exception e) {
						// TODO: handle exception
						WriteOut.addtxt("转换报错:"+mes[0]+"="+mes[1],9999);
					}
				}
			}else {
				String[] mes = vs[j].split("=");
				if (mes.length==2) {
					if (mes[0].equals("根骨")||mes[0].equals("灵性")||mes[0].equals("力量")||mes[0].equals("敏捷")||mes[0].equals("定力")) {
						int zhi = Integer.parseInt(mes[1]);
						zhi *= (1 + lvl * 0.1);
						zhi *= (1 + (pz==6?3.2:pz==5?1.6:pz==4?0.8:pz==3?0.4:pz==2?0.2:0));
						addQl(mes[0], zhi);
					}
				}
			}
		}
	}
	/**添加*/
	public void addKey(String v){
		try {
			String[] mes = v.split("=");
			if (mes.length==2) {
				for (int i = 0; i < BaseLimits.length; i++) {
					if (mes[0].startsWith(BaseLimits[i])) {
						if (i==4&&mes[0].indexOf("减少")!=-1) {addLimit(i, "-"+mes[1]);}
						else {addLimit(i, mes[1]);}
						return;
					}
				}
				addQl(mes[0], Double.parseDouble(mes[1]));
			}
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("转换报错:"+v,0);
		}
	}
	/**添加*/
	public void addKeyEW(String v){
		try {
			String[] mes = v.split("=");
			if (mes.length==2) {
				for (int i = 0; i < BaseLimits.length; i++) {
					if (mes[0].startsWith(BaseLimits[i])) {
						if (i==4&&mes[0].indexOf("减少")!=-1) {addLimit(i, "-"+mes[1]);}
						else {addLimit(i, mes[1]);}
						return;
					}
				}
				addQlEW(mes[0], Double.parseDouble(mes[1]));
			}
		} catch (Exception e) {
			// TODO: handle exception
			WriteOut.addtxt("转换报错:"+v,0);
		}
	}
	/**添加完成条件*/
	public void addLimit(int i,String value){
		if (baseLimit==null) {
			baseLimit=new BaseLimit();
		}		
		if (i<4) {//"力量要求","灵性要求","根骨要求","敏捷要求"
			int zhi=Integer.parseInt(value);
			if (i==0) {
				baseLimit.setLm(baseLimit.getLm()+zhi);
			}else if (i==1) {
				baseLimit.setLx(baseLimit.getLx()+zhi);
			}else if (i==2) {
				baseLimit.setGg(baseLimit.getGg()+zhi);
			}else {
				baseLimit.setMj(baseLimit.getMj()+zhi);
			}
		}else if (i==4) {//"属性需求"
			if (baseLimit.getXs()!=-999) {
				double zhi=Double.parseDouble(value);
				baseLimit.setXs(baseLimit.getXs()+zhi);
			}
		}else if (i==5) {//"装备角色"
			
		}else if (i==6) {//"等级要求"
			String[] lvls=value.split("转");
			if (lvls.length==1) {
				baseLimit.setLvl(Integer.parseInt(lvls[0]));
			}else{
			    if (lvls[0].equals("飞升")) {
			    	baseLimit.setZs(4);
				}else {
					baseLimit.setZs(Integer.parseInt(lvls[0]));	
				}
				baseLimit.setLvl(Integer.parseInt(lvls[1]));
			}
		}else if (i==7) {//"最高携带等级"
			String[] lvls=value.split("转");
			if (lvls.length==1) {
				baseLimit.setLvlMax(Integer.parseInt(lvls[0]));
			}else{
			    if (lvls[0].equals("飞升")) {
			    	baseLimit.setZsMax(4);
				}else {
					baseLimit.setZsMax(Integer.parseInt(lvls[0]));	
				}
				baseLimit.setLvlMax(Integer.parseInt(lvls[1]));
			}
		}else if (i==8) {//"性别"
			if (value.equals("1")||value.equals("男")) {
				baseLimit.setSex(1);
			}else {
				baseLimit.setSex(0);
			}
		}else if (i==8016) {//无属性
			baseLimit.setXs(-999);
		}else if (i==8017) {//无级别
			baseLimit.setL(true);
		}

	}
	/**添加属性*/
	public void addQl(String key,double value){
		if (qls==null) {qls=new ArrayList<>();}
		for (int i = qls.size()-1; i >=0 ; i--) {
			BaseQl baseQl=qls.get(i);
			if (baseQl.getKey().equals(key)) {
				baseQl.setValue(baseQl.getValue()+value);
				return;
			}
		}
		qls.add(new BaseQl(key, value));
	}
	/**添加额外属性*/
	public void addQlEW(String key,double value){
		if (qlews==null) {qlews=new ArrayList<>();}
		for (int i = qlews.size()-1; i >=0 ; i--) {
			BaseQl baseQl=qlews.get(i);
			if (baseQl.getKey().equals(key)) {
				baseQl.setValue(baseQl.getValue()+value);
				return;
			}
		}
		qlews.add(new BaseQl(key, value));
	}
	/**添加特技*/
	public void addSkill(BaseSkill baseSkill){
		if (baseSkills==null) {
			baseSkills=new ArrayList<>();
		}
		baseSkills.add(baseSkill);
	}
	public BaseLimit getBaseLimit() {
		return baseLimit;
	}
	public void setBaseLimit(BaseLimit baseLimit) {
		this.baseLimit = baseLimit;
	}
	public List<BaseQl> getQls() {
		return qls;
	}
	public void setQls(List<BaseQl> qls) {
		this.qls = qls;
	}
	public List<BaseSkill> getBaseSkills() {
		return baseSkills;
	}
	public void setBaseSkills(List<BaseSkill> baseSkills) {
		this.baseSkills = baseSkills;
	}
	public BaseStar getBaseStar() {
		return baseStar;
	}
	public void setBaseStar(BaseStar baseStar) {
		this.baseStar = baseStar;
	}
	public Integer getNJ() {
		return NJ;
	}
	public void setNJ(Integer nJ) {
		NJ = nJ;
	}
	public static String[] getBaseLimits() {
		return BaseLimits;
	}
	public static void setBaseLimits(String[] baseLimits) {
		BaseLimits = baseLimits;
	}
	public BaseSuit getBaseSuit() {
		return baseSuit;
	}
	public void setBaseSuit(BaseSuit baseSuit) {
		this.baseSuit = baseSuit;
	}
	public List<BaseQl> getQlews() {
		return qlews;
	}
	public void setQlews(List<BaseQl> qlews) {
		this.qlews = qlews;
	}
	public Integer getQhv() {
		return qhv;
	}
	public void setQhv(Integer qhv) {
		this.qhv = qhv;
	}	
}
