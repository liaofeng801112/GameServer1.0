package come.tool.Role;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.RoleSummoning;
import org.come.until.AllServiceUtil;

import come.tool.Calculation.CalculationUtil;
import come.tool.FightingData.ManData;


public class RolePool {
	private static Object LOCK=new Object();
	private static ConcurrentHashMap<BigDecimal,RoleData> allRoles=new ConcurrentHashMap<BigDecimal, RoleData>();
	//添加
	public static RoleData addRoleData(LoginResult loginResult, List<Goodstable> goods, List<RoleSummoning> pets, List<Lingbao> lingbaos, List<Baby> babys, List<Mount> mounts){
		RoleData roleData=new RoleData(loginResult,goods,pets,lingbaos,babys,mounts);
		synchronized (LOCK) {
			allRoles.put(loginResult.getRole_id(),roleData);
			allLineRoles.remove(loginResult.getRole_id());	
		}
		return roleData;
	}
	//获取
	public static RoleData getRoleData(BigDecimal role_id){
		return allRoles.get(role_id);
	}
	//获取LoginResult
	public static LoginResult getLoginResult(BigDecimal role_id){
		RoleData roleData=allRoles.get(role_id);
		if (roleData==null) {return null;}
		return roleData.getLoginResult();
	}
	//删除
	public static RoleData deleteRoleData(BigDecimal role_id){
		RoleData roleData=null;
		synchronized (LOCK) {
			roleData=allRoles.remove(role_id);
			if (roleData!=null) {
				roleData.setLine(0);
				allLineRoles.put(role_id, roleData);
			}	
		}
		return roleData;
	}
	private static ConcurrentHashMap<BigDecimal,RoleData> allLineRoles=new ConcurrentHashMap<BigDecimal, RoleData>();
	/**离线获取对象*/
	public static RoleData getLineRoleData(BigDecimal role_id){
		boolean is=false;
		RoleData roleData=null;
		synchronized (LOCK) {
			roleData=getRoleData(role_id);
			is=roleData==null;
			if (is) {roleData=allLineRoles.get(role_id);}
		}
		if (roleData==null) {
			LoginResult roleInfo=AllServiceUtil.getRoleTableService().selectRoleByRoleId(role_id);
			if (roleInfo!=null) {
				// 根据角色ID查询物品
				List<Goodstable> goods=AllServiceUtil.getGoodsTableService().getGoodsByRoleID(roleInfo.getRole_id());
				// 获得全部召唤兽
				List<RoleSummoning> pets=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRoleID(roleInfo.getRole_id());
				// 获得角色所有的灵宝
				List<Lingbao> lingbaos=AllServiceUtil.getLingbaoService().selectLingbaoByRoleID(roleInfo.getRole_id());
				// 返回该角色所有宝宝
				List<Baby> babys =AllServiceUtil.getBabyService().selectBabyByRolename(roleInfo.getRole_id());
				// 获得角色所有坐骑
				List<Mount> mounts=AllServiceUtil.getMountService().selectMountsByRoleID(roleInfo.getRole_id());
				roleData=new RoleData(roleInfo,goods,pets,lingbaos,babys,mounts);
				allLineRoles.put(role_id, roleData);
			}	
		}
		if (is&&roleData!=null) {roleData.setLine(0);}
		return roleData;
	}
	public synchronized static CBGData getLineCBGRoleData(BigDecimal role_id){
		CBGData cbgData=null;
		RoleData roleData=null;
		boolean is=false;
		synchronized (LOCK) {
			roleData=getRoleData(role_id);
			is=roleData==null;
			if (is) {roleData=allLineRoles.get(role_id);}
		}
		if (roleData==null) {
			LoginResult roleInfo=AllServiceUtil.getRoleTableService().selectRoleByRoleId(role_id);
			if (roleInfo!=null) {
				// 根据角色ID查询物品
				List<Goodstable> goods=AllServiceUtil.getGoodsTableService().getGoodsByRoleID(roleInfo.getRole_id());
				// 获得全部召唤兽
				List<RoleSummoning> pets=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRoleID(roleInfo.getRole_id());
				// 获得角色所有的灵宝
				List<Lingbao> lingbaos=AllServiceUtil.getLingbaoService().selectLingbaoByRoleID(roleInfo.getRole_id());
				// 返回该角色所有宝宝
				List<Baby> babys =AllServiceUtil.getBabyService().selectBabyByRolename(roleInfo.getRole_id());
				// 获得角色所有坐骑
				List<Mount> mounts=AllServiceUtil.getMountService().selectMountsByRoleID(roleInfo.getRole_id());
				roleData=new RoleData(roleInfo,goods,pets,lingbaos,babys,mounts);
				cbgData=new CBGData(roleInfo, goods, pets, mounts, lingbaos, babys);
				roleData.setCbgData(cbgData);
				allLineRoles.put(role_id, roleData);
			}	
		}
		if (is&&roleData!=null) {roleData.setLine(0);}
		if (roleData==null) {return null;}
		cbgData=roleData.getCbgData();
		if (cbgData==null) {
			LoginResult roleInfo=roleData.getLoginResult();
			// 根据角色ID查询物品
			List<Goodstable> goods=AllServiceUtil.getGoodsTableService().getGoodsByRoleID(roleInfo.getRole_id());
			// 获得全部召唤兽
			List<RoleSummoning> pets=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRoleID(roleInfo.getRole_id());
			// 获得角色所有的灵宝
			List<Lingbao> lingbaos=AllServiceUtil.getLingbaoService().selectLingbaoByRoleID(roleInfo.getRole_id());
			// 返回该角色所有宝宝
			List<Baby> babys =AllServiceUtil.getBabyService().selectBabyByRolename(roleInfo.getRole_id());
			// 获得角色所有坐骑
			List<Mount> mounts=AllServiceUtil.getMountService().selectMountsByRoleID(roleInfo.getRole_id());
			cbgData=new CBGData(roleInfo, goods, pets, mounts, lingbaos, babys);
			roleData.setCbgData(cbgData);
		}
		if (cbgData.getData2()==null) {
			ManData manData=new ManData(1, 0);
			CalculationUtil.loadRoleBattle(manData, roleData.getLoginResult(), roleData, null, null, null, null, null);
			GBGData2 gbgData2=new GBGData2(manData);
			cbgData.setData2(gbgData2);
		}
		return cbgData;	
	}
	/**清空长时间未用的RoleData 5分钟一次检测    4次检测都是不在线清空*/
	public static void checkRoleData(){
		Iterator<Map.Entry<BigDecimal,RoleData>> entries = allLineRoles.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<BigDecimal,RoleData> entrys = entries.next();
			RoleData roleData=entrys.getValue();
			if (roleData.upLine()) {
				entries.remove();
			}
		}
	}
}
