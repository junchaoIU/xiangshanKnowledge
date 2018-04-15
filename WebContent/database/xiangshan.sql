/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50638
 Source Host           : localhost
 Source Database       : xiangshan

 Target Server Type    : MySQL
 Target Server Version : 50638
 File Encoding         : utf-8

 Date: 04/15/2018 23:35:28 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `query_history`
-- ----------------------------
DROP TABLE IF EXISTS `query_history`;
CREATE TABLE `query_history` (
  `id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `subject` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `predicate` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `object` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `scope` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `time_space_scope` varchar(15) COLLATE utf8mb4_bin DEFAULT NULL,
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `create_date` datetime NOT NULL,
  `user_name` varchar(15) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_name` (`user_name`),
  CONSTRAINT `query_history_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `user_info` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_name` varchar(30) COLLATE utf8mb4_bin NOT NULL,
  `user_password` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `user_old` int(2) DEFAULT NULL,
  `user_role` varchar(10) COLLATE utf8mb4_bin NOT NULL,
  `register_date` datetime NOT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
