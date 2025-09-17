package org.khr.anzenauth.model.dto;

import lombok.Data;

/**
 @author KK
 @create 2025-09-17-11:03
 */
@Data
public class UserLoginDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

}
