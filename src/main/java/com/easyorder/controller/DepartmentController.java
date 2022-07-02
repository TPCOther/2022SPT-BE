/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-25 11:23:19
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 08:56:07
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\DepartmentController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;


import javax.annotation.Resource;


import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Department;
import com.easyorder.service.DeparmentService;
import com.easyorder.util.RBody;
import com.google.gson.Gson;
@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    DeparmentService departmentService;
    Gson gson=new Gson();

    @PostMapping("/update")
    public RBody updateDepartment(@RequestBody Department department)
    {
        RBody rBody=new RBody();
        //BaseExecution<Department> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.departmentService.updateDepartment(department);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }
    


    @PostMapping("/select")
    public RBody selectDepartment(@RequestBody Department department)
    {
        RBody rBody=new RBody();
        BaseExecution<Department> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.departmentService.selectDepartmentList(department);
            rBody=RBody.ok().data(baseExecution.getTList());
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/insert")
    public RBody insertDepartment(@RequestBody Department department)
    {
        RBody rBody=new RBody();
        BaseExecution<Department> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.departmentService.insertDepartment(department);
            rBody=RBody.ok().data((baseExecution.getTemp().getDepartmentId()));
        } catch (Exception e) {
            rBody=RBody.error(e.getMessage());
        }
        return rBody;
    }

    @PostMapping("/delete")
    public RBody deleteDepartment(@RequestBody Department department)
    {
        RBody rBody=new RBody();
        //BaseExecution<Department> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.departmentService.deleteDepartment(department);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }


}
