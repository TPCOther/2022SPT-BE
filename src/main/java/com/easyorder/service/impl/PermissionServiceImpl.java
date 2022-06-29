/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 16:30:30
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-29 17:04:14
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\PermissionServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.easyorder.service.PermissionService;
import com.easyorder.util.BaseExecuteException;

import org.apache.catalina.valves.rewrite.Substitution.RewriteRuleBackReferenceElement;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Headline;
import com.easyorder.entity.Permission;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.PermissionMapper;
@Service
public class PermissionServiceImpl implements PermissionService{

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public BaseExecution<Permission> selectPermissionList(Permission permission) throws BaseExecuteException{
        // TODO Auto-generated method stub
        QueryWrapper<Permission> wrapper=new QueryWrapper<>();
        BaseExecution<Permission> baseExecution=new BaseExecution<>();
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

        try {
            List<Permission> permissions=permissionMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(permissions);
            baseExecution.setCount(Long.valueOf(permissions.size()));
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("查询权限(Permission)失败:"+e.getMessage());
        }
    }

    @Override
    public BaseExecution<Permission> updatePermission(Permission permission) throws BaseExecuteException{
        
        BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            int effctedNum=permissionMapper.updateById(permission);
        if(effctedNum<=0)
        {
            throw new BaseExecuteException("更新0条信息");
        }
        baseExecution.setEum(ExecuteStateEum.SUCCESS);
        baseExecution.setTemp(permission);
        return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("更新权限(Permission)失败:"+e.getMessage());
        }
    }

    @Override
    public BaseExecution<Permission> insertPermission(Permission permission) throws BaseExecuteException{
        BaseExecution<Permission> baseExecution=new BaseExecution<>();
        if(permission.getPermissionState()==null)
        {
            permission.setPermissionState(1);
        }
        try {
            int effctedNum=permissionMapper.insert(permission);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("创建0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(permission);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("创建权限(Permission)失败:"+e.getMessage());
        }
    }

    @Override
    public BaseExecution<Permission> deletePermission(Permission permission) throws BaseExecuteException{
        BaseExecution<Permission> baseExecution=new BaseExecution<>();
        try {
            int effctedNum=permissionMapper.deleteById(permission);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("删除0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(permission);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("删除权限(Permission)失败:"+e.getMessage());
        }
    }
    
}
