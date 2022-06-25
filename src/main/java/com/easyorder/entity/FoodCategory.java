package com.easyorder.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodCategory {
	@TableId(type = IdType.AUTO)
	Long foodCategoryId;
	String foodCategoryName;
	String foodCategoryDesc;
	Integer priority;
	Date createTime;
}	
