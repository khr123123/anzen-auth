package org.khr.anzenauth.model.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 角色表 表定义层。
 *
 * @author KK
 * @since 2025-09-19 10:29:07
 */
public class SysRoleTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色表
     */
    public static final SysRoleTableDef SYS_ROLE = new SysRoleTableDef();

    /**
     * 角色ID
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 角色状态（0正常 1停用）
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 角色权限字符串（如：admin, user）
     */
    public final QueryColumn ROLE_KEY = new QueryColumn(this, "role_key");

    /**
     * 角色名称
     */
    public final QueryColumn ROLE_NAME = new QueryColumn(this, "role_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ROLE_ID, ROLE_NAME, ROLE_KEY, STATUS, CREATE_TIME};

    public SysRoleTableDef() {
        super("", "sys_role");
    }

    private SysRoleTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysRoleTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysRoleTableDef("", "sys_role", alias));
    }

}
