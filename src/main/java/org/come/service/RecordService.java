package org.come.service;

import org.come.entity.Record;

import java.util.List;

public interface RecordService {
	/**添加记录*/
	int insert(Record record);

	/**查询记录*/
	List<Record> selectRecordByType(int recordType, int count);
}
