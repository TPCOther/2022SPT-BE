/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:46:17
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-02 17:16:58
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
import com.easyorder.entity.RoleMenu;
import com.easyorder.entity.RolePermission;
import com.easyorder.entity.Staff;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.RoleMapper;
import com.easyorder.mapper.RoleMenuMapper;
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
    @Resource
    RoleMenuMapper roleMenuMapper;

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
        //TODO:角色更新为空判断
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
            Long roleId=role.getRoleId();
            Long staffId=staffMapper.findStaffIdByRoleId(role.getRoleId());
            List<Long> longs=rolePermissionMapper.findPermissionIdListByRoleId(role.getRoleId());
            for(Long permissionId:longs)
            {
                QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
                wrapper.eq(roleId!=null, "role_id",roleId);
                wrapper.eq(permissionId!=null, "permission_id",permissionId);
                rolePermissionMapper.delete(wrapper);
            }
            List<Long> longs2=roleMenuMapper.findControllerMenuIdListByRoleId(role.getRoleId());

            QueryWrapper<Staff> wrapper=new QueryWrapper<>();
            for(Long controllerMenuId:longs2)
            {
                QueryWrapper<RoleMenu> wrapper2=new QueryWrapper<>();
                wrapper.eq(roleId!=null, "role_id",roleId);
                wrapper.eq(controllerMenuId!=null, "controller_menu_id",controllerMenuId);
                roleMenuMapper.delete(wrapper2);
            }

            int e=1;
            if(staffId!=null)
            {
                e=staffMapper.deleteById(staffId);
            }
            int effctedNum=roleMapper.deleteById(role);

            if(e<=0||effctedNum<=0)
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
