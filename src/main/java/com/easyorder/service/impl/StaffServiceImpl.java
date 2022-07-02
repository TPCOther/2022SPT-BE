/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:00:54
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-02 16:04:27
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\StaffServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Permission;
import com.easyorder.entity.Role;
import com.easyorder.entity.Staff;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.PermissionMapper;
import com.easyorder.mapper.RoleMapper;
import com.easyorder.mapper.RolePermissionMapper;
import com.easyorder.mapper.StaffMapper;
import com.easyorder.service.StaffService;
import com.easyorder.util.BaseExecuteException;
import com.easyorder.util.HttpServletRequestUtil;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.crypto.digest.BCrypt;
@Service
public class StaffServiceImpl implements StaffService{

    @Resource 
    StaffMapper staffMapper;

    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Resource
    PermissionMapper permissionMapper;
    
    @Resource
    RoleMapper roleMapper;
    @Override
    public BaseExecution<Staff> selectStaffList(Staff staff) throws BaseExecuteException{
        QueryWrapper<Staff> wrapper=new QueryWrapper<>();
        BaseExecution<Staff> baseExecution=new BaseExecution<>();
        Long staffId=staff.getStaffId();
        Long departmentId=staff.getDepartmentId();
        Long roleId=staff.getRoleId();
        String name=staff.getStaffName();
        String gender=staff.getStaffName();
        Integer salary=staff.getStaffSalary();
        String position=staff.getStaffPosition();
        Long phone=staff.getStaffPhone();
        String address=staff.getStaffAddress();
        Integer state=staff.getStaffState();
        String account=staff.getStaffAccount();
        String passward=staff.getStaffPassword();
        
        wrapper.eq(staffId!=null,"staff_id",staffId);
        wrapper.eq(departmentId!=null,"staff_id",departmentId);
        wrapper.eq(roleId!=null,"staff_id",roleId);
        wrapper.eq(StringUtils.isNotEmpty(name),"staff_name",name);
        wrapper.eq(StringUtils.isNotEmpty(gender),"staff_gender",gender);
        wrapper.eq(salary!=null,"staff_salary",salary);
        wrapper.eq(StringUtils.isNotEmpty(position),"staff_position",position);
        wrapper.eq(phone!=null,"staff_phone",phone);
        wrapper.eq(StringUtils.isNotEmpty(address),"staff_address",address);
        wrapper.eq(state!=null,"staff_state",state);
        wrapper.eq(StringUtils.isNotEmpty(account), "staff_account", account);
        wrapper.eq(StringUtils.isNotEmpty(passward), "staff_passward", passward);

        try {
            List<Staff> staffs=staffMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(staffs);
            baseExecution.setCount(Long.valueOf(staffs.size()));
            return baseExecution;

        } catch (Exception e) {
            throw new BaseExecuteException("查询员工(staff)失败:"+e.getMessage());
        }
    }

    @Override
    public BaseExecution<Staff> updateStaff(Staff staff) throws BaseExecuteException{
        BaseExecution<Staff> baseExecution=new BaseExecution<>();
        //TODO:员工更新信息为空判断
        if(StringUtils.isNotEmpty(staff.getStaffName())&&StringUtils.isNotEmpty(staff.getStaffGender())
        &&staff.getStaffSalary()!=null&&StringUtils.isNotEmpty(staff.getStaffPosition())&&staff.getStaffPhone()!=null
        &&StringUtils.isNotEmpty(staff.getStaffAccount())&&StringUtils.isNotEmpty(staff.getStaffPassword()))
        {
            // BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            // String encodePassword=bCryptPasswordEncoder.encode(staff.getStaffPassword());
            // staff.setStaffPassword(encodePassword);
            try {
                int effctedNum=staffMapper.updateById(staff);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("更新0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(staff);
            return baseExecution;
            } catch (Exception e) {
                throw new BaseExecuteException("更新员工(staff)失败:"+e.getMessage());
            }
        }else{
            throw new BaseExecuteException("更新员工(staff)失败:请检查name、gender、salary、position、phone、account、password是否正确");
        }
    }

    @Transactional
    @Override
    public BaseExecution<Staff> insertStaff(Staff staff) throws BaseExecuteException{
        
        BaseExecution<Staff> baseExecution=new BaseExecution<>();
        if(staff.getStaffState()==null)
        {
            staff.setStaffState(1);
        }
        Long long1=staffMapper.findDepartmentIdByDepartmentId(staff.getDepartmentId());
        
        Long long2=staffMapper.findRoleIdByRoleId(staff.getRoleId());
        if(long1!=null&&long2!=null)
        {
            if(StringUtils.isNotEmpty(staff.getStaffName())&&StringUtils.isNotEmpty(staff.getStaffGender())
            &&staff.getStaffSalary()!=null&&StringUtils.isNotEmpty(staff.getStaffPosition())&&staff.getStaffPhone()!=null
            &&StringUtils.isNotEmpty(staff.getStaffAccount())&&StringUtils.isNotEmpty(staff.getStaffPassword()))
            {

                String encodePassword=BCrypt.hashpw(staff.getStaffPassword());
                staff.setStaffPassword(encodePassword);
                try {
                    int effctedNum=staffMapper.insert(staff);
                    if(effctedNum<=0)
                    {
                        throw new BaseExecuteException("创建0条信息");
                    }
                    baseExecution.setEum(ExecuteStateEum.SUCCESS);
                    baseExecution.setTemp(staff);
                    return baseExecution;
                } catch (Exception e) {
                    throw new BaseExecuteException("创建员工(staff)失败:"+e.getMessage());
                }
            }else{
                throw new BaseExecuteException("创建员工(staff)失败:请检查name、gender、salary、position、phone是否正确");
            }
        }else{
            throw new BaseExecuteException("创建员工(staff)失败:请检查role_id,department_id是否存在");
        }   
        
    }

    @Override
    public BaseExecution<Long> login(HttpServletRequest request) {

        String account=HttpServletRequestUtil.getString(request, "staffAccount");
        String password=HttpServletRequestUtil.getString(request, "staffPassword");
        BaseExecution<Long> baseExecution=new BaseExecution<>();
        try {
            String string=staffMapper.findPasswordByAccount(account);
            Long staffId=staffMapper.findStaffIdByAccount(account);
            
            if(BCrypt.checkpw(password, string));
            {
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTemp(staffId);
                return baseExecution;
            }
        } catch (Exception e) {
            throw new BaseExecuteException("登录失败！请检查staff_account、staff_password是否存在");
        }

        // UserExample userExample = new UserExample();
        // UserExample.Criteria criteria = userExample.createCriteria();
        // criteria.andNumberEqualTo(user.getNumber());
        // List<User> users = userMapper.selectByExample(userExample);
        // if (CollectionUtils.isEmpty(users)){
        //     return 0;
        // }
        // User user1 = users.get(0);
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // if (encoder.matches(user.getPassword(), user1.getPassword())){
        //     return 1;
        // }
        // return 0;

        // try {
        //     List<Staff> staffs=staffMapper.selectList(wrapper);
        //     baseExecution.setEum(ExecuteStateEum.SUCCESS);
        //     baseExecution.setTList(staffs);
        //     baseExecution.setCount(Long.valueOf(staffs.size()));
        //     return baseExecution;

        // } catch (Exception e) {
        //     throw new BaseExecuteException("查询员工(staff)失败:"+e.getMessage());
        // }
    }

    // @Override
    // public BaseExecution<Role> test(Long id) {
    //     BaseExecution<Role> baseExecution=new BaseExecution<>();
    //     Long roleId=staffMapper.findRoleIdByStaffId(id);
    //     Long permissionId=rolePermissionMapper.findPermissionIdByRoleId(roleId);
    //     QueryWrapper<Permission> wrapper=new QueryWrapper<>();
    //     wrapper.eq(permissionId!=null,"permission_id",permissionId);
    //     List<Permission> permissions=permissionMapper.selectList(wrapper);

    //     String roleName=roleMapper.findRoleName(roleId);
    //     // QueryWrapper<Role> wrapper2=new QueryWrapper<>();
    //     // wrapper2.eq(StringUtils.isNotEmpty(roleName),"role_name",roleName);
    //     // List<Role> roles=roleMapper.selectList(wrapper2);
        
    //     baseExecution.setEum(ExecuteStateEum.SUCCESS);
    //     baseExecution.setTemp(roleName);
    //     baseExecution.setCount(Long.valueOf(permissions.size()));
    //     return baseExecution;
    // }

    // @Override
    // public BaseExecution<Staff> deleteStaff(Staff staff) throws BaseExecuteException{
        
        
    //     BaseExecution<Staff> baseExecution=new BaseExecution<>();
    //     try {
    //         int effctedNum=staffMapper.deleteById(staff);
    //         if(effctedNum<=0)
    //         {
    //             throw new BaseExecuteException("删除0条信息");
    //         }
    //         baseExecution.setEum(ExecuteStateEum.SUCCESS);
    //         baseExecution.setTemp(staff);
    //         return baseExecution;
    //     } catch (Exception e) {
    //         throw new BaseExecuteException("删除员工(staff)失败:"+e.getMessage());
    //     }
        
    // }
    
}
