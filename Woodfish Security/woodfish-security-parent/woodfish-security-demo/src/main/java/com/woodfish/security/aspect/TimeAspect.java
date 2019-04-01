package com.woodfish.security.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeAspect {

    @Around("execution(* com.woodfish.security.controller.UserController.*(*.*))")
    public Object method(ProceedingJoinPoint pjp) throws Throwable {

        Object[] args = pjp.getArgs();
        for (Object obj:
             args) {
            System.out.println(obj);
        }
        System.out.println(pjp.getTarget()+"方法开始调用");
        long beginTime = System.currentTimeMillis();
        Object userControllerObj = pjp.proceed();  //被拦截住的方法
        long endTime = System.currentTimeMillis();
        System.out.println("方法运行时间: " + (endTime-beginTime));
        return  userControllerObj;
    }
}
