package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName roleauthority
 */
@TableName(value ="roleauthority")
@Data
public class Roleauthority implements Serializable {
    /**
     * 
     */
    @TableId(value = "roleauthorityid", type = IdType.AUTO)
    private Integer roleauthorityid;

    /**
     * 
     */
    @TableField(value = "positionid")
    private Integer positionid;

    /**
     * 
     */
    @TableField(value = "authorityid")
    private Integer authorityid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}