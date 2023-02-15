package org.come.mapper;

import java.util.List;

import org.come.annotation.MyBatisAnnotation;
import org.come.entity.Ipaddressmac;

@MyBatisAnnotation
public interface IpaddressmacMapper {
    /**@mbggenerated 1980-01-01*/
    int insert(Ipaddressmac record);

    /**@mbggenerated 1980-01-01*/
    int insertSelective(Ipaddressmac record);
    
    List<Ipaddressmac> selectIpaddressmac(String addresskey);
}