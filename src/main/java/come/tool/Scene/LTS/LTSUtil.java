package come.tool.Scene.LTS;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.come.bean.LoginResult;
import org.come.bean.PathPoint;
import org.come.server.GameServer;
import org.come.tool.ReadExelTool;
import org.come.until.AllServiceUtil;
import org.come.until.CreateTextUtil;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Title.TitleUtil;

public class LTSUtil {
	
	private static LTSUtil ltsUtil;
	public static LTSUtil getLtsUtil() {
		if (ltsUtil==null) {ltsUtil=new LTSUtil();ltsUtil.init();}
		return ltsUtil;
	}
	private LTSBean ltsBean;
	/**获取积分*/
	public PathPoint getJF(BigDecimal roleId){
		return ltsBean.getLtsMap().get(roleId);
	}
	/**添加积分*/
	public void addJF(BigDecimal roleId,int jf){
//		x:表示总积分 y:表示可用积分
		PathPoint point=ltsBean.getLtsMap().get(roleId);
		if (point==null) {
			point=new PathPoint(0, 0);
			ltsBean.getLtsMap().put(roleId,point);
		}
		point.add(jf);
	}
	/**初始化*/
	public void init(){
		String lts = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "lts.txt");
		if (lts==null||lts.equals("")) {
			ltsBean=new LTSBean();
		}else {
			ltsBean=GsonUtil.getGsonUtil().getgson().fromJson(lts, LTSBean.class);
		}
		bangList(null);
		TitleUtil.addTitle(TitleUtil.LTS,ltsBean.getIds());
	}
	/**保存积分记录*/
	public void BCLts(){
		try {
			String msg=GsonUtil.getGsonUtil().getgson().toJson(ltsBean);
			CreateTextUtil.createFile(ReadExelTool.class.getResource("/").getPath() + "lts.txt",msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**重置积分  true表示不重置积分*/
	public void resetLts(List<LoginResult> list){
		ltsBean.setCi(ltsBean.getCi()+1);
		if (ltsBean.getCi()>=2) {//重置全部积分
			ltsBean.resetL(1);
			BigDecimal[] ids=new BigDecimal[list.size()<5?list.size():5];
			for (int i = 0; i < ids.length; i++) {
				ids[i]=list.get(i).getRole_id();
			}
			TitleUtil.addTitle(TitleUtil.LTS,ids);
		}else {//重置可用积分
			ltsBean.resetL(0);
		}
		BCLts();
	}
	/**刷新排行榜*/
	public void bangList(String week){	
		List<Map.Entry<BigDecimal, PathPoint>> infoIds = new ArrayList<Map.Entry<BigDecimal, PathPoint>>(ltsBean.getLtsMap().entrySet());
		// 排序
		Collections.sort(infoIds, new Comparator<Map.Entry<BigDecimal, PathPoint>>() {
				@Override
				public int compare(Entry<BigDecimal, PathPoint> o1,Entry<BigDecimal, PathPoint> o2) {
					// TODO Auto-generated method stub
					return (o2.getValue().getX()-o1.getValue().getX());
				}
		});
		List<LoginResult> list=new ArrayList<>();
		for (int i = 0;i<50&&i<infoIds.size(); i++) {
		     Entry<BigDecimal, PathPoint> ent=infoIds.get(i); 
		     LoginResult login=new LoginResult();
		     LoginResult changRole=null;
			 RoleData roleData=RolePool.getRoleData(ent.getKey());
			 if (roleData!=null) {
				 ChannelHandlerContext ctx2=GameServer.getRoleNameMap().get(roleData.getLoginResult().getRolename());
				 if (ctx2==null) {continue;}
				 changRole = GameServer.getAllLoginRole().get(ctx2);
			 }else{
				 changRole =AllServiceUtil.getRoleTableService().selectRoleID(ent.getKey());				
			 } 
			 login.setRole_id(changRole.getRole_id());
			 login.setRolename(changRole.getRolename());
			 login.setGrade(changRole.getGrade());
			 login.setBangScore(ent.getValue().getX());
			 list.add(login);
		}
//		30-500  8-100 1-10    150以下    --180   飞升
//		4  3  2      1分
		if(GameServer.allBangList != null){
			GameServer.allBangList.put(5,list);
		}
		if (week!=null&&week.equals("Monday")) {resetLts(list);}
	}
}
