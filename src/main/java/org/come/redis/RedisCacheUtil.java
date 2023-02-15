package org.come.redis;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.come.action.pack.PackChangeAction;
import org.come.action.suit.SuitComposeAction;
import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.entity.Friend;
import org.come.entity.Gang;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.MountSkill;
import org.come.entity.PackRecord;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;
import org.come.entity.Salegoods;
import org.come.entity.Titletable;
import org.come.entity.UserTable;
import org.come.handler.MainServerHandler;
import org.come.server.GameServer;
import org.come.thread.RedisEqualWithSqlThread;
import org.come.tool.EquipTool;
import org.come.tool.GZip;
import org.come.tool.Goodtype;
import org.come.tool.ReadExelTool;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.CreateTextUtil;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;
import org.come.until.TimeUntil;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;

import come.tool.Scene.LTS.UserData;
import come.tool.Scene.PKLS.lsteamBean;
import come.tool.Transplant.FriendTransplant;
import come.tool.Transplant.GangTransplant;
import come.tool.Transplant.RoleDataBean;
import come.tool.Transplant.TransplantBean;
import come.tool.Transplant.UserDataBean;

public class RedisCacheUtil {
	
	// 表ID
	public static BigDecimal ADD=new BigDecimal(1000);
	private static BigDecimal role_pk;//人物id
	private static BigDecimal user_pk;//账号id
	private static BigDecimal baby_pk;//宝宝id
	private static BigDecimal goods_pk;//物品id
	private static BigDecimal mount_pk;//坐骑id
	private static BigDecimal pet_pk;//召唤兽id
	private static BigDecimal lingbao_pk;//灵宝id
	private static BigDecimal pal_pk;//伙伴id
	private static long record_pk;//物品记录最大值
	
	private static BigDecimal oneAreanNotes_pk;//单人竞技场战报ID
	
	public static BigDecimal oneAreanNotes_min=new BigDecimal(0);//战报3天内最小的ID
	
	private static RedisCacheBean<Goodstable> goodBean;
	private static RedisCacheBean<RoleSummoning> petBean;
	private static RedisCacheBean<Lingbao> lingbaoBean;
	private static RedisCacheBean<Baby> babyBean;
	private static RedisCacheBean<Mount> mountBean;
	private static RedisCacheBean<Pal> palBean;
	static{
		goodBean=new RedisCacheBean<>(1);
		petBean=new RedisCacheBean<>(2);
		lingbaoBean=new RedisCacheBean<>(3);
		babyBean=new RedisCacheBean<>(4);
		mountBean=new RedisCacheBean<>(5);
		palBean=new RedisCacheBean<>(6);
	}
	public static RedisCacheBean<?> getRedisCacheBean(String Key){
		if (Key.equals(RedisParameterUtil.GOODS)) {return goodBean;}
		else if (Key.equals(RedisParameterUtil.PET)) {return petBean;}
		else if (Key.equals(RedisParameterUtil.LINGBAO)) {return lingbaoBean;}
		else if (Key.equals(RedisParameterUtil.BABY)) {return babyBean;}
		else if (Key.equals(RedisParameterUtil.MOUNT)) {return mountBean;}
		else if (Key.equals(RedisParameterUtil.PAL)) {return palBean;}
		return null;
	}
	/**键值*/
	public void InitZhu(){
		Jedis jedis = RedisPoolUntil.getJedis();
		goods_pk=getMax(jedis.hkeys(RedisParameterUtil.GOODS));
		lingbao_pk=getMax(jedis.hkeys(RedisParameterUtil.LINGBAO));
		pet_pk=getMax(jedis.hkeys(RedisParameterUtil.PET));
		baby_pk=getMax(jedis.hkeys(RedisParameterUtil.BABY));
		mount_pk=getMax(jedis.hkeys(RedisParameterUtil.MOUNT));
		pal_pk=getMax(jedis.hkeys(RedisParameterUtil.PAL));
		RedisPoolUntil.returnResource(jedis);
		user_pk=AllServiceUtil.getUserTableService().selectUserMax();
		if (user_pk==null) {user_pk=new BigDecimal(GameServer.getQh());}
		role_pk=AllServiceUtil.getRoleTableService().selectRoleMax();
		if (role_pk==null) {role_pk=new BigDecimal(GameServer.getQh());}
		goods_pk=getGl(goods_pk, GameServer.getQh());
		lingbao_pk=getGl(lingbao_pk, GameServer.getQh());
		pet_pk=getGl(pet_pk, GameServer.getQh());
		baby_pk=getGl(baby_pk, GameServer.getQh());
		mount_pk=getGl(mount_pk, GameServer.getQh());
		pal_pk=getGl(pal_pk, GameServer.getQh());
		
		BigDecimal record=AllServiceUtil.getAppVersionService().selectSequence();
		record_pk=record!=null?record.longValue():System.currentTimeMillis();
		
		user_pk=getGl(user_pk, GameServer.getQh());
		if (role_pk.longValue()<10000000L*GameServer.getQh()) {
			role_pk=new BigDecimal(10000000L*GameServer.getQh());
		}
		
		oneAreanNotes_pk=AllServiceUtil.getOneArenaNotesService().selectMaxID(null);
		if (oneAreanNotes_pk==null) {oneAreanNotes_pk=new BigDecimal(GameServer.getQh());}
		oneAreanNotes_pk=getGl(oneAreanNotes_pk, GameServer.getQh());
		resetOneArenaTime();
		
		System.err.println("单人竞技场战报最大:"+oneAreanNotes_pk);
		System.err.println("物品最大:"+goods_pk);
		System.err.println("灵宝最大:"+lingbao_pk);
		System.err.println("召唤兽最大:"+pet_pk);
		System.err.println("宝宝最大:"+baby_pk);
		System.err.println("坐骑最大:"+mount_pk);
		System.err.println("伙伴最大:"+pal_pk);	
		System.err.println("角色最大:"+role_pk);	
		System.err.println("用户最大:"+user_pk);	
	}
	/**ID过滤*/
	public static BigDecimal getGl(BigDecimal id,int qh){
		long v=id.longValue();
		long ys=v%1000;
		if (ys==qh) {
			return id;
		}
		v+=ADD.longValue();
		v+=qh-ys;
		return new BigDecimal(v);
	}
	/**获取最大值*/
	public BigDecimal getMax(Set<String> set){
		BigDecimal max=new BigDecimal(0);
		for (String key : set) {
			try {
				BigDecimal a=new BigDecimal(key);
				if (a.compareTo(max)>0) {
					max=a;
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("错误key"+key);
			}
		}
		return new BigDecimal(max.longValue());
	}
	/**redis和数据库对比同步到数据库里面 redis主导*/
	public void dataTB(){
		RedisEqualWithSqlThread.AllToDatabase();
		// 物品表
		System.err.println("开始同步物品");
		List<Goodstable> goods = AllServiceUtil.getGoodsTableService().getAllGoods();
		Map<BigDecimal,Goodstable> goodmap1=new HashMap<>();
		for (int i = 0; i < goods.size(); i++) {
			goodmap1.put(goods.get(i).getRgid(),goods.get(i));
		}
		DataComparison(RedisParameterUtil.GOODS,goodmap1,Goodstable.class);
		goodmap1=null;
		// 灵宝表
		System.err.println("开始同步灵宝");
		List<Lingbao> lingbaos = AllServiceUtil.getLingbaoService().selectAllLingbao();
		Map<BigDecimal,Lingbao> lingmap1=new HashMap<>();
		for (int i = 0; i < lingbaos.size(); i++) {
			lingmap1.put(lingbaos.get(i).getBaoid(),lingbaos.get(i));
		}			
		DataComparison(RedisParameterUtil.LINGBAO,lingmap1,Lingbao.class);
		lingmap1=null;
		//pet表
		System.err.println("开始同步召唤兽");
		List<RoleSummoning> pets=AllServiceUtil.getRoleSummoningService().selectAllRoleSummonings();
		Map<BigDecimal,RoleSummoning> petmap1=new HashMap<>();
		for (int i = 0; i < pets.size(); i++) {
			petmap1.put(pets.get(i).getSid(),pets.get(i));
		}			
		DataComparison(RedisParameterUtil.PET,petmap1,RoleSummoning.class);
		petmap1=null;
		//haz表
		System.err.println("开始同步孩子");
		List<Baby> babys = AllServiceUtil.getBabyService().selectAllBaby();
		Map<BigDecimal,Baby> babymap1=new HashMap<>();
		for (int i = 0; i < babys.size(); i++) {
			babymap1.put(babys.get(i).getBabyid(),babys.get(i));
		}			
		DataComparison(RedisParameterUtil.BABY,babymap1,Baby.class);
		babymap1=null;
		// 坐骑表
		System.err.println("开始同步坐骑");
		List<Mount> mounts = AllServiceUtil.getMountService().selectAllMounts();
		Map<BigDecimal,Mount> mountmap1=new HashMap<>();
		for (int i = 0; i < mounts.size(); i++) {
			Mount mount=mounts.get(i);
			mountmap1.put(mount.getMid(),mount);
		}			
		DataComparison(RedisParameterUtil.MOUNT,mountmap1,Mount.class);
		mountmap1=null;
		System.err.println("开始同步伙伴");
		List<Pal> pals=AllServiceUtil.getPalService().selectAllPalSql();
		Map<BigDecimal,Pal> palmap1=new HashMap<>();
		for (int i = 0; i < pals.size(); i++) {
			Pal pal=pals.get(i);
			palmap1.put(pal.getId(),pal);
		}	
		DataComparison(RedisParameterUtil.PAL,palmap1,Pal.class);
		palmap1=null;
		WriteOut.TB();
		RedisEqualWithSqlThread.AllToDatabase();
	}
	/**对比加入操作记录*/
	public <T> void DataComparison(String key,Map<BigDecimal,T> map,Class<T> bean){
		
		Gson gson=GsonUtil.getGsonUtil().getgson();
		Jedis jedis = RedisPoolUntil.getJedis();
		Set<String> set=jedis.hkeys(key);
		RedisPoolUntil.returnResource(jedis);
		int size=0;
		int z=0;
		for (String ID : set) {
			size++;
			if (size>=1000) {
				size=0;z++;
				if (z%10==0) {System.err.println(z*1000);}	
			    try {Thread.sleep(1);} catch (Exception e) {}
		    }
			T t=map.remove(new BigDecimal(ID));
			if (t==null) {
				RedisControl.insertController(key,ID,RedisControl.CADD);	
			}else {
				Jedis jedis2 = RedisPoolUntil.getJedis();// 获取返回的字符串
				String a1 = jedis2.hget(key,ID);// 返回使用的连接
				RedisPoolUntil.returnResource(jedis2);	
				if (BD(t,gson.fromJson(a1,bean))) {	
					WriteOut.addtxt("REDIS:" +a1, 9999);
					WriteOut.addtxt("数据库:"  +gson.toJson(t), 9999);
					RedisControl.insertController(key,ID,RedisControl.CALTER);	
				}
			}
		}
		for (BigDecimal id : map.keySet()) { 
	         if (!set.contains(id.toString())) {
			     System.err.println("删除:"+key+":"+id);
			     RedisControl.insertController(key,id.toString(),RedisControl.CDELETE);
		     }
	    } 
	}
	/**2个对象对比
	 * @param <T>*/
	public <T> boolean BD(T t1,T t2){
		try {
			if (t1 instanceof Goodstable) {
				return BD0((Goodstable)t1,(Goodstable)t2);
			}else if (t1 instanceof Lingbao) {
				return BD1((Lingbao)t1,(Lingbao)t2);
			}else if (t1 instanceof RoleSummoning) {
				return BD2((RoleSummoning)t1,(RoleSummoning)t2);
			}else if (t1 instanceof Baby) {
				return BD3((Baby)t1,(Baby)t2);
			}else if (t1 instanceof Mount) {
				return BD4((Mount)t1,(Mount)t2);
			}else if (t1 instanceof Pal) {
				return BD5((Pal)t1,(Pal)t2);
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
	public boolean BD0(Goodstable t1,Goodstable t2){
		if (t1.getRgid().compareTo(t2.getRgid())!=0) {return true;}
		if (t1.getRole_id().compareTo(t2.getRole_id())!=0) {return true;}
		if (t1.getGoodsid().compareTo(t2.getGoodsid())!=0) {return true;}
		if (t1.getType()!=t2.getType()) {return true;}
		if (t1.getStatus().intValue()!=t2.getStatus().intValue()) {return true;}
		if (t1.getUsetime().intValue()!=t2.getUsetime().intValue()) {return true;}
		if (t1.getGoodlock()!=t2.getGoodlock()) {return true;}
		if (t1.getValue()==null) {t1.setValue("");}
		if (t2.getValue()==null) {t2.setValue("");}
		if (!t1.getValue().equals(t2.getValue())) {return true;}
		if (!t1.isQh(t2)) {return true;}
		return false;
	}
    public boolean BD1(Lingbao t1,Lingbao t2){
    	if (t1.getBaoactive().compareTo(t2.getBaoactive())!=0) {return true;}
     	if (!t1.getBaospeed().equals(t2.getBaospeed())) {return true;}
     	if (!t1.getBaoreply().equals(t2.getBaoreply())) {return true;}
     	if (!t1.getBaoshot().equals(t2.getBaoshot())) {return true;}
     	if (!t1.getBaoap().equals(t2.getBaoap())) {return true;}
     	if (!t1.getResistshot().equals(t2.getResistshot())) {return true;}
     	if (!t1.getAssistance().equals(t2.getAssistance())) {return true;}
     	if (t1.getRoleid().compareTo(t2.getRoleid())!=0) {return true;}
    	if (t1.getFushis()==null) {t1.setFushis("");}
		if (t2.getFushis()==null) {t2.setFushis("");}
		if (!t1.getFushis().equals(t2.getFushis())) {return true;}
		if (t1.getSkills()==null) {t1.setSkills("");}
		if (t2.getSkills()==null) {t2.setSkills("");}
		if (!t1.getSkills().equals(t2.getSkills())) {return true;}
		if (t1.getLingbaolvl().compareTo(t2.getLingbaolvl())!=0) {return true;}
		if (t1.getLingbaoexe().compareTo(t2.getLingbaoexe())!=0) {return true;}
		if (t1.getLingbaoqihe()!=t2.getLingbaoqihe()) {return true;}
		if (!t1.getKangxing().equals(t2.getKangxing())) {return true;}
	  	if (t1.getBaoid().compareTo(t2.getBaoid())!=0) {return true;}
		if (t1.getEquipment()!=t2.getEquipment()) {return true;}
		if (!t1.getBaoquality().equals(t2.getBaoquality())) {return true;}
		if (t1.getSkillsum().intValue()!=t2.getSkillsum().intValue()) {return true;}
		if (t1.getFusum().intValue()!=t2.getFusum().intValue()) {return true;}    	
    	return false;
	}
	private boolean BD2(RoleSummoning t1, RoleSummoning t2) {
		if (!t1.getSummoningid().equals(t2.getSummoningid())) {return true;}
		if (!t1.getSummoningname().equals(t2.getSummoningname())) {return true;}
		if (!t1.getSummoningskin().equals(t2.getSummoningskin())) {return true;}
		if (!t1.getSsn().equals(t2.getSsn())) {return true;}
		if (!t1.getStye().equals(t2.getStye())) {return true;}
		if (t1.getHp()!=t2.getHp()) {return true;}
		if (t1.getMp()!=t2.getMp()) {return true;}
		if (t1.getAp()!=t2.getAp()) {return true;}
		if (t1.getSp()!=t2.getSp()) {return true;}
		if (!t1.getGrowlevel().equals(t2.getGrowlevel())) {return true;}
		if (!t1.getResistance().equals(t2.getResistance())) {return true;}
		if (!t1.getGold().equals(t2.getGold())) {return true;}
		if (!t1.getWood().equals(t2.getWood())) {return true;}
		if (!t1.getSoil().equals(t2.getSoil())) {return true;}
		if (!t1.getWater().equals(t2.getWater())) {return true;}
		if (!t1.getFire().equals(t2.getFire())) {return true;}
		if (zf(t1.getColorScheme(), t2.getColorScheme())) {return true;}
		if (t1.getRoleid().compareTo(t2.getRoleid())!=0) {return true;}
		if (t1.getBone().intValue()!=t2.getBone().intValue()) {return true;}
		if (t1.getSpir().intValue()!=t2.getSpir().intValue()) {return true;}
		if (t1.getPower().intValue()!=t2.getPower().intValue()) {return true;}
		if (t1.getSpeed().intValue()!=t2.getSpeed().intValue()) {return true;}
		if (t1.getCalm().intValue()!=t2.getCalm().intValue()) {return true;}
		if (t1.getGrade().intValue()!=t2.getGrade().intValue()) {return true;}
		if (t1.getFaithful().intValue()!=t2.getFaithful().intValue()) {return true;}
		if (t1.getOpenSeal().intValue()!=t2.getOpenSeal().intValue()) {return true;}
		if (t1.getOpenql().intValue()!=t2.getOpenql().intValue()){return true;}
		if (t1.getBasishp()!=t2.getBasishp()) {return true;}
		if (t1.getBasismp()!=t2.getBasismp()) {return true;}
		if (t1.getFriendliness().longValue()!=t2.getFriendliness().longValue()) {return true;}
		if (t1.getExp().compareTo(t2.getExp())!=0) {return true;}
		if (t1.getSid().compareTo(t2.getSid())!=0) {return true;}
		if (zf(t1.getInnerGoods(), t2.getInnerGoods())) {return true;}
		if (zf(t1.getPetSkills(), t2.getPetSkills())) {return true;}
		if (t1.getDragon()!=t2.getDragon()) {return true;}
		if (zf(t1.getPetQlSkills(),t2.getPetQlSkills())){return true;}
		if (t1.getSpdragon()!=t2.getSpdragon()) {return true;}
		if (t1.getTurnRount()!=t2.getTurnRount()) {return true;}
		if (t1.getRevealNum()!=t2.getRevealNum()) {return true;}
		if (t1.getFlyupNum()!=t2.getFlyupNum()) {return true;}
		if (t1.getAlchemynum()!=t2.getAlchemynum()) {return true;}
		if (t1.getGrowUpDanNum()!=t2.getGrowUpDanNum()) {return true;}
		if (t1.getDraC()!=t2.getDraC()) {return true;}
		if (t1.getTrainNum()!=t2.getTrainNum()) {return true;}
		if (t1.getPetlock()!=t2.getPetlock()) {return true;}
		if (zf(t1.getNedanResistance(), t2.getNedanResistance())) {return true;}
		if (zf(t1.getBeastSkills(), t2.getBeastSkills())) {return true;}
		if (zf(t1.getFourattributes(), t2.getFourattributes())) {return true;}
		if (zf(t1.getSkillData(), t2.getSkillData())) {return true;}
		return false;
	}
    private boolean BD3(Baby t1, Baby t2) {
    	if (t1.getBabyid().compareTo(t2.getBabyid())!=0) {return true;}
		if (t1.getRoleid().compareTo(t2.getRoleid())!=0) {return true;}
		if (t1.getBabyage().intValue()!=t2.getBabyage().intValue()) {return true;}
		if (zf(t1.getOutcome(), t2.getOutcome())) {return true;}
		if (zf(t1.getTalents(), t2.getTalents())) {return true;}
		if (zf(t1.getParts(), t2.getParts())) {return true;}
    	return false;
	}
	private boolean BD4(Mount t1, Mount t2) {
		if (t1.getMountlvl().intValue()!=t2.getMountlvl().intValue()) {return true;}
		if (t1.getExp().intValue()!=t2.getExp().intValue()) {return true;}
		if (t1.getSpri().intValue()!=t2.getSpri().intValue()) {return true;}
		if (t1.getPower().intValue()!=t2.getPower().intValue()) {return true;}
		if (t1.getBone().intValue()!=t2.getBone().intValue()) {return true;}
		if (t1.getProficiency().intValue()!=t2.getProficiency().intValue()) {return true;}
		if (t1.getUseNumber().intValue()!=t2.getUseNumber().intValue()) {return true;}
        if (t1.getSid()==null) {if (t2.getSid()!=null) {return true;}}
        else if (t2.getSid()==null) {if (t1.getSid()!=null) {return true;}}
        else if (t1.getSid().compareTo(t2.getSid())!=0) {return true;}
        if (t1.getSid3()==null) {if (t2.getSid3()!=null) {return true;}}
        else if (t2.getSid3()==null) {if (t1.getSid3()!=null) {return true;}}
        else if (t1.getSid3().compareTo(t2.getSid3())!=0) {return true;}
        if (t1.getOthrersid()==null) {if (t2.getOthrersid()!=null) {return true;}}
        else if (t2.getOthrersid()==null) {if (t1.getOthrersid()!=null) {return true;}}
        else if (t1.getOthrersid().compareTo(t2.getOthrersid())!=0) {return true;}
		return false;
	}
	private boolean BD5(Pal t1, Pal t2) {
		if (t1.getLvl()!=t2.getLvl()) {return true;}
		if (t1.getExp()!=t2.getExp()) {return true;}
		if (t1.getGrow()!=t2.getGrow()) {return true;}
		if (zf(t1.getParts(), t2.getParts())) {return true;}
		return false;	
	}
	/**字符串对比*/
	public boolean zf(String v1,String v2){
		if (v1==null) {v1="";}
		if (v2==null) {v2="";}
		return !v1.equals(v2);
	}
	
	/**藏宝阁处理*/
	public void salegoods(){
		System.err.println("重置藏宝阁订单状态");
		Jedis salesS = RedisPoolUntil.getJedis();
		salesS.del(RedisParameterUtil.SALESGOODS_STATUES);	
	    RedisPoolUntil.returnResource(salesS);
		List<Salegoods> salegoods=AllServiceUtil.getSalegoodsService().selectByAll();
		for (int i = 0; i < salegoods.size(); i++) {
			 Salegoods salegood=salegoods.get(i);
			 AllServiceUtil.getSalegoodsService().updateFlag(salegood.getSaleid(), salegood.getFlag());
			 if (GameServer.redisReset==4) {
				 if (salegood.getFlag()==4||salegood.getSaletype() == 2 ) {continue;}
	                System.err.println(salegood.getSalename()+"已被取回");
			        // 取回时，删除商品，并且修改对应的数据表角色ID,发送该件商品回客户端
					AllServiceUtil.getSalegoodsService().deleteByPrimaryKey(salegood.getSaleid());
	                // 根据类型查找表数据
					if (salegood.getSaletype() == 3 || salegood.getSaletype() == 5) {// 物品表
						// 查找该物品
						Goodstable goods2 = AllServiceUtil.getGoodsTableService().getGoodsByRgID(salegood.getOtherid());
						if (goods2==null||goods2.getRole_id().abs().compareTo(salegood.getRoleid())!=0) {
							continue;
						}
						// 修改物品角色ID为负
	                    AllServiceUtil.getGoodsrecordService().insert(goods2, null, 1, 13);
						AllServiceUtil.getGoodsTableService().updateGoodsIndex(goods2, salegood.getRoleid(), null, null);	
					} else if (salegood.getSaletype() == 4) {// 召唤兽表
						// 查找该召唤兽
						RoleSummoning pet = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(salegood.getOtherid());
						if (pet==null||pet.getRoleid().abs().compareTo(salegood.getRoleid())!=0) {
							continue;
						}
						// 修改物品角色ID为负
						AllServiceUtil.getRoleSummoningService().updateRoleSummoningIndex(pet, salegood.getRoleid());
						// 获取召唤兽内丹饰品ID,修改角色ID为负
						List<BigDecimal> goodses=pet.getGoods();
						if (goodses!=null) {
							for (BigDecimal bigDecimal : goodses) {
								Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(bigDecimal);
								if (goodstable==null) {continue;}
								AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, salegood.getRoleid(), null, null);	
							}	
						}	
					} else if (salegood.getSaletype() == 6) {// 灵宝表
						// 查找该灵宝
						Lingbao lingbao = AllServiceUtil.getLingbaoService().selectByPrimaryKey(salegood.getOtherid());
						if (lingbao==null||lingbao.getRoleid().abs().compareTo(salegood.getRoleid())!=0) {
							continue;
						}
						// 修改物品角色ID为负
						AllServiceUtil.getLingbaoService().updateLingbaoIndex(lingbao, salegood.getRoleid());

						// 修改灵宝符石
						if( lingbao.getFushis() != null && !"".equals(lingbao.getFushis()) ){
							String[] baos = lingbao.getFushis().split("\\|");
							for (String string : baos) {
								Goodstable goodstable = AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(string));
								if (goodstable==null) {continue;}
								AllServiceUtil.getGoodsTableService().updateGoodsIndex(goodstable, salegood.getRoleid(), null, null);	
							}
						}
					}else if(salegood.getSaletype() == 2 ){
						BigDecimal gold=AllServiceUtil.getRoleTableService().selectMoneyRoleID(salegood.getRoleid());
						gold=gold.add(salegood.getOtherid());
						if( gold.compareTo(new BigDecimal("999999999999")) > 0 ){
							gold=new BigDecimal("999999999999");
						}
						AllServiceUtil.getRoleTableService().updateMoneyRoleID(salegood.getRoleid(),gold);
					}
			 }
		}
		if (GameServer.redisReset==4) {
		    RedisEqualWithSqlThread.AllToDatabase(); 
		}
	}
	/**将数据库的数据缓存到redis*/
	public void databaseToCache() {
		RedisEqualWithSqlThread.AllToDataRole();
	    if (GameServer.redisReset==0) {
	    	System.err.println("重置redis");
	    	Jedis jedis = RedisPoolUntil.getJedis();
	        //jedis.flushAll();
			long x=System.currentTimeMillis();
			Set<String> sets=jedis.keys(GameServer.area+"*");
			int size=0;
			for (String value : sets) {
				// 转对象
				size++;
				if (!value.equals(RedisParameterUtil.GOODS_RECORD)
						||!value.equals(RedisParameterUtil.USER_REDIS)
						||!value.equals(RedisParameterUtil.USER_REDIS)) {
					jedis.del(value);		
				}
			}
			long y=System.currentTimeMillis();
			System.err.println("清空redis的key个数:"+size+" 耗时:"+(y-x));
			RedisPoolUntil.returnResource(jedis);
			System.err.println("同步数据库开始");
			// 物品表
			List<Goodstable> goods = AllServiceUtil.getGoodsTableService().getAllGoods();
			for (Goodstable goodstable : goods) {
				RedisControl.insertKey(RedisParameterUtil.GOODS , goodstable.getRgid()+"", GsonUtil.getGsonUtil().getgson().toJson(goodstable));			
				RedisControl.insertListRedis(RedisParameterUtil.GOODS, goodstable.getRole_id().toString(), goodstable.getRgid().toString());
				RedisControl.insertListRedis(RedisParameterUtil.GOODSID+"_"+goodstable.getRole_id().toString(),goodstable.getGoodsid().toString(), goodstable.getRgid().toString());
	    	    RedisControl.insertListRedis(RedisParameterUtil.GOODSST+"_"+goodstable.getRole_id().toString(),goodstable.getStatus().toString(), goodstable.getRgid().toString());   		
			}  
			System.err.println("物品完毕");
			// 灵宝表
			List<Lingbao> lingbaos = AllServiceUtil.getLingbaoService().selectAllLingbao();
			for (Lingbao lingbao : lingbaos) {
				RedisControl.insertKey(RedisParameterUtil.LINGBAO,lingbao.getBaoid()+"",GsonUtil.getGsonUtil().getgson().toJson(lingbao));			
				RedisControl.insertListRedis(RedisParameterUtil.LINGBAO,lingbao.getRoleid().toString(),lingbao.getBaoid()+"");
			}
			System.err.println("灵宝完毕");
			//pet表
			List<RoleSummoning> pets=AllServiceUtil.getRoleSummoningService().selectAllRoleSummonings();
			for (RoleSummoning roleSummoning : pets) {
			    RedisControl.insertKey(RedisParameterUtil.PET , roleSummoning.getSid().toString(), GsonUtil.getGsonUtil().getgson().toJson(roleSummoning));
			    RedisControl.insertListRedis(RedisParameterUtil.PET , roleSummoning.getRoleid().toString(), roleSummoning.getSid().toString());
		    }
			System.err.println("召唤兽完毕");
			//haz表
			List<Baby> babys = AllServiceUtil.getBabyService().selectAllBaby();
			for (Baby baby : babys) {
				RedisControl.insertKey(RedisParameterUtil.BABY , baby.getBabyid().toString(), GsonUtil.getGsonUtil().getgson().toJson(baby));
				RedisControl.insertListRedis(RedisParameterUtil.BABY , baby.getRoleid().toString(), baby.getBabyid().toString());
			}
			System.err.println("孩子完毕");
			// 坐骑表
			List<Mount> mounts = AllServiceUtil.getMountService().selectAllMounts();
			for (Mount mount : mounts) {
				List<MountSkill> mountskill = AllServiceUtil.getMountskillService().selectMountskillsByMountid(mount.getMid());
				mount.setMountskill(mountskill);
				RedisControl.insertKey(RedisParameterUtil.MOUNT , mount.getMid().toString(), GsonUtil.getGsonUtil().getgson().toJson(mount));
				RedisControl.insertListRedis(RedisParameterUtil.MOUNT , mount.getRoleid().toString(), mount.getMid().toString());
			}
			System.err.println("坐骑完毕");
			List<Pal> pals = AllServiceUtil.getPalService().selectAllPalSql();
			for (Pal pal : pals) {
				RedisControl.insertKeyT(RedisParameterUtil.PAL, pal.getId().toString(),pal);		
				RedisControl.insertListRedis(RedisParameterUtil.PAL, pal.getRoleId().toString(), pal.getId().toString());
			}
			System.err.println("伙伴完毕");
			System.err.println("同步数据库结束");
		}else if (GameServer.redisReset==-1) {
			System.err.println("redis同步到数据库");
			dataTB();
		}else {
			System.err.println("不重置redis");
		} 
		salegoods();//重置藏宝阁状态
		InitZhu();//获取最大主键值
	    if (GameServer.redisReset==2) {//2额外批量处理
	    	sbwld();
		}else if (GameServer.redisReset==3) {//联赛处理
			LSData();
		}else if (GameServer.redisReset==4) {//生成账号数据 用于账号移植
			try {
				writeData();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if (GameServer.redisReset==5) {//将生成的账号数据 进行移植
			try {
			    loadData();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if (GameServer.redisReset==6) {
			try {
			    ZHBC();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if (GameServer.redisReset==7) {
			try {
				ZHBC2();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	/**额外批量处理*/
	public void sbwld(){		
//		try {// 找回摆摊物品
//			Jedis jedis = RedisPoolUntil.getJedis();
//			Set<String> set = jedis.keys(RedisParameterUtil.GOODS + "_-*");
//			Set<String> set2 = jedis.keys(RedisParameterUtil.PET + "_-*");
//			RedisPoolUntil.returnResource(jedis);
//			for (String key : set) {
//				System.err.println(key);
//				String[] keys = key.split("_");
//				BigDecimal roleId = new BigDecimal(keys[1]);
//				List<Goodstable> goods = AllServiceUtil.getGoodsTableService().getGoodsByRoleID(roleId);
//				roleId = new BigDecimal(-roleId.longValue());
//				System.err.println(key + ":" + goods.size());
//				for (int i = 0; i < goods.size(); i++) {
//					Goodstable good = goods.get(i);
//					if (EquipTool.canSuper(good.getType())) {// 叠加
//						// 判断该角色是否拥有这件物品
//						List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(roleId,good.getGoodsid());
//						if (sameGoodstable.size() != 0) {// 修改使用次数
//							sameGoodstable.get(0).setUsetime(sameGoodstable.get(0).getUsetime()+ good.getUsetime());
//							// 修改数据库
//							AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
//							AllServiceUtil.getGoodsTableService().deleteGoodsByRgid(good.getRgid());
//						} else {// 插入数据库
//							AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, roleId, null, null);
//						}
//					} else {// 不可叠加
//						AllServiceUtil.getGoodsTableService().updateGoodsIndex(good, roleId, null, null);
//					}
//				}
//			}
//			for (String key : set2) {
//				String[] keys = key.split("_");
//				BigDecimal roleId = new BigDecimal(keys[1]);
//				List<RoleSummoning> pets = AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRoleID(roleId);
//				roleId = new BigDecimal(-roleId.longValue());
//				System.err.println(key + ":" + pets.size());
//				for (int i = 0; i < pets.size(); i++) {
//					RoleSummoning pet = pets.get(i);
//					AllServiceUtil.getRoleSummoningService().updateRoleSummoningIndex(pet, roleId);
//				}
//			}
//			RedisEqualWithSqlThread.AllToDatabase();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		/**批量删除白龙帝 petid 512*/
		Gson gson=GsonUtil.getGsonUtil().getgson();
		Jedis jedis = RedisPoolUntil.getJedis();
		Set<String> set=jedis.hkeys(RedisParameterUtil.PET);
		RedisPoolUntil.returnResource(jedis);
		int size=0;
		int z=0;
		for (String ID : set) {
			size++;
			if (size>=1000) {
				size=0;z++;
				if (z%10==0) {System.err.println(z*1000);}	
			    try {Thread.sleep(1);} catch (Exception e) {}
		    }
			RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(new BigDecimal(ID));
			if (pet!=null&&pet.getSummoningid().equals("512")) {
				WriteOut.addtxt(GsonUtil.getGsonUtil().getgson().toJson(pet), 9999);
				AllServiceUtil.getRoleSummoningService().deleteRoleSummoningBySid(pet.getSid());
			}
		}
	}

	/** 加载联赛报名数据 */
	public void LSData() {
		List<UserData> userDatas = getLSUserData();
		System.err.println("总数" + userDatas.size());
		for (int i = 0; i < userDatas.size(); i++) {
			UserData userData = userDatas.get(i);
			UserTable userTable = userData.getUserTable();
			while (AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userTable.getUsername(),null) != null) {
				userTable.setUsername(userTable.getUsername() + "("+ userData.getI() + ")");
			}
			AllServiceUtil.getUserTableService().insertIntoUser(userTable);
			LoginResult loginResult = userData.getLoginResult();
			loginResult.setUser_id(userTable.getUser_id());
			// 判断角色名称是否存在
			while (AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(loginResult.getRolename()) != null) {
				loginResult.setRolename(loginResult.getRolename() + "("+ userData.getI() + ")");
			}
			AllServiceUtil.getRoleTableService().insertIntoRoleTable(loginResult);
			AllServiceUtil.getRoleTableService().updateRoleWhenExit(loginResult);
			//物品表
			List<Goodstable> goods = userData.getGoodstables();
			Map<BigDecimal,BigDecimal> ids=new HashMap<>();
			for (Goodstable goodstable : goods) {
				BigDecimal rgid=getGoods_pk();
				ids.put(goodstable.getRgid(),rgid);
				goodstable.setRgid(rgid);
				goodstable.setRole_id(loginResult.getRole_id());
			}
			for (Goodstable goodstable : goods) {
				if (Goodtype.EquipGem(goodstable.getType())) {
					String[] vs=goodstable.getValue().split("\\|");
					String extra=SuitComposeAction.getExtra(vs, SuitComposeAction.Extras[4]);
					if (extra!=null) {
						String[] extras=extra.split("&");
						StringBuffer buffer=new StringBuffer();
						buffer.append("宝石属性");
						for (int j = 1; j < extras.length; j++) {
							BigDecimal id=new BigDecimal(extras[j]);
							if (ids.get(id)!=null) {id=ids.get(id);}
							buffer.append("&");
							buffer.append(id);
						}
						goodstable.setValue(SuitComposeAction.newExtra(vs,4,buffer.toString()));	
					}		
				}
				RedisControl.insertKey(RedisParameterUtil.GOODS,goodstable.getRgid() + "", GsonUtil.getGsonUtil().getgson().toJson(goodstable));
				RedisControl.insertListRedis(RedisParameterUtil.GOODS,goodstable.getRole_id().toString(), goodstable.getRgid().toString());
				RedisControl.insertListRedis(RedisParameterUtil.GOODSID + "_"+ goodstable.getRole_id().toString(), goodstable.getGoodsid().toString(), goodstable.getRgid().toString());
				RedisControl.insertListRedis(RedisParameterUtil.GOODSST + "_"+ goodstable.getRole_id().toString(), goodstable.getStatus().toString(), goodstable.getRgid().toString());
			}
			// 灵宝表
			List<Lingbao> lingbaos = userData.getLingbaos();
			for (Lingbao lingbao : lingbaos) {
				if (lingbao.getFushis()!=null&&!lingbao.getFushis().equals("")) {
					StringBuffer buffer = new StringBuffer();
					String[] vs = lingbao.getFushis().split("\\|");
					for (int j = 0; j < vs.length; j++) {
						BigDecimal id = new BigDecimal(vs[j]);
						if (ids.get(id) != null) {id = ids.get(id);}
						if (buffer.length() != 0) {buffer.append("|");}
						buffer.append(id);
					}
					lingbao.setFushis(buffer.toString());	
				}
				lingbao.setBaoid(getLingbao_pk());
				lingbao.setRoleid(loginResult.getRole_id());
				RedisControl.insertKey(RedisParameterUtil.LINGBAO,lingbao.getBaoid() + "", GsonUtil.getGsonUtil().getgson().toJson(lingbao));
				RedisControl.insertListRedis(RedisParameterUtil.LINGBAO, lingbao.getRoleid().toString(), lingbao.getBaoid()+ "");
			}
			// pet表
			List<RoleSummoning> pets = userData.getPets();
			for (RoleSummoning roleSummoning : pets) {
				if (roleSummoning.getInnerGoods()!=null&&!roleSummoning.getInnerGoods().equals("")) {
					StringBuffer buffer=new StringBuffer();
					String[] v=roleSummoning.getInnerGoods().split("\\|");
					for (int j = 0; j < v.length; j++) {
						if (v[j].equals("")) {continue;}
						BigDecimal id=new BigDecimal(v[j]);
						if (ids.get(id)!=null) {
							id=ids.get(id);
						}
					    if (buffer.length()!=0) {buffer.append("|");}
					    buffer.append(id);
					}
					roleSummoning.setInnerGoods(buffer.toString());
				}
				if (roleSummoning.getStye()!=null&&roleSummoning.getStye().length()>1) {
					StringBuffer buffer=new StringBuffer();
					String[] v=roleSummoning.getStye().split("\\|");
					buffer.append(v[0]);
					for (int j = 1; j < v.length; j++) {
						String[] vs=v[j].split("-");
						if (vs.length<2) {
							buffer.append("|");
							buffer.append(v[j]);
						}else {
							BigDecimal id=new BigDecimal(vs[1]);
							if (ids.get(id)!=null) {
								id=ids.get(id);
							}
							buffer.append("|");
							for (int k = 0; k < vs.length; k++) {
								if (k!=0) {buffer.append("-");}
								if (k==1) {
									buffer.append(id);
								}else {
									buffer.append(vs[k]);
								}
							}
						}
					}
					roleSummoning.setStye(buffer.toString());
				}
				roleSummoning.setSid(getPet_pk());
				roleSummoning.setRoleid(loginResult.getRole_id());
				RedisControl.insertKey(RedisParameterUtil.PET, roleSummoning.getSid().toString(), GsonUtil.getGsonUtil().getgson().toJson(roleSummoning));
				RedisControl.insertListRedis(RedisParameterUtil.PET,roleSummoning.getRoleid().toString(), roleSummoning.getSid().toString());
			}
			// haz表
			List<Baby> babys = userData.getBabys();
			for (Baby baby : babys) {
				baby.setBabyid(getBaby_pk());
				baby.setRoleid(loginResult.getRole_id());
				RedisControl.insertKey(RedisParameterUtil.BABY, baby.getBabyid().toString(), GsonUtil.getGsonUtil().getgson().toJson(baby));
				RedisControl.insertListRedis(RedisParameterUtil.BABY, baby.getRoleid().toString(), baby.getBabyid().toString());
			}
			// 坐骑表
			List<Mount> mounts = userData.getMounts();
			for (Mount mount : mounts) {
				mount.setSid(null);
				mount.setOthrersid(null);
				mount.setSid3(null);
				mount.setMid(getMount_pk());
				mount.setRoleid(loginResult.getRole_id());
				RedisControl.insertKey(RedisParameterUtil.MOUNT, mount.getMid().toString(),GsonUtil.getGsonUtil().getgson().toJson(mount));
				RedisControl.insertListRedis(RedisParameterUtil.MOUNT, mount.getRoleid().toString(), mount.getMid().toString());
			}
		}
	}
	public List<UserData> getLSUserData(){
		List<UserData> userDatas=new ArrayList<>();
		for (int i = 1; i < 6; i++) {
			String text = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "lsteam("+i+").txt");
			List<UserData> datas;
			if (text==null||text.equals("")) {
				datas=new ArrayList<>();
			}else {
				lsteamBean lsteamBean=GsonUtil.getGsonUtil().getgson().fromJson(text,lsteamBean.class);
				datas=lsteamBean.getUserDatas();
				if (datas==null) {datas=new ArrayList<>();}
			}
			for (int j = 0; j < datas.size(); j++) {
				datas.get(j).setI(i);	
				userDatas.add(datas.get(j));
			}
		}
		return userDatas;
	}
	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param
	 *
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long date2TimeStamp(String date_str, String format) {
		if (date_str==null||date_str.equals("")||date_str.equals("0")) {
			return 0;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date_str).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**写入区数据@throws Exception */
	public void writeData() throws Exception{
		List<Friend> friends=AllServiceUtil.getFriendService().allFriend();
		FriendTransplant friendTransplant=new FriendTransplant(friends);
		CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "/GetTXT/HQFriend.txt", GZip.gZip(GsonUtil.getGsonUtil().getgson().toJson(friendTransplant).getBytes()));
		friends=null;friendTransplant=null;
		List<Gang> gangs=AllServiceUtil.getGangService().findAllGang();
		GangTransplant gangTransplant=new GangTransplant(gangs);
		CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "/GetTXT/HQGang.txt", GZip.gZip(GsonUtil.getGsonUtil().getgson().toJson(gangTransplant).getBytes()));
		gangs=null;gangTransplant=null;
		long TIME=System.currentTimeMillis()/1000;
		int SIZE=0;
		List<UserDataBean> userDataBeans=new ArrayList<>();
		List<UserTable> allUserTables=AllServiceUtil.getUserTableService().findAllUser();
		for (int i = 0; i < allUserTables.size(); i++) {
			System.err.println(i+":"+allUserTables.size());
    		UserTable userTable=allUserTables.get(i);
//    		long time=date2TimeStamp(userTable.getUSERLASTLOGIN(), "yyyy-MM-dd HH:mm:ss")/1000;
//    	    if (TIME-time>2592000) {continue;}
    	   
    	    Thread.sleep(100);
			List<RoleDataBean> roleDataBeans=new ArrayList<>();
			List<LoginResult> loginResults = AllServiceUtil.getUserTableService().findRoleByUserNameAndPassword(userTable.getUsername(),userTable.getUserpwd(),null);// 获得用户的角色
			for (int j = 0; j < loginResults.size(); j++) {
				LoginResult loginResult=loginResults.get(j);
				RoleDataBean bean=new RoleDataBean(loginResult, 
						AllServiceUtil.getPackRecordService().selectByPrimaryKey(loginResult.getRole_id()), 
						AllServiceUtil.getGoodsTableService().getGoodsByRoleID(loginResult.getRole_id()), 
						AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRoleID(loginResult.getRole_id()), 
						AllServiceUtil.getMountService().selectMountsByRoleID(loginResult.getRole_id()), 
						AllServiceUtil.getLingbaoService().selectLingbaoByRoleID(loginResult.getRole_id()), 
						AllServiceUtil.getBabyService().selectBabyByRolename(loginResult.getRole_id()), 
						AllServiceUtil.getTitletableService().selectRoleAllTitle(loginResult.getRole_id()));
				roleDataBeans.add(bean);
			}
			UserDataBean userDataBean=new UserDataBean(userTable, roleDataBeans);
			userDataBeans.add(userDataBean);
			if (userDataBeans.size()>=75) {
				SIZE++;
				CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "/GetTXT/HQUser"+(SIZE)+".txt", GZip.gZip(GsonUtil.getGsonUtil().getgson().toJson(new TransplantBean(userDataBeans)).getBytes()));
				userDataBeans.clear();
			}
		}
		SIZE++;
		CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "/GetTXT/HQUser"+(SIZE)+".txt", GZip.gZip(GsonUtil.getGsonUtil().getgson().toJson(new TransplantBean(userDataBeans)).getBytes()));
		userDataBeans.clear();
	}
	/**载入区数据*/
	public void loadData() throws Exception{
		String QZ=GameServer.QZ;
		Map<BigDecimal,Gang> gangMap=new HashMap<>();
		Map<BigDecimal,BigDecimal> roleMap=new HashMap<>();
		byte[] text = ReadTxtUtil.readFile(ReadExelTool.class.getResource("/").getPath() + "/GetTXT/HQGang.txt");
		if (text!=null) {
			text=GZip.unGZip(text);
			String msg=new String(text);
			GangTransplant gangTransplant=GsonUtil.getGsonUtil().getgson().fromJson(msg, GangTransplant.class);
			for (int i = 0; i < gangTransplant.getGangs().size(); i++) {
				Gang gang=gangTransplant.getGangs().get(i);
				while (AllServiceUtil.getGangService().findGangByGangName(gang.getGangname())!=null) {
					gang.setGangname(gang.getGangname()+QZ);
				}
				BigDecimal yid=gang.getGangid();
				AllServiceUtil.getGangService().createGang(gang);
				Gang newGang=AllServiceUtil.getGangService().findGangByGangName(gang.getGangname());
				gang.setGangid(newGang.getGangid());
				AllServiceUtil.getGangService().updateGang(gang);
				gangMap.put(yid, gang);
			}
		}
		int kh=0;
		List<String> filePaths=GetFilePaths(ReadExelTool.class.getResource("/").getPath() + "/GetTXT", "HQUser");
	    for (int z = 0; z < filePaths.size(); z++) {
			try {
				byte[] text1 = ReadTxtUtil.readFile(filePaths.get(z));
				if (text1==null) {continue;}
				text1=GZip.unGZip(text1);
				String msg=new String(text1);
				TransplantBean transplantBean=GsonUtil.getGsonUtil().getgson().fromJson(msg, TransplantBean.class);
				for (int i = 0; i < transplantBean.getList().size(); i++) {
					System.err.println(z+":"+filePaths.size()+"__"+i+":"+transplantBean.getList().size());
					UserDataBean userData = transplantBean.getList().get(i);
					if (userData.getRoleDataBeans().size()==0) {
						kh++;
						System.err.println("空号"+kh);
						continue;
					}
					Thread.sleep(100);
					UserTable userTable = userData.getUserTable();
					while (AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userTable.getUsername(),null) != null) {
						userTable.setUsername(userTable.getUsername() + QZ);
					}
					AllServiceUtil.getUserTableService().insertIntoUser(userTable);
					AllServiceUtil.getUserTableService().updateUser(userTable);
					for (int j = 0; j < userData.getRoleDataBeans().size(); j++) {
						RoleDataBean roleDataBean=userData.getRoleDataBeans().get(j);
						LoginResult loginResult=roleDataBean.getLoginResult();
						loginResult.setUser_id(userTable.getUser_id());
						// 判断角色名称是否存在
						while (AllServiceUtil.getRoleTableService().selectRoleTableByRoleName(loginResult.getRolename()) != null) {
							loginResult.setRolename(loginResult.getRolename() + QZ);
						}
						BigDecimal yid=loginResult.getRole_id();
						BigDecimal gang_id=loginResult.getGang_id();
						if (gang_id!=null) {
							Gang gang=gangMap.get(gang_id);
							if (gang!=null) {
								loginResult.setGang_id(gang.getGangid());
								loginResult.setGangname(gang.getGangname());
							}
						}
						AllServiceUtil.getRoleTableService().insertIntoRoleTable(loginResult);
						AllServiceUtil.getRoleTableService().updateRoleWhenExit(loginResult);
						roleMap.put(yid,loginResult.getRole_id());
						PackRecord packRecord=roleDataBean.getPackRecord();
						if (packRecord!=null) {
							packRecord.setRoleId(loginResult.getRole_id());
							AllServiceUtil.getPackRecordService().insert(packRecord);
							AllServiceUtil.getPackRecordService().updateByPrimaryKeySelective(packRecord);
						}
						//物品表
						List<Goodstable> goods = roleDataBean.getGoodstables();
						Map<BigDecimal,BigDecimal> ids=new HashMap<>();
						for (Goodstable goodstable : goods) {
							BigDecimal rgid=getGoods_pk();
							ids.put(goodstable.getRgid(),rgid);
							goodstable.setRgid(rgid);
							goodstable.setRole_id(loginResult.getRole_id());
						}
						for (Goodstable goodstable : goods) {
							if (Goodtype.EquipGem(goodstable.getType())) {
								String[] vs=goodstable.getValue().split("\\|");
								String extra=SuitComposeAction.getExtra(vs, SuitComposeAction.Extras[4]);
								if (extra!=null) {
									String[] extras=extra.split("&");
									StringBuffer buffer=new StringBuffer();
									buffer.append("宝石属性");
									for (int k = 1; k < extras.length; k++) {
										BigDecimal id=new BigDecimal(extras[k]);
										if (ids.get(id)!=null) {id=ids.get(id);}
										buffer.append("&");
										buffer.append(id);
									}
									goodstable.setValue(SuitComposeAction.newExtra(vs,4,buffer.toString()));	
								}		
							}
							RedisControl.insertKey(RedisParameterUtil.GOODS,goodstable.getRgid() + "", GsonUtil.getGsonUtil().getgson().toJson(goodstable));
							RedisControl.insertListRedis(RedisParameterUtil.GOODS,goodstable.getRole_id().toString(), goodstable.getRgid().toString());
							RedisControl.insertListRedis(RedisParameterUtil.GOODSID + "_"+ goodstable.getRole_id().toString(), goodstable.getGoodsid().toString(), goodstable.getRgid().toString());
							RedisControl.insertListRedis(RedisParameterUtil.GOODSST + "_"+ goodstable.getRole_id().toString(), goodstable.getStatus().toString(), goodstable.getRgid().toString());
							RedisControl.insertController(RedisParameterUtil.GOODS, goodstable.getRgid().toString(),RedisControl.CADD);
						}
						// 灵宝表
						List<Lingbao> lingbaos = roleDataBean.getLingbaos();
						for (Lingbao lingbao : lingbaos) {
							if (lingbao.getFushis()!=null&&!lingbao.getFushis().equals("")) {
								StringBuffer buffer = new StringBuffer();
								String[] vs = lingbao.getFushis().split("\\|");
								for (int k = 0; k < vs.length; k++) {
									BigDecimal id = new BigDecimal(vs[k]);
									if (ids.get(id) != null) {id = ids.get(id);}
									if (buffer.length() != 0) {buffer.append("|");}
									buffer.append(id);
								}
								lingbao.setFushis(buffer.toString());	
							}
							lingbao.setBaoid(getLingbao_pk());
							lingbao.setRoleid(loginResult.getRole_id());
							RedisControl.insertKey(RedisParameterUtil.LINGBAO,lingbao.getBaoid() + "", GsonUtil.getGsonUtil().getgson().toJson(lingbao));
							RedisControl.insertListRedis(RedisParameterUtil.LINGBAO, lingbao.getRoleid().toString(), lingbao.getBaoid()+ "");
							RedisControl.insertController(RedisParameterUtil.LINGBAO, lingbao.getBaoid().toString(),RedisControl.CADD);
						}
						// pet表
						List<RoleSummoning> pets = roleDataBean.getPets();
						for (RoleSummoning roleSummoning : pets) {
							if (roleSummoning.getInnerGoods()!=null&&!roleSummoning.getInnerGoods().equals("")) {
								StringBuffer buffer=new StringBuffer();
								String[] v=roleSummoning.getInnerGoods().split("\\|");
								for (int k = 0; k < v.length; k++) {
									if (v[k].equals("")) {continue;}
									BigDecimal id=new BigDecimal(v[k]);
									if (ids.get(id)!=null) {
										id=ids.get(id);
									}
								    if (buffer.length()!=0) {buffer.append("|");}
								    buffer.append(id);
								}
								roleSummoning.setInnerGoods(buffer.toString());
							}
							if (roleSummoning.getStye()!=null&&roleSummoning.getStye().length()>1) {
								StringBuffer buffer=new StringBuffer();
								String[] v=roleSummoning.getStye().split("\\|");
								buffer.append(v[0]);
								for (int k = 1; k < v.length; k++) {
									String[] vs=v[k].split("-");
									if (vs.length<2) {
										buffer.append("|");
										buffer.append(v[k]);
									}else {
										BigDecimal id=new BigDecimal(vs[1]);
										if (ids.get(id)!=null) {
											id=ids.get(id);
										}
										buffer.append("|");
										for (int k1 = 0; k1 < vs.length; k1++) {
											if (k1!=0) {buffer.append("-");}
											if (k1==1) {
												buffer.append(id);
											}else {
												buffer.append(vs[k1]);
											}
										}
									}
								}
								roleSummoning.setStye(buffer.toString());
							}
							roleSummoning.setSid(getPet_pk());
							roleSummoning.setRoleid(loginResult.getRole_id());
							RedisControl.insertKey(RedisParameterUtil.PET, roleSummoning.getSid().toString(), GsonUtil.getGsonUtil().getgson().toJson(roleSummoning));
							RedisControl.insertListRedis(RedisParameterUtil.PET,roleSummoning.getRoleid().toString(), roleSummoning.getSid().toString());
							RedisControl.insertController(RedisParameterUtil.PET, roleSummoning.getSid().toString(),RedisControl.CADD);
						}
						// haz表
						List<Baby> babys = roleDataBean.getBabys();
						for (Baby baby : babys) {
							baby.setBabyid(getBaby_pk());
							baby.setRoleid(loginResult.getRole_id());
							RedisControl.insertKey(RedisParameterUtil.BABY, baby.getBabyid().toString(), GsonUtil.getGsonUtil().getgson().toJson(baby));
							RedisControl.insertListRedis(RedisParameterUtil.BABY, baby.getRoleid().toString(), baby.getBabyid().toString());
							RedisControl.insertController(RedisParameterUtil.BABY, baby.getBabyid().toString(),RedisControl.CADD);
						}
						// 坐骑表
						List<Mount> mounts = roleDataBean.getMounts();
						for (Mount mount : mounts) {
							mount.setSid(null);
							mount.setOthrersid(null);
							mount.setSid3(null);
							mount.setMid(getMount_pk());
							mount.setRoleid(loginResult.getRole_id());
                            if (mount.getMountskill()!=null) {
                            	for (MountSkill mountSkill : mount.getMountskill()) {
                            		mountSkill.setMid(mount.getMid());
                            		AllServiceUtil.getMountskillService().insertMountskills(mountSkill);
                            	}
							}
							RedisControl.insertKey(RedisParameterUtil.MOUNT, mount.getMid().toString(),GsonUtil.getGsonUtil().getgson().toJson(mount));
							RedisControl.insertListRedis(RedisParameterUtil.MOUNT, mount.getRoleid().toString(), mount.getMid().toString());
							RedisControl.insertController(RedisParameterUtil.MOUNT,mount.getMid().toString(),RedisControl.CADD);
						}
						List<Titletable> titletables=roleDataBean.getTitletables();
						for (Titletable titletable : titletables) {
							titletable.setRoleid(loginResult.getRole_id());
							AllServiceUtil.getTitletableService().createRoleTitle(titletable);
						}
					}
				}
				RedisEqualWithSqlThread.AllToDatabase(); 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	/**补充账号数据 */
	public static void ZHBC() throws Exception{
		long TIME=System.currentTimeMillis()/1000;
		int SIZE=0;
		List<UserDataBean> userDataBeans=new ArrayList<>();
		List<UserTable> allUserTables=AllServiceUtil.getUserTableService().findAllUser();
		for (int i = 0; i < allUserTables.size(); i++) {
			UserTable userTable=allUserTables.get(i);
			long time=date2TimeStamp(userTable.getUSERLASTLOGIN(), "yyyy-MM-dd HH:mm:ss")/1000;
    	    if (TIME-time>2592000) {continue;}
    		List<RoleDataBean> roleDataBeans=new ArrayList<>();
			UserDataBean userDataBean=new UserDataBean(userTable, roleDataBeans);
			userDataBeans.add(userDataBean);
			if (userDataBeans.size()>=2000) {
				SIZE++;
				CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "/GetTXT/HQUser"+(SIZE)+".txt", GZip.gZip(GsonUtil.getGsonUtil().getgson().toJson(new TransplantBean(userDataBeans)).getBytes()));
				userDataBeans.clear();
			}
		}
		SIZE++;
		CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "/GetTXT/HQUser"+(SIZE)+".txt", GZip.gZip(GsonUtil.getGsonUtil().getgson().toJson(new TransplantBean(userDataBeans)).getBytes()));
		userDataBeans.clear();
	}
	public static void ZHBC2() throws Exception{
		String QZ=GameServer.QZ;
		List<String> filePaths=GetFilePaths(ReadExelTool.class.getResource("/").getPath() + "/GetTXT", "HQUser");
	    for (int z = 0; z < filePaths.size(); z++) {
			try {
				byte[] text1 = ReadTxtUtil.readFile(filePaths.get(z));
				if (text1==null) {continue;}
				text1=GZip.unGZip(text1);
				String msg=new String(text1);
				TransplantBean transplantBean=GsonUtil.getGsonUtil().getgson().fromJson(msg, TransplantBean.class);
				for (int i = 0; i < transplantBean.getList().size(); i++) {
					System.err.println(z+":"+filePaths.size()+"__"+i+":"+transplantBean.getList().size());
					UserDataBean userData = transplantBean.getList().get(i);
					UserTable userTable = userData.getUserTable();
					UserTable newUserTable=null;
					String userName=userTable.getUsername();
					int size=0;
					while (userName!=null) {
						size++;
						if (size>5) {System.err.println(userName);break;}
						newUserTable=AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userName,null);
						if (newUserTable==null||newUserTable.getUser_id().compareTo(new BigDecimal(126593439446L))<=0) {
							userName+=QZ;
							newUserTable=null;
							continue;
						}else {
							userName=null;
						}			
					}
					if (newUserTable!=null) {
						newUserTable.setUser_id(userTable.getUser_id());
						AllServiceUtil.getUserTableService().updateUser(userTable);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	/**获取文件夹下面的文件*/
	public static List<String> GetFilePaths(String paths,String QZ){
		File file = new File(paths);
		File[] array = file.listFiles();
		List<String> wuqi=new ArrayList<>();
		for(int i=0;i<array.length;i++){
			if (array[i].getPath().indexOf(QZ)!=-1) {
				System.err.println(array[i].getPath());
				wuqi.add(array[i].getPath());		
			}	
		}
		return wuqi;	
	}
	public static List removeDuplicate(List list){  
        List listTemp = new ArrayList();  
        for(int i=0;i<list.size();i++){  
            if(!listTemp.contains(list.get(i))){  
                listTemp.add(list.get(i));  
            }  
        }  
        return listTemp;  
    }
	public static synchronized BigDecimal getBaby_pk() {
		baby_pk=baby_pk.add(ADD);
		return baby_pk;
	}
	public static synchronized BigDecimal getGoods_pk() {
		goods_pk=goods_pk.add(ADD);
		return goods_pk;
	}
	public static synchronized BigDecimal getMount_pk() {
		mount_pk=mount_pk.add(ADD);
		return mount_pk;
	}
	public static synchronized BigDecimal getPet_pk() {
		pet_pk=pet_pk.add(ADD);
		return pet_pk;
	}
	public static synchronized BigDecimal getLingbao_pk() {
		lingbao_pk=lingbao_pk.add(ADD);
		return lingbao_pk;
	}
	public static synchronized BigDecimal getPal_pk() {
		pal_pk=pal_pk.add(ADD);
		return pal_pk;
	}
	public static synchronized long getRecord_pk() {
		record_pk++;
		return record_pk;
	}
	public static synchronized BigDecimal getRole_pk() {
		role_pk=role_pk.add(new BigDecimal(1));
		return role_pk;
	}
	public static synchronized BigDecimal getUser_pk() {
		user_pk=user_pk.add(ADD);
		return user_pk;
	}
	
	public static synchronized BigDecimal getOneAreanNotes_pk() {
		oneAreanNotes_pk=oneAreanNotes_pk.add(ADD);
		return oneAreanNotes_pk;
	}
	public static void resetOneArenaTime(){
		long time=System.currentTimeMillis();
		time-=1000*60*60*24*3;
		oneAreanNotes_min=AllServiceUtil.getOneArenaNotesService().selectMaxID(TimeUntil.getPastDate(time));
		if (oneAreanNotes_min==null) {oneAreanNotes_min=new BigDecimal(0);}
	}
	public static void reset(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("最高在线人数");
		buffer.append(GameServer.inlineMax);
		buffer.append("__连接次数");
		buffer.append(MainServerHandler.a);
		buffer.append("__连接异常次数");
		buffer.append(MainServerHandler.b);
		buffer.append("__物品使用规则异常次数");
		buffer.append(PackChangeAction.size);
		buffer.append((GameServer.getPortNumber()==7109?RedisCacheUtil.redisMonitor():""));
		
		buffer.append("__物品缓存清除:");goodBean.reset(buffer);
		buffer.append("__召唤兽缓存清除:");petBean.reset(buffer);
		buffer.append("__灵宝缓存清除:");lingbaoBean.reset(buffer);
		buffer.append("__孩子缓存清除:");babyBean.reset(buffer);
		buffer.append("__坐骑缓存清除:");mountBean.reset(buffer);
		buffer.append("__伙伴缓存清除:");palBean.reset(buffer);
		System.err.println(buffer.toString());
	}
	/**redis性能监测*/
	public static String redisMonitor(){
		Jedis salesS = RedisPoolUntil.getJedis();
		String value=salesS.info("Stats");
		String[] values=value.split("\\r?\\n");
		if (values[2].startsWith("total_commands_processed:")) {
            long time=System.currentTimeMillis();
            long processed=Long.parseLong(values[2].substring(25, values[2].length()));
			if (monitor.time!=0) {
				long maxsec=(processed-monitor.processed)/((time-monitor.time)/1000);	
				if (maxsec>monitor.MaxSec) {
					monitor.MaxSec=maxsec;
				}
			}
			monitor.processed=processed;
			monitor.time=time;
		}
		if (values[3].startsWith("instantaneous_ops_per_sec:")) {
			long sec=Long.parseLong(values[3].substring(26, values[3].length()));
			if (sec>monitor.redis_MaxSec) {
				monitor.redis_MaxSec=sec;
			}
		}
		RedisPoolUntil.returnResource(salesS);
		return monitor.getString();
		
	}
	private static Monitor monitor=new Monitor();
	static class Monitor{
		private long time;//上次检测的时间差 
		private long processed;//上次请求量
		private long redis_MaxSec;//redis获取的最大qps
		private long MaxSec;//系统换算的最大qps
		public String getString(){
			return "#redis最高qps"+redis_MaxSec+"#换算最高qps"+MaxSec;
		}
	}
}
