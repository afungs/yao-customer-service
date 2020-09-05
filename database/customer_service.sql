/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : customer_service

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 05/09/2020 13:10:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cs_customer_services
-- ----------------------------
DROP TABLE IF EXISTS `cs_customer_services`;
CREATE TABLE `cs_customer_services`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `visitors_num` int(11) NULL DEFAULT 0 COMMENT '接待中的数量',
  `queue_up_num` int(11) NULL DEFAULT 0 COMMENT '排队中的数量',
  `auto_receive_customer_num` int(11) NULL DEFAULT 0 COMMENT '自动接待客户数量',
  `online` int(11) NOT NULL DEFAULT 0 COMMENT '是否在线',
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `deleted_at` datetime(6) NULL DEFAULT NULL COMMENT '删除时间',
  `updated_at` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `cs_customer_services_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `cs_users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客服表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_customer_services
-- ----------------------------
INSERT INTO `cs_customer_services` VALUES (1, 1, 10, 12, 10, 1, '2020-09-02 15:31:12.000000', NULL, NULL, 1);

-- ----------------------------
-- Table structure for cs_province_city_districts
-- ----------------------------
DROP TABLE IF EXISTS `cs_province_city_districts`;
CREATE TABLE `cs_province_city_districts`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `deleted_at` datetime(6) NULL DEFAULT NULL COMMENT '删除时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `updated_at` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `code` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '父级编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_province_city_districts
-- ----------------------------

-- ----------------------------
-- Table structure for cs_roles
-- ----------------------------
DROP TABLE IF EXISTS `cs_roles`;
CREATE TABLE `cs_roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `deleted_at` datetime(6) NULL DEFAULT NULL COMMENT '删除时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `updated_at` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `description` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_roles
-- ----------------------------
INSERT INTO `cs_roles` VALUES (1, '2020-08-28 15:32:35.000000', NULL, 1, NULL, NULL, 'USER');
INSERT INTO `cs_roles` VALUES (2, '2020-08-28 15:32:35.000000', NULL, 1, NULL, NULL, 'CUSTOMER_SERVICE');

-- ----------------------------
-- Table structure for cs_users
-- ----------------------------
DROP TABLE IF EXISTS `cs_users`;
CREATE TABLE `cs_users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `deleted_at` datetime(6) NULL DEFAULT NULL COMMENT '删除时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `updated_at` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `birthday` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生日',
  `nick_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `we_chat_openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `city_id` bigint(20) NULL DEFAULT NULL COMMENT '城市',
  `district_id` bigint(20) NULL DEFAULT NULL COMMENT '区县',
  `province_id` bigint(20) NULL DEFAULT NULL COMMENT '省份',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKofwa659sc0iofrlbokpbpbsbv`(`city_id`) USING BTREE,
  INDEX `FKex90q60qywjq30uyv4vim4ff1`(`district_id`) USING BTREE,
  INDEX `FKflwvx8fph98lttfc87bhob47h`(`province_id`) USING BTREE,
  INDEX `FK5ljxhpprfghxxg5i0p19hjy5q`(`role_id`) USING BTREE,
  CONSTRAINT `FK5ljxhpprfghxxg5i0p19hjy5q` FOREIGN KEY (`role_id`) REFERENCES `cs_roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKex90q60qywjq30uyv4vim4ff1` FOREIGN KEY (`district_id`) REFERENCES `cs_province_city_districts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKflwvx8fph98lttfc87bhob47h` FOREIGN KEY (`province_id`) REFERENCES `cs_province_city_districts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKofwa659sc0iofrlbokpbpbsbv` FOREIGN KEY (`city_id`) REFERENCES `cs_province_city_districts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cs_users
-- ----------------------------
INSERT INTO `cs_users` VALUES (1, '2020-08-28 15:32:49.000000', NULL, 1, NULL, 10, 'http://c-ssl.duitang.com/uploads/item/201906/07/20190607102735_vwwko.thumb.1000_0.jpg', NULL, '1111', '123456', '123456', 1, 'admin', NULL, NULL, NULL, NULL, 2);
INSERT INTO `cs_users` VALUES (2, '2020-08-28 15:32:49.000000', NULL, 1, NULL, 10, 'https://tupian.qqw21.com/article/UploadPic/2020-7/20207919362945695.jpg', NULL, '1111', '123456', '123456', 1, 'admin2', NULL, NULL, NULL, NULL, 1);
INSERT INTO `cs_users` VALUES (3, '2020-08-28 15:32:49.000000', NULL, 1, NULL, 10, 'https://tupian.qqw21.com/article/UploadPic/2020-7/20207320525576602.jpg', NULL, '1111', '123456', '123456', 1, 'admin3', NULL, NULL, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
