package org.come.thread;

import java.math.BigDecimal;
import java.util.Map;

import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.PackRecord;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;
import org.come.entity.UserTable;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.redis.RedisPoolUntil;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.TimeUntil;

import redis.clients.jedis.Jedis;


/**每5分钟执行一次该线程和数据库进行同步*/
public class RedisEqualWithSqlThread implements Runnable{

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		AllToDatabase();
	}
	public static Object object=new Object();
	
	private static DataBaseControl dataBaseControl = new DataBaseControl();
	/**同步数据库*/
	public static void AllToDatabase(){	
		synchronized (object) {
			//获取对应表格的所有数据
			Jedis jedis=RedisPoolUntil.getJedis();
			Map<String, String> redisChangeMap=jedis.hgetAll(RedisParameterUtil.ROLE_CONTROL);
			jedis.del(RedisParameterUtil.ROLE_CONTROL);
			RedisPoolUntil.returnResource(jedis);
			long x=System.currentTimeMillis();
			int size=0;
			//进行map遍历
		    System.err.println("开始同步数据库本次数量:"+redisChangeMap.size());
			for (String key : redisChangeMap.keySet()) {
				insertIntoValueToSql(key,redisChangeMap.get(key));
				size++;		
				if (size%50000==0) {
					System.out.println(size+":"+redisChangeMap.size());
				}
			}
			DataBaseControl.manageBaby.ClearList();
            DataBaseControl.manageGoodstable.ClearList();
            DataBaseControl.manageLingBao.ClearList();
            DataBaseControl.manageMount.ClearList();
            DataBaseControl.managePal.ClearList();
            DataBaseControl.managePet.ClearList();
			if (size==0) {
			    System.err.println("本次没有物品数据同步");
			    return;
		    }
		    long y=System.currentTimeMillis()-x;
		    StringBuffer buffer=new StringBuffer();
		    buffer.append("同步");
		    buffer.append(size);
		    buffer.append("条物品数据用时:");
		    buffer.append(y);
		    buffer.append("平均用时");
		    buffer.append(y/size);
		    System.err.println(buffer.toString());
		    if (y>200000) {
		    	System.err.println("同步数据库超时:"+y);
		    	WriteOut.addtxt("同步数据库超时",y);
			}
		}
	}
	/**插入数据库操作  一区78毫秒*/
	public static void insertIntoValueToSql(String key,String value){
		String[] mesGet=key.split(":");
		dataBaseControl.control(value, mesGet[1], mesGet[0]);
	}
	  /**物品表*/
	  public static void controlLingbaoForm(String value,String ID){
		  if(value.equals("3")){//删除
			  AllServiceUtil.getLingbaoService().deleteLingbaosql(new BigDecimal(ID));
		      return;
		  }		 
		  Lingbao lingbao=RedisControl.getV(RedisParameterUtil.LINGBAO,ID,Lingbao.class);
		  if (lingbao==null) {
			  System.out.println("同步数据库出错:"+RedisParameterUtil.LINGBAO+":"+ID+":"+value);
			  return;
		  }
		  if(value.equals("1")){//插入操作
			  AllServiceUtil.getLingbaoService().insertLingbaosql(lingbao);
		  }else if(value.equals("2")){//修改	
			  AllServiceUtil.getLingbaoService().updateLingbaosql(lingbao);
		  }
	  } 
	  /**召唤兽表*/
	  public static void controlPetForm(String value,String ID){
		  if(value.equals("3")){//删除
			  AllServiceUtil.getRoleSummoningService().deleteRoleSummoningBySidsql(new BigDecimal(ID));
		      return;
		  }		 
		  RoleSummoning pet=RedisControl.getV(RedisParameterUtil.PET,ID,RoleSummoning.class);
		  if (pet==null) {
			  System.out.println("同步数据库出错:"+RedisParameterUtil.PET+":"+ID+":"+value);
			  return;
		  }
		  if(value.equals("1")){//插入操作
			  AllServiceUtil.getRoleSummoningService().insertRoleSummoningsql(pet);
			  AllServiceUtil.getRoleSummoningService().updateRoleSummoningsql(pet);
		  }else if(value.equals("2")){//修改	
			  AllServiceUtil.getRoleSummoningService().updateRoleSummoningsql(pet);
		  }
	  } 
	  /**召唤兽表*/
	  public static void controlBabyForm(String value,String ID){
		  if(value.equals("3")){//删除
//			  AllServiceUtil.getBabyService().deleteBabysql(new BigDecimal(ID));
		      return;
		  }		 
		  Baby baby=RedisControl.getV(RedisParameterUtil.BABY,ID,Baby.class);
		  if (baby==null) {
			  System.out.println("同步数据库出错:"+RedisParameterUtil.BABY+":"+ID+":"+value);
			  return;
		  }
		  if(value.equals("1")){//插入操作
			  AllServiceUtil.getBabyService().createBabysql(baby);
			  AllServiceUtil.getBabyService().updateBabysql(baby);
		  }else if(value.equals("2")){//修改	
			  AllServiceUtil.getBabyService().updateBabysql(baby);
		  }
	  }
	  /**召唤兽表*/
	  public static void controlMountForm(String value,String ID){
		  if(value.equals("3")){//删除
			  AllServiceUtil.getMountService().deleteMountsByMidsql(new BigDecimal(ID));
		      return;
		  }		 
		  Mount mount=RedisControl.getV(RedisParameterUtil.MOUNT,ID,Mount.class);
		  if (mount==null) {
			  System.out.println("同步数据库出错:"+RedisParameterUtil.MOUNT+":"+ID+":"+value);
			  return;
		  }
		  if(value.equals("1")){//插入操作
			  AllServiceUtil.getMountService().insertMountsql(mount);
			  AllServiceUtil.getMountService().updateMountsql(mount);
		  }else if(value.equals("2")){//修改	
			  AllServiceUtil.getMountService().updateMountsql(mount);
		  }
	  } 
	  /**伙伴表*/
	  public static void controlPalForm(String value,String ID){
		  if(value.equals("3")){//删除
			  AllServiceUtil.getPalService().deletePalSql(new BigDecimal(ID));
		      return;
		  }	
		  Pal pal=RedisControl.getV(RedisParameterUtil.PAL, ID, Pal.class);
		  if (pal==null) {
			  System.out.println("同步数据库出错:"+RedisParameterUtil.PAL+":"+ID+":"+value);
			  return;
		  }
		  if(value.equals("1")){//插入操作
			  AllServiceUtil.getPalService().insertPalSql(pal);
		  }else if(value.equals("2")){//修改	
			  AllServiceUtil.getPalService().updatePalSql(pal);
		  }
	  }
	  /**同步备份的玩家数据*/
	  public static void AllToDataRole(){
		  Jedis jedis=RedisPoolUntil.getJedis();
		  //获取对应表格的所有数据
		  Map<String, String> Map1=jedis.hgetAll(RedisParameterUtil.COPY_LOGIN);
		  Map<String, String> Map2=jedis.hgetAll(RedisParameterUtil.COPY_PACK);
		  RedisPoolUntil.returnResource(jedis);
		  long x=System.currentTimeMillis();
		  int size=0;
		  System.out.println("开始同步玩家数据本次数量:"+Map1.size());
		  for (String key : Map1.keySet()) {
				try {//0 是表名  1是表ID
					LoginResult loginResult=GsonUtil.getGsonUtil().getgson().fromJson(Map1.get(key),LoginResult.class);
					// 修改用户点卡
					UserTable userTable = new UserTable();
					userTable.setCodecard(loginResult.getCodecard());
					userTable.setMoney(loginResult.getMoney());
					userTable.setUsername(loginResult.getUserName());
					//设置下线时间
					userTable.setUSERLASTLOGIN(TimeUntil.getPastDate());
					AllServiceUtil.getUserTableService().updateUser(userTable);
					// 保存角色信息
					AllServiceUtil.getRoleTableService().updateRoleWhenExit(loginResult);
					PackRecord packRecord=GsonUtil.getGsonUtil().getgson().fromJson(Map2.get(key),PackRecord.class);
					if (packRecord!=null) {
						AllServiceUtil.getPackRecordService().updateByPrimaryKeySelective(packRecord);					
					}else {System.err.println("缺少背包记录");}	
					System.err.println("处理玩家下线成功" + loginResult.getRolename());
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println("同步人物数据库报错"+key+":"+Map1.get(key)+":"+Map2.get(key));
					WriteOut.addtxt("同步人物数据库报错"+key+":"+Map1.get(key)+":"+Map2.get(key),999);
					e.printStackTrace();
				}	
				size++;		
			}	  
		    if (size==0) {
			    System.err.println("本次没有人物数据同步");
			    return;
		    }
		    long y=System.currentTimeMillis()-x;
		    StringBuffer buffer=new StringBuffer();
		    buffer.append("同步");
		    buffer.append(size);
		    buffer.append("条人物数据用时:");
		    buffer.append(y);
		    buffer.append("平均用时");
		    buffer.append(y/size);
		    System.out.println(buffer.toString());
		    if (y>200000) {
		    	System.err.println("同步数据库超时:"+y);
		    	WriteOut.addtxt("同步数据库超时",y);
			}
		  jedis=RedisPoolUntil.getJedis();
		  jedis.del(RedisParameterUtil.COPY_LOGIN);
		  jedis.del(RedisParameterUtil.COPY_PACK);
		  RedisPoolUntil.returnResource(jedis); 
	  }
}
