package org.come.service;

import java.util.List;

import org.come.entity.Ipaddressmac;

public interface IpaddressmacService {

    /**@mbggenerated 1980-01-01*/
   int insert(Ipaddressmac record);

   /**@mbggenerated 1980-01-01*/
   int insertSelective(Ipaddressmac record);
   
   List<Ipaddressmac> selectIpaddressmac(String addresskey);
}
