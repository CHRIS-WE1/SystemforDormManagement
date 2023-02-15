/*
 Navicat Premium Data Transfer

 Source Server         : project
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 15/02/2023 21:59:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for build
-- ----------------------------
DROP TABLE IF EXISTS `build`;
CREATE TABLE `build`  (
  `buildid` int NOT NULL,
  `buildname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teacherno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teachername` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`buildid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of build
-- ----------------------------
INSERT INTO `build` VALUES (0, 'null', 'null', 'null', 'null');
INSERT INTO `build` VALUES (1, '明德9', '明德宿舍区9栋', '1', '李四');
INSERT INTO `build` VALUES (3, '明德10', '明德宿舍区10栋', '5', 'Chris');
INSERT INTO `build` VALUES (4, '明德2', '明德宿舍区2栋', '4', '帅哥');
INSERT INTO `build` VALUES (5, '明德3', '', '0', 'null');

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('0', '冯俊威', '男', 'admin', '123', '13977777777');

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission`  (
  `missionid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `begintime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `endtime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `publisherid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `executor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `filepath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`missionid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mission
-- ----------------------------
INSERT INTO `mission` VALUES (11, 'test2333', '888999', '2023-02-13 23:52', '2023-02-13 23:54', '0', '学生', '签到', '462f56ec-56c0-4c3c-bd2d-f8dca42a85e5_05 gettyimages-106629845_resized.jpg');
INSERT INTO `mission` VALUES (12, 'test2', '888', '2023-02-13 23:52', '2023-02-13 23:54', '0', '老师', '其它', 'e99c4a5f-5e8e-42df-954d-52fc5021397a_04.jpg');
INSERT INTO `mission` VALUES (14, '9987', '45654', '2023-02-13 23:52', '2023-02-20 23:52', '1', '明德9', '签到', NULL);
INSERT INTO `mission` VALUES (15, '4545', '454', '2023-02-13 23:52', '2023-02-20 23:52', '0', '学生', '签到', NULL);
INSERT INTO `mission` VALUES (16, '33', '44', '2023-02-12 23:52', '2023-02-19 23:52', '0', '学生', '签到', NULL);
INSERT INTO `mission` VALUES (17, '44', '33', '2023-02-12 23:52', '2023-02-19 23:52', '0', '学生', '其它', NULL);

-- ----------------------------
-- Table structure for mission_record
-- ----------------------------
DROP TABLE IF EXISTS `mission_record`;
CREATE TABLE `mission_record`  (
  `mission_record_id` int NOT NULL AUTO_INCREMENT,
  `missionid` int NOT NULL,
  `executorid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `executorstatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `filepath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`mission_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mission_record
-- ----------------------------
INSERT INTO `mission_record` VALUES (1, 12, '1', '已完成', '1db2783c-8477-488e-82fc-3a6918f09f7e_05 gettyimages-106629845_resized.jpg');
INSERT INTO `mission_record` VALUES (2, 11, '202001030129', '已完成', NULL);
INSERT INTO `mission_record` VALUES (3, 15, '202001030129', '已完成', '4a9f5ff5-ab74-444f-b2f4-a9fd9b1fbc43_16 gettyimages-1084578644_resized.jpg');
INSERT INTO `mission_record` VALUES (4, 16, '202001030129', '已完成', NULL);
INSERT INTO `mission_record` VALUES (5, 17, '202001030129', '已完成', 'ab9f1981-7a61-42a7-aa0e-47e7bc650058_04.jpg');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `recordid` int NOT NULL AUTO_INCREMENT,
  `no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `buildid` int NOT NULL,
  `roomid` int NOT NULL,
  `date` datetime NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`recordid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, '202001030129', '朱俊威', 0, 0, '2023-02-15 15:27:58', '已完成', NULL);
INSERT INTO `record` VALUES (2, '202001030129', '朱俊威', 0, 0, '2023-02-15 17:56:18', '请假', '不告诉你');
INSERT INTO `record` VALUES (3, '202001030129', '朱俊威', 0, 0, '2023-02-15 18:01:03', '已完成', NULL);

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `roomid` int NOT NULL,
  `buildid` int NOT NULL,
  `roomname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`roomid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (0, 0, 'null');
INSERT INTO `room` VALUES (1, 1, '401');
INSERT INTO `room` VALUES (3, 1, '317');
INSERT INTO `room` VALUES (5, 1, '319');
INSERT INTO `room` VALUES (6, 1, '312');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `buildid` int NULL DEFAULT NULL,
  `roomid` int NULL DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  PRIMARY KEY (`no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('202001030110', '超人强', '男', '202001030110', '123456', 1, 1, '12138');
INSERT INTO `student` VALUES ('202001030111', 'GG棒', '男', '202001030111', '123123', 1, 0, '12138');
INSERT INTO `student` VALUES ('202001030128', '王五', '男', '202001030128', '456', 0, 0, '135486');
INSERT INTO `student` VALUES ('202001030129', '朱俊威', '男', '202001030129', '456', 0, 0, '199740719008');
INSERT INTO `student` VALUES ('202001030130', '李四', '男', '202001030130', '456', 1, 1, '135486');
INSERT INTO `student` VALUES ('202001030134', '李四', '男', '202001030134', '456', 1, 1, '135486');
INSERT INTO `student` VALUES ('202001030135', '痞老板', '男', '202001030135', '123456', 1, 0, '19974071907');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `buildid` int NULL DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '黄老板', '男', '123', '123', 1, '199888777');
INSERT INTO `teacher` VALUES ('2', '王宁', '男', 'chris', '123123', 0, '19974071907');
INSERT INTO `teacher` VALUES ('4', '帅哥', '女', 'gdg', '123', 4, '1999979889');
INSERT INTO `teacher` VALUES ('5', 'Chris', '男', '5', '123456', 3, '19974071907');

-- ----------------------------
-- View structure for build_mes
-- ----------------------------
DROP VIEW IF EXISTS `build_mes`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `build_mes` AS select `build`.`buildid` AS `buildid`,`build`.`buildname` AS `buildname`,`teacher`.`name` AS `name`,`build`.`details` AS `details` from (`teacher` join `build`) where ((`teacher`.`no` = `build`.`no`) or ((`build`.`no` is null) and (`teacher`.`buildid` is null)));

-- ----------------------------
-- View structure for build_without_teacher
-- ----------------------------
DROP VIEW IF EXISTS `build_without_teacher`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `build_without_teacher` AS select `build`.`buildid` AS `buildid`,`build`.`buildname` AS `buildname` from `build` where (`build`.`teacherno` = 0);

-- ----------------------------
-- View structure for mission_list
-- ----------------------------
DROP VIEW IF EXISTS `mission_list`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `mission_list` AS select `mission_manager`.`missionid` AS `missionid`,`mission_manager`.`title` AS `title`,`mission_manager`.`details` AS `details`,`mission_manager`.`begintime` AS `begintime`,`mission_manager`.`endtime` AS `endtime`,`mission_manager`.`publisherid` AS `publisherid`,`mission_manager`.`executor` AS `executor`,`mission_manager`.`type` AS `type`,`mission_manager`.`filepath` AS `filepath`,`mission_manager`.`name` AS `name` from `mission_manager` union all select `mission_teacher`.`missionid` AS `missionid`,`mission_teacher`.`title` AS `title`,`mission_teacher`.`details` AS `details`,`mission_teacher`.`begintime` AS `begintime`,`mission_teacher`.`endtime` AS `endtime`,`mission_teacher`.`publisherid` AS `publisherid`,`mission_teacher`.`executor` AS `executor`,`mission_teacher`.`type` AS `type`,`mission_teacher`.`filepath` AS `filepath`,`mission_teacher`.`name` AS `name` from `mission_teacher`;

-- ----------------------------
-- View structure for mission_manager
-- ----------------------------
DROP VIEW IF EXISTS `mission_manager`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `mission_manager` AS select `mission`.`missionid` AS `missionid`,`mission`.`title` AS `title`,`mission`.`details` AS `details`,`mission`.`begintime` AS `begintime`,`mission`.`endtime` AS `endtime`,`mission`.`publisherid` AS `publisherid`,`mission`.`executor` AS `executor`,`mission`.`type` AS `type`,`mission`.`filepath` AS `filepath`,`manager`.`name` AS `name` from (`mission` join `manager`) where (`mission`.`publisherid` = `manager`.`no`);

-- ----------------------------
-- View structure for mission_teacher
-- ----------------------------
DROP VIEW IF EXISTS `mission_teacher`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `mission_teacher` AS select `mission`.`missionid` AS `missionid`,`mission`.`title` AS `title`,`mission`.`details` AS `details`,`mission`.`begintime` AS `begintime`,`mission`.`endtime` AS `endtime`,`mission`.`publisherid` AS `publisherid`,`mission`.`executor` AS `executor`,`mission`.`type` AS `type`,`mission`.`filepath` AS `filepath`,`teacher`.`name` AS `name` from (`mission` join `teacher`) where (`mission`.`publisherid` = `teacher`.`no`);

-- ----------------------------
-- View structure for record_mes
-- ----------------------------
DROP VIEW IF EXISTS `record_mes`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `record_mes` AS select `record`.`recordid` AS `recordid`,`record`.`no` AS `no`,`record`.`name` AS `name`,`build`.`buildname` AS `buildname`,`room`.`roomname` AS `roomname`,`record`.`date` AS `date`,`record`.`status` AS `status`,`record`.`details` AS `details` from ((`record` join `room`) join `build`) where ((`record`.`buildid` = `build`.`buildid`) and (`record`.`roomid` = `room`.`roomid`));

-- ----------------------------
-- View structure for room_mes
-- ----------------------------
DROP VIEW IF EXISTS `room_mes`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `room_mes` AS select `room`.`roomid` AS `roomid`,`build`.`buildname` AS `buildname`,`room`.`roomname` AS `roomname` from (`room` join `build`) where (`room`.`buildid` = `build`.`buildid`);

-- ----------------------------
-- View structure for student_mes
-- ----------------------------
DROP VIEW IF EXISTS `student_mes`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `student_mes` AS select `student`.`no` AS `no`,`student`.`name` AS `name`,`student`.`sex` AS `sex`,`build`.`buildname` AS `buildname`,`room`.`roomname` AS `roomname`,`student`.`tel` AS `tel` from ((`student` join `build`) join `room`) where ((`student`.`buildid` = `build`.`buildid`) and (`student`.`roomid` = `room`.`roomid`));

-- ----------------------------
-- View structure for teacher_mes
-- ----------------------------
DROP VIEW IF EXISTS `teacher_mes`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `teacher_mes` AS select `teacher`.`no` AS `no`,`teacher`.`name` AS `name`,`teacher`.`sex` AS `sex`,`build`.`buildname` AS `buildname`,`teacher`.`tel` AS `tel` from (`teacher` join `build`) where (`build`.`buildid` = `teacher`.`buildid`);

-- ----------------------------
-- View structure for teacher_without_build
-- ----------------------------
DROP VIEW IF EXISTS `teacher_without_build`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `teacher_without_build` AS select `teacher`.`no` AS `no`,`teacher`.`name` AS `name` from `teacher` where ((`teacher`.`buildid` is null) or (`teacher`.`buildid` = 0));

SET FOREIGN_KEY_CHECKS = 1;
