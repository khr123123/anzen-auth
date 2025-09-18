-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS `anzen_auth`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE anzen_auth;

-- 2. 用户表
CREATE TABLE sys_user
(
    user_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '登录账号',
    password    VARCHAR(100) NOT NULL COMMENT '密码',
    nickname    VARCHAR(50) COMMENT '昵称',
    status      CHAR(1)  DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 3. 角色表
CREATE TABLE sys_role
(
    role_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name   VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key    VARCHAR(50) NOT NULL UNIQUE COMMENT '角色权限字符串（如：admin, user）',
    status      CHAR(1)  DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- 4. 菜单/权限表
CREATE TABLE sys_menu
(
    menu_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    menu_name   VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id   BIGINT   DEFAULT 0 COMMENT '父菜单ID',
    order_num   INT      DEFAULT 0 COMMENT '显示顺序',
    perms       VARCHAR(100) COMMENT '权限标识（如：system:user:list）',
    url         VARCHAR(200) COMMENT '路由地址',
    menu_type   CHAR(1)  DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    icon        VARCHAR(100) COMMENT '菜单图标',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';

-- 5. 用户-角色关联表
CREATE TABLE sys_user_role
(
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES sys_user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES sys_role (role_id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户和角色关联表';

-- 6. 角色-菜单关联表
CREATE TABLE sys_role_menu
(
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id),
    FOREIGN KEY (role_id) REFERENCES sys_role (role_id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES sys_menu (menu_id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和菜单关联表';

-- 1. 插入用户
-- ======================
-- 初始化虚拟数据
-- ======================

-- 1. 插入用户
INSERT INTO sys_user (user_id, username, password, nickname, status)
VALUES (1, 'admin', '$2a$10$IY6oj602.b7u5Kp1mO.B7OxUJxetAGwyXKv7o4vW7fDO7Qo5hQH6S', '超级管理员', '0'),
       (2, 'khr', '$2a$10$IY6oj602.b7u5Kp1mO.B7OxUJxetAGwyXKv7o4vW7fDO7Qo5hQH6S', '测试用户', '0'),
       (3, 'tom', '$2a$10$IY6oj602.b7u5Kp1mO.B7OxUJxetAGwyXKv7o4vW7fDO7Qo5hQH6S', '子管理员', '0');

-- 2. 插入角色
INSERT INTO sys_role (role_id, role_name, role_key, status)
VALUES (1, '超级管理员', 'admin', '0'),
       (2, '普通用户', 'user', '0'),
       (3, '子管理员', 'sub_admin', '0');

-- 3. 插入菜单（包含子菜单）
-- 顶级菜单
INSERT INTO sys_menu (menu_id, menu_name, perms, url, parent_id, menu_type, order_num)
VALUES (1, '用户管理', 'system:user:list', '/system/user', 0, 'M', 1),
       (2, '角色管理', 'system:role:list', '/system/role', 0, 'M', 2),
       (3, '菜单管理', 'system:menu:list', '/system/menu', 0, 'M', 3),
       (4, '系统监控', NULL, '/monitor', 0, 'M', 4),
       (5, '日志管理', NULL, '/log', 0, 'M', 5);

-- 用户管理 → 子菜单
INSERT INTO sys_menu (menu_id, menu_name, perms, url, parent_id, menu_type, order_num)
VALUES (11, '新增用户', 'system:user:add', '/system/user/add', 1, 'C', 1),
       (12, '编辑用户', 'system:user:edit', '/system/user/edit', 1, 'C', 2),
       (13, '删除用户', 'system:user:delete', '/system/user/delete', 1, 'C', 3);

-- 角色管理 → 子菜单
INSERT INTO sys_menu (menu_id, menu_name, perms, url, parent_id, menu_type, order_num)
VALUES (21, '新增角色', 'system:role:add', '/system/role/add', 2, 'C', 1),
       (22, '编辑角色', 'system:role:edit', '/system/role/edit', 2, 'C', 2),
       (23, '分配权限', 'system:role:assign', '/system/role/assign', 2, 'C', 3);

-- 菜单管理 → 子菜单
INSERT INTO sys_menu (menu_id, menu_name, perms, url, parent_id, menu_type, order_num)
VALUES (31, '新增菜单', 'system:menu:add', '/system/menu/add', 3, 'C', 1),
       (32, '编辑菜单', 'system:menu:edit', '/system/menu/edit', 3, 'C', 2);

-- 系统监控 → 子菜单
INSERT INTO sys_menu (menu_id, menu_name, perms, url, parent_id, menu_type, order_num)
VALUES (41, '在线用户', 'monitor:online:list', '/monitor/online', 4, 'C', 1),
       (42, '服务监控', 'monitor:server:view', '/monitor/server', 4, 'C', 2);

-- 日志管理 → 子菜单
INSERT INTO sys_menu (menu_id, menu_name, perms, url, parent_id, menu_type, order_num)
VALUES (51, '操作日志', 'log:operation:list', '/log/operation', 5, 'C', 1),
       (52, '登录日志', 'log:login:list', '/log/login', 5, 'C', 2);

-- 4. 用户-角色关联
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1), -- admin → 超级管理员
       (2, 2), -- khr → 普通用户
       (3, 3); -- tom → 子管理员

-- 5. 角色-菜单关联
-- 超级管理员 → 全部菜单
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu;

-- 普通用户 → 只允许查看用户管理、查看角色管理（不能操作）
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 1), (2, 2);

-- 子管理员 → 拥有用户管理和角色管理的全部操作权限，但没有系统监控和日志管理
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 1), (3, 11), (3, 12), (3, 13), -- 用户管理全套
       (3, 2), (3, 21), (3, 22), (3, 23); -- 角色管理全套
