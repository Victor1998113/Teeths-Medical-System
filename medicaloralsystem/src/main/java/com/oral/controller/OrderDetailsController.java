package com.oral.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Detailedcost;
import com.oral.bean.Doctor;
import com.oral.bean.Orderdetails;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/17 19:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/orderDetails")
@Api(value = "订单",description = "详细订单")
public class OrderDetailsController extends BaseController{


    @GetMapping("/list")
    public Result list(String orderid){
        Page<Orderdetails> page = orderdetailsService.page(getPage(), new QueryWrapper<Orderdetails>().like("orderid", orderid).orderByDesc("paytime"));
        return Result.success(page);
    }
}
