package com.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @Author: Xiaok
 * @Date: 2019/8/21 11:12
 * @version: 1.0
 * @Description:
 */
public class JWTUtils {
    // token过期时间默认3分钟，毫秒
    private static long EXPIRE_TIME = 3*60*1000;

    /**
     * 创建JWT
     * @param id 唯一id，此设置为token的id
     * @param username 用户名
     * @param secret 秘钥
     * @return
     */
    public static String createdJWT(String id, String username, String secret){
        try {
            Date issuedAt = new Date(); //签发时间
            Date date = new Date(issuedAt.getTime() + EXPIRE_TIME); //过期时间
            Algorithm algorithm = Algorithm.HMAC256(secret); //签名
            return JWT.create().withJWTId(id).withIssuer(username).withIssuedAt(issuedAt).withExpiresAt(date).sign(algorithm);
        }catch (Exception e){
            System.out.println("------【创建jwt异常】------");
            return null;
        }
    }

    public static void main(String[] args){
        /*String token = createdJWT("1","admin","xiaok");
        System.out.println(token);*/
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhZG1pbiIsImV4cCI6MTU2NjM1OTYxNiwiaWF0IjoxNTY2MzU5NDM2LCJqdGkiOiIxIn0.gWRNvITISOgC8ORR1ay3Lcz0urKYPLLsjVSrfCO8ND0
        DecodedJWT jwt = JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhZG1pbiIsImV4cCI6MTU2NjM1OTYxNiwiaWF0IjoxNTY2MzU5NDM2LCJqdGkiOiIxIn0.gWRNvITISOgC8ORR1ay3Lcz0urKYPLLsjVSrfCO8ND0");
        System.out.println(jwt);
    }

    /*public static String createSign(String username, String password){
        return null;
    }*/

}
