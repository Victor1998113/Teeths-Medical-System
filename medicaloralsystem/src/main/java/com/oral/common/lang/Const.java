package com.oral.common.lang;

import io.swagger.models.auth.In;

/**
 * @author tanyongpeng
 * @Date 2021/7/7 15:40
 * @Version 1.0
 */
public class Const {


    public final static Integer STATUS_ON = 1;

    public final static Integer STATUS_OFF = 0;

    public final static String DEFAULT_PASSWORD = "888888";

    // 没有人脸，检测到不是人脸
    public final static Integer FACE_CODE_NOT_FACE = 100;
    // 人脸匹配失败
    public final static Integer FACE_CODE_NOT_MATE = 300;
    //匹配成功
    public final static Integer FACE_CODE_SUCCESS = 200;
        //Check the contain
    public final static Integer CHECK_THE_CONTAIN_SUCCESS = 200;

    public final static Integer CHECK_THE_CONTAIN_FAIL = 400;
}
