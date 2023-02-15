package org.come.entity;

import java.math.BigDecimal;

import org.come.model.Skill;
import org.come.server.GameServer;

/**
 * 灵宝
 * @author 叶豪芳
 * @date 2017年12月24日 下午6:58:04
 * 
 */ 
public class Lingbao implements Cloneable {
	// 灵宝名称
	private String baoname;
	// 获得难度
	private String gethard;
	// 类型
	private String baotype;
	// 活跃
	private Integer baoactive;
	// 速度
	private String baospeed;
	// 法宝回复
	private String baoreply;
	// 落宝几率
	private String baoshot;
	// 法宝伤害
	private String baoap;
	// 抗落宝
	private String resistshot;
	// 援助几率
	private String assistance;
	// 擅长技能  xxxx|xxxx
	private String goodskill;
	// 皮肤
	private String skin;
	// 角色ID
	private BigDecimal roleid;
	// 灵宝符石  id|id
	private String fushis;
	// 灵宝道行
	private BigDecimal lingbaolvl;
	// 灵宝当前进度经验
	private BigDecimal lingbaoexe;
	//灵宝契合度
	private long lingbaoqihe;
	// 灵宝技能集合    "疾风骤雨=1|疾风骤雨=2|疾风骤雨=3"
	private String skills;
	// 操作字段  为空不操作 删除="删除" 新增="增加"
	private String operation;	
	// 灵宝附加抗性  "风=1.5"
	private String kangxing;
	// 灵宝ID
	private BigDecimal baoid;
	//用于判断是否装备 不为空装备
	private int equipment;
	// 灵宝品质
	private String baoquality;
	// 天赋技能
	private String tianfuskill;
	// 技能开启数
	private Integer skillsum;
	// 符石开启数
	private Integer fusum;
	
	/**更改技能集合*/
	public String skilljihe(Skill skill){
		int type=skill.getSkilltype();
		if (type==0&&!baotype.equals("攻击")) {
			return "学习技能失败,无法学习该类型的技能";
		}else if (type==1&&!baotype.equals("辅助")) {
			return "学习技能失败,无法学习该类型的技能";
		}else if (type==2&&!baotype.equals("落宝")) {
			return "学习技能失败,无法学习该类型的技能";
		}
		String skillname = skill.getSkillname();
		String lvl=skill.getSkilllevel()+"";
		int min =Integer.parseInt(lvl.substring(0,1));
		int max =Integer.parseInt(lvl.substring(1,2));
		//先判断是否有足够的格子
		if ((skills==null||skills.equals(""))&&skillsum>0){		
			skills=skillname+"="+(GameServer.random.nextInt(max-min+1)+min);
			return null;
		}else if(skills!=null&&!skills.equals("")&&skillsum>skills.split("\\|").length) {
			String[] v = skills.split("\\|");
			int sum=(GameServer.random.nextInt(max-min+1)+min);
			skillname=skillname+"="+sum;	
			for (int i = 0; i < v.length; i++) {
				if (v[i].equals(skillname)) {
					return "学习技能失败,技能重复";
				}
			}
			skills=skills+"|"+skillname;
			return null;
		}
		return "学习技能失败,技能格子已满";
	}
	public String getBaoname() {
		return baoname;
	}
	public void setBaoname(String baoname) {
		this.baoname = baoname;
	}
	public String getGethard() {
		return gethard;
	}
	public void setGethard(String gethard) {
		this.gethard = gethard;
	}
	public String getBaotype() {
		return baotype;
	}
	public void setBaotype(String baotype) {
		this.baotype = baotype;
	}
	public Integer getBaoactive() {
		return baoactive;
	}
	public void setBaoactive(Integer baoactive) {
		this.baoactive = baoactive;
	}
	public String getBaospeed() {
		return baospeed;
	}
	public void setBaospeed(String baospeed) {
		this.baospeed = baospeed;
	}
	public String getBaoreply() {
		return baoreply;
	}
	public void setBaoreply(String baoreply) {
		this.baoreply = baoreply;
	}
	public String getBaoshot() {
		return baoshot;
	}
	public void setBaoshot(String baoshot) {
		this.baoshot = baoshot;
	}
	public String getBaoap() {
		return baoap;
	}
	public void setBaoap(String baoap) {
		this.baoap = baoap;
	}
	public String getResistshot() {
		return resistshot;
	}
	public void setResistshot(String resistshot) {
		this.resistshot = resistshot;
	}
	public String getAssistance() {
		return assistance;
	}
	public void setAssistance(String assistance) {
		this.assistance = assistance;
	}
	public String getGoodskill() {
		return goodskill;
	}
	public void setGoodskill(String goodskill) {
		this.goodskill = goodskill;
	}
	public BigDecimal getRoleid() {
		return roleid;
	}
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	public BigDecimal getBaoid() {
		return baoid;
	}
	public void setBaoid(BigDecimal baoid) {
		this.baoid = baoid;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getFushis() {
		return fushis;
	}
	public void setFushis(String fushis) {
		this.fushis = fushis;
	}
	public BigDecimal getLingbaolvl() {
		return lingbaolvl;
	}
	public void setLingbaolvl(BigDecimal lingbaolvl) {
		this.lingbaolvl = lingbaolvl;
	}
	public BigDecimal getLingbaoexe() {
		return lingbaoexe;
	}
	public void setLingbaoexe(BigDecimal lingbaoexe) {
		this.lingbaoexe = lingbaoexe;
	}
	public long getLingbaoqihe() {
		return lingbaoqihe;
	}
	public void setLingbaoqihe(long lingbaoqihe) {
		this.lingbaoqihe = lingbaoqihe;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getKangxing() {
		return kangxing;
	}
	public void setKangxing(String kangxing) {
		this.kangxing = kangxing;
	}
	public int getEquipment() {
		return equipment;
	}
	public void setEquipment(int equipment) {
		this.equipment = equipment;
	}
	public String getBaoquality() {
		return baoquality;
	}
	public void setBaoquality(String baoquality) {
		this.baoquality = baoquality;
	}
	public String getTianfuskill() {
		return tianfuskill;
	}
	public void setTianfuskill(String tianfuskill) {
		this.tianfuskill = tianfuskill;
	}
	public Integer getSkillsum() {
		return skillsum;
	}
	public void setSkillsum(Integer skillsum) {
		this.skillsum = skillsum;
	}
	public Integer getFusum() {
		return fusum;
	}
	public void setFusum(Integer fusum) {
		this.fusum = fusum;
	}
	@Override
	public Lingbao clone(){
		try {
			return (Lingbao) super.clone();		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
