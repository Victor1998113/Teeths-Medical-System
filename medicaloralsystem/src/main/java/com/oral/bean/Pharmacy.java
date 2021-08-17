package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName pharmacy
 */
@TableName(value = "pharmacy")
@Data
public class Pharmacy implements Serializable {
    /**
     *
     */
    @TableId(value = "phid", type = IdType.AUTO)
    private Integer phid;

    /**
     *
     */
    @TableField(value = "drugid")
    private Integer drugid;

    /**
     *
     */
    @TableField(value = "patientsid")
    private Integer patientsid;

    /**
     *
     */
    @TableField(value = "doctorid")
    private Integer doctorid;

    @TableField(exist = false)
    private String drName;

    @TableField(exist = false)
    private String paName;

    @TableField(exist = false)
    private String doName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}