package com.easyorder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
	
	private int customerId;
	
	private String customerNickname;
	
	private String customerImg;
	
	private String openId;
	
	private String customerName;
	
	private String customerGender;
	
	private int customerPhone;
	
	private int customerPoint;//顾客积分
	
	private int customerVip;
	
	private String createTime;
	
	private String lastEditTime;
	
}
