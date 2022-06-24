package com.easyorder.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.entity.DinTable;
import com.easyorder.service.DinTableService;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
public class DinTableController {
    @Resource
    private DinTableService dinTableService;
    @PostMapping("/tableinsert")
    public String tableInsert(@RequestBody DinTable insertTable){
        this.dinTableService.insertDinTable(insertTable);
        return this.dinTableService.getDinTableList();
    }
}
