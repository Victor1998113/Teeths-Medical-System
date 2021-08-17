package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @TableName appointment
 */
@TableName(value ="appointment")
@Data
public class Appointment implements Serializable {
    /**
     * 
     */

    @TableId(value = "appointmentid", type = IdType.AUTO)
    private Integer appointmentid;

    /**
     * 
     */
    @NotBlank(message = "预定不能为空")
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @NotBlank(message = "手机号不能为空")
    @TableField(value = "phone")
    private String phone;

    /**
     * 
     */
    @NotBlank(message = "性别不能为空")
    @TableField(value = "gender")
    private String gender;

    /**
     * 
     */
    @NotBlank(message = "年龄不能为空")
    @TableField(value = "age")
    private Integer age;

    /**
     * 
     */
    @NotBlank(message = "医生不能为空")
    @TableField(value = "doctorid")
    private Integer doctorid;

    /**
     * 
     */
    @NotBlank(message = "预定时间不能为空")
    @TableField(value = "atid")
    private Integer atid;

    /**
     * 
     */
    @TableField(value = "petient")
    private String petient;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String datetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}