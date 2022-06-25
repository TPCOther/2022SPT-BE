package com.easyorder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.service.CustomerService;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
public class Controller {
	@Resource
	CustomerService cs;
	
    @GetMapping("/hello")
    public Map<String,Object> hello(String hello,String l) {
    	Map<String, Object> map =new HashMap<>();
    	List<String> x=new ArrayList<String>();
    	x.add("testList1");
    	x.add("testList2");
        map.put("test1","test1");
        map.put("testList",x);
        map.put("test2",cs.findCustomerInfoById(1));
    	return map;
    }
    

}

