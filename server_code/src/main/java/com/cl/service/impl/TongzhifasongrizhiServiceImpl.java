package com.cl.service.impl;

import com.cl.dao.TongzhifasongrizhiDao;
import com.cl.entity.TongzhifasongrizhiEntity;
import com.cl.entity.view.TongzhifasongrizhiView;
import com.cl.service.TongzhifasongrizhiService;
import com.cl.utils.PageUtils;
import com.cl.utils.Query;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 通知发送日志
 */
@Service("tongzhifasongrizhiService")
public class TongzhifasongrizhiServiceImpl extends ServiceImpl<TongzhifasongrizhiDao, TongzhifasongrizhiEntity> implements TongzhifasongrizhiService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TongzhifasongrizhiEntity> page = this.selectPage(
                new Query<TongzhifasongrizhiEntity>(params).getPage(),
                new EntityWrapper<TongzhifasongrizhiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<TongzhifasongrizhiEntity> wrapper) {
        Page<TongzhifasongrizhiView> page = new Query<TongzhifasongrizhiView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }

    @Override
    public List<TongzhifasongrizhiView> selectListView(Wrapper<TongzhifasongrizhiEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public TongzhifasongrizhiView selectView(Wrapper<TongzhifasongrizhiEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }

}
