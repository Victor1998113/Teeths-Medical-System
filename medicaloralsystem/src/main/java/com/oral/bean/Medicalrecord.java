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
 * @TableName medicalrecord
 */
@TableName(value ="medicalrecord")
@Data
public class Medicalrecord implements Serializable {
    /**
     * 
     */
    @TableId(value = "medicalRecordid", type = IdType.AUTO)
    private Integer medicalRecordid;

    /**
     * 
     */
    @TableField(value = "patientsid")
    private Integer patientsid;

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
    @TableField(value = "doctorid")
    private Integer doctorid;

    /**
     * 
     */
    @TableField(value = "opsid")
    private Integer opsid;

    /**
     * 
     */
    @TableField(value = "drugid")
    private Integer drugid;

    /**
     * 
     */
    @TableField(value = "cost")
    private Integer cost;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
