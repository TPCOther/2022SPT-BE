package com.easyorder.dto;

import java.util.List;

import com.easyorder.enums.ExecuteStateEum;

import lombok.Data;

@Data
public class BaseExecution<T> {
	private ExecuteStateEum eum;
	private List<T> TList;
	private T temp;
	private Long count;
	public BaseExecution() {}
	public BaseExecution(ExecuteStateEum eum) {
		this.eum=eum;
	}
	public BaseExecution(ExecuteStateEum eum,T t) {
		this.eum=eum;
		this.temp=t;
	}
	public BaseExecution(ExecuteStateEum eum,List<T>list) {
		this.eum=eum;
		this.TList=list;
	}
}
