package com.easyorder.controller;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/showDepartment")
    public String showDepartment()
    {
        return gson.toJson(deparmentService.getDepartmentList());
    }

    @PostMapping("/updateDepartment")
    public String updateDepartment(@RequestBody Department department)
    {
        
        deparmentService.updateDepartment(department);
        return gson.toJson(deparmentService.getDepartmentList());
    }
    
}
