package org.come.action.suit;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.QualityClBean;

/**额外属性*/
public class QualityCPool {
    private static QualityCPool cPool;

	public static QualityCPool getcPool() {
		if (cPool==null) {
			cPool=new QualityCPool();
		}
		return cPool;
	}
	public QualityCPool() {
		// TODO Auto-generated constructor stub
		lhMap=new ConcurrentHashMap<>();
		lqMap=new ConcurrentHashMap<>();
		tzMap=new ConcurrentHashMap<>();
		jxMap=new ConcurrentHashMap<>();
		wxMap=new ConcurrentHashMap<>();
		arenaMap=new ConcurrentHashMap<>();
	}
	//类型   1:炼化  2:炼器  3:神兵  4:套装洗炼  -4:直接替换新属性
	ConcurrentHashMap<BigDecimal,QualityClBean> lhMap;//炼化
	ConcurrentHashMap<BigDecimal,QualityClBean> lqMap;//炼器
	ConcurrentHashMap<BigDecimal,QualityClBean> tzMap;//套装洗练
	ConcurrentHashMap<BigDecimal,QualityClBean> jxMap;//觉醒技能
	ConcurrentHashMap<BigDecimal,QualityClBean> wxMap;//五行技能
	ConcurrentHashMap<BigDecimal,QualityClBean> arenaMap;//五行技能
	
	//预添加 额外属性
	public void addExtra(QualityClBean clBean){
		if (clBean.getType()==1||clBean.getType()==53) {
			lhMap.put(clBean.getRgid(), clBean);
		}else if (clBean.getType()==2) {
			lqMap.put(clBean.getRgid(), clBean);
		}else if (clBean.getType()==4) {
			tzMap.put(clBean.getRgid(), clBean);
		}else if (clBean.getType()==44||clBean.getType()==46) {
			jxMap.put(clBean.getRgid(), clBean);
		}else if (clBean.getType()==54) {
			wxMap.put(clBean.getRgid(), clBean);
		}else if (clBean.getType()>=70&&clBean.getType()<=79) {
			arenaMap.put(clBean.getRgid(), clBean);
		}
	}
	/**获取*/
	public QualityClBean getExtra(int type,BigDecimal rgid){
		QualityClBean clBean=null;
		if (type==1||type==53) {
			clBean=lhMap.remove(rgid);
			if (clBean!=null) {clBean.setType(1);}
		}else if (type==2) {
			clBean=lqMap.remove(rgid);
		}else if (type==4) {
			clBean=tzMap.remove(rgid);
		}else if (type==44||type==46) {
			clBean=jxMap.remove(rgid);
			if (clBean!=null) {clBean.setType(6);}
		}else if (type==54) {
			clBean=wxMap.remove(rgid);
			if (clBean!=null) {clBean.setType(7);}
		}else if (type>=70&&type<=79) {
			clBean=arenaMap.remove(rgid);
		}
		return clBean;
	}
}
