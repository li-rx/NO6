package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

import com.cl.entity.TongzhifasongrizhiEntity;
import com.cl.entity.view.TongzhifasongrizhiView;

import com.cl.service.TongzhifasongrizhiService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.MapUtils;
import com.cl.utils.CommonUtil;

/**
 * 通知发送日志
 * 后端接口
 * @author 
 * @email 
 * @date 2025-03-27 15:44:15
 */
@RestController
@RequestMapping("/tongzhifasongrizhi")
public class TongzhifasongrizhiController {
    @Autowired
    private TongzhifasongrizhiService tongzhifasongrizhiService;

    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TongzhifasongrizhiEntity tongzhifasongrizhi,
                  HttpServletRequest request){
        EntityWrapper<TongzhifasongrizhiEntity> ew = new EntityWrapper<TongzhifasongrizhiEntity>();

        PageUtils page = tongzhifasongrizhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhifasongrizhi), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TongzhifasongrizhiEntity tongzhifasongrizhi,
                  HttpServletRequest request){
        EntityWrapper<TongzhifasongrizhiEntity> ew = new EntityWrapper<TongzhifasongrizhiEntity>();

        PageUtils page = tongzhifasongrizhiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tongzhifasongrizhi), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TongzhifasongrizhiEntity tongzhifasongrizhi){
        EntityWrapper<TongzhifasongrizhiEntity> ew = new EntityWrapper<TongzhifasongrizhiEntity>();
        ew.allEq(MPUtil.allEQMapPre( tongzhifasongrizhi, "tongzhifasongrizhi"));
        return R.ok().put("data", tongzhifasongrizhiService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TongzhifasongrizhiEntity tongzhifasongrizhi){
        EntityWrapper< TongzhifasongrizhiEntity> ew = new EntityWrapper< TongzhifasongrizhiEntity>();
        ew.allEq(MPUtil.allEQMapPre( tongzhifasongrizhi, "tongzhifasongrizhi"));
        TongzhifasongrizhiView tongzhifasongrizhiView =  tongzhifasongrizhiService.selectView(ew);
        return R.ok("查询通知发送日志成功").put("data", tongzhifasongrizhiView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TongzhifasongrizhiEntity tongzhifasongrizhi = tongzhifasongrizhiService.selectById(id);
        tongzhifasongrizhi = tongzhifasongrizhiService.selectView(new EntityWrapper<TongzhifasongrizhiEntity>().eq("id", id));
        return R.ok().put("data", tongzhifasongrizhi);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TongzhifasongrizhiEntity tongzhifasongrizhi = tongzhifasongrizhiService.selectById(id);
        tongzhifasongrizhi = tongzhifasongrizhiService.selectView(new EntityWrapper<TongzhifasongrizhiEntity>().eq("id", id));
        return R.ok().put("data", tongzhifasongrizhi);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    @SysLog("新增通知发送日志")
    public R save(@RequestBody TongzhifasongrizhiEntity tongzhifasongrizhi, HttpServletRequest request){
        tongzhifasongrizhiService.insert(tongzhifasongrizhi);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @SysLog("新增通知发送日志")
    @RequestMapping("/add")
    public R add(@RequestBody TongzhifasongrizhiEntity tongzhifasongrizhi, HttpServletRequest request){
        tongzhifasongrizhiService.insert(tongzhifasongrizhi);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改通知发送日志")
    public R update(@RequestBody TongzhifasongrizhiEntity tongzhifasongrizhi, HttpServletRequest request){
        tongzhifasongrizhiService.updateById(tongzhifasongrizhi);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除通知发送日志")
    public R delete(@RequestBody Long[] ids){
        tongzhifasongrizhiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 根据通知ID查询日志列表
     */
    @RequestMapping("/listByTongzhiId/{tongzhiid}")
    public R listByTongzhiId(@PathVariable("tongzhiid") Long tongzhiid){
        EntityWrapper<TongzhifasongrizhiEntity> ew = new EntityWrapper<TongzhifasongrizhiEntity>();
        ew.eq("tongzhiid", tongzhiid);
        ew.orderBy("caozuoshijian", false);
        List<TongzhifasongrizhiView> list = tongzhifasongrizhiService.selectListView(ew);
        return R.ok().put("data", list);
    }

}
