package com.oral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Pharmacy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.oral.bean.Pharmacy
 */
@Mapper
public interface PharmacyMapper extends BaseMapper<Pharmacy> {

    List<Pharmacy> selectAllByPatientsName(Page<Pharmacy> page, @Param("name") String name);

}




