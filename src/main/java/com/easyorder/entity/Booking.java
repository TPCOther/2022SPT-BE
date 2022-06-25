package com.easyorder.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
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
	Date createTime;
	Date startTime;
	Date endTime;
	Integer bookingState;
	
	Long customerId;
	Long tableId;
}
