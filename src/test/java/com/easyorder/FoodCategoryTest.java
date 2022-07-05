package com.easyorder;

import javax.annotation.Resource;

import org.junit.Test;

import com.easyorder.dto.BaseExecution;
import com.easyorder.mapper.FoodCategoryMapper;
import com.easyorder.service.FoodCategoryService;

public class FoodCategoryTest extends BaseTest {
	@Resource
	FoodCategoryMapper fcm;

	@Resource
	FoodCategoryService f;

	@Test
	public void testSelect() {

	}
}
