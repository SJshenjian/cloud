/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36-0ubuntu0.22.04.1)
 Source Host           : localhost:3306
 Source Schema         : cloud

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36-0ubuntu0.22.04.1)
 File Encoding         : 65001

 Date: 02/06/2024 18:30:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `file_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `file_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '文件类型(1:WORD，2:EXCEL，3:PPT，4:PDF，5:TXT)',
  `file_size` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '文件大小',
  `file_location` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '文件地址',
  `store_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '文件存储名',
  `show_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '文件显示名',
  `remark` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '修改人',
  `del_flag` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='附件';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `module_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '菜单ID',
  `module_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单名',
  `module_url` varchar(258) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单链接',
  `module_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单编码',
  `sort_code` int DEFAULT NULL COMMENT '排序码',
  `parent_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父菜单ID',
  `parent_name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父菜单名',
  PRIMARY KEY (`module_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单资源表';

-- ----------------------------
-- Records of sys_module
-- ----------------------------
BEGIN;
INSERT INTO `sys_module` (`module_id`, `module_name`, `module_url`, `module_code`, `sort_code`, `parent_id`, `parent_name`) VALUES ('001', '算法小生', NULL, NULL, 1, '-1', NULL);
INSERT INTO `sys_module` (`module_id`, `module_name`, `module_url`, `module_code`, `sort_code`, `parent_id`, `parent_name`) VALUES ('1797210739841884160', '机构管理', '/org', 'org', 1, '001', '算法小生');
INSERT INTO `sys_module` (`module_id`, `module_name`, `module_url`, `module_code`, `sort_code`, `parent_id`, `parent_name`) VALUES ('1797210846234599424', '菜单管理', '/module', 'module', 2, '001', '算法小生');
INSERT INTO `sys_module` (`module_id`, `module_name`, `module_url`, `module_code`, `sort_code`, `parent_id`, `parent_name`) VALUES ('1797210920687689728', '用户管理', '/user', 'user', 3, '001', '算法小生');
INSERT INTO `sys_module` (`module_id`, `module_name`, `module_url`, `module_code`, `sort_code`, `parent_id`, `parent_name`) VALUES ('1797211013025292288', '菜单管理', '/role', 'role', 4, '001', '算法小生');
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `org_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '机构ID',
  `org_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '机构编码',
  `org_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '机构名称',
  `parent_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父机构ID',
  `parent_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父机构编码',
  `parent_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父机构名称',
  `sort_code` int DEFAULT NULL COMMENT '排序码',
  `remark` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '删除标记 0正常 1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`org_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='机构信息表';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` (`org_id`, `org_code`, `org_name`, `parent_id`, `parent_code`, `parent_name`, `sort_code`, `remark`, `del_flag`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('1797211572465754112', '001.001', '欢迎关注我', NULL, '001', '算法小生', 1, '', '0', '2024-06-02 18:20:04', 'admin', NULL, NULL);
INSERT INTO `sys_org` (`org_id`, `org_code`, `org_name`, `parent_id`, `parent_code`, `parent_name`, `sort_code`, `remark`, `del_flag`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('4037a607-de4a-11e7-b521-4437e6437701', '001', '算法小生', '-1', '-1', NULL, 1, '', '0', '2023-08-01 13:31:21', 'admin', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色ID',
  `role_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色名',
  `remark` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`role_id`, `role_name`, `remark`) VALUES ('admin', '管理员', '各组织机构管理员');
INSERT INTO `sys_role` (`role_id`, `role_name`, `remark`) VALUES ('normal', '一般用户', '普通用户');
INSERT INTO `sys_role` (`role_id`, `role_name`, `remark`) VALUES ('super-admin', '超级管理员', '	系统管理员，全部管理功能，不能删除！！');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_module`;
CREATE TABLE `sys_role_module` (
  `role_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色ID',
  `module_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色与菜单资源关系表';

-- ----------------------------
-- Records of sys_role_module
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_module` (`role_id`, `module_id`) VALUES ('super-admin', '001');
INSERT INTO `sys_role_module` (`role_id`, `module_id`) VALUES ('super-admin', '1797210739841884160');
INSERT INTO `sys_role_module` (`role_id`, `module_id`) VALUES ('super-admin', '1797210846234599424');
INSERT INTO `sys_role_module` (`role_id`, `module_id`) VALUES ('super-admin', '1797210920687689728');
INSERT INTO `sys_role_module` (`role_id`, `module_id`) VALUES ('super-admin', '1797211013025292288');
INSERT INTO `sys_role_module` (`role_id`, `module_id`) VALUES ('normal', '1797210846234599424');
COMMIT;

-- ----------------------------
-- Table structure for usr_user
-- ----------------------------
DROP TABLE IF EXISTS `usr_user`;
CREATE TABLE `usr_user` (
  `user_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户名',
  `account` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户账号',
  `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '密码',
  `org_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '机构编码',
  `role_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色ID',
  `department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '部门',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '职称',
  `employee_number` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '工号',
  `state` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '状态 0启用 1禁用',
  `phone_number` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '手机号码',
  `gender` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '性别 0男 1女',
  `del_flag` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '删除标记 0正常 1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- ----------------------------
-- Records of usr_user
-- ----------------------------
BEGIN;
INSERT INTO `usr_user` (`user_id`, `username`, `account`, `password`, `org_code`, `role_id`, `department`, `title`, `employee_number`, `state`, `phone_number`, `gender`, `del_flag`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('1', '超级管理员', 'admin', 'c4ca4238a0b923820dcc509a6f75849b', '001', 'super-admin', NULL, NULL, NULL, '0', '1234567990', '', '0', '2023-07-24 16:18:36', 'admin', '2024-06-02 18:02:49', 'admin');
INSERT INTO `usr_user` (`user_id`, `username`, `account`, `password`, `org_code`, `role_id`, `department`, `title`, `employee_number`, `state`, `phone_number`, `gender`, `del_flag`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES ('1797211702321405952', 'shen', 'shen', 'e10adc3949ba59abbe56e057f20f883e', '001.001', 'normal', NULL, NULL, NULL, '0', NULL, NULL, '0', '2024-06-02 18:20:35', 'admin', '2024-06-02 18:28:03', 'admin');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
