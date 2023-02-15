package org.come.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.come.bean.BackRoleInfo;
import org.come.bean.LoginResult;
import org.come.entity.Haters;
import org.come.entity.Ipaddressmac;
import org.come.entity.RoleTable;
import org.come.entity.Rufenghaocontrol;
import org.come.entity.UserTable;
import org.come.entity.UserxyandroledhbcrEntity;
import org.come.mapper.UsertableMapper;
import org.come.redis.RedisControl;
import org.come.server.GameServer;
import org.come.service.IHatersService;
import org.come.service.IUserTableService;
import org.come.until.GsonUtil;
import org.come.until.MybatisUntil;
import org.come.until.TimeUntil;
import org.springframework.context.ApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 用户表操作类
 *
 * @author 叶豪芳
 * @date : 2017年11月21日 下午9:19:53
 */
public class UserTableServiceImpl implements IUserTableService {

	private UsertableMapper userTableMapper;

	public UserTableServiceImpl() {

		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		userTableMapper = (UsertableMapper) ctx.getBean("usertableMapper");

	}

	/*
	 * 查找角色(non-Javadoc)
	 *
	 * @see
	 * org.come.service.IUserTableService#findRoleByUserNameAndPassword(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public List<LoginResult> findRoleByUserNameAndPassword(String username, String userpwd, String serverMeString) {

		List<LoginResult> roles = userTableMapper.findRoleByUserNameAndPassword(username, userpwd, serverMeString);

		return roles;
	}

	/*
	 * 用户登入(non-Javadoc)
	 *
	 * @see
	 * org.come.service.IUserTableService#findUserByUserNameAndUserPassword(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public UserTable findUserByUserNameAndUserPassword(String userName, String userPwd) {
		UserTable userTable = userTableMapper.findUserByUserNameAndUserPassword(userName, userPwd);
		return userTable;
	}

	/*
	 * 用户注册(non-Javadoc)
	 *
	 * @see
	 * org.come.service.IUserTableService#insertIntoUser(org.come.bean.UserTable
	 * )
	 */
	@Override
	public int insertIntoUser(UserTable userTable) {
		// 插入注册时间
		userTable.setUserregidtsertime(TimeUntil.getPastDate());
		int message = userTableMapper.insertIntoUser(userTable);
		RedisControl.userController("U", userTable.getUser_id().toString(), RedisControl.CADD, GsonUtil.getGsonUtil().getgson().toJson(userTable));
		return message;
	}

	// 获得所有用户角色信息
	@Override
	public List<LoginResult> findAllUserRoles() {
		List<LoginResult> roles = userTableMapper.findAllUserRoles();
		return roles;
	}

	// 获得所有用户信息
	@Override
	public List<UserTable> findAllUser() {
		List<UserTable> userTable = userTableMapper.findAllUser();
		return userTable;
	}

	// 修改用户
	@Override
	public void updateUser(UserTable userTable) {
		userTableMapper.updateUser(userTable);

	}

	@Override
	public PageInfo<LoginResult> selectLogintableByCondition(Integer pageNum, String condition) {
		LoginResult loginResult = GsonUtil.getGsonUtil().getgson().fromJson(condition, LoginResult.class);
		PageHelper.startPage(pageNum, 8);
		List<LoginResult> list = userTableMapper.selectLogintableByCondition(loginResult);
		PageInfo<LoginResult> pageinfo = new PageInfo<>(list);
		return pageinfo;
	}

	@Override
	public UserTable selectForUsername(String username) {
		return userTableMapper.selectForUsername(username);
	}

	@Override
	public UserTable selectByPrimaryKey(BigDecimal userid) {
		return userTableMapper.selectByPrimaryKey(userid);
	}

	@Override
	public BackRoleInfo selectByCondition(String qid, String rolename, int pageNum, String statues) {

		BackRoleInfo jb = new BackRoleInfo();
		// 根据玩家状态查询
		if (statues != null) {
			List<RoleTable> roles = userTableMapper.selectByCondition(qid, null);
			roleState(roles);
			// 需要返回的集合
			List<RoleTable> selectRoles = new ArrayList<>();
			List<RoleTable> returnRoles = new ArrayList<>();

			switch (statues) {
				case "1":
					for (RoleTable roleTable : roles) {
						if (roleTable.getStatues().indexOf("1") != -1) {
							selectRoles.add(roleTable);
						}
					}
					break;
				case "2":
					for (RoleTable roleTable : roles) {
						if (roleTable.getStatues().indexOf("2") != -1) {
							selectRoles.add(roleTable);
						}
					}
					break;
				case "3":
					for (RoleTable roleTable : roles) {
						if (roleTable.getStatues().indexOf("3") != -1) {
							selectRoles.add(roleTable);
						}
					}
					break;
				case "4":
					for (RoleTable roleTable : roles) {
						if (roleTable.getStatues().indexOf("4") != -1) {
							selectRoles.add(roleTable);
						}
					}
					break;
				case "5":
					for (RoleTable roleTable : roles) {
						if (roleTable.getStatues().indexOf("4") == -1) {
							selectRoles.add(roleTable);
						}
					}
					break;
				case "6":
					for (RoleTable roleTable : roles) {
						if (roleTable.getStatues().indexOf("3") == -1) {
							selectRoles.add(roleTable);
						}
					}
					break;

				default:
					System.out.println("标识错误！！！");
					break;
			}

			// 查询角色名
			if (rolename != null) {
				for (RoleTable roleTable : selectRoles) {
					if (roleTable.getRolename().indexOf(rolename) == -1) {
						returnRoles.add(roleTable);
					}
				}
			} else {
				returnRoles.addAll(selectRoles);
			}

			// 进行分页
			int size = returnRoles.size();// 总条数
			int pages = 0;// 总页数
			if (size < 8) {
				if (size == 0) {
					pages = 0;
				} else {
					pages = 1;
				}
				jb.setList(returnRoles);
			} else if (size % 8 == 0) {
				pages = size / 8;
				jb.setList(returnRoles.subList((pageNum - 1) * 8, pageNum * 8 - 1));
			} else {
				pages = (int) Math.floor(size / 8) + 1;
				if (pageNum == pages) {
					jb.setList(returnRoles.subList(pageNum * 8, size - 1));
				} else {
					jb.setList(returnRoles.subList((pageNum - 1) * 8, pageNum * 8 - 1));
				}
			}
			jb.setPageNum(pageNum);
			jb.setPages(pages);
		} else {
			PageHelper.startPage(pageNum, 8);
			List<RoleTable> list = userTableMapper.selectByCondition(qid, rolename);
			PageInfo<RoleTable> pageInfo = new PageInfo<>(list);
			roleState(pageInfo.getList());
			jb.setList(pageInfo.getList());
			jb.setPageNum(pageInfo.getPageNum());
			jb.setPages(pageInfo.getPages());
		}

		return jb;
	}

	/**
	 * 判断玩家状态
	 *
	 * @param list
	 */
	public void roleState(List<RoleTable> list) {
		IHatersService hatersService = new HatersServiceImpl();
		for (RoleTable roleInfo : list) {

			// 玩家状态1、在线 2、下线 3、禁言 4、封号5、未封号 6、未禁言
			String status = "";
			// 查询玩家状态
			if (GameServer.getRoleNameMap().get(roleInfo.getRolename()) != null) {
				status += "/" + 1;
			} else {
				status += "/" + 2;
			}

			// 是否禁言
			Haters hater = hatersService.selectByPrimaryKey(roleInfo.getRole_id());
			if (hater != null) {
				status += "/" + 3;
			} else {
				status += "/" + 6;
			}

			// 是否被封
			UserTable userInfo = userTableMapper.selectByPrimaryKey(roleInfo.getUser_id());
			if (userInfo.getActivity() != 0) {
				status += "/" + 4;
			} else {
				status += "/" + 5;

			}

			roleInfo.setStatues(status.replaceFirst("/", ""));
		}
	}

	@Override
	public int selectSumForRoleUserHaterNumber(RoleTable roleTable) {
		// TODO Auto-generated method stub
		return userTableMapper.selectSumForRoleUserHaterNumber(roleTable);
	}

	@Override
	public List<RoleTable> selectSumForRoleUserHaterList(RoleTable roleTable) {
		// TODO Auto-generated method stub
		return userTableMapper.selectSumForRoleUserHaterList(roleTable);
	}

	@Override
	public int selectUsterTableForConcition(UserTable table) {
		// TODO Auto-generated method stub
		return userTableMapper.selectUsterTableForConcition(table);
	}

	@Override
	public List<UserTable> selectForConditionForUsertable(UserTable table) {
		// TODO Auto-generated method stub
		return userTableMapper.selectForConditionForUsertable(table);
	}

	@Override
	public int updateUsterWithUid(UserTable table) {
		// TODO Auto-generated method stub
		return userTableMapper.updateUsterWithUid(table);
	}

	@Override
	public int updateUsterWithUidforuserpasswd(UserTable table) {
		// TODO Auto-generated method stub
		return userTableMapper.updateUsterWithUidforuserpasswd(table);
	}

	@Override
	public int delectUsertableForUsername(String username) {
		// TODO Auto-generated method stub
		return userTableMapper.delectUsertableForUsername(username);
	}

	@Override
	public int deleteRoletableForUid(BigDecimal user_id) {
		// TODO Auto-generated method stub
		return userTableMapper.deleteRoletableForUid(user_id);
	}

	@Override
	public Ipaddressmac selectFromIpaddressmac(String ip) {
		// TODO Auto-generated method stub
		return userTableMapper.selectFromIpaddressmac(ip);
	}

	@Override
	public int insertFromIpaddressmac(String ip) {
		// TODO Auto-generated method stub
		return userTableMapper.insertFromIpaddressmac(ip);
	}

	@Override
	public int deleteFromIpaddressmac(String ip) {
		// TODO Auto-generated method stub
		return userTableMapper.deleteFromIpaddressmac(ip);
	}

	@Override
	public List<RoleTable> selectAllRoleTable(String userName) {
		// TODO Auto-generated method stub
		return userTableMapper.selectAllRoleTable(userName);
	}

	@Override
	public int roleChangeUser(String userName, BigDecimal user_id, String roleId) {
		// TODO Auto-generated method stub
		return userTableMapper.roleChangeUser(userName, user_id, roleId);
	}

	@Override
	public long selectAllCodecard() {
		// TODO Auto-generated method stub
		return userTableMapper.selectAllCodecard();
	}

	@Override
	public long selectAllPayintegration() {
		// TODO Auto-generated method stub
		return userTableMapper.selectAllPayintegration();
	}

	@Override
	public long selectAllGold() {
		// TODO Auto-generated method stub
		return userTableMapper.selectAllGold();
	}

	/** HGC--2019-11-15 start */
	/** 查询绑定某个手机号的绑定账号个数 */
	@Override
	public int selectPhoneNumberSum(String phoneumber) {
		return userTableMapper.selectPhoneNumberSum(phoneumber);
	}

	/** HGC-2019-11-19 */
	/** 账号充值明细条件搜索 */
	@Override
	public List<UserxyandroledhbcrEntity> selectAccountRechargeList(String time, String weekendsum, int page, String username) {
		PageHelper.startPage(page, 10);
		if (username != null && !"".equals(username) && !(time != null && !"".equals(time))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time = sdf.format(new Date());
		}
		return userTableMapper.selectAccountRechargeList(time, weekendsum, username);
	}

	/** 账户充值明细查询充值/消耗走势 */
	@Override
	public List<UserxyandroledhbcrEntity> selectAccountRechargeUser(BigDecimal userid) {

		return userTableMapper.selectAccountRechargeUser(userid);
	}

	/** HGC-2019-12-01 */
	@Override
	public List<UserxyandroledhbcrEntity> selectUserRoleXianyuDahuabiList() {
		return userTableMapper.selectUserRoleXianyuDahuabiList();
	}

	@Override
	public int addUserRoleXianyuDahuabi(UserxyandroledhbcrEntity userxyandroledhbcrEntity) {
		// TODO Auto-generated method stub
		return userTableMapper.addUserRoleXianyuDahuabi(userxyandroledhbcrEntity);
	}

	@Override
	public int addRufenghaoControl(UserTable userTable, String roleName, String reason, String controlname, int type) {
		// TODO Auto-generated method stub
		return userTableMapper.addRufenghaoControl(userTable, roleName, reason, controlname, type);
	}

	@Override
	public List<Rufenghaocontrol> selectRufenghaoControlList(String type, String time, String userName, String roleName, int page, int sort) {
		PageHelper.startPage(page, 10);
		return userTableMapper.selectRufenghaoControlList(type, time, userName, roleName, sort);
	}

	@Override
	public int deleteFenghaoRecord(BigDecimal id) {
		// TODO Auto-generated method stub
		return userTableMapper.deleteFenghaoRecord(id);
	}


	/** HGC-2019-12-05 */
	/**
	 * 查询所有的总消耗与总充值
	 *
	 * @param page
	 * @return
	 */
	@Override
	public List<UserxyandroledhbcrEntity> selectRechargeConsumeSum(String time) {
		return userTableMapper.selectRechargeConsumeSum(time);

	}

	@Override
	public int selectRechargeConsumeSumNum() {
		// TODO Auto-generated method stub
		return userTableMapper.selectRechargeConsumeSumNum();
	}

	@Override
	public UserxyandroledhbcrEntity selectRechargeConsumeNowSum() {
		// TODO Auto-generated method stub
		return userTableMapper.selectRechargeConsumeNowSum();
	}

	@Override
	public BigDecimal selectUserMax() {
		// TODO Auto-generated method stub
		return userTableMapper.selectUserMax();
	}

	// -- 三端 通过手机号获取账号
	@Override
	public List<UserTable> findUserByPhoneNum(String phonenum) {
		// TODO Auto-generated method stub
		return userTableMapper.findUserByPhoneNum(phonenum);
	}

	@Override
	public int updateUnSeal(String username) {
		// TODO Auto-generated method stub
		return userTableMapper.updateUnSeal(username);
	}
	@Override
	public UserTable selectByFlag(String flag) {
		// TODO Auto-generated method stub
		return userTableMapper.selectByFlag(flag);
	}

	@Override
	public UserTable selectByBinding(String username, String userpasw, String safety) {
		// TODO Auto-generated method stub
		return userTableMapper.selectByBinding(username, userpasw, safety);
	}

	@Override
	public int updateByBinding(UserTable userTable) {
		// TODO Auto-generated method stub
		return userTableMapper.updateByBinding(userTable);
	}

	@Override
	public String selectUserFlagById(BigDecimal userid) {
		// TODO Auto-generated method stub
		return userTableMapper.selectUserFlagById(userid);
	}
}
