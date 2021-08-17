package com.oral.service;

import com.oral.bean.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface MemberService extends IService<Member> {
    /**
     * 修充值金额; 同时新增支付记录
     * @param
     * @param memberid
     * @param mmoney
     */
    void updateMemberStatus(String memberid, String paystate,Double mmoney);
}
