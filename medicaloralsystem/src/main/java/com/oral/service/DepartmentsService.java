package com.oral.service;

import com.oral.bean.Departments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oral.bean.Position;

import java.util.List;

/**
 *
 */
public interface DepartmentsService extends IService<Departments> {

     String DepartmentsNameByUserId(long id);

}
