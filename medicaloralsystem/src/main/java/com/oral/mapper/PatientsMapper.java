package com.oral.mapper;

import com.oral.bean.Doctor;
import com.oral.bean.Patients;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity com.oral.bean.Patients
 */
public interface PatientsMapper extends BaseMapper<Patients> {

    List<Doctor> arrange(String clinictime);

}




