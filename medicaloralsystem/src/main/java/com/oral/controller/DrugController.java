package com.oral.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Drug;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanyongpeng
 * @Date 2021/7/14 15:08
 * @Version 1.0
 */
@RestController
@RequestMapping("/drug")
@Api(value = "药品", description = "药品接口")
public class DrugController extends BaseController {


    @GetMapping("/list")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据药品名称查询")
    @ApiImplicitParam(name = "name", value = "药品名称", required = true)
    public Result list(String name) {
        Page<Drug> page = drugService.page(getPage(), new QueryWrapper<Drug>().like(StrUtil.isNotBlank(name), "name", name));
        return Result.success(page);
    }

    @GetMapping("/selectById")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据药品Id查询")
    @ApiImplicitParam(name = "drugid", value = "药品ID", required = true)
    public Result selectById(int drugid) {
        Drug drug = drugService.getById(drugid);
        return Result.success(drug);
    }

    @RequestMapping("/deleteByid")
    @ApiOperation(value = "删除方法", httpMethod = "POST", notes = "根据药品Id删除")
    @ApiImplicitParam(name = "drugid", value = "药品Id", required = true)
    public Result deleteById(int drugid) {

        drugService.removeById(drugid);
        return Result.success("");
    }


    @RequestMapping("/updataById")
    @ApiOperation(value = "修改方法", httpMethod = "POST", notes = "根据药品Id修改")
    @ApiImplicitParam(name = "Drug", value = "药品", required = true)
    public Result updateById(@RequestBody Drug drug) {
        System.out.println(drug);
        drugService.updateById(drug);
        return Result.success("");
    }

    @RequestMapping("/save")
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    public Result save(@RequestBody Drug drug) {
        System.out.println(drug);
        drugService.save(drug);
        return Result.success("");
    }
}
