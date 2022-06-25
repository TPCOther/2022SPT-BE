package com.easyorder.entity;


import com.baomidou.mybatisplus.annotation.IdType;
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

    private Integer dinTableState;
    private Integer dinTableCapacity;

}
