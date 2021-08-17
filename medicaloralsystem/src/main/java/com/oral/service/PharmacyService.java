package com.oral.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Pharmacy;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface PharmacyService extends IService<Pharmacy> {

    Page<Pharmacy> selectAllByPatientsName(Page<Pharmacy> page, @Param("name") String name);
}
