package com.oral.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Appointmenttime;
import com.oral.bean.Doctor;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author panjianhui
 * @Date
 */
@RestController
@RequestMapping("/sys/scheduling")
@Api(value = "医生的预约时间表", description = "医生的预约时间接口")
public class SchedulingController extends BaseController {


    //查询医生的预约时间信息
    @GetMapping("/list")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据医生姓名进行模糊查询并分页")
    @ApiImplicitParam(name = "name", value = "医生姓名", required = false)
    public Result list(String name, Integer current, Integer size) {

        System.out.println(name);
        //页面页数和显示数据大小
        Page<Appointmenttime> page = appointmenttimeService.listdoctor(new Page<>(current, size), name);

        System.out.println(page);

        return Result.success(200, "查询成功", page);


    }

    //查询医生信息
    @GetMapping("/doctorList")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "查询所有医生")
    public Result doctorList() {
        List<Doctor> doctors = doctorService.list();
        return Result.success(doctors);
    }


    //删除
    @ResponseBody
    @ApiOperation(value = "删除方法", httpMethod = "POST", notes = "删除医生排版")
    @ApiImplicitParam(name = "atid", value = "当个删除", required = true)
    @DeleteMapping("/delete")
    public Result delete(int atid) {
        System.out.println(atid);
        appointmenttimeService.removeById(atid);
        return Result.success("");
    }

    //添加
    @ResponseBody
    @ApiOperation(value = "添加方法", httpMethod = "POST", notes = "添加医生排版")
    @ApiImplicitParam(name = "Appointmenttime", value = "医生排版对象", required = true)
    @PostMapping("/save")
    public Result save(@RequestBody Appointmenttime appointmenttime) {
        //默认为0
        System.out.println("appointmenttime = " + appointmenttime);
        appointmenttime.setAppstate(0);
        appointmenttimeService.save(appointmenttime);
        return Result.success("");
    }

    //编辑
    @ResponseBody
    @ApiOperation(value = "修改方法", httpMethod = "POST", notes = "编辑医生排版")
    @ApiImplicitParam(name = "Appointmenttime", value = "医生排版对象", required = true)
    @PutMapping("/updata")
    public Result updata(@RequestBody Appointmenttime appointmenttime) {
        appointmenttimeService.updateById(appointmenttime);
        return Result.success("");
    }


}