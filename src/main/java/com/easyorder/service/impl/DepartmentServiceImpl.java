/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-25 10:28:54
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-30 16:47:31
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\DepartmentServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Department;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.DepartmentMapper;
import com.easyorder.mapper.StaffMapper;
import com.easyorder.service.DeparmentService;
import com.easyorder.util.BaseExecuteException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DepartmentServiceImpl implements DeparmentService{

    @Resource
    DepartmentMapper departmentMapper;
   
    @Resource
    StaffMapper staffMapper;
    
    @Override
    @Transactional
    public BaseExecution<Department> updateDepartment(Department department) throws BaseExecuteException{
        BaseExecution<Department> baseExecution=new BaseExecution<>();
        if(StringUtils.isNotEmpty(department.getDepartmentName())&&StringUtils.isNotEmpty(department.getDepartmentDesc()))
        {
            try {
                int effctedNum=departmentMapper.updateById(department);
                if(effctedNum<=0)
                {
                    throw new BaseExecuteException("更新0条信息");
                }
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTemp(department);
                return baseExecution;
            } catch (Exception e) {
                throw new BaseExecuteException("更新部门(department)失败:"+e.getMessage());
            }
        }else{
            throw new BaseExecuteException("更新部门(department)失败:请检查name、desc是否正确");
        }
        
    }


    @Override
    public BaseExecution<Department> selectDepartmentList(Department department) throws BaseExecuteException{
        
        QueryWrapper<Department> wrapper=new QueryWrapper<>();
        BaseExecution<Department> baseExecution=new BaseExecution<>();
        Long id=department.getDepartmentId();
        String name=department.getDepartmentName();
        String desc=department.getDepartmentDesc();
        wrapper.eq(null!=id, "department_id",id);
        wrapper.eq(StringUtils.isNotEmpty(name),"department_name",name);
        wrapper.eq(StringUtils.isNotEmpty(desc), "department_desc",desc);

        try {
            List<Department> departments=departmentMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(departments);
            baseExecution.setCount(Long.valueOf(departments.size()));
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("查询部门(deparment)失败:"+e.getMessage());
        }
    }


    @Transactional
    @Override
    public BaseExecution<Department> insertDepartment(Department department) throws BaseExecuteException{
        
        BaseExecution<Department> baseExecution=new BaseExecution<>();
        if(StringUtils.isNotEmpty(department.getDepartmentName())&&StringUtils.isNotEmpty(department.getDepartmentDesc()))
        {
            try {
                int effctedNum=departmentMapper.insert(department);
                if(effctedNum<=0)
                {
                    throw new BaseExecuteException("创建0条信息");
                }
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTemp(department);
                return baseExecution;
            } catch (Exception e) {
                throw new BaseExecuteException("创建部门(department)失败:"+e.getMessage());
            }
        }else{
            throw new BaseExecuteException("创建角色失败:请检查name,desc是否正确");
        }
    }


    @Transactional
    @Override
    public BaseExecution<Department> deleteDepartment(Department department) throws BaseExecuteException{
        
        BaseExecution<Department> baseExecution=new BaseExecution<>();
        try {
            Long l=staffMapper.findStaffIdByDepartmentId(department.getDepartmentId());
            //QueryWrapper<Staff> wrapper=new QueryWrapper<>();
            //Long departmentId=department.getDepartmentId();
            Long staffId=l;
            int e=1;
            //wrapper.eq(staffId!=null, "staff_id",staffId);
            if(staffId!=null)
            {
                e=staffMapper.deleteById(staffId);
            }
            
            int effctedNum=departmentMapper.deleteById(department);
            if(e<=0||effctedNum<=0)
            {
                throw new BaseExecuteException("删除0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(department);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("删除角部门(department)失败:"+e.getMessage());
        }
    }
    
    
    
    
}
