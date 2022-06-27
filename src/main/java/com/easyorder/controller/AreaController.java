package com.easyorder.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.entity.Area;
import com.easyorder.service.AreaService;
import com.google.gson.Gson;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/area")
public class AreaController {

    private Gson gson = new Gson();
    
    @Resource
    private AreaService areaService;
    
    @PostMapping("/insert")
    public String tableInsert(@RequestBody Area insertArea){
        this.areaService.insertArea(insertArea);
        return gson.toJson(this.areaService.selectAreaList());
    }
}
