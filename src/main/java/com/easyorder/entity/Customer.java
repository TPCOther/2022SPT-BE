package com.easyorder.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("customer")
public class Customer {
	@TableId(type=IdType.AUTO)
	private Long customerId;
	
	private String customerNickname;
	
	private String customerImg;
	
	private String openId;
	
	private String customerName;
	
	private String customerGender;
	
	private Integer customerPhone;
	
	private Integer customerPoint;//顾客积分
	
	private Integer customerVip;
	
	private Date createTime;
	
	private Date lastEditTime;

	public Customer(){};
	
}
