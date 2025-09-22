package org.khr.anzenauth.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mybatisflex.annotation.*;

import java.io.Serializable;
import java.util.Date;

import java.io.Serial;

import com.mybatisflex.core.mask.Masks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表 实体类。
 *
 * @author KK
 * @since 2025-09-19 10:29:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_user")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id(keyType = KeyType.Auto)
    private Long userId;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 密码
     */
    @ColumnMask(Masks.PASSWORD)
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
