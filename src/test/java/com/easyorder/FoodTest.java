package com.easyorder;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.easyorder.entity.Food;
import com.easyorder.enums.FoodStateEum;
import com.easyorder.mapper.FoodMapper;

public class FoodTest extends BaseTest {
	@Resource
	FoodMapper fm;

	@Test
	public void foodInsert() {
		fm.insert(new Food(null, "回锅肉", "test", "test", 12.0f, 11.0f, FoodStateEum.SALING.getState(), "test", 1, new Date(), new Date(), (long) 1,null));
	
	}
}
