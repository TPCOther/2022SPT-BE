/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 16:43:30
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 15:50:05
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\PermissionController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import javax.annotation.Resource;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Permission;
import com.easyorder.service.PermissionService;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    PermissionService permissionService;
    Gson gson=new Gson();
    @PostMapping("/select")
    public RBody selectPermission(@RequestBody Permission permission)
    {
        RBody rBody=new RBody();
        BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.permissionService.selectPermissionList(permission);
            rBody=RBody.ok().data(baseExecution.getTList());
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }



    @PostMapping("/update")
    public RBody updatePermission(@RequestBody Permission permission)
    {
        RBody rBody=new RBody();
        //BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.permissionService.updatePermission(permission);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/insert")
    public RBody insertPermission(@RequestBody Permission permission)
    {
        RBody rBody=new RBody();
        BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.permissionService.insertPermission(permission);
            rBody=RBody.ok().data(baseExecution.getTemp().getPermissionId());;
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/delete")
    public RBody deletePermission(@RequestBody Permission permission)
    {
        RBody rBody=new RBody();
        //BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.permissionService.deletePermission(permission);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }
}
