package org.khr.anzenauth.security.service;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.khr.anzenauth.base.common.ErrorCode;
import org.khr.anzenauth.base.constant.UserStatus;
import org.khr.anzenauth.base.exception.ThrowUtils;
import org.khr.anzenauth.mapper.SysUserMapper;
import org.khr.anzenauth.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.khr.anzenauth.model.entity.table.SysUserTableDef.SYS_USER;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.selectOneByQuery(
            QueryWrapper.create().select(SYS_USER.PASSWORD, SYS_USER.STATUS).eq(SYS_USER.USERNAME.getName(), username));

        ThrowUtils.throwIf(ObjectUtil.isNull(sysUser), ErrorCode.OPERATION_ERROR, "登录用户：" + username + " 不存在.");

        ThrowUtils.throwIf(sysUser.getStatus().equals(UserStatus.DISABLE), ErrorCode.OPERATION_ERROR,
            "登录用户：" + username + " 已被停用.");
        // 此方法只负责校验账号密码，无关于权限字符串的处理
        return User.withUsername(username).password(sysUser.getPassword()).build();
    }

}
