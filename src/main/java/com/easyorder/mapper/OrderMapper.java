package com.easyorder.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT tb_order.`tb_order_amount` "+
            "FROM tb_order "+
            "WHERE tb_order.`table_id`=#{tableId} and tb_order.`tb_order_state`!=0 and tb_order.`tb_order_state`!=4")
    Integer getOrderAmountByTableId(@Param("tableId")Long tableId);
}
