package com.cl.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 通知发送日志
 * 数据库通用操作实体类（普通增删改查）
 * @author
 * @email
 * @date 2025-03-27 15:44:15
 */
@TableName("tongzhifasongrizhi")
public class TongzhifasongrizhiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public TongzhifasongrizhiEntity() {

	}

	public TongzhifasongrizhiEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 通知ID
	 */
	private Long tongzhiid;

	/**
	 * 操作类型：发送、重试、标记已读、标记接收
	 */
	private String caozuoleixing;

	/**
	 * 操作时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date caozuoshijian;

	/**
	 * 操作人
	 */
	private String caozuoren;

	/**
	 * 操作备注
	 */
	private String caozuobeizhu;

	/**
	 * 状态：成功、失败
	 */
	private String zhuangtai;

	/**
	 * 错误信息
	 */
	private String cuowuxinxi;


	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设置：通知ID
	 */
	public void setTongzhiid(Long tongzhiid) {
		this.tongzhiid = tongzhiid;
	}
	/**
	 * 获取：通知ID
	 */
	public Long getTongzhiid() {
		return tongzhiid;
	}

	/**
	 * 设置：操作类型
	 */
	public void setCaozuoleixing(String caozuoleixing) {
		this.caozuoleixing = caozuoleixing;
	}
	/**
	 * 获取：操作类型
	 */
	public String getCaozuoleixing() {
		return caozuoleixing;
	}

	/**
	 * 设置：操作时间
	 */
	public void setCaozuoshijian(Date caozuoshijian) {
		this.caozuoshijian = caozuoshijian;
	}
	/**
	 * 获取：操作时间
	 */
	public Date getCaozuoshijian() {
		return caozuoshijian;
	}

	/**
	 * 设置：操作人
	 */
	public void setCaozuoren(String caozuoren) {
		this.caozuoren = caozuoren;
	}
	/**
	 * 获取：操作人
	 */
	public String getCaozuoren() {
		return caozuoren;
	}

	/**
	 * 设置：操作备注
	 */
	public void setCaozuobeizhu(String caozuobeizhu) {
		this.caozuobeizhu = caozuobeizhu;
	}
	/**
	 * 获取：操作备注
	 */
	public String getCaozuobeizhu() {
		return caozuobeizhu;
	}

	/**
	 * 设置：状态
	 */
	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}
	/**
	 * 获取：状态
	 */
	public String getZhuangtai() {
		return zhuangtai;
	}

	/**
	 * 设置：错误信息
	 */
	public void setCuowuxinxi(String cuowuxinxi) {
		this.cuowuxinxi = cuowuxinxi;
	}
	/**
	 * 获取：错误信息
	 */
	public String getCuowuxinxi() {
		return cuowuxinxi;
	}

}
