package com.easyorder.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Customer;
import com.easyorder.entity.Food;
import com.easyorder.entity.Order;
import com.easyorder.entity.OrderFood;
import com.easyorder.enums.CustomerVipEum;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.enums.OrderStateEum;
import com.easyorder.mapper.CustomerMapper;
import com.easyorder.mapper.FoodMapper;
import com.easyorder.mapper.OrderMapper;
import com.easyorder.service.OrderFoodService;
import com.easyorder.service.OrderService;
import com.easyorder.util.BaseExecuteException;

import cn.hutool.core.date.DateUtil;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

	@Resource
	OrderFoodService orderFoodService;

	@Resource
	FoodMapper foodMapper;

	@Resource
	CustomerMapper customerMapper;
	@Resource
	OrderMapper orderMapper;

	/**
	 * 查询订单
	 */
	@Override
	public BaseExecution<Order> selectOrder(Order order, Integer pageSize, Integer pageIndex) {
		try {
			QueryWrapper<Order> q = new QueryWrapper<Order>();
			q.eq(order.getOrderId() != null, "order_id", order.getOrderId());
			q.eq(order.getCustomerId() != null, "customer_id", order.getCustomerId());
			q.eq(order.getStaffId() != null, "staff_id", order.getStaffId());
			q.eq(order.getDinTableId() != null, "table_id", order.getDinTableId());
			q.eq(order.getOrderState() != null, "order_state", order.getOrderState());
			q.orderByDesc("create_time");
			List<Order> orderList = null;
			BaseExecution<Order> be = new BaseExecution<Order>();
			if (pageIndex > 0 || pageSize > 0) {
				Page<Order> page = new Page<>(pageIndex, pageSize);
				page(page, q);
				orderList = page.getRecords();
				Long count = count(q);
				be.setCount(count);
			} else {
				orderList = list(q);
			}
			for (Order o : orderList) {
				QueryWrapper<OrderFood> queryWrapper = new QueryWrapper<OrderFood>();
				queryWrapper.eq("order_id", o.getOrderId());
				List<OrderFood> orderFoods = orderFoodService.list(queryWrapper);
				o.setOrderFoodList(orderFoods);
			}
			be.setEum(ExecuteStateEum.SUCCESS);
			be.setTList(orderList);
			return be;
		} catch (Exception e) {
			return new BaseExecution<Order>("未知错误");
		}
	}

	/**
	 * 更新订单
	 */
	@Override
	@Transactional
	public BaseExecution<Order> updateOrder(Order order) {
		try {
			boolean b;
			if (order.getOrderFoodList() != null && order.getOrderFoodList().size() > 0) {
				Customer customer = customerMapper.selectById(order.getCustomerId());
				order.setOrderAmount(setAmount(order.getOrderFoodList(),
						customer.getCustomerVip() == CustomerVipEum.VIP.getState()));
				QueryWrapper<OrderFood> q = new QueryWrapper<OrderFood>();
				q.eq("order_id", order.getOrderId());
				b = orderFoodService.remove(q);
				if (!b) {
					throw new BaseExecuteException("删除原来订单菜品列表失败");
				}
				b = orderFoodService.saveBatch(order.getOrderFoodList());
				if (!b) {
					throw new BaseExecuteException("修改订单菜品列表失败");
				}
			}
			b = updateById(order);
			if (!b) {
				throw new BaseExecuteException("修改订单失败");
			}
			return new BaseExecution<Order>(ExecuteStateEum.SUCCESS);
		} catch (BaseExecuteException e) {
			return new BaseExecution<Order>(e.getMessage());
		} catch (Exception e) {
			return new BaseExecution<Order>("未知错误");
		}
	}

	/**
	 * 新增订单
	 */
	@Override
	@Transactional
	public BaseExecution<Order> insertOrder(Order order) {
		try {
			Customer customer = customerMapper.selectById(order.getCustomerId());
			order.setCreateTime(new Date());
			order.setOrderAmount(
					setAmount(order.getOrderFoodList(), customer.getCustomerVip() == CustomerVipEum.VIP.getState()));
			boolean b = save(order);
			if (!b) {
				throw new BaseExecuteException("添加订单失败");
			}
			for (OrderFood orderfood : order.getOrderFoodList()) {
				orderfood.setOrderId(order.getOrderId());
			}
			b = orderFoodService.saveBatch(order.getOrderFoodList());
			if (!b) {
				throw new BaseExecuteException("添加订单菜品列表失败");
			}
			return new BaseExecution<Order>(ExecuteStateEum.SUCCESS, order);
		} catch (BaseExecuteException e) {
			return new BaseExecution<Order>(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new BaseExecution<Order>("未知错误");
		}
	}

	// 计算订单金额
	public Float setAmount(List<OrderFood> orderFoods, Boolean VIP) {
		Float sum = 0f;
		for (OrderFood of : orderFoods) {
			Food food = foodMapper.selectById(of.getFoodId());
			if (food.getFoodPromotionPrice() < 0) {
				sum += food.getFoodNormalPrice() * of.getOrderFoodNum();
			} else {
				sum += food.getFoodPromotionPrice() * of.getOrderFoodNum();
			}
		}
		if (VIP)
			sum *= 0.88f;

		return Float.valueOf(String.format("%.2f", sum));
	}

	// 统计订单信息
	public BaseExecution<Object> statistiscOrder() {
		// 统计今日营业额
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		List<Object> l = new ArrayList<>();
		Date date = new Date();
	
		Date date1 = DateUtil.beginOfDay(date);
		Date date2 = DateUtil.beginOfWeek(date);
		QueryWrapper<Order> q = new QueryWrapper<Order>();
		q.orderByAsc("pay_time");
		q.eq("order_state",OrderStateEum.COMPLETE.getState());
		q.lt("pay_time", date1);

		List<Order> orders = orderMapper.selectList(q);
		int effectiveOrder = 0;
		float revenueDay = 0;

		for (Order o : orders) {
			if (DateUtil.endOfDay(date2).after(o.getPayTime()))
				revenueDay += o.getOrderAmount();
			else {
				l.add(revenueDay);
				revenueDay = o.getOrderAmount();
				date2 = DateUtil.offsetDay(date2, 1);
			}

			if (o.getPayTime().after(date1)) {
				effectiveOrder++;
			}
		}
		if (l.size() < DateUtil.betweenDay(date2, date, true));
			l.add(revenueDay);
		l.add(effectiveOrder);
		return new BaseExecution<Object>(ExecuteStateEum.SUCCESS, l);
	}
}
