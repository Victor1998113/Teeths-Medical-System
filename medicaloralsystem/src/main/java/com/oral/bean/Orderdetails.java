package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName orderdetails
 */
@TableName(value ="orderdetails")
@Data
public class Orderdetails implements Serializable {
    /**
     * 
     */
    @TableId(value = "orderid")
    private String orderid;

    /**
     * 
     */
    @TableField(value = "patientsid")
    private Integer patientsid;

    /**
     * 
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 
     */
    @TableField(value = "paytime")
    private LocalDateTime paytime;

    /**
     * 
     */
    @TableField(value = "payment")
    private String payment;

    /**
     * 
     */
    @TableField(value = "payamount")
    private Integer payamount;

    /**
     * 
     */
    @TableField(value = "orderstate")
    private Integer orderstate;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}