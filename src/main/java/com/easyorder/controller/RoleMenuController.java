package com.easyorder.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.RoleMenu;
import com.easyorder.service.RoleMenuService;
import com.easyorder.util.RBody;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    @Resource
    RoleMenuService roleMenuService;

    @PostMapping("/select")
    public RBody selectRoleMenu(@RequestBody RoleMenu roleMenu){
        RBody rbody = new RBody();
        BaseExecution<RoleMenu> baseExecution = new BaseExecution<>();
        try{
            baseExecution = this.roleMenuService.selectRoleMenuList(roleMenu);
            rbody=RBody.ok().data(baseExecution.getTList());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }


    @PostMapping("/insert")
    public RBody insertRoleMenu(@RequestBody RoleMenu roleMenu){
        RBody rbody = new RBody();
        // = new BaseExecution<>();
        try{
            //baseExecution = 
            this.roleMenuService.insertRoleMenu(roleMenu);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/update")
    public RBody updateRoleMenu(HttpServletRequest request){
        
        RBody rbody = new RBody();
        //BaseExecution<RoleMenu> baseExecution = new BaseExecution<>();
        try{
            //baseExecution = 
            this.roleMenuService.updateRoleMenu(request);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/delete")
    public RBody deleteRoleMenu(@RequestBody RoleMenu roleMenu){
        RBody rbody = new RBody();
        //BaseExecution<RolePermission> baseExecution = new BaseExecution<>();
        try{
            //baseExecution = 
            this.roleMenuService.deleteRoleMenu(roleMenu);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }
}
