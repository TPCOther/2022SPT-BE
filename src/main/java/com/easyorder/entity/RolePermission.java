package com.easyorder.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("role_permission")
public class RolePermission {
	@MppMultiId
	Long roleId;
	@MppMultiId
	Long permissionId;
}
