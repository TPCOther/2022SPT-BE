/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 15:03:46
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 16:02:42
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\entity\ControllerMenu.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("controller_menu")
public class ControllerMenu {
    @TableId(type=IdType.AUTO)
    Long controllerMenuId;
    String controllerMenuName;
    String controllerMenuDesc;
    public ControllerMenu()
    {

    }
}


