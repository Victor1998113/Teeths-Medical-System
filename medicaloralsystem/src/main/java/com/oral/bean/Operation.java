package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName operation
 */
@TableName(value ="operation")
@Data
public class Operation implements Serializable {
    /**
     * 
     */
    @TableId(value = "operationid", type = IdType.AUTO)
    private Integer operationid;

    /**
     * 
     */
    @TableField(value = "doctorid")
    private Integer doctorid;

    /**
     * 
     */
    @TableField(value = "patientsid")
    private Integer patientsid;

    /**
     * 
     */
    @TableField(value = "opsid")
    private Integer opsid;

    @TableField(exist = false)
    private String doName;

    @TableField(exist = false)
    private String paName;

    @TableField(exist = false)
    private String opName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}