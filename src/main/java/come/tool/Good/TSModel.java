package come.tool.Good;

import java.util.ArrayList;
import java.util.List;

import org.come.bean.XXGDBean;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;
import org.come.task.MapZB;
import org.come.task.MonsterExp;
import org.come.task.MonsterMatch;

/**特殊怪设置*/
public class TSModel {
	//死亡放妖//战斗匹配//经验累加
	private List<DropType> types;
	private MonsterMatch match;
	private MonsterExp exp;
	public TSModel(String msg) {
		// TODO Auto-generated constructor stub
		String[] v=msg.split("\\|");
		for (int i = 0; i < v.length; i++) {
			String[] vs = v[i].split("=");
			DropType dropType=getDropType(vs);
			if (dropType!=null) {
				if (types==null) {types=new ArrayList<>();}
				types.add(dropType);
			}
		}
	}
	public DropType getDropType(String[] vs){
    	if (vs[0].equals("死亡放妖")) {
			return new DropType(DropType.DEATHBOOS,vs[1]);
		}else if (vs[0].equals("战斗匹配")) {
			match=new MonsterMatch(Integer.parseInt(vs[1]));
		}else if (vs[0].equals("经验累加")) {
			exp  =new MonsterExp(Integer.parseInt(vs[1]),Long.parseLong(vs[2]));
		}
    	return null;
	}
	public void die(MapMonsterBean monsterBean,String[] teams){
		DropType dropType=getDropType(DropType.DEATHBOOS);
		if (dropType==null) {return;}
		XXGDBean bean=DropUtil.getGoods(dropType.getDropGood());
		if (bean!=null) {
			StringBuffer buffer=new StringBuffer();
			if (teams!=null&&teams.length!=0) {
				buffer.append("玩家");
				buffer.append(teams[0]);	
			}else {
				buffer.append("有人");
			}
			buffer.append("在");
			MapZB mapZB=new MapZB(GameServer.getMapName(monsterBean.getMap()+""),monsterBean.getX(), monsterBean.getY());
			buffer.append(mapZB.getMap());
			buffer.append("(");
			buffer.append(mapZB.getX()/20);
			buffer.append(",");
			buffer.append(mapZB.getY()/20);
			buffer.append(")击杀");
			buffer.append(monsterBean.getRobotname());
			DropUtil.AddMonster(null,buffer.toString(),bean.getId(),mapZB);
		}
	}
	/**getType*/
	public DropType getDropType(int type){
		if (types==null) {return null;}
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i).getDropType()==type) {
				return types.get(i);
			}
		}
		return null;
	}
	public MonsterMatch getMatch() {
		if (match!=null) {return new MonsterMatch(match.getCountDown());}
		return match;
	}
	public MonsterExp getExp() {
		if (exp!=null) {return new MonsterExp(exp.getMaxSize(), exp.getExp());}
		return exp;
	}
}
