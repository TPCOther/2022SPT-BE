package com.easyorder.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Customer;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.CustomerMapper;
import com.easyorder.service.CustomerService;
import com.easyorder.util.BaseExecuteException;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
	@Resource
	public CustomerMapper customerMapper;
    /*
     * 会员查询
     */
	@Override
	public BaseExecution<Customer> selectCustomerList(Customer selectTable) throws BaseExecuteException {
		BaseExecution<Customer> baseExecution = new BaseExecution<Customer>();
            QueryWrapper<Customer> wapper = new QueryWrapper<>();
            Long customerId=selectTable.getCustomerId();
            Integer customerPhone=selectTable.getCustomerPhone();
            Integer customerVip=selectTable.getCustomerVip();
            wapper.eq(customerId!=null,"customer_id",customerId);
            wapper.eq(customerPhone!=null,"customer_phone",customerPhone);
            wapper.eq(customerVip!=null,"customer_vip",customerVip);
            try{
                List<Customer> customerList = customerMapper.selectList(wapper);
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTList(customerList);
                baseExecution.setCount(Long.valueOf(customerList.size()));
                return baseExecution;
            }catch (Exception e) {
                throw new BaseExecuteException("查询顾客(Customer)失败: "+e.getMessage());
			}
	}

	@Override
	@Transactional
	public BaseExecution<Customer> insertCustomer(Customer insertCustomer) throws BaseExecuteException {
        Date now = new Date();
        insertCustomer.setCreateTime(now);
        insertCustomer.setLastEditTime(now);
		BaseExecution<Customer> baseExecution = new BaseExecution<Customer>();
        try{
            // TODO 登陆
            int effctedNum = customerMapper.insert(insertCustomer);
            if(effctedNum <= 0){
                throw new BaseExecuteException("插入0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(insertCustomer);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("创建顾客(Customer)失败: "+e.getMessage());
        }
	}

	@Override
	@Transactional
	public BaseExecution<Customer> updateCustomer(Customer updateCustomer) throws BaseExecuteException {
        Date now = new Date();
        updateCustomer.setLastEditTime(now);
		BaseExecution<Customer> baseExecution = new BaseExecution<Customer>();
        try{
            int effctedNum = customerMapper.updateById(updateCustomer);
            if(effctedNum <= 0){
                throw new BaseExecuteException("更新0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(updateCustomer);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("更新顾客(Customer)失败: "+e.getMessage());
        }
	}

	@Override
	@Transactional
	public BaseExecution<Customer> deleteCustomer(Customer deleteCustomer) throws BaseExecuteException {
		BaseExecution<Customer> baseExecution = new BaseExecution<Customer>();
        try{
            int effctedNum = customerMapper.deleteById(deleteCustomer);
            if(effctedNum <= 0){
                throw new BaseExecuteException("删除0条数据");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(deleteCustomer);
            return baseExecution;
            
        }catch(Exception e){       
            throw new BaseExecuteException("删除顾客(Customer)失败: "+e.getMessage());
        }
	}
	
	
}
