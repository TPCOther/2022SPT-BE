package com.easyorder.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Order;
import com.easyorder.entity.OrderFood;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.enums.OrderStateEum;
import com.easyorder.service.FoodCategoryService;
import com.easyorder.service.OrderFoodService;
import com.easyorder.service.OrderService;
import com.easyorder.util.BaseExecuteException;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

import cn.hutool.json.JSONObject;

@CrossOrigin(origins = { "*", "null" }) // 用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/order")
public class OrderController {
	@Resource
	OrderService orderService;

	@Resource
	OrderFoodService orderFoodService;

	@Resource
	FoodCategoryService foodCategoryService;
	@Resource
	Gson gson;

	// 管理员 后厨人员 服务员 餐桌
	/**
	 * 查询订单信息
	 * 
	 * @param request
	 * @param order
	 * @return
	 */
	@GetMapping("/getorderlist")
	@ResponseBody
	public RBody getOrderList(HttpServletRequest request) {
		Order order = new Order();
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

		order.setCustomerId(HttpServletRequestUtil.getLong(request, "customerId"));
		order.setDinTableId(HttpServletRequestUtil.getLong(request, "dinTableId"));
		order.setOrderId(HttpServletRequestUtil.getLong(request, "orderId"));
		order.setOrderState(HttpServletRequestUtil.getInt(request, "orderState"));
		order.setStaffId(HttpServletRequestUtil.getLong(request, "staffId"));

		if (pageIndex == null || pageSize == null) {
			pageIndex = -1;
			pageSize = -1;
		}
		BaseExecution<Order> be = orderService.selectOrder(order, pageSize, pageIndex);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.data(be.getTList());
			if (be.getCount() != null)
				rBody.put("count", be.getCount());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	// 顾客
	@GetMapping("/getorderlistbycustomer")
	@ResponseBody
	public RBody getOrderListByCustomer(HttpServletRequest request) {
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Long customerId = HttpServletRequestUtil.getLong(request, "customerId");
		Order order = new Order();
		order.setCustomerId(customerId);
		if (pageIndex == null || pageSize == null) {
			pageIndex = -1;
			pageSize = -1;
		}
		BaseExecution<Order> be = orderService.selectOrder(order, pageSize, pageIndex);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.data(be.getTList());
			if (be.getCount() != null)
				rBody.put("count", be.getCount());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	// 餐桌 查询最近一次消费记录
	@GetMapping("/getlastorderbydintable")
	@ResponseBody
	public RBody getLastOrderByDinTable(HttpServletRequest request) {
		Order order = new Order();
		order.setDinTableId(HttpServletRequestUtil.getLong(request, "dinTableId"));
		BaseExecution<Order> be = orderService.selectOrder(order, 1, 1);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.data(be.getTList().get(0));
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	/**
	 * 添加订单顾客
	 * 
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("/insertorder")
	@ResponseBody
	public RBody insertOrder(@RequestBody Order order, HttpServletRequest request) {
		if (order.getOrderFoodList() == null || order.getOrderFoodList().size() == 0||order.getDinTableId()==null||order.getDinTableId()<=0)
			return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
		Format f = new SimpleDateFormat("yyyyMMddhhmmss");
		Random r = new Random();
		String orderId = f.format(new Date());
		orderId += r.nextInt(10);
		order.setOrderId(Long.valueOf(orderId));
		order.setOrderState(OrderStateEum.PENDING.getState());
		BaseExecution<Order> be = orderService.insertOrder(order);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.put("orderId", be.getTemp().getOrderId());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	/**
	 * 更新订单的基本信息和菜品列表
	 */
	@PostMapping("/updateorder")
	@ResponseBody
	public RBody updateOrder(@RequestBody Order order, HttpServletRequest request) {
		if (order != null && order.getOrderId() != null && order.getOrderId() > 0) {
			BaseExecution<Order> be = orderService.updateOrder(order);
			if (be.getEum() == ExecuteStateEum.SUCCESS) {
				return RBody.ok(be.getEum().getStateInfo());
			} else {
				return RBody.error(be.getStateInfo());
			}
		} else {
			return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
		}
	}

	/**
	 * 更新菜品列表的状态
	 */
	@PostMapping("/updateorderfoodState")
	@ResponseBody
	public RBody updateOrderFoodState(@RequestBody List<OrderFood> orderFoodList) {
		if (orderFoodList != null && orderFoodList.size() > 0) {
			try {
				boolean b = orderFoodService.updateBatchByMultiId(orderFoodList);
				if (!b) {
					throw new BaseExecuteException("更新失败");
				}
				return RBody.ok(ExecuteStateEum.SUCCESS.getStateInfo());
			} catch (Exception e) {
				return RBody.error(e.getMessage());
			}
		} else
			return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
	}

	/**
	 * 订单统计
	 */
	@GetMapping("/statisticsorder")
	@ResponseBody
	public RBody statisticsOrder() {
		BaseExecution<Object> be2 = orderService.statistiscOrder();
		BaseExecution<Long> be = foodCategoryService.selectCount();
		if (be.getEum() == ExecuteStateEum.SUCCESS && be2.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.put("foodCount", be.getTList().get(0)).put("foodCategoryCount", be.getTList().get(1)).put("VIPCount",
					be.getTList().get(2));
			if (be2.getTList() != null && be2.getTList().size() > 0) {
				rBody.put("effectiveOrder", be2.getTList().get(1));
				rBody.put("revenueDay", be2.getTList().get(0));
			}
			return rBody;
		} else {
			return RBody.error(be.getStateInfo() + "\n" + be2.getStateInfo());
		}
	}

	@PostMapping("/getsalesitu")
	@ResponseBody
	public RBody getSaleSitu(@RequestBody JSONObject jsonObject) {
		Date startDate = new Date();
		Date endDate = new Date();
		Long btime = jsonObject.getLong("btime");
		Long etime = jsonObject.getLong("etime");
		startDate.setTime(btime);
		endDate.setTime(etime);
		BaseExecution<Object> be = orderFoodService.statisticsOrderFood(startDate, endDate);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.put("saleData", be.getTList().get(0));
			rBody.put("dishData", be.getTList().get(1));
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}
}
