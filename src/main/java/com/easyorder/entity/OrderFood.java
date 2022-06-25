package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("order_food")
public class OrderFood {
	@TableId
	Long orderId;
	@TableId
	Long foodId;
	Integer orderFoodState;
}
