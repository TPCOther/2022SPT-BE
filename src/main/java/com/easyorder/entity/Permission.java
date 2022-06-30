/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 11:35:07
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 16:29:24
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\entity\Permission.java
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
@TableName("permission")
public class Permission {
	@TableId(type=IdType.AUTO)
	Long permissionId;
	String permissionName;
	String permissionUrl;
	String permissionDesc;
	Integer permissionState;
	public Permission()
	{
		
	}
}
