package com.oral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Operation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.oral.bean.Operation
 */
@Mapper
public interface OperationMapper extends BaseMapper<Operation> {

    List<Operation> selectByDoctorName(Page<Operation> page, @Param("name") String name);
}




