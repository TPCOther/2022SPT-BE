package com.easyorder.config.shiro;

import com.easyorder.entity.Staff;
import com.easyorder.mapper.StaffMapper;
import com.easyorder.service.StaffService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StaffMapper staffMapper;

    //判断封装令牌对象正确性
    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof OAuth2Token;
    }

    /*
     * 授权（验证权限限时调用）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        Staff staff = (Staff) collection.getPrimaryPrincipal();  //取出之前令牌中封装的staff
        Long staffId = staff.getStaffId();
        //TODO 查询权限列表，把权限列表添加到info对象中 id=0 为customer，无权限

        // Set<String> permsSet = staffService.searchUserPermissions(staffId);   //获取权限列表
        
        List<String> permissionList = new ArrayList<>();
        permissionList.add("dinTable:view");
        Set<String> permsSet = new HashSet<>(permissionList);

        List<String> roleList = new ArrayList<>();
        roleList.add("customer");
        Set<String> roleSet = new HashSet<>(roleList);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);    //设置令牌权限
        info.setRoles(roleSet);
        return info;    //返回令牌
    }

    /*
     * 认证（登陆时调用）
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal(); //获得字符串形式的令牌
        Long staffId = jwtUtil.getStaffId(accessToken);
        //TODO 判断staff是否可用
        Staff staff = staffMapper.selectById(staffId);   //取得用户信息,检查账户是否可用
        if(staff == null){ throw new LockedAccountException("账号不可用，请联系管理员"); }  //账户异常
        //TODO 往info对象中添加用户信息，Token字符串
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(staff, accessToken, getName()); //封装为令牌对象
        return info;
    }
}
