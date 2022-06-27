package com.easyorder.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyorder.entity.Customer;
import com.easyorder.mapper.CustomerMapper;
import com.easyorder.service.CustomerService;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
	@Resource
	public CustomerMapper cm;
	@Override
	public Customer findCustomerInfoById(int id) {
		return cm.selectById(id);
	}
	@Override
	public int insertCustomer(Customer customer) {
		return cm.insert(customer);
	}
	
}
