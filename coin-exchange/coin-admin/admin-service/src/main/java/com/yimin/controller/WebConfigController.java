package com.yimin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.WebConfig;
import com.yimin.model.R;
import com.yimin.service.WebConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;
/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 21:25
 *   @Description :
 *
 */
@RestController
@Api(tags = "webConfig的控制器")
@RequestMapping("/webConfigs")
public class WebConfigController {

    @Autowired
    private WebConfigService webConfigService;

    @GetMapping
    @ApiOperation(value = "分页查询我们的webConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "webConfig 的名称") ,
            @ApiImplicitParam(name = "type",value = "webConfig 的类型") , // integer current ,Integer size
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的大小") ,
    })
    @PreAuthorize("hasAuthority('web_config_query')")
    public R<Page<WebConfig>> findByPage(@ApiIgnore Page<WebConfig> page, String name, String type){
        Page<WebConfig> webConfigPage = webConfigService.findByPage(page, name, type);
        return R.ok(webConfigPage);
    }

    @PostMapping
    @ApiOperation(value = "新增一个WebConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig" ,value = "webConfig 的json 数据")
    })
    @PreAuthorize("hasAuthority('web_config_create')")
    public R add(@RequestBody @Validated WebConfig webConfig){
        boolean save = webConfigService.save(webConfig);
        if(save){
            return R.ok();
        }
        return R.fail("新增失败");
    }


    @PatchMapping
    @ApiOperation(value = "修改一个WebConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig" ,value = "webConfig 的json 数据")
    })
    @PreAuthorize("hasAuthority('web_config_update')")
    public R update(@RequestBody @Validated WebConfig webConfig){
        boolean flag = webConfigService.updateById(webConfig);
        if(flag){
            return R.ok();
        }
        return R.fail("修改失败");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除WebConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids" ,value = "要删除的数据的ids")
    })
    @PreAuthorize("hasAuthority('web_config_delete')")
    public R update(@RequestBody String[] ids){
        if(ids == null || ids.length == 0){
            return R.fail("删除需要给定删除数据的ID");
        }
        boolean flag = webConfigService.removeByIds(Arrays.asList(ids));
        if(flag){
            return R.ok();
        }
        return R.fail("删除失败");
    }


    @GetMapping("/banners")
    @ApiOperation(value = "获取我们pc端的banner图")
    public R<List<WebConfig>> banners(){
        List<WebConfig> banners = webConfigService.getBanners();
        return R.ok(banners);
    }
}
