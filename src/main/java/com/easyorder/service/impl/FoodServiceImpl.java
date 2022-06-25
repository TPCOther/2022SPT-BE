package com.easyorder.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.easyorder.dto.FoodExecution;
import com.easyorder.entity.Food;
import com.easyorder.entity.FoodImg;
import com.easyorder.enums.FoodStateEum;
import com.easyorder.mapper.FoodImgMapper;
import com.easyorder.mapper.FoodMapper;
import com.easyorder.service.FoodService;
import com.easyorder.util.BaseExecuteException;
import com.easyorder.util.ImageUtil;
import com.easyorder.util.PathUtil;

@Service
public class FoodServiceImpl implements FoodService {
	@Resource
	private FoodMapper foodMapper;
	@Resource
	private FoodImgMapper foodImgMapper;

	/**
	 * 添加菜品和菜品图片
	 */
	@Override
	@Transactional
	public FoodExecution insertFood(Food food, CommonsMultipartFile foodImg,
			Map<CommonsMultipartFile, String> foodImgMap) throws BaseExecuteException {
		if (food != null) {
			// 设置优先级、日期的默认值
			if (food.getPriority() == null)
				food.setPriority(0);
			food.setCreateTime(new Date());
			food.setLastEditTime(new Date());
			food.setFoodState(FoodStateEum.SALING.getState());

			// 缩略图是否存在
			if (foodImg != null) {
				try {
					addFoodImg(food, foodImg);
				} catch (Exception e) {
					throw new BaseExecuteException("图片添加失败：菜品缩略图添加失败");
				}

			}
			// 数据库插入操作
			try {
				int x = foodMapper.insert(food);
				if (x <= 0) {
					throw new BaseExecuteException("添加菜品信息失败");
				}
			} catch (Exception e) {
				throw new BaseExecuteException("菜品添加失败：添加菜品信息失败");
			}
			// 详情图片是否存在
			if (foodImgMap != null && foodImgMap.size() > 0) {
				try {
					addFoodImgList(food, foodImgMap);
				} catch (Exception e) {
					throw new BaseExecuteException("图片添加失败：" + e.getMessage());
				}
			}
			return new FoodExecution(FoodStateEum.SUCCESS);
		} else {
			return new FoodExecution(FoodStateEum.EMPTYMSG);
		}
	}

	@Override
	public FoodExecution updateFood(Food food, CommonsMultipartFile foodImg,
			Map<CommonsMultipartFile, String> foodImgList) throws BaseExecuteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Food selectFoodByFoodId(Long foodId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodExecution deletFoodByFoodId(Long FoodId) throws BaseExecuteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodExecution selectFoodList(Food food, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 添加图片
	 * 
	 * @param product
	 * @param productImg
	 */
	private void addFoodImg(Food food, CommonsMultipartFile productImg) {
		String dest = PathUtil.getFoodImagePath(food);
		String addr = ImageUtil.generateThumbnail(productImg, dest);
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
		// 处理详情图片并 创建初始化productImg对象 保存图
		for (CommonsMultipartFile f : foodImgDetailMap.keySet()) {
			String imgAddr = ImageUtil.clearThumbnail(f, dest);
			FoodImg foodImg = new FoodImg(null, food.getFoodId(), imgAddr, foodImgDetailMap.get(f));
			foodImgList.add(foodImg);
		}
		// 操作数据库保存记录
		if (foodImgList.size() > 0) {
			try {
				for (FoodImg f : foodImgList) {
					//TODO
					int e = foodImgMapper.insert(f);
					if (e <= 0) {
						throw new BaseExecuteException("添加详情菜品图片失败！");
					}
				}
			} catch (Exception e) {
				throw new BaseExecuteException(e.getMessage());
			}
		} else {
			throw new BaseExecuteException("菜品详情图片为空！");
		}
	}
}
