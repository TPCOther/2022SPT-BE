/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 14:57:43
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-30 17:40:02
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\StaffService.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service;



import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Staff;
import com.easyorder.util.BaseExecuteException;

public interface StaffService {
    BaseExecution<Staff> selectStaffList(Staff staff) throws BaseExecuteException;
    BaseExecution<Staff> updateStaff(Staff staff) throws BaseExecuteException;
    BaseExecution<Staff> insertStaff(Staff staff) throws BaseExecuteException;
    // BaseExecution<Staff> deleteStaff(Staff staff) throws BaseExecuteException;

}
