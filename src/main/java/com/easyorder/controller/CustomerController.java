package com.easyorder.controller;


import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.config.shiro.JwtUtil;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Customer;
import com.easyorder.enums.CustomerVipEum;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.CustomerService;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

import cn.hutool.json.JSONObject;

@CrossOrigin(origins = { "*", "null" }) // 用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Resource
	private CustomerService customerService;
	@Resource
	private JwtUtil jwtUtil;
	@Resource
	private Gson gson;
	@Resource
	private RedisTemplate redisTemplate;

	@Value("${easyorder.jwt.cache-expire}")
	private int caheExpire;

	private void saveCaheToken(String token, Long customerId) {
		redisTemplate.opsForValue().set(token, customerId + "", caheExpire, TimeUnit.DAYS);
	}

	@GetMapping("/login")
	public RBody login(HttpServletRequest request) {
		String code = HttpServletRequestUtil.getString(request, "code");
		Long tableId = HttpServletRequestUtil.getLong(request, "tableId");
		BaseExecution<Customer> be = customerService.login(code);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			Long id = be.getTemp().getCustomerId();

			String token = jwtUtil.createToken(-1l);
			saveCaheToken(token, id);
			return RBody.ok(be.getEum().getStateInfo()).put("token", token).data(be.getTemp());
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	@PostMapping("/select")
	public RBody customerSelect(@RequestBody Customer selectTable) {
		RBody rbody = new RBody();
		BaseExecution<Customer> be = new BaseExecution<Customer>();
		try {
			be = this.customerService.selectCustomerList(selectTable);
			rbody = RBody.ok().data(be.getTList());
		} catch (Exception e) {
			rbody = RBody.error(e.toString());
		}
		return rbody;
	}

	@PostMapping("/insert")
	public RBody tableInsert(@RequestBody JSONObject json,HttpServletRequest request) {
		RBody rbody = new RBody();
		// 测试
		String code = json.getStr("code");
		json.remove("code");
		Customer insertTable;
		try {
			insertTable = gson.fromJson(json.toString(), Customer.class);
		} catch (Exception e) {
			return RBody.error("未知错误,请联系管理员");
		}
		
		insertTable.setCustomerVip(CustomerVipEum.NOTVIP.getState());
		insertTable.setCustomerPoint(0);
		BaseExecution<Customer> be = new BaseExecution<Customer>();
		try {
			be = this.customerService.insertCustomer(insertTable, code);
			Long id = be.getTemp().getCustomerId();
			String token = jwtUtil.createToken(-1l);
			saveCaheToken(token, id);
			rbody = RBody.ok().data(be.getTemp()).put("token", token);
		} catch (Exception e) {
			rbody = RBody.error(e.toString());
		}
		return rbody;
	}

	@PostMapping("/update")
	public RBody tableUpdate(@RequestBody Customer insertTable) {
		RBody rbody = new RBody();
		try {
			this.customerService.updateCustomer(insertTable);
			rbody = RBody.ok();
		} catch (Exception e) {
			rbody = RBody.error(e.toString());
		}
		return rbody;
	}

	@PostMapping("/delete")
	public RBody tableDelete(@RequestBody Customer insertTable) {
		RBody rbody = new RBody();
		try {
			this.customerService.deleteCustomer(insertTable);
			rbody = RBody.ok();
		} catch (Exception e) {
			rbody = RBody.error(e.toString());
		}
		return rbody;
	}
}
