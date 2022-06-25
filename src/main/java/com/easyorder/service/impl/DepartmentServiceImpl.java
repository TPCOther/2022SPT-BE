/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-25 10:28:54
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @LastEditTime: 2022-06-25 10:40:34
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\DepartmentServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.easyorder.entity.Department;
import com.easyorder.mapper.DepartmentMapper;
import com.easyorder.service.DeparmentService;

import org.springframework.stereotype.Service;


@Service
public class DepartmentServiceImpl implements DeparmentService{

    @Resource
    DepartmentMapper departmentMapper;
    @Override
    public List<Department> getDepartmentList() {
        List<Department> departments=departmentMapper.selectList(null);
        return departments;
    }
    @Override
    public void updateDepartment(Department department) {
        // TODO Auto-generated method stub
        departmentMapper.updateById(department);
    }
    
    
    
}
