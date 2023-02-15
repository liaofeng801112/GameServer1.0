package come.tool.Transplant;

import java.util.List;

/**移植bean*/
public class TransplantBean {

	//修改时 赋予的前缀
	private List<UserDataBean> list;
	
	public TransplantBean(List<UserDataBean> list) {
		super();
		this.list = list;
	}
	public List<UserDataBean> getList() {
		return list;
	}
	public void setList(List<UserDataBean> list) {
		this.list = list;
	}
}
