package come.tool.newGang;

import java.util.ArrayList;
import java.util.List;

import org.come.bean.NPCDialog;
import org.come.entity.Gang;
import org.come.protocol.Agreement;
import org.come.until.GsonUtil;

public class GangGroup {

	private transient Gang gang;
	private int xy;//驯养等级
	private transient int xyNum;//驯养次数
	private int kj;//科技等级
	
	public GangGroup(Gang gang) {
		// TODO Auto-generated constructor stub
		this.gang=gang;
		String type=gang.getGangTxt();
		if (type==null||type.equals("")) {
			return;
		}
		String[] v=type.split("\\|");
		long time=System.currentTimeMillis()-Long.parseLong(v[0]);
		time/=60*60*1000;
		kj=Integer.parseInt(v[1]);
		String[] xys=v[2].split("=");
		xy=Integer.parseInt(xys[0]);
		xyNum=Integer.parseInt(xys[1]);
		addXY((int)time);
	}
	/**获取增加的驯养次数*/
	public boolean addXY(int size){
		if (xy==0) {return false;}
		int max=(3+xy*2)*6;
		if (xyNum>max) {return false;}
		xyNum+=(3+xy*2)*size;
		if (xyNum>max) {xyNum=max;}
		return true;
	}
//	0|1|1=0
	public String getTxt(){
		if (xy==0&&kj==0) {return null;}
		StringBuffer buffer=new StringBuffer();
		buffer.append(System.currentTimeMillis());
		buffer.append("|");
		buffer.append(kj);
		buffer.append("|");
		buffer.append(xy);
		buffer.append("=");
		buffer.append(xyNum);
		return buffer.toString();
	}
	static List<String> f1;
	static List<String> f2;
	static{
		f1=new ArrayList<>();f1.add("升级驯养师等级");f1.add("驯养参战召唤兽亲密");f1.add("驯养坐骑经验");f1.add("驯养坐骑技能熟练度");
		f2=new ArrayList<>();f2.add("升级科技等级");f2.add("领取经验加成");f2.add("领取强法加成");f2.add("领取抗性加成");
	}
	/**获取驯养师对话框*/
	public String getXYNpc(){
		NPCDialog npcDialog=new NPCDialog();
		StringBuffer buffer=new StringBuffer();
		buffer.append("当前驯养师等级");
		buffer.append(xy);
		if (xy==0) {
			buffer.append("。无法提供驯养服务");
		}else {
			buffer.append("。驯养次数剩余");
			buffer.append(xyNum);
			buffer.append("(上限:");
			buffer.append((3+xy*2)*6);
			buffer.append(")。每小时恢复");
			buffer.append((3+xy*2));
			buffer.append("次。驯养一次消耗150帮贡,每日最多驯养10次");
		}
		npcDialog.setMsg(buffer.toString());
		npcDialog.setFunctions(f1);
		return Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog));
	}
	/**获取科技对话框*/
	public String getKJNpc(){
		NPCDialog npcDialog=new NPCDialog();
		StringBuffer buffer=new StringBuffer();
		buffer.append("当前科技等级");
		buffer.append(kj);
		buffer.append("。领取消耗100帮贡,每日最多领取二次。");
		npcDialog.setMsg(buffer.toString());
		npcDialog.setFunctions(f2);
		return Agreement.getAgreement().npcAgreement(GsonUtil.getGsonUtil().getgson().toJson(npcDialog));
	}
	public int getXy() {
		return xy;
	}
	public void setXy(int xy) {
		this.xy = xy;
	}
	public int getKj() {
		return kj;
	}
	public void setKj(int kj) {
		this.kj = kj;
	}
	public int getXyNum() {
		return xyNum;
	}
	public void setXyNum(int xyNum) {
		this.xyNum = xyNum;
	}
}
