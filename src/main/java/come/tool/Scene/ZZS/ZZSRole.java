package come.tool.Scene.ZZS;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;

public class ZZSRole {

	private BigDecimal Id;
	private String role;
	//状态   0正常   1匹配  3淘汰赛资格   4被淘汰 
	private int I;
	//积分
	private int jf;
	//记录参赛场次
	private int CYnum;
	//记录获胜场次
	private int HSnum;
	//记录连胜场次
	private int LSnum;
	//淘汰赛战败场次
	private int ZBnum;
	private List<ZZSAward> awards;
	private long time;
	public ZZSRole(BigDecimal id, String role) {
		super();
		Id = id;
		this.role = role;
	}
	/**战绩刷新 积分变更返回true*/
	public boolean Battle(boolean is,int type){
		CYnum++;
		if (is) {
			int add=0;
			if (type==31) {
				add=5+LSnum;
			}else {
				add=10;
			}
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role);
			if (ctx!=null) {
				SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().PromptAgreement("你获得"+add+"点积分"));	
			}
			jf+=add;
			HSnum++;
			LSnum++;
			addAward();
			return true;
		}else {
			LSnum=0;
			if (type==32) {ZBnum++;}
			addAward();
			return false;
		}
	}
	/**判断是否添加奖励  5参与奖励 5胜奖励  3连胜奖励*/
	public void addAward(){
		if (CYnum>=5) {addAward(1);}
		if (HSnum>=5) {addAward(2);}
		if (LSnum>=3) {addAward(3);}
	}
	/**添加奖励*/
	public void addAward(int type){
		if (awards==null) {
			awards=new ArrayList<>();
		}
		for (int i = awards.size()-1; i >=0; i--) {
			if (awards.get(i).getType()==type) {
				return;
			}
		}
		String msg=null;
		if (type==1) {
			msg=Agreement.getAgreement().PromptAgreement("获得5场参与奖励,请找NPC领取");
		}else if (type==2) {
			msg=Agreement.getAgreement().PromptAgreement("获得5场胜利奖励,请找NPC领取");
		}else if (type==3) {
			msg=Agreement.getAgreement().PromptAgreement("获得3场连胜奖励,请找NPC领取");
		}else if (type==4) {
			msg=Agreement.getAgreement().PromptAgreement("获得十强奖励,请找NPC领取");
		}else if (type==5) {
			msg=Agreement.getAgreement().PromptAgreement("获得第一名奖励,请找NPC领取");
		}
		if (msg!=null) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(role);
			if (ctx!=null) {
				SendMessage.sendMessageToSlef(ctx,msg);			
			}		
		}
		awards.add(new ZZSAward(type));
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
	public int getI() {
		return I;
	}
	public void setI(int i) {
		I = i;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}
	public int getCYnum() {
		return CYnum;
	}
	public void setCYnum(int cYnum) {
		CYnum = cYnum;
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
	public int getZBnum() {
		return ZBnum;
	}
	public void setZBnum(int zBnum) {
		ZBnum = zBnum;
	}
	public List<ZZSAward> getAwards() {
		return awards;
	}
	public void setAwards(List<ZZSAward> awards) {
		this.awards = awards;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
