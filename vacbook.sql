/*
 Navicat Premium Data Transfer

 Source Server         : xiaopi
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : vacbook

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 08/10/2021 15:53:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_account` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location_id` int(11) NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `admin_account`(`admin_account`) USING BTREE,
  INDEX `location_id`(`location_id`) USING BTREE,
  CONSTRAINT `location_id` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'zliu3553', '123456', 'zhengcheng', 1);
INSERT INTO `admin` VALUES (2, 'account1', '123', 'Worde', 2);
INSERT INTO `admin` VALUES (3, 'account2', '321', 'Kevin', 3);
INSERT INTO `admin` VALUES (4, 'account3', '111111', 'Renee', 4);
INSERT INTO `admin` VALUES (5, 'account4', '222222', 'James', 5);
INSERT INTO `admin` VALUES (6, 'account5', '333333', 'Noname', 6);
INSERT INTO `admin` VALUES (7, 'hahah', 'changePassword', 'Lucy', 1);
INSERT INTO `admin` VALUES (8, '123', '123456', 'asd', 1);
INSERT INTO `admin` VALUES (9, '12322', '123', 'sadf', 2);
INSERT INTO `admin` VALUES (14, 'test1', '202cb962ac59075b964b07152d234b70', 'test1', 1);

-- ----------------------------
-- Table structure for booking
-- ----------------------------
DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking`  (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `vaccine_id` int(11) NOT NULL,
  `booking_timezone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`booking_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `booking_ibfk_1`(`vaccine_id`) USING BTREE,
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`vaccine_id`) REFERENCES `vaccine` (`vaccine_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of booking
-- ----------------------------
INSERT INTO `booking` VALUES (1, 1, 2, '13:00-14:00', '2021-09-10');
INSERT INTO `booking` VALUES (2, 2, 2, '13:00-14:00', '2021-09-12');
INSERT INTO `booking` VALUES (3, 3, 1, '11:00-12:00', '2021-09-11');
INSERT INTO `booking` VALUES (4, 3, 3, '11:00-17:00', '2021-09-20');

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location`  (
  `location_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`location_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of location
-- ----------------------------
INSERT INTO `location` VALUES (1, 'South Australia');
INSERT INTO `location` VALUES (2, 'Queensland');
INSERT INTO `location` VALUES (3, 'New South Wales');
INSERT INTO `location` VALUES (4, 'Victoria');
INSERT INTO `location` VALUES (5, 'Tasmania');
INSERT INTO `location` VALUES (6, 'Australian Capital Territory');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_lastname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_firstname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` int(3) NOT NULL,
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_account` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_question` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_safe_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_account`(`user_account`) USING BTREE,
  INDEX `age`(`age`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1234567890', '1@163.com', 'Liu', 'Gray', 'male', 'shanghai', 3, '123456', 'zliu3553', '1', '1');
INSERT INTO `user` VALUES (2, '1234567890', '1@163.com', 'X', 'James', 'male', 'Sydney', 3, '123456', '1', '1', '1');
INSERT INTO `user` VALUES (3, '1234567890', '1@163.com', 'X', 'Kevin', 'male', 'Sydney', 3, '123456', '2', '1', '1');
INSERT INTO `user` VALUES (4, '1234567890', '1@163.com', 'X', 'Worde', 'male', 'Sydney', 3, '123456', '3', '1', '1');
INSERT INTO `user` VALUES (5, '1234567890', '1@163.com', 'Li', 'Renee', 'female', 'Sydney', 3, '123456', '4', '1', '1');

-- ----------------------------
-- Table structure for vaccine
-- ----------------------------
DROP TABLE IF EXISTS `vaccine`;
CREATE TABLE `vaccine`  (
  `vaccine_id` int(11) NOT NULL AUTO_INCREMENT,
  `vaccine_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vaccine_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vaccine_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_id` int(11) NOT NULL,
  `vaccine_amount` int(11) NOT NULL,
  PRIMARY KEY (`vaccine_id`) USING BTREE,
  INDEX `admin_id1`(`admin_id`) USING BTREE,
  CONSTRAINT `admin_id1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vaccine
-- ----------------------------
INSERT INTO `vaccine` VALUES (1, 'mRNA', 'Pfizer', '666', 1, 300);
INSERT INTO `vaccine` VALUES (2, 'mRNA', 'AZ', '666', 1, 222);
INSERT INTO `vaccine` VALUES (3, 'mRNA', 'AZ', '666', 2, 223);
INSERT INTO `vaccine` VALUES (4, 'mRNA', 'AZ', '666', 3, 111);
INSERT INTO `vaccine` VALUES (5, 'mRNA', 'Pfizer', '666', 3, 11111);
INSERT INTO `vaccine` VALUES (17, '213', '21312', ' ', 2, 312321);
INSERT INTO `vaccine` VALUES (18, '213123', '123123', ' ', 2, 12312);
INSERT INTO `vaccine` VALUES (19, '421412', '214', ' ', 2, 123);
INSERT INTO `vaccine` VALUES (20, '2', '3', ' ', 2, 4);

SET FOREIGN_KEY_CHECKS = 1;
