package com.easyorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT tb_order.`order_amount` "+
            "FROM tb_order "+
            "WHERE tb_order.`din_table_id`=#{tableId} and tb_order.`order_state`!=0 and tb_order.`order_state`!=4")
    Integer getOrderAmountByTableId(@Param("tableId")Long tableId);
    
    @Select("SELECT o.*,c.customer_nickname "+
            "FROM tb_order o,customer c "+
            "${ew.customSqlSegment} and o.customer_id=c.customer_id")
    List<Order> getOrder(@Param("ew") QueryWrapper<Order> wapper);
    
    @Select("SELECT o.order_evaluation,c.customer_nickname,c.customer_img "+
            "FROM tb_order o,customer c "+
            " where o.customer_id=c.customer_id and o.order_evaluation is not null")
    List<Order> getOrderEvaluation();
    @Select("SELECT o.*,c.customer_nickname "+
            "FROM tb_order o,customer c "+
            "${ew.customSqlSegment} and o.customer_id=c.customer_id "+
            " limit #{p1},#{p2}" )
    List<Order> getOrderPage(@Param("ew") QueryWrapper<Order> wapper,@Param("p1")int start,@Param("p2")int end);
    
    @Select("SELECT count(*) "+
            "FROM tb_order o,customer c "+
            "${ew.customSqlSegment} and o.customer_id=c.customer_id ")
    Long countOrder(@Param("ew") QueryWrapper<Order> wapper);
}
