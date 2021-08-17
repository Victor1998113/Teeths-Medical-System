package com.oral.service;

import com.oral.bean.Position;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface PositionService extends IService<Position> {


    List<Position> listRolesByUserId(long id);


}
