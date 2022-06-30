package com.easyorder.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Customer;
import com.easyorder.service.CustomerService;
import com.easyorder.util.RBody;


@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping("/select")
    public RBody customerSelect(@RequestBody Customer selectTable){
        RBody rbody = new RBody();
        BaseExecution<Customer> be = new BaseExecution<Customer>();
        try{
            be = this.customerService.selectCustomerList(selectTable);
            rbody=RBody.ok().data(be.getTList());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }


    @PostMapping("/insert")
    public RBody tableInsert(@RequestBody Customer insertTable){
        RBody rbody = new RBody();
        BaseExecution<Customer> be = new BaseExecution<Customer>();
        try{
            be = this.customerService.insertCustomer(insertTable);
            rbody=RBody.ok().data(be.getTemp().getCustomerId());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/update")
    public RBody tableUpdate(@RequestBody Customer insertTable){
        RBody rbody = new RBody();
        try{
            this.customerService.updateCustomer(insertTable);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/delete")
    public RBody tableDelete(@RequestBody Customer insertTable){
        RBody rbody = new RBody();
        try{
            this.customerService.deleteCustomer(insertTable);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }
}
