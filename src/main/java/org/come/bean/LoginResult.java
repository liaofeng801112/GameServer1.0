package org.come.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

import come.tool.Calculation.BaseMeridians;
import come.tool.Calculation.BaseQl;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.tool.JmSum;

import come.tool.Role.RoleData;
import come.tool.Role.RoleShow;
import come.tool.Scene.LaborDay.LaborRole;
import come.tool.newTeam.TeamRole;
import come.tool.Calculation.BaseXingpans;

/**
 * 登入返回角色实体类
 * @author 叶豪芳
 * @date : 2017年11月23日 下午2:58:19
 */
public class LoginResult {
	
	// 帮派名称
	private String gangname;
	// 角色ID
	private BigDecimal role_id;
	// 种族名称
	private String race_name;
	// 用户ID
	private BigDecimal user_id;
	// 种类ID
	private BigDecimal species_id;
	// 召唤兽ID
	private BigDecimal summoning_id;
	// 参战伙伴id
	private String pals;
	// 角色表里的帮派ID
	private BigDecimal gang_id;
	// 坐骑ID
	private Integer mount_id;
	// 队伍ID
	private BigDecimal troop_id;
	// 种族ID
	private BigDecimal race_id;
	// 摆摊ID
	private BigDecimal booth_id;
	//任务id 现在是徽章id
    private Integer skill_id;
	// 血量
	private BigDecimal hp;
	// 蓝量
	private BigDecimal mp;
	// 金币
	private BigDecimal gold;
	// 绑玉
	private BigDecimal savegold;
	// 点卡
	private BigDecimal codecard;
	// 经验
	private BigDecimal experience;
	// 等级
	private Integer grade;
	// 声望
	private BigDecimal prestige;
	// 战绩
	private BigDecimal pkrecord;
	// 角色名字
	private String rolename;
	// 角色称谓
	private String title;
	// 角色本名
	private String localname;
	// 用户名
	private String userName;
	// 密码
	private String userPwd;
	// 状态
	private Integer activity;
	// 性别
	private String sex;
	// 所在坐标
	private Long x;
	private Long y;
	// 所在地图
	private Long mapid;
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
	// 修为点
	private Integer xiuwei;
	// 已兑换属性点
	private String extraPoint;		
	// 判断是否在战斗中(1、战斗中)
	private Integer fighting;
	// 下线时间
	private String uptime;
	// 背包密码
	private String password;
	// 是否有待产宝宝（为空没有  1、有）
	private Integer havebaby;
	// 洞房时间
	private long makeloveTime;
	//结婚的对象
	private String marryObject;
	//宝宝id
	private BigDecimal babyId;
	//角色技能ID
	private BigDecimal SkillS_Id;
	//宝宝状态
	private String babyState;
	// 累计充值金额
	private Integer money;
	//队伍信息（队长名字|队员一|队员二|.......）
	private String teamInfo;
	//技能集合 技能名字=熟练度|技能名字=熟练度
	private String Skills;
	// 定时物品效果使用
	private String TimingGood;
	// 转生标识字段（0转为0，1转为1以此类推）
	private int TurnAround;
	// 坐牢标志      PK点数=身份标志=做天牢次数=每周坐牢次数
	private String taskDaily;
	private String born;
   	//帮派守护主副抗性字段(抗性)  存储形式(主属性=值|副属性=值)
   	private String resistance;
   	//角色服务器区号
   	private String serverMeString;
   	//记录完成次数
 	private String taskComplete;
   	//记录任务数据
 	private String taskData; 
 	//记录使用的双倍时间 剩余精确到秒 6小时 21600秒
 	private Integer DBExp;
 	//记录积分
 	private String Score;
 	//记录击杀记录
 	private String Kill;
 	// 抽奖时间
 	private Date drawing;
 	//记录帮派积分
 	private Integer bangScore;
	//额外皮肤
  	private String skin;
	private String     meridians; //完整经脉系统
  	private BigDecimal Paysum;// 角色的总充值积分， 
  	private BigDecimal Daypaysum;// 角色的日累计充值 
  	private BigDecimal Dayandpayorno;//连续充值标识 1-7 
  	private int        Dayfirstinorno;//是否最新叠加冲值标识 0 表示没 有叠加，1 表示今日已经叠加
	private int        attachPack;// 扩充背包数量
	private int        hjmax;//战绩系统
	private int        dianka;//点卡系统
  	private BigDecimal Daygetorno;// 每日特惠领取与否 1 表示领取 2 表示没有 
  	private String Vipget;// 特权领取规则 1=1|2|3&&2=1|2|3其中1=表示vip特权,2=表示每日充 值领取等级包，1 表示 1 级，2 表示 2 级，以此类推。 
  	private int lowOrHihtpack;//小资礼包/土豪礼包获取 权限 1 表示小资礼包，2 表示土豪礼包。
  	//展示
    private transient RoleShow roleShow;
    private transient TeamRole teamRole;
    private transient int[] dailys;
    private transient RoleData roleData;
    private transient LaborRole laborRole;
	private LinkedHashMap<Integer,BaseMeridians> meridiansMap;

	private Integer fmsld;
	private String xingpans;

	private LinkedHashMap<Integer,BaseXingpans> xingpansMap;
    public int[] getDaily(){
		if (dailys==null) {
			dailys=new int[4];
			try {
				String[] v=taskDaily.split("\\|");
				dailys[0]=Integer.parseInt(v[0]);
				dailys[0]=Integer.parseInt(v[1]);
				dailys[0]=Integer.parseInt(v[2]);
				dailys[0]=Integer.parseInt(v[3]);		
			} catch (Exception e) {
				
			}
		}
		return dailys;
    }
    public String upDaily(int[] dailys){
    	if (dailys.length!=4) {
			return "0|0|0|0";
		}
    	this.dailys=dailys;
    	this.taskDaily=dailys[0]+"|"+dailys[1]+"|"+dailys[2]+"|"+dailys[3];
		return taskDaily;
    }
  	/** 获取人物战斗皮肤 */
	public String getBattleSkin(long weapon) {
		StringBuffer buffer=new StringBuffer();
		boolean is=true;
		if (skin!=null&&!skin.equals("")) {
			String[] vs=skin.split("\\|");
			for (int i = 0; i < vs.length; i++) {
				if (vs[i].startsWith("X") || vs[i].startsWith("P")) {
					if (buffer.length()!=0) {
						buffer.append("&");
					}
					String[] ts=vs[i].substring(1).split("_");
					buffer.append("tx/tx");
					buffer.append(ts[0]);
					buffer.append("_");
					buffer.append(ts[1]);
				} else if (vs[i].startsWith("S")) {
					is=false;
					if (buffer.length()!=0) {
						buffer.append("&");
					}
					buffer.append(vs[i].substring(1));
					buffer.append("_1_7");
				} else if (vs[i].startsWith("B")) {
					if (buffer.length()!=0) {
						buffer.append("&");
					}
					String cb=vs[i].substring(1);
					buffer.append("tx/");
					buffer.append(cb);
					buffer.append("0_-5&tx/");
					buffer.append(cb);
					buffer.append("1_5");
				}
			}
		}
		if (is) {
			if (buffer.length()!=0) {buffer.append("&");}
			if (weapon!=0) {
				buffer.append((weapon<<32)|species_id.longValue());
			}else {
				buffer.append(species_id);
			}
			buffer.append("_1_7");
		}
		if (getSkill_id()!=null) {
			buffer.append("&W");
			buffer.append(getSkill_id());
		}
		return buffer.toString();
	}
  	
  	/**赋值*/
  	public void transfer(LoginResult login){
  		
  		this.species_id=login.species_id;
  		this.summoning_id=login.summoning_id;
  		this.mount_id=login.mount_id;
  		this.race_id=login.race_id;
  		this.race_name=login.race_name;
  		this.localname=login.localname;
  		this.sex=login.sex;
  		this.skill_id=login.skill_id;
  		this.title=login.title;
  		this.x=login.x;
  		this.y=login.y;
  		this.mapid=login.mapid;
  		this.achievement=login.achievement;
  		this.contribution=login.contribution;
  		
  		this.troop_id=login.troop_id;
  		this.fighting=login.fighting;
  		
  		this.savegold=login.savegold;
  		this.password=login.password;
  		
  		this.hp=login.hp;
  		this.mp=login.mp;
  		this.bone=login.bone;
  		this.spir=login.spir;
  		this.power=login.power;
  		this.speed=login.speed;
  		this.calm=login.calm;
  		this.xiuwei=login.xiuwei;
  		this.extraPoint=login.extraPoint;
  		
  		
  		if (this.getGold().compareTo(login.getGold()) > 0) {this.gold=login.gold;}
  		this.experience=login.experience;
  		this.grade=login.grade;
  		this.TurnAround=login.TurnAround;
  		
  		this.havebaby=login.havebaby;
  		this.makeloveTime=login.makeloveTime;
  		this.marryObject=login.marryObject;
  		this.babyId=login.babyId;
  		this.SkillS_Id=login.SkillS_Id;
  		this.babyState=login.babyState;
  		this.teamInfo=login.teamInfo;
  		this.taskDaily=login.taskDaily;
  		this.resistance=login.resistance;
  		this.drawing=login.drawing;
  		this.skin=login.skin;
		this.hjmax = login.hjmax;
		this.meridians = login.meridians;
		this.fmsld=login.fmsld;
  	}
	public double getKilltype(String type) {
		if (Kill==null||Kill.equals(""))return 0;
		String[] v=Kill.split("\\|");
		for (int i = 0; i < v.length; i++) {
			String[] v2=v[i].split("=");
			if (v2[0].equals(type))
				return Double.parseDouble(v2[1]);
		}
		return 0;
	}
	public int getJQId(String type) {
		if (taskComplete==null||taskComplete.equals(""))return 0;
		String[] v=taskComplete.split("\\|");
		for (int i = 0; i < v.length; i++) {
			if (v[i].startsWith(type)) {
				return Integer.parseInt(v[i].substring(1));
			}
		}
		return 0;
	}
 	public Integer getBangScore() {
		return bangScore;
	}
	public void setBangScore(Integer bangScore) {
		this.bangScore = bangScore;
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	public String getKill() {
		return Kill;
	}
	public void setKill(String kill) {
		Kill = kill;
	}
	public Integer getDBExp() {
		return DBExp;
	}
	public void setDBExp(Integer dBExp) {
		DBExp = dBExp;
	}
	public String getGangname() {
		return gangname;
	}
	public void setGangname(String gangname) {
		this.gangname = gangname;
		if (roleShow!=null) {
			roleShow.setGangname(gangname);
		}
	}
	public BigDecimal getRole_id() {
		return role_id;
	}
	public void setRole_id(BigDecimal role_id) {
		this.role_id = role_id;
	}
	public String getRace_name() {
		return race_name;
	}
	public void setRace_name(String race_name) {
		this.race_name = race_name;
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
		if (roleShow!=null) {
			roleShow.setSpecies_id(species_id);
		}
		if (teamRole!=null) {
			teamRole.setSpeciesId(species_id);
		}
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
		if (roleShow!=null) {
			roleShow.setGang_id(gang_id);
		}
	}
	public Integer getMount_id() {
		return mount_id;
	}
	public void setMount_id(Integer mount_id) {
		this.mount_id = mount_id;
		if (roleShow!=null) {
			roleShow.setMount_id(mount_id);
		}
	}
	public BigDecimal getTroop_id() {
		return troop_id;
	}
	public void setTroop_id(BigDecimal troop_id) {
		this.troop_id = troop_id;
		if (roleShow!=null) {
			roleShow.setTroop_id(troop_id);
		}
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
		if (roleShow!=null) {
			roleShow.setBooth_id(booth_id);
		}
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
		return new BigDecimal(JmSum.MZ(gold.longValue()));
	}
	static BigDecimal MAX=new BigDecimal("9999999999999");
	static BigDecimal MIN=new BigDecimal(0);
	public void setGold(BigDecimal gold) {	
		if( gold.compareTo(MAX) > 0 ){
			gold=new BigDecimal("999999999999");
		}else if(gold.compareTo(MIN) < 0){
			if( userName != null && GameServer.getInlineUserNameMap().get(userName) != null ){
				this.gold = new BigDecimal(JmSum.ZM(gold.longValue()));
				ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getInlineUserNameMap().get(userName), userName);
				return;
			}
		}
		this.gold = new BigDecimal(JmSum.ZM(gold.longValue()));
	}
	public BigDecimal getCodecard() {
		return new BigDecimal(JmSum.MZ(codecard.longValue()));
	}
	public void setCodecard(BigDecimal codecard) {
		// 仙玉小于0时，直接封号
		if(codecard.compareTo(new BigDecimal(0)) < 0){
			if( userName != null && GameServer.getInlineUserNameMap().get(userName) != null ){
				ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getInlineUserNameMap().get(userName), userName);;
			}
		}
		this.codecard = new BigDecimal(JmSum.ZM(codecard.longValue()));
	}
	public BigDecimal getExperience() {
		return new BigDecimal(JmSum.MZ(experience.longValue()));
	}
	public void setExperience(BigDecimal experience) {
		this.experience = new BigDecimal(JmSum.ZM(experience.longValue()));
	}
	public Integer getGrade() {
		return (int) JmSum.MZ(grade.longValue());
	}
	public void setGrade(Integer grade) {
		this.grade = (int) JmSum.ZM(grade.longValue());
        if (roleShow !=null) { roleShow.setGrade(grade); }
        if (teamRole !=null) { teamRole.setGrade(grade); }
        if (laborRole!=null) { laborRole.setLvl(grade); }
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
		if (roleShow!=null) { roleShow.setRolename(rolename); }
		if (teamRole!=null) { teamRole.setName(rolename); }
		if (laborRole!=null) { laborRole.setName(rolename); }
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		if (roleShow!=null) {
			roleShow.setTitle(title);
		}
	}
	public String getLocalname() {
		return localname;
	}
	public void setLocalname(String localname) {
		this.localname = localname;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Long getX() {
		return x;
	}
	public void setX(Long x) {
		this.x = x;
		if (roleShow!=null) {
			roleShow.setX(x);
		}
	}
	public Long getY() {
		return y;
	}
	public void setY(Long y) {
		this.y = y;
		if (roleShow!=null) {
			roleShow.setY(y);
		}
	}
	public Long getMapid() {
		return mapid;
	}
	public void setMapid(Long mapid) {
		this.mapid = mapid;
		if (roleShow!=null) {
			roleShow.setMapid(mapid);
		}
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
		 return (int) JmSum.MZ(bone.longValue());
//		return bone;
	}
	public void setBone(Integer bone) {
		this.bone =(int) JmSum.ZM(bone.longValue());
//		this.bone = bone;
	}
	public Integer getSpir() {
		return (int) JmSum.MZ(spir.longValue());
//		return spir;
	}
	public void setSpir(Integer spir) {
		this.spir =(int) JmSum.ZM(spir.longValue());
//		this.spir = spir;
	}
	public Integer getPower() {
		return (int) JmSum.MZ(power.longValue());
//		return power;
	}
	public void setPower(Integer power) {
		this.power =(int) JmSum.ZM(power.longValue());
//		this.power = power;
	}
	public Integer getSpeed() {
		return (int) JmSum.MZ(speed.longValue());
//		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed =(int) JmSum.ZM(speed.longValue());
//		this.speed = speed;
	}
	public Integer getFighting() {
		return fighting;
	}
	public void setFighting(Integer fighting) {
		this.fighting = fighting;
		if (roleShow!=null) {
			roleShow.setFighting(fighting);
		}
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
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
	public long getMakeloveTime() {
		return makeloveTime;
	}
	public void setMakeloveTime(long makeloveTime) {
		this.makeloveTime = makeloveTime;
	}
	public String getMarryObject() {
		return marryObject;
	}
	public void setMarryObject(String marryObject) {
		this.marryObject = marryObject;
	}



	public BigDecimal getBabyId() {
		return babyId;
	}
	public void setBabyId(BigDecimal babyId) {
		this.babyId = babyId;
	}


	public BigDecimal getSkillS_Id() {
		return SkillS_Id;
	}
	public void setSkillS_Id(BigDecimal s) {
		this.SkillS_Id = SkillS_Id;
	}


	public String getBabyState() {
		return babyState;
	}
	public void setBabyState(String babyState) {
		this.babyState = babyState;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getTeamInfo() {
		return teamInfo;
	}
	public void setTeamInfo(String teamInfo) {
		this.teamInfo = teamInfo;
		if (roleShow!=null) {
			roleShow.setTeamInfo(teamInfo);
		}
	}
	public String getSkills() {
		return Skills;
	}
	public void setSkills(String skills) {
		Skills = skills;
	}
	public String getTimingGood() {
		return TimingGood;
	}
	public void setTimingGood(String timingGood) {
		TimingGood = timingGood;
	}
	public int getTurnAround() {
		return TurnAround;
	}
	public void setTurnAround(int turnAround) {
		TurnAround = turnAround;
		if (roleShow!=null) {
			roleShow.setTurnAround(turnAround);
		}
	}
	public String getTaskDaily() {
		return taskDaily;
	}
	public void setTaskDaily(String taskDaily) {
		this.taskDaily = taskDaily;
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
	public String getServerMeString() {
		return serverMeString;
	}
	public void setServerMeString(String serverMeString) {
		this.serverMeString = serverMeString;
	}
	public String getTaskComplete() {
		if (taskComplete==null) {
			taskComplete="";
		}
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
	
	public Integer getActivity() {
		return activity;
	}
	public void setActivity(Integer activity) {
		this.activity = activity;
	}
	
	public Date getDrawing() {
		return drawing;
	}
	public void setDrawing(Date drawing) {
		this.drawing = drawing;
	}
	public BigDecimal getScoretype(String type) {
		if (Score==null||Score.equals(""))return new BigDecimal(0);
		String[] v=Score.split("\\|");
		for (int i = 0; i < v.length; i++) {
			String[] v2=v[i].split("=");
			if (v2[0].equals(type))
				return new BigDecimal(v2[1]);
		}
		return new BigDecimal(0);
	}
	//获取队伍
	public String getTeam(){
		if (teamInfo==null||teamInfo.equals("")||teamInfo.equals("|")) {
			return rolename;
		}else {
			return teamInfo;
		}		
	}
	public Integer getSkill_id() {
		return skill_id;
	}
	public void setSkill_id(Integer skill_id) {
		this.skill_id = skill_id;
		if (roleShow!=null) {
			roleShow.setSkill_id(skill_id);
		}
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
		if (roleShow!=null) {
			roleShow.setSkin(skin);
		}
	}
	public Integer getCalm() {
		return (int) JmSum.MZ(calm.longValue());
	}
	public void setCalm(Integer calm) {
		this.calm =(int) JmSum.ZM(calm.longValue());
	}
	public Integer getXiuwei() {
		return (int) JmSum.MZ(xiuwei.longValue());
	}
	public void setXiuwei(Integer xiuwei) {
		this.xiuwei =(int) JmSum.ZM(xiuwei.longValue());
	}
	public String getExtraPoint() {
		return extraPoint;
	}
	public void setExtraPoint(String extraPoint) {
		this.extraPoint = extraPoint;
	}
	public RoleShow getRoleShow() {
		return roleShow;
	}
	public void setRoleShow(RoleShow roleShow) {
		this.roleShow = roleShow;
	}
	public TeamRole getTeamRole() {
		if (teamRole==null) {
			teamRole=new TeamRole(this);
		}
		return teamRole;
	}
	public void initTeamRole(TeamRole teamRole) {
		this.teamRole=teamRole;
		this.teamRole.upTeamRole(this);
	}
	/**获取帮派修炼属性*/
	public String[] getResistance(String type) {
		if (resistance==null||resistance.equals("")) {
			return null;
		}
		String[] vs=resistance.split("\\|");
	    for (int i = 0; i < vs.length; i++) {
	    	if (vs[i].startsWith(type)) {
	    		String[] v=vs[i].split("#");
	    		v[0]=v[0].substring(1);
	    		return v;
	    	}
		}
		return null;
	}
	public String setResistance(int type,String data) {
		String QZ=type==1?"X":"D";
		if (resistance==null||resistance.equals("")) {
			if (data==null) {
				return "";
			}
			resistance=QZ+data;
			return resistance;
		}
		String[] vs=resistance.split("\\|");
		for (int i = 0; i < vs.length; i++) {
	    	if (vs[i].startsWith(QZ)) {
	    		vs[i]=null;
	    		break;
	    	}
		}
		StringBuffer buffer=new StringBuffer();
		for (int i = 0; i < vs.length; i++) {
			if (vs[i]!=null) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append(vs[i]);	
			}
		}
		if (data!=null) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append(QZ);
			buffer.append(data);
		}
		resistance=buffer.toString();
		return resistance;
		
	}
	/**F额外分配属性点  T天演策修炼进度   X小成修炼点 D大成修炼点*/
	public int getExtraPointInt(String type) {
		if (extraPoint==null||extraPoint.equals("")) {
			return 0;
		}
	    String[] vs=extraPoint.split("\\|");
	    for (int i = 0; i < vs.length; i++) {
	    	if (vs[i].startsWith(type)) {
	    		return Integer.parseInt(vs[i].substring(1));
	    	}
		}
	    return 0;
	}
	public int getExtraPointInt() {
		if (extraPoint==null||extraPoint.equals("")) {
			return 0;
		}
	    String[] vs=extraPoint.split("\\|");
	    int p=0;
	    for (int i = 0; i < vs.length; i++) {
	    	if (vs[i].startsWith("F")) {
	    		p+=Integer.parseInt(vs[i].substring(1));			
			}
		}
	    return p;
	}
	public void setExtraPoint(String type,int p) {
		type=type.substring(0, 1);
		if (extraPoint==null||extraPoint.equals("")) {
			this.extraPoint=type+p;
		}
		String[] vs=extraPoint.split("\\|");
		for (int i = 0; i < vs.length; i++) {
			if (vs[i].startsWith(type)) {
				vs[i]=type+(Integer.parseInt(vs[i].substring(1))+p);
				StringBuffer buffer=new StringBuffer();
				for (int j = 0; j < vs.length; j++) {
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append(vs[j]);
				}
				this.extraPoint=buffer.toString();
				return;
			}
		}
		StringBuffer buffer=new StringBuffer();
		for (int j = 0; j < vs.length; j++) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append(vs[j]);
		}
		if (buffer.length()!=0) {buffer.append("|");}
		buffer.append(type);
		buffer.append(p);
		this.extraPoint=buffer.toString();
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
//	1=1|2|3&&2=1|2|3其中1=表示vip特权,2=表示每日充 值领取等级包，1 表示 1 级，2 表示 2 级，以此类推
	public void removeVipget(String type){
		if (Vipget==null||Vipget.equals("")) {
			return;
		}
		StringBuffer buffer=new StringBuffer();
		String[] vs=Vipget.split("&&");
		for (int i = 0; i < vs.length; i++) {
			if (vs[i].split("=")[0].equals(type)) {
				vs[i]=null;
			}else {
				if (buffer.length()!=0) {
					buffer.append("&&");
				}
				buffer.append(vs[i]);
			}
		}
		if (buffer.length()==0) {
			Vipget=null;
		}else {
			Vipget=buffer.toString();	
		}	
	}
	public int getLowOrHihtpack() {
		return lowOrHihtpack;
	}
	public void setLowOrHihtpack(int lowOrHihtpack) {
		this.lowOrHihtpack = lowOrHihtpack;
	}
	public int getDayfirstinorno() {
		return Dayfirstinorno;
	}
	public String getPals() {
		return pals;
	}
	public void setPals(String pals) {
		this.pals = pals;
	}
	public void setDayfirstinorno(int dayfirstinorno) {
		Dayfirstinorno = dayfirstinorno;
	}
	public RoleData getRoleData() {
		return roleData;
	}
	public void setRoleData(RoleData roleData) {
		this.roleData = roleData;
	}
	public void setLaborRole(LaborRole laborRole) {
		this.laborRole = laborRole;
	}
	public String getMeridians() {
		return meridians;
	}
	public void setMeridians(String meridians) {
		this.meridians = meridians;
	}
	/**包裹卡*/
	public int getAttachPack() {return attachPack;}
	public void setAttachPack(int attachPack) {
		this.attachPack = attachPack;
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
	/**
	 * 返回某一条经脉的值
	 * @param key
	 * @return
	 */
	public double getMeridiansValue(String key) {
		if (meridiansMap == null) {
			return 0;
		}
		double v = 0;
		for (BaseMeridians meridian : meridiansMap.values()) {
			if (meridian.getKey().equals(key)) {
				v += meridian.getKeyValue();
			}
		}
		return v;
	}
	/**
	 * 计算当前经脉属性返回BaseQl提供战斗包计算
	 * @return
	 */
	public BaseQl[] getBaseQl() {
		if (meridiansMap == null || meridiansMap.size() == 0) {
			return null;
		}
		BaseQl[] base = new BaseQl[meridiansMap.size()];

		for (int i = 0 ; i < meridiansMap.size() ; i++) {
			base[i] = new BaseQl(meridiansMap.get(i + 1).getKey(),meridiansMap.get(i + 1).getKeyValue());
		}
		return base;
	}

	public Integer getFmsld() {
		return fmsld;
	}
	public void setFmsld(Integer fmsld) {
		this.fmsld = fmsld;
	}
	public String getXingpans() {
		return xingpans;
	}
	public void setXingpans(String xingpans) {
		this.xingpans = xingpans;
		upXingpans(xingpans);
	}

	public void setXingpans(int num,BaseXingpans xingpans) {
		xingpansMap.put(num,xingpans);
		StringBuilder temp = new StringBuilder();
		for (BaseXingpans xingpan : xingpansMap.values()) {
			if (temp.length() > 0) {
				temp.append("|");
			}
			temp.append(xingpan.toString());
		}
		this.xingpans = temp.toString();
	}

	public BaseXingpans getXingpans(int num) {
		if (xingpansMap == null) {
			xingpansMap = new LinkedHashMap<>();
		}
		return xingpansMap.get(num);
	}

	public void upXingpans(String xingpans){
		if (xingpansMap == null) {
			xingpansMap = new LinkedHashMap<>();
		}
		if (xingpans==null||xingpans.equals("")) {
			return;
		}
		BaseXingpans baseXingpansR=null;
		String[] vs=xingpans.split("\\|");
		for (int i = 0; i < vs.length; i++) {
			String[] vss=vs[i].split("_");
			if (vss.length!=7) {
				continue;
			}
			int bh=Integer.parseInt(vss[0]);
			baseXingpansR=new BaseXingpans(bh,
					vss[1], Integer.parseInt(vss[2]),
					vss[3], Double.parseDouble(vss[4]),
					vss[5], Double.parseDouble(vss[6]));
			xingpansMap.put(bh, baseXingpansR);
		}
	}

	/**
	 * 计算当前星盘属性返回BaseQl提供战斗包计算 星盘
	 * @return
	 */
	public BaseQl[] getBaseQl1() {
		if (xingpans == null) {
			return null;
		}


		String[] vs=xingpans.split("\\|");
		BaseQl[] base = new BaseQl[(vs.length*2)];
		for (int i = 0; i < base.length; i++) {
			if(i<vs.length){
				String[] vss=vs[i].split("_");
				int bh=Integer.parseInt(vss[0]);
				base[i] = new BaseQl(xingpansMap.get(bh).getKey1(),xingpansMap.get(bh).getKeyValue1());
			}else {
				String[] vss=vs[(i-vs.length)].split("_");
				int bh=Integer.parseInt(vss[0]);
				base[i] = new BaseQl(xingpansMap.get(bh).getKey(),xingpansMap.get(bh).getKeyValue());
			}

		}
		return base;
	}


	/**
	 * 返回某一条星盘的值
	 * @param key
	 * @return
	 */
	public double getXingpansValue(String key) {
		if (xingpansMap == null) {
			return 0;
		}
		double v = 0;
		for (BaseXingpans xingpan : xingpansMap.values()) {
			if (xingpan.getKey().equals(key)) {
				v += xingpan.getKeyValue();
			}
		}
		return v;
	}

}
