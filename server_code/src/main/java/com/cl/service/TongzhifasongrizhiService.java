package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.entity.TongzhifasongrizhiEntity;
import com.cl.entity.view.TongzhifasongrizhiView;
import com.cl.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通知发送日志
 *
 * @author 
 * @email 
 * @date 2025-03-27 15:44:15
 */
public interface TongzhifasongrizhiService extends IService<TongzhifasongrizhiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<TongzhifasongrizhiView> selectListView(Wrapper<TongzhifasongrizhiEntity> wrapper);
    
    TongzhifasongrizhiView selectView(@Param("ew") Wrapper<TongzhifasongrizhiEntity> wrapper);
    
    PageUtils queryPage(Map<String, Object> params,Wrapper<TongzhifasongrizhiEntity> wrapper);
    
}
