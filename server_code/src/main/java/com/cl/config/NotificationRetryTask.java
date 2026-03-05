package com.cl.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cl.entity.TongzhijiluEntity;
import com.cl.service.NotificationService;
import com.cl.service.TongzhijiluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class NotificationRetryTask {

    @Autowired
    private TongzhijiluService tongzhijiluService;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 60000)
    public void retryFailedNotifications() {
        EntityWrapper<TongzhijiluEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("songdaizhuangtai", "发送失败")
               .lt("chongshicishu", 5)
               .ne("chulizhuangtai", "已处理");
        
        List<TongzhijiluEntity> failedNotifications = tongzhijiluService.selectList(wrapper);
        
        for (TongzhijiluEntity tongzhijilu : failedNotifications) {
            tongzhijilu.setChongshicishu(tongzhijilu.getChongshicishu() + 1);
            tongzhijilu.setZuichongshishijian(new Date());
            tongzhijilu.setSongdaizhuangtai("重试中");
            tongzhijiluService.updateById(tongzhijilu);
            
            notificationService.sendNotification(tongzhijilu);
        }
    }
}
