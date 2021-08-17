package com.oral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Departments;
import com.oral.bean.Position;
import com.oral.service.DepartmentsService;
import com.oral.mapper.DepartmentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments>
implements DepartmentsService{


    @Override
    public String DepartmentsNameByUserId(long id) {
        Departments departments = this.getOne(new QueryWrapper<Departments>().inSql("departmentsid"
                , "select departmentsid from doctor where doctorid = " + id));
        return departments.getDename();
    }
}




