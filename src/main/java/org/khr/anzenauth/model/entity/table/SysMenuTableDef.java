package org.khr.anzenauth.model.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 菜单表 表定义层。
 *
 * @author KK
 * @since 2025-09-18 09:23:43
 */
public class SysMenuTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单表
     */
    public static final SysMenuTableDef SYS_MENU = new SysMenuTableDef();

    /**
     * 路由地址
     */
    public final QueryColumn URL = new QueryColumn(this, "url");

    /**
     * 菜单图标
     */
    public final QueryColumn ICON = new QueryColumn(this, "icon");

    /**
     * 权限标识（如：system:user:list）
     */
    public final QueryColumn PERMS = new QueryColumn(this, "perms");

    /**
     * 菜单ID
     */
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");

    /**
     * 菜单名称
     */
    public final QueryColumn MENU_NAME = new QueryColumn(this, "menu_name");

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    public final QueryColumn MENU_TYPE = new QueryColumn(this, "menu_type");

    /**
     * 显示顺序
     */
    public final QueryColumn ORDER_NUM = new QueryColumn(this, "order_num");

    /**
     * 父菜单ID
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{MENU_ID, MENU_NAME, PARENT_ID, ORDER_NUM, PERMS, URL, MENU_TYPE, ICON, CREATE_TIME};

    public SysMenuTableDef() {
        super("", "sys_menu");
    }

    private SysMenuTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysMenuTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysMenuTableDef("", "sys_menu", alias));
    }

}
