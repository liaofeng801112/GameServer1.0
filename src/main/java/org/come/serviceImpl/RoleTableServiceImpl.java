package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.LoginResult;
import org.come.entity.RoleTable;
import org.come.mapper.RoleTableMapper;
import org.come.redis.RedisControl;
import org.come.service.IRoleTableService;
import org.come.until.GsonUtil;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class RoleTableServiceImpl implements IRoleTableService{

	private RoleTableMapper roleTableMapper;
	
	public RoleTableServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到	
		roleTableMapper = ctx.getBean("roleTableMapper",RoleTableMapper.class);
	}
	public static void main(String[] args) {
		RoleTableServiceImpl impl = new RoleTableServiceImpl();
		RoleTable roleTable = new RoleTable();
		roleTable.setRole_id(new BigDecimal(11));
		impl.updateByPrimaryKey(roleTable);
	}
	//创建用户
	@Override
	public boolean insertIntoRoleTable(LoginResult loginResult) {		
		boolean exist = roleTableMapper.insertIntoRoleTable(loginResult);
		RedisControl.userController("R",loginResult.getRole_id().toString(),RedisControl.CADD,GsonUtil.getGsonUtil().getgson().toJson(loginResult));
		return exist;
	}
	// 查询帮派所有成员
	@Override
	public List<LoginResult> findGangManberByGangID(BigDecimal gang_id) {
		List<LoginResult> gangRoles = roleTableMapper.findGangManberByGangID(gang_id);
		return gangRoles;
	}
	// 修改角色属性
	@Override
	public boolean updateRole(RoleTable roleTable) {

		boolean isSuccess = roleTableMapper.updateRole(roleTable);
		
		return isSuccess;
	}
	// 查找角色名是否存在
	@Override
	public RoleTable selectRoleTableByRoleName(String rolename) {

		RoleTable roleTable = roleTableMapper.selectRoleTableByRoleName(rolename);
		return roleTable;
	}
	// 用户退出时修改角色属性
	@Override
	public void updateRoleWhenExit(LoginResult loginResult) {
		
		roleTableMapper.updateRoleWhenExit(loginResult);
		
	}
	@Override
	public BigDecimal selectMoneyRoleID(BigDecimal role_id) {
		// TODO Auto-generated method stub
		return roleTableMapper.selectMoneyRoleID(role_id);
	}
	@Override
	public int updateMoneyRoleID(BigDecimal role_id,BigDecimal gold) {
		// TODO Auto-generated method stub
		return roleTableMapper.updateMoneyRoleID(role_id,gold);
	}
	@Override
	public void updateByPrimaryKey(RoleTable roleTable) {
		roleTableMapper.updateByPrimaryKey(roleTable);		
	}
	// 查询排行榜
	@Override
	public List<LoginResult> selectOrderByType(Integer type) {
		return roleTableMapper.selectOrderByType(type);
	}
	@Override
	public LoginResult selectRoleID(BigDecimal role_id) {
		// TODO Auto-generated method stub
		return roleTableMapper.selectRoleID(role_id);
	}
	@Override
	public int updateRolePwdForRid(RoleTable roleTable) {
		// TODO Auto-generated method stub
		return roleTableMapper.updateRolePwdForRid(roleTable);
	}
	@Override
	public List<LoginResult> selectSLDH() {
		// TODO Auto-generated method stub
		return roleTableMapper.selectSLDH();
	}
	@Override
	public RoleTable selectGang(BigDecimal role_id) {
		// TODO Auto-generated method stub
		return roleTableMapper.selectGang(role_id);
	}
	@Override
	public int updateGang(RoleTable roleTable) {
		// TODO Auto-generated method stub
		return roleTableMapper.updateGang(roleTable);
	}
	@Override
	public BigDecimal selectRoleMax() {
		// TODO Auto-generated method stub
		return roleTableMapper.selectRoleMax();
	}
	@Override
	public List<LoginResult> selectRoleByUserid(BigDecimal userid, BigDecimal fuserid) {
		// TODO Auto-generated method stub
		return roleTableMapper.selectRoleByUserid(userid, fuserid);
	}
	@Override
	public int updateRoleStatues(BigDecimal roleid) {
		// TODO Auto-generated method stub
		return roleTableMapper.updateRoleStatues(roleid);
	}
	@Override
	public int updateRoleBelong(BigDecimal roleid, BigDecimal userid) {
		// TODO Auto-generated method stub
		return roleTableMapper.updateRoleBelong(roleid, userid);
	}
	@Override
	public LoginResult selectRoleByRoleId(BigDecimal roleid) {
		// TODO Auto-generated method stub
		return roleTableMapper.selectRoleByRoleId(roleid);
	}
	@Override
	public LoginResult selectRoleName(String rolename) {
		// TODO Auto-generated method stub
		return roleTableMapper.selectRoleName(rolename);
	}
	
	@Override
	public void addQMJJ(BigDecimal roleid, int add) {
		// TODO Auto-generated method stub
		roleTableMapper.addQMJJ(roleid, add);
	}

	@Override
	public List<LoginResult> selectRoleByRoleNum(int count,String notInStr) {
		// TODO Auto-generated method stub
		return roleTableMapper.selectRoleByRoleNum(count,notInStr);
	}
}
