package com.oral.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oral.bean.Casehistory;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 *
 */
public interface CasehistoryService extends IService<Casehistory> {


    Page<Casehistory> selectBypaName(Page<Casehistory> page, String name);

    List<Casehistory> selectAll();

    Casehistory selectBypaId(Integer Id);

}
