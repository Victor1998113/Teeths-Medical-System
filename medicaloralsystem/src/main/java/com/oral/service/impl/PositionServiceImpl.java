package com.oral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Position;
import com.oral.service.PositionService;
import com.oral.mapper.PositionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position>
implements PositionService{

    @Override
    public List<Position> listRolesByUserId(long id) {
        List<Position> positionList = this.list(new QueryWrapper<Position>().inSql("positionid"
                , "select positionid from doctorposition where doctorid = " + id));
        return positionList;
    }


}




