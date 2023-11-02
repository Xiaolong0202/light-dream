/*
 Navicat Premium Data Transfer

 Source Server         : MyConnect
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : dreams

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 02/11/2023 13:15:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名字',
  `volunteer_id` bigint(0) NOT NULL COMMENT '志愿者ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务描述',
  `total_score` int(0) NOT NULL COMMENT '任务总分',
  `deadline` datetime(0) NULL DEFAULT NULL COMMENT '任务截止时间',
  `type` tinyint(1) NOT NULL COMMENT '任务类型（枚举类型暂时还没有确定）',
  `delete` tinyint(1) NOT NULL COMMENT '表示任务是否删除，0未删除，1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
