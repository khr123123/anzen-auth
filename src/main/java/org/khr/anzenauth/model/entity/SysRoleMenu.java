package org.khr.anzenauth.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色和菜单关联表 实体类。
 *
 * @author KK
 * @since 2025-09-17 15:23:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_role_menu")
public class SysRoleMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Id
    private Long roleId;

    /**
     * 菜单ID
     */
    @Id
    private Long menuId;

}
