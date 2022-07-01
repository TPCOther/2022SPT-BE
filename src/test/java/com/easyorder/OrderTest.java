package com.easyorder;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Order;
import com.easyorder.entity.OrderFood;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.service.OrderService;
import com.google.gson.Gson;

public class OrderTest extends BaseTest {
	@Autowired
	OrderService os;
	@Autowired
	Gson gson;
	@Test
	@Ignore
	public void insert() {
		Order o=new Order();
		o.setCreateTime(new Date());
		o.setDinTableId(1L);
		o.setCustomerId(1L);	
		o.setOrderDesc("test1");
		o.setOrderEvaluation("test2");
		o.setOrderState(1);
		o.setStaffId(1L);
		o.setOrderAmount(10f);
		List<OrderFood> l=new ArrayList<>();
		l.add(new OrderFood(null, 3L, 1, 1));
		l.add(new OrderFood(null, 2L, 1, 1));
		l.add(new OrderFood(null, 4L, 1, 1));
		o.setOrderFoodList(l);

		BaseExecution<Order> be=os.insertOrder(o);
		if(be.getEum()==ExecuteStateEum.SUCCESS)
			System.out.println(be.getEum().getStateInfo());
		else System.out.println(be.getStateInfo());
	}
	@Test
	@Ignore
	public void update() {
		Order o=new Order();
		o.setOrderId(7L);
		o.setCreateTime(new Date());
		o.setDinTableId(1L);
		o.setCustomerId(1L);	
		o.setOrderDesc("test1");
		o.setOrderEvaluation("test2");
		o.setOrderState(1);
		o.setStaffId(1L);
		o.setOrderAmount(10f);
		List<OrderFood> l=new ArrayList<>();
		l.add(new OrderFood(7L, 3L, 1,2));
		l.add(new OrderFood(7L, 2L, 1, 3));
		o.setOrderFoodList(l);
		BaseExecution<Order> be=os.updateOrder(o);
		if(be.getEum()==ExecuteStateEum.SUCCESS)
			System.out.println(be.getEum().getStateInfo());
		else System.out.println(be.getStateInfo());
	}
	@Test
	public void select() {
		Order o=new Order();
		o.setOrderId(7L);
		BaseExecution<Order> be=os.selectOrder(o,-1,-1);
		if(be.getEum()==ExecuteStateEum.SUCCESS)
			System.out.println(gson.toJson(be.getTList()));
		else System.out.println(be.getStateInfo());
	}
}
