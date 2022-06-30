package com.easyorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.DinTable;

public interface DinTableMapper extends BaseMapper<DinTable> {
    /*
     * 查询talbe时查询areaName
     */
    @Select("SELECT din_table.*,area.`area_name` "+
            "FROM din_table,area "+
            "${ew.customSqlSegment} and din_table.area_id=area.area_id")
    List<DinTable> getDinTableArea(@Param("ew")QueryWrapper<DinTable> wapper);
    
}
