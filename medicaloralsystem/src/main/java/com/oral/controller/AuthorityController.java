package com.oral.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oral.bean.Authority;
import com.oral.bean.Doctor;
import com.oral.bean.Roleauthority;
import com.oral.common.lang.Result;
import com.oral.common.vo.SysMenuDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/6 20:34
 * @Version 1.0
 *
 *
 */
@RestController
@RequestMapping("/sys/menu")
@Api(value = "菜单项",description = "菜单项接口")
public class AuthorityController extends BaseController {


    @GetMapping("/nav")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "查询当前用户的菜单项和权限信息")
    public Result nav(Principal principal){
        System.out.println("进来了==============");

        Doctor doctor = doctorService.getByUsername(principal.getName());
        //获取权限信息
        String authority = doctorService.getUserAuthorityInfo((long) doctor.getDoctorid());
        String[] authorityArray = StringUtils.tokenizeToStringArray(authority,",");

        //获取导航栏信息
        List<SysMenuDto> navs = authorityService.getCurrenUserNav();

        return Result.success(MapUtil.builder()
                .put("authoritys",authorityArray)
                .put("nav",navs)
                .map()
        );

    }

    @PreAuthorize("hasAnyAuthority('sys:system:list')")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "根据id查询当前单个菜单项")
    @ApiImplicitParam(name = "id",value = "菜单编号",required = true)
    @GetMapping("/info/{id}")
    public Result info(@PathVariable(name = "id") Long id){
        return Result.success(authorityService.getById(id));
    }


    @PreAuthorize("hasAnyAuthority('sys:system:list')")
    @ApiOperation(value = "查询方法",httpMethod = "GET",notes = "查询当前树状数据菜单")
    @GetMapping("/list")
    public Result list(){
        List<Authority> menus = authorityService.tree();
        return Result.success(menus);
    }

    @PreAuthorize("hasAnyAuthority('sys:system:save')")
    @ApiOperation(value = "添加方法",httpMethod = "POST",notes = "添加菜单项")
    @ApiImplicitParam(name = "Authority",value = "菜单对象",required = true)
    @PostMapping("/save")
    public Result save(@Validated @RequestBody Authority authority){
        authorityService.save(authority);
        // 清除所有与该菜单相关的权限缓存
        doctorService.clearUserAuthorityInfoMenuId((long)authority.getAuthorityid());
        return Result.success(authority);
    }

    @PreAuthorize("hasAnyAuthority('sys:system:update')")
    @ApiOperation(value = "修改方法",httpMethod = "POST",notes = "根据id修改菜单项")
    @ApiImplicitParam(name = "Authority",value = "菜单对象",required = true)
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Authority authority){
        authorityService.updateById(authority);

        // 清除所有与该菜单相关的权限缓存
        doctorService.clearUserAuthorityInfoMenuId((long)authority.getAuthorityid());

        return Result.success(authority);
    }

    @PreAuthorize("hasAnyAuthority('sys:system:delete')")
    @ApiOperation(value = "删除方法",httpMethod = "POST",notes = "根据id删除菜单项")
    @ApiImplicitParam(name = "id",value = "菜单id",required = true)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id){

        int count = authorityService.count(new QueryWrapper<Authority>().eq("parentid", id));

        if(count>0){
            return Result.fail("请先删除子菜单");
        }
        // 清除所有与该菜单相关的权限缓存
        doctorService.clearUserAuthorityInfoMenuId(id);

        authorityService.removeById(id);

        //删除中间关联信息表
        roleauthorityService.remove(new QueryWrapper<Roleauthority>().eq("authorityid",id));

        return Result.success("");
    }

}
