package com.easyorder.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("role")
public class Role {
	@TableId(type = IdType.AUTO)
	Long roleId;
	String roleName;
	String roleDesc;
}
