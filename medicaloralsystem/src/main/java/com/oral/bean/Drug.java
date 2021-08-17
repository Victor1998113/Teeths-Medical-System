package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName drug
 */
@TableName(value ="drug")
@Data
public class Drug implements Serializable {
    /**
     * 
     */
    @TableId(value = "drugid", type = IdType.AUTO)
    private Integer drugid;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "price")
    private BigDecimal price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}