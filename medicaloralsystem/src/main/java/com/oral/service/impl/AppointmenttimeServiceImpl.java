package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Appointmenttime;
import com.oral.service.AppointmenttimeService;
import com.oral.mapper.AppointmenttimeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class AppointmenttimeServiceImpl extends ServiceImpl<AppointmenttimeMapper, Appointmenttime>
        implements AppointmenttimeService {


    @Resource
    AppointmenttimeMapper appointmenttimeMapper;

    @Override
    public List<Appointmenttime> onTheDayOfTheDate(Map<String, Object> map) {
        return appointmenttimeMapper.onTheDayOfTheDate(map);
    }

    @Override
    public void updateState(Integer atid) {
        appointmenttimeMapper.updateState(atid);
    }

    @Override
    public void updateStateTwo(Integer atid) {
        appointmenttimeMapper.updateStateTwo(atid);
    }

    @Override
    public Page<Appointmenttime> listdoctor(Page<Appointmenttime> page, String name) {
        return page.setRecords(appointmenttimeMapper.listdoctor(page, name));
    }


}


