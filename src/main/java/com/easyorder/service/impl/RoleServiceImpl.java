/*
 * @Author: 123456 2373464672@qq.com
 * @Date: 2022-06-28 15:46:17
 * @LastEditors: 123456 2373464672@qq.com
 * @LastEditTime: 2022-06-28 16:22:21
 * @FilePath: \2022SPT-BE\src\main\java\com\easyorder\service\impl\RoleServiceImpl.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.easyorder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.easyorder.entity.Role;
import com.easyorder.mapper.RoleMapper;
import com.easyorder.mapper.StaffMapper;
import com.easyorder.service.RoleService;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
@Service
public class RoleServiceImpl implements RoleService{

    @Resource
    RoleMapper roleMapper;
    @Resource
    StaffMapper staffMapper;
    @Override
    public List<Role> getRoleList(Role role) {
        // TODO Auto-generated method stub
        QueryWrapper<Role> wrapper=new QueryWrapper<>();
        Long id=role.getRoleId();
        String name=role.getRoleName();
        String desc=role.getRoleDesc();

        wrapper.eq(id!=null,"role_id",id);
        wrapper.eq(StringUtils.isNotEmpty(name),"role_name",name);
        wrapper.eq(StringUtils.isNotEmpty(desc),"role_desc",desc);

        List<Role> roles=roleMapper.selectList(wrapper);
        return roles;

    }

    @Override
    public void updateRole(Role role) {
        // TODO Auto-generated method stub
        if(role.getRoleId()!=null)
        {
            roleMapper.updateById(role);
           
        }
        
    }

    @Override
    public void insertRole(Role role) {
        // TODO Auto-generated method stub
        if(role.getRoleId()!=null)
        {
            roleMapper.insert(role);
            
        }
    }

    // @Override
    // public void deleteRole(Role role) {
    //     // TODO Auto-generated method stub
    //     if(role.getRoleId()!=null)
    //     {
    //         Long staffId=roleMapper.findUserNameByBlogId(role.getRoleId());
    //         staffMapper.deleteById(staffId);
    //         roleMapper.deleteById(role);
            
    //     }
    // }
    
}
