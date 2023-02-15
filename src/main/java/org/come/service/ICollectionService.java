package org.come.service;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Collection;
import org.come.entity.CollectionExample;

public interface ICollectionService {
	List<Collection> selectRoleCollect(BigDecimal roleid);
	List<BigDecimal> selectUserCollection(BigDecimal roleid);
    /**
    *
    * @mbggenerated 2019-03-05
    */
   int countByExample(CollectionExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int deleteByExample(CollectionExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int deleteByPrimaryKey(BigDecimal colid);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int insert(Collection record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int insertSelective(Collection record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   List<Collection> selectByExample(CollectionExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   Collection selectByPrimaryKey(BigDecimal colid);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByExampleSelective(Collection record, CollectionExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByExample( Collection record, CollectionExample example);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByPrimaryKeySelective(Collection record);

   /**
    *
    * @mbggenerated 2019-03-05
    */
   int updateByPrimaryKey(Collection record);
}
