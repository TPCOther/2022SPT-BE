package com.easyorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT tb_order.`tb_order_amount` "+
            "FROM tb_order "+
            "WHERE tb_order.`table_id`=#{tableId} and tb_order.`tb_order_state`!=0 and tb_order.`tb_order_state`!=4")
    Integer getOrderAmountByTableId(@Param("tableId")Long tableId);
    
    @Select("SELECT o.*,c.customer_nickname "+
            "FROM tb_order o,customer c "+
            "${ew.customSqlSegment} and o.customer_id=c.customer_id")
    List<Order> getOrder(@Param("ew") QueryWrapper<Order> wapper);
    
    @Select("SELECT o.order_evaluation,c.customer_nickname,c.customer_img "+
            "FROM tb_order o,customer c "+
            " where o.customer_id=c.customer_id and o.order_evaluation is not null")
    List<Order> getOrderEvaluation();
}
