package com.ahutoj.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GsonUtil
{
    public Gson gson;

    public GsonUtil()
    {
        gson = new GsonBuilder().enableComplexMapKeySerialization().create();
    }

    @Bean("Gson")
    public Gson getGson()
    {
        return gson;
    }
}
