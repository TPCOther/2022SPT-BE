package com.easyorder.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("role_permission")
public class RolePermission {
	@TableId
	Long roleId;
	@TableId
	Long permissionId;
}
