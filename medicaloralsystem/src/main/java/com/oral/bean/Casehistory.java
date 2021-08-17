package com.oral.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName casehistory
 */
@TableName(value = "casehistory")
@Data
public class Casehistory implements Serializable {
    /**
     *
     */
    @ExcelProperty("病历编号")
    @TableId(value = "caid",type = IdType.AUTO)
    private Integer caid;

    /**
     *
     */
    @ExcelProperty("患者编号")
    @TableField(value = "patientsid")
    private Integer patientsid;

    /**
     *
     */
    @ExcelProperty("年龄")
    @TableField(value = "age")
    private Integer age;

    /**
     *
     */
    @ExcelProperty("性别")
    @TableField(value = "gender")
    private String gender;

    /**
     *
     */
    @ExcelProperty("就诊时间")
    @TableField(value = "clinictime")
    private String clinictime;

    /**
     *
     */
    @ExcelProperty("手术")
    @TableField(value = "operationName")
    private String operationName;

    /**
     *
     */
    @ExcelProperty("病情")
    @TableField(value = "petient")
    private String petient;

    @ExcelProperty("姓名")
    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
