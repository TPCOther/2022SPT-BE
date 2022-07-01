package com.easyorder.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Food;
import com.easyorder.entity.FoodCategory;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.FoodCategoryService;
import com.easyorder.service.FoodService;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

@CrossOrigin(origins = { "*", "null" }) // 用于跨域请求，*代表允许响应所有的跨域请求
//@SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/food")
public class FoodController {
	@Resource
	private FoodService foodService;
	@Resource
	private FoodCategoryService foodCategoryService;
	@Resource
	private Gson gson;
	private static final int maxImg = 4;

	// 查询菜品列表
	@GetMapping("/getfoodlist")
	@ResponseBody
	public RBody getFoodList(HttpServletRequest request) {
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if (pageIndex > 0 && pageSize > 0) {
			String foodName = HttpServletRequestUtil.getString(request, "foodName");
			String foodTag = HttpServletRequestUtil.getString(request, "foodTag");
			String categoryName = HttpServletRequestUtil.getString(request, "categoryName");

			Food food = new Food();
			food.setFoodName(foodName);
			food.setFoodTag(foodTag);
			
			QueryWrapper<FoodCategory> q = new QueryWrapper<FoodCategory>();
			q.eq("food_category_name", categoryName);
			q.last("LIMIT 1");
			FoodCategory foodCategory = foodCategoryService.getOne(q);
			if (foodCategory != null)
				food.setCategoryId(foodCategory.getFoodCategoryId());

			BaseExecution<Food> be = foodService.selectFoodList(food, pageIndex, pageSize);
			if (be.getEum() == ExecuteStateEum.SUCCESS) {
				RBody rBody = RBody.ok(be.getEum().getStateInfo());
				rBody.data(be.getTList());
				rBody.put("count", be.getCount());
				return rBody;
			} else {
				return RBody.error(be.getEum().getStateInfo());
			}
		}
		return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
	}

	// 查询菜品详细信息
	@GetMapping("/getfood")
	@ResponseBody
	public RBody getFood(@RequestParam Long foodId) {
		if (foodId != null && foodId > 0) {
			BaseExecution<Food> be = foodService.selectFoodByFoodId(foodId);
			if(be.getTemp()==null) {
				return RBody.ok("查询结果为空").data(be.getTemp());
			}
			FoodCategory foodCategory=foodCategoryService.getById(be.getTemp().getCategoryId());
			if (be.getEum() == ExecuteStateEum.SUCCESS) {
				RBody rBody = RBody.ok(be.getEum().getStateInfo());
				rBody.data(be.getTemp());
				rBody.put("foodCategoryName",foodCategory.getFoodCategoryName());
				return rBody;
			} else {
				return RBody.error(be.getEum().getStateInfo());
			}
		} else
			return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
	}

	// 添加菜品信息
	@PostMapping("/insertfood")
	@ResponseBody
	public RBody insertFood(HttpServletRequest request) {
		// 验证码
//		if (!CodeUtil.checkVerifyCode(request)) {
//			return RBody.error("验证码错误");
//		}
		// 接受信息
		String foodStr = HttpServletRequestUtil.getString(request, "foodStr");
		Food food = null;
		// 将信息转化为实体类实例
		try {
			food = gson.fromJson(foodStr, Food.class);
		} catch (Exception e) {
			return RBody.error(e.getMessage());
		}
		// 处理图片数据流

		MultipartHttpServletRequest multipartHttpServletRequest;
		CommonsMultipartFile foodImg = null;
		Map<CommonsMultipartFile, String> foodImgMap = new HashMap<>();
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 判断文件流
			if (commonsMultipartResolver.isMultipart(request)) {
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				foodImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("foodImg");
				// 缩略图
				if (foodImg == null) {
					return RBody.error("上传失败:缩略图片文件为空");
				}
				// 详情图
				for (int i = 0; i < maxImg; i++) {
					CommonsMultipartFile temp = (CommonsMultipartFile) multipartHttpServletRequest
							.getFile("foodImg" + i);
					String str = HttpServletRequestUtil.getString(request, "foodImgDesc" + i);
					if (temp != null) {
						foodImgMap.put(temp, str);
					} else
						break;
				}
				if (foodImgMap.size() <= 0) {
					return RBody.error("上传失败:详情图片文件为空");
				}
			} else {
				return RBody.error("上传失败:没有找到图片文件");
			}
		} catch (Exception e) {
			return RBody.error("上传失败:" + e.getMessage());
		}
		// 添加商品
		if (food != null) {
			try {
				BaseExecution<Food> be = foodService.insertFood(food, foodImg, foodImgMap);
				if (be.getEum().getState() == ExecuteStateEum.SUCCESS.getState()) {
					return RBody.ok("添加成功");
				} else {
					return RBody.error(be.getEum().getStateInfo());
				}
			} catch (Exception e) {
				return RBody.error(e.getMessage());
			}
		}
		return RBody.error();
	}

	// 修改菜品信息
	@PostMapping("/updatefood")
	@ResponseBody
	public RBody updateFood(HttpServletRequest request) {
		// 验证码
//		if (!CodeUtil.checkVerifyCode(request)) {
//			return RBody.error("验证码错误");
//		}
		// 接受信息
		String foodStr = HttpServletRequestUtil.getString(request, "foodStr");
		Food food = null;
		// 将信息转化为实体类实例
		try {
			food = gson.fromJson(foodStr, Food.class);
		} catch (Exception e) {
			return RBody.error(e.getMessage());
		}
		// 处理图片数据流

		MultipartHttpServletRequest multipartHttpServletRequest;
		CommonsMultipartFile foodImg = null;
		Map<CommonsMultipartFile, String> foodImgMap = new HashMap<>();
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// 判断文件流
			if (commonsMultipartResolver.isMultipart(request)) {
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				// 缩略图
				foodImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("foodImg");
				// 详情图
				for (int i = 0; i < maxImg; i++) {
					CommonsMultipartFile temp = (CommonsMultipartFile) multipartHttpServletRequest
							.getFile("foodImg" + i);
					String str = HttpServletRequestUtil.getString(request, "foodImgDesc" + i);
					if (temp != null) {
						foodImgMap.put(temp, str);
					} else
						break;
				}
			}
		} catch (Exception e) {
			return RBody.error("上传失败:" + e.getMessage());
		}
		// 修改操作
		if (food != null && food.getFoodId() != null && food.getFoodId() > 0) {
			try {
				BaseExecution<Food> be = foodService.updateFood(food, foodImg, foodImgMap);
				if (be.getEum().getState() == ExecuteStateEum.SUCCESS.getState()) {
					return RBody.ok(be.getEum().getStateInfo());
				} else {
					return RBody.error(be.getEum().getStateInfo());
				}
			} catch (Exception e) {
				return RBody.error(e.getMessage());
			}
		}
		return RBody.error(ExecuteStateEum.INCOMPLETE.getStateInfo());
	}

	// 删除菜品信息
	@PostMapping("/deletefood")
	@ResponseBody
	public RBody deleteFood(@RequestBody Food food) {
		if (food!=null&&food.getFoodId() != null && food.getFoodId() > 0) {
			BaseExecution<Food> be = foodService.deletFoodByFoodId(food.getFoodId());
			if (be.getEum() == ExecuteStateEum.SUCCESS) {
				RBody rBody = RBody.ok(be.getEum().getStateInfo());
				return rBody;
			} else {
				return RBody.error(be.getEum().getStateInfo());
			}
		}
		return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
	}
}
