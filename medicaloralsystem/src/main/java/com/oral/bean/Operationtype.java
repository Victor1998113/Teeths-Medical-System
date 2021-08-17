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
 * @TableName operationtype
 */
@TableName(value ="operationtype")
@Data
public class Operationtype implements Serializable {
    /**
     *
     */
    @TableId(value = "opsid", type = IdType.AUTO)
    private Integer opsid;

    /**
     *
     */
    @NotBlank(message = "类型不能为空")
    @TableField(value = "opsname")
    private String opsname;

    /**
     *
     */
    @NotBlank(message = "金额不能为空")
    @TableField(value = "opsmoney")
    private Integer opsmoney;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
