package com.ahutoj.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ResCode
{
    success(0, ""),
    ServerErrorCode(100001, "服务器错误"),
    InvalidParamCode(100002, "请求参数错误"),
    PageNotFoundCode(100003, "页面不存在"),
    NotimplementedCode(100004, "接口未实现"),

    //题解接口 31

    ;

    @Setter
    private Integer code;

    @Setter
    private String msg;

}
