package com.easyorder.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("food_category")
public class FoodCategory {
	@TableId(type = IdType.AUTO)
	Long foodCategoryId;
	String foodCategoryName;
	String foodCategoryDesc;
	Integer priority;
	Date createTime;
}	
