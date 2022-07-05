/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 11:37:54
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-05 09:28:22
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\mapper\RoleMenuMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.RoleMenu;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMenuMapper extends BaseMapper<RoleMenu>{
    
    @Select("SELECT role.role_id "+"FROM role"+" WHERE role.role_id=#{id} ")
    Long findRoleId(@Param("id")Long id);

    @Select("SELECT controller_menu.controller_menu_id "+"FROM controller_menu"+" WHERE controller_menu.controller_menu_id=#{id} ")
    Long findMenuId(@Param("id")Long id);


    @Select("SELECT role_menu.role_id "+"FROM role_menu,controller_menu "+" WHERE controller_menu.controller_menu_id=#{id} "+"AND role_menu.controller_menu_id=controller_menu.controller_menu_id")
    List<Long> findRoleIdListByControllerMenuId(@Param("id")Long id);

    @Select("SELECT role_menu.controller_menu_id "+"FROM role_menu,role "+" WHERE role.role_id=#{id} "+"AND role_menu.role_id=role.role_id")
    List<Long> findControllerMenuIdListByRoleId(@Param("id")Long id);
}
