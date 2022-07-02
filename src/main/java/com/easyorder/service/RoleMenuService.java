/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 11:34:12
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 14:35:14
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\RoleMenuService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;

import javax.servlet.http.HttpServletRequest;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.RoleMenu;
import com.easyorder.util.BaseExecuteException;

public interface RoleMenuService {
    BaseExecution<RoleMenu> selectRoleMenuList(RoleMenu roleMenu) throws BaseExecuteException;
    BaseExecution<RoleMenu> updateRoleMenu(HttpServletRequest request) throws BaseExecuteException;
    BaseExecution<RoleMenu> insertRoleMenu(RoleMenu roleMenu) throws BaseExecuteException;
    BaseExecution<RoleMenu> deleteRoleMenu(RoleMenu roleMenu) throws BaseExecuteException;
}
