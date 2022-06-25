package com.easyorder;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.easyorder.entity.Food;
import com.easyorder.entity.FoodCategory;
import com.easyorder.enums.FoodStateEum;
import com.easyorder.mapper.FoodMapper;

public class FoodTest extends BaseTest {
	@Resource
	FoodMapper fm;

	@Test
	public void foodInsert() {
		FoodCategory f=new FoodCategory(null, "川菜", "色香味俱全", 1, new Date());
		fm.insert(new Food(null, "回锅肉", "test", "test", 12.0f, 11.0f, FoodStateEum.EMPTY.getState(), "test", 1, new Date(), new Date(), (long) 1,null));
	
	}
}
