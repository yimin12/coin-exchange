package com.yimin.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.WorkIssue;
import com.yimin.model.R;
import com.yimin.service.WorkIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 21:41
 *   @Description :
 *
 */
@RestController
@RequestMapping("/workIssues")
@Api(tags = "客户工单的控制器")
public class WorkIssueController {

    @Autowired
    private WorkIssueService workIssueService;

    @GetMapping
    @ApiOperation(value = "分页条件查询工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "工单当前的处理状态"),
            @ApiImplicitParam(name = "startTime", value = "工单创建的起始时间"),
            @ApiImplicitParam(name = "endTime", value = "工单创建的截至时间"),
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
    })
    @PreAuthorize("hasAuthority('work_issue_query')")
    public R<Page<WorkIssue>> findByPage(@ApiIgnore Page<WorkIssue> page, Integer status, String startTime, String endTime){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<WorkIssue> workIssuePage = workIssueService.findByPage(page, status, startTime, endTime);
        return R.ok(workIssuePage);
    }


    @PatchMapping
    @ApiOperation(value = "回复某个工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "工单的ID"),
            @ApiImplicitParam(name = "answer", value = "工单的answer"),
    })
    @PreAuthorize("hasAuthority('work_issue_update')")
    public R work_issue_update(Long id, String answer){
        WorkIssue workIssue = new WorkIssue();
        workIssue.setId(id);
        workIssue.setAnswer(answer);
        // 设置回复人的ID
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        workIssue.setAnswerUserId(Long.valueOf(userIdStr));
        boolean flag = workIssueService.updateById(workIssue);
        if(flag){
            return R.ok();
        }
        return R.fail("回复失败");
    }

    @GetMapping("/issueList")
    @ApiOperation(value = "前台查询工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current" ,value = "当前页"),
            @ApiImplicitParam(name = "size" ,value = "显示的条数")
    })
    public R<Page<WorkIssue>> getIssueList(@ApiIgnore Page<WorkIssue> page){
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Page<WorkIssue> pageData = workIssueService.getIssueList(page, Long.valueOf(userIdStr));
        return R.ok(pageData);
    }

    @PostMapping("/addWorkIssue")
    @ApiOperation(value = "会员添加问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workIssue" , value = "workIssue的json,只包含questions")
    })
    public R addWorkIssue(@RequestBody WorkIssue workIssue){
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        workIssue.setUserId(Long.valueOf(userIdStr)); // 设置用户的id
        workIssue.setStatus(1); // 设置状态
        boolean save = workIssueService.save(workIssue);
        if(save){
            R.ok("提交成功");
        }
        return R.fail("提交失败");
    }
}
