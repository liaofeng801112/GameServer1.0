package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.tool.JmSum;

import com.gl.util.LingXiUtil;

import come.tool.Battle.BattleMixDeal;
/**
 * 召唤兽实体类
 * @author 叶豪芳
 * @date : 2017年11月23日 下午2:59:18
 */
public class RoleSummoning implements Cloneable{
	// 召唤兽ID
	private String summoningid;
	// 召唤兽名称
	private String summoningname;
	// 皮肤
	private String summoningskin;
	// 宝宝类型
	private String ssn;
	// 是否物理怪
	private String stye;
	// 血量
	private int hp;
	// 蓝量
	private int mp;
	// 伤害
	private int ap;
	// 敏捷
	private int sp;
	// 最高成长率
	private String growlevel;
	// 抗性
	private String resistance;
	// 技能
	private String skill;
	// 金
	private String gold;
	// 木
	private String wood;
	// 土
	private String soil;
	// 水
	private String water;
	// 火
	private String fire;
	//变色方案
	private String ColorScheme;
	// 角色ID
	private BigDecimal roleid;
	// 根骨
	private Integer bone=0;
	// 灵性
	private Integer spir=0;
	// 力量
	private Integer power=0;
	// 敏捷
	private Integer speed=0;
	// 定力
	private Integer calm;
	// 等级
	private Integer grade=0;
	// 经验
	private BigDecimal exp;
	// 忠诚度
	private Integer faithful;
	// 亲密值
	private Long friendliness;
	// ID
	private BigDecimal sid;
	// 当前血量
	private int basishp;
	// 当前蓝量
	private int basismp;
	// 有几个召唤兽技能框解开了封印（初始值为1）
	private Integer openSeal=1;
	private Integer openql=0;
	// 内丹  id|id
	private String innerGoods;
	// 龙骨
	private int dragon;
	// 超级龙骨
	private int spdragon;
	// 技能  静态表id|静态表id|静态表id...
	private String petSkills;
	private String petQlSkills;
	// 转身
	private int turnRount;
	// 内丹抗性
	private String NedanResistance;
	// 被点化次数
	private int revealNum;
	// 使用神兽飞升丹的次数
	private int flyupNum;
	// 神兽技能id
	private String beastSkills;
	// 召唤兽增加四种属性几率字段
	private String fourattributes;
	//召唤兽技能属性
	private String skillData;
	//坐骑抗性      (没用了)
	private String zqk;
	//炼妖抗性
	private String lyk;
	private String glyk;
	// 炼妖次数
	private int alchemynum;
	private int galchemynum;
	//使用超级元气丹的次数
	private int growUpDanNum;
	//龙涎丸使用次数
	private int draC;
	// 培养值
	private int trainNum;
	//召唤兽是否有加锁
	private int petlock;
	//召唤兽灵犀
	private String lingxi;

	// 悟灵技能  静态表id=1|静态表id=1|静态表id=1 最多3个
	private String petSkillswl;

	/**增加亲密度*/
	public void addqm(int v){
		setFriendliness(getFriendliness()+v);
	}
	public int getSI2(String type) {
		if (fourattributes == null || fourattributes.equals(""))
			return 0;
		String[] v = fourattributes.split("\\|");
		for (int i = 0; i < v.length; i++) {
			String[] v1 = v[i].split("=");
			if (v1[0].equals(type))
				return (int) Double.parseDouble(v1[1]);
		}
		return 0;
	}

	/**灵犀加成*/
	public void getLX(int[] pets) {
		if (lingxi == null || lingxi.equals(""))
			return;
		String[] lx = lingxi.split("&");
		String[] v = lx[3].split("=")[1].split("\\|");
		for (int i = 0; i < v.length; i++) {
			String[] v1 = v[i].split("_");
			int lvl = Integer.parseInt(v1[1]);
			if (lvl == 0) {
				continue;
			}
			if (v1[0].equals("11001")){
				pets[0]+=LingXiUtil.getNumberBySkillId(v1[0],1,lvl);
			}else if (v1[0].equals("11002")) {
				pets[1]+=LingXiUtil.getNumberBySkillId(v1[0],1,lvl);
			}else if (v1[0].equals("11003")) {
				pets[2]+=LingXiUtil.getNumberBySkillId(v1[0],1,lvl);
			}else if (v1[0].equals("11004")) {
				pets[3]+=LingXiUtil.getNumberBySkillId(v1[0],1,lvl);
			}
		}
	}


	/**扫表获得的数据 进行偏移*/
	public void SB(){
		setHp(hp);
		setMp(mp);
		setAp(ap);
		setSp(sp);
	}
	public int getPetlock() {
		return petlock;
	}

	public void setPetlock(int petlock) {
		this.petlock = petlock;
	}

	public int getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(int trainNum) {
		this.trainNum = trainNum;
	}

	public int getTurnRount() {
		return BattleMixDeal.petTurnRount(getGrade());
	}

	public void setTurnRount(int turnRount) {
		this.turnRount = turnRount;
	}

	public BigDecimal getSid() {
		return sid;
	}

	public void setSid(BigDecimal sid) {
		this.sid = sid;
	}

	public String getSummoningid() {
		return summoningid;
	}

	public void setSummoningid(String summoningid) {
		this.summoningid = summoningid;
	}

	public String getSummoningskin() {
		return summoningskin;
	}

	public void setSummoningskin(String summoningskin) {
		this.summoningskin = summoningskin;
	}

	public String getStye() {
		return stye;
	}

	public void setStye(String stye) {
		this.stye = stye;
	}

	public int getBasishp() {
		return basishp;
	}

	public void setBasishp(int basishp) {
		this.basishp = basishp;
	}

	public int getBasismp() {
		return basismp;
	}

	public void setBasismp(int basismp) {
		this.basismp = basismp;
	}

	public String getGrowlevel() {
		return growlevel;
	}

	public void setGrowlevel(String growlevel) {
		this.growlevel = growlevel;
	}

	public String getResistance() {
		return resistance;
	}

	public void setResistance(String resistance) {
		this.resistance = resistance;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getGold() {
		if (gold==null||gold.equals("")) {return "0";}
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getWood() {
		if (wood==null||wood.equals("")) {return "0";}
		return wood;
	}

	public void setWood(String wood) {
		this.wood = wood;
	}

	public String getSoil() {
		if (soil==null||soil.equals("")) {return "0";}
		return soil;
	}

	public void setSoil(String soil) {
		this.soil = soil;
	}

	public String getWater() {
		if (water==null||water.equals("")) {return "0";}
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public String getFire() {
		if (fire==null||fire.equals("")) {return "0";}
		return fire;
	}

	public void setFire(String fire) {
		this.fire = fire;
	}

	public String getSummoningname() {
		return summoningname;
	}

	public void setSummoningname(String summoningname) {
		this.summoningname = summoningname;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public BigDecimal getRoleid() {
		return roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	public Integer getFaithful() {
		return faithful;
	}

	public void setFaithful(Integer faithful) {
		this.faithful = faithful;
	}
	public Integer getOpenSeal() {
		return openSeal;
	}

	public void setOpenSeal(Integer openSeal) {
		this.openSeal = openSeal;
	}

	public Integer getOpenql() {
		return openql;
	}

	public void setOpenql(Integer openql) {
		this.openql = openql;
	}

	public String getInnerGoods() {
		return innerGoods;
	}

	public void setInnerGoods(String innerGoods) {
		this.innerGoods = innerGoods;
	}

	public int getDragon() {
		return dragon;
	}

	public void setDragon(int dragon) {this.dragon = dragon;}
	public int getSpdragon() {
		return spdragon;
	}
	public void setSpdragon(int spdragon) {this.spdragon = spdragon;}




	public String getPetSkills() {
		return petSkills;
	}

	public void setPetSkills(String petSkills) {
		this.petSkills = petSkills;
	}



	public String getPetQlSkills() {
		return petQlSkills;
	}

	public void setPetQlSkills(String petQlSkills) {
		this.petQlSkills = petQlSkills;
	}


	public String getNedanResistance() {
		return NedanResistance;
	}

	public void setNedanResistance(String nedanResistance) {
		NedanResistance = nedanResistance;
	}

	public int getRevealNum() {
		return revealNum;
	}

	public void setRevealNum(int revealNum) {
		this.revealNum = revealNum;
	}

	public int getFlyupNum() {
		return flyupNum;
	}

	public void setFlyupNum(int flyupNum) {
		this.flyupNum = flyupNum;
	}

	public String getBeastSkills() {
		return beastSkills;
	}

	public void setBeastSkills(String beastSkills) {
		this.beastSkills = beastSkills;
	}

	public String getFourattributes() {
		return fourattributes;
	}

	public void setFourattributes(String fourattributes) {
		this.fourattributes = fourattributes;
	}

	public String getSkillData() {
		return skillData;
	}

	public void setSkillData(String skillData) {
		this.skillData = skillData;
	}

	public String getZqk() {
		return zqk;
	}

	public void setZqk(String zqk) {
		this.zqk = zqk;
	}

	public String getLyk() {
		return lyk;
	}

	public void setLyk(String lyk) {
		this.lyk = lyk;
	}

	public String getGlyk() {
		return glyk;
	}

	public void setGlyk(String glyk) {
		this.glyk = glyk;
	}

	public int getAlchemynum() {
		return alchemynum;
	}

	public void setAlchemynum(int alchemynum) {
		this.alchemynum = alchemynum;
	}

	public int getGAlchemynum() {
		return galchemynum;
	}

	public void setGAlchemynum(int galchemynum) {
		this.galchemynum = galchemynum;
	}

	public int getGrowUpDanNum() {
		return growUpDanNum;
	}
	public void setGrowUpDanNum(int growUpDanNum) {
		this.growUpDanNum = growUpDanNum;
	}
	public String getColorScheme() {
		return ColorScheme;
	}
	public void setColorScheme(String colorScheme) {
		ColorScheme = colorScheme;
	}

	public String getLingxi() {
		return lingxi;
	}
	public void setLingxi(String lingxi) {
		this.lingxi = lingxi;
	}

	public int getDraC() {
		return draC;
	}

	public void setDraC(int draC) {
		this.draC = draC;
	}
	public int getHp() {
		return (int) JmSum.MZ(hp);
//		return hp;
	}
	public void setHp(int hp) {
		this.hp=(int) JmSum.ZM(hp);
//		this.hp = hp;
	}
	public int getMp() {
		return (int) JmSum.MZ(mp);
//		return mp;
	}
	public void setMp(int mp) {
		this.mp=(int) JmSum.ZM(mp);
//		this.mp = mp;
	}
	public int getAp() {
		return (int) JmSum.MZ(ap);
//		return ap;
	}
	public void setAp(int ap) {
		this.ap=(int) JmSum.ZM(ap);
//		this.ap = ap;
	}
	public int getSp() {
		return (int) JmSum.MZ(sp);
//		return sp;
	}
	public void setSp(int sp) {
		this.sp=(int) JmSum.ZM(sp);
//		this.sp = sp;
	}
	public Integer getBone() {
		return (int) JmSum.MZ(bone);
//		return bone;
	}
	public void setBone(Integer bone) {
		this.bone=(int) JmSum.ZM(bone);
//		this.bone = bone;
	}
	public Integer getSpir() {
		return (int) JmSum.MZ(spir);
//		return spir;
	}
	public void setSpir(Integer spir) {
		this.spir=(int) JmSum.ZM(spir);
//		this.spir = spir;
	}
	public Integer getPower() {
		return (int) JmSum.MZ(power);
//		return power;
	}
	public void setPower(Integer power) {
		this.power=(int) JmSum.ZM(power);
//		this.power = power;
	}

	public Integer getSpeed() {
		return (int) JmSum.MZ(speed);
//		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed=(int) JmSum.ZM(speed);
//		this.speed = speed;
	}
	public Integer getCalm() {
		if (calm==null) {setCalm(0);}
		return (int) JmSum.MZ(calm);
	}
	public void setCalm(Integer calm) {
		this.calm=(int) JmSum.ZM(calm);
	}
	public Integer getGrade() {
		return (int) JmSum.MZ(grade);
//		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade=(int) JmSum.ZM(grade);
//		this.grade = grade;
	}

	public BigDecimal getExp() {
		return new BigDecimal(JmSum.MZ(exp.longValue()));
//		return exp;
	}

	public void setExp(BigDecimal exp) {
		this.exp=new BigDecimal(JmSum.ZM(exp.longValue()));
//		this.exp = exp;
	}

	public Long getFriendliness() {
		if (friendliness==null)setFriendliness(0L);
		return JmSum.MZ(friendliness);
//		return friendliness;
	}

	public void setFriendliness(Long friendliness) {
		if (friendliness>20000000)friendliness=20000000L;
		this.friendliness=JmSum.ZM(friendliness);
//		this.friendliness = friendliness;
	}
	public String getPetSkillswl() {
		return petSkillswl;
	}

	public void setPetSkillswl(String petSkillswl) {
		this.petSkillswl = petSkillswl;
	}

	/**获取召唤兽装备的物品id集合*/
	public List<BigDecimal> getGoods(){
		if ((innerGoods==null||innerGoods.equals(""))&&(stye==null||stye.length()<=1)) {
			return null;
		}
		List<BigDecimal> goods=new ArrayList<>();
		if (innerGoods!=null&&!innerGoods.equals("")) {
			String[] v=innerGoods.split("\\|");
			for (int i = 0; i < v.length; i++) {
				if (!v[i].equals("")) {
					goods.add(new BigDecimal(v[i]));
				}
			}
		}
		if (stye!=null&&stye.length()>1) {
			String[] v=stye.split("\\|");
			for (int i = 1; i < v.length; i++) {
				String[] vs=v[i].split("-");
				if (vs.length>=2) {
					goods.add(new BigDecimal(vs[1]));
				}
			}
		}
		return goods;
	}

	@Override
	public RoleSummoning clone(){
		try {
			return (RoleSummoning) super.clone();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}



}