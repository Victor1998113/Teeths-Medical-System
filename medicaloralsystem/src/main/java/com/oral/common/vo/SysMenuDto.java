package com.oral.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {
 * 					name: 'SysUser',
 * 					title: '用户管理',
 * 					icon: 'el-icon-s-custom',
 * 					path: '/sys/users',
 * 					component: 'sys/User',
 * 					children: []
 *                                },
 *
 *                                返回前端菜单项实体类
 */
@Data
public class SysMenuDto implements Serializable {

	private Long authorityid;
	private String name;
	private String title;
	private String icon;
	private String path;
	private String component;
	private List<SysMenuDto> children = new ArrayList<>();

}
