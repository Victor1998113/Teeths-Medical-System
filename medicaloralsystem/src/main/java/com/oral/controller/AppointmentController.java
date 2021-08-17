package com.oral.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Appointment;
import com.oral.bean.Appointmenttime;
import com.oral.bean.Doctor;
import com.oral.bean.Patients;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/12 10:05
 * @Version 1.0
 */
@RestController
@RequestMapping("/app/ment")
@Api(value = "预约",description = "预约接口")
public class AppointmentController extends BaseController{


    @GetMapping("/info/{id}")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据id获取预定信息")
    @ApiImplicitParam(name = "id",value = "预定编号",required = true)
    public Result info(@PathVariable("id") long id){
        Appointment appointment = appointmentService.getById(id);
        return Result.success(appointment);
    }


    @GetMapping("/list")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据预约人姓名进行模糊查询")
    @ApiImplicitParam(name = "username",value = "预约人姓名",required = true)
    public Result list(String username){

        Page<Appointment> page = appointmentService.page(getPage(), new QueryWrapper<Appointment>().like(StrUtil.isNotBlank(username), "username", username));

        page.getRecords().forEach(a ->{
            a.setName(doctorService.getById(a.getDoctorid()).getName());
            a.setDatetime(appointmenttimeService.getById(a.getAtid()).getDatetime().toString());
        });

        return Result.success(page);
    }


    @PostMapping("/save")
    @Transactional
    @ApiOperation(value = "添加方法",httpMethod = "POST",notes = "添加预定信息")
    @ApiImplicitParam(name = "appointment",value = "预定对象",required = true)
    public Result save( @RequestBody Appointment appointment){
        //添加预定
        appointmentService.save(appointment);

        //修改医生排版状态
        Appointmenttime appointmenttime = appointmenttimeService.getById(appointment.getAtid());
        appointmenttime.setAppstate(1);
        appointmenttimeService.updateById(appointmenttime);

        return Result.success(appointment);
    }


    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除方法",httpMethod = "POST",notes = "添加删除信息")
    @ApiImplicitParam(name = "id",value = "预定id",required = true)
    public Result delete(@PathVariable("id") Integer id){
        appointmentService.removeById(id);
        return Result.success("");
    }


    @PostMapping("/see/{id}")
    @Transactional
    @ApiOperation(value = "修改方法",httpMethod = "POST",notes = "就诊为病人，删除当前预定信息")
    @ApiImplicitParam(name = "id",value = "预定id",required = true)
    public Result see(@PathVariable("id") Integer id){


        Appointment app = appointmentService.getById(id);

        Patients patients = new Patients();
        patients.setName(app.getUsername());
        patients.setAge(app.getAge());
        patients.setGender(app.getGender());
        patients.setPhone(app.getPhone());
        patients.setAtid(app.getAtid());
        patients.setDoctorid(app.getDoctorid());
        patients.setPetient(app.getPetient());
        patients.setPstate(0);
        //添加为病人
        patientsService.save(patients);

        //删除预定信息
        appointmentService.removeById(id);

        return Result.success("");
    }

    @GetMapping("/doctorList")
    @ApiOperation(value = "查询方法",httpMethod = "POST",notes = "查询所有医生")
    public Result doctorList(){
        List<Doctor> doctors = doctorService.list();
        return Result.success(doctors);
    }

    @GetMapping("/composingList/{id}")
    @ApiOperation(value = "查询方法",httpMethod = "POST",notes = "根据id查询当前医生的排版时间")
    @ApiImplicitParam(name = "id",value = "医生id",required = true)
    public Result composingList(@PathVariable("id") Integer id){

        List<Appointmenttime> appointmenttimeList = appointmenttimeService.list(new QueryWrapper<Appointmenttime>().eq("doctorid", id).eq("appstate",0));

        return Result.success(appointmenttimeList);
    }

    @GetMapping("/composingOne/{id}")
    @ApiOperation(value = "查询方法",httpMethod = "POST",notes = "根据id查询当前排版时间信息")
    @ApiImplicitParam(name = "id",value = "医生id",required = true)
    public Result composingOne(@PathVariable("id") Integer id){

        Appointmenttime appointmenttime = appointmenttimeService.getById(id);

        return Result.success(appointmenttime);
    }

}
