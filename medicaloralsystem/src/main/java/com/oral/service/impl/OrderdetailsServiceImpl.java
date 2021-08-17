package com.oral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oral.bean.Medical;
import com.oral.bean.Medicalrecord;
import com.oral.bean.Orderdetails;
import com.oral.mapper.MedicalrecordMapper;
import com.oral.service.OrderdetailsService;
import com.oral.mapper.OrderdetailsMapper;
import com.oral.utils.Join;
import com.oral.utils.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *
 */
@Service
public class OrderdetailsServiceImpl extends ServiceImpl<OrderdetailsMapper, Orderdetails>
implements OrderdetailsService{

    @Autowired
    MedicalrecordMapper medicalrecordMapper;
    @Autowired
    OrderdetailsService orderdetailsService;
    /**
     * 修改订单状态，改为 支付成功，已付款; 同时新增医疗费用订单详细
     *
     * @param patientsid
     * @param orderstate
     * @param payamount
     */
    @Override
    public void updateMedicalStatus(String orderid, String orderstate, String payamount) {
        Orderdetails orderdetails = getById(orderid);
        orderdetails = new Orderdetails();
        orderdetails.setPatientsid(Integer.valueOf(orderid));
        LocalDateTime dateTime = LocalDateTime.now();
        orderdetails.setPaytime(dateTime);
        //修改医疗费用记录
        orderdetailsService.updateById(orderdetails);

        //修改传过来的值
        Medical medical = new Medical();
        //根据medicalrecord查询医疗费用订单
        orderdetails = getById(orderid);
        String orderid2 = Join.createOrder("ALS");
//        Orderdetails orderdetails = new Orderdetails();
        orderdetails.setOrderid(orderid2);
        orderdetails.setPatientsid(Integer.valueOf(medical.getPatientsid()));
        orderdetails.setCount(medical.getCount());
        LocalDateTime dateTime2 = LocalDateTime.now();
        orderdetails.setPaytime(dateTime2);
        orderdetails.setPayment(String.valueOf(medical.getPayment()));
        orderdetails.setPayamount(medical.getPayamount());
        orderdetails.setOrderstate(Integer.valueOf(OrderStatusEnum.PAID.key));
        //添加医疗费用详细
        orderdetailsService.save(orderdetails);
    }
}




