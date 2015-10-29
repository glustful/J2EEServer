/*
Navicat MySQL Data Transfer

Source Server         : 211.149.227.111
Source Server Version : 50505
Source Host           : 211.149.227.111:3306
Source Database       : jujiabaodb

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2015-02-08 10:41:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_cart`
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_id` bigint(21) NOT NULL COMMENT '用户Id',
  `set_id` bigint(21) NOT NULL COMMENT '套餐id',
  `type` bigint(21) NOT NULL COMMENT '在这天的套餐类型id,具体数据来源于t_food_type表',
  `count` int(4) DEFAULT NULL,
  `cart_date` date NOT NULL COMMENT '购物车日期',
  PRIMARY KEY (`id`),
  KEY `cart_user_id` (`user_id`),
  KEY `cart_set_id` (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cart
-- ----------------------------

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `order_id` bigint(21) DEFAULT NULL COMMENT '被评论的订单',
  `set_id` bigint(21) DEFAULT NULL COMMENT '被评论套餐ID或者被回复的评论ID',
  `comment_type` char(2) DEFAULT NULL COMMENT '00用户评论  10系统回复',
  `to_title` varchar(100) DEFAULT NULL COMMENT '被评论的套餐标题',
  `user_id` bigint(21) DEFAULT NULL COMMENT '评论用户id',
  `nickname` varchar(32) DEFAULT NULL COMMENT '评论用户昵称',
  `group_no` bigint(21) DEFAULT NULL COMMENT '评论对话组id',
  `sub_count` int(11) DEFAULT NULL COMMENT '回复评论条数',
  `summary` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`),
  KEY `t_comment_order_id` (`order_id`),
  KEY `t_comment_set_id` (`set_id`),
  KEY `t_comment_user_id` (`user_id`),
  KEY `t_comment_group_no` (`group_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `t_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_id` bigint(21) NOT NULL COMMENT '上传人id',
  `file_path` varchar(320) NOT NULL COMMENT '下载路径',
  `extension` varchar(20) DEFAULT NULL COMMENT '扩展名',
  `size` decimal(21,0) NOT NULL COMMENT '大小',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `store_partition` int(6) DEFAULT NULL COMMENT '存放分区',
  `status` char(2) NOT NULL COMMENT '状态00保存前   10保存后   20已删除',
  `create_time` datetime NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`),
  KEY `fk_file_01` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_file
-- ----------------------------

-- ----------------------------
-- Table structure for `t_food_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_food_detail`;
CREATE TABLE `t_food_detail` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `code` varchar(10) DEFAULT NULL COMMENT '菜品编号',
  `name` varchar(32) NOT NULL COMMENT '单品名称',
  `logo` varchar(100) NOT NULL COMMENT '单品图片路径',
  `taste` varchar(100) DEFAULT NULL COMMENT '口味',
  `materials` varchar(100) DEFAULT NULL COMMENT '用料',
  `type` char(2) NOT NULL COMMENT '类型 10素菜 20荤菜',
  `status` char(2) NOT NULL COMMENT '使用状态: 00删除  10正常 20下架',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_food_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_food_set`
-- ----------------------------
DROP TABLE IF EXISTS `t_food_set`;
CREATE TABLE `t_food_set` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `sku_id` bigint(18) NOT NULL COMMENT '套餐号',
  `code` varchar(10) DEFAULT NULL COMMENT '菜品编号',
  `name` varchar(32) NOT NULL COMMENT '套餐名称',
  `logo` varchar(100) DEFAULT NULL COMMENT '菜品主图路径（用于菜品列表中）',
  `market_price` decimal(16,2) NOT NULL COMMENT '市场价',
  `price` decimal(16,2) NOT NULL COMMENT '促销价',
  `sale_count` int(6) NOT NULL COMMENT '销量',
  `comment_count` int(4) DEFAULT NULL COMMENT '评论量',
  `status` char(2) NOT NULL COMMENT '使用状态: 00删除  10正常 20下架',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `set_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_food_set
-- ----------------------------

-- ----------------------------
-- Table structure for `t_food_week`
-- ----------------------------
DROP TABLE IF EXISTS `t_food_week`;
CREATE TABLE `t_food_week` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `day` char(2) NOT NULL COMMENT '周几 10:周一 20：周二 30：周三 依次类推',
  `set_id` bigint(21) NOT NULL COMMENT '套餐id',
  `set_name` varchar(32) DEFAULT NULL COMMENT '套餐的具体名称',
  `type` bigint(20) NOT NULL COMMENT '在这天的餐品类型id', 
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `week_set_id` (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_food_week add column `day_count` int(6) DEFAULT NULL COMMENT '每日供应量';
alter table t_food_week add column `day_sale` int(6) DEFAULT NULL COMMENT '每日销量，每天自动复位为0';
alter table t_food_week add column `sort` int(4) DEFAULT NULL COMMENT '排序字段';

-- ----------------------------
-- Records of t_food_week
-- ----------------------------

-- ----------------------------
-- Table structure for `t_manage_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_manage_user`;
CREATE TABLE `t_manage_user` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `post` varchar(32) DEFAULT NULL COMMENT '职位',
  `job_number` varchar(32) DEFAULT NULL COMMENT '工号',
  `status` char(2) NOT NULL COMMENT '00正常  10删除',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manage_user
-- ----------------------------
INSERT INTO `t_manage_user` VALUES ('23869855501385728', '王雪彪', '3F8EC6D18DED283E460A2CA6A03CFA3E', '13888383695', '管理员', '002', '10', '2015-02-01 12:09:02');


-- ----------------------------
-- Table structure for `t_manage_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_manage_user_role`;
CREATE TABLE `t_manage_user_role` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_id` bigint(21) DEFAULT NULL COMMENT '用户id',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Table structure for `t_msg_verify`
-- ----------------------------
DROP TABLE IF EXISTS `t_msg_verify`;
CREATE TABLE `t_msg_verify` (
  `id` bigint(21) NOT NULL,
  `mobile` varchar(50) NOT NULL COMMENT '手机号码',
  `verify_num` varchar(50) NOT NULL COMMENT '短信验证码',
  `type` char(2) NOT NULL COMMENT '验证码类型(注册验证码：10， 找回密码：20)',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `t_msg_verify_user_name` (`mobile`),
  KEY `t_msg_verify_verify_num` (`verify_num`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_msg_verify
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_id` bigint(21) NOT NULL COMMENT '用户Id',
  `order_no` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `name` varchar(32) NOT NULL COMMENT '联系人姓名',
  `mobile` varchar(11) NOT NULL COMMENT '联系电话',
  `address_id` bigint(21) NOT NULL COMMENT '地址表id',
  `address` varchar(100) NOT NULL COMMENT '送货地址',
  `city_id` bigint(21) DEFAULT NULL COMMENT '城市ID',
  `county_id` bigint(21) NOT NULL COMMENT '区ID',
  `town_id` bigint(21) NOT NULL COMMENT '片区ID',
  `block_id` bigint(21) DEFAULT NULL COMMENT '小区或者大厦ID',
  `building_id` bigint(21) DEFAULT NULL COMMENT '大厦编号（如A座,B座）\n或者小区几栋',
  `cell_id` bigint(21) DEFAULT NULL COMMENT '小区单元（针对小区类型，\n大厦的话不填）',
  `location` varchar(50) DEFAULT NULL COMMENT '最小的那个region的层级位置',
  `floor` int(5) DEFAULT NULL COMMENT '楼层',
  `manual_address` varchar(30) DEFAULT NULL COMMENT '详细地址',
  `amount` decimal(16,2) NOT NULL COMMENT '订单金额',
  `status` char(2) NOT NULL COMMENT '订单状态 "00:删除 10：下单 20：已付款 30：已装车 40：签收, 60:取消, 70:退款"',
  `set_type` char(2) NOT NULL COMMENT '套餐类型（午餐还是晚餐）00：早餐，10:午餐 20：晚餐',
  `count` int(2) NOT NULL COMMENT '套餐份数',
  `trade_no` varchar(60) DEFAULT NULL COMMENT '支付宝交易流水号',
  `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
  `order_from` char(2) NOT NULL COMMENT '下单途径',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  `create_date` date NOT NULL COMMENT '下单日期',
  `comment_status` char(2) DEFAULT NULL COMMENT '评论状态:00待评论 10已评论',
  PRIMARY KEY (`id`),
  KEY `t_order_user_id` (`user_id`),
  KEY `t_order_order_no` (`order_no`),
  KEY `order_town_id` (`town_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

alter table t_order add column city_name varchar(15) DEFAULT NULL COMMENT '城市名称';
alter table t_order add column county_name varchar(15) DEFAULT NULL COMMENT '区县名称';
alter table t_order add column town_name varchar(26) DEFAULT NULL COMMENT '片区名称';
alter table t_order add column block_name varchar(15) DEFAULT NULL COMMENT '小区/大厦名称';
alter table t_order add column building_name varchar(15) DEFAULT NULL COMMENT '栋名或者座名';
alter table t_order add column cell_name varchar(15) DEFAULT NULL COMMENT '单元名';

-------------------------------
-- 为充值增加待付款金额字段
-------------------------------
alter table t_order add column to_pay decimal(16,2) NOT NULL COMMENT '付款金额字段';
-------------------------------
-- 结束增加待付款金额字段
-------------------------------


-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `order_id` bigint(21) NOT NULL COMMENT '订单id',
  `sku_id` bigint(18) NOT NULL COMMENT '套餐号',
  `set_id` bigint(21) NOT NULL COMMENT '套餐ID',
  `set_title` varchar(32) DEFAULT NULL COMMENT '套餐名字',
  `type` bigint(21) NOT NULL COMMENT '在这天的套餐类型id,具体数据来源于t_food_type表',
  `price` decimal(16,2) NOT NULL COMMENT '成交单价',
  `count` int(4) NOT NULL COMMENT '套餐份数',
  `amount` decimal(16,2) NOT NULL COMMENT '金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `logo` varchar(100) NOT NULL COMMENT '套餐图片路径',
  PRIMARY KEY (`id`),
  KEY `t_msg_verify_user_name` (`order_id`),
  KEY `t_msg_verify_verify_num` (`set_title`),
  KEY `verify_sku_id` (`sku_id`),
  KEY `verfiy_set_id` (`set_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order_extra`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_extra`;
CREATE TABLE `t_order_extra` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `order_id` bigint(21) NOT NULL COMMENT '订单id',
  `order_from` char(2) NOT NULL COMMENT '下单途径',
  `cordova` varchar(20) DEFAULT NULL COMMENT 'cordova版本',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台名称,如 ios、android',
  `uuid` varchar(64) DEFAULT NULL COMMENT 'uuid',
  `version` varchar(12) DEFAULT NULL COMMENT '操作系统版本',
  `user_agent` varchar(32) DEFAULT NULL COMMENT '浏览器的userAgent',
  `model` varchar(32) DEFAULT NULL COMMENT '手机型号',
  PRIMARY KEY (`id`),
  KEY `t_msg_verify_user_name` (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_extra
-- ----------------------------

-- ----------------------------
-- Table structure for `t_region`
-- ----------------------------
DROP TABLE IF EXISTS `t_region`;
CREATE TABLE `t_region` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `parent_id` bigint(21) DEFAULT NULL COMMENT '父级区域id',
  `name` varchar(32) DEFAULT NULL COMMENT '片区名称',
  `type` char(2) DEFAULT NULL COMMENT '类型（10：城市、20：曲县、30：片区， 40：大厦， 50小区、60座、70栋、80单元',
  `location` varchar(50) DEFAULT NULL COMMENT '层级位置',
  `status` char(2) NOT NULL COMMENT '使用状态 00删除  10正常',
  `has_child` char(1) NOT NULL COMMENT '是否有子节点 0无子节点  1有子节点',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `s_region_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_region
-- ----------------------------
INSERT INTO `t_region` VALUES ('23877403403092068', null, '昆明', '10', '000', '10', '2015-02-08 08:07:10');
INSERT INTO `t_region` VALUES ('23877403403092069', '23877403403092068', '官渡区', '20', '000000', '10', '2015-02-08 08:19:54');
INSERT INTO `t_region` VALUES ('23877403403092070', '23877403403092069', '螺蛳湾1期', '30', '000000000', '10', '2015-02-08 08:20:31');
INSERT INTO `t_region` VALUES ('23877403403092071', '23877403403092069', '螺蛳湾2期', '30', '000000001', '10', '2015-02-08 08:20:35');
INSERT INTO `t_region` VALUES ('23877403403092072', '23877403403092069', '新亚洲体育城', '30', '000000002', '10', '2015-02-08 08:20:53');
INSERT INTO `t_region` VALUES ('23877403403092073', '23877403403092069', '世纪城', '30', '000000003', '10', '2015-02-08 08:21:18');
INSERT INTO `t_region` VALUES ('23877403403092074', '23877403403092069', '关上', '30', '000000004', '10', '2015-02-08 08:21:34');
INSERT INTO `t_region` VALUES ('23877403403092075', '23877403403092071', '2323', '40', '000000001000', '10', '2015-02-08 10:35:34');

-- ----------------------------
-- Table structure for `t_region_time`
-- ----------------------------
DROP TABLE IF EXISTS `t_region_time`;
CREATE TABLE `t_region_time` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `region_id` bigint(21) NOT NULL COMMENT '城市Id',
  `region_name` varchar(32) DEFAULT NULL COMMENT '片区名称',
  `type` varchar(32) DEFAULT NULL COMMENT '时间类型，10午餐，20晚餐',
  `start_time` datetime DEFAULT NULL COMMENT '下单开始时间',
  `end_time` datetime(2) DEFAULT NULL COMMENT '下单结束时间',
  `delivery_time` VARCHAR(50) COMMENT '送达时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `time_region_id` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_region_time
-- ----------------------------

-- ----------------------------
-- Table structure for `t_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_id` bigint(21) DEFAULT NULL COMMENT '用户id',
  `resource_type` char(2) NOT NULL COMMENT '00图片  10视频  20附件',
  `file_id` bigint(21) DEFAULT NULL COMMENT '站内文件id',
  `file_path` varchar(320) DEFAULT NULL COMMENT '站内文件path',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `data_type` char(3) NOT NULL COMMENT '业务类型00套餐主图  10套餐轮番图 20单品主图 30广告图片',
  `data_id` bigint(21) NOT NULL COMMENT '业务id',
  `sort` int(12) DEFAULT NULL COMMENT '排序字段',
  `status` char(2) NOT NULL COMMENT '00 正常   10 删除',
  `create_time` datetime NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`),
  KEY `fk_resource_01` (`user_id`),
  KEY `fk_resource_02` (`file_id`),
  KEY `fk_resource_03` (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `t_sequence`;
CREATE TABLE `t_sequence` (
  `id` bigint(21) NOT NULL DEFAULT '0',
  `type` varchar(16) DEFAULT NULL,
  `current_value` bigint(20) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `increment` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for `t_session`
-- ----------------------------
DROP TABLE IF EXISTS `t_session`;
CREATE TABLE `t_session` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `session_id` char(36) NOT NULL COMMENT 'sessionId对应cookie值',
  `user_id` bigint(21) DEFAULT NULL COMMENT 'user表外键',
  `session_type` char(2) NOT NULL COMMENT '00临时session  10记住密码',
  `register_time` datetime NOT NULL COMMENT '注册session时间',
  `limit_time` datetime NOT NULL COMMENT 'session有效时间',
  `access_time` datetime NOT NULL COMMENT '最近一次访问时间',
  `status` char(2) NOT NULL COMMENT '00正常  10失效',
  PRIMARY KEY (`id`),
  KEY `idx_session_01` (`session_id`),
  KEY `session_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_session add column uri varchar(200) DEFAULT NULL COMMENT '产生session的uri';

-- ----------------------------
-- Records of t_session
-- ----------------------------

-- ----------------------------
-- Table structure for `t_session_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_session_data`;
CREATE TABLE `t_session_data` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `session_id` varchar(36) NOT NULL COMMENT 'sessionId对应cookie值',
  `session_key` varchar(36) NOT NULL COMMENT 'key值',
  `session_data` blob NOT NULL COMMENT 'session数据',
  PRIMARY KEY (`id`),
  KEY `idx_session_data_01` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_session_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_set_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_set_detail`;
CREATE TABLE `t_set_detail` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `set_id` bigint(21) NOT NULL COMMENT '套餐id',
  `detail_id` bigint(21) NOT NULL COMMENT '单品id',
  `title` varchar(50) DEFAULT NULL COMMENT '在套餐里的名称（默认单品名称）',
  `sort` int(2) NOT NULL COMMENT '排序（在套餐详情里展示的顺序）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `detail_detail_id` (`detail_id`),
  KEY `detail_set_id` (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_set_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_share_discussion`
-- ----------------------------
DROP TABLE IF EXISTS `t_share_discussion`;
CREATE TABLE `t_share_discussion` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `data_id` bigint(21) NOT NULL COMMENT '被评论的主体id,如分享id',
  `data_url` varchar(150) DEFAULT NULL COMMENT '被评论主体url,如分享针对的活动url，分享针对的社团url',
  `group_no` bigint(21) DEFAULT NULL COMMENT '评论对话组id，主评论的这个值和id值一样，回复评论的这个值，就是被回复的评论的disGroupId值',
  `content` varchar(512) NOT NULL COMMENT '评论内容',
  `user_id` bigint(21) NOT NULL COMMENT '评论人的code',
  `user_name` varchar(255) DEFAULT NULL COMMENT '评论人的名字',
  `to_title` varchar(240) DEFAULT NULL COMMENT '被评论的数据标题或者被回复的评论内容',
  `to_user_id` bigint(21) NOT NULL COMMENT '被回复评论的或者被评论的主体的用户code',
  `to_user_name` varchar(255) DEFAULT NULL COMMENT '被回复评论的或者被评论主体的用户名称',
  `add_date` datetime NOT NULL COMMENT '评论时间',
  `data_user_code` varchar(255) DEFAULT NULL COMMENT '数据持有人代码',
  `status` char(2) NOT NULL COMMENT '00正常  10删除',
  `data_type` char(1) NOT NULL COMMENT '分享针对的数据类型，1表示活动、2表示社团',
  `city` char(6) DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`id`),
  KEY `fk_party_share_discussion_01` (`data_id`),
  KEY `fk_party_share_discussion_02` (`group_no`),
  KEY `fk_party_share_discussion_03` (`user_id`),
  KEY `fk_party_share_discussion_04` (`to_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_share_discussion
-- ----------------------------

-- ----------------------------
-- Table structure for `t_sms`
-- ----------------------------
DROP TABLE IF EXISTS `t_sms`;
CREATE TABLE `t_sms` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `session_id` char(36) DEFAULT NULL COMMENT 'sessionId对应cookie值',
  `ip` varchar(20) DEFAULT NULL COMMENT '短信发送的ip地址',
  `user_id` bigint(21) DEFAULT NULL COMMENT '用户',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `message` varchar(100) NOT NULL COMMENT '内容',
  `type` char(2) DEFAULT NULL COMMENT '注册验证码：10， 找回密码：20',
  `create_time` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `sms_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sms
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_name` varchar(32) NOT NULL COMMENT '账号名称（手机号）',
  `mobile` varchar(11) NOT NULL COMMENT '手机',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `logo` varchar(100) DEFAULT NULL COMMENT '用户头像路径',
  `grade_credit` int(11) DEFAULT NULL,
  `credit` int(11) NOT NULL COMMENT '积分',
  `status` char(2) NOT NULL COMMENT '00删除  10正常',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE t_user ADD `ip` varchar(20) DEFAULT NULL COMMENT 'ip地址';
-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_address`;
CREATE TABLE `t_user_address` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `user_id` bigint(21) NOT NULL COMMENT '用户ID',
  `name` varchar(32) NOT NULL COMMENT '联系人',
  `mobile` varchar(11) NOT NULL COMMENT '联系电话',
  `city_id` bigint(21) NOT NULL COMMENT '城市Id',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',
  `county_id` bigint(21) NOT NULL COMMENT '区县Id',
  `county_name` varchar(32) DEFAULT NULL COMMENT '区县名称',
  `town_id` bigint(21) NOT NULL COMMENT '片区Id',
  `town_name` varchar(32) DEFAULT NULL COMMENT '片区名称',
  `block_id` bigint(21) NOT NULL COMMENT '小区或者大厦Id',
  `block_name` varchar(32) DEFAULT NULL COMMENT '小区或者大厦名称',
  `building_id` bigint(21) NOT NULL COMMENT '"大厦编号（如A座,B座）\n或者小区几栋"',
  `building_name` varchar(32) DEFAULT NULL COMMENT '"大厦编号（如A座,B座）\n或者小区几栋"',
  `cell_id` bigint(21) NOT NULL COMMENT '"小区单元（针对小区类型，\n大厦的话不填）对应的regionId"',
  `cell_name` varchar(32) DEFAULT NULL COMMENT '"小区单元（针对小区类型，\n大厦的话不填）"',
  `floor` int(5) DEFAULT NULL COMMENT '楼层',
  `manual_address` varchar(32) DEFAULT NULL COMMENT '详情（可选）',
  `status` char(2) NOT NULL COMMENT '00删除  10正常地址 20默认地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `address_user_id` (`user_id`),
  KEY `address_city_id` (`city_id`),
  KEY `address_county_id` (`county_id`),
  KEY `address_town_id` (`town_id`),
  KEY `address_block_id` (`block_id`),
  KEY `address_building_id` (`building_id`),
  KEY `address_cell_id` (`cell_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_address
-- ----------------------------


-- ----------------------------
-- Table structure for `t_user_test`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_test`;
CREATE TABLE `t_user_test` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_test
-- ----------------------------


DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '分组名称',
  `town_id` bigint(20) NOT NULL COMMENT '片区id',
  `sort` int DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`),
  KEY `group_town_id` (`town_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_group_region`;
CREATE TABLE `t_group_region` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `group_id` bigint(21) NOT NULL COMMENT '分组Id',
  `region_id` bigint(21) NOT NULL COMMENT '地址id（目前只是block或者building类型的）',
  `region_type` char(2) NOT NULL COMMENT '地址类型，10表示block，20表示building',
  PRIMARY KEY (`id`),
  KEY `group_region_group_id` (`group_id`),
  KEY `group_region_region_id` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_user_wallet`;
CREATE TABLE `t_user_wallet` (
   `id` bigint(21) NOT NULL COMMENT '主键',
   `user_id` bigint(21) NOT NULL COMMENT '用户id',
   `amount` decimal(16,2) NOT NULL COMMENT '金额',
   `balance` decimal(16,2) NOT NULL COMMENT '余额',
   `frozen_balance` decimal(16,2) NOT NULL COMMENT '冻结余额',
   `gift_balance` decimal(16,2) NOT NULL COMMENT '红包余额',
   `frozen_gift` decimal(16,2) NOT NULL COMMENT '冻结余额',
   `cash`   decimal(16,2) NOT NULL COMMENT '提现金额',
   `pay_pass` varchar(32) NOT NULL COMMENT '支付密码',
   `trade_time` datetime NOT NULL COMMENT '交易时间',
   `error_cnt` int DEFAULT NULL COMMENT '密码错误次数',
   `error_time` datetime DEFAULT NULL COMMENT '上次错误时间',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
   `id` bigint(21) NOT NULL COMMENT '主键',
   `balance` decimal(16,2) NOT NULL COMMENT '余额',
   `gift_balance` decimal(16,2) NOT NULL COMMENT '红包余额',
   `trade_time` datetime NOT NULL COMMENT '交易时间',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_account_detail`;
CREATE TABLE `t_account_detail`(
   `id` bigint(21) NOT NULL COMMENT '主键',
   `head` char(3) NOT NULL COMMENT '科目号  000总账户    100用户账户',
   `account_id` bigint(21) NOT NULL COMMENT '账户id',
   `user_id` bigint(21) NOT NULL COMMENT '用户id',
   `amount` decimal(16,2) NOT NULL COMMENT '交易金额',
   `balance_type` char(2) NOT NULL COMMENT '00 正常余额  10 红包',
   `credit_flag` char(1) NOT NULL COMMENT '0支出  1收入',
   `valid_type` char(1) NOT NULL COMMENT '0表外  1表内 ',
   `trade_type` char(3) NOT NULL COMMENT '000 充值  100 消费 200 支付宝支付  300 后台调整',
   `trade_time` datetime NOT NULL COMMENT '交易时间',
   `data_id` bigint(21) DEFAULT NULL COMMENT '交易id',
   `data_no` varchar(30) DEFAULT NULL COMMENT '交易no',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_recharge_set`;
CREATE TABLE `t_recharge_set`(
   `id` bigint(21) NOT NULL COMMENT '主键',
   `name` varchar(32) NOT NULL COMMENT '充值套餐名',
   `amount` decimal(16,2) NOT NULL COMMENT '充值金额',
   `gift` decimal(16,2) NOT NULL COMMENT '红包金额',
   `status` char(2) NOT NULL COMMENT '00 无效  10生效',
   `sort` int DEFAULT NULL COMMENT '排序字段',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_recharge_record`;
CREATE TABLE `t_recharge_record`(
   `id` bigint(21) NOT NULL COMMENT '主键',
   `recharge_id` bigint(21) NOT NULL COMMENT '充值套餐名',
   `user_id` bigint(21) NOT NULL COMMENT '用户id',
   `amount` decimal(16,2) NOT NULL COMMENT '充值金额',
   `gift` decimal(16,2) NOT NULL COMMENT '红包金额',
   `status` char(2) NOT NULL COMMENT '00 待支付  10完成支付',
   `trade_time` datetime NOT NULL COMMENT '交易时间',
   `trade_no` varchar(60) DEFAULT NULL COMMENT '支付宝交易流水号',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `config_key` varchar(50) NOT NULL COMMENT '配置项',
  `config_value` varchar(250) NOT NULL COMMENT '配置内容',
  `type` char(2) NOT NULL COMMENT '配置类型 10：系统性配置, 20:套餐类型配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `t_food_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_food_type`;
CREATE TABLE `t_food_type` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `name` varchar(250) NOT NULL COMMENT '分类名称',
  `sort` int(4) DEFAULT NULL COMMENT '排序字段',
  `meal` char(2) NOT NULL COMMENT '餐品的类型：00早餐、10午餐、20晚餐，数据来源于表t_meal配置',
  `type_id` bigint(21) NOT NULL COMMENT '所属商品分类id',
  `type_name` varchar(32) NOT NULL COMMENT '所属商品分类名称',
  `status` char(2) NOT NULL COMMENT '状态,00:删除，10正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_meal`
-- ----------------------------
DROP TABLE IF EXISTS `数据来源于表t_meal配置`;
CREATE TABLE `t_meal` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `name` varchar(250) NOT NULL COMMENT '名称',
  `code` char(2) NOT NULL COMMENT 'code',
  `sort` int(4) DEFAULT NULL COMMENT '排序字段',
  `status` char(2) NOT NULL COMMENT '状态,00:删除，10正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
--  Table structure for `t_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_type`;
CREATE TABLE `t_goods_type` (
  `id` bigint(21) NOT NULL COMMENT '主键',
  `name` varchar(250) NOT NULL COMMENT '分类名称',
  `sort` int(4) DEFAULT NULL COMMENT '排序字段',
  `status` char(2) NOT NULL COMMENT '状态,00:删除，10正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : jujiabao

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2015-03-17 16:56:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_advertisement`
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement`;
CREATE TABLE `t_advertisement` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '广告名称',
  `img_web` varchar(64) NOT NULL COMMENT '广告图片',
  `img_phone` varchar(64) NOT NULL COMMENT '广告图片',
  `content` text NOT NULL COMMENT '广告内容',
  `actions` varchar(128) DEFAULT NULL COMMENT '广告操作按钮',
  `type` char(2) NOT NULL COMMENT '00 轮播广告  10  通知广告',
  `background` varchar(60) DEFAULT NULL COMMENT '针对轮播广告配置的背景色',
  `support_version` int DEFAULT NULL COMMENT '支持的最低版本',
  `unsupport_msg` varchar(60) DEFAULT NULL COMMENT '版本不支持时的提示',
  `status` char(2) NOT NULL COMMENT '00 启用  10 停用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_advertisement
-- ----------------------------

-- ----------------------------
-- Table structure for `t_advertisement_show`
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement_show`;
CREATE TABLE `t_advertisement_show` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `advertisement_id` bigint(20) NOT NULL COMMENT '广告id',
  `region_id` bigint(20) NOT NULL COMMENT '区域id, 0表示所有片区',
  `region_name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_advertisement_show
-- ----------------------------

-- ----------------------------
-- Table structure for `t_discount`
-- ----------------------------
DROP TABLE IF EXISTS `t_discount`;
CREATE TABLE `t_discount` (
  `id` bigint(20) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` char(2) NOT NULL COMMENT '00 立减优惠   10 折扣优惠',
  `discount_amount` decimal(16,2) DEFAULT NULL COMMENT '减多少元',
  `discount_rate` int(2) DEFAULT NULL COMMENT '折扣比如97折，填写97',
  `grade_min` int(11) DEFAULT NULL,
  `grade_max` int(11) DEFAULT NULL,
  `region` bigint(20) DEFAULT NULL,
  `consume_min` decimal(16,2) DEFAULT NULL,
  `consume_max` decimal(16,2) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `limit` int(11) DEFAULT NULL,
  `join` int(11) DEFAULT NULL,
  `use_mix` char(1) DEFAULT NULL COMMENT '是否可以和其他优惠混用  0 否  1是',
  `user_limit` int(11) DEFAULT NULL,
  `status` char(255) DEFAULT NULL COMMENT '00 停用  10 启用',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE t_discount ADD `region_name` varchar(32) DEFAULT NULL COMMENT '活动地区名称';
ALTER TABLE t_discount ADD `for_new` char(2) DEFAULT NULL COMMENT '00 所有用户 10 针对新注册用户';
-- ----------------------------
-- Records of t_discount
-- ----------------------------

-- ----------------------------
-- Table structure for `t_discount_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_discount_record`;
CREATE TABLE `t_discount_record` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `user_id` bigint(20) DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL,
  `discount_amount` decimal(16,2) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_discount_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_seckill`
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill`;
CREATE TABLE `t_seckill` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '商品名称',
  `memo` varchar(128) DEFAULT NULL COMMENT '说明信息',
  `market_price` decimal(16,2) NOT NULL COMMENT '市场价',
  `price` decimal(16,2) NOT NULL COMMENT '价格',
  `show_time` datetime NOT NULL COMMENT '展现时间',
  `start_time` datetime NOT NULL COMMENT '起始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `hidden_time` datetime NOT NULL COMMENT '结束展现时间',
  `inventory_web` int(11) NOT NULL COMMENT 'web库存量',
  `inventory_phone` int(11) NOT NULL COMMENT '移动端库存量',
  `inventory_share` int(11) NOT NULL COMMENT '共享库存量',
  `type` bigint(21) NOT NULL COMMENT '餐品类型id',
  `logo` varchar(64) NOT NULL COMMENT '商品图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `question_web` varchar(64) DEFAULT NULL,
  `answer_web` varchar(32) DEFAULT NULL,
  `question_phone` varchar(64) DEFAULT NULL,
  `answer_phone` varchar(32) DEFAULT NULL,
  `region_id` bigint(20) NOT NULL COMMENT '区域id',
  `region_name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `md` varchar(42) NOT NULL COMMENT '校验码',
  `status` char(2) NOT NULL COMMENT '00 未启用  10 启用 20删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_seckill
-- ----------------------------

-- ----------------------------
-- Table structure for `t_seckill_instance`
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_instance`;
CREATE TABLE `t_seckill_instance` (
  `id` bigint(20) NOT NULL,
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀id',
  `inventory_type` char(2) NOT NULL COMMENT '00 web库存  10 phone库存  20共享库存',
  `status` char(2) NOT NULL COMMENT '00 初始状态 10 秒杀完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_seckill_instance
-- ----------------------------

-- ----------------------------
-- Table structure for `t_seckill_result`
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_result`;
CREATE TABLE `t_seckill_result` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `sec_id` bigint(20) NOT NULL,
  `sec_instance_id` bigint(20) NOT NULL,
  `sec_time` datetime NOT NULL,
  `sec_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_seckill_result
-- ----------------------------


-- ----------------------------
-- Table structure for `t_pay_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_log`;
CREATE TABLE `t_pay_log` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pay_no` varchar(30) NOT NULL COMMENT '订单no或者充值记录的id',
  `title` varchar(32) NOT NULL COMMENT '日志标题',
  `content` varchar(128) DEFAULT NULL COMMENT '说明信息',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '涉及金额',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `t_region_status` 配置地区的是否营业状态
-- ----------------------------
DROP TABLE IF EXISTS `t_region_status`;
CREATE TABLE `t_region_status` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `region_id` bigint(20) NOT NULL COMMENT '区域id, 0表示所有片区',
  `region_name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `comment` varchar(50) DEFAULT NULL COMMENT '信息说明，在非营业状态下的提示信息',
  `status` char(2) NOT NULL COMMENT '00 暂停营业 10 正常营业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
