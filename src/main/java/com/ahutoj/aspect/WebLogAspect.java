package com.ahutoj.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
public class WebLogAspect
{
    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.ahutoj.controller.*.*(..)))")

    public void webLog()
    {

    }

    //需要在拦截点的前和后分别拦截，之前是请求信息，之后是响应信息
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint)
    {
        //收到请求，记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String msg = "REQUEST URL:" + request.getRequestURL().toString();
        msg += " METHOD:" + request.getMethod();
        msg += " CONTROLLER:" + joinPoint.getSignature().getName();
        msg += " ARGS:" + Arrays.toString(joinPoint.getArgs());
        log.info(msg);
    }

    //响应
    @AfterReturning(returning = "res", pointcut = "webLog()")//参数，（返回的内容，拦截的点）
    public void doAfterReturning(Object res) throws JsonProcessingException
    {
        //处理完请求返回内容
        log.info("RESPONSE : " + new ObjectMapper().writeValueAsString(res));
    }
}