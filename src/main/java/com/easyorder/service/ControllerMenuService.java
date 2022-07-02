/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 15:16:36
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 15:33:38
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\ControllerMenuService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;

import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.ControllerMenu;
import com.easyorder.util.BaseExecuteException;

public interface ControllerMenuService {
    BaseExecution<ControllerMenu> selectControllerMenuList(ControllerMenu controllerMenu) throws BaseExecuteException;
    BaseExecution<ControllerMenu> updateControllerMenu(ControllerMenu controllerMenu) throws BaseExecuteException;
    BaseExecution<ControllerMenu> insertControllerMenu(ControllerMenu controllerMenu) throws BaseExecuteException;
    BaseExecution<ControllerMenu> deleteControllerMenu(ControllerMenu controllerMenu) throws BaseExecuteException;
}
