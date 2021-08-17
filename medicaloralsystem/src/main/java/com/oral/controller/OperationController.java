package com.oral.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Operation;
import com.oral.common.lang.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/operation")
public class OperationController extends BaseController {


    //查询所有手术统计记录
    @GetMapping("/list")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据医生名称进行模糊查询并分页")
    @ApiImplicitParam(name = "name", value = "医生名称", required = false)
    public Result list(String name, Integer current, Integer size) {
        System.out.println(name);
        //页面页数和显示数据大小
        Page<Operation> page = operationService.selectByDoctorName(new Page<>(current, size), name);
        System.out.println(page);
        return Result.success(200, "查询成功", page);
    }


}
