//package com.oral.utils.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTCreator;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.Verification;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author tanyongpeng
// * @Date 2021/7/1 10:57
// * @Version 1.0
// *
// * token处理
// */
//public class JWTUtil {
//
//    private static final String SING = "!@#DASD*&21SS";
//
//
//    /**
//     * 生成token
//     * @param map
//     * @return
//     */
//    public static String getToken(Map<String,String> map){
//        Calendar instaance = Calendar.getInstance();
//        instaance.add(Calendar.DATE,7);// 默认七天过期
//
//        JWTCreator.Builder builder = JWT.create();
//
//        //payload
//        map.forEach((k,v)->{
//           builder.withClaim(k,v);
//        });
//
//        String token = builder.withExpiresAt(instaance.getTime())
//                .sign(Algorithm.HMAC256(SING));
//
//        return token;
//    }
//
//    /**
//     * 验证token
//     * @param token
//     */
//    public static DecodedJWT verifyToken(String token){
//       return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
//    }
//
////    /**
////     * 获取token信息方法
////     * @param token
////     * @return
////     */
////    public static DecodedJWT getTokenInfo(String token){
////        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
////        return verify;
////    }
//
//
//}
