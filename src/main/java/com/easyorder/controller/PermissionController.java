/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 16:43:30
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 17:14:52
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\PermissionController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import javax.annotation.Resource;

import com.easyorder.entity.Permission;
import com.easyorder.service.PermissionService;
import com.google.gson.Gson;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
public class PermissionController {
    @Resource
    PermissionService permissionService;
    Gson gson=new Gson();
    @PostMapping("/selectPermission")
    public String selectPermission(@RequestBody Permission permission)
    {
        return gson.toJson(permissionService.getPermissionList(permission));
    }

    @PostMapping("/updatePermission")
    public String updatePermission(@RequestBody Permission permission)
    {
        
        permissionService.updatePermission(permission);
        Permission permission2=new Permission();
        return gson.toJson(permissionService.getPermissionList(permission2));
    }

    @PostMapping("/insertPermission")
    public String insertPermission(@RequestBody Permission permission)
    {
        
        permissionService.insertPermission(permission);
        Permission permission2=new Permission();
        return gson.toJson(permissionService.getPermissionList(permission2));
    }

    @PostMapping("/deletePermission")
    public String deleteHeadline(@RequestBody Permission permission)
    {
        permissionService.deletePermission(permission);
        Permission permission2=new Permission();
        return gson.toJson(permissionService.getPermissionList(permission2));
    }
}
