package org.come.mapper;

import org.apache.ibatis.annotations.Param;
import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Record;

import java.util.List;

@MyBatisAnnotation
public interface RecordMapper {
	/**添加记录*/
	int insert(Record record);
	/**查询记录*/
	List<Record> selectRecordByType(@Param("recordType") int recordType, @Param("count") int count);
}
