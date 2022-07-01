/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 11:32:12
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 16:16:53
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\entity\RoleMenu.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("role_menu")
public class RoleMenu {
    @MppMultiId
	@TableField(value ="role_id")
	Long roleId;

	@MppMultiId
	@TableField(value ="controller_menu_id")
	Long controllerMenuId;
}
