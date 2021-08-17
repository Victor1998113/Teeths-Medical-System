package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @TableName authority
 */
@TableName(value ="authority")
@Data
public class Authority implements Serializable {
    /**
     * 
     */
    @TableId(value = "authorityid", type = IdType.AUTO)
    private Integer authorityid;

    /**
     * 
     */
    @NotNull(message = "上级菜单不能为空")
    @TableField(value = "parentid")
    private Long parentid;

    /**
     * 
     */
    @NotBlank(message = "菜单名称不能为空")
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "path")
    private String path;

    /**
     * 
     */
    @NotBlank(message = "菜单授权码不能为空")
    @TableField(value = "perms")
    private String perms;

    /**
     * 
     */
    @TableField(value = "component")
    private String component;

    /**
     * 
     */
    @NotNull(message = "菜单类型不能为空")
    @TableField(value = "type")
    private Integer type;

    /**
     * 
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 
     */
    @TableField(value = "orderNum")
    private Integer orderNum;

    /**
     * 
     */
    @TableField(value = "statu")
    private Integer statu;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Authority> children = new ArrayList<>();

}