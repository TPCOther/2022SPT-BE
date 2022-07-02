/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 16:48:36
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 15:03:16
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\RolePermissionService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;



import javax.servlet.http.HttpServletRequest;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.RolePermission;
import com.easyorder.util.BaseExecuteException;

public interface RolePermissionService {
    BaseExecution<RolePermission> selectRolePermissionList(RolePermission rolePermission) throws BaseExecuteException;
    BaseExecution<RolePermission> updateRolePermission(HttpServletRequest request) throws BaseExecuteException;
    BaseExecution<RolePermission> insertRolePermission(RolePermission rolePermission) throws BaseExecuteException;
    BaseExecution<RolePermission> deleteRolePermission(RolePermission rolePermission) throws BaseExecuteException;
}
