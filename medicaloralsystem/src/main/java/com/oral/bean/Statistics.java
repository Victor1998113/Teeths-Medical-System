package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName statistics
 */
@TableName(value ="statistics")
@Data
public class Statistics implements Serializable {
    /**
     * 
     */
    @TableId(value = "staid")
    private String staid;

    /**
     * 
     */
    @TableField(value = "memberid")
    private Integer memberid;

    /**
     * 
     */
    @TableField(value = "paydate")
    private LocalDateTime paydate;

    /**
     * 
     */
    @TableField(value = "paymoney")
    private Double paymoney;

    @TableField(exist = false)
    private String mname;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}