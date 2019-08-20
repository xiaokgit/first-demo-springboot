package com.aop;

import com.annotation.AopLog;
import com.annotation.AopLogType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Author: Xiaok
 * @Date: 2019/8/20 14:50
 * @version: 1.0
 * @Description:
 */

@Aspect
@Component
public class AopLogAspect {

    @Pointcut(value = "@annotation(com.annotation.AopLog)")
    public void log(){

    }

    /**
     * 前置通知
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("==========前置通知============");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();//获取当前方法
        //是否包含AopLog注解
        boolean present = method.isAnnotationPresent(AopLog.class);
        //获取当前方法的所有注解
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof AopLog){
                System.out.println("=========注解【AopLog】==========");
                AopLog aopLog = (AopLog) annotation;
                String name = aopLog.name();
                String value = aopLog.value();
                AopLogType type = aopLog.type();
                System.out.println("=======【name:"+ name +"】【value:"+ value +"】【type:"+ type +"】======");
            }
        }
    }

}
