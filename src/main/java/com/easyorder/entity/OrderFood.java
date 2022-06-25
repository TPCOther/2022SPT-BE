package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("order_food")
public class OrderFood {
	@MppMultiId
	Long orderId;
	@MppMultiId
	Long foodId;
	Integer orderFoodState;
}
