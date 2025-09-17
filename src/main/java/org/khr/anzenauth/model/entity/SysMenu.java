package org.khr.anzenauth.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 菜单表 实体类。
 *
 * @author KK
 * @since 2025-09-17 15:23:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_menu")
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @Id(keyType = KeyType.Auto)
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 权限标识（如：system:user:list）
     */
    private String perms;

    /**
     * 路由地址
     */
    private String url;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    private Timestamp createTime;


    private List<SysMenu> children;
}
