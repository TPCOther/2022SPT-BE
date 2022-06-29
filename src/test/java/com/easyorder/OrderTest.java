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

public class OrderTest extends BaseTest {
	@Autowired
	OrderService os;
	@Test
	public void insert() {
		Order o=new Order();
		o.setCreateTime(new Date());
		o.setTableId(1L);
		o.setCustomerId(1L);
		o.setOrderDesc("test1");
		o.setOrderEvalution("test2");
		o.setOrderState(1);
		o.setStaffId(1L);
		List<OrderFood> l=new ArrayList<>();
		l.add(new OrderFood(null, 3L, 1, 1));
		l.add(new OrderFood(null, 2L, 1, 1));
		l.add(new OrderFood(null, 4L, 1, 1));
		o.setOrderFoodList(l);
		BaseExecution<Order> be=os.insertOrder(o);
		if(be.getEum()==ExecuteStateEum.SUCCESS)
			System.out.print(be.getEum().getStateInfo());
		else System.out.print(be.getStateInfo());
	}
	@Test
	@Ignore
	public void update() {
		
	}
}
