package com.easyorder.service;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.FoodCategory;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface FoodCategoryService extends IService<FoodCategory> {
	BaseExecution<FoodCategory> selectFoodCategoryList(String foodCategoryName, int pageSize, int pageIndex);

	BaseExecution<FoodCategory> insertFoodCategoryBath(List<FoodCategory> list);

	BaseExecution<FoodCategory> deleteFoodCategory(Long foodCategoryId);

	BaseExecution<FoodCategory> updateFoodCategory(FoodCategory foodCategory);
}
