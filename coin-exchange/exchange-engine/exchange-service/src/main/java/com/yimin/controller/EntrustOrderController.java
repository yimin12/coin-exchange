package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.EntrustOrder;
import com.yimin.model.R;
import com.yimin.param.OrderParam;
import com.yimin.service.EntrustOrderService;
import com.yimin.vo.TradeEntrustOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 22:46
 *   @Description :
 *
 */
@RestController
@RequestMapping("/entrustOrders")
@Api(tags = "委托记录")
public class EntrustOrderController {

    @Autowired
    private EntrustOrderService entrustOrderService;

    @GetMapping
    @ApiOperation(value = "查询用户的委托记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current" ,value = "当前页") ,
            @ApiImplicitParam(name = "size" ,value = "显示的条数") ,
            @ApiImplicitParam(name = "symbol" ,value = "交易对") ,
            @ApiImplicitParam(name = "symbol" ,value = "类型") ,
    })
    public R<Page<EntrustOrder>> findByPage(@ApiIgnore Page<EntrustOrder> page, String symbol , Integer type){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<EntrustOrder> entrustOrderPage = entrustOrderService.findByPage(page,userId,symbol,type) ;
        return R.ok(entrustOrderPage) ;
    }

    @GetMapping("/history/{symbol}")
    @ApiOperation(value = "查询历史的委托单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "条数") ,
    })
    public R<Page<TradeEntrustOrderVo>> historyEntrustOrder(@ApiIgnore Page<EntrustOrder> page, @PathVariable("symbol") String symbol){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Page<TradeEntrustOrderVo> pageData = entrustOrderService.getHistoryEntrustOrder(page, symbol, userId);
        return R.ok(pageData);
    }

    @GetMapping("/{symbol}")
    @ApiOperation(value = "查询未的委托单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "条数") ,
    })
    public R<Page<TradeEntrustOrderVo>> entrustOrders(@ApiIgnore Page<EntrustOrder> page , @PathVariable("symbol") String symbol){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<TradeEntrustOrderVo> pageData = entrustOrderService.getEntrustOrder(page,symbol,userId) ;
        return R.ok(pageData) ;
    }


    @PostMapping
    @ApiOperation(value = "委托单的下单操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderParam",value = "orderParam json数据")
    })
    public R createEntrustOrder(@RequestBody OrderParam orderParam){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Boolean isOk = entrustOrderService.createEntrustOrder(userId,orderParam) ;
        return isOk ? R.ok() :R.fail("创建失败") ;
    }


    @ApiOperation(value = "委托单的取消操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" ,value = "委托单的id")
    })
    @DeleteMapping("/{id}")
    public R deleteEntrustOrder(@PathVariable("id") Long orderId){
        entrustOrderService.cancleEntrustOrder(orderId) ;
        return R.ok("取消成功") ;
    }
}
