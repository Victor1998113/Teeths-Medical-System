package com.oral.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Pharmacy;
import com.oral.mapper.PharmacyMapper;
import com.oral.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class PharmacyServiceImpl extends ServiceImpl<PharmacyMapper, Pharmacy>
        implements PharmacyService {

    @Autowired
    private PharmacyMapper pharmacyMapper;


    @Override
    public Page<Pharmacy> selectAllByPatientsName(Page<Pharmacy> page, String name) {
        return page.setRecords(pharmacyMapper.selectAllByPatientsName(page,name));
    }
}




