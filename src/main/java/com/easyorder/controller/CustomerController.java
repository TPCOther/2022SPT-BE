package com.easyorder.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.entity.Customer;
import com.easyorder.service.CustomerService;
import com.google.gson.Gson;

@CrossOrigin(origins = { "*", "null" }) // 用于跨域请求，*代表允许响应所有的跨域请求
//@SuppressWarnings("all") 用于忽略报错
@RestController
public class CustomerController {
	@Resource
	private CustomerService cs;
	@Resource
	private Gson gson;

	@GetMapping("/FindCustomerById")
	public String findCustomerInfoByid(int id) {
		return gson.toJson(cs.findCustomerInfoById(id));
	}
//	@PostMapping("/Sign")
//	public String insertCustomer() {
//		
//	}
}
