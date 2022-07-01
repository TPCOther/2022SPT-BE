/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-07-01 15:18:54
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-07-01 17:18:28
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\ControllerMenuServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.dto.BaseExecution;
import com.easyorder.entity.ControllerMenu;
import com.easyorder.entity.RoleMenu;
import com.easyorder.enums.ExecuteStateEum;
import com.easyorder.mapper.ControllerMenuMapper;
import com.easyorder.mapper.RoleMenuMapper;
import com.easyorder.service.ControllerMenuService;
import com.easyorder.util.BaseExecuteException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ControllerMenuServiceImpl implements ControllerMenuService{


    @Resource
    ControllerMenuMapper controllerMenuMapper;

    @Resource
    RoleMenuMapper roleMenuMapper;
    
    @Override
    public BaseExecution<ControllerMenu> selectControllerMenuList(ControllerMenu controllerMenu)
            throws BaseExecuteException {
        BaseExecution<ControllerMenu> baseExecution=new BaseExecution<>();
        QueryWrapper<ControllerMenu> wrapper=new QueryWrapper<>();
        Long id=controllerMenu.getControllerMenuId();
        String name=controllerMenu.getControllerMenuName();
        String desc=controllerMenu.getControllerMenuDesc();
        wrapper.eq(id!=null, "controller_menu_id",id);
        wrapper.eq(StringUtils.isNotEmpty(name), "controller_menu_name", name);
        wrapper.eq(StringUtils.isNotEmpty(desc), "controller_menu_desc", desc);
        try {
            List<ControllerMenu> controllerMenus=controllerMenuMapper.selectList(wrapper);
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTList(controllerMenus);
            baseExecution.setCount(Long.valueOf(controllerMenus.size()));
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("查询菜单控制(controller_menu)失败:"+e.getMessage());
        }        
    }

    @Transactional
    @Override
    public BaseExecution<ControllerMenu> updateControllerMenu(ControllerMenu controllerMenu) throws BaseExecuteException {
        
        BaseExecution<ControllerMenu> baseExecution=new BaseExecution<>();
        try {
            int effctedNum=controllerMenuMapper.updateById(controllerMenu);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("更新0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(controllerMenu);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("更新菜单控制(controller_menu)失败:"+e.getMessage());
        }
        

    }

    @Transactional
    @Override
    public BaseExecution<ControllerMenu> insertControllerMenu(ControllerMenu controllerMenu) throws BaseExecuteException {
        
        BaseExecution<ControllerMenu> baseExecution=new BaseExecution<>();
        try {
            int effctedNum=controllerMenuMapper.insert(controllerMenu);
            if(effctedNum<=0)
            {
                throw new BaseExecuteException("创建0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(controllerMenu);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("创建控制菜单(controller_menu)失败:"+e.getMessage());
        }

    }

    @Transactional
    @Override
    public BaseExecution<ControllerMenu> deleteControllerMenu(ControllerMenu controllerMenu) throws BaseExecuteException {
        
        BaseExecution<ControllerMenu> baseExecution=new BaseExecution<>();
        try {
            Long long1=roleMenuMapper.findRoleIdByControllerMenuId(controllerMenu.getControllerMenuId());
            QueryWrapper<RoleMenu> wrapper=new QueryWrapper<>();
            Long roleId=long1;
            Long controllerMenuId=controllerMenu.getControllerMenuId();
            int e=1;
            wrapper.eq(roleId!=null, "role_id",roleId);
            wrapper.eq(controllerMenuId!=null, "controller_menu_id",controllerMenu);
            if(roleId!=null)
            {
                e=roleMenuMapper.delete(wrapper);
            }
            int effctedNum=controllerMenuMapper.deleteById(controllerMenuId);
            if(e<=0||effctedNum<=0)
            {
                throw new BaseExecuteException("删除0条信息");
            }
            baseExecution.setEum(ExecuteStateEum.SUCCESS);
            baseExecution.setTemp(controllerMenu);
            return baseExecution;
        } catch (Exception e) {
            throw new BaseExecuteException("删除菜单控制(controller_menu)失败:"+e.getMessage());
        }
    }
    
}
