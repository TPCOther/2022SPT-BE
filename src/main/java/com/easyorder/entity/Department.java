/*
 * @Author: error: git config user.name && git config user.email & please set dead value or install git
 * @Date: 2022-06-25 10:23:31
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-25 16:46:57
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\entity\Department.java
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
@TableName("department")
public class Department {
    @TableId(type = IdType.AUTO)
    private Long departmentId;
    private String departmentName;
    private String departmentDesc;
    public Department()
    {
        
    }
}
