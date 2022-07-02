package com.easyorder.service.impl;

import org.springframework.stereotype.Service;

import com.easyorder.entity.OrderFood;
import com.easyorder.mapper.OrderFoodMapper;
import com.easyorder.service.OrderFoodService;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;

@Service
public class OrderFoodServiceImpl extends MppServiceImpl<OrderFoodMapper, OrderFood> implements OrderFoodService{

}
