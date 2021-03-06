/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 11:35:07
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-02 10:27:01
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\mapper\StaffMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.Staff;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StaffMapper extends BaseMapper<Staff>{
    
    @Select("SELECT staff.staff_id "+"FROM staff,role "+" WHERE staff.role_id=#{id} "+"AND staff.role_id=role.role_id")
    Long findStaffIdByRoleId(@Param("id")Long id);

    @Select("SELECT staff.staff_id "+"FROM staff,department "+" WHERE staff.department_id=#{id} "+"AND staff.department_id=department.department_id")
    Long findStaffIdByDepartmentId(@Param("id")Long id);

    @Select("SELECT department.department_id "+"FROM department "+" WHERE department.department_id=#{id} ")
    Long findDepartmentIdByDepartmentId(@Param("id")Long id);

    @Select("SELECT role.role_id "+"FROM role "+" WHERE role.role_id=#{id} ")
    Long findRoleIdByRoleId(@Param("id")Long id);

    @Select("SELECT staff.department_id "+"FROM staff"+" WHERE staff.staff_id=#{id} ")
    Long findDepartmentIdByStaffId(@Param("id")Long id);

    @Select("SELECT staff.role_id "+"FROM staff"+" WHERE staff.staff_id=#{id} ")
    Long findRoleIdByStaffId(@Param("id")Long id);

    @Select("SELECT staff.staff_password "+"FROM staff "+"WHERE staff.staff_account=#{account} ")
    String findPasswordByAccount(@Param("account") String account);

    @Select("SELECT staff.staff_Id "+"FROM staff "+"WHERE staff.staff_account=#{account} ")
    Long findStaffIdByAccount(@Param("account") String account);
}
