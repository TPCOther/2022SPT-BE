package com.easyorder.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("tb_order")
public class Order {
	@TableId(type = IdType.AUTO)
	Long orderId;
	String orderDesc;
	Integer orderState;
	Float orderAmount;
	Date createTime;
	Date payTime;
	String orderEvaluation;//评价
	Integer orderPayMethod;
	
	Long customerId;
	Long staffId;
	Long dinTableId;
	
	@TableField(exist = false)
	List<OrderFood> orderFoodList;
	@TableField(exist = false)
	String customerNickname;
	@TableField(exist = false)
	String customerImg;
	public Order() {}
}
