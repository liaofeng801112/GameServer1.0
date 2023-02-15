package come.tool.Stall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.bean.UseCardBean;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;
import org.come.tool.JmSum;

import come.tool.Role.PartJade;
/**
 * 财产更新
 */
public class AssetUpdate {
    //类型 0npc购买 1商城购买 2积分兑换 3给予获取 4交易  5摆摊购买 6摆摊获得 7收摊  8物品使用获得 9活动怪偷钱 10套装返回11藏宝阁取回
	//21点击野怪立刻获取物品  25使用物品的效果 提示语直接写在msg
	public static int NPC=0;
	public static int MALL=1;
	public static int INTEGRATION=2;
	public static int GIVE=3;
	public static int DEAL=4;
	public static int STALLBUY=5;
	public static int STALLGET=6;
	public static int STALLRET=7;
	public static int USERGOOD=8;
	public static int STEALING=9;
	public static int SUIT=10;
	public static int CGB =11;
	public static int CLICK=21;
	public static int USEGOOD=25;

	public static int CBGBUY=15;
	public static int CBGGET=16;

	//物品
	private List<Goodstable> goods;
	//添加的召唤兽
	private List<RoleSummoning> pets;
	//添加的灵宝
	private List<Lingbao> lingbaos;	
	//修改的坐骑
	private List<Mount> mounts;
	//修改的孩子
	private List<Baby> babys;
	//记录玉符的修改
	private List<PartJade> jades;
	private List<Pal> pals;
	private UseCardBean useCard;
	//类型                     
	private int type;
	/**提示*/
	private String msg;
	/**更改的数据 D大话币X仙玉E经验C充值积分 G物品数量修改 T特效  NBASE召唤兽初值增加选项 NSKIN召唤兽皮肤*/
	/**E修炼点数   B帮派贡献  S 绑玉*/
//	 * 人物状态      R等级=经验=HP=MP
//	 * 召唤兽状态  P召唤兽ID=等级=经验=亲密=HP=MP
//	 * 坐骑状态      M坐骑id=等级=经验=熟练度
//	 * 灵宝状态      L灵宝id=等级=经验=契合
//	 * 内丹状态      N内丹id=转生=等级=经验
	private String data;
	//副本数据
	private String sceneMsg;
	//修改的帮派抗性
	private String resistance;
	private String vip;
	private String task;
	private long I;
	public AssetUpdate() {
		// TODO Auto-generated constructor stub
		setI(getFag());
	}
	public AssetUpdate(int type) {
		this.type = type;
		setI(getFag());
	}
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
   
	public void updata(String data) {
		if (data==null||data.equals("")) {return;}
		if (this.data==null||this.data.equals("")) {
			this.data=data;
		}else {
			this.data=this.data+"|"+data;
		}
	}
	public void upmsg(String msg) {
		if (msg==null||msg.equals("")) {return;}
		if (this.msg==null||this.msg.equals("")) {
			this.msg=msg;
		}else {
			this.msg=this.msg+"|"+msg;
		}
	}
	public List<Goodstable> getGoods() {
		if (goods==null) {
			goods=new ArrayList<>();
		}
		return goods;
	}
	public void setGoods(List<Goodstable> goods) {
		this.goods = goods;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<RoleSummoning> getPets() {
		if (pets==null) {
			pets=new ArrayList<>();
		}
		return pets;
	}
	public void setPets(List<RoleSummoning> pets) {
		this.pets = pets;
	}
	public List<Lingbao> getLingbaos() {
		if (lingbaos==null) {
			lingbaos=new ArrayList<>();
		}
		return lingbaos;
	}
	public void setLingbaos(List<Lingbao> lingbaos) {
		this.lingbaos = lingbaos;
	}
	public List<PartJade> getJades() {	
		if (this.jades==null) {
			this.jades=new ArrayList<>();
		}
		return jades;
	}
	public void setJades(List<PartJade> jades) {
		this.jades = jades;
	}
	
	public List<Mount> getMounts() {
		if (mounts==null) {mounts=new ArrayList<>();}
		return mounts;
	}
	public void setMounts(List<Mount> mounts) {
		this.mounts = mounts;
	}
	public List<Baby> getBabys() {
		if (babys==null) {babys=new ArrayList<>();}
		return babys;
	}
	public void setBabys(List<Baby> babys) {
		this.babys = babys;
	}
	public void setGood(Goodstable good) {
		getGoods().add(good);
	}
	public void setPet(RoleSummoning pet) {
		getPets().add(pet);
	}
	public void setLingbao(Lingbao lingbao) {
		getLingbaos().add(lingbao);
	}
	public void setBaby(Baby baby) {
		getBabys().add(baby);
	}
	public void setMount(Mount mount) {
		getMounts().add(mount);
	}
	
	public void setPal(Pal pal) {
		if (pals==null) {
			pals=new ArrayList<>();
		}
		pals.add(pal);
	}
	public void setJade(PartJade jade) {
		getJades();
		for (int i = 0; i < jades.size(); i++) {
			PartJade partJade=jades.get(i);
			if (jade.getSuitid()==partJade.getSuitid()&&jade.getPartId()==partJade.getPartId()) {
				jades.set(i, jade);
				return;
			}
		}
		this.jades.add(jade);
	}
	public UseCardBean getUseCard() {
		return useCard;
	}
	public void setUseCard(UseCardBean useCard) {
		this.useCard = useCard;
	}
	public long getI() {
		return JmSum.MZ(I);
	}
	public void setI(long i) {
		this.I=JmSum.ZM(i);
	}
	public String getSceneMsg() {
		return sceneMsg;
	}
	public void setSceneMsg(String sceneMsg) {
		this.sceneMsg = sceneMsg;
	}
	
	/**查询可重叠物品*/
	public Goodstable getGoodSid(BigDecimal sid){
		if (goods==null) {return null;}
		for (int i = goods.size()-1; i >=0 ; i--) {
			if (goods.get(i).getGoodsid().compareTo(sid)==0) {
				return goods.get(i);
			}
		}
		return null;
	} 
	/**重置*/
	public void reset(){setI(getFag());}
	private static long fag=0;
	private synchronized static long getFag(){
		fag++;
		return fag;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public String getResistance() {
		return resistance;
	}
	public void setResistance(String resistance) {
		this.resistance = resistance;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
}
