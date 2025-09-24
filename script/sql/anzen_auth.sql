/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : anzen_auth

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 19/09/2025 17:49:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`     bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint                                                        NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   int                                                           NULL DEFAULT 0 COMMENT '显示顺序',
    `perms`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识（如：system:user:list）',
    `url`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由和组件地址',
    `menu_type`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `icon`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
    `create_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 103
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, '系统管理', 0, 1, NULL, NULL, 'M', 'Settings', '2025-09-19 08:53:10', '2025-09-19 13:54:20');
INSERT INTO `sys_menu`
VALUES (2, '用户管理', 1, 1, 'sys:user:query', '/sys/user/index', 'C', 'User', '2025-09-19 08:53:10',
        '2025-09-19 13:29:51');
INSERT INTO `sys_menu`
VALUES (3, '角色管理', 1, 2, 'sys:role:query', '/sys/role/index', 'C', 'CustomerService', '2025-09-19 08:53:10',
        '2025-09-19 13:32:27');
INSERT INTO `sys_menu`
VALUES (4, '菜单管理', 1, 3, 'sys:menu:query', '/sys/menu/index', 'C', 'List', '2025-09-19 08:53:10',
        '2025-09-19 13:31:32');
INSERT INTO `sys_menu`
VALUES (5, '新增用户', 2, NULL, 'sys:user:add', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (6, '修改用户', 2, NULL, 'sys:user:edit', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (7, '删除用户', 2, NULL, 'sys:user:delete', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (8, '新增角色', 3, NULL, 'sys:role:add', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (9, '修改角色', 3, NULL, 'sys:role:edit', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (10, '删除角色', 3, NULL, 'sys:role:delete', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (11, '新增菜单', 4, NULL, 'sys:menu:add', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (12, '修改菜单', 4, NULL, 'sys:menu:edit', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (13, '删除菜单', 4, NULL, 'sys:menu:delete', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-19 13:54:13');
INSERT INTO `sys_menu`
VALUES (101, '分配角色', 2, NULL, 'sys:user:role', NULL, 'F', NULL, '2025-09-19 13:54:02', '2025-09-19 13:55:30');
INSERT INTO `sys_menu`
VALUES (102, '分配权限', 3, NULL, 'sys:role:perm', NULL, 'F', NULL, '2025-09-19 15:04:45', '2025-09-19 15:04:45');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`     bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
    `role_key`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串（如：admin, user）',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `create_time` datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`role_id`) USING BTREE,
    UNIQUE INDEX `role_key` (`role_key` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '系统管理员', 'admin', '0', '2025-09-19 08:53:11');
INSERT INTO `sys_role`
VALUES (2, '普通用户', 'user', '0', '2025-09-19 08:53:11');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint NOT NULL COMMENT '角色ID',
    `menu_id` bigint NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
    INDEX `menu_id` (`menu_id` ASC) USING BTREE,
    CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `sys_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (1, 1);
INSERT INTO `sys_role_menu`
VALUES (2, 1);
INSERT INTO `sys_role_menu`
VALUES (1, 2);
INSERT INTO `sys_role_menu`
VALUES (2, 2);
INSERT INTO `sys_role_menu`
VALUES (1, 3);
INSERT INTO `sys_role_menu`
VALUES (2, 3);
INSERT INTO `sys_role_menu`
VALUES (1, 4);
INSERT INTO `sys_role_menu`
VALUES (1, 5);
INSERT INTO `sys_role_menu`
VALUES (2, 5);
INSERT INTO `sys_role_menu`
VALUES (1, 6);
INSERT INTO `sys_role_menu`
VALUES (2, 6);
INSERT INTO `sys_role_menu`
VALUES (1, 7);
INSERT INTO `sys_role_menu`
VALUES (2, 7);
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
VALUES (1, 101);
INSERT INTO `sys_role_menu`
VALUES (1, 102);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '登录账号',
    `password`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `nickname`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '昵称',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
    `email`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    `create_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `username` (`username` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, 'admin', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonima',
        'https://i.pravatar.cc/150?img=2', 'admin@example.com', '0', '2025-09-19 08:47:22', '2025-09-19 10:27:52');
INSERT INTO `sys_user`
VALUES (2, 'zhang', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonima',
        'https://i.pravatar.cc/150?img=2', 'zhangsan@example.com', '0', '2025-09-19 08:53:11', '2025-09-19 14:15:35');
INSERT INTO `sys_user`
VALUES (3, 'caoni', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonima',
        'https://i.pravatar.cc/150?img=3', 'lisi@example.com', '0', '2025-09-19 08:53:11', '2025-09-19 14:15:35');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
    INDEX `role_id` (`role_id` ASC) USING BTREE,
    CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1, 1);
INSERT INTO `sys_user_role`
VALUES (2, 2);
INSERT INTO `sys_user_role`
VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;



create table sys_opera_log
(
    opera_id       bigint(20) not null auto_increment comment '日志主键',
    title          varchar(50)   default '' comment '模块标题',
    business_type  int(2)        default 0 comment '业务类型（0其它 1新增 2修改 3删除）',
    method         varchar(200)  default '' comment '方法名称',
    request_method varchar(10)   default '' comment '请求方式',
    opera_name     varchar(50)   default '' comment '操作人员',
    opera_url      varchar(255)  default '' comment '请求URL',
    opera_ip       varchar(128)  default '' comment '主机地址',
    opera_location varchar(255)  default '' comment '操作地点',
    opera_param    varchar(2000) default '' comment '请求参数',
    json_result    varchar(2000) default '' comment '返回参数',
    status         int(1)        default 0 comment '操作状态（0正常 1异常）',
    error_msg      varchar(2000) default '' comment '错误消息',
    opera_time     datetime comment '操作时间',
    cost_time      bigint(20)    default 0 comment '消耗时间',
    primary key (opera_id),
    key idx_sys_opera_log_bt (business_type),
    key idx_sys_opera_log_s (status),
    key idx_sys_opera_log_ot (opera_time)
) engine = innodb
  auto_increment = 100 comment = '操作日志记录';