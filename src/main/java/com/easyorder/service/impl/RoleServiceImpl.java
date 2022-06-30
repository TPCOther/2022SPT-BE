/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:46:17
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-30 16:48:34
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\RoleServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.Role;
import com.easyorder.entity.RolePermission;
import com.easyorder.entity.Staff;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.RoleMapper;
import com.easyorder.mapper.RolePermissionMapper;
import com.easyorder.mapper.StaffMapper;
import com.easyorder.service.RoleService;
import com.easyorder.util.BaseExecuteException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RoleServiceImpl implements RoleService{

    @Resource
    RoleMapper roleMapper;
    @Resource
    StaffMapper staffMapper;
    @Resource
    RolePermissionMapper rolePermissionMapper;
    @Override
    public BaseExecution<Role> selectRoleList(Role role) throws BaseExecuteException{
        
        QueryWrapper<Role> wrapper=new QueryWrapper<>();
        BaseExecution<Role> baseExecution=new BaseExecution<>();

        Long id=role.getRoleId();
        String name=role.getRoleName();
        String desc=role.getRoleDesc();

        wrapper.eq(id!=null,"role_id",id);
        wrapper.eq(StringUtils.isNotEmpty(name),"role_name",name);
        wrapper.eq(StringUtils.isNotEmpty(desc),"role_desc",desc);

        try {
            List<Role> roles=roleMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(roles);
            baseExecution.setCount(Long.valueOf(roles.size()));
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("查询角色(Role)失败:"+e.getMessage());
        }
    }

    @Transactional
    @Override
    public BaseExecution<Role> updateRole(Role role) throws BaseExecuteException{
        BaseExecution<Role> baseExecution=new BaseExecution<>();
        if(StringUtils.isNotEmpty(role.getRoleName()))
        {
            try {
                int effctedNum=roleMapper.updateById(role);
                if(effctedNum<=0)
                {
                    throw new BaseExecuteException("更新0条信息");
                }
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTemp(role);
                return baseExecution;
            } catch (Exception e) {
                throw new BaseExecuteException("更新角色(role)失败:"+e.getMessage());
            }
        }else{
            throw new BaseExecuteException("更新角色(role)失败:请检查name是否正确");
        }
        
    }

    @Transactional
    @Override
    public BaseExecution<Role> insertRole(Role role) throws BaseExecuteException{
        
        BaseExecution<Role> baseExecution=new BaseExecution<>();
        if(StringUtils.isNotEmpty(role.getRoleName()))
        {
            try {
                int effctedNum=roleMapper.insert(role);
                if(effctedNum<=0)
                {
                    throw new BaseExecuteException("创建0条信息");
                }
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTemp(role);
                return baseExecution;
            } catch (Exception e) {
                throw new BaseExecuteException("创建角色(role)失败:"+e.getMessage());
            }
        }else{
            throw new BaseExecuteException("创建角色失败:请检查name是否正确");
        }
    }

    @Transactional
    @Override
    public BaseExecution<Role> deleteRole(Role role) throws BaseExecuteException{
        
        BaseExecution<Role> baseExecution=new BaseExecution<>();
        try {
            Long l=staffMapper.findStaffIdByRoleId(role.getRoleId());
            QueryWrapper<Staff> wrapper=new QueryWrapper<>();
            Long roleId=role.getRoleId();
            Long staffId=l;
            wrapper.eq(staffId!=null, "staff_id",staffId);
            Long l2=rolePermissionMapper.findPermissionId(role.getRoleId());
            QueryWrapper<RolePermission> wrapper2=new QueryWrapper<>();
            Long permissionId=l2;
            wrapper2.eq(permissionId!=null, "permission_id",permissionId);
            wrapper2.eq(roleId!=null, "role_id",roleId);
            int e=1;
            int e2=1;
            if(staffId!=null)
            {
                e=staffMapper.deleteById(staffId);
            }else if(permissionId!=null)
            {
                e2=rolePermissionMapper.delete(wrapper2);
            }
            int effctedNum=roleMapper.deleteById(role);

            if(e<=0||e2<=0||effctedNum<=0)
            {
                throw new BaseExecuteException("删除0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(role);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("删除角色(role)失败:"+e.getMessage());
        }
    }
    
}
