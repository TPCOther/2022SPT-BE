/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-29 16:33:34
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-30 11:36:58
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\RolePermissionServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Role;
import com.easyorder.entity.RolePermission;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.RolePermissionMapper;
import com.easyorder.service.RolePermissionService;
import com.easyorder.util.BaseExecuteException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RolePermissionServiceImpl implements RolePermissionService{

    @Resource
    RolePermissionMapper rolePermissionMapper;
    @Override
    public BaseExecution<RolePermission> selectRolePermissionList(RolePermission rolePermission)
            throws BaseExecuteException {
        BaseExecution<RolePermission> baseExecution=new BaseExecution<>();
        QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
        Long roleId=rolePermission.getRoleId();
        Long permissionId=rolePermission.getPermissionId();
        wrapper.eq(roleId!=null, "role_id",roleId);
        wrapper.eq(permissionId!=null, "permission_id",permissionId);
        try {
            List<RolePermission> rolePermissions=rolePermissionMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(rolePermissions);
            baseExecution.setCount(Long.valueOf(rolePermissions.size()));
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("查询角色-权限(RolePermission)失败:"+e.getMessage());
        }
    }

    // @Override
    // public BaseExecution<RolePermission> updateRolePermission(RolePermission rolePermission)
    //         throws BaseExecuteException {
    //     BaseExecution<RolePermission> baseExecution=new BaseExecution<>();
    //     try {
    //         QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
    //         Long roleId=rolePermission.getRoleId();
    //         Long permissionId=rolePermission.getPermissionId();
    //         wrapper.eq(roleId!=null, "role_id", roleId);
    //         wrapper.eq(permissionId!=null, "permission_id",permissionId);
    //         int effctedNum=rolePermissionMapper.update(rolePermission,wrapper);
    //         if(effctedNum<=0)
    //         {
    //             throw new BaseExecuteException("更新0条信息");
    //         }
    //         baseExecution.setEum(ExecuteStateEum.SUCCESS);
    //         baseExecution.setTemp(rolePermission);
    //         return baseExecution;
    //     } catch (Exception e) {
    //         throw new BaseExecuteException("更新角色-权限(RolePermission)失败:"+e.getMessage());
    //     }
    // }

    @Transactional
    @Override
    public BaseExecution<RolePermission> insertRolePermission(RolePermission rolePermission)
            throws BaseExecuteException {
        
        BaseExecution<RolePermission> baseExecution=new BaseExecution<>();
        try {
            int effctedNum=rolePermissionMapper.insert(rolePermission);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("创建0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(rolePermission);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("创建角色-权限(RolePermission)失败:"+e.getMessage());
        }

    }

    @Transactional
    @Override
    public BaseExecution<RolePermission> deleteRolePermission(RolePermission rolePermission)
            throws BaseExecuteException {
        BaseExecution<RolePermission> baseExecution=new BaseExecution<>();
        QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
        Long roleId=rolePermission.getRoleId();
        Long permissionId=rolePermission.getPermissionId();
        wrapper.eq(roleId!=null,"role_id",roleId);
        wrapper.eq(permissionId!=null, "permission_id",permissionId);

        try {
            int effctedNum=rolePermissionMapper.delete(wrapper);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("删除0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(rolePermission);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("删除角色-权限(RolePermission)失败:"+e.getMessage());
        }
    }
    
}
