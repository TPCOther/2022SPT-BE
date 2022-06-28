/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 16:28:45
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 16:30:15
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\PermissionService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;

import java.util.List;

import com.easyorder.entity.Permission;

public interface PermissionService {
    List<Permission> getPermissionList(Permission permission);
    void updatePermission(Permission permission);
    void insertPermission(Permission permission);
    void deletePermission(Permission permission);
}
