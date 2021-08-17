package com.oral.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oral.bean.Appointmenttime;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface AppointmenttimeService extends IService<Appointmenttime> {


    List<Appointmenttime> onTheDayOfTheDate(Map<String, Object> map);

    void updateState(Integer atid);

    void updateStateTwo(Integer atid);


    public Page<Appointmenttime> listdoctor(Page<Appointmenttime> page, @Param("name") String name);
}