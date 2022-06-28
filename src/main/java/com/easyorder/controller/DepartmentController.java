/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-25 11:23:19
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 14:30:03
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\DepartmentController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.entity.Department;
import com.easyorder.service.DeparmentService;

import com.google.gson.Gson;
@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
public class DepartmentController {
    @Resource
    DeparmentService deparmentService;
    Gson gson=new Gson();
    // @GetMapping("/selectDepartment")
    // public String selectDepartment()
    // {
    //     return gson.toJson(deparmentService.getDepartmentList());
    // }

    @PostMapping("/updateDepartment")
    public String updateDepartment(@RequestBody Department department)
    {
        
        deparmentService.updateDepartment(department);
         
        return gson.toJson(deparmentService.getDepartmentList(department));
    }
    
    @PostMapping("/selectDepartment")
    public String selectDepartment(@RequestBody Department department)
    {
        
        return gson.toJson(deparmentService.getDepartmentList(department));
    }

    @PostMapping("/insertDepartment")
    public String insertDepartment(@RequestBody Department department)
    {
        deparmentService.insertDepartment(department);
        Department department2=new Department();
        return gson.toJson(deparmentService.getDepartmentList(department2));
    }

    @PostMapping("/deleteDepartment")
    public String deleteDepartment(@RequestBody Department department)
    {
        deparmentService.deleteDepartment(department);
        Department department2=new Department();
        return gson.toJson(deparmentService.getDepartmentList(department2));
    }


}
