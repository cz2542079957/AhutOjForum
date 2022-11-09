package com.ahutoj.controller;

import com.ahutoj.mapper.ahutojForum.test1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class demo
{
    @Autowired
    private test1 test1;

    @GetMapping("/insert")
    public Integer insertUser2(@PathParam("content") String content) {
        return test1.insert(content);
    }

    @GetMapping("/hello")
    public String insertUser() {
        return "hello";
    }
}
