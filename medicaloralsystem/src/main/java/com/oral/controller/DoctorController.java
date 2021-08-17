package com.oral.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Doctor;
import com.oral.bean.Doctorposition;
import com.oral.bean.Position;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/7 9:45
 * @Version 1.0
 */
@RequestMapping("/sys")
@RestController
@Api(value = "医生(用户)",description = "医生(用户)接口")
public class DoctorController extends BaseController {


    @GetMapping("/userInfo")
    @ApiOperation(value = "获取用户",httpMethod = "GET",notes = "获取当前用户的信息")
    public Result userInfo(Principal principal){
        Doctor doctor = doctorService.getByUsername(principal.getName());
        return Result.success(MapUtil.builder()
        .put("id",doctor.getDoctorid())
        .put("username",doctor.getUsername()).map());
    }


    @PreAuthorize("hasAnyAuthority('sys:doctorList:list')")
    @GetMapping("/user/info/{id}")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据id获取当前用户信息")
    @ApiImplicitParam(name = "id",value = "用户编号",required = true)
    public Result info(@PathVariable("id") long id){

        Doctor doctor = doctorService.getById(id);

        Assert.notNull(doctor,"找不到该管理员");

        List<Position> roles = positionService.listRolesByUserId(id);

        doctor.setRoles(roles);

        return Result.success(doctor);
    }

    @PreAuthorize("hasAnyAuthority('sys:doctorList:list')")
    @GetMapping("/user/list")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据用户名进行模糊查询并分页")
    @ApiImplicitParam(name = "username",value = "用户编号",required = false)
    public Result list(String username){

        Page<Doctor> page = doctorService.page(getPage(), new QueryWrapper<Doctor>().like(StrUtil.isNotBlank(username), "name", username));

        page.getRecords().forEach(u -> {
            u.setRoles(positionService.listRolesByUserId(u.getDoctorid()));
        });

        return Result.success(page);
    }

    @PreAuthorize("hasAnyAuthority('sys:doctorList:save')")
    @PostMapping("/user/save")
    @ApiOperation(value = "添加方法",httpMethod = "POST",notes = "添加用户")
    @ApiImplicitParam(name = "doctor",value = "医生对象",required = true)
    public Result save(@Validated @RequestBody Doctor doctor){
        doctor.setStatu(STATUS_ON);
        doctor.setPassword(DEFAULT_PASSWORD);
        doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        doctorService.save(doctor);
        return Result.success(doctor);
    }

    @PreAuthorize("hasAnyAuthority('sys:doctorList:update')")
    @PostMapping("/user/update")
    @ApiOperation(value = "修改方法",httpMethod = "POST",notes = "修改用户")
    @ApiImplicitParam(name = "doctor",value = "医生对象",required = true)
    public Result update(@Validated @RequestBody Doctor doctor){
        if("".equals(doctor.getPassword())||null==doctor.getPassword()){
            Doctor newDoctor = doctorService.getById(doctor.getDoctorid());
            doctor.setPassword(newDoctor.getPassword());
        }else{
            doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        }
        doctorService.updateById(doctor);
        return Result.success(doctor);
    }

    @PreAuthorize("hasAnyAuthority('sys:doctorList:delete')")
    @Transactional
    @PostMapping("/user/delete")
    @ApiOperation(value = "删除方法",httpMethod = "POST",notes = "删除用户")
    @ApiImplicitParam(name = "ids",value = "当个删除，或者删除多个",required = true)
    public Result delete(@RequestBody Long[] ids){

        doctorService.removeByIds(Arrays.asList(ids));

        doctorpositionService.remove(new QueryWrapper<Doctorposition>().in("doctorid",ids));

        return Result.success("");
    }


    @PreAuthorize("hasAnyAuthority('sys:doctorList:roles')")
    @Transactional
    @PostMapping("/user/role/{userId}")
    @ApiOperation(value = "修改方法",httpMethod = "POST",notes = "分配权限")
    public Result rolePerm(@PathVariable("userId") Integer userId, @RequestBody Long[] roleIds){

        List<Doctorposition> userRoles = new ArrayList<>();


        Arrays.stream(roleIds).forEach(r -> {
            Doctorposition doctorposition = new Doctorposition();

            doctorposition.setPositionid(Math.toIntExact(r));

            doctorposition.setDoctorid(userId);

            userRoles.add(doctorposition);
        });

        doctorpositionService.remove(new QueryWrapper<Doctorposition>().eq("doctorid",userId));

        doctorpositionService.saveBatch(userRoles);

        //删除缓存

        Doctor doctor = doctorService.getById(userId);
        doctorService.clearUserAuthorityInfo(doctor.getUsername());
        return Result.success("");
    }

}
