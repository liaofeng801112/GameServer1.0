package org.come.entity;

import java.math.BigDecimal;

public class Record {
	private BigDecimal recordId;
	private int recordType;
	private String text;
	private String recordTime;

	public Record() {
	}

	public Record(int recordType, String text) {
		this.recordType = recordType;
		this.text = text;
	}

	public BigDecimal getRecordId() {
		return this.recordId;
	}

	public void setRecordId(BigDecimal recordId) {
		this.recordId = recordId;
	}

	public int getRecordType() {
		return this.recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
}