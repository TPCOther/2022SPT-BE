package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easyorder.entity.Area;
import com.easyorder.mapper.AreaMapper;
import com.easyorder.service.AreaService;
import com.easyorder.util.BaseExecuteException;

@Service
public class AreaServiceImpl implements AreaService{
    @Resource
    AreaMapper areaMapper;
    /**
     * 查询所有区域
     */
    @Override
    public List<Area> selectAreaList() {
        List<Area> areaList = areaMapper.selectList(null);
        return areaList;
    }




    /**
     * 插入区域
     */
    @Override
    public Long insertArea(Area area) throws BaseExecuteException{
        try{
            int effctedNum = areaMapper.insert(area);
            if(effctedNum <= 0){
                throw new BaseExecuteException("创建区域(Area)失败: "+"插入0条数据");
            }
            return area.getAreaId();

        }catch(Exception e){
            throw new BaseExecuteException("创建区域(Area)失败: "+e.toString());
        }
    }
    /**
     * 更新区域
     */
    @Override
    public void updateArea(Area area) {
        try{
            int effctedNum = areaMapper.updateById(area);
            if(effctedNum <= 0){
                throw new BaseExecuteException("更新区域(Area)失败: "+"更新0条数据");
            }

        }catch(Exception e){
            throw new BaseExecuteException("更新区域(Area)失败: "+e.toString());
        }
        
    }
    /**
     * 删除区域
     */
    @Override
    public void deleteArea(Area area) {
        try{
            int effctedNum = areaMapper.deleteById(area);
            if(effctedNum <= 0){
                throw new BaseExecuteException("删除区域(Area)失败: "+"删除0条数据");
            }

        }catch(Exception e){
            throw new BaseExecuteException("删除区域(Area)失败: "+e.toString());
        }
        
    }

    

}
