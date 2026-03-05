package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.cl.service.NotificationSendService;
import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;
import com.cl.annotation.SysLog;

import com.cl.entity.JiuzhentongzhiEntity;
import com.cl.entity.view.JiuzhentongzhiView;

import com.cl.service.JiuzhentongzhiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 就诊通知
 * 后端接口
 * @author 
 * @email 
 * @date 2025-03-27 15:44:15
 */
@RestController
@RequestMapping("/jiuzhentongzhi")
public class JiuzhentongzhiController {
    @Autowired
    private JiuzhentongzhiService jiuzhentongzhiService;

    @Autowired
    private NotificationSendService notificationSendService;







    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JiuzhentongzhiEntity jiuzhentongzhi,
                                                                                                                                                    HttpServletRequest request){
                    String tableName = request.getSession().getAttribute("tableName").toString();
                                                                        if(tableName.equals("yisheng")) {
                    jiuzhentongzhi.setYishengzhanghao((String)request.getSession().getAttribute("username"));
                                    }
                                                                                                                                                                    if(tableName.equals("yonghu")) {
                    jiuzhentongzhi.setZhanghao((String)request.getSession().getAttribute("username"));
                                    }
                                                                                                                        EntityWrapper<JiuzhentongzhiEntity> ew = new EntityWrapper<JiuzhentongzhiEntity>();
                                                                                                                                                                                                        
        
        
        PageUtils page = jiuzhentongzhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiuzhentongzhi), params), params));
        return R.ok().put("data", page);
    }







    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JiuzhentongzhiEntity jiuzhentongzhi,
		HttpServletRequest request){
        EntityWrapper<JiuzhentongzhiEntity> ew = new EntityWrapper<JiuzhentongzhiEntity>();

		PageUtils page = jiuzhentongzhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jiuzhentongzhi), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JiuzhentongzhiEntity jiuzhentongzhi){
       	EntityWrapper<JiuzhentongzhiEntity> ew = new EntityWrapper<JiuzhentongzhiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jiuzhentongzhi, "jiuzhentongzhi")); 
        return R.ok().put("data", jiuzhentongzhiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JiuzhentongzhiEntity jiuzhentongzhi){
        EntityWrapper< JiuzhentongzhiEntity> ew = new EntityWrapper< JiuzhentongzhiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jiuzhentongzhi, "jiuzhentongzhi")); 
		JiuzhentongzhiView jiuzhentongzhiView =  jiuzhentongzhiService.selectView(ew);
		return R.ok("查询就诊通知成功").put("data", jiuzhentongzhiView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JiuzhentongzhiEntity jiuzhentongzhi = jiuzhentongzhiService.selectById(id);
		jiuzhentongzhi = jiuzhentongzhiService.selectView(new EntityWrapper<JiuzhentongzhiEntity>().eq("id", id));
        return R.ok().put("data", jiuzhentongzhi);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JiuzhentongzhiEntity jiuzhentongzhi = jiuzhentongzhiService.selectById(id);
		jiuzhentongzhi = jiuzhentongzhiService.selectView(new EntityWrapper<JiuzhentongzhiEntity>().eq("id", id));
        return R.ok().put("data", jiuzhentongzhi);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    @SysLog("新增就诊通知")
    public R save(@RequestBody JiuzhentongzhiEntity jiuzhentongzhi, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(jiuzhentongzhi);
        jiuzhentongzhiService.insert(jiuzhentongzhi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @SysLog("新增就诊通知")
    @RequestMapping("/add")
    public R add(@RequestBody JiuzhentongzhiEntity jiuzhentongzhi, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(jiuzhentongzhi);
        jiuzhentongzhiService.insert(jiuzhentongzhi);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改就诊通知")
    public R update(@RequestBody JiuzhentongzhiEntity jiuzhentongzhi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jiuzhentongzhi);
        jiuzhentongzhiService.updateById(jiuzhentongzhi);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除就诊通知")
    public R delete(@RequestBody Long[] ids){
        jiuzhentongzhiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	








    /**
     * 获取发送失败的通知列表
     */
    @RequestMapping("/failedList")
    public R failedList(@RequestParam Map<String, Object> params, HttpServletRequest request){
        EntityWrapper<JiuzhentongzhiEntity> ew = new EntityWrapper<JiuzhentongzhiEntity>();
        ew.eq("fasongzhuangtai", "发送失败");

        PageUtils page = jiuzhentongzhiService.queryPage(params, MPUtil.sort(MPUtil.between(ew, params), params));
        return R.ok().put("data", page);
    }

    /**
     * 手动重试发送失败的通知
     */
    @RequestMapping("/retry/{id}")
    @Transactional
    @SysLog("重试发送通知")
    public R retry(@PathVariable("id") Long id, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        try {
            notificationSendService.retryFailedNotification(id, username);
            return R.ok("重试请求已提交");
        } catch (Exception e) {
            return R.error(1, e.getMessage());
        }
    }

    /**
     * 批量重试发送失败的通知
     */
    @RequestMapping("/retryBatch")
    @Transactional
    @SysLog("批量重试发送通知")
    public R retryBatch(@RequestBody Long[] ids, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        int successCount = 0;
        int failCount = 0;

        for(Long id : ids) {
            try {
                notificationSendService.retryFailedNotification(id, username);
                successCount++;
            } catch (Exception e) {
                failCount++;
            }
        }

        return R.ok("成功提交" + successCount + "个重试请求，失败" + failCount + "个");
    }

    /**
     * 标记通知为已读
     */
    @RequestMapping("/markAsRead/{id}")
    @Transactional
    @SysLog("标记通知已读")
    public R markAsRead(@PathVariable("id") Long id, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        try {
            notificationSendService.markAsRead(id, username);
            return R.ok("标记已读成功");
        } catch (Exception e) {
            return R.error(1, e.getMessage());
        }
    }

    /**
     * 根据预约ID查询通知列表
     */
    @RequestMapping("/listByYuyueId/{yuyueid}")
    public R listByYuyueId(@PathVariable("yuyueid") Long yuyueid){
        EntityWrapper<JiuzhentongzhiEntity> ew = new EntityWrapper<JiuzhentongzhiEntity>();
        ew.eq("yuyueid", yuyueid);
        ew.orderBy("jihuafasongshijian", true);
        List<JiuzhentongzhiView> list = jiuzhentongzhiService.selectListView(ew);
        return R.ok().put("data", list);
    }

    /**
     * 获取通知统计信息
     */
    @RequestMapping("/statistics")
    public R statistics(){
        Map<String, Object> result = new HashMap<>();

        // 待发送
        EntityWrapper<JiuzhentongzhiEntity> ew1 = new EntityWrapper<>();
        ew1.eq("fasongzhuangtai", "待发送");
        int pendingCount = jiuzhentongzhiService.selectCount(ew1);
        result.put("pendingCount", pendingCount);

        // 发送成功
        EntityWrapper<JiuzhentongzhiEntity> ew2 = new EntityWrapper<>();
        ew2.eq("fasongzhuangtai", "发送成功");
        int successCount = jiuzhentongzhiService.selectCount(ew2);
        result.put("successCount", successCount);

        // 发送失败
        EntityWrapper<JiuzhentongzhiEntity> ew3 = new EntityWrapper<>();
        ew3.eq("fasongzhuangtai", "发送失败");
        int failedCount = jiuzhentongzhiService.selectCount(ew3);
        result.put("failedCount", failedCount);

        // 已接收/已读
        EntityWrapper<JiuzhentongzhiEntity> ew4 = new EntityWrapper<>();
        ew4.in("jieshouzhuangtai", new String[]{"已接收", "已读"});
        int receivedCount = jiuzhentongzhiService.selectCount(ew4);
        result.put("receivedCount", receivedCount);

        return R.ok().put("data", result);
    }

}
