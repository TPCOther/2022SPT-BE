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
						payMap.put("微信支付", payMap.getOrDefault("微信支付", 0f) + o.getOrderAmount());
					} else if (o.getOrderPayMethod() == OrderStateEum.ALIPAY.getState()) {
						payMap.put("支付宝", payMap.getOrDefault("支付宝", 0f) + o.getOrderAmount());
					} else {
						payMap.put("其他支付", payMap.getOrDefault("其他支付", 0f) + o.getOrderAmount());
					}
				}
				List<OrderFood> l2 = orderFoodMapper.getOrderFoodList(q1);
				for (OrderFood of : l2) {
					foodMap.put(of.getFoodName(), foodMap.getOrDefault(of.getFoodName(), 0) + of.getOrderFoodNum());
				}
			}
			l.add(payMap);
			l.add(foodMap);
			return new BaseExecution<Object>(ExecuteStateEum.SUCCESS, l);
		} catch (Exception e) {
			return new BaseExecution<Object>("未知错误");
		}

	}

}
