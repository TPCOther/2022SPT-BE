package com.easyorder.service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Order;

public interface OrderService extends IService<Order>{
	BaseExecution<Order> selectOrder(Order order,Integer pageSize,Integer pageIndex);
	BaseExecution<Order> updateOrder(Order order);
	BaseExecution<Order> insertOrder(Order order);
	BaseExecution<Object> statistiscOrder();
	BaseExecution<Order> selectOrder(Order order, Integer pageSize, Integer pageIndex,String CustomerNickname,Date startTime,Date endTime,Double maxAmount,Double minAount);
	BaseExecution<Order> selectOrder();
}
