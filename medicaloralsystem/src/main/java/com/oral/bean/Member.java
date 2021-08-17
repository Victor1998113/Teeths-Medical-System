package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 *
 * @TableName member
 */
@TableName(value ="member")
@Data
public class Member implements Serializable {
    /**
     *
     */
    @TableId(value = "memberid", type = IdType.AUTO)
    private Integer memberid;

    /**
     *
     */
    @TableField(value = "mname")
    private String mname;

    /**
     *
     */
    @TableField(value = "mage")
    private Integer mage;

    /**
     *
     */
    @TableField(value = "mgender")
    private String mgender;

    /**
     *
     */
    @TableField(value = "mphone")
    private String mphone;

    /**
     *
     */
    @TableField(value = "mdate")
    private String mdate;

    /**
     *
     */
    @TableField(value = "mmoney")
    private Double mmoney;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
