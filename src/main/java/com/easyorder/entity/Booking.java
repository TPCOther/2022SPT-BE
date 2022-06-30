package com.easyorder.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("booking")
public class Booking {
	@TableId(type = IdType.AUTO)
	Long bookingId;
	
	Long customerId;
	Long dinTableId;
	
	Date createTime;
	Date startTime;
	Date endTime;
	Integer bookingState;

	String bookingName;
	Integer bookingPhone;
    @TableField(exist =false)
	String areaName;
    @TableField(exist =false)
	String dinTableName;

	public Booking(){}
}
