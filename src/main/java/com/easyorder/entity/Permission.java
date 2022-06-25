package com.easyorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("permission")
public class Permission {
	@TableId(type=IdType.AUTO)
	Long permissionId;
	String permissionName;
	String permissionUrl;
	String permissionDesc;
	Integer permissionState;
	
}
