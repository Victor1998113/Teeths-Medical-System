package com.oral.service;

import com.oral.bean.Drug;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface DrugService extends IService<Drug> {

    void updateCount(Integer id);
}
