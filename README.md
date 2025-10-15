```markdown
# Anzen-Auth

**从 RuoYi 框架提取的权限校验模块**

---

## 项目概述

`Anzen-Auth` 是从 RuoYi 框架中提取出来的轻量级 **权限校验模块**。  
它提供 **基于角色的访问控制（RBAC）**、菜单权限管理以及用户授权功能，适用于 Spring Boot 应用。  

项目技术栈：

- **Spring Boot 3.5.5**
- **Java 21**
- **MyBatis-Flex 1.11.1**（ORM 框架）
- **Hutool 5.8.0**（工具库）
- **HikariCP 4.0.3**（数据库连接池）
- **MySQL 8.0.32**
- **Knife4j 4.4.0**（API 文档）
- **Lombok**（可选，用于简化代码）

---

## 功能特点

- 基于角色的访问控制（RBAC）
- 菜单及权限管理
- 用户-角色与角色-菜单映射
- 自定义错误码与统一 API 响应
- 轻量、易集成到任意 Spring Boot 项目
- 完全兼容 Spring Boot 3.x

---

## 项目结构

```

org.khr.anzenauth
├── base
│   ├── common      # 通用工具类，如 ResultUtils
│   └── constant    # 常量类，如 RoleConstant
├── service
│   ├── impl        # 服务实现，如 SysMenuServiceImpl
│   └── interface   # 服务接口，如 SysMenuService
├── mapper           # MyBatis-Flex Mapper 接口
├── model
│   ├── entity       # 实体类
│   └── entity/table # 数据库表定义

````

---

## 依赖列表

| 依赖 | 版本 |
|------|------|
| Spring Boot | 3.5.5 |
| Java | 21 |
| Hutool | 5.8.0 |
| MyBatis-Flex | 1.11.1 |
| HikariCP | 4.0.3 |
| MySQL Connector/J | 8.0.32 |
| Knife4j | 4.4.0 |
| Lombok | 可选 |

---

## 安装方法

1. 克隆项目：

```bash
git clone https://github.com/khr123123/anzen-auth.git
````

2. 在 IDE（IntelliJ IDEA / Eclipse）中导入为 **Maven 项目**。

3. 构建项目：

```bash
mvn clean install
```

4. 在 `application.yml` 或 `application.properties` 中配置数据库连接。

---

## 数据库初始化

### 表结构示例

#### sys\_user

```sql
CREATE TABLE sys_user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    status TINYINT DEFAULT 1
);
```

#### sys\_role

```sql
CREATE TABLE sys_role (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    role_key VARCHAR(50),
    status TINYINT DEFAULT 1
);
```

#### sys\_menu

```sql
CREATE TABLE sys_menu (
    menu_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    menu_name VARCHAR(100) NOT NULL,
    perms VARCHAR(255),
    url VARCHAR(255),
    type TINYINT,
    parent_id BIGINT,
    order_num INT
);
```

#### sys\_user\_role

```sql
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, role_id)
);
```

#### sys\_role\_menu

```sql
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY(role_id, menu_id)
);
```

---

## 使用方法

### 1. 添加依赖

在你的 Spring Boot 项目中添加 `anzen-auth` 依赖：

```xml
<dependency>
    <groupId>org.khr</groupId>
    <artifactId>anzen-auth</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. 配置权限校验

使用模块提供的服务方法实现 API 权限校验：

```java
@Autowired
private SysMenuService sysMenuService;

Set<String> perms = sysMenuService.selectMenuPermsByUserId(userId);

if (perms.contains("admin:add")) {
    // 执行 admin 添加操作
}
```

### 3. 统一返回 API 响应

使用 `ResultUtils` 工具类返回统一响应：

```java
// 成功返回
return ResultUtils.success(data);

// 错误返回，使用错误码
return ResultUtils.error(ErrorCode.NO_AUTH_ERROR);

// 错误返回，自定义提示信息
return ResultUtils.error(ErrorCode.PARAMS_ERROR, "参数无效");
```

---

## 错误码说明

| 错误码   | 描述     |
| ----- | ------ |
| 0     | 成功     |
| 40000 | 请求参数无效 |
| 40100 | 未登录    |
| 40101 | 无权限    |
| 40300 | 访问被禁止  |
| 40400 | 数据未找到  |
| 42900 | 请求过多   |
| 50000 | 系统内部错误 |
| 50001 | 操作失败   |

---

## 开发说明

* 所有服务方法默认通过 MyBatis-Flex Spring Boot Starter 提供事务支持。
* 实体类可以通过 **MyBatis-Flex Codegen** 自动生成。
* 常量与通用工具类集中在 `base` 模块，便于维护。
* 模块设计轻量，适合微服务或模块化项目集成。

---

## 作者

KK – [GitHub](https://github.com/khr123123)
