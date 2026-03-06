package com.cl.entity.view;

import com.cl.entity.TongzhifasongrizhiEntity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 通知发送日志
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义字段需要返回使用）
 */
public class TongzhifasongrizhiView extends TongzhifasongrizhiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public TongzhifasongrizhiView(){
	}

	public TongzhifasongrizhiView(TongzhifasongrizhiEntity tongzhifasongrizhiEntity){
		try {
			BeanUtils.copyProperties(this, tongzhifasongrizhiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
