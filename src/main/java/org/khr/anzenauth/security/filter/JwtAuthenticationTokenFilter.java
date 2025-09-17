package org.khr.anzenauth.security.filter;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.khr.anzenauth.service.SysMenuService;
import org.khr.anzenauth.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.khr.anzenauth.base.constant.TokenConstant.TOKEN_HEADER;
import static org.khr.anzenauth.base.constant.TokenConstant.TOKEN_PREFIX;
import static org.khr.anzenauth.model.entity.table.SysUserTableDef.SYS_USER;

/**
 * Token 过滤器，验证 token 有效性
 */
@Component
@NonNullApi
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        String header = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(TOKEN_PREFIX)) {
            String token = header.substring(TOKEN_PREFIX.length());

            if (TokenUtil.validateToken(token)) {
                String username = (String) TokenUtil.getClaim(token, SYS_USER.USERNAME.toString());
                Long userId = Long.parseLong(TokenUtil.getClaim(token, SYS_USER.USER_ID.toString()).toString());
                // 查询用户权限
                Set<String> permsSet = sysMenuService.selectMenuPermsByUserId(userId);

                // 转换为 GrantedAuthority
                Set<SimpleGrantedAuthority> authorities = permsSet.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                        Map.of(SYS_USER.USER_ID, userId, SYS_USER.USERNAME, username),
                        null,
                        authorities);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}
