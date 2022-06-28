package com.easyorder.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyorder.entity.Area;
import com.easyorder.util.BaseExecuteException;

public interface AreaService extends IService<Area>{
    List<Area> selectAreaList(Long areaId,String areaName) throws BaseExecuteException;    
    Long insertArea(Area area) throws BaseExecuteException;
    void updateArea(Area area) throws BaseExecuteException;
    void deleteArea(Area area) throws BaseExecuteException;
}
