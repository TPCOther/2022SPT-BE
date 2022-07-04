package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Food;
import com.easyorder.entity.FoodCategory;
import com.easyorder.entity.FoodImg;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.FoodCategoryMapper;
import com.easyorder.mapper.FoodImgMapper;
import com.easyorder.mapper.FoodMapper;
import com.easyorder.service.FoodCategoryService;
import com.easyorder.util.BaseExecuteException;

@Service
public class FoodCategoryImpl extends ServiceImpl<FoodCategoryMapper, FoodCategory> implements FoodCategoryService {

	@Resource
	FoodMapper foodMapper;
	@Resource
	FoodImgMapper foodImgMapper;
	/**
	 * 批量插入
	 */
	@Override
	@Transactional
	public BaseExecution<FoodCategory> insertFoodCategoryBath(List<FoodCategory> list) {

		try {
			boolean b = saveBatch(list);
			if (b)
				return new BaseExecution<FoodCategory>(ExecuteStateEum.SUCCESS);
			else
				throw new BaseExecuteException("插入操作失败");
		} catch (BaseExecuteException e) {
			return new BaseExecution<FoodCategory>(e.getMessage());
		} catch (Exception e) {
			return new BaseExecution<FoodCategory>("未知错误");
		}
	}

	/**
	 * 删除
	 */
	@Override
	@Transactional
	public BaseExecution<FoodCategory> deleteFoodCategory(Long foodCategoryId) {
		try {
			QueryWrapper<Food> q = new QueryWrapper<>();
			q.eq("category_id", foodCategoryId);
			int e = foodMapper.delete(q);
			if (e < 0) {
				throw new BaseExecuteException("删除相关菜品失败");
			}
			boolean b = removeById(foodCategoryId);
			if (!b) {
				throw new BaseExecuteException("删除菜品种类失败");
			}
			return new BaseExecution<FoodCategory>(ExecuteStateEum.SUCCESS);
		} catch (BaseExecuteException e) {
			return new BaseExecution<FoodCategory>(e.getMessage());
		} catch (Exception e) {
			return new BaseExecution<FoodCategory>("未知错误");
		}
	}

	/**
	 * 如果符合条件分页查询，反之不分页
	 */
	@Override
	public BaseExecution<FoodCategory> selectFoodCategoryList(String foodCategoryName, int pageSize, int pageIndex) {
		try {
			QueryWrapper<FoodCategory> q = new QueryWrapper<FoodCategory>();
			q.orderByDesc("priority");
			BaseExecution<FoodCategory> be = null;
			List<FoodCategory> foodCategoryList = null;
			q.like(foodCategoryName != null, "food_category_name", foodCategoryName);
			if (pageIndex > 0 && pageSize > 0) {
				Page<FoodCategory> pageScale = new Page<>(pageIndex, pageSize);
				page(pageScale, q);
				foodCategoryList = pageScale.getRecords();
				Long count = count(q);
				be = new BaseExecution<FoodCategory>(ExecuteStateEum.SUCCESS, foodCategoryList);
				be.setCount(count);
			} else {
				foodCategoryList = list(q);
				be = new BaseExecution<FoodCategory>(ExecuteStateEum.SUCCESS, foodCategoryList);
			}
			return be;
		} catch (Exception e) {
			return new BaseExecution<FoodCategory>("未知错误");
		}

	}

	/**
	 * 修改菜品种类
	 */
	@Override
	@Transactional
	public BaseExecution<FoodCategory> updateFoodCategory(FoodCategory foodCategory) {
		try {
			boolean b = updateById(foodCategory);
			if (!b) {
				throw new BaseExecuteException("更新失败");
			}
			return new BaseExecution<FoodCategory>(ExecuteStateEum.SUCCESS);
		} catch (BaseExecuteException e) {
			return new BaseExecution<FoodCategory>(e.getMessage());
		} catch (Exception e) {
			return new BaseExecution<FoodCategory>("未知错误");
		}
	}

	@Override
	public BaseExecution<FoodCategory> selectFoodCategoryAndFoodAll() {
		try {
			QueryWrapper<FoodCategory> q = new QueryWrapper<FoodCategory>();
			q.orderByDesc("priority");
			BaseExecution<FoodCategory> be = null;
			List<FoodCategory> foodCategoryList = list(q);
			if (foodCategoryList == null) {
				return new BaseExecution<FoodCategory>("查询结果为空");
			}
			for (FoodCategory foodCategory : foodCategoryList) {
				QueryWrapper<Food> queryWrapper = new QueryWrapper<Food>();
				queryWrapper.eq("category_id", foodCategory.getFoodCategoryId());
				List<Food> foodList=foodMapper.selectList(queryWrapper);
				for(Food food :foodList) {
					QueryWrapper<FoodImg> q1=new QueryWrapper<FoodImg>();
					q1.eq("food_id",food.getFoodId());
					food.setFoodImgList(foodImgMapper.selectList(q1));
				}
				foodCategory.setFoodList(foodList);
			}
			be = new BaseExecution<FoodCategory>(ExecuteStateEum.SUCCESS, foodCategoryList);
			return be;
		} catch (Exception e) {
//			return new BaseExecution<FoodCategory>("未知错误");
			return new BaseExecution<FoodCategory>(e.getMessage());
		}
	}

}
