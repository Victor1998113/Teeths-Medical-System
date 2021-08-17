package com.oral.service;

import com.oral.bean.Authority;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oral.common.vo.SysMenuDto;

import java.util.List;

/**
 *
 */
public interface AuthorityService extends IService<Authority> {

    List<SysMenuDto> getCurrenUserNav();

    List<Authority> tree();
}
