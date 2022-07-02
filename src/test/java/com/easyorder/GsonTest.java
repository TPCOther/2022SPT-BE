package com.easyorder;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.easyorder.entity.FoodImg;
import com.easyorder.mapper.FoodImgMapper;
import com.google.gson.Gson;

public class GsonTest extends BaseTest {
	@Resource
	Gson gson;
	@Resource
	FoodImgMapper fim;
	@Test
	public void gsonTest() {
		List<FoodImg> l=fim.selectList(null);
		System.out.println(gson.toJson(l));
	}
}
