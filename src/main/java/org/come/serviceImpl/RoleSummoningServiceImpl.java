package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.RoleSummoning;
import org.come.entity.RolesummoningRoleUser;
import org.come.mapper.RoleSummoningMapper;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.IRoleSummoningService;
import org.come.until.AllServiceUtil;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

public class RoleSummoningServiceImpl implements IRoleSummoningService{
	
	private RoleSummoningMapper roleSummoningMapper;
	
	public RoleSummoningServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		roleSummoningMapper = (RoleSummoningMapper) ctx.getBean("roleSummoningMapper");
		
	}
	
	/**
	 * 根据角色ID查找角色的召唤兽
	 */
	@Override
	public List<RoleSummoning> selectRoleSummoningsByRoleID(BigDecimal roleid) {
		List<RoleSummoning> roleSummonings = RedisControl.getS(RedisParameterUtil.PET, roleid.toString(), RoleSummoning.class);
		return roleSummonings;
	}
	
	/**修改索引*/
	@Override
	public void updateRoleSummoningIndex( RoleSummoning pet,BigDecimal role_id) {
		BigDecimal yrid=pet.getRoleid();//原来的召唤兽id;
    	BigDecimal nrid=role_id;//修改后的召唤兽id
    	
    	pet.setRoleid(role_id);
    	RedisControl.insertKeyT(RedisParameterUtil.PET, pet.getSid().toString(),pet);
    	RedisControl.insertController(RedisParameterUtil.PET,pet.getSid().toString(),RedisControl.CALTER);
    	//从人物召唤兽中删除
		RedisControl.deletrValue(RedisParameterUtil.PET,yrid.toString(),pet.getSid().toString() );
    	RedisControl.insertListRedis(RedisParameterUtil.PET,nrid.toString(),pet.getSid().toString());
    	
    	RoleData data=RolePool.getRoleData(yrid);
		if (data!=null) {
			data.CPet(pet.getSid(), false);
		}
		data=RolePool.getRoleData(nrid);
		if (data!=null) {
			data.CPet(pet.getSid(), true);
		}
		
		StringBuffer buffer=new StringBuffer();
		buffer.append("召唤兽名称=");
		buffer.append(pet.getSummoningname());
		buffer.append("|召唤兽初值=");
		buffer.append(pet.getHp());
		buffer.append(",");
		buffer.append(pet.getMp());
		buffer.append(",");
		buffer.append(pet.getAp());
		buffer.append(",");
		buffer.append(pet.getSp());
		buffer.append("|召唤兽技能=");
		buffer.append(pet.getPetSkills());
		buffer.append(pet.getPetQlSkills());
		AllServiceUtil.getGoodsrecordService().insertGoodsrecord(yrid, nrid, 50200, pet.getSid(), "召唤兽交易", buffer.toString(), 1);
	}

	/**根据ID修改召唤兽的属性＼名字*/
	@Override
	public void updateRoleSummoning( RoleSummoning roleSummoning ) {
		RoleSummoning pet=RedisControl.getV(RedisParameterUtil.PET, roleSummoning.getSid().toString(),RoleSummoning.class);
        if (pet!=null) {
           	BigDecimal yrid=pet.getRoleid();//原来的召唤兽id;
        	BigDecimal nrid=roleSummoning.getRoleid();
        	if (nrid!=null&&nrid.compareTo(yrid)!=0) {//用户id
        		return;
        	}
        	
        	roleSummoning.setTrainNum(pet.getTrainNum());
        	roleSummoning.setOpenSeal(pet.getOpenSeal());
        	roleSummoning.setOpenql(pet.getOpenql());
        	roleSummoning.setGrowUpDanNum(pet.getGrowUpDanNum());
        	roleSummoning.setColorScheme(pet.getColorScheme());
        	roleSummoning.setDraC(pet.getDraC());
        	roleSummoning.setRevealNum(pet.getRevealNum());
        	roleSummoning.setFlyupNum(pet.getFlyupNum());
        	roleSummoning.setSsn(pet.getSsn());
        	roleSummoning.setGold(pet.getGold());
        	roleSummoning.setWood(pet.getWood());
        	roleSummoning.setSoil(pet.getSoil());
        	roleSummoning.setWater(pet.getWater());
        	roleSummoning.setFire(pet.getFire());
        	roleSummoning.setDragon(pet.getDragon());
			roleSummoning.setSpdragon(pet.getSpdragon());
        	roleSummoning.setGrowlevel(pet.getGrowlevel());
        	roleSummoning.setHp(pet.getHp());
        	roleSummoning.setMp(pet.getMp());
        	roleSummoning.setAp(pet.getAp());
        	roleSummoning.setSp(pet.getSp());
        	roleSummoning.setSummoningid(pet.getSummoningid());
        	roleSummoning.setSkill(pet.getSkill());
        	roleSummoning.setPetSkills(pet.getPetSkills());
			roleSummoning.setPetQlSkills(pet.getPetQlSkills());
        	roleSummoning.setBeastSkills(pet.getBeastSkills());
        	roleSummoning.setSkillData(pet.getSkillData());
        	roleSummoning.setFourattributes(pet.getFourattributes());
        	roleSummoning.setFriendliness(pet.getFriendliness());
            roleSummoning.setGrade(pet.getGrade());
            roleSummoning.setExp(pet.getExp()); 
            
        	RedisControl.insertKeyT(RedisParameterUtil.PET, pet.getSid().toString(),roleSummoning);
        	RedisControl.insertController(RedisParameterUtil.PET,pet.getSid().toString(),RedisControl.CALTER);
		}
	}
	@Override
	public void updatePetRedis(RoleSummoning pet) {
		// TODO Auto-generated method stub
       	RedisControl.insertKeyT(RedisParameterUtil.PET, pet.getSid().toString(),pet);
		RedisControl.insertController(RedisParameterUtil.PET,pet.getSid().toString(),RedisControl.CALTER);
	}
	/**
	 * 根据表ID查找召唤兽信息
	 * @param
	 * @return
	 */
	@Override
	public RoleSummoning selectRoleSummoningsByRgID(BigDecimal rgid) {
		// 获取召唤兽
		RoleSummoning pet=RedisControl.getV(RedisParameterUtil.PET, rgid.toString(),RoleSummoning.class);
        return pet;
	}

	/**
	 * 根据表ID删除召唤兽
	 * @param
	 * @return
	 */
	@Override
	public void deleteRoleSummoningBySid(BigDecimal sid) {
		// 获取召唤兽
		RoleSummoning pet=RedisControl.getV(RedisParameterUtil.PET, sid.toString(),RoleSummoning.class);
		if( pet != null ){
			// 从人物召唤兽中删除
			RedisControl.deletrValue(RedisParameterUtil.PET,pet.getRoleid().toString(),sid.toString() );
			// 从召唤兽表中删除
			RedisControl.delForKey(RedisParameterUtil.PET, sid.toString());
			// 加入操作表
			RedisControl.insertController(RedisParameterUtil.PET, sid.toString(),RedisControl.CDELETE);
			RoleData data=RolePool.getRoleData(pet.getRoleid());
			if (data!=null) {
				data.CPet(pet.getSid(), false);
			}
		}
	}

	/**
	 * 添加召唤兽
	 * @param roleSummoning
	 * @return
	 */
	@Override
	public void insertRoleSummoning(RoleSummoning roleSummoning) {
		// 获取召唤兽主键
		roleSummoning.setSid(RedisCacheUtil.getPet_pk());
		roleSummoning.setBone(0);
		roleSummoning.setSpir(0);
		roleSummoning.setPower(0);
		roleSummoning.setSpeed(0);
		roleSummoning.setCalm(0);
		roleSummoning.setGrade(0);
		roleSummoning.setExp(new BigDecimal(0));
		roleSummoning.setFaithful(100);
		roleSummoning.setFriendliness(0L);
		roleSummoning.setOpenSeal(1);
		roleSummoning.setOpenql(0);
		roleSummoning.setDragon(0);
		roleSummoning.setSpdragon(0);
		roleSummoning.setAlchemynum(0);
		roleSummoning.setGrowUpDanNum(0);
		roleSummoning.setDraC(0);
		// 插入数据表缓存
		//RedisControl.insertKey(RedisParameterUtil.PET, roleSummoning.getSid().toString(), GsonUtil.getGsonUtil().getgson().toJson(roleSummoning));
		RedisControl.insertKeyT(RedisParameterUtil.PET, roleSummoning.getSid().toString(), roleSummoning);
		// 插入人物召唤兽缓存
		RedisControl.insertListRedis(RedisParameterUtil.PET, roleSummoning.getRoleid().toString(), roleSummoning.getSid().toString());
		// 加入操作表
		RedisControl.insertController(RedisParameterUtil.PET, roleSummoning.getSid().toString(),RedisControl.CADD);	
		RoleData data=RolePool.getRoleData(roleSummoning.getRoleid());
		if (data!=null) {
			data.CPet(roleSummoning.getSid(), true);
		}
	}
	// 查找所有角色召唤兽
	@Override
	public List<RoleSummoning> selectAllRoleSummonings() {
		return roleSummoningMapper.selectAllRoleSummonings();
	}
	@Override
	public BigDecimal selectMaxID() {
		return roleSummoningMapper.selectMaxID();
	}
	@Override
	public void updateRoleSummoningsql(RoleSummoning roleSummoning) {
		// TODO Auto-generated method stub
		roleSummoningMapper.updateRoleSummoning(roleSummoning);
	}
	@Override
	public void deleteRoleSummoningBySidsql(BigDecimal sid) {
		// TODO Auto-generated method stub
		roleSummoningMapper.deleteRoleSummoningBySid(sid);
	}
	@Override
	public void insertRoleSummoningsql(RoleSummoning roleSummoning) {
		// TODO Auto-generated method stub
		roleSummoningMapper.insertRoleSummoning(roleSummoning);
	}
	
	private final Integer limit = 10;

	@Override
	public List<RolesummoningRoleUser> selectRsRU(RolesummoningRoleUser rru) {
		// TODO Auto-generated method stub
		Integer start = ((rru.getPageNow() - 1) * limit) + 1;
		Integer end = start + limit;
		rru.setStart(start);
		rru.setEnd(end);
		List<RolesummoningRoleUser> rsRUList = roleSummoningMapper.selectRsRU(rru);
		return rsRUList;
	}

	@Override
	public Integer selectRsRUCount(RolesummoningRoleUser rru) {
		// TODO Auto-generated method stub
		Integer rsRUCount = roleSummoningMapper.selectRsRUCount(rru);
		return rsRUCount;
	}

	@Override
	public RolesummoningRoleUser selectRoleSummoningById(String summoningid) {
		// TODO Auto-generated method stub
		return roleSummoningMapper.selectRoleSummoningById(summoningid);
	}
	
	 @Override
	    public void deleteRoleSummoningBySidList(List<BigDecimal> list) {
	        // TODO Auto-generated method stub
	        roleSummoningMapper.deleteRoleSummoningBySidList(list);
	    }

	    @Override
	    public void insertRoleSummoningList(List<RoleSummoning> list) {
	        // TODO Auto-generated method stub
	        roleSummoningMapper.insertRoleSummoningList(list);
	    }

	    @Override
	    public void updateRoleSummoningList(List<RoleSummoning> list) {
	        // TODO Auto-generated method stub
	        roleSummoningMapper.updateRoleSummoningList(list);
	    }

	    @Override
	    public void insertRoleSummoningSingle(RoleSummoning roleSummoning) {
	        // TODO Auto-generated method stub
	        roleSummoningMapper.insertRoleSummoningSingle(roleSummoning);
	    }
}
