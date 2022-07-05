package com.easyorder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.entity.OrderFood;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;

public interface OrderFoodMapper extends MppBaseMapper<OrderFood> {
	@Select("SELECT orf.*,f.food_name,f.food_normal_price,f.food_promotion_price,f.food_img " + "FROM food f,order_food orf " + "${ew.customSqlSegment} and f.food_id=orf.food_id")
	List<OrderFood> getOrderFoodList(@Param("ew") QueryWrapper<OrderFood> wapper);
}
