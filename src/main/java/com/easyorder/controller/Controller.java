/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-24 16:47:14
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-25 11:24:22
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\Controller.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.config.shiro.JwtUtil;
import com.easyorder.service.CustomerService;
import com.easyorder.util.RBody;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
public class Controller {
	@Resource
    private JwtUtil jwtUtil;
	RBody rbody= new RBody();

	@Autowired
    private RedisTemplate redisTemplate;
	@Value("${easyorder.jwt.cache-expire}")
    private int cacheExpire;

    @GetMapping("/hello")
    public RBody hello() {
		Long id=1L;
		String res=jwtUtil.createToken(id);
    	rbody=RBody.ok().data(res);
    	return rbody;
    }
	@GetMapping("/test")
    public RBody test() {
		Long id=1L;
		String token="eyJ0eX	AiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTcxNjUzOTQsInN0YWZmSWQiOjF9.w1f39zf0IoIecM2_8qjN_LgStYFVj9JIOi1iUtmhsFI";
		// String token=jwtUtil.createToken(id);
		// redisTemplate.opsForValue().set(token,"2",cacheExpire, TimeUnit.DAYS);
		if(redisTemplate.hasKey(token)){
			redisTemplate.delete(token);
			Long staffId = jwtUtil.getStaffId(token);
			token = jwtUtil.createToken(staffId);
			redisTemplate.opsForValue().set(token,staffId+"2",cacheExpire, TimeUnit.DAYS);
		}
		rbody=RBody.ok().data(token);
    	return rbody;
    }
    
}
