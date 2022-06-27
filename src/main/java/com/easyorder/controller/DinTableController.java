package com.easyorder.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.entity.DinTable;
import com.easyorder.service.DinTableService;
import com.google.gson.Gson;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/table")
public class DinTableController {

    private Gson gson = new Gson();
    
    @Resource
    private DinTableService dinTableService;
    @PostMapping("/insert")
    public String tableInsert(@RequestBody DinTable insertTable){
        this.dinTableService.insertDinTable(insertTable);
        return gson.toJson(this.dinTableService.getDinTableList());
    }
}
