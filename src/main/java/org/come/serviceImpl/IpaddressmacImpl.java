package org.come.serviceImpl;

import java.util.List;

import org.come.entity.Ipaddressmac;
import org.come.mapper.IpaddressmacMapper;
import org.come.service.IpaddressmacService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class IpaddressmacImpl implements IpaddressmacService {

	private IpaddressmacMapper ipaddressmacMapper;
	
	public IpaddressmacImpl() {
		// TODO Auto-generated constructor stub
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		ipaddressmacMapper = (IpaddressmacMapper)ctx.getBean("ipaddressmacMapper");
	}

	@Override
	public int insert(Ipaddressmac record) {
		// TODO Auto-generated method stub
		return ipaddressmacMapper.insert(record);
	}

	@Override
	public int insertSelective(Ipaddressmac record) {
		// TODO Auto-generated method stub
		return ipaddressmacMapper.insertSelective(record);
	}

	@Override
	public List<Ipaddressmac> selectIpaddressmac(String addresskey) {
		// TODO Auto-generated method stub
		return ipaddressmacMapper.selectIpaddressmac(addresskey);
	}

}
