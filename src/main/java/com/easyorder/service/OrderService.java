package com.easyorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Order;

public interface OrderService extends IService<Order>{
	BaseExecution<Order> selectOrder(Order order,Integer pageSize,Integer pageIndex);
	BaseExecution<Order> updateOrderList(Order order);
	BaseExecution<Order> insertOrder(Order order);
}
