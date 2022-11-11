package com.ahutoj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.annotation.RequestScope;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParamException extends RuntimeException
{
}
