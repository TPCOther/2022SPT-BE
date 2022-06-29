/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 16:28:45
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 15:32:30
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\PermissionService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;



import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Permission;
import com.easyorder.util.BaseExecuteException;

public interface PermissionService {
    BaseExecution<Permission> selectPermissionList(Permission permission)throws BaseExecuteException;
    BaseExecution<Permission> updatePermission(Permission permission);
    BaseExecution<Permission> insertPermission(Permission permission);
    BaseExecution<Permission> deletePermission(Permission permission);
}
