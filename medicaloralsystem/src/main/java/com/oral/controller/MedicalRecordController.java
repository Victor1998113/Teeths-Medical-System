package com.oral.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oral.bean.*;
import com.oral.common.lang.Result;
import com.oral.utils.AppUtil;
import com.oral.utils.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author tanyongpeng
 * @Date 2021/7/15 22:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/sys/medical")
@Api(value = "费用列表", description = "费用接口")
// @SessionAttributes(types = {Orderdetails.class})
public class MedicalRecordController extends BaseController {

    private Orderdetails order;

    public Orderdetails getOrder() {
        return order;
    }

    public void setOrder(Orderdetails order) {
        this.order = order;
    }

    @GetMapping("/listById")
    public Result selectAll(Integer id){
        List<Detailedcost> detailedcostList = detailedcostService.list(new QueryWrapper<Detailedcost>().eq("patientsid", id).eq("state", 0));
        return Result.success(detailedcostList);
    }


    //点击结算  确定
    //前往支付宝第三方网关进行支付
    @ApiOperation(value = "支付宝支付",httpMethod = "POST",notes = "支付宝接口")
    @ApiImplicitParam(name = "medicalrecord",value = "支付宝",required = true)
    @PostMapping(value="/pay",produces = "application/json;charset=utf-8")
    @Transactional
    public Result pay(@RequestBody Orderdetails orderdetails) throws AlipayApiException {


        this.setOrder(orderdetails);

        //System.out.println("medical+++++"+medical);
        System.out.println("前往支付宝第三方网关进行支付To");
        //获得初始化的AlipayClient
        AlipayClient alipayClient =new DefaultAlipayClient(AppUtil.gatewayUrl, AppUtil.app_id, AppUtil.merchant_private_key, "JSON", AppUtil.charset, AppUtil.alipay_public_key, AppUtil.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AppUtil.return_urlTo);
        alipayRequest.setNotifyUrl(AppUtil.notify_urlTo);
        //订单号
        String out_trade_no = UUID.randomUUID().toString().substring(0,9);

        /**
         * 判断当前患者是否是会员
         * 如果是会员，开始会员结算
         * 开始判断会与余额是否充足
         * 充足：会员结算
         * 不足：二维码结算
         */
        //患者信息
        Patients patients = patientsService.getById(orderdetails.getPatientsid());
        //会员信息
        Member member = memberService.getOne(new QueryWrapper<Member>().eq("mphone", patients.getPhone()));

        if(member!=null){

            // 有会员情况下支付

            //会员余额
            Double mmoney = member.getMmoney();

            Double payamount =(double) orderdetails.getPayamount();

            if(mmoney>=payamount){

                //余额充足 扣除余额
                double mos = mmoney - (payamount*0.9) ;
                member.setMmoney(mos);
                memberService.updateById(member);

                /**
                 * 保存订单信息
                 * 修改病人状态
                 * 添加病历信息
                 */
                this.order.setPayamount((int)(payamount*0.9));
                updatePatientsAndSaveCasehistory(this.order,true);

                return Result.success("");

            }else{
                //余额不足
                return Result.success(paySelect(out_trade_no,orderdetails.getPayamount(),orderdetails.getPayment(),"测试：",alipayRequest,alipayClient));
            }

        }else{
            // 没有会员情况下支付
            return Result.success(paySelect(out_trade_no,orderdetails.getPayamount(),orderdetails.getPayment(),"测试：",alipayRequest,alipayClient));

        }
    }




    @RequestMapping("/getreturn")
    @Transactional
    public void getreturn(HttpServletRequest request, HttpServletResponse response){

        boolean success = false;

        try {
            //获取支付宝GET过来反馈信息
            System.out.println("获取支付宝GET过来反馈信息");
            Map<String,String> params = new HashMap<String,String>();
            Map<String,String[]> requestParams = request.getParameterMap();
            Iterator<String> iter = requestParams.keySet().iterator();
            while (iter.hasNext()){
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr ="";
                for (int i=0;i<values.length;i++) {
                    valueStr = (i==values.length-1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //这段代码在出现乱码是使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name,valueStr);
            }
            //RSA2验证
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AppUtil.alipay_public_key,AppUtil.charset,AppUtil.sign_type);
            System.out.println("signVerified："+signVerified);
            //调试用,写文本函数记录程序运行情况是否正常  写入文本
            String sword  = AlipaySignature.getSignCheckContentV1(params);
            AppUtil.logResult(sword);

            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            System.out.println(out);
            if(signVerified){//验证成功
                //商品订单号
                String out_trade_no = request.getParameter("out_trade_no");
                //支付宝交易号
                String trade_no = request.getParameter("trade_no");
                //交易状态  金额
                String total_amount = request.getParameter("total_amount");
                // 修改充值状态为支付成功  并加上支付金额

                /**
                 * 保存订单信息
                 * 修改病人状态
                 * 添加病历信息
                 */
                updatePatientsAndSaveCasehistory(this.order,false);

                out.write("out_trade_no"+trade_no+"<br/>trade_no"+out_trade_no+"<br/>total_amount"+total_amount+"<br/><h3>交易成功!<h3/>");

                success = true;

            }else{//验证失败
                out.println("验签失败");
                success = false;
            }

        }catch (AlipayApiException e){
            success = false;

        }catch (IOException e){
            success = false;
        }

        setState(success);
    }



    //接受支付宝返回的异步通知的信息
    @RequestMapping("/getnotify")
    public void getnotify(HttpServletRequest request, HttpServletResponse response)throws AlipayApiException, IOException {
        //获取支付宝POST过来反馈信息
        System.out.println("获取支付宝POST过来反馈信息");
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        Iterator<String> iter = requestParams.keySet().iterator();
        while (iter.hasNext()){
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr ="";
            for (int i=0;i<values.length;i++) {
                valueStr = (i==values.length-1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //这段代码在出现乱码是使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name,valueStr);
        }
        //回调SDK验签
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AppUtil.alipay_public_key,AppUtil.charset,AppUtil.sign_type);
        //实际验证过程建议商务必须添加一下效验
        //1.需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
        //2.判断total_amout是否确实为该订单的实际金额(及商品订单创建时的金额)
        //3.效验通知中的seller_id(或者seller_email)是否out_trade_no这笔单据的对应的操作放
        //有的时候,一个商户可能有多个seller_id/seller_email
        //4.验证app_id是否为该商户本身.
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(signVerified){//验证成功
            //商品订单号
            String out_trade_no = request.getParameter("out_trade_no");
            //支付宝交易号
            String trade_no =request.getParameter("trade_no");
            Integer memberid =Integer.valueOf(request.getParameter("memberid"));
            //交易状态
            String trade_status = request.getParameter("trade_status");
            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做处理,根据订单号(out_trade_no) 在商户网站的订单系统中查到该订单的详细,并执行商户的业务程序
                //如果有做过处理,不执行商户的业务程序

                //注意:
                //退款日期超过可退款期限后(如三个月可退款),支付宝系统发送该交易状态通知
            }else if(trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商品中已经做过处理
                //如果没有做过处理,根据订单号(out_trade_no)在商户网站的订单系统中查到该笔订单的详细,并执行商户的业务程序
                //如果没有处理,不执行商户的业务程序

                //注意:
                //付款完成后,支付宝系统发送该交易状态通知

            }
            out.println("success");
        }else{//验证失败
            out.println("fail");
            //调试用,写文本函数记录程序运行情况是否正常
            String sword  = AlipaySignature.getSignCheckContentV1(params);
            AppUtil.logResult(sword);
        }
    }


}
