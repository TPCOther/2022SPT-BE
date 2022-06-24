package com.easyorder.service;

import java.util.List;

import com.easyorder.entity.Customer;

public interface CustomerService {
	List<Customer> findCustomerInfoById(int id);
}
