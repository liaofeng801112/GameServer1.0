package org.come.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.come.bean.PathPoint;
/*
这好像是个废除的类
 */
public class MonsterMoveBase {
	
	private int bh;//路径编号
	private List<PathPoint> points;//路径数据
	private String moveMsg;
	private int endTime;//路径所需时间
	//0-3 蟠桃园路径   5-9长安保卫战     10-15 大闹天宫路径
	public MonsterMoveBase(int bh) {
		super();
		this.bh=bh;
		if (bh>=0&&bh<=3) {
			BTY(bh);	
		}else if (bh>=5&&bh<=9) {
			BWZ(bh);
		}else if (bh>=10&&bh<=15) {
			DNTG(bh);	
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(bh);
		for (int i=0;i<points.size();i++) {
			PathPoint point=points.get(i);
			buffer.append("^");buffer.append(point.getX());
			buffer.append("^");buffer.append(point.getY());
		}
		moveMsg=buffer.toString();	
		for (int i = 1; i < points.size(); i++) {
			PathPoint pointOne=points.get(i-1);
			PathPoint pointTwo=points.get(i);
			endTime+=gettime(pointTwo.getX()-pointOne.getX(),pointTwo.getY()-pointOne.getY(),0.067);
		}
	}

	public MonsterMoveBase(int x, int y){
		this.bh=1;
		float addX = new Random().nextFloat();
		float addY = new Random().nextFloat();
		points = new ArrayList<>();
		for(int i = 20; i > 0; i--){
			points.add(new PathPoint(Math.round(x - addX * i), Math.round(y - addY * i)));
		}
		for(int i = 1; i <= 20; i++){
			points.add(new PathPoint(Math.round(x + addX * i),  Math.round(y + addY * i)));
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(bh);
		for (int i=0;i<points.size();i++) {
			PathPoint point=points.get(i);
			buffer.append("^");buffer.append(point.getX());
			buffer.append("^");buffer.append(point.getY());
		}
		moveMsg=buffer.toString();
		for (int i = 1; i < points.size(); i++) {
			PathPoint pointOne=points.get(i-1);
			PathPoint pointTwo=points.get(i);
			endTime+=gettime(pointTwo.getX()-pointOne.getX(),pointTwo.getY()-pointOne.getY(),0.067);
		}
		//System.out.println(points.get(points.size() - 1).getX() + " : " + points.get(points.size() - 1).getY());
		
	}
	/**根据速度和距离换算时间*/
	public static double gettime(long l, long m, double sp) {
		double move = Math.sqrt(l * l + m * m);
		return move / sp;
	}

	private void DNTG(int I) {
		I -= 10;
		points = new ArrayList<>();
		if (I % 3 == 0) {
			points.add(new PathPoint(2800, 940));
			points.add(new PathPoint(4010, 350));
			points.add(new PathPoint(4790, 410));
			points.add(new PathPoint(5970, 230));
			points.add(new PathPoint(6990, 310));
			points.add(new PathPoint(7850, 510));
			points.add(new PathPoint(8310, 970));
			points.add(new PathPoint(8310, 1270));
			points.add(new PathPoint(8590, 1570));
			points.add(new PathPoint(8490, 1670));
			points.add(new PathPoint(8590, 1950));
			points.add(new PathPoint(8430, 2130));
			points.add(new PathPoint(8690, 2730));
		} else if (I % 3 == 1) {
			points.add(new PathPoint(2100, 1760));
			points.add(new PathPoint(3010, 1730));
			points.add(new PathPoint(3510, 1750));
			points.add(new PathPoint(3910, 2150));
			points.add(new PathPoint(3910, 2170));
			points.add(new PathPoint(4870, 3130));
			points.add(new PathPoint(5530, 3170));
			points.add(new PathPoint(6290, 2930));
			points.add(new PathPoint(6690, 3050));
		} else if (I % 3 == 2) {
			points.add(new PathPoint(720, 1720));
			points.add(new PathPoint(810, 2010));
			points.add(new PathPoint(510, 2330));
			points.add(new PathPoint(530, 2590));
			points.add(new PathPoint(290, 2890));
			points.add(new PathPoint(210, 3610));
			points.add(new PathPoint(250, 3670));
			points.add(new PathPoint(390, 3810));
			points.add(new PathPoint(810, 4010));
			points.add(new PathPoint(2070, 3670));
			points.add(new PathPoint(2210, 3570));
			points.add(new PathPoint(4210, 3970));
			points.add(new PathPoint(4670, 3910));
			points.add(new PathPoint(6110, 3670));
			points.add(new PathPoint(6150, 3690));
			points.add(new PathPoint(6470, 3670));
		}
		if (I >= 3) {
			List<PathPoint> list2 = new ArrayList<>();
			for (int j = points.size() - 1; j >= 0; j--) {
				list2.add(points.get(j));
			}
			points = list2;
		}
	}

	public void BWZ(int I) {
		//6130-1630点一 5970-1530点二 5830-1610点三  5990-1770点四  5970-1990点五
		I-=5;
		points=new ArrayList<>();
		if (I==0) {
			points.add(new PathPoint(6130, 1630));
		}else if (I==1) {
			points.add(new PathPoint(5970, 1530));
		}else if (I==2) {
			points.add(new PathPoint(5830, 1610));
		}else if (I==3) {
			points.add(new PathPoint(5990, 1770));
		}else if (I==4) {
			points.add(new PathPoint(5970, 1990));
		}
		if (I!=4) {points.add(new PathPoint(5970, 1990));}
		points.add(new PathPoint(5990, 2710));
		points.add(new PathPoint(5750, 3430));
		points.add(new PathPoint(5570, 3730));
		points.add(new PathPoint(5550, 3750));
		points.add(new PathPoint(5430, 3850));
		points.add(new PathPoint(4810, 3910));
		points.add(new PathPoint(4470, 3870));
		points.add(new PathPoint(4150, 3670));
		points.add(new PathPoint(2930, 2550));
		points.add(new PathPoint(2790, 2550));
		points.add(new PathPoint(490, 690));
	}
	private void BTY(int I) {
		if (I == 0) {
			points = new ArrayList<>();
			points.add(new PathPoint(1582, 2192));
			points.add(new PathPoint(730, 2070));
			points.add(new PathPoint(690, 2050));
			points.add(new PathPoint(610, 1970));
			points.add(new PathPoint(390, 1510));
			points.add(new PathPoint(390, 1470));

			points.add(new PathPoint(740, 1080));
			points.add(new PathPoint(1420, 1080));
			points.add(new PathPoint(1260, 820));
			points.add(new PathPoint(1600, 520));
			points.add(new PathPoint(1830, 590));
		} else if (I == 1) {
			points = new ArrayList<>();
			points.add(new PathPoint(1676, 2592));
			points.add(new PathPoint(950, 2250));
			points.add(new PathPoint(690, 2050));
			points.add(new PathPoint(610, 1970));
			points.add(new PathPoint(390, 1510));
			points.add(new PathPoint(390, 1470));
			points.add(new PathPoint(390, 1470));
			points.add(new PathPoint(740, 1080));
			points.add(new PathPoint(1420, 1080));
			points.add(new PathPoint(1260, 820));
			points.add(new PathPoint(1600, 520));
			points.add(new PathPoint(1830, 590));
		} else if (I == 2) {
			points = new ArrayList<>();
			points.add(new PathPoint(1116, 2682));
			points.add(new PathPoint(1270, 2630));
			points.add(new PathPoint(1330, 2590));
			points.add(new PathPoint(1390, 2530));
			points.add(new PathPoint(1030, 2270));
			points.add(new PathPoint(950, 2250));
			points.add(new PathPoint(690, 2050));
			points.add(new PathPoint(610, 1970));
			points.add(new PathPoint(390, 1510));
			points.add(new PathPoint(390, 1470));

			points.add(new PathPoint(740, 1080));
			points.add(new PathPoint(1420, 1080));
			points.add(new PathPoint(680, 760));
			points.add(new PathPoint(1260, 820));
			points.add(new PathPoint(1600, 520));
			points.add(new PathPoint(1830, 590));
		} else if (I == 3) {
			points = new ArrayList<>();
			points.add(new PathPoint(1694, 1733));
			points.add(new PathPoint(1510, 1690));
			points.add(new PathPoint(1270, 1570));
			points.add(new PathPoint(1250, 1550));
			points.add(new PathPoint(1170, 1410));
			points.add(new PathPoint(1160, 1200));
			points.add(new PathPoint(600, 800));
			points.add(new PathPoint(1260, 820));
			points.add(new PathPoint(1600, 520));
			points.add(new PathPoint(1830, 590));
		}
	}
	
	public int getBh() {
		return bh;
	}
	public void setBh(int bh) {
		this.bh = bh;
	}
	public List<PathPoint> getPoints() {
		return points;
	}
	public void setPoints(List<PathPoint> points) {
		this.points = points;
	}
	public String getMoveMsg() {
		return moveMsg;
	}
	public void setMoveMsg(String moveMsg) {
		this.moveMsg = moveMsg;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
