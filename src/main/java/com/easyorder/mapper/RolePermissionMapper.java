/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 11:35:07
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-30 14:59:29
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\mapper\RolePermissionMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyorder.entity.RolePermission;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    @Select("SELECT role_permission.role_id "+"FROM role_permission,permission"+" WHERE role_permission.permission_id=#{id} "+"AND role_permission.permission_id=permission.permission_id")
    Long findRoleId(@Param("id")Long id);

    @Select("SELECT role_permission.permission_id "+"FROM role_permission,role"+" WHERE role_permission.role_id=#{id} "+"AND role_permission.role_id=role.role_id")
    Long findPermissionId(@Param("id")Long id);
// @Select("SELECT t_user.user_name " +
//             " FROM t_blog, t_user " +
//             " WHERE t_blog.id = #{id} " +
//             "     AND t_blog.user_id = t_user.id")
//     String findUserNameByBlogId(@Param("id") Long id);


// @Select("SELECT * " +
//             " FROM t_blog, t_user " +
//             " ${ew.customSqlSegment} ")
//     IPage<BlogVO> findBlog(IPage<BlogVO> page, @Param("ew") Wrapper wrapper);

// @Select("select * " + "From permission,role_permission"+"${ew.customSqlSegment}")
    // IPage<RolePermission> findRolePermission=(IPage<RolePermission> page,@Param("ew") Wrapper wrapper);
}