package com.oral.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Doctor;
import com.oral.bean.Operationtype;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author panjianhui
 * @Date
 */
@RestController
@RequestMapping("/sys/operationtype")
@Api(value = "手术类型表",description = "手术类型接口")
public class OperationtypeController extends BaseController{
    //查询所有手术类型记录
    @GetMapping("/list")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据用户名进行模糊查询并分页")
    @ApiImplicitParam(name = "opsname",value = "手术类型",required = false)
    public Result list(String opsname){
        Page<Operationtype> page = operationtypeService.page(getPage(), new QueryWrapper<Operationtype>().like(StrUtil.isNotBlank(opsname), "opsname", opsname));
        return Result.success(page);
    }


    @ResponseBody
    @ApiOperation(value = "添加方法",httpMethod = "POST",notes = "添加手术类型")
    @ApiImplicitParam(name = "operationtype",value = "手术类型对象",required = true)
    @PostMapping("/save")
    public Result save(@RequestBody Operationtype operationtype) {

        boolean save = operationtypeService.save(operationtype);
        if (save) {
            return Result.success(200, "添加成功", null);
        } else {
            return Result.fail(400, "添加失败", null);
        }
    }

    @ResponseBody
    @ApiOperation(value = "删除方法",httpMethod = "POST",notes = "删除手术类型")
    @ApiImplicitParam(name = "opsid",value = "当个删除",required = true)
    @DeleteMapping("/delete")
    public Result delete(int id) {
        System.out.println(id);
        boolean b = operationtypeService.removeById(id);
        if (b) {
            return Result.success(200, "删除成功", null);
        } else {
            return Result.fail(400, "删除失败", null);
        }
    }

    @ResponseBody
    @ApiOperation(value = "修改方法",httpMethod = "POST",notes = "修改手术类型")
    @ApiImplicitParam(name = "operationtype",value = "手术类型对象",required = true)
    @PutMapping("/updata")
    public Result updata(@RequestBody Operationtype operationtype) {
        boolean b = operationtypeService.updateById(operationtype);
        if (b) {
            return Result.success(200, "修改成功", null);
        } else {
            return Result.fail(400, "修改失败", null);
        }
    }
}
