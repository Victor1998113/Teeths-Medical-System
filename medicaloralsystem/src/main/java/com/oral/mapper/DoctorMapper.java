package com.oral.mapper;
import org.apache.ibatis.annotations.Param;

import com.oral.bean.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity com.oral.bean.Doctor
 */
@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {

    List<Long> getNavMenuIds(Long doctorid);

    List<Doctor> listByMenuId(Long menuId);

    Doctor selectAllByDoctorid(@Param("doctorid") Integer doctorid);
}