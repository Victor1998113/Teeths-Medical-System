package com.oral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Authority;
import com.oral.bean.Doctor;
import com.oral.bean.Position;
import com.oral.service.AuthorityService;
import com.oral.service.DoctorService;
import com.oral.mapper.DoctorMapper;
import com.oral.service.PositionService;
import com.oral.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor>
implements DoctorService{

    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    PositionService positionService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String getUserAuthorityInfo(Long userId) {


        Doctor doctor = doctorMapper.selectById(userId);

        String authority = "";

        if(redisUtil.hasKey("GrantedAuthority:"+doctor.getUsername())){
           authority = (String) redisUtil.get("GrantedAuthority:"+doctor.getUsername());
        }else{

            //获取角色
            List<Position> roles = positionService.list(new QueryWrapper<Position>().inSql("positionid", "select positionid from doctorposition where doctorid =" + userId));

            if(roles.size()>0){
                String  roleCodes = roles.stream().map(r -> "ROLE_" + r.getPosicode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }

            //获取菜单操作编码
            List<Long> MenuIds = doctorMapper.getNavMenuIds(userId);
            if(MenuIds.size()>0){

                List<Authority> authorities = authorityService.listByIds(MenuIds);
                String menuPerms = authorities.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
                authority = authority.concat(menuPerms);
            }

            redisUtil.set("GrantedAuthority:"+doctor.getUsername(),authority,60*60);

        }

        return authority;
    }


    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:"+username);
    }


    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {

        List<Doctor> doctorList = this.list(new QueryWrapper<Doctor>().inSql("doctorid", "select doctorid from doctorposition where positionid =" + roleId));

        doctorList.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoMenuId(Long menuId) {

        List<Doctor> doctorList = doctorMapper.listByMenuId(menuId);
        doctorList.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public Doctor getByUsername(String username) {
        return getOne(new QueryWrapper<Doctor>().eq("username", username));
    }


}