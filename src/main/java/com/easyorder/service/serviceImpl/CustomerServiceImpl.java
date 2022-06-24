package com.easyorder.service.serviceImpl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easyorder.entity.Customer;
import com.easyorder.mapper.CustomerMapper;
import com.easyorder.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Resource
	public CustomerMapper cm;
	@Override
	public Customer findCustomerInfoById(int id) {
		return cm.selectById(id);
	}
	@Override
	public int insert(Customer customer) {
		return cm.insert(customer);
	}
	
}
