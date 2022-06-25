package com.easyorder;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.easyorder.entity.FoodCategory;
import com.easyorder.mapper.FoodCategoryMapper;

public class FoodCategoryTest extends BaseTest {
	@Resource
	FoodCategoryMapper fcm;
	
	@Test
	public void testInsert() {
		int x=fcm.insert(new FoodCategory(null, "川菜", "色香味俱全", 1, new Date()));
		assertTrue(x==1);
	}
}
