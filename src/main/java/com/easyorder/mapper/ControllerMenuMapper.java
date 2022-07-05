/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 15:07:39
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-05 09:00:50
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\mapper\ControllerMenuMapper.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyorder.entity.ControllerMenu;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ControllerMenuMapper extends BaseMapper<ControllerMenu>{
    @Select("SELECT controller_menu.controller_menu_name " +
            " FROM controller_menu" +
            " WHERE controller_menu_id = #{id}")
    String findControllerMenuName(@Param("id") Long id);
}




