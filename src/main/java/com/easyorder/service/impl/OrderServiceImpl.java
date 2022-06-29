package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.FoodCategory;
import com.easyorder.entity.Order;
import com.easyorder.entity.OrderFood;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.OrderFoodMapper;
import com.easyorder.mapper.OrderMapper;
import com.easyorder.service.OrderService;
import com.easyorder.util.BaseExecuteException;

public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

	@Resource
	OrderFoodMapper orderFoodMapper;

	/**
	 * 查询订单
	 */
	@Override
	public BaseExecution<Order> selectOrder(Order order, Long customerId, Long staffId, Long tableId, Integer pageSize,
			Integer pageIndex) {
		try {
			QueryWrapper<Order> q = new QueryWrapper<Order>();
			q.eq(order.getOrderId() != null, "order_id", order.getOrderId());
			q.eq(customerId != null, "customer_id", customerId);
			q.eq(staffId != null, "staff_id", staffId);
			q.eq(tableId != null, "table_id", tableId);
			q.eq(order.getOrderState() != null, "order_state", order.getOrderState());
			q.orderByDesc("create_time");
			List<Order> orderList=null;
			BaseExecution<Order> be = new BaseExecution<Order>();
			if (pageIndex > 0 || pageSize > 0) {
				Page<Order> page = new Page<>(pageIndex, pageSize);
				page(page,q);
				orderList=page.getRecords();
				Long count=count(q);
				be.setCount(count);
			}else {
				orderList = list(q);
			}
			for (Order o : orderList) {
				QueryWrapper<OrderFood> queryWrapper = new QueryWrapper<OrderFood>();
				queryWrapper.eq("order_id", o.getOrderId());
				List<OrderFood> orderFoods = orderFoodMapper.selectList(queryWrapper);
				o.setOrderFoodList(orderFoods);
			}
			be.setEum(ExecuteStateEum.SUCCESS);
			be.setTList(orderList);
			return be;
		} catch (Exception e) {
			return new BaseExecution<Order>(ExecuteStateEum.INNER_ERROR);
		}
	}

	/**
	 * 更新订单
	 */
	@Override
	@Transactional
	public BaseExecution<Order> updateOrder(Order order) {
		try {
			boolean b=updateById(order);
			if(!b) {
				throw new BaseExecuteException("修改失败");
			}
			if(order.getOrderFoodList()!=null&&order.getOrderFoodList().size()>0) {
				for(OrderFood orderfood: order.getOrderFoodList()) {
					int e=orderFoodMapper.updateById(orderfood);
					if(e<=0) {
						throw new BaseExecuteException("修改失败");
					}
				}
			}
			return new BaseExecution<Order>(ExecuteStateEum.SUCCESS);
		}catch (Exception e) {
			return new BaseExecution<Order>(ExecuteStateEum.INNER_ERROR);
		}
	}
	
	/**
	 * 新增订单
	 */
	@Override
	@Transactional
	public BaseExecution<Order> insertOrder(Order order) {
		
		return null;
	}
	
}
