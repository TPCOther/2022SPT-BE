/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 11:35:07
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 16:20:26
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\mapper\RoleMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.mapper;

import com.easyorder.entity.Role;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper extends MppBaseMapper<Role> {

    @Select("SELECT staff.staff_id " +
            " FROM staff, role " +
            " WHERE staff_id = #{id} " +
            "     AND staff.stafff_id = role.role_id")
    Long findUserNameByBlogId(@Param("role_id") Long id);

}
