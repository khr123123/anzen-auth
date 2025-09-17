-- 1. 创建数据库
CREATE DATABASE `anzen_auth` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE anzen_auth;

-- 2. 用户表
CREATE TABLE sys_user
(
    user_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '登录账号',
    password    VARCHAR(100) NOT NULL COMMENT '密码',
    nickname    VARCHAR(50) COMMENT '昵称',
    status      CHAR(1)   DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 3. 角色表
CREATE TABLE sys_role
(
    role_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name   VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key    VARCHAR(50) NOT NULL UNIQUE COMMENT '角色权限字符串（如：admin, user）',
    status      CHAR(1)   DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';

-- 4. 菜单/权限表
CREATE TABLE sys_menu
(
    menu_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    menu_name   VARCHAR(50) NOT NULL COMMENT '菜单名称',
    perms       VARCHAR(100) COMMENT '权限标识（如：system:user:list）',
    url         VARCHAR(200) COMMENT '路由地址',
    parent_id   BIGINT    DEFAULT 0 COMMENT '父菜单ID',
    order_num   INT       DEFAULT 0 COMMENT '显示顺序',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
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
INSERT INTO sys_user (user_id, username, password, nickname, status)
VALUES (1, 'admin', '$2a$10$abcdefghijklmnopqrstuv', '超级管理员', '0'), -- 密码是 bcrypt 加密（可以替换成你自己的）
       (2, 'khr', '$2a$10$abcdefghijklmnopqrstuv', '测试用户', '0');

-- 2. 插入角色
INSERT INTO sys_role (role_id, role_name, role_key, status)
VALUES (1, '超级管理员', 'admin', '0'),
       (2, '普通用户', 'user', '0');

-- 3. 插入菜单（权限点）
INSERT INTO sys_menu (menu_id, menu_name, perms, url, parent_id, order_num)
VALUES (1, '用户管理', 'system:user:list', '/system/user', 0, 1),
       (2, '角色管理', 'system:role:list', '/system/role', 0, 2),
       (3, '菜单管理', 'system:menu:list', '/system/menu', 0, 3);

-- 4. 用户-角色关联
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1), -- admin → 超级管理员
       (2, 2);
-- khr → 普通用户

-- 5. 角色-菜单关联
-- 超级管理员拥有全部菜单
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);

-- 普通用户只拥有用户管理权限
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 1);
