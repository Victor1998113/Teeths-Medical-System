package com.oral.security;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oral.bean.Doctor;
import com.oral.bean.Position;
import com.oral.controller.BaseController;
import com.oral.service.DoctorService;
import com.oral.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl extends BaseController implements UserDetailsService {

	@Autowired
	DoctorService doctorService;

	@Autowired
	HttpServletRequest request;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Doctor doctor = doctorService.getByUsername(username);

		if (doctor == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		return new AccountUser((long)doctor.getDoctorid(), doctor.getUsername(), doctor.getPassword(), getUserAuthority((long)doctor.getDoctorid()));
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(Long userId){

		String authorityInfo = doctorService.getUserAuthorityInfo(userId);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorityInfo);
	}
}
