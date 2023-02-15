package come.tool.FightingData;

import java.util.List;

import org.come.bean.LoginResult;
import org.come.entity.Lingbao;
import org.come.entity.RoleSummoning;

/**
 * 战斗结束
 * @author Administrator
 *
 */
public class FightingEndD {
	//玩家数据
	private LoginResult loginResult;
	//召唤兽数据集合
	private RoleSummoning pet;	
	//战斗编号
	private int Fightingsumber;
	//送他去监狱 0不送 1死 2地 3天   类型 4帮战输了
	private int type;
	private List<Lingbao> lingbaos;
	private String mData;	
	private Integer doorId;
	public LoginResult getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(LoginResult loginResult) {
		this.loginResult = loginResult;
	}
	public List<Lingbao> getLingbaos() {
		return lingbaos;
	}
	public void setLingbaos(List<Lingbao> lingbaos) {
		this.lingbaos = lingbaos;
	}
	
	public String getmData() {
		return mData;
	}
	public void setmData(String mData) {
		this.mData = mData;
	}
	public RoleSummoning getPet() {
		return pet;
	}
	public void setPet(RoleSummoning pet) {
		this.pet = pet;
	}
	public int getFightingsumber() {
		return Fightingsumber;
	}
	public void setFightingsumber(int fightingsumber) {
		Fightingsumber = fightingsumber;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getDoorId() {
		return doorId;
	}
	public void setDoorId(Integer doorId) {
		this.doorId = doorId;
	}
	
	
}
