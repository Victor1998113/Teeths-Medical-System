package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Member;
import com.oral.bean.Statistics;
import com.oral.service.MemberService;
import com.oral.mapper.MemberMapper;
import com.oral.service.StatisticsService;
import com.oral.utils.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
implements MemberService{

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private StatisticsService statisticsService;
    /**
     * 修充值状态，改为 支付成功，已付款; 同时新增支付记录
     *  @param
//     * @param memberid
     * morderid
     * @param mmoney
     */
    @Override
    public void updateMemberStatus(String memberid,String paystate, Double mmoney) {

        /**
         * 1,获取前台传过来的对象，
         * 2，通过当前对象的id去数据库查询出它的余额，
         * 3，查询余额后，加上前台输入的余额
         * 4，相加后，根据当前id对数据余额进行修改
         * 5，生成订单，余额为，前台输入的余额
         */
        Member member = getById(memberid);
        Double mong = member.getMmoney() + mmoney;
        member = new Member();
        member.setMemberid(Integer.valueOf(memberid));
        //修改金额
        member.setMmoney(mong);
        memberMapper.updateById(member);
        //根据memberid查询充值统计
        member = getById(memberid);
        String staid = Join.createOrder("ALS");
        Statistics statistics = new Statistics();
        statistics.setStaid(staid);
        LocalDateTime dateTime = LocalDateTime.now();
        statistics.setPaydate(dateTime);
        //添加输入的余额
        statistics.setPaymoney(mmoney);
        statistics.setMemberid(Integer.valueOf(member.getMemberid()));
        //新增加支付记录
        statisticsService.save(statistics);
    }
}




