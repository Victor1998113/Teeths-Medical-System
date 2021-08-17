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
 * @TableName detailedcost
 */
@TableName(value ="detailedcost")
@Data
public class Detailedcost implements Serializable {
    /**
     * 
     */
    @TableField(value = "id")
    private Integer id;

    /**
     * 
     */
    @TableField(value = "clinictime")
    private LocalDateTime clinictime;

    /**
     * 
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 
     */
    @TableField(value = "doctorName")
    private String doctorName;

    /**
     * 
     */
    @TableField(value = "opsname")
    private String opsname;

    /**
     * 
     */
    @TableField(value = "drugName")
    private String drugName;

    /**
     * 
     */
    @TableField(value = "pname")
    private String pname;

    /**
     * 
     */
    @TableField(value = "cost")
    private Integer cost;

    /**
     * 
     */
    @TableField(value = "patientsid")
    private Integer patientsid;

    /**
     * 
     */
    @TableField(value = "state")
    private Integer state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}