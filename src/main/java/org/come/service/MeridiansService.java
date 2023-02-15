package org.come.service;

public interface MeridiansService {


    String selectMeridians(Long roleid);

    void saveMeridians(Long roleid, String meridians);
}