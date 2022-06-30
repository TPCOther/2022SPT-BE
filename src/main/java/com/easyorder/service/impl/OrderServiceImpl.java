package com.easyorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Order;
import com.easyorder.mapper.OrderMapper;
import com.easyorder.service.OrderService;

public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{

	/**
	 * 查询订单
	 */
	@Override
	public BaseExecution<Order> selectOrder(Order order, Integer pageSize, Integer pageIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 更新订单
	 */
	@Override
	public BaseExecution<Order> updateOrderList(Order order) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 新增订单
	 */
	@Override
	public BaseExecution<Order> insertOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
