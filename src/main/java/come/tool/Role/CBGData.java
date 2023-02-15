package come.tool.Role;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.LoginResult;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.RoleSummoning;

public class CBGData {
	private BigDecimal role_id;
	private String grade;
	private LoginResult loginResult;
	private GBGData2 data2;
	private List<Goodstable> goods;
	private List<RoleSummoning> pets;
	private List<Mount> mounts;
	private List<Lingbao> lings;
	private List<Baby> babys;
	public CBGData(LoginResult loginResult,List<Goodstable> goods, List<RoleSummoning> pets,List<Mount> mounts, List<Lingbao> lings, List<Baby> babys) {
		super();
		this.role_id = loginResult.getRole_id();
		this.loginResult = loginResult;
		this.goods = goods;
		this.pets = pets;
		this.mounts = mounts;
		this.lings = lings;
		this.babys = babys;
	}
	public BigDecimal getRole_id() {
		return role_id;
	}
	public void setRole_id(BigDecimal role_id) {
		this.role_id = role_id;
	}
	public LoginResult getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(LoginResult loginResult) {
		this.loginResult = loginResult;
	}
	public GBGData2 getData2() {
		return data2;
	}
	public void setData2(GBGData2 data2) {
		this.data2 = data2;
	}
	public List<Goodstable> getGoods() {
		return goods;
	}
	public void setGoods(List<Goodstable> goods) {
		this.goods = goods;
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
	public List<Lingbao> getLings() {
		return lings;
	}
	public void setLings(List<Lingbao> lings) {
		this.lings = lings;
	}
	public List<Baby> getBabys() {
		return babys;
	}
	public void setBabys(List<Baby> babys) {
		this.babys = babys;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
