package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodImg {
	@TableId(type = IdType.AUTO)
	Long foodImg;
	Long foodId;
	String foodImgUrl;
	String foodImgDesc;
}
