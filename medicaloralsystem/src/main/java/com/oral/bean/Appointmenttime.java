package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

import javax.validation.constraints.Future;

/**
 * @TableName appointmenttime
 */
@TableName(value = "appointmenttime")
@Data
public class Appointmenttime implements Serializable {
    /**
     *
     */
    @TableId(value = "atid", type = IdType.AUTO)
    private Integer atid;

    /**
     *
     */
    @TableField(value = "datetime")
    @Future

    private String datetime;

    /**
     *
     */
    @TableField(value = "doctorid")
    private Integer doctorid;

    /**
     *
     */
    @TableField(value = "appstate")
    private Integer appstate;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
