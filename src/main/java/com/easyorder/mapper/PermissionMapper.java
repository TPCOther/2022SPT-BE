package com.easyorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.Permission;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT permission.permission_id " +
            " FROM permission, role " +
            " WHERE role_id = #{id} " +
            "     AND permission.permission_id = role.role_id")
    Long findUserNameByBlogId(@Param("role_id") Long id);
}
