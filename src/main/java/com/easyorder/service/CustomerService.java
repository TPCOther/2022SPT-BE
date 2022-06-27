package com.easyorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyorder.entity.Customer;

public interface CustomerService extends IService<Customer>{
	Customer findCustomerInfoById(int id);
	int insertCustomer(Customer customer);
}
