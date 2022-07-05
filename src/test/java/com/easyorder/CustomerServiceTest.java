package com.easyorder;



import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.easyorder.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyorderApplication.class)
public class CustomerServiceTest {
	@Resource
	public	CustomerService cs;
	@Test
	@Ignore
	public void testInsertBath() {
//		Customer customer1=new Customer(null, "sh3rlock", null, "test1", "test", "te", 0, 0, 0, new Date(), new Date());
//		Customer customer2=new Customer(null, "sh3rlock", null, "test1", "test", "te", 0, 0, 0, new Date(), new Date());
//		customer1.setOpenId("test2");
//		customer2.setOpenId("test3");
//		List<Customer> customerList=new ArrayList<>();
//		customerList.add(customer1);
//		customerList.add(customer2);
//		cs.saveBatch(customerList);
	}
	@Test
	public void testSelect() {
		Integer a=1;
		int b=1;
		if(a==b) System.out.print(1);
		else System.out.print(2);
	}
}
