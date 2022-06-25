/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-25 10:28:54
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-25 17:01:38
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\DepartmentServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.entity.Department;

import com.easyorder.mapper.DepartmentMapper;
import com.easyorder.service.DeparmentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DepartmentServiceImpl implements DeparmentService{

    @Resource
    DepartmentMapper departmentMapper;
   
    @Override
    @Transactional
    public void updateDepartment(Department department) {
        // TODO Auto-generated method stub
        if(department.getDepartmentId()!=null)
        {
            departmentMapper.updateById(department);
        }
        
    }


    @Override
    public List<Department> getDepartmentList(Department department) {
        // TODO Auto-generated method stub
        QueryWrapper<Department> wrapper=new QueryWrapper<>();
        Long id=department.getDepartmentId();
        String name=department.getDepartmentName();
        String desc=department.getDepartmentDesc();
        wrapper.eq(null!=id, "department_id",id);
        wrapper.eq(StringUtils.isNotEmpty(name),"department_name",name);
        wrapper.eq(null!=desc, "department_desc",desc);
        List<Department> departments=departmentMapper.selectList(wrapper);
        return departments;
    }


    @Override
    public void insertDepartment(Department department) {
        // TODO Auto-generated method stub
        if(department.getDepartmentId()!=null)
        {
            departmentMapper.insert(department);
        }
    }


    @Override
    public void deleteDepartment(Department department) {
        // TODO Auto-generated method stub
        if(department.getDepartmentId()!=null)
        {
            departmentMapper.deleteById(department.getDepartmentId());
        }
    }
    
    
    
    
}
