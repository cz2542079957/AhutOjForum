package com.ahutoj.utils;


import com.ahutoj.AhutOjForumApplication;
import com.ahutoj.exception.InvalidParamException;

public class ParamCheckUtil
{
    public static boolean notNull(Object param)
    {
        if (null == param)
        {
            AhutOjForumApplication.log.warn("出现参数错误");
            throw new InvalidParamException();
        }
        return true;
    }

    public static boolean stringNotEmpty(String param)
    {
        if (null == param)
        {
            AhutOjForumApplication.log.warn("出现参数错误");
            throw new InvalidParamException();
        }
        else
        {
            if (param.isEmpty())
                throw new InvalidParamException();
            else
                return true;
        }
    }

    /**
     * @Description 数字需要是正确的大于等于0的
     * @Params t 数字
     **/
    public static <T> boolean numberShouldValid(T t)
    {
        if (null == t)
            throw new InvalidParamException();
        if (t instanceof Number)
        {
            if (((Number) t).doubleValue() >= 0)
            {
                return true;
            }
        }
        throw new InvalidParamException();
    }
}
