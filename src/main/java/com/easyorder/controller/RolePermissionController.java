/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 08:54:19
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 16:29:11
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\RolePermissionController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-29 16:52:59
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 14:57:24
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\RolePermissionController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.RolePermission;
import com.easyorder.service.RolePermissionService;

import com.easyorder.util.RBody;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {
    @Resource
    RolePermissionService rolePermissionService;

    @PostMapping("/select")
    public RBody selectRolePermission(@RequestBody RolePermission rolePermission){
        RBody rbody = new RBody();
        BaseExecution<RolePermission> baseExecution = new BaseExecution<>();
        try{
            baseExecution = this.rolePermissionService.selectRolePermissionList(rolePermission);
            rbody=RBody.ok().data(baseExecution.getTList());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }


    @PostMapping("/insert")
    public RBody insertRolePermission(@RequestBody RolePermission rolePermission){
        RBody rbody = new RBody();
        // = new BaseExecution<>();
        try{
            //baseExecution = 
            this.rolePermissionService.insertRolePermission(rolePermission);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/update")
    public RBody updateRolePermission(HttpServletRequest request){
        
        RBody rbody = new RBody();
        //BaseExecution<RolePermission> baseExecution = new BaseExecution<>();
        try{
            //baseExecution = 
            this.rolePermissionService.updateRolePermission(request);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/delete")
    public RBody deleteRolePermission(@RequestBody RolePermission rolePermission){
        RBody rbody = new RBody();
        //BaseExecution<RolePermission> baseExecution = new BaseExecution<>();
        try{
            //baseExecution = 
            this.rolePermissionService.deleteRolePermission(rolePermission);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

}
