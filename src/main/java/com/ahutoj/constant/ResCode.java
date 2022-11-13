package com.ahutoj.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ResCode
{
    success(0, "", ResCodeType.empty),
    ServerErrorCode(100001, "服务器错误", ResCodeType.error),
    InvalidParamCode(100002, "请求参数错误", ResCodeType.error),
    PageNotFoundCode(100003, "页面不存在", ResCodeType.error),
    NotimplementedCode(100004, "接口未实现", ResCodeType.error),

    //题解接口 31
    PublishSolutionError(310101, "非常抱歉，发布题解失败，可能出现了一些异常", ResCodeType.error),

    AstrictUserChangeThumbUpState(310601, "你操作太频繁了~", ResCodeType.warning);


    @Setter
    private Integer code;

    @Setter
    private String msg;

    @Setter
    private String type;

}


class ResCodeType
{
    public static String empty = "";
    public static String success = "\\\\success";
    public static String info = "\\\\info";
    public static String warning = "\\\\warning";
    public static String error = "\\\\error";
}