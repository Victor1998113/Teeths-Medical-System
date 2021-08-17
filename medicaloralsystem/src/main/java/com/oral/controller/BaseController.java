package com.oral.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Calculateexpense;
import com.oral.bean.Casehistory;
import com.oral.bean.Orderdetails;
import com.oral.bean.Patients;
import com.oral.common.lang.Const;
import com.oral.service.*;
import com.oral.utils.RedisUtil;
import com.oral.utils.SnowflakeIdWorker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tanyongpeng
 * @Date 2021/7/6 20:38
 * @Version 1.0
 */

public class BaseController extends Const {

    @Autowired
    HttpServletRequest request;

    private boolean state;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PositionService positionService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    DoctorpositionService doctorpositionService;

    @Autowired
    RoleauthorityService roleauthorityService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    DepartmentsService departmentsService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AppointmenttimeService appointmenttimeService;

    @Autowired
    PatientsService patientsService;

    @Autowired
    MedicalrecordService medicalrecordService;

    @Autowired
    OperationtypeService operationtypeService;

    @Autowired
    OrderdetailsService orderdetailsService;

    @Autowired
    OperationService operationService;

    @Autowired
    DrugService drugService;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    MemberService memberService;

    @Autowired
    DetailedcostService detailedcostService;

    @Autowired
    CasehistoryService casehistoryService;

    @Autowired
    CalculateexpenseService calculateexpenseService;


    /**
     * 获取页码
     *
     * @return
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 5);
        return new Page(current, size);
    }

    /**
     * 支付宝返回结果
     * @param out_trade_no
     * @param payamount
     * @param payment
     * @param des
     * @param alipayRequest
     * @param alipayClient
     * @return
     */
    public String paySelect(String out_trade_no, Integer payamount, String payment, String des, AlipayTradePagePayRequest alipayRequest, AlipayClient alipayClient){
        String result = null;

        Integer total_amount = payamount;
        //订单名称，必填
        String subject = payment;
        //商品描述，可空
        String body = des;

        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加订单
     * 修改病人状态
     * 保存病例
     * @param orderdetails
     * @param state
     */
    @Transactional
    public void updatePatientsAndSaveCasehistory(Orderdetails orderdetails,boolean state){
        //添加订单
        Orderdetails order = orderdetails;
        order.setOrderstate(20);
        LocalDateTime localDateTime =  LocalDateTime.now();
        order.setPaytime(localDateTime);
        if(state){
            order.setPayment("会员扣费(优惠9折)");
        }
        order.setOrderid(SnowflakeIdWorker.generateOrderId());
        orderdetailsService.save(order);

        //修改病人状态
        Patients patients = patientsService.getById(order.getPatientsid());
        patients.setPstate(1);
        patientsService.updateById(patients);

        //保存病例
        Casehistory casehistory = new Casehistory();
        casehistory.setPatientsid(patients.getPatientsid());
        casehistory.setAge(patients.getAge());
        casehistory.setGender(patients.getGender());
        casehistory.setClinictime(patients.getClinictime());

        casehistoryService.save(casehistory);
    }

    /**
     * 获取桌面路径
     * @return
     */
    public String getPath(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
        return com.getPath();
    }

}
