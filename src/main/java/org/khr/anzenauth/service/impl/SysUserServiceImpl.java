package org.khr.anzenauth.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.anzenauth.common.common.ErrorCode;
import org.khr.anzenauth.common.exception.BusinessException;
import org.khr.anzenauth.mapper.SysUserMapper;
import org.khr.anzenauth.model.dto.LoginUser;
import org.khr.anzenauth.model.entity.SysUser;
import org.khr.anzenauth.service.SysUserService;
import org.khr.anzenauth.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户表 服务层实现。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public String userLogin(String username, String password) {
        // 用户验证
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long userId = getOne(query().eq(SysUser::getUsername, username)).getUserId();
        // 生成token
        return TokenUtil.generateToken(username, userId);
    }


    @Override
    public SysUser selectUserByUserName(String username) {
        return getOne(query().eq(SysUser::getUsername, username));
    }

    @Override
    public Set<String> getMenuPermission(SysUser user) {
        return Set.of();
    }

}
