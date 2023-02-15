package come.tool.FightingData;

import org.come.entity.RoleSummoning;
import org.come.until.AllServiceUtil;

import come.tool.Calculation.BaseQl;
import come.tool.Role.Hang;

/**
 * 战斗中召唤兽集合
 * @author Administrator
 *
 */
public class FightingSummon {
    //召唤兽数据
	private ManData SummonData;
	//召唤兽是否出场 0未上场 1正在上场 2已经下场  
	private int Play;
	private Hang hang;
	private Double helpV;
	private BaseQl[] vs;
	private double starXS;
	private int camp;
	private int man;
	private boolean isBB;
	public FightingSummon() {
		// TODO Auto-generated constructor stub
	}

	public FightingSummon(int play, Hang hang,int camp,int man) {
		super();
		this.Play = play;
		this.hang = hang;
		this.camp = camp;
		this.man  = man+5;
	}
	
	public int getPlay() {
		return Play;
	}
	public void setPlay(int play) {
		Play = play;
	}
	public Hang getHang() {
		return hang;
	}
	public void setHang(Hang hang) {
		this.hang = hang;
	}
	
	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public int getMan() {
		return man;
	}

	public void setMan(int man) {
		this.man = man;
	}

	public boolean isBB() {
		return isBB;
	}

	public void setBB(boolean isBB) {
		this.isBB = isBB;
	}

	public Double getHelpV() {
		return helpV;
	}

	public void setHelpV(Double helpV) {
		this.helpV = helpV;
	}

	public void setStar(BaseQl[] vs,double starXS) {
		this.vs = vs;
		this.starXS=starXS;
		if (this.vs!=null) {
			if (SummonData!=null) {
			    for (int i = 0; i < vs.length; i++) {
			    	GetqualityUntil.AddR(SummonData.getQuality(), vs[i].getKey(), vs[i].getValue()*this.starXS);
				}
			}
		}
	}
	/**判断是否触发闪现0没闪现 1闪失败 2闪成功*/
	public int getsx(double jc) {
		if (getPet()==null) {
			return 0;
		}
		if (helpV==null) {
			return 0;
		}
		if (helpV+jc>Battlefield.random.nextInt(100)) {
			return getPet()!=null?2:1;
		}
		return 1;
	}
	/**获取召唤兽战斗对象*/
	public ManData getPet(){
		if (SummonData!=null) {
			return SummonData;
		}
		RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(hang.getId());
		if (pet==null) {
			this.Play=2;
			return null;
		}else if (isBB&&pet.getFriendliness()<200000) {
			this.Play=2;
			return null;
		}
		//开始加载战斗对象
		SummonData=new ManData(pet, this);
		return SummonData;
	}
	/**参战时判断 0是正常   1忠诚不够 2亲密不够*/
	public int getAttendPet(){
		RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(hang.getId());
		if (pet==null) {
			this.Play=2;
			return 1;
		}else if (isBB&&pet.getFriendliness()<200000) {
			this.Play=2;
			return 2;
		}else if (pet.getFaithful()<70||(Battlefield.random.nextBoolean()&&pet.getFaithful()==70)) {
			this.Play=2;
			return 1;
		}
		//开始加载战斗对象
		SummonData=new ManData(pet, this);
		return 0;
	}
}
