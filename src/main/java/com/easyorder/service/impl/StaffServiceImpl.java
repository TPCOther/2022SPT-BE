/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:00:54
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-30 17:36:25
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\StaffServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Staff;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.enums.StaffStateEum;
import com.easyorder.mapper.StaffMapper;
import com.easyorder.service.StaffService;
import com.easyorder.util.BaseExecuteException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class StaffServiceImpl implements StaffService{

    @Resource 
    StaffMapper staffMapper;

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
        Integer phone=staff.getStaffPhone();
        String address=staff.getStaffAddress();
        Integer state=staff.getStaffState();

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
        if(StringUtils.isNotEmpty(staff.getStaffName())&&StringUtils.isNotEmpty(staff.getStaffGender())
        &&staff.getStaffSalary()!=null&&StringUtils.isNotEmpty(staff.getStaffPosition())&&staff.getStaffPhone()!=null)
        {
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
            throw new BaseExecuteException("创建员工(staff)失败:请检查name、gender、salary、position、phone是否正确");
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
        //TODU
        //department_id  exist
        //role_id exist
        if(StringUtils.isNotEmpty(staff.getStaffName())&&StringUtils.isNotEmpty(staff.getStaffGender())
        &&staff.getStaffSalary()!=null&&StringUtils.isNotEmpty(staff.getStaffPosition())&&staff.getStaffPhone()!=null)
        {
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
        
        
    }

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
