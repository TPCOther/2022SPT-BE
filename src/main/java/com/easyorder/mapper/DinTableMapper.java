package com.easyorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.DinTable;

public interface DinTableMapper extends BaseMapper<DinTable> {
    /*
     * 查询talbe时查询areaName
     */
    @Select("SELECT din_table.*,area.`area_name` FROM din_table,area WHERE din_table.din_table_id=area.area_id")
    List<DinTable> getDinTableArea(QueryWrapper<DinTable> wapper);
    
}
