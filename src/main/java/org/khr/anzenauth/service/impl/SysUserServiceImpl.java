package org.khr.anzenauth.service.impl;

import cn.hutool.core.lang.UUID;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.khr.anzenauth.base.common.ErrorCode;
import org.khr.anzenauth.base.exception.BusinessException;
import org.khr.anzenauth.base.utils.TokenUtil;
import org.khr.anzenauth.mapper.SysUserMapper;
import org.khr.anzenauth.model.entity.SysRole;
import org.khr.anzenauth.model.entity.SysUser;
import org.khr.anzenauth.model.entity.SysUserRole;
import org.khr.anzenauth.service.SysRoleService;
import org.khr.anzenauth.service.SysUserRoleService;
import org.khr.anzenauth.service.SysUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.khr.anzenauth.base.constant.RedisConstants.LOGIN_TOKEN_KEY;
import static org.khr.anzenauth.base.utils.TokenUtil.EXPIRE_MINUTES;

/**
 * 用户表 服务层实现。
 *
 * @author KK
 * @since 2025-09-17 10:12:12
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final AuthenticationManager authenticationManager;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public String userLogin(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, e.getMessage());
        }
        Long userId = getOne(query().eq(SysUser::getUsername, username)).getUserId();
        // 生成token
        String token = TokenUtil.generateToken(username, userId);
        String frontEndToken = LOGIN_TOKEN_KEY + UUID.fastUUID();
        redisTemplate.opsForValue().set(frontEndToken, token, EXPIRE_MINUTES, TimeUnit.MINUTES);
        return frontEndToken;
    }

    @Override
    @Transactional
    public boolean grantRole(Long userId, List<Long> roleIds) {
        // 校验 roleIds 是否存在
        if (roleIds != null && !roleIds.isEmpty()) {
            long count = sysRoleService.count(query().in(SysRole::getRoleId, roleIds));
            if (count != roleIds.size()) {
                throw new IllegalArgumentException("存在无效角色ID");
            }
        }
        // 1. 先删除当前用户的所有角色绑定
        sysUserRoleService.remove(query().eq(SysUserRole::getUserId, userId));

        // 2. 批量新增新的角色绑定
        if (roleIds != null && !roleIds.isEmpty()) {
            List<SysUserRole> userRoles = roleIds.stream()
                .map(roleId -> new SysUserRole(userId, roleId))
                .toList();
            return sysUserRoleService.saveBatch(userRoles);
        }
        // 如果没有传角色，则只是清空用户角色，返回 true
        return true;
    }

    @Override
    public List<Long> getUserRoles(Long id) {
        return sysUserRoleService.listAs(
            query().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, id), Long.class);
    }


}
