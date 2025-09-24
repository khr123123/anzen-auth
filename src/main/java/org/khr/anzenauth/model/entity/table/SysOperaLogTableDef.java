package org.khr.anzenauth.model.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 操作日志记录 表定义层。
 *
 * @author KK
 * @since 2025-09-24 13:17:17
 */
public class SysOperaLogTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作日志记录
     */
    public static final SysOperaLogTableDef SYS_OPERA_LOG = new SysOperaLogTableDef();

    /**
     * 模块标题
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 方法名称
     */
    public final QueryColumn METHOD = new QueryColumn(this, "method");

    /**
     * 操作状态（0正常 1异常）
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 日志主键
     */
    public final QueryColumn OPERA_ID = new QueryColumn(this, "opera_id");

    /**
     * 主机地址
     */
    public final QueryColumn OPERA_IP = new QueryColumn(this, "opera_ip");

    /**
     * 消耗时间
     */
    public final QueryColumn COST_TIME = new QueryColumn(this, "cost_time");

    /**
     * 错误消息
     */
    public final QueryColumn ERROR_MSG = new QueryColumn(this, "error_msg");

    /**
     * 请求URL
     */
    public final QueryColumn OPERA_URL = new QueryColumn(this, "opera_url");

    /**
     * 操作人员
     */
    public final QueryColumn OPERA_NAME = new QueryColumn(this, "opera_name");

    /**
     * 操作时间
     */
    public final QueryColumn OPERA_TIME = new QueryColumn(this, "opera_time");

    /**
     * 返回参数
     */
    public final QueryColumn JSON_RESULT = new QueryColumn(this, "json_result");

    /**
     * 请求参数
     */
    public final QueryColumn OPERA_PARAM = new QueryColumn(this, "opera_param");

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    public final QueryColumn BUSINESS_TYPE = new QueryColumn(this, "business_type");

    /**
     * 操作地点
     */
    public final QueryColumn OPERA_LOCATION = new QueryColumn(this, "opera_location");

    /**
     * 请求方式
     */
    public final QueryColumn REQUEST_METHOD = new QueryColumn(this, "request_method");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{OPERA_ID, TITLE, BUSINESS_TYPE, METHOD, REQUEST_METHOD, OPERA_NAME, OPERA_URL, OPERA_IP, OPERA_LOCATION, OPERA_PARAM, JSON_RESULT, STATUS, ERROR_MSG, OPERA_TIME, COST_TIME};

    public SysOperaLogTableDef() {
        super("", "sys_opera_log");
    }

    private SysOperaLogTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysOperaLogTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysOperaLogTableDef("", "sys_opera_log", alias));
    }

}
