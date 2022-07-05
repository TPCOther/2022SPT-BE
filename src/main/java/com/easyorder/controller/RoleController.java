/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:54:14
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 08:56:49
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\RoleController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import javax.annotation.Resource;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Role;
import com.easyorder.service.RoleService;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    RoleService roleService;
    Gson gson=new Gson();
    
    @PostMapping("/select")
    @RequiresPermissions("role:select")
    public RBody selectRole(@RequestBody Role role)
    {
        RBody rBody=new RBody();
        BaseExecution<Role> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.roleService.selectRoleList(role);
            rBody=RBody.ok().data(baseExecution.getTList());
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/update")
    @RequiresPermissions("role:update")
    public RBody updateRole(@RequestBody Role role)
    {
        RBody rBody=new RBody();
        //BaseExecution<Role> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.roleService.updateRole(role);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/insert")
    @RequiresPermissions("role:insert")
    public RBody insertRole(@RequestBody Role role)
    {
        RBody rBody=new RBody();
        BaseExecution<Role> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.roleService.insertRole(role);
            rBody=RBody.ok().data((baseExecution.getTemp().getRoleId()));
        } catch (Exception e) {
            rBody=RBody.error(e.getMessage());
        }
        return rBody;
    }

    @PostMapping("/delete")
    @RequiresPermissions("role:delete")
    public RBody deleteRole(@RequestBody Role role)
    {
        RBody rBody=new RBody();
        //BaseExecution<Role> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.roleService.deleteRole(role);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }
}
