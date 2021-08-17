package com.oral.mapper;

import com.oral.bean.Drug;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.oral.bean.Drug
 */
public interface DrugMapper extends BaseMapper<Drug> {

    void updateCount(Integer id);
}




