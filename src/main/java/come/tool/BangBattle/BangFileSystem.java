package come.tool.BangBattle;

import java.math.BigDecimal;

import org.come.entity.GangBattle;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class BangFileSystem {

	//一周的时间
	private static long WEEK=7*24*60*60*1000;
	//时间戳起始时间差  时间1970年01月01日08时00分00秒 周4
	private static long CHA=1000*60*60*8+3*24*60*60*1000;
	private static BangFileSystem bangFileSystem;
	public static BangFileSystem getBangFileSystem() {
        if (bangFileSystem==null)bangFileSystem=new BangFileSystem();
		return bangFileSystem;
	}
	
	public BangFileSystem() {
		// TODO Auto-generated constructor stub
	}
	
	//数据保存
	public void DataSaving(BangBattlePool pool){
		GangBattle gangBattle=AllServiceUtil.getGangBattleService().selectGangBattle(new BigDecimal(pool.week));
		if (gangBattle==null) {
			gangBattle=new GangBattle();
			gangBattle.setWeek(new BigDecimal(pool.week));
			gangBattle.setValue(GsonUtil.getGsonUtil().getgson().toJson(pool.group));
			AllServiceUtil.getGangBattleService().addGangBattle(gangBattle);
		}else {
			gangBattle.setValue(GsonUtil.getGsonUtil().getgson().toJson(pool.group));
			AllServiceUtil.getGangBattleService().updataGangBattle(gangBattle);
		}
	}
	//数据读取
	public String DataReading(BigDecimal week){
		GangBattle gangBattle=AllServiceUtil.getGangBattleService().selectGangBattle(week);
		if (gangBattle==null){
			return "";	
		}
        if (gangBattle.getValue()==null) {
        	return "";
		} 		
		return gangBattle.getValue();		
	}
	//获取周数
	public long getweek(){
		return (System.currentTimeMillis()+CHA)/WEEK;
	}

}
