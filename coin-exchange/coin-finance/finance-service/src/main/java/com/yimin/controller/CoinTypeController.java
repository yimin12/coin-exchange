package com.yimin.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CoinType;
import com.yimin.model.R;
import com.yimin.service.CoinTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 15:43
 *   @Description :
 *
 */
@RestController
@RequestMapping("/coinTypes")
@Api(value = "币种类型的控制器")
public class CoinTypeController {

    @Autowired
    private CoinTypeService coinTypeService;

    @GetMapping
    @ApiOperation(value = "条件分页查询我们的币种类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页显示的条数") ,
            @ApiImplicitParam(name = "code",value = "币种类型") ,
    })
    @PreAuthorize("hasAuthority('trade_coin_type_query')")
    public R<Page<CoinType>> findByPage(@ApiIgnore Page<CoinType>page, String code){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<CoinType> coinTypePage = coinTypeService.findByPage(page, code);
        return R.ok(coinTypePage);
    }

    @PostMapping
    @ApiOperation(value = "新增货币类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coinType" ,value = "coinType 的json")
    })
    @PreAuthorize("hasAuthority('trade_coin_type_create')")
    public R add(@RequestBody @Validated CoinType coinType){
        boolean save = coinTypeService.save(coinType);
        if(save){
            return R.ok();
        }
        return R.fail("新增失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改货币的类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coinType" ,value = "coinType 的json")
    })
    @PreAuthorize("hasAuthority('trade_coin_type_update')")
    public R update(@RequestBody @Validated CoinType coinType){
        boolean update = coinTypeService.updateById(coinType);
        if(update){
            return R.ok();
        }
        return R.fail("修改失败");
    }

    @PostMapping("/setStatus")
    @ApiOperation(value = "修改货币状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" ,value = "coinType 的Id") ,
            @ApiImplicitParam(name = "status" ,value = "修改成的状态")
    })
    @PreAuthorize("hasAuthority('trade_coin_type_update')")
    public R setStatus(@RequestBody CoinType coinType){
        boolean update = coinTypeService.updateById(coinType);
        if(update){
            return R.ok() ;
        }
        return R.fail("修改失败") ;
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询所有的币种类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "币种的状态")
    })
    @PreAuthorize("hasAuthority('trade_coin_type_query')")
    public R<List<CoinType>> findAllCoinTypeByStatus(Byte status){
        List<CoinType> coinTypes = coinTypeService.listByStatus(status);
        return R.ok(coinTypes);
    }
}
