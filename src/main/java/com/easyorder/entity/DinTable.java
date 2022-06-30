package com.easyorder.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("din_table")
public class DinTable {
    @TableId(type = IdType.AUTO)
    private Long dinTableId;
    
    private Long areaId;
    private String dinTableName;

    private Integer dinTableState;
    private Integer dinTableCapacity;

    @TableField(exist =false)
    private String areaName;

    public DinTable(){}
    

}
