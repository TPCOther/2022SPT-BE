package com.easyorder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyorder.entity.DinTable;
import com.easyorder.mapper.DinTableMapper;
import com.easyorder.service.DinTableService;
import com.google.gson.Gson;

@Service
public class DinTableServiceImpl implements DinTableService {
    
    private Gson gson = new Gson();
    
    @Autowired
    private DinTableMapper dinTableMapper;
    
    @Override
    public String getDinTableList(){
        List<DinTable> tableList = dinTableMapper.selectList(null);
        return gson.toJson(tableList);
    }

    @Override
    public void insertDinTable(DinTable insertTable){
        dinTableMapper.insert(insertTable);
    }

    
}
