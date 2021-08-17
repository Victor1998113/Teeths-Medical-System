package com.oral.controller;

import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oral.bean.Member;
import com.oral.bean.Statistics;
import com.oral.common.lang.Result;
import com.oral.utils.AppUtil;
import com.oral.utils.Join;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author panjianhui
 * @Date
 */
@RestController
@RequestMapping("/sys/member")
@Api(value = "会员列表", description = "会员列表接口")
public class MemberController extends BaseController{
    //查询会员列表信息
    @GetMapping("/list")
    @ApiOperation(value = "查询方法", httpMethod = "GET", notes = "根据会员姓名进行模糊查询并分页")
    @ApiImplicitParam(name = "mname", value = "会员姓名", required = false)
    public Result list(String mname){
        Page<Member> page = memberService.page(getPage(), new QueryWrapper<Member>().like(StrUtil.isNotBlank(mname), "mname", mname));
        return Result.success(page);
    }

    //添加
    @ApiOperation(value = "添加方法",httpMethod = "POST",notes = "添加会员列表")
    @ApiImplicitParam(name = "member",value = "会员列表对象",required = true)
    @PostMapping("/save")
    @Transactional
    public Result save(@RequestBody Member member) {

        Member memberServiceOne = memberService.getOne(new QueryWrapper<Member>().eq("mphone", member.getMphone()));

        if(memberServiceOne==null){
            member.setMmoney(0d);
            //添加会员
            memberService.save(member);
            return Result.success("");
        }else {
            return Result.fail(400,"会员号码已经存在","");
        }
    }
    //点击充值  输入充值金额   确认输入金额后  进入支付宝支付 修改金额数量
    @ApiOperation(value = "根据id查询",httpMethod = "GET",notes = "根据单个id查询会员列表")
    @ApiImplicitParam(name = "member",value = "会员列表对象",required = true)
    @GetMapping("/selectMemberId")
    public Result selectMemberId(@RequestParam("memberid")Integer memberid){
       Member member = memberService.getById(memberid);
        return Result.success(member);
    }

    //点击付款
    //前往支付宝第三方网关进行支付
    @ApiOperation(value = "支付宝支付",httpMethod = "POST",notes = "支付宝接口")
    @ApiImplicitParam(name = "member",value = "支付宝",required = true)
    @PostMapping(value="/pay",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result pay(@RequestBody  Member member) throws AlipayApiException {
        System.out.println("member+++++"+member);
        System.out.println("前往支付宝第三方网关进行支付");
        //获得初始化的AlipayClient
        AlipayClient alipayClient =new DefaultAlipayClient(AppUtil.gatewayUrl, AppUtil.app_id, AppUtil.merchant_private_key, "JSON", AppUtil.charset, AppUtil.alipay_public_key, AppUtil.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AppUtil.return_url);
        alipayRequest.setNotifyUrl(AppUtil.notify_url);
        //订单号
       String out_trade_no = member.getMemberid()+UUID.randomUUID().toString().substring(0,9);
       //付款金额，必填
        Double total_amount = member.getMmoney();
       //订单名称，必填
        String subject = member.getMname();
       //商品描述，可空
        String body = "测试：";
       //商品描述，可空

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return Result.success(result);

    }


    @RequestMapping("/getreturn")
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
                String trade_no =request.getParameter("trade_no");
                //交易状态  金额
                String total_amount = request.getParameter("total_amount");
                System.out.println(Integer.parseInt(out_trade_no.substring(0,1)));
                // 修改充值状态为支付成功  并加上支付金额

//            memberService.updateMemberStatus(Integer.parseInt(out_trade_no.substring(0,1)),String.valueOf(total_amount));
                memberService.updateMemberStatus(out_trade_no.substring(0,1),trade_no, Double.valueOf(total_amount));
                Member member =memberService.getById(out_trade_no);
                System.out.println("测试:"+member);

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
