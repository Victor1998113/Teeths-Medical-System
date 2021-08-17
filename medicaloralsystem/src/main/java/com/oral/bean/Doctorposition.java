package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName doctorposition
 */
@TableName(value ="doctorposition")
@Data
public class Doctorposition implements Serializable {
    /**
     * 
     */
    @TableId(value = "dpid", type = IdType.AUTO)
    private Integer dpid;

    /**
     * 
     */
    @TableField(value = "doctorid")
    private Integer doctorid;

    /**
     * 
     */
    @TableField(value = "positionid")
    private Integer positionid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}