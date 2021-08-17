package com.oral.service;

import com.oral.bean.Doctor;
import com.oral.bean.Patients;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface PatientsService extends IService<Patients> {

    List<Doctor> arrange(String clinictime);
}
