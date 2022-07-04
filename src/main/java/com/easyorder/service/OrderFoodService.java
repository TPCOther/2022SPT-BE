package com.easyorder.service;

import java.util.Date;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.OrderFood;
import com.github.jeffreyning.mybatisplus.service.IMppService;

public interface OrderFoodService extends IMppService<OrderFood>{
	BaseExecution<Object> statisticsOrderFood(Date startDate,Date endDate);
}
