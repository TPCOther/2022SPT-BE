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
@TableName("food")
public class Food {
	@TableId(type = IdType.AUTO )
	Long foodId;
	String foodTag;
	String foodName;
	String foodDesc;
	Float foodNormalPrice;
	Float foodPromotionPrice;
	Integer foodState;//是否上架
	String foodImg;//缩略图
	Integer priority;
	Date createTime;
	Date lastEditTime;
	
	
	Long categoryId;
	@TableField(exist = false)
	List<FoodImg> foodImgList;//详情图
	public Food() {}
}
