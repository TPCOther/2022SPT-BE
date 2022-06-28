/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 16:30:30
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 16:41:55
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\PermissionServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.easyorder.service.PermissionService;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.entity.Permission;
import com.easyorder.mapper.PermissionMapper;
@Service
public class PermissionServiceImpl implements PermissionService{

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionList(Permission permission) {
        // TODO Auto-generated method stub
        QueryWrapper<Permission> wrapper=new QueryWrapper<>();

        Long id=permission.getPermissionId();
        String name=permission.getPermissionName();
        String url=permission.getPermissionUrl();
        String desc=permission.getPermissionDesc();
        Integer state=permission.getPermissionState();

        wrapper.eq(id!=null, "permission_id",id);
        wrapper.eq(StringUtils.isNotEmpty(name), "permission_name", name);
        wrapper.eq(StringUtils.isNotEmpty(url), "permission_url", url);
        wrapper.eq(StringUtils.isNotEmpty(desc), "permission_desc", desc);
        wrapper.eq(state!=null, "permission_state",state);

        List<Permission> permissions=permissionMapper.selectList(wrapper);
        return permissions;
    }

    @Override
    public void updatePermission(Permission permission) {
        // TODO Auto-generated method stub
        if(permission.getPermissionId()!=null)
        {
            permissionMapper.updateById(permission);
        }
    }

    @Override
    public void insertPermission(Permission permission) {
        // TODO Auto-generated method stub
        if(permission.getPermissionId()!=null)
        {
            permissionMapper.insert(permission);
        }
    }

    @Override
    public void deletePermission(Permission permission) {
        // TODO Auto-generated method stub
        if(permission.getPermissionId()!=null)
        {
            permissionMapper.deleteById(permission);
        }
    }
    
}
