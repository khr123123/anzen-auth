package org.khr.anzenauth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.khr.anzenauth.model.entity.SysUser;

import java.util.Set;

/**
 @author KK
 @create 2025-09-17-10:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserInfoVO {

    private SysUser user;

    private Set<String> permissions;

    private Set<String> roles;

}
