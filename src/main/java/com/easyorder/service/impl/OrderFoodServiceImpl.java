package com.easyorder.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Order;
import com.easyorder.entity.OrderFood;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.enums.OrderStateEum;
import com.easyorder.mapper.OrderFoodMapper;
import com.easyorder.mapper.OrderMapper;
import com.easyorder.service.OrderFoodService;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;

@Service
public class OrderFoodServiceImpl extends MppServiceImpl<OrderFoodMapper, OrderFood> implements OrderFoodService {

	@Resource
	OrderMapper orderMapper;
	@Resource
	OrderFoodMapper orderFoodMapper;

	@Override
	public BaseExecution<Object> statisticsOrderFood(Date startDate, Date endDate) {
		try {
			List<Object> l = new ArrayList<>();
			QueryWrapper<Order> q = new QueryWrapper<Order>();
			q.eq("order_state", OrderStateEum.COMPLETE.getState());
			q.le("pay_time", endDate);
			q.ge("pay_time", startDate);
			List<Order> orders = orderMapper.selectList(q);
			Map<String, Integer> foodMap = new HashMap<>();
			Map<String, Float> payMap = new HashMap<>();
			for (Order o : orders) {
				QueryWrapper<OrderFood> q1 = new QueryWrapper<OrderFood>();
				q1.eq("order_id", o.getOrderId());
				if (o.getOrderPayMethod() != null) {
					if (o.getOrderPayMethod() == OrderStateEum.WECHAT.getState()) {
						payMap.put("Wechat", payMap.getOrDefault("Wechat", 0f) + o.getOrderAmount());
//						System.out.println(o.getOrderAmount());
					} else if (o.getOrderPayMethod() == OrderStateEum.ALIPAY.getState()) {
						payMap.put("Alipay", payMap.getOrDefault("Alipay", 0f) + o.getOrderAmount());
					} else {
						payMap.put("Else", payMap.getOrDefault("Else", 0f) + o.getOrderAmount());
					}
				}
				List<OrderFood> l2 = orderFoodMapper.getOrderFoodList(q1);
				for (OrderFood of : l2) {
					foodMap.put(of.getFoodName(), foodMap.getOrDefault(of.getFoodName(), 0) + of.getOrderFoodNum());
				}
			}
			List<Object[]> foodList=new ArrayList<>();
			for(String foodname:foodMap.keySet()) {
				Object[] t=new Object[2];
				t[0]=foodname;
				t[1]=foodMap.get(foodname);
				foodList.add(t);
			}
			l.add(payMap);
			l.add(foodList);
			return new BaseExecution<Object>(ExecuteStateEum.SUCCESS, l);
		} catch (Exception e) {
			return new BaseExecution<Object>("未知错误");
		}

	}

}
