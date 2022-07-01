/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-29 16:33:34
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 17:04:45
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\RolePermissionServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.RolePermission;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.RolePermissionMapper;
import com.easyorder.service.RolePermissionService;
import com.easyorder.util.BaseExecuteException;
import com.easyorder.util.HttpServletRequestUtil;

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

    @Transactional
    @Override
    public BaseExecution<RolePermission> updateRolePermission(HttpServletRequest rolePermission)
            throws BaseExecuteException {

        BaseExecution<RolePermission> baseExecution=new BaseExecution<>();
        try {
            String preRoleId=HttpServletRequestUtil.getString(rolePermission, "preRoleId");
            String prePermissionId=HttpServletRequestUtil.getString(rolePermission, "prePermissionId");
            Long curRoleId=HttpServletRequestUtil.getLong(rolePermission, "curRoleId");
            Long curPermissionId=HttpServletRequestUtil.getLong(rolePermission, "curPermissionId");


            QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
            wrapper.eq(preRoleId!=null, "role_id", preRoleId);
            wrapper.eq(prePermissionId!=null, "permission_id",prePermissionId);
            RolePermission rolePermission2=new RolePermission(curRoleId, curPermissionId);

            if(StringUtils.isNotEmpty(preRoleId)&&StringUtils.isNotEmpty(prePermissionId))
            {
                int effctedNum=rolePermissionMapper.delete(wrapper);
                int e=rolePermissionMapper.insert(rolePermission2);
                if(effctedNum<=0||e<=0)
                {
                    throw new BaseExecuteException("更新0条信息");
                }
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                //baseExecution.setTemp(rolePermission);
                return baseExecution;
            }else{
                throw new BaseExecuteException("更新0条信息");
            }
           
        } catch (Exception e) {
            throw new BaseExecuteException("更新角色-权限(RolePermission)失败:"+e.getMessage());
        }
    }

    @Transactional
    @Override
    public BaseExecution<RolePermission> insertRolePermission(RolePermission rolePermission)
            throws BaseExecuteException {
        
        BaseExecution<RolePermission> baseExecution=new BaseExecution<>();
        Long long1=rolePermissionMapper.findRoleId(rolePermission.getRoleId());
        Long long2=rolePermissionMapper.findPermissionId(rolePermission.getPermissionId());
        if(long1!=null&&long2!=null)
        {
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
        }else{
            throw new BaseExecuteException("创建角色-权限失败:请检查role_id、permission_id是否存在");
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
        if(roleId!=null&&permissionId!=null)
        {
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
        }else{
            throw new BaseExecuteException("删除角色-权限失败:请检查role_id、permission_id是否存在");
        }
        
    }
    
}
