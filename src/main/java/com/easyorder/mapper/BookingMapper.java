package com.easyorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.Booking;

public interface BookingMapper extends BaseMapper<Booking> {
    @Select("SELECT booking.*,din_table.`din_table_name`,area.`area_name` "+
            "FROM booking,din_table,area "+
            "${ew.customSqlSegment} and booking.din_table_id=din_table.din_table_id and din_table.area_id=area.area_id")
    List<Booking> getBookingDetail(@Param("ew")QueryWrapper<Booking> wapper);
    
}
