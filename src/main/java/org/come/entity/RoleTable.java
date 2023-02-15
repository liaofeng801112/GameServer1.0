package org.come.entity;

import java.math.BigDecimal;

import org.come.bean.LoginResult;

/**
 * 角色信息bean
 * 
 * @author 叶豪芳
 * @date : 2017年11月27日 上午10:07:35
 */
public class RoleTable {

	private BigDecimal role_id;

	private BigDecimal user_id;

	private BigDecimal species_id;

	private BigDecimal summoning_id;

	private BigDecimal gang_id;

	private Integer mount_id;

	private BigDecimal troop_id;

	private BigDecimal race_id;

	private BigDecimal booth_id;

	private BigDecimal hp;

	private BigDecimal mp;

	private BigDecimal gold;

	private BigDecimal codecard;

	private BigDecimal experience;

	private Integer grade;

	private BigDecimal prestige;

	private BigDecimal pkrecord;

	private String rolename;

	private String title;

	private String sex;

	private String localname;

	private String skill;

	private String uptime;
	// 帮派名称
	private String gangname;

	// 帮派职务
	private String gangpost;

	// 帮派成就
	private BigDecimal achievement;

	// 帮派贡献
	private BigDecimal contribution;

	// 根骨
	private Integer bone;

	// 灵性
	private Integer spir;

	// 力量
	private Integer power;

	// 敏捷
	private Integer speed;

	// 定力
	private Integer calm;

	// 是否为队长（0、不是 1、是）
	private Integer captain;

	// 存款
	private BigDecimal savegold;

	// 背包密码
	private String password;

	// 是否有待产宝宝（为空没有 1、有）
	private Integer havebaby;

	// 所在坐标
	private Long x;

	private Long y;

	// 所在地图
	private Long mapid;

	// 是否新手（0、新手 1、老手）
	private Integer newrole;

	// 种族
	private String racename;

	// 最大经验
	private BigDecimal maxexp;

	// 结婚的对象
	private String marryObject;

	// 技能集合 技能名字=熟练度|技能名字=熟练度
	private String Skills;
	// 定时物品效果使用
	private String TimingGood;
	private String born;

	// 帮派守护主副抗性字段(抗性) 存储形式(主属性=值|副属性=值)
	private String resistance;
	// 日常任务 id=次数=状态=进度=地图=坐标x，坐标y，名称 ，战斗id&坐标x，坐标y，名称 ，战斗id=地图 坐标x，坐标y，名称
	// ，战斗id&坐标x，坐标y，名称 ，战斗id
	private String taskDaily;
	// 脚色服务器区号
	private String serverMeString;

	// 记录完成次数
	private String taskComplete;
	// 记录任务数据
	private String taskData;
	
  	private BigDecimal Paysum;// 角色的总充值积分， 
  	private BigDecimal Daypaysum;// 角色的日累计充值 
  	private BigDecimal Dayandpayorno;//连续充值标识 1-7 
  	private int        Dayfirstinorno;//是否最新叠加冲值标识 0 表示没 有叠加，1 表示今日已经叠加
	private int  		hjmax;
	private int 		dianka;
	private BigDecimal Daygetorno;// 每日特惠领取与否 1 表示领取 2 表示没有
  	private String Vipget;// 特权领取规则 1=1|2|3&&2=1|2|3其中1=表示vip特权,2=表示每日充 值领取等级包，1 表示 1 级，2 表示 2 级，以此类推。 
  	private int lowOrHihtpack;//小资礼包/土豪礼包获取 权限 1 表示小资礼包，2 表示土豪礼包。
	// 修为点
	private Integer xiuwei;
	// 已兑换属性点
	private String extraPoint;
	// 状态
	private String statues;
    //quID
    private BigDecimal qid;
	// 用户状态（0 正常）
    private Short activity;
    // zeng-190711 --
 	private String PAYINTEGRATION;

	private Integer fmsld;
    /**
     * 开始页面
     */
    private int start;
      /**
       * 结束页面
       */
    private int end;
    
    /**
     * null
     */
    private String unknown;
    
    
    /**
     * 独立标识，使用查询没有实际意义,每次单独调用可以使用
     * @return
     */
    
    private String userString;
    
    public RoleTable() {
		// TODO Auto-generated constructor stub
	}
    /**帮派对象赋值*/
    public RoleTable(int gang,LoginResult result) {
		// TODO Auto-generated constructor stub
    	this.role_id=result.getRole_id();
    	this.rolename=result.getRolename();
    	this.gang_id=result.getGang_id();
    	this.gangpost=result.getGangpost();
    	this.gangname=result.getGangname();
	}
    
    public String getUserString() {
		return userString;
	}

	public void setUserString(String userString) {
		this.userString = userString;
	}

	public String getUnknown() {
		return unknown;
	}

	public void setUnknown(String unknown) {
		this.unknown = unknown;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}



	public BigDecimal getQid() {
		return qid;
	}

	public void setQid(BigDecimal qid) {
		this.qid = qid;
	}

	public Short getActivity() {
		return activity;
	}

	public void setActivity(Short activity) {
		this.activity = activity;
	}

	public BigDecimal getRole_id() {
		return role_id;
	}

	public void setRole_id(BigDecimal role_id) {
		this.role_id = role_id;
	}

	public BigDecimal getUser_id() {
		return user_id;
	}

	public void setUser_id(BigDecimal user_id) {
		this.user_id = user_id;
	}

	public BigDecimal getSpecies_id() {
		return species_id;
	}

	public void setSpecies_id(BigDecimal species_id) {
		this.species_id = species_id;
	}

	public BigDecimal getSummoning_id() {
		return summoning_id;
	}

	public void setSummoning_id(BigDecimal summoning_id) {
		this.summoning_id = summoning_id;
	}

	public BigDecimal getGang_id() {
		return gang_id;
	}

	public void setGang_id(BigDecimal gang_id) {
		this.gang_id = gang_id;
	}

	public Integer getMount_id() {
		return mount_id;
	}

	public void setMount_id(Integer mount_id) {
		this.mount_id = mount_id;
	}

	public BigDecimal getTroop_id() {
		return troop_id;
	}

	public void setTroop_id(BigDecimal troop_id) {
		this.troop_id = troop_id;
	}

	public BigDecimal getRace_id() {
		return race_id;
	}

	public void setRace_id(BigDecimal race_id) {
		this.race_id = race_id;
	}

	public BigDecimal getBooth_id() {
		return booth_id;
	}

	public void setBooth_id(BigDecimal booth_id) {
		this.booth_id = booth_id;
	}

	public BigDecimal getHp() {
		return hp;
	}

	public void setHp(BigDecimal hp) {
		this.hp = hp;
	}

	public BigDecimal getMp() {
		return mp;
	}

	public void setMp(BigDecimal mp) {
		this.mp = mp;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {

		if (gold.compareTo(new BigDecimal(2000000000)) > 0) {
			setGold(new BigDecimal(2000000000));
		}

		this.gold = gold;
	}

	public BigDecimal getCodecard() {
		return codecard;
	}

	public void setCodecard(BigDecimal codecard) {
		this.codecard = codecard;
	}

	public BigDecimal getExperience() {
		return experience;
	}

	public void setExperience(BigDecimal experience) {
		this.experience = experience;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public BigDecimal getPrestige() {
		return prestige;
	}

	public void setPrestige(BigDecimal prestige) {
		this.prestige = prestige;
	}

	public BigDecimal getPkrecord() {
		return pkrecord;
	}

	public void setPkrecord(BigDecimal pkrecord) {
		this.pkrecord = pkrecord;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocalname() {
		return localname;
	}

	public void setLocalname(String localname) {
		this.localname = localname;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getGangname() {
		return gangname;
	}

	public void setGangname(String gangname) {
		this.gangname = gangname;
	}

	public String getGangpost() {
		return gangpost;
	}

	public void setGangpost(String gangpost) {
		this.gangpost = gangpost;
	}

	public BigDecimal getAchievement() {
		return achievement;
	}

	public void setAchievement(BigDecimal achievement) {
		this.achievement = achievement;
	}

	public BigDecimal getContribution() {
		return contribution;
	}

	public void setContribution(BigDecimal contribution) {
		this.contribution = contribution;
	}

	public Integer getBone() {
		return bone;
	}

	public void setBone(Integer bone) {
		this.bone = bone;
	}

	public Integer getSpir() {
		return spir;
	}

	public void setSpir(Integer spir) {
		this.spir = spir;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getCalm() {
		return calm;
	}

	public void setCalm(Integer calm) {
		this.calm = calm;
	}

	public Integer getCaptain() {
		return captain;
	}

	public void setCaptain(Integer captain) {
		this.captain = captain;
	}

	public BigDecimal getSavegold() {
		return savegold;
	}

	public void setSavegold(BigDecimal savegold) {
		this.savegold = savegold;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getHavebaby() {
		return havebaby;
	}

	public void setHavebaby(Integer havebaby) {
		this.havebaby = havebaby;
	}

	public Long getX() {
		return x;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public Long getY() {
		return y;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public Long getMapid() {
		return mapid;
	}

	public void setMapid(Long mapid) {
		this.mapid = mapid;
	}

	public Integer getNewrole() {
		return newrole;
	}

	public void setNewrole(Integer newrole) {
		this.newrole = newrole;
	}

	public String getRacename() {
		return racename;
	}

	public void setRacename(String racename) {
		this.racename = racename;
	}

	public BigDecimal getMaxexp() {
		return maxexp;
	}

	public void setMaxexp(BigDecimal maxexp) {
		this.maxexp = maxexp;
	}

	public String getMarryObject() {
		return marryObject;
	}

	public void setMarryObject(String marryObject) {
		this.marryObject = marryObject;
	}

	public String getSkills() {
		return Skills;
	}

	public void setSkills(String skills) {
		Skills = skills;
	}

	public String getBorn() {
		return born;
	}

	public void setBorn(String born) {
		this.born = born;
	}

	public String getResistance() {
		return resistance;
	}

	public void setResistance(String resistance) {
		this.resistance = resistance;
	}

	public String getTimingGood() {
		return TimingGood;
	}

	public void setTimingGood(String timingGood) {
		TimingGood = timingGood;
	}

	public String getTaskDaily() {
		return taskDaily;
	}

	public void setTaskDaily(String taskDaily) {
		this.taskDaily = taskDaily;
	}

	public String getServerMeString() {
		return serverMeString;
	}

	public void setServerMeString(String serverMeString) {
		this.serverMeString = serverMeString;
	}

	public String getTaskComplete() {
		return taskComplete;
	}

	public void setTaskComplete(String taskComplete) {
		this.taskComplete = taskComplete;
	}

	public String getTaskData() {
		return taskData;
	}

	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}

	public String getStatues() {
		return statues;
	}

	public void setStatues(String statues) {
		this.statues = statues;
	}

	public String getPAYINTEGRATION() {
		return PAYINTEGRATION;
	}

	public void setPAYINTEGRATION(String pAYINTEGRATION) {
		PAYINTEGRATION = pAYINTEGRATION;
	}

	public BigDecimal getPaysum() {
		return Paysum;
	}

	public void setPaysum(BigDecimal paysum) {
		Paysum = paysum;
	}

	public BigDecimal getDaypaysum() {
		return Daypaysum;
	}

	public void setDaypaysum(BigDecimal daypaysum) {
		Daypaysum = daypaysum;
	}

	public BigDecimal getDayandpayorno() {
		return Dayandpayorno;
	}

	public void setDayandpayorno(BigDecimal dayandpayorno) {
		Dayandpayorno = dayandpayorno;
	}

	public int getDayfirstinorno() {
		return Dayfirstinorno;
	}

	public void setDayfirstinorno(int dayfirstinorno) {
		Dayfirstinorno = dayfirstinorno;
	}

	public BigDecimal getDaygetorno() {
		return Daygetorno;
	}

	public void setDaygetorno(BigDecimal daygetorno) {
		Daygetorno = daygetorno;
	}

	public String getVipget() {
		return Vipget;
	}

	public void setVipget(String vipget) {
		Vipget = vipget;
	}

	public int getLowOrHihtpack() {
		return lowOrHihtpack;
	}

	public void setLowOrHihtpack(int lowOrHihtpack) {
		this.lowOrHihtpack = lowOrHihtpack;
	}

	public Integer getXiuwei() {
		return xiuwei;
	}

	public void setXiuwei(Integer xiuwei) {
		this.xiuwei = xiuwei;
	}

	public String getExtraPoint() {
		return extraPoint;
	}

	public void setExtraPoint(String extraPoint) {
		this.extraPoint = extraPoint;
	}
	public int getHjmax() {
    	return hjmax;
    }

	public void setHjmax(int hjmax) {
		this.hjmax = hjmax;
	}

	public int getDianka() {
		return dianka;
	}

	public void setDianka(int dianka) {
		this.dianka = dianka;
	}
	public Integer getFmsld() {
		return fmsld;
	}

	public void setFmsld(Integer fmsld) {
		this.fmsld = fmsld;
	}
}