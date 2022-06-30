/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:54:14
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 16:27:00
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\RoleController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import javax.annotation.Resource;

import com.easyorder.entity.Role;
import com.easyorder.service.RoleService;
import com.google.gson.Gson;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
public class RoleController {

    @Resource
    RoleService roleService;
    Gson gson=new Gson();
    
    @PostMapping("/selectRole")
    public String selectRole(@RequestBody Role role)
    {
        return gson.toJson(roleService.getRoleList(role));
    }

    @PostMapping("/updateRole")
    public String updateRole(@RequestBody Role role)
    {
        
        roleService.updateRole(role);
        Role role2=new Role();
        return gson.toJson(roleService.getRoleList(role2));
    }

    @PostMapping("/insertRole")
    public String insertRole(@RequestBody Role role)
    {
        
        roleService.insertRole(role);
        Role role2=new Role();
        return gson.toJson(roleService.getRoleList(role2));
    }

    // @PostMapping("/deleteRole")
    // public String deleteRole(@RequestBody Role role)
    // {
    //     roleService.deleteRole(role);
    //     Role role2=new Role();
    //     return gson.toJson(roleService.getRoleList(role2));
    // }
}
