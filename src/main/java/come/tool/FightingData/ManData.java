package come.tool.FightingData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.come.bean.PathPoint;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;
import org.come.model.Monster;
import org.come.model.PalData;
import org.come.model.Skill;
import org.come.model.Talent;
import org.come.server.GameServer;
import org.come.tool.CustomFunction;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gl.util.GLUtil;
import com.gl.util.LingXiUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleMixDeal;
import come.tool.Calculation.BaseEquip;
import come.tool.Calculation.BaseQl;
import come.tool.Calculation.BaseStar;
import come.tool.Calculation.BaseValue;
import come.tool.Calculation.CalculationPal;
import come.tool.Calculation.CalculationPet;
import come.tool.Calculation.CalculationUtil;
import come.tool.Mixdeal.CreepsMixdeal;
import come.tool.Mixdeal.PetGrade;

//TODO 战斗数据（人、宠、孩子、灵宝）
/**
 * 战斗中人物数据
 *
 * @author Administrator
 */

public class ManData implements Cloneable {
	private int type;// 类型 0玩家 1召唤兽 2野怪 3灵宝 4小孩
	private int id;// id
	private int camp;// 战斗1阵营
	private int man;// 战斗位置 1玩家 6召唤兽 11灵宝 16小孩
	private String manname;// 名称
	private int hp;// 血量
	private int mp;// 蓝量
	private int hp_z;// 总血量
	private int mp_z;// 总蓝量
	private Ql Quality;// 人物的所有抗性
	private int ap;// ap 普攻伤害
	private int sp;// sp 敏捷度
	private int sp2;// sp 敏捷度
	private int States;// 当前状态 0表示活着 1表示死亡 2表示已经逃跑
	private List<AddState> addStates;// 附加状态集合
	private List<FightingSkill> skills;// 已有技能集合
	private List<FightingSummon> pets;// 玩家已有的召唤兽集合
	private ManData Child;// 孩子
	private String model;// 人物模型
	private int spellSum = 0;// 法术攻击的次数
	private int yqz = 149;// 怨气值 初始149 上限500
	private int nqz = 30;// 怒气值
	private List<FightingLingbao> lings;// 玩家已有的灵宝集合
	// 灵宝属性字段 -人物 4项属性
	private long qihe;// 契合度
	private double huoyue;// 活跃 根骨
	private double shanghai;// 伤害或者回复或者落宝 灵性
	private double kangluobao;// 抗落宝 力量
	private double yuanzhu;// 支援 敏捷
	private int lvl;// 等级
	private int zs;// 转生字段
	private List<AI> ais;// AI
	private int sr;// 技能释放的概率
	private String msg;// 喊话
	private long money;// 被偷取的金钱
	// 承受的法术类型
	private List<String> bearSkills;
	private BigDecimal se;

	private BigDecimal xk_id;//星卡id
	private Map<String, AddAttack> attacks;// 追加攻击

	private BaseStar baseStar;
	private Double expXS;
	private Double exp2XS;

	private Integer fmsld = 0;// 法门熟练度
	private int famencs = 0;// 法门层数

	//保存额外增加属性
	private int ewhuoyue =0;// 活跃 根骨
	private int ewshanghai=0;// 伤害或者回复或者落宝 灵性
	private int ewkangluobao=0;// 抗落宝 力量
	private int ewyuanzhu=0;// 支援 敏捷

	//法门 利刃 储存伤害
	private int dffmsld = 0;
	private int nrlrjs = 0;
	private int nrjycz = 0;
	private int fmwjbc = 0;//法门 无坚不摧
	private int fmshfs = 0;//法门 兽魂俯首

	// 用完即消的属性
	public double zm = 0;		// 附加致命
	public double kb = 0;		// 附加狂暴
	public double mz = 0;		// 附加命中
	public double pwljl = 0;	// 附加破物理几率
	public double pwlcd = 0;	// 附加破物理程度
	public double hsfz = 0;		// 附加忽视反震概率
	public int ljv = 0;			// 附加连击率

	public double qf = 0;		// 强仙法鬼火
	public double hs = 0;		// 忽视仙法鬼火
	public double fsmz = 0;		// 法术命中
	public double fskbcd = 0;	// 法术狂暴程度
	public double fskbjl = 0;	// 法术狂暴几率
	public double hsjv = 0;		// 忽视法术抗性几率

	public double bhws = 0; 	// 附加冰混忘睡抗性

	// 惊涛拍岸标记
	public boolean jtpa = false;

	// 灵犀通用冷却
	private int cd = 0;

	// 存放灵犀字符串
	private String lingxi;
	// 卷土重来触发标记
	public boolean jtcl = false;

	// 一鸣惊人触发标记
	public boolean ymjr = false;

	// 奋不顾身标记允许攻击加成
	public boolean fbgs = true;

	// 属性以及保留的回合


	public String param = "";

	// 本次攻击是否造成了法术狂暴
	public boolean fskbbj = false;

	private String skin;
	public ManData(int camp, int man) {
		this.yqz = 149;
		this.nqz = 30;
		this.expXS=1D;
		this.exp2XS=0D;
		this.camp=camp;
		this.man=man;
		this.type=0;
		this.skills=new ArrayList<>();
		this.addStates=new ArrayList<>();
	}
	/**召唤兽技能加载*/
	public void addPetSkill(String petSkills,long qm,FightingSummon summon){
		if (petSkills==null||petSkills.equals("")) {
			return;
		}
		String[] v=petSkills.split("\\|");
		for (int i = 0; i < v.length; i++) {
			Skill skill=GameServer.getSkill(v[i]);
			if (skill!=null) {
				CalculationPet.addQlSkill(Quality, skill.getSkillid(), qm);
				int skilltype=skilltype(skill.getSkillid());
				if (skilltype==1) {
					continue;
				}else if (skilltype==1207) {
					for (int j = 0; j < skills.size(); j++) {
						if (skills.get(j).getSkillid() == 1831|| skills.get(j).getSkillid() == 1833)
							skills.get(j).setSkillhurt(skills.get(j).getSkillhurt() + 15);
					}
					continue;
				}else {

					// 灵犀-法术-大开杀戒判定熟练度提升
					int sld = (int) (executeDksj(1) * 3.5);

					FightingSkill fightingSkill=new FightingSkill(skill, lvl, zs, 350 + sld,qm,0);
					int skillid=fightingSkill.getSkillid();
					if (skillid == 1806 || (skillid >= 1820 && skillid <= 1827)) {
						summon.setHelpV(fightingSkill.getSkillhitrate());
					}
					if (fightingSkill.getSkillid()>=1041&&fightingSkill.getSkillid()<=1065) {
						fightingSkill.setSkillhurt(fightingSkill.getSkillhurt()*2);
					}
					addSkill(fightingSkill);
				}
			}
		}
	}

	/**召唤兽灵犀加载*/
	public void addPetLX(String lingxi,long qm){
		if (lingxi==null||lingxi.equals("")) {
			return;
		}
		List<Skill> skills = LingXiUtil.getLingXiSkill(lingxi);
		for (Skill skill : skills) {
			if (skill!=null) {
				FightingSkill fightingSkill=new FightingSkill(skill, lvl, zs, 250,qm,0);
				fightingSkill.setSkillbeidong(1);

				// 灵犀-一矢中的
				if (skill.getSkillid() == 11047) {
					fightingSkill.setSkillbeidong(0);
					fightingSkill.setCamp(1);
				}
				addSkill(fightingSkill);

				// 3回合持续技能添加，化险为夷为护盾技能
				if (skill.getSkillid() == 11006 || skill.getSkillid() == 11007 ||
						skill.getSkillid() == 11010 || skill.getSkillid() == 11012 ||
						skill.getSkillid() == 11013 || skill.getSkillid() == 11014 ||
						skill.getSkillid() == 11015 || skill.getSkillid() == 11017 ||
						skill.getSkillid() == 11018 || skill.getSkillid() == 11019 || skill.getSkillid() == 11025
						// 法术系被动加载
						|| skill.getSkillid() == 11027 || skill.getSkillid() == 11029
						|| skill.getSkillid() == 11031 || skill.getSkillid() == 11032
						|| skill.getSkillid() == 11033 || skill.getSkillid() == 11034 || skill.getSkillid() == 11035) {
					AddState addState = new AddState();
					addState.setType(1);
					addState.setStatename(skill.getSkillid() + "");
					addState.setStateEffect(skill.getSkilllevel());
					addState.setSurplus(3);
					addStates.add(addState);
				}
			}
		}
	}
	/**召唤兽初始化*/
	public ManData(RoleSummoning pet,FightingSummon summon) {
		this.camp=summon.getCamp();
		this.man=summon.getMan();
		this.type=1;
		this.skills=new ArrayList<>();
		this.addStates=new ArrayList<>();
		this.id =pet.getSid().intValue();
		this.lvl=BattleMixDeal.petLvlint(pet.getGrade());
		this.zs =BattleMixDeal.petTurnRount(pet.getGrade());
		this.manname=pet.getSummoningname();
		this.skin=pet.getSummoningskin();
		this.Quality=new Ql();


		this.huoyue = pet.getBone();// 活跃 根骨
		this.shanghai = pet.getSpir();// 伤害或者回复或者落宝 灵性
		this.kangluobao = pet.getPower();// 抗落宝 力量
		this.yuanzhu = pet.getSpeed();// 支援 敏捷

		long qm=pet.getFriendliness().longValue();
		Mount mount=summon.getHang().getMid()!=null?AllServiceUtil.getMountService().selectMountsByMID(summon.getHang().getMid()):null;
		addPetSkill(pet.getPetSkills(),qm,summon);//召唤兽技能
		addPetSkill(pet.getPetQlSkills(),qm,summon);//启灵技能
		addPetSkill(pet.getBeastSkills(),qm,summon);//神兽技能
		addPetSkill(pet.getSkill(),qm,summon);//天生技能

		this.lingxi = pet.getLingxi();
		addPetLX(pet.getLingxi(),qm);//灵犀技能

		// 满级灵犀增加隐藏属性
		String lxStr = LingXiUtil.isFull(pet.getLingxi());
		if (lxStr != "") {
			String[] lx = lxStr.split("=");
			int point = Integer.parseInt(lx[1]);
			switch (lx[0]) {
				case "1":
					//物理型，增加破物理几率、程度
					this.Quality.setRolehsfyv(this.Quality.getRolehsfyv() + point/2);
					this.Quality.setRolehsfyl(this.Quality.getRolehsfyl() + point/2);
					break;
				case "2":
					//法术型，增加法术狂暴、狂暴程度
					this.Quality.setRolelfkb(this.Quality.getRolelfkb() + point/2);
					this.Quality.setRoleffkb(this.Quality.getRoleffkb() + point/2);
					this.Quality.setRolesfkb(this.Quality.getRolesfkb() + point/2);
					this.Quality.setRolehfkb(this.Quality.getRolehfkb() + point/2);
					this.Quality.setRoleghkb(this.Quality.getRoleghkb() + point/2);

					this.Quality.setBlfcd(this.Quality.getBlfcd() + point/2);
					this.Quality.setBffcd(this.Quality.getBffcd() + point/2);
					this.Quality.setBsfcd(this.Quality.getBsfcd() + point/2);
					this.Quality.setBhfcd(this.Quality.getBhfcd() + point/2);
					this.Quality.setBghcd(this.Quality.getBghcd() + point/2);
					break;
				case "3":
					//辅助型，增加物理闪避、法术闪避
					this.Quality.setEfsds(this.Quality.getEfsds() + point/5);
					this.Quality.setRolefdsl(this.Quality.getRolefdsl() + point/5);
					break;
				default:
					break;
			}
		}

		CalculationUtil.getPet(Quality, pet, mount, this);
	}

	/**宝宝初始化*/
	public ManData(Baby baby, int camp, int man,Map<String,Double> map) {
		this.id = baby.getBabyid().intValue();
		this.camp = camp;
		this.man = man + 15;
		this.hp = 1;
		this.hp_z = 1;
		this.type = 4;
		String nan = "100001";
		String nv = "100002";
		if (baby.getXiaoxin() > 1000) {
			nan = "100007";
			nv = "100008";
			if (baby.getXiaoxin() > 2000) {
				nan = "100009";
				nv = "100006";
			}
		}
		this.model = baby.getChildSex()==0?nan:nv;
		this.skin=model;
		this.manname = baby.getBabyname();
		// 技能初始化
		skills = new ArrayList<>();
		String Talents = baby.getTalents();
		if (Talents != null && !Talents.equals("")) {
			String[] v = Talents.split("\\|");
			for (int i = 0; i < v.length; i++) {
				String[] vs = v[i].split("=");
				int id = Integer.parseInt(vs[0]);
				if (id==1) {
					CalculationUtil.addValue(map, "AP成长", 0.01);
				}else if (id==2) {
					CalculationUtil.addValue(map, "HP成长", 0.01);
				} else if (id==3) {
					CalculationUtil.addValue(map, "MP成长", 0.01);
				} else {
					Talent talent = GameServer.getTalent(id);
					if (talent != null) {
						int lvl = Integer.parseInt(vs[1]);
						FightingSkill fightingSkill = new FightingSkill(talent, lvl);
						addSkill(fightingSkill);
					}
				}
			}
		}
		addStates = new ArrayList<>();
	}
	/**灵宝初始化*/
	public ManData(Lingbao lingbao,ManData manData){
		this.id=lingbao.getBaoid().intValue();
		this.hp=1;
		this.hp_z=1;
		this.type=3;
		this.id=lingbao.getBaoid().intValue();
		this.model=lingbao.getSkin();
		this.skin=model;
		this.manname=lingbao.getBaoname();
		this.camp=manData.getCamp();
		this.man=manData.getMan()+10;
		this.sp=(int)Double.parseDouble(lingbao.getBaospeed());
		this.huoyue=lingbao.getBaoactive();
		this.qihe=lingbao.getLingbaoqihe();
		int lx=0;
		if (lingbao.getBaotype().equals("攻击")) {
			lx=2;
			this.shanghai=Double.parseDouble(lingbao.getBaoap());
		}else if (lingbao.getBaotype().equals("辅助")) {
			lx=1;
			this.shanghai=Double.parseDouble(lingbao.getBaoreply());
		}else {
			this.shanghai=Double.parseDouble(lingbao.getBaoshot());
		}
		this.kangluobao=Double.parseDouble(lingbao.getResistshot());
		this.yuanzhu=Double.parseDouble(lingbao.getAssistance());
		if (lingbao.getFushis()!=null&&!lingbao.getFushis().equals("")) {
			String[] fs=lingbao.getFushis().split("\\|");
			for (int i = 0; i < fs.length; i++) {
				Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(fs[i]));
				BaseEquip baseEquip=good!=null?good.getEquip():null;
				if (baseEquip!=null&&baseEquip.getQls()!=null) {
					for (int j = baseEquip.getQls().size()-1; j >=0; j--) {
						BaseQl baseQl=baseEquip.getQls().get(j);
						if (baseQl.getKey().equals("活跃")) {
							huoyue+=baseQl.getValue();
						}else if (baseQl.getKey().equals("速度")) {
							sp+=baseQl.getValue();
						}else if (baseQl.getKey().equals("负敏")) {
							sp-=baseQl.getValue();
						}else if (baseQl.getKey().equals("支援")) {
							yuanzhu+=baseQl.getValue()/100;
						}else if (baseQl.getKey().equals("抗落宝")) {
							kangluobao+=baseQl.getValue()/100;
						}else if ((lx==2&&baseQl.getKey().equals("伤害"))||
								(lx==1&&baseQl.getKey().equals("回复"))||
								(lx==0&&baseQl.getKey().equals("落宝"))) {
							shanghai+=baseQl.getValue()/100;
						}
					}
				}
			}
		}
		//技能初始化
		skills = new ArrayList<>();
		int pz=BaseValue.getQv(lingbao.getBaoquality());
		if (lingbao.getSkills() != null && !lingbao.getSkills().equals("")) {
			String[] scs=null;
			if (lingbao.getGoodskill()!=null&&!lingbao.getGoodskill().equals("")){
				scs=lingbao.getGoodskill().split("\\|");
			}
			String[] v = lingbao.getSkills().split("\\|");
			for (int i = 0; i < v.length; i++) {
				if (v[i].equals("")) {
					continue;
				}
				String[] vs = v[i].split("=");
				Skill skill = GameServer.getSkill(vs[0]);
				if (skill != null) {
					int sc=EquipTool.contains(scs, vs[0])?1:0;
					FightingSkill fightingSkill = new FightingSkill(skill,lingbao.getLingbaolvl().intValue() + pz,Integer.parseInt(vs[1]),sc, 0, 0);
					addSkill(fightingSkill);
				}
			}
		}
		if (lingbao.getTianfuskill()!=null&&!lingbao.getTianfuskill().equals("")) {
			String[] tfs=lingbao.getTianfuskill().split("\\|");
			for (int i = 0; i < tfs.length; i++) {
				int lvl=tfs[i].startsWith("高")?2:1;
				if (tfs[i].endsWith("契合")) {
					qihe*=(1+0.1*lvl);
				}else if (tfs[i].endsWith("闪现")) {
					yuanzhu+=0.1*lvl;
				}else if (tfs[i].endsWith("活跃")) {
					huoyue*=(1+0.1*lvl);
				}else if (tfs[i].endsWith("抵抗")) {
					kangluobao+=0.1*lvl;
				}else if (tfs[i].endsWith("敏捷")) {
					sp+=(lvl==2?300:200);
				}else if ((lx==2&&tfs[i].endsWith("增强"))||(lx==1&&tfs[i].endsWith("回生"))) {
					if (tfs[i].indexOf("根骨")!=-1) {
						shanghai+=0.0005*lvl*(int)(manData.getHuoyue()/4);
					}else if (tfs[i].indexOf("灵性")!=-1) {
						shanghai+=0.0005*lvl*(int)(manData.getShanghai()/4);
					}else if (tfs[i].indexOf("力量")!=-1) {
						shanghai+=0.0005*lvl*(int)(manData.getKangluobao()/4);
					}else if (tfs[i].indexOf("敏捷")!=-1) {
						shanghai+=0.0005*lvl*(int)(manData.getYuanzhu()/4);
					}
				}
			}
		}
		addStates=new ArrayList<>();
	}
	/**伙伴初始化*/
	public ManData(int camp, int man,Pal pal,int lvl,ManData master) {
		this.yqz = 149;
		this.nqz = 30;
		this.camp=camp;
		this.man=man;
		this.type=2;
		this.skills=new ArrayList<>();
		this.addStates=new ArrayList<>();
		PalData palData=GameServer.getPalData(pal.getpId());
		this.manname=palData.getName();
		this.model = palData.getSkin();
		this.skin=model;
		CalculationPal.getPal(pal, palData, this, lvl, master);
		/**初始化AI*/
		if (palData.getAis()==null) {
			if (palData.getAi()!=null&&!palData.getAi().equals("")) {
				String[] vs=palData.getAi().split("\\|");
				for (int i = 0; i < vs.length; i++) {
					addAI(vs[i]);
				}
				palData.setAis(ais);
			}
		}else {
			ais=palData.getAis();
		}
	}
	public void JQ(double xs){
		this.hp_z*=xs;
		this.hp=this.hp_z;
		this.ap*=xs;
		this.sp*=xs;
		xs-=1;
		xs*=2;
		Quality.addap((int)xs);
		Quality.addkang((int)(xs*2));
		Quality.addQ(xs*2);
		Quality.addHS(xs*2);
		Quality.setEjs(Quality.getEjs()+xs);
		Quality.setEzs(Quality.getEzs()+xs);
	}

//////////////////////////////////////////////////////////////////////////// 武神山守护 /////////////////////////////////////////////////////////


	/**
	 * 武神山宝宝/灵宝初始化
	 * @param
	 * @param isHaizi
	 */
	public ManData(JSONObject man, boolean isHaizi, double xs) {
		this.id = 1;
		this.camp = 0;
		this.hp = 1;
		this.hp_z = 1;

		if (isHaizi) {
			this.man = man.getIntValue(GLUtil.MAN);;
			this.type = 4;
			this.model = man.getString(GLUtil.MODEL);
			this.skin=man.getString(GLUtil.SKIN);;
			this.manname=man.getString(GLUtil.NAME);
			// 技能初始化
			this.skills = new ArrayList<>();

			JSONArray jineng = man.getJSONArray(GLUtil.FASHU);
			for (int i = 0 ; i < jineng.size() ; i++) {
				int id = jineng.getIntValue(i);
				if (id==1) {
				} else if (id==2) {
				} else if (id==3) {
				} else {
					Talent talent = GameServer.getTalent(id);
					if (talent != null) {
						int lvl = 10;
						FightingSkill fightingSkill = new FightingSkill(talent, lvl);
						addSkill(fightingSkill);
					}
				}
			}
		} else {
			this.id=1;
			this.hp=1;
			this.hp_z=1;
			this.type=3;
			this.model=man.getString(GLUtil.MODEL);
			this.skin=man.getString(GLUtil.SKIN);;
			this.manname=man.getString(GLUtil.NAME);
			this.camp=0;
			this.man=man.getIntValue(GLUtil.MAN);
			this.sp=man.getIntValue(GLUtil.SP);
			this.huoyue=man.getDoubleValue(GLUtil.HUOYUE) * ((xs / 5) < 1 ? 1 : (xs / 5));
			this.qihe=man.getLongValue(GLUtil.QIHE);
			this.shanghai=man.getDoubleValue(GLUtil.SHANGHAI) * ((xs / 4) < 1 ? 1 : (xs / 4));
			this.kangluobao=man.getDoubleValue(GLUtil.KANGLUOBAO);
			this.yuanzhu=man.getDoubleValue(GLUtil.YUANZHU);

			//技能初始化
			this.skills = new ArrayList<>();
			JSONArray jineng = man.getJSONArray(GLUtil.FASHU);
			for (int i = 0 ; i < jineng.size() ; i++) {
				Skill skill = GameServer.getSkill(jineng.getString(i));
				if (skill != null) {
					FightingSkill fightingSkill = new FightingSkill(skill,50 ,1,0, 0, 0);
					addSkill(fightingSkill);
				}
			}
		}
		addStates = new ArrayList<>();
	}


	/**
	 * 武神山召唤兽初始化
	 * @param
	 * @param
	 */
	public ManData(JSONObject shou, double xs){
		yqz = 149;	//怨气值
		nqz = 30;	//怒气值
		this.id = 1;
		this.manname = shou.getString(GLUtil.NAME);
		this.sr = 1;
		this.zs = shou.getIntValue(GLUtil.ZHUANSHENG);
		this.lvl = shou.getIntValue(GLUtil.DENGJI);
		this.model = shou.getString(GLUtil.MODEL);
		this.skin = shou.getString(GLUtil.SKIN);
		this.lingxi = shou.getString(GLUtil.LINGXI);
		this.type = 2;
		this.camp = 0;
		this.man = shou.getIntValue(GLUtil.MAN);
		this.States = 0;
		this.hp = (int)(shou.getIntValue(GLUtil.HP) * xs);
		this.hp_z = hp;
		this.mp =  (int)(shou.getIntValue(GLUtil.MP) * xs);
		this.mp_z = mp;
		this.ap = (int)(shou.getIntValue(GLUtil.AP) * xs);
		this.sp = (int)(shou.getIntValue(GLUtil.SP) * xs/3);
		this.Quality = GsonUtil.getGsonUtil().getgson().fromJson(shou.getString(GLUtil.KANG), Ql.class);
		bearSkills=new ArrayList<>();

		this.huoyue = shou.getIntValue(GLUtil.HUOYUE);// 活跃 根骨
		this.shanghai = shou.getIntValue(GLUtil.SHANGHAI);// 伤害或者回复或者落宝 灵性
		this.kangluobao = shou.getIntValue(GLUtil.KANGLUOBAO);// 抗落宝 力量
		this.yuanzhu = shou.getIntValue(GLUtil.YUANZHU);// 支援 敏捷

		this.skills = new ArrayList<>();
		this.addStates = new ArrayList<>();
		// 技能初始化
		JSONArray jineng = shou.getJSONArray(GLUtil.FASHU);
		for (int i = 0 ; i < jineng.size() ; i++) {
			Skill skill = GameServer.getSkill(jineng.getString(i));// 根据技能id获取
			if (skill != null) {
				FightingSkill fightingSkill = new FightingSkill(skill,this.lvl, 3, xs * 100 , 2000000, 2000);
				if (id>=100&&id<=104) {
					fightingSkill.setSkillsum(10);
					fightingSkill.setSkillcontinued(100);
					if (id == 104) {
						fightingSkill.setSkillhurt(fightingSkill.getSkillhurt() + 300000);
					}
				}
				fightingSkill.setSkillblue(0);
				addSkill(fightingSkill);

				// 3回合持续技能添加，化险为夷为护盾技能
				if (skill.getSkillid() == 11006 || skill.getSkillid() == 11007 ||
						skill.getSkillid() == 11010 || skill.getSkillid() == 11012 ||
						skill.getSkillid() == 11013 || skill.getSkillid() == 11014 ||
						skill.getSkillid() == 11015 || skill.getSkillid() == 11017 ||
						skill.getSkillid() == 11018 || skill.getSkillid() == 11019 || skill.getSkillid() == 11025) {
					AddState addState = new AddState();
					addState.setType(1);
					addState.setStatename(skill.getSkillid() + "");
					addState.setStateEffect(skill.getSkilllevel());
					addState.setSurplus(3);
					addStates.add(addState);
				}
			}
		}
	}

	/**
	 * 武神山守护者属性初始化
	 * @param id
	 * @param name
	 * @param ren
	 */
	public ManData(int id, String name, JSONObject ren, double xs) {
		yqz = 149;	//怨气值
		nqz = 30;	//怒气值
		this.id = id;
		this.manname = name;
		this.sr = 1;
		this.zs = ren.getIntValue(GLUtil.ZHUANSHENG);
		this.lvl = ren.getIntValue(GLUtil.DENGJI);
		this.model = ren.getString(GLUtil.MODEL);
		this.skin = ren.getString(GLUtil.SKIN);
		this.type = 2;
		this.camp = 0;
		this.man = ren.getIntValue(GLUtil.MAN);
		this.States = 0;
		this.hp = (int)(ren.getIntValue(GLUtil.HP) * xs);
		this.hp_z = hp;
		this.mp =  (int)(ren.getIntValue(GLUtil.MP) * xs);
		this.mp_z = mp;
		this.ap = (int)(ren.getIntValue(GLUtil.AP) * xs);
		this.sp = (int)(ren.getIntValue(GLUtil.SP) * xs/2.5);
		this.msg = man == 2 ? "#18现在的我们拥有很强的力量，打不过的话等一刻钟后再来尝试吧。" : "";
		this.Quality = GsonUtil.getGsonUtil().getgson().fromJson(ren.getString(GLUtil.KANG), Ql.class);
		bearSkills=new ArrayList<>();

		this.skills = new ArrayList<>();
		this.addStates = new ArrayList<>();
		// 技能初始化
		JSONArray jineng = ren.getJSONArray(GLUtil.FASHU);
		List<Integer> jinenglist = new ArrayList<Integer>();

		for (int i = 0 ; i < jineng.size() ; i++) {
			Skill skill = GameServer.getSkill(jineng.getString(i));// 根据技能id获取
			if (skill != null) {
				FightingSkill fightingSkill = new FightingSkill(skill,this.lvl, 3,zs * 5000 + 10000, 2000000, (int)xs);
				if (id>=100&&id<=104) {
					fightingSkill.setSkillsum(10);
					fightingSkill.setSkillcontinued(100);
					if (id == 104) {
						fightingSkill.setSkillhurt(fightingSkill.getSkillhurt() + 300000);
					}
				}
				fightingSkill.setSkillblue(0);
				addSkill(fightingSkill);

				if (fightingSkill.getSkillbeidong() == 0) {
					jinenglist.add(fightingSkill.getSkillid());
				}
			}
		}
	}

//////////////////////////////////////////////////////////////////////////武神山守护  END/////////////////////////////////////////////////////////

	/**野怪属性初始化*/
	public ManData(Monster fightMonsterBean, int camp, int man, int lvl) {
		yqz = 149;
		nqz = 30;
		bearSkills=new ArrayList<>();
		double grow = 5;
		double chuhp = 20000;
		double chump = 10000;
		double chuap = 1000;
		double chusp = 1000;
		int chulvl = 180;
		String ColorScheme = null;
		try {
			this.id = fightMonsterBean.getMonsterid();
			this.sr = fightMonsterBean.getMonstersr();
			grow = fightMonsterBean.getMonstergrow();// 成长
			chuhp = fightMonsterBean.getMonsterhp();// 初血
			chump = fightMonsterBean.getMonstermp();// 初蓝
			chuap = fightMonsterBean.getMonsterap();// 初功
			chusp = fightMonsterBean.getMonstersp();// 初敏
			chulvl = lvl == 0 ? fightMonsterBean.getMonsterlvl() : lvl;// 等级
			if (!fightMonsterBean.getMonsterpet().equals("")) {
				this.zs = Integer.parseInt(fightMonsterBean.getMonsterpet());
			}
			if (fightMonsterBean.getColor() != null && !fightMonsterBean.getColor().equals("")) {
				ColorScheme = fightMonsterBean.getColor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.model = fightMonsterBean.getMonsterskin();
		this.skin=model;
		this.manname = fightMonsterBean.getMonstername();
		this.type = 2;
		this.camp = camp;
		this.man = man;
		this.States = 0;
		this.lvl = chulvl;
		this.hp = PetGrade.getRoleValue(chulvl, chulvl * 8, grow,(int) chuhp, 0);
		this.hp_z = hp;
		this.mp = PetGrade.getRoleValue(chulvl, chulvl * 4, grow, (int) chump,1);
		this.mp_z = mp;
		this.ap = PetGrade.getRoleValue(chulvl, chulvl * 3, grow,(int) chuap, 2);
		this.sp = PetGrade.getRoleValue(chulvl, chulvl, grow, (int) chusp, 3);
		this.addStates = new ArrayList<>();
		// 技能初始化
		boolean isTX=false;//判断是否添加召唤兽装备技能特效
		this.skills = new ArrayList<>();
		if (fightMonsterBean.getMonsterskill() != null
				&& !fightMonsterBean.getMonsterskill().equals("")) {
			String[] v = fightMonsterBean.getMonsterskill().split("\\|");
			double sld=this.lvl * 125;
			if (id>=2000&&id<=2105) {sld=1;}
			for (int i = 0; i < v.length; i++) {
				try {
					if (v[i].equals("")) {continue;}
					String newid = v[i];
					int id = Integer.parseInt(v[i]);
					if (id == 100) {newid = "1030";}
					else if (id == 101) {newid = "1035";}
					else if (id == 102) {newid = "1205";}
					else if (id == 103) {newid = "1203";}
					else if (id == 104) {newid = "1056";}
					int skilltype = skilltype(Integer.parseInt(newid));
					if (skilltype == 1) {continue;}
					else if (skilltype == 3){CreepsMixdeal.addNeiDanSkill(this, id);continue;}
					else if (skilltype == 4){isTX=true;}
					else if (skilltype == 1207) {
						for (int j = 0; j < skills.size(); j++) {
							FightingSkill skill2=skills.get(j);
							if (skill2.getSkillid() == 1831||skill2.getSkillid() == 1833)
								skill2.setSkillhurt(skill2.getSkillhurt() + 15);
						}
						continue;
					}
					Skill skill = GameServer.getSkill(newid);// 根据技能id获取
					if (skill != null) {
						FightingSkill fightingSkill = new FightingSkill(skill,this.lvl, 3,sld, 2000000, 20);
						if (id>=100&&id<=104) {
							fightingSkill.setSkillsum(10);
							fightingSkill.setSkillcontinued(100);
							if (id == 104) {
								fightingSkill.setSkillhurt(fightingSkill.getSkillhurt() + 300000);
							}
						}
						fightingSkill.setSkillblue(0);
						addSkill(fightingSkill);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		this.msg = fightMonsterBean.getMsg();
		try {
			Quality = fightMonsterBean.getQl();
			Quality.setKzml(Quality.getKzml() + CustomFunction.XS(hp_z, 0.4)+ 5);
			if (fightMonsterBean.getMonsterai() != null && !fightMonsterBean.getMonsterai().equals("")) {
				String[] v = fightMonsterBean.getMonsterai().split("\\|");// 初始化ai
				for (int i = v.length - 1; i >= 0; i--) {
					addAI(v[i]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(model);
		buffer.append("_1_1");
		if (ColorScheme != null) {
			buffer.append("_");
			buffer.append(ColorScheme);
		}
		if (isTX) {buffer.append("&jx_6_-1");}
		model = buffer.toString();

	}

	/**判断是否是增加属性  0不是  1是  2是且为组合技能
	 * 1207可以增加召唤兽连击率10%，并加成高级分裂攻击和分花拂柳的几率15%。(被动)
	 */
	public int skilltype(int id){
		if (id==3034||(id>=1800&&id<=1803)||(id>=1810&&id<=1819)||
				id==1208||id==1209||id==1213||id==1214||id==1222||
				id==1226||(id>=1845&&id<=1846)||(id>=1855&&id<=1857)||
				(id>=1859&&id<=1860)||id==1235) {
			return 1;
		}else if (id>=1820&&id<=1827) {
			return 2;
		}else if (id>=1511&&id<=1520) {
			return 3;
		}else if (id>=1300&&id<=1334) {
			return 4;
		}else if (id == 1207) {
			return 1207;
		}

		return 0;
	}
	/** 技能添加 */
	public void addSkill(FightingSkill skill) {
		if (skills == null) {
			skills = new ArrayList<>();
		}
		if (skill.getSkillid() == 1838 || skill.getSkillid() == 1333|| skill.getSkillid() == 1334|| skill.getSkillid() == 7019) {
			AddState addState = new AddState();
			addState.setStatename("冷却");
			addState.setStateEffect(skill.getSkillid());
			addState.setSurplus(3);
			addStates.add(addState);
		}
		// 判断是否加特效
		if (skill.getSkillid() == 6022 || skill.getSkillid() == 6024) {
			AddState addState = new AddState(skill.getSkillid() + "", 0, 9999);
			addStates.add(addState);
		}
		if (skill.getSkillid() == 9223 || skill.getSkillid() == 9342) {//减蓝
			FightingSkill skill2 = null;
			if (skill.getSkillid() == 9223) {
				skill2 = getSkillId(1040);
			} else if (skill.getSkillid() == 9342) {
				skill2 = getSkillId(1075);
			}
			if (skill2 != null) {
				int bule = skill2.getSkillblue();
				bule -= skill.getSkillhurt();
				if (bule < 0) {
					bule = 0;
				}
				skill2.setSkillblue(bule);
			}
			return;
		}
		skills.add(skill);
	}
	/**根据技能id获取*/
	public FightingSkill getSkillId(int id){
		if (skills==null) {return null;}
		for (int i = skills.size()-1; i>=0; i--) {
			if (skills.get(i).getSkillid()==id) {
				return skills.get(i);
			}
		}
		return null;
	}
	/**内丹重复判断*/
	public void neidang(String type,int lvl){
		for (int i = 0; i < addStates.size(); i++) {
			if (addStates.get(i).getStatename().indexOf(type)!=-1) {
				if (addStates.get(i).getStateEffect()<lvl) {
					addStates.get(i).setStatename(type+lvl);
					addStates.get(i).setStateEffect(lvl);
				}
				return;
			}
		}
		AddState addState=new AddState(type+lvl,lvl,9999);
		addStates.add(addState);
	}
	// 初始化ai
	public void addAI(String v) {
		if (ais == null) {
			ais = new ArrayList<>();
		}
		String[] vs = v.split("=");
		if (vs[0].equals("状态")) {
			vs = vs[1].split("-");
			if (vs[0].equals("物理狂暴")) {
				sp += 600;
				getZDSKILL(1831, 50000000);
				getZDSKILL(1833, 50000000);
			} else if (vs[0].equals("仙法狂暴")) {
				sp += 600;
				Quality.kuangbao(1);
			}
			this.addStates.add(new AddState(vs[0], 0, Integer.parseInt(vs[1])));
		} else if (vs[0].equals("条件")) {
			// 条件=死亡-1000&死亡-1001=逃跑(救人-1001)(狂暴)
			List<AICondition> aiConditions = new ArrayList<>();
			String[] vt = vs[1].split("&");
			for (int j = 0; j < vt.length; j++) {
				String[] vts = vt[j].split("-");
				if (vts[0].equals("概率")) {
					aiConditions.add(new AICondition(AI.AI_BILITY, Integer
							.parseInt(vts[1])));
				} else if (vts[0].equals("死亡")) {
					aiConditions.add(new AICondition(AI.AI_DEATH, Integer
							.parseInt(vts[1])));
				} else if (vts[0].equals("回合")) {
					aiConditions.add(new AICondition(AI.AI_ROUND, Integer
							.parseInt(vts[1])));
				} else if (vts[0].equals("法术")) {
					aiConditions.add(new AICondition(AI.AI_SKILL, Integer
							.parseInt(vts[1])));
				} else if (vts[0].equals("承受法术")) {
					AICondition aiCondition = new AICondition(AI.AI_BEARSKILL,
							vts[1]);
					if (vts.length >= 3) {
						aiCondition.setY(Integer.parseInt(vts[2]));
					}
					aiConditions.add(aiCondition);
				} else if (vts[0].equals("携带状态")) {
					AICondition aiCondition = new AICondition(AI.AI_CARRYSTATE,vts[1]);
					if (vts.length >= 3) {
						aiCondition.setY(Integer.parseInt(vts[2]));
					}
					aiConditions.add(aiCondition);
				}
			}
			AI ai = null;
			vt = vs[2].split("-");
			if (vt[0].equals("出手状态") || vt[0].equals("回合状态")) {
				int value = 0;
				if (vt.length >= 4) {
					value = Integer.parseInt(vt[3]);
				}
				ai = new AI(vt[0], vt[1], Integer.parseInt(vt[2]),
						aiConditions, value);
			} else if (vt.length == 1) {
				ai = new AI(vt[0], 0, 0, aiConditions);
			} else if (vt.length == 2) {
				ai = new AI(vt[0], Integer.parseInt(vt[1]), 0, aiConditions);
			} else if (vt.length == 3) {
				ai = new AI(vt[0], Integer.parseInt(vt[1]),
						Integer.parseInt(vt[2]), aiConditions);
			}
			if (ai != null) {
				ais.add(ai);
			}
		}else if (vs[0].equals("指令")) {
			if (ais == null) {
				ais = new ArrayList<>();
			}
			ais.add(new AI(vs[0], vs));
		} else {
			if (vs.length <= 1) {
				return;
			}
			String[] vs1 = vs[1].split("&");
			if (vs1.length <= 1) {
				return;
			}
			List<AICondition> aiConditions = new ArrayList<>();
			aiConditions.add(new AICondition(AI.AI_BILITY, Integer
					.parseInt(vs1[0])));
			int aiman = 0;
			int value = Integer.parseInt(vs1[1]);
			if (vs[0].equals("加血")) {
				aiman = this.id;
			} else if (vs[0].equals("救人")) {
				vs[0] = "加血";
			}
			if (ais == null) {
				ais = new ArrayList<>();
			}
			ais.add(new AI(vs[0], aiman, value, aiConditions));
		}
	}

	/** 根据技能名字放回技能 */
	public FightingSkill skillname(String skillname) {
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getSkillname().equals(skillname)) {
				return skills.get(i);
			}
		}
		return null;
	}

	/** 一次性技能，移除式获取 */
	public FightingSkill findRemoveSkill(String skillname) {
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getSkillname().equals(skillname)) {
				FightingSkill skill = skills.get(i);
				skills.remove(i);
				return skill;
			}
		}
		return null;
	}

	/** 根据技能ID放回技能 */
	public FightingSkill skillId(int id) {
		for (int i = skills.size()-1; i >=0; i--) {
			if (skills.get(i).getSkillid() == id) {
				return skills.get(i);
			}
		}
		return null;
	}

	/**
	 * 查找技能，找到任意一个匹配即返回
	 * @param
	 * @return
	 */
	public FightingSkill skillIds(int ...ids) {
		for (int i = skills.size()-1; i >=0; i--) {
			for (int id : ids) {
				if (skills.get(i).getSkillid() == id) {
					return skills.get(i);
				}
			}
		}
		return null;
	}

	/** 寻找主动技能 */
	public FightingSkill getSkillName(String skillname) {
		for (int i = 0; i < skills.size(); i++) {
			FightingSkill skill = skills.get(i);
			if (skill.getSkillbeidong() == 0
					&& skill.getSkillname().equals(skillname)) {
				return skill;
			}
		}
		return null;
	}

	/** 直扣处理 */
	public void getFightingState(FightingState org) {
		org.setCamp(camp);
		org.setMan(man);
		org.setStartState(TypeUtil.JN);
	}

	/** 致死处理 */
	public void ChangeDataZS(FightingState state) {
		state.setCamp(camp);
		state.setMan(man);
		state.setStartState(TypeUtil.JN);
		state.setHp_Change(-hp);
		this.hp = 0;
		States = 1;
		addnq(-5, state);
	}

	/***/
	public void ChangeData(ChangeFighting change, FightingState state) {
		state.setCamp(camp);
		state.setMan(man);
		if (change.getChangehp() < 0) {
			state.setStartState("被攻击");
			// if (getSkillType(TypeUtil.ZY_WD)!=null) {change.setChangehp(0);}
			if (type == 0) {
				FightingSkill skill = skillId(9387);
				if (skill != null) {
					int maxhp = (int) (hp_z * skill.getSkillhurt() / 100.0);
					if (-change.getChangehp() > maxhp) {
						change.setChangehp(-maxhp);
					}
				}
			} else if (type == 1) {
				if (huoyue >= 700) {
					FightingSkill skill = skillId(1322);// 皮糙肉厚
					if (skill != null) {
						int maxhp = (int) (hp_z * (100-skill.getSkillgain()) / 100.0);
						if (-change.getChangehp() > maxhp) {
							change.setChangehp(-maxhp);
						}
					}
				}
			}
			if (type == 0 && change.getSkill(9283) == null) {
				FightingSkill fightingSkill = change.getSkill(9286);
				addnq((fightingSkill != null ? -(int) fightingSkill.getSkillhurt(): 0) + 3, state);
			}
		} else if (change.getChangehp() > 0) {
			if (this.States!=0&&xzstate(TypeUtil.BB_DHSM)!=null) {
				change.setChangehp(0);
				change.setChangemp(0);
			}else {
				AddState addState = xzstate(TypeUtil.TY_GH_XGNC);
				if (addState != null) {
					change.setChangehp(0);
					change.setChangemp(0);
				} else {
					addState = xzstate(TypeUtil.TY_ZS_FH);
					if (addState != null) {
						change.setChangehp((int) (change.getChangehp() * (1 - addState.getStateEffect() / 100.0)));
					}
				}
			}
		}
		if (change.getChangehp() != 0) {// 处理血量变化
			int changehp = hp + change.getChangehp();
			if (changehp <= 0) {
				this.hp = 0;
				States = 1;
				addnq(-5, state);
			} else {
				if (changehp > hp_z) {
					this.hp = hp_z;
					States = 0;
				} else {
					this.hp = changehp;
					States = 0;
				}
			}
			state.setHp_Change(change.getChangehp());
		}
		if (change.getChangemp() < 0) {
			FightingSkill skill = getAppendSkill(9246);
			if (skill != null) {
				change.setChangemp((int) (change.getChangemp() * (1-skill.getSkillhurt()/100.0)));
			}
		}
		if (change.getChangemp() != 0) {// 处理蓝量变化
			int changemp = mp + change.getChangemp();
			if (changemp > mp_z) {
				this.mp = mp_z;
			} else if (changemp > 0) {
				this.mp = changemp;
			} else {
				this.mp = 0;
			}
			state.setMp_Change(change.getChangemp());
		}

		// 减速|减人仙|减魔鬼|TypeUtil.ZD
		if (change.getChangetype2() != null) {
			if (change.getChangetype2().equals("非控制减益")) {
				state.setEndState_2(change.getChangetype2());
				RemoveAbnormal(state, values4);
			}
		}
		if (change.getChangetype().equals("")) {
			return;
		}
		// 处理持续回合的类型
		String types = change.getChangetype();
		if (types.equals("清除状态")) {
			state.setEndState_2(types);
			RemoveAbnormal(state, values1);
			return;
		} else if (types.equals("清除异常状态")) {
			state.setEndState_2(types);
			RemoveAbnormal(state, values2);
			return;
		} else if (types.equals("清除五行")) {
			state.setEndState_2(types);
			RemoveAbnormal(values3);
			return;
		} else if (types.equals(TypeUtil.TY_SSC_LFHX)) {
			state.setEndState_2("清除异常状态");
			RemoveAbnormal(state, values2);
			if (change.getChangevlaue() <= 2) {
				return;
			}
		} else if (types.equals(TypeUtil.BB_E_HNYG)) {
			state.setEndState_2("清除状态");
			RemoveAbnormal(state, values1);
		}

		if (types.equals(TypeUtil.YW) || types.equals(TypeUtil.HS)|| types.equals(TypeUtil.HL)) {
			RemoveAbnormal(TypeUtil.YW, TypeUtil.HS, TypeUtil.HL, TypeUtil.FY);
		} else if (types.equals(TypeUtil.FY)) {
			RemoveAbnormal(TypeUtil.YW, TypeUtil.HS, TypeUtil.HL, TypeUtil.FY,TypeUtil.ZD);
		} else if (types.equals("金") || types.equals("木") || types.equals("水")
				|| types.equals("土") || types.equals("火")) {
			RemoveAbnormal(values3);
		} else if (types.equals(TypeUtil.TZ_MXJX)) {
			RemoveAbnormal(TypeUtil.YW, TypeUtil.HS, TypeUtil.HL,TypeUtil.TZ_MXJX);
		} else if (types.equals(TypeUtil.TZ_HGFZ)) {
			RemoveAbnormal(TypeUtil.TZ_HGFZ, TypeUtil.YW, TypeUtil.FY,
					TypeUtil.ZD, TypeUtil.HS, TypeUtil.HL, TypeUtil.FB_JGE,
					TypeUtil.FB_QW);
		}else if (types.equals(TypeUtil.JS) || types.equals(TypeUtil.LL)) {
//	    	9804	浪遏飞舟	沧波状态下的目标有{公式一}%几率不能获得加攻和加速状态。
			if (getAppendSkill(9804)!=null) {return;}
			RemoveAbnormal(types);
		} else if (!(types.equals(TypeUtil.L_FY) || types.equals(TypeUtil.L_LL)|| types.equals(TypeUtil.TY_L_GL_PYGQ))) {
			RemoveAbnormal(types);
		}


		if (types.equals(TypeUtil.YW)) {
			double a = 65, b = 65, c = 0;
			a += change.getChangevlaue2();
			b += change.getChangevlaue2();
			c += change.getChangevlaue2();
			if (change.getSkills() != null) {
				for (int i = change.getSkills().size() - 1; i >= 0; i--) {
					FightingSkill skill = change.getSkills().get(i);
					if (skill.getSkillid() == 9346) {
						b += skill.getSkillhurt();
						change.getSkills().remove(i);
					} else if (skill.getSkillid() == 9349) {
						c += skill.getSkillhurt();
						change.getSkills().remove(i);
					}
				}
				if (change.getSkills().size() == 0) {
					change.setSkill(null);
				}
			}
			AddState addState2 = new AddState();
			addState2.setStatename(change.getChangetype());
			addState2.setStateEffect(-1);
			addState2.setSurplus(change.getChangesum());
			addState2.setSkills(change.getSkills());
			addStates.add(addState2);
			for (int i = 0; i < skills.size(); i++) {
				if (skills.get(i).getSkillbeidong() == 1) {
					continue;
				}
				int skillId = skills.get(i).getSkillid();
				if ((skillId >= 1001 && skillId <= 1100)
						|| (skillId >= 1600 && skillId <= 2000)) {
					if (Battlefield.isV(a)) {
						AddState addState = new AddState();
						addState.setStatename(change.getChangetype());
						addState.setStateEffect(skillId);
						addState.setSurplus(change.getChangesum());
						addStates.add(addState);
					}
				} else if (skillId >= 5001 && skillId <= 5015) {
					if (Battlefield.isV(b)) {
						AddState addState = new AddState();
						addState.setStatename(change.getChangetype());
						addState.setStateEffect(skillId);
						addState.setSurplus(change.getChangesum());
						addStates.add(addState);
					}
				} else if (skillId >= 1200 && skillId <= 1300) {
					if (Battlefield.isV(c)) {
						AddState addState = new AddState();
						addState.setStatename(change.getChangetype());
						addState.setStateEffect(skillId);
						addState.setSurplus(change.getChangesum());
						addStates.add(addState);
					}
				}
			}
		} else if (types.equals(TypeUtil.L_FY) || types.equals(TypeUtil.L_LL)|| types.equals(TypeUtil.TY_L_GL_PYGQ)) {
			int lx = types.equals(TypeUtil.L_FY) ? 0 : 1;
			AddState addState = xzstate(types);
			if (addState == null) {
				addState = new AddState();
				addStates.add(addState);
			} else {
				if (lx == 0) {
					UP(state, 0, addState.getStateEffect() / 100D);
					UP(state, 1, addState.getStateEffect2() / 100D);
				} else {
					UPModel(state, null);
					UP(state, 0, -addState.getStateEffect() / 100D);
				}
			}
			addState.setStatename(change.getChangetype());
			addState.setStateEffect(change.getChangevlaue());
			addState.setStateEffect2(change.getChangevlaue2());
			addState.setSurplus(change.getChangesum());
			addState.setSkills(change.getSkills());
			if (lx == 0) {
				UP(state, 0, -addState.getStateEffect() / 100D);
				UP(state, 1, -addState.getStateEffect2() / 100D);
			} else {
				if (types.equals(TypeUtil.L_LL)) {
					UPModel(state, CreepsMixdeal.getL_LL(model));
				}
				UP(state, 0, addState.getStateEffect() / 100D);
			}
		} else {
			AddState addState = new AddState();
			addState.setStatename(change.getChangetype());
			addState.setStateEffect(change.getChangevlaue());
			addState.setStateEffect2(change.getChangevlaue2());
			addState.setSurplus(change.getChangesum());
			addState.setSkills(change.getSkills());
			addStates.add(addState);
		}
		state.setEndState_1(change.getChangetype());
	}

	public static String[] values1 = { "减人仙", "减魔鬼", "庇护", "遗忘", "封印", "中毒","昏睡", "混乱",
			"金", "木", "水", "火", "土", "力量", "抗性", "加速", "smmh" };
	public static String[] values2 = { "遗忘", "封印", "中毒", "昏睡", "混乱",TypeUtil.FB_JGE, TypeUtil.FB_QW,TypeUtil.BB_LHFM,TypeUtil.BB_DHSM};
	public static String[] values3 = { "金", "木", "水", "火", "土" };
	public static String[] values4 = { "减速", "减人仙", "减人仙", TypeUtil.ZD };

	/** 清除指定状态 */
	public void RemoveAbnormal(String... values) {
		for (int i = addStates.size() - 1; i >= 0; i--) {
			String statename = addStates.get(i).getStatename();
			for (int j = 0; j < values.length; j++) {
				if (statename.equals(values[j])) {
					addStates.remove(i);
				}
			}
		}
	}

	/** 清除考虑代价的 */
	public void RemoveAbnormal(FightingState state, String... values) {
		List<AddState> rAddStates = null;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			AddState addState = addStates.get(i);
			String statename = addState.getStatename();
			for (int j = 0; j < values.length; j++) {
				if (statename.equals(values[j])) {
					addStates.remove(i);
					if (addState.getSkills() != null) {
						if (rAddStates == null) {
							rAddStates = new ArrayList<>();
						}
						rAddStates.add(addState);
					}
				}
			}
		}
		if (rAddStates != null) {
			MixDeal.rid(this, state, rAddStates);
		}
	}

	/** 判断当前是否有这个状态 */
	public boolean chongfustate(AddState addState) {
		for (int i = 0; i < addStates.size(); i++) {
			if (addStates.get(i).getStatename().equals(addState.getStatename())) {
				addStates.remove(addStates.get(i));
				return true;
			}
		}
		return false;
	}

	/** 找到摸个状态返回 */
	public AddState xzstate(String type) {
		if (addStates == null)
			return null;
		for (int i = 0; i < addStates.size(); i++) {
			if (addStates.get(i).getStatename().equals(type)) {
				return addStates.get(i);
			}
		}
		return null;
	}

	/** 召唤和召回 */
	public ManData bb(String bbid) {
		ManData petData = null;
		int id = -1;
		if (!bbid.equals("召回")) {
			id = Integer.parseInt(bbid);
		}
		if (id != -1) {
			for (int i = 0; i < pets.size(); i++) {
				if (pets.get(i).getPlay() == 1) {
					pets.get(i).setPlay(2);
				}
				if (id == pets.get(i).getHang().getId().intValue()) {
					pets.get(i).setPlay(1);
					petData = pets.get(i).getPet();
				}
			}
		}
		return petData;
	}

	/** 根据状态返回字符串 */
	public List<String> ztstlist(FightingManData fightingManData) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < addStates.size(); i++) {
			AddState addState = addStates.get(i);
			if (addState.getStatename().equals("冷却")) {continue;}
			if (addState.getStatename().equals("回蓝")) {continue;}
			if (addState.getStatename().equals("仙法狂暴")) {continue;}
			if (addState.getStatename().equals("物理狂暴")) {continue;}
			if (addState.getStatename().equals("隐身")) {
				fightingManData.setAlpha(0.3f);
			} else {
				if (!list.contains(addState.getStatename())){
					list.add(addState.getStatename());
				}
			}
		}
		return list;
	}

	/**
	 * 返回当前可用技能
	 */
	public List<String> skilluse() {
		List<String> s = new ArrayList<>();
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getSkillbeidong() == 0
					&& !SkillCooling(skills.get(i).getSkillid())) {
				s.add(skills.get(i).getSkillname());
			}
		}
		return s;
	}

	/**
	 * 判断该技能是否为冷却中
	 *
	 * @return
	 */
	public boolean SkillCooling(int skillid) {
		for (int i = 0; i < addStates.size(); i++) {
			AddState addState = addStates.get(i);
			if (addState.getStatename().equals(TypeUtil.YW)) {
				if (addState.getStateEffect() == skillid) {
					return true;
				}
			} else if (addState.getStatename().equals("冷却")) {
				if (addState.getStateEffect() == skillid) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean SkillCooling(FightingSkill skill,Battlefield battlefield) {
		for (int i=0,length=addStates.size();i<length;i++) {
			AddState addState = addStates.get(i);
			if (addState.getStateEffect()==skill.getSkillid()) {
				if (addState.getStatename().equals(TypeUtil.YW)) {
					battlefield.systemMsg(this,null,6,skill);
					return true;
				}else if (addState.getStatename().equals("冷却")) {
					battlefield.systemMsg(this,null,7,skill);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 根据类型判断是否属于可作用的人 1 离开的人 逃跑成功的人不可作用 2 目前没有作用于小孩的技能 Lingbao 0表示选择人 1表示选择灵宝
	 * 2表示选召唤兽 3表示选玩家 nocamp 选择与nocamp不同阵营的人 point 不与这个point相同的人 death
	 * 判断是否可以作用于死人 0选择活人 1可以活人和死人 2必须选择死人 Stealth 可以作用于 0不可以作用于隐身的人 1可以作用于隐身的人
	 * 2只可以作用于隐身的人 0表示不能选择封印 1表示能选择封印 2表示只能选择封印 活死人判断 阵营不相同判断 隐身判断 人灵宝小孩判断
	 * 不为这个人判断 不为封印判断 yao 判断是否可以用药 0可以 1不行
	 */
	public boolean zuoyong(int death, int nocamp, int Stealth, int lingbao,
						   PathPoint point, int fengyin, int yao, int hs) {
		// 不为逃跑
		if (States == 2)
			return false;
		// 不为小孩
		if (type == 4)
			return false;
		// 不为相同nocamp阵营
		if (camp == nocamp)
			return false;
		// 不为制定的人
		if (zhiding(point))
			return false;
		// 选人要求
		if (!DemandLingbao(lingbao))
			return false;
		// 满足生存要求
		if (!Demanddeath(death))
			return false;
		// 不为封印
		if (!fengyin(fengyin, TypeUtil.FY))
			return false;
		// 不为昏睡
		if (!fengyin(hs, TypeUtil.HS))
			return false;
		// 满足隐身要求
		if (!fengyin(Stealth, "隐身"))
			return false;
		// 满足用药
		if (!Demandyao(yao))
			return false;
		return true;
	}

	/** 满足用药 */
	public boolean Demandyao(int yao) {
		if (yao == 0)
			return true;
		if (xzstate("归墟") == null)
			return true;
		return false;
	}

	/** 判断是否是指定的人 是返回true */
	public boolean zhiding(PathPoint point) {
		if (point.getX() == camp && point.getY() == man) {
			return true;
		}
		return false;
	}

	/** States状态 判断是否可以作用于死人 0选择活人 1可以活人和死人 2必须选择死人 */
	public boolean Demanddeath(int death) {
		if (death == 0) {
			if (States == 0) {
				return true;
			}
		} else if (death == 1) {
			return true;
		} else if (death == 2) {
			if (States == 1) {
				return true;
			}
		}
		return false;
	}

	/** 不可以作用于拥有指定状态的人 1可以作用于拥有指定状态的人 2只可以作用于拥有指定状态的人 */
	public boolean fengyin(int fengyin, String type) {
		if (fengyin == 0) {
			if (xzstate(type) == null) {
				return true;
			}
		} else if (fengyin == 1) {
			return true;
		} else if (fengyin == 2) {
			if (xzstate(type) != null) {
				return true;
			}
		}
		return false;
	}

	/** 0表示选择人 1表示选择灵宝 2表示选召唤兽 3表示选玩家 4玩家或者召唤兽 */
	public boolean DemandLingbao(int Lingbao) {
		if (Lingbao == 0) {
			if (type != 3)
				return true;
		} else if (Lingbao == 1) {
			if (type == 3)
				return true;
		} else if (Lingbao == 2) {
			if (type == 1)
				return true;
		} else if (Lingbao == 3) {
			if (type == 0)
				return true;
		} else if (Lingbao == 4) {
			if (type == 0 || type == 1)
				return true;
		}
		return false;
	}

	/** 简化的判断是否可以执行 */
	public boolean isLicense(FightingSkill skill) {
		if (skill.getSkillid() >= 5001 && skill.getSkillid() <= 5015) {
			if (yqz >= skill.getSkillblue()) {
				return true;
			} else {
				return false;
			}
		} else if (skill.getSkillid() >= 9000 && skill.getSkillid() <= 9999) {
			if (nqz >= skill.getSkillblue()) {
				return true;
			} else {
				return false;
			}
		} else if (skill.getSkillname().equals("兵临城下")) {
			if (mp >= (int) (mp_z * getBlcxKc(2)) && hp > (int) (hp_z * getBlcxKc(1))) {
				return true;
			} else {
				return false;
			}
		} else if (skill.getSkillname().equals("奋蹄扬威")
				|| skill.getSkillname().equals("势如破竹")
				|| skill.getSkillname().equals("黑夜蒙蔽")
				|| skill.getSkillname().equals("无极")) {
			if (hp > (int) (hp_z * 0.5)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (mp >= skill.getSkillblue()) {
				return true;
			} else {
				return false;
			}
		}
	}

	/** 扣除代价 */
	public boolean daijia(FightingState fightingState, Battlefield battlefield) {
		if (States == 1) {
			return true;
		}
		fightingState.setCamp(camp);
		fightingState.setMan(man);
		for (int i = skills.size() - 1; i >= 0; i--) {
			FightingSkill skill = skills.get(i);
			if (skill.getSkillname().equals(fightingState.getEndState())) {
				if (skill.getSkillid() >= 5001 && skill.getSkillid() <= 5015) {
					yqz -= skill.getSkillblue();
					fightingState.setYq_c(-skill.getSkillblue());
					return false;
				} else if (skill.getSkillid() >= 9000
						&& skill.getSkillid() <= 9999) {
					nqz -= skill.getSkillblue();
					fightingState.setNq_c(-skill.getSkillblue());
					return false;
				}
				// 报复
				MixDeal.clbf(skill, this, battlefield);
				if (States == 1) {
					return true;
				}
				// 2个特殊类型的技能
				if (skill.getSkillname().equals("兵临城下")) {

					double cmp = getBlcxKc(2);
					double chp = getBlcxKc(1);

					mp = mp + (int) (-mp_z * cmp);
					hp = hp + (int) (-hp_z * chp);
					fightingState.setHp_Change((int) (-hp_z * chp));
					fightingState.setMp_Change((int) (-mp_z * cmp));
				} else if (skill.getSkillname().equals("奋蹄扬威")
						|| skill.getSkillname().equals("黑夜蒙蔽")
						|| skill.getSkillname().equals("无极")) {
					hp = hp + (int) (-hp_z * 0.5);
					fightingState.setHp_Change((int) (-hp_z * 0.5));
				} else if (skill.getSkillname().equals("势如破竹")) {
					hp = hp + (int) (-hp_z * 0.1);
					fightingState.setHp_Change((int) (-hp_z * 0.1));
				} else if (skill.getSkilltype().equals(TypeUtil.BB_SS)
						|| skill.getSkilltype().equals(TypeUtil.BB_E_YHSS)) {
					fightingState.setHp_Change(-hp);
					States = 1;
					hp = 0;
				} else {
					if (skill.getSkillid() >= 1001
							&& skill.getSkillid() <= 1100) {
						addnq(2, fightingState);
					}
					mp = mp - skill.getSkillblue();
					fightingState.setMp_Change(-skill.getSkillblue());
				}
				SummonType.xianzhi(this, skill);
				return false;
			}
		}
		return true;
	}

	/** 控制系 */
	public boolean daijia(FightingSkill skill, FightingState fightingState,
						  Battlefield battlefield) {
		if (States == 1) {
			return true;
		}
		fightingState.setCamp(camp);
		fightingState.setMan(man);
		if (skill.getSkillid() >= 5001 && skill.getSkillid() <= 5015) {
			yqz -= skill.getSkillblue();
			fightingState.setYq_c(-skill.getSkillblue());
			return false;
		} else if (skill.getSkillid() >= 9000 && skill.getSkillid() <= 9999) {
			nqz -= skill.getSkillblue();
			fightingState.setNq_c(-skill.getSkillblue());
			return false;
		}
		// 报复
		MixDeal.clbf(skill, this, battlefield);
		if (States == 1) {
			return true;
		}

		// 3个特殊类型的技能
		if (skill.getSkillname().equals("兵临城下")) {
			double cmp = getBlcxKc(2);
			double chp = getBlcxKc(1);

			mp = mp + (int) (-mp_z * cmp);
			hp = hp + (int) (-hp_z * chp);
			fightingState.setHp_Change((int) (-hp_z * chp));
			fightingState.setMp_Change((int) (-mp_z * cmp));
		} else if (skill.getSkillname().equals("奋蹄扬威")
				|| skill.getSkillname().equals("黑夜蒙蔽")
				|| skill.getSkillname().equals("无极")) {
			hp = hp + (int) (-hp_z * 0.5);
			fightingState.setHp_Change((int) (-hp_z * 0.5));
		} else if (skill.getSkillname().equals("势如破竹")) {
			hp = hp + (int) (-hp_z * 0.1);
			fightingState.setHp_Change((int) (-hp_z * 0.1));
		} else if (skill.getSkilltype().equals(TypeUtil.BB_SS)
				|| skill.getSkilltype().equals(TypeUtil.BB_E_YHSS)) {
			fightingState.setHp_Change(-hp);
			States = 1;
			hp = 0;
		} else {
			mp = mp - skill.getSkillblue();
			fightingState.setMp_Change(-skill.getSkillblue());
		}
		SummonType.xianzhi(this, skill);
		return false;
	}

	/**
	 * 捕获判断
	 *
	 * @return
	 */
	public int capture() {
		if (type == 2) {
			if (Battlefield.random.nextInt(100) < 30) {
				return zs;
			}
		}
		return -1;
	}

	/**
	 * 反震伤害
	 */
	public double getfz(long ap) {
		if (States != 0)
			return 0;
		if (Battlefield.random.nextInt(100) + 1 < Quality.getRoleffzl()) {
			if (Quality.getRoleffzcd() > 200) {
				return ap * 2;
			}
			return ap * (Quality.getRoleffzcd() / 100);
		}
		return 0;
	}

	// 伤害 无 风 雷 水 火 中毒 鬼火 三尸
	// 0 抗 1忽视 2强 3伤害  4其他
	public double getsx(int i, String type) {
		if (i == 0) {
			return getk(type);
		} else if (i == 1) {
			return geth(type);
		} else if (i == 2) {
			return getq(type);
		} else if (i == 3) {
			return gets(type);
		}else if (i == 4) {
			return getqt(type);
		}
		return 0;
	}

	// 9152|烟锁重楼|被混乱命中的单位仙法鬼火抗性降低（3%*等级），震慑抗性降低（1%*等级），抗三尸虫降低（500*等级）。（仅在与NPC之间战斗有效）

	// 获取抗 //抗 物理 震慑 风 雷 水 火 混乱 昏睡 封印 中毒 遗忘 鬼火 三尸
	public double getk(String type) {
		double xiao = 0;
		AddState addState = xzstate("抗" + type);
		if (addState != null) {
			xiao += addState.getStateEffect();
		}
		if (type.equals("普通攻击")) {
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			FightingSkill skill = getAppendSkill(9347);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			skill = getAppendSkill(9704);
			if (skill != null) {
				xiao -= 20;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate("以静制动");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.84);
			}
			addState = xzstate("清心静气");
			if (addState != null) {
				xiao += (addState.getStateEffect()*4.19);
			}
			xiao += Quality.getRolekwl();
			return xiao;
		} else if (type.equals("风")) {
			xiao += Quality.getRolekff();
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 25;
				else
					xiao -= 20;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate("以静制动");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.72);
			}
			addState = xzstate("清心静气");
			if (addState != null) {
				xiao += (addState.getStateEffect()*4.19);
			}
			addState = xzstate(TypeUtil.TY_FY_FDSJ);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.TY_HL_WSLT);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect()*4;}

			FightingSkill skill = getAppendSkill(9152);
			if (skill != null) {
				xiao -= skill.getSkillhurt() * 3;
			}
			skill = getAppendSkill(9129);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			skill = getAppendSkill(9704);
			if (skill != null) {
				xiao -= 20;
			}
			return xiao;
		} else if (type.equals("雷")) {
			xiao += Quality.getRoleklf();
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)xiao -= 25;
				else xiao -= 20;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate("以静制动");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.72);
			}
			addState = xzstate("清心静气");
			if (addState != null) {
				xiao += (addState.getStateEffect()*4.19);
			}
			addState = xzstate(TypeUtil.TY_FY_FDSJ);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.TY_HL_WSLT);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect()*4;}

			FightingSkill skill = getAppendSkill(9152);
			if (skill != null) {
				xiao -= skill.getSkillhurt() * 3;
			}
			skill = getAppendSkill(9129);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			skill = getAppendSkill(9704);
			if (skill != null) {
				xiao -= 20;
			}
			return xiao;
		} else if (type.equals("水")) {
			xiao += Quality.getRoleksf();
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 25;
				else
					xiao -= 20;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate("以静制动");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.72);
			}
			addState = xzstate("清心静气");
			if (addState != null) {
				xiao += (addState.getStateEffect()*4.19);
			}
			addState = xzstate(TypeUtil.TY_FY_FDSJ);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.TY_HL_WSLT);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect()*4;}

			FightingSkill skill = getAppendSkill(9152);
			if (skill != null) {
				xiao -= skill.getSkillhurt() * 3;
			}
			skill = getAppendSkill(9129);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			skill = getAppendSkill(9704);
			if (skill != null) {
				xiao -= 20;
			}
			return xiao;
		} else if (type.equals("火")) {
			xiao += Quality.getRolekhf();
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 25;
				else
					xiao -= 20;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate("以静制动");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.72);
			}
			addState = xzstate("清心静气");
			if (addState != null) {
				xiao += (addState.getStateEffect()*4.19);
			}
			addState = xzstate(TypeUtil.TY_FY_FDSJ);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.TY_HL_WSLT);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect()*4;}

			FightingSkill skill = getAppendSkill(9152);
			if (skill != null) {
				xiao -= skill.getSkillhurt() * 3;
			}
			skill = getAppendSkill(9129);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			skill = getAppendSkill(9704);
			if (skill != null) {
				xiao -= 20;
			}
			return xiao;
		} else if (type.equals("混乱")) {
			xiao += Quality.getRolekhl() + this.bhws;
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 12;
				else
					xiao -= 8;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			addState = xzstate("明镜止水");
			if (addState != null) {
				xiao += (addState.getStateEffect()*3);
			}
			addState = xzstate("气聚神凝");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.0015);
			}
			addState = xzstate(TypeUtil.TY_F_CZDF);
			if (addState != null) {
				xiao -= addState.getStateEffect2();
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect();}

			return xiao;
		} else if (type.equals("封印")) {
			xiao += Quality.getRolekfy() + this.bhws;
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 12;
				else
					xiao -= 8;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			addState = xzstate("明镜止水");
			if (addState != null) {
				xiao += (addState.getStateEffect()*3);
			}
			addState = xzstate("气聚神凝");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.0015);
			}
			addState = xzstate(TypeUtil.TY_FY_ARXH);
			if (addState != null) {
				xiao -= addState.getStateEffect2();
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect();}

			return xiao;
		} else if (type.equals("昏睡")) {
			xiao += Quality.getRolekhs() + this.bhws;
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 12;
				else
					xiao -= 8;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			addState = xzstate("明镜止水");
			if (addState != null) {
				xiao += (addState.getStateEffect()*3);
			}
			addState = xzstate("气聚神凝");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.0015);
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect();}

			return xiao;
		} else if (type.equals("中毒")) {
			xiao += Quality.getRolekzd();
			addState = xzstate("减人仙");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 12;
				else
					xiao -= 8;
			}
			addState = xzstate(TypeUtil.KX);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			return xiao;
		} else if (type.equals("震慑")) {
			xiao += Quality.getRolekzs();
			addState = xzstate("减魔鬼");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 15;
				else
					xiao -= 10;
			}
			addState = xzstate(TypeUtil.MH);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			FightingSkill skill = getAppendSkill(9152);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			return xiao;
		} else if (type.equals("遗忘")) {
			xiao += Quality.getRolekyw() + this.bhws;
			addState = xzstate("减魔鬼");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 15;
				else
					xiao -= 10;
			}
			addState = xzstate(TypeUtil.MH);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate("明镜止水");
			if (addState != null) {
				xiao += (addState.getStateEffect()*3);
			}
			addState = xzstate("气聚神凝");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.0015);
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect();}

			return xiao;
		} else if (type.equals("鬼火")) {
			xiao += Quality.getRolekgh();
			addState = xzstate("减魔鬼");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 25;
				else
					xiao -= 20;
			}
			addState = xzstate(TypeUtil.MH);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate("以静制动");
			if (addState != null) {
				xiao += (addState.getStateEffect()*0.72);
			}
			addState = xzstate("清心静气");
			if (addState != null) {
				xiao += (addState.getStateEffect()*4.19);
			}
			addState = xzstate(TypeUtil.TY_FY_FDSJ);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.TY_HL_WSLT);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_E_HNYG);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect()*4;}

			FightingSkill skill = getAppendSkill(9152);
			if (skill != null) {
				xiao -= skill.getSkillhurt() * 3;
			}
			skill = getAppendSkill(9129);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			skill = getAppendSkill(9704);
			if (skill != null) {
				xiao -= 20;
			}
			return xiao;
		} else if (type.equals("三尸")) {
			xiao += Quality.getRoleksc();
			addState = xzstate("减魔鬼");
			if (addState != null) {
				if (addState.getStateEffect() == 2)
					xiao -= 2000;
				else
					xiao -= 1000;
			}
			addState = xzstate(TypeUtil.MH);
			if (addState != null) {
				xiao += addState.getStateEffect() * 100;
			}
			addState = xzstate(TypeUtil.BB_RHTY);//如虎添翼
			if (addState != null) {xiao += addState.getStateEffect()*100;}

			FightingSkill skill = getAppendSkill(9152);
			if (skill != null) {
				xiao -= skill.getSkillhurt() * 500;
			}
			return xiao;
		} else if (type.equals("无")) {
			return Quality.getK_wsxsh();
		}
		return xiao;
	}

	// 获取忽视 //忽视 防御程度 防御几率 风 雷 水 火 混乱 昏睡 封印 中毒 鬼火 遗忘
	public double geth(String type) {
		double xiao = 0;
		AddState addState = xzstate("忽视" + type);
		if (addState != null) {
			xiao += addState.getStateEffect();
		}
		if (type.equals(TypeUtil.PTGJ)) {
			// 9207|拔山
			// |释放一个强力的魔神附身，增加被牛的单位（1%*等级）点破物理几率和破物理程度，持续3回合。（仅在与玩家之间战斗有效）
			addState = xzstate(TypeUtil.TY_LL_BS);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			if (Battlefield.random.nextInt(100) + 1 < (Quality.getRolehsfyl() + xiao + pwljl)) {
				xiao += Quality.getRolehsfyv() + pwlcd;
			}
			addState = xzstate(TypeUtil.FB_DSC);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			return xiao;
		} else if (type.equals("风")) {
			if (Battlefield.random.nextInt(100) + 1 < Quality.getRolehsxfkl() + hsjv) {
				xiao += Quality.getRolehsxfcd();
			}
			addState = xzstate(TypeUtil.TY_F_HKJF);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			xiao += Quality.getRolehsff();
			return xiao;
		} else if (type.equals("雷")) {
			if (Battlefield.random.nextInt(100) + 1 < Quality.getRolehsxfkl() + hsjv) {
				xiao += Quality.getRolehsxfcd();
			}
			xiao += Quality.getRolehslf();
			return xiao;
		} else if (type.equals("水")) {
			if (Battlefield.random.nextInt(100) + 1 < Quality.getRolehsxfkl() + hsjv) {
				xiao += Quality.getRolehsxfcd();
			}
			xiao += Quality.getRolehssf();
			return xiao;
		} else if (type.equals("火")) {
			if (Battlefield.random.nextInt(100) + 1 < Quality.getRolehsxfkl() + hsjv) {
				xiao += Quality.getRolehsxfcd();
			}
			xiao += Quality.getRolehshf();
			return xiao;
		} else if (type.equals("混乱")) {
			xiao += Quality.getRolehshl();
			return xiao;
		} else if (type.equals("封印")) {
			xiao += Quality.getRolehsfy();
			return xiao;
		} else if (type.equals("昏睡")) {
			xiao += Quality.getRolehshs();
			return xiao;
		} else if (type.equals("中毒")) {
			xiao += Quality.getRolehszd();
			return xiao;
		} else if (type.equals("鬼火")) {
			xiao += Quality.getRolehsgh();
			// 9369|白泣残红|鬼火对敌方单位造成伤害后如果目标当前血还高于（80%-5%*等级）,则下回合对目标使用鬼火时忽视鬼火抗性（2%*等级）点。(仅在与距之间战斗有效)
			addState = xzstate(TypeUtil.TY_GH_BQCH);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			return xiao;
		} else if (type.equals("遗忘")) {
			// 9350|贵妃醉酒|使用孟婆汤,牺牲对法术遗忘的成功率（4%*等级）换取遗忘命中率增加。(仅在与玩间战斗有效。)
			// 9352|梦笔生花|对敌方全体单位释放孟婆汤,对主目标额增加（2%*等级）点忽视抗遗忘,对其他目标额外增加（1%*等级）点忽视抗遗忘,同时对命中的单位的法宝和召唤兽技能的遗忘成功率提高至（25%+5%*等级）。(仅在与玩家之间战斗有效)
			addState = xzstate(TypeUtil.TY_YW_GFZJ);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			addState = xzstate(TypeUtil.TY_YW_MBSH);
			if (addState != null) {
				xiao += addState.getStateEffect();
			}
			xiao += Quality.getRolehsyw();
			return xiao;
		} else if (type.equals("震慑")) {
			xiao += Quality.getRolehszs();
			return xiao;
		}
		return xiao;
	}

	// 获取强 //强 震慑 风 雷 水 火 混乱 昏睡 封印 中毒 遗忘 鬼火三尸 三尸血回血程度
	public double getq(String type) {
		double xiao = 0;
		if (type.equals("震慑")) {
			AddState addState = xzstate("强震慑");
			if (addState != null)
				xiao += addState.getStateEffect();
			xiao += Quality.getRoleqzs();
			return xiao;
		} else if (type.equals("风")) {
			xiao += Quality.getRoleqff();
			return xiao;
		} else if (type.equals("雷")) {
			xiao += Quality.getRoleqlf();
			return xiao;
		} else if (type.equals("水")) {
			xiao += Quality.getRoleqsf();
			return xiao;
		} else if (type.equals("火")) {
			xiao += Quality.getRoleqhf();
			return xiao;
		} else if (type.equals("混乱")) {
			xiao += Quality.getRoleqhl();
			return xiao;
		} else if (type.equals("封印")) {
			xiao += Quality.getRoleqfy();
			AddState addState = xzstate(TypeUtil.TY_FY_ARXH);
			if (addState != null) {
				xiao += addState.getStateEffect2();
			}
			return xiao;
		} else if (type.equals("昏睡")) {
			xiao += Quality.getRoleqhs();
			return xiao;
		} else if (type.equals("中毒")) {
			xiao += Quality.getRoleqzd();
			return xiao;
		} else if (type.equals("遗忘")) {
			xiao += Quality.getRolestrongforget();
			return xiao;
		} else if (type.equals("鬼火")) {
			xiao += Quality.getRolegstronghostfire();
			return xiao;
		} else if (type.equals("三尸")) {
			AddState addState = xzstate("强三尸虫");
			if (addState != null)
				xiao += addState.getStateEffect();
			xiao += Quality.getRolestrongbodyblood();
			return xiao;
		} else if (type.equals("三尸回血")) {
			xiao += Quality.getRolestrongbodyblooddeep();
			FightingSkill skill = getAppendSkill(9345);
			if (skill != null) {
				xiao -= skill.getSkillhurt();
			}
			AddState addState = xzstate(TypeUtil.TY_H_JSYY);
			if (addState != null) {
				xiao -= addState.getStateEffect();
			}
			return xiao;
		} else if (type.equals("力量")) {
			xiao += Quality.getJqgjfs();
			return xiao;
		} else if (type.equals("抗性") || type.equals(TypeUtil.MH)) {
			xiao += Quality.getJqfyfs();
			return xiao;
		} else if (type.equals("加速")) {
			xiao += Quality.getJqsdfs();
			return xiao;
		} else if (type.equals(TypeUtil.L_PL)) {
			xiao += Quality.getQlpl();
			return xiao;
		} else if (type.equals(TypeUtil.L_CB)) {
			xiao += Quality.getQlcb();
			return xiao;
		} else if (type.equals(TypeUtil.L_FY)) {
			xiao += Quality.getQlfy();
			return xiao;
		} else if (type.equals(TypeUtil.L_GL)) {
			xiao += Quality.getQlglv();
			return xiao;
		} else if (type.equals("甘霖回血")) {
			AddState addState = xzstate(type);
			if (addState != null){xiao += addState.getStateEffect();}
			xiao += Quality.getQlglc();
			return xiao;
		}
		return xiao;
	}

	// 获取伤害 //伤害 物理 风 雷 水 火 中毒 鬼火 三尸
	public double gets(String type) {
		if (type.equals("无")) {
			return Quality.getRolewsxsh();
		} else if (type.equals("风")) {
			return Quality.getRoleffsh();
		} else if (type.equals("雷")) {
			return Quality.getRolelfsh();
		} else if (type.equals("水")) {
			return Quality.getRolesfsh();
		} else if (type.equals("火")) {
			return Quality.getRolehfsh();
		} else if (type.equals("中毒")) {
			return Quality.getRolezdsh();
		} else if (type.equals("三尸")) {
			return Quality.getRolesssh();
		} else if (type.equals("鬼火")) {
			return Quality.getRoleghsh();
		}
		return 0;
	}
	/**获取其他*/
	public double getqt(String type) {
		double xiao=0;
		if (type.equals(TypeUtil.SX_SBL)) {
			xiao+=Quality.getRolefdsl();
			FightingSkill skill=getAppendSkill(9603);
			if (skill!=null) {xiao+=skill.getSkillhurt();}
			skill=getAppendSkill(9226);
			if (skill!=null) {xiao+=skill.getSkillhurt();}
			AddState addState=xzstate(TypeUtil.BB_TJTT);
			if (addState!=null) {xiao-=addState.getStateEffect();}
		}
		return xiao;
	}
	/**获取躲闪*/
	public double getds(int skillID) {
		skillID-=1001;
		skillID/=5;
		if (skillID==0) {return Quality.getDhl();}
		else if (skillID==1) {return Quality.getDfy();}
		else if (skillID==2) {return Quality.getDhs();}
		else if (skillID==3) {return Quality.getDdf();}
		else if (skillID==4) {return Quality.getDzs();}
		else if (skillID==8) {return Quality.getDff();}
		else if (skillID==9) {return Quality.getDlf();}
		else if (skillID==10) {return Quality.getDsf();}
		else if (skillID==11) {return Quality.getDhf();}
		else if (skillID==12) {return Quality.getDgh();}
		else if (skillID==13) {return Quality.getDsc();}
		else if (skillID==14) {return Quality.getDyw();}

		return 0;
	}
	/**获取减伤*/
	public double getjs(String type) {
		double value=Quality.getEjs();
		if (type.equals(TypeUtil.F)) {value+=Quality.getJff();}
		else if (type.equals(TypeUtil.H)) {value+=Quality.getJhf();}
		else if (type.equals(TypeUtil.S)) {value+=Quality.getJsf();}
		else if (type.equals(TypeUtil.L)) {value+=Quality.getJlf();}
		else if (type.equals(TypeUtil.GH)) {value+=Quality.getJgh();}
		return value;

	}
	/** 不找师门技能 获取摸个技能类型的技能 */
	public FightingSkill getSkillType(String type) {
		for (int i = 0; i < skills.size(); i++) {
			FightingSkill skill = skills.get(i);
			if (skill.getSkillid()<=1100) {continue;}
			if (skill.getSkilltype().equals(type)) {
				if (SkillCooling(skill.getSkillid())) {
					return null;
				}
				return skill;
			}
		}
		return null;
	}

	/** 判断是否触发修罗内丹 */
	public FightingSkill getxlnd() {
		initAttacks();
		AddAttack attack = attacks.get("乘风破浪");
		if (attack != null && Battlefield.isV(attack.getSkill().getSkillgain())) {
			return attack.getSkill();
		}
		attack = attacks.get("霹雳流星");
		if (attack != null && Battlefield.isV(attack.getSkill().getSkillgain())) {
			return attack.getSkill();
		}
		attack = attacks.get("大海无量");
		if (attack != null && Battlefield.isV(attack.getSkill().getSkillgain())) {
			return attack.getSkill();
		}
		attack = attacks.get("祝融取火");
		if (attack != null && Battlefield.isV(attack.getSkill().getSkillgain())) {
			return attack.getSkill();
		}
		return null;
	}

	/**
	 * 判断连击次数
	 *
	 * @return
	 */
	public int ljs(double ljjc) {
		int sum = 0;
		if (Battlefield.random.nextInt(100) + 1 < Quality.getRolefljl() + ljjc)
			sum = (int) Quality.getRolefljv();
		FightingSkill skill = null;
		if (sum != 0) {
			skill = getSkillType("浪子回头");
			if (skill == null) {
				return sum;
			}
			double hurt = skill.getSkillhurt();
			skill = getSkillType(TypeUtil.BB_E_QHLZHT);
			if (skill != null) {
				hurt = hurt * (1 + skill.getSkillgain() / 100D);
			}
			if (Battlefield.isV(hurt))
				sum = 2 * sum;
			if (sum > 15)
				sum = 15;
		}
		skill = getAppendSkill(9201);
		if (skill != null && Battlefield.isV(skill.getSkillhurt())) {
			sum++;
		}
		return sum;
	}

	/** 限制字符串 */
	public String xz() {
		if (type == 2 || type == 3 || type == 4){
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getSkillbeidong() == 0) {
				if (buffer.length() != 0) {
					buffer.append("_");
				} else {
					buffer.append("技能=");
				}
				String skillName = skills.get(i).getSkillname();
				buffer.append(skillName);
				buffer.append("-");
				if (!SkillCooling(skills.get(i).getSkillid())) {
					for (int j = 0; j < BattleData.vs.length; j++) {
						if (skillName.equals(BattleData.vs[j])) {
							buffer.append(1);
							continue;
						}
					}
					buffer.append(0);
				} else {
					buffer.append(2);
				}
			}
		}
		if (buffer.length()!=0){
			buffer.append("|");
		}
		if (pets != null) {
			if (pets.size() != 0){
				buffer.append("召唤兽=");
				for (int i = 0; i < pets.size(); i++) {
					if (i != 0){
						buffer.append("_");
					}
					buffer.append(pets.get(i).getHang().getId());
					buffer.append("-");
					buffer.append(pets.get(i).getPlay());
				}
			}
		}
		XZ = buffer.toString();
		return XZ;
	}

	/** 限制字符串 */
	/** 记录上一次的字符串 */
	public String XZ;

	public String xz2() {
		if (type == 2 || type == 3 || type == 4)
			return null;
		StringBuffer buffer = new StringBuffer();
		s: for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getSkillbeidong() == 0) {
				if (buffer.length() != 0) {
					buffer.append("_");
				} else {
					buffer.append("技能=");
				}
				String skillName = skills.get(i).getSkillname();
				buffer.append(skillName);
				buffer.append("-");
				if (!SkillCooling(skills.get(i).getSkillid())) {
					for (int j = 0; j < BattleData.vs.length; j++) {
						if (skillName.equals(BattleData.vs[j])) {
							buffer.append(1);
							continue s;
						}
					}
					buffer.append(0);
				} else {
					buffer.append(2);
				}
			}
		}
		if (!buffer.toString().equals(""))
			buffer.append("|");
		if (pets != null) {
			if (pets.size() != 0)
				buffer.append("召唤兽=");
			for (int i = 0; i < pets.size(); i++) {
				if (i != 0)
					buffer.append("_");
				buffer.append(pets.get(i).getHang().getId());
				buffer.append("-");
				buffer.append(pets.get(i).getPlay());
			}
		}
		String XZS = buffer.toString();
		if (XZ != null && XZ.equals(XZS)) {
			return null;
		}
		XZ = XZS;
		return XZ;
	}

	/**
	 * 获取灵宝触发的技能
	 *
	 * @return
	 */
	public FightingSkill getlingskill(int size, int type) {
		int gl = 0;
		FightingSkill zb = null;
		for (int i = skills.size() - 1; i >= 0; i--) {
			FightingSkill skill = skills.get(i);
			if (size >= skill.getSkilllvl()
					&& MixDeal.getyx(skill.getSkillid(), type)) {
				int bx = Battlefield.random.nextInt(100);
				if (gl <= bx) {
					gl = bx;
					zb = skill;
				}
			}
		}
		return zb;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public int getMan() {
		return man;
	}

	public void setMan(int man) {
		this.man = man;
	}

	public String getManname() {
		return manname;
	}

	public void setManname(String manname) {
		this.manname = manname;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	/**获取真实总血量*/
	public int getZHP_Z(){
		return original!=null?original.getHp_z():hp_z;
	}
	public int getHp_z() {
		return hp_z;
	}

	public void setHp_z(int hp_z) {
		this.hp_z = hp_z;
	}

	public int getMp_z() {
		return mp_z;
	}

	public void setMp_z(int mp_z) {
		this.mp_z = mp_z;
	}

	public int getAp() {
		int jc = 0;
		AddState addState = xzstate(TypeUtil.LL);
		if (addState != null) {
			jc += (ap * addState.getStateEffect() / 100.0);
		}
		addState = xzstate(TypeUtil.TZ_PFCZ);
		if (addState != null) {
			jc += addState.getStateEffect();
		}
		addState = xzstate(TypeUtil.TY_GH_QYBY);
		if (addState != null) {
			jc -= (ap * addState.getStateEffect() / 100.0);
		}
		addState = xzstate(TypeUtil.L_LL);
		if (addState != null) {
			jc += (ap * addState.getStateEffect() / 100.0);
		}
		addState = xzstate(TypeUtil.TY_L_PL_SLJL);
		if (addState != null) {
			jc +=addState.getStateEffect();
		}
		FightingSkill skill = getSkillType("杀身成仁");
		if (skill != null) {
			double hurt = skill.getSkillhurt();
			skill = getSkillType(TypeUtil.BB_E_QHSSCR);
			if (skill != null) {
				hurt = hurt * (1 + skill.getSkillgain() / 100D);
			}
			jc += ((hp_z - hp) * hurt / 100.0);
		}
		skill=getAppendSkill(9811);
		if (skill != null) {
			jc-=ap*(skill.getSkillhurt()+20)/100.0;
		}
		return ap + jc;
	}

	public int huoAp() {
		return ap;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}

	public int getSp() {
		if (type == 3) {
			AddState addState = xzstate(TypeUtil.TY_JS_YYCF);
			int jc = 0;
			if (addState != null) {
				jc += (sp * addState.getStateEffect() / 100.0);
			}
			return sp + sp2 + jc;
		}
		if (xzstate(TypeUtil.TZ_YSHY) != null) {
			return 99999;
		}
		if (xzstate("荆棘束身") != null) {
			return 0;
		}
		AddState addState = xzstate(TypeUtil.JS);
		int jc = 0;
		if (addState != null) {
			jc += (sp * addState.getStateEffect() / 100.0);
		}
		addState = xzstate("减速");
		if (addState != null) {
			jc -= (sp * addState.getStateEffect() / 100.0);
		}
		addState = xzstate(TypeUtil.TY_FY_ZYCX);
		if (addState != null) {
			jc -= (sp * addState.getStateEffect() / 100.0);
		}
		addState = xzstate(TypeUtil.TY_F_WHSF);
		if (addState != null) {
			jc -= (sp * addState.getStateEffect() / 100.0);
		}
		addState = xzstate(TypeUtil.TY_GH_QYBY);
		if (addState != null) {
			jc -= (sp * addState.getStateEffect() / 200.0);
		}
		addState = xzstate("知耻后勇");
		if (addState != null) {
			jc += 400;
		}
		return sp + sp2 + jc;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public List<FightingSkill> getSkills() {
		return skills;
	}

	public void setSkills(List<FightingSkill> skills) {
		this.skills = skills;
	}

	public List<FightingSummon> getPets() {
		return pets;
	}

	public void setPets(List<FightingSummon> pets) {
		this.pets = pets;
	}

	public List<AddState> getAddStates() {
		return addStates;
	}

	public void setAddStates(List<AddState> addStates) {
		this.addStates = addStates;
	}

	public int getStates() {
		return States;
	}

	public void setStates(int states) {
		States = states;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ql getQuality() {
		if (Quality == null) {
			Quality = new Ql();
		}
		return Quality;
	}

	public void setQuality(Ql quality) {
		Quality = quality;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public long getQihe() {
		return qihe;
	}

	public void setQihe(long qihe) {
		this.qihe = qihe;
	}

	public double getHuoyue() {
		return huoyue;
	}

	public void setHuoyue(double huoyue) {
		this.huoyue = huoyue;
	}

	public double getShanghai() {
		return shanghai + 1;
	}

	public void setShanghai(double shanghai) {
		this.shanghai = shanghai;
	}

	public double getKangluobao() {
		return kangluobao;
	}

	public void setKangluobao(double kangluobao) {
		this.kangluobao = kangluobao;
	}

	public double getYuanzhu() {
		return yuanzhu;
	}

	public void setYuanzhu(double yuanzhu) {
		this.yuanzhu = yuanzhu;
	}

	public List<FightingLingbao> getLings() {
		return lings;
	}

	public void setLings(List<FightingLingbao> lings) {
		this.lings = lings;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getZs() {
		return zs;
	}

	public void setZs(int zs) {
		this.zs = zs;
	}

	public int getSp2() {
		return sp2;
	}

	public void setSp2(int sp2) {
		this.sp2 = sp2;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ManData getChild() {
		return Child;
	}

	public void setChild(ManData child) {
		Child = child;
	}

	/**
	 * 根据类型获取值
	 */
	public double getvalue(int type) {
		if (type < 2) {
			return hp / (double) hp_z;
		} else if (type < 4) {
			return mp / (double) mp_z;
		} else if (type < 6) {
			return ap;
		} else {
			return sp;
		}
	}

	/**
	 * 阴阳逆转判断 0不触发 1触发 2触发消失
	 */
	public int getyylz() {
		for (int i = addStates.size() - 1; i >= 0; i--) {
			AddState addState = addStates.get(i);
			if (addState.getStatename().equals("阴阳逆转")) {
				addState.setStateEffect(addState.getStateEffect() - 1);
				if (addState.getStateEffect() <= 0) {
					addStates.remove(i);
					return 2;
				}
				return 1;
			}
		}
		return 0;
	}

	/** 复活判断 */
	public AddState fhy() {
		if (xzstate("归墟") != null)
			return null;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			AddState addState = addStates.get(i);
			if (addState.getStatename().equals("回魂咒")) {
				addStates.remove(i);
				return addState;
			} else if (addState.getStatename().equals("复活")) {
				addStates.remove(i);
				return addState;
			}
		}
		return null;
	}

	/** 判断是否触发孩子技能 */
	public FightingSkill getChildSkill(String type) {
		for (int i = skills.size() - 1; i >= 0; i--) {
			FightingSkill skill = skills.get(i);
			if (skill.getSkilltype().equals(type)) {
				if (Battlefield.random.nextInt(100) < skill.getSkillhitrate()) {
					return skill;
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否触发物雾里抗花技能
	 */
	public int getWLKHSkill() {
		if (States != 0 || type == 3 || type == 4)
			return 0;
		int hurt = 0;
		AddState addState = null;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			if (!addStates.get(i).getStatename().equals("雾里看花"))
				continue;
			addState = addStates.get(i);
			addStates.remove(i);
			break;
		}
		if (addState != null) {
			hurt = (int) ((hp_z - hp) * addState.getStateEffect() / 100);
			if (hurt <= 0)
				hurt = 1;
		}
		return hurt;
	}

	/** 添加状态同时去除重叠 */
	public boolean addAddState(String type, double e1, double e2, int lus) {
		AddState addState = null;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			addState = addStates.get(i);
			if (addState.getStatename().equals(type)) {
				addState.setStateEffect(e1);
				addState.setStateEffect2(e2);
				addState.setSkills(null);
				addState.setSurplus(lus);
				return false;
			}
		}
		addStates.add(new AddState(type, e1, e2, lus));
		return true;
	}

	/** 添加状态同时数值相加 */
	public AddState addAddState2(String type, double e1, double e2, int lus) {
		AddState addState = null;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			addState = addStates.get(i);
			if (addState.getStatename().equals(type)) {
				addState.setStateEffect(addState.getStateEffect() + e1);
				addState.setStateEffect2(addState.getStateEffect2() + e2);
				addState.setSkills(null);
				addState.setSurplus(lus);
				return addState;
			}
		}
		addState = new AddState(type, e1, e2, lus);
		addStates.add(new AddState(type, e1, e2, lus));
		return addState;
	}

	// 删除制定状态
	public boolean deleteState(String type) {
		String[] vs = new String[] { type };
		if (type.equals("清除异常状态")) {
			vs = new String[] { "遗忘", "封印", "中毒", "昏睡", "混乱", TypeUtil.FB_JGE,
					TypeUtil.FB_QW };
		}
		boolean l = false;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			AddState addState = addStates.get(i);
			for (int j = 0; j < vs.length; j++) {
				if (addState.getStatename().equals(vs[j])) {
					addStates.remove(i);
					l = true;
					break;
				}
			}

		}
		return l;
	}

	/** 记录承受的法术 */
	public void addBear(String type) {
		if (bearSkills != null) {
			bearSkills.add(type);
		}

	}

	/** 判断是否承受该类型法术 */
	public boolean isBear(String type) {
		if (bearSkills != null) {
			return bearSkills.contains(type);
		}
		return false;
	}

	/** 清空承受法术 */
	public void removeBear() {
		if (bearSkills != null) {
			bearSkills.clear();
		}
	}

	/** 释放指定法术 */
	public FightingSkill getZDSKILL(int id, int sld) {
		int skillId = id;
		for (int i = skills.size() - 1; i >= 0; i--) {
			if (skills.get(i).getSkillid() == id) {
				return skills.get(i);
			}
		}
		// 1030对应100，目标数10，持续回合100
		// 1035对应101，目标数10，持续回合100
		// 1205对应102，目标数10，持续回合100
		// 1203对应103，目标数10，持续回合100
		// 1056对应104，目标数10，持续回合100，给加30万基础上啊hi
		if (id == 100) {
			skillId = 1030;
		} else if (id == 101) {
			skillId = 1035;
		} else if (id == 102) {
			skillId = 1205;
		} else if (id == 103) {
			skillId = 1203;
		} else if (id == 104) {
			skillId = 1056;
		}
		// 没有这个技能加载
		Skill skill = GameServer.getSkill(skillId + "");
		if (skill != null) {
			FightingSkill fightingSkill = new FightingSkill(skill, lvl, 0, sld,
					sld, 1);
			if (id >= 100 && id <= 104) {
				fightingSkill.setSkillid(id);
				fightingSkill.setSkillsum(10);
				fightingSkill.setSkillcontinued(100);
				if (id == 104) {
					fightingSkill
							.setSkillhurt(fightingSkill.getSkillhurt() + 300000);
				}
			}
			fightingSkill.setSkillblue(0);
			skills.add(fightingSkill);
			return fightingSkill;
		}
		return null;
	}

	public int getSr() {
		return sr;
	}

	public void setSr(int sr) {
		this.sr = sr;
	}

	public List<AI> getAis() {
		return ais;
	}

	public void setAis(List<AI> ais) {
		this.ais = ais;
	}

	// 修改怨气
	public void addyq(int v, FightingState org) {
		if (yqz + v > 500) {
			v = 500 - yqz;
		} else if (yqz + v < 0) {
			v = -yqz;
		}
		yqz += v;
		org.setYq_c(v);
	}

	// 修改怒气
	public void addnq(int v, FightingState org) {
		if (type == 0) {
			if (nqz + v > 100) {
				v = 100 - nqz;
			} else if (nqz + v < 0) {
				v = -nqz;
			}
			nqz += v;
			org.setNq_c(v);
		}
	}

	/** 获取修正和等级强度 */
	public int getlvl() {
		return lvl + zs * 25;
	}

	public int getYqz() {
		return yqz;
	}

	public int getNqz() {
		return nqz;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public int getSpellSum() {
		return spellSum;
	}

	public void setSpellSum(int spellSum) {
		this.spellSum = spellSum;
	}

	/** 判断是否有生生不息有的对法术伤害 */
	public double getSpellJC() {
		double xs = 1D;
		FightingSkill skill = getSkillType(TypeUtil.TZ_SSBX);
		if (skill != null) {
			spellSum++;
			if (spellSum > 5) {spellSum = 1;}
			xs +=skill.getSkillhurt()/100D*spellSum;
		}
		skill = getSkillType(TypeUtil.TJ_QSBX);
		if (skill != null) { // 75-130%
			xs *= (0.8 + Battlefield.random.nextInt(50) / 100.0);
		}
		return xs;
	}
	/** 获取万古同悲加成 */
	public double getWGTB() {
		FightingSkill skill = getSkillType(TypeUtil.TZ_WGTB);
		if (skill != null) {
			// 9367|轻云蔽月|每次鬼火攻击有33%降低敌方主属性的（1.2%*等级），如果主属性是敏捷,则效果减半,持续两回合。(仅在与玩家之间战斗有效）
			double lx = this.shanghai;
			AddState addState = xzstate(TypeUtil.TY_GH_QYBY);
			if (addState != null) {
				lx -= (lx * addState.getStateEffect() / 100.0);
			}
			if (lx > 1209) {
				lx = 1209;
			}
			if (lx >= 609) {
				return lx
						* (skill.getSkillhitrate() + lx * skill.getSkillhurt())
						/ 2;
			}
		}
		return 0;
	}

	/** 回合结束清除状态 */
	public FightingState endState() {
		FightingState fightingState = null;
		List<AddState> rAddStates = null;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			// 减少一回合
			AddState addState = addStates.get(i);
			if (addState.isEnd()) {
				addStates.remove(i);
				String statename = addState.getStatename();
				if (addState.getSkills() != null
						|| statename.equals(TypeUtil.L_FY)
						|| statename.equals(TypeUtil.L_LL)
						|| statename.equals(TypeUtil.TY_L_GL_PYGQ)) {
					if (rAddStates == null) {
						rAddStates = new ArrayList<>();
					}
					rAddStates.add(addState);
				}
				if (statename.equals("冷却")) {
					continue;
				} else if (statename.equals(TypeUtil.TZ_YGZG)) {
					Quality.addkr(addState.getStateEffect());
					Quality.addks(addState.getStateEffect2());
				} else if (statename.equals(TypeUtil.TZ_PHQY)) {
					Quality.addkr(addState.getStateEffect());
				} else if (statename.equals(TypeUtil.TZ_XYXG)) {
					Quality.addks(addState.getStateEffect());
				} else if (statename.equals("仙法狂暴")) {
					sp -= 600;
					Quality.kuangbao(-1);
				} else if (statename.equals("物理狂暴")) {
					sp -= 600;
					for (int j = skills.size() - 1; j >= 0; j--) {
						int id = skills.get(i).getSkillid();
						if (id == 1831 || id == 1833) {
							skills.remove(i);
						}
					}
				}
				if (fightingState == null) {
					fightingState = new FightingState();
					fightingState.setCamp(camp);
					fightingState.setMan(man);
					fightingState.setStartState("代价");
				}
				fightingState.setEndState_2(statename);
			}
		}
		if (fightingState != null && rAddStates != null) {
			MixDeal.rid(this, fightingState, rAddStates);
		}
		return fightingState;
	}

	/**
	 * 获取 0 慈乌反哺 高级慈乌反哺 1 反哺之私 高级反哺之私
	 */
	public FightingSkill getFeedback() {
		for (int i = skills.size() - 1; i >= 0; i--) {
			FightingSkill skill = skills.get(i);
			if (skill.getSkilltype().equals(TypeUtil.BB_CWFB)
					|| skill.getSkilltype().equals(TypeUtil.BB_FBZS)) {
				if (skill.getSkillhitrate() > Battlefield.random.nextInt(100)) {
					return skill;
				}
			}
		}
		return null;
	}

	/** 随机获取一个增益状态 */
	public AddState getGainState() {
		AddState addState = null;
		for (int i = addStates.size() - 1; i >= 0; i--) {
			if (addStates.get(i).getType() == 0) {
				if (addState != null && Battlefield.isV(40)) {
					continue;
				}
				addState = addStates.get(i);
			}
		}
		return addState;
	}

	/** 获取一个控制状态 */
	public AddState getControlState() {
		for (int i = addStates.size() - 1; i >= 0; i--) {
			AddState addState = addStates.get(i);
			if (addState.getStatename().equals(TypeUtil.FY)
					|| addState.getStatename().equals(TypeUtil.HS)
					|| addState.getStatename().equals(TypeUtil.YW)
					|| addState.getStatename().equals(TypeUtil.HL)) {
				return addState;
			}
		}
		return null;
	}

	/** 随机清除一个负面状态 */
	public boolean RemoveNegativeState(FightingState state) {
		for (int i = addStates.size() - 1; i >= 0; i--) {
			AddState addState = addStates.get(i);
			String statename = addState.getStatename();
			for (int j = 0; j < values2.length; j++) {
				if (statename.equals(values2[j])) {
					RemoveAbnormal(state, statename);
					state.setEndState_2(statename);
					return true;
				}
			}
		}
		return false;
	}

	/** 获取附加技能 */
	public FightingSkill getAppendSkill(int id) {
		for (int i = addStates.size() - 1; i >= 0; i--) {
			FightingSkill skill = addStates.get(i).getSkill(id);
			if (skill != null) {
				return skill;
			}
		}
		return null;
	}

	/** 移除附加技能 */
	public void RemoveAppendSkill(int id) {
		for (int i = addStates.size() - 1; i >= 0; i--) {
			FightingSkill skill = addStates.get(i).getSkill(id);
			if (skill != null) {
				addStates.get(i).getSkills().remove(skill);
				if (addStates.get(i).getSkills().size() == 0) {
					addStates.get(i).setSkills(null);
				}
				return;
			}
		}
	}
	public BigDecimal getSe() {
		return se;
	}
	public void setSe(BigDecimal se) {
		this.se = se;
	}
	public BigDecimal getXk_id() {
		return xk_id;
	}
	public void setXk_id(BigDecimal xk_id) {
		this.xk_id = xk_id;
	}

	public BaseStar getBaseStar() {
		return baseStar;
	}

	public void setBaseStar(BaseStar baseStar) {
		this.baseStar = baseStar;
	}

	public Double getExpXS() {
		if (expXS==null) {
			expXS=1D;
		}
		return expXS;
	}

	public void setExpXS(Double expXS) {
		this.expXS = expXS;
	}

	public Double getExp2XS() {
		return exp2XS;
	}
	public void setExp2XS(Double exp2xs) {
		exp2XS = exp2xs;
	}
	public String getLingXi() {
		return lingxi;
	}
	public void setLingXi(String lingxi) {
		this.lingxi = lingxi;
	}
	public AddAttack getAttacks(String type){
		return initAttacks().get(type);
	}
	public Map<String, AddAttack> initAttacks() {
		if (attacks == null) {
			attacks = new HashMap<String, AddAttack>();
			for (int i = skills.size() - 1; i >= 0; i--) {
				FightingSkill skill = skills.get(i);
				String skillname = skill.getSkillname();
				String skilltype = skill.getSkilltype();
				if (skillname.equals("乘风破浪")||skillname.equals("霹雳流星")||skillname.equals("大海无量")||skillname.equals("祝融取火")) {
					attacks.put(skillname, new AddAttack(skillname, skill));
					skills.remove(i);
				}else if (skilltype.equals(TypeUtil.BB_LHFM)) {
					attacks.put(skilltype, new AddAttack(skilltype, skill));
					skills.remove(i);
				}else if (skillname.equals("浩然正气")) {
					FightingSkill skill2 = getSkillType(TypeUtil.BB_E_YNYD);
					if (skill2 != null) {
						attacks.put(skillname, new AddAttack(skillname, skill,skill2));
					} else {
						attacks.put(skillname, new AddAttack(skillname, skill));
					}
					skills.remove(i);
				}else if (skillname.equals("隔山打牛")) {
					FightingSkill skill2 = getSkillType(TypeUtil.BB_E_LWKD);
					if (skill2 != null) {
						attacks.put(skillname, new AddAttack(skillname, skill,skill2));
					} else {
						attacks.put(skillname, new AddAttack(skillname, skill));
					}
					skills.remove(i);
				}else if (skillname.equals("暗影离魂")||skillname.equals("亢龙有悔")) {
					attacks.put(skillname,new AddAttack(skillname,skill));
					skills.remove(i);
				}
			}
		}
		return attacks;
	}

	private Original original;

	/** 上限处理 0調整血量上限 1調整藍量上限 */
	public void UP(FightingState state, int type, double xs) {
		if (xs==0) {return;}
		if (original == null) {
			original = new Original(hp_z, mp_z, model);
		}
		if (type == 0) {
			hp_z = (int) (original.getHp_z() * original.upXS(0, xs));
			if (hp_z <= 0) {
				hp_z = 1;
			}
			state.setUp("HP", hp_z);
			if (hp > hp_z) {
				state.setHp_Change(hp_z - hp);
				hp = hp_z;
			}
		} else if (type == 1) {
			mp_z = (int) (original.getMp_z() * original.upXS(1, xs));
			if (mp_z <= 0) {
				mp_z = 1;
			}
			state.setUp("MP", mp_z);
			if (mp > mp_z) {
				state.setHp_Change(mp_z - mp);
				mp = mp_z;
			}
		}
	}

	/** 变换造型 */
	public void UPModel(FightingState state, String skin) {
		if (original == null) {
			original = new Original(hp_z, mp_z, model);
		}
		if (skin != null) {
			model = skin;
			state.setSkin(skin);
		} else {
			model = original.getModel();
			state.setSkin(model);
		}
	}
	/**清除天演策冷却*/
	public void TY_L_GL_MYJS(){
		for (int i = addStates.size() - 1; i >= 0; i--) {
			AddState addState=addStates.get(i);
			if (addState.getStatename().equals("冷却")) {
				if (addState.getStateEffect()>=9000&&addState.getStateEffect()<10000) {
					addStates.remove(i);
				}
			}
		}
	}
	/**获取当前正在战斗的召唤兽 并修改剩余的HPMp*/
	public RoleSummoning getPet(boolean isH,boolean isM,boolean isHS ,boolean isMS){
		for (int i = pets.size()-1; i >=0; i--) {
			FightingSummon summon=pets.get(i);
			if (summon.getPlay()==1) {
				RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(summon.getHang().getId());
				if (pet!=null) {
					if (isH) {
						pet.setBasishp(summon.getPet().getHp());
					}else {
						pet.setBasishp(summon.getPet().getHp_z());
					}
					if (isHS){
						pet.setBasishp(summon.getPet().getHp());
					}else {
						pet.setBasishp(summon.getPet().getHp_z());
					}

					if (isM) {
						pet.setBasismp(summon.getPet().getMp());
					}else {
						pet.setBasismp(summon.getPet().getMp_z());
					}
					if (isMS){
						pet.setBasismp(summon.getPet().getMp());
					}else {
						pet.setBasismp(summon.getPet().getMp_z());
					}

				}

				return pet;
			}
		}
		return null;

	}



	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////  /////    /////  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////  ////  /  ////  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////  ///  //  ///  ///////////////////////////////////////////    物理系         ////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////  //  ///  //  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////     ////     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * 灵犀时效类基础属性加成技能加载
	 */
	public void initLXBuff() {
		// 羊入虎口,概率增加命中
		AddState zt = xzstate("11006");
		if (zt != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
				// 判断数值
				this.mz += LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());
			}
		}

		// 灵犀-势不可挡 11007
		zt = xzstate("11007");
		if (zt != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
				this.hsfz += LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());
			}
		}

		// 战意、破甲命中处理
		zt = xzstate("战意");
		if (zt != null) {
			// 判断数值
			this.mz += zt.getStateEffect();
			this.hsfz += zt.getStateEffect();
		}

		zt = xzstate("破甲");
		if (zt != null) {
			// 判断数值
			this.mz += zt.getStateEffect();
			this.hsfz += zt.getStateEffect();

			if (zt.getStatename().equals("破甲")) {
				// 破甲增加破物理属性
				this.pwlcd += zt.getStateEffect() * 3;
				this.pwljl += zt.getStateEffect() * 3;
				// 扣除破甲
				getAddStates().remove(zt);
				int count = (int)zt.getStateEffect() - 1;
				if (count > 0) {
					zt.setStateEffect(count);
					zt.setType(1);
					zt.setStatename("破甲");
					zt.setSurplus(50);
					getAddStates().add(zt);
				} else {
					zt.setStateEffect(0);
					zt.setType(1);
					zt.setStatename("破甲");
					zt.setSurplus(0);
					getAddStates().add(zt);
				}
			}

		}
	}

	/**
	 * 灵犀-化险为夷护盾判断及增加
	 */
	public void addDun(long ap, FightingState ace) {
		// 判断是否死亡
		if (getStates() != 0) {
			return;
		}

		// 化险为夷,概率回血加盾
		AddState zt = xzstate("11010");
		if (zt != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId("11010", 1, (int)zt.getStateEffect()))) {
				// 判断数值
				int dun = LingXiUtil.getNumberBySkillId("11010", 2, (int)zt.getStateEffect());

				long hp = ap * dun / 100;

				int currHP = getHp();
				int maxHP = getHp_z();

				int queshao = maxHP - currHP;


				if (queshao > 0) {
					ChangeFighting fighting=new ChangeFighting();
					if (queshao > hp) {
						// 缺少的生命值大于盾的值则只加血
						fighting.setChangehp((int)hp);
						hp = 0;
					} else {
						// 缺少的生命值小于盾的值则加满血，剩余加盾
						fighting.setChangehp(queshao);
						hp = hp - queshao;
					}

					ChangeData(fighting, ace);
				}
				if (hp > 0) {
					// 判断是否已经有盾，有盾则补盾并刷新回合数
					AddState addState = xzstate("化险为夷");
					if (addState == null) {
						addState = new AddState();
						addState.setStateEffect(hp > maxHP ? maxHP : hp);
					} else {
						getAddStates().remove(addState);
						hp += addState.getStateEffect();
						addState.setStateEffect(hp > maxHP ? maxHP : hp);
					}
					addState.setType(1);
					addState.setStatename("化险为夷");
					addState.setSurplus(3);
					ace.setEndState_1("化险为夷");
					getAddStates().add(addState);
				}

			}
		}
	}

	/**
	 * 灵犀-获得战意、破甲（回合前）
	 */
	public void addZhanyi(List<FightingState> xieStates,FightingState fightingState) {
		// 判断是否死亡
		if (getStates() != 0) {
			return;
		}
		int idx = 1;
		// 冲冠一怒、唇亡齿寒
		AddState zt = xzstate("11012");
		if (zt == null) {
			idx = 2;
			zt = xzstate("11013");
		}
		if (zt != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
				// 判断是否已经有战意、破甲，有则添加次数
				AddState addState = xzstate(idx == 1 ? "战意" : "破甲");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(2);
				} else {
					getAddStates().remove(addState);
					int count = (int)addState.getStateEffect() + 2;
					addState.setStateEffect(count > 12 ? 12 : count);
				}
				addState.setType(1);
				addState.setStatename(idx == 1 ? "战意" : "破甲");
				addState.setSurplus(50);
				getAddStates().add(addState);

				if (fightingState != null) {
					fightingState.setEndState_1(idx == 1 ? "战意" : "破甲");
				} else {
					fightingState=new FightingState();
					fightingState.setCamp(camp);
					fightingState.setMan(man);
					fightingState.setEndState_1(idx == 1 ? "战意" : "破甲");
					xieStates.add(fightingState);
				}
			}
		}
	}

	/**
	 * 火冒三丈、攻守兼备处理，每次物理攻击调用
	 *
	 * @return 0 未生效、1，获得战意冲天，2，获得势不可遏
	 */
	public int executeHmsz(List<FightingState> xieStates) {
		// 火冒三丈、攻守兼备
		int result = 0;
		int idx = 1;
		AddState zt = xzstate("11018");
		if (zt == null) {
			idx = 2;
			zt = xzstate("11019");
		}
		if (zt != null) {
			// 层数
			int count = 0;
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
				// 判断是否已经有战意、破甲，有则添加次数
				AddState addState = xzstate(idx == 1 ? "战意" : "破甲");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(2);
				} else {
					getAddStates().remove(addState);
					count = (int)addState.getStateEffect() + 2;
					addState.setStateEffect(count > 12 ? 12 : count);
				}
				addState.setType(1);
				addState.setStatename(idx == 1 ? "战意" : "破甲");
				addState.setSurplus(50);
				getAddStates().add(addState);

				FightingState fightingState=new FightingState();
				fightingState.setCamp(camp);
				fightingState.setMan(man);
				fightingState.setEndState_1(idx == 1 ? "战意" : "破甲");
				xieStates.add(fightingState);
			}
			if (this.cd == 0 && count >= 12) {
				// 处理满层加成  命中+忽视反震
				int val = LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());
				this.mz += val;
				this.hsfz += val;
				result = idx;
				this.cd = 5;
			}
		}
		return result;
	}

	/**
	 * 绝地反击判定，触发致命时调用
	 */
	public void executeJdfj(List<FightingState> xieStates) {
		// 绝地反击
		AddState zt = xzstate("11017");
		if (zt != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
				// 判断是否已经有破甲，有则添加次数
				AddState addState = xzstate("破甲");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(2);
				} else {
					getAddStates().remove(addState);
					int count = (int)addState.getStateEffect() + 2;
					addState.setStateEffect(count > 12 ? 12 : count);
				}
				addState.setType(1);
				addState.setStatename("破甲");
				addState.setSurplus(50);
				getAddStates().add(addState);

				FightingState fightingState=new FightingState();
				fightingState.setCamp(camp);
				fightingState.setMan(man);
				fightingState.setEndState_1("破甲");
				xieStates.add(fightingState);
			}
		}
	}


	public static void main(String[] args) {
		System.out.println(269685417l >> 27);
		System.out.println(467618314l >> 27);
	}



	/**
	 * 判定是否执行战意分裂
	 * @return
	 */
	public boolean executeZhanyi() {
		AddState zt = xzstate("战意");
		if (zt != null) {
			// 判断概率，战意打到12层且拥有火冒三丈状态
			if (Battlefield.isV(zt.getStateEffect() * 3.5) || (cd == 5 && zt.getStateEffect() == 12 && xzstate("11018") != null)) {
				// 扣除战意
				getAddStates().remove(zt);
				int count = (int)zt.getStateEffect() - 2;
				if (count > 0) {
					zt.setStateEffect(count);
					zt.setType(1);
					zt.setStatename("战意");
					zt.setSurplus(50);
					getAddStates().add(zt);
				} else {
					zt.setStateEffect(0);
					zt.setType(1);
					zt.setStatename("战意");
					zt.setSurplus(0);
					getAddStates().add(zt);
				}
				if (cd == 5) {
					// 防止同一回合触发多次，这里减少1点CD
					cd--;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 触发怒不可揭
	 * @return
	 */
	public boolean executeNbkj(int count) {
		if (count > 2) {
			return false;
		}
		// 判断怒不可揭
		AddState nbkj = xzstate("11014");
		// 判断概率
		if (nbkj != null && Battlefield.isV(LingXiUtil.getNumberBySkillId(nbkj.getStatename(), 1, (int)nbkj.getStateEffect()))) {
			// 增加临时狂暴
			this.kb += LingXiUtil.getNumberBySkillId(nbkj.getStatename(), 2, (int)nbkj.getStateEffect());
			return true;
		}
		return false;
	}

	/**
	 * 触发晴天霹雳返回致命率
	 * @return
	 */
	public int executeQtpl(ManData noData,List<FightingState> zls) {
		if (noData.getHp() < 300000) {
			return 0;
		}
		// 判断晴天霹雳
		AddState qtpl = xzstate("11015");
		// 判断概率
		if (qtpl != null && Battlefield.isV(LingXiUtil.getNumberBySkillId(qtpl.getStatename(), 1, (int)qtpl.getStateEffect()))) {
			FightingState ace = new FightingState();
			ace.setCamp(camp);
			ace.setMan(man);
			ace.setText("看我的#G晴天霹雳");
			zls.add(0,ace);
			// 返回致命
			return LingXiUtil.getNumberBySkillId(qtpl.getStatename(), 2, (int)qtpl.getStateEffect());
		}
		return 0;
	}

	/**
	 * 灵犀技能-如释重负、耳聪目明判定执行
	 */
	public void executeRfzs(ManData noman,FightingState ace, List<FightingState> zls, Battlefield battlefield) {
		FightingSkill skill = skillname("如释重负");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1,skill.getSkilllvl()))) {
				String state = LingXiUtil.BUFF[Battlefield.random.nextInt(4)];
				noman.RemoveAbnormal(state);
				ace.setEndState_2(state);
			}
		}
		skill = skillname("耳聪目明");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1,skill.getSkilllvl()))) {
				// 获取周围一格内的隐身单位
				List<ManData> datas = battlefield.getZWYS(noman);
				for (ManData data : datas) {
					FightingState manstate=new FightingState();
					manstate.setCamp(camp);
					manstate.setMan(man);
					manstate.setText("看我的#G耳聪目明");
					zls.add(manstate);

					data.RemoveAbnormal("隐身");
					FightingState org=new FightingState();
					org.setCamp(data.getCamp());
					org.setMan(data.getMan());
					org.setEndState_2("隐身");
					zls.add(org);
					return;
				}
			}
		}
	}

	/**
	 * 灵犀技能-连环出击判定及属性附加
	 */
	public void executeLhcj() {
		FightingSkill skill = findRemoveSkill("连环出击");
		// 灵犀-连环出击加载
		if (skill != null) {
			// 惊涛拍岸 附加概率
			double gl = 0;
			AddState zt = xzstate("11025");
			if (zt != null) {
				gl = LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect());
			}
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()) + gl)){
				this.ljv += LingXiUtil.getNumberBySkillId(skill.getSkillname(), 2, skill.getSkilllvl());
			}
		}
	}

	/**
	 * 灵犀技能-与子同仇判定（只做判定加状态，但是不特效，特效放到外面增加）
	 */
	public void executeYztc(Battlefield battlefield,FightingState fightingState) {
		FightingSkill skill = findRemoveSkill("与子同仇");
		if (skill != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))){
				// 判断是否已经有战意、破甲，有则添加次数
				AddState addState = xzstate("战意");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(3);
				} else {
					getAddStates().remove(addState);
					int count = (int)addState.getStateEffect() + 3;
					addState.setStateEffect(count > 12 ? 12 : count);
				}
				addState.setType(1);
				addState.setStatename("战意");
				addState.setSurplus(50);
				getAddStates().add(addState);

				if (fightingState != null) {
					fightingState.setEndState_1("战意");
				} else {
					List<FightingState> zls = new ArrayList<>();
					FightingState ace = new FightingState();
					ace.setCamp(getCamp());
					ace.setMan(getMan());
					ace.setEndState_1("战意");
					zls.add(ace);

					FightingEvents event=new FightingEvents();
					event.setAccepterlist(zls);
					battlefield.NewEvents.add(event);
				}
			}
		}
	}

	/**
	 * 灵犀技能-惊涛拍岸技能判定，每次连击时调用（只调用一次）
	 * @return  返回最大连击次数
	 */
	public int executeJtpa(List<FightingState> zls,int maxg) {
		AddState zt = xzstate("11025");
		if (zt != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect()))){
				FightingState manstate=new FightingState();
				manstate.setCamp(camp);
				manstate.setMan(man);
				manstate.setText("看我的#G惊涛拍岸");
				zls.add(manstate);
				this.jtpa = true;
				int cs = LingXiUtil.getNumberBySkillId(zt.getStatename(), 4, (int)zt.getStateEffect());
				return maxg < cs ? maxg : cs;
			}
		}
		// 防止同一回合多次触发连击时第一次判定就开始生效的问题，每次单独判定
		this.jtpa = false;
		return maxg;
	}

	/**
	 * 获得惊涛拍岸攻击系数
	 * @return
	 */
	public double getJtpaXs(double xs) {
		AddState zt = xzstate("11025");
		if (zt != null) {
			return LingXiUtil.getNumberBySkillId(zt.getStatename(), 5, (int)zt.getStateEffect()) / 100.0;
		}
		return xs;
	}

	/**
	 * 惊涛拍岸隔山打牛判定
	 * @return
	 */
	public boolean getJtpaGs() {
		AddState zt = xzstate("11025");
		if (zt != null) {
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 3, (int)zt.getStateEffect()));
		}
		return false;
	}

	/**
	 * 有备无患判定
	 * idx = 1,摆脱控制    2，护盾反射成功  3,反射召唤兽（固定概率60%）
	 * @return
	 */
	public boolean executeYbwh(int idx) {
		FightingSkill skill = skillname("有备无患");
		if (skill != null) {
			if (idx == 3) {
				return Battlefield.isV(60);
			}
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl()));
		}
		return false;
	}

	/**
	 * 灵犀技能-将功补过判定
	 */
	public void executeJgbg(List<FightingState> zls) {
		FightingSkill skill = skillname("将功补过");
		if (skill != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))){
				// 判断是否已经有战意，有则添加次数
				AddState addState = xzstate("战意");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(3);
				} else {
					getAddStates().remove(addState);
					int count = (int)addState.getStateEffect() + 3;
					addState.setStateEffect(count > 12 ? 12 : count);
				}
				addState.setType(1);
				addState.setStatename("战意");
				addState.setSurplus(50);
				getAddStates().add(addState);
				if (zls.size() > 0 && zls.get(zls.size() - 1).getStartState().equals("移动")) {
					zls.get(zls.size() - 1).setEndState_1("战意");
				} else {
					FightingState fightingState=new FightingState();
					fightingState.setCamp(camp);
					fightingState.setMan(man);
					fightingState.setEndState_1("战意");
					zls.add(fightingState);
				}
			}
		}
	}


	/**
	 * 灵犀技能-一往无前判定
	 */
	public void executeYwwq(FightingState fightingState) {
		FightingSkill skill = skillname("一往无前");
		if (skill != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))){
				// 判断是否已经有战意，有则添加次数
				AddState addState = xzstate("破甲");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(2);
				} else {
					getAddStates().remove(addState);
					int count = (int)addState.getStateEffect() + 2;
					addState.setStateEffect(count > 12 ? 12 : count);
				}
				addState.setType(1);
				addState.setStatename("破甲");
				addState.setSurplus(50);
				getAddStates().add(addState);
				fightingState.setEndState_1("破甲");
			}
		}
	}


	/**
	 * 灵犀技能-卷土重来判定
	 */
	public boolean executeJtcl() {
		FightingSkill skill = findRemoveSkill("卷土重来");
		if (skill != null) {
			double gl = 0;
			AddState addState = xzstate("战意");
			if (addState != null) {
				gl = addState.getStateEffect() * 1.1;
			}

			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()) + gl)){
				// 不能删，用它来控制喊话以及二次加载
				this.jtcl = true;
				return true;
			}
		}
		return false;
	}


	/**
	 * 召唤兽灵犀二次加载
	 * @param
	 * @param
	 *
	 *
	 */
	public void loadLingXiSkill(){
		// 回满血法
		this.hp = hp_z;
		this.mp = mp_z;
		// 清临时增益
		clearBuff();
		// 清所有持续增减
		addStates.clear();

		if (lingxi==null||lingxi.equals("")) {
			return;
		}
		List<Skill> skills = LingXiUtil.getLingXiSkill(lingxi);
		for (Skill skill : skills) {
			if (skill!=null) {
				findRemoveSkill(skill.getSkillname());

				if ("卷土重来".equals(skill.getSkillname())) {
					continue;
				}

				FightingSkill fightingSkill=new FightingSkill(skill, lvl, zs, lvl*50,0,0);
				fightingSkill.setSkillbeidong(1);
				addSkill(fightingSkill);

				// 3回合持续技能添加，化险为夷为护盾技能
				if (skill.getSkillid() == 11006 || skill.getSkillid() == 11007 ||
						skill.getSkillid() == 11010 || skill.getSkillid() == 11012 ||
						skill.getSkillid() == 11013 || skill.getSkillid() == 11014 ||
						skill.getSkillid() == 11015 || skill.getSkillid() == 11017 ||
						skill.getSkillid() == 11018 || skill.getSkillid() == 11019 || skill.getSkillid() == 11025) {
					AddState addState = new AddState();
					addState.setType(1);
					addState.setStatename(skill.getSkillid() + "");
					addState.setStateEffect(skill.getSkilllevel());
					addState.setSurplus(3);
					addStates.add(addState);
				}
			}
		}
	}

	/**
	 * 灵犀-奋不顾身：计算兵临城下的血蓝扣除系数
	 * @param type  1=HP 2=MP
	 * @return
	 */
	public double getBlcxKc(int type) {
		FightingSkill skill = skillname("奋不顾身");
		if (skill != null) {
			return LingXiUtil.getNumberBySkillId(skill.getSkillname(), type, skill.getSkilllvl()) / 100.0;
		}
		return type == 1 ? 0.5 : 0.2;
	}

	/**
	 * 灵犀-奋不顾身：各项数值获取
	 * @param	idx  为0时附加战意或破甲
	 * @return
	 */
	public int executeFbgs(int idx, FightingState org) {

		if (idx == 7) {
			if (skillname("兵临城下") == null) {
				return 0;
			}
		}

		FightingSkill skill = skillname("奋不顾身");
		if (skill != null) {
			//idx  为0时附加战意或破甲
			if (idx == 0) {
				String state = Battlefield.random.nextInt(10) > 4 ? "战意" : "破甲";
				// 判断是否已经有战意，有则添加次数
				AddState addState = xzstate(state);
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(2);
				} else {
					getAddStates().remove(addState);
					int count = (int)addState.getStateEffect() + 2;
					addState.setStateEffect(count > 12 ? 12 : count);
				}
				addState.setType(1);
				addState.setStatename(state);
				addState.setSurplus(50);
				getAddStates().add(addState);

				org.setText("看我的#G奋不顾身");
				org.setEndState_1(state);
			} else {
				// 返回倍率
				return LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl());
			}
		}
		return 0;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////         /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////        /////////////////////////////////////////////    法术系         ////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 八面玲珑判定
	 * @return
	 */
	public void executeBmll() {
		AddState zt = xzstate("11027");
		// 判断概率
		if (zt != null && Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
			// 增加临时强法
			this.qf += LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect()) / 100.0;
		}
	}

	/**
	 * 一鸣惊人判定
	 * @return
	 */
	public boolean executeYmjr() {
		FightingSkill skill = skillname("一鸣惊人");
		if (skill != null) {
			// 一鸣惊人判断概率
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()));
		}
		return false;
	}

	/**
	 * 大开杀戒判定
	 * @return
	 */
	public int executeDksj(int idx) {
		FightingSkill skill = skillname("大开杀戒");
		if (skill != null) {
			return LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl());
		}
		return 0;
	}

	/**
	 * 青云直上判定
	 * @return
	 */
	public int executeQyzs() {
		FightingSkill skill = skillname("青云直上");
		if (skill != null) {
			return LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl());
		}
		return 0;
	}

	/**
	 * 锥心刺骨判定
	 * @return
	 */
	public int executeZxcg(int idx) {
		FightingSkill skill = skillname("锥心刺骨");
		if (skill != null) {
			return LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl());
		}
		return 0;
	}

	/**
	 * 哀兵必胜判定
	 * @return
	 */
	public boolean executeAbbs(Battlefield battlefield) {
		if (battlefield.CurrentRound < 4) {
			return false;
		}
		FightingSkill skill = skillname("哀兵必胜");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))){
				this.hsfz += 35;
				this.hs += 15;
				this.fsmz += 25;
				return true;
			}
		}
		return false;
	}


	/**
	 * 一飞冲天\展翅欲飞状态附加
	 * @return
	 */
	public void executeYfct(List<FightingState> zls) {
		FightingSkill skill = skillname("一飞冲天");
		if (skill != null) {
			int jc = LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl());
			this.fskbcd += jc;
			this.fskbjl += jc;

			AddState addState = new AddState();
			addState.setStatename("一飞冲天");
			addState.setSurplus(3);
			addState.setStateEffect(jc);
			getAddStates().add(addState);

			FightingState ace = new FightingState();
			ace.setCamp(camp);
			ace.setMan(man);
			ace.setText("看我的#G一飞冲天");
			ace.setEndState_1("一飞冲天");
			zls.add(ace);
		}
		skill = skillname("展翅欲飞");
		if (skill != null) {
			int jc = LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl());
			this.hs += jc;
			this.hsjv += jc;

			AddState addState = new AddState();
			addState.setStatename("展翅欲飞");
			addState.setSurplus(3);
			addState.setStateEffect(jc);
			getAddStates().add(addState);

			FightingState ace = new FightingState();
			ace.setCamp(camp);
			ace.setMan(man);
			ace.setText("看我的#G展翅欲飞");
			ace.setEndState_1("展翅欲飞");
			zls.add(ace);
		}
	}

	/**
	 * 一飞冲天\展翅欲飞状态判定
	 * @return
	 */
	public void executeYfct2() {
		AddState zt = xzstate("一飞冲天");
		if (zt != null) {
			this.fskbcd += zt.getStateEffect();
			this.fskbjl += zt.getStateEffect();
		}
		zt = xzstate("展翅欲飞");
		if (zt != null) {
			this.hs += zt.getStateEffect();
			this.hsjv += zt.getStateEffect();
		}
	}


	/**
	 * 飘忽不定判定
	 * @return
	 */
	public void executePhbd(FightingEvents events) {
		FightingSkill skill = skillname("飘忽不定");
		if (skill != null) {
			// 判定是否有隐身技
			if (getSkillType("隐身") != null) {
				// 飘忽不定判断概率
				if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))) {
					FightingState state = new FightingState();
					state.setCamp(camp);
					state.setMan(man);
					state.setEndState_1("隐身");

					AddState addState = new AddState();
					addState.setStatename("隐身");
					addState.setSurplus(3);
					getAddStates().add(addState);

					events.getAccepterlist().add(state);
				}
			}
		}
	}

	/**
	 * 待时而飞判定
	 * @param
	 */
	public void executeSdef(Battlefield battlefield) {
		AddState zt = xzstate("11035");
		if (zt != null) {
			// 待时而飞判断概率
			int my = 0;	//我方人数
			int it = 0;	//对方人数
			for (ManData data : battlefield.fightingdata) {
				if (data.getStates() == 0 && data.getType() < 3) {
					if (data.getCamp() == getCamp()) {
						my++;
					} else {
						it++;
					}
				}
			}

			if (my < it) {
				this.fskbcd += LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect());
			}
		}
	}


	/**
	 * 清风盈袖\月共潮生\灵光灌顶判定
	 * @return
	 */
	public void executeQfyx(ManData noData,List<FightingState> zls) {
		FightingSkill skill = skillname("灵光灌顶");
		// 灵光灌顶判断概率
		if (skill != null) {
			// 每损失血量百分百
			int hpb = LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl());
			// 计算损失血量
			int shp = hp_z - hp;
			// 当前损失的百分百
			int bfb = (int)((double)shp / hp_z * 100);
			// 倍率
			double bl = (double) bfb / hpb;

			this.hs += 3 * bl;
		}

		if (noData.getHp() < 300000) {
			return;
		}
		AddState zt = xzstate("11031");
		// 判断概率
		if (zt != null && Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
			// 增加临时法术狂暴程度
			this.fskbcd += LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());
			FightingState ace = new FightingState();
			ace.setCamp(camp);
			ace.setMan(man);
			ace.setText("看我的#G清风盈袖");
			zls.add(0,ace);
		}
		zt = xzstate("11032");
		// 判断概率
		if (zt != null && Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
			// 增加临时法术忽视
			this.hs += LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());
			FightingState ace = new FightingState();
			ace.setCamp(camp);
			ace.setMan(man);
			ace.setText("看我的#G月共潮生");
			zls.add(0,ace);
		}
	}


	/**
	 * 节节高升\步步为营判定（不判定主目标，每回合开始调用一次）
	 * @return
	 */
	public void executeJjgs(List<FightingState> xieStates,FightingState fightingState) {
		AddState zt = xzstate("11033");
		// 判断概率
		if (zt != null && Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
			// 增加临时法术狂暴程度
			this.fskbcd += LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());

			if (fightingState != null) {
				fightingState.setText("看我的#G节节高升");
			} else {
				fightingState=new FightingState();
				fightingState.setCamp(camp);
				fightingState.setMan(man);
				fightingState.setText("看我的#G节节高升");
				xieStates.add(fightingState);
			}
		}
		zt = xzstate("11034");
		// 判断概率
		if (zt != null && Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
			// 增加临时法术忽视
			this.hs += LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());

			if (fightingState != null) {
				fightingState.setText("看我的#G步步为营");
			} else {
				fightingState=new FightingState();
				fightingState.setCamp(camp);
				fightingState.setMan(man);
				fightingState.setText("看我的#G步步为营");
				xieStates.add(fightingState);
			}
		}
	}



	/**
	 * 灵犀-风荷送香护盾判断及增加
	 */
	public void addFaDun(long fs, FightingState ace) {
		// 判断是否死亡
		if (getStates() != 0) {
			return;
		}

		// 风荷送香,概率回血加盾
		AddState zt = xzstate("11029");
		if (zt != null) {
			// 判断概率
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(zt.getStatename(), 1, (int)zt.getStateEffect()))) {
				// 判断数值
				int dun = LingXiUtil.getNumberBySkillId(zt.getStatename(), 2, (int)zt.getStateEffect());

				long hp = fs * dun / 100;

				int currHP = getHp();
				int maxHP = getHp_z();

				int maxDun =  getMp_z() / 100 * LingXiUtil.getNumberBySkillId(zt.getStatename(), 3, (int)zt.getStateEffect());

				int queshao = maxHP - currHP;


				if (queshao > 0) {
					ChangeFighting fighting=new ChangeFighting();
					if (queshao > hp) {
						// 缺少的生命值大于盾的值则只加血
						fighting.setChangehp((int)hp);
						hp = 0;
					} else {
						// 缺少的生命值小于盾的值则加满血，剩余加盾
						fighting.setChangehp(queshao);
						hp = hp - queshao;
					}

					ChangeData(fighting, ace);
				}
				if (hp > 0) {
					// 判断是否已经有盾，有盾则补盾并刷新回合数
					AddState addState = xzstate("风荷送香");
					if (addState == null) {
						addState = new AddState();
						addState.setStateEffect(hp > maxDun ? maxDun : hp);
					} else {
						getAddStates().remove(addState);
						hp += addState.getStateEffect();
						addState.setStateEffect(hp > maxDun ? maxDun : hp);
					}
					addState.setType(1);
					addState.setStatename("风荷送香");
					addState.setSurplus(3);
					ace.setEndState_1("风荷送香");
					getAddStates().add(addState);
				}

			}
		}
	}

	/**
	 * 灵犀技能-清者自清、洞浊其相判定执行
	 * ace 目标动作
	 */
	public void executeQzzq(ManData noman,FightingState ace, List<FightingState> zls, Battlefield battlefield) {
		FightingSkill skill = skillname("清者自清");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1,skill.getSkilllvl()))) {
				String state = LingXiUtil.BUFF[Battlefield.random.nextInt(4)];
				noman.RemoveAbnormal(state);
				ace.setEndState_2(state);
			}
		}
		skill = skillname("洞浊其相");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1,skill.getSkilllvl()))) {
				// 获取周围一格内的隐身单位
				List<ManData> datas = battlefield.getYSDR(noman);
				if (datas.size() != 0) {
					ManData data = datas.get(Battlefield.random.nextInt(datas.size()));
					FightingState manstate=new FightingState();
					manstate.setCamp(camp);
					manstate.setMan(man);
					manstate.setText("看我的#G洞浊其相");
					zls.add(manstate);

					data.RemoveAbnormal("隐身");
					FightingState org=new FightingState();
					org.setCamp(data.getCamp());
					org.setMan(data.getMan());
					org.setEndState_2("隐身");
					zls.add(org);
					return;
				}
			}
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////           ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////    ////////////////////////////////////////////    辅助系         ////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	/**
	 * 春风化雨判定
	 * @return
	 */
	public boolean executeCfhy() {
		FightingSkill skill = skillname("春风化雨");
		if (skill != null) {
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()));
		}
		return false;
	}

	/**
	 * 惠风在衣判定
	 * @return
	 */
	public boolean executeHfzy() {
		FightingSkill skill = skillname("惠风在衣");
		if (skill != null) {
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()));
		}
		return false;
	}

	/**
	 * 上下天光判定
	 * @return
	 */
	public boolean executeSxtg() {
		FightingSkill skill = skillname("上下天光");
		if (skill != null) {
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()));
		}
		return false;
	}

	/**
	 * 左右逢源判定
	 * @return
	 */
	public boolean executeZyfy() {
		FightingSkill skill = skillname("左右逢源");
		if (skill != null) {
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()));
		}
		return false;
	}


	/**
	 * 冰消瓦解判定
	 * @return
	 */
	public boolean executeBxwj() {
		FightingSkill skill = skillname("冰消瓦解");
		if (skill != null) {
			return Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()));
		}
		return false;
	}

	/**
	 * 紫燕翻飞判定
	 * @return
	 */
	public int executeZyff(int idx) {
		FightingSkill skill = skillname("紫燕翻飞");
		if (skill != null) {
			return LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl());
		}
		return 0;
	}




	/**
	 * 一矢中的增加忽视系数
	 * 1,获取一矢中的暗渡加成系数
	 * 2,自身减少的忽视系数
	 * 3,获得一矢中的增益的系数
	 * @return
	 */
	public int executeYszd(int idx) {

		if (idx == 3) {
			AddState addState = xzstate("一矢中的");
			if (addState != null) {
				return (int)addState.getStateEffect();
			}
			return 0;
		}

		FightingSkill skill = skillname("一矢中的");
		if (skill != null) {
			if (idx == 1) {
				return LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl());
			} else {
				AddState addState = xzstate("暗渡失效");
				if (addState != null) {
					return (int)addState.getStateEffect();
				}
			}
		}
		return 0;
	}




	/**
	 * 银装素裹\傲雪凌霜判定
	 * @return
	 */
	public void executeYzsg(Battlefield battlefield ,FightingState fightingState) {
		FightingSkill skill = skillname("银装素裹");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))){
				int value = LingXiUtil.getNumberBySkillId(skill.getSkillname(), 2, skill.getSkilllvl());
				this.Quality.setRolekhl(this.Quality.getRolekhl()+value);
				this.Quality.setRolekhs(this.Quality.getRolekhs()+value);
				this.Quality.setRolekfy(this.Quality.getRolekfy()+value);
				this.Quality.setRolekyw(this.Quality.getRolekzd()+value);

				if (fightingState != null) {
					fightingState.setText("看我的#G银装素裹");
				} else {
					List<FightingState> zls = new ArrayList<>();
					FightingState ace = new FightingState();
					ace.setCamp(getCamp());
					ace.setMan(getMan());
					ace.setText("看我的#G银装素裹");
					zls.add(ace);

					FightingEvents event=new FightingEvents();
					event.setAccepterlist(zls);
					battlefield.NewEvents.add(event);
				}
			}
		}
		skill = skillname("傲雪凌霜");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))){
				int value = LingXiUtil.getNumberBySkillId(skill.getSkillname(), 2, skill.getSkilllvl());
				this.Quality.setRolekff(this.Quality.getRolekff()+value);
				this.Quality.setRoleklf(this.Quality.getRoleklf()+value);
				this.Quality.setRoleksf(this.Quality.getRoleksf()+value);
				this.Quality.setRolekhf(this.Quality.getRolekhf()+value);
				this.Quality.setRolekgh(this.Quality.getRolekgh()+value);
				if (fightingState != null) {
					fightingState.setText("看我的#G傲雪凌霜");
				} else {
					List<FightingState> zls = new ArrayList<>();
					FightingState ace = new FightingState();
					ace.setCamp(getCamp());
					ace.setMan(getMan());
					ace.setText("看我的#G傲雪凌霜");
					zls.add(ace);

					FightingEvents event=new FightingEvents();
					event.setAccepterlist(zls);
					battlefield.NewEvents.add(event);
				}
			}
		}
	}


	/**
	 * 八风不动判定
	 * @return
	 */
	public int executeBfbd(int idx) {
		FightingSkill skill = skillname("八风不动");
		if (skill != null) {
			return LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl()) ;
		}
		return 0;
	}

	/**
	 * 雪中送炭判定
	 * @return
	 */
	public void executeXzst(Battlefield battlefield) {
		FightingSkill skill = skillname("雪中送炭");
		if (skill != null) {
			if(Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))) {
				int wei = battlefield.Datapathhuo(getCamp(),getMan() - 5);
				if (wei != -1) {
					ManData master = battlefield.fightingdata.get(wei);
					master.RemoveAbnormal(values1);

					FightingEvents moveEvents=new FightingEvents();
					List<FightingState> zls = new ArrayList<>();
					FightingState ace = new FightingState();
					ace.setCamp(getCamp());
					ace.setMan(getMan());
					ace.setText("看我的#G雪中送炭");
					zls.add(ace);

					FightingState ace2 = new FightingState();
					ace2.setCamp(master.getCamp());
					ace2.setMan(master.getMan());
					ace2.setEndState_2("清除异常状态");
					zls.add(ace2);
					moveEvents.setAccepterlist(zls);
					battlefield.NewEvents.add(moveEvents);
				}
			}
		}
		return;
	}

	/**
	 * 八方来援判定
	 * @return
	 */
	public int executeBfly(Battlefield battlefield) {
		FightingSkill skill = skillname("八方来援");
		if (skill != null) {
			int my = 0;
			for (ManData data : battlefield.fightingdata) {
				if (data.getStates() == 0 && data.getType() == 1) {
					if (data.getCamp() == getCamp()) {
						my++;
					}
				}
			}
			if (my <= LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl())) {
				return LingXiUtil.getNumberBySkillId(skill.getSkillname(), 2, skill.getSkilllvl());
			}
		}
		return 0;
	}

	/**
	 * 拔刀相助判定
	 * @return
	 */
	public int executeBdxz(Battlefield battlefield) {
		FightingSkill skill = skillname("拔刀相助");
		if (skill != null) {
			int my = 0;
			for (ManData data : battlefield.fightingdata) {
				if (data.getStates() == 0 && data.getType() == 0) {
					if (data.getCamp() == getCamp()) {
						my++;
					}
				}
			}
			if (my <= LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl())) {
				return LingXiUtil.getNumberBySkillId(skill.getSkillname(), 2, skill.getSkilllvl());
			}
		}
		return 0;
	}
	public void addHuDun2(int idx,List<FightingState> list) {
		FightingSkill skill = skillname("吉人天相");
		if (skill != null) {
			if (Battlefield.isV(skill.getSkillhitrate()*10)) {
				addAddState("吉人天相", (getHp_z()*0.3), 0, 3);

				FightingState org=new FightingState();
				org.setCamp(getCamp());
				org.setMan(getMan());
				org.setEndState_1("吉人天相");
				org.setText("#G吉人天相");
				list.add(org);
			}
		}
	}
	/**
	 * 碧荷凝露判定
	 * @return
	 */
	public void executeBhnl(Battlefield battlefield) {
		FightingSkill skill = skillname("碧荷凝露");
		if (skill != null) {
			int my = 0;
			for (ManData data : battlefield.fightingdata) {
				if (data.getStates() == 0 && data.getType() == 0) {
					if (data.getCamp() == getCamp()) {
						my++;
					}
				}
			}
			if (my <= 2) {
				if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()) + 100)) {
					int[] yao = {21000,42000,0,0};
					FightingEvents moveEvents=new FightingEvents();
					List<FightingState> zls = new ArrayList<>();

					List<ManData> ds=MixDeal.get(true, this,2,battlefield.nomy(getCamp()),1, 0, 0, 0, 1, 0, battlefield,1);
					if (ds.size() > 0) {
						ManData data=ds.get(0);
						ChangeFighting changeFighting=battlefield.Typeyao(data,yao);
						FightingState yaostate=new FightingState();
						yaostate.setStartState("药");
						data.ChangeData(changeFighting, yaostate);
						zls.add(yaostate);

						FightingState say = new FightingState();
						say.setCamp(getCamp());
						say.setMan(getMan());
						say.setText("看我的#G碧荷凝露");
						zls.add(say);
						moveEvents.setAccepterlist(zls);
						battlefield.NewEvents.add(moveEvents);
					}

				}
			}
		}
		return;
	}

	/**
	 * 有仇必报判定
	 * @return
	 */
	public void executeYcbb(Battlefield battlefield) {
		FightingSkill skill = skillname("有仇必报");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))) {

				FightingEvents moveEvents=new FightingEvents();
				List<FightingState> zls = new ArrayList<>();
				FightingState ace = new FightingState();
				ace.setCamp(getCamp());
				ace.setMan(getMan());
				ace.setText("看我的#G有仇必报");
				zls.add(ace);
				moveEvents.setAccepterlist(zls);
				battlefield.NewEvents.add(moveEvents);

				for (ManData data : battlefield.fightingdata) {
					if (data.getStates() == 0 && data.getType() <= 2 && data.getCamp() != getCamp()) {
						data.getQuality().setRolekff(data.getQuality().getRolekff()-2);
						data.getQuality().setRoleklf(data.getQuality().getRoleklf()-2);
						data.getQuality().setRoleksf(data.getQuality().getRoleksf()-2);
						data.getQuality().setRolekhf(data.getQuality().getRolekhf()-2);
						data.getQuality().setRolekgh(data.getQuality().getRolekgh()-2);
					}
				}
			}
		}
	}

	/**
	 * 灵犀-步步相逼
	 */
	public void executeBbxb(ManData data ,List<FightingState> list,FightingState diren) {
		FightingSkill skill = findRemoveSkill("步步相逼");
		if (skill != null) {
			AddState state = data.xzstate("步步相逼");
			if (state == null) {
				data.addAddState("步步相逼", 0, 0, 999);
				int value = LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl());
				data.getQuality().setRolekff(data.getQuality().getRolekff()-value);
				data.getQuality().setRoleklf(data.getQuality().getRoleklf()-value);
				data.getQuality().setRoleksf(data.getQuality().getRoleksf()-value);
				data.getQuality().setRolekhf(data.getQuality().getRolekhf()-value);
				data.getQuality().setRolekgh(data.getQuality().getRolekgh()-value);
				diren.setEndState_1("步步相逼");

				FightingState org=new FightingState();
				org.setCamp(getCamp());
				org.setMan(getMan());
				org.setText("看我的#G步步相逼");
				list.add(0,org);
			}
		}
	}


	/**
	 * 灵犀-焕然新生护盾判断及增加
	 */
	public void addFaDun2(int idx,List<FightingState> list) {
		FightingSkill skill = findRemoveSkill("焕然新生");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl()))) {
				addAddState("焕然新生", getMp_z(), 0, 3);

				FightingState org=new FightingState();
				org.setCamp(getCamp());
				org.setMan(getMan());
				org.setEndState_1("焕然新生");
				org.setText("看我的#G焕然新生");
				list.add(org);
			}
		}
	}

	/**
	 * 灵犀-飘然出尘判定
	 */
	public void executePrcc(int idx,List<FightingState> list) {
		FightingSkill skill = skillname("飘然出尘");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), idx, skill.getSkilllvl()))) {
				addAddState("隐身", 0, 0, LingXiUtil.getNumberBySkillId(skill.getSkillname(), 3, skill.getSkilllvl()));
				FightingState state = new FightingState();
				state.setCamp(getCamp());
				state.setMan(getMan());
				state.setEndState_1("隐身");
				state.setText("看我的#G飘然出尘");
				list.add(state);
			} else if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 4, skill.getSkilllvl()))) {
				addAddState(TypeUtil.BB_RHTY, 5, 0, 2);
				FightingState state = new FightingState();
				state.setCamp(getCamp());
				state.setMan(getMan());
				state.setText("看我的#G飘然出尘");
				state.setStartState("法术攻击");
				state.setSkillskin(TypeUtil.BB_RHTY);
				state.setEndState_1(TypeUtil.BB_RHTY);
				addAddState(TypeUtil.BB_RHTY, skill.getSkillhurt(), 0, 2);
				list.add(state);
			}
		}
	}

	/**
	 * 灵犀-春色满园
	 */
	public int executeCsmy(List<FightingState> list) {
		FightingSkill skill = findRemoveSkill("春色满园");
		if (skill != null) {
			if (Battlefield.isV(LingXiUtil.getNumberBySkillId(skill.getSkillname(), 1, skill.getSkilllvl()))) {
				FightingState org=new FightingState();
				org.setCamp(getCamp());
				org.setMan(getMan());
				org.setText("看我的#G春色满园");
				list.add(org);
				return LingXiUtil.getNumberBySkillId(skill.getSkillname(), 2, skill.getSkilllvl());
			}
		}
		return 0;
	}




	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////             ///////////      /////////   ///////////         /////////////////////////////////////////////////
	///////////////////////////////   /////////////////////   /   ////////   ///////////   //////   ///////////////////////////////////////////////
	//////////////////////////////   /////////////////////   //   ///////   ///////////   /////////   /////////////////////////////////////////////
	/////////////////////////////   /////////////////////   ///   //////   ///////////   ///////////   ////////////////////////////////////////////
	////////////////////////////  	     ///////////////   ////   /////   ///////////   /////////////   ///////////////////////////////////////////
	///////////////////////////   /////////////////////   /////   ////   ///////////   /////////////   ////////////////////////////////////////////
	//////////////////////////   /////////////////////   //////   ///   ///////////   /////////////   /////////////////////////////////////////////
	/////////////////////////   /////////////////////   ///////   //   ///////////   ///////////   ////////////////////////////////////////////////
	////////////////////////   /////////////////////   ////////   /   ///////////   ////////    ///////////////////////////////////////////////////
	///////////////////////              //////////   /////////      ///////////            ///////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 五倍怪特殊技能，被物理攻击就释放4级火法打死对面一个单位,必须有四级火法技能
	 */
	public boolean executeAdfh() {
		FightingSkill skill = skillname("挨打反火");
		if (skill != null) {
			skill = skillname("挨打反火");
			if (skill != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 五倍怪特殊技能，死亡时对周围一码范围的队友造成自身生命上限20%的伤害
	 */
	public boolean executeSwbz() {
		FightingSkill skill = skillname("死亡爆炸");
		if (skill != null) {
			return true;
		}
		return false;
	}



	/**
	 * 处理回合开始前以及后续的进场事件
	 * @param xieStates
	 */
	public void before(List<FightingState> xieStates,FightingState fightingState) {
		addZhanyi(xieStates,fightingState);
		executeBmll();
		initLXBuff();
		executeJjgs(xieStates,fightingState);
		executeLhcj();
		executeYfct2();
	}


	public String getSkin() {
		return skin;
	}

	/**
	 * 清除临时增益(回合开始前调用，清除上一回合buff)
	 */
	public void clearBuff() {
		if (this.type > 2) {
			return;
		}
		this.jtpa = false;
		this.fbgs = true;
		this.ymjr = false;
		this.fskbbj = false;


		this.zm = 0;		// 附加致命
		this.kb = 0;		// 附加狂暴
		this.mz = 0;		// 附加命中
		this.pwljl = 0;		// 附加破物理几率
		this.pwlcd = 0;		// 附加破物理程度
		this.hsfz = 0;		// 附加忽视反震概率
		this.ljv = 0;		// 附加连击率

		this.qf = 0;		// 强仙法鬼火
		this.hs = 0;		// 忽视仙法鬼火
		this.fsmz = 0;		// 法术命中
		this.fskbcd = 0;	// 法术狂暴程度
		this.fskbjl = 0;	// 法术狂暴几率
		this.hsjv = 0;		// 忽视法术抗性几率

		this.bhws = 0; 	// 附加冰混忘睡抗性

		this.cd = this.cd != 0 ? this.cd-- : 0;
	}

	public void famenBuff(FightingState org) {
		if (famencs== 0) { //法门为0 清空法门特效
			//以静制动
			FightingSkill skill = skillname("以静制动");
			if (skill != null) {
				// 判断是否已经有BUFF
				AddState addState = xzstate("以静制动");
				if (addState != null) {
					getAddStates().remove(addState); //删掉状态
					org.setEndState_2("以静制动"); //状态
				}
			}
			FightingSkill skill1 = skillname("妙法莲华");
			if (skill1 != null) {
				// 判断是否已经有BUFF
				AddState addState = xzstate("妙法莲华");
				if (addState != null) {
					getAddStates().remove(addState); //删掉状态
					org.setEndState_2("妙法莲华"); //状态
				}
			}
			FightingSkill skill2 = skillname("明镜止水");
			if (skill2 != null) {
				// 判断是否已经有BUFF
				AddState addState = xzstate("明镜止水");
				if (addState != null) {
					getAddStates().remove(addState); //删掉状态
					org.setEndState_2("明镜止水"); //状态
				}
			}


		}else {
			if (this.famencs> 5) {
				this.famencs = 5;
			}
			//以静制动
			FightingSkill skill = skillname("以静制动");
			if (skill != null) {
				AddState addState = xzstate("以静制动");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(famencs); //效果
				} else {
					getAddStates().remove(addState);
					addState.setStateEffect(famencs);
				}
				addState.setType(0); //0增益 1减益
				addState.setStatename("以静制动");
				addState.setSurplus(50); //回合数
				getAddStates().add(addState);
				//org2.setSkillskin("以静制动");  //技能
				org.setEndState_1("以静制动"); //状态
			}
			//妙法莲华
			FightingSkill skill1 = skillname("妙法莲华");
			if (skill1 != null) {
				AddState addState = xzstate("妙法莲华");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(famencs); //效果
				} else {
					getAddStates().remove(addState);
					addState.setStateEffect(famencs);
				}
				addState.setType(0); //0增益 1减益
				addState.setStatename("妙法莲华");
				addState.setSurplus(50); //回合数
				getAddStates().add(addState);
				//org2.setSkillskin("妙法莲华");  //技能
				org.setEndState_1("妙法莲华"); //状态
			}
			//明镜止水
			FightingSkill skill2 = skillname("明镜止水");
			if (skill2 != null) {
				AddState addState = xzstate("明镜止水");
				if (addState == null) {
					addState = new AddState();
					addState.setStateEffect(famencs); //效果
				} else {
					getAddStates().remove(addState);
					addState.setStateEffect(famencs);
				}
				addState.setType(0); //0增益 1减益
				addState.setStatename("明镜止水");
				addState.setSurplus(50); //回合数
				getAddStates().add(addState);
				//org2.setSkillskin("明镜止水");  //技能
				org.setEndState_1("明镜止水"); //状态
			}
		}
	}

	public void fmPTGJ(FightingState fightingState) {

		FightingSkill skill1 = skillname("神力加身");
		FightingSkill skill2 = skillname("力挽狂澜");
		FightingSkill skill3 = skillname("势如破竹");
		if (skill1 != null||skill2 != null||skill3 != null) {

			famencs = famencs +1;

			if (this.famencs> 5) {
				this.famencs = 5;
			}
		}
	}

	//每回合重置利刃加身的总伤害
	public void fmLrjscz() {
		AddState addState = xzstate("利刃加身");
		if(addState!=null) {
			if(this.getDffmsld()>0) {
				this.setNrlrjs((int)(this.getDffmsld()*22.85));
			}
		}
		//	this.setNrjycz(this.getFmsld());
	}

	//判断兽魂俯首 hp_z mp_z ap sp
	public void fmshfssx() {
		if (this.type==1) {
			AddState addState = xzstate("兽魂俯首");
			if(addState!=null) {

				if (huoyue>shanghai) {
					if(huoyue>kangluobao) {
						if(huoyue>yuanzhu) {
							if (ewhuoyue ==0) {
								this.ewhuoyue = this.hp_z;
								this.hp_z = this.hp_z + (this.hp_z*(this.fmshfs/100)/300) ;// 活跃 根骨
							}
						}else {
							if (ewyuanzhu ==0) {
								this.ewyuanzhu =this.sp;
								this.sp = this.sp + (this.sp*(this.fmshfs/100)/600);// 支援 敏捷
							}
						}
					}else if(kangluobao>yuanzhu) {
						if (ewkangluobao ==0) {
							this.ewkangluobao = this.ap;// 抗落宝 力
							this.ap = this.ap+ (this.ap*(this.fmshfs/100)/300);// 抗落宝 力量
							//this.ap = this.ap +10000;
						}
					}else {
						if (ewyuanzhu ==0) {
							this.ewyuanzhu = this.sp;// 支援 敏捷
							this.sp = this.sp+ (this.sp*(this.fmshfs/100)/600);// 支援 敏捷
						}
					}
				}else if(shanghai>kangluobao) {
					if(shanghai>yuanzhu) {
						if (ewshanghai ==0) {
							this.ewshanghai = this.mp_z;// 伤害或者回复或者落宝 灵性
							this.mp_z = this.mp_z + (this.mp_z*(this.fmshfs/100)/300);//
						}
					}else {
						if (ewyuanzhu ==0) {
							this.ewyuanzhu = this.sp;// 支援 敏捷
							this.sp = this.sp+ (this.sp*(this.fmshfs/100)/600);// 支援 敏捷
						}
					}
				}else if(kangluobao>yuanzhu) {
					if (ewkangluobao ==0) {
						this.ewkangluobao = this.ap;// 抗落宝 力量
						this.ap = this.ap+ (this.ap*(this.fmshfs/100)/300);//
					}
				}else {
					if (ewyuanzhu ==0) {
						this.ewyuanzhu = this.sp;// 支援 敏捷
						this.sp = this.sp+ (this.sp*(this.fmshfs/100)/600);// 支援 敏捷
					}
				}
			}else {
				if (ewhuoyue !=0) {
					this.hp_z = this.ewhuoyue;// 活跃 根骨
					this.ewhuoyue = 0;// 活跃 根骨
				}
				if (ewshanghai !=0) {
					this.mp_z = this.ewshanghai;// 伤害或者回复或者落宝 灵性
					this.ewshanghai = 0;// 伤害或者回复或者落宝 灵性
				}
				if (ewkangluobao !=0) {
					this.ap = this.ewkangluobao;// 抗落宝 力量
					this.ewkangluobao = 0;// 抗落宝 力量
				}
				if (ewyuanzhu !=0) {
					this.sp = this.ewyuanzhu;// 支援 敏捷
					this.ewyuanzhu = 0;// 支援 敏捷
				}
			}
		}
	}

	//法门-重生
	public boolean fmshfscs() {
		AddState addState = xzstate("兽魂俯首");
		if(addState!=null) {
			int gv= fmshfs /222;
			if(Battlefield.isV(gv)) {
				return true;
			}
		}
		return false;
	}
	public int getfamencs() {
		return famencs;
	}

	public void setfamencs(int famencs) {
		this.famencs = famencs;
	}

	public Integer getFmsld() {
		return fmsld;
	}

	public void setFmsld(Integer fmsld) {
		this.fmsld = fmsld;
	}

	public int getNrlrjs() {
		return nrlrjs;
	}

	public void setNrlrjs(int nrlrjs) {
		this.nrlrjs = nrlrjs;
	}

	public int getNrjycz() {
		return nrjycz;
	}

	public void setNrjycz(int nrjycz) {
		this.nrjycz = nrjycz;
	}
	public int getDffmsld() {
		return dffmsld;
	}

	public void setDffmsld(int dffmsld) {
		this.dffmsld = dffmsld;
	}
	public int getFmwjbc() {
		return fmwjbc;
	}

	public void setFmwjbc(int fmwjbc) {
		this.fmwjbc = fmwjbc;
	}

	public int getFmshfs() {
		return fmshfs;
	}

	public void setFmshfs(int fmshfs) {
		this.fmshfs = fmshfs;
	}


}
