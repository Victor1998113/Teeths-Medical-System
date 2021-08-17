package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @TableName doctor
 */
@TableName(value ="doctor")
@Data
public class Doctor implements Serializable {
    /**
     * 
     */
    @TableId(value = "doctorid", type = IdType.AUTO)
    private Integer doctorid;

    /**
     * 
     */

    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 
     */
    @TableField(value = "idcard")
    private String idcard;

    /**
     * 
     */
    @NotBlank(message = "用户名不能为空")
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "lastlogin")
    private LocalDateTime lastlogin;


    /**
     * 
     */
    @TableField(value = "statu")
    private Integer statu;

    @TableField(exist = false)
    private String scheduling;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 拥有得角色
     */
    @TableField(exist = false)
    private List<Position> roles = new ArrayList<>();


}