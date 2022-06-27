package com.easyorder.dto;

import java.util.List;

import com.easyorder.entity.Food;
import com.easyorder.enums.FoodStateEum;

import lombok.Data;

@Data
public class FoodExecution {
	private int state;
	private String stateInfo;
	private List<Food> foodList;
	private Food food;
	private int count;
	public FoodExecution() {}
	public FoodExecution(FoodStateEum foodStateEum) {
		state=foodStateEum.getState();
		stateInfo=foodStateEum.getStateInfo();
	}
	public FoodExecution(FoodStateEum foodStateEum,Food food) {
		state=foodStateEum.getState();
		stateInfo=foodStateEum.getStateInfo();
	}
	public FoodExecution(FoodStateEum foodStateEum,List<Food>foodList) {
		state=foodStateEum.getState();
		stateInfo=foodStateEum.getStateInfo();
		this.foodList=foodList;
	}
}
