package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.ServiceArea;
import org.come.mapper.ServiceAreaMapper;
import org.come.service.ServiceAreaService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;



@Service
public class ServiceAreaServiceImpl  implements ServiceAreaService{
	//实例化
	private ServiceAreaMapper serviceAreaMapper;
     public ServiceAreaServiceImpl() {
    		ApplicationContext ctx = MybatisUntil.getApplicationContext();
    		// id为类名且首字母小写才能被自动扫描扫到
    		serviceAreaMapper = (ServiceAreaMapper)ctx.getBean("serviceAreaMapper");
	}
	@Override
	public int deleteByPrimaryKey(BigDecimal sid) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.deleteByPrimaryKey(sid);
	}

	@Override
	public int insert(ServiceArea record) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.insert(record);
	}

	@Override
	public int insertSelective(ServiceArea record) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.insertSelective(record);
	}

	@Override
	public ServiceArea selectByPrimaryKey(BigDecimal sid) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.selectByPrimaryKey(sid);
	}

	@Override
	public int updateByPrimaryKeySelective(ServiceArea record) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ServiceArea record) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public List<BigDecimal> selectServiceAreaid(ServiceArea record) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.selectServiceAreaid(record);
	}

	@Override
	public List<ServiceArea> selectAllService() {
		// TODO Auto-generated method stub
		return serviceAreaMapper.selectAllService();
	}

	@Override
	public List<ServiceArea> selectListAreaForUid(BigDecimal manageid) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.selectListAreaForUid(manageid);
	}

	@Override
	public List<ServiceArea> selectServiceForPage(int pageNumber) {
		// TODO Auto-generated method stub
		return serviceAreaMapper.selectServiceForPage(pageNumber);
	}
		
}