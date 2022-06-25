package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easyorder.entity.DinTable;
import com.easyorder.mapper.DinTableMapper;
import com.easyorder.service.DinTableService;

@Service
public class DinTableServiceImpl implements DinTableService {
    
    @Resource
    private DinTableMapper dinTableMapper;
    
    @Override
    public List<DinTable> getDinTableList(){
        List<DinTable> tableList = dinTableMapper.selectList(null);
        return tableList;
    }

    @Override
    public void insertDinTable(DinTable insertTable){
        dinTableMapper.insert(insertTable);
    }

    
}
