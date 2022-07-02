/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 11:35:07
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-02 16:46:59
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\mapper\RolePermissionMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.RolePermission;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    @Select("SELECT role.role_id "+"FROM role"+" WHERE role.role_id=#{id} ")
    Long findRoleId(@Param("id")Long id);

    @Select("SELECT permission.permission_id "+"FROM permission"+" WHERE permission.permission_id=#{id} ")
    Long findPermissionId(@Param("id")Long id);

    @Select("SELECT role_permission.role_id "+"FROM role_permission,permission "+" WHERE permission.permission_id=#{id} "+"AND role_permission.permission_id=permission.permission_id")
    List<Long> findRoleIdListByPermissionId(@Param("id")Long id);

    @Select("SELECT role_permission.permission_id "+"FROM role_permission,role "+" WHERE role.role_id=#{id} "+"AND role_permission.role_id=role.role_id")

    Long findPermissionIdByRoleId(@Param("id")Long id);

    @Select("SELECT role_permission.permission_id "+"FROM role_permission,role "+" WHERE role.role_id=#{id} "+"AND role_permission.role_id=role.role_id")
    List<Long> findPermissionIdListByRoleId(@Param("id")Long id);



}