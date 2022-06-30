// /*
//  * @Author: 123456 2373464672@qq.com
//  * @Date: 2022-06-28 15:27:57
//  * @LastEditors: 123456 2373464672@qq.com
//  * @LastEditTime: 2022-06-28 15:36:17
//  * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\controller\StaffController.java
//  * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
//  */
// package com.easyorder.controller;

// import javax.annotation.Resource;

// import com.easyorder.entity.Staff;
// import com.easyorder.service.StaffService;
// import com.google.gson.Gson;

// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求
// // @SuppressWarnings("all") 用于忽略报错
// @RestController
// public class StaffController {
//     @Resource
//     StaffService staffService;
//     Gson gson=new Gson();
    
//     @PostMapping("/selectStaff")
//     public String selectStaff(@RequestBody Staff staff)
//     {
//         return gson.toJson(staffService.getStaffList(staff));
//     }

//     @PostMapping("/updateHeadline")
//     public String updateHeadline(@RequestBody Staff staff)
//     {
        
//         staffService.updateStaff(staff);
//         Staff staff2=new Staff();
//         return gson.toJson(staffService.getStaffList(staff2));
//     }

//     @PostMapping("/insertHeadline")
//     public String insertHeadline(@RequestBody Staff staff)
//     {
        
//         staffService.insertStaff(staff);
//         Staff staff2=new Staff();
//         return gson.toJson(staffService.getStaffList(staff2));
//     }

//     @PostMapping("/deleteDepartment")
//     public String deleteDepartment(@RequestBody Staff staff)
//     {
//         staffService.deleteStaff(staff);
//         Staff staff2=new Staff();
//         return gson.toJson(staffService.getStaffList(staff2));
//     }
// }
