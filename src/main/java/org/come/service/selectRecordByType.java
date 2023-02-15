package org.come.service;

import java.util.List;
import org.come.entity.Record;

public interface selectRecordByType {
    int insert(Record var1);

    List<Record> selectRecordByType(int var1, int var2);
}
