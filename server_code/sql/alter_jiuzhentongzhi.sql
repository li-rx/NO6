-- 就诊通知表添加新字段
-- 添加用户接收状态、通知发送状态、重试次数等字段

ALTER TABLE `jiuzhentongzhi`
ADD COLUMN `jieshouzhuangtai` varchar(20) DEFAULT '未接收' COMMENT '接收状态：未接收、已接收、已读',
ADD COLUMN `fasongzhuangtai` varchar(20) DEFAULT '待发送' COMMENT '发送状态：待发送、发送中、发送成功、发送失败',
ADD COLUMN `chongshicishu` int(11) DEFAULT 0 COMMENT '重试次数',
ADD COLUMN `zuizhongchongshishijian` datetime DEFAULT NULL COMMENT '最后重试时间',
ADD COLUMN `fasongshibaiyuanyin` varchar(500) DEFAULT NULL COMMENT '发送失败原因',
ADD COLUMN `tongzhileixing` varchar(50) DEFAULT '就诊提醒' COMMENT '通知类型：预约成功提醒、就诊前24小时提醒、就诊前1小时提醒、就诊当天提醒',
ADD COLUMN `jihuafasongshijian` datetime DEFAULT NULL COMMENT '计划发送时间',
ADD COLUMN `shijifasongshijian` datetime DEFAULT NULL COMMENT '实际发送时间',
ADD COLUMN `yuyueid` bigint(20) DEFAULT NULL COMMENT '关联的预约ID',
ADD INDEX `idx_fasongzhuangtai` (`fasongzhuangtai`),
ADD INDEX `idx_jieshouzhuangtai` (`jieshouzhuangtai`),
ADD INDEX `idx_yuyueid` (`yuyueid`),
ADD INDEX `idx_jihuafasongshijian` (`jihuafasongshijian`);

-- 创建通知发送日志表
CREATE TABLE IF NOT EXISTS `tongzhifasongrizhi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tongzhiid` bigint(20) NOT NULL COMMENT '通知ID',
  `caozuoleixing` varchar(50) DEFAULT NULL COMMENT '操作类型：发送、重试、标记已读、标记接收',
  `caozuoshijian` datetime DEFAULT NULL COMMENT '操作时间',
  `caozuoren` varchar(200) DEFAULT NULL COMMENT '操作人',
  `caozuobeizhu` varchar(500) DEFAULT NULL COMMENT '操作备注',
  `zhuangtai` varchar(20) DEFAULT NULL COMMENT '状态：成功、失败',
  `cuowuxinxi` varchar(500) DEFAULT NULL COMMENT '错误信息',
  PRIMARY KEY (`id`),
  KEY `idx_tongzhiid` (`tongzhiid`),
  KEY `idx_caozuoshijian` (`caozuoshijian`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知发送日志';
