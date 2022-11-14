package com.ahutoj.controller;

import com.ahutoj.AhutOjForumApplication;
import com.ahutoj.bean.Solution;
import com.ahutoj.bean.SolutionComment;
import com.ahutoj.bean.User;
import com.ahutoj.constant.ResCode;
import com.ahutoj.dao.ahutojForum.SolutionCommentDao;
import com.ahutoj.exception.InvalidParamException;
import com.ahutoj.service.SolutionCommentService;
import com.ahutoj.service.SolutionService;
import com.ahutoj.service.SolutionThumbUpsService;
import com.ahutoj.service.UserService;
import com.ahutoj.utils.MoreTransaction;
import com.ahutoj.utils.ParamCheckUtil;
import com.ahutoj.utils.ResCodeSetter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum/solution")
public class SolutionController
{
    private final SolutionService solutionService;
    private final SolutionThumbUpsService solutionThumbUpsService;
    private final SolutionCommentService solutionCommentService;
    private final UserService userService;

    private final Gson gson;

    @Autowired
    public SolutionController(SolutionService solutionService, UserService userService, SolutionThumbUpsService solutionThumbUpsService, SolutionCommentService solutionCommentService, Gson gson)
    {
        this.solutionService = solutionService;
        this.userService = userService;
        this.solutionThumbUpsService = solutionThumbUpsService;
        this.solutionCommentService = solutionCommentService;
        this.gson = gson;
    }

    /**
     * @Description 创建题解（根据题目id、UID、内容Content）
     * @Params [PID, req]
     **/
    @PostMapping("/add/{PID}")
    @MoreTransaction(value = {"ahutojForumTransactionManager"})
    public Map<String, Object> publishSolution(@PathVariable("PID") String PID, @RequestBody Map<String, Object> req)
    {
        if (!req.containsKey("UID") || !req.containsKey("Content"))
        {
            throw new InvalidParamException();
        }
        String UID = (String) req.get("UID");
        String Content = (String) req.get("Content");
        Map<String, Object> ret = new HashMap<>();
        Integer res = solutionService.addSolution(PID, UID, Content);
        if (res == 0)
        {
            ResCodeSetter.setResCode(ret, ResCode.PublishSolutionError);
            return ret;
        }
        if (res == -1)
        {
            ResCodeSetter.setResCode(ret, ResCode.AstrictUserPublishSolution);
            return ret;
        }
        ResCodeSetter.setResCode(ret, ResCode.success);
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
    public Map<String, Object> getSolutionsByPID(@PathVariable("PID") String PID, String UID, Integer Page, Integer Limit)
    {
        //PID用来获取列表，UID用来获取请求人是否点赞该题解
        Map<String, Object> ret = new HashMap<>();
        //PID一定不能为空
        ParamCheckUtil.notNull(PID);
        //页面index从0开始
        Page = null == Page ? 0 : Page;
        Limit = null == Limit ? 10 : Limit;
        //获取solution单页列表
        List<Solution> solutionList = solutionService.getSolutions(PID, null, Page, Limit, 1);
        List<Map<String, Object>> data = new LinkedList<>();
        //获取总数
        Integer Count = solutionService.getSolutionsCountByPID(PID, 1);
        ret.put("Count", Count);
        for (Solution solution : solutionList)
        {
            //获取用户信息
            User user = userService.getUserInfoByUID(solution.getUID());
            //获取本用户点赞情况
            Integer IThumbUp = solutionThumbUpsService.getSolutionUserThumbUpState(solution.getSLTID(), UID);
            Map<String, Object> temp = new HashMap<>();
            temp.putAll(gson.fromJson(gson.toJson(solution), Map.class));
            if (null != user)
            {
                temp.putAll(gson.fromJson(gson.toJson(user), Map.class));
            }
            else
            {
                System.out.println(solution.toString());
                AhutOjForumApplication.log.warn("出现空User对象");
                continue;
            }
            temp.put("IThumbsUp", IThumbUp);
            data.add(temp);
        }
        ret.put("data", data);
        ResCodeSetter.setResCode(ret, ResCode.success);
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

    /**
     * @Description 修改用户点赞题解状态（根据题解id、UID、State）
     * @Params [SLTID, req]
     **/
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
        Integer res = solutionThumbUpsService.updateSolutionThumbUpState(SLTID, UID, State);
        if (res == 0)
        {
            //说明被限制
            ResCodeSetter.setResCode(ret, ResCode.AstrictUserChangeThumbUpState);
            return ret;
        }
        ResCodeSetter.setResCode(ret, ResCode.success, res);
        return ret;
    }

    /**
     * @Description 获取题解的评论（根据题解id、页面号、单页数量）
     * @Params [SLTID, Page, Limit]
     **/
    @GetMapping("/comment/{SLTID}")
    public Map<String, Object> getSolutionComment(@PathVariable("SLTID") Integer SLTID, Integer Page, Integer Limit)
    {
        Map<String, Object> ret = new HashMap<>();
        ParamCheckUtil.notNull(SLTID);
        Page = null == Page ? 0 : Page;
        Limit = null == Limit ? 0 : Limit;
        //获取总数
        Integer Count = solutionCommentService.getCommentCountBySLTID(SLTID);
        ret.put("Count", Count);
        //获取评论列表
        List<SolutionComment> list = solutionCommentService.getCommentsBySLTID(SLTID, Page, Limit);
        List<Map<String, Object>> compositeList = new LinkedList<>();   //拼接对象列表
        for (SolutionComment temp : list)
        {
            User user = userService.getUserInfoByUID(temp.getUID());
            Map<String, Object> tempObject = new HashMap<>(gson.fromJson(gson.toJson(temp), Map.class));
            if (null != user)
            {
                tempObject.putAll(gson.fromJson(gson.toJson(user), Map.class));
            }
            else
            {
                AhutOjForumApplication.log.warn("USER信息获取失败");
                continue;
            }
            compositeList.add(tempObject);
        }
        ret.put("data", compositeList);
        ResCodeSetter.setResCode(ret, ResCode.success);
        return ret;
    }

    //todo 修改题解内容


    //todo 审核通过

}
