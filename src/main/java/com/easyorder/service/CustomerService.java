package com.easyorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Customer;
import com.easyorder.util.BaseExecuteException;

public interface CustomerService extends IService<Customer>{
	BaseExecution<Customer> selectCustomerList(Customer selectTable) throws BaseExecuteException;
    BaseExecution<Customer> insertCustomer(Customer insertCustomer) throws BaseExecuteException;
    BaseExecution<Customer> updateCustomer(Customer updateCustomer) throws BaseExecuteException;
    BaseExecution<Customer> deleteCustomer(Customer deleteCustomer) throws BaseExecuteException;
}
