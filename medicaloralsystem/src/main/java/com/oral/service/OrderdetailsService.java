package com.oral.service;

import com.oral.bean.Orderdetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface OrderdetailsService extends IService<Orderdetails> {
    /**
     *  修改订单状态，改为 支付成功，已付款; 同时新增医疗费用订单详细
     * @param
     * @param
     * @param
     */
    void updateMedicalStatus(String medicalRecordid, String orderstate,String payamount);
}
