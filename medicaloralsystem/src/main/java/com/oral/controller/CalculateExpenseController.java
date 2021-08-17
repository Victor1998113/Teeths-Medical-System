package com.oral.controller;

import com.oral.bean.Calculateexpense;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/17 13:04
 * @Version 1.0
 */
@RestController
@RequestMapping("/calculate")
@Api(value = "首页",description = "首页费用计算接口")
public class CalculateExpenseController extends BaseController{


    @GetMapping("/expense")
    public Result expense(){
        Calculateexpense c = calculateexpenseService.getOne(null);

            c.setTotal(c.getTotal()==null?0:c.getTotal());
            c.setMonthincome(c.getMonthincome()==null?0:c.getMonthincome());
            c.setDayincome(c.getDayincome()==null?0:c.getDayincome());
            c.setPatientscount(c.getPatientscount()==null?0:c.getPatientscount());
            c.setAppointmentcount(c.getAppointmentcount()==null?0:c.getAppointmentcount());
            c.setYesterdayincome(c.getYesterdayincome()==null?0:c.getYesterdayincome());
            c.setNearlyincome(c.getNearlyincome()==null?0:c.getNearlyincome());
            c.setLastmonthincome(c.getLastmonthincome()==null?0:c.getLastmonthincome());

            Long l = statisticsService.total();
            c.setTotal(c.getTotal()+(l==null?0:l));

            return Result.success(c);
    }



}
