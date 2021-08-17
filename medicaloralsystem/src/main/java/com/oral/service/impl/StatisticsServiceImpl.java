package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Statistics;
import com.oral.service.StatisticsService;
import com.oral.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics>
implements StatisticsService{

    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public Long total() {
        return statisticsMapper.total();
    }
}




