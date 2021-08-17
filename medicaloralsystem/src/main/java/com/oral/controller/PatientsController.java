package com.oral.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.*;
import com.oral.common.lang.Result;
import com.oral.common.vo.NewSurgery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/13 9:07
 * @Version 1.0
 */
@RestController
@RequestMapping("/patients")
@Api(value = "患者", description = "患者接口")
public class PatientsController extends BaseController {


    @GetMapping("/list")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "分页模糊查询")
    public Result list(String username) {
        System.out.println(username);
        Page<Patients> page = patientsService.page(getPage(), new QueryWrapper<Patients>().like(StrUtil.isNotBlank(username), "name", username).orderByAsc("pstate"));
        page.getRecords().forEach(p -> {
            p.setDoctorName(doctorService.getById(p.getDoctorid()).getName());
            p.setClinictime(appointmenttimeService.getById(p.getAtid()).getDatetime().toString());
        });

        return Result.success(page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据ID查询患者")
    @ApiImplicitParam(name = "patientsid", value = "患者ID", required = true)
    public Result info(@PathVariable("id") int id) {
        Patients patients = patientsService.getById(id);
        return Result.success(patients);
    }

    @PostMapping("/save")
    @Transactional
    @ApiOperation(value = "添加方法", httpMethod = "POST", notes = "添加患者")
    @ApiImplicitParam(name = "patients", value = "患者对象", required = true)
    public Result save(@RequestBody Patients patients) {
        patients.setPstate(0);
        patientsService.save(patients);
        //编辑排班时间状态
        appointmenttimeService.updateState(patients.getAtid());
        return Result.success(patients);

    }

    @GetMapping("/arrange/{clinictime}")
    @ApiOperation(value = "修改方法", httpMethod = "GET", notes = "根据时间给出当天的医生")
    @ApiImplicitParam(name = "clinictime", value = "时间属性", required = true)
    public Result arrange(@PathVariable String clinictime) {
        System.out.println(clinictime);
        List<Doctor> doctorList = patientsService.arrange(clinictime);
        return Result.success(doctorList);

    }


    @GetMapping("/scheduling")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据医生Id和选择的时间给出医生的排班时间")
    public Result scheduling(@RequestParam("id") Integer id, @RequestParam("clinictime") String clinictime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("clinictime", clinictime);
        List<Appointmenttime> appointmenttimeList = appointmenttimeService.onTheDayOfTheDate(map);
        return Result.success(appointmenttimeList);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改方法", httpMethod = "POST", notes = "修改患者")
    @ApiImplicitParam(name = "patients", value = "患者对象", required = true)
    @Transactional
    public Result update(@RequestBody Patients patients) {

        System.out.println("patients = " + patients);
        patientsService.updateById(patients);
        return Result.success(patients);
    }

    @PostMapping("/delete/{ids}")
    @ApiOperation(value = "删除方法", httpMethod = "POST", notes = "根据Id删除患者")
    @ApiImplicitParam(name = "id", value = "患者对象编号", required = true)
    @Transactional
    public Result delete(@PathVariable("ids") String ids) {

        String[] split = ids.split(":");
        patientsService.removeById(Integer.parseInt(split[0]));
        appointmenttimeService.updateStateTwo(Integer.parseInt(split[1]));

        return Result.success("");
    }


    @PostMapping("/undergoSurgery")
    @Transactional
    @ApiOperation(value = "添加方法", httpMethod = "POST", notes = "添加手术记录以及医疗记录")
    public Result undergoSurgery(@RequestBody NewSurgery newSurgery) {

        /**
         * 手术统计添加
         * 医疗费用添加
         */
        List<Operation> operationList = new ArrayList<>();

        List<Medicalrecord> medicalrecordList = new ArrayList<>();

        for (int i = 0; i < newSurgery.getIds().length; i++) {

            Operation operation = new Operation();
            operation.setDoctorid(newSurgery.getSurgeryDoctorid());
            operation.setPatientsid(newSurgery.getSurgeryPatientsid());
            operation.setOpsid(newSurgery.getIds()[i]);
            operationList.add(operation);

            Medicalrecord medicalrecord = new Medicalrecord();
            medicalrecord.setPatientsid(newSurgery.getSurgeryPatientsid());
            LocalDateTime dateTime = LocalDateTime.now();
            medicalrecord.setClinictime(dateTime);
            medicalrecord.setCount(1);
            medicalrecord.setDoctorid(newSurgery.getSurgeryDoctorid());
            medicalrecord.setDrugid(null);
            medicalrecord.setOpsid(newSurgery.getIds()[i]);
            medicalrecord.setCost(operationtypeService.getById(newSurgery.getIds()[i]).getOpsmoney());
            medicalrecordList.add(medicalrecord);

        }
        operationService.saveBatch(operationList);
        medicalrecordService.saveBatch(medicalrecordList);

        return Result.success("");
    }

    @PostMapping("/prescribe")
    @Transactional
    @ApiOperation(value = "添加方法", httpMethod = "POST", notes = "添加用药记录以及医疗记录")
    public Result prescribe(@RequestBody NewSurgery newSurgery) {

        List<Pharmacy> pharmacyList = new ArrayList<>();

        List<Medicalrecord> medicalrecordList = new ArrayList<>();

        for (int i = 0; i < newSurgery.getIds().length; i++) {

            //判断药品数量

            if (drugService.getById(newSurgery.getIds()[i]).getCount() == 0) {
                return Result.fail(400, "", CHECK_THE_CONTAIN_FAIL);
            } else {
                Pharmacy pharmacy = new Pharmacy();
                pharmacy.setDrugid(newSurgery.getIds()[i]);
                pharmacy.setPatientsid(newSurgery.getSurgeryPatientsid());
                pharmacy.setDoctorid(newSurgery.getSurgeryDoctorid());
                pharmacyList.add(pharmacy);

                Medicalrecord medicalrecord = new Medicalrecord();

                medicalrecord.setPatientsid(newSurgery.getSurgeryPatientsid());
                LocalDateTime dateTime = LocalDateTime.now();
                medicalrecord.setClinictime(dateTime);
                medicalrecord.setCount(1);
                medicalrecord.setDoctorid(newSurgery.getSurgeryDoctorid());
                medicalrecord.setDrugid(newSurgery.getIds()[i]);
                medicalrecord.setOpsid(null);
                medicalrecord.setCost(Integer.parseInt(drugService.getById(newSurgery.getIds()[i]).getPrice().toString()));

                medicalrecordList.add(medicalrecord);
                drugService.updateCount(newSurgery.getIds()[i]);
            }

        }
        pharmacyService.saveBatch(pharmacyList);
        medicalrecordService.saveBatch(medicalrecordList);
        return Result.success("");


    }


    @GetMapping("/operationList")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "获取所有手术类型")
    public Result operationList(String opsname){
        List<Operationtype> operationtypeList = operationtypeService.list(new QueryWrapper<Operationtype>().like("opsname", opsname));
        return Result.success(operationtypeList);
    }

    @GetMapping("/drugList")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "获取所有药品信息")
    public Result drugList(String name){
        List<Drug> list = drugService.list(new QueryWrapper<Drug>().like("name", name));
        return Result.success(list);
    }

}
