package com.ahutoj.controller;

import com.ahutoj.bean.Solution;
import com.ahutoj.bean.User;
import com.ahutoj.constant.ResCode;
import com.ahutoj.exception.InvalidParamException;
import com.ahutoj.service.SolutionService;
import com.ahutoj.service.SolutionThumbUpService;
import com.ahutoj.service.UserService;
import com.ahutoj.utils.MoreTransaction;
import com.ahutoj.utils.ParamCheckUtil;
import com.ahutoj.utils.ResCodeSetter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum/solution")
public class SolutionController
{
    private final SolutionService solutionService;
    private final SolutionThumbUpService solutionThumbUpService;
    private final UserService userService;

    private final Gson gson;

    @Autowired
    public SolutionController(SolutionService solutionService, UserService userService, SolutionThumbUpService solutionThumbUpService, Gson gson)
    {
        this.solutionService = solutionService;
        this.userService = userService;
        this.solutionThumbUpService = solutionThumbUpService;
        this.gson = gson;
    }

    @PostMapping("/add/{PID}")
    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> publishSolution(@PathVariable("PID") Integer PID, @RequestBody Map<String, Object> req)
    {
        if (!req.containsKey("UID") || !req.containsKey("Content"))
        {
            throw new InvalidParamException();
        }
        String UID = (String) req.get("UID");
        String Content = (String) req.get("Content");
        Map<String, Object> ret = new HashMap<>();
        Integer res = solutionService.addSolution(PID, UID, Content);
        if (res <= 0)
        {
            ResCodeSetter.setResCode(ret, ResCode.PublishSolutionError);
            return ret;
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
     * @Params [PID 题目id, UID 用户ID, Page 页面, Limit 页面展示数量]
     **/
    @GetMapping("/list/pid/{PID}")
    public Map<String, Object> getSolutionsByPID(@PathVariable("PID") Integer PID, String UID, Integer Page, Integer Limit)
    {
        Map<String, Object> ret = new HashMap<>();
        //获取solution列表再根据UID获取获取用户信息
        if (null == PID && null == UID)
            throw new InvalidParamException();
        //页面从0开始
        if (null == Page)
        {
            Page = 0;
        }
        if (null == Limit)
        {
            Limit = 10;
        }
        List<Solution> solutionList = solutionService.getSolutions(PID, UID, Page, Limit);
        List<Map<String, Object>> data = new LinkedList<>();
        for (Solution solution : solutionList)
        {
            //获取用户信息
            User user = userService.getUserInfoByUID(solution.getUID());
            //获取本用户点赞情况
            Integer IThumbUp = solutionThumbUpService.getSolutionUserThumbUpState(solution.getSLTID(), UID);
            Map<String, Object> temp = new HashMap<>();
            temp.putAll(gson.fromJson(gson.toJson(solution), Map.class));
            temp.putAll(gson.fromJson(gson.toJson(user), Map.class));
            temp.put("IThumbUp", IThumbUp);
            data.add(temp);
        }
        ResCodeSetter.setResCode(ret, ResCode.success, data);
        return ret;
    }

    /**
     * @Description 获取用户的题目题解
     * @Params [UID 用户ID, Page 页面, Limit 页面展示数量]
     **/
    @GetMapping("/list/uid/{UID}")
    public Map<String, Object> getSolutionsByUID(@PathVariable("UID") String UID, Integer Page, Integer Limit)
    {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    @GetMapping("/{SLTID}")
    //    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> getSolution(@PathVariable("SLTID") Integer SLTID)
    {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    @PostMapping("/thumbup/{SLTID}")
    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> changeThumbUpState(@PathVariable("SLTID") Integer SLTID, @RequestBody Map<String, Object> req)
    {
        if (!req.containsKey("UID") || !req.containsKey("State"))
        {
            throw new InvalidParamException();
        }
        String UID = (String) req.get("UID");
        Integer State = (Integer) req.get("State");
        Map<String, Object> ret = new HashMap<>();
        //参数校验
        ParamCheckUtil.notNull(SLTID);
        ParamCheckUtil.stringNotEmpty(UID);
        ParamCheckUtil.notNull(State);
        Integer res = solutionThumbUpService.updateSolutionThumbUpState(SLTID, UID, State);
        if (res == 0)
        {
            //说明被限制
            ResCodeSetter.setResCode(ret, ResCode.AstrictUserChangeThumbUpState);
            return ret;
        }
        ResCodeSetter.setResCode(ret, ResCode.success, res);
        return ret;
    }


    //todo 修改题解内容


    //todo 审核通过

}
