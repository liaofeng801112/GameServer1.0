package org.come.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.bean.LoginResult;
import org.come.entity.Ipaddressmac;
import org.come.entity.RoleTable;
import org.come.entity.Rufenghaocontrol;
import org.come.entity.UserTable;
import org.come.entity.UserxyandroledhbcrEntity;

/**
 * @author 叶豪芳
 * @date : 2017年11月21日 下午9:04:29
 */
@MyBatisAnnotation
public interface UsertableMapper {

	// 根据条件查找账号信息
	List<LoginResult> selectLogintableByCondition(LoginResult loginResult);

	// 获得所有用户角色信息
	List<LoginResult> findAllUserRoles();

	// 获得所有用户信息
	List<UserTable> findAllUser();

	// 用户登入查询用户名密码,是否存在用户
	UserTable findUserByUserNameAndUserPassword(@Param("username") String username, @Param("userpwd") String userpwd);

	// 根据用户名密码查找用户角色
	List<LoginResult> findRoleByUserNameAndPassword(@Param("userName") String username, @Param("userPwd") String userpwd, @Param("serverMeString") String serverMeString);

	// 用户注册
	int insertIntoUser(UserTable userTable);

	// 修改用户
	void updateUser(UserTable userTable);

	// 依据用户名查询对应的信息
	UserTable selectForUsername(String username);

	// 根据账号ID查找用户
	UserTable selectByPrimaryKey(BigDecimal userid);

	// 查询玩家名称
	List<RoleTable> selectByCondition(@Param("qid") String qid, @Param("rolename") String rolename);

	/**查询当前所有的玩家个数*/
	int selectSumForRoleUserHaterNumber(RoleTable roleTable);

	/**依据信息查询符合信息的数据（玩家角色管理，封号，禁言等等）*/
	List<RoleTable> selectSumForRoleUserHaterList(RoleTable roleTable);

	/**
	 * 依据条件查询页码总页数
	 */
	int selectUsterTableForConcition(UserTable table);

	/**
	 * 依据条件查询符合条件的数据
	 */
	List<UserTable> selectForConditionForUsertable(UserTable table);

	/**
	 * 依据用户ID进行修改安全码
	 */
	int updateUsterWithUid(UserTable table);

	/**
	 * 依据用户Id进行密码修改
	 */
	int updateUsterWithUidforuserpasswd(UserTable table);

	/**
	 * 依据角色名删除用户信息
	 */
	int delectUsertableForUsername(String username);

	/**
	 * 依据用户ID删除角色信息
	 */
	int deleteRoletableForUid(BigDecimal user_id);

	/**
	 * 查询是否存在ip
	 */
	Ipaddressmac selectFromIpaddressmac(String ip);

	/**
	 * 插入IP
	 */
	int insertFromIpaddressmac(String ip);

	/**
	 * 删除Ip
	 */
	int deleteFromIpaddressmac(String ip);

	/** 根据用户名查询用户所有的角色信息RoleTable */
	List<RoleTable> selectAllRoleTable(String userName);

	/** 根据用户名和角色Id更换到另一个用户身上 */
	int roleChangeUser(@Param("userName") String userName, @Param("user_id") BigDecimal user_id, @Param("roleId") String roleId);

	// 查询仙玉总量
	long selectAllCodecard();

	// 查询积分总量
	long selectAllPayintegration();

	// 查询金钱总量
	long selectAllGold();

	/** HGC--2019-11-15 start */
	/** 查询绑定某个手机号的绑定账号个数 */
	int selectPhoneNumberSum(String phoneumber);

	/** HGC-2019-11-19 */
	/** 账户充值明细条件搜索 */
	List<UserxyandroledhbcrEntity> selectAccountRechargeList(@Param("time") String time, @Param("weekendsum") String weekendsum, @Param("username") String username);

	/** 账户充值明细查询充值/消耗走势 */
	List<UserxyandroledhbcrEntity> selectAccountRechargeUser(BigDecimal userid);

	/** HGC-2019-12-01 */
	/** 查询玩家/角色大话币仙玉操作记录 */
	List<UserxyandroledhbcrEntity> selectUserRoleXianyuDahuabiList();

	/** 添加玩家/角色大话币仙玉消耗统计 */
	int addUserRoleXianyuDahuabi(UserxyandroledhbcrEntity userxyandroledhbcrEntity);

	/** HGC-2019-12-03 */
	/**
	 * 
	 * @param userTable
	 *            用户信息
	 * @param roleName
	 *            角色名
	 * @param reason
	 *            封号原因
	 * @param controlname
	 *            操作者
	 * @param type
	 *            封号还是解封操作
	 * @return
	 */
	int addRufenghaoControl(@Param("userTable") UserTable userTable, @Param("roleName") String roleName, @Param("reason") String reason, @Param("controlname") String controlname,
			@Param("type") int type);

	/**
	 * 搜索所有的封号记录
	 * 
	 * @param type
	 * @param time
	 * @param userName
	 * @param roleName
	 * @param sort
	 * @return
	 */
	List<Rufenghaocontrol> selectRufenghaoControlList(@Param("type") String type, @Param("time") String time, @Param("userName") String userName, @Param("roleName") String roleName,
			@Param("sort") int sort);

	/**
	 * 删除封号记录
	 * 
	 * @param id
	 * @return
	 */
	int deleteFenghaoRecord(BigDecimal id);
	

	/** HGC-2019-12-05 */
	/**
	 * 查询所有的总消耗与总充值
	 * 
	 * @return
	 */
	List<UserxyandroledhbcrEntity> selectRechargeConsumeSum(String time);

	/**
	 * 查询所有的总消耗与总充值总条数
	 * 
	 * @return
	 */
	int selectRechargeConsumeSumNum();

	/**
	 * 查询当前总消耗与总充值之和
	 * 
	 * @return
	 */
	UserxyandroledhbcrEntity selectRechargeConsumeNowSum();
	
	// -- 三端 根据手机号查询账号
	List<UserTable> findUserByPhoneNum(@Param("phonenum") String phonenum);
	
	/**查询最大ID*/
	BigDecimal selectUserMax();
	
	/**解封*/
	int updateUnSeal(String username);
	
	/** 查询账号是否绑定 */
	UserTable selectByFlag(@Param("flag") String flag);

	/** 查询绑定账号 */
	UserTable selectByBinding(@Param("username") String username, @Param("userpasw") String userpasw, @Param("safety") String safety);

	/** 账号绑定 */
	int updateByBinding(@Param("userTable") UserTable userTable);

	/** 根据userid查询 账号标识 */
	String selectUserFlagById(@Param("userid") BigDecimal userid);
}