package com.ahutoj.controller;

import com.ahutoj.bean.Solution;
import com.ahutoj.bean.User;
import com.ahutoj.constant.ResCode;
import com.ahutoj.exception.InvalidParamException;
import com.ahutoj.service.SolutionService;
import com.ahutoj.service.UserService;
import com.ahutoj.utils.JedisUtil;
import com.ahutoj.utils.MoreTransaction;
import com.ahutoj.utils.ResCodeSetter;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum/solution")
public class SolutionController
{
    private JedisUtil jedisUtil;

    private SolutionService solutionService;
    private UserService userService;

    @Autowired
    public SolutionController(SolutionService solutionService, UserService userService, JedisUtil jedisUtil)
    {
        this.solutionService = solutionService;
        this.userService = userService;
        this.jedisUtil = jedisUtil;
    }

    @PostMapping("/add/{PID}")
    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> publishSolution(@PathVariable("PID") Integer PID, @RequestBody Map<String, Object> req)
    {
        Map<String, Object> ret = new HashMap<>();
        if (!req.containsKey("UID") || !req.containsKey("Content"))
        {
            throw new InvalidParamException();
        }
        String UID = (String) req.get("UID");
        String Content = (String) req.get("Content");
        Integer res = solutionService.addSolution(PID, UID, Content);
        if (res <= 0)
        {
//            ResCodeSetter.setResCode(ret, );
        }
        ret.put("data", req);
        return ret;
    }

    @DeleteMapping("/delete/{SLTID}")
//    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> deleteSolution(@PathVariable("SLTID") Integer SLTID)
    {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    @PutMapping("/update/{SLTID}")
//    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> updateSolution(@PathVariable("SLTID") Integer SLTID, String Content)
    {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    /**
     * @Description 获取题目题解
     * @Params [PID 题目id, Page 页面, Limit 页面展示数量]
     **/
    @GetMapping("/list/{PID}")
    @MoreTransaction(value = {"ahutojTransactionManager", "ahutojForumTransactionManager"})
    public Map<String, Object> getSolutionsByPID(@PathVariable("PID") Integer PID, @PathParam("Page") Integer Page, @PathParam("Limit") Integer Limit)
    {
        Map<String, Object> ret = new HashMap<>();
        //获取solution列表再根据UID获取获取用户信息
        if (PID <= 0)
        {
            throw new InvalidParamException();
        }
        //页面从0开始
        if (null == Page)
        {
            Page = 0;
        }
        if (null == Limit)
        {
            Limit = 10;
        }
        List<Solution> solutionList = solutionService.getSolutionsByPID(PID, Page, Limit);
        List<Map<String, Object>> data = new LinkedList<>();
        for (Solution solution : solutionList)
        {
            User user = userService.getUserInfoByUID(solution.getUID());
            Map<String, Object> temp = new HashMap<>();
            temp.putAll(JSONObject.parseObject(JSONObject.toJSONString(solution), Map.class));
            temp.putAll(JSONObject.parseObject(JSONObject.toJSONString(user), Map.class));
            data.add(temp);
        }
        ResCodeSetter.setResCode(ret, ResCode.success, data);
        return ret;
    }

    @PostMapping("/delete/{SLTID}")
//    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> getSolutionByUID(@PathVariable("SLTID") Integer SLTID)
    {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }
}
