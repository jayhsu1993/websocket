/*
Navicat MySQL Data Transfer

Source Server Version : 50630
Source Database       : haidong

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2017-01-13 22:39:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for SYS_ANDORRA
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ANDORRA`;
CREATE TABLE `SYS_ANDORRA` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '广告ID',
  `title` varchar(200) DEFAULT NULL COMMENT '广告标题',
  `content` varchar(200) DEFAULT NULL COMMENT '广告内容',
  `type` char(2) DEFAULT NULL COMMENT '广告类型  0为默认文字广告，1为图片广告',
  `adurl` varchar(200) DEFAULT NULL COMMENT '广告图片地址',
  `publisher` varchar(50) DEFAULT NULL COMMENT '广告发布人',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `starttime` varchar(50) DEFAULT NULL COMMENT '开始时间',
  `endtime` varchar(50) DEFAULT NULL COMMENT '结束时间',
  `status` char(2) DEFAULT '0' COMMENT '状态  0为默认未启用，1为启用',
  `tourl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_APP_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYS_APP_USER`;
CREATE TABLE `SYS_APP_USER` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `PHONE` varchar(100) DEFAULT NULL,
  `SFID` varchar(100) DEFAULT NULL,
  `START_TIME` varchar(100) DEFAULT NULL,
  `END_TIME` varchar(100) DEFAULT NULL,
  `YEARS` int(10) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `CID` varchar(64) DEFAULT NULL COMMENT '客户端推送标识',
  `SYS_TYPE` varchar(32) DEFAULT NULL COMMENT '系统类型（Andriod and IOS）',
  `SYS_ORIGIN` int(11) DEFAULT NULL COMMENT '来源（1.app微信;2.H5微信;3.手机）',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_DICTIONARIES
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICTIONARIES`;
CREATE TABLE `SYS_DICTIONARIES` (
  `ZD_ID` varchar(100) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `BIANMA` varchar(100) DEFAULT NULL,
  `ORDY_BY` int(10) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `JB` int(10) DEFAULT NULL,
  `P_BM` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ZD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_FEATURED
-- ----------------------------
DROP TABLE IF EXISTS `SYS_FEATURED`;
CREATE TABLE `SYS_FEATURED` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '特别推荐ID',
  `title` varchar(200) DEFAULT NULL COMMENT '特别推荐标题',
  `content` varchar(300) DEFAULT NULL COMMENT '特别推荐内容',
  `url` varchar(300) DEFAULT NULL COMMENT '特别推荐连接地址',
  `heat` char(2) DEFAULT '0' COMMENT '热度  按1 2 3 4 5级',
  `stars` char(2) DEFAULT '0' COMMENT '星级  按1 2 3 4 5级',
  `sequence` int(10) DEFAULT '0' COMMENT '排序',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `status` char(2) DEFAULT '0' COMMENT '状态 0为未启用，1为启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_GL_QX
-- ----------------------------
DROP TABLE IF EXISTS `SYS_GL_QX`;
CREATE TABLE `SYS_GL_QX` (
  `GL_ID` varchar(100) NOT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `FX_QX` int(10) DEFAULT NULL,
  `FW_QX` int(10) DEFAULT NULL,
  `QX1` int(10) DEFAULT NULL,
  `QX2` int(10) DEFAULT NULL,
  `QX3` int(10) DEFAULT NULL,
  `QX4` int(10) DEFAULT NULL,
  PRIMARY KEY (`GL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_LINK
-- ----------------------------
DROP TABLE IF EXISTS `SYS_LINK`;
CREATE TABLE `SYS_LINK` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '友情链接ID',
  `stiename` varchar(100) DEFAULT NULL COMMENT '友情链接网站名称',
  `sitecontent` varchar(200) DEFAULT NULL COMMENT '友情链接网站内容',
  `type` char(2) DEFAULT '0' COMMENT '类型  0为文字连接，1为图片连接',
  `stieurl` varchar(200) DEFAULT NULL COMMENT '图片连接地址',
  `addtime` varchar(50) DEFAULT NULL COMMENT '添加时间',
  `uptime` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `status` char(2) DEFAULT '0' COMMENT '状态  0为未启用，1为启用',
  `sequence` int(10) DEFAULT NULL COMMENT '排序',
  `tourl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYS_MENU`;
CREATE TABLE `SYS_MENU` (
  `MENU_ID` int(11) NOT NULL,
  `MENU_NAME` varchar(255) DEFAULT NULL,
  `MENU_URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `MENU_ORDER` varchar(100) DEFAULT NULL,
  `MENU_ICON` varchar(30) DEFAULT NULL,
  `MENU_TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_NEWS
-- ----------------------------
DROP TABLE IF EXISTS `SYS_NEWS`;
CREATE TABLE `SYS_NEWS` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '新闻ID',
  `title` varchar(150) DEFAULT NULL COMMENT '新闻标题',
  `content` text COMMENT '新闻内容',
  `publisher` varchar(50) DEFAULT NULL COMMENT '发布人',
  `addtime` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `uptime` varchar(50) DEFAULT NULL COMMENT '修改时间',
  `pip` varchar(50) DEFAULT NULL COMMENT '发布IP',
  `hits` int(11) DEFAULT '0' COMMENT '点击数',
  `sequence` int(10) DEFAULT '0' COMMENT '排序',
  `recommand` char(2) DEFAULT '0' COMMENT '推荐  0默认未推荐，1推荐',
  `status` char(2) DEFAULT '0' COMMENT '状态  0默认未发布，1发布',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_NOTICE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_NOTICE`;
CREATE TABLE `SYS_NOTICE` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(200) NOT NULL COMMENT '公告标题',
  `content` varchar(500) NOT NULL COMMENT '公告内容',
  `publisher` varchar(50) NOT NULL COMMENT '公告发布人',
  `addtime` datetime NOT NULL COMMENT '公告发布时间',
  `uptime` datetime NOT NULL COMMENT '修改时间',
  `status` char(2) NOT NULL DEFAULT '0' COMMENT '状态  0默认未启用，1启用',
  `sequence` int(10) DEFAULT '0' COMMENT '公告排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_PLAY
-- ----------------------------
DROP TABLE IF EXISTS `SYS_PLAY`;
CREATE TABLE `SYS_PLAY` (
  `PLAY_ID` varchar(255) NOT NULL,
  `PLAYNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) NOT NULL,
  PRIMARY KEY (`PLAY_ID`,`PASSWORD`,`LAST_LOGIN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
  `ROLE_ID` varchar(100) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `ADD_QX` varchar(255) DEFAULT NULL,
  `DEL_QX` varchar(255) DEFAULT NULL,
  `EDIT_QX` varchar(255) DEFAULT NULL,
  `CHA_QX` varchar(255) DEFAULT NULL,
  `QX_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_TEST
-- ----------------------------
DROP TABLE IF EXISTS `SYS_TEST`;
CREATE TABLE `SYS_TEST` (
  `TID` varchar(255) NOT NULL,
  `TNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `SKIN` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  `PHONE` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SYS_USER_QX
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_QX`;
CREATE TABLE `SYS_USER_QX` (
  `U_ID` varchar(100) NOT NULL,
  `C1` int(10) DEFAULT NULL,
  `C2` int(10) DEFAULT NULL,
  `C3` int(10) DEFAULT NULL,
  `C4` int(10) DEFAULT NULL,
  `Q1` int(10) DEFAULT NULL,
  `Q2` int(10) DEFAULT NULL,
  `Q3` int(10) DEFAULT NULL,
  `Q4` int(10) DEFAULT NULL,
  PRIMARY KEY (`U_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account` (
  `id` varchar(100) NOT NULL COMMENT '订单ID',
  `userId` varchar(100) NOT NULL COMMENT '用户ID',
  `totalfee` double(32,2) DEFAULT NULL COMMENT '订单总额 ',
  `outtradeno` varchar(255) DEFAULT NULL COMMENT '商户订单号',
  `timestart` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单开始时间',
  `timeend` timestamp NULL DEFAULT NULL COMMENT '订单结束时间',
  `state` tinyint(2) DEFAULT '0' COMMENT '状态，0待处理，1已处理,2已关闭',
  `type` tinyint(2) DEFAULT '0' COMMENT '1 充值 2 提现 3 商城支出',
  `cashNumber` varchar(32) DEFAULT NULL COMMENT '提现账号',
  `shopOrderId` varchar(255) DEFAULT NULL COMMENT '商城订单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_alicode
-- ----------------------------
DROP TABLE IF EXISTS `tb_alicode`;
CREATE TABLE `tb_alicode` (
  `id` varchar(100) NOT NULL,
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `code` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL COMMENT '动码流水',
  `status` int(255) DEFAULT NULL COMMENT '0:已发送,1:已验证',
  `type` int(255) DEFAULT NULL COMMENT ' 1:注册, 2修改密码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_allwater
-- ----------------------------
DROP TABLE IF EXISTS `tb_allwater`;
CREATE TABLE `tb_allwater` (
  `id` varchar(100) NOT NULL COMMENT '主键ID',
  `USER_ID` varchar(255) DEFAULT NULL COMMENT '用户id 用户表获得',
  `waterid` varchar(255) DEFAULT NULL COMMENT '流水ID 自动生成',
  `status` varchar(255) DEFAULT NULL COMMENT '状态  0是发出礼物 1是收入礼物,2是充值，3是提现',
  `liveid` varchar(255) DEFAULT NULL COMMENT '直播间id 对应直播表的id',
  `gold` varchar(32) DEFAULT NULL COMMENT '涉及金额',
  `giftid` varchar(32) DEFAULT NULL COMMENT '礼物ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `deal_time` varchar(255) DEFAULT NULL COMMENT '交易时间',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_apply
-- ----------------------------
DROP TABLE IF EXISTS `tb_apply`;
CREATE TABLE `tb_apply` (
  `id` varchar(100) NOT NULL,
  `picture` varchar(255) DEFAULT NULL COMMENT '封面',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机',
  `place` varchar(255) DEFAULT NULL COMMENT '地点',
  `start_time` varchar(255) DEFAULT NULL COMMENT '结婚时间',
  `status` varchar(255) DEFAULT '0' COMMENT '状态(0代表未审核，1代表审核通过)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL COMMENT '用户app_id',
  `share_pic` varchar(255) DEFAULT NULL COMMENT '微信分享图片路径',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_baitiao
-- ----------------------------
DROP TABLE IF EXISTS `tb_baitiao`;
CREATE TABLE `tb_baitiao` (
  `id` varchar(100) NOT NULL,
  `userid` varchar(255) DEFAULT NULL COMMENT '申请人id',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证',
  `realname` varchar(32) DEFAULT NULL COMMENT '真实名字',
  `gjjin` varchar(32) DEFAULT NULL COMMENT '公积金账户',
  `telnumber` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `idcardimg1` varchar(255) DEFAULT NULL COMMENT '身份证正面',
  `idcardimg2` varchar(255) DEFAULT NULL COMMENT '身份证反面',
  `edu` varchar(100) DEFAULT NULL COMMENT '额度',
  `yiedu` varchar(100) DEFAULT NULL COMMENT '已用额度',
  `state` int(2) DEFAULT '0' COMMENT '0正在处理，1成功，2失败',
  `shuserid` varchar(100) DEFAULT '' COMMENT '审核人id',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `info` varchar(255) DEFAULT NULL COMMENT '反馈结果',
  `shTime` datetime DEFAULT NULL COMMENT '审核日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_ball
-- ----------------------------
DROP TABLE IF EXISTS `tb_ball`;
CREATE TABLE `tb_ball` (
  `id` varchar(100) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT 'title',
  `createtime` varchar(255) DEFAULT NULL COMMENT 'createTime',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_ball2
-- ----------------------------
DROP TABLE IF EXISTS `tb_ball2`;
CREATE TABLE `tb_ball2` (
  `BALL2_ID` varchar(100) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL COMMENT '姓名',
  `AGE` int(11) NOT NULL COMMENT '年龄',
  PRIMARY KEY (`BALL2_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_conference
-- ----------------------------
DROP TABLE IF EXISTS `tb_conference`;
CREATE TABLE `tb_conference` (
  `id` varchar(100) NOT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT '参加人数',
  `phoneNumber` varchar(15) NOT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary`;
CREATE TABLE `tb_dictionary` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL COMMENT '字典表名称',
  `info` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `value` varchar(255) DEFAULT NULL COMMENT '字典属性值',
  `state` int(2) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for tb_game_gift
-- ----------------------------
DROP TABLE IF EXISTS `tb_game_gift`;
CREATE TABLE `tb_game_gift` (
  `id` varchar(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '婚礼申请人名称',
  `liveId` varchar(255) DEFAULT NULL COMMENT '所属房间号',
  `firstGame` varchar(255) DEFAULT NULL COMMENT '1号游戏名称',
  `secondGame` varchar(255) DEFAULT NULL COMMENT '2号游戏名称',
  `thirdGame` varchar(255) DEFAULT NULL COMMENT '3号游戏名称',
  `firstGameId` varchar(64) DEFAULT NULL COMMENT '第一个游戏礼物详情id',
  `secondGameId` varchar(64) DEFAULT NULL COMMENT '第二个游戏礼物详情id',
  `thirdGameId` varchar(64) DEFAULT NULL COMMENT '第三个游戏礼物详情id',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_game_gift_details
-- ----------------------------
DROP TABLE IF EXISTS `tb_game_gift_details`;
CREATE TABLE `tb_game_gift_details` (
  `id` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '主键',
  `gameCode` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '游戏代号',
  `gameName` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '游戏名称',
  `liveId` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '直播Id',
  `first_prize_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '一等奖名称',
  `first_prize_num` varchar(16) CHARACTER SET utf8 DEFAULT NULL COMMENT '一等奖奖项个数',
  `first_prize_pic` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '一等奖图片地址',
  `second_prize_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '二等奖名称',
  `second_prize_num` varchar(16) CHARACTER SET utf8 DEFAULT NULL COMMENT '二等奖奖项个数',
  `second_prize_pic` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '二等奖图片地址',
  `third_prize_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '三等奖名称',
  `third_prize_num` varchar(16) CHARACTER SET utf8 DEFAULT NULL COMMENT '三等奖奖项个数',
  `third_prize_pic` varchar(255) DEFAULT NULL COMMENT '三等奖图片地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_gift
-- ----------------------------
DROP TABLE IF EXISTS `tb_gift`;
CREATE TABLE `tb_gift` (
  `id` varchar(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT 'NAME',
  `status` varchar(255) DEFAULT NULL COMMENT 'STATUS',
  `logo` varchar(255) DEFAULT NULL COMMENT 'LOGO',
  `price` varchar(255) DEFAULT NULL COMMENT 'PRICE',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  `giftid` varchar(255) DEFAULT NULL COMMENT '礼物id 对应GiftConst',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id` varchar(100) NOT NULL,
  `groupname` varchar(255) DEFAULT NULL COMMENT 'groupName',
  `USER_ID` varchar(255) DEFAULT NULL COMMENT 'userid',
  `num` varchar(255) DEFAULT NULL COMMENT '当前群组成员数量',
  `headUrl` varchar(255) DEFAULT NULL COMMENT '群组头像地址',
  `weddingTime` varchar(255) DEFAULT NULL COMMENT '婚礼时间',
  `weddingPlace` varchar(255) DEFAULT NULL COMMENT '婚礼地点',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_grouptouser
-- ----------------------------
DROP TABLE IF EXISTS `tb_grouptouser`;
CREATE TABLE `tb_grouptouser` (
  `id` varchar(100) NOT NULL,
  `groupid` varchar(255) DEFAULT NULL COMMENT 'groupid',
  `USER_ID` varchar(255) DEFAULT NULL COMMENT 'userid',
  `nickname` varchar(255) DEFAULT NULL COMMENT 'nickname',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `useridentity` varchar(255) DEFAULT '0' COMMENT 'userIdentity(0:代表群成员 1:代表群主 )',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_hllc
-- ----------------------------
DROP TABLE IF EXISTS `tb_hllc`;
CREATE TABLE `tb_hllc` (
  `id` varchar(100) NOT NULL,
  `userid` varchar(100) DEFAULT NULL COMMENT '创建者id',
  `lcname` varchar(255) DEFAULT NULL,
  `state` int(4) DEFAULT '0' COMMENT '0为用户添加的数据，1为默认',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `orderNumber` int(8) DEFAULT '0' COMMENT '排序参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_hllc_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_hllc_info`;
CREATE TABLE `tb_hllc_info` (
  `id` varchar(100) NOT NULL,
  `hllcid` varchar(100) DEFAULT NULL COMMENT '婚礼流程父类id',
  `startTime` varchar(10) DEFAULT NULL COMMENT '时间',
  `info` varchar(255) DEFAULT NULL COMMENT '事宜',
  `person` varchar(255) DEFAULT NULL COMMENT '人员',
  `state` int(4) DEFAULT '0' COMMENT '0用户数据，1默认数据',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建者时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_hongbao
-- ----------------------------
DROP TABLE IF EXISTS `tb_hongbao`;
CREATE TABLE `tb_hongbao` (
  `id` varchar(100) NOT NULL,
  `liveId` varchar(255) DEFAULT NULL COMMENT '房间id',
  `total` varchar(255) DEFAULT NULL COMMENT '金额',
  `amount` int(11) NOT NULL COMMENT '分数',
  `totaled` varchar(255) DEFAULT NULL COMMENT '已抢金额',
  `amounted` int(11) NOT NULL COMMENT '已抢金额',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_hongbaotouser
-- ----------------------------
DROP TABLE IF EXISTS `tb_hongbaotouser`;
CREATE TABLE `tb_hongbaotouser` (
  `id` varchar(100) NOT NULL,
  `hbid` varchar(255) DEFAULT NULL COMMENT '红包id',
  `money` varchar(255) DEFAULT NULL COMMENT '已抢金额',
  `luckly` int(11) NOT NULL COMMENT '是否手气最佳',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_iap
-- ----------------------------
DROP TABLE IF EXISTS `tb_iap`;
CREATE TABLE `tb_iap` (
  `id` varchar(100) NOT NULL,
  `desc` varchar(255) NOT NULL COMMENT '描述',
  `title` varchar(255) NOT NULL COMMENT '显示标题',
  `gold` varchar(255) NOT NULL DEFAULT '' COMMENT '嗨豆',
  `productid` varchar(255) NOT NULL COMMENT '定义内购商品的产品id',
  `state` int(2) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_invitations
-- ----------------------------
DROP TABLE IF EXISTS `tb_invitations`;
CREATE TABLE `tb_invitations` (
  `id` varchar(100) NOT NULL,
  `start_time` varchar(255) DEFAULT NULL COMMENT '结婚时间',
  `groom` varchar(255) DEFAULT NULL COMMENT '新郎',
  `bride` varchar(255) DEFAULT NULL COMMENT '新娘',
  `music_name` varchar(255) DEFAULT NULL COMMENT '音乐名字 通过音乐表获取',
  `place` varchar(255) DEFAULT NULL COMMENT '地点',
  `photo1` varchar(255) DEFAULT NULL COMMENT '照片名字1  通过照片表获取',
  `photo2` varchar(255) DEFAULT NULL COMMENT '照片名字2  通过照片表获取',
  `photo3` varchar(255) DEFAULT NULL COMMENT '照片名字3  通过照片表获取',
  `compressImg` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content1` varchar(255) DEFAULT NULL COMMENT '祝福语1  通过祝福语表获取',
  `content2` varchar(255) DEFAULT NULL COMMENT '祝福语2  通过祝福语表获取',
  `content3` varchar(255) DEFAULT NULL COMMENT '祝福语3  通过祝福语表获取',
  `url` varchar(1055) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  `type` int(32) DEFAULT '1' COMMENT '模板类型 默认1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_invitations_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_invitations_info`;
CREATE TABLE `tb_invitations_info` (
  `id` varchar(100) NOT NULL,
  `invitations_id` varchar(255) DEFAULT NULL COMMENT '关联id',
  `name` varchar(255) DEFAULT NULL COMMENT '出席人',
  `person` int(8) DEFAULT '1' COMMENT '出席人数',
  `info` varchar(255) DEFAULT NULL COMMENT '附加信息',
  `state` int(1) DEFAULT '0' COMMENT '出席状态',
  `telnumber` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `unionid` varchar(255) DEFAULT NULL COMMENT '宾客授权的unionid',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_line`;
CREATE TABLE `tb_line` (
  `LINE_ID` varchar(100) NOT NULL,
  `TITLE` varchar(255) DEFAULT NULL COMMENT '名称',
  `LINE_URL` varchar(255) DEFAULT NULL COMMENT '链接',
  `LINE_ROAD` varchar(255) DEFAULT NULL COMMENT '线路',
  `TYPE` varchar(255) DEFAULT NULL COMMENT '类型',
  `LINE_ORDER` int(10) DEFAULT NULL COMMENT '排序',
  `PARENT_ID` varchar(255) DEFAULT NULL COMMENT '父类ID',
  PRIMARY KEY (`LINE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_live
-- ----------------------------
DROP TABLE IF EXISTS `tb_live`;
CREATE TABLE `tb_live` (
  `id` varchar(255) NOT NULL COMMENT '直播房间号',
  `liveCode` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `person` varchar(255) DEFAULT NULL COMMENT '人数',
  `gift` varchar(255) DEFAULT NULL COMMENT '礼物 通过礼物列表获取',
  `name` varchar(255) DEFAULT '' COMMENT '申请人名称',
  `status` varchar(255) DEFAULT NULL COMMENT '状态(0：未开始 1：正在直播 2：已结束)',
  `city` varchar(255) DEFAULT NULL COMMENT '婚礼地点',
  `wedding_time` varchar(255) DEFAULT NULL COMMENT '婚礼时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `join_status` varchar(255) DEFAULT '0' COMMENT '加入直播列表状态(0:未加入，1:已加入)',
  `userId` varchar(255) DEFAULT NULL COMMENT '对应的是wechat表中的用户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_live_game
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_game`;
CREATE TABLE `tb_live_game` (
  `liveId` varchar(255) NOT NULL COMMENT '直播id',
  `gameCode` varchar(255) NOT NULL COMMENT '游戏代码',
  `status` int(11) DEFAULT NULL,
  `create_time` timestamp NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`gameCode`,`liveId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_live_game_score
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_game_score`;
CREATE TABLE `tb_live_game_score` (
  `id` varchar(100) NOT NULL,
  `liveId` varchar(255) DEFAULT NULL COMMENT '游戏id',
  `gameCode` varchar(255) DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  `unionid` varchar(255) DEFAULT NULL COMMENT '用户id',
  `score` int(11) NOT NULL COMMENT '分数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_live_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_resource`;
CREATE TABLE `tb_live_resource` (
  `id` varchar(100) NOT NULL,
  `back_path` varchar(255) DEFAULT NULL COMMENT '回放地址',
  `live_path` varchar(255) DEFAULT NULL COMMENT '直播地址',
  `font_pic` varchar(255) DEFAULT NULL COMMENT '封面图片地址',
  `share_pic` varchar(255) DEFAULT NULL COMMENT '微信分享图片地址',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '用户头像地址',
  `live_id` varchar(255) DEFAULT NULL COMMENT '直播房间号',
  `create_time` timestamp NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `id` varchar(100) NOT NULL,
  `action` varchar(255) DEFAULT NULL COMMENT '操作',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `ip` varchar(255) DEFAULT NULL COMMENT 'IP',
  `excetion` varchar(255) DEFAULT NULL COMMENT '异常信息',
  `type` int(11) NOT NULL COMMENT '类型,1:登录.2:退出',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  `SYS_TYPE` varchar(255) DEFAULT NULL COMMENT '手机操作系统',
  `CID` varchar(255) DEFAULT NULL COMMENT '手机标识码',
  `vendor` varchar(255) DEFAULT NULL COMMENT '手机设备类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_money
-- ----------------------------
DROP TABLE IF EXISTS `tb_money`;
CREATE TABLE `tb_money` (
  `USER_ID` varchar(100) NOT NULL,
  `openid` varchar(255) DEFAULT NULL COMMENT '用户 对应用户表userId',
  `gold` float(11,2) DEFAULT '2000.00' COMMENT '嗨币余额',
  `money` varchar(255) DEFAULT '0' COMMENT 'rmb余额',
  `score` varchar(255) DEFAULT NULL COMMENT '积分',
  `saverecord` varchar(255) DEFAULT NULL COMMENT '充值记录(总和)',
  `payrecord` varchar(255) DEFAULT NULL COMMENT '消费记录(总和)',
  `chargerecord` varchar(255) DEFAULT NULL COMMENT '收费记录',
  `saverecordH` varchar(255) DEFAULT NULL COMMENT '充值记录（红包）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_music
-- ----------------------------
DROP TABLE IF EXISTS `tb_music`;
CREATE TABLE `tb_music` (
  `id` varchar(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '音乐名字',
  `format` varchar(255) DEFAULT NULL COMMENT '格式',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_photo
-- ----------------------------
DROP TABLE IF EXISTS `tb_photo`;
CREATE TABLE `tb_photo` (
  `id` varchar(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT 'NAME',
  `format` varchar(255) DEFAULT NULL COMMENT 'FORMAT',
  `path` varchar(255) DEFAULT NULL COMMENT 'PATH',
  `status` varchar(255) DEFAULT NULL COMMENT 'STATUS',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_pictures
-- ----------------------------
DROP TABLE IF EXISTS `tb_pictures`;
CREATE TABLE `tb_pictures` (
  `PICTURES_ID` varchar(100) NOT NULL,
  `TITLE` varchar(255) DEFAULT NULL COMMENT '标题',
  `NAME` varchar(255) DEFAULT NULL COMMENT '文件名',
  `PATH` varchar(255) DEFAULT NULL COMMENT '路径',
  `CREATETIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MASTER_ID` varchar(255) DEFAULT NULL COMMENT '属于',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`PICTURES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_redpacket
-- ----------------------------
DROP TABLE IF EXISTS `tb_redpacket`;
CREATE TABLE `tb_redpacket` (
  `id` varchar(100) NOT NULL,
  `USER_ID` varchar(255) DEFAULT NULL COMMENT '用户id 用户表获得',
  `waterhid` varchar(255) DEFAULT NULL COMMENT '流水id 自动生成',
  `status` varchar(255) DEFAULT NULL COMMENT '类型 0是收红包 1是发红包',
  `money` varchar(255) DEFAULT NULL COMMENT '涉及金额',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_report
-- ----------------------------
DROP TABLE IF EXISTS `tb_report`;
CREATE TABLE `tb_report` (
  `unionid` varchar(255) NOT NULL,
  `openid` varchar(255) NOT NULL DEFAULT '' COMMENT '微信表里的openid',
  `liveid` varchar(255) NOT NULL DEFAULT '' COMMENT '直播id 直播表里',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `wish` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `state` int(32) DEFAULT '0' COMMENT '登录次数',
  `renickname` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `reheadimgurl` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  `money` varchar(255) DEFAULT '0' COMMENT '用户签到发的份子钱',
  `outtradeno` varchar(255) DEFAULT NULL COMMENT '微信支付份子钱订单号',
  `paystate` int(32) DEFAULT '0' COMMENT '支付状态',
  PRIMARY KEY (`liveid`,`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_rtmp
-- ----------------------------
DROP TABLE IF EXISTS `tb_rtmp`;
CREATE TABLE `tb_rtmp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rtmp_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '推流地址',
  `m3u8_url` varchar(255) DEFAULT NULL COMMENT 'm3u8播放地址',
  `domain` varchar(255) DEFAULT NULL COMMENT '所属域名',
  `status` varchar(10) DEFAULT '0' COMMENT '状态 0:已使用 1:未使用',
  `liveId` varchar(255) DEFAULT NULL COMMENT '对应的直播房间号',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_seat
-- ----------------------------
DROP TABLE IF EXISTS `tb_seat`;
CREATE TABLE `tb_seat` (
  `id` varchar(100) NOT NULL,
  `userid` varchar(255) DEFAULT NULL COMMENT '创建者ID',
  `tablenumber` int(8) DEFAULT NULL COMMENT '桌号',
  `persondesc` varchar(255) DEFAULT NULL COMMENT '每桌人的详细情况',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `tableName` varchar(255) DEFAULT NULL COMMENT '桌名',
  `personNumber` int(8) DEFAULT NULL COMMENT '一桌的人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user_auths
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auths`;
CREATE TABLE `tb_user_auths` (
  `id` varchar(100) NOT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `identity_type` varchar(255) DEFAULT NULL COMMENT '验证类型 ',
  `identifier` varchar(255) DEFAULT NULL COMMENT '验证详细',
  `credential` varchar(255) DEFAULT NULL COMMENT '验证密码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_wechatinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechatinfo`;
CREATE TABLE `tb_wechatinfo` (
  `USER_ID` varchar(100) NOT NULL,
  `wechat` varchar(255) DEFAULT NULL COMMENT '用户表 id',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信用户标识',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '头像',
  `unionid` varchar(255) DEFAULT NULL COMMENT '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。',
  `cid` varchar(255) DEFAULT NULL COMMENT '手机ID',
  `count` varchar(255) DEFAULT NULL COMMENT '次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL COMMENT '来源（1.app微信登陆 2.现场扫码登陆）',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  `birthday` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_wishes
-- ----------------------------
DROP TABLE IF EXISTS `tb_wishes`;
CREATE TABLE `tb_wishes` (
  `id` varchar(100) NOT NULL,
  `content` varchar(255) DEFAULT NULL COMMENT 'CONTENT',
  `status` varchar(255) DEFAULT NULL COMMENT 'STATUS',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  `liveid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_withdrawals
-- ----------------------------
DROP TABLE IF EXISTS `tb_withdrawals`;
CREATE TABLE `tb_withdrawals` (
  `id` varchar(100) NOT NULL,
  `tid` varchar(255) DEFAULT NULL COMMENT '流水号',
  `money` varchar(255) DEFAULT NULL COMMENT '提取金额',
  `userid` varchar(255) DEFAULT NULL COMMENT 'userId',
  `status` varchar(255) DEFAULT NULL COMMENT 'status',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `modify_time` timestamp NULL DEFAULT NULL,
  `columns1` varchar(255) DEFAULT NULL COMMENT 'columns1',
  `columns2` varchar(255) DEFAULT NULL COMMENT 'columns2',
  `columns3` varchar(255) DEFAULT NULL COMMENT 'columns3',
  `columns4` varchar(255) DEFAULT NULL COMMENT 'columns4',
  `columns5` varchar(255) DEFAULT NULL COMMENT 'columns5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 增加祝福语默认数据
-- ----------------------------

INSERT INTO `tb_gift` VALUES ('1', '祝福语', '0', '签到背景2.jpg', '5', '2016-9-22 10:36:08', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'language');
INSERT INTO `tb_gift` VALUES ('2', '红包一', '0', NULL, '66', '2016-9-22 10:36:09', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'red1');
INSERT INTO `tb_gift` VALUES ('3', '红包二', '0', NULL, '88', '2016-9-22 10:34:50', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'red2');
INSERT INTO `tb_gift` VALUES ('4', '天使之心', '0', NULL, '10', '2016-9-22 10:36:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'angel');
INSERT INTO `tb_gift` VALUES ('5', '花好月圆', '0', NULL, '10', '2016-9-22 10:37:16', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'flower');
INSERT INTO `tb_gift` VALUES ('6', '法拉利', '0', NULL, '50', '2016-9-22 10:40:42', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'car');
INSERT INTO `tb_gift` VALUES ('7', '潜艇', '0', NULL, '60', '2016-9-22 10:40:11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'tanker');
INSERT INTO `tb_gift` VALUES ('8', '丘比特之心', '0', 'ddd', '100', '2016-10-21 10:05:37', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cupid');

-- ----------------------------
-- 增加音乐默认数据
-- ----------------------------

INSERT INTO `tb_music` VALUES ('1', 'Bruno Mars - Marry You.mp3', 'mp3', 'Bruno Mars - Marry You.mp3', '1', '2016-10-17 13:58:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('10', 'moumoon - Sunshine Girl - 英文版.mp3', 'mp3', 'moumoon - Sunshine Girl - 英文版.mp3', '1', '2016-10-17 14:10:43', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('2', 'Maroon 5 - Sugar.mp3', 'mp3', 'Maroon 5 - Sugar.mp3', '1', '2016-10-17 13:59:28', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('3', 'Maroon 5 - This love.mp3', 'mp3', 'Maroon 5 - This love.mp3', '1', '2016-10-17 14:00:03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('4', 'Mocca - I Remember.mp3', 'mp3', 'Mocca - I Remember.mp3', '1', '2016-10-17 14:00:40', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('5', '梁静茹 - 小手拉大手.mp3', 'mp3', '梁静茹 - 小手拉大手.mp3', '1', '2016-10-17 14:07:23', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('6', '林俊杰、蔡卓妍 - 小酒窝.mp3', 'mp3', '林俊杰、蔡卓妍 - 小酒窝.mp3', '1', '2016-10-17 14:07:32', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('7', '彭坦、春晓 - 我们的小世界.mp3', 'mp3', '彭坦、春晓 - 我们的小世界.mp3', '1', '2016-10-17 14:08:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('8', '周杰伦 - 告白气球 - 自制版伴奏.mp3', 'mp3', '周杰伦 - 告白气球 - 自制版伴奏.mp3', '1', '2016-10-17 14:08:59', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('9', '朱主爱 - 好想你.mp3', 'mp3', '朱主爱 - 好想你.mp3', '1', '2016-10-17 14:09:24', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('b87c36068cc546e394fd3eecb5e8a70b', '薛之谦 - 初学者.mp3', 'mp3', '2c58eb9299ee4094ae27caea75013a7c.mp3', '1', '2016-7-8 14:10:16', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_music` VALUES ('eedd149579504f6fb90608c087d4b99a', '小孩.mp3', 'mp3', '3bb8c1923a014823bcb4c5879b35e82a.mp3', '1', '2016-7-8 14:10:12', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- 增加婚礼流程默认数据
-- ----------------------------
INSERT INTO `tb_hllc` (`id`, `userid`, `lcname`, `state`, `createTime`, `orderNumber`) VALUES ('1', '1', '新人准备流程', '1', '2016-11-05 15:18:43', '1');
INSERT INTO `tb_hllc` (`id`, `userid`, `lcname`, `state`, `createTime`, `orderNumber`) VALUES ('2', '1', '接亲流程', '1', '2016-11-05 15:24:29', '2');
INSERT INTO `tb_hllc` (`id`, `userid`, `lcname`, `state`, `createTime`, `orderNumber`) VALUES ('3', '1', '婚礼流程', '1', '2016-11-05 15:24:49', '3');
INSERT INTO `tb_hllc` (`id`, `userid`, `lcname`, `state`, `createTime`, `orderNumber`) VALUES ('4', '1', '婚宴流程', '1', '2016-11-05 16:53:09', '4');

-- ----------------------------
-- 增加婚礼流程详细默认数据
-- ----------------------------

INSERT INTO `tb_hllc_info` VALUES ('1', '1', '7:00', '新郎新娘起床化妆，跟化妆师到位', '', '1', '2016-11-08 21:46:40');
INSERT INTO `tb_hllc_info` VALUES ('10', '2', '11:40', '车队出发', ' ', '1', '2016-11-08 21:52:21');
INSERT INTO `tb_hllc_info` VALUES ('11', '2', '12:00', '车队到达男方家', ' ', '1', '2016-11-08 21:52:56');
INSERT INTO `tb_hllc_info` VALUES ('12', '2', '12:10', '新郎的父亲抱新娘进门（湖南习俗）', ' ', '1', '2016-11-08 21:53:42');
INSERT INTO `tb_hllc_info` VALUES ('13', '2', '12:15', '新娘新郎拜天地、父母敬茶', ' ', '1', '2016-11-08 21:54:44');
INSERT INTO `tb_hllc_info` VALUES ('14', '2', '12:30', '开席就餐', ' ', '1', '2016-11-08 21:55:24');
INSERT INTO `tb_hllc_info` VALUES ('15', '3', '15:00', '婚庆到位，现场布置完毕', ' ', '1', '2016-11-08 21:56:44');
INSERT INTO `tb_hllc_info` VALUES ('16', '3', '17:00', '最后检查酒席安排、司仪、灯光音响、签到处等细节', ' ', '1', '2016-11-08 21:58:07');
INSERT INTO `tb_hllc_info` VALUES ('17', '3', '17:10', '结婚证书或者戒指准备', ' ', '1', '2016-11-08 21:58:05');
INSERT INTO `tb_hllc_info` VALUES ('18', '3', '17:20', '新娘换妆、新郎伴郎伴娘迎宾等人员到位', ' ', '1', '2016-11-08 22:00:02');
INSERT INTO `tb_hllc_info` VALUES ('19', '3', '18:00', '时间到、奏乐、新人入场', ' ', '1', '2016-11-08 22:00:47');
INSERT INTO `tb_hllc_info` VALUES ('2', '1', '8:00', '新郎发型做好，跟拍摄像到位', '', '1', '2016-11-08 21:47:39');
INSERT INTO `tb_hllc_info` VALUES ('20', '3', '18:30', '婚礼仪式、交换戒指等', ' ', '1', '2016-11-08 22:01:28');
INSERT INTO `tb_hllc_info` VALUES ('21', '3', '19:00', '感谢父母环节', ' ', '1', '2016-11-08 22:01:56');
INSERT INTO `tb_hllc_info` VALUES ('22', '3', '19:15', '新娘换敬酒礼服、敬酒开始', ' ', '1', '2016-11-08 22:02:47');
INSERT INTO `tb_hllc_info` VALUES ('23', '4', '19:15', '确认烟酒到新娘新郎手上（可准备假烟），伴郎伴娘随敬酒', ' ', '1', '2016-11-08 22:04:04');
INSERT INTO `tb_hllc_info` VALUES ('25', '4', '20:00', '敬酒完毕、婚宴结束、清点所以物品、离开酒店', ' ', '1', '2016-11-08 22:05:35');
INSERT INTO `tb_hllc_info` VALUES ('3', '2', '10:10', '婚车到位，婚车装饰到位', '', '1', '2016-11-08 21:47:58');
INSERT INTO `tb_hllc_info` VALUES ('4', '2', '10:30', '所有婚车到位到新娘家', '', '1', '2016-11-08 21:48:24');
INSERT INTO `tb_hllc_info` VALUES ('5', '2', '10:35', '伴郎准备好鲜花、红包', '', '1', '2016-11-08 21:48:53');
INSERT INTO `tb_hllc_info` VALUES ('6', '2', '10:35', '新娘藏好新鞋、等待新郎伴郎', '', '1', '2016-11-08 21:49:33');
INSERT INTO `tb_hllc_info` VALUES ('7', '2', '10:40', '敲门、堵门、盘问、塞红包等游戏', ' ', '1', '2016-11-08 21:50:21');
INSERT INTO `tb_hllc_info` VALUES ('8', '2', '11:00', '新郎找新鞋、向女方家人承诺', ' ', '1', '2016-11-08 21:51:22');
INSERT INTO `tb_hllc_info` VALUES ('9', '2', '11:30', '新郎新娘喝甜汤，向新娘父母告别', ' ', '1', '2016-11-08 21:52:00');

-- ----------------------------
-- 增加系统默认菜单
-- ----------------------------
INSERT INTO `SYS_MENU` VALUES (1, '系统管理', '#', '0', '1', 'icon-desktop', '1');
INSERT INTO `SYS_MENU` VALUES (2, '组织管理', 'role.do', '1', '2', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (4, '会员管理', 'happuser/listUsers.do', '1', '4', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (5, '系统用户', 'user/listUsers.do', '1', '3', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (6, '信息管理', '#', '0', '2', 'icon-list-alt', '2');
INSERT INTO `SYS_MENU` VALUES (7, '图片管理', 'pictures/list.do', '6', '1', NULL, '2');
INSERT INTO `SYS_MENU` VALUES (8, '性能监控', 'druid/index.html', '9', '1', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (9, '系统工具', '#', '0', '3', 'icon-th', '1');
INSERT INTO `SYS_MENU` VALUES (10, '接口测试', 'tool/interfaceTest.do', '9', '2', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (11, '发送邮件', 'tool/goSendEmail.do', '9', '3', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (12, '置二维码', 'tool/goTwoDimensionCode.do', '9', '4', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (13, '多级别树', 'tool/ztree.do', '9', '5', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (14, '地图工具', 'tool/map.do', '9', '6', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (15, '广告管理', 'andorra/list.do', '6', '2', NULL, '2');
INSERT INTO `SYS_MENU` VALUES (16, '特别推荐', 'featured/list.do', '6', '3', NULL, '2');
INSERT INTO `SYS_MENU` VALUES (17, '线路管理', 'line/list.do', '6', '4', NULL, '2');
INSERT INTO `SYS_MENU` VALUES (18, '友情链接', 'link/list.do', '6', '6', NULL, '2');
INSERT INTO `SYS_MENU` VALUES (19, '新闻管理', 'news/list.do', '6', '7', NULL, '2');
INSERT INTO `SYS_MENU` VALUES (20, '公告管理', 'notice/list.do', '6', '8', NULL, '2');
INSERT INTO `SYS_MENU` VALUES (21, '测试管理', 'play/listUsers.do', '1', '5', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (22, 'ball管理', 'ball2/list.do', '1', '1', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (23, 'pose管理', 'test/listTest.do', '1', '6', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (24, '申请管理', 'apply/list.do', '30', '5', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (25, '礼物管理', 'gift/list.do', '30', '6', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (26, '微喜帖管理', 'invitations/list.do', '30', '7', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (27, '直播管理', 'live/list.do', '30', '2', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (28, '音乐管理', 'music/list.do', '30', '3', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (29, '照片管理', 'photo/list.do', '30', '4', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (30, '嗨动管理', '#', '0', '4', 'icon-heart', '1');
INSERT INTO `SYS_MENU` VALUES (31, '祝福语管理', 'wishes/list.do', '30', '1', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (33, '静态', 'ball/proCode.do', '30', '10', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (34, '微信管理', 'wechatinfo/list.do', '30', '9', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (35, '群组管理', 'group/list.do', '30', '11', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (36, '群成员管理', 'grouptouser/list.do', '30', '12', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (37, '提现管理', 'withdrawals/list.do', '30', '13', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (38, '游戏礼物管理', 'gamegift/list.do', '30', '14', NULL, '1');
INSERT INTO `SYS_MENU` VALUES (39, '数据字典', 'dictionary/list.do', '30', '15', NULL, '1');

-- ----------------------------
-- 增加默认系统角色
-- ----------------------------

INSERT INTO `SYS_ROLE` VALUES ('1', '系统管理员', '1086626725878', '0', '1', '1', '1', '1', '1');
INSERT INTO `SYS_ROLE` VALUES ('2', '超级管理员', '1086626725878', '1', '545458749686', '274875809842', '274875809842', '274875809830', '2');
INSERT INTO `SYS_ROLE` VALUES ('7dfd8d1f7b6245d283217b7e63eec9b2', '管理员B', '1086626725878', '1', '2097398', '0', '0', '0', '7dfd8d1f7b6245d283217b7e63eec9b2');
INSERT INTO `SYS_ROLE` VALUES ('ac66961adaa2426da4470c72ffeec117', '管理员A', '1086626725878', '1', '2097206', '2097206', '0', '2097398', 'ac66961adaa2426da4470c72ffeec117');

-- ----------------------------
-- 增加默认用户
-- ----------------------------
INSERT INTO `SYS_USER` VALUES ('1', 'admin', 'de41b7fb99201d8334c23c014db35ecd92df81bc', '系统管理员', '1133671055321055258374707980945218933803269864762743594642571294', '1', '2016-12-01 14:46:55', '0:0:0:0:0:0:0:1', '0', '最高统治者', 'default', 'admin@main.com', '001', '18788888888');

-- ----------------------------
-- 增加默认用户
-- ----------------------------
INSERT INTO `SYS_USER_QX`VALUES ('1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `SYS_USER_QX` VALUES ('2', '1', '1', '1', '1', '1', '1', '1', '1');

-- ----------------------------
-- 增加角色 权限默认数据
-- ----------------------------
INSERT INTO `SYS_GL_QX` VALUES ('1', '2', '1', '1', '1', '1', '1', '1');
INSERT INTO `SYS_GL_QX` VALUES ('2', '1', '1', '1', '1', '1', '1', '1');

-- ----------------------------
-- 增加系统默认配置数据
-- ----------------------------
INSERT INTO `tb_dictionary` VALUES ('048793400f504bf8bee085ca3556835d', 'H5URL', 'h5项目的链接地址', 'http://h5.hidongtv.com/', '1');
INSERT INTO `tb_dictionary` VALUES ('41d3e504f0234075b21b30b5da7cda38', 'QINIUHOSTPATH', '七牛服务器地址', 'http://ocau360tj.bkt.clouddn.com/', '1');
INSERT INTO `tb_dictionary` VALUES ('4d0d3a2da361499a805ef2b82e89ea3d', 'APIURL', 'api链接地址', 'http://api.hidongtv.com/', '1');
INSERT INTO `tb_dictionary` VALUES ('543738077c5341fabaa6238a83e6db12', 'DEFAULTGOLD', '嗨豆默认值', '2000', '1');
INSERT INTO `tb_dictionary` VALUES ('7cd1e6892bae4bc085d199729ac03a55', 'CHATURL', '聊天接口链接地址', 'http://121.40.194.154:8081/haidong-chat/', '1');

INSERT INTO `SYS_DICTIONARIES` VALUES ('212a6765fddc4430941469e1ec8c8e6c', '人事部', '001', '1', 'c067fdaf51a141aeaa56ed26b70de863', '2', 'BM_001');
INSERT INTO `SYS_DICTIONARIES` VALUES ('3cec73a7cc8a4cb79e3f6ccc7fc8858c', '行政部', '002', '2', 'c067fdaf51a141aeaa56ed26b70de863', '2', 'BM_002');
INSERT INTO `SYS_DICTIONARIES` VALUES ('48724375640341deb5ef01ac51a89c34', '北京', 'dq001', '1', 'cdba0b5ef20e4fc0a5231fa3e9ae246a', '2', 'DQ_dq001');
INSERT INTO `SYS_DICTIONARIES` VALUES ('5a1547632cca449db378fbb9a042b336', '研发部', '004', '4', 'c067fdaf51a141aeaa56ed26b70de863', '2', 'BM_004');
INSERT INTO `SYS_DICTIONARIES` VALUES ('7f9cd74e60a140b0aea5095faa95cda3', '财务部', '003', '3', 'c067fdaf51a141aeaa56ed26b70de863', '2', 'BM_003');
INSERT INTO `SYS_DICTIONARIES` VALUES ('b861bd1c3aba4934acdb5054dd0d0c6e', '科技不', 'kj', '7', 'c067fdaf51a141aeaa56ed26b70de863', '2', 'BM_kj');
INSERT INTO `SYS_DICTIONARIES` VALUES ('c067fdaf51a141aeaa56ed26b70de863', '部门', 'BM', '1', '0', '1', 'BM');
INSERT INTO `SYS_DICTIONARIES` VALUES ('cdba0b5ef20e4fc0a5231fa3e9ae246a', '地区', 'DQ', '2', '0', '1', 'DQ');
INSERT INTO `SYS_DICTIONARIES` VALUES ('f184bff5081d452489271a1bd57599ed', '上海', 'SH', '2', 'cdba0b5ef20e4fc0a5231fa3e9ae246a', '2', 'DQ_SH');
INSERT INTO `SYS_DICTIONARIES` VALUES ('f30bf95e216d4ebb8169ff0c86330b8f', '客服部', '006', '6', 'c067fdaf51a141aeaa56ed26b70de863', '2', 'BM_006');
