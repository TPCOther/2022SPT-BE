/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 15:48:00
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 15:52:16
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\ControllerMenuController.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.controller;

import javax.annotation.Resource;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.ControllerMenu;
import com.easyorder.service.ControllerMenuService;
import com.easyorder.util.RBody;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/controllerMenu")
public class ControllerMenuController {
    @Resource
    ControllerMenuService controllerMenuService;

    @PostMapping("/select")
    @RequiresPermissions("controllerMenu")
    public RBody selectControllerMenu(@RequestBody ControllerMenu controllerMenu)
    {
        RBody rBody=new RBody();
        BaseExecution<ControllerMenu> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.controllerMenuService.selectControllerMenuList(controllerMenu);
            rBody=RBody.ok().data(baseExecution.getTList());
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }


    @PostMapping("/update")
    @RequiresPermissions("controllerMenu")
    public RBody updateControllerMenu(@RequestBody ControllerMenu controllerMenu)
    {
        RBody rBody=new RBody();
        //BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.controllerMenuService.updateControllerMenu(controllerMenu);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/insert")
    @RequiresPermissions("controllerMenu")
    public RBody insertControllerMenu(@RequestBody ControllerMenu controllerMenu)
    {
        RBody rBody=new RBody();
        BaseExecution<ControllerMenu> baseExecution=new BaseExecution<>();
        try {
            baseExecution=this.controllerMenuService.insertControllerMenu(controllerMenu);
            rBody=RBody.ok().data(baseExecution.getTemp().getControllerMenuId());;
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }

    @PostMapping("/delete")
    @RequiresPermissions("controllerMenu")
    public RBody deleteControllerMenu(@RequestBody ControllerMenu controllerMenu)
    {
        RBody rBody=new RBody();
        //BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            //baseExecution=
            this.controllerMenuService.deleteControllerMenu(controllerMenu);
            rBody=RBody.ok();
        } catch (Exception e) {
            rBody=RBody.error(e.toString());
        }
        return rBody;
    }
}
