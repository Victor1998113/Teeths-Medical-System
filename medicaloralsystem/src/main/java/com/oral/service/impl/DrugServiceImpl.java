package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Drug;
import com.oral.service.DrugService;
import com.oral.mapper.DrugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 *
 */
@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug>
implements DrugService{

    @Autowired
    DrugMapper drugMapper;

    @Override
    public void updateCount(Integer id) {
        drugMapper.updateCount(id);
    }
}




