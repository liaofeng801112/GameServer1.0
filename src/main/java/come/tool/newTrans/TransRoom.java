package come.tool.newTrans;


public class TransRoom {

	//交易id
	private int transId;
	//交易发起人
	private TransRole role1;
	//交易接收者
	private TransRole role2;
	public TransRoom(int transId, TransRole role1, TransRole role2) {
		super();
		this.transId = transId;
		this.role1 = role1;
		this.role2 = role2;
	}
	/**检查双方都已经同意交易了*/
	public boolean isTrans(){
		if (role1.getState()==0) {return false;}
		else if (role2.getState()==0) {return false;}
		return true;
	}
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public TransRole getRole1() {
		return role1;
	}
	public void setRole1(TransRole role1) {
		this.role1 = role1;
	}
	public TransRole getRole2() {
		return role2;
	}
	public void setRole2(TransRole role2) {
		this.role2 = role2;
	}
}
