package come.tool.BangBattle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.come.until.GsonUtil;
//记录已经报名的帮派
public class BangBattlePool {
	private static BangBattlePool BangBattlePool;
    public static BangBattlePool getBangBattlePool() {
    	if (BangBattlePool==null)BangBattlePool=new BangBattlePool();
		if (BangBattlePool.week!=BangFileSystem.getBangFileSystem().getweek())
			BangBattlePool.init();
    	return BangBattlePool;
	}

    /**
     * 初始化
     */
    public void init(){
    	BangFileSystem bangFileSystem=BangFileSystem.getBangFileSystem();
    	week=bangFileSystem.getweek();
    	String text=bangFileSystem.DataReading(new BigDecimal(week));
        if (text.equals("")){
        	group=new Group(null, 0);	
        }else {
        	group=GsonUtil.getGsonUtil().getgson().fromJson(text, Group.class);
            group.init(0);	
		}
        //分组 
    	bangThread=new BangThread(this);
    	Thread t=new Thread(bangThread);
		t.start();
//		FightOpenClose();
    }
    public long week;
    //报名列表
    public Group group;
    public List<BangFight> BangFights=new ArrayList<>();
    private BangThread bangThread;
    //根据帮派获取沙盘
    public BangFight getBangFight(BigDecimal bang_id){
    	if (bang_id==null)return null;
		for (int i = BangFights.size()-1; i >=0; i--) {
			if (BangFights.get(i).getMap(bang_id)!=null)
				return BangFights.get(i);
		}
    	return null;
    }
   
	//判断现在是否在帮战报名时间内
	public boolean isEnroll(){
		Date today = new Date();
	    Calendar c=Calendar.getInstance();
	    c.setTime(today);
	    int weekday=c.get(Calendar.DAY_OF_WEEK);
		if (weekday==2) {//周1
			int hour=c.get(Calendar.HOUR_OF_DAY);
			if (hour>=8)return true;
		}else if (weekday==6) {//周五
			int hour=c.get(Calendar.HOUR_OF_DAY);
			if (hour<19)return true;
		}else if (weekday==3||weekday==4||weekday==5) {
			return true;
		}
	    return false;
	}
	//获取帮派
	public BangPoints getBangPoints(BigDecimal id){
		if (id==null)return null;
		for (int i = group.getlist().size()-1; i >=0; i--) {
			if (group.getlist().get(i).getId().compareTo(id)==0){
				return group.getlist().get(i);	
			}
		}
		return null;		
	}
	//配对
	public void Match(Group group,int sum){
		if (group==null)return;
		Group victory=group.victory;
		Group fail=group.fail;
		if (victory==null&&fail==null) {
			if (group.getlist().size()>1) {
				for (int i = (group.getlist().size()-1)/2; i >=0; i--) {
					BangPoints Left = null;
					if ((i*2)<group.getlist().size()){
						Left=group.getlist().get(i*2);
					}
					BangPoints Right = null;
					if ((i*2+1)<group.getlist().size()){
						Right=group.getlist().get(i*2+1);
					}
					BangFights.add(new BangFight(sum,Left,Right));
				}	
			}						
		}else {
			Match(victory,sum+1);
			Match(fail,sum+1);	
		}
	}
	public class Group {
		 private List<BangPoints> list;
		 public transient Group victory;
		 public transient Group fail;
		 public Group(List<BangPoints> list,int sum) {
			// TODO Auto-generated constructor stub
			 this.list=list;
			 init(sum);
		 }
	     public List<BangPoints> getlist(){
			 if (list==null)list=new ArrayList<>();
	    	 return list; 
	     }
	     public void addlist(BangPoints bangPoints){
	    	 if (bangPoints==null)return;
	    	 getlist().add(bangPoints);
	     }
	     public void init(int sum){
	    	List<BangPoints> vs=new ArrayList<>();
	    	List<BangPoints> fs=new ArrayList<>();
	    	for (int i = getlist().size()-1; i >=0; i--) {
	    		BangPoints bang=getlist().get(i);
	    		if (bang.getRecord()==(bang.getRecord()|(1<<sum)))vs.add(bang);
	    		else fs.add(bang);
			}
	    	if (fs.size()==getlist().size())return;
	    	victory = new Group(vs,sum+1);
	    	fail    = new Group(fs,sum+1);	
	     }
	     //战斗失败胜利处理
	     public void result(BangPoints bang,int sum){
	    	Group group =this; 
	    	for (int i = 0; i <sum; i++) {
	    		if (bang.getRecord()==(bang.getRecord()|(1<<i))){
					if (group.victory==null){
						group.victory=new Group(null, i+1);				
					}
					group=group.victory;
				}else {
					if (group.fail==null){
						group.fail=new Group(null, i+1);				
					}
					group=group.fail;
				}
			}
	    	group.addlist(bang);
	     }
	}
	//帮战开启
	public void FightOpenClose(){
		BangFights.clear();
		Match(group,0);
		for (int i = 0; i < BangFights.size(); i++) {
			BangFights.get(i).BangState=1;
		}
//		bangThread.notify();
	}
	//胜负处理
	public void WinOrLose(BangFight bangFight){
		try {
//			   if (bangFight.Camp_Left_ID.getId().compareTo(new BigDecimal(-1))!=0) {
//			    	group.result(bangFight.Camp_Left_ID, bangFight.sum);	
//				}
//			    if (bangFight.Camp_Right_ID.getId().compareTo(new BigDecimal(-1))!=0) {
//			    	group.result(bangFight.Camp_Right_ID, bangFight.sum);   	
//				}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 到了下一周
	 */
	public void NewWeek(){
		BangFileSystem bangFileSystem=BangFileSystem.getBangFileSystem();
		bangFileSystem.DataSaving(this);
		week++;
		BangFights.clear();
		String text=bangFileSystem.DataReading(new BigDecimal(week));
        if (text.equals("")){
        	group=new Group(null, 0);	
        }else {
        	group=GsonUtil.getGsonUtil().getgson().fromJson(text, Group.class);
            group.init(0);	
		}
	}
	
}