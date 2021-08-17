package com.oral.mapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Appointmenttime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Entity com.oral.bean.Appointmenttime
 */
@Mapper
public interface AppointmenttimeMapper extends BaseMapper<Appointmenttime> {

    List<Appointmenttime> onTheDayOfTheDate(Map<String, Object> map);

    void updateState(Integer atid);

    void updateStateTwo(Integer atid);

    List<Appointmenttime> listdoctor(Page<Appointmenttime> page, @Param("name")String name);
}