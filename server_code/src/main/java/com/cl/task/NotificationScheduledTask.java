package com.cl.task;

import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.service.NotificationSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通知定时任务
 * 定时扫描并发送待发送的通知
 */
@Component
public class NotificationScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduledTask.class);

    @Autowired
    private NotificationSendService notificationSendService;

    /**
     * 每分钟扫描一次待发送的通知
     * 检查计划发送时间已到的通知并发送
     */
    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void sendPendingNotifications() {
        logger.info("开始扫描待发送的通知...");

        try {
            List<JiuzhentongzhiEntity> pendingList = notificationSendService.getPendingNotifications();

            if (pendingList != null && !pendingList.isEmpty()) {
                logger.info("发现 {} 条待发送通知", pendingList.size());

                for (JiuzhentongzhiEntity tongzhi : pendingList) {
                    try {
                        notificationSendService.sendNotification(tongzhi);
                        logger.info("通知 [{}] 发送任务已提交", tongzhi.getTongzhibianhao());
                    } catch (Exception e) {
                        logger.error("发送通知 [{}] 时发生错误: {}", tongzhi.getTongzhibianhao(), e.getMessage());
                    }
                }
            } else {
                logger.debug("暂无待发送的通知");
            }
        } catch (Exception e) {
            logger.error("扫描待发送通知时发生错误: {}", e.getMessage());
        }
    }

    /**
     * 每小时扫描一次发送失败的通知
     * 自动重试发送失败的通知（最多重试3次）
     */
    @Scheduled(fixedRate = 3600000) // 每小时执行一次
    public void retryFailedNotifications() {
        logger.info("开始扫描发送失败的通知...");

        try {
            List<JiuzhentongzhiEntity> failedList = notificationSendService.getFailedNotifications();

            if (failedList != null && !failedList.isEmpty()) {
                logger.info("发现 {} 条发送失败的通知", failedList.size());

                for (JiuzhentongzhiEntity tongzhi : failedList) {
                    // 只重试重试次数小于3次的通知
                    if (tongzhi.getChongshicishu() != null && tongzhi.getChongshicishu() < 3) {
                        try {
                            notificationSendService.sendNotification(tongzhi);
                            logger.info("失败通知 [{}] 重试任务已提交", tongzhi.getTongzhibianhao());
                        } catch (Exception e) {
                            logger.error("重试通知 [{}] 时发生错误: {}", tongzhi.getTongzhibianhao(), e.getMessage());
                        }
                    } else {
                        logger.warn("通知 [{}] 已超过最大重试次数，不再自动重试", tongzhi.getTongzhibianhao());
                    }
                }
            } else {
                logger.debug("暂无发送失败的通知");
            }
        } catch (Exception e) {
            logger.error("扫描发送失败通知时发生错误: {}", e.getMessage());
        }
    }

    /**
     * 每天凌晨2点清理过期通知
     * 清理超过30天的已发送成功且已读的通知
     */
    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
    public void cleanupOldNotifications() {
        logger.info("开始清理过期通知...");
        // TODO: 实现过期通知清理逻辑
        logger.info("过期通知清理完成");
    }
}
