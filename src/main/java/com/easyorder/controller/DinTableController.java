package com.easyorder.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.DinTable;
import com.easyorder.service.DinTableService;
import com.easyorder.util.RBody;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/table")
public class DinTableController {
    
    @Resource
    private DinTableService dinTableService;

    @PostMapping("/select")
    @RequiresPermissions("dinTable:select")
    // @RequiresRoles(value={"admin","customer"},logical = Logical.OR)
    public RBody dinTableSelect(@RequestBody DinTable selectDinTable){
        RBody rbody = new RBody();
        BaseExecution<DinTable> be = new BaseExecution<DinTable>();
        try{
            be = this.dinTableService.selectDinTableList(selectDinTable);
            rbody=RBody.ok().data(be.getTList());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }


    @PostMapping("/insert")
    @RequiresPermissions("dinTable:insert")
    public RBody dinTableInsert(@RequestBody DinTable insertTable){
        RBody rbody = new RBody();
        BaseExecution<DinTable> be = new BaseExecution<DinTable>();
        try{
            be = this.dinTableService.insertDinTable(insertTable);
            rbody=RBody.ok().data(be.getTemp());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/update")
    @RequiresPermissions("dinTable:update")
    public RBody dinTableUpdate(@RequestBody DinTable insertTable){
        RBody rbody = new RBody();
        try{
            this.dinTableService.updateDinTable(insertTable);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/delete")
    @RequiresPermissions("dinTable:delete")
    public RBody dinTableDelete(@RequestBody DinTable insertTable){
        RBody rbody = new RBody();
        try{
            this.dinTableService.deleteDinTable(insertTable);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

}
