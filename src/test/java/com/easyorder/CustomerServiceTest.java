package com.easyorder;



import java.util.Date;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.easyorder.entity.Customer;
import com.easyorder.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyorderApplication.class)
public class CustomerServiceTest {
	@Resource
	public	CustomerService cs;
	@Test
	@Ignore
	public void testInsert() {
		Customer customer=new Customer(null, "sh3rlock", null, "test", "test", "te", 0, 0, 0, new Date(), new Date());
		int x=cs.insert(customer);
		 System.out.print(x);
	}
	@Test
	public void testSelect() {
		System.out.print(cs.findCustomerInfoById(1));
	}
}
