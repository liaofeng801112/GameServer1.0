package come.tool.FightingData;
import java.util.ArrayList;
import java.util.List;
/**
 * 战斗中人物数据
 * @author Administrator
 *
 */
public class FightingManData {

	private int id;
    //阵营                  0  1
	private int Camp;
    //位置
	private int man;	
	//类型  0属于玩家  1属于召唤兽  2属于野怪3属于灵宝 4属于宝宝 
	private int type;
	//人物附加状态  人物身上受到的增益或者减益效果 ""    |
	private String State_1;
	private List<String> States=new ArrayList<>();
	//名称
	private String manname;
	//总血量
	private int hp_Total;
	//当前血量 
	private int hp_Current;
	//总蓝量
	private int mp_Total;
	//当前蓝量
	private int mp_Current;
	//怨气值
	private int yqz;
	//怒气值
	private int nqz;
	//人物造型
	private String model="img/角色/鬼族/祭剑魂";
	//是否隐身状态 不为1.0f为隐身
	private float alpha = 1.0f;
	//转生字段
	private int zs;	
	//喊话
	private String msg;
	/**
	 * 状态添加 
	 * @return
	 */
	public boolean addstate(String type){
		if (type==null) return true;
		for (int i = 0; i < States.size(); i++) {
			if (States.get(i).equals(type)){
				return true;
			}
		}
		if (type!=null) {
			if(type.equals("隐身")){
				alpha=0.3f;
			}else if (isstate(type)) {
				States.add(type);			
			}
		}
	   return true;
	}
	/**
	 * 判断是否属于状态列表中
	 */
	public boolean isstate(String type){		
		if (type.equals("金")||type.equals("木")||type.equals("水")||type.equals("土")||type.equals("火")) {
			deletestate("清除五行");
			return true;
		}else if (type.equals("遗忘")||type.equals("封印")||type.equals("昏睡")||type.equals("混乱")) {
			if (type.equals("封印")) {
				deletestate("中毒");
			}
			deletestate("封印");
			deletestate("昏睡");
			deletestate("混乱");
			deletestate("遗忘");
			return true;
		}else if (type.equals("减人仙")||type.equals("减魔鬼")||type.equals("庇护")||type.equals("化无")||type.equals("中毒")||type.equals("力量")||type.equals("抗性")||type.equals("加速")) {
			 return true;
		}
	   return false;
	}
   /**状态刷新*/
    public void shuxingstate(String types){
    
		States.clear();
		alpha=1.0f;
		if (types!=null&&!types.equals("")) {
			String[] v=types.split("\\|");
			for (int i = 0; i < v.length; i++) {
				if (!v[i].equals("隐身")) {
					States.add(v[i]);			
				}else {
					alpha=0.3f;
				}	
			}
		}
	}
	/**
	 * 状态删除
	 * @return
	 */
	public void deletestate(String type){
		if (type!=null) {
			if (type.equals("清除状态")) {
				String[] values={"减人仙","减魔鬼","庇护","遗忘","封印","中毒","昏睡","混乱","金","木","水","火","土","力量","抗性","加速"};
				RemoveAbnormal(values);
		    }else if (type.equals("清除异常状态")) {
				String[] values={"遗忘","封印","中毒","昏睡","混乱"};
				RemoveAbnormal(values);
			}else if (type.equals("清除五行")) {
				String[] values={"金","木","水","火","土"};
				RemoveAbnormal(values);
			}else
			if (!type.equals("隐身")) {
				States.remove(type);					
			}else {
				alpha=1.0f;
			}	
		}
		
	}
	/**清除指定状态*/
	public void RemoveAbnormal(String[] values){
		for (int i =  States.size()-1; i >=0; i--) {
			String statename=States.get(i);
			for (int j = 0; j < values.length; j++) {
				if (statename.equals(values[j])) {
					States.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * 血蓝变化
	 * @return
	 */
	public void chang(int hp,int mp){
		if (hp!=0)hp_Current=xiugai(hp_Total, hp_Current, hp);
		if (mp!=0)mp_Current=xiugai(mp_Total, mp_Current, mp);
	}
	/**
	 * 数据修改
	 * 总 当 变
	 */ 
	public static int xiugai(int z,int d,int c){
		if (d+c>z)return z;
		else if (d+c<0)return 0;
		else return d+c;
	}
	/**
	 * 状态查找
	 * @return
	 */
	public boolean ztcz(String type){
		for (int i = 0; i < States.size(); i++) {
			if (States.get(i).equals(type))return true;
		}
		return false;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getState_1() {
		return State_1;
	}
	public void setState_1(String state_1) {
		State_1 = state_1;
	}
	public String getManname() {
		return manname;
	}
	public void setManname(String manname) {
		this.manname = manname;
	}
	public int getCamp() {
		return Camp;
	}
	public void setCamp(int camp) {
		Camp = camp;
	}
	public int getMan() {
		return man;
	}
	public void setMan(int man) {
		this.man = man;
	}	
	public int getHp_Total() {
		return hp_Total;
	}
	public void setHp_Total(int hp_Total) {
		this.hp_Total = hp_Total;
	}
	public int getHp_Current() {
		return hp_Current;
	}
	public void setHp_Current(int hp_Current) {
		this.hp_Current = hp_Current;
	}
	public int getMp_Total() {
		return mp_Total;
	}
	public void setMp_Total(int mp_Total) {
		this.mp_Total = mp_Total;
	}
	public int getMp_Current() {
		return mp_Current;
	}
	public void setMp_Current(int mp_Current) {
		this.mp_Current = mp_Current;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public List<String> getStates() {
		return States;
	}
	public void setStates(List<String> states) {
		States = states;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getZs() {
		return zs;
	}
	public void setZs(int zs) {
		this.zs = zs;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getYqz() {
		return yqz;
	}
	public void setYqz(int yqz) {
		this.yqz = yqz;
	}
	public int getNqz() {
		return nqz;
	}
	public void setNqz(int nqz) {
		this.nqz = nqz;
	}
	
}
