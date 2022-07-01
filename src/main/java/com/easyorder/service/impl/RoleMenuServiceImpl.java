/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 11:36:50
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 17:02:14
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\RoleMenuServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.RoleMenu;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.RoleMenuMapper;
import com.easyorder.service.RoleMenuService;
import com.easyorder.util.BaseExecuteException;
import com.easyorder.util.HttpServletRequestUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RoleMenuServiceImpl implements RoleMenuService{

    @Resource
    RoleMenuMapper roleMenuMapper;
    @Override
    public BaseExecution<RoleMenu> selectRoleMenuList(RoleMenu roleMenu) throws BaseExecuteException {
        BaseExecution<RoleMenu> baseExecution=new BaseExecution<>();
        QueryWrapper<RoleMenu> wrapper=new QueryWrapper<>();
        Long roleId=roleMenu.getRoleId();
        Long controllerMenuId=roleMenu.getControllerMenuId();
        wrapper.eq(roleId!=null, "role_id",roleId);
        wrapper.eq(controllerMenuId!=null, "controller_menu_id",controllerMenuId);
        try {
            List<RoleMenu> rolePermissions=roleMenuMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(rolePermissions);
            baseExecution.setCount(Long.valueOf(rolePermissions.size()));
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("查询角色-菜单(RoleMenu)失败:"+e.getMessage());
        }
    }

    @Transactional
    @Override
    public BaseExecution<RoleMenu> updateRoleMenu(HttpServletRequest request) throws BaseExecuteException {
        
        BaseExecution<RoleMenu> baseExecution=new BaseExecution<>();
        try {
            String preRoleId=HttpServletRequestUtil.getString(request, "preRoleId");
            String preMenuId=HttpServletRequestUtil.getString(request, "preMenuId");
            Long curRoleId=HttpServletRequestUtil.getLong(request, "curRoleId");
            Long curMenuId=HttpServletRequestUtil.getLong(request, "curMenuId");


            QueryWrapper<RoleMenu> wrapper=new QueryWrapper<>();
            wrapper.eq(preRoleId!=null, "role_id", preRoleId);
            wrapper.eq(preMenuId!=null, "controller_menu_id",preMenuId);
            RoleMenu roleMenu=new RoleMenu(curRoleId, curMenuId);

            if(StringUtils.isNotEmpty(preRoleId)&&StringUtils.isNotEmpty(preMenuId))
            {
                int effctedNum=roleMenuMapper.delete(wrapper);
                int e=roleMenuMapper.insert(roleMenu);
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
            throw new BaseExecuteException("更新角色-菜单(RoleMenu)失败:"+e.getMessage());
        }
    }

    @Transactional
    @Override
    public BaseExecution<RoleMenu> insertRoleMenu(RoleMenu roleMenu) throws BaseExecuteException {
        
        BaseExecution<RoleMenu> baseExecution=new BaseExecution<>();
        // Long l1=roleMenu.getRoleId();
        // Long l2=roleMenu.getControllerMenuId();
        // System.out.print(l1);
        // System.out.println(l2);
        Long long1=roleMenuMapper.findRoleId(roleMenu.getRoleId());
        Long long2=roleMenuMapper.findMenuId(roleMenu.getControllerMenuId());
        if(long1!=null&&long2!=null)
        {
            try {
                int effctedNum=roleMenuMapper.insert(roleMenu);
                if(effctedNum<=0)
                {
                    throw new BaseExecuteException("创建0条信息");
                }
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTemp(roleMenu);
                return baseExecution;
            } catch (Exception e) {
                throw new BaseExecuteException("创建角色-菜单(RoleMenu)失败:"+e.getMessage());
            }
        }else{
            throw new BaseExecuteException("创建角色菜单失败:请检查role_id、menu_id是否存在");
        }
        
    }

    @Transactional
    @Override
    public BaseExecution<RoleMenu> deleteRoleMenu(RoleMenu roleMenu) throws BaseExecuteException {
        BaseExecution<RoleMenu> baseExecution=new BaseExecution<>();
        QueryWrapper<RoleMenu> wrapper=new QueryWrapper<>();
        Long roleId=roleMenu.getRoleId();
        Long menuId=roleMenu.getControllerMenuId();
        wrapper.eq(roleId!=null,"role_id",roleId);
        wrapper.eq(menuId!=null, "controller_menu_id",menuId);
        if(roleId!=null&&menuId!=null)
        {
            try {
                int effctedNum=roleMenuMapper.delete(wrapper);
                if(effctedNum<=0)
                {
                    throw new BaseExecuteException("删除0条信息");
                }
                baseExecution.setEum(ExecuteStateEum.SUCCESS);
                baseExecution.setTemp(roleMenu);
                return baseExecution;
            } catch (Exception e) {
                throw new BaseExecuteException("删除角色-菜单(RoleMenu)失败:"+e.getMessage());
            }
        }else{
            throw new BaseExecuteException("删除角色菜单失败:请检查role_id、menu_id是否存在");
        }
        
    
    }
    
}
