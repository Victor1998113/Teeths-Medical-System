package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName calculateexpense
 */
@TableName(value ="calculateexpense")
@Data
public class Calculateexpense implements Serializable {
    /**
     * 
     */
    @TableField(value = "total")
    private Long total;

    /**
     * 
     */
    @TableField(value = "monthincome")
    private Long monthincome;

    /**
     * 
     */
    @TableField(value = "dayincome")
    private Long dayincome;

    /**
     * 
     */
    @TableField(value = "patientscount")
    private Long patientscount;

    /**
     * 
     */
    @TableField(value = "appointmentcount")
    private Long appointmentcount;

    /**
     * 
     */
    @TableField(value = "yesterdayincome")
    private Long yesterdayincome;

    /**
     * 
     */
    @TableField(value = "nearlyincome")
    private Long nearlyincome;

    /**
     * 
     */
    @TableField(value = "lastmonthincome")
    private Long lastmonthincome;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}