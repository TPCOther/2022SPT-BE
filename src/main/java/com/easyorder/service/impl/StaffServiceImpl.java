// /*
//  * @Author: 123456 2373464672@qq.com
//  * @Date: 2022-06-28 15:00:54
//  * @LastEditors: 123456 2373464672@qq.com
//  * @LastEditTime: 2022-06-28 15:27:41
//  * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\StaffServiceImpl.java
//  * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
//  */
// package com.easyorder.service.impl;

// import java.util.List;

// import javax.annotation.Resource;

// import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
// import com.baomidou.mybatisplus.core.toolkit.StringUtils;
// import com.easyorder.entity.Staff;
// import com.easyorder.mapper.StaffMapper;
// import com.easyorder.service.StaffService;

// public class StaffServiceImpl implements StaffService{

//     @Resource 
//     StaffMapper staffMapper;

//     @Override
//     public List<Staff> getStaffList(Staff staff) {
//         // TODO Auto-generated method stub
//         QueryWrapper<Staff> wrapper=new QueryWrapper<>();
//         Long staffId=staff.getStaffId();
//         Long departmentId=staff.getDepartmentId();
//         Long roleId=staff.getRoleId();
//         String name=staff.getStaffName();
//         String gender=staff.getStaffName();
//         Integer salary=staff.getStaffSalary();
//         String position=staff.getStaffPosition();
//         Integer phone=staff.getStaffPhone();
//         String address=staff.getStaffAddress();
//         Integer state=staff.getStaffState();

//         wrapper.eq(staffId!=null,"staff_id",staffId);
//         wrapper.eq(departmentId!=null,"staff_id",departmentId);
//         wrapper.eq(roleId!=null,"staff_id",roleId);
//         wrapper.eq(StringUtils.isNotEmpty(name),"staff_name",name);
//         wrapper.eq(StringUtils.isNotEmpty(gender),"staff_gender",gender);
//         wrapper.eq(salary!=null,"staff_salary",salary);
//         wrapper.eq(StringUtils.isNotEmpty(position),"staff_position",position);
//         wrapper.eq(phone!=null,"staff_phone",phone);
//         wrapper.eq(StringUtils.isNotEmpty(address),"staff_address",address);
//         wrapper.eq(state!=null,"staff_state",state);
        
//         List<Staff> staffs=staffMapper.selectList(wrapper);
//         return staffs;
//     }

//     @Override
//     public void updateStaff(Staff staff) {
//         // TODO Auto-generated method stub
//         if(staff.getStaffId()!=null)
//         {
//             staffMapper.updateById(staff);
//         }
//     }

//     @Override
//     public void insertStaff(Staff staff) {
//         // TODO Auto-generated method stub
//         if(staff.getStaffId()!=null)
//         {
//             staffMapper.insert(staff);
//         }
        
//     }

//     @Override
//     public void deleteStaff(Staff staff) {
//         // TODO Auto-generated method stub
//         if(staff.getStaffId()!=null)
//         {
//             staffMapper.deleteById(staff);
//         }
        
//     }
    
// }
