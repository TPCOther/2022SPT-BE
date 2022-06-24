package com.easyorder.service;



import com.easyorder.entity.Customer;

public interface CustomerService {
	Customer findCustomerInfoById(int id);
	int insert(Customer customer);
}
