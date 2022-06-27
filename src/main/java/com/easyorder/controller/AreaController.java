package com.easyorder.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.entity.Area;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.AreaService;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/area")
public class AreaController {

    private Gson gson = new Gson();
    
    @Resource
    private AreaService areaService;
    
    @GetMapping("/select")
    public RBody areaSelect(Long areaId ,String areaName){
        RBody rbody = new RBody();
        try{
            List<Area> areaList = this.areaService.selectAreaList(areaId, areaName);
            if(areaList.size() == 0){
                rbody=RBody.executeState(ExecuteStateEum.EMPTY);
            }else{
                rbody=RBody.ok().data(areaList);
            }
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/insert")
    public RBody tableInsert(@RequestBody Area insertArea){
        RBody rbody = new RBody();
        try{
            Long insertId = this.areaService.insertArea(insertArea);
            rbody=RBody.ok().data(insertId);
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }
}
