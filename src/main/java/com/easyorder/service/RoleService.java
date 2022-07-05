/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:44:31
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-05 09:01:24
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\RoleService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;


import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Role;
import com.easyorder.util.BaseExecuteException;
public interface RoleService {
    BaseExecution<Role> selectRoleList(Role role) throws BaseExecuteException;
    BaseExecution<Role> updateRole(Role role) throws BaseExecuteException;
    BaseExecution<Role> insertRole(Role role) throws BaseExecuteException;
    BaseExecution<Role> deleteRole(Role role) throws BaseExecuteException;
    BaseExecution<String> getControllerMenuNameListById(Long staffId) throws BaseExecuteException;
    BaseExecution<String> getRoleNameByStaffId(Long staffId) throws BaseExecuteException;
}
