package com.easyorder.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.FoodCategory;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.FoodCategoryService;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;
import com.google.gson.Gson;

@CrossOrigin(origins = { "*", "null" }) // 用于跨域请求，*代表允许响应所有的跨域请求
//@SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/foodcategory")
public class FoodCategoryController {
	@Resource
	FoodCategoryService foodCategoryService;
	@Resource
	Gson gson;
	/**
	 * 查询菜品种类 有分页信息就分页，没有就不分页
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/getfoodcategorylist")
	@ResponseBody
	public RBody getFoodCategory(HttpServletRequest request) {
		Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if (pageIndex == null || pageSize == null) {
			pageIndex = -1;
			pageSize = -1;
		}
		String foodCategoryName = HttpServletRequestUtil.getString(request, "foodCategoryName");
		BaseExecution<FoodCategory> be = foodCategoryService.selectFoodCategoryList(foodCategoryName, pageSize,
				pageIndex);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.data(be.getTList());
			if (be.getCount() != null)
				rBody.put("count", be.getCount());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	/**
	 * 批量添加
	 * 
	 * @param foodCategoryList
	 * @return
	 */
	@PostMapping("/insertfoodcategorylist")
	@ResponseBody
	public RBody insertFoodCategoryList(@RequestBody List<FoodCategory> foodCategoryList) {
		if (foodCategoryList != null && foodCategoryList.size() > 0) {
			for (FoodCategory fc : foodCategoryList) {
				fc.setCreateTime(new Date());
			}
			BaseExecution<FoodCategory> be = foodCategoryService.insertFoodCategoryBath(foodCategoryList);
			if (be.getEum() == ExecuteStateEum.SUCCESS) {
				return RBody.ok(be.getEum().getStateInfo());
			} else {
				return RBody.error(be.getStateInfo());
			}
		} else {
			return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
		}
	}
	/**
	 * 删除菜品种类
	 * @param request
	 * @return
	 */
	@PostMapping("/deletefoodcategory")
	@ResponseBody
	public RBody deleteFoodCategory(HttpServletRequest request) {
		Long foodCategoryId = HttpServletRequestUtil.getLong(request, "foodCategoryId");
		if (foodCategoryId != null && foodCategoryId > 0) {
			BaseExecution<FoodCategory> be = foodCategoryService.deleteFoodCategory(foodCategoryId);
			if (be.getEum() == ExecuteStateEum.SUCCESS) {
				return RBody.ok(be.getEum().getStateInfo());
			} else {
				return RBody.error(be.getStateInfo());
			}
		} else {
			return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
		}
	}
	/**
	 * 修改
	 * @param foodCategory
	 * @return
	 */
	@PostMapping("/updatefoodcategory")
	@ResponseBody
	public RBody updateFoodCategory(@RequestBody FoodCategory foodCategory) {
		if(foodCategory!=null&&foodCategory.getFoodCategoryId()!=null&&foodCategory.getFoodCategoryId()>0) {
			BaseExecution<FoodCategory> be =foodCategoryService.updateFoodCategory(foodCategory);
			if (be.getEum() == ExecuteStateEum.SUCCESS) {
				return RBody.ok(be.getEum().getStateInfo());
			} else {
				return RBody.error(be.getStateInfo());
			}
		}else {
			return RBody.error(ExecuteStateEum.INPUT_ERROR.getStateInfo());
		}
	}
	
	/**
	 * 获取所有菜品以及对应菜品
	 * 
	 */
	@GetMapping("/getallfoodandfoodcategory")
	@ResponseBody
	public RBody getAllFoodAndFoodcategory() {
		BaseExecution<FoodCategory> be=foodCategoryService.selectFoodCategoryAndFoodAll();
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			return RBody.ok(be.getEum().getStateInfo()).data(be.getTList());
		} else {
			return RBody.error(be.getStateInfo());
		}
	}
}
