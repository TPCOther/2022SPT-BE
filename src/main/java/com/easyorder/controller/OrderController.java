package com.easyorder.controller;

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
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.OrderService;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;

@CrossOrigin(origins = { "*", "null" }) // 用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/order")
public class OrderController {
	@Resource
	OrderService orderService;

	/**
	 * 查询订单信息 管理员
	 * @param request
	 * @param order
	 * @return
	 */
	@GetMapping("/getorderlist")
	@ResponseBody
	public RBody getOrderList(HttpServletRequest request, @RequestBody Order order) {
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
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
	
	//后厨人员
	
	//顾客
	
	//服务员
	
	/**
	 * 添加订单
	 * @param order
	 * @param request
	 * @return
	 */
	@PostMapping("/insertorder")
	@ResponseBody
	public RBody insertOrder(@RequestBody Order order,HttpServletRequest request) {
		return null;
	}
	//服务员
	//顾客
	
	
	/**
	 * 更新订单
	 */
	
	//后厨人员
	//顾客
	//服务员
	
}
