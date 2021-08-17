package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName departments
 */
@TableName(value ="departments")
@Data
public class Departments implements Serializable {
    /**
     * 
     */
    @TableId(value = "departmentsid", type = IdType.AUTO)
    private Integer departmentsid;

    /**
     * 
     */
    @TableField(value = "dename")
    private String dename;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}