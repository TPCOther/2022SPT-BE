package com.easyorder.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/select")
    public RBody dinTableSelect(Long dinTableId, Long areaId, Integer dinTableState, Integer dinTableCapacity){
        RBody rbody = new RBody();
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        try{
            baseExecution = this.dinTableService.selectDinTableList(dinTableId, areaId,dinTableState,dinTableCapacity);
            rbody=RBody.ok().data(baseExecution.getTList());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }


    @PostMapping("/insert")
    public RBody tableInsert(@RequestBody DinTable insertTable){
        RBody rbody = new RBody();
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        try{
            baseExecution = this.dinTableService.insertDinTable(insertTable);
            rbody=RBody.ok().data(baseExecution.getTemp().getDinTableId());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/update")
    public RBody tableUpdate(@RequestBody DinTable insertTable){
        RBody rbody = new RBody();
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        try{
            baseExecution = this.dinTableService.updateDinTable(insertTable);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/delete")
    public RBody tableDelete(@RequestBody DinTable insertTable){
        RBody rbody = new RBody();
        BaseExecution<DinTable> baseExecution = new BaseExecution<DinTable>();
        try{
            baseExecution = this.dinTableService.deleteDinTable(insertTable);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

}
