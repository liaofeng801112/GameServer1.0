package come.tool.FightingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.come.bean.PathPoint;
import org.come.entity.Record;
import org.come.entity.StarPalace;
import org.come.handler.MainServerHandler;
import org.come.model.PalData;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Battle.BattleData;
import come.tool.Battle.BattleMixDeal;
import come.tool.Calculation.BaseQl;
import come.tool.Calculation.BaseStar;
import come.tool.FightingDataAction.DataActionType;
import come.tool.FightingSpellAction.SpellActionType;
import come.tool.Good.DropModel;

/**战场*/
public class Battlefield {
	public static Random random=new Random();
	public Battlefield(BattleData battleData,int BattleType) {
		super();
		this.battleData = battleData;
		this.BattleType = BattleType;
		this.fightingdata=new ArrayList<>();
		this.Events=new ArrayList<>();
		this.baohu=new ArrayList<>();
		this.erwai=new ArrayList<>();
		this.huayu=new ArrayList<>();
		this.recordList=new ArrayList<>();
		this.JLSkills=new HashMap<>();
		this.waitList=new ArrayList<>();
		this.chaosSp=300;
	}
	//初始化战斗参数
	public void init(BaseStar star1,BaseStar star2){
		StarPalace palace1=null,palace2=null;
		if (star1!=null) {palace1=GameServer.getStarPalace(star1.getVs()[1]);}
		if (star2!=null) {palace2=GameServer.getStarPalace(star2.getVs()[1]);}
		//义薄云天  化无  悬刃 遗患 讨命 明察秋毫 双管齐下 牵制
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			if (data.getType()==3||data.getType()==4) {
				continue;
			}
			if (data.getType()==0) {
				if (data.getCamp()==1) {
					if (star1!=null&&palace1!=null) {
						if (data.getMan()==star1.getMan()) {
							addBuff(new GroupBuff(getBuffid(),"星阵", data,BattleMixDeal.getID(star1.getVs()[1])));
						}
						addXK_SX(star1, palace1.getVs(), data);
					}
				}else if (star2!=null&&palace2!=null) {
					if (data.getMan()==star2.getMan()) {
						addBuff(new GroupBuff(getBuffid(),"星阵", data, BattleMixDeal.getID(star2.getVs()[1])));
					}
					addXK_SX(star2, palace2.getVs(), data);
				}			
			}
			for (int j =  data.getSkills().size()-1; j >=0; j--) {
				FightingSkill skill=data.getSkills().get(j);
				int id=skill.getSkillid();
				if (id>=1041&&id<=1065) {
					if (skill.getSkillhurt()>42000) {skill.setSkillhurt(42000);}
				}else if (id>=1066&&id<=1070) {
					if (skill.getSkillhurt()>20000) {skill.setSkillhurt(20000);}
				}else if ((id>=1081&&id<=1090)||(id>=1096&&id<=1100)) {
                    if (skill.getSkillhurt()>65) {skill.setSkillhurt(65);}
				}else if (skill.getSkilltype().equals(TypeUtil.YBYT)||skill.getSkilltype().equals(TypeUtil.BB_XLHC)) {
					addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
				}else if (PK_MixDeal.isPK(BattleType)) {
					if (skill.getSkilltype().equals(TypeUtil.BB_HW)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
						data.getSkills().remove(j);
					}else if (skill.getSkilltype().equals(TypeUtil.BB_XR)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
						data.getSkills().remove(j);
					}else if (skill.getSkilltype().equals(TypeUtil.BB_YH)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
						data.getSkills().remove(j);
					}else if (skill.getSkilltype().equals(TypeUtil.BB_TM)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
					}else if (skill.getSkilltype().equals("妙手空空")&&isV(30)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
					}else if (skill.getSkilltype().equals(TypeUtil.BB_MCQH)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
					}else if (skill.getSkilltype().equals(TypeUtil.BB_SGQX)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
					}else if (skill.getSkilltype().equals(TypeUtil.BB_QZ)) {
						addBuff(new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillhurt()));
					}else if (skill.getSkilltype().equals(TypeUtil.TY_JS_JTSB)) {
						int sp=(int) skill.getSkillhurt();
						if (chaosSp>sp) {chaosSp=sp;}
						data.getSkills().remove(j);
					}else if (skill.getSkilltype().equals(TypeUtil.TY_JS_YZFS)) {
						data.setSp((int)(data.getSp2()+skill.getSkillhurt()));
						data.getSkills().remove(j);
					}
//				}else if (id==8054) {//8054	法术波动  穷奇的法术秒10设置
//					data.getSkills().remove(j);
//					for (int k =  data.getSkills().size()-1; k >=0; k--) {
//						FightingSkill skillTwo=data.getSkills().get(k);
//						if (skillTwo.getSkillid()>1000&&skillTwo.getSkillid()<=1100) {
//							skillTwo.setSkillsum(5);
//						}
//					}
				}else if (id==8054) {//8054	法术波动  穷奇的法术秒10设置
					data.getSkills().remove(j);
					for (int k =  data.getSkills().size()-1; k >=0; k--) {
						FightingSkill skillTwo=data.getSkills().get(k);
						if (skillTwo.getSkillid()>1000&&skillTwo.getSkillid()<=1100) {
							skillTwo.setSkillsum(10);
						}
					}
				}else {
					if (skill.getSkilltype().equals(TypeUtil.TY_SSC_MCQT)) {
						data.getSkills().remove(j);
					}
				}
			}
		}
	}
	/**添加星阵属性*/
	public void addXK_SX(BaseStar star,BaseQl[] vs,ManData data){
		for (int i = 0; i <vs.length; i++) {
			if (vs[i].getKey().equals("经验加成")) {
				data.setExpXS(data.getExpXS()+(vs[i].getValue()*star.getXs()/100D));
			} else {
				GetqualityUntil.AddR(data.getQuality(), vs[i].getKey(), vs[i].getValue()*star.getXs());
			}
		}
		int path=Battlefield.FXFightingpath(data.getMan());
//		星阵属性 星阵属性=星阵名称=星阵的五行属性=宫位属性星卡 
		if (star.getVs().length>path+3) {
			StarPalace palace=GameServer.getStarPalace(star.getVs()[path+3]);
			if (palace!=null) {
				for (int i = 0; i <palace.getVs().length; i++) {
		            if (palace.getVs()[i].getKey().equals("经验加成")) {
		            	data.setExpXS(data.getExpXS()+(palace.getVs()[i].getValue()*star.getXs()/100D));
					}else {
						GetqualityUntil.AddR(data.getQuality(), palace.getVs()[i].getKey(), palace.getVs()[i].getValue()*star.getXs());
					}
				}
			} 
		}
		if (star.getVs()[1].equals("金牛")||star.getVs()[1].equals("苍狼")) {
			for (int i = 0; i < data.getPets().size(); i++) {
				FightingSummon bbData=data.getPets().get(i);
				bbData.setStar(vs,star.getXs());
			}
		}
	}
	/**水陆属性修正*/
	public void sldh(){
		int max=0;
		for (int i=0,length=fightingdata.size();i<length;i++) {
			ManData manData=fightingdata.get(i);
			if (manData.getType()==0&&max<manData.getlvl()) {
				max=manData.getlvl();
			}
		}
        for (int i=0,length=fightingdata.size();i<length;i++) {
        	ManData manData=fightingdata.get(i);
			if (manData.getType()==0&&max>manData.getlvl()) {
				int cha=(max-manData.getlvl());
				if (cha>50) {cha=50;}
				GetqualityUntil.AddR(manData.getQuality(),"伤害减免",0.2*cha);
				GetqualityUntil.AddR(manData.getQuality(),"伤害加深",0.3*cha);
			}
		}
	}
	public DropModel dropModel;
	public int DropXS;//默认0 奖励系数
	public int ndXS  ;//默认0 难度系数 
	//当前回合
	public int CurrentRound;
	/**主怪名*/
	public String yename;
    /**回合开始前指令数*/
	public int elsum;
	public BattleData battleData;
	//1阵营死亡人数
	public int MyDeath;
	//其他阵营死亡人数
	public int NoDeath;
	//乱敏区间 
	public int chaosSp;
	//雾里看花处理
	public boolean isWLKH;
	//火上浇油(火种)处理
	public boolean isHSJY;
	//死亡抗性添加处理
	public PathPoint bbDeathPoint;//当前回合宝宝死亡记录
	//存处于战斗中的数据
	public List<ManData> fightingdata;
	//战斗类型 
	public int BattleType; 
	//存放当前回合的战斗事件
	public List<FightingEvents> Events;
	public List<FightingEvents> baohu;//保护指令
	public List<FightingEvents> erwai;//额外指令
	public List<FightingEvents> NewEvents;
	public List<ManData> waitList;//还未出手的单位
	/**记录当前回合释放的法术*/
	public Map<Integer,Integer> JLSkills;
	//存放化羽死亡单位//第一个死亡单位阵营第二个伤害值
	public List<PathPoint> huayu;
	public List<String> recordList;
	private int buffid=0;//群体buff
	public int getBuffid() {return ++buffid;}
	public List<GroupBuff> buffs=new ArrayList<>();
	public boolean addBuff(GroupBuff buff){
		//判断是全场唯一 还是阵营唯一
		boolean b=true;
		if (buff.getBuffType().equals(TypeUtil.BB_SGQX)||buff.getBuffType().equals(TypeUtil.BB_TM)) {
			b=false;//全场唯一
		}
		for (int i = buffs.size()-1; i >=0; i--) {
			GroupBuff buff3=buffs.get(i);
			if (b&&buff.getCamp()!=buff3.getCamp()) {
				continue;
			}
			if (buff3.getBuffType().equals(buff.getBuffType())) {
				if (buff.getBuffType().equals(TypeUtil.YBYT)) {
					if (buff3.getValue()<buff.getValue()) {
						buff3.setData(buff.getData());
						buff3.setValue(buff.getValue());	
					}			
				}else if (buff.getBuffType().equals(TypeUtil.BB_E_HYMB)) {
					buff3.setValue(buff.getValue());
					buff3.setValue2(buff.getValue2());
				}
				return false;
			}
		}
		buffs.add(buff);
		return true;
	}
	//获取阵营的buff
	public GroupBuff getBuff(int camp,String type){
		//双管齐下 讨命 无视阵营
		boolean b=true;
		if (type.equals(TypeUtil.BB_SGQX)||type.equals(TypeUtil.BB_TM)) {
		   b=false;//全场唯一
		}
		for (int i = buffs.size()-1; i >=0; i--) {
			GroupBuff buff=buffs.get(i);
			if (b&&camp!=buff.getCamp()) {
				continue;
			}
			if (buff.getBuffType().equals(type)) {
				return buff;
			}	
		}
		return null;
	}
	public GroupBuff getNoCampBuff(int camp,String type){
		//全场唯一
		boolean b=!(type.equals(TypeUtil.BB_SGQX)||type.equals(TypeUtil.BB_TM));
		for (int i = buffs.size()-1; i >=0; i--) {
			GroupBuff buff=buffs.get(i);
			if (b&&camp==buff.getCamp()) {
				continue;
			}
			if (buff.getBuffType().equals(type)) {
				return buff;
			}	
		}
		return null;
	}
	//清除buff
	public List<GroupBuff> ClearBuff(ManData data){
		List<GroupBuff> list=null;
		for (int i = buffs.size()-1; i >=0; i--) {
			GroupBuff buff=buffs.get(i);
			if (data==buff.getData()) {
			    if (buff.getBuffType().equals(TypeUtil.BB_HW)) {
					continue;
				}
			    if (buff.getBuffType().equals(TypeUtil.BB_SGQX)) {
					continue;
				}
			    if (list==null) {
			    	list=new ArrayList<>();
				}
			    list.add(buff);
			    buffs.remove(i);
			}
		}
		return list;
    }
	/**清除单个buff*/
	public List<GroupBuff> ClearBuff(GroupBuff groupBuff){
		if (buffs.remove(groupBuff)) {
			List<GroupBuff> list=new ArrayList<>();
			list.add(groupBuff);
			return list;	
		}
		return null;
	}
	//判断是否有摸个类型的buff添加
	public GroupBuff addbuff(int camp,String type){
		//双管齐下 讨命 全阵营选出一个
		boolean b=true;
		if (type.equals(TypeUtil.BB_SGQX)||type.equals(TypeUtil.BB_TM)) {
			b=false;//全场唯一
		}
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			if (data.getStates()!=0||data.getType()!=1) {
				continue;
			}
			if (b&&camp!=data.getCamp()) {
				continue;
			}
			FightingSkill skill=data.getSkillType(type);
			if (skill!=null) {
				GroupBuff buff=new GroupBuff(getBuffid(), type, data, skill.getSkillhurt());
				if (addBuff(buff)) {
				    return buff;
				}
			    return null;
			}
		}
		return null;	
	}
	//单个添加
	public List<GroupBuff> addBuff(ManData data){
		List<GroupBuff> addbuffs=null;
		for (int j =  data.getSkills().size()-1; j >=0; j--) {
			FightingSkill fightingSkill=data.getSkills().get(j);
			GroupBuff buff=null;
			if (fightingSkill.getSkilltype().equals(TypeUtil.YBYT)||fightingSkill.getSkilltype().equals(TypeUtil.BB_XLHC)) {
				buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
			}else if (PK_MixDeal.isPK(BattleType)) {
				if (fightingSkill.getSkilltype().equals(TypeUtil.BB_HW)) {
					buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
					data.getSkills().remove(j);
				}else if (fightingSkill.getSkilltype().equals(TypeUtil.BB_XR)) {
					buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
					data.getSkills().remove(j);
				}else if (fightingSkill.getSkilltype().equals(TypeUtil.BB_YH)) {
					buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
					data.getSkills().remove(j);
				}else if (fightingSkill.getSkilltype().equals(TypeUtil.BB_TM)) {
					buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
				}else if (fightingSkill.getSkilltype().equals(TypeUtil.BB_MCQH)) {
					buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
				}else if (fightingSkill.getSkilltype().equals(TypeUtil.BB_SGQX)) {
					buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
				}else if (fightingSkill.getSkilltype().equals("妙手空空")&&isV(30)) {
					buff=new GroupBuff(getBuffid(),fightingSkill.getSkilltype(), data, fightingSkill.getSkillhurt());
				}
			}
			if (buff!=null) {
				if (addBuff(buff)) {
					if (addbuffs==null) {
						addbuffs=new ArrayList<>();
					}
					addbuffs.add(buff);
				}
			}
		}
		return addbuffs;
	}
	public List<GroupBuff> addBuff(ManData data,FightingSkill skill){
		List<GroupBuff> addbuffs=null;
		GroupBuff buff=new GroupBuff(getBuffid(),skill.getSkilltype(), data, skill.getSkillgain());
		if (skill.getSkillid()==1313) {buff.setValue2(3);}
		else if (skill.getSkillid()==1234) {buff.setValue2(skill.getSkillcontinued());}
		else if (skill.getSkillid()==23009) {buff.setValue2(skill.getSkillcontinued());}
		if (addBuff(buff)) {
			if (addbuffs==null) {addbuffs=new ArrayList<>();}
			addbuffs.add(buff);
		}
		return addbuffs;	
	}	
	/**buff反转交替*/
	public void reverseBuff(ManData data,FightingState fightingState){
		//化无，明察秋毫，噤若寒蝉，悬刃， 遗患
		StringBuffer buffer=null;
		for (int i = buffs.size()-1; i >=0; i--) {
			GroupBuff buff=buffs.get(i);
			if (buff.getCamp()!=data.getCamp()) {
				String type=buff.getBuffType();
				if (type.equals(TypeUtil.BB_HW)||type.equals(TypeUtil.BB_MCQH)||
					type.equals(TypeUtil.BB_XLHC)||type.equals(TypeUtil.BB_XR)||
					type.equals(TypeUtil.BB_YH)) {
					if (buffer==null) { buffer=new StringBuffer(); }
					//先删除
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append("1=");buffer.append(buff.getBuffId());
					buffer.append("="); buffer.append(buff.getCamp());
					buffer.append("="); buffer.append(buff.getBuffType());
					if (getBuff(data.getCamp(), type)==null) {
						buff.setCamp(data.getCamp());
						buffer.append("|0=");buffer.append(buff.getBuffId());
						buffer.append("=");  buffer.append(buff.getCamp());
						buffer.append("=");  buffer.append(buff.getBuffType());
					}else {
						buffs.remove(i);
					}
				}
			}
		}
		if (buffer!=null) {
			fightingState.setBuff(buffer.toString());	
		}
	}
	/**接收战斗回合事件*/
	public  List<FightingEvents> ReceiveEvent(){		
		//正式处理战斗数据
		try {		
			elsum=0;
			CurrentRound=battleData.getRound();
			baohu.clear();
			erwai.clear();
			huayu.clear();
			JLSkills.clear();
			recordList.clear();
			if (NewEvents==null){NewEvents=new ArrayList<>();}
			else{NewEvents.clear();} 
			Events=battleData.getPolicyMap().get(CurrentRound);
			waitSell();
			RoundDeal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WriteOut.addtxt("战斗报错:"+MainServerHandler.getErrorMessage(e),9999);
		}
		return NewEvents;
	}
	/**
	 * 正式处理回合数据
	 * @throws InterruptedException 
	 */
	public void RoundDeal() throws InterruptedException{
		//判断人物是否存在回蓝符回血符
		GroupBuff XZBuff1=getBuff(1,       "星阵");
		GroupBuff XZBuff2=getBuff(nomy(1), "星阵");
		List<FightingState> BT=null;
		boolean isS=false;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			if (data.getStates()==1) {isS=true;}
			if (data.getStates()!=0||data.getType()==3||data.getType()==4){
				 continue;
			}
			BT=Rid(data,BT);
			if (XZBuff1!=null&&data.getCamp()==1) {
				if (BT==null) {BT=new ArrayList<>();}
				FightingState fightingState=new FightingState();
				data.getFightingState(fightingState);
				fightingState.setSkillskin((int)XZBuff1.getValue()+"");
				BT.add(fightingState);
				XZBuff1=null;
			}else if (XZBuff2!=null&&data.getCamp()!=1) {
				if (BT==null) {BT=new ArrayList<>();}
				FightingState fightingState=new FightingState();
				data.getFightingState(fightingState);
				fightingState.setSkillskin((int)XZBuff2.getValue()+"");
				BT.add(fightingState);
				XZBuff2=null;
			}
		}
		if (BT!=null) {
			FightingEvents ksevents=new FightingEvents();
			ksevents.setCurrentRound(CurrentRound);
			ksevents.setAccepterlist(BT);
			NewEvents.add(ksevents);
		}
		if (isS) {
			//扶伤处理
			List<FightingState> FS=null;
			for (int i = fightingdata.size()-1; i >=0; i--) {
				ManData data=fightingdata.get(i);
				if (data.getStates()!=0||data.getType()==3||data.getType()==4||data.xzstate(TypeUtil.FY)!=null){
					 continue;
				}
				FightingSkill skill=data.getSkillType(TypeUtil.BB_FS);
				if (skill==null||skill.getSkillhitrate()<random.nextInt(100)) {continue;}
				List<ManData> datas=MixDeal.get(false,null,2,nomy(data.getCamp()), 1, 0, 1, 0, 1, -1, this, 1);
				if (datas.size()==0) {continue;}
				data=datas.get(0);
				if (FS==null) {FS=new ArrayList<>();}
				FightingState fs2=new FightingState();
				fs2.setStartState(TypeUtil.JN);
				fs2.setSkillskin(skill.getSkilltype());
				ChangeFighting fighting=new ChangeFighting();
				fighting.setChangehp((int)(data.getHp_z()*0.5));
				FightingPackage.ChangeProcess(fighting, null, data, fs2, TypeUtil.JN, FS, this);
			}
			if (FS!=null) {
				FightingEvents ksevents=new FightingEvents();
				ksevents.setCurrentRound(CurrentRound);
				ksevents.setAccepterlist(FS);
				NewEvents.add(ksevents);
			}
		}
		int[] yao=new int[4];
		List<FightingState> acStates=null;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			if (data.getStates()!=0||data.getType()==3||data.getType()==4){
				 continue;
			}
			AddState addState=null;
			if (data.getType()==0) {
				addState=data.xzstate("回蓝");
				FightingState fightingState=new FightingState();
				if (acStates==null) acStates=new ArrayList<>();
				acStates.add(fightingState);
				fightingState.setCamp(data.getCamp());
				fightingState.setMan(data.getMan());
				fightingState.setStartState("代价");
				data.addnq(10, fightingState);
				if (addState!=null) {
					fightingState.setStartState(TypeUtil.JN);
					yao[1]=(int) addState.getStateEffect();
					ChangeFighting changeFighting=Typeyao(data,yao);		
					data.ChangeData(changeFighting,acStates.get(acStates.size()-1));		
					yao[1]=0;
				}
				if (data.getSkillType(TypeUtil.TJ_SY)!=null) {
			        data.addyq(20,fightingState);
				}
			}
			addState=data.xzstate(TypeUtil.FB_BGZ);
			if (addState!=null) {
				if (acStates==null) acStates=new ArrayList<>();
				FightingState fightingState=new FightingState();
				fightingState.setStartState(TypeUtil.JN);
				acStates.add(fightingState);
				ChangeFighting changeFighting=new ChangeFighting();	
				changeFighting.setChangemp((int)-addState.getStateEffect());
				data.ChangeData(changeFighting, acStates.get(acStates.size()-1));								
			}
			FightingSkill skill=data.getSkillType(TypeUtil.BB_LSDC);
			if (skill!=null) {
				int hmp=(int)(data.getMp_z()*skill.getSkillhurt()/100);
				if (hmp!=0) {
					if (acStates==null) acStates=new ArrayList<>();
					FightingState fightingState=new FightingState();
					fightingState.setStartState("代价");
					acStates.add(fightingState);
					ChangeFighting changeFighting=new ChangeFighting();	
					changeFighting.setChangemp(hmp);
					data.ChangeData(changeFighting, fightingState);		
				}
			}
		}
		if (acStates!=null) {
			FightingEvents ksevents=new FightingEvents();
			ksevents.setCurrentRound(CurrentRound);
			ksevents.setAccepterlist(acStates);
			NewEvents.add(ksevents);
		}
		List<FightingState> xieStates=new ArrayList<>();
	    //处理数据前先判断是否存在回合开始被动技能 身上没有不良状态
		for (int i = 0; i < fightingdata.size(); i++) {	
			ManData manData=fightingdata.get(i);
			manData.clearBuff();
			if (manData.getType()==4||manData.getType()==3){continue;}
			if (manData.getStates()==0){
				if (manData.getType()==1&&manData.getMan()>=5&&manData.getMan()<10) {
					FightingSkill sk1=manData.getSkillType("忠诚");
					FightingSkill sk2=manData.getSkillType(TypeUtil.BB_LY);
					if (sk1!=null||sk2!=null) {
						int path=Datapathhuo(manData.getCamp(),manData.getMan()-5);
						if (path!=-1) {
							ManData data=fightingdata.get(path);
							if (sk1!=null) {
								FightingState fightingState=new FightingState();
								fightingState.setStartState(TypeUtil.JN);
								fightingState.setSkillskin("1804");
								xieStates.add(fightingState);
								yao[2]=5;
								ChangeFighting changeFighting=Typeyao(data,yao);	
								data.ChangeData(changeFighting, fightingState);		
								yao[2]=0;
							}
							if (sk2!=null) {
								FightingState fightingState=new FightingState();
								fightingState.setStartState(TypeUtil.JN);
								fightingState.setSkillskin(sk2.getSkilltype());
								xieStates.add(fightingState);
								data.addyq(20,fightingState);	
							}
						}
					}						
				}
				FightingSkill DY=manData.getSkillType(TypeUtil.BB_DY);
				if (DY!=null&&isV(DY.getSkillhitrate())) {
					List<ManData> datas=MixDeal.get(false, null, 0, nomy(manData.getCamp()), 1, 0,1, 0, 1, 3, this, 1);
			        if (datas.size()!=0) {
					    ManData data=datas.get(0);
					    if (data.getvalue(3)<0.3) {
					     	FightingState fightingState=new FightingState();
							fightingState.setStartState(TypeUtil.JN);
							fightingState.setSkillskin(DY.getSkilltype());	
							yao[3]=50;
							ChangeFighting changeFighting=Typeyao(data,yao);	
							data.ChangeData(changeFighting,fightingState);		
							yao[3]=0;
							xieStates.add(fightingState);
						}  
					}		
				}
//				9325|烟火人间|在销声匿迹护盾存在期间,每个攻击自己的目标都会受到灼烧,处于该状态下的目标每回合减少气血上限的（1%*等级）持续两回合,不可加。(仅在与PC之间战斗有效。
				DY=manData.getAppendSkill(9325);
				if (DY!=null) {
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					ChangeFighting changeFighting=new ChangeFighting();	
					changeFighting.setChangehp(-(int)(manData.getHp_z()*DY.getSkillhurt()/100.0));
					manData.ChangeData(changeFighting,fightingState);
					xieStates.add(fightingState);
				}
//				1324	波澜不惊	每回合开始时若自身水五行大于50，则有{公式九}%会受到水五行的庇护；自身抗强力克水增加{公式一}%，抗水法狂暴几率增加{公式四}%，持续1回合。
				DY=manData.getSkillType(TypeUtil.BB_E_BLBJ);
				if (DY!=null&&manData.getQuality().getRolewxs()>=50&&isV(DY.getSkillgain()/2)) {
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					ChangeFighting changeFighting=new ChangeFighting();	
					changeFighting.setChangetype(DY.getSkilltype());
					changeFighting.setChangevlaue(DY.getSkillgain());
					changeFighting.setChangevlaue2(DY.getSkillgain()*0.3);
					changeFighting.setChangesum(1);
					manData.ChangeData(changeFighting,fightingState);
					fightingState.setText("波澜不惊#2");
					xieStates.add(fightingState);
				}
				if (manData.getSkillType("自医")!=null) {
		        	if (manData.getvalue(1)<0.3&&random.nextInt(100)<30) {
						FightingState fightingState=new FightingState();
						fightingState.setStartState(TypeUtil.JN);
						fightingState.setSkillskin("1805");
						yao[2]=50;
						ChangeFighting changeFighting=Typeyao(manData,yao);	
						manData.ChangeData(changeFighting,fightingState);		
						yao[2]=0;
						xieStates.add(fightingState);	
					}
				}
				FightingSkill skill=manData.getSkillId(23005);
				if (skill!=null&&isV(30)) {
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					ChangeFighting changeFighting=new ChangeFighting();
					changeFighting.setChangetype("清除异常状态");
					manData.ChangeData(changeFighting, fightingState);
					fightingState.setText("神清气正#2");
					xieStates.add(fightingState);
				}
				skill=manData.getSkillId(23007);
				int wei = Datapathhuo(manData.getCamp(), manData.getMan() + 5);
				if (skill!=null&&wei==-1&&isV(30)) {
					FightingState fightingState=new FightingState();
					ChangeFighting changeFighting=new ChangeFighting();
					fightingState.setStartState(TypeUtil.JN);
					changeFighting.setChangetype("知耻后勇");
					changeFighting.setChangesum(1);
					manData.ChangeData(changeFighting, fightingState);
					fightingState.setText("知耻后勇#2");
					xieStates.add(fightingState);
				}
				AddState addState=manData.xzstate(TypeUtil.ZD);
				if (addState!=null) {
					//判断是否有厚积薄发
					AddState HJBF=manData.xzstate(TypeUtil.TZ_HJBF);				
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					fightingState.setCamp(manData.getCamp());
					fightingState.setMan(manData.getMan());	
					ChangeFighting changeFighting=TypeHurtTotal(addState.getStateEffect());
					if (HJBF!=null) {
						changeFighting.setChangehp(changeFighting.getChangehp()*addState.getSurplus());
					}
					FightingPackage.ChangeProcess(changeFighting, null, manData, fightingState,"中毒", xieStates, this);
					if (HJBF!=null) {
						fightingState.setEndState_2(addState.getStatename());
						fightingState.setEndState_2(TypeUtil.TZ_HJBF);
						manData.getAddStates().remove(addState);
						manData.getAddStates().remove(HJBF);				
					}
		       	}
				addState=manData.xzstate(TypeUtil.TY_ZD_AHBY);
				if (addState!=null&&addState.getSurplus()<=1) {
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					fightingState.setCamp(manData.getCamp());
					fightingState.setMan(manData.getMan());	
					ChangeFighting changeFighting=TypeHurtTotal(addState.getStateEffect());
					FightingPackage.ChangeProcess(changeFighting,null,manData,fightingState,"中毒",xieStates,this);
				}
				addState=manData.xzstate(TypeUtil.TY_H_ZSMH_S);
				if (addState!=null) {
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					fightingState.setCamp(manData.getCamp());
					fightingState.setMan(manData.getMan());	
					ChangeFighting changeFighting=new ChangeFighting();
//	            	//护盾存在期间每个攻击自己的敌方目标都会受到灼烧,处于该状态下的目标下回合开始前随机受到自已气血上限（0.5%*等级）~（1.5%*等级）的伤害,护盾最多持续3回合。(仅在与玩家之间战斗有效
					int hurt=(int) (manData.getHp_z()*addState.getStateEffect()/100D);
					hurt*=1+(random.nextInt(200)/200D);
					changeFighting.setChangehp(-hurt);
					FightingPackage.ChangeProcess(changeFighting,null,manData,fightingState,TypeUtil.TY_H_ZSMH,xieStates,this);	
				}
				addState=manData.xzstate("毒针轻刺");
				if (addState!=null) {
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					fightingState.setCamp(manData.getCamp());
					fightingState.setMan(manData.getMan());	
					ChangeFighting changeFighting=new ChangeFighting();
					changeFighting.setChangehp((int)-addState.getStateEffect());
					FightingPackage.ChangeProcess(changeFighting, null, manData, fightingState,"毒针轻刺", xieStates, this);
		       }
			   if (manData.getSkillType("顾影自怜")!=null) {
					if (MixDeal.isgyzl(manData.getCamp(),this)) {
						FightingState fightingState=new FightingState();
						ChangeFighting changeFighting=new ChangeFighting();
						changeFighting.setChangetype("清除异常状态");
						manData.ChangeData(changeFighting, fightingState);	
						 skill=manData.getSkillType(TypeUtil.BB_E_QHHYZL);
						if (skill!=null&&isV(skill.getSkillgain())&&(skill.getSkillcontinued()==0||CurrentRound>=skill.getSkillcontinued()+5)) {
							skill.setSkillcontinued(CurrentRound);
							changeFighting.setSkills(null);
							changeFighting.setChangetype("隐身");
							changeFighting.setChangesum(1);
							manData.ChangeData(changeFighting, fightingState);	
							fightingState.setText("强化顾影自怜#2");
						}
						fightingState.setStartState("代价");
						xieStates.add(fightingState);							
					}
				}
			    if (manData.getType()==0&&PK_MixDeal.isPK(BattleType)) {
			    	//判断是否有时过境迁
				     skill=manData.getSkillType(TypeUtil.TZ_SGJQ);
				    if (skill!=null&&skill.getSkillhurt()>random.nextInt(100)) {
				    	List<ManData> datas=MixDeal.get(false, null, 1, manData.getCamp(), 1, 1, 1, 0, 1, -1, this, 1);
					    if (datas.size()!=0) {
					    	datas.get(0).addAddState(TypeUtil.TZ_SGJQ,0,0,1);
						}
				    }
				}
			    
			    // 灵犀-回合开始前被动处理
			    if (manData.getType() < 3) {
		    		manData.before(xieStates, null);
				}
			    
			    
			}else if (manData.getStates()==1) {
				//判断是否有复活药
				AddState addState=manData.fhy();
				if (addState!=null) {
					FightingState fightingState=new FightingState();
					fightingState.setStartState(TypeUtil.JN);
					fightingState.setSkillskin("1509");
					fightingState.setCamp(manData.getCamp());
					fightingState.setMan(manData.getMan());	
					fightingState.setText("复活");
					ChangeFighting changeFighting=new ChangeFighting();
					changeFighting.setChangehp((int)(addState.getStateEffect()));
					FightingPackage.ChangeProcess(changeFighting, null, manData, fightingState,"复活", xieStates, this);
				}
			}
		}
		if (xieStates.size()!=0) {
			FightingEvents ksevents=new FightingEvents();
			ksevents.setCurrentRound(CurrentRound);
			ksevents.setAccepterlist(xieStates);
			NewEvents.add(ksevents);
		}
		//初始化指令处理
		if (CurrentRound==1) {DataActionType.getActionById(32).analysisAction(null, null,null, this);}
		
		AI_MixDeal.AI_KS(this);
		PK_MixDeal.PK_ST(this);	
		guilei();//指令归类
		MixDeal.flee(this,1);//忽如一夜判断
		ZLCL();//指令处理
		L_GL();//甘霖回血处理
		XP_YYBJ();
		XP_WYQS();
		MixDeal.HNYG(this);//患难与共判断
		SXBuff();//buff刷新
	    for (int i = fightingdata.size()-1; i >=0; i--){
	    	if(fightingdata.get(i).getType()==1){
	    		fightingdata.remove(i);
	    	}
	    }
	    for (int j = fightingdata.size()-1; j >=0; j--) {
			ManData fighting=fightingdata.get(j);
			if (fighting.getType()!=0) {continue;}
			for (int k = 0; k < fighting.getPets().size(); k++) {
				if (fighting.getPets().get(k).getPlay()==1){
					ManData data=fighting.getPets().get(k).getPet();
					if (data!=null) {
						fightingdata.add(data);
					}
					break;	
				}
			}
		}
	    //清算状态
		List<FightingState> removeStates=new ArrayList<>();
		for (int j = 0; j < fightingdata.size(); j++) {
			ManData manData=fightingdata.get(j);
			if (manData.getStates()==2){continue;}
			struggle(manData,removeStates); 
			FightingState fightingState=manData.endState();
			if (fightingState!=null) {
			    removeStates.add(fightingState);	
			} 
		}
		if (removeStates.size()!=0) {
			FightingEvents ksevents=new FightingEvents();
			ksevents.setCurrentRound(CurrentRound);
			ksevents.setAccepterlist(removeStates);
			NewEvents.add(ksevents);
		}
		List<FightingState> SXStates=null;
		for (int j = 0; j < fightingdata.size(); j++) {
			ManData manData=fightingdata.get(j);
			manData.removeBear();
			if (manData.getStates()==2) continue;
			if (manData.getType()>=2) continue;
			String v=manData.xz2();
			if (v!=null) {
				 if (SXStates==null)SXStates=new ArrayList<>();	
				 FightingState fightingState=new FightingState();
				 fightingState.setCamp(manData.getCamp());
				 fightingState.setMan(manData.getMan());
				 fightingState.setStartState("限制刷新");
				 fightingState.setEndState(v);
				 SXStates.add(fightingState);				
			}	
		}
		if (SXStates!=null) {
			FightingEvents ksevents=new FightingEvents();
			ksevents.setCurrentRound(CurrentRound);
			ksevents.setAccepterlist(SXStates);
			NewEvents.add(ksevents);
		}
	}
	/**甘霖处理*/
	private void L_GL() {
		// TODO Auto-generated method stub
		List<FightingState> Accepterlist=null;
		for (int i = fightingdata.size()-1; i>=0; i--) {
			ManData data=fightingdata.get(i);
			if ((data.getType()==1&&data.getStates()!=0)||data.getHp()==data.getHp_z()||data.getStates()==2) {continue;}
			AddState addState=data.xzstate(TypeUtil.L_GL);
			if (addState==null) {continue;}
			ChangeFighting change=new ChangeFighting();
			FightingState state=new FightingState();
			int states=data.getStates();
			if (states==0) {
				FightingSkill skill=addState.getSkill(9602);
				if (skill!=null&&isV(10)) {
					change.setChangemp((int)(skill.getSkillhurt()));
				}
			}else {
				FightingSkill skill=addState.getSkill(9604);
				if (skill!=null&&isV(20)) {
					change.setChangetype(TypeUtil.TY_L_GL_YMFZ);
					change.setChangevlaue((int)(addState.getStateEffect()*skill.getSkillhurt()/100D));
					change.setChangesum(3);
				}
			}
			change.setChangehp((int)addState.getStateEffect());
			data.ChangeData(change, state);
			state.setStartState(TypeUtil.JN);
			if (states==1) {
				FightingSkill skill=addState.getSkill(9608);
				if (skill==null||!isV(skill.getSkillhurt())) {
					data.RemoveAbnormal(state,TypeUtil.L_GL);		
				}
				skill=addState.getSkill(9605);
				if (skill!=null&&isV(skill.getSkillhurt())) {
					data.addyq(10, state);
				}
				skill=addState.getSkill(9607);
				if (skill!=null&&isV(skill.getSkillhurt())) {
					data.RemoveNegativeState(state);
				}
			}
			if (Accepterlist==null) {Accepterlist=new ArrayList<>();}
			Accepterlist.add(state);
			FightingSkill skill=addState.getSkill(9606);
			if (skill!=null&&data.getvalue(0)<=0.3&&isV(skill.getSkillhurt())) {
				FightingState state1=new FightingState();
				data.ChangeData(change,state1);
				state1.setStartState(TypeUtil.JN);
				Accepterlist.add(state1);
			}
		}
		if (Accepterlist==null) {return;}
		FightingEvents events=new FightingEvents();
		events.setAccepterlist(Accepterlist);
		NewEvents.add(events);	
	}
	private void XP_YYBJ() {
		// TODO Auto-generated method stub
		List<FightingState> Accepterlist=null;
		for (int i = fightingdata.size()-1; i>=0; i--) {
			ManData data=fightingdata.get(i);
			if ((data.getType()==1&&data.getStates()!=0)||data.getMp()==data.getMp_z()||data.getStates()==2) {continue;}
			FightingSkill skill=data.getSkillId(23001);
			if (skill==null) {continue;}
			ChangeFighting change=new ChangeFighting();
			FightingState state=new FightingState();
			int states=data.getStates();
			if (states==0) {
				if (isV(30)) {
					change.setChangemp((int)(10000));
					state.setText("源源不绝#2");
				}
			}
			data.ChangeData(change, state);
			state.setStartState(TypeUtil.JN);

			if (Accepterlist==null) {Accepterlist=new ArrayList<>();}
			Accepterlist.add(state);
		}
		if (Accepterlist==null) {return;}
		FightingEvents events=new FightingEvents();
		events.setAccepterlist(Accepterlist);
		NewEvents.add(events);
	}
	private void XP_WYQS() {
		// TODO Auto-generated method stub
		List<FightingState> Accepterlist=null;
		for (int i = fightingdata.size()-1; i>=0; i--) {
			ManData data=fightingdata.get(i);
			if ((data.getType()==1&&data.getStates()!=0)||data.getMp()==data.getMp_z()||data.getStates()==2) {continue;}
			FightingSkill skill=data.getSkillId(23006);
			if (skill==null) {continue;}
			ChangeFighting change=new ChangeFighting();
			FightingState state=new FightingState();
			int states=data.getStates();
			if (states==0) {
				if (isV(30)) {
					change.setChangetype("隐身");
					change.setChangesum(2);
					state.setText("雾掩青山#2");
				}
			}
			data.ChangeData(change, state);
			state.setStartState(TypeUtil.JN);

			if (Accepterlist==null) {Accepterlist=new ArrayList<>();}
			Accepterlist.add(state);
		}
		if (Accepterlist==null) {return;}
		FightingEvents events=new FightingEvents();
		events.setAccepterlist(Accepterlist);
		NewEvents.add(events);
	}
	/**buff刷新*/
	public void SXBuff(){
		List<GroupBuff> removeBuffs=null;
		//判定双管齐下是否取消
	    GroupBuff buff=getBuff(1, TypeUtil.BB_SGQX);
	    if (buff!=null) {
			if (buff.getData().getStates()!=0) {
				buffs.remove(buff);
				GroupBuff buff2=addbuff(1,TypeUtil.BB_SGQX);
				if (buff2==null) {
					if (removeBuffs==null) {removeBuffs=new ArrayList<>();}
					removeBuffs.add(buff);
				}
			}
		}
	    //判断黑夜蒙蔽
		for (int i = buffs.size()-1;i>=0; i--) {
			buff=buffs.get(i);
			if (buff.getBuffType().equals(TypeUtil.BB_E_HYMB)||buff.getBuffType().equals(TypeUtil.BB_FBSF)||buff.getBuffType().equals("23009")) {
				buff.setValue2(buff.getValue2()-1);
		    	if (buff.getValue2()<=0) {
	    		    buffs.remove(buff);
	    		    if (removeBuffs==null) {removeBuffs=new ArrayList<>();}
	    		    removeBuffs.add(buff);
			    }
			}
		}
	    if (removeBuffs!=null) {//找一个还活着的单位 当替身
		    for (int i = fightingdata.size()-1; i >= 0; i--) {
				ManData data=fightingdata.get(i);
				if (data.getStates()==0) {
					FightingEvents hhe=new FightingEvents();
					List<FightingState> hhs=new ArrayList<>();
					FightingState fs=new FightingState();
					fs.setCamp(data.getCamp());
					fs.setMan(data.getMan());
					fs.setStartState("代价");
					fs.setBuff(MixDeal.getBuffStr(removeBuffs, false));
					hhs.add(fs);
					hhe.setAccepterlist(hhs);
				    NewEvents.add(hhe);
					break;
				}
			}	
		}
	}

	/** 指令处理 */
	public void ZLCL() {
		int size = 0;
		while (waitList.size()!=0||erwai.size()!=0||huayu.size() != 0) {
			size++;
			if (size > 999999) {
				WriteOut.addtxt("战斗循环运算过多", 9999);
				WriteOut.addtxt(1 + ":"+ GsonUtil.getGsonUtil().getgson().toJson(Events), 9999);
				WriteOut.addtxt(2 + ":"+ GsonUtil.getGsonUtil().getgson().toJson(erwai), 9999);
				WriteOut.addtxt(3 + ":"+ GsonUtil.getGsonUtil().getgson().toJson(huayu), 9999);
				break;
			}
			// 先判断战斗是否结束
			if (endFighting(fightingdata.get(0).getCamp()) != -1) {
				break;
			}
			if (huayu.size() != 0) {
				// 化羽归尘处理
				DataActionType.getActionById(19).analysisAction(null, null,null, this);
				huayu.clear();
				MixDeal.flee(this, 0);// 作鸟兽散判断
				continue;
			}
			FightingEvents zl = null;
			if (erwai.size() != 0) {
				zl = erwai.remove(0);
			} else {
				zl = SpBubble();
			}
			if (zl == null) { break; }
			// 出手人发起的类型
			String type = zl.getOriginator().getStartState();
			if (type.equals("防御")||type.equals("保护")) {
				continue;
			} else if (type.equals("复活")) {
				DataActionType.getActionById(17).analysisAction(null, zl,zl.getOriginator().getStartState(), this);
				continue;
			} else if (type.equals(TypeUtil.BB_GQLX)) {  //TODO 灵听归去来兮处理
				int wei = -1;
				for (int i = 0; i < fightingdata.size(); i++) {
					if ( fightingdata.get(i).getCamp()==zl.getOriginator().getCamp()&&
							fightingdata.get(i).getMan()==zl.getOriginator().getMan()) {
						wei = i;
					}
				}
				if (wei != -1) {
					ManData myData = fightingdata.get(wei);
					for (int i = myData.getSkills().size()-1; i >=0; i--) {
						FightingSkill skill2=myData.getSkills().get(i);
						if (skill2.getSkilltype().equals(TypeUtil.BB_GQLX)) {
							DataActionType.getActionById(35).analysisAction(myData, zl,zl.getOriginator().getStartState(), this);
						}
					}
				}
				continue;
			} else if (type.equals("遗产")) {
				DataActionType.getActionById(9).analysisAction(null, zl,zl.getOriginator().getStartState(), this);
				continue;
			} else if (type.equals(TypeUtil.BB_JS)|| type.equals(TypeUtil.BB_DT)) {
				DataActionType.getActionById(10).analysisAction(null, zl,zl.getOriginator().getStartState(), this);
				continue;
			} else if (type.equals(TypeUtil.BB_RHTY)) {
				DataActionType.getActionById(29).analysisAction(null, zl,zl.getOriginator().getStartState(), this);
				continue;
			} else if (type.equals(TypeUtil.BB_YAHY)) {
				DataActionType.getActionById(30).analysisAction(null, zl,zl.getOriginator().getStartState(), this);
				continue;
			} else if (type.equals(TypeUtil.BB_TDTS)) {
				DataActionType.getActionById(31).analysisAction(null, zl,zl.getOriginator().getStartState(), this);
				continue;
			} else if (type.equals(TypeUtil.BB_NZQK)) {
				DataActionType.getActionById(33).analysisAction(null, zl,zl.getOriginator().getStartState(), this);
				continue;
			} else if (type.startsWith("闪现&")) {
				int wei = Datapath(zl.getOriginator().getCamp(), zl.getOriginator().getMan());
				if (wei != -1) {
					ManData bbData = fightingdata.get(wei).bb(type.split("\\&")[1]);
					DataActionType.getActionById(5).analysisAction(bbData, zl,"闪现", this);
				}
				continue;
			}
			ManData myData = null;
			int path = -1;
			if (zl.getOriginator().getMan() < 5) {
				path = Datapath(zl.getOriginator().getCamp(), zl.getOriginator().getMan());
			} else {
				path = Datapathhuo(zl.getOriginator().getCamp(), zl.getOriginator().getMan());
			}
			if (path == -1) {continue;}
			myData = fightingdata.get(path);
			if (myData.getType() == 4) {// 为孩子处理
				
			} else if (myData.getType() == 3) {// 灵宝处理
				DataActionType.getActionById(6).analysisAction(myData, zl,zl.getOriginator().getStartState(), this);
			} else if (type.equals("召回")) {
				int wei = Datapathhuo(myData.getCamp(), myData.getMan() + 5);
				if (wei != -1) {
					MixDeal.ys(myData,false, this);
					myData = fightingdata.get(wei);
					for (int i = myData.getSkills().size()-1; i >=0; i--) {
						FightingSkill skill2=myData.getSkills().get(i);
						if (skill2.getSkilltype().equals(TypeUtil.BB_GQLX)) {  //TODO 灵听归去来兮处理
							DataActionType.getActionById(35).analysisAction(myData, zl,zl.getOriginator().getStartState(), this);
						}
					}
					DataActionType.getActionById(5).analysisAction(myData, zl,type, this);
				}
			} else if (myData.getStates() == 0 && type.startsWith("召唤&")) {
				int wei = Datapathhuo(myData.getCamp(), myData.getMan() + 5);
				if (wei != -1) {
					MixDeal.ys(myData,false, this);
					myData = fightingdata.get(wei);
					for (int i = myData.getSkills().size()-1; i >=0; i--) {
						FightingSkill skill2=myData.getSkills().get(i);
						if (skill2.getSkilltype().equals(TypeUtil.BB_GQLX)) {  //TODO 灵听归去来兮处理
							DataActionType.getActionById(35).analysisAction(myData, zl,zl.getOriginator().getStartState(), this);
						}
					}
				}
				CSGZ(fightingdata.get(path), zl);
			} else if (myData.getStates() != 0) {
				// 死亡处理
			} else if (type.equals("逃跑")) {
				MixDeal.ys(myData,false,this);
				DataActionType.getActionById(3).analysisAction(myData, zl,type, this);
			} else if (BattleType == 4 && type.equals("偷钱")) {
				DataActionType.getActionById(25).analysisAction(myData, zl,type, this);
			} else {
				CSGZ(myData, zl);
			}
			AI_MixDeal.AI_End(this, myData);
			if (isWLKH) {HurtWLKH();}//雾里看花处理
			if (isHSJY) {HurtHSJY();}//火上浇油处理
			if (bbDeathPoint!=null&&(bbDeathPoint.getX()!=0||bbDeathPoint.getY()!=0)) {
				BBDeathPoint();//死亡宝宝加抗性技能处理
			}
			MixDeal.flee(this, 0);//作鸟兽散判断
			
			if (myData.getMan() < 5 && type.equals("技能")) {
				int wei = Datapathhuo(myData.getCamp(), myData.getMan() + 5);
				if (wei == -1) {
					MixDeal.zhszy(this, myData);//召唤兽支援判断
				}
			}
			
		}
		
	}
	/**出手规则*/
	public void CSGZ(ManData myData,FightingEvents zl){
		//出手人发起的类型
		String type =zl.getOriginator().getStartState();
		
		//判断单位身上的状态
		int h = 0,s = 0,q = 0,j = 0,ss = 0,y=0,ht=0,lhfm=0;
        if (myData.getAddStates()!=null && (zl.getOriginator().getEndState() == null || !zl.getOriginator().getEndState().equals("流风回雪"))) {
        	for (int i = myData.getAddStates().size()-1; i >=0; i--) {
        		String Stype=myData.getAddStates().get(i).getStatename();
        		if (h==0&&Stype.equals(TypeUtil.HL)) { h=1;type=TypeUtil.PTGJ; } //混
				else if (s==0&&Stype.equals(TypeUtil.HS)) { s=1; }               //睡 
				else if (q==0&&Stype.equals(TypeUtil.FB_QW)) { q=1; }            //情网
				else if (j==0&&Stype.equals(TypeUtil.FB_JGE)) { j=1; }           //金箍儿
				else if (ss==0&&Stype.equals(TypeUtil.BB_SS)) { ss=1; }          //舍身取义
				else if (y==0&&Stype.equals(TypeUtil.YW)) { y=1; }               //遗忘
				else if (ht==0&&Stype.equals(TypeUtil.TZ_HTSA)) { ht=1; }        //回头是岸
				else if (lhfm==0&&Stype.equals(TypeUtil.BB_LHFM)) { lhfm=1; }    //灵魂封魔	
				else if (Stype.equals(TypeUtil.TY_F_CFWL_S)) {//长风万里给提示结束
					FightingState accepter=new FightingState(myData.getCamp(),myData.getMan(),TypeUtil.JN);
					accepter.setText("我已经被风吹走了");
					zl.setOriginator(null);zl.setAccepterlist(null);
					List<FightingState> Accepterlist=new ArrayList<>();
					Accepterlist.add(accepter);
					zl.setAccepterlist(Accepterlist);
					NewEvents.add(zl);
					return; 
				}else if (Stype.equals(TypeUtil.FY)) { Events.add(zl);return; }   //封印直接结束
        	}	
		}
        if (type.equals(TypeUtil.PTGJ)) {
	    	if (myData.getType()==1) {
				GroupBuff buff=getNoCampBuff(myData.getCamp(), TypeUtil.BB_XLHC);
				if (buff!=null&&recordList.contains(TypeUtil.PTGJ+"_"+myData.getCamp())) {return;}
				recordList.add(TypeUtil.PTGJ+"_"+myData.getCamp());
			}
			if (q==1) { systemMsg(myData,null,0,null);return; }//情网
			
			MixDeal.ys(myData,false,this);
			DataActionType.getActionById(h==1?8:1).analysisAction(myData,zl,type,this);
			return;
		}
        if (type.equals("药")) {
        	if (myData.getType()==1) {
				GroupBuff buff=getNoCampBuff(myData.getCamp(), TypeUtil.BB_XLHC);
				if (buff!=null&&recordList.contains("药_"+myData.getCamp())) {return;}
				recordList.add("药_"+myData.getCamp());
			}
        	if (y==1) { systemMsg(myData,null,1,null);return; }
        	
        	MixDeal.ys(myData,false,this);
    		DataActionType.getActionById(4).analysisAction(myData,zl,type,this);	
        	return;
        }
		if (type.startsWith("召唤&")) {
			String[] vs=type.split("\\&");
			try {
				MixDeal.ys(myData,false, this);
				ManData bb=myData.bb(vs[1]);	
				DataActionType.getActionById(5).analysisAction(bb,zl,vs[0],this);	
			} catch (Exception e) {
				// TODO: handle exception
				StringBuffer buffer=new StringBuffer();
				buffer.append("战斗类型:");
				buffer.append(BattleType);
				buffer.append("人物属性:");
				buffer.append(myData.getType()+"_"+myData.getCamp()+"_"+myData.getMan()+"_"+myData.getId()+"_"+myData.getManname());
				buffer.append("报错代码:"+MainServerHandler.getErrorMessage(e));
				AllServiceUtil.getRecordService().insert(new Record(0, buffer.toString()));
			}
			return;
		}
    	if (type.equals("捕捉")){
    		MixDeal.ys(myData,false, this);
			DataActionType.getActionById(7).analysisAction(myData,zl,type,this);								
    	    return;
    	}
		if (type.equals("防御")||type.equals("保护")){ return; }
		if (s==1) { WriteOut.addtxt("昏睡进入:"+GsonUtil.getGsonUtil().getgson().toJson(zl),9999); return; }
		FightingSkill skill=myData.getSkillName(zl.getOriginator().getEndState());
		if (skill==null) { return; }
		if (ss==1||lhfm==1||(skill.getSkillid()>=1001&&skill.getSkillid()<=1100&&(j==1||ht==1))) { 
			//舍生取义 灵魂封魔 金箍儿 回头是岸
			systemMsg(myData,null,ss==1?2:lhfm==1?3:j==1?4:5,skill);
			return; 
		}
		//9812 平波息浪 施放一个强力沧海横流，同时使沧波状态下的目标在使用5阶和4阶师门法术时有{公式一}%几率减弱为3阶和1阶法术，持续3回合。
        if (skill.getSkillid()>=1001&&skill.getSkillid()<=1100) {
			if (skill.getSkilllvl()==4||skill.getSkilllvl()==5) {
				FightingSkill skill2=myData.getAppendSkill(9812);
				if (skill2!=null&&isV(skill2.getSkillhurt())) {
					skill=myData.skillId(skill.getSkilllvl()==4?skill.getSkillid()-3:skill.getSkillid()-2);
					if (skill==null) {return;}
				}
			}	
		}
        if (myData.getType()==1) {
			GroupBuff buff=getNoCampBuff(myData.getCamp(), TypeUtil.BB_XLHC);
			if (buff!=null&&recordList.contains(skill.getSkilltype()+"_"+myData.getCamp())) {return;}
			recordList.add(skill.getSkilltype()+"_"+myData.getCamp());
		}
        if (!myData.isLicense(skill)) { systemMsg(myData,null,9,skill);return; }
		if (myData.SkillCooling(skill,this)) { return; }
		
		if (PK_MixDeal.isPK(BattleType)) {
			//先遗患 悬刃
			PK_MixDeal.PK_YX(myData,skill,this);
			if (myData.getStates()!=0) { return; }
			if (!myData.isLicense(skill)){ systemMsg(myData,null,9,skill);return; }
			if (PK_MixDeal.PK_HW(myData,skill,this)) {return; }	
		}

		if (PK_MixDeal.isPK(BattleType)) {
			PK_MixDeal.PK_MSKK(myData,skill,this);
			if (myData.getStates()!=0) { return; }
		}
		int id=skill.getSkillid();
		
		//释放师门法术判断是否添加回头是岸
		if (id>=1001&&id<=1100) { TZ_HTSA(myData); }
		
		MixDeal.ys(myData,MixDeal.isTZ_XMST(id),this);
		JLSkills.put(id,myData.getCamp());
		if ((id>=1001&&id<=1020)||(id>=1071&&id<=1075)) {//控制法术
			SpellActionType.getActionById(1).spellAction(myData, skill, zl, this);
		}else if (id>=1041&&id<=1065) {//伤害法术
			SpellActionType.getActionById(11).spellAction(myData, skill, zl, this);
		}else if (id>=1021&&id<=1025) {//震慑
			SpellActionType.getActionById(4).spellAction(myData, skill, zl, this);
		}else if ((id>=1026&&id<=1040)||(id>=1076&&id<=1080)) {//增益法术
			SpellActionType.getActionById(5).spellAction(myData, skill, zl, this);
		}else if (id>=1066&&id<=1070) {//三尸虫法术
			SpellActionType.getActionById(8).spellAction(myData, skill, zl, this);
		}else if ((id>=1081&&id<=1090)||(id>=1096&&id<=1100)) {//霹雳法术
			SpellActionType.getActionById(20).spellAction(myData, skill, zl, this);
		}else if (id>=1091&&id<=1095) {//甘霖法术
			SpellActionType.getActionById(21).spellAction(myData, skill, zl, this);
		}else if (id==3035) {//逆鳞
			SpellActionType.getActionById(22).spellAction(myData, skill, zl, this);
		}else if (id==1230) {//势如破竹
			SpellActionType.getActionById(13).spellAction(myData, skill, zl, this);
		}else if (id==1313||id==1234||id==23009) {//黑夜蒙蔽 or 防不慎防
			SpellActionType.getActionById(17).spellAction(myData, skill, zl, this);
		}else if (id==1333) {//无极
			SpellActionType.getActionById(18).spellAction(myData, skill, zl, this);
		}else if (id==1334) {//横扫四方
			SpellActionType.getActionById(19).spellAction(myData, skill, zl, this);
		}else if (id==1250) {//新增技能 偷梁换柱
			SpellActionType.getActionById(26).spellAction(myData, skill, zl, this);
		}else if (id==1251) {//新增技能 遮天蔽日
			SpellActionType.getActionById(27).spellAction(myData, skill, zl, this);
		}else if (id==1252) {//新增技能 大闹天宫
			SpellActionType.getActionById(28).spellAction(myData, skill, zl, this);
		}else if (id==1254) {//新增技能 万剑归宗
			SpellActionType.getActionById(11).spellAction(myData, skill, zl, this);
		}else if(id==23004) {
			SpellActionType.getActionById(30).spellAction(myData, skill, zl, this);
		}else if (id>=5001&&id<=5015) {//法宝
			DataActionType.getActionById(22).analysisAction(myData,zl,type,this);
		}else if (id>=9000&&id<=9999) {//天演策通用
			if (id==9110) {//知盈处虚
				SpellActionType.getActionById(9).spellAction(myData, skill, zl, this);
			}else if (id==9126) {//生死相许
				SpellActionType.getActionById(3).spellAction(myData, skill, zl, this);
			}else if (id==9209) {//惊才艳艳
				SpellActionType.getActionById(6).spellAction(myData, skill, zl, this);
			}else if (id==9389||id==9262) {//流风回雪 销声匿迹
				SpellActionType.getActionById(10).spellAction(myData, skill, zl, this);
			}else if (id==9412) {//弱水三千
				SpellActionType.getActionById(7).spellAction(myData, skill, zl, this);
			}else if (id==9270) {//疾风迅雷
				SpellActionType.getActionById(12).spellAction(myData, skill, zl, this);
			}else {//强力师门技能的大招
				SpellActionType.getActionById(2).spellAction(myData, skill, zl, this);
			}
		}else if (id>=22000&&id<=23000) {//法门通用
			if (id==22000||id==22012||id==22032||id==22034||id==22026) {//清心静气",,"气吞山河","行气如虹","神龙摆尾
				SpellActionType.getActionById(24).spellAction(myData, skill, zl, this);
			}
			if(id==22006||id==22016||id==22022) {//法门
				//DataActionType.getActionById(37).analysisAction(manData, fightingEvents,skilltype,battlefield);
				DataActionType.getActionById(2).analysisAction(myData,zl,type,this);
			}
			//法门 施加状态 //"凝神一击",利刃加身,无坚不摧 神不守舍 幻影迷踪 兽魂俯首 法魂护体  鱼龙潜跃 失魂落魄
			if(id==22002||id==22004||id==22010||id==22028||id==22030||id==22014||id==22008||id==22018||id==22024) {
				SpellActionType.getActionById(25).spellAction(myData, skill, zl, this);
			}

		}

		else {//通用
			DataActionType.getActionById(2).analysisAction(myData,zl,type,this);
		}
		if (myData.getStates()==0&&id>=1001&&id<=1100) {//9809	月沉碧海	沧波状态下的单位在施放师门法术后有20%几率受到本次施放耗蓝{公式一}%的法力伤害。
			FightingSkill ycbh=myData.getAppendSkill(9809);
			if (ycbh!=null&&isV(20)) {
				FightingState state=new FightingState();
				ChangeFighting change=new ChangeFighting();
				change.setChangemp((int)(-skill.getSkillblue()*ycbh.getSkillhurt()/100D));
				state.setStartState(TypeUtil.JN);
				myData.ChangeData(change, state);
				FightingEvents events=new FightingEvents();
				List<FightingState> Accepterlist=new ArrayList<>();
				Accepterlist.add(state);
				events.setAccepterlist(Accepterlist);
				NewEvents.add(events);
			}
		}
		if (id>=1001&&id<=1100) { XP_GLRX(myData); }
	}
	/**判断给予系统提示*/
	public void systemMsg(ManData data,List<FightingState> list,int type,FightingSkill skill){
		if (data.getType()!=0&&data.getType()!=1) { return; }
        //情网 遗忘 舍生取义 灵魂封魔 金箍儿 回头是岸  遗忘 冷却 化无  法力 怨气  怒气		999  暗渡陈仓
		//0   1   2       3       4     5       6   7   8    9   9    9
		StringBuffer buffer=new StringBuffer(data.getType()==0?"你":("你的召唤兽#R"+data.getManname()+"#Y"));
		if (type == 999) {
			buffer.append("#Y没有携带#G暗渡陈仓#Y内丹，无法释放#G一矢中的");	
		}else if (type==9) {
			if (skill.getSkillid()>=5001&&skill.getSkillid()<=5015) { buffer.append("#R怨气"); }
			else if (skill.getSkillid()>=9000&&skill.getSkillid()<=9999) { buffer.append("#R怒气"); }
			else { buffer.append("#R法力");	 }
			buffer.append("#Y不足,无法施放#G");	
			buffer.append(skill.getSkillname());
		}else {
			buffer.append("处于#R");	
			buffer.append(type==0?"情网":type==2?"舍生取义":type==3?"灵魂封魔":type==4?"金箍儿":type==5?"回头是岸":type==7?"冷却":type==8?"化无":type==11?"偷梁换柱":"遗忘");
			buffer.append("#Y状态,");	
			if (skill!=null) {
				buffer.append("无法施放#G");
				buffer.append(skill.getSkillname());
			}else if (type==1) {
				buffer.append("无法使用#G药品");
			}else if (type==0) {
				buffer.append("无法进行#G物理攻击");
			}else if(type==11) {
				buffer.append("无法进行#G物理攻击，吃药，施法");
			}
		}
		FightingState fightingState=new FightingState(data.getCamp(), data.getMan()<5?data.getMan():(data.getMan()-5), "代价");
		fightingState.setMsg(buffer.toString());
		if (list==null) {
			FightingEvents events=new FightingEvents();
			list=new ArrayList<>();
			list.add(fightingState);
			events.setAccepterlist(list);
			NewEvents.add(events);
			return;
		}
		list.add(fightingState);
	}
	/**判断回头是岸*/
	public void TZ_HTSA(ManData data){
		int camp=data.getCamp();
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data2=fightingdata.get(i);
			if (camp!=data2.getCamp()&&data2.getStates()==0) {
				FightingSkill skill=data2.getSkillType(TypeUtil.TZ_HTSA);
				if (skill!=null&&skill.getSkillhurt()>random.nextInt(100)) {
					data.addAddState(TypeUtil.TZ_HTSA,0,0,2);
					FightingEvents zl=new FightingEvents();
					FightingState accepter=new FightingState();
					accepter.setCamp(data.getCamp());
					accepter.setMan(data.getMan());
					List<FightingState> Accepterlist=new ArrayList<>();
					accepter.setEndState_1(TypeUtil.TZ_HTSA);
					accepter.setSkillskin("6025");
					accepter.setStartState("代价");
					Accepterlist.add(accepter);
					zl.setAccepterlist(Accepterlist);
					NewEvents.add(zl);
					return;
				}
			}
		}
	}
	public void XP_GLRX(ManData data){
		int camp=data.getCamp();
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data2=fightingdata.get(i);
			if (camp==data2.getCamp()&&data2.getStates()==0) {
				FightingSkill skill=data.getSkillType("甘霖瑞雪");
				if (skill!=null&&isV(10)) {
					FightingEvents zl=new FightingEvents();
					FightingState accepter=new FightingState();
					accepter.setCamp(data.getCamp());
					accepter.setMan(data.getMan());
					List<FightingState> Accepterlist=new ArrayList<>();
					accepter.setStartState("药");
					int[] yao=new int[4];
					if(isV(50)) {
						yao[2]=100;
					}else {
						yao[3]=100;
					}
					ChangeFighting changeFighting=Typeyao(data2,yao);
					data2.ChangeData(changeFighting,accepter);
					Accepterlist.add(accepter);
					zl.setAccepterlist(Accepterlist);
					NewEvents.add(zl);
					return;
				}
			}
		}
	}
	/**判断是否为防御*/
	public boolean fypd(int camp,int man){
		for (int i = 0; i < baohu.size(); i++) {
			if (baohu.get(i).getOriginator().getStartState().equals("防御")) {
			FightingState state=baohu.get(i).getOriginator();
			if (state.getCamp()==camp&&state.getMan()==man) {
			return true;
			}	
			}
		}
		return false;
	}
	/**判断是否为保护*/
	public ManData bhpd(int camp,int man){
		for (int i = 0; i < baohu.size(); i++) {
			if (baohu.get(i).getOriginator().getStartState().equals("保护")) {
			FightingState state=baohu.get(i).getAccepterlist().get(0);
			if (state.getCamp()==camp&&state.getMan()==man) {
				int nomypath=Datapathhuo(baohu.get(i).getOriginator().getCamp(),baohu.get(i).getOriginator().getMan());
				if (nomypath!=-1) {
					baohu.remove(i);
					return fightingdata.get(nomypath);
				}	
			}	
			}
		}
		return null;
	}
	/**寻找指令中最近的闪现指令 没有就是0*/
	public int xzsx(int camp,int man){
		for (int i = 0; i < NewEvents.size(); i++) {
			if (NewEvents.get(i).getOriginator()==null||!NewEvents.get(i).getOriginator().getStartState().equals("闪现")) continue;
			if (NewEvents.get(i).getOriginator().getCamp()==camp&&NewEvents.get(i).getOriginator().getMan()==man) {
				return i+1;
			}
		}
		return 0;
	}
	/**中毒*/
	public ChangeFighting TypeHurtTotal(double vlaue){
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangehp((int)(-vlaue));	
		return changeFighting;
	}
	/**
	 * 处理药品类型的事件
	 */
	public ChangeFighting Typeyao(ManData data,int[] yao){
		ChangeFighting changeFighting=new ChangeFighting();
		if (data.xzstate("归墟")!=null)
		return changeFighting;
		//接收人的总生命
		int hp_z=data.getHp_z();
		//接收人的总蓝量
		int mp_z=data.getMp_z();
		int changehp=yao[0]+(yao[2]*hp_z/100);
		int changemp=yao[1]+(yao[3]*mp_z/100);
		changeFighting.setChangehp(changehp);
		changeFighting.setChangemp(changemp);
		return changeFighting;
		
	}
	public ChangeFighting BBNoHurt(ManData myData,ManData nomyData,String type,FightingSkill fightingSkill){
		ChangeFighting changeFighting=new ChangeFighting();
		if (type.equals("减人仙")||type.equals("减魔鬼")) {
			changeFighting.setChangetype(type);
			changeFighting.setChangesum(fightingSkill.getSkillcontinued());
			changeFighting.setChangevlaue(fightingSkill.getSkillhurt());
			return changeFighting;
		}
		if (type.equals(TypeUtil.BB_E_YHSS)) {
			if (Battlefield.isV(fightingSkill.getSkillgain())) {
				changeFighting.setChangetype("清除异常状态");
				changeFighting.setChangehp(nomyData.getHp_z()/2);
				changeFighting.setChangemp(nomyData.getMp_z()/2);	
			}	
			return changeFighting;
		}
		if (nomyData.xzstate(TypeUtil.TZ_MXJX)!=null) {return changeFighting;}
		if (Battlefield.isV(fightingSkill.getSkillhitrate())) {
			changeFighting.setChangetype(type);
			changeFighting.setChangesum(fightingSkill.getSkillcontinued());
			changeFighting.setChangevlaue(fightingSkill.getSkillhurt());
		}
		return changeFighting;
	}	
	/**处理增益类型的事件*/
	public ChangeFighting TypeGain(ManData data,String type,FightingSkill fightingSkill){
		ChangeFighting changeFighting=new ChangeFighting();
		changeFighting.setChangetype(type);
		changeFighting.setChangesum(fightingSkill.getSkillcontinued());
        if (type.equals("庇护")) {
        	changeFighting.setChangevlaue(data.getId());
        	changeFighting.setChangevlaue2(25);
		}else {
			changeFighting.setChangevlaue(Calculation.getCalculation().mofa(fightingSkill.getSkillgain(), data, type));	
		    if (type.equals(TypeUtil.KX)) {
		    	changeFighting.setChangevlaue2(changeFighting.getChangevlaue()/3);
			}else if (type.equals(TypeUtil.MH)) {
				changeFighting.setChangevlaue2(changeFighting.getChangevlaue()/2);
			}
		}
		return changeFighting;
	}	
	/**
	  * 获取逃跑的人数据
	 * @return 
	  */
	public ManData taopao(int camp,int man){
		for (int i = 0; i < fightingdata.size(); i++) {
			if ( fightingdata.get(i).getCamp()==camp&&
				fightingdata.get(i).getMan()==man&&fightingdata.get(i).getType()==0) {
			return fightingdata.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 根据阵营和位置找到 数据存放位置 
	 */
	public int Datapath(int camp,int man){
		for (int i = 0; i < fightingdata.size(); i++) {
			if ( fightingdata.get(i).getCamp()==camp&&
					fightingdata.get(i).getMan()==man&&fightingdata.get(i).getStates()!=2) {
				return i;
			}
		}
		return -1;		
	}
	/**
	 * 根据阵营和位置找到和活的 数据存放位置 
	 */
	public int Datapathhuo(int camp,int man){		
		for (int i = 0; i < fightingdata.size(); i++) {
			if ( fightingdata.get(i).getCamp()==camp&&
					fightingdata.get(i).getMan()==man&&fightingdata.get(i).getStates()==0) {
				return i;
			}}
		return -1;		
	}
	/**
	 * 根据阵营和位置找到 回合数据存放位置 
	 */
	public int RoundDatapath(int camp,int man){
		for (int i = 0; i < Events.size(); i++) {
			if ( Events.get(i).getOriginator().getCamp()==camp&&
					Events.get(i).getOriginator().getMan()==man) {
				return i;
			}
		}
		return -1;
	}
	//根据id获取对应野怪
	public ManData getMonster(int id){
		if (id == 0) { //id为0时找血最少的
			int t = 0; // 血最少的单位
			int hp = 0;
			for (int i = fightingdata.size()-1; i >=0; i--) {
				ManData data=fightingdata.get(i);
				if (hp == 0 || data.getHp() < hp) {
					t = i;
					hp = data.getHp();
				}
			}
			return fightingdata.get(t);
		}
		
		
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			if (data.getType()==2){
				 if (data.getId()==id) {
					return data;
				}
			}			
		}
		return null;
	}

	public PathPoint instructionspath2(int camp, int noskill, ManData data) {
		// 一个x存阵营 一个y存位置
		int size=0;
		while (true) {
			size++;
			if (size>999) {break;}
			int path = random.nextInt(fightingdata.size());
			ManData nodata = fightingdata.get(path);
			if (camp != -1) {
				if (noskill == 1) {
					if (nodata.getCamp() == camp) {
						return new PathPoint(nodata.getCamp(),nodata.getMan());
					}
				} else {
					if (nodata.getCamp() == camp && nodata != data) {
						return new PathPoint(nodata.getCamp(),nodata.getMan());
					}
				}
			} else {
				if (noskill == 1) {
					return new PathPoint(nodata.getCamp(),nodata.getMan());
				} else {
					if (nodata != data) {
						return new PathPoint(nodata.getCamp(),nodata.getMan());
					}
				}
			}
		}
		return new PathPoint(data.getCamp(),data.getMan());
	}

	/**
	 * 找到敌方阵营的编号
	 */
	public int nomy(int camp){
		for (int i = 0; i < fightingdata.size(); i++) {
			if (fightingdata.get(i).getCamp()!=camp) {
				return fightingdata.get(i).getCamp();
			}
		}
		
		return -1;	
	}
	/**
	 * 根据当前状态能生成的攻击指令
	 */
	public FightingSkill mayinstructions2(ManData data){
		if (data.getType()!=2||data.getStates()!=0) {return null;}
		if (isV(data.getSr())&&data.xzstate("物理狂暴")==null) {
			List<Integer> list=new ArrayList<>();
			for (int i = 0; i < data.getSkills().size(); i++) {
				FightingSkill skill=data.getSkills().get(i);
				if (skill.getSkillbeidong()==0&&!data.SkillCooling(skill.getSkillid())) {
					list.add(i);
				}		
			}
			if (list.size()==0) {return null;}
			return data.getSkills().get(list.get(random.nextInt(list.size())));
		}			
		return null;		
	} 
	/**等待出手序列*/
	public void waitSell(){
		List<ManData> lingbao1=null;
		List<ManData> lingbao2=null;
		waitList.clear();
		boolean isBB=PK_MixDeal.isBB(BattleType);
	    for (int i = fightingdata.size()-1; i >=0; i--) {
	    	ManData data=fightingdata.get(i);
	    	if (data.getType()==4){ continue; }
	    	if (data.getStates()==2){ continue; }
	    	if (data.getStates()==1&&data.getType()==0) {
	    		ManData pet=getSeek(data,1);
	    		if (pet==null||pet.getStates()==1) {
					FightingEvents events=getWaitEvent(data.getCamp(), data.getMan());
	    			if (events==null) { continue; }
	    			String type=events.getOriginator().getStartState();
	    			//防御 召唤 逃跑
	    			if (!type.equals("防御")&&!type.equals("逃跑")&&!type.startsWith("召唤")) { continue; }
	    		}
			}else if (data.getStates()==1&&data.getType()!=0&&data.getType()!=2){ 
				continue; 
			}
	    	if (data.getType()==3) {
				if (data.getCamp()==1){
					if (lingbao1==null)lingbao1=new ArrayList<>();
					lingbao1.add(data);	
				}else{
					if (lingbao2==null)lingbao2=new ArrayList<>();
					lingbao2.add(data);
				} 
				continue;
			}
	    	if (isBB&&data.getMan()<5) {
				continue;
			}
	    	waitList.add(data);
		}
	    ManData ling1=getling(lingbao1);
		if (ling1!=null)waitList.add(ling1);
		ManData ling2=getling(lingbao2);
		if (ling2!=null)waitList.add(ling2);
		
	}
	/**判断灵宝中出手的人*/
	public ManData getling(List<ManData> lingbao1){
		if (lingbao1==null)return null;
		for (int i =lingbao1.size()-1; i >=0; i--) {
			ManData data=lingbao1.get(i);
			if(!chufa(data.getHuoyue()))lingbao1.remove(i);
		}
		if (lingbao1.size()==0)return null;
		return lingbao1.get(random.nextInt(lingbao1.size()));
	}
	/**判断灵宝是否触发*/
	public boolean chufa(double huoyue){if (Battlefield.random.nextInt(10000)<huoyue) {return true;}return false;}	
	/**初始化归类*/
	public void guilei(){
		boolean isBB=PK_MixDeal.isBB(BattleType);
		List<ManData> haizi = null;
		for (int i = fightingdata.size()-1; i >=0 ; i--) {
			ManData data=fightingdata.get(i);
			if (data.getType()==4&&!isBB) {
				if (haizi==null){haizi=new ArrayList<>();}
				haizi.add(data);
			}
			data.setSp2(random.nextInt(chaosSp));
		}
		
		if (haizi!=null) {
			List<FightingState> fightingStates = null;
			for (int i = haizi.size()-1; i >=0; i--) {
				ManData child=haizi.get(i);
				ManData data=getParents(child);
				if (data==null){
					continue;
				}
				FightingSkill skill=child.getChildSkill("加敏序");
				if (skill!=null) {
					if (fightingStates==null)fightingStates=new ArrayList<>();
					fightingStates.add(MixDeal.getChildSkill(child, skill.getSkillname()));
					data.setSp2(random.nextInt(chaosSp)+(int)skill.getSkillhurt());
					List<ManData> datas=MixDeal.get(false, data, 0,nomy(data.getCamp()), 1,0,0,0, 1, 6, this,1);
					if (datas.size()!=0)
						datas.get(0).setSp2(random.nextInt(chaosSp)+(int)skill.getSkillgain());	
				}
				skill=child.getChildSkill("免疫物理");
				if (skill!=null) {
					if (fightingStates==null)fightingStates=new ArrayList<>();
					fightingStates.add(MixDeal.getChildSkill(child, skill.getSkillname()));
					AddState addState=new AddState();
					addState.setSurplus(1);
					addState.setStatename(skill.getSkilltype());
					addState.setStateEffect(skill.getSkillhurt());
					AddState addState2=new AddState();
					addState2.setSurplus(1);
					addState2.setStatename("抗普通攻击");
					addState2.setStateEffect(skill.getSkillgain());
					data.getAddStates().add(addState);
					data.getAddStates().add(addState2);
				}
				
			}
			
			if (fightingStates!=null) {
				FightingEvents ksevents=new FightingEvents();
				ksevents.setCurrentRound(CurrentRound);
				ksevents.setAccepterlist(fightingStates);
				NewEvents.add(ksevents);
			}
		}
		for (int i = Events.size() - 1; i >= 0; i--) {
			FightingEvents event = Events.get(i);
			if (event == null) {
				Events.remove(i);
				continue;
			}
			FightingState org = event.getOriginator();
			if (org.getStartState().equals("防御")) {
				baohu.add(event);
			} else if (org.getStartState().equals("保护")) {
				baohu.add(event);
			} else if (org.getEndState() != null) {
				// 9110|知盈处虚|牺牲自己（4%*等级）的速度，使本回合内已被封印命中的单位的速度降低（4%*等级），持续3回合，回合开始前使用。（仅在与玩家之间战斗有效。）
				// 9389|流风回雪|回合开始前使用,使用后清除身上所有负面状态（等级2并回复自身血法上限的60%；等级三同时免疫物理攻击，持续1回合；等级四改为同时免疫所有攻击，持续1回合
				// 等级5改为同时免疫所有的攻击和控制，持续1回合）
				if (org.getEndState().equals("清心静气")||org.getEndState().equals("凝神一击")||org.getEndState().equals("气吞山河")
						||org.getEndState().equals("行气如虹")||org.getEndState().equals("气聚神凝")||org.getEndState().equals("神龙摆尾")||
						org.getEndState().equals("知盈处虚")||org.getEndState().equals("流风回雪")||org.getEndState().equals("逆鳞")||org.getEndState().equals("天罡八卦")) {
					Events.remove(i);
					erwai.add(event);
				}
			}
		}
	}
	/**敏捷度排序*/
	public FightingEvents SpBubble(){
		 List<PathPoint> ren=new ArrayList<>();
		 for (int i = waitList.size()-1;i>=0;i--) {
			  ManData data=waitList.get(i);
			  if (data.getStates()==2){
				  continue;
			  }
			  ren.add(new PathPoint(i,data.getSp()));		   		
		 }
		 for (int i = 0; i < ren.size() - 1; i++) {  
		        for (int j = 1; j < ren.size() - i; j++) {  
		        	PathPoint a;  
		        	if (ren.get(j - 1).getY()<ren.get(j).getY()) {  
		            	a = ren.get(j - 1);  
		                ren.set((j - 1), ren.get(j));  
		                ren.set(j, a);  
		            }  
		        }  
		}
		for (int i = 0; i <ren.size(); i++) {
			ManData data=waitList.get(ren.get(i).getX());
			FightingEvents events=getWaitEvent(data.getCamp(), data.getMan());
			if (events!=null) {
				String type=events.getOriginator().getStartState();
				if (data.xzstate("昏睡")!=null&&!(type.equals("药")||type.equals("召回")||type.indexOf("召唤")!=-1||type.equals("逃跑"))) {
					continue;
				}
				if (data.xzstate("封印")!=null&&!type.equals("召回")&&!type.equals("逃跑")) {
					continue;
				}
				Events.remove(events);
			}else {
				if (data.xzstate("封印")!=null||data.xzstate("昏睡")!=null){continue;}
				events=createEvent(data);
			}
			waitList.remove(ren.get(i).getX());
			return events;
		}
		return null;		
	}
	/**获取出手的指令*/
	public FightingEvents getWaitEvent(int camp,int man){
		for (int i=Events.size()-1;i>=0;i--) {
			FightingEvents events=Events.get(i);
			if (camp==events.getOriginator().getCamp()&&man==events.getOriginator().getMan()) {
				return events;
			}
		}
		return null;
	}
	/**单人竞技场智能施法处理*/
	public void oneArenaRole(FightingEvents events,ManData data){
		if (data.getStates()!=0) {//死亡状态普通攻击吧
			FightingState originator=events.getOriginator();
			originator.setStartState(TypeUtil.PTGJ);
			PathPoint pathPoint=instructionspath2(nomy(data.getCamp()), 0, data);	
		    List<FightingState> accepterlist=new ArrayList<>();
		    FightingState accepter=new FightingState();	
		    accepter.setCamp(pathPoint.getX());
		    accepter.setMan(pathPoint.getY());
		    accepterlist.add(accepter);
		    events.setAccepterlist(accepterlist);
			return;
		}
		//人物判断  一直释放加成最高的师门系技能
		FightingSkill fightingSkill=null;
		ManData manData=null;
		if (data.getType()==0) {
			int xl=0;
			for (int i = 0; i < data.getSkills().size(); i++) {
				FightingSkill skill=data.getSkills().get(i);
				if (skill.getSkillbeidong()!=0) {continue;}
				if (skill.getSkillblue()>data.getMp()) {continue;}
				if (skill.getSkillid()<=1000||skill.getSkillid()>1100) {continue;}
				if (data.SkillCooling(skill.getSkillid())) {continue;}
				if (fightingSkill==null) {
					fightingSkill=skill;
					xl=(skill.getSkillid()-1001)/5;
				}else {
					int oxl=(skill.getSkillid()-1001)/5;
					if (xl!=oxl) {
						if (data.getQuality().isSMXL(oxl, xl)) {//强力系列替换
							fightingSkill=skill;
							xl=oxl;
						}
					}else if (skill.getSkillid()>fightingSkill.getSkillid()) {
						fightingSkill=skill;
					}
				}
			}
		}else {//召唤兽判断
			if (CurrentRound==1) {
				for (int i = 0; i < data.getSkills().size(); i++) {
					FightingSkill skill=data.getSkills().get(i);
					if (skill.getSkillbeidong()!=0) {continue;}
					if (skill.getSkillblue()>data.getMp()) {continue;}
					int id=skill.getSkillid();
					if (!(id>=1600&&id<=1604||id==1607||id==1200)) {continue;}
					if (data.SkillCooling(skill.getSkillid())) {continue;}
					fightingSkill=skill;
					break;
				}
			}
			if (fightingSkill!=null) {
				//判断自己阵营中是否有人处于死亡状态  判断是否有拉人技能  1606 1611
				//判断自己阵营中是否有人处于异常状态  判断是否有解除异常技能 1608 1612
				FightingSkill skillSW=null;FightingSkill skillYC=null;
				for (int i = 0; i < data.getSkills().size(); i++) {
					FightingSkill skill=data.getSkills().get(i);
					if (skill.getSkillbeidong()!=0) {continue;}
					if (skill.getSkillblue()>data.getMp()) {continue;}
					int id=skill.getSkillid();
					if (!(id==1606||id==1608||id==1611||id==1612)) {continue;}
					if (data.SkillCooling(skill.getSkillid())) {continue;}
					if (id==1606||id==1611) {
						skillSW=skill;
					}else {
						skillYC=skill;
					}
				}
				ManData SW=null;
				ManData YC=null;
				if (skillSW!=null||skillYC!=null) {
					for (int i = 0; i < fightingdata.size(); i++) {
					     ManData data2=fightingdata.get(i);
					     if (data2.getCamp()!=data.getCamp()) {continue;}
					     if (data2.getStates()==2) {continue;}
					     if (data2.getStates()==1) {
					    	 if (skillSW!=null) {
							     SW=data2;
							     break;
						     }
						 }else if (skillYC!=null&&YC==null) {
							 if (data2.xzstate(TypeUtil.FY)!=null||data2.xzstate(TypeUtil.HL)!=null||
									 data2.xzstate(TypeUtil.HS)!=null||data2.xzstate(TypeUtil.YW)!=null) {
								 YC=data2;
							}
					     }
				    }
				}
				if (SW!=null) {
					manData=SW;
					fightingSkill=skillSW;
				}else if (YC!=null) {
					manData=YC;
					fightingSkill=skillYC;
				}
			}
		}
		FightingState originator=events.getOriginator();
		PathPoint pathPoint=null;
        if (manData!=null) {
        	pathPoint=new PathPoint(manData.getCamp(), manData.getMan());
		}else {
			if (fightingSkill!=null) {
				originator.setStartState(TypeUtil.JN);
				originator.setEndState(fightingSkill.getSkillname());
				if (!fightingSkill.Goodskill()) {//随机敌方阵营		
					pathPoint=instructionspath2(nomy(data.getCamp()), 1, data);
				}else {
					pathPoint=instructionspath2(data.getCamp(), 1, data);
				}
			}else {
				originator.setStartState(TypeUtil.PTGJ);
				pathPoint=instructionspath2(nomy(data.getCamp()), 0, data);	
			}	
		}
		List<FightingState> accepterlist=new ArrayList<>();
		FightingState accepter=new FightingState();	
		accepter.setCamp(pathPoint.getX());
		accepter.setMan(pathPoint.getY());
		accepterlist.add(accepter);
		events.setAccepterlist(accepterlist);
	}
	
	/**武神山智能施法处理*/
	public void wssBattle(FightingEvents events,ManData data){
		if (data.getStates()!=0) {//死亡状态普通攻击吧
			FightingState originator=events.getOriginator();
			originator.setStartState(TypeUtil.PTGJ);
			PathPoint pathPoint=instructionspath2(nomy(data.getCamp()), 0, data);	
		    List<FightingState> accepterlist=new ArrayList<>();
		    FightingState accepter=new FightingState();	
		    accepter.setCamp(pathPoint.getX());
		    accepter.setMan(pathPoint.getY());
		    accepterlist.add(accepter);
		    events.setAccepterlist(accepterlist);
			return;
		}
		//人物判断  一直释放加成最高的师门系技能
		FightingSkill fightingSkill=null;
		ManData manData=null;
		if (data.getMan() <= 4) {
			int xl=0;
			for (int i = 0; i < data.getSkills().size(); i++) {
				FightingSkill skill=data.getSkills().get(i);
				if (skill.getSkillbeidong()!=0) {continue;}
				if (skill.getSkillblue()>data.getMp()) {continue;}
				if (skill.getSkillid()<=1000||skill.getSkillid()>1100) {continue;}
				if (data.SkillCooling(skill.getSkillid())) {continue;}
				if (fightingSkill==null) {
					fightingSkill=skill;
					xl=(skill.getSkillid()-1001)/5;
				}else {
					int oxl=(skill.getSkillid()-1001)/5;
					if (xl!=oxl) {
						if (data.getQuality().isSMXL(oxl, xl)) {//强力系列替换
							fightingSkill=skill;
							xl=oxl;
						}
					}else if (skill.getSkillid()>fightingSkill.getSkillid()) {
						fightingSkill=skill;
					}
				}
			}
		}else {//召唤兽判断
			if (CurrentRound==1) {
				for (int i = 0; i < data.getSkills().size(); i++) {
					FightingSkill skill=data.getSkills().get(i);
					if (skill.getSkillbeidong()!=0) {continue;}
					if (skill.getSkillblue()>data.getMp()) {continue;}
					int id=skill.getSkillid();
					if (!(id>=1600&&id<=1604||id==1607||id==1200)) {continue;}
					if (data.SkillCooling(skill.getSkillid())) {continue;}
					fightingSkill=skill;
					break;
				}
			}
			if (fightingSkill!=null) {
				//判断自己阵营中是否有人处于死亡状态  判断是否有拉人技能  1606 1611
				//判断自己阵营中是否有人处于异常状态  判断是否有解除异常技能 1608 1612
				FightingSkill skillSW=null;FightingSkill skillYC=null;
				for (int i = 0; i < data.getSkills().size(); i++) {
					FightingSkill skill=data.getSkills().get(i);
					if (skill.getSkillbeidong()!=0) {continue;}
					if (skill.getSkillblue()>data.getMp()) {continue;}
					int id=skill.getSkillid();
					if (!(id==1606||id==1608||id==1611||id==1612)) {continue;}
					if (data.SkillCooling(skill.getSkillid())) {continue;}
					if (id==1606||id==1611) {
						skillSW=skill;
					}else {
						skillYC=skill;
					}
				}
				ManData SW=null;
				ManData YC=null;
				if (skillSW!=null||skillYC!=null) {
					for (int i = 0; i < fightingdata.size(); i++) {
					     ManData data2=fightingdata.get(i);
					     if (data2.getCamp()!=data.getCamp()) {continue;}
					     if (data2.getStates()==2) {continue;}
					     if (data2.getStates()==1) {
					    	 if (skillSW!=null) {
							     SW=data2;
							     break;
						     }
						 }else if (skillYC!=null&&YC==null) {
							 if (data2.xzstate(TypeUtil.FY)!=null||data2.xzstate(TypeUtil.HL)!=null||
									 data2.xzstate(TypeUtil.HS)!=null||data2.xzstate(TypeUtil.YW)!=null) {
								 YC=data2;
							}
					     }
				    }
				}
				if (SW!=null) {
					manData=SW;
					fightingSkill=skillSW;
				}else if (YC!=null) {
					manData=YC;
					fightingSkill=skillYC;
				}
			}
		}
		FightingState originator=events.getOriginator();
		PathPoint pathPoint=null;
        if (manData!=null) {
        	pathPoint=new PathPoint(manData.getCamp(), manData.getMan());
		}else {
			if (fightingSkill!=null) {
				originator.setStartState(TypeUtil.JN);
				originator.setEndState(fightingSkill.getSkillname());
				if (!fightingSkill.Goodskill()) {//随机敌方阵营		
					pathPoint=instructionspath2(nomy(data.getCamp()), 1, data);
				}else {
					pathPoint=instructionspath2(data.getCamp(), 1, data);
				}
			}else {
				originator.setStartState(TypeUtil.PTGJ);
				pathPoint=instructionspath2(nomy(data.getCamp()), 0, data);	
			}	
		}
		List<FightingState> accepterlist=new ArrayList<>();
		FightingState accepter=new FightingState();	
		accepter.setCamp(pathPoint.getX());
		accepter.setMan(pathPoint.getY());
		accepterlist.add(accepter);
		events.setAccepterlist(accepterlist);
	}
	
	public FightingEvents createEvent(ManData data){
		FightingEvents events=new FightingEvents();	
		FightingState originator=new FightingState();
		originator.setCamp(data.getCamp());
		originator.setMan(data.getMan());	
		events.setOriginator(originator);
		if (BattleType==4&&data.getStates()==0&&data.getType()==2) {
			if (CurrentRound >= 3 && random.nextInt(100) < 10) {
				originator.setStartState("逃跑");
			} else {
				originator.setStartState("偷钱");
			}
			return events;
		}else if (BattleType==101&&data.getCamp()==2) {
			if (data.getType()==0||data.getType()==1) {//智能指令
				oneArenaRole(events,data);
				return events;
			}
		}else if (BattleType==886&&data.getCamp()==0) {
			if (data.getType()==2) {//智能指令
				wssBattle(events,data);
				return events;
			}
		}
		AI ai=AI_MixDeal.AI_ZL(this, data);
		if (ai!=null&&!(ai.getType()==AI.AI_TYPE_SKILL||ai.getType()==AI.AI_TYPE_AUTOMATE)) {
			if (ai.getType()==AI.AI_TYPE_FY) {
				originator.setStartState("防御");
			}else if (ai.getType()==AI.AI_TYPE_TP) {
				originator.setStartState("逃跑");
			}else if (ai.getType()==AI.AI_TYPE_HELP) {
				int YCamp=-1;int Yman=-1;
				ManData Ydata=getMonster(ai.getMan());
				if (Ydata!=null) {
					YCamp=Ydata.getCamp();
					Yman =Ydata.getMan();	
				}else {
					PathPoint pathPoint=instructionspath2(data.getCamp(), 1, data);
					YCamp=pathPoint.getX();
					Yman=pathPoint.getY();
				}
				originator.setStartState("药");
				originator.setEndState("HP%="+ai.getValue());
				List<FightingState> accepterlist=new ArrayList<>();
				FightingState accepter=new FightingState();	
				accepter.setCamp(YCamp);
				accepter.setMan(Yman);
				accepterlist.add(accepter);
				events.setAccepterlist(accepterlist);
			}
			return events;
		}
		String mayname=TypeUtil.PTGJ;
		FightingSkill skill=null;
		if (ai!=null) {
			if (ai.getType()==AI.AI_TYPE_SKILL) {
				skill=data.getZDSKILL(ai.getMan(),ai.getValue());
				if (skill!=null) {
					mayname=skill.getSkillname();
				}	
			}else if (ai.getType()==AI.AI_TYPE_AUTOMATE) {
				AIAutomate aiAutomate=ai.getAiAutomate(BattleType);
				if (aiAutomate!=null) {
					if (aiAutomate.getType().equals("防御")||aiAutomate.getType().equals("逃跑")) {
						originator.setStartState(aiAutomate.getType());
						return events;
					}
					mayname=aiAutomate.getType();
					if (aiAutomate.getBs()==1) {
						for (int i = 0; i < aiAutomate.getSkills().length; i++) {
							skill=data.getSkillId(aiAutomate.getSkills()[i]);
							if (skill!=null) {
								if (skill.getSkillbeidong()==1||data.SkillCooling(skill.getSkillid())||!data.isLicense(skill)) {
									skill=null;
									continue;
								}
								mayname=skill.getSkillname();
								break;
							}
						}
					}
				}
			}		
		}else {
			skill=mayinstructions2(data);
			if (skill!=null) {
				mayname=skill.getSkillname();
			}	
		}
		PathPoint pathPoint=null;
		if (skill!=null) {
			originator.setStartState(TypeUtil.JN);
			originator.setEndState(mayname);
			if (!skill.Goodskill()) {//随机敌方阵营		
				pathPoint=instructionspath2(nomy(data.getCamp()), 1, data);
			}else {
				pathPoint=instructionspath2(data.getCamp(), 1, data);
			}
		}else {
			originator.setStartState(mayname);
			pathPoint=instructionspath2(nomy(data.getCamp()), 0, data);	
		}
		List<FightingState> accepterlist=new ArrayList<>();
		FightingState accepter=new FightingState();	
		accepter.setCamp(pathPoint.getX());
		accepter.setMan(pathPoint.getY());
		accepterlist.add(accepter);
		events.setAccepterlist(accepterlist);
		return events;
	}
	/**
	 * 可以指挥的数量
	 * 场上非野怪的数量
	 */
	public int noyesum(){
		boolean isArena=PK_MixDeal.isArena(BattleType);
		int sum=fightingdata.size();		
		for (int i = sum-1; i >=0;i--) {
			ManData data=fightingdata.get(i);
			if (data.getStates()==2){
				sum--;
			}else if (data.getType()==2||data.getType()==3||data.getType()==4){
				sum--;
			}else if (isArena&&data.getCamp()!=1) {
				sum--;
			}else if (data.getType()==1&&data.getStates()==1){
				sum--;
			}
		}
		sum+=elsum;
		return sum;		
	}
	/**场上玩家的数量*/
	public int noyenosumomsum(){
		int sum=0;
		for (int i = 0; i < fightingdata.size(); i++) {
			if (fightingdata.get(i).getType()==0&&fightingdata.get(i).getStates()!=2) {
				sum++;
			}
		}
		return sum;		
	}
	/**根据List<ManData>生成 List<FightingManData>*/
	public List<FightingManData> Transformation(){
		List<FightingManData> fightingManDatas=new ArrayList<>();
		try {
			for (int i = 0; i < fightingdata.size(); i++) {
				ManData data=fightingdata.get(i);
				if (data.getStates()==2){
					continue;
				}
				if (data.getStates()==1&&(data.getType()!=0&&data.getType()!=2)){
					continue;
				}
				FightingManData fightingManData=new FightingManData();
				fightingManData.setModel(data.getModel());
				fightingManData.setManname(data.getManname());
				fightingManData.setCamp(data.getCamp());
				fightingManData.setMan(data.getMan());
				fightingManData.setHp_Current(data.getHp());
				fightingManData.setHp_Total(data.getHp_z());
				fightingManData.setMp_Current(data.getMp());
				fightingManData.setMp_Total(data.getMp_z());
				fightingManData.setState_1(data.xz());
				fightingManData.setType(data.getType());
				fightingManData.setId(data.getId());
				fightingManData.setZs(data.getZs());
				fightingManData.setStates(data.ztstlist(fightingManData));
			    fightingManData.setMsg(data.getMsg());
				fightingManData.setYqz(data.getYqz());
				fightingManData.setNqz(data.getNqz());
				fightingManDatas.add(fightingManData);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return fightingManDatas;		
	}
	/**
	 * 结束判断 判断阵营是否还有活人
	 * -1代表未结束战斗
	 * 传回当前相同数则胜利
	 * 其他为失败
	 */
	public int endFighting(int mycamp){
	        int nomycamp=nomy(mycamp);
	        int mysum=Surplus(mycamp);
	        int nomysum=Surplus(nomycamp);
	        if (CurrentRound>120) {return nomycamp;}
	        if ((BattleType==21||BattleType==33)&&CurrentRound>=60) {
	        	return nomycamp;
			}
	        if (PK_MixDeal.isArena(BattleType)&&CurrentRound>10) {return nomycamp;}
	        
	        if (mysum>0&&nomysum>0) {
	        	return -1;
			}else if (mysum>0) {
	        	return mycamp;
			}else if (nomysum>0) {
				return nomycamp;
			}
	        return random.nextBoolean()?mycamp:nomycamp;
	}
	/**
	 * 判断阵营剩余的人数
	 * 不为逃跑的人 
	 * 不为小孩和灵宝
	 */
	public int Surplus(int camp){
		boolean isBB=PK_MixDeal.isBB(BattleType);
		int sum=0;
		int pSum=0;
		boolean is=true;
		for (int i = 0; i < fightingdata.size(); i++) {
			ManData data=fightingdata.get(i);
			if (data.getType()==3||data.getType()==4||data.getCamp()!=camp){
				continue;
			}
			if (isBB&&data.getMan()<5) {continue;}	
			if (camp!=0&&data.getType()==2) {
				if (data.getStates()==0) {pSum++;}
				continue;
			}
			if (data.getStates()==0) {
				sum++;
				break;
			}else if (is&&data.getStates()==1) {
				is=false;
			}
		}
		if (sum==0) {
			return is?sum:(sum+pSum);
		}
		return sum;
	}
	/**找出阵营中蓝量最低 且还活着的人*/
	public ManData minmp(int camp) {
		int mp = 10000000;
		int path = -1;
		for (int i = 0; i < fightingdata.size(); i++) {
			ManData data = fightingdata.get(i);
			if (data.getType() == 3 || data.getType() == 4 || data.getCamp() != camp) {
				continue;
			}
			if (data.getStates() == 0) {
				if (mp > data.getMp()) {
					mp = data.getMp();
					path = i;
				}
			}
		}
		if (path != -1) return fightingdata.get(path);
		return null;
	}
	/**找到是否存在指定位置 不存在添加在后面*/
	public boolean Addbb(ManData bbData,int mycamp,int myman){
		for (int i = 0; i < fightingdata.size(); i++) {
			int camp=fightingdata.get(i).getCamp();
			int man=fightingdata.get(i).getMan();
			if (bbData!=null) {
				if (camp==bbData.getCamp()&&man==bbData.getMan()) {
					int path=Datapath(camp, man-5);
					Petplay(fightingdata.get(i),fightingdata.get(path) , 2);
					fightingdata.get(i).getAddStates().clear();
					fightingdata.set(i, bbData);
					Petplay(fightingdata.get(i),fightingdata.get(path) , 1);
					return true;
				}	
			}else {
				if (camp==mycamp&&man==myman) {
					fightingdata.get(i).setStates(2);
					fightingdata.get(i).getAddStates().clear();
					Petplay(fightingdata.get(i),fightingdata.get(Datapath(camp, man-5)) , 2);
					return true;
				}	
			}			
		}
		if (bbData!=null) {
	        Petplay(bbData,fightingdata.get(Datapath(bbData.getCamp(),bbData.getMan()-5)) , 1);
		    fightingdata.add(bbData);
		}
		return false;
	}
	/**设置召唤兽的play*/
	public void Petplay(ManData petdata,ManData roledata,int play){
		for (int i = 0; i < roledata.getPets().size(); i++) {
			if (petdata.getId()==roledata.getPets().get(i).getHang().getId().intValue()) {
				roledata.getPets().get(i).setPlay(play);
			}
		}
	}
	/**根据队伍获得靠中间的战斗位置*/
	public static int Fightingpath(int camp,int man){
		if (camp==0) {
			if (man==0) return 2;
			else if (man==1) return 1;
			else if (man==2) return 3;
			else if (man==3) return 0;
			else if (man==4) return 4;
			else if (man==5) return 7;
			else if (man==6) return 6;
			else if (man==7) return 8;
			else if (man==8) return 5;
			else return 9;
		}else {
			if (man==0)return 2;
			else if (man==1)return 1;
			else if (man==2)return 3;
			else if (man==3)return 0;
			else return 4;
		}
	}
	/**反向解析根据队伍获得靠中间的战斗位置*/
	public static int FXFightingpath(int man){
		if (man==0)return 3;
		else if (man==1)return 1;
		else if (man==2)return 0;
		else if (man==3)return 2;
		else return 4;
	}
	/**返回该单位的孩子*/
	public ManData getChild(ManData data){
		if (data.getType()!=0)return null;
		int man=data.getMan()+15;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData Child=fightingdata.get(i);
			if (Child.getCamp()!=data.getCamp())continue;
			if (Child.getMan()!=man)continue;
			return Child;
		}
		return null;
	}
	/**返回孩子的家长*/
	public ManData getParents(ManData data){
		if (data.getType()!=4)return null;
		int man=data.getMan()-15;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData Parents=fightingdata.get(i);
			if (Parents.getCamp()!=data.getCamp())continue;
			if (Parents.getMan()!=man)continue;
			return Parents;
		}
		return null;
	}
	/**返回召唤兽的家长*/
	public ManData getPetParents(ManData data){
		if (data.getType()!=1)return null;
		int man=data.getMan()-5;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData Parents=fightingdata.get(i);
			if (Parents.getCamp()!=data.getCamp())continue;
			if (Parents.getMan()!=man)continue;
			return Parents;
		}
		return null;
	}
    /**家长寻找单位  1召唤兽 3灵宝 4小孩*/
	public ManData getSeek(ManData data,int type){
		if (data.getType()!=0) {
			return null;
		}
		int man=data.getMan();
		if (type==1) {man+=5;}
		else if (type==3) {man+=10;}
		else if (type==4) {man+=15;}
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData Parents=fightingdata.get(i);
			if (Parents.getType()!=type||Parents.getMan()!=man||
				Parents.getCamp()!=data.getCamp()){
				continue;
			}
			return Parents;
		}
		return data;
	}
    /**挣扎处理*/
	public void struggle(ManData data,List<FightingState> states){	
		ManData child=getChild(data);
		AddState zt=data.xzstate("混乱");
		FightingSkill skill=null;
		if (zt!=null) {
			if (child!=null) {
				skill=child.getChildSkill("解混乱");
				if (skill!=null) {
					states.add(MixDeal.getChildSkill(child, skill.getSkillname()));
					FightingState state2=new FightingState();
					state2.setCamp(data.getCamp());
					state2.setMan(data.getMan());
					state2.setStartState("代价");
					state2.setEndState_2("混乱");
					data.getAddStates().remove(zt);
					zt.rid(data, state2);
					ChangeFighting fighting=new ChangeFighting();
					fighting.setChangetype("抗混乱");
					fighting.setChangevlaue(skill.getSkillgain());
					fighting.setChangesum(skill.getSkillcontinued());
					data.ChangeData(fighting, state2);
					states.add(state2);
					zt=null;
				}		
			}
		}
		if (zt!=null) {
			if (random.nextInt(888)<data.getsx(0,"混乱")) {
				FightingState state2=new FightingState();
    			state2.setCamp(data.getCamp());
    			state2.setMan(data.getMan());
    			state2.setStartState("代价");
    			state2.setEndState_2("混乱");
    			states.add(state2);
    			data.getAddStates().remove(zt);
    			zt.rid(data, state2);
			}
		}
		zt=data.xzstate("封印");
		if (zt!=null) {
		if (child!=null) {	
		skill=child.getChildSkill("解封印");
        if (skill!=null) {
        	states.add(MixDeal.getChildSkill(child, skill.getSkillname()));
        	FightingState state2=new FightingState();
			state2.setCamp(data.getCamp());
			state2.setMan(data.getMan());
			state2.setStartState("代价");
			state2.setEndState_2("封印");
			data.getAddStates().remove(zt);
			zt.rid(data, state2);
			ChangeFighting fighting=new ChangeFighting();
			fighting.setChangetype("抗封印");
			fighting.setChangevlaue(skill.getSkillgain());
			fighting.setChangesum(skill.getSkillcontinued());
			data.ChangeData(fighting, state2);
			states.add(state2);
			zt=null;
		}
		}
		}
        if (zt!=null) {
            if (random.nextInt(888)<data.getsx(0,"封印")) {
            	FightingState state2=new FightingState();
    			state2.setCamp(data.getCamp());
    			state2.setMan(data.getMan());
    			state2.setStartState("代价");
    			state2.setEndState_2("封印");
    			states.add(state2);
    			data.getAddStates().remove(zt);
    			zt.rid(data, state2);
			}
		}
        zt=data.xzstate("昏睡");
        if (zt!=null) {
        if (child!=null) {	
        skill=child.getChildSkill("解昏睡");
        if (skill!=null) {
        	states.add(MixDeal.getChildSkill(child, skill.getSkillname()));
        	FightingState state2=new FightingState();
			state2.setCamp(data.getCamp());
			state2.setMan(data.getMan());
			state2.setStartState("代价");
			state2.setEndState_2("昏睡");
			data.getAddStates().remove(zt);
			zt.rid(data, state2);
			ChangeFighting fighting=new ChangeFighting();
			fighting.setChangetype("抗昏睡");
			fighting.setChangevlaue(skill.getSkillgain());
			fighting.setChangesum(skill.getSkillcontinued());
			data.ChangeData(fighting, state2);
			states.add(state2);
			zt=null;
		}
        }
        }
        if (zt!=null) {
            if (random.nextInt(888)<data.getsx(0,"昏睡")) {
            	FightingState state2=new FightingState();
    			state2.setCamp(data.getCamp());
    			state2.setMan(data.getMan());
    			state2.setStartState("代价");
    			state2.setEndState_2("昏睡");
    			states.add(state2);
    			data.getAddStates().remove(zt);
    			zt.rid(data, state2);
			}
		}
        zt=data.xzstate("中毒");
        if (zt!=null) {
        if (child!=null) {	
        skill=child.getChildSkill("解中毒");
        if (skill!=null) {
        	states.add(MixDeal.getChildSkill(child, skill.getSkillname()));
        	FightingState state2=new FightingState();
			state2.setCamp(data.getCamp());
			state2.setMan(data.getMan());
			state2.setStartState("代价");
			state2.setEndState_2("中毒");
			data.getAddStates().remove(zt);
			ChangeFighting fighting=new ChangeFighting();
			fighting.setChangehp((int)(data.getHp_z()*skill.getSkillgain()/100));
			data.ChangeData(fighting, state2);
			states.add(state2);
			zt=null;
		}
        }
        }
        if (zt!=null) {
            if (random.nextInt(888)<data.getsx(0,"中毒")) {
            	FightingState state2=new FightingState();
    			state2.setCamp(data.getCamp());
    			state2.setMan(data.getMan());
    			state2.setStartState("代价");
    			state2.setEndState_2("中毒");
    			states.add(state2);
    			data.getAddStates().remove(zt);
			}
		}
        zt=data.xzstate(TypeUtil.FB_JGE);
        if (zt!=null) {
			FightingSkill sMM=data.getSkillType(TypeUtil.TY_FY_MHNY);
			if (skill!=null&&isV(sMM.getSkillhurt())) {
				FightingState state2=new FightingState();
    			state2.setCamp(data.getCamp());
    			state2.setMan(data.getMan());
    			state2.setStartState("代价");
    			state2.setEndState_2(TypeUtil.TY_FY_MHNY);
    			states.add(state2);
    			data.getAddStates().remove(zt);
			}
		}
        if (data.getType()==1) {
			skill=data.getSkillType(TypeUtil.BB_E_XYLX);
			if (skill!=null&&isV(skill.getSkillgain())) {
				ManData parent=getPetParents(data);
				if (parent!=null&&parent.getvalue(0)<skill.getSkillgain()/100D) {
					FightingState state=new FightingState();
					state.setCamp(data.getCamp());
					state.setMan(data.getMan());
					state.setStartState("代价");
					state.setText("心有灵犀#2");
					states.add(state);
					FightingState state2=new FightingState();
					ChangeFighting changeFighting=new ChangeFighting();
					changeFighting.setChangehp(parent.getHp_z()/2);
					changeFighting.setChangemp(parent.getMp_z()/2);
					parent.ChangeData(changeFighting, state2);
					state2.setStartState("代价");
					states.add(state2);
				}
			}	
		}
        
        zt=data.xzstate("化险为夷");
        if (zt!=null) {
        	// 身上有盾
        	if (data.executeYbwh(1)) {
	        	data.RemoveAbnormal(ManData.values1);
				FightingState state2=new FightingState();
    			state2.setCamp(data.getCamp());
    			state2.setMan(data.getMan());
    			state2.setStartState("代价");
    			state2.setEndState_2("清除异常状态");
    			states.add(state2);
        	}
		}
        
        zt=data.xzstate("焕然新生");
        if (zt!=null) {
        	// 盾持续回合为最后一回合
        	if (zt.getOngoing() == 2) {
	        	data.RemoveAbnormal(ManData.values1);
				FightingState state2=new FightingState();
    			state2.setCamp(data.getCamp());
    			state2.setMan(data.getMan());
    			state2.setStartState("代价");
    			state2.setEndState_2("清除异常状态");
    			states.add(state2);
        	}
		}
	}
	/**摆脱处理*/
	public List<FightingState> Rid(ManData data,List<FightingState> states){
		AddState zt=data.xzstate("混乱");
		FightingSkill skill=null;
		if (zt!=null) {
			skill=data.getSkillType(TypeUtil.BB_QMS);
			if (skill!=null&&skill.getSkillhitrate()>random.nextInt(100)) {
				if (states==null) {states=new ArrayList<>();}
				FightingState state2=new FightingState();
				state2.setCamp(data.getCamp());
				state2.setMan(data.getMan());
				state2.setStartState(TypeUtil.JN);
				state2.setSkillskin(skill.getSkilltype());
				state2.setEndState_2(zt.getStatename());
				states.add(state2);
				data.RemoveAbnormal(state2,zt.getStatename());
			}
		}
		zt=data.xzstate("封印");
		if (zt!=null) {
			double xs=0;
			skill=data.getSkillType(TypeUtil.BB_TKS);
			if (skill!=null) {xs+=skill.getSkillhitrate();}
			skill=data.getSkillType(TypeUtil.BB_E_BXJL);
			if (skill!=null) {xs+=skill.getSkillgain();}
			if (isV(xs)) {
				if (states==null) {states=new ArrayList<>();}
				FightingState state2=new FightingState();
				state2.setCamp(data.getCamp());
				state2.setMan(data.getMan());
				state2.setStartState(TypeUtil.JN);
				state2.setSkillskin(TypeUtil.BB_TKS);
				state2.setEndState_2(zt.getStatename());
				if (skill!=null) {state2.setText("冰雪精灵#2");}
				states.add(state2);
				data.RemoveAbnormal(state2,zt.getStatename());
			}
		}
		zt=data.xzstate("遗忘");
		if (zt!=null) {
			skill=data.getSkillType(TypeUtil.BB_QXS);
			if (skill!=null&&skill.getSkillhitrate()>random.nextInt(100)) {
				if (states==null) {states=new ArrayList<>();}
				FightingState state2=new FightingState();
				state2.setCamp(data.getCamp());
				state2.setMan(data.getMan());
				state2.setStartState(TypeUtil.JN);
				state2.setSkillskin(skill.getSkilltype());
				state2.setEndState_2(zt.getStatename());
				states.add(state2);
				data.RemoveAbnormal(state2,zt.getStatename());
			}
		}
		return states;
	}
	public void BBDeathPoint(){
		if (bbDeathPoint==null) {return;}
		List<FightingState> zls=null;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			if (data.getType()!=1) {continue;}
			if (data.getStates()!=0) {continue;}
			if ((bbDeathPoint.getX()!=0&&data.getCamp()==1)||(bbDeathPoint.getY()!=0&&data.getCamp()!=1)) {
				int death=data.getCamp()==1?bbDeathPoint.getX():bbDeathPoint.getY();
				FightingSkill skill=data.getSkillType(TypeUtil.BB_FLSQ);
				if (skill!=null) {
					int num=skill.getSkillsum();
					if (num==0) {//添加特效
						if (zls==null) {zls=new ArrayList<>();}
						data.addAddState(TypeUtil.BB_FLSQ, 0, 0, 9999);
						FightingState ace=new FightingState(data.getCamp(),data.getMan(),TypeUtil.JN);
						ace.setEndState_1(TypeUtil.BB_FLSQ);
						zls.add(ace);
					}
					skill.setSkillsum(skill.getSkillsum()+death);
					if (skill.getSkillsum()>=15) {skill.setSkillsum(15);data.getSkills().remove(skill);}
					data.getQuality().addK_FHSLG(skill.getSkillhurt()*(skill.getSkillsum()-num));
				}
				skill=data.getSkillType(TypeUtil.BB_LAHM);
				if (skill != null) {
					int num=skill.getSkillsum();
					skill.setSkillsum(skill.getSkillsum()+death);
					if (skill.getSkillsum()>=10) {skill.setSkillsum(10);data.getSkills().remove(skill);}
					data.getQuality().addK_BHSY(skill.getSkillhurt()*(skill.getSkillsum()-num));
				}
			}
		}
		if (zls!=null) {
			FightingEvents fightingEvents=new FightingEvents();
			fightingEvents.setCurrentRound(CurrentRound);
			fightingEvents.setAccepterlist(zls);
			NewEvents.add(fightingEvents);	
		}
		bbDeathPoint.setX(0);
		bbDeathPoint.setY(0);
	}
	/**火上浇油状态处理*/
	public void HurtHSJY(){
		ChangeFighting changeFighting=new ChangeFighting();
		List<FightingState> hsjystate=null;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			if (data.getStates()!=0||data.getType()==3||data.getType()==4){continue;}
            AddState addState=data.xzstate(TypeUtil.TY_H_HSJY);
            if (addState==null) {continue;}
            data.getAddStates().remove(addState);
            int hurt=(int) addState.getStateEffect2();
			if (hsjystate==null)hsjystate=new ArrayList<>();
			changeFighting.setChangehp(-hurt);
			FightingState org2=new FightingState();
			FightingPackage.ChangeProcess(changeFighting, null, data, org2,TypeUtil.TY_H_HSJY, hsjystate, this);
			org2.setCamp(data.getCamp());
			org2.setMan(data.getMan());
			org2.setStartState(TypeUtil.JN);
			org2.setEndState_2(TypeUtil.TY_H_HSJY);
		}
		if (hsjystate!=null) {
			FightingEvents ksevents=new FightingEvents();
			ksevents.setCurrentRound(CurrentRound);
			ksevents.setAccepterlist(hsjystate);
			NewEvents.add(ksevents);
		}
		isHSJY=false;
	}
	/**雾里看花状态处理*/
	public void HurtWLKH(){
		ChangeFighting changeFighting=new ChangeFighting();
		List<FightingState> wlkhstate=null;
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData data=fightingdata.get(i);
			int hurt=data.getWLKHSkill();
			if (hurt<=0)continue;
			if (hurt>123456) {hurt=123456;}
			if (wlkhstate==null)wlkhstate=new ArrayList<>();
			changeFighting.setChangehp(-hurt);
			FightingState org2=new FightingState();
			FightingPackage.ChangeProcess(changeFighting, null, data, org2, "雾里看花", wlkhstate, this);
			org2.setCamp(data.getCamp());
			org2.setMan(data.getMan());
			org2.setStartState(TypeUtil.JN);
		}
		if (wlkhstate!=null) {
			FightingEvents ksevents=new FightingEvents();
			ksevents.setCurrentRound(CurrentRound);
			ksevents.setAccepterlist(wlkhstate);
			NewEvents.add(ksevents);
		}
		isWLKH=false;
	}
	/**
	 * 获取被打击人周围2格内单位
	 * @param data	被打击人
	 * @param
	 * @return
	 */
	public List<ManData> getZW2(ManData data){
		List<ManData> datas=new ArrayList<>();
		int camp=data.getCamp();
		int man=data.getMan();
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData noData=fightingdata.get(i);
			if (noData.getType()==3||noData.getType()==4)continue;
			if (noData.getStates()!=0)continue;
			if (noData.getCamp()!=camp)continue;
			int noman=noData.getMan();
			if (man==noman)continue;
			if (noman == man - 6 || noman == man - 5 || (noman == man - 4 && man > 4) || 
					(noman == man - 1 && man != 5) || (noman == man - 2 && (man > 6 || man < 5)) ||
					(noman == man + 1 && man != 4) || (noman == man + 2 && (man > 4 || man < 3)) ||
					(noman == man + 4 && man < 5 && man != 0) || noman == man + 5 || noman == man + 6) {
	            if (noData.xzstate("封印")==null&&noData.xzstate("隐身")==null) {
					datas.add(noData);	
				}
			}
		}
		return datas;
	}
	

	
	/**获取该单位的周围单位*/
	public List<ManData> getZW(ManData data){
		List<ManData> datas=new ArrayList<>();
		int camp=data.getCamp();
		int man=data.getMan();
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData noData=fightingdata.get(i);
			if (noData.getType()==3||noData.getType()==4)continue;
			if (noData.getStates()!=0)continue;
			if (noData.getCamp()!=camp)continue;
			int noman=noData.getMan();
			if (man==noman)continue;
			if (man==noman+1||man==noman-1||man==noman+5||man==noman-5) {
				if (man==5&&noman==4)continue;
				if (man==4&&noman==5)continue;
	            if (noData.xzstate("封印")==null&&noData.xzstate("隐身")==null) {
					datas.add(noData);	
				}
			}
		}
		return datas;
	}
	
	/**获取该单位的一个周围单位*/
	public ManData getZW1(ManData data){
		int camp=data.getCamp();
		int man=data.getMan();
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData noData=fightingdata.get(i);
			if (noData.getType()==3||noData.getType()==4)continue;
			if (noData.getStates()!=0)continue;
			if (noData.getCamp()!=camp)continue;
			int noman=noData.getMan();
			if (man==noman)continue;
			if (man==noman+1||man==noman-1||man==noman+5||man==noman-5) {
				if (man==5&&noman==4)continue;
				if (man==4&&noman==5)continue;
	            if (noData.xzstate("封印")==null&&noData.xzstate("隐身")==null) {
					return noData;	
				}
			}
		}
		return null;
	}
	
	/**获取该单位的周围一格的隐身单位*/
	public List<ManData> getZWYS(ManData data){
		List<ManData> datas=new ArrayList<>();
		int camp=data.getCamp();
		int man=data.getMan();
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData noData=fightingdata.get(i);
			if (noData.getType()==3||noData.getType()==4)continue;
			if (noData.getStates()!=0)continue;
			if (noData.getCamp()!=camp)continue;
			int noman=noData.getMan();
			if (man==noman)continue;
			if (man==noman+1||man==noman-1||man==noman+5||man==noman-5) {
				if (man==5&&noman==4)continue;
				if (man==4&&noman==5)continue;
	            if (noData.xzstate("隐身")!=null) {
					datas.add(noData);	
				}
			}
		}
		return datas;
	}
	
	/**获取一个与目标同阵营的隐身单位*/
	public List<ManData> getYSDR(ManData data){
		List<ManData> datas=new ArrayList<>();
		int camp=data.getCamp();
		int man=data.getMan();
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData noData=fightingdata.get(i);
			if (noData.getType()==3||noData.getType()==4)continue;
			if (noData.getStates()!=0)continue;
			if (noData.getCamp()!=camp)continue;
			int noman=noData.getMan();
			if (man==noman)continue;
            if (noData.xzstate("隐身")!=null) {
				datas.add(noData);	
			}
		}
		return datas;
	}
	
	/**获取该单位的同排单位*/
	public List<ManData> getTP(int camp,FightingEvents events){
		List<ManData> datas=new ArrayList<>();
		int v=0;
		if (events.getAccepterlist()!=null&&events.getAccepterlist().size()!=0) {
    		v=events.getAccepterlist().get(0).getMan()/5;
    	}
		for (int i = fightingdata.size()-1; i >=0; i--) {
			ManData noData=fightingdata.get(i);
			if (noData.getType()==3||noData.getType()==4)continue;
			if (noData.getStates()!=0)continue;
			if (noData.getCamp()!=camp)continue;
			int noman=noData.getMan();
			if (v==noman||v==noman-1||v==noman-2||v==noman-3||v==noman-4) {
				if (noData.xzstate("封印")==null&&noData.xzstate("隐身")==null) {
					datas.add(noData);	
				}
			}
		}
		return datas;
	}
	/**获取偷钱金额*/
	public long getStealing(){
		 long money=0;
		 for (int i = 0; i < fightingdata.size(); i++) {
			ManData data=fightingdata.get(i);
			if (data.getType()==0) {
				money=data.getMoney();
			}else if (data.getType()==2) {
				if (data.getStates()==2) {
					money=0;
					break;
				}
			}
		}
		 return money;
	}
	/**百分制概率*/
	public static boolean isV(double value){
		return value>random.nextInt(100);
	} 
	/**根据名字获取对应mandata*/
	public ManData getBattleEndData(String name){
		for (int i = fightingdata.size()-1;i>=0;i--) {
			ManData manData=fightingdata.get(i);
			if (manData.getType()!=0||manData.getStates()==2) {
				continue;
			}
			if (manData.getManname().equals(name)) {
				return manData;
			}
		}
		return null;
	}
	/**true 表示会掉 判断战斗类型是否会掉血法忠诚*/
	public boolean isFightType() {
		if (BattleType == 5 || BattleType == 11 || BattleType == 12 || BattleType == 31 || BattleType == 33 || BattleType == 34) {
			return false;
		}
		return true;
	}
	/**判断是否有该种族*/
	public boolean isRace(int value){
		int sex=value%2;
		int race=value<=2?10001:value<=4?10002:value<=6?10003:value<=8?10004:10005;		
		String raceSting=null;
		for (int i=fightingdata.size()-1;i>=0;i--) {
			ManData manData=fightingdata.get(i);
			if (manData.getCamp()!=1) {continue;}
			if (manData.getType()==0) {//判断人物
				if (Sepcies_MixDeal.getSex(manData.getSe())==sex&&Sepcies_MixDeal.getRace(manData.getSe())==race) {
					return true;
				}
			}else if (manData.getType()==2) {//判断伙伴
//				男人-1,女人-2,男魔-3,女魔-4,男仙-5,女仙-6,男鬼-7,女鬼-8,男龙-9,女龙-10
//				魔族（男） 仙族（男） 人族（男） 鬼族（男）
//				仙族（女） 魔族（女） 鬼族（男） 仙族（女）
//				人族（男） 鬼族（女）
				PalData palData=GameServer.getPalData(manData.getId());
				if (palData==null) {continue;}
				if (raceSting==null) {
					raceSting=value==1?"人族（男）":value==2?"人族（女）":value==3?"魔族（男）":value==4?"魔族（女）":value==5?"仙族（男）":value==6?"仙族（女）":value==7?"鬼族（男）":value==8?"鬼族（女）":value==9?"龙族（男）":"龙族（女）";
				}
				if (palData.getTrait().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
}
