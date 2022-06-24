package com.easyorder.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DinTable {
    @TableId(type = IdType.AUTO)
    private Long dinTableId;
    
    private Integer dinTableState;
    private Integer dinTableCapacity;

}
