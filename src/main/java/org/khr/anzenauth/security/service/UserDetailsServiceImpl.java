package org.khr.anzenauth.security.service;

import cn.hutool.core.util.ObjectUtil;
import org.khr.anzenauth.base.common.ErrorCode;
import org.khr.anzenauth.base.constant.UserStatus;
import org.khr.anzenauth.base.exception.ThrowUtils;
import org.khr.anzenauth.model.entity.SysUser;
import org.khr.anzenauth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        ThrowUtils.throwIf(ObjectUtil.isNull(user), ErrorCode.OPERATION_ERROR, "登录用户：" + username + " 不存在.");
        ThrowUtils.throwIf(user.getStatus().equals(UserStatus.DISABLE), ErrorCode.OPERATION_ERROR,
            "登录用户：" + username + " 已被停用.");
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                // 转换为 GrantedAuthority
                return userService.getMenuPermission(user).stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return username;
            }
        };
    }

}
