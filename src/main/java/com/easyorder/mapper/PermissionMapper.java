/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 11:35:07
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 15:21:19
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\mapper\PermissionMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.Permission;

import io.lettuce.core.dynamic.annotation.Param;



public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("SELECT permission.permission_url " +
            " FROM permission" +
            " WHERE permission_id = #{id}")
    String findPermissionUrl(@Param("id") Long id);
}
