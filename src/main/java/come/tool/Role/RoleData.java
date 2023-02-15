package come.tool.Role;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.github.pagehelper.util.StringUtil;
import come.tool.Stall.AssetUpdate;
import io.netty.channel.ChannelHandlerContext;
import org.come.action.sys.enterGameAction;
import org.come.bean.LoginResult;
import org.come.bean.UseCardBean;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.MountSkill;
import org.come.entity.PackRecord;
import org.come.entity.Record;
import org.come.entity.RoleSummoning;
import org.come.handler.SendMessage;
import org.come.model.Npctable;
import org.come.model.TaskProgress;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.Goodtype;
import org.come.until.AllServiceUtil;

import come.tool.Calculation.BaseQl;
import come.tool.Calculation.BaseSkill;
import come.tool.Calculation.BaseValue;
import come.tool.Mixdeal.CreepsMixdeal;
import come.tool.newTask.Task;
import come.tool.newTask.TaskRecord;
import come.tool.newTask.TaskUtil;
import org.come.until.GsonUtil;

public class RoleData {
	//标识玩家id
	private BigDecimal roleid;
	//玩家IP
	private String IP;
	//玩家数据
	private LoginResult loginResult;
	//私密数据
	private PrivateData privateData;
	//背包记录数据
	private PackRecord packRecord;
	//玩家系统设置
	private RoleSystem roleSystem;
	//任务数据
	private List<Task> tasks;
	//任务记录
	private ConcurrentHashMap<Integer,TaskRecord> taskRecordMap;
	//装备ID
	private BigDecimal[] goodEquip;
	/**时效数据*/
	private ConcurrentHashMap<String,UseCardBean> limitMap;
	//技能
	private List<BaseSkill> skills;
	//修正属性
	private BaseQl[] borns;
	//小成修炼抗性
	private BaseQl[] xcxls;
	//大成修炼抗性
	private BaseQl[] dcxls;
	//帮派守护神抗性 帮派抗性
	private BaseQl[] bpxls;
	//经脉抗性
	private BaseQl[] jmxls;
	private BaseQl[] xpxls;
	//召唤兽
	private List<Hang> pets;
	//灵宝
	private Hang ls;
	//法宝
	private List<Hang> fs;
	//参战的伙伴
	private List<BigDecimal> ps;
	private int fzlvl;//法宝总评分
	//乘骑坐骑的ID
	private BigDecimal mid;
	//背包数量
	private int goodNum;
	//携带上限
	private int goodMax;
	private int line;
	//玩家属性
	private CBGData cbgData;
	//玩家属性
	public RoleData(LoginResult loginResult, List<Goodstable> goods, List<RoleSummoning> pets2, List<Lingbao> lingbaos, List<Baby> babys, List<Mount> mounts2) {
		// TODO Auto-generated constructor stub
		this.loginResult=loginResult;
		this.roleid=loginResult.getRole_id();
		this.loginResult.setRoleData(this);
		packRecord=AllServiceUtil.getPackRecordService().selectByPrimaryKey(roleid);
		if (packRecord==null) {
			packRecord=new PackRecord();
			packRecord.setRoleId(roleid);
			packRecord.setRecord("0-0");
			AllServiceUtil.getPackRecordService().insert(packRecord);
		}
		this.roleSystem=new RoleSystem();
		roleTransfer(loginResult);
		if (loginResult.getResistance()!=null&&!loginResult.getResistance().equals("")) {
			String[] v=loginResult.getResistance().split("\\|");
			List<BaseQl> kxlist = new ArrayList<BaseQl>();
			for (int i = 0; i < v.length; i++) {
				if (v[i].startsWith("主-")){
					String zhu = v[i].substring(2);
					if (zhu.length() > 0) {
						//获取捐献帮派的金额
						BigDecimal achi = loginResult.getAchievement();
						kxlist.add(new BaseQl(zhu, BaseValue.getBangQuality(achi, true)));
					}
				}
				if (v[i].startsWith("副-")){
					String fu = v[i].substring(2);
					if (fu.length() > 0) {
						BigDecimal achi = loginResult.getAchievement();
						kxlist.add(new BaseQl(fu, BaseValue.getBangQuality(achi, false)));
					}
				}
				if (v[i].startsWith("X")||v[i].startsWith("D")) {
					String[] vs=v[i].split("#");
					vs[0]=vs[0].substring(1);
					if (v[i].startsWith("X")) {
						xcxls=BaseValue.xls(vs);
					} else {
						dcxls=BaseValue.xls(vs);
					}
				}
			}
			if (kxlist.size() > 0) {
				bpxls = new BaseQl[kxlist.size()];
				for (int i = 0 ; i < kxlist.size() ; i++) {
					bpxls[i] = kxlist.get(i);
				}
			}
		}
		if (StringUtil.isNotEmpty(loginResult.getMeridians())) {
			jmxls = loginResult.getBaseQl();
		}
		if (StringUtil.isNotEmpty(loginResult.getXingpans())) {
			xpxls = loginResult.getBaseQl1();
		}
		/**包裹卡转身数据*/
		String cb=null;//装备的翅膀皮肤
		goodEquip=new BigDecimal[14];
		pets=new ArrayList<>();
		fs=new ArrayList<>();
		//包裹卡使用数量>=2
		if (loginResult.getAttachPack() > 2) {loginResult.setAttachPack(2);}
		goodMax = 24+(loginResult.getAttachPack()+(loginResult.getTurnAround()>=4?3:loginResult.getTurnAround())) * 24;
		int s=0; //拥有6阶仙器/神兵数量
		long weaponSkin=0;
		for (int i = goods.size()-1; i>=0; i--) {
			Goodstable good=goods.get(i);
			if (good.getStatus()==0&&good.getType()!=8888) {
				goodNum++;
			}
			if (good.getStatus()!=1) {continue;}
			int type=Goodtype.EquipmentType(good.getType());
			if (type==-1) {continue;}
			if (type==10&&goodEquip[type]!=null) {goodEquip[11]=good.getRgid();}
			else {goodEquip[type]=good.getRgid();}
			if (type==12){cb=good.getSkin();}
			//获取物品皮肤
			if (type==0) {weaponSkin=CreepsMixdeal.good(Integer.parseInt(good.getSkin()));}

			// 身上装备是神兵
			if (Goodtype.GodEquipment_God(good.getType())) {
				for (BaseQl ql : good.getEquip().getQls()) {
					if (ql.getKey().equals("等级") && ql.getValue() == 6) {
						s++;
						break;
					}
				}
			} else if (Goodtype.GodEquipment_xian(good.getType())) {
				for (BaseQl ql : good.getEquip().getQls()) {
					if (ql.getKey().equals("阶数") && ql.getValue() == 6) {
						s++;
						break;
					}
				}
			}else if (Goodtype.GodEquipment_Ding(good.getType())) {
				for (BaseQl ql : good.getEquip().getQls()) {
					if (ql.getKey().equals("阶数") && ql.getValue() == 6) {
						s++;
						break;
					}
				}
			}
		}

		for (int i = pets2.size()-1; i >=0; i--) {
			RoleSummoning pet=pets2.get(i);
			pets.add(new Hang(pet.getSid()));
			if (pet.getInnerGoods()==null||pet.getInnerGoods().equals("")) {continue;}
			boolean is=false;
			String[] innerGoodses = pet.getInnerGoods().split("\\|");
			for (String string : innerGoodses) {
				BigDecimal id = new BigDecimal(string);
				Goodstable innerGoods = null;
				for (int j = goods.size() - 1; j >= 0; j--) {
					if (goods.get(j).getType() == 750) {
						if (id.compareTo(goods.get(j).getRgid()) == 0) {
							innerGoods = goods.get(j);
							if (innerGoods.getStatus() != 1) {
								AllServiceUtil.getGoodsTableService().updateGoodsIndex(innerGoods,null,null,1);
							}
							break;
						}
					}
				}
				if (innerGoods == null) {
					StringBuffer buffer = new StringBuffer();
					for (String string2 : innerGoodses) {
						if (!string2.equals(string)) {
							if (buffer.length() != 0) {buffer.append("|");}
							buffer.append(string);
						}
					}
					pet.setInnerGoods(buffer.toString());
					is = true;
				}
			}
			if (is) {AllServiceUtil.getRoleSummoningService().updateRoleSummoning(pet);}
		}
		for (int i = lingbaos.size()-1; i >=0; i--) {
			Lingbao lingbao=lingbaos.get(i);
			if (lingbao.getBaotype().equals("法宝")) {
				fzlvl+=lingbao.getLingbaolvl().intValue();
				fzlvl+=BaseValue.getQv(lingbao.getBaoquality());
				if (lingbao.getEquipment()==1) {
					fs.add(new Hang(lingbao, 1));
				}
			}else if (lingbao.getEquipment()==1) {
				ls=new Hang(lingbao,0);
			}
		}
		for (int i = mounts2.size()-1; i >=0; i--) {
			Mount mount=mounts2.get(i);
			if (loginResult.getMount_id()!=null&&mount.getMountid()==loginResult.getMount_id()) {
				mid=mount.getMid();
			}
			if (mount.getMountskill() == null) {
				List<MountSkill> mountskill = new ArrayList<>();
				mount.setMountskill(mountskill);
				AllServiceUtil.getMountService().updateMountRedis(mount);
			} else if (mount.getMountskill().size() > 2) {// 删除坐骑所有技能
				AllServiceUtil.getMountskillService().deleteMountskills(mount.getMid());
				mount.getMountskill().clear();
				AllServiceUtil.getMountService().updateMountRedis(mount);
			}
			if (mount.getSid()==null&&mount.getSid3()==null&&mount.getOthrersid()==null) {
				continue;
			}
			for (int j = pets.size()-1; j >=0; j--) {
				pets.get(j).initSid(mount);
			}
		}
		ps=new ArrayList<>();
		if (loginResult.getPals()!=null&&!loginResult.getPals().equals("")) {
			String[] vs=loginResult.getPals().split("\\|");
			for (int i = 0; i < vs.length; i++) {
				ps.add(new BigDecimal(vs[i]));
			}
		}
		String eSkin=null;
		UseCardBean limit=limitMap.get("童卡");
		if (limit==null) {limit=limitMap.get("变身卡");}
		if (limit!=null) {
			Double value=limit.getQlKey("皮肤");
			if (value!=null) {eSkin=value.intValue()+"";}
		}
		if (eSkin==null&&weaponSkin!=0 && s==5) {
			long se=loginResult.getSpecies_id().longValue();
			//TODO 获取光武皮肤
			if ((weaponSkin == 1 && se == 20001|| weaponSkin == 2 && se == 20001 || weaponSkin == 1 && se == 20002 ||
					weaponSkin == 3 && se == 20002  || weaponSkin == 4 && se == 20003 || weaponSkin == 5 && se == 20003 ||
					weaponSkin == 9 && se == 20004  || weaponSkin == 8 && se == 20004 || weaponSkin == 10 && se == 20005 ||
					weaponSkin == 7 && se == 20005  || weaponSkin == 10 && se == 20006 || weaponSkin == 12 && se == 20006 ||
					weaponSkin == 1 && se == 20007  || weaponSkin == 5 && se == 20007 || weaponSkin == 1 && se == 20008 ||
					weaponSkin == 10 && se == 20008 || weaponSkin == 2 && se == 20009 || weaponSkin == 6 && se == 20009 ||
					weaponSkin == 8 && se == 20010  || weaponSkin == 1 && se == 20010 || weaponSkin == 12 && se == 21001 ||
					weaponSkin == 7 && se == 21001  || weaponSkin == 10 && se == 21002 || weaponSkin == 13 && se == 21002 ||
					weaponSkin == 10 && se == 21003 || weaponSkin == 12 && se == 21003 || weaponSkin == 9 && se == 21004 ||
					weaponSkin == 10 && se == 21004 || weaponSkin == 7 && se == 21005 || weaponSkin == 1 && se == 21005 ||
					weaponSkin == 14 && se == 21006 || weaponSkin == 8 && se == 21006 || weaponSkin == 12 && se == 21007 ||
					weaponSkin == 4 && se == 21007  || weaponSkin == 10 && se == 21008|| weaponSkin == 11 && se == 21008 ||
					weaponSkin == 10 && se == 21009 || weaponSkin == 4 && se == 21009 || weaponSkin == 14&& se == 21010||
					weaponSkin == 9 && se == 21010  || weaponSkin == 12 && se == 2200 || weaponSkin == 3 && se == 22001||
					weaponSkin == 14 && se == 22002 || weaponSkin == 1 && se == 22002 || weaponSkin == 7&& se == 22003||
					weaponSkin == 14 && se == 22003 || weaponSkin == 10 && se == 22004 || weaponSkin == 5&& se == 22004||
					weaponSkin == 7 && se == 22005  || weaponSkin == 16&& se == 22005 || weaponSkin == 1&& se == 22006||
					weaponSkin == 12 && se == 22006 || weaponSkin == 12 && se == 22007 || weaponSkin == 14 && se == 22007 ||
					weaponSkin == 11 && se == 22008 || weaponSkin == 16 && se == 22008 || weaponSkin == 1&& se == 22009||
					weaponSkin == 13 && se == 22009 || weaponSkin == 16 && se == 22010|| weaponSkin == 17 && se == 22010 ||
					weaponSkin == 1&& se == 23001   || weaponSkin == 10 && se == 23001 || weaponSkin == 12&& se == 23002 ||
					weaponSkin == 5 && se == 23002  || weaponSkin == 13 && se == 23003|| weaponSkin == 6 && se == 23003||
					weaponSkin == 9 && se == 23004  || weaponSkin == 8 && se == 23004|| weaponSkin == 17&& se == 23005L ||
					weaponSkin == 11 && se == 23005 || weaponSkin == 11 && se == 23006 || weaponSkin == 16 && se == 23006||
					weaponSkin == 1 && se == 24001  || weaponSkin == 6 && se == 24001|| weaponSkin == 12 && se == 24002||
					weaponSkin == 10 && se == 24002 || weaponSkin == 18 && se == 24003|| weaponSkin == 11&& se == 24003||
					weaponSkin == 9 && se == 24004  || weaponSkin == 3 && se == 24004 || weaponSkin == 18&& se == 24005||
					weaponSkin == 12 && se == 24005 || weaponSkin == 1&& se == 24006 || weaponSkin == 17 && se == 24006)) {
				weaponSkin += 18;
			}
			eSkin=((weaponSkin<<32)|loginResult.getSpecies_id().longValue())+"";
		}
		//获取皮肤
		loginResult.setSkin(enterGameAction.getskin(eSkin,packRecord.getPutTX(),loginResult.getTitle(),cb));
		loginResult.setRoleShow(new RoleShow(loginResult));
	}
	/**转移loginResult数据*/
	public void roleTransfer(LoginResult loginResult){
		synchronized (this) {
			privateData=new PrivateData();
			privateData.setDBExp(loginResult.getDBExp());loginResult.setDBExp(null);
			privateData.setTaskComplete(loginResult.getTaskComplete());loginResult.setTaskComplete(null);
			privateData.setTaskData(loginResult.getTaskData());loginResult.setTaskData(null);
			privateData.setSkills(loginResult.getSkills());loginResult.setSkills(null);
			privateData.setBorn(loginResult.getBorn());loginResult.setBorn(null);
			privateData.setTimingGood(loginResult.getTimingGood());loginResult.setTimingGood(null);
			skills  =BaseValue.reSkill(privateData,loginResult);
			borns   =BaseValue.reborn(privateData.getBorn());
			tasks   =TaskUtil.initTask(privateData.getTaskData());
			taskRecordMap=TaskUtil.initTaskRecord(privateData.getTaskComplete());
			limitMap=privateData.initLimit(loginResult.getPaysum().longValue());
		}
	}
	/**恢复loginResult数据*/
	public void roleRecover(LoginResult loginResult){
		synchronized (this) {
			if (privateData==null) {
				AllServiceUtil.getRecordService().insert(new Record(0,"恢复loginResult数据时对象为空"));
				return;
			}
			loginResult.setDBExp(privateData.getDBExp());
			loginResult.setTaskComplete(privateData.getTaskComplete());
			loginResult.setSkills(privateData.getSkills());
			loginResult.setBorn(privateData.getBorn());
			//生成任务数据
			StringBuffer buffer=new StringBuffer();
			for (int i = tasks.size()-1; i >=0; i--) {
				Task task=tasks.get(i);
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append(task.getTaskId());
				buffer.append("=");
				buffer.append(task.getTaskState());
				if (task.getTime()!=0) {buffer.append("=T");buffer.append(task.getTime()/1000);}
				TaskUtil.Progress(task, buffer);
			}
			loginResult.setTaskData(buffer.toString());
			buffer.setLength(0);
//			时效名#时效类型#时效皮肤#剩余时效#时效描述
			for (UseCardBean limit:limitMap.values()) {
				if (limit.getType().equals("SVIP")) {
					continue;
				}
				if (buffer.length()!=0) {
					buffer.append("^");
				}
				buffer.append(limit.getName());
				buffer.append("#");
				buffer.append(limit.getType());
				buffer.append("#");
				buffer.append(limit.getSkin());
				buffer.append("#");
				buffer.append(limit.getTime()/60000);
				if (limit.getValue()!=null&&!limit.getValue().equals("")) {
					buffer.append("#");
					buffer.append(limit.getValue());
				}
			}
			loginResult.setTimingGood(buffer.toString());
			loginResult.setTaskComplete(TaskUtil.toTaskRecord(taskRecordMap));
		}
	}
	/**清除任务集合 9超时取消任务超时*/
	public String removeTasks(int type,String... ids){
		StringBuffer buffer=null;
		for (int i = 0; i < ids.length; i++) {
			int taskID=Integer.parseInt(ids[i]);
			for (int j = tasks.size()-1;j>=0;j--) {
				Task task=tasks.get(j);
				if (taskID==task.getTaskId()) {
					if (buffer==null) {buffer=new StringBuffer();}
					if (buffer.length()!=0) {buffer.append("|");}
					buffer.append(taskID);
					buffer.append("=");
					buffer.append(type);
					tasks.remove(j);
					break;
				}
			}
		}
		return buffer==null?null:buffer.toString();
	}
	public void removeTask(int taskID){
		for (int j = tasks.size()-1;j>=0;j--) {
			Task task=tasks.get(j);
			if (taskID==task.getTaskId()) {
				tasks.remove(j);
				break;
			}
		}
	}
	/**添加任务*/
	public void addTask(Task task,boolean is){
		removeTask(task.getTaskId());
		tasks.add(is?task:task.FZ());
	}
	/**获取任务*/
	public Task getTask(int taskId){
		for (int i = tasks.size()-1;i>=0;i--) {
			Task task=tasks.get(i);
			if (taskId==task.getTaskId()) {
				return task;
			}
		}
		return null;
	}
	/**获取任务记录*/
	public TaskRecord getTaskRecord(int taskSetId){
		return taskRecordMap.get(taskSetId);
	}
	/**获取领取次数*/
	public int getTaskLQ(int taskSetId){
		TaskRecord record=taskRecordMap.get(taskSetId);
		return record!=null?record.getrSum():0;
	}
	/**获取完成次数*/
	public int getTaskWC(int taskSetId){
		TaskRecord record=taskRecordMap.get(taskSetId);
		return record!=null?record.getcSum():0;
	}
	/**添加*/
	public void addTaskRecord(TaskRecord taskRecord){
		taskRecordMap.put(taskRecord.getTaskId(), taskRecord);
	}
	/**添加完成次数 返回最终完成次数*/
	public int addTaskRecordWC(int taskSetId){
		TaskRecord record=taskRecordMap.get(taskSetId);
		if (record==null) {
			record=new TaskRecord(taskSetId);
			taskRecordMap.put(taskSetId, record);
		}
		record.addCSum(1);
		return record.getcSum();
	}
	/**判断robotId是否合法*/
	public boolean isRobotId(int robotId){
		for (int i = tasks.size()-1;i>=0;i--) {
			List<TaskProgress> progress=tasks.get(i).getProgress();
			for (int j = progress.size()-1; j >=0; j--) {
				TaskProgress ps=progress.get(j);
				if (ps.getSum()<ps.getMax()) {
					if (ps.getType()==0||ps.getType()==1) {
						if (ps.getDId()==robotId) {
							return false;
						}
					}else if (ps.getType()==2) {
						Npctable npctable=GameServer.getNpc(ps.getDId()+"");
						if (npctable!=null) {
							if (npctable.getRobotID()==robotId) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	/**添加时效符*/
	public void addLimit(UseCardBean limit){
		limitMap.put(limit.getType(), limit);
	}
	/**删除时效符*/
	public UseCardBean removeLimit(String type){
		return limitMap.remove(type);
	}
	/**获取时效符*/
	public UseCardBean getLimit(String type){
		return limitMap.get(type);
	}
	public PackRecord getPackRecord() {
		if (packRecord.getRecord()==null) {
			packRecord.setRecord("0-0");
		}
		return packRecord;
	}
	public void setPackRecord(PackRecord packRecord) {
		this.packRecord = packRecord;
	}
	public LoginResult getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(LoginResult loginResult) {
		this.loginResult = loginResult;
	}
	public RoleSystem getRoleSystem() {
		return roleSystem;
	}
	public void setRoleSystem(RoleSystem roleSystem) {
		this.roleSystem = roleSystem;
	}
	public PrivateData getPrivateData() {
		return privateData;
	}
	public void setPrivateData(PrivateData privateData) {
		this.privateData = privateData;
	}
	public void upPrivateData(PrivateData privateData) {
		if (!this.privateData.getBorn().equals(privateData.getBorn())) {
			this.privateData.setBorn(privateData.getBorn());
			borns=BaseValue.reborn(privateData.getBorn());
		}
		if (!this.privateData.getSkills().equals(privateData.getSkills())) {
			this.privateData.setSkills(privateData.getSkills());
			skills=BaseValue.reSkill(this.privateData,loginResult);
		}
	}
	public BaseQl[] getXls(int type) {
		if (type==1) {
			return xcxls;
		} else if(type == 40) {
			return bpxls;
		} else if(type == 41) {
			return jmxls;
		} else if(type == 42) {
			return xpxls;
		}else {
			return dcxls;
		}
	}
	public void setXls(int type,BaseQl[] xls) {
		if (type==1) {
			this.xcxls = xls;
		} else if(type == 40) {
			this.bpxls = xls;
		} else if(type == 41) {
			this.jmxls = xls;
		} else if(type == 42) {
			this.xpxls = xls;
		} else {
			this.dcxls = xls;
		}
	}
	public List<BaseSkill> getSkills() {
		return skills;
	}
	public void setSkills(List<BaseSkill> skills) {
		this.skills = skills;
	}
	public BaseQl[] getBorns() {
		return borns;
	}
	public void setBorns(BaseQl[] borns) {
		this.borns = borns;
	}
	public Hang getLs() {
		return ls;
	}
	public void setLs(Hang ls) {
		this.ls = ls;
	}
	public List<Hang> getFs() {
		return fs;
	}
	public void setFs(List<Hang> fs) {
		this.fs = fs;
	}

	public List<Hang> getPets() {
		return pets;
	}
	public BigDecimal[] getGoodEquip() {
		return goodEquip;
	}
	public void setGoodEquip(BigDecimal[] goodEquip) {
		this.goodEquip = goodEquip;
	}

	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	/**修改已携带的装备 ture添加 false删除*/
	public void CEquip(BigDecimal rgid,int type,boolean is){
		if (is) {
			if (type==10) {
				if (goodEquip[type]!=null) {
					type=11;
				}
			}
			goodEquip[type]=rgid;
		}else {
			if (type==10) {
				if (goodEquip[type]!=null&&goodEquip[type].compareTo(rgid)!=0) {
					type=11;
				}
			}
			if (goodEquip[type]!=null&&goodEquip[type].compareTo(rgid)==0) {
				goodEquip[type]=null;
			}
		}
	}
	/**修改携带的召唤兽*/
	public void CPet(BigDecimal sid,boolean is){
		for (int i = pets.size()-1; i >=0; i--) {
			Hang hang=pets.get(i);
			if (hang.getId().compareTo(sid)==0) {
				pets.remove(i);
				break;
			}
		}
		if (is) {
			pets.add(new Hang(sid));
		}
	}
	/**更改管制的召唤兽*/
	public void MPet(Mount mount,boolean is){
		for (int i = pets.size()-1; i >=0; i--) {
			Hang hang=pets.get(i);
			if (hang.getMid()!=null) {
				if (hang.getMid().compareTo(mount.getMid())==0) {
					if (is) {
						if (mount.getSid()!=null&&hang.getId().compareTo(mount.getSid()) == 0) {
							continue;
						}else if (mount.getOthrersid()!=null&&hang.getId().compareTo(mount.getOthrersid())==0) {
							continue;
						}else if (mount.getSid3()!=null&&hang.getId().compareTo(mount.getSid3())==0) {
							continue;
						}
					}
					hang.setMid(null);
				}
			}else {
				if (is) {
					if (mount.getSid()!=null&&hang.getId().compareTo(mount.getSid()) == 0) {
						hang.setMid(mount.getMid());
					}else if (mount.getOthrersid()!=null&&hang.getId().compareTo(mount.getOthrersid())==0) {
						hang.setMid(mount.getMid());
					}else if (mount.getSid3()!=null&&hang.getId().compareTo(mount.getSid3())==0) {
						hang.setMid(mount.getMid());
					}
				}
			}
		}
	}
	/**修改已携带的灵宝 ture添加 false删除*/
	public void CLing(BigDecimal baoId,String lx,boolean is){
		if (lx.equals("法宝")) {
			for (int i = fs.size()-1; i>=0; i--) {
				Hang hang=fs.get(i);
				if (hang.getId().compareTo(baoId)==0) {
					fs.remove(i);
					break;
				}
			}
			if (is) {
				fs.add(new Hang(baoId));
			}
		}else {
			if (is) {
				ls=new Hang(baoId);
			}else if (ls!=null&&ls.getId().compareTo(baoId)==0) {
				ls=null;
			}
		}

	}
	public BigDecimal getMid() {
		return mid;
	}
	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}
//	public void addFzlvl(int add) {
//		fzlvl+=add;
//	}
	/**判断是否满背包 true 满*/
	public boolean isGoodFull(){
		return goodNum>=goodMax;
	}
	/**修改背包当前数量*/
	public void upGoodNum(int num){
		goodNum+=num;
	}
	public int getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}
	public int getGoodMax() {
		return goodMax;
	}
	public void setGoodMax(int goodMax) {
		this.goodMax = goodMax;
	}
	public ConcurrentHashMap<Integer, TaskRecord> getTaskRecordMap() {
		return taskRecordMap;
	}
	/**获取参战的伙伴数量*/
	public int PSize(){
		return ps.size();
	}
	public List<BigDecimal> getPs() {
		return ps;
	}
	public boolean upLine() {
		synchronized (this) {
			line--;
			return line<=-3;
		}
	}
	public void setLine(int value) {
		synchronized (this) {this.line=value;}
	}
	public CBGData getCbgData() {
		return cbgData;
	}
	public void setCbgData(CBGData cbgData) {
		this.cbgData = cbgData;
	}
	/**包裹卡*/
	public void upPackNum(Goodstable good, ChannelHandlerContext ctx, LoginResult login) {
		AssetUpdate assetUpdate = new AssetUpdate();
		String msg = good.getGoodsname() + "最多只能使用两次";
		assetUpdate.setType(AssetUpdate.USEGOOD);
		if (login.getAttachPack() <= 2) {
			login.setAttachPack(login.getAttachPack() + 1);
			this.goodMax = 24 + (login.getAttachPack() + (login.getTurnAround() >= 4 ? 3 : login.getTurnAround())) * 24;
			AllServiceUtil.getGoodsrecordService().insert(good, null, 1, 9);
			AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
			msg = good.getGoodsname() + "#G使用成功";
		}

		assetUpdate.setMsg(msg);
		SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
	}

}
