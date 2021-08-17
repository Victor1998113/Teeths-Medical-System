package com.oral.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author panjianhui
 * @Date
 */
@Data
public class Medical {
    private String orderstate;
    private LocalDateTime paytime;
    private Integer count;
    private Integer payamount;
    private String payment;
    private Integer patientsid;
}
