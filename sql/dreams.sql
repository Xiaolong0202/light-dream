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

 Date: 08/11/2023 16:36:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `child_user_id` bigint(0) NOT NULL COMMENT '儿童用户ID',
  `task_id` bigint(0) NOT NULL COMMENT '任务ID',
  `answer_status` tinyint(1) NOT NULL COMMENT '回答状态 (枚举类型暂时还没有确定)',
  `answer_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '回答内容',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片URL地址',
  `score` int(0) NULL DEFAULT NULL COMMENT '分数',
  `comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评语',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名字',
  `admin_id` bigint(0) NOT NULL COMMENT '管理者ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务描述',
  `total_score` int(0) NOT NULL COMMENT '任务总分',
  `difficulty` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '任务难度级别',
  `priority` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '任务优先级',
  `budget` int(0) NULL DEFAULT NULL COMMENT '任务预算',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '任务截止时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '任务开始时间',
  `submit_form` tinyint(1) NULL DEFAULT NULL COMMENT '任务提交形式',
  `type` tinyint(1) NOT NULL COMMENT '任务类型（枚举类型暂时还没有确定）',
  `isdelete` tinyint(1) NOT NULL COMMENT '表示任务是否删除，0未删除，1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `volunteer_id` bigint(0) NULL DEFAULT NULL COMMENT '对应的志愿者ID,如果该用户类型为志愿者，该字段就为空',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `user_type` tinyint(1) NOT NULL COMMENT '用户类型枚举| 儿童为1 志愿者为 2',
  `score` int(0) NULL DEFAULT 0 COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
