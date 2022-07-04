/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:27:57
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-02 16:13:24
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\StaffController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Staff;
import com.easyorder.service.StaffService;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/staff")
public class StaffController {
    @Resource
    StaffService staffService;
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

    @PostMapping("/insert")
    public RBody insertStaff(@RequestBody Staff staff)
    {
        
        RBody rBody=new RBody();
        BaseExecution<Staff> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.staffService.insertStaff(staff);
            rBody=RBody.ok().data((baseExecution.getTemp().getStaffId()));
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
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.getMessage());
        }
        return rBody;
    }


    // @GetMapping("/test")
    // public RBody test(Long id)
    // {
    //     BaseExecution<Role> baseExecution=new BaseExecution<>();
    //     RBody rBody=new RBody();
    //     try {
    //         baseExecution=this.staffService.test(1l);
    //         rBody=RBody.ok().data((baseExecution.getTList()));
    //     } catch (Exception e) {
    //         rBody=RBody.error(e.getMessage());
    //     }
    //     return rBody;
    // }

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
