package com.oral.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oral.bean.Operation;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface OperationService extends IService<Operation> {
    Page<Operation> selectByDoctorName(Page<Operation> page, @Param("name") String name);
}
