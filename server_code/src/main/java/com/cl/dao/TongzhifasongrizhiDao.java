package com.cl.dao;

import com.cl.entity.TongzhifasongrizhiEntity;
import com.cl.entity.view.TongzhifasongrizhiView;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 通知发送日志
 * 
 * @author 
 * @email 
 * @date 2025-03-27 15:44:15
 */
public interface TongzhifasongrizhiDao extends BaseMapper<TongzhifasongrizhiEntity> {
	
	List<TongzhifasongrizhiView> selectListView(@Param("ew") Wrapper<TongzhifasongrizhiEntity> wrapper);

	List<TongzhifasongrizhiView> selectListView(Pagination page,@Param("ew") Wrapper<TongzhifasongrizhiEntity> wrapper);
	
	TongzhifasongrizhiView selectView(@Param("ew") Wrapper<TongzhifasongrizhiEntity> wrapper);
	
}
