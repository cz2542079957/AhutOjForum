package com.ahutoj.utils;

import com.ahutoj.constant.ResCode;
import com.ahutoj.exception.InvalidParamException;
import com.ahutoj.exception.NotFoundException;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class CommonExceptionHandler
{

    @ExceptionHandler({InvalidParamException.class, PropertyAccessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleInvalidParamException(Exception ex)
    {
        Map<String, Object> ret = new HashMap<>();
        ResCodeSetter.setResCode(ret, ResCode.InvalidParamCode);
        return ret;
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, Object> handleNotFoundException(NotFoundException ex)
    {
        Map<String, Object> ret = new HashMap<>();
        ResCodeSetter.setResCode(ret, ResCode.PageNotFoundCode);
        return ret;
    }
 
}