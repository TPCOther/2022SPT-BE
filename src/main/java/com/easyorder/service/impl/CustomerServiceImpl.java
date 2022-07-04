package com.easyorder.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service
@Scope("prototype")
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
	@Resource
	public CustomerMapper customerMapper;
    
	@Value("${wx.app-id}")
	private String appId;
	
	@Value("${wx.app-secret}")
	private String appSecret;
	
	
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
	public BaseExecution<Customer> insertCustomer(Customer insertCustomer,String code) throws BaseExecuteException {
        Date now = new Date();
        insertCustomer.setCreateTime(now);
        insertCustomer.setLastEditTime(now);
		BaseExecution<Customer> baseExecution = new BaseExecution<Customer>();
        try{
            // TODO 注册
        	String openId=getOpenId(code);
        	insertCustomer.setOpenId(openId);
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
	
	
	//访问微信平台获取openId
	private String getOpenId(String code) {
		String url="https://api.weixin.qq.com/sns/jscode2session";
		Map<String,Object> map=new HashMap<>();
		map.put("appid",appId);
		map.put("secret", appSecret);
		map.put("js_code",code);
		map.put("grant_type","authorization_code");
		String response=HttpUtil.get(url,map);
		JSONObject json=JSONUtil.parseObj(response);
		String openId=json.getStr("openid");
//		System.out.println(response);
		if(openId==null||openId.length()==0) {
			throw new BaseExecuteException("获取授权失败");
		}
		return openId;
	}

	@Override
	public BaseExecution<Customer> login(String code) {
		try {
			String openId =getOpenId(code);
			QueryWrapper<Customer> q=new QueryWrapper<Customer>();
			q.eq("open_id",openId);
			q.last("limit 1");
			Customer customer=getOne(q);
			if(customer==null) {
				throw new BaseExecuteException("账户不存在");
			}
			BaseExecution<Customer> be=new BaseExecution<Customer>();
			be.setTemp(customer);
			be.setEum(ExecuteStateEum.SUCCESS);
			return be;
		} catch (BaseExecuteException e) {
			return new BaseExecution<Customer>(e.getMessage());
		}catch (Exception e) {
			return new BaseExecution<Customer>("未知错误");
		}
		
	}
	
}
