package com.easyorder.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Food;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.FoodService;
import com.easyorder.util.CodeUtil;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = { "*", "null" }) // 用于跨域请求，*代表允许响应所有的跨域请求
//@SuppressWarnings("all") 用于忽略报错
@RestController
public class FoodController {
	@Resource
	private FoodService foodService;

	private static final int maxImg = 4;

	// 查询菜品信息
	@GetMapping("/getFoodList")
	@ResponseBody
	public RBody getFoodList() {
		return RBody.ok();
	}

	// 添加菜品信息
	@PostMapping("/insertFood")
	@ResponseBody
	public RBody insertFood(HttpServletRequest request) {
		// 验证码
//		if (!CodeUtil.checkVerifyCode(request)) {
//			return RBody.error("验证码错误");
//		}
		// 接受信息
		ObjectMapper mapper = new ObjectMapper();
		String foodStr = HttpServletRequestUtil.getString(request, "foodStr");
		Food food = null;
		// 将信息转化为实体类实例
		try {
			food = mapper.readValue(foodStr, Food.class);
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
	@PostMapping("/updateFood")
	@ResponseBody
	public RBody updateFood(HttpServletRequest request) {
		// 验证码
//		if (!CodeUtil.checkVerifyCode(request)) {
//			return RBody.error("验证码错误");
//		}
		// 接受信息
		ObjectMapper mapper = new ObjectMapper();
		String foodStr = HttpServletRequestUtil.getString(request, "foodStr");
		Food food = null;
		// 将信息转化为实体类实例
		try {
			food = mapper.readValue(foodStr, Food.class);
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
		// 修改操作
		return RBody.ok();
	}

	// 删除菜品信息
	@GetMapping("deleteFood")
	@ResponseBody
	public RBody deleteFood() {
		return RBody.ok();
	}
}
