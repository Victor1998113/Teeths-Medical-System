package com.oral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Casehistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.oral.bean.Casehistory
 */
@Mapper
public interface CasehistoryMapper extends BaseMapper<Casehistory> {


    List<Casehistory> selectBypaName(Page<Casehistory> page, @Param("name") String name);

    List<Casehistory> selectALL();


    Casehistory selectBypaId(@Param("Id") Integer id);
}




