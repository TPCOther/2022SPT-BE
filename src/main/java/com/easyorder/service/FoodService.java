package com.easyorder.service;

import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Food;
import com.easyorder.entity.FoodImg;
import com.easyorder.util.BaseExecuteException;

public interface FoodService extends IService<FoodImg> {
	BaseExecution<Food> insertFood(Food food,CommonsMultipartFile foodImg,Map<CommonsMultipartFile,String>foodImgMap)throws BaseExecuteException;
	BaseExecution<Food> updateFood(Food food,CommonsMultipartFile foodImg,Map<CommonsMultipartFile,String>foodImgMap)throws BaseExecuteException;
	Food selectFoodByFoodId(Long foodId);
	BaseExecution<Food> deletFoodByFoodId(Long foodId)throws BaseExecuteException;
	BaseExecution<Food> selectFoodList(Food food,int pageIndex,int pageSize);
}
