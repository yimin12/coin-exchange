package com.yimin.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.Notice;
import com.yimin.model.R;
import com.yimin.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 17:58
 *   @Description :
 *
 */
@RestController
@RequestMapping("/notices")
@Api(tags = "公告管理")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页查询
     */
    @GetMapping
    @ApiOperation(value = "分页查询公告")
    @PreAuthorize("hasAuthority('notice_query')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "title", value = "公告的标题"),
            @ApiImplicitParam(name = "startTime", value = "公告的创建开始时间"),
            @ApiImplicitParam(name = "endTime", value = "公告的创建结束时间时间"),
    })
    public R<Page<Notice>> findByPage(@ApiIgnore Page<Notice> page, String title, String startTime, String endTime, Integer status){
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(noticeService.findByPage(page, title, startTime, endTime, status));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除一个公告")
    @PreAuthorize("hasAuthority('notice_delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要删除的id 的集合")
    })
    public R delete(@RequestBody String[] ids){
        if(ids == null || ids.length == 0){
            return R.fail("删除时需要id的值");
        }
        boolean flag = noticeService.removeByIds(Arrays.asList(ids));
        if(flag){
            return R.ok();
        }
        return R.fail("删除失败");
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "启用/禁用一个公告")
    @PreAuthorize("hasAuthority('notice_update')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要启用/禁用一个公告的公告Id"),
            @ApiImplicitParam(name = "status", value = "要设置的公告状态")
    })
    public R updateStatus(Long id, Integer status){
        Notice notice = new Notice();
        notice.setId(id);
        notice.setStatus(status);
        boolean flag = noticeService.updateById(notice); // 局部的修改：不为 null
        if(flag) {
            return R.ok("修改成功");
        }
        return R.fail("修改失败");
    }

    @PostMapping
    @ApiOperation(value = "新增一个公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notice" ,value = "notice 的json 数据")
    })
    @PreAuthorize("hasAuthority('notice_create')")
    public R add(@RequestBody @Validated Notice notice){
        notice.setStatus(1);
        boolean save = noticeService.save(notice);
        if(save){
            return R.ok();
        }
        return R.fail("新增公告失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改一个公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notice" ,value = "notice 的json 数据")
    })
    @PreAuthorize("hasAuthority('notice_update')")
    public R update(@RequestBody @Validated Notice notice){
        boolean update = noticeService.updateById(notice);
        if(update){
            return R.ok();
        }
        return R.fail("修改公告失败") ;
    }

    @GetMapping("/simple")
    @ApiOperation(value = "查询前台展示的notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
    })
    public R<Page<Notice>> findNoticeForSimple(Page<Notice> page){
        Page<Notice> pageData = noticeService.findNoticeSimple(page);
        return R.ok(pageData);
    }

    @GetMapping("/simple/{id}")
    @ApiOperation(value = "查询某条Notice的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "notice的id")
    })
    public R<Notice> noticesSimpleInfo(@PathVariable("id")Long id){
        Notice notice = noticeService.getById(id);
        return R.ok(notice);
    }
}
