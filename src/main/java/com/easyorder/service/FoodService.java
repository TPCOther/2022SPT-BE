package com.easyorder.service;

import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.easyorder.dto.FoodExecution;
import com.easyorder.entity.Food;
import com.easyorder.util.BaseExecuteException;

public interface FoodService {
	FoodExecution insertFood(Food food,CommonsMultipartFile foodImg,Map<CommonsMultipartFile,String>foodImgList)throws BaseExecuteException;
	FoodExecution updateFood(Food food,CommonsMultipartFile foodImg,Map<CommonsMultipartFile,String>foodImgList)throws BaseExecuteException;
	Food selectFoodByFoodId(Long foodId);
	FoodExecution deletFoodByFoodId(Long FoodId)throws BaseExecuteException;
	FoodExecution selectFoodList(Food food,int pageIndex,int pageSize);
}
