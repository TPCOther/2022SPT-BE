package com.easyorder.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Food;
import com.easyorder.entity.FoodImg;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.enums.FoodStateEum;
import com.easyorder.mapper.FoodImgMapper;
import com.easyorder.mapper.FoodMapper;
import com.easyorder.service.FoodService;
import com.easyorder.util.BaseExecuteException;
import com.easyorder.util.ImageUtil;
import com.easyorder.util.PathUtil;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodImgMapper, FoodImg> implements FoodService {
	@Resource
	private FoodMapper foodMapper;

	/**
	 * 添加菜品和菜品图片
	 */
	@Override
	@Transactional
	public BaseExecution<Food> insertFood(Food food, CommonsMultipartFile foodImg,
			Map<CommonsMultipartFile, String> foodImgMap) throws BaseExecuteException {
		if (food != null) {
			// 设置优先级、日期的默认值
			if (food.getPriority() == null)
				food.setPriority(0);
			food.setCreateTime(new Date());
			food.setLastEditTime(new Date());
			food.setFoodState(FoodStateEum.SALING.getState());
			// 数据库插入操作
			try {
				int x = foodMapper.insert(food);
				if (x <= 0) {
					throw new BaseExecuteException("添加菜品信息失败");
				}
			} catch (Exception e) {
				throw new BaseExecuteException("菜品添加失败：添加菜品信息失败");
			}

			// 缩略图是否存在
			if (foodImg != null) {   
				try {
					addFoodImg(food, foodImg);
					int x = foodMapper.updateById(food);
					if (x <= 0) {
						throw new BaseExecuteException("图片添加失败：菜品缩略图添加失败");
					}
				} catch (Exception e) {
					throw new BaseExecuteException(e.getMessage());
				}
			}
			// 详情图片是否存在
			if (foodImgMap != null && foodImgMap.size() > 0) {
				try {
					addFoodImgList(food, foodImgMap);
				} catch (Exception e) {
					throw new BaseExecuteException("图片添加失败：" + e.getMessage());
				}
			}
			return new BaseExecution<Food>(ExecuteStateEum.SUCCESS,food);
		} else {
			return new BaseExecution<Food>(ExecuteStateEum.INCOMPLETE);
		}
	}

	/**
	 * 修改菜品信息及图片
	 */
	@Override
	@Transactional
	public BaseExecution<Food> updateFood(Food food, CommonsMultipartFile foodImg,
			Map<CommonsMultipartFile, String> foodImgMap) throws BaseExecuteException {
		if (food != null) {
			food.setLastEditTime(new Date());
			// 处理缩略图
			if (foodImg != null) {
				Food temp = foodMapper.selectById(food.getFoodId());

				if (temp.getFoodImg() != null) {
					ImageUtil.deleteFile(temp.getFoodImg());
				}
				addFoodImg(food, foodImg);
			}
			// 处理详情图片
			if (foodImgMap != null && foodImgMap.size() > 0) {
				deleteFoodImgList(food.getFoodId());
				addFoodImgList(food, foodImgMap);
			}
			// 操作数据库
			try {
				int e = foodMapper.updateById(food);
				if (e <= 0)
					throw new BaseExecuteException("更新菜品信息失败");
				return new BaseExecution<Food>(ExecuteStateEum.SUCCESS,food);
			} catch (Exception e) {
				throw new BaseExecuteException("更新失败:" + e.getMessage());
			}
		} else {
			return new BaseExecution<Food>(ExecuteStateEum.INCOMPLETE,food);
		}
	}

	/**
	 * 查询单条信息
	 */
	@Override
	public BaseExecution<Food> selectFoodByFoodId(Long foodId) {
		Food f;
		try {
			f=foodMapper.selectById(foodId);
			QueryWrapper<FoodImg> q=new QueryWrapper<FoodImg>();
			q.eq("food_id",foodId);
			List<FoodImg> foodImgs=list(q);
			f.setFoodImgList(foodImgs);
		} catch (Exception e) {
			return new BaseExecution<Food>(ExecuteStateEum.INNER_ERROR);
		}
		return new BaseExecution<Food>(ExecuteStateEum.SUCCESS, f);
		
	}

	/**
	 * 删除菜品并删除对应的图片文件和数据表信息
	 */
	@Override
	@Transactional
	public BaseExecution<Food> deletFoodByFoodId(Long foodId)  {
		if (foodId != null) {
			try {
				Food temp = foodMapper.selectById(foodId);
				if (temp.getFoodImg() != null) {
					ImageUtil.deleteFile(temp.getFoodImg());
				}
				deleteFoodImgList(foodId);
				ImageUtil.deleteFile(PathUtil.getFoodAllImagePath(temp));
				int e = foodMapper.deleteById(foodId);
				if (e <= 0) {
					throw new BaseExecuteException("数据库操作失败");
				}
				return new BaseExecution<Food>(ExecuteStateEum.SUCCESS);
			} catch (Exception e) {
				return new BaseExecution<Food>(ExecuteStateEum.INNER_ERROR); 
			}
		}else {
			return new BaseExecution<Food>(ExecuteStateEum.INCOMPLETE);
		}
	}

	/**
	 * 菜品分页查找，支持菜品id,菜品种类id,菜品名，菜品标签查找
	 */
	@Override
	public BaseExecution<Food> selectFoodList(Food food, int pageIndex, int pageSize) {
		QueryWrapper<Food> q = new QueryWrapper<>();
		q.eq(food.getFoodId() != null, "food_id", food.getFoodId());
		q.eq(food.getCategoryId() != null, "category_id", food.getCategoryId());
		q.like(food.getFoodName() != null, "food_name", food.getFoodName());
		q.like(food.getFoodTag() != null, "food_tag", food.getFoodTag());
		Page<Food> page = new Page<>(pageIndex, pageSize);
		try {
			foodMapper.selectPage(page, q);
			BaseExecution<Food> be = new BaseExecution<Food>(ExecuteStateEum.SUCCESS, page.getRecords());
			Long count = foodMapper.selectCount(q);
			be.setCount(count);
			return be;
		} catch (Exception e) {
			return new BaseExecution<Food>(ExecuteStateEum.INNER_ERROR);
		}
	}

	/**
	 * 添加图片
	 * 
	 * @param
	 * @param
	 */
	private void addFoodImg(Food food, CommonsMultipartFile foodImg) {
		String dest = PathUtil.getFoodImagePath(food);
		String addr = ImageUtil.generateThumbnail(foodImg, dest);
		food.setFoodImg(addr);
	}

	/**
	 * 添加详情菜品图片并保存到菜品图片表中
	 * 
	 * @param product
	 * @param productImgDetailList
	 */
	private void addFoodImgList(Food food, Map<CommonsMultipartFile, String> foodImgDetailMap) {
		String dest = PathUtil.getFoodImageDetailPath(food);
		List<FoodImg> foodImgList = new ArrayList<>();
		// 处理详情图片并 创建初始化foodImg对象 保存图
		for (CommonsMultipartFile f : foodImgDetailMap.keySet()) {
			String imgAddr = ImageUtil.clearThumbnail(f, dest);
			FoodImg foodImg = new FoodImg(null, food.getFoodId(), imgAddr, foodImgDetailMap.get(f));
			foodImgList.add(foodImg);
		}
		// 操作数据库保存记录
		if (foodImgList.size() > 0) {
			try {
				boolean e = saveBatch(foodImgList);
				if (!e) {
					throw new BaseExecuteException("添加详情菜品图片失败！");
				}
			} catch (Exception e) {
				throw new BaseExecuteException(e.getMessage());
			}
		} else {
			throw new BaseExecuteException("菜品详情图片为空！");
		}
	}

	/**
	 * 删除详情图 删除文件和数据库记录
	 * 
	 * @param foodId
	 */
	private void deleteFoodImgList(Long foodId) {
		QueryWrapper<FoodImg> q = new QueryWrapper<>();
		q.eq("food_id", foodId);
		List<FoodImg> foodImgList = list(q);
		// 删除数据库记录
		remove(q);
		// 删除文件
		for (FoodImg x : foodImgList) {
			ImageUtil.deleteFile(x.getFoodImgUrl());
		}
	}


}
