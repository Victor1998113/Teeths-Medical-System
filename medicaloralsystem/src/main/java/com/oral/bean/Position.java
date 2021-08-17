package com.oral.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @TableName position
 */
@TableName(value ="position")
@Data
public class Position implements Serializable {
    /**
     * 
     */
    @TableId(value = "positionid", type = IdType.AUTO)
    private Integer positionid;

    /**
     * 
     */
    @NotBlank(message = "职位名称不能为空")
    @TableField(value = "job")
    private String job;

    /**
     * 
     */
    @NotBlank(message = "职位编码不能为空")
    @TableField(value = "posicode")
    private String posicode;

    /**
     * 
     */
    @TableField(value = "pstate")
    private Integer pstate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Integer> menuIds = new ArrayList<>();

}