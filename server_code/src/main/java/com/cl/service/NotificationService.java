package com.cl.service;

import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.entity.TongzhijiluEntity;
import com.cl.entity.YishengyuyueEntity;
import com.cl.service.JiuzhentongzhiService;
import com.cl.service.TongzhijiluService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private JiuzhentongzhiService jiuzhentongzhiService;

    @Autowired
    private TongzhijiluService tongzhijiluService;

    @Transactional
    public void createAndSendNotifications(YishengyuyueEntity yuyue) {
        String[] reminderTypes = {"预约成功通知", "就诊前1天提醒", "就诊前2小时提醒"};
        
        for (String reminderType : reminderTypes) {
            createNotification(yuyue, reminderType);
        }
    }

    private void createNotification(YishengyuyueEntity yuyue, String reminderType) {
        String tongzhibianhao = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        
        JiuzhentongzhiEntity jiuzhentongzhi = new JiuzhentongzhiEntity();
        jiuzhentongzhi.setTongzhibianhao(tongzhibianhao);
        jiuzhentongzhi.setYishengzhanghao(yuyue.getYishengzhanghao());
        jiuzhentongzhi.setDianhua(yuyue.getDianhua());
        jiuzhentongzhi.setJiuzhenshijian(yuyue.getYuyueshijian());
        jiuzhentongzhi.setTongzhishijian(new Date());
        jiuzhentongzhi.setZhanghao(yuyue.getZhanghao());
        jiuzhentongzhi.setShouji(yuyue.getShouji());
        jiuzhentongzhi.setTongzhibeizhu(reminderType);
        jiuzhentongzhiService.insert(jiuzhentongzhi);
        
        TongzhijiluEntity tongzhijilu = new TongzhijiluEntity();
        tongzhijilu.setTongzhibianhao(tongzhibianhao);
        tongzhijilu.setYishengzhanghao(yuyue.getYishengzhanghao());
        tongzhijilu.setDianhua(yuyue.getDianhua());
        tongzhijilu.setJiuzhenshijian(yuyue.getYuyueshijian());
        tongzhijilu.setTongzhishijian(new Date());
        tongzhijilu.setZhanghao(yuyue.getZhanghao());
        tongzhijilu.setShouji(yuyue.getShouji());
        tongzhijilu.setTongzhibeizhu(reminderType);
        tongzhijilu.setSongdaizhuangtai("发送中");
        tongzhijilu.setChongshicishu(0);
        tongzhijilu.setChulizhuangtai("待处理");
        tongzhijiluService.insert(tongzhijilu);
        
        sendNotification(tongzhijilu);
    }

    public void sendNotification(TongzhijiluEntity tongzhijilu) {
        try {
            boolean success = simulateSend(tongzhijilu);
            if (success) {
                tongzhijilu.setSongdaizhuangtai("已送达");
                tongzhijilu.setChulizhuangtai("已处理");
            } else {
                tongzhijilu.setSongdaizhuangtai("发送失败");
                tongzhijilu.setCuowuxinxi("模拟发送失败");
            }
            tongzhijiluService.updateById(tongzhijilu);
        } catch (Exception e) {
            tongzhijilu.setSongdaizhuangtai("发送失败");
            tongzhijilu.setCuowuxinxi(e.getMessage());
            tongzhijiluService.updateById(tongzhijilu);
        }
    }

    private boolean simulateSend(TongzhijiluEntity tongzhijilu) {
        return true;
    }
}
