/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:27:57
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-02 09:35:08
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\StaffController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.easyorder.config.shiro.JwtUtil;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Staff;
import com.easyorder.service.PermissionService;
import com.easyorder.service.StaffService;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Resource
    StaffService staffService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private PermissionService permissionService;

    @Value("${easyorder.jwt.cache-expire}")
    private int cacheExpire;

    Gson gson=new Gson();
    
    @PostMapping("/select")
    public RBody selectStaff(@RequestBody Staff staff)
    {
        RBody rBody=new RBody();
        BaseExecution<Staff> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.staffService.selectStaffList(staff);
            rBody=RBody.ok().data(baseExecution.getTList());
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/update")
    public RBody updateStaff(@RequestBody Staff staff)
    {
        RBody rBody=new RBody();
        //BaseExecution<Staff> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.staffService.updateStaff(staff);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/register")
    public RBody registerStaff(@RequestBody Staff staff)
    {
        
        RBody rBody=new RBody();
        BaseExecution<Staff> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.staffService.insertStaff(staff);
            //创建jwt
            Long staffId = baseExecution.getTemp().getStaffId();
            String token = jwtUtil.createToken(staffId);
            // TODO 获取权限列表 注释
            // redisTemplate.opsForValue().set(token,staffId+"",cacheExpire, TimeUnit.DAYS);
            // List<String> permissionList = permissionService.getPermissionListById(staffId).getTList();
            // Set<String> permsSet = userService.searchUserPermissions(id);
            // Set<String> permsSet = new HashSet<>(permissionList);
            // rBody=RBody.ok("注册成功").data((baseExecution.getTemp().getStaffId())).token(token).put("permission",permsSet);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.getMessage());
        }
        return rBody;
    }

    @PostMapping("/login")
    public RBody loginStaff(HttpServletRequest request)
    {
        RBody rBody=new RBody();
        try {
            this.staffService.login(request);
            rBody=RBody.ok("登陆成功");
        } catch (Exception e) {
            rBody=RBody.error(e.getMessage());
        }
        return rBody;
    }



    // @PostMapping("/delete")
    // public RBody deleteStaff(@RequestBody Staff staff)
    // {
    //     RBody rBody=new RBody();
    //     BaseExecution<Staff> baseExecution=new BaseExecution<>();
    //     try {
    //         baseExecution=this.staffService.deleteStaff(staff);
    //         rBody=RBody.ok();
    //     } catch (Exception e) {
    //         rBody=RBody.error(e.toString());
    //     }
    //     return rBody;
    // }
}
