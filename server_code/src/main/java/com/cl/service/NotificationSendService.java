package com.cl.service;

import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.entity.TongzhifasongrizhiEntity;
import com.cl.entity.YishengyuyueEntity;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 通知发送服务
 * 负责发送就诊通知，包含重试机制
 */
@Service
public class NotificationSendService {

    @Autowired
    private JiuzhentongzhiService jiuzhentongzhiService;

    @Autowired
    private TongzhifasongrizhiService tongzhifasongrizhiService;

    // 最大重试次数
    private static final int MAX_RETRY_COUNT = 3;

    // 通知类型常量
    public static final String TONGZHI_LEIXING_YUYUE_CHENGGONG = "预约成功提醒";
    public static final String TONGZHI_LEIXING_JIUZHEN_24H = "就诊前24小时提醒";
    public static final String TONGZHI_LEIXING_JIUZHEN_1H = "就诊前1小时提醒";
    public static final String TONGZHI_LEIXING_JIUZHEN_DANGTIAN = "就诊当天提醒";

    // 发送状态常量
    public static final String FASONG_ZHUANGTAI_DAIFASONG = "待发送";
    public static final String FASONG_ZHUANGTAI_FASONGZHONG = "发送中";
    public static final String FASONG_ZHUANGTAI_CHENGGONG = "发送成功";
    public static final String FASONG_ZHUANGTAI_SHIBAI = "发送失败";

    // 接收状态常量
    public static final String JIESHOU_ZHUANGTAI_WEIJIESHOU = "未接收";
    public static final String JIESHOU_ZHUANGTAI_YIJIESHOU = "已接收";
    public static final String JIESHOU_ZHUANGTAI_YIDU = "已读";

    /**
     * 预约审核通过后，创建所有后续通知
     * 在预约成功时立即创建所有提醒通知
     */
    @Transactional
    public void createAllNotifications(YishengyuyueEntity yuyue) {
        if (yuyue == null || yuyue.getYuyueshijian() == null) {
            return;
        }

        Date yuyueTime = yuyue.getYuyueshijian();
        Long yuyueId = yuyue.getId();

        // 1. 创建预约成功通知（立即发送）
        createNotification(yuyue, TONGZHI_LEIXING_YUYUE_CHENGGONG, new Date(),
                "您的预约已成功！预约时间：" + formatDate(yuyueTime));

        // 2. 创建就诊前24小时提醒
        Date time24h = calculateTimeBefore(yuyueTime, 24);
        if (time24h.after(new Date())) {
            createNotification(yuyue, TONGZHI_LEIXING_JIUZHEN_24H, time24h,
                    "温馨提醒：您预约的就诊将在24小时后开始，请做好准备。");
        }

        // 3. 创建就诊前1小时提醒
        Date time1h = calculateTimeBefore(yuyueTime, 1);
        if (time1h.after(new Date())) {
            createNotification(yuyue, TONGZHI_LEIXING_JIUZHEN_1H, time1h,
                    "温馨提醒：您预约的就诊将在1小时后开始，请准时到达。");
        }

        // 4. 创建就诊当天提醒
        Date timeSameDay = calculateTimeSameDay(yuyueTime, 8); // 就诊当天上午8点提醒
        if (timeSameDay.after(new Date())) {
            createNotification(yuyue, TONGZHI_LEIXING_JIUZHEN_DANGTIAN, timeSameDay,
                    "温馨提醒：您今天有预约就诊，请记得准时前往。");
        }
    }

    /**
     * 创建单个通知
     */
    private void createNotification(YishengyuyueEntity yuyue, String tongzhiLeixing,
                                    Date jihuafasongshijian, String beizhu) {
        JiuzhentongzhiEntity tongzhi = new JiuzhentongzhiEntity();
        tongzhi.setTongzhibianhao(generateTongzhiBianhao());
        tongzhi.setYishengzhanghao(yuyue.getYishengzhanghao());
        tongzhi.setDianhua(yuyue.getDianhua());
        tongzhi.setJiuzhenshijian(yuyue.getYuyueshijian());
        tongzhi.setTongzhishijian(new Date());
        tongzhi.setZhanghao(yuyue.getZhanghao());
        tongzhi.setShouji(yuyue.getShouji());
        tongzhi.setTongzhibeizhu(beizhu);
        tongzhi.setTongzhileixing(tongzhiLeixing);
        tongzhi.setJihuafasongshijian(jihuafasongshijian);
        tongzhi.setFasongzhuangtai(FASONG_ZHUANGTAI_DAIFASONG);
        tongzhi.setJieshouzhuangtai(JIESHOU_ZHUANGTAI_WEIJIESHOU);
        tongzhi.setChongshicishu(0);
        tongzhi.setYuyueid(yuyue.getId());

        jiuzhentongzhiService.insert(tongzhi);

        // 如果是立即发送的通知，立即执行发送
        if (jihuafasongshijian == null || jihuafasongshijian.before(new Date())
                || jihuafasongshijian.equals(new Date())) {
            sendNotification(tongzhi);
        }
    }

    /**
     * 发送通知（带重试机制）
     */
    @Async
    public void sendNotification(JiuzhentongzhiEntity tongzhi) {
        if (tongzhi == null) {
            return;
        }

        // 更新状态为发送中
        tongzhi.setFasongzhuangtai(FASONG_ZHUANGTAI_FASONGZHONG);
        jiuzhentongzhiService.updateById(tongzhi);

        boolean success = false;
        String errorMsg = null;
        int retryCount = 0;

        // 尝试发送（包含重试）
        while (retryCount <= MAX_RETRY_COUNT && !success) {
            try {
                // 执行实际的发送操作
                success = doSend(tongzhi);

                if (success) {
                    // 发送成功
                    tongzhi.setFasongzhuangtai(FASONG_ZHUANGTAI_CHENGGONG);
                    tongzhi.setShijifasongshijian(new Date());
                    tongzhi.setJieshouzhuangtai(JIESHOU_ZHUANGTAI_YIJIESHOU);
                    tongzhi.setFasongshibaiyuanyin(null);

                    // 记录成功日志
                    saveLog(tongzhi.getId(), "发送", "系统", "通知发送成功", "成功", null);
                } else {
                    throw new RuntimeException("发送失败");
                }
            } catch (Exception e) {
                retryCount++;
                errorMsg = e.getMessage();

                if (retryCount <= MAX_RETRY_COUNT) {
                    // 记录重试日志
                    saveLog(tongzhi.getId(), "重试", "系统",
                            "第" + retryCount + "次重试", "失败", errorMsg);

                    // 等待一段时间后重试（指数退避）
                    try {
                        Thread.sleep(1000 * retryCount);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        // 如果最终失败
        if (!success) {
            tongzhi.setFasongzhuangtai(FASONG_ZHUANGTAI_SHIBAI);
            tongzhi.setFasongshibaiyuanyin(errorMsg);
            tongzhi.setChongshicishu(retryCount);
            tongzhi.setZuizhongchongshishijian(new Date());

            // 记录最终失败日志
            saveLog(tongzhi.getId(), "发送", "系统",
                    "通知发送失败，已重试" + MAX_RETRY_COUNT + "次", "失败", errorMsg);
        }

        jiuzhentongzhiService.updateById(tongzhi);
    }

    /**
     * 手动重试发送失败的通知
     */
    @Transactional
    public void retryFailedNotification(Long tongzhiId, String operator) {
        JiuzhentongzhiEntity tongzhi = jiuzhentongzhiService.selectById(tongzhiId);
        if (tongzhi == null) {
            throw new RuntimeException("通知不存在");
        }

        if (!FASONG_ZHUANGTAI_SHIBAI.equals(tongzhi.getFasongzhuangtai())) {
            throw new RuntimeException("只有发送失败的通知才能重试");
        }

        // 重置状态
        tongzhi.setFasongzhuangtai(FASONG_ZHUANGTAI_DAIFASONG);
        tongzhi.setChongshicishu(0);
        tongzhi.setFasongshibaiyuanyin(null);
        jiuzhentongzhiService.updateById(tongzhi);

        // 记录手动重试日志
        saveLog(tongzhiId, "重试", operator, "管理员手动触发重试", "成功", null);

        // 重新发送
        sendNotification(tongzhi);
    }

    /**
     * 标记通知为已读
     */
    @Transactional
    public void markAsRead(Long tongzhiId, String operator) {
        JiuzhentongzhiEntity tongzhi = jiuzhentongzhiService.selectById(tongzhiId);
        if (tongzhi == null) {
            throw new RuntimeException("通知不存在");
        }

        tongzhi.setJieshouzhuangtai(JIESHOU_ZHUANGTAI_YIDU);
        jiuzhentongzhiService.updateById(tongzhi);

        // 记录日志
        saveLog(tongzhiId, "标记已读", operator, "用户标记通知为已读", "成功", null);
    }

    /**
     * 执行实际的发送操作
     * 这里模拟发送，实际项目中可以集成短信、推送等服务
     */
    private boolean doSend(JiuzhentongzhiEntity tongzhi) {
        // TODO: 集成实际的发送服务（短信、APP推送等）
        // 模拟发送成功率90%
        return Math.random() > 0.1;
    }

    /**
     * 保存操作日志
     */
    private void saveLog(Long tongzhiId, String caozuoleixing, String caozuoren,
                         String beizhu, String zhuangtai, String cuowuxinxi) {
        TongzhifasongrizhiEntity log = new TongzhifasongrizhiEntity();
        log.setTongzhiid(tongzhiId);
        log.setCaozuoleixing(caozuoleixing);
        log.setCaozuoren(caozuoren);
        log.setCaozuobeizhu(beizhu);
        log.setZhuangtai(zhuangtai);
        log.setCuowuxinxi(cuowuxinxi);
        log.setCaozuoshijian(new Date());

        tongzhifasongrizhiService.insert(log);
    }

    /**
     * 生成通知编号
     */
    private String generateTongzhiBianhao() {
        return "TZ" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4);
    }

    /**
     * 计算指定时间前N小时的时间
     */
    private Date calculateTimeBefore(Date baseTime, int hoursBefore) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseTime);
        cal.add(Calendar.HOUR_OF_DAY, -hoursBefore);
        return cal.getTime();
    }

    /**
     * 计算就诊当天指定时间
     */
    private Date calculateTimeSameDay(Date baseTime, int hourOfDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseTime);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 格式化日期
     */
    private String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * 查询需要发送的通知（定时任务调用）
     */
    public List<JiuzhentongzhiEntity> getPendingNotifications() {
        EntityWrapper<JiuzhentongzhiEntity> ew = new EntityWrapper<>();
        ew.eq("fasongzhuangtai", FASONG_ZHUANGTAI_DAIFASONG);
        ew.le("jihuafasongshijian", new Date()); // 计划发送时间已到
        return jiuzhentongzhiService.selectList(ew);
    }

    /**
     * 查询发送失败的通知
     */
    public List<JiuzhentongzhiEntity> getFailedNotifications() {
        EntityWrapper<JiuzhentongzhiEntity> ew = new EntityWrapper<>();
        ew.eq("fasongzhuangtai", FASONG_ZHUANGTAI_SHIBAI);
        return jiuzhentongzhiService.selectList(ew);
    }
}
