package com.oral.common.vo;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author tanyongpeng
 * @Date 2021/7/14 11:07
 * @Version 1.0
 */
@Data
public class NewSurgery {

    private Integer[] ids;

    private Integer surgeryPatientsid;

    private Integer surgeryDoctorid;
}
