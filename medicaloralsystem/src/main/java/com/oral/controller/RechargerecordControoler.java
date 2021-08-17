package com.oral.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Member;
import com.oral.bean.Operationtype;
import com.oral.bean.Statistics;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author panjianhui
 * @Date
 */
@RestController
@RequestMapping("/sys/rechargerecord")
@Api(value = "充值记录表",description = "充值记录接口")
public class RechargerecordControoler extends BaseController{
    //查询所有充值记录表
    @GetMapping("/list")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据订单编号进行模糊查询并分页")
    @ApiImplicitParam(name = "staid",value = "充值记录类型",required = false)
    public Result list(String staid){
        Page<Statistics> page = statisticsService.page(getPage(), new QueryWrapper<Statistics>().like(StrUtil.isNotBlank(staid), "staid", staid).orderByDesc("paydate"));
        page.getRecords().forEach(u -> {
        u.setMname(memberService.getById(u.getMemberid()).getMname());
        });
        return Result.success(page);
    }
}
