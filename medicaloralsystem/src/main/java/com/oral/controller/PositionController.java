package com.oral.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Doctorposition;
import com.oral.bean.Position;
import com.oral.bean.Roleauthority;
import com.oral.common.lang.Const;
import com.oral.common.lang.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanyongpeng
 * @Date 2021/7/7 14:55
 * @Version 1.0
 *
 *
 */
@RestController
@RequestMapping("/sys/role")
@Api(value = "职位(角色)",description = "职位(角色)接口")
public class PositionController extends BaseController {

    @PreAuthorize("hasAnyAuthority('sys:position:list')")
    @GetMapping("/info/{id}")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据id获取当前角色的信息")
    @ApiImplicitParam(name = "id",value = "角色编号",required = true)
    public Result info(@PathVariable Long id){

        Position position = positionService.getById(id);

        //获取角色相关练的菜单id
        List<Roleauthority> roleauthorities = roleauthorityService.list(new QueryWrapper<Roleauthority>().eq("positionid", id));
        List<Integer> menuIds = roleauthorities.stream().map(p -> p.getAuthorityid()).collect(Collectors.toList());

        position.setMenuIds(menuIds);

        return Result.success(position);
    }
    @PreAuthorize("hasAnyAuthority('sys:position:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据名称进行模糊查询所有角色")
    @ApiImplicitParam(name = "name",value = "角色名称",required = false)
    public Result list(String name){

        Page<Position> pageData = positionService.page(getPage(),
                new QueryWrapper<Position>()
                        .like(StrUtil.isNotBlank(name), "job", name)
        );
        return Result.success(pageData);
    }

    @PreAuthorize("hasAnyAuthority('sys:position:save')")
    @ApiOperation(value = "添加方法",httpMethod = "POST",notes = "添加角色")
    @ApiImplicitParam(name = "position",value = "角色对象",required = true)
    @PostMapping("/save")
    public Result save(@Validated @RequestBody Position position){
        position.setPstate(Const.STATUS_ON);
        positionService.save(position);
        return Result.success(position);
    }

    @PreAuthorize("hasAnyAuthority('sys:position:update')")
    @ApiOperation(value = "修改方法",httpMethod = "POST",notes = "修改角色")
    @ApiImplicitParam(name = "position",value = "角色对象",required = true)
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Position position){

        positionService.updateById(position);

        //更新缓存
        doctorService.clearUserAuthorityInfoByRoleId((long)position.getPositionid());

        return Result.success(position);
    }

    @PreAuthorize("hasAnyAuthority('sys:position:delete')")
    @ApiOperation(value = "删除方法",httpMethod = "POST",notes = "删除角色")
    @ApiImplicitParam(name = "menuIds",value = "单个id，或者多个id",required = true)
    @PostMapping("/delete")
    @Transactional
    public Result delete(@RequestBody Long[] ids){

        System.out.println("ids = ==============================" + ids);

        positionService.removeByIds(Arrays.asList(ids));
        //级联删除
        doctorpositionService.remove(new QueryWrapper<Doctorposition>().in("positionid",ids));
        roleauthorityService.remove(new QueryWrapper<Roleauthority>().in("positionid",ids));

        //缓存同步删除
        Arrays.stream(ids).forEach(id -> {
            //更新缓存
            doctorService.clearUserAuthorityInfoByRoleId(id);

        });
        return Result.success("");
    }


    @PreAuthorize("hasAnyAuthority('sys:position:authority')")
    @Transactional
    @ApiOperation(value = "修改方法",httpMethod = "POST",notes = "分配权限")
    @PostMapping("/perm/{roleId}")
    public Result perm(@PathVariable(name = "roleId") Integer roleId,@RequestBody Long[] menuIds){

        List<Roleauthority> arrayList = new ArrayList<>();

        Arrays.stream(menuIds).forEach(menuId -> {
            Roleauthority roleauthority = new Roleauthority();
            roleauthority.setAuthorityid(Math.toIntExact(menuId));
            roleauthority.setPositionid(roleId);

            arrayList.add(roleauthority);

        });

        roleauthorityService.remove(new QueryWrapper<Roleauthority>().eq("positionid", roleId));
        roleauthorityService.saveBatch(arrayList);


        //删除缓存
        doctorService.clearUserAuthorityInfoByRoleId((long)roleId);

        return Result.success(menuIds);
    }

}
