package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Operation;
import com.oral.mapper.OperationMapper;
import com.oral.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation>
        implements OperationService {

    @Autowired
    private OperationMapper operationMapper;

    @Override
    public Page<Operation> selectByDoctorName(Page<Operation> page, String name) {
        return page.setRecords(operationMapper.selectByDoctorName(page, name));

    }
}




