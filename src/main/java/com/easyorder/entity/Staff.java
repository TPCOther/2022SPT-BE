package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("staff")
public class Staff {
    @TableId(type = IdType.AUTO)
    private Long staffId;

    private Long departmentId;
    private Long roleId;

    private String staffName;
    private String staffGender;
    private Integer staffSalary; 
    private String staffPosition;
    private Integer staffPhone;
    private String staffAddress;
    private Integer staffState;
    private String staffAccount;
    private String staffPassword;
    
    
}
