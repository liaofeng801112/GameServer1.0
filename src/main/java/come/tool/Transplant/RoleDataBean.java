package come.tool.Transplant;

import java.util.List;

import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.PackRecord;
import org.come.entity.RoleSummoning;
import org.come.entity.Titletable;

public class RoleDataBean {

	private LoginResult loginResult;//人物数据
	private PackRecord packRecord;//背包记录数据
	private List<Goodstable> goodstables;//物品
	private List<RoleSummoning> pets;//宝宝
	private List<Mount> mounts;//坐骑
	private List<Lingbao> lingbaos;//灵宝
	private List<Baby> babys;//孩子
	private List<Titletable> titletables;//称谓
	public RoleDataBean(LoginResult loginResult, PackRecord packRecord,
			List<Goodstable> goodstables, List<RoleSummoning> pets,
			List<Mount> mounts, List<Lingbao> lingbaos, List<Baby> babys,
			List<Titletable> titletables) {
		super();
		this.loginResult = loginResult;
		this.packRecord = packRecord;
		this.goodstables = goodstables;
		this.pets = pets;
		this.mounts = mounts;
		this.lingbaos = lingbaos;
		this.babys = babys;
		this.titletables = titletables;
	}
	public LoginResult getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(LoginResult loginResult) {
		this.loginResult = loginResult;
	}
	public PackRecord getPackRecord() {
		return packRecord;
	}
	public void setPackRecord(PackRecord packRecord) {
		this.packRecord = packRecord;
	}
	public List<Goodstable> getGoodstables() {
		return goodstables;
	}
	public void setGoodstables(List<Goodstable> goodstables) {
		this.goodstables = goodstables;
	}
	public List<RoleSummoning> getPets() {
		return pets;
	}
	public void setPets(List<RoleSummoning> pets) {
		this.pets = pets;
	}
	public List<Mount> getMounts() {
		return mounts;
	}
	public void setMounts(List<Mount> mounts) {
		this.mounts = mounts;
	}
	public List<Lingbao> getLingbaos() {
		return lingbaos;
	}
	public void setLingbaos(List<Lingbao> lingbaos) {
		this.lingbaos = lingbaos;
	}
	public List<Baby> getBabys() {
		return babys;
	}
	public void setBabys(List<Baby> babys) {
		this.babys = babys;
	}
	public List<Titletable> getTitletables() {
		return titletables;
	}
	public void setTitletables(List<Titletable> titletables) {
		this.titletables = titletables;
	}
	
}
