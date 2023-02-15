package come.tool.Scene.LTS;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

public class LTSRole {

	private BigDecimal Id;
	private String role;
	//积分
	private int jf;
	//记录参赛场次
//	private int CYnum;
	//记录获胜场次
	private int HSnum;
	//记录连胜场次
	private int LSnum;
	//淘汰赛战败场次
	private int ZBnum;
	//不战而胜场次
	private int BZnum;
	private long time;
	//判断是否领奖
	private boolean isAward;
	public LTSRole(BigDecimal id, String role) {
		super();
		Id = id;
		this.role = role;
		this.isAward=false;
	}
	/**-1是积分刷新    其他是连胜终结分数  战绩刷新 积分变更返回true  is胜负  is2 擂台 打擂*/
	public void battle(boolean is,boolean is2,int type,int ew){
		if (is) {
			time=System.currentTimeMillis()+10000;
			int add=type==3?4:type==2?3:2;
			if (LSnum>=10) {add+=2;}
			else if (LSnum>=3) {add+=1;}
			add+=ew;
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role);
			if (ctx!=null) {
			    SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你获得"+add+"点积分"));	
		    }
			jf+=add;
			LTSUtil.getLtsUtil().addJF(Id,add);
			HSnum++;
			LSnum++;
		}else {
			time=System.currentTimeMillis()+180000;
			LSnum=0;
			ZBnum+=is2?2:1;
			jf+=1;
			LTSUtil.getLtsUtil().addJF(Id,1);
		}	
	}
	public BigDecimal getId() {
		return Id;
	}
	public void setId(BigDecimal id) {
		Id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}
	public int getZBnum() {
		return ZBnum;
	}
	public void setZBnum(int zBnum) {
		ZBnum = zBnum;
	}
	public int getHSnum() {
		return HSnum;
	}
	public void setHSnum(int hSnum) {
		HSnum = hSnum;
	}
	public int getLSnum() {
		return LSnum;
	}
	public void setLSnum(int lSnum) {
		LSnum = lSnum;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getBZnum() {
		return BZnum;
	}
	public void setBZnum(int bZnum) {
		BZnum = bZnum;
	}
	public boolean isAward() {
		return isAward;
	}
	public void setAward(boolean isAward) {
		this.isAward = isAward;
	}
}
