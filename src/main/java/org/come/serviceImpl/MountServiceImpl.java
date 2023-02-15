package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.entity.Mount;
import org.come.entity.MountRoleUser;
import org.come.entity.MountSkill;
import org.come.mapper.MountMapper;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.IMountService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

public class MountServiceImpl implements IMountService{
	private MountMapper mountMapper;
	
	public MountServiceImpl() {
		
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		mountMapper = (MountMapper) ctx.getBean("mountMapper");
	
	}
	/**根据角色ID查找角色坐骑*/
	@Override
	public List<Mount> selectMountsByRoleID( BigDecimal roleID ) {
		List<Mount> mounts = RedisControl.getS(RedisParameterUtil.MOUNT, roleID.toString(), Mount.class);
		return mounts;
	}
	/**查找角色坐骑*/
	@Override
	public Mount selectMountsByMID(BigDecimal mid) {
		Mount mount=RedisControl.getV(RedisParameterUtil.MOUNT, mid.toString(), Mount.class);
		return mount;
	}
	/**删除角色坐骑*/
	@Override
	public void deleteMountsByMid(BigDecimal roleID) {
		Mount mount=RedisControl.getV(RedisParameterUtil.MOUNT,roleID.toString(), Mount.class);
		if (mount!=null) {
			// 删除角色下的坐骑
			RedisControl.deletrValue(RedisParameterUtil.MOUNT, mount.getRoleid().toString(), mount.getMid().toString());
			// 删除数据表中的数据
			RedisControl.delForKey(RedisParameterUtil.MOUNT, mount.getMid().toString());
			// 加入操作表
			RedisControl.insertController(RedisParameterUtil.MOUNT, mount.getMid().toString(), RedisControl.CDELETE);
			RoleData data=RolePool.getRoleData(mount.getRoleid());
			if (data!=null) {
				data.MPet(mount,false);
			}
		}
	}
	/**修改坐骑属性*/
	@Override
	public void updateMount(Mount mount) {
		Mount mount2=RedisControl.getV(RedisParameterUtil.MOUNT, mount.getMid().toString(), Mount.class);
		if (mount2!=null) {
			RedisControl.insertKeyT(RedisParameterUtil.MOUNT, mount2.getMid().toString(),mount2);	
			// 加入操作表
			RedisControl.insertController(RedisParameterUtil.MOUNT, mount2.getMid().toString(), 2+"");		
		}
	}
	@Override
	public void updateMountRedis(Mount mount) {
		// TODO Auto-generated method stub
		RedisControl.insertKeyT(RedisParameterUtil.MOUNT, mount.getMid().toString(), mount);
		// 加入操作表
		RedisControl.insertController(RedisParameterUtil.MOUNT, mount.getMid().toString(), 2+"");		
	}
	/**添加坐骑*/
	@Override
	public void insertMount(Mount mount) {
		mount.setMid(RedisCacheUtil.getMount_pk());
		mount.setExp(0);
		mount.setUseNumber(0);
		mount.setProficiency(0);
		List<MountSkill> mountskill=new ArrayList<>();
		mount.setMountskill(mountskill);
		RedisControl.insertKeyT(RedisParameterUtil.MOUNT, mount.getMid().toString(),mount);
		// 插入人物坐骑
		RedisControl.insertListRedis(RedisParameterUtil.MOUNT, mount.getRoleid().toString(), mount.getMid().toString());
		// 加入操作表
		RedisControl.insertController(RedisParameterUtil.MOUNT, mount.getMid().toString(), 1+"");
	}
	
	// 查找所有坐骑
	@Override
	public List<Mount> selectAllMounts() {
		return mountMapper.selectAllMounts();
	}
	@Override
	public BigDecimal selectMaxID() {
		return mountMapper.selectMaxID();
	}
	@Override
	public void deleteMountsByMidsql(BigDecimal roleID) {
		// TODO Auto-generated method stub
		mountMapper.deleteMountsByMid(roleID);
	}
	@Override
	public void updateMountsql(Mount mount) {
		// TODO Auto-generated method stub
		mountMapper.updateMount(mount);
	}
	@Override
	public void insertMountsql(Mount mount) {
		// TODO Auto-generated method stub
		mountMapper.insertMount(mount);
	}
	
	private final Integer limit = 10;

	@Override
	public List<MountRoleUser> selectMount(MountRoleUser mru) {
		// TODO Auto-generated method stub
		Integer start = ((mru.getPageNum() - 1) * limit) + 1;
		Integer end = start + limit;
		mru.setStart(start);
		mru.setEnd(end);
		List<MountRoleUser> mountList = mountMapper.selectMount(mru);
		return mountList;
	}

	@Override
	public Integer selectMountCount(MountRoleUser mru) {
		// TODO Auto-generated method stub
		Integer mountCount = mountMapper.selectMountCount(mru);
		return mountCount;
	}
	
	@Override
    public void deleteMountsByMidList(List<BigDecimal> roleID) {
        // TODO Auto-generated method stub
        mountMapper.deleteMountsByMidList(roleID);

    }

    @Override
    public void updateMountList(List<Mount> mount) {
        // TODO Auto-generated method stub
        mountMapper.updateMountList(mount);

    }

    @Override
    public void insertMountList(List<Mount> mount) {
        // TODO Auto-generated method stub
        mountMapper.insertMountList(mount);
    }

    @Override
    public void insertMountSingle(Mount mount) {
        // TODO Auto-generated method stub
        mountMapper.insertMountSingle(mount);
    }
}
