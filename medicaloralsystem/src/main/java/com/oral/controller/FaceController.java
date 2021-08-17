package com.oral.controller;

import com.oral.common.lang.Result;
import com.oral.utils.jwt.JwtUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanyongpeng
 * @Date 2021/7/8 22:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/face")
@Api(value = "人脸",description = "人脸接口")
public class FaceController {

    @Autowired
    JwtUtils jwtUtils;


    @RequestMapping("/verify")
    public Result faceVerify(HttpServletRequest request, HttpServletResponse response){

        String jwt = jwtUtils.generateToken(request.getParameter("name"));
        response.setHeader(jwtUtils.getHeader(), jwt);

        return Result.success("操作成功!");
    }

}
