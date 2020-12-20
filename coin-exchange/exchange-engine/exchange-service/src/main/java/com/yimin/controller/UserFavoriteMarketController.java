package com.yimin.controller;

import cn.hutool.core.lang.Snowflake;
import com.yimin.domain.Market;
import com.yimin.domain.UserFavoriteMarket;
import com.yimin.model.R;
import com.yimin.service.MarketService;
import com.yimin.service.UserFavoriteMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 13:32
 *   @Description :
 *
 */
@RestController
@RequestMapping("/userFavorites")
@Api(tags = "用户时长收藏控制器")
public class UserFavoriteMarketController {

    @Autowired
    private UserFavoriteMarketService userFavoriteMarketService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private Snowflake snowflake;

    @PostMapping("/addFavorite")
    @ApiOperation(value = "用户收藏某个市场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "market", value = "market的交易对和类型")
    })
    public R addFavorite(@RequestBody Market market){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        UserFavoriteMarket userFavoriteMarket = new UserFavoriteMarket();
        @NotNull String symbol = market.getSymbol(); // 市场的symbol
        Market marketDb = marketService.getMarkerBySymbol(symbol);

        userFavoriteMarket.setId(snowflake.nextId());
        userFavoriteMarket.setUserId(userId);
        userFavoriteMarket.setMarketId(marketDb.getId());
        userFavoriteMarket.setType(market.getType().intValue());
        boolean save = userFavoriteMarketService.save(userFavoriteMarket);
        if(save){
            return R.ok("收藏成功");
        }
        return R.fail("收藏失败");
    }


    @DeleteMapping("/{symbol}")
    @ApiOperation(value = "用户取消收藏某个市场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "symbol" ,value = "交易对")
    })
    public R deleteUserFavoriteMarket(@PathVariable("symbol") String symbol){
        Market markerBySymbol = marketService.getMarkerBySymbol(symbol);
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        boolean delete = userFavoriteMarketService.deleteUserFavoriteMarket(markerBySymbol.getId(), userId);
        if(delete){
            return R.ok();
        }
        return R.fail("取消失败");
    }
}
