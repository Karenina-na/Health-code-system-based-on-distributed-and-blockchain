/*
 Navicat Premium Data Transfer

 Source Server         : 云边协同区块链一码通系统数据库
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.71.129:3306
 Source Schema         : ybxt_db

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 23/09/2022 00:09:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for nucleic_acid_data_tb
-- ----------------------------
DROP TABLE IF EXISTS `nucleic_acid_data_tb`;
CREATE TABLE `nucleic_acid_data_tb`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `person_id` int(0) NOT NULL,
  `nucleic_acid_company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nucleic_acid_sample_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nucleic_acid_sample_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nucleic_acid_test_result_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nucleic_acid_test_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nucleic_acid_test_result` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `nucleic_person_id`(`person_id`) USING BTREE,
  CONSTRAINT `nucleic_person_id` FOREIGN KEY (`person_id`) REFERENCES `person_data_tb` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for person_data_tb
-- ----------------------------
DROP TABLE IF EXISTS `person_data_tb`;
CREATE TABLE `person_data_tb`  (
  `id` int(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` char(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `identity_number` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone_number` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `identity_number`(`identity_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trace_data_tb
-- ----------------------------
DROP TABLE IF EXISTS `trace_data_tb`;
CREATE TABLE `trace_data_tb`  (
  `id` int(0) NOT NULL,
  `person_id` int(0) NOT NULL,
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `street` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `trace_person_id`(`person_id`) USING BTREE,
  CONSTRAINT `trace_person_id` FOREIGN KEY (`person_id`) REFERENCES `person_data_tb` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vaccines_data_tb
-- ----------------------------
DROP TABLE IF EXISTS `vaccines_data_tb`;
CREATE TABLE `vaccines_data_tb`  (
  `id` int(0) NOT NULL,
  `person_id` int(0) NOT NULL,
  `vaccines_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `vaccines_company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `vaccines_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `vaccines_person_id`(`person_id`) USING BTREE,
  CONSTRAINT `vaccines_person_id` FOREIGN KEY (`person_id`) REFERENCES `person_data_tb` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
