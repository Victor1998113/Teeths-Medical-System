package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Casehistory;
import com.oral.mapper.CasehistoryMapper;
import com.oral.service.CasehistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CasehistoryServiceImpl extends ServiceImpl<CasehistoryMapper, Casehistory>
        implements CasehistoryService {

    @Autowired
    private CasehistoryMapper casehistoryMapper;


    public Page<Casehistory> selectBypaName(Page<Casehistory> page, String name) {
        return page.setRecords(casehistoryMapper.selectBypaName(page, name));
    }

    @Override
    public List<Casehistory> selectAll() {
        return casehistoryMapper.selectALL();
    }

    @Override
    public Casehistory selectBypaId(Integer Id) {
        return casehistoryMapper.selectBypaId(Id);
    }



}




