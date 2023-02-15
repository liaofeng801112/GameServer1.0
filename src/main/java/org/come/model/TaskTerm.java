package org.come.model;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.Coordinates;
import org.come.bean.PathPoint;
import org.come.entity.Goodstable;
import org.come.server.GameServer;
import org.come.tool.SplitStringTool;

import come.tool.newTask.GeneratNames;
import come.tool.newTask.RandomDaily;

/**完成条件*/
public class TaskTerm {
//	目的类型0系统野怪 1自定义野怪 2击杀NPC 3问候NPC 4给与物品 5护送任务
//	0=Did集合=N指定名称=M	指定位置map-x-y=S指定数量没有默认1
//	1=Did集合=N指定名称=M	指定位置=S指定数量没有默认1
//	2=Did集合=N指定名称=M	指定位置=S指定数量没有默认1
//	3=Did集合=N指定名称=M	指定位置=S指定数量没有默认1
//	4=Did集合=S指定数量没有默认1=G接受者ID
//	5=Did集合=G接受者ID
	private int type;//完成类型
	private List<String> dList;//完成id
	private int sum;//需要完成数量
	private String Name;//指定名称
	private Coordinates ZB;//指定坐标
	private List<String> gList;//接受id
	public TaskTerm(String v) {
		// TODO Auto-generated constructor stub
		String[] vs=v.split("=");
		this.type=Integer.parseInt(vs[0]);
		this.sum=1;
		for (int i = 1; i < vs.length; i++) {
			if (vs[i].startsWith("N")) {//指定名称
				this.Name=vs[i].substring(1);
			}else if (vs[i].startsWith("S")) {//指定数量
				this.sum=Integer.parseInt(vs[i].substring(1));
			}else if (vs[i].startsWith("M")) {//指定位置
				String[] zbs=vs[i].substring(1).split("-");
				this.ZB=new Coordinates(Integer.parseInt(zbs[0]),Integer.parseInt(zbs[1]),Integer.parseInt(zbs[2]));
			}else if (vs[i].startsWith("D")) {
				this.dList=SplitStringTool.splitString(vs[i].substring(1));
			}else if (vs[i].startsWith("G")) {
				this.gList=SplitStringTool.splitString(vs[i].substring(1));
			}
		}
	}
	/**生成任务进度数据*/
	public TaskProgress create(TaskData taskData,int max){
		TaskProgress taskProgress=new TaskProgress();
		taskProgress.setType(type);
		taskProgress.setMax(sum);
		if (ZB!=null) {
			taskProgress.setMap(ZB.getMapID());
			taskProgress.setX(ZB.getX());
			taskProgress.setY(ZB.getY());
		}else if (type==1) {//随机生成刷怪地点
			Sghostpoint sghostpoint = GameServer.getSghostpoint(taskData.getTaskName());
			if (sghostpoint!=null) {
				PathPoint point = sghostpoint.getPoints()[GameServer.random.nextInt(sghostpoint.getPoints().length)];	
				taskProgress.setMap(sghostpoint.getPointkey());
				taskProgress.setX(point.getX());
				taskProgress.setY(point.getY());
			}
		}
		if (dList!=null) {
			int dId=getDId();
			taskProgress.setDId(dId);
			if (Name!=null) {
				taskProgress.setDName(Name);
			}else if (type==0||type==1||type==5) {
				if (taskData.getTaskID() == 3046 || taskData.getTaskID() == 3047) {
					int sum = (max - 100) / 20;
					if (sum < 0) {
						sum = 0;
					}else if (sum>=dList.size()) {
						sum=dList.size()-1;
					}
					dId=Integer.parseInt(dList.get(sum));
					taskProgress.setDId(dId);
				}
				Robots robots=GameServer.getAllRobot().get(dId+"");
				if (robots!=null) {
					if (robots.getRobotname()==null||robots.getRobotname().equals("")) {
						if (taskData.getTaskID()==1006) {
							taskProgress.setDName("贼王"+GeneratNames.returnName());
						}else {
							taskProgress.setDName(RandomDaily.RandomName1(taskData.getTaskName()));
						}
					}else {
						taskProgress.setDName(robots.getRobotname());
					}
				}
			}else if (type==2||type==3) {
				Npctable npctable=GameServer.getNpc(dId+"");
				if (npctable!=null) {
					taskProgress.setDName(npctable.getNpcname());
					if (ZB==null) {
						taskProgress.setMap(npctable.getMap());
						taskProgress.setX(Integer.parseInt(npctable.getTx()));
						taskProgress.setY(Integer.parseInt(npctable.getTy()));
					}
				}
			}else if (type==4) {
				Goodstable good=GameServer.getAllGoodsMap().get(new BigDecimal(dId));
				if (good!=null) {
					taskProgress.setDName(good.getGoodsname());
				}
			}
		}
		if (gList!=null) {
			int gId=getGId();
			taskProgress.setGId(gId);
			if (type==4||type==5) {
				Npctable npctable=GameServer.getNpc(gId+"");
				if (npctable!=null) {
					taskProgress.setGName(npctable.getNpcname());
					if (ZB==null) {
						taskProgress.setMap(npctable.getMap());
						taskProgress.setX(Integer.parseInt(npctable.getTx()));
						taskProgress.setY(Integer.parseInt(npctable.getTy()));
					}
				}
			}
		}
		return taskProgress;
	}
	/***/
	public int getDId(){
		return Integer.parseInt(dList.get(GameServer.random.nextInt(dList.size())));
	}
	public int getGId(){
		return Integer.parseInt(gList.get(GameServer.random.nextInt(gList.size())));
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getdList() {
		return dList;
	}
	public void setdList(List<String> dList) {
		this.dList = dList;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Coordinates getZB() {
		return ZB;
	}
	public void setZB(Coordinates zB) {
		ZB = zB;
	}
	public List<String> getgList() {
		return gList;
	}
	public void setgList(List<String> gList) {
		this.gList = gList;
	}
}
