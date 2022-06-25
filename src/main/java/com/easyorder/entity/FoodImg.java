package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("food_img")
public class FoodImg {
	@TableId(type = IdType.AUTO)
	Long foodImgId;
	Long foodId;
	String foodImgUrl;
	String foodImgDesc;
}
