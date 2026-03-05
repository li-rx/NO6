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


@TableName("tongzhijilu")
public class TongzhijiluEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public TongzhijiluEntity() {
		
	}
	
	public TongzhijiluEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@TableId(type = IdType.AUTO)
	private Long id;
	private String tongzhibianhao;
	private String yishengzhanghao;
	private String dianhua;
	private Date jiuzhenshijian;
	private Date tongzhishijian;
	private String zhanghao;
	private String shouji;
	private String tongzhibeizhu;
	private String songdaizhuangtai;
	private Integer chongshicishu;
	private Date zuichongshishijian;
	private String cuowuxinxi;
	private String chulizhuangtai;
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
	public void setTongzhibianhao(String tongzhibianhao) {
		this.tongzhibianhao = tongzhibianhao;
	}
	public String getTongzhibianhao() {
		return tongzhibianhao;
	}
	public void setYishengzhanghao(String yishengzhanghao) {
		this.yishengzhanghao = yishengzhanghao;
	}
	public String getYishengzhanghao() {
		return yishengzhanghao;
	}
	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}
	public String getDianhua() {
		return dianhua;
	}
	public void setJiuzhenshijian(Date jiuzhenshijian) {
		this.jiuzhenshijian = jiuzhenshijian;
	}
	public Date getJiuzhenshijian() {
		return jiuzhenshijian;
	}
	public void setTongzhishijian(Date tongzhishijian) {
		this.tongzhishijian = tongzhishijian;
	}
	public Date getTongzhishijian() {
		return tongzhishijian;
	}
	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}
	public String getZhanghao() {
		return zhanghao;
	}
	public void setShouji(String shouji) {
		this.shouji = shouji;
	}
	public String getShouji() {
		return shouji;
	}
	public void setTongzhibeizhu(String tongzhibeizhu) {
		this.tongzhibeizhu = tongzhibeizhu;
	}
	public String getTongzhibeizhu() {
		return tongzhibeizhu;
	}
	public void setSongdaizhuangtai(String songdaizhuangtai) {
		this.songdaizhuangtai = songdaizhuangtai;
	}
	public String getSongdaizhuangtai() {
		return songdaizhuangtai;
	}
	public void setChongshicishu(Integer chongshicishu) {
		this.chongshicishu = chongshicishu;
	}
	public Integer getChongshicishu() {
		return chongshicishu;
	}
	public void setZuichongshishijian(Date zuichongshishijian) {
		this.zuichongshishijian = zuichongshishijian;
	}
	public Date getZuichongshishijian() {
		return zuichongshishijian;
	}
	public void setCuowuxinxi(String cuowuxinxi) {
		this.cuowuxinxi = cuowuxinxi;
	}
	public String getCuowuxinxi() {
		return cuowuxinxi;
	}
	public void setChulizhuangtai(String chulizhuangtai) {
		this.chulizhuangtai = chulizhuangtai;
	}
	public String getChulizhuangtai() {
		return chulizhuangtai;
	}

}
