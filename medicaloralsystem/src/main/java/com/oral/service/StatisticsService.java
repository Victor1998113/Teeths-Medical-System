package com.oral.service;

import com.oral.bean.Statistics;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface StatisticsService extends IService<Statistics> {

    Long total();
}
