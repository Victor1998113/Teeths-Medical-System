package com.oral.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Pharmacy;
import com.oral.common.lang.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/pharmacy/")
public class PharmacyController extends BaseController {


    @RequestMapping("list")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据患者姓名查询")
    @ApiImplicitParam(name = "name", value = "患者名称", required = true)
    public Result selectAllByPaName(String name, Integer current, Integer size) {


        Page<Pharmacy> pharmacies = pharmacyService.selectAllByPatientsName(new Page<>(current, size), name);

        return Result.success(pharmacies);

    }

}
