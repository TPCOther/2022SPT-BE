package com.easyorder.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Booking;
import com.easyorder.service.BookingService;
import com.easyorder.util.HttpServletRequestUtil;
import com.easyorder.util.RBody;

@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// @SuppressWarnings("all") 用于忽略报错
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Resource
    private BookingService bookingService;
    

    @PostMapping("/select")
    @RequiresPermissions("booking:select")
    public RBody bookingSelect(@RequestBody Booking selectBooking){
        RBody rbody = new RBody();
        BaseExecution<Booking> be = new BaseExecution<Booking>();
        try{
            be = this.bookingService.selectBookingList(selectBooking);
            rbody=RBody.ok().data(be.getTList());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/insert")
    @RequiresPermissions("booking:insert")
    public RBody bookingInsert(HttpServletRequest request){
        RBody rbody = new RBody();
        Booking insertBooking = new Booking();
        BaseExecution<Booking> be = new BaseExecution<Booking>();
        //需要传入表单
        insertBooking.setDinTableId(HttpServletRequestUtil.getLong(request, "dinTableId"));
        insertBooking.setBookingName(HttpServletRequestUtil.getString(request, "bookingName"));
        insertBooking.setBookingPhone(HttpServletRequestUtil.getInt(request, "bookingPhone"));
        insertBooking.setStartTime(new Date(HttpServletRequestUtil.getLong(request, "startTime")));
        try{
            be = this.bookingService.insertBooking(insertBooking);
            rbody=RBody.ok().data(be.getTemp().getBookingId());
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/update")
    @RequiresPermissions("booking:update")
    public RBody bookingUpdate(@RequestBody Booking insertBooking){
        RBody rbody = new RBody();
        try{
            this.bookingService.updateBooking(insertBooking);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }

    @PostMapping("/delete")
    @RequiresPermissions("booking:delete")
    public RBody bookingDelete(@RequestBody Booking insertBooking){
        RBody rbody = new RBody();
        try{
            this.bookingService.deleteBooking(insertBooking);
            rbody=RBody.ok();
        }catch(Exception e){
            rbody=RBody.error(e.toString());
        }
        return rbody;
    }


}
