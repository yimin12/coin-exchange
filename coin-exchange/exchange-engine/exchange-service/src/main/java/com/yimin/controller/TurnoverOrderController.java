package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.TurnoverOrder;
import com.yimin.model.R;
import com.yimin.service.TurnoverOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 14:02
 *   @Description :
 *
 */
@RestController
@RequestMapping("/turnoverOrders")
@Api(tags = "成交记录")
public class TurnoverOrderController {

    @Autowired
    private TurnoverOrderService turnoverOrderService;

    public R<Page<TurnoverOrder>> findByPage(@ApiIgnore Page<TurnoverOrder> page, String symbol, Integer type){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Page<TurnoverOrder> pageData = turnoverOrderService.findByPage(page,userId,symbol, type);
        return R.ok(pageData);
    }
}
