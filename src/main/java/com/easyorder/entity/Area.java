package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("area")
public class Area {
    @TableId(type = IdType.AUTO)
    private Long areaId;

    private String areaName;
    private String areaDesc;
}
