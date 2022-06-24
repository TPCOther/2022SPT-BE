package com.easyorder.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import com.easyorder.entity.Customer;
import com.easyorder.mapper.CustomerMapper;
import com.easyorder.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	@Resource
	public CustomerMapper cm;
	@Override
	public Customer findCustomerInfoById(int id) {
		return cm.selectById(id);
	}

}
