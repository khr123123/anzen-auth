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

 Date: 03/10/2025 09:45:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识（如：system:user:list）',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由和组件地址',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'System', 0, 1, NULL, NULL, 'M', 'Settings', '2025-09-19 08:53:10', '2025-09-25 17:03:11');
INSERT INTO `sys_menu` VALUES (2, 'User', 1, 1, 'sys:user:query', '/sys/user/index', 'C', 'User', '2025-09-19 08:53:10', '2025-09-25 17:02:47');
INSERT INTO `sys_menu` VALUES (3, 'Role', 1, 2, 'sys:role:query', '/sys/role/index', 'C', 'CustomerService', '2025-09-19 08:53:10', '2025-09-25 17:02:47');
INSERT INTO `sys_menu` VALUES (4, 'Menu', 1, 3, 'sys:menu:query', '/sys/menu/index', 'C', 'List', '2025-09-19 08:53:10', '2025-09-25 17:02:47');
INSERT INTO `sys_menu` VALUES (5, 'AddUser', 2, NULL, 'sys:user:add', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-26 11:56:56');
INSERT INTO `sys_menu` VALUES (6, 'EditUser', 2, NULL, 'sys:user:edit', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-26 11:56:56');
INSERT INTO `sys_menu` VALUES (7, 'DelUser', 2, NULL, 'sys:user:delete', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-09-26 11:56:56');
INSERT INTO `sys_menu` VALUES (8, 'AddRole', 3, NULL, 'sys:role:add', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-10-03 09:31:31');
INSERT INTO `sys_menu` VALUES (9, 'EditRole', 3, NULL, 'sys:role:edit', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-10-03 09:31:32');
INSERT INTO `sys_menu` VALUES (10, 'DelRole', 3, NULL, 'sys:role:delete', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-10-03 09:31:32');
INSERT INTO `sys_menu` VALUES (11, 'AddMenu', 4, NULL, 'sys:menu:add', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-10-03 09:31:32');
INSERT INTO `sys_menu` VALUES (12, 'EditMenu', 4, NULL, 'sys:menu:edit', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-10-03 09:31:32');
INSERT INTO `sys_menu` VALUES (13, 'DelMenu', 4, NULL, 'sys:menu:delete', NULL, 'F', NULL, '2025-09-19 08:53:10', '2025-10-03 09:31:32');
INSERT INTO `sys_menu` VALUES (101, 'AssignRole', 2, NULL, 'sys:user:role', NULL, 'F', NULL, '2025-09-19 13:54:02', '2025-10-03 09:32:28');
INSERT INTO `sys_menu` VALUES (102, 'AssignPerm', 3, NULL, 'sys:role:perm', NULL, 'F', NULL, '2025-09-19 15:04:45', '2025-10-03 09:32:28');
INSERT INTO `sys_menu` VALUES (1001, 'Log', 1, 4, 'sys:log:query', '/sys/log/index', 'C', 'CalendarClock', '2025-09-24 14:34:16', '2025-09-25 17:02:47');
INSERT INTO `sys_menu` VALUES (1002, 'Server', 1, 5, 'sys:monitor:query', '/sys/monitor/index', 'C', 'Eye', '2025-09-25 10:13:00', '2025-09-25 17:02:47');
INSERT INTO `sys_menu` VALUES (1003, 'DataSource', 1, 6, 'sys:db:query', '/sys/db/index', 'C', 'Eye', '2025-10-02 14:48:58', '2025-10-02 14:48:58');
INSERT INTO `sys_menu` VALUES (1004, 'RedisMonitor', 1, 7, 'sys:redis:query', '/sys/redis/index', 'C', 'Eye', '2025-10-02 15:58:49', '2025-10-02 15:58:49');
INSERT INTO `sys_menu` VALUES (100001, 'MyGitHub', 0, 2, NULL, 'https://github.com/khr123123', 'M', 'Github', '2025-10-02 16:27:15', '2025-10-02 16:42:10');

-- ----------------------------
-- Table structure for sys_opera_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_opera_log`;
CREATE TABLE `sys_opera_log`  (
  `opera_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `opera_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `opera_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `opera_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `opera_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `opera_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `opera_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`opera_id`) USING BTREE,
  INDEX `idx_sys_opera_log_bt`(`business_type` ASC) USING BTREE,
  INDEX `idx_sys_opera_log_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_opera_log_ot`(`opera_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 158 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串（如：admin, user）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `role_key`(`role_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'Super Admin', 'admin', '0', '2025-09-19 08:53:11');
INSERT INTO `sys_role` VALUES (2, 'Personnel Admin', 'user', '0', '2025-09-19 08:53:11');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `menu_id`(`menu_id` ASC) USING BTREE,
  CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 101);
INSERT INTO `sys_role_menu` VALUES (1, 102);
INSERT INTO `sys_role_menu` VALUES (1, 1001);
INSERT INTO `sys_role_menu` VALUES (1, 1002);
INSERT INTO `sys_role_menu` VALUES (1, 1003);
INSERT INTO `sys_role_menu` VALUES (1, 1004);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonimaas1239', 'http://localhost:8080/api/uploads/49b46c94-9a84-4943-bb2d-b90d0800f886.jpg', '123@example.coma', '0', '2025-09-19 08:47:22', '2025-10-03 09:36:34');
INSERT INTO `sys_user` VALUES (2, 'zhang', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonima', 'https://avatars.githubusercontent.com/u/199218017?s=400&u=1b0fe516cea77fb8830638e62b0bcd0f900c0d87&v=4', 'zhangsan@example.com', '0', '2025-09-19 08:53:11', '2025-09-22 12:02:26');
INSERT INTO `sys_user` VALUES (3, 'caoni', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'caonima', 'https://avatars.githubusercontent.com/u/199218017?s=400&u=1b0fe516cea77fb8830638e62b0bcd0f900c0d87&v=4', 'lisi@example.com', '0', '2025-09-19 08:53:11', '2025-09-22 12:02:26');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;
