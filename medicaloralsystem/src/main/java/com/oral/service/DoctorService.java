package com.oral.service;

import com.oral.bean.Doctor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DoctorService extends IService<Doctor> {

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoMenuId(Long menuId);

    Doctor getByUsername(String username);

}
