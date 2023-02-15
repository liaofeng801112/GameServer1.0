package come.tool.Good;

import java.math.BigDecimal;

public class NPCDialogBean {

	private long id;
	private int type;
	private BigDecimal OId;
	private int value;
	public NPCDialogBean(long id, int type, BigDecimal oId, int value) {
		super();
		this.id = id;
		this.type = type;
		OId = oId;
		this.value = value;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public BigDecimal getOId() {
		return OId;
	}
	public void setOId(BigDecimal oId) {
		OId = oId;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
