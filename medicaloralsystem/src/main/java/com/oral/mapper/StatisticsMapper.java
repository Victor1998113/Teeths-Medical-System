package com.oral.mapper;

import com.oral.bean.Statistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.oral.bean.Statistics
 */
public interface StatisticsMapper extends BaseMapper<Statistics> {


    Long total();
}




