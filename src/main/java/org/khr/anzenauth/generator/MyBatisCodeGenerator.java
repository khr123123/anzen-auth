package org.khr.anzenauth.generator;

import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * MyBatis Flex 代码生成器
 */
public class MyBatisCodeGenerator {

    public static void main(String[] args) {
        // 获取数据元信息
        Dict dict = YamlUtil.loadByPath("application.yml");
        Map<String, Object> dataSourceConfig = dict.getByPath("spring.datasource");
        String url = String.valueOf(dataSourceConfig.get("url"));
        String username = String.valueOf(dataSourceConfig.get("username"));
        String password = String.valueOf(dataSourceConfig.get("password"));
        // 配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // 创建配置内容
        GlobalConfig globalConfig = createGlobalConfig();

        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        // 生成代码
        generator.generate();
    }


    // 详细配置见：https://mybatis-flex.com/zh/others/codegen.html
    public static GlobalConfig createGlobalConfig() {
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        // 注册 特殊的JDBC 类型映射
        JdbcTypeMapping.registerMapping(LocalDateTime.class, Date.class);
        JdbcTypeMapping.registerMapping(BigInteger.class, Long.class);
        // 设置根包，建议先生成到一个临时目录下，生成代码之后，再移动到对应的项目目录
        globalConfig.getPackageConfig().setBasePackage("org.khr.temp");

        // 设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
            .setGenerateTable("sys_opera_log")
//            .setTablePrefix("tb_")
            // 设置逻辑删除的默认字段名称
            .setLogicDeleteColumn("is_delete");

        // 设置生成 entity 并启用 Lombok
        globalConfig.enableEntity().setWithLombok(true).setJdkVersion(24);

        // 设置生成 mapper
        globalConfig.enableMapper();
        globalConfig.enableMapperXml();

        // 设置生成 Service
        globalConfig.enableService();
        globalConfig.enableServiceImpl();

        // 设置生成 Controller
        globalConfig.enableController();

        //设置生成 TableDef
        globalConfig.enableTableDef();
        // 设置生成注释，比如生成的时间和作者，避免后续多余的代码改动
        globalConfig.getJavadocConfig().setAuthor("KK").setSince(LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return globalConfig;
    }
}

