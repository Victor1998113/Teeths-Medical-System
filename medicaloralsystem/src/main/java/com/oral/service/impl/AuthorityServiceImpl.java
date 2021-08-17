package com.oral.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Authority;
import com.oral.bean.Doctor;
import com.oral.common.vo.SysMenuDto;
import com.oral.controller.BaseController;
import com.oral.mapper.DoctorMapper;
import com.oral.service.AuthorityService;
import com.oral.mapper.AuthorityMapper;
import com.oral.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 *
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
implements AuthorityService{

    @Autowired
    DoctorService doctorService;

    @Autowired
    DoctorMapper doctorMapper;

    @Override
    public List<SysMenuDto> getCurrenUserNav() {

        //获取用户信息
        String username =(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Doctor doctor = doctorService.getByUsername(username);

        List<Long> navMenuIds = doctorMapper.getNavMenuIds((long) doctor.getDoctorid());

        List<Authority> menus = this.listByIds(navMenuIds);

        //转树状结构
        List<Authority> menuTree = buildTreeMenu(menus);

        //实体转DTO
        return convert(menuTree);
    }

    @Override
    public List<Authority> tree() {
        //获取所有菜单信息
        List<Authority> menus = this.list(new QueryWrapper<Authority>().orderByAsc("orderNum"));
        //转成树状结构
        return buildTreeMenu(menus);
    }

    private List<SysMenuDto> convert(List<Authority> menuTree) {

        List<SysMenuDto> menuDtos = new ArrayList<>();
        menuTree.forEach(m ->{
            SysMenuDto dto = new SysMenuDto();
            dto.setAuthorityid((long)m.getAuthorityid());
            dto.setName(m.getPerms());
            dto.setIcon(m.getIcon());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());

            if(m.getChildren().size()>0){
                //子节点调用当前方法进行再次转换  递归
                dto.setChildren(convert(m.getChildren()));
            }
            menuDtos.add(dto);
        });

        return  menuDtos;
    }

    private List<Authority> buildTreeMenu(List<Authority> menus) {

        List<Authority> finalMenus = new ArrayList<>();

        /**
         * 当前节点跟节点对比，如果相等
         * 说明第二循环是一个循环的子节点
         * 然后可以添加到children
         */
        for(Authority menu :menus){
            for(Authority e :menus){
                if(menu.getAuthorityid()==(long) e.getParentid()){
                    menu.getChildren().add(e);
                }
            }
            /**
             * 判断当前是否是父节点
             */
            if(menu.getParentid() == 0L){
                finalMenus.add(menu);
            }
        }
        System.out.println(JSONUtil.toJsonStr(finalMenus));
        return finalMenus;
    }
}




