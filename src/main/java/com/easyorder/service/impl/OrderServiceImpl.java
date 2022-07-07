package com.easyorder.service.impl;

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
import com.easyorder.entity.DinTable;
import com.easyorder.entity.Food;
import com.easyorder.entity.Order;
import com.easyorder.entity.OrderFood;
import com.easyorder.enums.CustomerVipEum;
import com.easyorder.enums.DinTableStateEum;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.enums.OrderStateEum;
import com.easyorder.mapper.CustomerMapper;
import com.easyorder.mapper.DinTableMapper;
import com.easyorder.mapper.FoodMapper;
import com.easyorder.mapper.OrderFoodMapper;
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
	OrderFoodMapper orderFoodMapper;
	@Resource
	FoodMapper foodMapper;

	@Resource
	CustomerMapper customerMapper;
	@Resource
	OrderMapper orderMapper;
	@Resource
	DinTableMapper dinTableMapper;

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
			q.eq(order.getDinTableId() != null, "din_table_id", order.getDinTableId());
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
				List<OrderFood> orderFoods = orderFoodMapper.getOrderFoodList(queryWrapper);
				o.setOrderFoodList(orderFoods);
			}
			be.setEum(ExecuteStateEum.SUCCESS);
			be.setTList(orderList);
			return be;
		} catch (Exception e) {
			System.out.println(e.getMessage());
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

			Customer customer = customerMapper.selectById(order.getCustomerId());
			if (order.getOrderFoodList() != null && order.getOrderFoodList().size() > 0) {
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
			// 结账
			if (order.getOrderState() != null && order.getOrderState() == OrderStateEum.COMPLETE.getState()) {
				order.setPayTime(new Date());
				if (order.getOrderPayMethod() == null)
					order.setOrderPayMethod(OrderStateEum.ELSE.getState());
				b = updateById(order);
				if (!b) {
					throw new BaseExecuteException("修改订单失败");
				}
				order = getById(order.getOrderId());
				float point = order.getOrderAmount();
				customer.setCustomerPoint(customer.getCustomerPoint() + (int) point);
				if (customer.getCustomerPoint() > 1000
						&& customer.getCustomerVip() == CustomerVipEum.NOTVIP.getState()) {
					customer.setCustomerVip(CustomerVipEum.VIP.getState());
				}
				int x = customerMapper.updateById(customer);
				if (x <= 0) {
					throw new BaseExecuteException("积分增加失败");
				}
				DinTable dinTable = new DinTable();
				dinTable.setDinTableId(order.getDinTableId());
				dinTable.setDinTableState(DinTableStateEum.IDLE.getState());
				int e = dinTableMapper.updateById(dinTable);
				if (e < 0) {
					throw new BaseExecuteException("桌台状态更新失败");
				}
				return new BaseExecution<Order>(ExecuteStateEum.SUCCESS, order);
			} else {
				b = updateById(order);
				if (!b) {
					throw new BaseExecuteException("修改订单失败");
				}
				return new BaseExecution<Order>(ExecuteStateEum.SUCCESS);
			}
		} catch (BaseExecuteException e) {
			return new BaseExecution<Order>(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
			DinTable dinTable = new DinTable();
			dinTable.setDinTableId(order.getDinTableId());
			dinTable.setDinTableState(DinTableStateEum.USING.getState());
			int e = dinTableMapper.updateById(dinTable);
			if (e < 0) {
				throw new BaseExecuteException("桌台状态更新失败");
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
		// 统计近7天营业额
		try {
			List<Object> l = new ArrayList<>();
			Date end_date = new Date();

			Date start_date = DateUtil.beginOfDay(DateUtil.offsetDay(end_date, -6));
			QueryWrapper<Order> q = new QueryWrapper<Order>();
			q.eq("order_state", OrderStateEum.COMPLETE.getState());
			q.le("pay_time", end_date);
			q.ge("pay_time", start_date);
			List<Order> orders = orderMapper.selectList(q);
			int effectiveOrder = 0;
			float revenueDay[] = new float[(int) DateUtil.betweenDay(start_date, end_date, true) + 1];
			for (Order o : orders) {
				int offset = (int) DateUtil.betweenDay(start_date, o.getPayTime(), true);
				revenueDay[offset] += o.getOrderAmount();
				if (offset == revenueDay.length - 1) {
					effectiveOrder++;
				}
			}
			l.add(revenueDay);
			l.add(effectiveOrder);
			return new BaseExecution<Object>(ExecuteStateEum.SUCCESS, l);
		} catch (Exception e) {
			return new BaseExecution<Object>(e.getMessage());
		}

	}

	@Override
	public BaseExecution<Order> selectOrder(Order order, Integer pageSize, Integer pageIndex, String CustomerNickname,
			Date startTime, Date endTime, Double maxAmount, Double minAount) {
		try {
			QueryWrapper<Order> q = new QueryWrapper<Order>();
			q.ge(startTime != null, "pay_time", startTime);
			q.le(endTime != null, "pay_time", endTime);
			q.le(maxAmount != null, "order_amount", maxAmount);
			q.ge(minAount != null, "order_amount", minAount);
			q.eq(order.getOrderId() != null, "order_id", order.getOrderId());
			q.eq(order.getStaffId() != null, "staff_id", order.getStaffId());
			q.eq(order.getDinTableId() != null, "din_table_id", order.getDinTableId());
			q.eq(order.getOrderState() != null, "order_state", order.getOrderState());
			q.like(CustomerNickname != null, "c.customer_nickname", CustomerNickname);
			List<Order> orderList = null;
			BaseExecution<Order> be = new BaseExecution<Order>();
			if (pageIndex > 0 || pageSize > 0) {
				q.eq("1", 1);
				Long count = orderMapper.countOrder(q);
				be.setCount(count);
				orderList = orderMapper.getOrderPage(q,(pageIndex-1)*pageSize,pageSize);
			} else {
				q.eq("1", 1);
				orderList = orderMapper.getOrder(q);
			}
			for (Order o : orderList) {
				QueryWrapper<OrderFood> queryWrapper = new QueryWrapper<OrderFood>();
				queryWrapper.eq("order_id", o.getOrderId());
				List<OrderFood> orderFoods = orderFoodMapper.getOrderFoodList(queryWrapper);
				o.setOrderFoodList(orderFoods);
			}
			be.setEum(ExecuteStateEum.SUCCESS);
			be.setTList(orderList);
			return be;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new BaseExecution<Order>("未知错误");
		}
	}

	@Override
	public BaseExecution<Order> selectOrder() {
		try {
			List<Order> l = orderMapper.getOrderEvaluation();
			return new BaseExecution<Order>(ExecuteStateEum.SUCCESS, l);
		} catch (Exception e) {
			return new BaseExecution<Order>("未知错误,请联系管理员");
		}
	}

}
