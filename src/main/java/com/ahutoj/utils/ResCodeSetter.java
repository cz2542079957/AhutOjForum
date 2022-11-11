package com.ahutoj.utils;


import com.ahutoj.constant.ResCode;

import java.util.Map;

public class ResCodeSetter
{
    public static void setResCode(Map<String, Object> ret, ResCode type)
    {
        ret.put("code", type.getCode());
        ret.put("msg", type.getMsg());
    }

    public static void setResCode(Map<String, Object> ret, ResCode type, Object object)
    {
        ret.put("code", type.getCode());
        ret.put("msg", type.getMsg());
        ret.put("data", object);
    }

}
