package com.cl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务配置类
 * 启用异步和定时任务支持
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ScheduledConfig {
    // 配置类，启用定时任务和异步处理
}
