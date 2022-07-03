package com.easyorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.entity.Order;
import com.easyorder.entity.OrderFood;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;

public interface OrderFoodMapper extends MppBaseMapper<OrderFood> {
	@Select("SELECT orf.*" + "FROM tb_order o,order_food orf " + "${ew.customSqlSegment} and o.order_id=orf.order_id")
	List<OrderFood> getOrderFoodList(@Param("ew") QueryWrapper<Order> wapper);
}
