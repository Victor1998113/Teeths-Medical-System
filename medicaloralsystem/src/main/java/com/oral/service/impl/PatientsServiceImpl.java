package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Doctor;
import com.oral.bean.Patients;
import com.oral.service.PatientsService;
import com.oral.mapper.PatientsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class PatientsServiceImpl extends ServiceImpl<PatientsMapper, Patients>
implements PatientsService{

    @Autowired
    PatientsMapper patientsMapper;

    @Override
    public List<Doctor> arrange(String clinictime) {
        return patientsMapper.arrange(clinictime);
    }
}




