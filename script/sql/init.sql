-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS `anzen_auth`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE anzen_auth;

-- 2. 用户表
CREATE TABLE sys_user
(
    user_id       BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username      VARCHAR(50)                                                   NOT NULL UNIQUE COMMENT '登录账号',
    password      VARCHAR(100)                                                  NOT NULL COMMENT '密码',
    nickname      VARCHAR(50) COMMENT '昵称',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
    `email`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
    status        CHAR(1)                                                            DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    create_time   DATETIME                                                           DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
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
    menu_id       BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    menu_name     VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id     BIGINT           DEFAULT 0 COMMENT '父菜单ID',
    order_num     INT              DEFAULT 0 COMMENT '显示顺序',
    perms         VARCHAR(100) COMMENT '权限标识（如：system:user:list）',
    url           VARCHAR(200) COMMENT '路由和组件地址',
    menu_type     CHAR(1)          DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    icon          VARCHAR(100) COMMENT '菜单图标',
    create_time   DATETIME         DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME    NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
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

-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 'admin', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonima',
        'https://i.pravatar.cc/150?img=1',
        'admin@example.com', '0', '2025-09-19 08:47:22', '2025-09-19 08:53:11');
INSERT INTO `sys_user`
VALUES (2, 'zhangsan', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye',
        'caonima', 'https://i.pravatar.cc/150?img=2', 'zhangsan@example.com', '0', '2025-09-19 08:53:11',
        '2025-09-19 08:53:11');
INSERT INTO `sys_user`
VALUES (3, 'caonima', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonima',
        'https://i.pravatar.cc/150?img=3',
        'lisi@example.com', '0', '2025-09-19 08:53:11', '2025-09-19 08:53:11');

INSERT INTO `sys_role`
VALUES (1, '系统管理员', 'admin', '0', '2025-09-19 08:53:11');
INSERT INTO `sys_role`
VALUES (2, '普通用户', 'user', '0', '2025-09-19 08:53:11');

INSERT INTO `sys_menu`
VALUES (1, '系统管理', 0, 1, '', NULL, 'M', 'SettingOutlined', '2025-09-19 08:53:10', '2025-09-19 08:57:33');
INSERT INTO `sys_menu`
VALUES (2, '用户管理', 1, 1, 'system:user:query', 'sys/user/index', 'C', 'UserOutlined',
        '2025-09-19 08:53:10', '2025-09-19 09:47:01');
INSERT INTO `sys_menu`
VALUES (3, '角色管理', 1, 2, 'system:role:query', 'sys/role/index', 'C', 'TeamOutlined',
        '2025-09-19 08:53:10', '2025-09-19 09:47:01');
INSERT INTO `sys_menu`
VALUES (4, '菜单管理', 1, 3, 'system:menu:query', 'sys/menu/index', 'C', 'MenuOutlined',
        '2025-09-19 08:53:10', '2025-09-19 09:47:01');
INSERT INTO `sys_menu`
VALUES (5, '新增用户', 2, 1, 'system:user:add', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:24:33');
INSERT INTO `sys_menu`
VALUES (6, '修改用户', 2, 2, 'system:user:edit', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:24:33');
INSERT INTO `sys_menu`
VALUES (7, '删除用户', 2, 3, 'system:user:delete', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:24:33');
INSERT INTO `sys_menu`
VALUES (8, '新增角色', 3, 1, 'system:role:add', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:21:01');
INSERT INTO `sys_menu`
VALUES (9, '修改角色', 3, 2, 'system:role:edit', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:21:01');
INSERT INTO `sys_menu`
VALUES (10, '删除角色', 3, 3, 'system:role:delete', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:21:01');
INSERT INTO `sys_menu`
VALUES (11, '新增菜单', 4, 1, 'system:menu:add', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:22:01');
INSERT INTO `sys_menu`
VALUES (12, '修改菜单', 4, 2, 'system:menu:edit', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:22:01');
INSERT INTO `sys_menu`
VALUES (13, '删除菜单', 4, 3, 'system:menu:delete', '', 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 09:22:01');

INSERT INTO `sys_user_role`
VALUES (1, 1);
INSERT INTO `sys_user_role`
VALUES (2, 2);
INSERT INTO `sys_user_role`
VALUES (3, 2);


-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (1, 1);
INSERT INTO `sys_role_menu`
VALUES (1, 2);
INSERT INTO `sys_role_menu`
VALUES (1, 3);
INSERT INTO `sys_role_menu`
VALUES (1, 4);
INSERT INTO `sys_role_menu`
VALUES (1, 5);
INSERT INTO `sys_role_menu`
VALUES (1, 6);
INSERT INTO `sys_role_menu`
VALUES (1, 7);
INSERT INTO `sys_role_menu`
VALUES (1, 8);
INSERT INTO `sys_role_menu`
VALUES (1, 9);
INSERT INTO `sys_role_menu`
VALUES (1, 10);
INSERT INTO `sys_role_menu`
VALUES (1, 11);
INSERT INTO `sys_role_menu`
VALUES (1, 12);
INSERT INTO `sys_role_menu`
VALUES (1, 13);
INSERT INTO `sys_role_menu`
VALUES (2, 1);
INSERT INTO `sys_role_menu`
VALUES (2, 2);
INSERT INTO `sys_role_menu`
VALUES (2, 5);
INSERT INTO `sys_role_menu`
VALUES (2, 6);
INSERT INTO `sys_role_menu`
VALUES (2, 7);