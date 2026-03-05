-- 通知记录表
DROP TABLE IF EXISTS `tongzhijilu`;

CREATE TABLE `tongzhijilu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tongzhibianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通知编号',
  `yishengzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '医生账号',
  `dianhua` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `jiuzhenshijian` datetime DEFAULT NULL COMMENT '就诊时间',
  `tongzhishijian` datetime DEFAULT NULL COMMENT '通知时间',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `shouji` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机',
  `tongzhibeizhu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通知备注',
  `songdaizhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '送达状态',
  `chongshicishu` int(11) DEFAULT NULL COMMENT '重试次数',
  `zuichongshishijian` datetime DEFAULT NULL COMMENT '最后重试时间',
  `cuowuxinxi` longtext COLLATE utf8mb4_unicode_ci COMMENT '错误信息',
  `chulizhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '处理状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tongzhibianhao` (`tongzhibianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知记录';
