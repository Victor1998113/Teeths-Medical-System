package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @TableName patients
 */
@TableName(value ="patients")
@Data
public class Patients implements Serializable {
    /**
     * 
     */
    @TableId(value = "patientsid", type = IdType.AUTO)
    private Integer patientsid;

    /**
     * 
     */
    @NotBlank(message = "名称不能为空")
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @NotBlank(message = "年龄不能为空")
    @TableField(value = "age")
    private Integer age;

    /**
     * 
     */
    @NotBlank(message = "性别不能为空")
    @TableField(value = "gender")
    private String gender;

    /**
     * 
     */
    @NotBlank(message = "手机号不能为空")
    @TableField(value = "phone")
    private String phone;

    /**
     * 
     */
    @NotBlank(message = "就诊时间不能为空")
    @TableField(value = "atid")
    private Integer atid;

    /**
     * 
     */
    @NotBlank(message = "医生不能为空")
    @TableField(value = "doctorid")
    private Integer doctorid;

    @TableField(exist = false)
    private String clinictime;
    /**
     * 
     */
    @NotBlank(message = "病情不能为空")
    @TableField(value = "petient")
    private String petient;

    @TableField(value = "pstate")
    private Integer pstate;

    @TableField(exist = false)
    private String doctorName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}